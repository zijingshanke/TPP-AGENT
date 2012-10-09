<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.io.*"%>
<%@page import="com.bocom.netpay.b2cAPI.B2CConnection"%>
<%@page import="com.bocom.netpay.b2cAPI.BOCOMB2CClient"%>
<%@page import="com.neza.base.Constant;"%>
<%
	BOCOMB2CClient client = new BOCOMB2CClient();
	int ret = client.initialize(Constant.WEB_INFO_PATH + File.separator
			+ "bankkey" + File.separator + "init" + File.separator
			+ "bankInterfaceConfig-BCM.xml");
	if (ret != 0) {
		out.print("init failed:" + client.getLastErr());
	} else {
		String srcUrl = "https://ebank.95559.com.cn/corporbank/NsTrans?dse_operationName=cb2202_queryOrderOp&reqData=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22gb2312%22%3F%3E%3CBOCOMB2C%3E%3CopName%3Ecb2202_queryOrderOp%3C%2FopName%3E%3CreqParam%3E%3CmerchantID%3E301440360129520%3C%2FmerchantID%3E%3Cnumber%3E1%3C%2Fnumber%3E%3Cdetail%3E1%3C%2Fdetail%3E%3Corders%3EC20091030000001%3C%2Forders%3E%3C%2FreqParam%3E%3C%2FBOCOMB2C%3E&signData=MIIE%2FgYJKoZIhvcNAQcCoIIE7zCCBOsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA%2BUwggPhMIICyaADAgECAgRMXdLhMA0GCSqGSIb3DQEBBQUAMDUxCzAJBgNVBAYTAkNOMRQwEgYDVQQKEwtCQU5LQ09NTSBDQTEQMA4GA1UEAxMHQk9DT01DQTAeFw0wOTA5MjIwNzMxMzhaFw0xMTA5MjIwNzMxMzhaMIGAMQswCQYDVQQGEwJDTjEUMBIGA1UEChMLQkFOS0NPTU0gQ0ExETAPBgNVBAsTCEJBTktDT01NMRIwEAYDVQQLEwlNZXJjaGFudHMxNDAyBgNVBAMTKzA0MEAwMTQ0MDQwMDAwMDE1MjcxNkBbMzAxNDQwMzYwMTI5NTIwXUAwMDAwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBALglwOl2sLwGJ8JiaHp4DtjVygLat2b6ZEvk7qD2heCbpB1CVwhFRvXD8v7Ly%2FQgxjG3G4Z6RuT51f2DLuDACIDTreFTxCvRQIxQA3OYGVnqyq3CC3Qc5kYy6olGGpPYAnoODADchG7jiAFDqU%2FPpvEUuMmhUENoxzIllxQMgSwRAgMBAAGjggEvMIIBKzAfBgNVHSMEGDAWgBTSs9GxOUepN3l34yRNY7X4QsuZczBEBgkqhkiG9w0BCQ8ENzA1MA4GCCqGSIb3DQMCAgIAgDAOBggqhkiG9w0DBAICAIAwBwYFKw4DAgcwCgYIKoZIhvcNAwcwPQYDVR0gBDYwNDAyBgRVHSAAMCowKAYIKwYBBQUHAgEWHGh0dHA6Ly8xOTIuMTY4LjMuMTEwL2Nwcy5odG0wVgYDVR0fBE8wTTBLoEmgR6RFMEMxCzAJBgNVBAYTAkNOMRQwEgYDVQQKEwtCQU5LQ09NTSBDQTEMMAoGA1UECxMDY3JsMRAwDgYDVQQDEwdjcmwzODc1MAwGA1UdDwQFAwMH%2BYAwHQYDVR0OBBYEFAf4PbwEuZqX6aIT4iXipwUfSsMEMA0GCSqGSIb3DQEBBQUAA4IBAQB1lgHmpF6591tUOWmOa61DkJd8ZoV0yk5P2Fl6heGXowH0SUEtIU38m4TTYEEtNYnylbo5XlhhBc0k99lMEXh0gzNhUvmKhnbviHDYzVLNNsF1idRNfqc9rQhrPDoFgJXKqI0cdHAY4%2B27fahfwmiRGVB3SmVekvBloAQFdD4FGEpwI3aB2ITbIisFZbSL70loX9PYj57OLxv4SMm5j1Sx58ZWrOcndOm4cx6TMh6wFK4hB0PqR1h05n45geJlhbTifkewBcOLcIvoK4S8cbrfVMAURHDJDLTcDfIcFUPMxlSaI4JPM3sbtM9VMLngupGv%2BvSKWh8SuL1EPPjgtDhEMYHiMIHfAgEBMD0wNTELMAkGA1UEBhMCQ04xFDASBgNVBAoTC0JBTktDT01NIENBMRAwDgYDVQQDEwdCT0NPTUNBAgRMXdLhMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYAUEnRCOKov%2FkGrYni6fUQaMgZX%2BYCXkLMwGwG%2F0vzmr%2B%2FYPYCc78pkRgI8v%2FFskIXmtSjAeyG3VlytV%2Bq15ZWK1c3RcXWO6DDQGo7MJYAqZ37CwHsx%2Fgy%2B9bp6AizZFW9StGSAw0UEsR5kEEyr8urpr56phWM9vWjxFPIqaurY1w%3D%3D";
		B2CConnection test = new B2CConnection(true);

		long a = System.currentTimeMillis();

		String repXML = test.sendAndReceive(srcUrl);
		long b = System.currentTimeMillis();

		System.out.println("----BCM2.jsp sendAndReceive()------"+ (b - a));
		out.print(repXML);
	}
%>