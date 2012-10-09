package com.fordays.fdpay.bank.icbc.test;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.protocol.Protocol;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.icbc.IcbcOrderResult;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.MySSLSocketFactory;
import com.neza.tool.URLUtil;

/**
 * 工商银行订单查询
 * 
 * @b2c/b2b/c2c通用
 */
public class IcbcOrderTest {
	private String APIName = "";// 接口名称
	private String APIVersion = "";// 接口版本
	// 请求数据,xml包 MerReqData （注意：xml包中所有字段都是必输项）：
	private String orderNum = "";// 订单号
	private String tranDate = "";// 交易日期
	private String ShopCode = "";// 商家号码
	private String ShopAccount = "";// 商城账号
	// -------------------------------
	private static final String httpHead = "https";
	private static final String bankHost = "corporbank.icbc.com.cn";
	private static final String serverPath = "/servlet/ICBCINBSEBusinessServlet";
	private static final int port = 443;
	private String interfacePath = "";
	private String strSrc = "";
	private String url = "";

	private String storeFile = "";
	private String trustFile = "";

	public IcbcOrderTest(String company, String version) {
		if ("fdpays".equals(company)) {
			initFdays(version);
		} else if ("qmpay".equals(company)) {
			initQmpay(version);
		} else {
			System.out.println("没有正确初始化company");
		}	
	}

	public void initQmpay(String version) {		
		trustFile = "E:\\cert/ICBC_cooperate_trust";

		this.APIName = "EAPI";// 区别大小写

		if ("B2B".equals(version)) {
			this.ShopCode = "2002EC13336181";
			this.APIVersion = "001.001.001.001";
			this.ShopAccount = "2002022509100027149";
			
			storeFile = "E:\\cert/qmpayb2b20090901store";
		} else if ("B2C".equals(version)) {
			this.ShopCode = "2002EC23343699";
			this.APIVersion = "001.001.002.001";
			this.ShopAccount = "2002022509100027149";
			
			storeFile = "E:\\cert/qmpayb2c20090911store";
		} else if ("C2C".equals(version)) {
			this.APIVersion = "001.001.003.001";
		}
	}

	public void initFdays(String version) {
		storeFile = "E:\\cert/fdays09store";
		trustFile = "E:\\cert/ICBC_cooperate_trust";

		this.APIName = "EAPI";// 区别大小写
		this.ShopCode = "2002EC20000484";
		if ("B2B".equals(version)) {
			this.APIVersion = "001.001.001.001";
			this.ShopAccount = "2002020319100105292";
		} else if ("B2C".equals(version)) {
			this.APIVersion = "001.001.002.001";
			this.ShopAccount = "2002022519100035059";
		} else if ("C2C".equals(version)) {
			this.APIVersion = "001.001.003.001";
		}
	}

	/**
	 * @执行查询、解析结果
	 */
	public IcbcOrderResult PostQueryCmd() {
		IcbcOrderResult order = null;
		NameValuePair[] nvp = new NameValuePair[3];
		nvp[0] = new NameValuePair("APIName", this.APIName);
		nvp[1] = new NameValuePair("APIVersion", this.APIVersion);
		nvp[2] = new NameValuePair("MerReqData", this.getStrSrc());

		try {
			Protocol protocol = this.getProtocol();
			String resultEncode = URLUtil.getResponseBodyAsPost(bankHost, port,
					serverPath, nvp, protocol);
			System.out.println("---IcbcOrderTest get message is :"
					+ BankUtil.getURLDecode(resultEncode));
			System.out.println("订单:" + this.orderNum + ",查询结果是:");
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

		// System.out.println("工行订单查询,加载证书:" + storeFile + "--" + trustFile);

		try {
			String pass = "12345678";
			FileInputStream inkey = new FileInputStream(storeFile);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(inkey, pass.toCharArray());
			inkey.close();

			FileInputStream inTru = new FileInputStream(trustFile);
			KeyStore ts = KeyStore.getInstance("JKS");
			ts.load(inTru, pass.toCharArray());

			KeyManagerFactory kmf;
			TrustManagerFactory tmf;

			kmf = KeyManagerFactory.getInstance("SunX509");//IbmX509
			kmf.init(ks, pass.toCharArray());

			tmf = TrustManagerFactory.getInstance("SunX509");//IbmPKIX
			tmf.init(ts);

			MySSLSocketFactory sslFactory = new MySSLSocketFactory(kmf
					.getKeyManagers(), tmf.getTrustManagers());

			Protocol myhttps = new Protocol("https", sslFactory, 443);
			Protocol.registerProtocol("https", myhttps);

			// System.out.println("Protocol.registerProtocol()");

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
	public static IcbcOrderResult getQueryDataResult(String resultEncode)
			throws AppException {
		IcbcOrderResult result = null;
		String queryString = BankUtil.getURLDecode(resultEncode);
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
		boolean flag = false;
		String result = getErrorInfo(resultData);
		System.out.println("---check result---" + result);
		if (result == null) {
			flag = true;
		}
		return flag;
	}

	private static HashMap<String, String> errors = null;

	public String getErrorInfo(String resultData) {
		String errorInfo = null;
		if (errors == null) {
			errors = new HashMap<String, String>();
			errors.put("40972", "API查询的订单不存在");
			errors.put("40973", "API查询过程中系统异常");
			errors.put("40976", "API查询系统异常");
			errors.put("40977", "商户证书信息错");
			errors.put("40978", "解包商户请求数据报错");
			errors.put("40979", "查询的订单不存在");
			errors.put("40980", "API查询过程中系统异常");
			errors.put("40981", "给商户打包返回数据错");
			errors.put("40982", "系统错误");
			errors.put("40983", "查询的订单不唯一");
			errors.put("40987", "请求数据中接口名错误");
			errors.put("40947", "商户代码或者商城账号有误");
			errors.put("40948", "商城状态非法");
			errors.put("40949", "商城类别非法");
			errors.put("40950", "商城应用类别非法");
			errors.put("40951", "商户证书id状态非法");
			errors.put("40952", "商户证书id未绑定");
			errors.put("40953", "商户id权限非法");
			errors.put("40954", "检查商户状态时数据库异常");
		}
		Object obj = IcbcOrderTest.errors.get(resultData);
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

	public String getStoreFile() {
		return storeFile;
	}

	public String getTrustFile() {
		return trustFile;
	}
}