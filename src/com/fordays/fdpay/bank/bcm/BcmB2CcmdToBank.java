package com.fordays.fdpay.bank.bcm;

import java.io.File;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;

/**
 * 交通银行网上银行B2C支付订单 接口版本：1.0.0.0
 */
public class BcmB2CcmdToBank {
	// --------interface form
	private String interfaceVersion = "";// 消息版本号
	private String merID = "";// 商户客户号
	private String orderid = "";// 订单号 20 商户应保证定单号+定单日期的 唯一性
	private String orderDate = "";// 商户订单日期 Y 8 YyyyMMdd
	private String orderTime = "";// 商户订单时间 N 8 Hhmmss
	private String tranType = "";// 交易类别
	private String amount = "";// 订单金额 Y 17 单位:元并带两位小数 1位整数+2 位小数
	private String curType = "";// 订单币种
	private String orderContent = "";// 订单内容 商家填写的其他订单信息，在个人客户页面显示
	private String orderMono = "";// 商家备注 不在个人客户页面显示的备注，可在商户管理页面上显示
	private String phdFlag = "";// 物流配送标志
	private String notifyType = "";// 通知方式
	private String merURL = "";// 主动通知URL
	private String goodsURL = "";// 取货URL
	private String jumpSeconds = "";// 自动跳转时间
	private String payBatchNo = "";// 商户批次号
	private String proxyMerName = "";// 代理商家名称
	private String proxyMerType = "";// 代理商家证件类型
	private String proxyMerCredentials = "";// 代理商家证件号码
	private String netType = "";// 渠道编号
	private String merSignMsg = "";// 商家签名 Y 2000 商家签名 detech 方式签名
	// -----------------
	private String strSrc = "";// 订单明文
	private String interfacePath = "";
	private String url = "";
	private Document configDoc = null;
	// -----------------
	private LogUtil myLog;

	// ------持卡人身份验证
	// BOCOMB2COPReply verifyCustID (
	private String cardNo = ""; // 银行卡号
	private String custName = "";// 客户名称
	private String certType = ""; // 证件类型,可以为空
	private String certNo = ""; // 证件号,可以为空

	public void getVerifyCustCmd() {
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = getBOCOMB2CClient();
		System.out.println("after get Client");

		com.bocom.netpay.b2cAPI.BOCOMB2COPReply rep = client.verifyCustID(
				this.cardNo, this.custName, this.certType, this.certNo);

		String err;
		String code;
		String msg;
		if (rep == null) {
			err = client.getLastErr();
			System.out.print("交易错误信息：" + err + "<br>");
		} else {
			code = rep.getRetCode(); // 得到交易返回码
			err = rep.getLastErr();
			msg = rep.getErrorMessage();
			System.out.print("交易返回码：" + code + "<br>");
			System.out.print("交易错误信息：" + msg + "<br>");
		}
	}

	// 其中，证件类型有：
	// 代码 名称
	// 15 居民身份证
	// 16 临时身份证
	// 17 军人身份证件
	// 18 武警身份证件
	// 19 港澳台居民通行证
	// 20 护照
	// 21 其他
	// 22 临时户口
	// 23 户口簿
	// 24 边境证

	public BcmB2CcmdToBank() {
		init();
	}

	/**
	 * 交通银行B2C接口 初始化指令对象
	 */
	public com.bocom.netpay.b2cAPI.BOCOMB2CClient getBOCOMB2CClient() {
		myLog = new LogUtil(false, false, BcmB2CcmdToBank.class);
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = new com.bocom.netpay.b2cAPI.BOCOMB2CClient();
		final String fileName = Constant.WEB_INFO_PATH + File.separator
				+ "bankkey" + File.separator + "init" + File.separator
				+ "bankInterfaceConfig-BCM.xml";
		configDoc = BankUtil.loadXml(fileName);

		myLog.info("交通银行接口配置文件,path:" + fileName);

		int ret = client.initialize(fileName);
		if (ret != 0) {
			myLog.error("初始化失败,错误信息：" + client.getLastErr());
		}
		return client;
	}

	public void init() {
		@SuppressWarnings("unused")
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = getBOCOMB2CClient();// 初始化指令对象
		merID = com.bocom.netpay.b2cAPI.BOCOMSetting.MerchantID;
		interfacePath = com.bocom.netpay.b2cAPI.BOCOMSetting.OrderURL;

		XmlUtil xml = new XmlUtil();
		interfaceVersion = xml.getTextByNode(configDoc,
				"//BOCOMB2C/interfaceVersion");
		tranType = xml.getTextByNode(configDoc, "//BOCOMB2C/tranType");
		curType = xml.getTextByNode(configDoc, "//BOCOMB2C/curType");
		phdFlag = xml.getTextByNode(configDoc, "//BOCOMB2C/phdFlag");
		netType = xml.getTextByNode(configDoc, "//BOCOMB2C/netType");
		notifyType = xml.getTextByNode(configDoc, "//BOCOMB2C/notifyType");
		merURL = xml.getTextByNode(configDoc, "//BOCOMB2C/merURL");
		goodsURL = xml.getTextByNode(configDoc, "//BOCOMB2C/goodsURL");
		jumpSeconds = xml.getTextByNode(configDoc, "//BOCOMB2C/jumpSeconds");
		payBatchNo = xml.getTextByNode(configDoc, "//BOCOMB2C/payBatchNo");
		proxyMerName = xml.getTextByNode(configDoc, "//BOCOMB2C/proxyMerName");
		proxyMerType = xml.getTextByNode(configDoc, "//BOCOMB2C/proxyMerType");
		proxyMerCredentials = xml.getTextByNode(configDoc,
				"//BOCOMB2C/proxyMerCredentials");
	}

