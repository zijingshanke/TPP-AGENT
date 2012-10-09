package com.fordays.fdpay.security.ssl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

public class KeyStoreConvert {

	public static void main(String[] args) {

		//        convert(args[0],args[1],args[2],args[3],args[4],args[5],   
		//                args[6],args[7],args[8],args[9],args[10]);   
		//
		try {
			convert("JKS", 
					"D:\\project\\fdpay-client\\defaultroot\\WEB-INF\\cert\\.keystore", 
					"password",
					"client_4",
					"password", 
					"client_4", 
					
					"PKCS12", 
					"D:\\project\\fdpay-client\\defaultroot\\WEB-INF\\cert\\client.p12",
					"password", 
					"client_4", 
					"password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//convert("PKCS12","test.p12","abc123","kp2","abc123","kp2",   
		//      "JKS","test1.jks","abc123","kp2","abc123");   
	}

	public static void convert( String storeType1, 
								String stroe1FileName,
								String store1Passwd, 
								String store1KeyAlias,
								String store1KeyPasswd,
								String store1CertChainAlias,
								//********after change parameters
								String storeType2, 
								String store2FileName, 
								String store2Passwd,
								String store2KeyAlias, 
								String store2KeyPasswd){
		try {
			
			
			FileInputStream in = new FileInputStream(stroe1FileName);

			KeyStore ks = KeyStore.getInstance(storeType1);

			ks.load(in, store1Passwd.toCharArray());
			in.close();
			Key key = ks.getKey(store1KeyAlias, store1KeyPasswd.toCharArray());

			KeyFactory keyfact = java.security.KeyFactory.getInstance(key
					.getAlgorithm());
			PrivateKey priKey = keyfact
					.generatePrivate(new PKCS8EncodedKeySpec(key.getEncoded()));
			

			KeyStore ksSecond = KeyStore.getInstance(storeType2);

			ksSecond.load(null, null);
			Certificate[] certificate=ks.getCertificateChain(store1CertChainAlias);
			ksSecond.setKeyEntry(store2KeyAlias, priKey, store2KeyPasswd
					.toCharArray(),certificate);

			FileOutputStream outputStream = new FileOutputStream(store2FileName);
			ksSecond.store(outputStream, store2Passwd.toCharArray());

			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
