package com.fordays.fdpay.bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Socket工具类
 */
public class SocketUtil {

	/**
	 * 创建Socket Client
	 * 
	 * @param String
	 *            host
	 * @param int
	 *            port
	 * @return Socket
	 */
	public static Socket initSocket(String host, int port) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}

	/**
	 * 创建Socket Server
	 * 
	 * @param int
	 *            port
	 * @return Socket
	 */
	public static ServerSocket initSocketServer(int port) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
			return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}

	/**
	 * 创建Socket Server
	 * 
	 * @param int
	 *            port
	 * @param int
	 *            count待发数
	 * @return Socket
	 */
	public static ServerSocket initSocketServer(int port, int count) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port, count);
			return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	public static Socket getSocketByServer(int port){
		try {
			return initSocketServer(port).accept();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 建立Socket client连接,发送报文，不接收反馈
	 * 
	 * @param Socket
	 *            socket
	 * @param String
	 *            cmd
	 */
	public static void sendMessageNoReceive(Socket socket, String strCmd) {
		BufferedWriter bw = null;
		try {
			// 向服务端发送数据
			bw = getBufferWriter(socket);
			bw.write(strCmd);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 建立Socket client连接,发送指令报文,获得服务端反馈结果
	 * 
	 * @param Socket
	 *            socket
	 * @param String
	 *            cmd
	 */
	public static String getResultAsSocket(Socket socket, String strCmd)
			throws IOException {
		BufferedWriter bw = null;
		BufferedReader br = null;

		String serveresult = "";// 服务端反馈结果
		try {
			// 向服务端发送数据
			bw = getBufferWriter(socket);
			bw.write(strCmd);
			bw.flush();

			// 接收返回数据
			br = getBufferReader(socket);
			String brResult = "";
			while ((brResult = br.readLine()) != null) {
				serveresult += brResult;
			}
			socket.close();
			return serveresult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			bw.close();
			br.close();
		}
	}

	/**
	 * 服务端调用，读取请求的数据
	 */
	public static BufferedReader getServerBufferReader(Socket socket) {
		BufferedReader br = null;
		try {
			br = SocketUtil.getBufferReader(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * 服务端调用，读取请求的数据
	 */
	public static String getServerBuffer(Socket socket) {
		String clientRequest = "";
		BufferedReader br = null;
		try {
			br = SocketUtil.getBufferReader(socket);
			String line="";
			while ((line = br.readLine()) != null) {
				clientRequest=line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();			
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return clientRequest;
	}

	public static PrintWriter getPrintWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	/**
	 * 向服务端发送数据
	 */
	public static BufferedWriter getBufferWriter(Socket socket)
			throws IOException {
		OutputStream ops = socket.getOutputStream();
		OutputStreamWriter opsw = new OutputStreamWriter(ops);
		return new BufferedWriter(opsw);
	}

	/**
	 * 从服务端接收数据
	 */
	public static BufferedReader getBufferReader(Socket socket)
			throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

}
