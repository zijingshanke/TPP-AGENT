package com.fordays.fdpay.bank.icbc.cbst.module;

import com.neza.tool.DateUtil;

/**
 * 交易时间
 */
public class TransactionTime {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StringBuffer getTradTimeXML() {
		StringBuffer con = new StringBuffer("<Time>");
		con.append(DateUtil.getDateString("HHmmss"));
		con.append("</Time>");
		return con;
	}

}
