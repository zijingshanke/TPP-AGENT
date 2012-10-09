package com.fordays.fdpay.cooperate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.fordays.fdpay.cooperate.biz.AgentBindBiz;

import com.fordays.fdpay.transaction.OrderDetails;

public class DirectCoterieRefund implements Runnable {
	AgentBindBiz biz;
	HashMap<String,String> map=null;
	String url=null;
	String encoding=null;
	GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public HashMap<String, String> getMap() {
		return map;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public DirectCoterieRefund(AgentBindBiz biz) {
		this.biz = biz;
	}
	public void setMap(HashMap<String,String> map){
		this.map=map;
	}
	public void run() {
		try {
			System.out.println("开启线程....");
			String result=biz.refund(map, url,gatewayMessage);
			for(int i=0;i<3;i++){
				try {
					System.out.println(">>>result["+i+"]=:"+result);
					String context=HttpInvoker.readContentFromPost(map.get("notify_url")+"?"+result, result);
					System.out.println(">>>context["+i+"]=:"+context);
					if(context!=null&&context.indexOf("SUCCESS")>=0){
						System.out.println(">>>context["+i+"]=:SUCCESS");
						break;
					}
				
					Thread.sleep(30000);
					List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map.get("pay_fee_detail_data"));
					String[] refund = listRefund.get(0);
					String orderNo = refund[0];
					List<String[]> listRefund1 = AnalyseParameter.analyseRefundParameter(map.get("buyer_fee_detail_data"));
					String[] refund1 = listRefund1.get(0);
					String orderNo1 = refund1[0];
					if(i==2){
						OrderDetails orderDetails = new OrderDetails();
						orderDetails = biz.getOrderDetails(orderNo, map.get("partner"));
						ActionLog actionLog = new ActionLog();
						actionLog.setStatus(new Long(0)); //失败状态为0
						actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
						actionLog.setContent("失败,原因是: "+context+"      url:"+url);
						actionLog.setOrderDetails(orderDetails);
						actionLog.setLogType(ActionLog.LOG_TYPE_REFUND_FAIL_REQUEST);
						biz.saveActionLog(actionLog);
						
						OrderDetails orderDetails1 = new OrderDetails();
						orderDetails1 = biz.getOrderDetails(orderNo1, map.get("partner"));
						ActionLog actionLog1 = new ActionLog();
						actionLog1.setStatus(new Long(0)); //失败状态为0
						actionLog1.setLogDate(new Timestamp(System.currentTimeMillis()));
						actionLog1.setContent("失败,原因是: "+context+"      url:"+url);
						actionLog1.setOrderDetails(orderDetails1);
						actionLog1.setLogType(ActionLog.LOG_TYPE_REFUND_FAIL_REQUEST);
						biz.saveActionLog(actionLog1);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public AgentBindBiz getBiz() {
		return biz;
	}
	public void setBiz(AgentBindBiz biz) {
		this.biz = biz;
	}
}