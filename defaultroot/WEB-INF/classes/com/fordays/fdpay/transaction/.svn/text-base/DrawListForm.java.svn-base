package com.fordays.fdpay.transaction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

public class DrawListForm extends ListActionForm
{
	private String  beginDate="";
  	private String  endDate="";

  	private long status=-1;
  	
	public long getStatus() {
	
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
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
	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest)
	{

		thisAction = "";
		beginDate="";
		endDate="";
	}
}
