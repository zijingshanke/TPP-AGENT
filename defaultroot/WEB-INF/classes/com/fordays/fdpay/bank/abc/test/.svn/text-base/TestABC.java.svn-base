package com.fordays.fdpay.bank.abc.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletResponse;

import cn.com.infosec.util.Base64;

import com.fordays.fdpay.bank.abc.AbcB2CcmdToBank;
import com.hitrust.trustpay.client.TrxException;
import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.b2c.PaymentResult;
import com.neza.encrypt.BASE64;

public class TestABC {
	public static void main(String[] args) {
		// testRequest();
		// testGetResources();
		// testNewFileStream();
		// getValue("b");
		verifyReturnMsg();
	}

	public static void verifyReturnMsg() {
		String MSG = "PE1TRz48TWVzc2FnZT48VHJ4UmVzcG9uc2U+PFJldHVybkNvZGU+MDAwMDwvUmV0dXJuQ29kZT48RXJyb3JNZXNzYWdlPjwvRXJyb3JNZXNzYWdlPjxFQ01lcmNoYW50VHlwZT5CMkM8L0VDTWVyY2hhbnRUeXBlPjxNZXJjaGFudElEPjEwMzQ0MDMwMDAyOTkzNzwvTWVyY2hhbnRJRD48VHJ4VHlwZT5QYXlSZXN1bHQ8L1RyeFR5cGU+PE9yZGVyTm8+WDIwMDkxMDE0MDAwMDAxPC9PcmRlck5vPjxBbW91bnQ+MC4wMTwvQW1vdW50PjxCYXRjaE5vPjAwMDA5MzwvQmF0Y2hObz48Vm91Y2hlck5vPjAwMDY2NDwvVm91Y2hlck5vPjxIb3N0RGF0ZT4yMDA5LzEwLzE0PC9Ib3N0RGF0ZT48SG9zdFRpbWU+MTA6Mjg6NTI8L0hvc3RUaW1lPjxNZXJjaGFudFJlbWFya3M+MjE5LjEzMS4xOTQuMTk0PC9NZXJjaGFudFJlbWFya3M+PFBheVR5cGU+UEFZMDE8L1BheVR5cGU+PE5vdGlmeVR5cGU+MDwvTm90aWZ5VHlwZT48L1RyeFJlc3BvbnNlPjwvTWVzc2FnZT48U2lnbmF0dXJlLUFsZ29yaXRobT5TSEExd2l0aFJTQTwvU2lnbmF0dXJlLUFsZ29yaXRobT48U2lnbmF0dXJlPjl4ZlhiNElTalBBSlByZDVpVTJxYVE1NDd5ZHczUzNBMzhUeTBFdVRDTDNNWWEzdHpIbkg1WXE5T1oyeDNnVHQ5ekg2Z2xXRHdDdktlMzJpT0kxSVVPUGQvY1lNeGlWNXZMc29nUS8zQ3VOSnRVMHJNc29kMmNlNmVhbnB6bjJidEZUR3JQVGNueUQ3aHNrUGw5OTE1cGc0RFdUODE1dVE0eC9xTlFDK2NrWT08L1NpZ25hdHVyZT48L01TRz4=";

		String src = BASE64.dencrypt(MSG);
		System.out.println(src);
//		try {
//			PaymentResult tResult = new PaymentResult(MSG);
//			String OrderNo = tResult.getValue("OrderNo");
//			String Amount = tResult.getValue("Amount");
//			String BatchNo = tResult.getValue("BatchNo");
//			String VoucherNo = tResult.getValue("VoucherNo");
//			String HostDate = tResult.getValue("HostDate");
//			String HostTime = tResult.getValue("HostTime");
//			String MerchantRemarks = tResult.getValue("MerchantRemarks");
//			String PayType = tResult.getValue("PayType");
//			String NotifyType = tResult.getValue("NotifyType");
//
//			System.out.println("--OrderNo--" + OrderNo);
//			System.out.println("--Amount--" + Amount);
//			System.out.println("--BatchNo--" + BatchNo);
//			System.out.println("--VoucherNo--" + VoucherNo);
//			System.out.println("--HostDate--" + HostDate);
//			System.out.println("--HostTime--" + HostTime);
//			System.out.println("--MerchantRemarks--" + MerchantRemarks);
//			System.out.println("--PayType--" + PayType);
//			System.out.println("--NotifyType--" + NotifyType);
//		} catch (TrxException e) {
//			e.printStackTrace();
//		}
	}

	public static void getValue(String aTag) {
		String iXMLString = "<a>1</a><cc>3</cc>4<cc></cc><b>2</b>";
		// XMLDocument tXMLDocument = null;
		int tStartIndex = iXMLString.indexOf("<" + aTag.trim() + ">");
		int tEndIndex = iXMLString.indexOf("</" + aTag.trim() + ">");
		if (tStartIndex >= 0 && tEndIndex >= 0 && tStartIndex < tEndIndex)
			System.out.println("startIndex=" + tStartIndex + " endIndex="
					+ tEndIndex);
		System.out.println(iXMLString.charAt(tEndIndex - 1));
	}

	public static void testNewFileStream() {
		String MerchantCertFile = "E:\\projectclient\\defaultroot\\WEB-INF\\bankkey\\abc\\cert\\2006.pfx";
		MerchantCertFile = "E:\\test.txt";
		try {
			FileInputStream in = new FileInputStream(MerchantCertFile);
			System.out.println(in.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void testGetResources() {
		String path = "TrustMerchant";
		path = "com.fordays.fdpay.bank.abc.biz.TrustMerchant";
		System.out.println(path);
		System.out.println(ResourceBundle.getBundle(path).getString("LogPath"));
	}

	public static void testRequest() {
		AbcB2CcmdToBank bank = new AbcB2CcmdToBank();
		TrxResponse tTrxResponse = bank.getTTrxResponse();
		if (tTrxResponse.isSuccess()) {
			HttpServletResponse response = null;
			// 6、支付请求提交成功，将客户端导向支付页面
			try {
				response.sendRedirect(tTrxResponse.getValue("PaymentURL"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 7、支付请求提交失败，商户自定后续动作
			System.out.println("failed");
			System.out.println("ReturnCode =" + tTrxResponse.getReturnCode());
			System.out.println("ErrorMsg=" + tTrxResponse.getErrorMessage());
		}
	}
}
