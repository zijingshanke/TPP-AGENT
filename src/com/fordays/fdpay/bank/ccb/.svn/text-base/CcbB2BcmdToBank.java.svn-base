package com.fordays.fdpay.bank.ccb;

import java.io.File;

import org.dom4j.Document;

import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;
import com.neza.encrypt.MD5;
import com.neza.encrypt.XmlUtil;

/*******************************************************************************
 * @建设银行网上银行B2B支付订单
 * @接口名称:
 * @接口版本：5.20
 ******************************************************************************/
public class CcbB2BcmdToBank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantid = ""; // 商户代码(客户号) CHAR(21) 由建行统一分配
	private String posid = ""; // 商户柜台代码 CHAR(9) 由建行统一分配，缺省为000000000
	private String branchid = ""; // 分行代码 CHAR(9) 由建行统一指定
	private String orderid = ""; // 定单号 CHAR(30) 由商户提供，最长30位,按实际长度给出
	private String payment = ""; // 付款金额 NUMBER(16,2) 由商户提供，按实际金额给出
	private String curcode = ""; // 币种 CHAR(2) 缺省为01－人民币
	private String remark1 = ""; // 备注1 CHAR(30) 网银不处理，直接传到城综网
	private String remark2 = ""; // 备注2 CHAR(30) 网银不处理，直接传到城综网
	private String txcode = ""; // 交易码 CHAR(6) 690401
	private String mac = ""; // MAC校验域 CHAR(32) 采用标准MD5算法，由商户实现

	private String interfacePath = "";
	private String strSrc = "";// 待签名的明文
	private String url = "";
	private Document configDoc = null;

	public CcbB2BcmdToBank() {
		init();
	}

	private void init() {
		String fileName = Constant.WEB_INFO_PATH + "bankkey" + File.separator
				+ "init" + File.separator + "bankInterfaceConfig-CCB.xml";
		configDoc = BankUtil.loadXml(fileName);
		XmlUtil xml = new XmlUtil();

		this.merchantid = xml.getTextByNode(configDoc,
				"//BANK/CCB/B2B/merchantid");
		this.posid = xml.getTextByNode(configDoc, "//BANK/CCB/B2B/posid");
		this.branchid = xml.getTextByNode(configDoc, "//BANK/CCB/B2B/branchid");
		this.curcode = xml.getTextByNode(configDoc, "//BANK/CCB/B2B/curcode");
		this.txcode = xml.getTextByNode(configDoc, "//BANK/CCB/B2B/txcode");
		this.interfacePath = xml.getTextByNode(configDoc,
				"//BANK/CCB/B2B/interfacePath");
	}

	public String getCCB_B2B_URL() {
		String orderinfo = this.getStrSrc();
		mac = MD5.encrypt(orderinfo).toLowerCase();
		url = interfacePath+"?" + orderinfo + "&MAC=" + mac;
		return url;
	}

	public String getStrSrc() {
		// 字符串中变量名必须是大写字母
		strSrc = "MERCHANTID=" + this.merchantid + "&POSID=" + this.posid
				+ "&BRANCHID=" + this.branchid + "&ORDERID=" + this.orderid
				+ "&PAYMENT=" + this.payment + "&CURCODE=" + this.curcode
				+ "&TXCODE=" + this.txcode + "&REMARK1=" + this.remark1
				+ "&REMARK2=" + this.remark2;
		return strSrc;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getCurcode() {
		return curcode;
	}

	public void setCurcode(String curcode) {
		this.curcode = curcode;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getTxcode() {
		return txcode;
	}

	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getInterfacePath() {
		return interfacePath;
	}

	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
	}
}
