package com.fordays.qmpay.blackscreen.biz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.Socket;
import java.sql.Timestamp;


import org.dom4j.Document;


import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.blackscreen.Blackscreen;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.NoUtil;
import com.neza.exception.AppException;



/**
 * 充值业务实现类
 */

public class BlackscreenBizImp implements BlackscreenBiz {
	private String ip="fdays4.cncsz.net";
	private AgentDAO agentDAO;
	private TransactionDAO transactionDAO;
	public Document document = null;
	private NoUtil noUtil;

	
	//创建一笔黑屏充值交易记录（本地）
	public Transaction createTransaction(Blackscreen blackscreen,Agent agent) throws AppException{
		BigDecimal ratePrice=new BigDecimal(0);
		ratePrice=new BigDecimal(blackscreen.getResultAmount());
	       System.out.println("黑屏充值金额:"+ratePrice.toString());
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setShopName("黑屏充值:"+blackscreen.getBlackscreenAccount());
			orderDetails.setShopPrice(ratePrice);
			orderDetails.setShopTotal(new Long(1));
			orderDetails.setDetailsContent("充值金额:"+blackscreen.getResultAmount()+"黑屏账号:"+blackscreen.getBlackscreenAccount());
			orderDetails.setPaymentPrice(ratePrice);
			orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
			orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());	
			orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
			transactionDAO.addOrderDetails(orderDetails);
			Transaction transaction = new Transaction();
			transaction.setStatus(Transaction.STATUS_1);
			transaction.setType(Transaction.TYPE_4);
			String loginName="qmlinmq@163.com";
			Agent toAgent=null;
			toAgent = agentDAO.getAgentByEmail(loginName);		
			transaction.setFromAgent(agent);
			transaction.setToAgent(toAgent);
			transaction.setOrderDetails(orderDetails);
			transaction.setAmount(ratePrice);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setMark(blackscreen.getBlackscreenAccount());
			transactionDAO.saveTransaction(transaction);
			
			return transaction;
		
	}

    //判断黑屏账号是否存在
	public  String checkBlackscreenAccount(Blackscreen blackscreen)throws AppException{
	  
		String rmsg="";
		Socket socket=null;
		try{
			System.out.println("链接服务器");
			socket=new Socket(ip,3695);
				
		Reader reader = new BufferedReader(new InputStreamReader(socket
				.getInputStream(), "UTF-8"));
		Writer writer = new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream(), "UTF-8"));
		
		
		//发送关闭命令
//		OutputStream socketOut=socket.getOutputStream();
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		xml.append("<request>");
		xml.append("<cmd>select</cmd>");
		xml.append("<type>account_is_exits</type>");
		xml.append("<account>"+blackscreen.getBlackscreenAccount()+"</account>");
		xml.append("<opration>验证黑屏账号</opration>");
		xml.append("<remark>验证黑屏账号</remark>");
		xml.append("<amount></amount>");
		xml.append("</request>");
		writer.write(xml.toString());
		writer.flush();
		//socketOut.write(xml.toString().getBytes());
		//接收服务器的反馈
		BufferedReader br = new BufferedReader(
		new InputStreamReader(socket.getInputStream()));
		String msg=null;
		msg=br.readLine();
			System.out.println(msg);
		if("true".equals(msg)){
		 rmsg= "true";
		 }else{
			 rmsg= "黑屏账号不存在!";
		 }
		
		}catch(IOException e){
		e.printStackTrace();
		 rmsg= "Connection timed out: connect";
		}finally{
		try{
		if(socket!=null)socket.close();
		}catch(IOException e){
			e.printStackTrace();
			
		}
	}
		return rmsg;
		
	}
	public  boolean doNoticeBlackscreen(Transaction transaction)throws AppException{
		 String BlackscreenAccount = transaction.getRemark();    
		 BigDecimal amount=transaction.getAmount();   
		 boolean rmsg=false;	
		 if(BlackscreenAccount!=null){
	     
		Socket socket=null;
		try{
			System.out.println("链接服务器");
			socket=new Socket(ip,3695);
				
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
		xml.append("<type>update_allow_balance</type>");
		xml.append("<account>"+BlackscreenAccount+"</account>");
		xml.append("<opration>修改余额</opration>");
		xml.append("<remark>修改金额"+amount+"</remark>");
		xml.append("<amount>"+amount+"</amount>");
		xml.append("</request>");
		writer.write(xml.toString());
		writer.flush();
		//socketOut.write(xml.toString().getBytes());
		//接收服务器的反馈
		BufferedReader br = new BufferedReader(
		new InputStreamReader(socket.getInputStream()));
		String msg=null;
		msg=br.readLine();
		System.out.println(msg);
		if("true".equals(msg)){
		 rmsg= true;
		 }else{
			 rmsg= false;
		 }
		
		}catch(IOException e){
		e.printStackTrace();
		rmsg=false;
		}finally{
		try{
		if(socket!=null)socket.close();
		}catch(IOException e){e.printStackTrace();}
		}
		
	  }
		 return rmsg;
	}



	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public NoUtil getNoUtil() {
		return noUtil;
	}
	


	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

}
