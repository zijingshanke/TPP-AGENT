package com.fordays.fdpay.bank.icbc;

import com.fordays.fdpay.bank.ResultFromBank;

public class IcbcB2BResultFromBank extends ResultFromBank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String APIName; // 接口名称
	private String APIVersion; // 接口版本号
	private String Shop_code; // 商户代码
	private String MerchantURL; // 支付结果信息通知程序地址
	private String Serial_no; // 指令序号 MAX(18) 必输，签名
	private String PayStatusZHCN; // 订单处理状态=1 必输，签名 0-成功 1-失败 2-可疑交易 3-等待授权
	private String TranErrorCode; // 错误代码 MAX(5) 必输，签名
	private String TranErrorMsg; // 错误描述 MAX(200) 必输，签名
	private String ContractNo; // 订单号
	private String ContractAmt; // 订单金额
	private String Account_cur = "001"; // 人民币
	private String JoinFlag; // 检验联名标志
	private String ShopJoinFlag; // 商城联名标志 0：已与工行联名；1：未与工行联名 （暂无该功能，预留），签名
	private String CustJoinFlag; // 客户联名标志 0：已注册；1：未注册 （暂无该功能，预留）签名
	private String CustJoinNumber; // 联名会员号 （暂无该功能，预留）签名
	private String NotifySign; // 订单签名数据 无限制 必输，为字符串，已经过Base64编码，商户验证前需先解码
	private String SendType; // 结果发送类型 0 --成功失败信息都发送 1 -- 只发送成功信息
	// 原输入值，签名，如果取0，工行向商户发送一笔订单的每一次交易结果，无论支付成功或者失败，如果取1，工行只向商户发送交易成功的通知信息。
	private String TranTime; // 接收交易日期时间
	// 原输入值，签名，YYYYMMDDHHmmss。与系统当前时间差为：前1小时，后12小时
	private String NotifyTime; // 返回通知日期时间 必输，签名，YYYYMMDDHHmmss。
	private String Shop_acc_num; // 商城账号 原输入值，签名
	private String PayeeAcct; // 收款单位账号 原输入值，签名
	private String PayeeName; // 收款单位名称 MAX (60) 签名
	private String ShopRem; // 商城备注字段 MAX(100) 原输入值，最多100字符
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getSerial_no() {
		return Serial_no;
	}

	public void setSerial_no(String serial_no) {
		Serial_no = serial_no;
	}

	public String getPayStatusZHCN() {
		return PayStatusZHCN;
	}

	public void setPayStatusZHCN(String payStatusZHCN) {
		PayStatusZHCN = payStatusZHCN;
	}

	public String getTranErrorCode() {
		return TranErrorCode;
	}

	public void setTranErrorCode(String tranErrorCode) {
		TranErrorCode = tranErrorCode;
	}

	public String getTranErrorMsg() {
		return TranErrorMsg;
	}

	public void setTranErrorMsg(String tranErrorMsg) {
		TranErrorMsg = tranErrorMsg;
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

	public String getShopJoinFlag() {
		return ShopJoinFlag;
	}

	public void setShopJoinFlag(String shopJoinFlag) {
		ShopJoinFlag = shopJoinFlag;
	}

	public String getCustJoinFlag() {
		return CustJoinFlag;
	}

	public void setCustJoinFlag(String custJoinFlag) {
		CustJoinFlag = custJoinFlag;
	}

	public String getCustJoinNumber() {
		return CustJoinNumber;
	}

	public void setCustJoinNumber(String custJoinNumber) {
		CustJoinNumber = custJoinNumber;
	}

	public String getNotifySign() {
		return NotifySign;
	}

	public void setNotifySign(String notifySign) {
		NotifySign = notifySign;
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

	public String getNotifyTime() {
		return NotifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		NotifyTime = notifyTime;
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

	public String getPayeeName() {
		return PayeeName;
	}

	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}

	public String getShopRem() {
		return ShopRem;
	}

	public void setShopRem(String shopRem) {
		ShopRem = shopRem;
	}

}
