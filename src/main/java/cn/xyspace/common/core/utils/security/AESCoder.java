package cn.xyspace.common.core.utils.security;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES安全编码组件。
 * 
 * @author ChenFangjie(2015/1/10 13:33:37)
 * 
 * @since 1.0.0
 * 
 * @version 1.0.0
 *
 */
public final class AESCoder {

    private static final Logger logger = LoggerFactory.getLogger(AESCoder.class);

    // 让工具类彻底不可以实例化
    private AESCoder() {
        throw new Error("工具类不可以实例化！");
    }

    /**
     * 编码格式。
     * 
     * @deprecated Use Java 7's {@link java.nio.charset.StandardCharsets}
     */
    @Deprecated
    public static final String UTF_8 = "UTF-8";

    /** 密钥算法 */
    public static final String KEY_ALGORITHM = "AES";

    /** 密钥长度为128位、192位或256位 */
    public static final int KEY_SIZE = 256;

    /**
     * 加密/解密算法/工作模式/填充方式，<br>
     * Java 6支持PKCS5Padding填充方式，<br>
     * Bouncy Castle支持PKCS7Padding填充方式。
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String KEY_SEED = "3Vsqa36OvwyWHgaL1XrJ8k1+b0/RfujWfLs0/+EaejU=";

    private static byte[] keyBytes;

    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(KEY_SEED.getBytes());
            generator.init(KEY_SIZE, secureRandom);
            keyBytes = generator.generateKey().getEncoded();
            generator = null;
        }
        catch (Exception e) {
            logger.error("初始化密钥出错！", e);
        }
    }

    /**
     * 对给定字符串进行加密。
     * 
     * @param str
     *            待加密的字符串
     * @return 加密数据
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String encryptStr(String str) {
        byte[] data = encrypt(StringUtils.getBytesUtf8(str), keyBytes);
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 对给定字符串进行解密。
     * 
     * @param str
     *            待解密的字符串
     * @return 解密数据
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String decryptStr(String str) {
        byte[] data = decrypt(Base64.getDecoder().decode(str), keyBytes);
        return StringUtils.newStringUtf8(data);
    }

    /**
     * 生成密钥。
     * 
     * @return 二进制密钥
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] initKey() {
        byte[] key = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(KEY_SIZE); /* AES要求密钥长度为128位、192位或256位 */
            SecretKey secretKey = kg.generateKey(); // 生成秘密密钥
            key = secretKey.getEncoded(); // 获得密钥的二进制编码形式
        }
        catch (Exception e) {
            logger.error("生成密钥出错！", e);
        }

        return key;
    }

    /**
     * 转换密钥。
     * 
     * @param key
     *            二进制密钥
     * 
     * @return 密钥
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static Key toKey(byte[] key) {
        // 实例化AES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密。
     * 
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return 加密数据
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);

        byte[] encryptStrBytes = null;
        try {
            // 实例化 使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);
            // 执行操作
            encryptStrBytes = cipher.doFinal(data);
        }
        catch (Exception e) {
            logger.error("加密出错！", e);
        }

        return encryptStrBytes;
    }

    /**
     * 解密。
     * 
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return 解密数据
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        // 还原密钥
        Key k = toKey(key);

        byte[] decryptStrBytes = null;
        try {
            // 实例化使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);
            // 执行操作
            decryptStrBytes = cipher.doFinal(data);
        }
        catch (Exception e) {
            logger.error("解密出错！", e);
        }

        return decryptStrBytes;
    }

}
