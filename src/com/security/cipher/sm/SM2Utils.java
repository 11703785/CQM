package com.security.cipher.sm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;


import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.math.ec.ECPoint;
import sun.misc.BASE64Encoder;

public class SM2Utils 
{
	public static  String str="BCYs9nkkEPJRCD5yYzc6OLqaiSSkF+Mapucj8Cm/SFwo57738sOqBya71UUXYv5d+vuDzgO7L//MaaXapajUK7g=";
	//生成随机秘钥对  
    public static void generateKeyPair(){  
        SM2 sm2 = SM2.Instance();  
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();  
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();  
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();  
        BigInteger privateKey = ecpriv.getD();  
        ECPoint publicKey = ecpub.getQ();  
          
        //System.out.println("公钥: " + Util.byteToHex(publicKey.getEncoded()));  
       // System.out.println("私钥: " + Util.byteToHex(privateKey.toByteArray()));  
    }

	public static byte[] encrypt(byte[] publicKey, byte[] data) throws IOException
	{
		if (publicKey == null || publicKey.length == 0)
		{
			return null;
		}
		
		if (data == null || data.length == 0)
		{
			return null;
		}
		
		byte[] source = new byte[data.length];
		System.arraycopy(data, 0, source, 0, data.length);
		
		Cipher cipher = new Cipher();
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
		
		ECPoint c1 = cipher.Init_enc(sm2, userKey);
		cipher.Encrypt(source);
		byte[] c3 = new byte[32];
		cipher.Dofinal(c3);
		
		DERInteger x = new DERInteger(c1.getX().toBigInteger());
		DERInteger y = new DERInteger(c1.getY().toBigInteger());
		DEROctetString derDig = new DEROctetString(c3);
		DEROctetString derEnc = new DEROctetString(source);
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(x);
		v.add(y);
		v.add(derDig);
		v.add(derEnc);
		DERSequence seq = new DERSequence(v);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DEROutputStream dos = new DEROutputStream(bos);
		dos.writeObject(seq);
		return bos.toByteArray();
	}
	
	public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException
	{
		if (privateKey == null || privateKey.length == 0)
		{
			return null;
		}
		
		if (encryptedData == null || encryptedData.length == 0)
		{
			return null;
		}
		
		byte[] enc = new byte[encryptedData.length];
		System.arraycopy(encryptedData, 0, enc, 0, encryptedData.length);
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(1, privateKey);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(enc);
		ASN1InputStream dis = new ASN1InputStream(bis);
		DERObject derObj = dis.readObject();
		ASN1Sequence asn1 = (ASN1Sequence) derObj;
		DERInteger x = (DERInteger) asn1.getObjectAt(0);
		DERInteger y = (DERInteger) asn1.getObjectAt(1);
		ECPoint c1 = sm2.ecc_curve.createPoint(x.getValue(), y.getValue(), true);
		
		Cipher cipher = new Cipher();
		cipher.Init_dec(userD, c1);
		DEROctetString data = (DEROctetString) asn1.getObjectAt(3);
		enc = data.getOctets();
		cipher.Decrypt(enc);
		byte[] c3 = new byte[32];
		cipher.Dofinal(c3);
		return enc;
	}
	
