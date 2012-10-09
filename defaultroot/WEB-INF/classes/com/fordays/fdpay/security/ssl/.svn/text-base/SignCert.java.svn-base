package com.fordays.fdpay.security.ssl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;

import com.ibm.security.x509.AlgorithmId;
import com.ibm.security.x509.CertificateAlgorithmId;
import com.ibm.security.x509.CertificateIssuerName;
import com.ibm.security.x509.CertificateSerialNumber;
import com.ibm.security.x509.CertificateValidity;
import com.ibm.security.x509.X500Name;
import com.ibm.security.x509.X509CertImpl;
import com.ibm.security.x509.X509CertInfo;


public class SignCert {
	//parameters
	private String signAlias;
	private String signKeypass;
	private String userAlias;
	private String userKeypass;
	private String keystore;
	private String storepass;
	private String path;
	//temp
	private KeyStore store=null;
	private Certificate signCA = null;
	private PrivateKey privateKey = null;
	
	

	public void setSignAlias(String signAlias) {
		this.signAlias = signAlias;
	}

	public void setSignKeypass(String signKeypass) {
		this.signKeypass = signKeypass;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public void setUserKeypass(String userKeypass) {
		this.userKeypass = userKeypass;
	}

	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}

	public void setStorepass(String storepass) {
		this.storepass = storepass;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public KeyStore loadServerKeyStore()
			throws Exception {
		KeyStore ks = null;
		if (keystore == null || "".equals(keystore)
				|| storepass == null || "".equals(storepass)) {
			return null;
		}
		FileInputStream in = new FileInputStream(getPath(path,keystore));
		ks = KeyStore.getInstance(Constant.JKS);
		ks.load(in, storepass.toCharArray());
		in.close();
		return ks;
	}

	public Certificate getSignCA() throws Exception,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {
		if (signCA == null) {
			KeyStore keyStore = null;
			if(store==null){
				keyStore=loadServerKeyStore();
			}else{
				keyStore=store;
			}
			signCA = keyStore.getCertificate(signAlias);
			privateKey = (PrivateKey) keyStore.getKey(signAlias, signKeypass.toCharArray());
		}
		return signCA;
	}

	public PrivateKey getSignCAPrivateKey() throws Exception,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {
		if (privateKey == null) {
			KeyStore keyStore = null;
			if(store==null){
				keyStore=loadServerKeyStore();
			}else{
				keyStore=store;
			}
			signCA = keyStore.getCertificate(signAlias);
			privateKey = (PrivateKey) keyStore.getKey(
					signAlias, signKeypass.toCharArray());
		}
		return privateKey;
	}
	public byte[] signCert(String alias,String keypass,int days,BigInteger sn) throws Exception {
		byte[] bytes=null;
		try {
			// 加载证书库
			KeyStore ks=null;
			store = loadServerKeyStore();
			ks=store;
			// 获取管理证书
			Certificate certSecond = ks.getCertificate(userAlias);
			X509CertImpl certImpl = new X509CertImpl(certSecond.getEncoded());
			X509CertInfo certInfoSecond = (X509CertInfo) certImpl
					.get(X509CertImpl.NAME + "." + X509CertImpl.INFO);

			// 设置新证书有效日期
			certInfoSecond.set(X509CertInfo.VALIDITY,getCertValidity(days));
			// 设置新证书的序列号
			certInfoSecond.set(X509CertInfo.SERIAL_NUMBER,new CertificateSerialNumber(sn));
			// 设置新证书的签发者
			certInfoSecond.set(X509CertInfo.ISSUER + "."
					+ CertificateIssuerName.DN_NAME, this.getIssuer());
			// 新的签发者是CA的证书中读出来的
			// 设置新证书的算法，指定CA签名该证书所使用的算法为md5WithRSA
			certInfoSecond.set(CertificateAlgorithmId.NAME + "."
					+ CertificateAlgorithmId.ALGORITHM, getAlgorithm());
			/**
			 * 创建新的签名后的证书
			 */
			X509CertImpl newCert = new X509CertImpl(certInfoSecond);
			// 签名,使用CA证书的私钥进行签名，签名使用的算法为MD5WithRSA
			newCert.sign(this.getSignCAPrivateKey(),Constant.KEY_ARITHMETIC);
			PrivateKey userPrivateKey = (PrivateKey) ks.getKey(userAlias, userKeypass.toCharArray());
			java.security.cert.Certificate[] certificate = {newCert };
			
			/*
			 * 第一个参数指定所添加条目的别名，假如使用已存在别名将覆盖已存在条目，使用新别名将增加一个新条目
			 * 第二个参数为条目的私钥
			 * 第三个为设置的新口令，第四个为该私钥的公钥的证书链
			 */
			ks.setKeyEntry(alias, userPrivateKey,keypass.toCharArray(), certificate); 
			// 用新密钥替代原来的没有签名的证书的密码
			FileOutputStream out = new FileOutputStream(getPath(path,keystore));
			ks.store(out, storepass.toCharArray());
			out.close();
			
			bytes=newCert.getEncoded();
			
			
			/*BASE64Encoder base64=new BASE64Encoder();
			String cryptotext=base64.encode(userPrivateKey.getEncoded());
			System.out.println(">>>>>>\n"+cryptotext);*/
			// 生成JKS证书
			/*FileOutputStream f = new FileOutputStream(getPath(path, alias)+".cer");
			f.write(bytes);
			f.close();
			*******************************************************************************
			
			PKCS10 tmp=gen.getCertRequest(getSubject(subjects));
			byte[] bytes=tmp.getEncoded();
			BASE64Encoder base64=new BASE64Encoder();
			String cryptotext=base64.encode(bytes);
			System.out.println("*******************************");
			System.out.println("******Generate User Cert*******");
			System.out.println("*******************************");
			System.out.println(cryptotext);*/
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return bytes;
	}

	/**
	 * 得到新证书签发者
	 * 
	 * @throws Exception
	 * @return X500Name
	 */
	private X500Name getIssuer() throws Exception {
		Certificate cerfificate = this.getSignCA();
		X509CertImpl cimpl = new X509CertImpl(cerfificate.getEncoded());
		X509CertInfo certInfoFirst = (X509CertInfo) cimpl.get(X509CertImpl.NAME
				+ "." + X509CertImpl.INFO);
		X500Name issuer = (X500Name) certInfoFirst.get(X509CertInfo.SUBJECT
				+ "." + CertificateIssuerName.DN_NAME);
		return issuer;
	}

	/**
	 * 得到新证书有效日期
	 * 
	 * @throws Exception
	 * @return CertificateValidity
	 */
	private CertificateValidity getCertValidity(int days) throws Exception {
		long vValidity = (60 * 60 * 24 * 1000L) * days;
		Calendar vCal = null;
		Date vBeginDate = null, vEndDate = null;
		vCal = Calendar.getInstance();
		vBeginDate = vCal.getTime();
		vEndDate = vCal.getTime();
		vEndDate.setTime(vBeginDate.getTime() + vValidity);
		return new CertificateValidity(vBeginDate, vEndDate);
	}

	/**
	 * 得到新证书的序列号
	 * 
	 * @return CertificateSerialNumber
	 */
	private CertificateSerialNumber getCertSerualNumber(BigInteger sn) {
		return null;
	}

	/**
	 * 得到新证书的签名算法
	 * 
	 * @return AlgorithmId
	 */
	private AlgorithmId getAlgorithm() {
		return new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
	}
	/**
	 * getpath
	 * 
	 * @throws Exception
	 * @return X500Name
	 */
	private static String getPath(String path, String filename){
		return path + filename;
	}

	public static void main(String[] agrs) {
		/*try {
			SignCert sign = new SignCert();
			sign.setKeystore(".keystore");
			sign.setStorepass("password");
			sign.setPath(Constant.PATH);
			sign.setSignAlias("root_ca");
			sign.setSignKeypass("password");
			sign.setUserAlias("sign_ca");
			sign.setUserKeypass("password");
			sign.signCert("sign", "password", 365, 2009042803);
//			KeyStoreConvert.convert("JKS",Constant.PATH+".keystore", Constant.STOREPASS,
//					"client", "password", "client", "PKCS12",Constant.PATH+"client.p12","password", "client", "password");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String str="汪华!";
		byte[] bytes=str.getBytes();
		String e=new String(bytes,0,bytes.length);
		System.out.println(e);
		

	}
}
