package com.fordays.fdpay.bank.icbc.test;

import java.io.FileInputStream;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.fordays.fdpay.bank.BankUtil;
import com.neza.encrypt.BASE64;
import cn.com.infosec.icbc.ReturnValue;

public class TestSign extends TestCase {
	public static void main(String[] args) {

	}

	public static void test2() {
		Method[] methods = TestSign.class.getMethods();
		int methodsLen = methods.length;
		for (int i = 0; i < methodsLen; i++) {
			Method method = methods[i];
			System.out.println("method:" + method);
		}
	}

	//
	public static void parseReturnMsg() {
		try {
			String testStr = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIiA/PjxCMkNSZXE+PGludGVyZmFjZU5hbWU+SUNCQ19QRVJCQU5LX0IyQzwvaW50ZXJmYWNlTmFtZT48aW50ZXJmYWNlVmVyc2lvbj4xLjAuMC4zPC9pbnRlcmZhY2VWZXJzaW9uPjxvcmRlckluZm8+PG9yZGVyRGF0ZT4yMDA5MDcyNTE4MjEwMzwvb3JkZXJEYXRlPjxvcmRlcmlkPkMyMDA5MDYyNTAwMDA3OTwvb3JkZXJpZD48YW1vdW50PjEwMDwvYW1vdW50PjxjdXJUeXBlPjAwMTwvY3VyVHlwZT48bWVySUQ+MjAwMkVDMjAwMDA0ODQ8L21lcklEPjxtZXJBY2N0PjIwMDIwMjI1MTkxMDAwMzUwNTk8L21lckFjY3Q+PC9vcmRlckluZm8+PGN1c3RvbT48dmVyaWZ5Sm9pbkZsYWc+MDwvdmVyaWZ5Sm9pbkZsYWc+PExhbmd1YWdlPlpIX0NOPC9MYW5ndWFnZT48L2N1c3RvbT48bWVzc2FnZT48Z29vZHNJRD4wMDAxPC9nb29kc0lEPjxnb29kc05hbWU+5YWF5YC8PC9nb29kc05hbWU+PGdvb2RzTnVtPjE8L2dvb2RzTnVtPjxjYXJyaWFnZUFtdD4wPC9jYXJyaWFnZUFtdD48bWVySGludD7mga3llpzlj5HotKI8L21lckhpbnQ+PHJlbWFyazE+PC9yZW1hcmsxPjxyZW1hcmsyPjwvcmVtYXJrMj48bWVyVVJMPmh0dHA6Ly93d3cucW1wYXkuY29tLmNuOjkwODAvZmRwYXktY2xpZW50L2JhbmsvaWNiY2IyY1Jlc3VsdC5kbzwvbWVyVVJMPjxtZXJWQVI+bnVsbDwvbWVyVkFSPjwvbWVzc2FnZT48L0IyQ1JlcT4=";

			System.out.println(new String(ReturnValue.base64dec(testStr
					.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 解码提交的数据
	public static void testTranData() {
		String tranData = "";
		tranData = BASE64.dencrypt(tranData);
		byte[] key = BankUtil.getByteFromFile("D://fdays0513.crt");
		System.out.println(new String(key));
		// ReturnValue.verifySign(srcLen, src, sign, cert)
	}

	// base64解码
	public static void testReturnValueDec() {
		// byte[] byteSrc = "aa+".getBytes();
		// byte[] byteSrc2="aa%2B".getBytes();
		// byte[] byteDec = ReturnValue.base64dec(byteSrc);
		// byte[] byteDec2 = ReturnValue.base64dec(byteSrc2);

		String enSrc = BASE64.encrypt("aa+/=%2B");
		String dnSrc = BASE64.dencrypt(enSrc);
		System.out.println("enSrc=" + enSrc);
		System.out.println("dnSrc=" + dnSrc);
	}

	// 转换URL特殊字符的方法
	public static String testEncode(String signMsg) {
		signMsg = signMsg.replace("+", "%2B");
		signMsg = signMsg.replace("/", "%2F");
		signMsg = signMsg.replace("=", "%3D");
		System.out.println(">>>>signMsg>>>>>>>>>>>");
		System.out.println(signMsg);
		return signMsg;
	}

	public static void testURLEncode(String src) {
		// System.out.println(">>>>>src=" + src);
		// System.out.println(">>>>>encoder=" + URLEncoder.encode(src));
	}

	// URL中特殊字符的处理
	public static void testCode() {
		// // 编码
		// // 解码
		// String aa = "a/b+c=";
		// String bb = URLEncoder.encode(aa);
		// System.out.println(bb);
		// System.out.println(URLDecoder.decode(bb));
	}

	public static void testVerity(String strSrc, String signMsg) {
		byte[] byteSrc = strSrc.getBytes();
		byte[] EncSign = signMsg.getBytes();
		byte[] EncCert = BankUtil.getByteFromFile("d:\\fdays0513.cer");
		byte[] DecSign = ReturnValue.base64dec(EncSign);
		byte[] DecCert = ReturnValue.base64dec(EncCert);
		int a = 0;
		try {
			a = ReturnValue.verifySign(byteSrc, byteSrc.length, DecCert,
					DecSign); // 验证签名
			System.out.println("a=" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getKey() {
		try {
			FileInputStream in1 = new FileInputStream("d:\\test03.crt.cer");
			byte[] bcert = new byte[in1.available()];
			in1.read(bcert);
			System.out.println("user.crt--" + new String(bcert));
			in1.close();
			//			

			// FileInputStream in2 = new FileInputStream("d:\\test03.key");
			// byte[] bkey = new byte[in2.available()];
			// in2.read(bkey);
			// System.out.println("key----"+new String(bkey));
			// in2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test() {
		String tranData = "this is a test";
		System.out
				.println("<font face='Arial' size='4' color='Green'>明文：</font>"
						+ tranData + "<br>");
		String password = "11111111";
		try {
			byte[] byteSrc = tranData.getBytes();
			char[] keyPass = password.toCharArray();

			FileInputStream in1 = new FileInputStream("d:\\user.crt");
			byte[] bcert = new byte[in1.available()];
			in1.read(bcert);
			in1.close();
			FileInputStream in2 = new FileInputStream("d:\\user.key");
			byte[] bkey = new byte[in2.available()];
			in2.read(bkey);
			in2.close();

			byte[] sign = ReturnValue.sign(byteSrc, byteSrc.length, bkey,
					keyPass);
			if (sign == null) {
				System.out
						.println("<font face='Arial' size='4' color='Red'>签名失败,签名返回为空。<br>请检查证书私钥和私钥保护口令是否正确。</font><br>");
			} else {
				System.out
						.println("<font face='Arial' size='4' color='Green'>签名成功</font><br>");

				byte[] EncSign = ReturnValue.base64enc(sign);
				String SignMsgBase64 = new String(EncSign).toString();
				System.out
						.println("<font face='Arial' size='4' color='Green'>签名信息BASE64编码：</font>"
								+ SignMsgBase64.substring(0, 100) + "...<br>");

				byte[] EncCert = ReturnValue.base64enc(bcert);
				String CertBase64 = new String(EncCert).toString();
				System.out
						.println("<font face='Arial' size='4' color='Green'>证书公钥BASE64编码：</font>"
								+ CertBase64.substring(0, 100) + "...<br>");

				byte[] DecSign = ReturnValue.base64dec(EncSign);
				if (DecSign != null) {
					System.out
							.println("<font face='Arial' size='4' color='Green'>签名信息BASE64解码成功</font><br>");
					byte[] DecCert = ReturnValue.base64dec(EncCert);
					if (DecCert != null) {
						System.out
								.println("<font face='Arial' size='4' color='Green'>证书公钥BASE64解码成功</font><br>");
						int a = ReturnValue.verifySign(byteSrc, byteSrc.length,
								DecCert, DecSign);
						if (a == 0)
							System.out
									.println("<font face='Arial' size='4' color='Green'>验签成功</font><br>");
						else
							System.out
									.println("<font face='Arial' size='4' color='Red'>验签失败<br>验证返回码：</font><br>"
											+ a);
					} else
						System.out
								.println("<font face='Arial' size='4' color='Red'>证书BASE64解码失败</font><br>");
				} else
					System.out
							.println("<font face='Arial' size='4' color='Red'>签名信息BASE64解码失败</font><br>");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
