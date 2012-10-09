package com.fordays.fdpay.security.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

import com.ibm.security.x509.X509CertImpl;


public class ClientCertInfo {
	private KeyStore ks=null;
	private KeyStore loadServerKeyStore(String path,String keystore,String storepass)
			throws Exception {
		KeyStore ks = null;
		if (keystore == null || "".equals(keystore)
				|| storepass == null || "".equals(storepass)) {
			return null;
		}
		FileInputStream in = new FileInputStream(getPath(path,keystore));
		ks = KeyStore.getInstance("JKS");
		ks.load(in, storepass.toCharArray());
		in.close();
		return ks;
	}
	public X509CertImpl getCertInfo(String path,String keystore,String storepass,String alias) 
		throws Exception{
		if(ks==null){
			ks=loadServerKeyStore(path,keystore,storepass);
		}
		Certificate cert=ks.getCertificate(alias);
		return new X509CertImpl(cert.getEncoded());
		
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
		try {
			RemoveCert cert = new RemoveCert();
			cert.remove(Constant.PATH,Constant.KEYSTORE,Constant.STOREPASS,"client_4");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
