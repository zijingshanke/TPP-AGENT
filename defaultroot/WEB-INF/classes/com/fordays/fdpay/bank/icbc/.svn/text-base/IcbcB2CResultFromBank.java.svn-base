package com.fordays.fdpay.bank.icbc;

import com.fordays.fdpay.bank.ResultFromBank;

public class IcbcB2CResultFromBank extends ResultFromBank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merVAR = "";// 返回商户变量 无限制
								// 取值：商户提交接口中merVAR字段当返回银行结果时，作为一个隐藏域变量，商户可以用此变量维护session等等。由客户端浏览器支付完成后提交通知结果时是明文传输，建议商户对此变量使用额外安全防范措施，如签名、base64，银行端将此字段原样返回
	private String notifyData = "";// 通知结果数据
	private String signMsg = "";// 银行对通知结果的签名数据
	// 注意：签名是对notifyData的xml明文进行签名，不是其BASE64编码后的串；签名后得到二进制数据，对此数据进行BASE64编码得到signMsg

	// -----------notifyData---------------
	private String interfaceName = "";// 接口名称
	private String interfaceVersion = "";// 接口版本号
	private String orderDate= "";// 交易日期时间
	private String orderid= "";// 订单号
	private String amount= "";// 订单金额 ，以分为单位。不可以为零，必需符合金额标准。
	private String curType= "";// 支付币种
	private String merID= "";// 商户代码
	private String merAcct= "";// 商城账号
	private String verifyJoinFlag= "";// 检验联名标志
	private String JoinFlag= "";// 客户联名标志，1客户联名 0客户未联名
	private String UserNum= "";// 联名会员号。
	private String TranSerialNo= "";// 银行指令序号 MAX(30) 银行端指令流水号
	private String notifyDate= "";// 返回通知日期时间
	private String tranStat= "";// 订单处理状态 =1 1-“交易成功，已清算”；2-“交易失败”； 3-“交易可疑”
	private String comment= "";// 错误描述 MAX(100) 错误描述
	// --------------------------------------------------
	
	private String url= "";

	public String getMerVAR() {
		return merVAR;
	}

	public void setMerVAR(String merVAR) {
		this.merVAR = merVAR;
	}

	public String getNotifyData() {
		return notifyData;
	}

	public void setNotifyData(String notifyData) {
		this.notifyData = notifyData;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
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

	public String getJoinFlag() {
		return JoinFlag;
	}

	public void setJoinFlag(String joinFlag) {
		JoinFlag = joinFlag;
	}

	public String getUserNum() {
		return UserNum;
	}

	public void setUserNum(String userNum) {
		UserNum = userNum;
	}

	public String getTranSerialNo() {
		return TranSerialNo;
	}

	public void setTranSerialNo(String tranSerialNo) {
		TranSerialNo = tranSerialNo;
	}

	public String getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}

	public String getTranStat() {
		return tranStat;
	}

	public void setTranStat(String tranStat) {
		this.tranStat = tranStat;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
