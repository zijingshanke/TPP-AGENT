package com.fordays.fdpay.bank.ssl.util;

import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

public class MyKeyManager {

	public static KeyManager[] getKeyManager(String storeFile, String password,
			String algorithm, String provider) {
		try {
			KeyStore ks = SSLUtil.getKeyStore("JKS", storeFile, password);
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm,provider);
			kmf.init(ks, password.toCharArray());
			KeyManager[] km = kmf.getKeyManagers();
			return km;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
