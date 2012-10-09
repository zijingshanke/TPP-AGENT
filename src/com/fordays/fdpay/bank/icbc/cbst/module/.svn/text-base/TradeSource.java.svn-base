package com.fordays.fdpay.bank.icbc.cbst.module;

import java.util.HashMap;

/**
 * 交易发起方
 */
public class TradeSource {
	private String sponsor = "";// 交易发起方

	public StringBuffer getTradeSourceXML() {
		sponsor = "F";
		StringBuffer con = new StringBuffer("<TradSrc>");
		con.append(sponsor);
		con.append("</TradSrc>");
		return con;
	}

	public static HashMap<String, String> tradeSources = null;

	public static String getTradeSourceDes(String tradeSourceValue) {
		return TradeSource.tradeSources.get(tradeSourceValue).toString();
	}

	public static HashMap<String, String> getTradeSources() {
		if (tradeSources == null) {
			tradeSources.put("B", "银行发起");
			tradeSources.put("F", "商户发起");
		}
		return tradeSources;
	}

	public static void setTradeSources(HashMap<String, String> tradeSources) {
		TradeSource.tradeSources = tradeSources;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

}
