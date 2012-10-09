package com.fordays.fdpay.bank.ssl.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Security;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSL 工具类
 * 
 * @author YAN RUI
 */
public class SSLUtil {

	/**
	 * 为HttpsURLConnection配置SSLSocketFactory
	 * 
	 * @param HttpsURLConnection
	 * 
	 * @param SSLSocketFactory
	 */
	public static void configSSLSocketFactory(HttpsURLConnection conn,
			SSLSocketFactory factory) throws Exception {
		conn.setSSLSocketFactory(factory);
	}

	/**
	 * 配置SSLSocketFactory,单独指定信任证书管理者
	 * 
	 * @param HttpsURLConnection
	 * 
	 * @param trustKeyStorePath
	 *            信任库路径
	 * @param trustpass
	 *            信任库密码
	 * @param algorithm
	 *            算法
	 * @param provider
	 *            提供者
	 */
	public static void configSSLSocketFactory_TrustManager(
			HttpsURLConnection conn, String trustStorePath, String trustpass,
			String algorithm, String provider) throws Exception {
		SSLSocketFactory sslFactory = getSSLSocketFactory(trustStorePath,
				trustpass, algorithm, provider);
		conn.setSSLSocketFactory(sslFactory);
	}

	/**
	 * 配置SSLSocketFactory，包含私有密钥和信任证书
	 * 
	 * @param HttpsURLConnection
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param keypass
	 *            密钥密码 
	 * @param trustKeyStorePath
	 *            信任库路径
	 * @param trustpass
	 *            信任库密码
	  * @param keyAlgorithm
	 *            密钥管理器算法
	 * @param trustAlgorithm
	 *            信任管理器算法          
	 */
	public static void configSSLSocketFactory(HttpsURLConnection conn,
			String keyStorePath, String keypass, String trustStorePath,
			String trustpass, String keyAlgorithm, String trustAlgorithm)
			throws Exception {
		SSLSocketFactory sslfactory = getSSLSocketFactory(keyStorePath,
				keypass, trustStorePath, trustpass, keyAlgorithm,
				trustAlgorithm);

		// System.out.println("configSSLSocketFactory keyalgorithm:"
		// + keyAlgorithm + ",trustAlgorithm:" + trustAlgorithm);

		conn.setSSLSocketFactory(sslfactory);
	}

	/**
	 * 初始化自定义信任管理器
	 * 
	 * @param trustKeyStorePath
	 *            信任库路径
	 * @param trustpass
	 *            信任库密码
	 * @param algorithm
	 *            创建信任管理工厂算法
	 * @param provider
	 *            提供者
	 */

