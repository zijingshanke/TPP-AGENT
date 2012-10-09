package com.fordays.fdpay.bank.ccb.test;

import java.io.IOException;
import java.util.Properties;

import com.fordays.fdpay.bank.ccb.CcbB2CcmdToBank;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.neza.encrypt.MD5;
import com.neza.tool.URLUtil;

public class TestQuery {
	public static String prefix = "https://ibsbjstar.ccb.com.cn/app/ccbMain?";

	public static void main(String[] args) {
		String url = "";
		// singleQueryTest("20091020", "C20091020000035");
		url = multiQueryTest("20091020", "BEGORDERTIME", "ENDORDERTIME", "1");
		// singleQuery();
		// multiQuery();
		// testMac();

		postMethod(url);
	}

	public static void printJVMInfo() {
		// Properties psys=System.getProperties();
		// String path = psys.getProperty("user.dir");// 程序所在的路径；
		// // psys.list(System.out);
	}

	public static void postMethod(String url) {
		System.out.println("postMethod=" + url);
		String result = "";
		try {
			result = HttpInvoker.readContentFromPost(url, "");
			result = URLUtil.getResponseBodyAsGet(url).toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	public static String singleQueryTest(String date, String orderid) {
		String query = "MERCHANTID=" + "105440442150001" + "&BRANCHID="
				+ "441000000" + "&POSID=" + "100000785" + "&ORDERDATE=" + date
				+ "&ORDERID=" + orderid + "&TXCODE=" + 410404;
		String mac = MD5.encrypt(query).toLowerCase();
		String url = prefix + query + "&MAC=" + mac;
		return url;
	}

	/**
	 * 
	 * sel_type
	 * 
	 * 1 页面形式 2 文件返回形式 (提供TXT和XML格式文件的下载) 3 XML页面形式
	 * 
	 */
	public static String multiQueryTest(String date, String BEGORDERTIME,
			String ENDORDERTIME, String selType) {
		String order = "MERCHANTID=" + "105440442150001" + "&BRANCHID="
				+ "441000000" + "&POSID=" + "100000785" + "&ORDERDATE=" + date
				+ "&BEGORDERTIME=" + "01" + "&ENDORDERTIME=" + "23"
				+ "&BEGORDERID=" + "" + "&ENDORDERID=" + "" + "&QUPWD="
				+ "000000" + "&TXCODE=" + "410405" + "&SEL_TYPE=" + selType
				+ "&OPERATOR=";

		String strSrc = "MERCHANTID=" + "105440442150001" + "&BRANCHID="
				+ "441000000" + "&POSID=" + "100000785" + "&ORDERDATE=" + date
				+ "&BEGORDERTIME=" + "01" + "&ENDORDERTIME=" + "23"
				+ "&BEGORDERID=" + "" + "&ENDORDERID=" + "" + "&QUPWD=" + ""
				+ "&TXCODE=" + "410405" + "&SEL_TYPE=" + selType + "&OPERATOR=";

		String mac = MD5.encrypt(strSrc).toLowerCase();

		String url = prefix + order + "&MAC=" + mac;
		return url;
	}

	public static void singleQuery() {
		CcbB2CcmdToBank bank = new CcbB2CcmdToBank();
		String query = "MERCHANTID=" + bank.getMerchantid() + "&BRANCHID="
				+ bank.getBranchid() + "&POSID=" + bank.getPosid()
				+ "&ORDERDATE=" + "20091020" + "&ORDERID=" + "C20091020000035"
				+ "&TXCODE=" + 410404;
		String mac = MD5.encrypt(query).toLowerCase();
		String url = prefix + query + "&MAC=" + mac;
		System.out.println("----getUrl---" + url);
	}

	public static void multiQuery() {
		CcbB2CcmdToBank bank = new CcbB2CcmdToBank();
		String order = "MERCHANTID=" + bank.getMerchantid() + "&BRANCHID="
				+ bank.getBranchid() + "&POSID=" + bank.getPosid()
				+ "&ORDERDATE=" + "20090309" + "&BEGORDERTIME=" + "01"
				+ "&ENDORDERTIME=" + "23" + "&BEGORDERID=" + ""
				+ "&ENDORDERID=" + "" + "&QUPWD=" + "000000" + "&TXCODE="
				+ "410405" + "&SEL_TYPE=" + "3" + "&OPERATOR=";

		String strSrc = "MERCHANTID=" + bank.getMerchantid() + "&BRANCHID="
				+ bank.getBranchid() + "&POSID=" + bank.getPosid()
				+ "&ORDERDATE=" + "20090309" + "&BEGORDERTIME=" + "01"
				+ "&ENDORDERTIME=" + "23" + "&BEGORDERID=" + ""
				+ "&ENDORDERID=" + "" + "&QUPWD=" + "" + "&TXCODE=" + 410405
				+ "&SEL_TYPE=" + "3" + "&OPERATOR=";

		String mac = MD5.encrypt(strSrc).toLowerCase();

		String url = prefix + order + "&MAC=" + mac;
		System.out.println("--getUrl--" + url);
	}
}
