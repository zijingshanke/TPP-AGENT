package com.fordays.fdpay.bank.icbc;

import com.fordays.fdpay.bank.ResultFromBank;

/**
 * @description:工商银行订单查询结果
 * @remark:b2c/b2b/c2c通用
 */
public class IcbcOrderResult extends ResultFromBank{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String APIName = "";// 接口名称
	private String APIVersion = "";// 接口版本号
	private String orderNum = "";// 订单号
	private String tranDate = "";// 交易日期
	private String ShopCode = "";// 商家号码
	private String ShopAccount = "";// 商城账号
	private String tranSerialNum = "";// 指令序号
	private String tranStat = "";// 订单处理状态
	private String bankRem = "";// 指令错误信息
	private String amount = "";// 订单总金额
	private String currType = "";// 支付币种
	private String tranTime = "";// 返回通知日期时间
	private String PayeeName = "";// 商城户名
	private String JoinFlag = "";// 校验联名标志
	private String MerJoinFlag = "";// 商城联名标志
	private String CustJoinFlag = "";// 客户联名标志
	private String CustJoinNum = "";// 联名会员号
	private String CertID = "";// 商户签名证书id

	// ----
	private String url = "";
	private long chargeStatus = new Long(0);// 对应Charge订单处理状态

	public String getUrl() {
		this.url = "APIName=" + this.APIName + "&APIVersion=" + this.APIVersion
				+ "&orderNum=" + this.orderNum + "&tranDate=" + this.tranDate
				+ "&ShopCode" + this.ShopCode + "&ShopAccount="
				+ this.ShopAccount + "&tranSerialNum=" + this.tranSerialNum
				+ "&tranStat=" + this.tranStat + "&bankRem=" + this.bankRem
				+ "&amount=" + this.amount + "&currType=" + this.currType
				+ "&tranTime=" + this.tranTime + "&PayeeName=" + this.PayeeName
				+ "&JoinFlag=" + this.JoinFlag + "&MerJoinFlag="
				+ this.MerJoinFlag + "&CustJoinFlag=" + this.CustJoinFlag
				+ "&CustJoinNum=" + this.CustJoinNum + "&CertID=" + this.CertID;
		return url;
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

	public String getTranSerialNum() {
		return tranSerialNum;
	}

	public void setTranSerialNum(String tranSerialNum) {
		this.tranSerialNum = tranSerialNum;
	}

	public String getTranStat() {
		return tranStat;
	}

	public void setTranStat(String tranStat) {
		this.tranStat = tranStat;
	}

	public String getBankRem() {
		return bankRem;
	}

	public void setBankRem(String bankRem) {
		this.bankRem = bankRem;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrType() {
		return currType;
	}

	public void setCurrType(String currType) {
		this.currType = currType;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getPayeeName() {
		return PayeeName;
	}

	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}

	public String getJoinFlag() {
		return JoinFlag;
	}

	public void setJoinFlag(String joinFlag) {
		JoinFlag = joinFlag;
	}

	public String getMerJoinFlag() {
		return MerJoinFlag;
	}

	public void setMerJoinFlag(String merJoinFlag) {
		MerJoinFlag = merJoinFlag;
	}

	public String getCustJoinFlag() {
		return CustJoinFlag;
	}

	public void setCustJoinFlag(String custJoinFlag) {
		CustJoinFlag = custJoinFlag;
	}

	public String getCustJoinNum() {
		return CustJoinNum;
	}

	public void setCustJoinNum(String custJoinNum) {
		CustJoinNum = custJoinNum;
	}

	public String getCertID() {
		return CertID;
	}

	public void setCertID(String certID) {
		CertID = certID;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(long chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
}
