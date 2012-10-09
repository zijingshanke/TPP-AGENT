package com.fordays.fdpay.bank.icbc;

import java.io.File;

import org.dom4j.Document;

import cn.com.infosec.icbc.ReturnValue;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.base.Bank;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;

public class IcbcB2BCmdToBank extends Bank {
	private String APIName; // 接口名称
	private String APIVersion; // 接口版本号
	private String Shop_code; // 商户代码
	private String MerchantURL; // 支付结果信息通知程序地址
	private String ContractNo; // 订单号
	private String ContractAmt; // 订单金额 MAX(18) 保留到分，无小数点，如金额为1.00元，上传数据为“100”
	private String Account_cur; // 支付币种
	private String JoinFlag; // 检验联名标志
	private String Mer_Icbc20_signstr; // 订单签名数据
	private String Cert; // 商城证书数据
	private String SendType;// 结果发送类型
	private String TranTime;// 交易日期时间=14 必输，签名，YYYYMMDDHHmmss。
	private String Shop_acc_num; // 商城账号
	private String PayeeAcct; // 收款单位账号
	private String GoodsCode; // 商品编号 MAX (30) 选输
	private String GoodsName; // 商品名称 MAX (60) 选输
	private String Amount; // 订单数量 MAX (10) 选输
	private String TransFee; // 已含运费金额 MAX (18) 选输
	private String ShopRemark; // 商城提示 MAX (120) 选输，最多120字符
	private String ShopRem; // 商城备注字段 MAX(100) 选输，最多100字符
	//-----------
	private String interfacePath = "";// 提交的路径
	private String strSrc = "";// 待签名的明文
	private String url = "";//
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-ICBC.xml";
	private Document configDoc = null;
	//---------
	private LogUtil myLog;
	
	public IcbcB2BCmdToBank() {
		init();
	}

	public void getICBC_B2B_CMD() {
		getStrSrc();
		getCert();
		getMer_Icbc20_signstr();
	}

