package com.fordays.fdpay.bank.citic;

import java.io.File;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ccb.biz.CcbBankBizImp;
import com.lsy.baselib.crypto.exception.ECCryptoProcessorException;
import com.lsy.baselib.crypto.processor.ECCryptoProcessor;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;

/**
 * @description:中信银行网上银行B2C支付平台第三方商户订单
 * @接口名称:
 * @接口版本：
 */
public class CiticB2CcmdToBank {
	private String E3RDPAYNO = "";// 第三方支付平台编号
	private String ORDERMODE = "";// 订单支付模式
	private String ORDERDATE = "";// 订单日期 yyyy-mm-dd
	private String ORDERTIME = "";// 订单时间 hh:mm:ss
	private String ORDERNO = "";// 订单号
	private String CURRID = "";// 订单支付币种
	private String ORDERAMT = "";// 订单金额
	private String MEMO = "";// 摘要
	private String NOTIFYMODE = "";// 通知模式01
	private String NOTIFYURL = "";// 商户通知
	private String RISKLEVEL = "";// 风险级别
	private String SUPPTCARDTYPE = "";// 支持卡种
	private String TTL = "";// 订单有效期
	private String MEMBERID = "";// 订单人,可以是用户在商户网站的USERID
	private String NOTIFYSCOPE = "";// 支付结果通知范围
	// ----
	private String SIGNREQMSG = "";//
	// ----
	private String interfacePath = "";// 提交的路径
	private String strSrc = "";// 待签名的明文
	private String url = "";//
	// ---
	private static Document configDoc = null;
	private LogUtil myLog ;

	public CiticB2CcmdToBank() {
		init();
	}

	public void init() {
		XmlUtil xml = new XmlUtil();
		String fileName = Constant.WEB_INFO_PATH + "bankkey" + File.separator
				+ "init" + File.separator + "bankInterfaceConfig-CITIC.xml";

		configDoc = BankUtil.loadXml(fileName);

		this.E3RDPAYNO = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/E3RDPAYNO");
		this.ORDERMODE = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/ORDERMODE");
		this.CURRID = xml.getTextByNode(configDoc, "//BANK/CITIC/B2C/CURRID");
		this.MEMO = xml.getTextByNode(configDoc, "//BANK/CITIC/B2C/MEMO");
		this.NOTIFYMODE = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/NOTIFYMODE");
		this.NOTIFYURL = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/NOTIFYURL");
		this.RISKLEVEL = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/RISKLEVEL");
		this.SUPPTCARDTYPE = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/SUPPTCARDTYPE");
		this.interfacePath = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/interfacePath");
	}

	// 获取签名数据项
	public String getSIGNREQMSG() {
		byte[] signedMessage = null;
		try {
			ECCryptoProcessor processor = getECCryptoProcessor_Sign();
			signedMessage = processor.sign(getStrSrc().getBytes());
		} catch (ECCryptoProcessorException e) {
			e.printStackTrace();
		}
		this.SIGNREQMSG = new String(signedMessage);
		
		myLog= new LogUtil(false, false, CcbBankBizImp.class);
		myLog.info("提交中信银行B2C订单签名项SIGNREQMSG:\n" + SIGNREQMSG);
		
		return SIGNREQMSG;
	}

	/**
	 * @初始化签名组件
	 */
	public static ECCryptoProcessor getECCryptoProcessor_Sign() {
		ECCryptoProcessor processor = new ECCryptoProcessor();

		XmlUtil xml = new XmlUtil();
		String signCertUrl = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/CITIC/B2C/signCert");
		String privateKeyUrl = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/CITIC/B2C/privateKey");
		String keyPasswordUrl = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/CITIC/B2C/keyPassword");

		byte[] byteSignercrt = BankUtil.readFileAsByteArray(signCertUrl)
				.toByteArray();
		byte[] byteSignerkey = BankUtil.readFileAsByteArray(privateKeyUrl)
				.toByteArray();
		String byteKeypasswd = new String(BankUtil.readFileAsByteArray(
				keyPasswordUrl).toByteArray());

		try {
			processor.setSignerCertificate(byteSignercrt);
			processor.setSignerPrivatekey(byteSignerkey, byteKeypasswd);
		} catch (ECCryptoProcessorException e) {
			e.printStackTrace();
		}
		return processor;
	}

	/**
	 * @初始化验证签名组件
	 */
	public static ECCryptoProcessor getECCryptoProcessor_VerifySign() {
		ECCryptoProcessor processor = new ECCryptoProcessor();

		XmlUtil xml = new XmlUtil();
		String trustedCrt = xml.getTextByNode(configDoc,
				"//BANK/CITIC/B2C/trustedCrt");
		/**
		 * 添加信任证书
		 */
		try {
			processor.addTrustedCertificate(trustedCrt.getBytes());
			// 验签对象的声明和信任证书的设置需要耗费一定的时间，建议对上述操作进行全局设置。
		} catch (ECCryptoProcessorException e) {
			e.printStackTrace();
		}
		return processor;
	}

