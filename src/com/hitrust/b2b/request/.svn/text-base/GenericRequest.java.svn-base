//package com.hitrust.b2b.request;
//
//import com.hitrust.b2b.response.GenericResponse;
//import com.hitrust.b2b.util.*;
//import java.io.*;
//import java.net.*;
//import java.security.Signature;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateFactory;
//import javax.net.ssl.SSLSocket;
//import org.apache.log4j.Category;
//
//public abstract class GenericRequest {
//	static Category LOG = Category.getInstance("GenericRequest");
//	public static final String PRE_PAY_CODE = "P001";
//	public static final String SPL_PRE_PAY_CODE = "P002";
//	public static final String CAL_PRE_PAY_CODE = "P003";
//	public static final String REL_PAY_CODE = "P004";
//	public static final String BTH_REL_PAY_CODE = "P005";
//	public static final String DIR_PAY_CODE = "P006";
//	public static final String QRY_ORDER_CODE = "Q001";
//	public static final String QRY_TRNX_CODE = "Q002";
//	public static final String QRY_BTH_REL_PAY_RSL_CODE = "Q003";
//	public static final String DWN_TAB_CODE = "D001";
//	public static final String BIND_MER_CER_CODE = "B001";
//	protected XMLDocument reqMsg;
//	protected String Version;
//	protected String SignFlag;
//	protected String Language;
//	protected String ClientTime;
//	protected String MerchantNo;
//	protected String MerchantTrnxNo;
//	protected String TrnxCode;
//	protected String Signature;
//	protected String Signature_Algorithm;
//	protected XMLDocument totalDocument;
//	protected XMLDocument baseDocument;
//	protected XMLDocument reqHeadDocument;
//	protected XMLDocument dataBodyDocument;
//	protected XMLDocument SignDataDocument;
//	private static final int VERSION_LENGTH = 3;
//	private static final int SIGNFLAG_LENGTH = 1;
//	private static final int LANGUAGE_LENGTH = 10;
//	private static final int CLIENTTIME_LENGTH = 14;
//	private static final int MERCHANTNO_LENGTH = 15;
//	private static final int MERCHANTTRNXNO_LENGTH = 50;
//	private static final int TRNXCODE_LENGTH = 4;
//	private static final int SIGNATURE_LENGTH = 0;
//	private static final int SIGNATURE_ALGORITHM_LENGTH = 0;
//
//	public GenericRequest() {
//		Version = "";
//		SignFlag = "";
//		Language = "";
//		ClientTime = "";
//		MerchantNo = "";
//		MerchantTrnxNo = "";
//		TrnxCode = "";
//		Signature = "";
//		Signature_Algorithm = "";
//	}
//
//	public GenericResponse postRequest() {
//		GenericResponse tTrxResponse = null;
//		LOG.debug("begin");
//		try {
//			Version = MerchantConfig.getParameterByName("MessageVersion");
//			SignFlag = MerchantConfig.getParameterByName("MessageSignFlag");
//			Language = MerchantConfig.getParameterByName("MessageLanguage");
//			ClientTime = DateUtil.getCurrentTime();
//			try {
//				MerchantNo = "1".equals(MerchantConfig.getPressureTestFlag()) ? MerchantNo
//						: MerchantConfig.getMerchantID();
//			} catch (Exception ex) {
//				MerchantNo = MerchantConfig.getMerchantID();
//			}
//			LOG.debug("================== 交易请求开始 ==================");
//			LOG.debug("<<<<<<< 检查交易请求是否合法 - 开始 >>>>>>");
//			checkRequestMsg();
//			LOG.debug("<<<<<<< 检查交易请求是否合法 - 结束 >>>>>>");
//			LOG.debug("<<<<<<< 组成请求报文 - 开始 >>>>>>");
//			composeRequestMsg();
//			LOG.debug("<<<<<<< 组成请求报文 - 结束 >>>>>>");
//			LOG.debug("<<<<<<< 对交易报文进行签名 - 开始 >>>>>>");
//			singOnMsg();
//			LOG.debug("<<<<<<< 对交易报文进行签名 - 结束 >>>>>>");
//			LOG.debug("<<<<<<< 发送交易报文至网上支付平台 - 开始 >>>>>>");
//			XMLDocument tResponseMessage = sendMessage(totalDocument);
//			LOG.debug("<<<<<<< 发送交易报文至网上支付平台 - 结束 >>>>>>");
//			LOG.debug("<<<<<<< 验证网上支付平台响应报文的签名 - 开始 >>>>>>");
//			verifySign(tResponseMessage);
//			LOG.debug("<<<<<<< 验证网上支付平台响应报文的签名 - 结束 >>>>>>");
//			LOG.debug("<<<<<<< 生成交易响应对象 - 开始 >>>>>>");
//			tTrxResponse = new GenericResponse(tResponseMessage);
//			LOG.debug("<<<<<<< 生成交易响应对象 - 结束 >>>>>>");
//		} catch (TrxException e) {
//			tTrxResponse = new GenericResponse(e.getCode(), e.getCodeMessage()
//					+ " - " + e.getMessage());
//		} catch (Exception e) {
//			LOG.debug("发送请求报文时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			tTrxResponse = new GenericResponse("1999", "系统发生无法预期的错误 - "
//					+ e.getMessage());
//		}
//		LOG.debug("====== 交易请求结束 =====");
//		return tTrxResponse;
//	}
//
//	protected void composeRequestHeaderMsg() {
//		String baseMessage = "<Base><Version>" + Version + "</" + "Version"
//				+ ">" + "<" + "SignFlag" + ">" + SignFlag + "</" + "SignFlag"
//				+ ">" + "<" + "Language" + ">" + Language + "</" + "Language"
//				+ ">" + "</" + "Base" + ">";
//		baseDocument = new XMLDocument(baseMessage);
//		String reqHeaderMessage = "<ReqHeader><ClientTime>" + ClientTime + "</"
//				+ "ClientTime" + ">" + "<" + "MerchantNo" + ">" + MerchantNo
//				+ "</" + "MerchantNo" + ">" + "</" + "ReqHeader" + ">";
//		reqHeadDocument = new XMLDocument(reqHeaderMessage);
//		totalDocument = new XMLDocument(baseDocument.toString()
//				+ reqHeadDocument.toString());
//	}
//
//	protected void checkRequestHeaderMsg() throws TrxException {
//		DataVerify.checkEmptyAndLength(Version, "版本号", "Y", 3);
//		DataVerify.checkEmptyAndLength(SignFlag, "签名标志", "Y", 1);
//		if (!"0".equals(SignFlag) && !"1".equals(SignFlag))
//			throw new TrxException("1101", "商户提交的交易资料不合法", "签名标志只能为0或1! ");
//		DataVerify.checkEmptyAndLength(Language, "请求报文编码", "N", 10);
//		DataVerify.checkEmptyAndLength(ClientTime, "客户端时间", "Y", 14);
//		if (!DataVerify.isValidDateTime(ClientTime)) {
//			throw new TrxException("1101", "商户提交的交易资料不合法", "客户端时间格式不正确! ");
//		} else {
//			DataVerify.checkEmptyAndLength(MerchantNo, "商户代码", "Y", 15);
//			DataVerify.checkEmptyAndLength(MerchantTrnxNo, "商户交易号", "Y", 50);
//			DataVerify.checkEmptyAndLength(TrnxCode, "交易请求代码", "Y", 4);
//			return;
//		}
//	}
//
//	private void singOnMsg() throws TrxException {
//		String tMessage = null;
//		try {
//			String tKeyStoreType = MerchantConfig.getKeyStoreType();
//			if (tKeyStoreType.equalsIgnoreCase("0"))
//				tMessage = fileSignMessage(totalDocument);
//			else if (tKeyStoreType.equalsIgnoreCase("1"))
//				tMessage = signServerSignMessage(totalDocument);
//		} catch (TrxException e) {
//			throw e;
//		} catch (Exception e) {
//			LOG.debug("签名请求报文时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1102", "签名交易报文时发生错误", "签名交易报文时发生错误 - "
//					+ e.getMessage());
//		}
//		Signature = tMessage;
//		Signature_Algorithm = "SHA1withRSA";
//		String signMessage = "<SignData><Signature>" + Signature + "</"
//				+ "Signature" + ">" + "<" + "Signature-Algorithm" + ">"
//				+ Signature_Algorithm + "</" + "Signature-Algorithm" + ">"
//				+ "</" + "SignData" + ">";
//		SignDataDocument = new XMLDocument(signMessage);
//		totalDocument = new XMLDocument("<CMBCB2B><MessageData>"
//				+ totalDocument.toString() + "</" + "MessageData" + ">"
//				+ SignDataDocument.toString() + "</" + "CMBCB2B" + ">");
//		LOG.debug("请求报文 : " + totalDocument.getFormatDocument(" ").toString());
//		byte compressData[] = (byte[]) null;
//		try {
//			compressData = totalDocument.getValueNoNull("CMBCB2B").getBytes(
//					"GBK");
//		} catch (Exception e1) {
//			LOG.debug("压缩请求报文失败! ");
//			LOG.error("Exception : " + e1.getMessage());
//			throw new TrxException("1999", "系统发生无法预期的错误", "系统发生无法预期的错误 - "
//					+ e1.getMessage());
//		}
//		Base64 base64 = new Base64();
//		totalDocument = new XMLDocument("<CMBCB2B>"
//				+ base64.encode(compressData) + "</" + "CMBCB2B" + ">");
//	}
//
//	private String fileSignMessage(XMLDocument aMessage) throws Exception {
//		Signature tSignature = java.security.Signature
//				.getInstance("SHA1withRSA");
//		tSignature.initSign(MerchantConfig.getMerchantKey());
//		tSignature.update(aMessage.toString().getBytes("GBK"));
//		byte tSigned[] = tSignature.sign();
//		Base64 tBase64 = new Base64();
//		String tSignedBase64 = tBase64.encode(tSigned);
//		return tSignedBase64;
//	}
//
//	private String signServerSignMessage(XMLDocument aMessage) throws Exception {
//		return null;
//	}
//
//	private XMLDocument sendMessage(XMLDocument aMessage) throws TrxException {
//		String tMessage = aMessage.toString();
//		int tContentLength = 0;
//		try {
//			tContentLength = tMessage.getBytes("GBK").length;
//		} catch (Exception e) {
//			LOG.debug("取得请求报文Bytes时发生异常 !");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1999", "系统发生无法预期的错误", e.getMessage());
//		}
//		String tResponseMessage = "";
//		XMLDocument tTrxResponse = null;
//		SSLSocket tSSLSocket = null;
//		try {
//			LOG.debug("连线网上支付平台");
//			Socket tSocket = null;
//			if (MerchantConfig.getTrustPayConnectMethod().equals("https")) {
//				LOG.debug("use:"
//						+ MerchantConfig.getParameterByName("TrustStoreFile"));
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
//			LOG.debug("提交交易报文");
//			String tHttpHeader = "POST "
//					+ MerchantConfig.getTrustPayTrxURL()
//					+ " HTTP/1.1"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Content-Type: application/x-www-form-urlencoded"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Cache-Control: no-cache"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Pragma: no-cache"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "User-Agent: Java/1.4.2_04"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Host: "
//					+ MerchantConfig.getTrustPayServerName()
//					+ ":"
//					+ MerchantConfig.getTrustPayServerPort()
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Accept: text/html, image/gif, image/jpeg, *; q=.2, * /*; q=.2"
//					+ MerchantConfig.getTrustPayNewLine()
//					+ "Connection: keep-alive"
//					+ MerchantConfig.getTrustPayNewLine() + "Content-Length: ";
//			LOG.debug("tHttpHeader:" + tHttpHeader);
//			String tHttpRequest = tHttpHeader + tContentLength
//					+ MerchantConfig.getTrustPayNewLine()
//					+ MerchantConfig.getTrustPayNewLine() + tMessage;
//			PrintWriter tOut = new PrintWriter(new BufferedWriter(
//					new OutputStreamWriter(tSocket.getOutputStream(), "GBK")));
//			LOG.debug("tHttpRequest is:" + tHttpRequest);
//			tOut.write(tHttpRequest);
//			tOut.flush();
//			LOG.debug("等待网上支付平台返回交易结果");
//			BufferedReader tIn = new BufferedReader(new InputStreamReader(
//					tSocket.getInputStream(), "GBK"));
//			for (String tLine = null; (tLine = tIn.readLine()) != null;) {
//				tResponseMessage = tResponseMessage + tLine;
//				if (tLine.indexOf("</CMBCB2B>") != -1)
//					break;
//			}
//
//			tSocket.close();
//			if (tResponseMessage.indexOf("HTTP/1.1 200") == -1)
//				throw new TrxException("1206", "网上支付平台服务暂时停止");
//			LOG.debug("回应报文tResponseMessage:" + tResponseMessage);
//			tTrxResponse = new XMLDocument(tResponseMessage);
//			if (tTrxResponse.getValue("CMBCB2B") == null)
//				throw new TrxException("1205", "无法辨识网上支付平台的响应报文",
//						"无[CMBCB2B]段！");
//			LOG.debug("解压缩回应报文");
//			try {
//				Base64 base64 = new Base64();
//				byte compressData[] = base64.decode(tTrxResponse
//						.getValueNoNull("CMBCB2B"));
//				LOG.debug("解压缩后报文:" + DeflaterUtil.inflate(compressData));
//				tTrxResponse = new XMLDocument("<CMBCB2B>"
//						+ DeflaterUtil.inflate(compressData) + "</" + "CMBCB2B"
//						+ ">");
//			} catch (Exception e1) {
//				LOG.debug("解压缩回应报文失败! ");
//				LOG.error("Exception : " + e1.getMessage());
//				throw new TrxException("1999", "系统发生无法预期的错误", e1.getMessage());
//			}
//		} catch (TrxException e) {
//			throw e;
//		} catch (UnknownHostException e) {
//			LOG.debug("发送请求报文时发生异常! ");
//			LOG.error("UnknownHostException : " + e.getMessage());
//			throw new TrxException("1201", "无法连线网上支付平台", "无法取得["
//					+ MerchantConfig.getTrustPayServerName() + "]的IP地址!");
//		} catch (ConnectException e) {
//			LOG.debug("发送请求报文时发生异常! ");
//			LOG.error("ConnectException : " + e.getMessage());
//			throw new TrxException("1201", "无法连线网上支付平台", "无法连线"
//					+ MerchantConfig.getTrustPayServerName() + "的"
//					+ MerchantConfig.getTrustPayServerPort() + "端口!");
//		} catch (SocketException e) {
//			LOG.debug("发送请求报文时发生异常! ");
//			LOG.error("SocketException : " + e.getMessage());
//			throw new TrxException("1202", "提交交易时发生网络错误", "连线中断！");
//		} catch (IOException e) {
//			LOG.debug("发送请求报文时发生异常! ");
//			LOG.error("IOException : " + e.getMessage());
//			throw new TrxException("1202", "提交交易时发生网络错误", "连线中断！");
//		}
//		return tTrxResponse;
//	}
//
//	private void verifySign(XMLDocument aMessage) throws TrxException {
//		XMLDocument bodyMsg = aMessage.getValue("MessageData");
//		XMLDocument signMsg = aMessage.getValue("SignData");
//		if (bodyMsg == null || signMsg == null)
//			throw new TrxException("1301", "网上支付平台的响应报文不完整",
//					"无MessageData或SignData! ");
//		XMLDocument tAlgorithm = aMessage.getValue("Signature-Algorithm");
//		if (tAlgorithm == null)
//			throw new TrxException("1301", "网上支付平台的响应报文不完整",
//					"无Signature-Algorithm! ");
//		XMLDocument tSignBase64 = aMessage.getValue("Signature");
//		if (tSignBase64 == null)
//			throw new TrxException("1301", "网上支付平台的响应报文不完整", "无Signature! ");
//		XMLDocument tCertificate = aMessage.getValue("BankCertificate");
//		if (tCertificate == null)
//			throw new TrxException("1301", "网上支付平台的响应报文不完整",
//					"无BankCertificate! ");
//		Base64 tBase64 = new Base64();
//		byte tSign[] = tBase64.decode(tSignBase64.toString());
//		try {
//			Certificate tMerchantCertificate = getCertificateByBase64String(tCertificate
//					.toString());
//			Signature tSignature = java.security.Signature
//					.getInstance("SHA1withRSA");
//			tSignature.initVerify(tMerchantCertificate);
//			tSignature.update(bodyMsg.toString().getBytes("GBK"));
//			if (!tSignature.verify(tSign))
//				throw new TrxException("1302", "网上支付平台的响应报文签名验证失败", "验证签名失败! ");
//		} catch (TrxException e) {
//			throw e;
//		} catch (Exception e) {
//			LOG.debug("验证回复报文签名时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1302", "网上支付平台的响应报文签名验证失败", e.getMessage());
//		}
//	}
//
//	private static Certificate getCertificateByBase64String(String tBase64Cert)
//			throws TrxException {
//		Certificate tCertificate = null;
//		Base64 tBase64 = new Base64();
//		byte tCertBytes[] = tBase64.decode(tBase64Cert);
//		try {
//			CertificateFactory tCertificateFactory = CertificateFactory
//					.getInstance("X.509");
//			ByteArrayInputStream bais = new ByteArrayInputStream(tCertBytes);
//			if (bais.available() > 0)
//				tCertificate = tCertificateFactory.generateCertificate(bais);
//		} catch (Exception e) {
//			LOG.debug("取出支付平台证书时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1304", "无法辨识网上支付平台的证书", e.getMessage());
//		}
//		return tCertificate;
//	}
//
//	protected abstract void composeRequestMsg();
//
//	protected abstract void checkRequestMsg() throws TrxException;
//
//	public String getClientTime() {
//		return ClientTime;
//	}
//
//	public void setClientTime(String clientTime) {
//		ClientTime = clientTime;
//	}
//
//	public String getLanguage() {
//		return Language;
//	}
//
//	public void setLanguage(String language) {
//		Language = language;
//	}
//
//	public String getMerchantNo() {
//		return MerchantNo;
//	}
//
//	public void setMerchantNo(String merchantNo) {
//		MerchantNo = merchantNo;
//	}
//
//	public String getMerchantTrnxNo() {
//		return MerchantTrnxNo;
//	}
//
//	public void setMerchantTrnxNo(String merchantTrnxNo) {
//		MerchantTrnxNo = merchantTrnxNo;
//	}
//
//	public String getSignature() {
//		return Signature;
//	}
//
//	public void setSignature(String signature) {
//		Signature = signature;
//	}
//
//	public String getSignature_Algorithm() {
//		return Signature_Algorithm;
//	}
//
//	public void setSignature_Algorithm(String signature_Algorithm) {
//		Signature_Algorithm = signature_Algorithm;
//	}
//
//	public String getSignFlag() {
//		return SignFlag;
//	}
//
//	public void setSignFlag(String signFlag) {
//		SignFlag = signFlag;
//	}
//
//	public String getTrnxCode() {
//		return TrnxCode;
//	}
//
//	public void setTrnxCode(String trnxCode) {
//		TrnxCode = trnxCode;
//	}
//
//	public String getVersion() {
//		return Version;
//	}
//
//	public void setVersion(String version) {
//		Version = version;
//	}
//
//}