	public String getMer_Icbc20_signstr() {
		myLog=new LogUtil(false,false,this.getClass());
		
		byte[] EncSign = null;// 签名/编码后的 签名

		byte[] byteSrc = this.strSrc.getBytes(); // 明文字节数组
		int byteSrcLen = byteSrc.length; // 明文字节数组长度

		XmlUtil xml = new XmlUtil();
		String PriKeyUrl = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/privateKey");

		byte[] priKey = BankUtil.getByteFromFile(PriKeyUrl);// 读取私钥
		char[] keyPass = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2B/keyPassWord").toCharArray();// 私钥保护密码

		try {
			// 私钥对明文签名
			byte[] sign = ReturnValue
					.sign(byteSrc, byteSrcLen, priKey, keyPass);
			if (sign == null) {
				myLog.error("ICBC B2B 私钥签名失败");
			} else {
				myLog.info("ICBC B2B私钥签名成功");
				EncSign = ReturnValue.base64enc(sign);// 签名BASE64编码

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.Mer_Icbc20_signstr = new String(EncSign).toString().trim();
		return Mer_Icbc20_signstr;
	}

	public void init() {
		configDoc = BankUtil.loadXml(configXmlUrl);
		XmlUtil xml = new XmlUtil();

		APIName = xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/APIName");
		APIVersion = xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/APIVersion");
		Shop_code = xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/Shop_code");
		MerchantURL = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2B/MerchantURL");
		Account_cur = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2B/Account_cur");
		JoinFlag = xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/JoinFlag");
		SendType = xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/SendType");
		Shop_acc_num = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2B/Shop_acc_num");
		PayeeAcct = xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/PayeeAcct");
		interfacePath = xml.getTextByNode(configDoc,
				"//BANK/ICBC/B2B/interfacePath");
	}

	public String getStrSrc() {
		// 拼凑待签名明文
		this.strSrc = "APIName=" + this.APIName + "&APIVersion="
				+ this.APIVersion + "&Shop_code=" + this.Shop_code
				+ "&MerchantURL=" + this.MerchantURL + "&ContractNo="
				+ this.ContractNo + "&ContractAmt=" + this.ContractAmt
				+ "&Account_cur=" + this.Account_cur + "&JoinFlag="
				+ this.JoinFlag + "&SendType=" + this.SendType + "&TranTime="
				+ this.TranTime + "&Shop_acc_num=" + this.Shop_acc_num
				+ "&PayeeAcct=" + this.PayeeAcct;
		return strSrc;
	}

	//
	public String getCert() {
		byte[] EncCert = null;

		XmlUtil xml = new XmlUtil();
		String PubKeyUrl = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configDoc, "//BANK/ICBC/B2B/merCert");// 商户公钥

		byte[] byteCert = BankUtil.getByteFromFile(PubKeyUrl);// 读取证书公钥
		EncCert = ReturnValue.base64enc(byteCert);// 公钥BASE64编码

		this.Cert = new String(EncCert).toString().trim();
		return Cert;
	}

	public String getICBC_B2B_URL() {
		this.url = this.interfacePath + "?" + "APIName=" + this.APIName
				+ "&APIVersion=" + this.APIVersion + "&Shop_code="
				+ this.getShop_code() + "&MerchantURL=" + this.MerchantURL
				+ "&ContractNo=" + this.getContractNo() + "&ContractAmt="
				+ this.getContractAmt() + "&Account_cur="
				+ this.getAccount_cur() + "&JoinFlag=" + this.getJoinFlag()
				+ "&Mer_Icbc20_signstr=" + this.getMer_Icbc20_signstr()
				+ "&Cert=" + this.getCert() + "&SendType=" + this.getSendType()
				+ "&TranTime=" + this.getTranTime() + "&Shop_acc_num="
				+ this.getShop_acc_num() + "&PayeeAcct=" + this.getPayeeAcct()
				+ "&GoodsCode=" + this.getGoodsCode() + "&GoodsName="
				+ this.getGoodsName() + "&Amount=" + this.getAmount()
				+ "&TransFee=" + this.getTransFee() + "&ShopRemark="
				+ this.getShopRemark() + "&ShopRem=" + this.getShopRem();
		return url;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
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

	public String getShop_code() {
		return Shop_code;
	}

	public void setShop_code(String shop_code) {
		Shop_code = shop_code;
	}

	public String getMerchantURL() {
		return MerchantURL;
	}

	public void setMerchantURL(String merchantURL) {
		MerchantURL = merchantURL;
	}

	public String getContractNo() {
		return ContractNo;
	}

	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}

	public String getContractAmt() {
		return ContractAmt;
	}

	public void setContractAmt(String contractAmt) {
		ContractAmt = contractAmt;
	}

	public String getAccount_cur() {
		return Account_cur;
	}

	public void setAccount_cur(String account_cur) {
		Account_cur = account_cur;
	}

	public String getJoinFlag() {
		return JoinFlag;
	}

	public void setJoinFlag(String joinFlag) {
		JoinFlag = joinFlag;
	}

	public void setMer_Icbc20_signstr(String mer_Icbc20_signstr) {
		Mer_Icbc20_signstr = mer_Icbc20_signstr;
	}

	public void setCert(String cert) {
		Cert = cert;
	}

	public String getSendType() {
		return SendType;
	}

	public void setSendType(String sendType) {
		SendType = sendType;
	}

	public String getTranTime() {
		return TranTime;
	}

	public void setTranTime(String tranTime) {
		TranTime = tranTime;
	}

	public String getShop_acc_num() {
		return Shop_acc_num;
	}

	public void setShop_acc_num(String shop_acc_num) {
		Shop_acc_num = shop_acc_num;
	}

	public String getPayeeAcct() {
		return PayeeAcct;
	}

	public void setPayeeAcct(String payeeAcct) {
		PayeeAcct = payeeAcct;
	}

	public String getGoodsCode() {
		return GoodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		GoodsCode = goodsCode;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getTransFee() {
		return TransFee;
	}

	public void setTransFee(String transFee) {
		TransFee = transFee;
	}

	public String getShopRemark() {
		return ShopRemark;
	}

	public void setShopRemark(String shopRemark) {
		ShopRemark = shopRemark;
	}

	public String getShopRem() {
		return ShopRem;
	}

	public void setShopRem(String shopRem) {
		ShopRem = shopRem;
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

	public static String getConfigXmlUrl() {
		return configXmlUrl;
	}

	public static void setConfigXmlUrl(String configXmlUrl) {
		IcbcB2BCmdToBank.configXmlUrl = configXmlUrl;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}
}
