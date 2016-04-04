package cn.xyspace.common.core.utils.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA安全编码组件。
 *
 * @author ChenFangjie(2015/1/11 15:00:19)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public final class RSACoder {

    private static final Logger logger = LoggerFactory.getLogger(RSACoder.class);

    // 让工具类彻底不可以实例化
    private RSACoder() {
        throw new Error("工具类不可以实例化！");
    }

    /** 数字签名密钥算法 */
    public static final String KEY_ALGORITHM = "RSA";

    /** 数字签名签名/验证算法 */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /* 公钥 */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /* 私钥 */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /* RSA密钥长度 默认1024位， 密钥长度必须是64的倍数， 范围在512至65536位之间 */
    private static final int KEY_SIZE = 1024;

    /**
     * 初始化密钥。
     * 
     * @return 密钥对Map
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static Map<String, Key> initKey() {
        // 封装密钥
        Map<String, Key> keyMap = new HashMap<String, Key>(2);

        try {
            // 实例化密钥对儿生成器
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 初始化密钥对儿生成器
            keyPairGen.initialize(KEY_SIZE);
            // 生成密钥对儿
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
        }
        catch (Exception e) {
            logger.error("初始化密钥出错！", e);
        }

        return keyMap;
    }

    /**
     * 获取私钥。
     * 
     * @param keyMap
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] getPrivateKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 获取公钥。
     * 
     * @param keyMap
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] getPublicKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * 生成签名。
     * 
     * @param data
     *            待签名数据
     * @param privateKey
     *            私钥
     * @return 数字签名
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] sign(byte[] data, byte[] privateKey) {
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

        byte[] sign = null;
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 取私钥匙对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 实例化Signature
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            // 初始化Signature
            signature.initSign(priKey);
            // 更新
            signature.update(data);
            // 签名
            sign = signature.sign();
        }
        catch (Exception e) {
            logger.error("签名出错！", e);
        }

        return sign;
    }

    /**
     * 校验签名。
     * 
     * @param data
     *            待校验数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * 
     * @return 校验成功返回true，失败返回false
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) {
        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

        boolean verify = false;
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成公钥
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            // 实例化Signature
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            // 初始化Signature
            signature.initVerify(pubKey);
            // 更新
            signature.update(data);
            // 验证
            verify = signature.verify(sign);
        }
        catch (Exception e) {
            logger.error("校验出错！", e);
        }

        return verify;
    }

    /**
     * 校验签名。
     * 
     * @param data
     *            待校验数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * 
     * @return 校验成功返回true，失败返回false
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static boolean verify(String data, String publicKey, String sign) {
        return verify(StringUtils.getBytesUtf8(data), Base64.getDecoder().decode(publicKey), Base64.getDecoder().decode(sign));
    }

}
