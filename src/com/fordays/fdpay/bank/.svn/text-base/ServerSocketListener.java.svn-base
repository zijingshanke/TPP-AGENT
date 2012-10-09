package com.fordays.fdpay.bank;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 设置Socket端口监听
 */
public class ServerSocketListener {
	private int port;

	public ServerSocketListener(int listenerPort) {
		port=listenerPort;
	}

	// 允许客户机连接到服务器,等待客户机请求
	public void acceptConnections() {
		try {
			// 指定待发数（backlog 值）是5,一次可以放五个请求到队列中
			ServerSocket server = SocketUtil.initSocketServer(port,5);			
			while (true) {				
				 handleConnection(server.accept());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 与客户机Socket交互以将客户机所请求的文件的内容发送到客户机
	public void handleConnection(Socket socket) {
		new Thread(new ServerSocketHandle(socket)).start();
	}
}
