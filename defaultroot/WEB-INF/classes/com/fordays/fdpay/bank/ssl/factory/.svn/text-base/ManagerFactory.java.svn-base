package com.fordays.fdpay.bank.ssl.factory;

import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;



public interface ManagerFactory {
	/**
	 * 加载密钥库或信任证书库
	 */
	public KeyStore getKeyStore(String type, String storeFile, String password);

	/**
	 * 实例化然后初始化 KeyManagerFactory 对象来封装底层密钥库
	 */
	public KeyManagerFactory getKeyManagerFactory(String algorithm,
			KeyStore keystore, String password);

	/**
	 * getKeyManager
	 */
	public KeyManager[] getKeyManager(String algorithm, KeyStore keystore,
			String password);

	public KeyManager[] getKeyManager(String algorithm, String keyStoreType,
			String storeFile, String password);

	/**
	 * 实例化 TrustManagerFactory 对象并且使用您的信任库将其初始化
	 */
	public TrustManagerFactory getTrustManagerFactory(String algorithm,
			KeyStore truststore);

	/**
	 * getTrustManager
	 */
	public TrustManager[] getTrustManager(String algorithm, KeyStore truststore);

	public TrustManager[] getTrustManager(String algorithm,
			String keyStoreType, String trustFile, String password);
}
