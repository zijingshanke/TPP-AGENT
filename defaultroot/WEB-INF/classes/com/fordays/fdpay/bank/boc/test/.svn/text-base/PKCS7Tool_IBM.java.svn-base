//package com.fordays.fdpay.bank.boc.test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.InvalidKeyException;
//import java.security.KeyStore;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.PrivateKey;
//import java.security.Signature;
//import java.security.SignatureException;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.Enumeration;
//import javax.security.auth.x500.X500Principal;
//import com.ibm.misc.BASE64Decoder;
//import cn.com.infosec.util.Base64;
//import sun.security.pkcs.ContentInfo;
//import sun.security.pkcs.PKCS7;
//import sun.security.pkcs.SignerInfo;
//import sun.security.x509.AlgorithmId;
//import sun.security.x509.X500Name;
//
///**
// * 改造PKCS7Tool.java pkcs7格式签名工具,使之适合于ibm-jdk下使用
// */
//public class PKCS7Tool_IBM {
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
//	private PKCS7Tool_IBM(int mode) {
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
//	public static PKCS7Tool_IBM getSigner(String keyStorePath,
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
//		PKCS7Tool_IBM tool = new PKCS7Tool_IBM(SIGNER);
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
//	public static PKCS7Tool_IBM getVerifier(String rootCertificatePath)
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
//		PKCS7Tool_IBM tool = new PKCS7Tool_IBM(VERIFIER);
//		tool.rootCertificate = rootCertificate;
//		return tool;
//	}
//
//	/**
//	 * 签名
//	 * 
//	 * @param data
//	 *            数据
//	 * @return signature 签名结果
//	 * @throws GeneralSecurityException
//	 * @throws IOException
//	 * @throws IllegalArgumentException
//	 */
//	public String sign(byte[] data) throws GeneralSecurityException,
//			IOException {
//		if (mode != SIGNER)
//			throw new IllegalStateException(
//					"call a PKCS7Tool instance not for signature.");
//
//		Signature signer = Signature.getInstance(signingAlgorithm);
//		signer.initSign(privateKey);
//		signer.update(data, 0, data.length);
//		byte[] signedAttributes = signer.sign();
//
//		ContentInfo contentInfo = null;
//
//		contentInfo = new ContentInfo(ContentInfo.DATA_OID, null);
//		// 根证书
//		X509Certificate x509 = certificates[certificates.length - 1];
//		// 如果jdk1.4则用以下语句
//		// sun.security.util.BigInt serial = new
//		// sun.security.util.BigInt(x509.getSerialNumber());
//		// 如果jdk1.5则用以下语句
//		java.math.BigInteger serial = x509.getSerialNumber();
//		// 签名信息
//		SignerInfo si = new SignerInfo(new X500Name(x509.getIssuerDN()
//				.getName()), // X500Name, issuerName,
//				serial, // x509.getSerialNumber(), BigInteger serial,
//				AlgorithmId.get(digestAlgorithm), // AlgorithmId,
//				// digestAlgorithmId,
//				null, // PKCS9Attributes, authenticatedAttributes,
//				new AlgorithmId(AlgorithmId.RSAEncryption_oid), // AlgorithmId,
//				// digestEncryptionAlgorithmId,
//				signedAttributes, // byte[] encryptedDigest,
//				null); // PKCS9Attributes unauthenticatedAttributes) {
//
//		SignerInfo[] signerInfos = { si };
//
//		// 构造PKCS7数据
//		AlgorithmId[] digestAlgorithmIds = { AlgorithmId.get(digestAlgorithm) };
//		PKCS7 p7 = new PKCS7(digestAlgorithmIds, contentInfo, certificates,
//				signerInfos);
//		
//		com.ibm.security.pkcs7.PKCS7 ibmP7=new com.ibm.security.pkcs7.PKCS7();
//		
//
//		ByteArrayOutputStream baout = new ByteArrayOutputStream();
//		p7.encodeSignedData(baout);
//		
////		ibmP7.
//		
//		// Base64编码		
//		return Base64.encode(baout.toByteArray());
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
//	public void verify(String signature, byte[] data, String dn)
//			throws IOException, NoSuchAlgorithmException, SignatureException,
//			InvalidKeyException, CertificateException, NoSuchProviderException {
//		if (mode != VERIFIER)
//			throw new IllegalStateException(
//					"call a PKCS7Tool instance not for verify.");
//
//		PKCS7 p7 = new PKCS7(Base64.decode(signature));
//
//		// 验证签名本身、证书用法、证书扩展
//		SignerInfo[] sis = p7.verify(data);
//
//		// check the results of the verification
//		if (sis == null)
//			throw new SignatureException(
//					"Signature failed verification, data has been tampered");
//
//		for (int i = 0; i < sis.length; i++) {
//			SignerInfo si = sis[i];
//
//			X509Certificate cert = si.getCertificate(p7);
//			// 证书是否过期验证，如果不用系统日期可用cert.checkValidity(date);
//			cert.checkValidity();
//			// 验证证书签名
//			cert.verify(rootCertificate.getPublicKey());
//			// 验证dn
//			if (i == 0 && dn != null) {
//				X500Principal name = cert.getSubjectX500Principal();
//				if (!dn.equals(name.getName(X500Principal.RFC1779))
//						&& !new X500Principal(dn).equals(name))
//					throw new SignatureException("Signer dn '"
//							+ name.getName(X500Principal.RFC1779)
//							+ "' does not matchs '" + dn + "'");
//			}
//		}
//	}
//
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
