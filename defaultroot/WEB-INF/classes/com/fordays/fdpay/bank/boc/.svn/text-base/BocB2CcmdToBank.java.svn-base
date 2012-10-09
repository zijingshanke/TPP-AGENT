package com.fordays.fdpay.bank.boc;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.pgw.test.security.PKCS7Tool;

/**
 * @中国银行网上银行B2C订单
 */
public class BocB2CcmdToBank {
	private String merchantNo = "";// 商户号
	private String payType = "";// 支付类型 1-网上购物 2-基金直销
	private String orderNo = "";// 商户订单号
	private String curCode = "";// 订单币种 001-人民币
	private String orderAmount = "";// 订单金额
	// 格式：整数位不前补零,小数位补 齐2 位 即：不超过10 位整数位+1 位小 数点+2 位小数
	// 无效格式如 123，.10，1.1,有 效格式如1.00，0.10
	private String orderTime = "";// 订单时间 格式：YYYYMMDD24HHMMSS
	// 其中时间为24 小时格式，如下午3 点15 表示为151500
	private String orderNote = "";// 订单说明
	private String orderUrl = "";// 客户支付完成后能够返回的商户网站URL
	private String signData = "";// 商户签名数据
	// -----------------------
	private String keyStorePath = "";// 证书库路径
	private String keyStorePassword = "";// 证书库口令
	private String keyPassword = "";// 签名私钥口令，一般与证书库口令相同
	// -----------------------
	private String interfacePath = "";// 提交的路径
	private String strSrc = "";// 待签名的明文
	private String url = "";
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-BOC.xml";
	private Document configDoc = null;

	public BocB2CcmdToBank() {
		init();
	}

	public void init() {
		XmlUtil xml = new XmlUtil();
		configDoc = BankUtil.loadXml(configXmlUrl);

		merchantNo = xml.getTextByNode(configDoc, "//BANK/BOC/B2C/merchantNo");
		payType = xml.getTextByNode(configDoc, "//BANK/BOC/B2C/payType");
		curCode = xml.getTextByNode(configDoc, "//BANK/BOC/B2C/curCode");
		orderUrl = xml.getTextByNode(configDoc, "//BANK/BOC/B2C/orderUrl");

		interfacePath = xml.getTextByNode(configDoc,
				"//BANK/BOC/B2C/interfacePath");

		keyStorePath = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/BOC/B2C/keyStorePath");
		keyStorePassword = xml.getTextByNode(configDoc,
				"//BANK/BOC/B2C/keyStorePassword");
		keyPassword = xml
				.getTextByNode(configDoc, "//BANK/BOC/B2C/keyPassword");
	}

	public String getBocB2Ccmd() {
		String src = getStrSrc();
		String url = interfacePath + "?" + src + "&signData=" + getSignData();
		return url;
	}

	public String getStrSrc() {
		this.strSrc = "merchantNo=" + this.merchantNo + "&payType="
				+ this.payType + "&orderNo=" + this.orderNo + "&curCode="
				+ this.curCode + "&orderAmount=" + this.orderAmount
				+ "&orderTime=" + this.orderTime + "&orderNote="
				+ this.orderNote + "&orderUrl=" + this.orderUrl;
		return strSrc;
	}

	/**
	 * 签名
	 */
	public String getSignData() {
		// 签名，返回signature：base64格式的签名结果
		byte[] data = strSrc.getBytes();// 明文字符串
		try {
			signData = getPKCS7Tool().sign(data);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signData;
	}

	/**
	 * 获取签名工具
	 */
	public PKCS7Tool getPKCS7Tool() {
		PKCS7Tool tool = null;
		try {
			tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword,
					keyPassword);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tool;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getOrderUrl() {
		return orderUrl;
	}

	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
	}

	public String getInterfacePath() {
		return interfacePath;
	}

	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static String getConfigXmlUrl() {
		return configXmlUrl;
	}

	public static void setConfigXmlUrl(String configXmlUrl) {
		BocB2CcmdToBank.configXmlUrl = configXmlUrl;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}
}
