package info.lzzy.utils;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.SecureRandom;

/*
 * AES对称加密和解密
 */
public class SymmetricEncoder {
	public static void main(String[] args) throws Exception {
        // aes + base  -->  aes + base
        String content =   "{\"mac\":\"123\",\"idfa\":\"123\",\"clientIp\":\"456\",insertTime\":\"test\"}";
        System.out.println("加密内容：" + content);
        String key = "123abc";
        System.out.println("加密密钥和解密密钥：" + key);
        String encrypt = AESEncode(key,content);
        System.out.println("加密后：" +encrypt);
        String decrypt = AESDncode( key,encrypt);
        
        System.out.println("解密后：" + decrypt);
    }

    /**
     * 编码
     * @param bstr
     * @return String
     */
    public static String Base64encode(byte[] bstr) {
        return Base64.encodeBase64String(bstr);
    }

    /**
     * 解码
     * @param str
     * @return string
     */
    public static byte[] Base64decode(String str) {
        return Base64.decodeBase64(str);
    }

    /*
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        /*防止linux下 随机生成key*/
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encryptKey.getBytes());
        kgen.init(128, random);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String AESEncode(String encryptKey, String content) throws Exception {
        return Base64encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {
        byte[] decryptBytes = new byte[0];
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            /*防止linux下 随机生成key*/
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(decryptKey.getBytes());
            kgen.init(128, random);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptKey;

    }

    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String AESDncode(String decryptKey, String encryptStr){
        return aesDecryptByBytes(Base64decode(encryptStr), decryptKey);
    }}