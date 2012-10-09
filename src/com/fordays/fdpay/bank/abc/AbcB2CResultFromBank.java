package com.fordays.fdpay.bank.abc;

import com.fordays.fdpay.bank.ResultFromBank;

/**
 * 农业银行网上银行B2C订单处理结果
 */
public class AbcB2CResultFromBank extends ResultFromBank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String OrderNo = "";// 订单号
	private String Amount = "";// 订单金额
	private String BatchNo = "";// 交易批次号
	private String VoucherNo = "";// 交易凭证号（用于交易对账时使用）
	private String HostDate = "";// 银行交易日期（YYYY/MM/DD）
	private String HostTime = "";// 银行交易时间（HH:MM:SS）
	private String MerchantRemarks = "";// 商户备注信息（商户在支付请求时所提交的信息）
	private String PayType = "";// 消费者支付方式
	// PAY01：银行网点注册客户支付
	// PAY02：网上注册客户支付
	// PAY03：威士(VISA)国际卡支付
	// PAY04：万事达(MasterCard)国际卡支付
	private String NotifyType = "";// 支付结果通知方式 0：页面通知； 1：服务器通知
	// ------------------
	private String isSuccess = "";// 失败-0，成功-1
	private String returnCode = "";
	private String errorMsg = "";

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getBatchNo() {
		return BatchNo;
	}

	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}

	public String getVoucherNo() {
		return VoucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		VoucherNo = voucherNo;
	}

	public String getHostDate() {
		return HostDate;
	}

	public void setHostDate(String hostDate) {
		HostDate = hostDate;
	}

	public String getHostTime() {
		return HostTime;
	}

	public void setHostTime(String hostTime) {
		HostTime = hostTime;
	}

	public String getMerchantRemarks() {
		return MerchantRemarks;
	}

	public void setMerchantRemarks(String merchantRemarks) {
		MerchantRemarks = merchantRemarks;
	}

	public String getPayType() {
		return PayType;
	}

	public void setPayType(String payType) {
		PayType = payType;
	}

	public String getNotifyType() {
		return NotifyType;
	}

	public void setNotifyType(String notifyType) {
		NotifyType = notifyType;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
