package com.fordays.fdpay.bank.bcm.test;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.Vector;

import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.ChangeCharset;

public class TestBCM {
	public static void main(String[] args) {
		// getBcmResult();
		// changeCharset("");
		// urlDecode();

	}

	public static void urlDecode() {
		String src = "https://ebank.95559.com.cn/corporbank/NsTrans?dse_operationName=cb2202_queryOrderOp&reqData=%3C%3Fxml+version%3D%221.0%22+encoding%3D%22gb2312%22%3F%3E%3CBOCOMB2C%3E%3CopName%3Ecb2202_queryOrderOp%3C%2FopName%3E%3CreqParam%3E%3CmerchantID%3E301440360129520%3C%2FmerchantID%3E%3Cnumber%3E1%3C%2Fnumber%3E%3Cdetail%3E1%3C%2Fdetail%3E%3Corders%3EC20091023000210%3C%2Forders%3E%3C%2FreqParam%3E%3C%2FBOCOMB2C%3E&signData=MIIE%2FgYJKoZIhvcNAQcCoIIE7zCCBOsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA%2BUwggPhMIICyaADAgECAgRMXdLhMA0GCSqGSIb3DQEBBQUAMDUxCzAJBgNVBAYTAkNOMRQwEgYDVQQKEwtCQU5LQ09NTSBDQTEQMA4GA1UEAxMHQk9DT01DQTAeFw0wOTA5MjIwNzMxMzhaFw0xMTA5MjIwNzMxMzhaMIGAMQswCQYDVQQGEwJDTjEUMBIGA1UEChMLQkFOS0NPTU0gQ0ExETAPBgNVBAsTCEJBTktDT01NMRIwEAYDVQQLEwlNZXJjaGFudHMxNDAyBgNVBAMTKzA0MEAwMTQ0MDQwMDAwMDE1MjcxNkBbMzAxNDQwMzYwMTI5NTIwXUAwMDAwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBALglwOl2sLwGJ8JiaHp4DtjVygLat2b6ZEvk7qD2heCbpB1CVwhFRvXD8v7Ly%2FQgxjG3G4Z6RuT51f2DLuDACIDTreFTxCvRQIxQA3OYGVnqyq3CC3Qc5kYy6olGGpPYAnoODADchG7jiAFDqU%2FPpvEUuMmhUENoxzIllxQMgSwRAgMBAAGjggEvMIIBKzAfBgNVHSMEGDAWgBTSs9GxOUepN3l34yRNY7X4QsuZczBEBgkqhkiG9w0BCQ8ENzA1MA4GCCqGSIb3DQMCAgIAgDAOBggqhkiG9w0DBAICAIAwBwYFKw4DAgcwCgYIKoZIhvcNAwcwPQYDVR0gBDYwNDAyBgRVHSAAMCowKAYIKwYBBQUHAgEWHGh0dHA6Ly8xOTIuMTY4LjMuMTEwL2Nwcy5odG0wVgYDVR0fBE8wTTBLoEmgR6RFMEMxCzAJBgNVBAYTAkNOMRQwEgYDVQQKEwtCQU5LQ09NTSBDQTEMMAoGA1UECxMDY3JsMRAwDgYDVQQDEwdjcmwzODc1MAwGA1UdDwQFAwMH%2BYAwHQYDVR0OBBYEFAf4PbwEuZqX6aIT4iXipwUfSsMEMA0GCSqGSIb3DQEBBQUAA4IBAQB1lgHmpF6591tUOWmOa61DkJd8ZoV0yk5P2Fl6heGXowH0SUEtIU38m4TTYEEtNYnylbo5XlhhBc0k99lMEXh0gzNhUvmKhnbviHDYzVLNNsF1idRNfqc9rQhrPDoFgJXKqI0cdHAY4%2B27fahfwmiRGVB3SmVekvBloAQFdD4FGEpwI3aB2ITbIisFZbSL70loX9PYj57OLxv4SMm5j1Sx58ZWrOcndOm4cx6TMh6wFK4hB0PqR1h05n45geJlhbTifkewBcOLcIvoK4S8cbrfVMAURHDJDLTcDfIcFUPMxlSaI4JPM3sbtM9VMLngupGv%2BvSKWh8SuL1EPPjgtDhEMYHiMIHfAgEBMD0wNTELMAkGA1UEBhMCQ04xFDASBgNVBAoTC0JBTktDT01NIENBMRAwDgYDVQQDEwdCT0NPTUNBAgRMXdLhMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYBsERXv8NkbHk90b3UHkhLTweC6t8rYIaL9ZXIeJEsXiXhD7cNqdOOASt%2BgI2FToWPD6%2BJfgCUCgdltpKwl03s57MFn3UMioHVKNEQH00OhVY3O0cG0wzlUU1U%2BLuuZ60j%2FTpG2i%2Fsk2hG64luuboR77hDsV6MF8%2FyLpSk4s5WPjw%3D%3D";
		String result = BankUtil.getURLDecode(src);
		System.out.println(result);
	}

	public static void changeCharset(String str) {
		ChangeCharset util = new ChangeCharset();
		try {
			System.out.println(util.changeCharset(str, "utf-8"));
			System.out.println(util.toUTF_8(str));
			System.out.println(util.toISO_8859_1(str));
			System.out.println(util.toASCII(str));
			System.out.println(util.changeCharset(str, "GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void getRealPath() {

	}

	public static void getBcmResult() {
		String notifyMsg = "00a|00b|00c|00d";
		int lastIndex = notifyMsg.lastIndexOf("|");
		String signMsg = notifyMsg.substring(lastIndex + 1, notifyMsg.length());// 获取签名信息
		String srcMsg = notifyMsg.substring(0, lastIndex + 1);
		System.out.println("notifyMsg>>>" + notifyMsg);
		System.out.println("lastIndex>>>" + lastIndex);
		System.out.println("signMsg>>>>>" + signMsg);
		System.out.println("srcMsg>>>>>>" + srcMsg);

		StringTokenizer stName = new StringTokenizer(srcMsg, "|");// 拆解通知结果到Vector
		Vector<String> vc = new Vector<String>();
		int i = 0;
		while (stName.hasMoreTokens()) {
			String value = (String) stName.nextElement();
			if (value.equals(""))
				value = "&nbsp;";
			vc.add(i++, value);
		}

		for (int j = 0; j < vc.size(); j++) {
			System.out.println(vc.get(j));
		}
	}
}
