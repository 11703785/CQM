package com.platform.application.common.util;

import java.net.URLEncoder;
import java.util.Date;

import com.platform.framework.common.util.DateUtils;
import com.security.cipher.sm.SM2Utils;
import com.security.cipher.sm.Util;

public class GenerateRegistration {
	
	/**
	 * 生成注册码
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		/*//String str="AMa4ZtdET5ZwGebQaeqdKIjIe0CXAwK0GzlJCwIfOVXw$&BCYs9nkkEPJRCD5yYzc6OLqaiSSkF+Mapucj8Cm/SFwo57738sOqBya71UUXYv5d+vuDzgO7L//MaaXapajUK7g=";
		SM2Utils.generateKeyPair();
		
		*//**
		 * 生成注册码时，请注意
		 * 
		 * 修改 orgCode, begindate, endtime, orgName生成后打印出的加密字符串即 注册码
		 * 不修改上述四个字段，打印出的加密字符串即为 使用注册码
		 *//*
		// 国密规范测试私钥
		String prik = "00C6B866D7444F967019E6D069EA9D2888C87B40970302B41B39490B021F3955F0";
		String prikS = new String(Base64.encode(Util.hexToByte(prik)));
		
		//String plainText = "机构编码;版本;起始时间;结束时间;机构名称";
		//==========================试用版=========================
		
		String orgCode = "rjhc";                  //机构编码
		String orgName = "商业银行统一试用版";     //机构名称
		String orgAdd = "北京"; //地区
		String placeCode = "110000";//地区编码
		//有限期限
		Date begindate = DateUtils.parse("2016-09-07 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date endtime = DateUtils.parse("2016-10-29 23:59:59", "yyyy-MM-dd HH:mm:ss");
		
		
		//=====================注册码==============================
		
		//orgCode = "rjhc";
		//orgName = "商业银行统一试用版";     //机构名称
		
		//有限期限
		//begindate = DateUtils.parse("2016-09-07 00:00:00", "yyyy-MM-dd HH:mm:ss");
		//endtime = DateUtils.parse("2016-10-10 23:59:59", "yyyy-MM-dd HH:mm:ss");
		
		String str="MIIC0wIhANy/BEvuYWZfkARhM6UvH+/qCAMFWq772lPm3rVv6Gk1AiA/Apb0sgqNtxkqsWMCgsGcjNI+tWSk1aQ0xWcTrNWGfgQgEVPJFJruPYybA2aJ+q8dHLmZ29UyRrIcUkZ6KNJNxq0EggJofofZwsLgBgJ4A7v7wzXM72OkeNfyzBWp9K0fAf9Dc0xGUv0mr5kBpB7lcFnw8t2IXJm3vbCft64sssfouhkAAYzzFLB3d2gcosO1GvS9IUNDyWsBT1bfeHTX6JvjxMEn18dSkcrHmBXigQBX0Dz8x6lwxquY+hFRca9D1VloEgy80yM0VJlKBX5wGrOuDcfQEHkXSa65ZWltodF4kQQmeVCVx07iFET401OGaCBUwqTzEhB4bTN+beb7AUva/6d07VZiArJUCThXzi+mzb/K6ItFokRxPc/baUHM97Q2136a+J7L5dSGw0eNLVOKA1YnQEUmuXGe7eB4HEolm0D4gU7yL3W818OeSQemKvcLTjcW0G3XgVQSXcGKU7gEWmWkxURIdLw4YO7OcowCgDNQfOIG0JbgRm5mk/zYxJhMgvcP1AkKdcBdDhwh9wAlx+0zhVRRn9rx1Bg0bdJ0ysgAy7n5U7Y1kEiiyit78ZEtgfYeXTC6GKoOArq4XzuwYL1lhv4N1UwudKqp7sgOM2kkvC+sMpD/AOGbS79i5W880tpYNmVRgRCZb1+Pawcyq4oQb7FcaX7qE/FRTs3fqZGH1mEoeKRu2lWqT19qbzWD+wo6BpjBvX5mRTISQ3s0YLUCf6PkPMzA9kvfRg0nu8nxFTzG5jnOy0+6CB+qrSgbjezTBz2f7OLEkIBgpAtZbm0mdZphQR/g68keqxBGTOHv+wd7hjxahd06RDgKi7leUmdPGd4ev8HusrNhEwPMfWCaC0aoZnIGKa6LY5ULO5g03ooqV97lMhlbMOdW5ik/HYou74AoZ76PFQ==";
		System.out.println("解密: ");
		String plainTexts = new String(SM2Utils.decrypt(Base64.decode(prikS.getBytes()), Base64.decode(str.getBytes())));
		System.out.println(plainTexts);
		
		//System.out.println("prikS: " + prikS);
		//String str = SysInfo.getMsgInfo();//机器的属性
		//str = "MIIChwIhAPsANnZCTxCzqWh2un5VDh+sQMH3c2LggJRwYvz3XInQAiEAkw9/Gj1RaUxtNe0Ez2ugF8Xy5rEVcI0yAV1mRbFblFEEIK/d0Oi+IxXWE53qarFFUVxpx2XQngRx1v0IwU/14oLSBIICGxbdd73gtPDpQpZomCmg6Uip/gQhQu1uN21KRgRblhe++1kabKD8DIBRz2ec5GqUeOFXlxmt+wV7UbRrRWGEZK3+QqbYNu3LxxAE53AUVJLKqWrl+3cv6sfXfnR2kYJfJ1Vkmy7L/AqzEWoAT82vdegT2DL9MyrHXstOtzOPewe63Iq9SzsgJ/TYXtY6eIzgXTW6x3tLnlJ/ZuoYcEW1dV7NmDSF0dzcdZbvwpsq81bUpaTCBZ9yfM5TCTS0jfCDv57ZJM/pMelPkyb6OSVAz+OzFqssXk8AbPVD/1veP7DwxELO1+tCGYo8Q8pULxtRCHncVryRR/2W3LCgwWhJdGBoM2LGj8GbgBovzw2ksX25ci4scSxxC5cLd3Fuf9blqfKA3l/FkxGyMd131S7FXqS8hsG4JKBwSFZqq7C6a/FwWC7YuABJB//mPYuxtT1xNSkkbKr8oxtl7q5hsUsmYaEq23ckXqNpWaKaLrwRE6VYShcW5C5DS1QeyG0NXO6+4ml4fn/SzkithEZImCi0AfYpZ92IuxD91NaT97qX1V0ntci0M1NsmMDu/uQbYFONz28shFVtUoSSlzYwZnoM764IakGqyaWPPRJRog1xZMBFi9Kg3NGA20NedH5A84KXl6URXhRWF5Z+hR2dfRrfLBkfEuRW7K55xueu+jiHS2NThamBbaPoxbgiY8hCoIC8zXxlo0l5ndZzZwE4";
		
		str = new String(SM2Utils.decrypt(Base64.decode(prikS.getBytes()), Base64.decode(str.getBytes())));//解密机器码
		System.out.println("g"+str);
		//plainText生成是机器码还有其他的信息
		
		String orgNameCode = URLEncoder.encode(orgName,"UTF-8");
		orgAdd =  URLEncoder.encode(orgAdd,"UTF-8");

		String plainText = orgCode+";version 1.0;"+ begindate.getTime()+";"+endtime.getTime()+";"+orgNameCode+";"+orgAdd+";"+str+";"+placeCode;
		byte[] sourceData = plainText.getBytes();


		// 国密规范测试公钥
		String pubk = "04262CF6792410F251083E7263373A38BA9A8924A417E31AA6E723F029BF485C28E7BEF7F2C3AA0726BBD5451762FE5DFAFB83CE03BB2FFFCC69A5DAA5A8D42BB8";
		String pubkS = new String(Base64.encode(Util.hexToByte(pubk)));
		//System.out.println("pubkS: " + pubkS);
	
		System.out.println("加密: ");
		 
		byte[] cipherText = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), sourceData);
		//System.out.println(new String(Base64.encode(cipherText)));
		String  sss=new String(Base64.encode(cipherText));
		System.out.println("sss=="+sss);

		
		//============================================================================
		
		// algorithm签名算法 SM2 
