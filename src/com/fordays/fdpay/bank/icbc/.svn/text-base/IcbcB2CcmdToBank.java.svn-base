package com.fordays.fdpay.bank.icbc;

import java.io.File;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import cn.com.infosec.icbc.ReturnValue;

/*******************************************************************************
 * @工商银行B2C支付提交表单
 * @接口名称:ICBC_PERBANK_B2C
 * @接口版本：1.0.0.3
 ******************************************************************************/
public class IcbcB2CcmdToBank {
	private String interfaceName = "";// 接口名称
	private String interfaceVersion = "";// 接口版本号
	private String tranData = "";// 交易数据
	private String merSignMsg = "";// 订单签名数据
	private String merCert = ""; // 商城证书公钥
	private String orderDate = ""; // 交易日期时间,签名
	private String orderid = ""; // 订单号
	private String amount = ""; // 订单金额,以分为单位。
	private String curType = ""; // 支付币种
	private String merID = ""; // 商户代码
	private String merAcct = ""; // 商城账号
	private String verifyJoinFlag = "";// 检验联名标志
	private String Language = "";//语言
	private String goodsID = "";// 商品编号
	private String goodsName = ""; // 商品名称
	private String goodsNum = ""; // 商品数量
	private String carriageAmt = "";// 已含运费金额
	private String merHint = ""; // 商城提示
	private String remark1 = ""; // 备注字段1
	private String remark2 = "";// 备注字段2
	private String merURL = "";//商户的URL
	private String merVAR = ""; // 返回商户变量

	private String interfacePath = "";// 提交的路径
	private String strSrc = "";// 待签名的明文
	private String url = "";//
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-ICBC.xml";
	private Document configDoc = null;

	public IcbcB2CcmdToBank() {
		init();
	}

	public void init() {
		configDoc = BankUtil.loadXml(configXmlUrl);

		XmlUtil xml = new XmlUtil();
		interfaceName = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2C/interfaceName");
		interfaceVersion = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2C/interfaceVersion");
		curType = xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/curType");
		verifyJoinFlag = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2C/verifyJoinFlag");
		Language = xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/Language");
		merURL = xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/merURL");
		merID = xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/merID");
		merAcct = xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/merAcct");
		interfacePath = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2C/interfacePath");
	}

	/**
	 * @description: 签名、赋值
	 */
	public void getICBC_B2C_CMD() {
		getStrSrc();
		getTranData();
		getMerSignMsg();
	}

	// 组装交易数据原文
	public String getStrSrc() {
		StringBuffer str = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?>");
		str.append("<B2CReq>");
		str.append("<interfaceName>" + this.interfaceName + "</interfaceName>");
		str.append("<interfaceVersion>" + this.interfaceVersion
				+ "</interfaceVersion>");
		str.append("<orderInfo>");
		str.append("<orderDate>" + this.orderDate + "</orderDate>");
		str.append("<orderid>" + this.orderid + "</orderid>");
		str.append("<amount>" + this.amount + "</amount>");
		str.append("<curType>" + this.curType + "</curType>");
		str.append("<merID>" + this.merID + "</merID>");
		str.append("<merAcct>" + this.merAcct + "</merAcct>");
		str.append("</orderInfo>");
		str.append("<custom>");
		str.append("<verifyJoinFlag>" + this.verifyJoinFlag
				+ "</verifyJoinFlag>");
		str.append("<Language>" + this.Language + "</Language>");
		str.append("</custom>");
		str.append("<message>");
		str.append("<goodsID>" + this.goodsID + "</goodsID>");
		str.append("<goodsName>" + this.goodsName + "</goodsName>");
		str.append("<goodsNum>" + this.goodsNum + "</goodsNum>");
		str.append("<carriageAmt>" + this.carriageAmt + "</carriageAmt>");
		str.append("<merHint>" + this.merHint + "</merHint>");
		str.append("<remark1>" + this.remark1 + "</remark1>");
		str.append("<remark2>" + this.remark2 + "</remark2>");
		str.append("<merURL>" + this.merURL + "</merURL>");
		str.append("<merVAR>" + this.merVAR + "</merVAR>");
		str.append("</message>");
		str.append("</B2CReq>");
		this.strSrc = str.toString().trim();// 明文
		return strSrc;
	}

	// 交易数据Base64编码
	public String getTranData() {
		try {
			this.tranData = new String(ReturnValue.base64enc(strSrc.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tranData;
	}

	// 数据签名
	public String getMerSignMsg() {
		byte[] byteSrc = null;// 明文字节数组
		byteSrc = this.strSrc.getBytes();

		int byteSrcLen = byteSrc.length; // 明文字节数组长度
		byte[] EncSign = null;

		XmlUtil xml = new XmlUtil();
		final String privateKey = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/privateKey");
		final String keyPassword = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2C/keyPassword");

		byte[] priKey = BankUtil.getByteFromFile(privateKey);// 读取私钥
		char[] keyPass = keyPassword.toCharArray();// 私钥保护密码

		try {
			// 私钥对明文签名
			byte[] sign = ReturnValue
					.sign(byteSrc, byteSrcLen, priKey, keyPass);
			if (sign == null) {
				System.out.println("私钥签名失败");
			} else {
				// System.out.println("私钥签名成功");
				EncSign = ReturnValue.base64enc(sign);// 签名BASE64编码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.merSignMsg = new String(EncSign).toString().trim();
		return merSignMsg;
	}

	// 读取公钥
	public String getMerCert() {
		XmlUtil xml = new XmlUtil();
		final String merCertUrl = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/ICBC/B2C/merCert");
		byte[] EncCert = BankUtil.getByteFromFile(merCertUrl);
		merCert = new String(ReturnValue.base64enc(EncCert));
		return merCert;
	}

	public String getICBC_B2C_URL() {
		this.url = this.interfacePath + "?" + "interfaceName="
				+ this.interfaceName + "&interfaceVersion="
				+ this.interfaceVersion + "&tranData=" + this.tranData
				+ "&merSignMsg=" + this.merSignMsg + "&merCert=" + this.merCert;
		return url;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public void setTranData(String tranData) {
		this.tranData = tranData;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	public void setMerCert(String merCert) {
		this.merCert = merCert;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getMerID() {
		return merID;
	}

	public void setMerID(String merID) {
		this.merID = merID;
	}

	public String getMerAcct() {
		return merAcct;
	}

	public void setMerAcct(String merAcct) {
		this.merAcct = merAcct;
	}

	public String getVerifyJoinFlag() {
		return verifyJoinFlag;
	}

	public void setVerifyJoinFlag(String verifyJoinFlag) {
		this.verifyJoinFlag = verifyJoinFlag;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getCarriageAmt() {
		return carriageAmt;
	}

	public void setCarriageAmt(String carriageAmt) {
		this.carriageAmt = carriageAmt;
	}

	public String getMerHint() {
		return merHint;
	}

	public void setMerHint(String merHint) {
		this.merHint = merHint;
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

	public String getMerURL() {
		return merURL;
	}

	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}

	public String getMerVAR() {
		return merVAR;
	}

	public void setMerVAR(String merVAR) {
		this.merVAR = merVAR;
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

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}

	public static String getConfigXmlUrl() {
		return configXmlUrl;
	}

	public static void setConfigXmlUrl(String configXmlUrl) {
		IcbcB2CcmdToBank.configXmlUrl = configXmlUrl;
	}
}