	public String getStrSrc() {
		StringBuffer str = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GBK\" ?>");
		str.append("<stream>");
		str.append("<E3RDPAYNO>" + this.E3RDPAYNO + "</E3RDPAYNO>");
		str.append("<ORDERMODE>" + this.ORDERMODE + "</ORDERMODE>");
		str.append("<ORDERDATE>" + this.ORDERDATE + "</ORDERDATE>");
		str.append("<ORDERTIME>" + this.ORDERTIME + "</ORDERTIME>");
		str.append("<ORDERNO>" + this.ORDERNO + "</ORDERNO>");
		str.append("<CURRID>" + this.CURRID + "</CURRID>");
		str.append("<ORDERAMT>" + this.ORDERAMT + "</ORDERAMT>");
		str.append("<MEMO>" + this.MEMO + "</MEMO>");
		str.append("<NOTIFYMODE>" + this.NOTIFYMODE + "</NOTIFYMODE>");
		str.append("<NOTIFYURL>" + this.NOTIFYURL + "</NOTIFYURL>");
		str.append("<RISKLEVEL>" + this.RISKLEVEL + "</RISKLEVEL>");
		str.append("<SUPPTCARDTYPE>" + this.SUPPTCARDTYPE + "</SUPPTCARDTYPE>");
		str.append("<TTL>" + this.TTL + "</TTL>");
		str.append("<MEMBERID>" + this.MEMBERID + "</MEMBERID>");
		str.append("<NOTIFYSCOPE>" + this.NOTIFYSCOPE + "</NOTIFYSCOPE>");
		str.append("</stream>");
		this.strSrc = str.toString().trim();
		return strSrc;
	}

	public String getCITIC_B2C_URL() {
		this.url = this.interfacePath + "?" + this.getStrSrc();
		return url;
	}

	public String getE3RDPAYNO() {
		return E3RDPAYNO;
	}

	public void setE3RDPAYNO(String e3rdpayno) {
		E3RDPAYNO = e3rdpayno;
	}

	public String getORDERMODE() {
		return ORDERMODE;
	}

	public void setORDERMODE(String ordermode) {
		ORDERMODE = ordermode;
	}

	public String getORDERDATE() {
		return ORDERDATE;
	}

	public void setORDERDATE(String orderdate) {
		ORDERDATE = orderdate;
	}

	public String getORDERTIME() {
		return ORDERTIME;
	}

	public void setORDERTIME(String ordertime) {
		ORDERTIME = ordertime;
	}

	public String getORDERNO() {
		return ORDERNO;
	}

	public void setORDERNO(String orderno) {
		ORDERNO = orderno;
	}

	public String getCURRID() {
		return CURRID;
	}

	public void setCURRID(String currid) {
		CURRID = currid;
	}

	public String getORDERAMT() {
		return ORDERAMT;
	}

	public void setORDERAMT(String orderamt) {
		ORDERAMT = orderamt;
	}

	public String getMEMO() {
		return MEMO;
	}

	public void setMEMO(String memo) {
		MEMO = memo;
	}

	public String getNOTIFYMODE() {
		return NOTIFYMODE;
	}

	public void setNOTIFYMODE(String notifymode) {
		NOTIFYMODE = notifymode;
	}

	public String getNOTIFYURL() {
		return NOTIFYURL;
	}

	public void setNOTIFYURL(String notifyurl) {
		NOTIFYURL = notifyurl;
	}

	public String getRISKLEVEL() {
		return RISKLEVEL;
	}

	public void setRISKLEVEL(String risklevel) {
		RISKLEVEL = risklevel;
	}

	public String getSUPPTCARDTYPE() {
		return SUPPTCARDTYPE;
	}

	public void setSUPPTCARDTYPE(String supptcardtype) {
		SUPPTCARDTYPE = supptcardtype;
	}

	public String getTTL() {
		return TTL;
	}

	public void setTTL(String ttl) {
		TTL = ttl;
	}

	public String getMEMBERID() {
		return MEMBERID;
	}

	public void setMEMBERID(String memberid) {
		MEMBERID = memberid;
	}

	public String getNOTIFYSCOPE() {
		return NOTIFYSCOPE;
	}

	public void setNOTIFYSCOPE(String notifyscope) {
		NOTIFYSCOPE = notifyscope;
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

	public void setSIGNREQMSG(String signreqmsg) {
		SIGNREQMSG = signreqmsg;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	@SuppressWarnings("static-access")
	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}
}