	/**
	 * 获取B2C支付请求订单
	 */
	public void getBCM_B2C_CMD() {
		getStrSrc();// 组装订单明文
		getMerSignMsg();// 签名
	}

	/**
	 * 组装B2C订单明文
	 */
	public String getStrSrc() {
		String sourceMsg = this.interfaceVersion + "|" + this.merID + "|"
				+ this.orderid + "|" + this.orderDate + "|" + this.orderTime
				+ "|" + this.tranType + "|" + this.amount + "|" + this.curType
				+ "|" + this.orderContent + "|" + this.orderMono + "|"
				+ this.phdFlag + "|" + this.notifyType + "|" + this.merURL
				+ "|" + this.goodsURL + "|" + this.jumpSeconds + "|"
				+ this.payBatchNo + "|" + this.proxyMerName + "|"
				+ this.proxyMerType + "|" + this.proxyMerCredentials + "|"
				+ this.netType;
		this.strSrc = sourceMsg;

		myLog = new LogUtil(false, false, BcmB2CcmdToBank.class);
		myLog.info("B2C订单明文:" + strSrc);
		return strSrc;
	}

	public String getMerSignMsg() {
		myLog = new LogUtil(false, false, BcmB2CcmdToBank.class);

		// 初始化签名库
		com.bocom.netpay.b2cAPI.NetSignServer nss = new com.bocom.netpay.b2cAPI.NetSignServer();
		String merchantDN = com.bocom.netpay.b2cAPI.BOCOMSetting.MerchantCertDN;
		byte[] bSignMsg = null;
		try {
			nss.NSSetPlainText(strSrc.getBytes());
			bSignMsg = nss.NSDetachedSign(merchantDN); // 订单签名
			if (nss.getLastErrnum() < 0) {
				myLog.error("ERROR:商户端签名失败");
				return "";
			}
			this.merSignMsg = new String(bSignMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merSignMsg;
	}

	public String getBCM_B2C_URL() {
		this.url = this.interfacePath + "?interfaceVersion="
				+ this.interfaceVersion + "&merID=" + this.getMerID()
				+ "&orderid=" + this.orderid + "&orderDate=" + this.orderDate
				+ "&orderTime=" + this.orderTime + "&tranType=" + this.tranType
				+ "&amount=" + this.amount + "&curType=" + this.curType
				+ "&orderContent=" + this.orderContent + "&orderMono="
				+ this.orderMono + "&phdFlag=" + this.phdFlag + "&notifyType="
				+ this.notifyType + "&merURL=" + this.merURL + "&goodsURL="
				+ this.goodsURL + "&jumpSeconds=" + this.jumpSeconds
				+ "&payBatchNo=" + this.payBatchNo + "&proxyMerName="
				+ this.proxyMerName + "&proxyMerType=" + this.proxyMerType
				+ "&proxyMerCredentials=" + this.proxyMerCredentials
				+ "&netType=" + this.netType + "&merSignMsg=" + this.merSignMsg;
		return url;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getMerID() {
		return merID;
	}

	public void setMerID(String merID) {
		this.merID = merID;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
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

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public String getOrderMono() {
		return orderMono;
	}

	public void setOrderMono(String orderMono) {
		this.orderMono = orderMono;
	}

	public String getPhdFlag() {
		return phdFlag;
	}

	public void setPhdFlag(String phdFlag) {
		this.phdFlag = phdFlag;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getMerURL() {
		return merURL;
	}

	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}

	public String getGoodsURL() {
		return goodsURL;
	}

	public void setGoodsURL(String goodsURL) {
		this.goodsURL = goodsURL;
	}

	public String getJumpSeconds() {
		return jumpSeconds;
	}

	public void setJumpSeconds(String jumpSeconds) {
		this.jumpSeconds = jumpSeconds;
	}

	public String getPayBatchNo() {
		return payBatchNo;
	}

	public void setPayBatchNo(String payBatchNo) {
		this.payBatchNo = payBatchNo;
	}

	public String getProxyMerName() {
		return proxyMerName;
	}

	public void setProxyMerName(String proxyMerName) {
		this.proxyMerName = proxyMerName;
	}

	public String getProxyMerType() {
		return proxyMerType;
	}

	public void setProxyMerType(String proxyMerType) {
		this.proxyMerType = proxyMerType;
	}

	public String getProxyMerCredentials() {
		return proxyMerCredentials;
	}

	public void setProxyMerCredentials(String proxyMerCredentials) {
		this.proxyMerCredentials = proxyMerCredentials;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
	}

	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}

	public String getInterfacePath() {
		return interfacePath;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}
}