	public static SSLSocketFactory getSSLSocketFactory(String trustStorePath,
			String trustpass, String trustAlgorithm, String provider) {
		SSLSocketFactory sslFactory = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager(trustStorePath,
					trustpass, trustAlgorithm, provider) };

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			sslFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslFactory;
	}

	/**
	 * @param keyStorePath
	 *            密钥库路径
	 * @param keypass
	 *            密钥密码
	 * @param trustKeyStorePath
	 *            信任库路径
	 * @param trustpass
	 *            信任库密码
	 * @param keyAlgorithm
	 *            密钥管理器算法
	 * @param trustAlgorithm
	 *            信任管理器算法
	 */

	public static SSLSocketFactory getSSLSocketFactory(String keyStorePath,
			String keypass, String trustStorePath, String trustpass,
			String keyAlgorithm, String trustAlgorithm) {
		SSLSocketFactory sslfactory = null;
		try {
			// 初始化密钥库
			KeyStore keystore = getKeyStore("JKS", keyStorePath, keypass);
			KeyManagerFactory keyManagerFactory = initKeyManagerFactory(
					keyAlgorithm, keystore, keypass);

			// 初始化信任库
			KeyStore truststore = getKeyStore("JKS", trustStorePath, trustpass);
			TrustManagerFactory trustManagerFactory = initTrustManagerFactory(
					trustAlgorithm, truststore);

			// 初始化SSL上下文
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory
					.getTrustManagers(), null);
			sslfactory = ctx.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslfactory;
	}

	/**
	 * 获得KeyStore
	 * 
	 * @param type
	 * @param keyStorePath
	 * @param password
	 * @return
	 */
	public static KeyStore getKeyStore(String type, String storeFile,
			String password) {
		KeyStore ks = null;
		try {
			FileInputStream in = new FileInputStream(storeFile);
			ks = KeyStore.getInstance(type);
			ks.load(in, password.toCharArray());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ks;
	}

	/**
	 * 初始化密钥管理工厂
	 */
	public static KeyManagerFactory initKeyManagerFactory(String algorithm,
			KeyStore keystore, String password) {
		KeyManagerFactory kmf = null;
		try {
			kmf = KeyManagerFactory.getInstance(algorithm);
			kmf.init(keystore, password.toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kmf;
	}

	/**
	 * 初始化信任管理工厂
	 */
	public static TrustManagerFactory initTrustManagerFactory(String algorithm,
			KeyStore truststore) {
		TrustManagerFactory tmf = null;
		try {
			tmf = TrustManagerFactory.getInstance(algorithm);
			tmf.init(truststore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmf;
	}

	public KeyManager[] getKeyManager(KeyManagerFactory keyfactory) {
		KeyManager[] keyManager = keyfactory.getKeyManagers();
		return keyManager;
	}

	public TrustManager[] getTrustManager(TrustManagerFactory trustfactory) {
		TrustManager[] trustManager = trustfactory.getTrustManagers();
		return trustManager;
	}

	/**
	 * 交通银行B2C支付接口，在服务器环境进行连接之前必须加此方法，否则报no trusted certificate found
	 * 
	 * 原理不详
	 */
	public static void trustAllHttpsCertificates() {
		// Create a trust manager that does not validate certificate chains:
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];

		javax.net.ssl.TrustManager tm = new MyTrustManager();
		trustAllCerts[0] = tm;

		javax.net.ssl.SSLContext context;
		try {
			context = javax.net.ssl.SSLContext.getInstance("SSL");
			context.init(null, trustAllCerts, null);

			// System.out.println(context.getProtocol());
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context
					.getSocketFactory());
			// System.out.println(HttpsURLConnection.getDefaultSSLSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName
						+ " vs. " + session.getPeerHost());
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	/**
	 * 在系统属性中指定信任库
	 * 
	 * @param trustStorePath
	 *            信任库路径
	 * @param keyStoreType
	 *            信任库类型(JKS)
	 */
	public static void setSystemTrustStore(String trustStorePath,
			String keyStoreType) {
		System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
	}

	/**
	 * 在系统属性中指定私有密钥库
	 * 
	 * @param keyStorePath
	 *            私钥库路径
	 * @param keypass
	 *            私钥密码
	 */
	public static void setSystemKeyStore(String keyStorePath, String keypass) {
		System.setProperty("javax.net.ssl.keyStore", keyStorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", keypass);
	}

	/**
	 * 在系统属性中指定JSSE协议处理程序（protocol handler） JVM----SUN
	 */
	public static void setSystemProtocolHandler_SUN() {
		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		// System.setProperty("java.protocol.handler.pkgs",
		// "com.sun.net.ssl.internal.www.protocol");

	}

	/**
	 * 在系统属性中指定JSSE协议处理程序（protocol handler） JVM----IBM
	 */
	public static void setSystemProtocolHandler_IBM() {
		// IBM JSSE 实现所使用的协议处理程序不同于SUN JSSE。
		Security.addProvider(new com.ibm.jsse.IBMJSSEProvider());
		System.setProperty("java.protocol.handler.pkgs",
				"com.ibm.net.ssl.internal.www.protocol");
	}
}
