package com.fordays.fdpay.bank;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import com.neza.encrypt.XmlUtil;
import CCBSign.RSASig;

/**
 * 银行接口工具类
 */

public class BankUtil {
	public static void main(String[] args) {
		// testLoadProperties();
	}

	// -------------------IP---------------
	/**
	 * 获取本地IP
	 */
	public static String getLocalHost() {
		String address = "";
		try {
			address = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("getLocalHost:" + address);
		return address;
	}

	/**
	 * 比较是否与本地IP相同
	 */
	public static boolean isLocalHost(String host) {

		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			if (host.equals(address)) {
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 获取请求Head,例如：https://www.qmpay.com
	 */
	public static String getRequestHead(HttpServletRequest request) {
		String requestHead = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		return requestHead;
	}

	// -------------------IP---------------

	public static void testParseXML() {
		String aa = "<bankorder><orderNo>C20091030000001</orderNo><ChargeStatus>1</ChargeStatus><RequestHost>125.89.68.86</RequestHost></bankorder>";
		XmlUtil xml = new XmlUtil();
		Document doc = xml.readResult(new StringBuffer(aa));
		System.out.println(xml.getTextByNode(doc, "//bankorder/orderNo"));
	}

	public static void testLoadProperties() {
		String res = "com.fordays.fdpay.bank.bankReturnMsg";
		ResourceBundle reb = getResourceBundle(res);
		String paraName = "1001";
		String paraValue = getParameterByName(reb, paraName);
		try {
			System.out.println(paraName + "--"
					+ new String(paraValue.getBytes("ISO-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description:载入一个xml文档
	 * @return 成功返回Document对象，失败返回null
	 * @param uri
	 *            文件路径
	 */
	public static Document loadXml(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	//
	public static ResourceBundle getResourceBundle(String resource) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(resource);
		return resourceBundle;
	}

	//
	public static String getParameterByName(ResourceBundle resourceBundle,
			String paraName) {
		if (resourceBundle == null)
			System.out.println("资源不存在");
		String tValue = null;
		try {
			tValue = resourceBundle.getString(paraName).trim();
			if (tValue.equals(""))
				System.out.println("读取资源文件,值为空," + paraName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tValue;
	}

	// ------------------------begin 数值格式转换-------------------------
	/**
	 * @description：金额进制转换，元转分
	 * @example：工商银行
	 * @param:BigDecimal dollarAmount
	 * @return:String
	 */
	public static String getCentAmount(BigDecimal dollarAmount) {
		String tempAmount = (dollarAmount.multiply(new BigDecimal(100)))
				.toString();
		int isDecimal = tempAmount.indexOf(".");
		if (isDecimal > 0) {
			tempAmount = tempAmount.substring(0, isDecimal);
		}
		return tempAmount;
	}

	/**
	 * 金额进制转换，分转元
	 * 
	 * @example：工商银行
	 * @param:String
	 * @return:BigDecimal dollarAmount
	 */
	public static BigDecimal getDollarAmount(String centAmount) {
		BigDecimal tempAmount = new BigDecimal(0);
		Double doubleAmount = Double.valueOf(centAmount);
		doubleAmount = doubleAmount / 100;
		tempAmount = BigDecimal.valueOf(doubleAmount);
		// System.out.println(tempAmount);
		return tempAmount;
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:String strAmount
	 * @return:BigDecimal dollarAmount
	 */
	public static BigDecimal getBigDecimalByString(String strAmount) {
		BigDecimal tempAmount = new BigDecimal(0);
		Double doubleAmount = Double.valueOf(strAmount);
		tempAmount = BigDecimal.valueOf(doubleAmount);
		return tempAmount;
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:Double doubleAmount
	 * @return:BigDecimal dollarAmount
	 */
	public static BigDecimal getBigDecimalByDouble(Double doubleAmount) {
		BigDecimal tempAmount = new BigDecimal(0);
		tempAmount = BigDecimal.valueOf(doubleAmount);
		return tempAmount;
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:BigDecimal dollarAmount
	 * @return:String strAmount
	 */
	public static String getStringByBigDecimal(BigDecimal amount) {
		Double doubleAmount = amount.doubleValue();
		// 得到本地的缺省格式
		DecimalFormat df = new DecimalFormat("####.00");
		return df.format(doubleAmount);
	}

	/**
	 * 金额数据类型转换
	 * 
	 * @param:BigDecimal dollarAmount
	 * @return:String strAmount
	 */
	public static String getStringByLong(long amount) {
		// 得到本地的缺省格式
		DecimalFormat df = new DecimalFormat("####.00");
		return df.format(amount);
	}

	// ------------------------end 数值格式转换-------------------------

	// ------------------------begin 字符串----------------------------
	/**
	 * 判断字符串中是否有中文
	 */
	public static boolean hasFullSize(String inStr) {
		if (inStr.getBytes().length != inStr.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 连接字符串
	 * 
	 * @param String
	 *            fullString 原始字符串
	 * @param String
	 *            cellString 需要拼接的字符串
	 * @param Sting
	 *            appString 连接字符
	 * 
	 */
	public static String appendString(String fullString, String cellStrng,
			String appString) {
		if ("".equals(fullString)) {
			fullString = cellStrng + appString;
		} else {
			fullString = fullString + cellStrng + appString;
		}
		// System.out.println("fullString=" + fullString);
		return fullString;
	}

	/**
	 * 将字符串按分隔符转成Vector @ 支持 |
	 * @param String
	 *            strSrc
	 * @param String
	 *            strKen
	 * @return Vector<String>
	 */
	public static Vector<String> getVectorString(String strSrc, String strKen) {
		StringTokenizer toKen = new StringTokenizer(strSrc, strKen);
		Vector<String> vec = new Vector<String>();
		int i = 0;
		while (toKen.hasMoreElements()) {
			String value = (String) toKen.nextElement();
			if (value.equals(""))
				value = "&nbsp;";
			vec.add(i++, value);
		}
		for (int j = 0; j < vec.size(); j++) {
			System.out.println(j + "---" + vec.get(j));
		}
		return vec;
	}

	/**
	 * 将字符串按分隔符转成字符数组
	 * 
	 * @支持的分隔符: , / @ #
	 * @param String
	 *            strSrc
	 * @param String
	 *            splitStr
	 * @return String[]
	 */
	public static String[] getSplitString(String strSrc, String splitStr) {
		String splitString[] = strSrc.split(splitStr);
		return splitString;
	}

	/**
	 * 将字符串按分隔符转成整形数组
	 * 
	 * @支持的分隔符: , / @ #
	 * @param String
	 *            strSrc
	 * @param String
	 *            splitStr
	 * @return int[]
	 */
	public static int[] getSplitInt(String strSrc, String splitStr) {
		int[] array = null;
		String splitString[] = getSplitString(strSrc, splitStr);
		int arrayLength = splitString.length;
		array = new int[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			array[i] = Integer.parseInt(splitString[i]);
		}
		return array;
	}

	/**
	 * 删除字符数组中指定的元素
	 * 
	 * @param String[]
	 *            array
	 * @param String
	 *            para 需要删除的元素
	 */
	public static String[] delArrayCellByStr(String[] array, String para) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(para)) {
				array[i] = "";
			}
		}
		return array;
	}

	/**
	 * 替换字符数组中指定的元素
	 * 
	 * @param String[]
	 *            array
	 * @param String
	 *            para 需要替换的元素
	 * 
	 * @param String
	 *            replace 替换的目标
	 */
	public static String[] repArrayCellByStr(String[] array, String para,
			String replace) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(para)) {
				array[i] = replace;
			}
		}
		return array;
	}

	public static String getStringByStringArray(String[] array, String splitStr) {
		String returnStr = "";
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null || "".equals(array[i])) {
				returnStr = returnStr + "";
			} else {
				returnStr = returnStr + array[i] + splitStr;
			}
		}
		return returnStr;
	}
	
	// ------------------------end 字符串----------------------------
	
	// ------------------------begin 证书----------------------------
	/**
	 * 读取证书（base64编码格式）
	 * 
	 * @example：民生银行/中信银行
	 * @param String
	 *            url
	 * @return ByteArrayOutputStream
	 */
	public static ByteArrayOutputStream readFileAsByteArray(String url) {
		try {
			FileInputStream fileInStream = new FileInputStream(url);
			ByteArrayOutputStream fileByteStream = new ByteArrayOutputStream();
			int i = 0;
			while ((i = fileInStream.read()) != -1) {
				fileByteStream.write(i);
			}
			fileInStream.close();
			return fileByteStream;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 从文件（公钥/证书）中获取字节数组
	 * 
	 * @param:String url
	 * @return byte[]
	 */
	public static byte[] getByteFromFile(String url) {
		byte[] pubKey = null;
		try {
			FileInputStream in = new FileInputStream(url);
			pubKey = new byte[in.available()];
			in.read(pubKey);
			in.close();
			return pubKey;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * 读取文本文件的内容
	 * 
	 * @example：建设银行读取公钥
	 * @param:String url
	 * @return String
	 */
	public static String readStrFromTxt(String url) {
		String str = "";
		try {
			char data[] = new char[600];
			FileReader reader = new FileReader(url);

			int num = reader.read(data);
			System.out.println("num is--" + num);

			str = new String(data, 0, num);

			System.out.println("读取的字符是----");
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("读取文件失败，原因是：----------");
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 建设银行 验证签名（公钥）
	 * 
	 * @String strSrc
	 * @String strSign
	 * @String strPubKey
	 * @return void
	 */
	public static void verityCCBSigature(String strSrc, String strSign,
			String strPubKey) {
		RSASig rsa = new RSASig();
		rsa.setPublicKey(strPubKey);
		System.out.println("验证签名结果=" + rsa.verifySigature(strSign, strSrc));
	}

	// URLEncode
	@SuppressWarnings("deprecation")
	public static String getURLEncode(String src) {
		return URLEncoder.encode(src);
	}

	// URLDecode
	@SuppressWarnings("deprecation")
	public static String getURLDecode(String code) {
		return URLDecoder.decode(code);
	}
}
