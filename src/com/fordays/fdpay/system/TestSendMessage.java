package com.fordays.fdpay.system;

import com.fordays.fdpay.bank.ChangeCharset;
import com.fordays.fdpay.cooperate.HttpInvoker;

public class TestSendMessage {
	public static void main(String[] args) {
		testSendMessage();
	}

	public static void testSendMessage() {
		String userid, password, destnumbers, msg, sendtime, xmlstring;
		String retinfo = "";
		String qurl;

		userid = "7696";//用户名
		password = "an654321";//密码
		destnumbers = "13192229256";//手机号
		msg = "test3" + "【钱门第三方支付平台】";//短信内容
		sendtime = "";

		try {
			if ((userid.length() > 0) && (password.length() > 0)
					&& (destnumbers.length() > 0) && (msg.length() > 0)) {

				// 生成调用的URL.如果参数中有中文一定要用
				// java.net.URLEncoder.encode(<参数>,"UTF-8")
				qurl = "http://chineseserver.net:3388/CellServer/SmsAPI2/SendMessage.jsp?userid="
						+ userid
						+ "&password="
						+ password
						+ "&destnumbers="
						+ destnumbers
						+ "&msg="
						+ java.net.URLEncoder.encode(msg, "UTF-8")
						+ "&sendtime=" + sendtime;
				
				System.out.println(qurl);
				String result = HttpInvoker.readContentFromPost(qurl, "");
				ChangeCharset util = new ChangeCharset();
				System.out.println(util.changeCharset(result, "UTF-8"));

				// // 这里用了 dom4j 来分析返回的XML
				// org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
				// org.dom4j.Document doc = reader.read(new java.net.URL(qurl));
				//
				// if ("0".endsWith(doc.valueOf("/root/@return"))) {
				// // 节点"/root/@return" 的值为 0 时调用成功
				// retinfo = "return=0,成功";
				// } else {
				// // 节点"/root/@return" 的值不为 0 时调用失败
				// retinfo = "return=" + doc.valueOf("/root/@return")
				// + ",info=" + doc.valueOf("/root/@info");
				// }
				// // 用于打印返回XML的源以用于调试
				// xmlstring = new String(doc.asXML().getBytes(), "UTF-8");
				// System.out.println(xmlstring);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
