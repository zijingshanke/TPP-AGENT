package com.fordays.fdpay.bank.ssl.factory;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class ManagerFactoryImp implements ManagerFactory {

	public KeyStore getKeyStore(String type, String storeFile, String password) {
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

	public KeyManagerFactory getKeyManagerFactory(String algorithm,
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

	public KeyManager[] getKeyManager(String algorithm, KeyStore keystore,
			String password) {
		KeyManager[] keyManager = getKeyManagerFactory(algorithm, keystore,
				password).getKeyManagers();
		return keyManager;
	}

	public KeyManager[] getKeyManager(String algorithm, String keyStoreType,
			String storeFile, String password) {
		KeyStore keystore = getKeyStore(keyStoreType, storeFile, password);

		KeyManagerFactory kmf = getKeyManagerFactory(algorithm, keystore,
				password);
		
		KeyManager[] keyManager = kmf.getKeyManagers();
		return keyManager;
	}

	public TrustManagerFactory getTrustManagerFactory(String algorithm,
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

	public TrustManager[] getTrustManager(String algorithm, KeyStore truststore) {
		TrustManager[] trustManager = getTrustManager(algorithm, truststore);
		return trustManager;
	}

	public TrustManager[] getTrustManager(String algorithm,
			String keyStoreType, String trustFile, String password) {
		KeyStore truststore = getKeyStore(keyStoreType, trustFile, password);
		TrustManager[] trustManager = getTrustManager(algorithm, truststore);
		return trustManager;
	}
}