package com.fordays.fdpay.cooperate;

import java.io.File;

import com.fordays.fdpay.bank.LogUtil;

/**
 * 钱门商户接口日志
 */
public class CooperateLogUtil extends LogUtil {

	@SuppressWarnings("unchecked")
	public CooperateLogUtil(boolean isSystemOut, boolean isPrintClass,
			Class classIn, String des) {
		super(isSystemOut, isPrintClass, classIn, des);
	}

	@SuppressWarnings("unchecked")
	public void init(boolean isSystemOut, boolean isPrintClass, Class useClass) {
		setInputValue(isSystemOut, isPrintClass, useClass);

		String cbstLogFilePath = File.separator + "opt" + File.separator
				+ "IBM" + File.separator + "WebSphere" + File.separator
				+ "AppServer" + File.separator + "profiles" + File.separator
				+ "AppSrv01" + File.separator + "logs" + File.separator
				+ "cooperatelog";// 设置日志目录;
		String cbstLogName = "cooperate.log";// 名称项目

		arrangePrintLog(cbstLogFilePath, cbstLogName);
	}
}
