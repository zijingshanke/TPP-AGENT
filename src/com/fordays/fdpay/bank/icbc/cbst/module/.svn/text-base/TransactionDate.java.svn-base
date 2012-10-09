package com.fordays.fdpay.bank.icbc.cbst.module;

import com.neza.tool.DateUtil;

/**
 * 交易日期
 */
public class TransactionDate {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tradDate = "";

	public StringBuffer getTradDateXML() {
		tradDate = DateUtil.getDateString("yyyyMMdd");
		
		StringBuffer con = new StringBuffer("<Date>");
		con.append(tradDate);
		con.append("</Date>");
		return con;
	}

	public String getTradDate() {
		return tradDate;
	}

	public void setTradDate(String tradDate) {
		this.tradDate = tradDate;
	}

}
