package com.fordays.fdpay.bank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import com.fordays.fdpay.bank.util.FileUtil;

/**
 * 轻量日志工具
 * 
 * @author YanRui
 */
public class LogUtil extends FileUtil{
	@SuppressWarnings("unchecked")
	private Class classIn = null;
	private PrintWriter logWriter = null;// 写日志类

	// 文件属性
	private String logFilePath = "";// 日志目录
	private String logName = ""; // 文件名称项
	private String logFile = ""; // 目标日志文件完整路径

	// 日志内容属性
	private boolean isAppend = true;// 是否在原来的文件上追加
	private boolean iSystemOut = false;// 是否在控制台显示
	public boolean iPrintClass = false;// 是否打印使用LogUtil的类

	// 日志内容项
	private String dateStr = "";//
	private String className = "None"; // 使用LogUtil的类的名字

	// 日志等级常数
	private static final int logLevel_Error = 1;// 日志等级,错误信息
	private static final int logLevel_Info = 2;// 日志等级,一般信息
	private static final int logLevel_Detail = 3;// 日志等级,详细信息

	/**
	 * 单独使用，保持原银行支付接口日志工具类基础
	 * 
	 * @param boolean
	 *            isSystemOut 是否在console打印日志
	 * @param boolean
	 *            isPrintClass 是否打印当前路径
	 * @param Class
	 *            userClass 当前类
	 */
	@SuppressWarnings("unchecked")
	public LogUtil(boolean isSystemOut, boolean isPrintClass, Class classIn) {
		initDefault(isSystemOut, isPrintClass, classIn);
	}

	/**
	 * 扩展调用，init()将在子类中重写
	 */
	@SuppressWarnings("unchecked")
	public LogUtil(boolean isSystemOut, boolean isPrintClass,
			Class classIn, String description) {
		init(isSystemOut, isPrintClass, classIn);
	}

	/**
	 * 日志初始化
	 * 
	 * @param boolean
	 *            isSystemOut
	 * @param boolean
	 *            isPrintClass
	 * @param boolean
	 *            useClass
	 */
	@SuppressWarnings("unchecked")
	public void initDefault(boolean isSystemOut, boolean isPrintClass,
			Class useClass) {
		setInputValue(isSystemOut, isPrintClass, useClass);
		setDefaultLogPathValue();// ---------------

		setLogFile(logFilePath, logName);

		setLogWriter(logFile);
	}

	/**
	 * 具体方法将在子类同名方法中重写
	 */
	@SuppressWarnings("unchecked")
	public void init(boolean isSystemOut, boolean isPrintClass, Class useClass) {
	}

	/**
	 * 准备写日志
	 * 
	 * @param String
	 *            日志路径
	 * @param String
	 *            部分文件名
	 */
	public void arrangePrintLog(String logFilePath, String logName) {
		logFile = setLogFile(logFilePath, logName);

		autoIncrement(logFile, 2000);

		setLogWriter(logFile);
	}

	/**
	 * 设置默认LogPath,保持之前版本的兼容
	 */
	public void setDefaultLogPathValue() {
		if ("".equals(logFilePath)) {
			logFilePath = File.separator + "opt" + File.separator + "IBM"
					+ File.separator + "WebSphere" + File.separator
					+ "AppServer" + File.separator + "profiles"
					+ File.separator + "AppSrv01" + File.separator + "logs"
					+ File.separator + "banklog";
		}

		if ("".equals(logName)) {
			logName = "bank.log.";
		}
	}

	/**
	 * 设置LogWriter
	 * @param String 文件全路径
	 */
	private void setLogWriter(String logFile) {
		boolean isAppend = true;
		try {
			if (isAppend) // 是否在原来的文件上追加
				logWriter = new PrintWriter(new BufferedWriter(new FileWriter(
						logFile, true)), true);
			else
				logWriter = new PrintWriter(new BufferedWriter(new FileWriter(
						logFile, false)), true);
		} catch (Exception e) {
			System.out.println("logWriter initlizes failure!" + e.getMessage());
		}
	}

	/**
	 * 设置日志文件完整路径
	 * 
	 * @param String
	 *            日志文件所在目录
	 * @param String
	 *            日志文件名(部分)
	 */
	private String setLogFile(String logFilePath, String logName) {
		File myLogFile = new File(logFilePath);
		if (!myLogFile.exists()) {
			myLogFile.mkdirs();
		}
		logFile = logFilePath + File.separator + logName
				+ getDateStr("yyyyMMdd") + ".log";

		return logFile;
	}

