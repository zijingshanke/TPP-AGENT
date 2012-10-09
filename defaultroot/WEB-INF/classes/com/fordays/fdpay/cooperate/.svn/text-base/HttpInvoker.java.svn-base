package com.fordays.fdpay.cooperate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fordays.fdpay.security.Certification;
import com.neza.tool.SSLURLUtil;

public class HttpInvoker {
	public static void readContentFromGet(String url) throws IOException {
		URL getUrl = new URL(url);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取7
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		System.out.println("=============================");
		System.out.println("Contents of get request");
		System.out.println("=============================");
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		System.out.println("=============================");
		System.out.println("Contents of get request ends");
		System.out.println("=============================");
	}

	public static String readContentFromPost(String url, String parameters)
			throws IOException {
		try {
			// Post请求的url，与get不同的是不需要带参数
			url = url.replaceAll("[ ]", "%20");
			System.out.println("HttpInvoker readContentFromPost(),url:" + url);
			URL postUrl = new URL(url);

			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) postUrl
					.openConnection();
			connection.setConnectTimeout(50000);

			// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true
			connection.setDoOutput(true);

			// Read from the connection. Default is true.
			connection.setDoInput(true);

			// Set the post method. Default is GET
			connection.setRequestMethod("POST");

			// Post cannot use caches
			connection.setUseCaches(false);

			// This method takes effects to every instances of this class.
			// URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
			// connection.setFollowRedirects(true);

			// URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);

			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的意思是正文是urlencoded编码过的form参数，
			// 下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();

			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			String content = parameters;
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeBytes(content);

			out.flush();
			out.close(); // flush and close

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			
			reader.close();
			connection.disconnect();
			return result.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			// return "链接超时";
			return ex.getMessage();
		}
	}


	public static String readContentFromSSLGET(String url, String parameters)
	throws IOException {

	// Post请求的url，与get不同的是不需要带参数
	url = url.replaceAll("[ ]", "%20");
	System.out.println("HttpInvoker readContentFromPost(),url:" + url);
	String result=SSLURLUtil.getResponseBodyAsGet(url, Certification.getProtocol());
	System.out.println(result);
	return result;
}


	public static void main(String[] args) {
		try {
			// HttpInvoker.readContentFromGet(temp);
			String temp = "https://www.sun.com";
			temp = "https://www.qmpay.com/cooperate/receive.do?body=&buyer_email=pay@160.la&buyer_id=4193&notify_id=4642&notify_time=2009-08-05%2015:35:44&notify_type=trade_status_sync&out_trade_no=20090102002&payment_type=1&price=0.01&quantity=1&seller_email=pay@160.la&subject=%B2%E2%CA%D4&total_fee=0.01&trade_no=O20090805000129&trade_status=TRADE_SUCCESS&sign=8b32bd2dc49d467d46d7414410fd0c4b&sign_type=MD5";
			temp = "http://125.89.68.86:8301/fdpay-client/transaction/transaction.do?thisAction=transactionPaymentReturnByBank&transactionNo=T20091013000371";
//			temp = "https://www.qmpay.com/transaction/transaction.do?thisAction=transactionPaymentReturnByBank&transactionNo=T20091013000379";

			temp="https://qm.qmpay.com/bank/testQueryOrder_BCM6.jsp?orders=123456&version=B2C";
			//temp ="http://125.89.68.86:8301/fdpay-client/agent/agent.do?thisAction=agentInfoById";
			//	temp ="http://125.89.68.86:8301/fdpay-client/cooperate/gateway.do?service=direct_payment_by_bank&remark=order_no:71878";	
			//temp ="http://www.qmpay.com/cooperate/gateway.do?service=direct_payment_by_bank&remark=order_no:71878";			
			// temp="http://192.168.1.10:9013/FSAdmin/Order/CreditNotify.aspx?body=%D6%A7%B8%B6%C8%CB%3Aawt002-%B6%A9%B5%A5%BA%C5%3AC09072915085740660-PNR%3AXSKHB-%D0%D0%B3%CC%3A%B9%E3%D6%DD-%BA%A3%BF%DA&notify_id=7248&notify_time=2009-12-07
			// 17:23:26&notify_type=trade_status_sync&out_trade_no=uisdfptssf&payment_type=2&subject=%BB%FA%C6%B1%D4%A4%B6%A9%3AXSKHB&total_fee=20.00&trade_no=O20091207000015&trade_status=TRADE_SUCCESS&sign=2ccf66089d895bea1b06b00e0dd8d255&sign_type=MD5&buyer_email=shilifengg@tom.com&seller_email=zhangsan@test.com&receive_email=zuhchenwei@163.com&pay_email=shilifengg@126.com";


			String result = HttpInvoker.readContentFromSSLGET(temp,java.net.URLEncoder.encode(temp, "UTF-8"));
//
			// StringBuffer result = URLUtil.getResponseBodyAsGet(temp);
			System.out.println(result);
			
			System.out.println(temp.substring(0,5));
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
	}
}