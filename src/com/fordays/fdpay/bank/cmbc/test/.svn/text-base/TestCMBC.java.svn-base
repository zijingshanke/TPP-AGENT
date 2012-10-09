package com.fordays.fdpay.bank.cmbc.test;

import java.util.Vector;
import Union.JnkyServer;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.cmbc.CmbcB2CResultFromBank;
import com.neza.exception.AppException;

public class TestCMBC {
	public static void main(String[] args) {
		// test1();
		// test2();
		// test3();

		verify();
	}


	public static void verify() {
		CmbcB2CResultFromBank cmbcResult = null;
		String payresult = "EnAEAACAAEt0WcW5KiaoEiYguHiWmEIT9Br3AQqaNqkfktvxuaO48fGHdTwjkkEs+27CSFRkD4hz3rUv368EsIWjpue2i44dZwC6RmBWoo0ws60KwFW/6PAGnGNwAoSWj2+GXKgeGTTP7kNNXYA8lKS/K2b3wnv8JVlD/J23LkLxA0oifsq/JtQ27oZf3itpvWAF6jmukcyNbMUR04n8SqJanzaGM3LYmApoh7v0+28PZDGrlBhPugq+c0Ep76/abSdVTVa/8ikU83kg3NprkoVzqyUmRp7aSZ4HLZn+NrXXKP+Igk+Pnd+9XX+h/VNPudvxKYD82ecRw9Lx2sl3D+xAdfb7+nXYNBh6ERgoeghLnSDl/TFJHTzRHmLiwcccio06rfX/D7e0mGJdeKRSODcAZSf9TY21TRBnXwamKQnlhuhEFJOnulPet4YMaic3GaTqq74tXPM6+inEfTAj+XQWPM/d5H8Z5FyepVwYdf/Sxl06TP9/c90HnfSI+9h715IJy/BjNInRcRF+fm1ZNsJk78/D35M9Sr4YfTLpK6jiHVGIXMdfu9WPEkkMlrcbP+UUE1LUreRY9yaFtYB3AIsd0V4iPR8w5tb41DNdUvOoknADbWnCe9WAc1TPAN4QKmd7daJdCuQzJEO27d8B1Uejb7xAeohnsbyzmH7Uqyffn/GLYY0JZadapuf8EDZNsm8O8rsZQy/gwk4bKfDTCGgvZq1Np8gj2XLjYKrD9X5vZwJytW+l2FCpK3yXqOOZOdVckFt38QMrsllfcoPYoHAcB0qnZt9nNVWaVHxuNutpIkc++eiwoL5hxl5R2wSMmdRiyj+JYccMOHqSwBGuMTs15ga8wiXOfho0qN8PrudQlkEmY+3nh22SYiE+n7cbDQQT12Rvuovskz0ga2kOrEnhOx1S5HR+XeSuxHQhe1od6VQRKSt6JyOAMywUiOkmfaSnk91N9wzXxiL2Z40h5YPzmZr4984zFysj6dEbgR1OyAiqJ0ZTvAS1bOtCKw3AzQ29p0Rew6wodijxKqbkkELcL6tob87Q0ieRtpKGdlMYZvtRCZ2rapJvCsEelZZEUCuESuRz5KK3XNLr8LMW4mn6znMox0RzRkhbrPqTr+IBGMF/9+J760wIQPNKIk5MOs0GZtnZfeVAtLVsFmITZgYcWd6txQiER086PVHtoXVy/eKAVfxV4O6GUjnkEPVGYb6GbSafnjnLwqWcr37JPhvnlS1BkmwAu/LLU8Jw8LImXhpJoLdQRAVo32pZtzF18DAdY6aFfQTTNWpmsY5BYu6dXo7uMg9IqFDrq0nYO/45C55jxHhvKZ3Jq2Et64T/kM9PJ1gdQQIJ+THN0k3u4Xt9TBnDIKRwBJYp+tSEQ/o8Uq1X6JuHb9Ab3ZEJPzHJ3+vPb8nW/qNISxcRXPdseUC8OoR8L8qgiiiPswXQkLLpZOXpiQtuHgppl1gzDEOUSOAwAoOSAsbgv4u8K4xfe2V6Wxcu5SyV435UVnyeUmCTdJLnrZpWT+JrZe3b0XF5wGBa/D6ptfjoDZXUHO/gMYBwcpOlahwKg/eEFSQb7MWR/MAxcKN67ap8VI4Oy4Dve5lTnbiWbcOltJZfa/AhU+5eM5D6t/dKs7qzYUXEw48ETad+zdMlO1eAh4lhahsdl0Q04/yrXdOtYTC3rEC9RJLmHD4Mm9A=";

	    payresult ="EnAEAACAADqRrk62AKd3PXk3cNhmw49vKt7B3ESHLXdLLh9MYZClV+40erOZ6Wwr4H7tXdI42anOg4tVsS17DLilPZA+OgMbultxgcGz7svQHl1YJoyeq69ccBAS4WpfOAMeGhv4heLA7F5Jp+Pn61nUxha7EACJcMVYDSFhp4jqabRjZ5cEZYB0LWohyRM13hPoBQ8QJUZ1lNu7Mwdqr80Q2T0WwTmWsumLgQHGcs0WiFkMaqZT492QTFJ8EmalIFKlD7Wdc5EnMPl2mFOyGTpdaNgXOOhE+b3IDG3gpVxshNK02GogJj/oO2vy/F+LYoGtymGiGmi/d2l28GbIMbzRrpGOsF7VNrTUw87DNKzrP40lWVYmUamh/2dSsKsYnxEEDDkKHwD/EE6zAXyg/JUgU+mJd7kSYRpo+h6nxp4OhX+xq1cgArwzzznCp6gEwkbTK7En8Rkv4jA19VYz9D5OieKfGxLeS5tTGNcRiy1GDHFwo3ZtiiYri130kEeib6ZXOYB/s2PZiYSm4FWEy/owriJ2yEDYo9cXDzHpP415kF0ZX36JR2wbKnRjaji55m03Wsdu4K3mn/jeKBuhT/q4rrLwoH1DmlGO50p4L4G1B3YXhVc2BnQPEynBjIj5BA9eWicNCOyHicDrDTpByB0nONGPlMjlXg30bj7cH1z+y5frsyzfKq69T+2wROs15UTVj6sfy9L4Nm21wRpqkLV1eDVY2SbHabemsWpGtbZ81QNzuPKY5rIKLmzLFVXHnBIXGwIENOc4XO+iaulPUjXgjvGoEZ4HqbQPeQYLiAngGlVxcGD+slZjd+J4QlElN1UQqvf6izEH7S2/wkb1Hr64S/jYIIcMB+e0XkMRCrR57oWF7QR6OemPT0B2VwqIUVLfa4/NZi7FaXNmyBSOicLpEK9AV+3vUobm+3huWNHjC7bW92tjPuog/PkQGMDDE6mb759l0PoeKvOlZuJpEc+N1zO7BNKv/wkzxxrmaNsoutRO8BDmE2U7WhTkTSv+sjzFVwwEi2OfKFg5NVWKmw6JQuogma32j+q/hx9HQ+jGtMR4rh+vxtaWek9dcEg1q0CxvP4Y4nEbo4vxu3OWJCFHp3He598friqJgvyDMV4lYUDpbeMzpIHDDeUntVaFlD8q7B6ZXvqvc19UhpyQjtruXgm66JZkV7R7OXTD9LNUC0YYbFkHFpazGOa66FQj2gulfDtITpqk5uPCd6T1EOnJIvxHMsFxJOyol7z8XtrUkx/LlcN/t3LvfeXA5vJ6xGIs6SuZ3JrjgcbvWP9CY9/ubhiyLS/h65BbLW2CEuP41WKAjotfKvwxspnc4MHI6jAf34F4YFN0bWmhMmG++Ha9DCYlK/S4NglvwwcSHwoQ//iC6/zKREY4qJsbx/VuWxBljkYZ3XXbAA3ZaeYtuVYOLmXTL3lwxSfuIqJRCxtXWOI7+Ipge96Qd+IZWR/KCjTlaKP+e4fk3f0eCcu3FuSq8cXSh4mxjqhUl+FIK3rPSvhcB9JnI/HQb6MwQo7yemptK+CEouRicmeQaYKt+7xMD8+kXZY9crFb/l2BIdhviQ6srdRVU1DPFCWw5t3bRF/LiQwDrycneRc35ZOeSvl4YG6xTGF85dRm86dyPWfpqbfvliGoPeOmgswWUQAfAYWIQbuxBaGMy744oY9KHrg5nScLFDI=";	

		JnkyServer jnkyServer = null;
		String plainText = "";
		try {
			jnkyServer = getJnkyServer();// 初始化解密函数

			plainText = jnkyServer.DecryptData(payresult);
		} catch (Exception e) {
			e.printStackTrace();
		}// 解密
		System.out.println("解密后反馈信息>>>" + plainText);

		// 结果信息按照顺序，并且每项以“|”分隔：
		// 订单号|商户代码|交易金额 |交易日期|交易时间|订单状态
		// 例如：
		// 0001|1234|111.23|20021010|121212|01001|remark1|remark2

		Vector<String> vec = BankUtil.getVectorString(plainText, "|");

		String billNo = (String) vec.get(0);
		String corpID = (String) vec.get(1);
		String txAmt = (String) vec.get(2);
		String txDate = (String) vec.get(3);
		String txTime = (String) vec.get(4);
		String billstatus = (String) vec.get(5);
		System.out.println("---" + billstatus);
		String Billremark1 = (String) vec.get(6);
		String Billremark2 = (String) vec.get(7);

		cmbcResult = new CmbcB2CResultFromBank();
		cmbcResult.setBillNo(billNo);
		cmbcResult.setCorpID(corpID);
		cmbcResult.setTxAmt(txAmt);
		cmbcResult.setTxDate(txDate);
		cmbcResult.setTxTime(txTime);
		cmbcResult.setBillstatus(billstatus);
		cmbcResult.setBillremark1(Billremark1);
		cmbcResult.setBillremark2(Billremark2);
		// -----------------
		cmbcResult.setROrderNo(billNo.substring(5));// 去除前五位商户号
		cmbcResult.setRAmount(BankUtil.getBigDecimalByString(txAmt));

	}

