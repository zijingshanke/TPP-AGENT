package com.fordays.fdpay.bank.icbc.test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import cn.com.infosec.icbc.ReturnValue;
import cn.com.infosec.util.Base64;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.icbc.IcbcOrder;
import com.fordays.fdpay.bank.icbc.IcbcOrderResult;

public class TestICBC {
	public static void main(String[] args) {
		// getMerVAR();
		// IcbcB2CcmdToBank bank = new IcbcB2CcmdToBank();
		// bank.setAmount("1000");
		// bank.setMerHint("aaaa你好");
		// System.out.println(bank.getTranData());

		// replace();
		// compareCert();
		// getOrderResult();

		 IcbcOrderTest order = new IcbcOrderTest("qmpay","B2C");
		 order.setOrderNum("C20100117000010");
		 order.setTranDate("20100117");
		 IcbcOrderResult result = order.PostQueryCmd();

		// base64Decode();
		// readCert();
		// URLUtil.getResponseBodyAsPost(strURL, nvp)

		// testB2BVerifySign();

//		testSignVeritySelf();
	}

	public static void testSignVeritySelf() {
		String PriKeyUrl = "E:\\icbcb2b\\qmpayb2c20090911.key";

		String strSrc = "abcdefg12345";
		byte[] byteSrc = strSrc.getBytes();
		int byteSrcLen = byteSrc.length;
		byte[] EncSign = null;

		byte[] priKey = BankUtil.getByteFromFile(PriKeyUrl);// 读取私钥
		char[] keyPass = "12345678".toCharArray();// 私钥保护密码

		try {
			// 私钥对明文签名
			byte[] sign = ReturnValue
					.sign(byteSrc, byteSrcLen, priKey, keyPass);
			if (sign == null) {
				System.out.println("私钥签名失败");
			} else {
				System.out.println("私钥签名成功");

				EncSign = ReturnValue.base64enc(sign);// 签名BASE64编码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String strSign = new String(EncSign);
		System.out.println("签名数据:" + strSign);		
		//-------------------------------
		byte[] EncSign2 = strSign.getBytes();
		byte[] DecSign = ReturnValue.base64dec(EncSign2);// 签名解码

		byte[] EncCert = BankUtil.readFileAsByteArray("E:\\icbcb2b\\qmpayb2c20090911.crt").toByteArray();
		byte[] DecCert = ReturnValue.base64dec(EncCert);// 证书解码
		
		int a = 1;
		try {			
			a = ReturnValue.verifySign(byteSrc, byteSrcLen, DecCert, DecSign);// 验证签名
			System.out.println(a);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

	}

	public static void testB2BVerifySign() {
		String strSrc = "APIName=B2B&APIVersion=001.001.001.001&Shop_code=2002EC13336181&MerchantURL=http://219.131.194.194/fdpay-client/bank/icbcb2bResult.do&Serial_no=&PayStatusZHCN=&TranErrorCode=40947&TranErrorMsg=Execute Procedure[pckg_b2b_onlinepay.proc_b2bqueryeshopinfo] Error = 商户代码不存在&ContractNo=C20090930000002&ContractAmt=100&Account_cur=001&JoinFlag=2&ShopJoinFlag=&CustJoinFlag=&CustJoinNumber=&SendType=0&TranTime=20090930094143&NotifyTime=20091211094122&Shop_acc_num=2002022509100027149&PayeeAcct=2002022509100027149&PayeeName=";
		String NotifySign = "SbVAvWXYHMoGJc9Q2pfH/pFAX942gGHjppnbneRCfncVgR+U6frLVCs14SisG3uhGJPe5hLineKCiH4ve5zAwwl5Auwhx/4gJd9qF97TwglEhPGMpvEoYOEUCBwUU6d9RqnnfwSWHTPfK8s7S38vfGuPXLia4H0o8cC1ige/3gs=";

		byte[] byteSrc = null;
		byteSrc = strSrc.getBytes();
		int byteSrcLength = byteSrc.length;

		byte[] EncSign = NotifySign.getBytes();
		byte[] DecSign = ReturnValue.base64dec(EncSign);// 签名解码

		// byte[] EncCert = BankUtil.getByteFromFile("E:\\icbcb2b\\ca.cer");
		// byte[] EncCert =
		// BankUtil.readFileAsByteArray("E:\\icbcb2b\\ca.cer").toByteArray();
		// byte[] EncCert =
		// BankUtil.getByteFromFile("E:\\icbcb2b\\cabase64.cer");
		byte[] EncCert = BankUtil.readFileAsByteArray(
				"E:\\icbcb2b\\cabase64.cer").toByteArray();

		// byte[] EncCert =
		// BankUtil.readFileAsByteArray("E:\\icbcb2b\\ebb2cpublic.crt").toByteArray();
		// byte[] EncCert =
		// BankUtil.readFileAsByteArray("E:\\icbcb2b\\qmpaycs2_b2b_test_20090927.crt").toByteArray();
		// byte[] EncCert =
		// BankUtil.readFileAsByteArray("E:\\icbcb2b\\coporbank3Cer.cer").toByteArray();
		// byte[] EncCert =
		// BankUtil.getByteFromFile("E:\\icbcb2b\\ceshi007Cer.cer");
		// byte[] EncCert =
		// BankUtil.readFileAsByteArray("E:\\icbcb2b\\ceshi007Cer.cer").toByteArray();
		// byte[] EncCert =
		// BankUtil.getByteFromFile("E:\\icbcb2b\\admin-b2c-test.crt");
		// byte[] EncCert =
		// BankUtil.readFileAsByteArray("E:\\icbcb2b\\admin-b2c-test.crt").toByteArray();

		System.out.println("---1------");

		// byte[] EncCert =
		// "MIICODCCAaGgAwIBAgIKG5LKECVWAAAAATANBgkqhkiG9w0BAQUFADAtMREwDwYDVQQDEwh0ZXN0aWNiYzEYMBYGA1UEChMPdGVzdGljYmMuY29tLmNuMB4XDTAzMDMxNTA3MjI0MVoXDTIzMDMxNTA3MjI0MVowLTERMA8GA1UEAxMIdGVzdGljYmMxGDAWBgNVBAoTD3Rlc3RpY2JjLmNvbS5jbjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA//
		// 5u2VP7WnqtaBCnv5bOHXXoWmq68Bk0P4Q44VvsjoAMyzZwhAo33PVgPYwYSYT5dAIW/liqm0DOS3mmSbr18ljxapG8W8FabWvYBsi3EfJZmKz3SdXBHYZQt/t5Fopp4G/DtsMME2Soqe9k5XWYTSoJk+ev4HPAnxEInYZA3dUCAwEAAaNfMF0wSgYDVR0fBEMwQTA/oD2gO6Q5MDcxDTALBgNVBAMTBGNybDExDDAKBgNVBAsTA2NybDEYMBYGA1UEChMPdGVzdGljYmMuY29tLmNuMA8GA1UdYwQIAwYAESIzRFUwDQYJKoZIhvcNAQEFBQADgYEArpyxkn0k4kE8r1jKwy5XFwL79RYoq/x9Pa8sQwvUJitQ+ubHhLJEPv5wsW2w/M2njLYKXrqr/AaUdgH2QkE8tnwllVECb57FsYF5TmYpOTvKzsa0dOK22iYcBu7S9WdE/5rQ+ZjafeqF1LF3C6l+89bK3lbFUTNlV5LLHcPGGeg="
		// .getBytes();
		System.out.println("---2------");
		byte[] DecCert = ReturnValue.base64dec(EncCert);// 证书解码
		int a = 1;
		System.out.println("---3------");
		try {
			System.out.println("---4------");
			a = ReturnValue
					.verifySign(byteSrc, byteSrcLength, DecCert, DecSign);// 验证签名
			System.out.println("---5------");
			System.out.println(a);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}

	public static void readCert() {
		try {
			String aa = new String(Base64.encode(BankUtil
					.getByteFromFile("E:\\ebpublic0909.key")));
			System.out.println("----------------------");
			String bb = new String(Base64.encode(BankUtil
					.getByteFromFile("E:\\ebb2cpublic.crt")));
			String cc = new String(Base64.encode(BankUtil
					.getByteFromFile("E:\\ca.cer")));
			String dd = new String(Base64.encode(BankUtil
					.getByteFromFile("E:\\cabase64.cer.txt")));
			String ee = new String(BankUtil
					.getByteFromFile("E:\\2cabase64.cer.txt"));

			System.out.println("aa:-----------\n" + aa);
			System.out.println("bb:----------\n" + bb);
			System.out.println("cc:----------\n" + cc);
			System.out.println("dd:----------\n" + dd);
			System.out.println("ee:----------\n" + ee);
			System.out.println(cc.equals(ee));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getSysCoding() {
		String encoding = System.getProperty("file.encoding");
		System.out.println(encoding);
	}

	public static void testIndex() {
		String bankuri = "/bank/icbcb2bResult.do";
		String gateuri = "/cooperate/gateway.do";
		int intIndex1 = gateuri.indexOf(gateuri);
		int intIndex2 = bankuri.indexOf(gateuri);
		System.out.println(intIndex1 + "---" + intIndex2);
	}

	public static void compareCert() {
		String baseUrl = "E:\\projectclient\\defaultroot\\WEB-INF\\bankkey\\icbc";
		String pfxUrl = baseUrl + "\\fdays0513.pfx";
		String crtUrl_1 = baseUrl + "\\fdays0513.crt";
		String cerUrl_2 = baseUrl + "\\fdays0513_2.cer";
		String cerUrl_6 = baseUrl + "\\fdays0513_6.cer";

		String i2Url = baseUrl + "\\i2.cer";
		String i6Url = baseUrl + "\\i6.cer";
		try {
			byte[] bytePfx = BankUtil.getByteFromFile(pfxUrl);
			byte[] byteCrt = BankUtil.getByteFromFile(crtUrl_1);
			byte[] byteCer2 = BankUtil.getByteFromFile(cerUrl_2);
			byte[] byteCer6 = BankUtil.getByteFromFile(cerUrl_6);

			byte[] byteI2 = BankUtil.getByteFromFile(i2Url);
			byte[] byteI6 = BankUtil.getByteFromFile(i6Url);
			System.out.println("byte size " + "pfx=" + bytePfx.length + " crt="
					+ byteCrt.length + " cer2=" + byteCer2.length + " cer6="
					+ byteCer6.length + " i2=" + byteI2.length + " i6="
					+ byteI6.length);

			// String pfx = Base64.encode(bytePfx);
			String crt = Base64.encode(byteCrt);
			String cer2 = Base64.encode(byteCer2);
			String cer6 = Base64.encode(byteCer6);
			// String i2 = Base64.encode(byteI2);
			// String i6 = Base64.encode(byteI6);

			System.out.println("crt equal cer2=" + crt.equals(cer2));
			System.out.println("crt equesl cer6=" + crt.equals(cer6));
			System.out.println("cer2 equal cer6=" + cer2.equals(cer6));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void replace() {
		String src = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?><B2CReq><interfaceName>ICBC_PERBANK_B2C</interfaceName><interfaceVersion>1.0.0.3</interfaceVersion><orderInfo><orderDate>20090606221103</orderDate><orderid>C20090521000001</orderid><amount>100</amount><curType>001</curType><merID>2002EC20000484</merID><merAcct>2002022519100035059</merAcct></orderInfo><custom><verifyJoinFlag>0</verifyJoinFlag><Language>ZH_CN</Language></custom><message><goodsID>0001</goodsID><goodsName>充值</goodsName><goodsNum>1</goodsNum><carriageAmt>0</carriageAmt><merHint>恭喜发财</merHint><remark1></remark1><remark2></remark2><merURL>http://www.qmpay.com.cn/fdpay-client/bank/icbcb2cResult.do</merURL><merVAR>0,0:0</merVAR></message></B2CReq>";
		src = src.replaceAll("GBK", "utf-8");
		System.out.println(src);
	}

	public void TestXml() {
		StringBuffer str = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		str.append("<ICBC><B2C>");
		str.append("<SubmitOrder>");
		str.append("<merID>HDERI009</merID>");
		str.append("<orderDate>20080808200101</orderDate>");
		str.append("<orderID>ABC123</orderID>");
		str.append("<merAcct>0200029109000030106</merAcct>");
		str.append("<cutomerTel>1391199229</cutomerTel>");
		str.append("<amount>10000</amount>");
		str.append("<curType>001</curType>");
		str.append("</SubmitOrder>");
		str.append("<Ret>");
		str.append("<code>0</code><msg>商户订单被接收</msg>");
		str.append("<bankTime>20080808200130</bankTime>");
		str.append("<bankSignMsg>TTUYTOYYIUKJ2322GFd……</bankSignMsg>");
		str.append("</Ret>");
		str.append("</B2C></ICBC>");

		// -----------------------------------
		// XmlUtil xmlctl = new XmlUtil();
		// Document document = xmlctl.readResult(str);

	}
}
