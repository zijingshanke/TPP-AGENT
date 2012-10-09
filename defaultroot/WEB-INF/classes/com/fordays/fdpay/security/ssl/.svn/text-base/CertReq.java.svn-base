package com.fordays.fdpay.security.ssl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import com.ibm.security.x509.CertAndKeyGen;
import com.ibm.security.x509.X500Name;


public class CertReq {

	public static void generateCert(String path, String keystore,
			String storepass, String subjects, String alias,String keypass,int days)
			throws Exception {
		if (keystore == null || "".equals(keystore) || storepass == null
				|| "".equals(storepass) || subjects == null
				|| "".equals(subjects) || alias == null || "".equals(alias)) {
			throw new Exception("input parameters can not be empty!");
		}
		// 初始化证书
		CertAndKeyGen gen = new CertAndKeyGen(Constant.KEYALG, Constant.KEY_ARITHMETIC);
		gen.generate(Constant.KEY_SIZE);
		PrivateKey privateKey = gen.getPrivateKey();
		X509Certificate certificate = gen.getSelfCertificate(getSubject(subjects), getValidity(days));
		// 装载证书库
		FileInputStream in = new FileInputStream(getKeystorePath(path,
				keystore));
		KeyStore ks = KeyStore.getInstance(Constant.JKS);
		ks.load(in, storepass.toCharArray());
		in.close();
		// 新建证书存入原密钥库
		Certificate[] certFicate = { certificate };
	
		ks.setKeyEntry(alias, privateKey, keypass.toCharArray(), certFicate);
		FileOutputStream out = new FileOutputStream(getKeystorePath(path,keystore));
		ks.store(out, storepass.toCharArray());
		out.close();
		
		
		//对用户信息进行BASE64编码
		/*PKCS10 tmp=gen.getCertRequest(getSubject(subjects));
		byte[] bytes=tmp.getEncoded();
		BASE64Encoder base64=new BASE64Encoder();
		String cryptotext=base64.encode(bytes);
		System.out.println("*******************************");
		System.out.println("******Generate User Cert*******");
		System.out.println("*******************************");
		System.out.println(cryptotext);*/
	}

	/**
	 * 得到新证书签发者
	 * 
	 * @throws Exception
	 * @return X500Name
	 */
	private static X500Name getSubject(String subjects) throws Exception {
		return new X500Name(subjects);
	}

	/**
	 * 得到新证书有效日期
	 * 
	 * @throws Exception
	 * @return X500Name
	 */
	private static long getValidity(int days) throws Exception {
		long lValidity = days * 24 * 60 * 60;
		return lValidity;
	}

	/**
	 * 得到证书库Path
	 * 
	 * @throws Exception
	 * @return X500Name
	 */
	private static String getKeystorePath(String path, String keystore)
			throws Exception {
		return path + keystore;
	}

	// ***********************************************************************************************************
	public static void main(String[] args) {
		try {
			// req.generateReqCert("CN=192.168.1.190 Corporation Consumer
			// CA,O=FDPAY,OU=CS,L=ZhuHai,ST=GuangDong,C=CN", 1000,
			// "ADMIN_CA.cer", "password", "ADMIN_CA",
			// "server.keystore",Constant.PATH);

			CertReq.generateCert(
							Constant.PATH,
							".keystore",
							"password",
							"CN=192.168.1.190  Corporation Consumer CA,O=FDPAY,OU=CS,L=ZhuHai,ST=GuangDong,C=CN",
							"sign_ca","password", 360);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
