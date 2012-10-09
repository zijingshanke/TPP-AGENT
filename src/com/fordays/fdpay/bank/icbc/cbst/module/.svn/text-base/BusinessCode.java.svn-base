package com.fordays.fdpay.bank.icbc.cbst.module;

import java.util.HashMap;

/**
 * 业务功能号
 */
public class BusinessCode {
	private String code = "";
	private String msg = "";

	public StringBuffer getBusinessCodeXML() {
		StringBuffer con = new StringBuffer("<BusCd>");
		con.append(code);
		con.append("</BusCd>");
		return con;
	}

	private HashMap<String, String> businessCodes = null;

	public HashMap<String, String> getBusinessCodes() {
		if (businessCodes == null) {
			// 20001-20999：系统类
			businessCodes.put("20001", "签到");
			businessCodes.put("20002", "签退");
			businessCodes.put("20003", "通信检测");
			businessCodes.put("20004", "密钥同步");
			// 21001-21999账户类
			businessCodes.put("21001", "银商转帐开户");
			businessCodes.put("21002", "银商转帐销户");
			businessCodes.put("21003", "银行账户变更");
			businessCodes.put("21004", "查询商户账户余额");
			businessCodes.put("21005", "查询银行账户余额");
			// 22001-22999交易类
			businessCodes.put("22001", "银转商");
			businessCodes.put("22002", "商转银");
			businessCodes.put("22003", "银转商冲正");
			businessCodes.put("22004", "商转银冲正");
			businessCodes.put("22005", "转账交易明细查询");
			// 23001-23999对帐类
			businessCodes.put("23001", "对帐文件就绪");
		}
		return businessCodes;
	}

	public void setBusinessCodes(HashMap<String, String> businessCodes) {
		this.businessCodes = businessCodes;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
