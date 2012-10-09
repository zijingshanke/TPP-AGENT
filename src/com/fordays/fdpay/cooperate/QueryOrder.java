package com.fordays.fdpay.cooperate;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.OrderDetails;

/**
 * 商户订单查询接口请求对象
 */
public class QueryOrder extends org.apache.struts.action.ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 协议参数
	private String service = "";//
	private String partner = "";//
	private String sign = "";//
	private String sign_type = "";//
	// 业务参数
	private String out_trade_no = "";//
	private String _input_charset = "";//
	// private String ="";//
	// private String ="";//

	// 批量
	private String beginDate = "";
	private String endDate = "";

	// 参数校验标识
	private boolean isValided = false;
	private String validedRemark = "";
	private String partenerKey = "";

	// --------
	private String sigleQueryUrl = "";

	public String getSigleQueryUrl() {
		sigleQueryUrl = "out_trade_no=" + out_trade_no + "&partner=" + partner
				+ "&service=" + service + "&sign=" + sign + "&sign_type="
				+ sign_type + "&_input_charset=" + _input_charset;

		return sigleQueryUrl;
	}
	public void setSigleQueryUrl(String sigleQueryUrl) {
		this.sigleQueryUrl = sigleQueryUrl;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isValided() {
		return isValided;
	}

	public void setValided(boolean isValided) {
		this.isValided = isValided;
	}

	public String getValidedRemark() {
		return validedRemark;
	}

	public void setValidedRemark(String validedRemark) {
		this.validedRemark = validedRemark;
	}

	public String getPartenerKey() {
		return partenerKey;
	}

	public void setPartenerKey(String partenerKey) {
		this.partenerKey = partenerKey;
	}

}
