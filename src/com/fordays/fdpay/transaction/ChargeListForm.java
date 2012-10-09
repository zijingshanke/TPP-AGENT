package com.fordays.fdpay.transaction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

public class ChargeListForm extends ListActionForm {
	private long status= -1;
	private String beginDate="";
	private String endDate="";
	private String bank="";
	private String cardNo="";

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest)
	{

		thisAction = "";
		beginDate="";
		endDate="";
	}
}
