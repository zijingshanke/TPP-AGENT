package com.fordays.fdpay.security.ssl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
public class RemoveCert {
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
	public void remove(String path,String keystore,String storepass,String alias) 
		throws Exception{
		if(ks==null){
			ks=loadServerKeyStore(path,keystore,storepass);
		}
		ks.deleteEntry(alias);
		
		// 用新密钥替代原来的没有签名的证书的密码
		FileOutputStream out = new FileOutputStream(getPath(path,keystore));
		ks.store(out, storepass.toCharArray());
		out.close();
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
