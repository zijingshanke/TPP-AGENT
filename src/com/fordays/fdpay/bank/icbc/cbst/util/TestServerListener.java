package com.fordays.fdpay.bank.icbc.cbst.util;

import java.net.Socket;
import com.fordays.fdpay.bank.ServerSocketListener;
import com.fordays.fdpay.bank.SocketUtil;

public class TestServerListener {
	public static void main(String[] args) {
		String host = "192.168.1.91";
		int port = 123;

//		startServer(port);
		testClient(host, port);
	}

	public static void startServer(int port) {
		ServerSocketListener listener = new ServerSocketListener(port);
		listener.acceptConnections();
	}

	public static void testClient(String host, int port) {
		Socket socket = SocketUtil.initSocket(host, port);
		SocketUtil.sendMessageNoReceive(socket, "test");
	}
}