	/**
	 * 设置初始化选项
	 */
	@SuppressWarnings("unchecked")
	public void setInputValue(boolean isSystemOut, boolean isPrintClass,
			Class useClass) {
		classIn = useClass;
		iSystemOut = isSystemOut;
		iPrintClass = isPrintClass;
	}



	/**
	 * 写日志
	 * 
	 * @param logLevel
	 *            等级 1：错误信息 2：一般信息
	 * @param logInfo
	 *            log信息
	 */
	private void log(int logLevel, String logInfo) {
		logInfo = getLogInfo(logLevel, logInfo, classIn);//		
		try {
			if (iSystemOut) {
				System.out.println(logInfo);
			}
			logWriter.print(logInfo);
			logWriter.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String getLogInfo(int logLevel, String logInfo, Class classIn) {
		if (logInfo == null || "".equals(logInfo)) {
			logInfo = "getLogInfo(int,String,Class)无";
		}

		if (checkLogLevel(logLevel)) {// 检查日志等级设置
			String printLocus = "";

			if (iPrintClass) {
				className = classIn.getName();
				printLocus = "类:" + className + " ";
			}

			dateStr = getDateStr("yyyy-MM-dd HH:mm:ss");

			if (logLevel == logLevel_Error) {
				logInfo = printLocus + "[错误:" + dateStr + "] " + logInfo;
			} else if (logLevel == logLevel_Info) {
				logInfo = printLocus + "[" + dateStr + "] " + logInfo;
			} else if (logLevel == logLevel_Detail) {
				logInfo = printLocus + "[详细信息:" + dateStr + "] " + logInfo;
			} else {
				logInfo = LogUtil.class.getName() + "打印日志未正确设置日志信息等级 "
						+ dateStr + "] " + logInfo;
			}
		} else {
			logInfo = LogUtil.class.getName() + "打印日志未正确设置日志信息等级 "
					+ dateStr + "] " + logInfo;
		}
		return logInfo;
	}

	/**
	 * 检查日志等级
	 */
	public boolean checkLogLevel(int logLevel) {
		return true;
	}

	public String getDateStr(String dateType) {
		SimpleDateFormat dateFormat = null; // 日期格式
		java.sql.Timestamp time = new java.sql.Timestamp(System
				.currentTimeMillis());// Timestamp

		dateFormat = new SimpleDateFormat(dateType);
		dateStr = dateFormat.format(time);
		return dateStr;
	}

	/**
	 * 写错误信息
	 * 
	 * @param logInfo
	 *            String
	 */
	public void error(String logInfo) {
		log(logLevel_Error, logInfo);
	}

	/**
	 * 写一般信息
	 * 
	 * @param logInfo
	 *            String
	 */
	public void info(String logInfo) {
		if (logInfo == null || "".equals(logInfo)) {
			logInfo = "无";
		}

		log(logLevel_Info, logInfo);
	}

	/**
	 * 写调试信息
	 * 
	 * @param logInfo
	 *            String
	 */
	public void detail(String logInfo) {
		log(logLevel_Detail, logInfo);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public PrintWriter getLogWriter() {
		return logWriter;
	}

	public void setLogWriter(PrintWriter logWriter) {
		this.logWriter = logWriter;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public boolean isAppend() {
		return isAppend;
	}

	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	public static int getLogLevel_Error() {
		return logLevel_Error;
	}

	public static int getLogLevel_Info() {
		return logLevel_Info;
	}

	public static int getLogLevel_Detail() {
		return logLevel_Detail;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public boolean isISystemOut() {
		return iSystemOut;
	}

	public void setISystemOut(boolean systemOut) {
		iSystemOut = systemOut;
	}

	public boolean isIPrintClass() {
		return iPrintClass;
	}

	public void setIPrintClass(boolean printClass) {
		iPrintClass = printClass;
	}

	public String getDateStr() {
		return dateStr;
	}

	@SuppressWarnings("unchecked")
	public Class getClassIn() {
		return classIn;
	}

	@SuppressWarnings("unchecked")
	public void setClassIn(Class classIn) {
		this.classIn = classIn;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}
}
