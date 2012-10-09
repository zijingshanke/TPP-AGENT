package com.fordays.fdpay.bank.icbc.cbst.module;

/**
 * 版本
 */
public class Version {

	private static String DAFAULT_VERSION = "1.0.0";

	public StringBuffer getVersionXML() {
		StringBuffer con = new StringBuffer("<!--版本-->");
		con.append("<Version>");
		con.append(DAFAULT_VERSION);
		con.append("</Version>");
		return con;
	}

}
