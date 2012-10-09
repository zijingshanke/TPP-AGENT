package com.fordays.fdpay.bank.icbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.protocol.Protocol;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.ChangeCharset;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.transaction.Charge;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.MySSLSocketFactory;
import com.neza.tool.URLUtil;

/**
 * 工商银行订单查询
 * 
 * @remark:b2c/b2b/c2c通用
 */
public class IcbcOrder {
	private String APIName = "";// 接口名称
	private String APIVersion = "";// 接口版本
	// 请求数据,xml包 MerReqData （注意：xml包中所有字段都是必输项）：
	private String orderNum = "";// 订单号
	private String tranDate = "";// 交易日期
	private String ShopCode = "";// 商家号码
	private String ShopAccount = "";// 商城账号

	// -------------------------------
	private String httpHead = "";
	private String bankHost = "";
	private String serverPath = "";
	private int port = 0;
	private String interfacePath = "";
	private String KeyStoreFile = "";// 商户私钥库
	// --------------------------------
	private String strSrc = "";
	private String url = "";
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-ICBC.xml";
	private Document configDoc = null;
	private String icbcOrderMsg = "";
	// ---------------------------------
	private LogUtil myLog;

	public IcbcOrder(String version) {
		init(version);
	}

	public void init(String version) {
		configDoc = BankUtil.loadXml(configXmlUrl);
		XmlUtil xml = new XmlUtil();

		httpHead = xml.getTextByNode(configDoc, "//BANK/ICBC/QUERY/httpHead");
		bankHost = xml.getTextByNode(configDoc, "//BANK/ICBC/QUERY/bankHost");
		serverPath = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/serverPath");
		port = Integer.parseInt(xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/port"));

		APIName = xml.getTextByNode(configDoc, "//BANK/ICBC/QUERY/APIName");

		if ("B2B".equals(version)) {
			APIVersion = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/APIVersion_B2B");
			ShopCode = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/ShopCode_B2B");
			ShopAccount = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/ShopAccount_B2B");
			KeyStoreFile = Constant.WEB_INFO_PATH+ xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/storeFileB2B");
		} else if ("B2C".equals(version)) {
			APIVersion = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/APIVersion_B2C");
			ShopCode = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/ShopCode_B2C");
			ShopAccount = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/ShopAccount_B2C");
			KeyStoreFile = Constant.WEB_INFO_PATH+ xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/storeFile");
		} else if ("C2C".equals(version)) {
			APIVersion = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/APIVersion_C2C");
			ShopCode = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/ShopCode_C2C");
			ShopAccount = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/ShopAccount_C2C");
			KeyStoreFile="";
		}
	}

	/**
	 * @执行查询、解析结果
	 */
	public IcbcOrderResult getIcbcOrderResult() {
		myLog = new LogUtil(false, false, IcbcOrder.class);
		IcbcOrderResult order = null;

		NameValuePair[] nvp = new NameValuePair[3];
		nvp[0] = new NameValuePair("APIName", this.APIName);
		nvp[1] = new NameValuePair("APIVersion", this.APIVersion);
		nvp[2] = new NameValuePair("MerReqData", this.getStrSrc());
		
		myLog.info("MerReqData:"+"\r\n"+nvp[2]);

		try {
			Protocol protocol = this.getProtocol();
			String resultEncode = URLUtil.getResponseBodyAsPost(bankHost, port,
					serverPath, nvp, protocol);

			myLog.info("IcbcOrder get the message of decoded is :"
					+ BankUtil.getURLDecode(resultEncode).toString());

			myLog.info("订单:" + this.orderNum + ",查询结果是:");

			boolean flag = checkQueryResult(resultEncode);
			if (flag) {
				order = getQueryDataResult(resultEncode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	/**
	 * @查询订单XML报文
	 */
	public String getStrSrc() {
		StringBuffer str = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>");
		str.append("<ICBCAPI>");
		str.append("<in>");
		str.append("<orderNum>" + this.orderNum + "</orderNum>");
		str.append("<tranDate>" + this.tranDate + "</tranDate>");
		str.append("<ShopCode>" + this.ShopCode + "</ShopCode>");
		str.append("<ShopAccount>" + this.ShopAccount + "</ShopAccount>");
		str.append("</in>");
		str.append("</ICBCAPI>");
		this.strSrc = str.toString();
		return strSrc;
	}

	private Protocol getProtocol() {
		myLog = new LogUtil(false, false, IcbcOrder.class);
		XmlUtil xml = new XmlUtil();
		final String storeFile = KeyStoreFile;
		final String trustFile = Constant.WEB_INFO_PATH+ xml.getTextByNode(configDoc, "//BANK/ICBC/QUERY/trustFile");

		myLog.info("工行订单查询,加载证书--");
		myLog.info("storeFile:" + storeFile);
		myLog.info("trustFile:" + trustFile);

		try {
			final String pass = xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/storePassword");
			FileInputStream inkey = new FileInputStream(storeFile);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(inkey, pass.toCharArray());
			inkey.close();

			FileInputStream inTru = new FileInputStream(trustFile);
			KeyStore ts = KeyStore.getInstance("JKS");
			ts.load(inTru, pass.toCharArray());

			KeyManagerFactory kmf;
			TrustManagerFactory tmf;

			kmf = KeyManagerFactory.getInstance(xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/storeInstance"));
			kmf.init(ks, pass.toCharArray());

			tmf = TrustManagerFactory.getInstance(xml.getTextByNode(configDoc,"//BANK/ICBC/QUERY/trustInstance"));
			tmf.init(ts);

			MySSLSocketFactory sslFactory = new MySSLSocketFactory(kmf
					.getKeyManagers(), tmf.getTrustManagers());

			Protocol myhttps = new Protocol("https", sslFactory, 443);
			Protocol.registerProtocol("https", myhttps);

			myLog.info("--Protocol.registerProtocol() success...");

			return new Protocol("https", sslFactory, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @解析订单查询结果(B2B/B2C)
	 * @param resultEncode
	 * @return QueryOrderResult
	 */
	public IcbcOrderResult getQueryDataResult(String resultEncode)
			throws AppException {
		myLog=new LogUtil(false,false,this.getClass());
		
		IcbcOrderResult result = null;

		ChangeCharset charUtil = new ChangeCharset();
		String queryString = "";
		try {
			queryString = charUtil.changeCharset(BankUtil
					.getURLDecode(resultEncode), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuffer resultData = new StringBuffer(queryString);

		XmlUtil xmlctl = new XmlUtil();
		Document document = xmlctl.readResult(resultData);
		String APIName = xmlctl
				.getTextByNode(document, "//ICBCAPI/pub/APIName");
		String APIVersion = xmlctl.getTextByNode(document,
				"//ICBCAPI/pub/APIVersion");
		String orderNum = xmlctl.getTextByNode(document,
				"//ICBCAPI/in/orderNum");
		String tranDate = xmlctl.getTextByNode(document,
				"//ICBCAPI/in/tranDate");
		String ShopCode = xmlctl.getTextByNode(document,
				"//ICBCAPI/in/ShopCode");
		String ShopAccount = xmlctl.getTextByNode(document,
				"//ICBCAPI/in/ShopAccount");
		String tranSerialNum = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/tranSerialNum");
		String tranStat = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/tranStat");
		String bankRem = xmlctl
				.getTextByNode(document, "//ICBCAPI/out/bankRem");
		String amount = xmlctl.getTextByNode(document, "//ICBCAPI/out/amount");
		String currType = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/currType");
		String tranTime = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/tranTime");
		String PayeeName = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/PayeeName");
		String JoinFlag = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/JoinFlag");
		String MerJoinFlag = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/MerJoinFlag");
		String CustJoinFlag = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/CustJoinFlag");
		String CustJoinNum = xmlctl.getTextByNode(document,
				"//ICBCAPI/out/CustJoinNum");
		String CertID = xmlctl.getTextByNode(document, "//ICBCAPI/out/CertID");

		result = new IcbcOrderResult();
		result.setAPIName(APIName);
		result.setAPIVersion(APIVersion);
		result.setOrderNum(orderNum);
		result.setTranDate(tranDate);
		result.setShopCode(ShopCode);
		result.setShopAccount(ShopAccount);
		result.setTranSerialNum(tranSerialNum);
		result.setTranStat(tranStat);
		result.setBankRem(bankRem);
		result.setAmount(amount);
		result.setCurrType(currType);
		result.setTranTime(tranTime);
		result.setPayeeName(PayeeName);
		result.setJoinFlag(JoinFlag);
		result.setMerJoinFlag(MerJoinFlag);
		result.setCustJoinFlag(CustJoinFlag);
		result.setCustJoinNum(CustJoinNum);
		result.setCertID(CertID);

		// ------------------------
		result.setROrderNo(orderNum);
		result.setRAmount(BankUtil.getDollarAmount(amount));

		if ("001.001.002.001".equals(APIVersion)) {// B2C
			if ("1".equals(tranStat)) {
				result.setRChargeStatus(Charge.CHARGE_STATUS_1);
			} else {
				result.setRChargeStatus(Charge.CHARGE_STATUS_2);
				myLog.info("[0-支付成功，未清算 1-支付成功，已清算  2-支付失败  3-支付可疑交易]:"+tranStat);
			}
		} else if("001.001.001.001".equals(APIVersion)){
			if ("3".equals(tranStat)) {
				result.setRChargeStatus(Charge.CHARGE_STATUS_1);
			} else {
				result.setRChargeStatus(Charge.CHARGE_STATUS_2);
				myLog.info("[3-指令处理完成，转账成功 4-指令处理失败，转账未完成 6-指令超过支付人的限额,正在等待主管会计批复 7-指令超过支付人的限额，正在等待主管会计第二次批复 8-指令超过支付人的限额，被主管会计否决 9-银行正在处理（可疑）]:"+tranStat);
			}
		}else{
			myLog.error("非法的API类型:"+APIVersion);
		}
		
		result.setRRequestHost("www.qmpay.com");
		result.setRUrl(result.getUrl());
		return result;
	}

	/**
	 * @解析订单查询结果(C2C)
	 * @param resultEncode
	 * @return QueryOrderResult
	 */
	public static IcbcOrderResult getQueryDataResult_C2C(String resultEncode)
			throws AppException {
		IcbcOrderResult result = null;
		return result;
	}

	// 记录查询
	public String getUrl() {
		this.interfacePath = httpHead + "://" + bankHost + serverPath + ":"
				+ port;
		this.url = getInterfacePath() + "\n" + getStrSrc();
		return url;
	}

	/**
	 * @检查订单查询反馈结果是否有异常
	 */
	public boolean checkQueryResult(String resultData) {
		myLog = new LogUtil(false, false, IcbcOrder.class);
		boolean flag = false;
		String result = getErrorInfo(resultData);
		myLog.info("检查订单查询反馈结果:");
		if (result == null) {
			flag = true;
			myLog.info("正常");
		} else {
			myLog.info(result);
		}
		return flag;
	}

	private static HashMap<String, String> errors = null;

	public String getErrorInfo(String resultData) {
		String errorInfo = null;
		if (errors == null) {
			String res = "com.fordays.fdpay.bank.icbc.IcbcOrderMsg";
			ResourceBundle reb = ResourceBundle.getBundle(res);
			if (reb != null) {
				errors = new HashMap<String, String>();
				Enumeration<String> keyEmu = reb.getKeys();
				while (keyEmu.hasMoreElements()) {
					String key = (String) keyEmu.nextElement();
					String tempValue = "";
					try {
						tempValue = new String(reb.getString(key).getBytes(
								"ISO-8859-1"), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					String value = tempValue;
					errors.put(key, value);
				}
			}
		}
		Object obj = IcbcOrder.errors.get(resultData);
		if (obj != null) {
			errorInfo = obj.toString();
		}
		return errorInfo;
	}

	public String getAPIName() {
		return APIName;
	}

	public void setAPIName(String name) {
		APIName = name;
	}

	public String getAPIVersion() {
		return APIVersion;
	}

	public void setAPIVersion(String version) {
		APIVersion = version;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getShopCode() {
		return ShopCode;
	}

	public void setShopCode(String shopCode) {
		ShopCode = shopCode;
	}

	public String getShopAccount() {
		return ShopAccount;
	}

	public void setShopAccount(String shopAccount) {
		ShopAccount = shopAccount;
	}

	public String getInterfacePath() {
		return interfacePath;
	}

	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static String getConfigXmlUrl() {
		return configXmlUrl;
	}

	public static void setConfigXmlUrl(String configXmlUrl) {
		IcbcOrder.configXmlUrl = configXmlUrl;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}

	public String getHttpHead() {
		return httpHead;
	}

	public void setHttpHead(String httpHead) {
		this.httpHead = httpHead;
	}

	public String getBankHost() {
		return bankHost;
	}

	public void setBankHost(String bankHost) {
		this.bankHost = bankHost;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getIcbcOrderMsg() {
		return icbcOrderMsg;
	}

	public void setIcbcOrderMsg(String icbcOrderMsg) {
		this.icbcOrderMsg = icbcOrderMsg;
	}

	public String getKeyStoreFile() {
		return KeyStoreFile;
	}

	public void setKeyStoreFile(String keyStoreFile) {
		KeyStoreFile = keyStoreFile;
	}
}