//		String algorithm = "SM3WITHSM2"; 
//		// dn主题 
//		String dn = "CN=dfg, OU=aert, O=45y, L=sdfg, ST=fg, C=CN"; 
//
//		KeyPairGenerator ecPair = null; 
//		SecureRandom rand = null; 
//		rand = SecureRandom.getInstance("TrueRandom", "FishermanJCE"); 
//		//ecPair = KeyPairGenerator.getInstance("SM2", "FishermanJCE"); 
//		ecPair.initialize(256, new SecureRandom()); 
//		// keyPair密钥对 
//		KeyPair keyPair = ecPair.generateKeyPair(); 
//		String requestReq = getP10ReqestBC(algorithm,dn,keyPair,"SM2"); 
//		System.out.println("SM2 私钥=" + keyPair.getPrivate()); 
//		System.out.println("SM2 公钥=" + keyPair.getPublic()); 
//		System.out.println("SM2 p10请求的字符串=" + requestReq); 

		System.err.println("************************************"); 

		// algorithm签名算法 RSA 
//		String algorithm2 = "SHA1withRSA"; 
//		// dn主题 
//		String dn2 = "CN=zdfg, OU=ert, O=er, L=fgj, ST=vfgh, C=CN"; 
//
//		rand = SecureRandom.getInstance("TrueRandom", "FishermanJCE"); 
//		ecPair = KeyPairGenerator.getInstance("RSA", "BC"); 
//		ecPair.initialize(1024, new SecureRandom()); 

		// keyPair密钥对 
//		KeyPair keyPair2 = ecPair.generateKeyPair(); 
//		String requestReq2 = getP10ReqestBC(algorithm2,dn2,keyPair2,"RSA"); 
//		System.out.println("RSA 私钥=" + keyPair.getPrivate()); 
//		System.out.println("RSA 公钥=" + keyPair.getPublic()); 
//		System.out.println("RSA p10请求的字符串=" + requestReq2); 
		
		*/
		
	}
	

}
