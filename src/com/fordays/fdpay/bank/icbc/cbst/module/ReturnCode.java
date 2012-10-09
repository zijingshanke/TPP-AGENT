package com.fordays.fdpay.bank.icbc.cbst.module;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;


/**
 * 工商银行集中式银商转帐系统返回码
 */
public class ReturnCode {
	private static HashMap<String, String> codes = null;

	public static String getReturnCodeMsg(String returnCode) {
		String codeMsg = null;
		if (codes == null) {
			String res = "com.fordays.fdpay.bank.icbc.cbst.module.CbstReturnCodeMsg";
			ResourceBundle reb = ResourceBundle.getBundle(res);
			if (reb != null) {
				codes = new HashMap<String, String>();
				Enumeration<String> keyEmu = reb.getKeys();
				while (keyEmu.hasMoreElements()) {
					String key = (String) keyEmu.nextElement();
					String tempValue = "";
					try {
						tempValue = new String(reb.getString(key).getBytes(
								"ISO-8859-1"), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					String value = tempValue;
					codes.put(key, value);
				}
			}
		}
		Object obj = codes.get(returnCode);
		if (obj != null) {
			codeMsg = obj.toString();
		}
		return codeMsg;
	}

	public static void main(String[] args) {
		String msg = getReturnCodeMsg("2019");
		System.out.println(msg);
	}
}