	public static JnkyServer getJnkyServer() throws AppException {
		Union.JnkyServer jnkyServer = null;
		byte[] byteMerPfx = null;
		byte[] byteMerCer = null;

		final String keyPassword = "1111";// 商户私钥的口令
		final String merCerFile = "E:\\cmbcTest\\cmbc-base64.cer";
		final String merPfxFile = "E:\\cmbcTest\\CMBC0630.pfx";

		System.out.println("merCerFile:" + merCerFile);
		System.out.println("merPfxFile:" + merPfxFile);

		byteMerCer = BankUtil.readFileAsByteArray(merCerFile).toByteArray();
		byteMerPfx = BankUtil.readFileAsByteArray(merPfxFile).toByteArray();

		try {
			jnkyServer = new Union.JnkyServer(byteMerCer, byteMerPfx,
					keyPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jnkyServer;
	}

	public static void test3() {
		String str = "12345abc";
		System.out.println(str.substring(5));
	}

	public static void test2() {
		try {
			String str = "123你好";
			System.out.println(str);
			byte[] byteArray1 = str.getBytes();
			byte[] byteArray2 = str.getBytes("GBK");
			byte[] byteArray3 = str.getBytes("utf-8");
			System.out.println("byte1Length--" + byteArray1.length
					+ "--byte2Length--" + byteArray2.length + "--byte3Length--"
					+ byteArray3.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test1() {
		String myCer = "MIIDqjCCAxOgAwIBAgIEP+vHoDANBgkqhkiG9w0BAQUFADApMQswCQYDVQQGEwJDTjEaMBgGA1UEChMRQ0ZDQSBPcGVyYXRpb24gQ0EwHhcNMDYxMjI3MDY1MzQyWhcNMDgxMjI3MDcyMzQyWjBmMQswCQYDVQQGEwJDTjEaMBgGA1UEChMRQ0ZDQSBPcGVyYXRpb24gQ0ExDTALBgNVBAsTBENNQkMxEjAQBgNVBAsTCUN1c3RvbWVyczEYMBYGA1UEAxQPOTJjbWJjQDAwMDAwMDAxMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZfvEjA4u56u0aKfhIYwm6xsrZBKsRhiikE2YoiQ9XqUkAmPNKBgE9NvN5BPEVXEHRMweXtYRLBLdCmPP/nkJ7cCFKuVMPsvRWk4RHOrDspAzPQqhbM66IEDam1a+I2lR8B6kmMveBu5IMqvG+bsMS3oHAPR32Wm4FuvieKIp3eQIDAQABo4IBoDCCAZwwCwYDVR0PBAQDAgXgMCsGA1UdEAQkMCKADzIwMDYxMjI3MDY1MzQyWoEPMjAwODEyMjcwNzIzNDJaMBEGCWCGSAGG+EIBAQQEAwIFoDApBglghkgBhvhCAQIEHBYaaHR0cHM6Ly9TSVRFX05BTUUvY2RhLWNnaS8wSwYJYIZIAYb4QgEDBD4WPGNsaWVudGNnaS5leGU/YWN0aW9uPWNoZWNrUmV2b2NhdGlvbiYmQ1JMPWNuPUNSTDE3ODgmc2VyaWFsPTAfBgNVHREEGDAWgRR3YW5ndGluZ0BjbWJjLmNvbS5jbjBOBgNVHR8ERzBFMEOgQaA/pD0wOzELMAkGA1UEBhMCQ04xGjAYBgNVBAoTEUNGQ0EgT3BlcmF0aW9uIENBMRAwDgYDVQQDEwdDUkwxNzg4MB8GA1UdIwQYMBaAFMMnxjZoyCdlJIevkadLJjMC5RrpMB0GA1UdDgQWBBRElMUdhslGUAzD2INiYSm0UAbIPzAJBgNVHRMEAjAAMBkGCSqGSIb2fQdBAAQMMAobBFY2LjADAgOoMA0GCSqGSIb3DQEBBQUAA4GBAHtlC5eatxZAsHZcmz2kHPxT9pim4cMyOXB+aVGbxrejUly9bUXDPwrAk2qxLtPfbh+ndnwPsxP/+SNtfIok0NbO+LzM2npqA0G2WbyLnjtuFQINMtof+7G4LUvpEXQpJ0WHZO8manwSQv/Tm3AWxUwH8khn12MZk3a/xz7QVHgv"
				.trim();
		String ieCer = "MIIDqjCCAxOgAwIBAgIEP+vHoDANBgkqhkiG9w0BAQUFADApMQswCQYDVQQGEwJDTjEaMBgGA1UEChMRQ0ZDQSBPcGVyYXRpb24gQ0EwHhcNMDYxMjI3MDY1MzQyWhcNMDgxMjI3MDcyMzQyWjBmMQswCQYDVQQGEwJDTjEaMBgGA1UEChMRQ0ZDQSBPcGVyYXRpb24gQ0ExDTALBgNVBAsTBENNQkMxEjAQBgNVBAsTCUN1c3RvbWVyczEYMBYGA1UEAxQPOTJjbWJjQDAwMDAwMDAxMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZfvEjA4u56u0aKfhIYwm6xsrZBKsRhiikE2YoiQ9XqUkAmPNKBgE9NvN5BPEVXEHRMweXtYRLBLdCmPP/nkJ7cCFKuVMPsvRWk4RHOrDspAzPQqhbM66IEDam1a+I2lR8B6kmMveBu5IMqvG+bsMS3oHAPR32Wm4FuvieKIp3eQIDAQABo4IBoDCCAZwwCwYDVR0PBAQDAgXgMCsGA1UdEAQkMCKADzIwMDYxMjI3MDY1MzQyWoEPMjAwODEyMjcwNzIzNDJaMBEGCWCGSAGG+EIBAQQEAwIFoDApBglghkgBhvhCAQIEHBYaaHR0cHM6Ly9TSVRFX05BTUUvY2RhLWNnaS8wSwYJYIZIAYb4QgEDBD4WPGNsaWVudGNnaS5leGU/YWN0aW9uPWNoZWNrUmV2b2NhdGlvbiYmQ1JMPWNuPUNSTDE3ODgmc2VyaWFsPTAfBgNVHREEGDAWgRR3YW5ndGluZ0BjbWJjLmNvbS5jbjBOBgNVHR8ERzBFMEOgQaA/pD0wOzELMAkGA1UEBhMCQ04xGjAYBgNVBAoTEUNGQ0EgT3BlcmF0aW9uIENBMRAwDgYDVQQDEwdDUkwxNzg4MB8GA1UdIwQYMBaAFMMnxjZoyCdlJIevkadLJjMC5RrpMB0GA1UdDgQWBBRElMUdhslGUAzD2INiYSm0UAbIPzAJBgNVHRMEAjAAMBkGCSqGSIb2fQdBAAQMMAobBFY2LjADAgOoMA0GCSqGSIb3DQEBBQUAA4GBAHtlC5eatxZAsHZcmz2kHPxT9pim4cMyOXB+aVGbxrejUly9bUXDPwrAk2qxLtPfbh+ndnwPsxP/+SNtfIok0NbO+LzM2npqA0G2WbyLnjtuFQINMtof+7G4LUvpEXQpJ0WHZO8manwSQv/Tm3AWxUwH8khn12MZk3a/xz7QVHgv"
				.trim();
		System.out.println(myCer.equals(ieCer));
	}
}
