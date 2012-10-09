package com.fordays.fdpay.bank.ssl.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {
	private String trustedCert = "";
	private String passphrase = "";
	private String algorithm = "";
	private String provider = "";

	X509TrustManager userdefinedX509TrustManager;

	public MyX509TrustManager(String myTrustedCert, String trustPass,
			String myAlgorithm, String myProvider) throws Exception {

		init(myTrustedCert, trustPass, myAlgorithm, myProvider);// init

		// create a "default" JSSE X509TrustManager.
		appointTrustManager();

	}

	public void init(String myTrustedCert, String trustPass,
			String myAlgorithm, String myProvider) {
		trustedCert = myTrustedCert;
		passphrase = trustPass;
		algorithm = myAlgorithm;
		provider = myProvider;
	}

	public void appointTrustManager() throws Exception {
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(trustedCert), passphrase.toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm,
				provider);

		tmf.init(ks);
		TrustManager tms[] = tmf.getTrustManagers();

		/*
		 * Iterate over the returned trustmanagers, look for an instance of
		 * X509TrustManager. If found, use that as our "default" trust manager.
		 */
		for (int i = 0; i < tms.length; i++) {
			if (tms[i] instanceof X509TrustManager) {
				userdefinedX509TrustManager = (X509TrustManager) tms[i];
				return;
			}
		}

		/*
		 * Find some other way to initialize, or else we have to fail the
		 * constructor.
		 */
		throw new Exception("Couldn't initialize");
	}

	public static TrustManager[] getTrustManager(String mytrust, String trustpass,
			String myAlgorithm, String myProvider) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(mytrust), trustpass.toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(
					myAlgorithm,myProvider);

			tmf.init(ks);
			TrustManager tms[] = tmf.getTrustManagers();
			return tms;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Delegate to the default trust manager.
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		try {
			userdefinedX509TrustManager.checkClientTrusted(chain, authType);
		} catch (CertificateException excep) {
			// do any special handling here, or rethrow exception.
		}
	}

	/*
	 * Delegate to the default trust manager.
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		try {
			userdefinedX509TrustManager.checkServerTrusted(chain, authType);
		} catch (CertificateException excep) {
			/*
			 * Possibly pop up a dialog box asking whether to trust the cert
			 * chain.
			 */
		}
	}

	/*
	 * Merely pass this through.
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return userdefinedX509TrustManager.getAcceptedIssuers();
	}

	public String getTrustedCert() {
		return trustedCert;
	}

	public void setTrustedCert(String trustedCert) {
		this.trustedCert = trustedCert;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
