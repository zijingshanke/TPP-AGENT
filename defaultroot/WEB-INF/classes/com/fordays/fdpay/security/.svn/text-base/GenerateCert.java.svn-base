package com.fordays.fdpay.security;

import java.math.BigInteger;

import com.fordays.fdpay.security.ssl.CertReq;
import com.fordays.fdpay.security.ssl.Constant;
import com.fordays.fdpay.security.ssl.GenRootCert;
import com.fordays.fdpay.security.ssl.SignCert;

public class GenerateCert {
	public static void main(String[] args){
		try {
			//generate keystore and root cert
			GenRootCert.generateRootCert(
					Constant.PATH,
					Constant.KEYSTORE,
					Constant.STOREPASS,
					"CN=www.qmpay.com  珠海市钱门网络科技有限公司 Root CA,O=FDPAY,OU=CS,L=ZhuHai,ST=GuangDong,C=CN",
					Constant.ROOT_CA_ALIAS, 360);
			//generate administrator cert
			CertReq.generateCert(
							Constant.PATH,
							Constant.KEYSTORE,
							Constant.STOREPASS,
							"CN=www.qmpay.com  珠海市钱门网络科技有限公司 Consumer CA,O=FDPAY,OU=CS,L=ZhuHai,ST=GuangDong,C=CN",
							"sign_ca","password", 360);
			//sign the administrator cert
			BigInteger sn=BigInteger.valueOf(1l);
			SignCert sign = new SignCert();
			sign.setKeystore(Constant.KEYSTORE);
			sign.setStorepass(Constant.STOREPASS);
			sign.setPath(Constant.PATH);
			System.out.println(Constant.PATH);
			sign.setSignAlias(Constant.ROOT_CA_ALIAS);
			sign.setSignKeypass(Constant.ROOT_CA_KEYPASS);
			sign.setUserAlias("sign_ca");
			sign.setUserKeypass("password");
			sign.signCert(Constant.SIGN_CA_ALIAS,Constant.SIGN_CA_KEYPASS, Constant.DAYS, sn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
