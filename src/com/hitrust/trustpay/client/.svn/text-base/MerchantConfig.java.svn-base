package com.hitrust.trustpay.client;

import com.fordays.fdpay.bank.ssl.util.SSLUtil;
import com.neza.base.Constant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.net.ssl.SSLSocketFactory;

/**
 * 农业银行网上支付平台B2C商户配置类
 * 
 * @修改人:YAN RUI
 * @修改备注：使配置文件TrustMerchant.properties支持WEB-INF目录
 * @String filePath = Constant.WEB_INFO_PATH+getParameterByName("filePath");
 */
public class MerchantConfig {

	private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	public static final String KEY_STORE_TYPE_FILE = "0";
	public static final String KEY_STORE_TYPE_SIGN_SERVER = "1";
	public static final String KEY_STORE_TYPE_OTHERS = "3";
	private static String iKeyStoreType = "0";
	private static final String RESOURCE_NAME = "com.fordays.fdpay.bank.abc.biz.TrustMerchantB2C";
	private static boolean iIsInitialed = false;
	private static ResourceBundle iResourceBundle = null;
	private static String iMerchantID = "";
	private static String iMerchantCertificate = "";
	private static PrivateKey iMerchantKey = null;
	private static String iTrustPayConnectMethod = "http";
	private static String iTrustPayServerName = "";
	private static int iTrustPayServerPort = 0;
	private static String iTrustPayTrxURL = "";
	private static String iNewLine = "1";
	private static SSLSocketFactory iSSLSocketFactory = null;
	private static Certificate iTrustpayCertificate = null;
	private static String iLogPath = "";

	// 以下属性是钱门扩展，改造initSSL(),以适应不同JSSE
	private static String iTrustFilePath = "";
	private static String iTrustStorePass = "";
	private static String iTrustAlgorithm = "";
	private static String iTrustProvider = "";

	// ------------------------------------

	public MerchantConfig() throws TrxException {
		bundle();
	}

