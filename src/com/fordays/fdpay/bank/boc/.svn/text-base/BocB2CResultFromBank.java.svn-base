package com.fordays.fdpay.bank.boc;

import com.fordays.fdpay.bank.ResultFromBank;

/**
 * 中国银行网上银行B2C订单处理结果
 */
public class BocB2CResultFromBank extends ResultFromBank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantNo = "";// 商户ID
	private String orderNo = "";// 商户系统产生的订单号
	private String orderSeq = "";// 银行订单流水号
	private String cardTyp = "";// 银行卡类别：
	// 01：中行借记卡
	// 02：中行信用卡，信用卡（分行卡）
	// 04：中行信用卡，信用卡（总行卡）
	// 11：银联借记卡
	// 21：VISA 借记卡
	// 22：VISA 信用卡
	// 31：MC 借记卡
	// 32：MC 信用卡
	// 42：运通卡
	// 52：大来卡
	// 62：JCB 卡
	private String payTime = "";// 支付时间
	private String orderStatus = "";// 订单状态
	// 0-未处理
	// 1-支付
	// 2-撤销
	// 3-退货
	// 4-未明
	// 5-失败
	private String payAmount = "";// 支付金额
	private String signData = "";// 网关签名
	// -------------the end
	private String url = "";

	public String getUrl() {
		url = "merchantNo=" + merchantNo + "&orderNo=" + orderNo + "&orderSeq="
				+ orderSeq + "&cardTyp=" + cardTyp + "&payTime=" + payTime
				+ "&orderStatus=" + orderStatus + "&payAmount=" + payAmount
				+ "&signData=" + signData;
		return url;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getCardTyp() {
		return cardTyp;
	}

	public void setCardTyp(String cardTyp) {
		this.cardTyp = cardTyp;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
