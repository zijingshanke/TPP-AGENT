package com.fordays.fdpay.bank.cmbc;

import java.io.File;
import org.dom4j.Document;
import Union.JnkyServer;
import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

/**
 * @民生银行网上银行B2C支付订单
 */
public class CmbcB2CcmdToBank {
	private String billNo = "";// 订单号 CHAR(20) 商户号（5位）＋商户自定义序号（15位）
	private String txAmt = "";// 交易金额 DECIMAL(13,2) Not Null 例如：111111.23
	private String PayerCurr = "";// 币种 CHAR(2) Not Null 01：人民币
	private String txDate = "";// 交易日期 CHAR(8) Not Null 格式：20021010
	private String txTime = "";// 交易时间 CHAR(6) Not Null
	private String corpID = "";// 商户代码 CHAR(5) Not Null 由民生银行统一分配
	private String corpName = "";// 商户名称 CHAR(62) Not Null
	private String Billremark1 = "";// 备注1 VarChar(254)
	private String Billremark2 = "";// 备注2 VarChar(254)
	private String CorpRetType = "";// 是否实时返回标志 CHAR(1) 0：即时返回 1：查询
	private String retUrl = "";// 处理结果返回的URL VarCHAR(254) Not Null
	private String langMAC = "";// MAC VarCHAR(200) 因采用了证书机制，此项可不用
	// --------
	private String merSignMsg = "";// 签名数据
	private String interfacePath = "";
	private String strSrc = "";
	private String url = "";
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-CMBC.xml";
	private static Document configDoc = null;

	public CmbcB2CcmdToBank() {
		init();
	}

	public CmbcB2CcmdToBank(String type) {
		if ("ChinaPay".equals(type)) {
			initChinaPay();
		}
	}

	public void init() {
		XmlUtil xml = new XmlUtil();

		configDoc = BankUtil.loadXml(configXmlUrl);

		corpID = xml.getTextByNode(configDoc, "//BANK/CMBC/B2C/corpID");
		corpName = xml.getTextByNode(configDoc, "//BANK/CMBC/B2C/corpName");
		PayerCurr = xml.getTextByNode(configDoc, "//BANK/CMBC/B2C/PayerCurr");
		CorpRetType = xml.getTextByNode(configDoc,
				"//BANK/CMBC/B2C/CorpRetType");
		retUrl = xml.getTextByNode(configDoc, "//BANK/CMBC/B2C/retUrl");
		interfacePath = xml.getTextByNode(configDoc,
				"//BANK/CMBC/B2C/interfacePath");
	}

	public void initChinaPay() {
		XmlUtil xml = new XmlUtil();
		configDoc = BankUtil.loadXml(configXmlUrl);

		corpID = xml.getTextByNode(configDoc, "//BANK/CMBC/ChinaPay/corpID");
		corpName = xml
				.getTextByNode(configDoc, "//BANK/CMBC/ChinaPay/corpName");
		PayerCurr = xml.getTextByNode(configDoc,
				"//BANK/CMBC/ChinaPay/PayerCurr");
		CorpRetType = xml.getTextByNode(configDoc,
				"//BANK/CMBC/ChinaPay/CorpRetType");
		retUrl = xml.getTextByNode(configDoc, "//BANK/CMBC/ChinaPay/retUrl");
		interfacePath = xml.getTextByNode(configDoc,
				"//BANK/CMBC/ChinaPay/interfacePath");
	}

	/**
	 * 签名、赋值,获得民生银行B2C订单完整指令
	 */
	public void getCMBC_B2C_CMD() {
		getStrSrc();
		getMerSignMsg();
	}

	// 加密原文
	public String getStrSrc() {
		this.strSrc = this.billNo + "|" + this.txAmt + "|" + this.PayerCurr
				+ "|" + this.txDate + "|" + this.txTime + "|" + this.corpID
				+ "|" + this.corpName + "|" + this.Billremark1 + "|"
				+ this.Billremark2 + "|" + this.CorpRetType + "|" + this.retUrl
				+ "|" + this.langMAC;
		return strSrc;
	}

	// 数据签名
	public String getMerSignMsg() {
		Union.JnkyServer jnkyServer = null;
		try {
			jnkyServer = getJnkyServer();
			merSignMsg = jnkyServer.EnvelopData(strSrc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merSignMsg;
	}

	public String getCMBC_B2C_URL() {
		this.url = this.getInterfacePath() + "?" + "billNo=" + this.getBillNo()
				+ "&txAmt=" + this.getTxAmt() + "&PayerCurr="
				+ this.getPayerCurr() + "&txDate=" + this.txDate + "&txTime="
				+ this.getTxTime() + "&corpID=" + this.corpID + "&corpName="
				+ this.corpName + "&Billremark1=" + this.getBillremark1()
				+ "&Billremark2=" + this.getBillremark2() + "&CorpRetType="
				+ this.getCorpRetType() + "&retUrl=" + this.getRetUrl()
				+ "&langMAC=" + this.getLangMAC() + "&orderinfo="
				+ this.getMerSignMsg();
		return url;
	}

	/**
	 * 初始化民生银行加密解密控件
	 * 
	 * @return JnkyServer
	 */
	public static JnkyServer getJnkyServer() throws AppException {
		Union.JnkyServer jnkyServer = null;
		byte[] byteMerPfx = null;
		byte[] byteMerCer = null;

		XmlUtil xml = new XmlUtil();
		configDoc = BankUtil.loadXml(configXmlUrl);

		final String keyPassword = xml.getTextByNode(configDoc,
				"//BANK/CMBC/B2C/keyPassword");// 商户私钥的口令
		final String merCerFile = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/CMBC/B2C/merCerFile");
		final String merPfxFile = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/CMBC/B2C/merPfxFile");
		// System.out.println("merCerFile:" + merCerFile);
		// System.out.println("merPfxFile:" + merPfxFile);

		byteMerCer = BankUtil.readFileAsByteArray(merCerFile).toByteArray();
		byteMerPfx = BankUtil.readFileAsByteArray(merPfxFile).toByteArray();

		try {
			jnkyServer = new Union.JnkyServer(byteMerCer, byteMerPfx,
					keyPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jnkyServer;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public String getPayerCurr() {
		return PayerCurr;
	}

	public void setPayerCurr(String payerCurr) {
		PayerCurr = payerCurr;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public String getCorpID() {
		return corpID;
	}

	public void setCorpID(String corpID) {
		this.corpID = corpID;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getBillremark1() {
		return Billremark1;
	}

	public void setBillremark1(String billremark1) {
		Billremark1 = billremark1;
	}

	public String getBillremark2() {
		return Billremark2;
	}

	public void setBillremark2(String billremark2) {
		Billremark2 = billremark2;
	}

	public String getCorpRetType() {
		return CorpRetType;
	}

	public void setCorpRetType(String corpRetType) {
		CorpRetType = corpRetType;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getLangMAC() {
		return langMAC;
	}

	public void setLangMAC(String langMAC) {
		this.langMAC = langMAC;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	public String getInterfacePath() {
		return interfacePath;
	}

	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	@SuppressWarnings("static-access")
	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}
}
