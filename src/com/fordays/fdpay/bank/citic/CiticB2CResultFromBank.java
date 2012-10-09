package com.fordays.fdpay.bank.citic;

import com.fordays.fdpay.bank.ResultFromBank;

/**
 * @中信银行网上银行B2C第三方支付商户订单支付结果
 */
public class CiticB2CResultFromBank extends ResultFromBank {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String MSGCODE = "";// 交易返回码 AAAAAAA成功,其余代表失败
	private String MSGCN = "";// 支付交易平台返回交易中文信息<!—成功；失败原因-->
	private String E3RDPAYNO = "";// 第三方支付编号
	private String ORDERDATE = "";// 订单日期
	private String ORDERTIME = "";// 订单时间
	private String ORDERNO = "";// 订单号
	private String ORDERAMT = "";// 订单金额
	private String CURRID = "";// 币种
	private String PAYAMT = "";// 支付金额
	private String PAYNO = "";// 支付交易流水号
	private String ACCNO = "";// 交易卡号
	private String ACCTYPE = "";// 帐户类型
	private String PBCSTNAME = "";// 个人客户中文姓名
	private String ACCHASH = "";// 交易卡号哈希值

	public String getMSGCODE() {
		return MSGCODE;
	}

	public void setMSGCODE(String msgcode) {
		MSGCODE = msgcode;
	}

	public String getMSGCN() {
		return MSGCN;
	}

	public void setMSGCN(String msgcn) {
		MSGCN = msgcn;
	}

	public String getE3RDPAYNO() {
		return E3RDPAYNO;
	}

	public void setE3RDPAYNO(String e3rdpayno) {
		E3RDPAYNO = e3rdpayno;
	}

	public String getORDERDATE() {
		return ORDERDATE;
	}

	public void setORDERDATE(String orderdate) {
		ORDERDATE = orderdate;
	}

	public String getORDERTIME() {
		return ORDERTIME;
	}

	public void setORDERTIME(String ordertime) {
		ORDERTIME = ordertime;
	}

	public String getORDERNO() {
		return ORDERNO;
	}

	public void setORDERNO(String orderno) {
		ORDERNO = orderno;
	}

	public String getORDERAMT() {
		return ORDERAMT;
	}

	public void setORDERAMT(String orderamt) {
		ORDERAMT = orderamt;
	}

	public String getCURRID() {
		return CURRID;
	}

	public void setCURRID(String currid) {
		CURRID = currid;
	}

	public String getPAYAMT() {
		return PAYAMT;
	}

	public void setPAYAMT(String payamt) {
		PAYAMT = payamt;
	}

	public String getPAYNO() {
		return PAYNO;
	}

	public void setPAYNO(String payno) {
		PAYNO = payno;
	}

	public String getACCNO() {
		return ACCNO;
	}

	public void setACCNO(String accno) {
		ACCNO = accno;
	}

	public String getACCTYPE() {
		return ACCTYPE;
	}

	public void setACCTYPE(String acctype) {
		ACCTYPE = acctype;
	}

	public String getPBCSTNAME() {
		return PBCSTNAME;
	}

	public void setPBCSTNAME(String pbcstname) {
		PBCSTNAME = pbcstname;
	}

	public String getACCHASH() {
		return ACCHASH;
	}

	public void setACCHASH(String acchash) {
		ACCHASH = acchash;
	}
}
