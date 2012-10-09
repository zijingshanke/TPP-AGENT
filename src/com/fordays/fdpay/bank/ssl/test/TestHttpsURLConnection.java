package com.fordays.fdpay.bank.ssl.test;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.protocol.Protocol;
import com.fordays.fdpay.bank.StreamUtil;
import com.fordays.fdpay.bank.ssl.util.MyKeyManager;
import com.fordays.fdpay.bank.ssl.util.MyX509TrustManager;
import com.fordays.fdpay.bank.ssl.util.SSLUtil;
import com.neza.tool.CmdUtil;
import com.neza.tool.MySSLSocketFactory;
import com.neza.tool.URLUtil;

/**
 * 访问HTTPS站点方法示例
 */
public class TestHttpsURLConnection {
	public static void main(String[] args) {
		String keyStorePath = "D:\\GLKeyStore1120";
		String keypass = "111111";
		String trustStorePath = "D:\\QMTrust";
		String trustpass = "changeit";

		String keyAlgorithm = "IbmX509";
		String trustAlgorithm = "IbmPKIX";
		String provider = "IBMJSSE";// 必须大写

		keyAlgorithm = "SunX509";
		trustAlgorithm = "SunX509";// 或者SunPKIX
		provider = "SunJSSE";

		String url = "https://www.sun.com";
		// url = "https://www.qmpay.com/login.jsp";

		keyStorePath = "D:\\276628Keystore2";
		keypass = "1111111";
		url = "https://qm.qmpay.com/security/certificate.do?thisAction=getRootCertInfo&test=test&sign=1ab309ba55a340368eb00a88478b447f";
		url = "https://qm.qmpay.com/bank/testQueryOrder_BCM6.jsp?orders=C2009112800001&version=B2C";
		url="https://www.qmpay.com/cooperate/gateway.do?_input_charset=UTF-8&agent_email=fdays@126.com&partner=2088001636104894&service=get_agent_balance&sign=2e1399fd6d38ee6edada91c6712a6877&sign_type=MD5";
		
		 SSLUtil.trustAllHttpsCertificates();
		 testResponseBodyAsPost_Https_NotVerityCA(url);

		// testMySSLSocketFactory(keyStorePath, keypass, trustStorePath,
		// trustpass, keyAlgorithm, trustAlgorithm, provider);

//		testSSLSocketFactory(url, keyStorePath, keypass, trustStorePath,
//				trustpass, keyAlgorithm, trustAlgorithm);

	}

	public static void testCmdUtil() {
		// CmdUtil.exec("cmd /c dir c: ");
		CmdUtil
				.exec("cmd   /c   keytool -list -v -keystore D:\\cacerts /c changeit ");

	}

	/**
	 * 使用SSLUtil.configSSLSocketFactory(.,keystore,..truststore,..); 访问Https站点示例
	 */
	public static void testSSLSocketFactory(String srcUrl, String keyStorePath,
			String keypass, String trustStorePath, String trustpass,
			String keyAlgorithm, String trustAlgorithm) {
		try {
			URL url = new URL(srcUrl);
			System.out.println("---url---:" + url);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

			System.out.println("--------HttpsURLConnection---1---");
			System.out.println(conn);

			SSLUtil.configSSLSocketFactory(conn, keyStorePath, keypass,
					trustStorePath, trustpass, keyAlgorithm, trustAlgorithm);

			System.out.println("--------HttpsURLConnection---2---");
			System.out.println(conn);

			String result = StreamUtil.getStringFromHttpConnectionStream(conn);
			System.out.println("result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造com.neza.tool.MySSLSocketFactory;
	 * 使用URLUtil.getResponseBodyAsPost(),即org.apache.commons.httpclient.HttpClient;
	 * 访问Https站点示例 含私钥和信任证书
	 */
	public static void testMySSLSocketFactory(String keyStorePath,
			String keypass, String trustStorePath, String trustpass,
			String keyAlgorithm, String trustAlgorithm, String provider) {
		String bankHost = "qm.qmpay.com";
		int port = 443;
		String serverPath = "/bank/testQueryOrder_BCM6.jsp";
		// serverPath = "/login.jsp";
		NameValuePair[] nvp = new NameValuePair[2];
		nvp[0] = new NameValuePair("orders", "C20091105000135");
		nvp[1] = new NameValuePair("version", "B2C");

		try {
			TrustManager[] tm = MyX509TrustManager.getTrustManager(
					trustStorePath, trustpass, trustAlgorithm, provider);
			KeyManager[] km = MyKeyManager.getKeyManager(keyStorePath, keypass,
					keyAlgorithm, provider);

			MySSLSocketFactory factory = new MySSLSocketFactory(km, tm);
			Protocol myhttps = new Protocol("https", factory, 443);
			Protocol.registerProtocol("https", myhttps);

			String resultEncode = URLUtil.getResponseBodyAsPost(bankHost, port,
					serverPath, nvp, myhttps);

			System.out.println("resultEncode:" + resultEncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 访问须信任服务器证书Https站点示例
	 */
	public static void testResponseBodyAsPost_Https_TrustManager(String url,
			String trustStorePath, String trustpass, String algorithm,
			String provider) {
		try {
			URL myURL = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection) myURL
					.openConnection();

			// 指定TrustManager
			SSLUtil.configSSLSocketFactory_TrustManager(httpsConn,
					trustStorePath, trustpass, algorithm, provider);

			System.out.println(StreamUtil
					.getStringFromHttpConnectionStream(httpsConn));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接访问不验证任何证书Https站点示例
	 */
	public static void testResponseBodyAsPost_Https_NotVerityCA(String url) {
		try {
			URL myURL = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection) myURL
					.openConnection();

			String result = StreamUtil
					.getStringFromHttpConnectionStream(httpsConn);

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
