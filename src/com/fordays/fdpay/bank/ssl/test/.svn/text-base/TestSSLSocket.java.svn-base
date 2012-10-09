package com.fordays.fdpay.bank.ssl.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Security;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class TestSSLSocket {
	public static void main(String[] args) {
		 TestSSLSocket();
	}

	// 使用低级别的 Socket API，您需要手工处理服务器和客户端之间的握手，并且将 HTTP 请求信息传递到 Socket 的输入流中。
	public static void TestSSLSocket() {
//		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());// -----------

		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		try {
			SSLSocket sslSocket = (SSLSocket) sslsocketfactory.createSocket(
					"login.yahoo.com", 443);

			sslSocket.startHandshake();

			String abc = "GET / HTTP/1.1\n";
			abc = abc + "Accept: */*\n";
			abc = abc + "Accept-Language: en-us\n";
			abc = abc + "Accept-Encoding: gzip, deflate\n";
			abc = abc
					+ "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 1.1.4322)\n";
			abc = abc + "Host: login.yahoo.com:443\n\n\n";

			sslSocket.getOutputStream().write(abc.getBytes());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					sslSocket.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
			sslSocket.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
