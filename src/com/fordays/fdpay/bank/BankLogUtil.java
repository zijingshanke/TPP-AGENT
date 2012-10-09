package com.fordays.fdpay.bank;

import java.io.File;

/**
 * 银行支付接口日志
 */
public class BankLogUtil extends LogUtil {

	/**
	 * 没有重载，仍然使用LogUtil默认构造
	 */
	@SuppressWarnings("unchecked")
	public BankLogUtil(boolean isSystemOut, boolean isPrintClass, Class classIn) {
		super(isSystemOut, isPrintClass, classIn);
	}

	@SuppressWarnings("unchecked")
	public void init(boolean isSystemOut, boolean isPrintClass, Class useClass) {
		setInputValue(isSystemOut, isPrintClass, useClass);

		String cbstLogFilePath = File.separator + "opt" + File.separator
				+ "IBM" + File.separator + "WebSphere" + File.separator
				+ "AppServer" + File.separator + "profiles" + File.separator
				+ "AppSrv01" + File.separator + "logs" + File.separator
				+ "banklog";// 设置日志目录;
		String cbstLogName = "bank.";// 名称项目

		arrangePrintLog(cbstLogFilePath, cbstLogName);
	}
}
