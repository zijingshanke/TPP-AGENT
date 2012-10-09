package com.fordays.fdpay.bank.icbc.cbst.util;

import java.io.File;

import com.fordays.fdpay.bank.LogUtil;

/**
 * 银商转帐系统接口日志
 */
public class CbstLogUtil extends LogUtil {
	@SuppressWarnings("unchecked")
	public CbstLogUtil(boolean isSystemOut, boolean isPrintClass,
			Class classIn, String description) {
		super(isSystemOut, isPrintClass, classIn, description);
	}

	/**
	 * 重写父类的初始化方法
	 */
	@SuppressWarnings("unchecked")
	public void init(boolean isSystemOut, boolean isPrintClass, Class useClass) {
		setInputValue(isSystemOut, isPrintClass, useClass);

		String cbstLogFilePath = File.separator + "opt" + File.separator
				+ "IBM" + File.separator + "WebSphere" + File.separator
				+ "AppServer" + File.separator + "profiles" + File.separator
				+ "AppSrv01" + File.separator + "logs" + File.separator
				+ "cbstlog";// 设置日志目录

		// cbstLogFilePath="E:\\cbstlog";//测试用

		String cbstLogName = "cbst.log.";// 名称项目

		arrangePrintLog(cbstLogFilePath, cbstLogName);
	}
}