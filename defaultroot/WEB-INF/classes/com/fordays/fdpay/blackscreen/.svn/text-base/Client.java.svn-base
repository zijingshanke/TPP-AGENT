package com.fordays.fdpay.blackscreen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket=null;
		try{
			System.out.println("链接服务器");
		socket=new Socket("125.89.68.86",3695);
		
				
		Reader reader = new BufferedReader(new InputStreamReader(socket
				.getInputStream(), "UTF-8"));
		Writer writer = new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream(), "UTF-8"));
		//发送关闭命令
//		OutputStream socketOut=socket.getOutputStream();
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		xml.append("<request>");
		xml.append("<cmd>update</cmd>");
		xml.append("<type>update_credit_balance</type>");
		xml.append("<account>zwwlmzy</account>");
		xml.append("<opration>修改授信</opration>");
		xml.append("<remark>授信金额1315</remark>");
		xml.append("<amount>1315</amount>");
		xml.append("</request>");
		writer.write(xml.toString());
		writer.flush();
		//socketOut.write(xml.toString().getBytes());
		//接收服务器的反馈
		BufferedReader br = new BufferedReader(
		new InputStreamReader(socket.getInputStream()));
		String msg=null;
		while((msg=br.readLine())!=null)
		System.out.println(msg);
		}catch(IOException e){
		e.printStackTrace();
		}finally{
		try{
		if(socket!=null)socket.close();
		}catch(IOException e){e.printStackTrace();}
		}
		}
	
	
	
	}

