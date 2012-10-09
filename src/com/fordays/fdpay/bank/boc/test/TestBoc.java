package com.fordays.fdpay.bank.boc.test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import com.fordays.fdpay.bank.boc.BocB2CResultFromBank;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.pgw.test.security.PKCS7Tool;

public class TestBoc {
	public static void main(String[] args) {
//		get24HourTime();
		
		// Document document = BankUtil.loadXml("E:\\xmldemo2.xml");
		// getBocB2CResult(document);

		// XmlUtil xmlUtil = new XmlUtil();
		// xmlUtil.getTextByNode(document, "/res/body/orderTrans[2]/payAmount");
		// xmlUtil.getTextByNode(document, "/res/header/merchantNo");

		// TestParseQueryOrderResult();
		 testPKCS7Tool();

	}

	public static void testPKCS7Tool() {
		String keyStorePath = "D:\\淘宝网.pfx";
		String keyStorePassword = "11111111";
		String keyPassword = "11111111";
		PKCS7Tool tool = getPKCS7Tool(keyStorePath, keyStorePassword,
				keyPassword);

		String plaintext = "123456";// 明文
		try {
			String strsign = tool.sign(plaintext.getBytes());// 签名
			System.out.println(strsign);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 获取签名工具
	 */
	public static PKCS7Tool getPKCS7Tool(String keyStorePath,
			String keyStorePassword, String keyPassword) {
		PKCS7Tool tool = null;
		try {
			tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword,
					keyPassword);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tool;
	}

	public static void get24HourTime() {
		String str = DateUtil.getDateString("yyyyMMddHHmmss");
		System.out.println(str);
	}

	public static void TestParseQueryOrderResult() {
		StringBuffer buf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\" ?>");
		buf.append("<res>");
		buf.append("<header>");
		buf.append("<merchantNo>104110070118888</merchantNo>");
		buf.append("</header>");
		buf.append("<body>");
		buf.append("<orderTrans>");
		buf.append("<orderNo>4</orderNo>");
		buf.append("<orderSeq>9</orderSeq>");
		buf.append("<orderStatus>1</orderStatus>");
		buf.append("<cardTyp>04</cardTyp>");
		buf.append("<payTime>20090605000000</payTime>");
		buf.append("<payAmount>200.12</payAmount>");
		buf.append("</orderTrans>");
		buf.append("<orderTrans>");
		buf.append("<orderNo>4</orderNo>");
		buf.append("<orderSeq>9</orderSeq>");
		buf.append("<orderStatus>1</orderStatus>");
		buf.append("<cardTyp>04</cardTyp>");
		buf.append("<payTime>20090605000000</payTime>");
		buf.append("<payAmount>360.00</payAmount>");
		buf.append("</orderTrans>");
		buf.append("</body>");
		buf.append("</res>");

		// XmlUtil xml = new XmlUtil();
		// Document doc = xml.readResult(buf);
		// xml.getTextByNode(doc, "/res/header/merchantNo");

		try {
			getB2CResult(buf);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	// 解析B2C订单查询反馈结果
	public static BocB2CResultFromBank getB2CResult(StringBuffer fer)
			throws AppException {
		BocB2CResultFromBank result = new BocB2CResultFromBank();

		System.out.println("---1111------" + fer);

		XmlUtil xml = new XmlUtil();
		Document document = xml.readResult(fer);// stringBuffer息还原为XML

		Element elementRoot = document.getRootElement();// 根节点
		System.out.println(elementRoot.getText() + "---");

		// 遍历根结点
		for (Iterator iter = elementRoot.elementIterator(); iter.hasNext();) {
			Element elementFirst = (Element) iter.next();// 一级子节点

			if ("header".equals(elementFirst.getName())) {
				Node merchantNo = elementFirst.getXPathResult(1);
				System.out.println(merchantNo);
				result.setMerchantNo(merchantNo.getText());
			} else if ("body".equals(elementFirst.getName())) {
				// 遍历子结点的子节点
				for (Iterator iterInner = elementFirst.elementIterator(); iterInner
						.hasNext();) {
					Element elementSec = (Element) iterInner.next();// 二级子节点

					for (Iterator iterThird = elementSec.elementIterator(); iterThird
							.hasNext();) {
						Element elementThird = (Element) iterThird.next();// 三级子节点

						System.out.println(elementThird.getName() + "--"
								+ elementThird.getText());
						System.out.println(elementThird.getUniquePath());
					}
				}
			}
		}
		return null;
	}

	public void xmlText() {

		// <?xml version="1.0" encoding="utf-8" ?>
		// <res>
		// <header>
		// <merchantNo>104110070118888</merchantNo>
		// </header>
		// <body>
		// <orderTrans>
		// <orderNo>4</orderNo>
		// <orderSeq>9</orderSeq>
		// <orderStatus>1</orderStatus>
		// <cardTyp>04</cardTyp>
		// <payTime>20090605000000</payTime>
		// <payAmount>200.12</payAmount>
		// </orderTrans>
		// <orderTrans>
		// <orderNo>4</orderNo>
		// <orderSeq>9</orderSeq>
		// <orderStatus>1</orderStatus>
		// <cardTyp>04</cardTyp>
		// <payTime>20090605000000</payTime>
		// <payAmount>360.00</payAmount>
		// </orderTrans> </body> </res>

	}

	public static void read(Document document) {
		HashMap map = new HashMap();
		SAXReader saxReader = new SAXReader();

		Element root = document.getRootElement();

		System.out.println("root:" + root.getName());

		// 遍历根结点
		for (Iterator iter = root.elementIterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			if (element.getName().equals("body")) {
				System.out.println("11111111");

				// 遍历子结点的子节点
				for (Iterator iterInner = element.elementIterator(); iterInner
						.hasNext();) {
					Element elementInner = (Element) iterInner.next();
					System.out.println("elementInner Name:"
							+ elementInner.getName() + "-----"
							+ elementInner.getText());
					// System.out.println(elementInner.getData());
					for (Iterator iterThird = elementInner.elementIterator(); iterThird
							.hasNext();) {
						Element elementThird = (Element) iterThird.next();
						System.out.println("elementInner Name:"
								+ elementThird.getName() + "---"
								+ elementThird.getText());
					}
				}
			}
		}
	}
}