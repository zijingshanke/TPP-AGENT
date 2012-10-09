//package com.hitrust.b2b.util;
//
//import com.hitrust.b2b.request.TrxException;
//import com.neza.base.Constant;
//import com.sun.net.ssl.*;
//import com.sun.net.ssl.internal.ssl.Provider;
//import java.io.FileInputStream;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.cert.Certificate;
//import java.security.cert.X509Certificate;
//import java.util.*;
//import javax.net.ssl.SSLSocketFactory;
//import org.apache.log4j.Category;
//import org.apache.log4j.PropertyConfigurator;
//
///**
// * 中国民生银行B2B商户配置类
// * 
// * @修改人:YAN RUI
// * @修改备注：
// * @1、使配置文件TrustMerchant.properties支持WEB-INF目录
// * @String filePath = Constant.WEB_INFO_PATH+getParameterByName("filePath");
// * @2、修改资源文件读取路径 TrustMerchant--->com.fordays.fdpay.bank.cmbc.biz.CMBCB2BTrustMerchant
// * @3、MERCHANT_ENCODING = "GBK"--->UTF-8
// */
//public class MerchantConfig {
//	static Category LOG = Category.getInstance("MerchantConfig");
//	public static final String RESOURCE_NAME = "com.fordays.fdpay.bank.cmbc.biz.CMBCB2BTrustMerchant";
//	public static final String LOG_RESOURCE_NAME = "com.fordays.fdpay.bank.cmbc.biz.log";
//	public static final String MERCHANT_ENCODING = "UTF-8";
//	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
//	public static final String KEY_STORE_TYPE_FILE = "0";
//	public static final String KEY_STORE_TYPE_SIGN_SERVER = "1";
//	public static final String KEY_STORE_TYPE_OTHERS = "3";
//	private static boolean bundleStatus = false;
//	private static ResourceBundle iResourceBundle;
//	private static String iKeyStoreType = "";
//	private static String iMerchantID = "";
//	private static String iMerchantCertificate = "";
//	private static PrivateKey iMerchantKey = null;
//	private static String iTrustPayConnectMethod = "http";
//	private static String iTrustPayServerName = "";
//	private static int iTrustPayServerPort = 0;
//	private static String iTrustPayTrxURL = "";
//	private static String iNewLine = "1";
//	private static SSLSocketFactory iSSLSocketFactory = null;
//	private static String iMerchantPayType = "";
//
//	public MerchantConfig() {
//	}
//
//	private static void bundle() throws TrxException {
//		try {
//			if (!bundleStatus) {
//				iResourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);
//				System.out.println("CMBCB2B MerchantRESOURCE:" + RESOURCE_NAME);
//				try {
//					ResourceBundle rs = ResourceBundle
//							.getBundle(LOG_RESOURCE_NAME);
//					System.out.println("CMBCB2B LogRESOURCE:"
//							+ LOG_RESOURCE_NAME);
//					if (rs == null)
//						throw new Exception("log.properties not found");
//					Enumeration keyEmu = rs.getKeys();
//					Properties props = new Properties();
//					String key;
//					for (; keyEmu.hasMoreElements(); props.put(key, rs
//							.getString(key)))
//						key = (String) keyEmu.nextElement();
//
//					PropertyConfigurator.configure(props);
//				} catch (Exception e1) {
//					LOG.debug("配置LOG4J时发生异常! ");
//					LOG.error("Exception : " + e1.getMessage());
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 商户日志目录[LogPath]配置错误！");
//				}
//				iTrustPayConnectMethod = getParameterByName("TrustPayConnectMethod");
//				if (iTrustPayConnectMethod.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 网上支付平台通讯方式[TrustPayConnectMethod]配置错误！");
//				iTrustPayServerName = getParameterByName("TrustPayServerName");
//				if (iTrustPayServerName.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 网上支付平台服务器IP[TrustPayServerName]配置错误！");
//				String tTrustPayServerPort = getParameterByName("TrustPayServerPort");
//				if (tTrustPayServerPort.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 网上支付平台交易端口[TrustPayServerPort]配置错误！");
//				try {
//					iTrustPayServerPort = Integer.parseInt(tTrustPayServerPort);
//				} catch (Exception e) {
//					LOG.debug("读取支付平台端口时发生异常! ");
//					LOG.error("Exception : " + e.getMessage());
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 网上支付平台交易端口[TrustPayServerPort]配置错误！");
//				}
//				iTrustPayTrxURL = getParameterByName("TrustPayTrxURL");
//				if (iTrustPayTrxURL.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 网上支付平台交易网址[TrustPayTrxURL]配置错误！");
//				String tNewLine = getParameterByName("TrustPayNewLine");
//				if (tNewLine.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 网上支付平台接口特性[TrustPayNewLine]配置错误！");
//				if (tNewLine.equals("1"))
//					iNewLine = "\n";
//				else
//					iNewLine = "\r\n";
//				initSSL();
//				iMerchantID = getParameterByName("MerchantID");
//				if (iMerchantID.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 商户号[MerchantID]配置错误！");
//				iMerchantPayType = getParameterByName("MerchantPayType");
//				if (iMerchantPayType.length() == 0)
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 商户号[MerchantPayType]配置错误！");
//				iKeyStoreType = getParameterByName("MerchantKeyStoreType");
//				if (iKeyStoreType.equals("0"))
//					bindMerchantCertificateByFile();
//				else if (!iKeyStoreType.equals("1"))
//					throw new TrxException("1001",
//							"商户端配置文件中参数设置错误 - 证书储存媒体配置错误！");
//				bundleStatus = true;
//			}
//		} catch (Exception e) {
//			LOG.debug("初始化系统失败! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1000", "无法读取商户端配置文件", e.getMessage());
//		}
//	}
//
//	private static void initSSL() throws TrxException {
//		try {
//			java.security.Provider tProvider = new Provider();
//			SSLContext tSSLContext = SSLContext.getInstance("TLS", tProvider);
//			TrustManagerFactory tTrustManagerFactory = TrustManagerFactory
//					.getInstance("SunX509", tProvider);
//			KeyStore tKeyStore = KeyStore.getInstance("JKS");
//			String storeFileUrl = Constant.WEB_INFO_PATH
//					+ getParameterByName("TrustStoreFile");
//			System.out.println("CMBCB2B trustStoreFile:" + storeFileUrl);
//			tKeyStore.load(new FileInputStream(storeFileUrl),
//					getParameterByName("TrustStorePassword").toCharArray());
//			tTrustManagerFactory.init(tKeyStore);
//			com.sun.net.ssl.TrustManager tTrustManager[] = tTrustManagerFactory
//					.getTrustManagers();
//			KeyManagerFactory tKeyManagerFactory = KeyManagerFactory
//					.getInstance("SunX509", tProvider);
//			KeyStore kKeyStore = KeyStore.getInstance("PKCS12");
//
//			String merchantFileUrl = Constant.WEB_INFO_PATH
//					+ getParameterByName("MerchantCertFile");
//			System.out.println("CMBCB2B MerchantCertFile:" + merchantFileUrl);
//			kKeyStore.load(new FileInputStream(merchantFileUrl),
//					getParameterByName("MerchantCertPassword").toCharArray());
//			tKeyManagerFactory.init(kKeyStore, getParameterByName(
//					"MerchantCertPassword").toCharArray());
//			com.sun.net.ssl.KeyManager tKeyManager[] = tKeyManagerFactory
//					.getKeyManagers();
//			tSSLContext.init(tKeyManager, tTrustManager, null);
//			iSSLSocketFactory = tSSLContext.getSocketFactory();
//		} catch (Exception e) {
//			LOG.debug("初始化SSL SocketFactory时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1999", "系统发生无法预期的错误", e.getMessage());
//		}
//	}
//
//	private static void bindMerchantCertificateByFile() throws TrxException {
//		LOG.debug("begin bindMerchantCertificateByFile");
//		String tMerchantCertFile = Constant.WEB_INFO_PATH
//				+ getParameterByName("MerchantCertFile");
//		if (tMerchantCertFile.length() == 0)
//			throw new TrxException("1001", "商户端配置文件中参数设置错误",
//					"商户证书储存目录档名[MerchantCertFile]配置错误！");
//		String tMerchantCertPassword = getParameterByName("MerchantCertPassword");
//		if (tMerchantCertFile.length() == 0)
//			throw new TrxException("1001", "商户端配置文件中参数设置错误",
//					"商户私钥加密密码[MerchantCertPassword]配置错误！");
//		KeyStore tKeyStore = null;
//		try {
//			FileInputStream tIn = new FileInputStream(tMerchantCertFile);
//			tKeyStore = KeyStore.getInstance("PKCS12", (new Provider())
//					.getName());
//			tKeyStore.load(tIn, tMerchantCertPassword.toCharArray());
//			tIn.close();
//		} catch (Exception e) {
//			LOG.debug("读取商户证书文件时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1002", "无法读取证书文档", e.getMessage());
//		}
//		Certificate tCert = null;
//		String tAliases = "";
//		try {
//			for (Enumeration e = tKeyStore.aliases(); e.hasMoreElements();) {
//				tAliases = (String) e.nextElement();
//				break;
//			}
//
//			tCert = tKeyStore.getCertificate(tAliases);
//			Base64 tBase64 = new Base64();
//			iMerchantCertificate = tBase64.encode(tCert.getEncoded());
//		} catch (Exception e) {
//			LOG.debug("生成商户证书对象时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1006", "证书格式错误", e.getMessage());
//		}
//		try {
//			X509Certificate tX509Cert = (X509Certificate) tCert;
//			tX509Cert.checkValidity();
//		} catch (Exception e) {
//			LOG.debug("验证商户证书有效期时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1005", "证书过期", e.getMessage());
//		}
//		try {
//			iMerchantKey = (PrivateKey) tKeyStore.getKey(tAliases,
//					tMerchantCertPassword.toCharArray());
//		} catch (Exception e) {
//			LOG.debug("取出商户密钥时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1003", "无法读取商户私钥", e.getMessage());
//		}
//	}
//
//	public static String getParameterByName(String aParamName)
//			throws TrxException {
//		if (iResourceBundle == null)
//			bundle();
//		String tValue = null;
//		try {
//			tValue = iResourceBundle.getString(aParamName).trim();
//			if (tValue.equals(""))
//				throw new TrxException("1001", "商户端配置文件中参数设置错误", " - 未设定["
//						+ aParamName + "]参数值!");
//		} catch (Exception e) {
//			LOG.debug("取出配置文件中指定参数值时发生异常! ");
//			LOG.error("Exception : " + e.getMessage());
//			throw new TrxException("1001", "商户端配置文件中参数设置错误", " - 无["
//					+ aParamName + "]参数!");
//		}
//		return tValue;
//	}
//
//	public static String getMerchantID() throws TrxException {
//		bundle();
//		return iMerchantID;
//	}
//
//	public static String getMerchantCertificate() throws TrxException {
//		bundle();
//		return iMerchantCertificate;
//	}
//
//	public static PrivateKey getMerchantKey() throws TrxException {
//		bundle();
//		return iMerchantKey;
//	}
//
//	public static String getTrustPayConnectMethod() throws TrxException {
//		bundle();
//		return iTrustPayConnectMethod;
//	}
//
//	public static String getKeyStoreType() throws TrxException {
//		bundle();
//		return iKeyStoreType;
//	}
//
//	public static String getTrustPayServerName() throws TrxException {
//		bundle();
//		return iTrustPayServerName;
//	}
//
//	public static int getTrustPayServerPort() throws TrxException {
//		bundle();
//		return iTrustPayServerPort;
//	}
//
//	public static String getTrustPayNewLine() throws TrxException {
//		bundle();
//		return iNewLine;
//	}
//
//	public static String getTrustPayTrxURL() throws TrxException {
//		bundle();
//		return iTrustPayTrxURL;
//	}
//
//	public static SSLSocketFactory getSSLSocketFactory() throws TrxException {
//		bundle();
//		return iSSLSocketFactory;
//	}
//
//	public static String getMerchantPayType() throws TrxException {
//		bundle();
//		return iMerchantPayType;
//	}
//
//	public static String getPressureTestFlag() throws TrxException {
//		if (iResourceBundle == null)
//			bundle();
//		String tValue = null;
//		tValue = iResourceBundle.getString("PressureTest");
//		return tValue;
//	}
//
//	public static void main(String args[]) {
//		try {
//			System.out.println("-" + getMerchantCertificate() + "-");
//		} catch (TrxException e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//}