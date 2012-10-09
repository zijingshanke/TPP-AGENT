package com.fordays.fdpay.cooperate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.cooperate.biz.AgentBindBiz;
import com.fordays.fdpay.cooperate.biz.FreezeBiz;
import com.fordays.fdpay.cooperate.biz.GatewayBiz;
import com.fordays.fdpay.transaction.OrderDetails;

public class unfreezeDirectPayment implements Runnable {
	FreezeBiz biz = null;
	HashMap<String, String> map = null;
	String url = null;
	String encoding = null;
	Agent userAgent = null;
	boolean isPayByBank = false;
	private String result = "";

	GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);

	public FreezeBiz getBiz() {
		return biz;
	}

	public void setBiz(FreezeBiz biz) {
		this.biz = biz;
	}

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

	public unfreezeDirectPayment(FreezeBiz biz, String result) {
		this.biz = biz;
		this.result = result;
	}

	
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public void run() {
		try {
			System.out.println("开启线程....");

			for (int i = 0; i < 3; i++) {
				try {
					Thread.sleep(30000);
					String context = HttpInvoker.readContentFromPost(url,
							java.net.URLEncoder.encode(result, encoding));
					System.out.println("返回结果:" + context);
					if (context != null && context.indexOf("SUCCESS") >= 0) {
						log(context, 1);
						System.out.println("---通知完毕--结束");
						return;
					} else {
						log("失败,原因是: " + context+"      url:"+url, 0);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("线程结束....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Agent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(Agent userAgent) {
		this.userAgent = userAgent;
	}

	public boolean isPayByBank() {
		return isPayByBank;
	}

	public void setPayByBank(boolean isPayByBank) {
		this.isPayByBank = isPayByBank;
	}

	public unfreezeDirectPayment(String result) {
		super();
		this.result = result;
	}

	private void log(String context, int status) throws Exception {
		//OrderDetails orderDetails = new OrderDetails();
		OrderDetails unfreezeOrder=new OrderDetails();
		HashMap<String,String> map1=AnalyseParameter.analyseURL(result);
		unfreezeOrder=biz.queryOrderDetailByOrderNoAndPartner(map1.get("unfreeze_out_order_no"));
		ActionLog actionLog = new ActionLog();
		actionLog.setStatus(new Long(status)); // 失败状态为0
		actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
		actionLog.setContent("外部订单号：" + map1.get("trade_no") + " "
				+ context);
		actionLog.setOrderDetails(unfreezeOrder);
		actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
		biz.saveActionLog(actionLog);
	}
}