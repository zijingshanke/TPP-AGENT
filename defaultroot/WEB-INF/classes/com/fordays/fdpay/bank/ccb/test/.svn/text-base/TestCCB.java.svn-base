package com.fordays.fdpay.bank.ccb.test;

import CCBSign.RSASig;

import com.neza.encrypt.MD5;
import com.neza.tool.FileUtil;

public class TestCCB {
	public static void main(String[] args) {
		 testMd5();
//		testVerifySign();
	}

	public static void testMd5() {
		
		String str = "123456";
		String md5Str = MD5.encrypt(str);
		System.out.println(md5Str);//287da680fa4657380cc73bd1ca94dddb
	}

	public static void testVerifySign() {
		//--------fdays----------------------
//		String strSrc = "POSID=100000785&BRANCHID=441000000&ORDERID=C20091101000137&PAYMENT=610.00&CURCODE=01&REMARK1=www.qmpay.com&REMARK2=&ACC_TYPE=12&SUCCESS=Y";
//		String strSign = "27e985850dc6e215021646df71d534df7ef81a05b3af06a0082e5c2afd2b4604f8a726ea204bc5a1f9e1b406f929c8d47a17d0f8039573552bd92d0ea473caeaa23cd6e634b58cf1b64dddceffea80aaca09a19b609a6989dfe4f3cb6c97c10a02ece94840f0b8f889df3f168c0f801aa6f3ab5ad7db91a14edfcd13e642e66f";
//		String keyfile = "E:\\ccbpubkey.txt";
		//-----------------------------------
		
		//--------qmpay----
		String strSrc="POSID=100001326&BRANCHID=441000000&ORDERID=C20100102000004&PAYMENT=599.00&CURCODE=01&REMARK1=www.qmpay.com&REMARK2=&ACC_TYPE=12&SUCCESS=Y";
		String strSign="33e80190dc99cbf843a47b95f48b462fac0019e9713d1516821e40f007ee7158d5c6bd17d99abc15a7a2b9c3867894ce0616e6614cc6fbb5f37b7c58c43645b0fccb5c0d27edafd704f5c99ffef1a2ae64182d21e33a184a3a9a5b70f80ece11833dc7db479183a18ee6894ae0b316652e9890efd39fb6ea71475a68154f559b";
		String keyfile="E:\\ccbpubkey-qmpay.txt";
		//-----------------
		
		boolean flag = verifySigature(strSrc, strSign, keyfile);
		System.out.println("flag=" + flag);
	}

	// RSASig验签方法
	public static boolean verifySigature(String strSrc, String strSign,
			String keyfile) {
		String strPubKey;
		try {
			strPubKey = FileUtil.read(keyfile);// 读取商户公钥文件
			RSASig rsa = new RSASig();
			if (strPubKey != null && !"".equals(strPubKey)) {
				rsa.setPublicKey(strPubKey);
				if (rsa.verifySigature(strSign, strSrc)) {
					System.out.println("Sign OK");
					return true;
				} else {
					System.out.println("Sign fail");
					return false;
				}
			} else {
				System.out.println("读取公钥为空!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
