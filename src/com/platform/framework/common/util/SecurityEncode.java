package com.platform.framework.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Encoder;

/**
 * 安全加密类
 * @date 2007-10-31
 * 
 */
public class SecurityEncode {

	/**
	 * MD5方式加密字符串
	 * 
	 * @param str       要加密的字符串
	 * @return          加密后的字符串
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @comment 程序的价值体现在两个方面:它现在的价值,它未来的价值
	 */
	public static String EncoderByMd5(String str) {
		try {
			// 确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			// 加密后的字符串
			String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
			return newstr;
		} catch (Exception ex) {
			return "加密失败！";
		}
	}
	
	/** 
     * 基于des的单一密钥加密 
     * @param src 数据源 
     * @param key 密钥，长度必须是8的倍数 
     * @return  返回加密后的数据 
     * @throws Exception 
     */ 
    public static byte[] encrypt(byte[] src, byte[] key)throws Exception { 
        //DES算法要求有一个可信任的随机数源 
        SecureRandom sr = new SecureRandom(); 
        //从原始密匙数据创建DESKeySpec对象 
        DESKeySpec dks = new DESKeySpec(key); 
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成 一个SecretKey对象 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey securekey = keyFactory.generateSecret(dks); 
        // Cipher对象实际完成加密操作 
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象 
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr); 
        // 现在，获取数据并执行加密操作 
        return cipher.doFinal(src); 
	}

    /** 
    * 基于des的单一密钥解密
    * @param src 数据源
    * @param key 密钥，长度必须是8的倍数 
    * @return   返回解密后的原始数据
    * @throws Exception 
    */
    public static byte[] decrypt(byte[] src, byte[] key)throws Exception { 
        // DES算法要求有一个可信任的随机数源 
        SecureRandom sr = new SecureRandom(); 
        // 从原始密匙数据创建一个DESKeySpec对象 
        DESKeySpec dks = new DESKeySpec(key); 
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 一个SecretKey对象 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey securekey = keyFactory.generateSecret(dks); 
        // Cipher对象实际完成解密操作 
        Cipher cipher = Cipher.getInstance("DES"); 
        // 用密匙初始化Cipher对象 
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密 
        return cipher.doFinal(src);
	}
    
    /** 
     * 字符串密文解密 
     * @param data 
     * @return 
     * @throws Exception
     */ 
    public final static String decrypt(String data, String key)throws Exception{
        return new String(decrypt(hex2byte(data.getBytes()),key.getBytes())); 
    } 

    /** 
     * 密码加密 
     * @param password 
     * @return 
     * @throws Exception 
     */
    public final static String encrypt(String password, String key)throws Exception{ 
        return byte2hex(encrypt(password.getBytes(),key.getBytes()));
    }
    
    /** 
     * 二行制转字符串
     * @param b
     * @return
     */ 
    public static String byte2hex(byte[] b)throws Exception { 
        String hs = ""; 
        String stmp = ""; 
        for (int n = 0; n < b.length; n++) { 
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF)); 
            if (stmp.length() == 1) 
                hs = hs + "0" + stmp; 
            else 
                hs = hs + stmp; 
        } 
        return hs.toUpperCase(); 
	}

    public static byte[] hex2byte(byte[] b) { 
        if((b.length%2)!=0) throw new IllegalArgumentException("长度不是偶数!"); 
        byte[] b2 = new byte[b.length/2]; 
        for (int n = 0; n < b.length; n+=2) { 
        	String item = new String(b,n,2); 
            b2[n/2] = (byte)Integer.parseInt(item,16); 
        }
        return b2;
    }
    
    public static void main(String[] args){
    	System.out.println(EncoderByMd5("password"));
    }

}