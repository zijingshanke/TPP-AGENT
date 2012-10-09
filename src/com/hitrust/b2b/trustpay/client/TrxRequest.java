//// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://kpdus.tripod.com/jad.html
//// Decompiler options: packimports(3) fieldsfirst ansi space 
//// Source File Name:   TrxRequest.java
//
//package com.hitrust.b2b.trustpay.client;
//
//import java.io.*;
//import java.net.*;
//import javax.net.SocketFactory;
//import javax.net.ssl.SSLSocket;
//
//// Referenced classes of package com.hitrust.b2b.trustpay.client:
////			LogWriter, MerchantConfig, TrxResponse, TrxException, 
////			XMLDocument
//
//public abstract class TrxRequest {
//	private LogWriter iLogWriter;
//	private String iECMerchantType;
//	public static final String EC_MERCHANT_TYPE_B2C = "B2C";
//	public static final String EC_MERCHANT_TYPE_B2B = "B2B";
//
//	public TrxRequest(String aECMerchantType) {
//		iLogWriter = null;
//		iECMerchantType = "";
//		iECMerchantType = aECMerchantType;
//	}
//
//	public TrxResponse postRequest() {
//		TrxResponse tTrxResponse = null;
//		try {
//			iLogWriter = new LogWriter(MerchantConfig.getTrxLogFile());
//			iLogWriter.logNewLine("检查交易请求是否合法：");
//			checkRequest();
//			iLogWriter.logNewLine("正确");
//			iLogWriter.logNewLine("交易报文：");
//			XMLDocument tRequestMessage = getRequestMessage();
//			iLogWriter.logNewLine("完整交易报文：");
//			tRequestMessage = composeRequestMessage(tRequestMessage);
//			iLogWriter.logNewLine("签名后的报文：");
//			tRequestMessage = MerchantConfig.signMessage(tRequestMessage);
//			iLogWriter.logNewLine("发送交易报文至网上支付平台：");
//			XMLDocument tResponseMessage = sendMessage(tRequestMessage);
//			iLogWriter.logNewLine("验证网上支付平台响应报文的签名：");
//			tResponseMessage = MerchantConfig.verifySign(tResponseMessage);
//			iLogWriter.log("正确");
//			iLogWriter.logNewLine("生成交易响应对象：");
//			tTrxResponse = constructResponse(tResponseMessage);
//			iLogWriter
//					.logNewLine("交易结果：[" + tTrxResponse.getReturnCode() + "]");
//			iLogWriter.logNewLine("错误信息：[" + tTrxResponse.getErrorMessage()
//					+ "]");
//		} catch (TrxException e) {
//			tTrxResponse = new TrxResponse(e.getCode(), e.getMessage() + " - "
//					+ e.getDetailMessage());
//			if (iLogWriter != null)
//				iLogWriter.logNewLine("错误代码：[" + tTrxResponse.getReturnCode()
//						+ "]    错误信息：[" + tTrxResponse.getErrorMessage() + "]");
//		} catch (Exception e) {
//			tTrxResponse = new TrxResponse("1999", "系统发生无法预期的错误 - "
//					+ e.getMessage());
//			if (iLogWriter != null)
//				iLogWriter.logNewLine("错误代码：[" + tTrxResponse.getReturnCode()
//						+ "]    错误信息：[" + tTrxResponse.getErrorMessage() + "]");
//		}
//		if (iLogWriter != null)
//			iLogWriter.closeWriter();
//		return tTrxResponse;
//	}
//
//	protected abstract void checkRequest() throws TrxException;
//
//	protected abstract XMLDocument getRequestMessage() throws TrxException;
//
//	private XMLDocument composeRequestMessage(XMLDocument aMessage)
//			throws TrxException {
//		String tMessage = "<Merchant><ECMerchantType>" + iECMerchantType
//				+ "</ECMerchantType>" + "<MerchantID>"
//				+ MerchantConfig.getMerchantID() + "</MerchantID>"
//				+ "</Merchant>" + aMessage.toString();
//		return new XMLDocument(tMessage);
//	}
//
//	private XMLDocument sendMessage(XMLDocument aMessage) throws TrxException {
//		String tMessage = "<MSG>" + aMessage.toString() + "</MSG>";
//		iLogWriter.logNewLine("提交网上支付平台的报文：\n" + tMessage);
//		int tContentLength = 0;
//		try {
//			tContentLength = tMessage.getBytes("UTF-8").length;
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//			throw new TrxException("1999", "系统发生无法预期的错误", e.getMessage());
//		}
//		String tResponseMessage = "";
//		XMLDocument tTrxResponse = null;
//		SSLSocket tSSLSocket = null;
//		try {
//			iLogWriter.logNewLine("连线网上支付平台：");
//			Socket tSocket = null;
//			if (MerchantConfig.getTrustPayConnectMethod().equals("https")) {
//				tSSLSocket = (SSLSocket) MerchantConfig.getSSLSocketFactory()
//						.createSocket(
//								InetAddress.getByName(MerchantConfig
//										.getTrustPayServerName()),
//								MerchantConfig.getTrustPayServerPort());
//				tSSLSocket.startHandshake();
//				tSocket = tSSLSocket;
//			} else {
//				tSocket = new Socket(InetAddress.getByName(MerchantConfig
//						.getTrustPayServerName()), MerchantConfig
//						.getTrustPayServerPort());
//			}
//			iLogWriter.log("成功");
//			iLogWriter.logNewLine("提交交易报文：");
//			String tHttpHeader = "POST "
//					+ MerchantConfig.getTrustPayTrxURL()
//					+ " HTTP/1.1"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "User-Agent: Java/1.3.1"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Host: "
//					+ MerchantConfig.getTrustPayServerName()
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Cookie: JSESSIONID=aIG-RvFXRUUe"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Accept: text/html, image/gif, image/jpeg, *; q=.2, * /*; q=.2"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Connection: keep-alive"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Content-Type: application/x-www-form-urlencoded"
//					+ MerchantConfig.getTrustPayNewLine() + "Content-Length: ";
//			String tHttpRequest = tHttpHeader + tContentLength
//					+ MerchantConfig.getTrustPayNewLine()
//					+ MerchantConfig.getTrustPayNewLine() + tMessage;
//			PrintWriter tOut = new PrintWriter(new BufferedWriter(
//					new OutputStreamWriter(tSocket.getOutputStream(), "UTF-8")));
//			tOut.write(tHttpRequest);
//			tOut.flush();
//			iLogWriter.log("成功");
//			iLogWriter.logNewLine("等待网上支付平台返回交易结果：");
//			BufferedReader tIn = new BufferedReader(new InputStreamReader(
//					tSocket.getInputStream(), "UTF-8"));
//			for (String tLine = null; (tLine = tIn.readLine()) != null;) {
//				tResponseMessage = tResponseMessage + tLine;
//				if (tLine.indexOf("</MSG>") != -1)
//					break;
//			}
//
//			tSocket.close();
//			iLogWriter.log("成功");
//			iLogWriter.logNewLine("返回报文：");
//			iLogWriter.log("\n" + tResponseMessage.toString());
//			if (tResponseMessage.indexOf("HTTP/1.1 200") == -1)
//				throw new TrxException("1206", "网上支付平台服务暂时停止");
//			tTrxResponse = (new XMLDocument(tResponseMessage)).getValue("MSG");
//			if (tTrxResponse == null)
//				throw new TrxException("1205", "无法辨识网上支付平台的响应报文", "无[MSG]段！");
//		} catch (TrxException e) {
//			throw e;
//		} catch (UnknownHostException e) {
//			e.printStackTrace(System.out);
//			throw new TrxException("1201", "无法连线网上支付平台", "无法取得["
//					+ MerchantConfig.getTrustPayServerName() + "]的IP地址!");
//		} catch (ConnectException e) {
//			e.printStackTrace(System.out);
//			throw new TrxException("1201", "无法连线网上支付平台", "无法连线"
//					+ MerchantConfig.getTrustPayServerName() + "的"
//					+ MerchantConfig.getTrustPayServerPort() + "端口!");
//		} catch (SocketException e) {
//			e.printStackTrace(System.out);
//			throw new TrxException("1202", "提交交易时发生网络错误", "连线中断！");
//		} catch (IOException e) {
//			e.printStackTrace(System.out);
//			throw new TrxException("1202", "提交交易时发生网络错误", "连线中断！");
//		}
//		return tTrxResponse;
//	}
//
//	protected abstract TrxResponse constructResponse(XMLDocument xmldocument)
//			throws TrxException;
//}