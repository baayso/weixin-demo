package cn.xyspace.common.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;
import org.springside.modules.utils.PropertiesLoader;

import com.fasterxml.jackson.databind.JavaType;
import com.jfinal.plugin.redis.Redis;

/**
 * 微信API工具类。
 *
 * @author ChenFangjie(2015年7月11日 下午8:12:42)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public final class WeixinApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(WeixinApiUtils.class);

    // 让工具类彻底不可以实例化
    private WeixinApiUtils() {
        throw new Error("工具类不可以实例化！");
    }

    private static JsonMapper jsonMapper = new JsonMapper();

    private static JavaType javaType = jsonMapper.contructMapType(HashMap.class, Object.class, Object.class);

    private static final String WEIXIN_ACCESS_TOKEN = "access_token";

    private static final String WEIXIN_JS_API_TICKET = "ticket";

    private static final PropertiesLoader PL = new PropertiesLoader("classpath:config.properties");

    public static String appId = PL.getProperty("weixin.appId");
    public static String appSecret = PL.getProperty("weixin.appSecret");

    private static String GET_ACCESS_TOKEN_URL = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);

    /**
     * 获取微信访问Token。
     * 
     * @return 微信访问Token字符串。
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String getToken() {
        String accessToken = Redis.use().get(WEIXIN_ACCESS_TOKEN);

        if (StringUtils.isBlank(accessToken)) {
            String result = HttpUtils.get(GET_ACCESS_TOKEN_URL);
            Map<Object, Object> resultMap = jsonMapper.fromJson(result, javaType);

            Object accessTokenObj = resultMap.get(WEIXIN_ACCESS_TOKEN);

            accessToken = String.valueOf(accessTokenObj);

            if (StringUtils.isBlank(accessToken)) {
                logger.error("获取微信访问Token失败！微信服务器返回的数据为：{}", result);
                return StringUtils.EMPTY;
            }

            // 缓存微信access_token，缓存时长为1小时
            Redis.use().setex(WEIXIN_ACCESS_TOKEN, 3600, accessToken);
        }

        return accessToken;
    }

    /**
     * 获取调用微信JS接口的临时票据。
     * 
     * @return 调用微信JS接口的临时票据。
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String getJsApiTicket() {
        String ticket = Redis.use().get(WEIXIN_JS_API_TICKET);

        if (StringUtils.isBlank(ticket)) {
            String accessToken = getToken();

            if (StringUtils.isBlank(accessToken)) {
                logger.error("获取微信访问Token失败！");
                return StringUtils.EMPTY;
            }

            String url = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi", accessToken);

            String result = HttpUtils.get(url);
            Map<Object, Object> resultMap = jsonMapper.fromJson(result, javaType);

            Object ticketObj = resultMap.get(WEIXIN_JS_API_TICKET);

            ticket = String.valueOf(ticketObj);

            if (StringUtils.isBlank(ticket)) {
                logger.error("获取微信js api ticket失败！微信服务器返回的数据为：{}", result);
                return StringUtils.EMPTY;
            }

            // 缓存微信js api ticket，缓存时长为1小时
            Redis.use().setex(WEIXIN_JS_API_TICKET, 3600, ticket);
        }

        return ticket;
    }

    /**
     * 获取微信JS-SDK使用权限签名。
     * 
     * @param url
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String getJsApiSignature(final String url) {
        String jsApiTicket = getJsApiTicket();
        String noncestr = RandomStringUtils.randomAlphanumeric(16);
        long timestamp = System.currentTimeMillis() / 1000;

        String temp = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s", jsApiTicket, noncestr, timestamp, url);

        // DigestUtils.sha1(temp.getBytes())
        byte[] shaSignature = Digests.sha1(temp.getBytes());
        String signature = Encodes.encodeHex(shaSignature);

        Map<String, Object> data = new HashMap<>(4);
        data.put("appId", appId);
        data.put("nonceStr", noncestr);
        data.put("timestamp", timestamp);
        data.put("signature", signature);

        return jsonMapper.toJson(data);
    }

}
