package com.fordays.fdpay.bank.icbc.cbst.module;

import java.util.HashMap;

/**
 * 应用系统类型
 */
public class SystemType {
	public static HashMap<String, String> systemTypes = null;

	public StringBuffer getSystemTypeXML() {
		StringBuffer con = new StringBuffer("<SysType>");
		con.append("8");
		con.append("</SysType>");
		return con;
	}

	public static String getSystemTypeDes(String systemTypeValue) {
		return systemTypes.get(systemTypeValue).toString();
	}

	public static HashMap<String, String> getSystemTypes() {
		if (systemTypes == null) {
			systemTypes = new HashMap<String, String>();
			systemTypes.put("8", "集中式银商转帐");
		}
		return systemTypes;
	}

	public static void setSystemTypes(HashMap<String, String> systemTypes) {
		SystemType.systemTypes = systemTypes;
	}
}
