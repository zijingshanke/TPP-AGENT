package com.fordays.fdpay.bank.ccb.test;

import com.neza.tool.DateUtil;

public class Test {
	public static void main(String[] args) {
		// getUrl
		// CcbCmdToBank bank = new CcbCmdToBank();
		// bank.setOrderid("32345678");
		// bank.setPayment("1000000");
		// bank.setRemark1("0");
		// bank.setRemark2("0");
		// String url = bank.getCcburl();
		// System.out.println("ccb cmdtobank getccburl()--" + url);

		// veritySigature();
		// readPubKeyStr("defaultroot/WEB-INF/ccbpubkey.txt");

		// String ccb = Constant.WEB_INFO_PATH + File.separator +
		// "ccbpubkey.txt";
		// FileUtil.read(ccb);
		// verityAmount();
		// testSubString();
//		System.out.println("汪华>>>>----" + MD5.encrypt("汪华>>>>"));
		
		System.out.println(DateUtil.getDateString("yyyy-MM-dd hh:mm:ss"));
	}

	// 判断是否存在"."
	public static void verityAmount() {
		String amount1 = "12.036";
		// String amount2 = "0.1";
		// String amount3 = "100";
		// String test1 = "aa:bb";
		int result = amount1.indexOf(".");
		System.out.println(">>>>>>>>>>>>>" + result);
	}

	public static void testSubString() {
		String test1 = "aa:bb";
		String[] strArray = test1.split(":");
		for (int i = 0; i < strArray.length; i++) {
			System.out.println(strArray[i]);
		}
	}
}
