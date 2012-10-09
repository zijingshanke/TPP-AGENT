package com.fordays.fdpay.blackscreen;

public class Blackscreen extends org.apache.struts.action.ActionForm{
    
	private String isp; //供应商
	private String blackscreenAccount;//黑屏账号
	private String blackscreenAccount2;//临时黑屏账号
	private String resultAmount;//充值金额
	
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getBlackscreenAccount() {
		return blackscreenAccount;
	}
	public void setBlackscreenAccount(String blackscreenAccount) {
		this.blackscreenAccount = blackscreenAccount;
	}

	public String getResultAmount() {
		return resultAmount;
	}
	public void setResultAmount(String resultAmount) {
		this.resultAmount = resultAmount;
	}
	public String getBlackscreenAccount2() {
		return blackscreenAccount2;
	}
	public void setBlackscreenAccount2(String blackscreenAccount2) {
		this.blackscreenAccount2 = blackscreenAccount2;
	}
	
}
