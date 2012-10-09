package com.fordays.fdpay.bank;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import javax.net.ssl.HttpsURLConnection;

/**
 * 
 * 流处理工具类
 */
public class StreamUtil {
	public static void main(String[] args) {

	}

	/**
	 * @param HttpURLConnection
	 * @param String
	 *            content // The URL-encoded content
	 */
	public static void postStringAsOutPutStream(HttpURLConnection connection,
			String content) {
		try {
			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());

			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流
			out.writeBytes(content);
			out.flush();
			out.close(); // flush and close
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param Socket
	 *            socket
	 * @param String
	 */
	public static String getStringFromSocketStream(Socket socket)
			throws Exception {
		String result = "";
		try {
			InputStream in = socket.getInputStream();
			result = getStringFromInputStream(in);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param HttpURLConnection
	 *            connection
	 * @return String
	 */
	public static String getStringFromHttpConnectionStream(
			HttpURLConnection connection) {
		String result = "";
		try {
			InputStream in = connection.getInputStream();
			result = getStringFromInputStream(in);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param HttpsURLConnection
	 *            connection
	 * @return String
	 */
	public static String getStringFromHttpsConnectionStream(
			HttpsURLConnection connection) {
		String result = "";
		try {
			InputStream in = connection.getInputStream();
			result = getStringFromInputStream(in);
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getStringFromInputStream(InputStream in) {
		return getStringBufferFromInputStream(in).toString();
	}

	public static StringBuffer getStringBufferFromInputStream(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;

		StringBuffer resultBuf = new StringBuffer();
		try {
			while ((line = reader.readLine()) != null) {
				resultBuf.append(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultBuf;
	}
}