	private static void bundle() throws TrxException {
		if (!iIsInitialed) {
			// System.out.println("[Trustpay商户端API] - 初始 - 开始============");
			try {
				// System.out.println("资源文件路径=" + RESOURCE_NAME);
				iResourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);
			} catch (Exception e) {
				System.out.println("无法读取商户端配置文件");
				throw new TrxException("1000", "无法读取商户端配置文件");
			}
			// System.out.println("读取系统配置文件");

			iTrustPayConnectMethod = getParameterByName("TrustPayConnectMethod");
			if (iTrustPayConnectMethod.length() == 0)
				throw new TrxException("1001",
						"通讯方式[TrustPayConnectMethod]配置错误！");
			// System.out.println("平台通讯方式 = [" + iTrustPayConnectMethod + "]");
			iTrustPayServerName = getParameterByName("TrustPayServerName");
			if (iTrustPayServerName.length() == 0)
				throw new TrxException("1001", "服务器IP[TrustPayServerName]配置错误！");
			// System.out.println("服务器IP = [" + iTrustPayServerName + "]");
			String tTrustPayServerPort = getParameterByName("TrustPayServerPort");
			if (tTrustPayServerPort.length() == 0)
				throw new TrxException("1001", "交易端口[TrustPayServerPort]配置错误！");
			try {
				iTrustPayServerPort = Integer.parseInt(tTrustPayServerPort);
			} catch (Exception e) {
				throw new TrxException("1001", "交易端口[TrustPayServerPort]配置错误！");
			}
			// System.out.println("交易端口 = [" + tTrustPayServerPort + "]");
			iTrustPayTrxURL = getParameterByName("TrustPayTrxURL");
			if (iTrustPayTrxURL.length() == 0)
				throw new TrxException("1001", "交易网址[TrustPayTrxURL]配置错误！");
			// System.out.println("交易网址 = [" + iTrustPayTrxURL + "]");
			String tNewLine = getParameterByName("TrustPayNewLine");
			if (tNewLine.length() == 0)
				throw new TrxException("1001", "接口特性[TrustPayNewLine]配置错误！");
			if (tNewLine.equals("1"))
				iNewLine = "\n";
			else
				iNewLine = "\r\n";
			// System.out.println("接口特性 = [" + tNewLine + "]");

			String tTrustPayCertFile = Constant.WEB_INFO_PATH
					+ getParameterByName("TrustPayCertFile").trim();
			// System.out.println("平台证书 = [" + tTrustPayCertFile + "]");

			if (tTrustPayCertFile.length() == 0)
				throw new TrxException("1001", "平台证书[tTrustPayCertFile]配置错误！");
			iTrustpayCertificate = getCertificate(tTrustPayCertFile);

			iLogPath = Constant.WEB_INFO_PATH
					+ getParameterByName("LogPath").trim();
			// System.out.println("日志文件目录 = [" + iLogPath + "]");
			if (iLogPath.length() == 0)
				throw new TrxException("1001", "商户日志目录[LogPath]配置错误！");

			initSSL();

			iMerchantID = getParameterByName("MerchantID");
			// System.out.println("商户编号 = [" + iMerchantID + "]");

			if (iMerchantID.length() == 0)
				throw new TrxException("1001", "商户号[MerchantID]配置错误！");

			iKeyStoreType = getParameterByName("MerchantKeyStoreType");
			if (iKeyStoreType.equals("0"))
				bindMerchantCertificateByFile();
			else if (!iKeyStoreType.equals("1"))
				throw new TrxException("1001", "证书储存媒体配置错误！");
			// System.out.println("商户证书及私钥初始完成");
			iIsInitialed = true;
			// System.out.println("[Trustpay商户端API] - 初始 - 完成======");
		}
	}

	/**
	 * 初始化SSL会话
	 * 
	 * @author YanRui 修改为无JSSE版本依赖
	 */
	private static void initSSL() throws TrxException {
		try {
			iTrustFilePath = Constant.WEB_INFO_PATH
					+ getParameterByName("TrustStoreFile").trim();
			iTrustStorePass = getParameterByName("TrustStorePassword").trim();
			iTrustAlgorithm = getParameterByName("TrustAlgorithm").trim();
			iTrustProvider = getParameterByName("TrustProvider").trim();

			SSLSocketFactory sslFactory = SSLUtil.getSSLSocketFactory(
					iTrustFilePath, iTrustStorePass, iTrustAlgorithm,
					iTrustProvider);

			iSSLSocketFactory = sslFactory;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -----------农行自带
	// private static void initSSL() throws TrxException {
	// try {
	// java.security.Provider tProvider = new Provider();
	//
	// SSLContext tSSLContext = SSLContext.getInstance("TLS", tProvider);
	// TrustManagerFactory tTrustManagerFactory = TrustManagerFactory
	// .getInstance("SunX509", tProvider);
	// KeyStore tKeyStore = KeyStore.getInstance("JKS");
	//
	// String storeFilePath = Constant.WEB_INFO_PATH
	// + getParameterByName("TrustStoreFile").trim();
	// System.out.println("TrustStoreFile=" + storeFilePath);
	//
	// FileInputStream storeFile = new FileInputStream(storeFilePath);
	// String trustStorePassword = getParameterByName("TrustStorePassword");
	// tKeyStore.load(storeFile, trustStorePassword.toCharArray());
	//
	// tTrustManagerFactory.init(tKeyStore);
	// com.sun.net.ssl.TrustManager tTrustManager[] = tTrustManagerFactory
	// .getTrustManagers();
	// tSSLContext.init(null, tTrustManager, null);
	// iSSLSocketFactory = tSSLContext.getSocketFactory();
	// } catch (Exception e) {
	// System.out.println("[Trustpay商户端API] - 初始 - 系统发生无法预期的错误"
	// + e.getMessage());
	// throw new TrxException("1999", "系统发生无法预期的错误", e.getMessage());
	// } catch (Error e) {
	// System.out.println("[Trustpay商户端API] - 初始 - 系统发生无法预期的错误"
	// + e.getMessage());
	// throw new TrxException("1999", "系统发生无法预期的错误", e.getMessage());
	// }
	// System.out.println("[Trustpay商户端API] - 初始 - SSLSocketFactory完成");
	// }

	private static Certificate getCertificate(String tCertFile)
			throws TrxException {
		Certificate tCertificate = null;
		byte tCertBytes[] = new byte[4096];
		int tCertBytesLen = 0;
		try {
			FileInputStream tIn = new FileInputStream(tCertFile);
			tCertBytesLen = tIn.read(tCertBytes);
			tIn.close();
		} catch (Exception e) {
			throw new TrxException("1002", "无法读取证书文档[" + tCertFile + "]！");
		}
		byte tFinalCertBytes[] = new byte[tCertBytesLen];
		for (int i = 0; i < tCertBytesLen; i++)
			tFinalCertBytes[i] = tCertBytes[i];

		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		try {
			CertificateFactory tCertificateFactory = CertificateFactory
					.getInstance("X.509");
			ByteArrayInputStream bais = new ByteArrayInputStream(
					tFinalCertBytes);
			if (bais.available() > 0)
				tCertificate = tCertificateFactory.generateCertificate(bais);
		} catch (Exception e) {
			throw new TrxException("1006", "证书格式错误 - 无法由[" + tCertFile
					+ "]生成X.509证书对象！" + e.getMessage());
		}
		return tCertificate;
	}

	public static String getParameterByName(String aParamName)
			throws TrxException {
		if (iResourceBundle == null)
			bundle();
		String tValue = null;
		try {
			tValue = iResourceBundle.getString(aParamName).trim();
			if (tValue.equals(""))
				throw new TrxException("1001", "商户端配置文件中参数设置错误", " - 未设定["
						+ aParamName + "]参数值!");
		} catch (Exception e) {
			throw new TrxException("1001", "商户端配置文件中参数设置错误", " - 无["
					+ aParamName + "]参数!");
		}
		return tValue;
	}

	public static BufferedWriter getTrxLogFile() throws TrxException {
		return getTrxLogFile("TrxLog");
	}

	public static BufferedWriter getTrxLogFile(String aFileName)
			throws TrxException {
		bundle();
		BufferedWriter tLogFile = null;
		String tFileName = "";
		try {
			HiCalendar tHiCalendar = new HiCalendar();
			tFileName = iLogPath + System.getProperty("file.separator")
					+ aFileName + tHiCalendar.toString(".%Y%m%d.log");

			tLogFile = new BufferedWriter(new FileWriter(tFileName, true));
		} catch (IOException e) {
			throw new TrxException("1004", "无法写入交易日志文档", " - 系统无法写入交易日志至["
					+ tFileName + "]中!");
		}
		return tLogFile;
	}

	private static void bindMerchantCertificateByFile() throws TrxException {
		String tMerchantCertFile = Constant.WEB_INFO_PATH
				+ getParameterByName("MerchantCertFile").trim();

		// System.out.println("MerchantCertFile=" + tMerchantCertFile);

		if (tMerchantCertFile.length() == 0)
			throw new TrxException("1001", "商户端配置文件中参数设置错误",
					"商户证书储存目录档名[MerchantCertFile]配置错误！");
		String tMerchantCertPassword = getParameterByName("MerchantCertPassword");
		if (tMerchantCertFile.length() == 0)
			throw new TrxException("1001", "商户端配置文件中参数设置错误",
					"商户私钥加密密码[MerchantCertPassword]配置错误！");
		KeyStore tKeyStore = null;
		try {
			FileInputStream tIn = new FileInputStream(tMerchantCertFile);

			// tKeyStore = KeyStore.getInstance("PKCS12", (new
			// Provider()).getName());
			// System.out.println(new Provider().getName());

			tKeyStore = KeyStore.getInstance("PKCS12");

			tKeyStore.load(tIn, tMerchantCertPassword.toCharArray());
			tIn.close();
		} catch (Exception e) {
			throw new TrxException("1002", "无法读取证书文档", "[" + tMerchantCertFile
					+ "]！" + e.getMessage());
		}
		Certificate tCert = null;
		String tAliases = "";
		try {
			for (Enumeration e = tKeyStore.aliases(); e.hasMoreElements();) {
				tAliases = (String) e.nextElement();
				break;
			}

			tCert = tKeyStore.getCertificate(tAliases);
			Base64 tBase64 = new Base64();
			iMerchantCertificate = tBase64.encode(tCert.getEncoded());
		} catch (Exception e) {
			throw new TrxException("1006", "证书格式错误", "无法对证书进行编码！");
		}
		try {
			X509Certificate tX509Cert = (X509Certificate) tCert;
			tX509Cert.checkValidity();
		} catch (Exception e) {
			throw new TrxException("1005", "证书过期");
		}
		try {
			iMerchantKey = (PrivateKey) tKeyStore.getKey(tAliases,
					tMerchantCertPassword.toCharArray());
		} catch (Exception e) {
			throw new TrxException("1003", "无法读取商户私钥", "无法生成私钥证书对象！"
					+ e.getMessage());
		}
	}

	public static String getMerchantID() throws TrxException {
		bundle();
		return iMerchantID;
	}

	public static String getMerchantCertificate() throws TrxException {
		bundle();
		return iMerchantCertificate;
	}

	public static PrivateKey getMerchantKey() throws TrxException {
		bundle();
		return iMerchantKey;
	}

	public static String getTrustPayConnectMethod() throws TrxException {
		bundle();
		return iTrustPayConnectMethod;
	}

	public static String getKeyStoreType() throws TrxException {
		bundle();
		return iKeyStoreType;
	}

	public static String getTrustPayServerName() throws TrxException {
		bundle();
		return iTrustPayServerName;
	}

	public static int getTrustPayServerPort() throws TrxException {
		bundle();
		return iTrustPayServerPort;
	}

	public static String getTrustPayNewLine() throws TrxException {
		bundle();
		return iNewLine;
	}

	public static String getTrustPayTrxURL() throws TrxException {
		bundle();
		return iTrustPayTrxURL;
	}

	public static SSLSocketFactory getSSLSocketFactory() throws TrxException {
		bundle();
		return iSSLSocketFactory;
	}

	public static Certificate getTrustpayCertificate() throws TrxException {
		bundle();
		return iTrustpayCertificate;
	}

	public static XMLDocument signMessage(XMLDocument aMessage)
			throws TrxException {
		bundle();
		XMLDocument tMessage = null;
		try {
			String tKeyStoreType = getKeyStoreType();
			if (tKeyStoreType.equalsIgnoreCase("0"))
				tMessage = fileSignMessage(aMessage);
			else if (tKeyStoreType.equalsIgnoreCase("1"))
				tMessage = signServerSignMessage(aMessage);
		} catch (TrxException e) {
			throw e;
		} catch (Exception e) {
			throw new TrxException("1102", "签名交易报文时发生错误 - " + e.getMessage());
		}
		return tMessage;
	}

	private static XMLDocument fileSignMessage(XMLDocument aMessage)
			throws Exception {
		StringBuffer tMessage = new StringBuffer("");
		Signature tSignature = null;
		try {
			tSignature = Signature.getInstance("SHA1withRSA");
			tSignature.initSign(getMerchantKey());
			tSignature.update(aMessage.toString().getBytes("UTF-8"));
			byte tSigned[] = tSignature.sign();					
			
			Base64 tBase64 = new Base64();
			String tSignedBase64 = tBase64.encode(tSigned);
					
			tMessage.append("<Message>").append(aMessage.toString()).append(
					"</Message>").append("<Signature-Algorithm>").append(
					"SHA1withRSA").append("</Signature-Algorithm>").append(
					"<Signature>").append(tSignedBase64).append("</Signature>");
		} catch (Exception e) {
			throw e;
		}
		return new XMLDocument(tMessage.toString());
	}

	private static XMLDocument signServerSignMessage(XMLDocument aMessage)
			throws Exception {
		StringBuffer tMessage = new StringBuffer("");
		Socket tSocket = null;
		OutputStream tOut = null;
		BufferedReader tIn = null;
		String tSignServerIP = getParameterByName("SignServerIP");
		int tSignServerPort = Integer
				.parseInt(getParameterByName("SignServerPort"));
		String tSignServerPass = getParameterByName("SignServerPassword");
		try {
			tSocket = new Socket(InetAddress.getByName(tSignServerIP),
					tSignServerPort);
			Base64 tBase64 = new Base64();
			String tData = tBase64
					.encode(aMessage.toString().getBytes("UTF-8"));
			StringBuffer tSignRequest = (new StringBuffer("")).append(
					"<SignReq>").append("<Password>").append(tSignServerPass)
					.append("</Password>").append("<Signature-Algorithm>")
					.append("SHA1withRSA").append("</Signature-Algorithm>")
					.append("<Data>").append(tData).append("</Data>").append(
							"</SignReq>\n");
			tOut = tSocket.getOutputStream();
			tOut.write(tSignRequest.toString().getBytes("iso8859-1"));
			tOut.flush();
			tIn = new BufferedReader(new InputStreamReader(tSocket
					.getInputStream()));
			String tLine = tIn.readLine();
			if (tLine == null)
				throw new TrxException("1104", "签名服务器返回签名错误", "无响应");
			XMLDocument tSignedResponse = new XMLDocument(tLine);
			String tSignBase64 = "";
			if (tSignedResponse.getValueNoNull("RC").equals("0"))
				tSignBase64 = tSignedResponse.getValueNoNull("Signature");
			else
				throw new TrxException("1104", "签名服务器返回签名错误", "错误代码["
						+ tSignedResponse.getValueNoNull("RC") + "]");
			tMessage.append("<Message>").append(aMessage.toString()).append(
					"</Message>").append("<Signature-Algorithm>").append(
					"SHA1withRSA").append("</Signature-Algorithm>").append(
					"<Signature>").append(tSignBase64).append("</Signature>");
		} catch (TrxException e) {
			throw e;
		} catch (Exception e) {
			throw new TrxException("1103", "无法连线签名服务器", e.getMessage());
		} finally {
			if (tIn != null)
				try {
					tIn.close();
				} catch (Exception exception1) {
				}
			if (tOut != null)
				try {
					tOut.close();
				} catch (Exception exception2) {
				}
			if (tSocket != null)
				try {
					tSocket.close();
				} catch (Exception e) {
				}
		}
		return new XMLDocument(tMessage.toString());
	}

	public static XMLDocument verifySign(XMLDocument aMessage)
			throws TrxException {
		bundle();
		XMLDocument tTrxResponse = aMessage.getValue("Message");
		if (tTrxResponse == null)
			throw new TrxException("1301", "网上支付平台的响应报文不完整", "无[Message]段！");
		XMLDocument tAlgorithm = aMessage.getValue("Signature-Algorithm");
		if (tAlgorithm == null)
			throw new TrxException("1301", "网上支付平台的响应报文不完整",
					"无[Signature-Algorithm]段！");
		XMLDocument tSignBase64 = aMessage.getValue("Signature");
		if (tSignBase64 == null)
			throw new TrxException("1301", "网上支付平台的响应报文不完整", "无[Signature]段！");
		Base64 tBase64 = new Base64();
		byte tSign[] = tBase64.decode(tSignBase64.toString());
		try {
			Signature tSignature = Signature.getInstance(tAlgorithm.toString());
			tSignature.initVerify(getTrustpayCertificate());
			tSignature.update(tTrxResponse.toString().getBytes("gb2312"));
			if (!tSignature.verify(tSign))
				throw new TrxException("1302", "网上支付平台的响应报文签名验证失败");
		} catch (TrxException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new TrxException("1302", "网上支付平台的响应报文签名验证失败 - "
					+ e.getMessage());
		}
		return tTrxResponse;
	}

	public static void main(String args[]) {
	}
}