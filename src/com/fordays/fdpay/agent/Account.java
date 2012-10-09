package com.fordays.fdpay.agent;

import com.fordays.fdpay.agent._entity._Account;

public class Account extends _Account {
	private static final long serialVersionUID = 1L;
	public static final long certificationStatus_0 = 0;
	public static final long certificationStatus_1 = 1;
	public static final long bindStatus_NoBind = 1;
	public static final long bindStatus_Bind = 2;

	public String getHaskCardNo() {
		String countxing = "";
		for (int i = 0; i <= (cardNo.length() - 5); i++) {
			countxing += "*";
		}
		if (cardNo.length() > 5)
			return countxing
					+ cardNo.substring(cardNo.length() - 5, cardNo.length());
		else
			return "************";
	}
	public String getBankName(){
		return com.fordays.fdpay.base.Bank.getCName(this.bankId.intValue()+"c");
	}
	public String getBankSname(){
		return com.fordays.fdpay.base.Bank.getCName(this.bankId.intValue()+"s");
	}


}
