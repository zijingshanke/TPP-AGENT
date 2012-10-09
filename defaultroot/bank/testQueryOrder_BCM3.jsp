<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.net.*"%>
<%@page import="java.lang.*"%>
<%@page import="com.neza.base.Constant"%>
<%@page import="com.fordays.fdpay.bank.LogUtil"%>
<%@page import="com.bocom.netpay.b2cAPI.*"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!-- JSP页面:/bank/testQueryOrder_BCM1 -->
<html>
	<%
		//String orders = request.getParameter("orders");
		String orders = "C20091030000001";

		long a = System.currentTimeMillis();

		LogUtil myLog = new LogUtil(false, true, this.getClass());
		myLog.info("orders=" + orders);

		String code, err, msg;
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = new com.bocom.netpay.b2cAPI.BOCOMB2CClient();
		int ret = client.initialize(Constant.WEB_INFO_PATH + File.separator
				+ "bankkey" + File.separator + "init" + File.separator
				+ "bankInterfaceConfig-BCM.xml");
		if (ret != 0) {
			out.print("init error:" + client.getLastErr());
		} else {
			long b = System.currentTimeMillis();
			System.out.println("--BCM3.jsp--1-----" + (b - a));

			//---------------------
			StringBuffer myBuf = new StringBuffer();//-----------

			B2CConnection connection = new B2CConnection(
			BOCOMSetting.UseSSL);
			connection.trustAllHttpsCertificates();

			String fullURL = "https://ebank.95559.com.cn/corporbank/NsTrans?dse_operationName=cb2202_queryOrderOp&reqData=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22gb2312%22%3F%3E%3CBOCOMB2C%3E%3CopName%3Ecb2202_queryOrderOp%3C%2FopName%3E%3CreqParam%3E%3CmerchantID%3E301440360129520%3C%2FmerchantID%3E%3Cnumber%3E1%3C%2Fnumber%3E%3Cdetail%3E1%3C%2Fdetail%3E%3Corders%3EC20091030000001%3C%2Forders%3E%3C%2FreqParam%3E%3C%2FBOCOMB2C%3E&signData=MIIE%2FgYJKoZIhvcNAQcCoIIE7zCCBOsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA%2BUwggPhMIICyaADAgECAgRMXdLhMA0GCSqGSIb3DQEBBQUAMDUxCzAJBgNVBAYTAkNOMRQwEgYDVQQKEwtCQU5LQ09NTSBDQTEQMA4GA1UEAxMHQk9DT01DQTAeFw0wOTA5MjIwNzMxMzhaFw0xMTA5MjIwNzMxMzhaMIGAMQswCQYDVQQGEwJDTjEUMBIGA1UEChMLQkFOS0NPTU0gQ0ExETAPBgNVBAsTCEJBTktDT01NMRIwEAYDVQQLEwlNZXJjaGFudHMxNDAyBgNVBAMTKzA0MEAwMTQ0MDQwMDAwMDE1MjcxNkBbMzAxNDQwMzYwMTI5NTIwXUAwMDAwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBALglwOl2sLwGJ8JiaHp4DtjVygLat2b6ZEvk7qD2heCbpB1CVwhFRvXD8v7Ly%2FQgxjG3G4Z6RuT51f2DLuDACIDTreFTxCvRQIxQA3OYGVnqyq3CC3Qc5kYy6olGGpPYAnoODADchG7jiAFDqU%2FPpvEUuMmhUENoxzIllxQMgSwRAgMBAAGjggEvMIIBKzAfBgNVHSMEGDAWgBTSs9GxOUepN3l34yRNY7X4QsuZczBEBgkqhkiG9w0BCQ8ENzA1MA4GCCqGSIb3DQMCAgIAgDAOBggqhkiG9w0DBAICAIAwBwYFKw4DAgcwCgYIKoZIhvcNAwcwPQYDVR0gBDYwNDAyBgRVHSAAMCowKAYIKwYBBQUHAgEWHGh0dHA6Ly8xOTIuMTY4LjMuMTEwL2Nwcy5odG0wVgYDVR0fBE8wTTBLoEmgR6RFMEMxCzAJBgNVBAYTAkNOMRQwEgYDVQQKEwtCQU5LQ09NTSBDQTEMMAoGA1UECxMDY3JsMRAwDgYDVQQDEwdjcmwzODc1MAwGA1UdDwQFAwMH%2BYAwHQYDVR0OBBYEFAf4PbwEuZqX6aIT4iXipwUfSsMEMA0GCSqGSIb3DQEBBQUAA4IBAQB1lgHmpF6591tUOWmOa61DkJd8ZoV0yk5P2Fl6heGXowH0SUEtIU38m4TTYEEtNYnylbo5XlhhBc0k99lMEXh0gzNhUvmKhnbviHDYzVLNNsF1idRNfqc9rQhrPDoFgJXKqI0cdHAY4%2B27fahfwmiRGVB3SmVekvBloAQFdD4FGEpwI3aB2ITbIisFZbSL70loX9PYj57OLxv4SMm5j1Sx58ZWrOcndOm4cx6TMh6wFK4hB0PqR1h05n45geJlhbTifkewBcOLcIvoK4S8cbrfVMAURHDJDLTcDfIcFUPMxlSaI4JPM3sbtM9VMLngupGv%2BvSKWh8SuL1EPPjgtDhEMYHiMIHfAgEBMD0wNTELMAkGA1UEBhMCQ04xFDASBgNVBAoTC0JBTktDT01NIENBMRAwDgYDVQQDEwdCT0NPTUNBAgRMXdLhMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYAUEnRCOKov%2FkGrYni6fUQaMgZX%2BYCXkLMwGwG%2F0vzmr%2B%2FYPYCc78pkRgI8v%2FFskIXmtSjAeyG3VlytV%2Bq15ZWK1c3RcXWO6DDQGo7MJYAqZ37CwHsx%2Fgy%2B9bp6AizZFW9StGSAw0UEsR5kEEyr8urpr56phWM9vWjxFPIqaurY1w%3D%3D";

			HttpURLConnection httpconn = null;
			InputStream in = null;
			try {
				URL url = new URL(fullURL);
				httpconn = (HttpURLConnection) url.openConnection();

				long c = System.currentTimeMillis();
				System.out.println("----2------" + (c - b));

				httpconn.setRequestProperty("User-Agent", "Mozilla/MSIE");
				httpconn.setConnectTimeout(100000);//-------
				httpconn.setReadTimeout(100000);// ------------

				httpconn.connect();

				long d = System.currentTimeMillis();
				System.out.println("----3------" + (d - c));

				System.out.println("responseCode:"
				+ httpconn.getResponseCode());

				int contentLen = httpconn.getContentLength();
				in = httpconn.getInputStream();

				String resMsg = httpconn.getHeaderField(0);
				System.out.println("contentLen:" + contentLen + "\n"
				+ "resMsg:" + resMsg);

				InputStreamReader insr = new InputStreamReader(httpconn
				.getInputStream());

				int respInt = insr.read();
				while (respInt != -1) {
			myBuf = myBuf.append((char) respInt);
			out.println((char) respInt); //----------
			respInt = insr.read();
				}

				long e = System.currentTimeMillis();
				System.out.println("----4------" + (e - d));

				System.out.println(myBuf.toString());
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			//-----------------------------------------------
			String repContent = myBuf.toString();
			repContent.replace('\r', ' ');
			repContent.replace('\n', ' ');
			repContent = repContent.trim();
			int begIndex = repContent.indexOf("<opRep>");
			int endIndex = repContent.indexOf("</opRep>");
			String origData = repContent.substring(begIndex, endIndex + 8);
			begIndex = repContent.indexOf("<signData>");
			endIndex = repContent.indexOf("</signData>");
			String signMsg = repContent.substring(begIndex + 10, endIndex);
			//signServer = new NetSignServer();
			//signServer.NSDetachedVerify(signMsg.getBytes("GBK"), origData
			//.getBytes("GBK"));
			//if (signServer.getLastErrnum() < 0) {
			//	System.out.println("verify sign Failed,response xml:");
			//}

			//System.out.println(repContent);
		}
	%>
</html>
