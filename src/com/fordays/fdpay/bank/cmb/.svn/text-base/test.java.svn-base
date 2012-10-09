package com.fordays.fdpay.bank.cmb;

import java.util.Vector;

import com.fordays.fdpay.bank.BankUtil;

import cmb.netpayment.Settle;

class test {
	static private Settle settle;
	static private int iRet;

	private static void testQueryUnsettledOrder() {
		StringBuffer strbuf = new StringBuffer();
		iRet = settle.QueryUnsettledOrder(strbuf);
		if (iRet == 0) {
			System.out.println("QueryUnsettledOrder ok");
			System.out.println(strbuf.toString());
		} else {
			System.out.println(settle.GetLastErr(iRet));
		}
	}

	private static void testQuerySettledOrder() {
		StringBuffer strbuf = new StringBuffer();
		iRet = settle.QuerySettledOrder("20040101", "20041010", strbuf);
		if (iRet == 0) {
			System.out.println("QuerySettledOrder ok");
			System.out.println(strbuf.toString());
		} else {
			System.out.println(settle.GetLastErr(iRet));
			System.out.println(settle.GetLastErr(iRet));
		}
	}

	private static void testQueryTransact() {
		StringBuffer strbuf = new StringBuffer();
		iRet = settle.QueryTransact("20040302", strbuf);
		if (iRet == 0) {
			System.out.println("QueryTransact ok");
			System.out.println(strbuf.toString());
		} else {
			System.out.println(settle.GetLastErr(iRet));
			System.out.println(settle.GetLastErr(iRet));
		}
	}

	private static void testQueryPagedSettledOrder() {
		StringBuffer strbuf = new StringBuffer();
		settle.PageReset();
		do {
			iRet = settle.QuerySettledOrderByPage("20040101", "20041010", 10,
					strbuf);
		} while (iRet == 0 && !settle.m_bIsLastPage);
		if (iRet == 0) {
			System.out.println("QuerySettledOrder ok");
			System.out.println(strbuf.toString());
		} else {
			System.out.println(settle.GetLastErr(iRet));
			System.out.println(settle.GetLastErr(iRet));
		}
	}

	private static void testQueryPagedTransact() {
		StringBuffer strbuf = new StringBuffer();
		settle.PageReset();
		do {
			iRet = settle.QueryTransactByPage("20040302", 10, strbuf);
		} while (iRet == 0 && !settle.m_bIsLastPage);
		if (iRet == 0) {
			System.out.println("QueryTransact ok");
			System.out.println(strbuf.toString());
		} else {
			System.out.println(settle.GetLastErr(iRet));
			System.out.println(settle.GetLastErr(iRet));
		}
	}

	public static void testSettleLogin() {
		settle = new Settle();
		iRet = settle.SetOptions("netpay.cmbchina.com");
		if (iRet == 0) {
			System.out.println("SetOptions ok");
		} else {
			System.out.println(settle.GetLastErr(iRet));
			System.out.println(settle.GetLastErr(iRet));
			return;
		}

		iRet = settle.LoginC("0755", "000107", "888888");
		if (iRet == 0) {
			System.out.println("LoginC ok");
		} else {
			System.out.println(settle.GetLastErr(iRet));
			return;
		}
		testQueryUnsettledOrder();

		settle.Logout();

		testVerifySign();
	}

	public static void testGenMerchantCode() {
		cmb.MerchantCode mc = new cmb.MerchantCode();
		String strVerifyCode = mc.genMerchantCode("KeyString", "20081129",
				"0571", "002696", "0011223344", "12.43", "�̻�����",
				"https://www.alipay.com/bankReciev", "User1", "User2",
				"202.97.113.23", "00000000", "");
		System.out.println(strVerifyCode);
	}

	private static void testVerifySign() {
		try {
			cmb.netpayment.Security pay = new cmb.netpayment.Security(
					"E:\\project\\fdpay-client\\defaultroot\\WEB-INF\\bankkey\\cmb\\public.key");

			// byte[] baSig =
			// "Succeed=Y&BillNo=000000&Amount=12.00&Date=20001221&Msg=付款请求已被银行接受.&Signature=9|96|42|124|72|152|158|163|254|181|233|185|138|15|6|89|43|167|41|171|28|218|209|216|211|47|169|5|243|235|2|225|189|233|84|130|206|204|49|236|196|127|109|65|193|110|229|29|107|135|174|44|185|109|250|70|163|225|137|18|84|205|236|82|".getBytes("GB2312");
			byte[] baSig = "Succeed=Y&CoNo=000004&BillNo=8104700022&Amount=60&Date=20071213&MerchantPara=8120080420080414701013700022&Msg=00270000042007121307321387100000002470&Signature=177|48|67|121|22|40|125|29|39|162|103|204|103|156|74|196|63|148|45|142|206|139|243|120|224|193|84|46|216|23|42|29|25|64|232|213|114|3|22|51|131|76|169|143|183|229|87|164|138|77|185|198|116|254|224|68|26|169|194|160|94|35|111|150|"
					.getBytes();
			boolean bRet = pay.checkInfoFromBank(baSig);
			System.out.println("checkInfoFromBank: " + bRet);
		} catch (Exception e) {
			System.out.println("new netpayment object failed: "
					+ e.getMessage());
		}
	}

	public static void main(String args[]) {
		// testVerifySign();
		String plainText = "Succeed=Y&CoNo=000004&BillNo=8104700022&Amount=60&Date=20071213&MerchantPara=8120080420080414701013700022&Msg=00270000042007121307321387100000002470&Signature=177|48|67|121|22|40|125|29|39|162|103|204|103|156|74|196|63|148|45|142|206|139|243|120|224|193|84|46|216|23|42|29|25|64|232|213|114|3|22|51|131|76|169|143|183|229|87|164|138|77|185|198|116|254|224|68|26|169|194|160|94|35|111|150|";

		Vector<String> vec = BankUtil.getVectorString(plainText, "&");

	}
}
