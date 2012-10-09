package com.fordays.fdpay.bank.cmbc;

import com.fordays.fdpay.bank.ResultFromBank;

/**
 * @民生银行银联支付接口订单处理结果
 */
public class CmbcChinaPayResultFromBank extends ResultFromBank {
	/**
	 * 
	 */
	 
	private static final long serialVersionUID = 1L;
	private String billNo = "";// 订单号 CHAR(20) 从商户传送的信息中获得
	private String corpID = "";// 商户代码 CHAR(5) 从商户传送的信息中获得
	private String txAmt = "";// 交易金额 DECIMAL(13,2) 从商户传送的信息中获得
	private String txDate = "";// 交易日期 CHAR(8) 从商户传送的信息中获得
	private String txTime = "";// 交易时间 CHAR(6) 从商户传送的信息中获得
	private String billstatus = "";// 订单状态 Char(1) 0：成功 1，2都不发送
	private String Billremark1 = "";// 备注1 VarChar(254) 从商户传送的信息中获得
	private String Billremark2 = "";// 备注2 VarChar(254) 从商户传送的信息中获得

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCorpID() {
		return corpID;
	}

	public void setCorpID(String corpID) {
		this.corpID = corpID;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getTxTime() {
		return txTime;
	}

	public void setTxTime(String txTime) {
		this.txTime = txTime;
	}

	public String getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public String getBillremark1() {
		return Billremark1;
	}

	public void setBillremark1(String billremark1) {
		Billremark1 = billremark1;
	}

	public String getBillremark2() {
		return Billremark2;
	}

	public void setBillremark2(String billremark2) {
		Billremark2 = billremark2;
	}
}
