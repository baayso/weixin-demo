package cn.xyspace.xyweixin.fun.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xyspace.common.core.utils.WeixinApiUtils;

import com.jfinal.core.Controller;

/**
 * 微信相关控制器。
 *
 * @author ChenFangjie(2015年7月12日 上午2:16:52)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class WeixinController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void signature() {
        String shareUrl = super.getPara("shareUrl");

        // request.getParameter(...)会进行第一次解码
        // 再调用 java.net.URLDecoder.decode(String s, String enc) 方法进行第二次解码
        try {
            shareUrl = URLDecoder.decode(shareUrl, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("参数解码时发生异常！", e);
        }

        super.renderJson(WeixinApiUtils.getJsApiSignature(shareUrl));
    }

}
