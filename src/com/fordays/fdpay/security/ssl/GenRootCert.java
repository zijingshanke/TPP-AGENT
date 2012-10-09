package com.fordays.fdpay.security.ssl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import com.ibm.security.x509.CertAndKeyGen;
import com.ibm.security.x509.X500Name;

public class GenRootCert {
	public static void generateRootCert(String path, String keystore,
			String storepass, String subjects, String alias, int days)
			throws Exception {

		if (keystore == null || "".equals(keystore) || storepass == null
				|| "".equals(storepass) || subjects == null
				|| "".equals(subjects) || alias == null || "".equals(alias)) {
			throw new Exception("input parameters can not be empty!");
		}
		// 初始化证书
		CertAndKeyGen gen = new CertAndKeyGen(Constant.KEYALG,
				Constant.KEY_ARITHMETIC);
		gen.generate(Constant.KEY_SIZE);
		PrivateKey privateKey = gen.getPrivateKey();
		//
		X509Certificate certificate = gen.getSelfCertificate(
				getSubject(subjects), getValidity(days));
		// 新建证书存入密钥库
		KeyStore ks = KeyStore.getInstance(Constant.JKS);
		ks.load(null, null);
		Certificate[] certFicate = { certificate };
		ks.setKeyEntry(alias, privateKey, storepass.toCharArray(), certFicate);
		FileOutputStream out = new FileOutputStream(getKeystorePath(path,
				keystore));
		ks.store(out, storepass.toCharArray());
		out.close();
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
	/**
	 * get root ca public key
	 * 
	 * @throws Exception
	 * @return X500Name
	 */

	public static PublicKey getPublicKey() throws Exception {
		KeyStore ks = null;
		FileInputStream in = new FileInputStream(getPath(Constant.PATH, Constant.KEYSTORE));
		ks = KeyStore.getInstance(Constant.JKS);
		ks.load(in, Constant.STOREPASS.toCharArray());
		in.close();
		Certificate rootCA=ks.getCertificate(Constant.ROOT_CA_ALIAS);
		return rootCA.getPublicKey();
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
	public static void main(String[] args) {
		try {
			GenRootCert
					.generateRootCert(
							Constant.PATH,
							".keystore",
							"password",
							"CN=192.168.1.190  Corporation Root CA,O=FDPAY,OU=CS,L=ZhuHai,ST=GuangDong,C=CN",
							"root_ca", 360);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
