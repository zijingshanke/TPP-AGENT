package com.fordays.fdpay.bank;

import java.net.Socket;

/**
 * Socket Server监听实现
 */
public class ServerSocketHandle implements Runnable {
	private Socket socketHandle;
	private String requestContent;

	public ServerSocketHandle(Socket socket) {
		socketHandle = socket;
	}

	public void run() {
		try {
			// 读Socket
			requestContent=SocketUtil.getServerBuffer(socketHandle);
			
			System.out.println("requestContent:" + requestContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket getSocketHandle() {
		return socketHandle;
	}

	public void setSocketHandle(Socket socketHandle) {
		this.socketHandle = socketHandle;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
}
