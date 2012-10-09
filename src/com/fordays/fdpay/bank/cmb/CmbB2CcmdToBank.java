package com.fordays.fdpay.bank.cmb;

import java.io.File;
import org.dom4j.Document;
import cmb.MerchantCode;
import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

/**
 * @招商银行网上银行B2C支付订单
 */
public class CmbB2CcmdToBank {
	private String branchid = "";// 地区0571
	private String cono = "";// 商户号002696
	private String date = "";// 日期20070823
	private String billno = "";// 订单号
	private String amount = "";// 金额12.43
	private String merchantPara = "";// 商户参数
	private String merchantUrl = "";// 商户URL
	private String merchantCode = "";// 校验码

	// --
	private String merchantKey = "";// 商户密钥
	private String payerID = "";// 付款方用户标识。用来唯一标识商户的一个用户。长度限制为40字节以内。
	// 并不要求商户提供用户的注册名称，但需要保证一个用户对应一个UserID。
	// 商户可以通过某些转换，把用户名转换为一个UserID。比如商户可以把用户注册的“日期+时分秒毫秒”作为UserID。如果还有重复，可再加上用户的IP。
	// 空白表示匿名用户。
	private String payeeID = "";// 收款方的用户标识。生成规则同上。
	private String clientIP = "";// 商户取得的客户端IP，如果有多个IP用逗号”,”分隔。长度限制为64字节。
	private String goodsType = "54011600";// 商品类型，长度限制为8字节。54011600 网上支付
	private String reserved = "";// 保留，长度限制为1024字节。

	// --------
	private String interfacePath = "";// https://netpay.cmbchina.com/netpayment/basehttp.dll?prepayc2
	private String strSrc = "";
	private String url = "";
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-CMB.xml";
	private static Document configDoc = null;

	public CmbB2CcmdToBank() {
		init();
	}

	public void init() {
		XmlUtil xml = new XmlUtil();

		configDoc = BankUtil.loadXml(configXmlUrl);

		cono = xml.getTextByNode(configDoc, "//BANK/CMB/B2C/cono");
		merchantKey = xml
				.getTextByNode(configDoc, "//BANK/CMB/B2C/merchantKey");
		branchid = xml.getTextByNode(configDoc, "//BANK/CMB/B2C/branchid");
		merchantPara = xml.getTextByNode(configDoc,
				"//BANK/CMB/B2C/merchantPara");
		merchantUrl = xml
				.getTextByNode(configDoc, "//BANK/CMB/B2C/merchantUrl");
		interfacePath = xml.getTextByNode(configDoc,
				"//BANK/CMB/B2C/interfacePath");
	}

	/**
	 * 签名、赋值,获得招商银行B2C订单完整指令
	 */
	public void getCMB_B2C_CMD() {
		getMerchantCode();
	}

	//
	public String getMerchantCode() {
		try {
			merchantCode = MerchantCode.genMerchantCode(this.merchantKey,
					this.date, this.branchid, this.cono, this.billno,
					this.amount, this.merchantPara, this.merchantUrl,
					this.payerID, this.payeeID, this.clientIP, this.goodsType,
					this.reserved);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantCode;
	}

	public static cmb.netpayment.Security getSecurityTool() throws AppException {
		cmb.netpayment.Security securityTool = null;

		try {
			XmlUtil xml = new XmlUtil();
			configDoc = BankUtil.loadXml(configXmlUrl);

			String publicKeyFile = Constant.WEB_INFO_PATH
					+ xml.getTextByNode(configDoc, "//BANK/CMB/B2C/publicKeyFile");
															   
			securityTool = new cmb.netpayment.Security(publicKeyFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return securityTool;
	}

	public String getCMB_B2C_URL() {
		this.url = this.getInterfacePath() + "?" + "branchid=" + this.branchid
				+ "&cono=" + this.getCono() + "&date=" + this.getDate()
				+ "&billno=" + this.billno + "&amount=" + this.amount
				+ "&merchantPara=" + this.merchantPara + "&merchantUrl="
				+ this.merchantUrl + "&merchantCode=" + this.merchantCode;
		return url;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getCono() {
		return cono;
	}

	public void setCono(String cono) {
		this.cono = cono;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMerchantPara() {
		return merchantPara;
	}

	public void setMerchantPara(String merchantPara) {
		this.merchantPara = merchantPara;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getPayerID() {
		return payerID;
	}

	public void setPayerID(String payerID) {
		this.payerID = payerID;
	}

	public String getPayeeID() {
		return payeeID;
	}

	public void setPayeeID(String payeeID) {
		this.payeeID = payeeID;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getStrSrc() {
		return strSrc;
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
