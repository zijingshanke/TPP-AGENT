///*
// * @(#)PKCS7Tool.java 1.0 2008-9-23
// * Copyright (c) 2008 Bank Of China Software Center
// * All rights reserved.
// */
//
//package com.fordays.fdpay.bank.boc.test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.Signature;
//import java.security.cert.CRL;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.Enumeration;
//import com.ibm.misc.BASE64Decoder;
//import com.ibm.security.pkcs7.Content;
//import com.ibm.security.pkcs7.Data;
//import com.ibm.security.pkcs7.ContentInfo;
//import com.ibm.security.pkcs7.PKCS7;
//import com.ibm.security.pkcs7.SignedData;
//import com.ibm.security.pkcs7.SignerIdentifier;
//import com.ibm.security.pkcs7.SignerInfo;
//import com.ibm.security.pkcsutil.PKCSAttributes;
//import com.ibm.security.util.ObjectIdentifier;
//import com.ibm.security.x509.AlgorithmId;
//import com.ibm.security.x509.X500Name;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//
///**
// * PKCS7Tool.java pkcs7格式签名工具 测试改造版
// */
//public class PKCS7Tool_Test {
//
//	/** 签名 */
//	private static final int SIGNER = 1;
//	/** 验证 */
//	private static final int VERIFIER = 2;
//	/** 用途 */
//	private int mode = 0;
//	/** 摘要算法 */
//	private String digestAlgorithm = "SHA1";
//	/** 签名算法 */
//	private String signingAlgorithm = "SHA1withRSA";
//	/** 签名证书链 */
//	private X509Certificate[] certificates = null;
//	/** 签名私钥 */
//	private PrivateKey privateKey = null;
//	/** 根证书 */
//	private Certificate rootCertificate = null;
//
//	/**
//	 * 私有构造方法
//	 */
//	private PKCS7Tool_Test(int mode) {
//		this.mode = mode;
//	}
//
//	/**
//	 * 取得签名工具 加载证书库, 取得签名证书链和私钥
//	 * 
//	 * @param keyStorePath
//	 *            证书库路径
//	 * @param keyStorePassword
//	 *            证书库口令
//	 * @throws GeneralSecurityException
//	 * @throws IOException
//	 */
//	public static PKCS7Tool_Test getSigner(String keyStorePath,
//			String keyStorePassword, String keyPassword)
//			throws GeneralSecurityException, IOException {
//		// 加载证书库
//		KeyStore keyStore = null;
//		if (keyStorePath.toLowerCase().endsWith(".pfx"))
//			keyStore = KeyStore.getInstance("PKCS12");
//		else
//			keyStore = KeyStore.getInstance("JKS");
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(keyStorePath);
//			keyStore.load(fis, keyStorePassword.toCharArray());
//		} finally {
//			if (fis != null)
//				fis.close();
//		}
//		// 在证书库中找到签名私钥
//		Enumeration aliases = keyStore.aliases();
//		String keyAlias = null;
//		if (aliases != null) {
//			while (aliases.hasMoreElements()) {
//				keyAlias = (String) aliases.nextElement();
//				Certificate[] certs = keyStore.getCertificateChain(keyAlias);
//				if (certs == null || certs.length == 0)
//					continue;
//				X509Certificate cert = (X509Certificate) certs[0];
//				if (matchUsage(cert.getKeyUsage(), 1)) {
//					try {
//						cert.checkValidity();
//					} catch (CertificateException e) {
//						continue;
//					}
//					break;
//				}
//			}
//		}
//		// 没有找到可用签名私钥
//		if (keyAlias == null)
//			throw new GeneralSecurityException(
//					"None certificate for sign in this keystore");
//
//		X509Certificate[] certificates = null;
//		if (keyStore.isKeyEntry(keyAlias)) {
//			// 检查证书链
//			Certificate[] certs = keyStore.getCertificateChain(keyAlias);
//			for (int i = 0; i < certs.length; i++) {
//				if (!(certs[i] instanceof X509Certificate))
//					throw new GeneralSecurityException("Certificate[" + i
//							+ "] in chain '" + keyAlias
//							+ "' is not a X509Certificate.");
//			}
//			// 转换证书链
//			certificates = new X509Certificate[certs.length];
//			for (int i = 0; i < certs.length; i++)
//				certificates[i] = (X509Certificate) certs[i];
//		} else if (keyStore.isCertificateEntry(keyAlias)) {
//			// 只有单张证书
//			Certificate cert = keyStore.getCertificate(keyAlias);
//			if (cert instanceof X509Certificate) {
//				certificates = new X509Certificate[] { (X509Certificate) cert };
//			}
//		} else {
//			throw new GeneralSecurityException(keyAlias
//					+ " is unknown to this keystore");
//		}
//
//		PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
//				keyPassword.toCharArray());
//		// 没有私钥抛异常
//		if (privateKey == null) {
//			throw new GeneralSecurityException(keyAlias
//					+ " could not be accessed");
//		}
//
//		PKCS7Tool_Test tool = new PKCS7Tool_Test(SIGNER);
//		tool.certificates = certificates;
//		tool.privateKey = privateKey;
//		return tool;
//	}
//
//	/**
//	 * 取得验签名工具 加载信任根证书
//	 * 
//	 * @param rootCertificatePath
//	 *            根证书路径
//	 * @throws GeneralSecurityException
//	 * @throws IOException
//	 */
//	public static PKCS7Tool_Test getVerifier(String rootCertificatePath)
//			throws GeneralSecurityException, IOException {
//		// 加载根证书
//		FileInputStream fis = null;
//		Certificate rootCertificate = null;
//		try {
//			fis = new FileInputStream(rootCertificatePath);
//			CertificateFactory certificatefactory = CertificateFactory
//					.getInstance("X.509");
//			try {
//				rootCertificate = certificatefactory.generateCertificate(fis);
//			} catch (Exception exception) {
//				BASE64Decoder base64decoder = new BASE64Decoder();
//				InputStream is = new ByteArrayInputStream(base64decoder
//						.decodeBuffer(fis));
//				rootCertificate = certificatefactory.generateCertificate(is);
//			}
//		} finally {
//			if (fis != null)
//				fis.close();
//		}
//
//		PKCS7Tool_Test tool = new PKCS7Tool_Test(VERIFIER);
//		tool.rootCertificate = rootCertificate;
//		return tool;
//	}
//
//	public static void main(String[] args) {
//		String keyStorePath = "D:\\淘宝网.pfx";
//		// keyStorePath="D:\\keycerts_sit.jks";//1111111a
//
//		String keyStorePassword = "11111111";
//		String keyPassword = "11111111";
//		PKCS7Tool_Test tool = null;
//		try {
//			tool = getSigner(keyStorePath, keyStorePassword, keyPassword);
//		} catch (GeneralSecurityException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String plaintext = "123456";
//		try {
//			System.out.println(tool.sign(plaintext.getBytes()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 签名
//	 * 
//	 * @param data
//	 *            数据
//	 * @return signature 签名结果
//	 * @throws Exception 
//	 * @throws IllegalArgumentException
//	 */
//	public String sign(byte[] data) throws Exception {
//		if (mode != SIGNER)
//			throw new IllegalStateException(
//					"call a PKCS7Tool instance not for signature.");
//
//		byte[] signedAttributes = signByPrivateKey(data);
//		
//		System.out.println(new String(signedAttributes));
//		
//		// 根证书
//		X509Certificate x509 = certificates[certificates.length - 1];
//
//		X500Name issuerName = new X500Name(x509.getIssuerDN().getName());
//		java.math.BigInteger serial = x509.getSerialNumber();
//		AlgorithmId digestAlgorithmId = AlgorithmId.get(digestAlgorithm);
//		AlgorithmId digestEncryptionAlgorithmId = new AlgorithmId(
//				AlgorithmId.RSAEncryption_oid);
//
//		System.out.println(issuerName);
//		System.out.println(serial);
//		System.out.println(digestAlgorithmId);
//		System.out.println(digestEncryptionAlgorithmId);
//
//		// 签名信息
//		// SignerInfo si = new SignerInfo(issuerName, serial, digestAlgorithmId,
//		// null, digestEncryptionAlgorithmId, signedAttributes, null);
//		
////		SignerInfo si=new SignerInfo(x509);
//
//
//		ByteArrayOutputStream baout = new ByteArrayOutputStream();
////		si.encode(baout);
//		System.out.println("----------------------------");
////		System.out.println(si);
//		// Base64编码
//		return Base64.encode(baout.toByteArray());
//	}
//
//	public byte[] signByPrivateKey(byte[] data) throws Exception {
//		// 获得证书
//		X509Certificate x509Certificate = certificates[0];
//		// 获取私钥
//		// KeyStore ks = getKeyStore(keyStorePath, password);
//		// 取得私钥
//		// PrivateKey privateKey = this.privateKey;
//
//		String algName = x509Certificate.getSigAlgName();
//		System.out.println(algName);
//		System.out.println(privateKey.getAlgorithm());
//
//		// 构建签名
//		Signature signature = Signature.getInstance(algName);
//		signature.initSign(privateKey);
//		signature.update(data);
//		return signature.sign();
//	}
//
//	/**
//	 * 验证签名(无CRL)
//	 * 
//	 * @param signature
//	 *            签名签名结果
//	 * @param data
//	 *            被签名数据
//	 * @param dn
//	 *            签名证书dn, 如果为空则不做匹配验证
//	 * @throws IOException
//	 * @throws NoSuchAlgorithmException
//	 * @throws SignatureException
//	 * @throws InvalidKeyException
//	 * @throws CertificateException
//	 * @throws NoSuchProviderException
//	 */
//	// public void verify(String signature, byte[] data, String dn)
//	// throws IOException, NoSuchAlgorithmException, SignatureException,
//	// InvalidKeyException, CertificateException, NoSuchProviderException {
//	// if (mode != VERIFIER)
//	// throw new IllegalStateException(
//	// "call a PKCS7Tool instance not for verify.");
//	//
//	// PKCS7 p7 = new PKCS7(BASE64.dencrypt(signature).getBytes());
//	//
//	// // 验证签名本身、证书用法、证书扩展
//	// SignerInfo[] sis = p7.verify(data);
//	//
//	// // check the results of the verification
//	// if (sis == null)
//	// throw new SignatureException(
//	// "Signature failed verification, data has been tampered");
//	//
//	// for (int i = 0; i < sis.length; i++) {
//	// SignerInfo si = sis[i];
//	//
//	// X509Certificate cert = si.getCertificate(p7);
//	// // 证书是否过期验证，如果不用系统日期可用cert.checkValidity(date);
//	// cert.checkValidity();
//	// // 验证证书签名
//	// cert.verify(rootCertificate.getPublicKey());
//	// // 验证dn
//	// if (i == 0 && dn != null) {
//	// X500Principal name = cert.getSubjectX500Principal();
//	// if (!dn.equals(name.getName(X500Principal.RFC1779))
//	// && !new X500Principal(dn).equals(name))
//	// throw new SignatureException("Signer dn '"
//	// + name.getName(X500Principal.RFC1779)
//	// + "' does not matchs '" + dn + "'");
//	// }
//	// }
//	// }
//	//
//	/**
//	 * 匹配私钥用法
//	 * 
//	 * @param keyUsage
//	 * @param usage
//	 * @return
//	 */
//	private static boolean matchUsage(boolean[] keyUsage, int usage) {
//		if (usage == 0 || keyUsage == null)
//			return true;
//		for (int i = 0; i < Math.min(keyUsage.length, 32); i++) {
//			if ((usage & (1 << i)) != 0 && !keyUsage[i])
//				return false;
//		}
//		return true;
//	}
//
//	/**
//	 * @return 返回 digestAlgorithm。
//	 */
//	public final String getDigestAlgorithm() {
//		return digestAlgorithm;
//	}
//
//	/**
//	 * @param digestAlgorithm
//	 *            要设置的 digestAlgorithm。
//	 */
//	public final void setDigestAlgorithm(String digestAlgorithm) {
//		this.digestAlgorithm = digestAlgorithm;
//	}
//
//	/**
//	 * @return 返回 signingAlgorithm。
//	 */
//	public final String getSigningAlgorithm() {
//		return signingAlgorithm;
//	}
//
//	/**
//	 * @param signingAlgorithm
//	 *            要设置的 signingAlgorithm。
//	 */
//	public final void setSigningAlgorithm(String signingAlgorithm) {
//		this.signingAlgorithm = signingAlgorithm;
//	}
//}
