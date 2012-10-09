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
			System.out.println("------BCM.jsp----1-----" + (b - a));

			//	com.bocom.netpay.b2cAPI.BOCOMB2COPReply rep = client.queryOrder(orders);
			if (orders == null || orders.trim().length() == 0) {
				out.println("orders == null or length==0");
			}
			StringTokenizer st = new StringTokenizer(orders, "|");
			int size = st.countTokens();
			if (size > 20) {
				out.println("size >20");
			}

			long c = System.currentTimeMillis();
			System.out.println("----2------" + (c - b));

			BOCOMB2COPRequest req = new BOCOMB2COPRequest();
			req.addParam("merchantID", BOCOMSetting.MerchantID);
			req.addParam("number", Integer.toString(size));
			req.addParam("detail", "1");
			req.addParam("orders", orders);
			req.setOpName("cb2202_queryOrderOp");
			String xml = req.getXML();
			if (xml == null) {
				out.println("request data error");
			}
			System.out.println("request Xml:" + xml);

			long d = System.currentTimeMillis();
			System.out.println("----3------" + (d - c));

			//	String repXML = client.executeOperation("cb2202_queryOrderOp",xml);
			String opName = "cb2202_queryOrderOp";
			String reqData = xml;

			if (!BOCOMSetting.isAPIInitialize) {
				System.out.println("Bocomm API init Failed");
			}

			String fullURL = BOCOMSetting.ApiURL + "?dse_operationName="
			+ opName;
			fullURL = fullURL + "&reqData=" + URLEncoder.encode(reqData);

			B2CConnection connection = new B2CConnection(
			BOCOMSetting.UseSSL);

			long e = System.currentTimeMillis();
			System.out.println("----4------" + (e - d));

			NetSignServer signServer = new NetSignServer();
			signServer.NSSetPlainText(reqData.getBytes("GBK"));
			String DN = BOCOMSetting.MerchantCertDN;
			byte signData[] = signServer.NSDetachedSign(DN);
			if (signServer.getLastErrnum() < 0) {
				System.out.println("client sign failed");
			}

			long f = System.currentTimeMillis();
			System.out.println("----5-----" + (f - e));

			fullURL = fullURL + "&signData="
			+ URLEncoder.encode(new String(signData, "GBK"));
			signServer = null;

			long g = System.currentTimeMillis();
			System.out.println("----6------" + (g - f));
			System.out.println("3-----fullURL-----" + fullURL);

			//	String repContent = connection.sendAndReceive(fullURL);//---------------
			StringBuffer myBuf = new StringBuffer();//-----------
			connection.trustAllHttpsCertificates();

			long h = System.currentTimeMillis();
			System.out.println("----7------" + (h - g));

			HttpURLConnection httpconn = null;
			InputStream in = null;
			try {
				URL url = new URL(fullURL);
				httpconn = (HttpURLConnection) url.openConnection();

				long i = System.currentTimeMillis();
				System.out.println("----8------" + (i - h));

				httpconn.setRequestProperty("User-Agent", "Mozilla/MSIE");
				httpconn.setConnectTimeout(100000);//-------
				httpconn.setReadTimeout(100000);// ------------

				httpconn.connect();

				long j = System.currentTimeMillis();
				System.out.println("----9------" + (j - i));

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

				long k = System.currentTimeMillis();
				System.out.println("----10------" + (k - j));

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
			signServer = new NetSignServer();
			signServer.NSDetachedVerify(signMsg.getBytes("GBK"), origData
			.getBytes("GBK"));
			if (signServer.getLastErrnum() < 0) {
				System.out.println("verify sign Failed,response xml:");
			}

			//System.out.println(repContent);
		}
	%>
</html>