	public static byte[] sign(byte[] userId, byte[] privateKey, byte[] sourceData) throws IOException
	{
		if (privateKey == null || privateKey.length == 0)
		{
			return null;
		}
		
		if (sourceData == null || sourceData.length == 0)
		{
			return null;
		}
		
		SM2 sm2 = SM2.Instance();
		BigInteger userD = new BigInteger(privateKey);
		System.out.println("userD: " + userD.toString(16));
		System.out.println("");
		
		ECPoint userKey = sm2.ecc_point_g.multiply(userD);
		System.out.println("椭圆曲线点X: " + userKey.getX().toBigInteger().toString(16));
		System.out.println("椭圆曲线点Y: " + userKey.getY().toBigInteger().toString(16));
		System.out.println("");
		
		SM3Digest sm3 = new SM3Digest();
		byte[] z = sm2.sm2GetZ(userId, userKey);
		System.out.println("SM3摘要Z: " + Util.getHexString(z));
	    System.out.println("");
	    
	    System.out.println("M: " + Util.getHexString(sourceData));
		System.out.println("");
		
		sm3.update(z, 0, z.length);
	    sm3.update(sourceData, 0, sourceData.length);
	    byte[] md = new byte[32];
	    sm3.doFinal(md, 0);
	    
	    System.out.println("SM3摘要值: " + Util.getHexString(md));
	    System.out.println("");
	    
	    SM2Result sm2Result = new SM2Result();
	    sm2.sm2Sign(md, userD, userKey, sm2Result);
	    System.out.println("r: " + sm2Result.r.toString(16));
	    System.out.println("s: " + sm2Result.s.toString(16));
	    System.out.println("");
	    
	    DERInteger d_r = new DERInteger(sm2Result.r);
	    DERInteger d_s = new DERInteger(sm2Result.s);
	    ASN1EncodableVector v2 = new ASN1EncodableVector();
	    v2.add(d_r);
	    v2.add(d_s);
	    DERObject sign = new DERSequence(v2);
	    byte[] signdata = sign.getDEREncoded();
		return signdata;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean verifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData) throws IOException
	{
		if (publicKey == null || publicKey.length == 0)
		{
			return false;
		}
		
		if (sourceData == null || sourceData.length == 0)
		{
			return false;
		}
		
		SM2 sm2 = SM2.Instance();
		ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
		
		SM3Digest sm3 = new SM3Digest();
		byte[] z = sm2.sm2GetZ(userId, userKey);
		sm3.update(z, 0, z.length);
		sm3.update(sourceData, 0, sourceData.length);
	    byte[] md = new byte[32];
	    sm3.doFinal(md, 0);
	    System.out.println("SM3摘要值: " + Util.getHexString(md));
	    System.out.println("");
		
	    ByteArrayInputStream bis = new ByteArrayInputStream(signData);
	    ASN1InputStream dis = new ASN1InputStream(bis);
	    DERObject derObj = dis.readObject();
	    Enumeration<DERInteger> e = ((ASN1Sequence) derObj).getObjects();
	    BigInteger r = ((DERInteger)e.nextElement()).getValue();
	    BigInteger s = ((DERInteger)e.nextElement()).getValue();
	    SM2Result sm2Result = new SM2Result();
	    sm2Result.r = r;
	    sm2Result.s = s;
	    System.out.println("r: " + sm2Result.r.toString(16));
	    System.out.println("s: " + sm2Result.s.toString(16));
	    System.out.println("");
	    
	    
	    sm2.sm2Verify(md, userKey, sm2Result.r, sm2Result.s, sm2Result);
        return sm2Result.r.equals(sm2Result.R);
	}
	/** 
	* @param algorithm签名算法 1.SHA1withRSA 2.SM3WITHSM2 
	* @param dn主题 
	* @param keyPair密钥对 
	* @param keyType密钥类型 SM2|RSA 
	* @return 返回p10请求的字符串 
	* @throws SignatureException 
	* @throws NoSuchProviderException 
	* @throws NoSuchAlgorithmException 
	* @throws InvalidKeyException 
	* @throws KeyPairException 
	*/ 
	private static String getP10ReqestBC(String algorithm, String dn, KeyPair keyPair,String keyType) throws Exception{ 
		String p10RequestString = null; 
		byte[] p10buffer = null; 
		if(keyType.equals("SM2")){ 
			PKCS10CertificationRequest p10Request; 
			p10Request = new PKCS10CertificationRequest( 
			algorithm, new X500Principal(dn), keyPair.getPublic(), 
			null, keyPair.getPrivate()); 
			p10buffer = p10Request.getDEREncoded(); 
		} 
	
		p10RequestString = new BASE64Encoder().encode(p10buffer); 
	
		return p10RequestString; 
	}
	

}
