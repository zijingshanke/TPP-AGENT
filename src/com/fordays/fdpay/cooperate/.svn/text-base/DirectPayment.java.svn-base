package com.fordays.fdpay.cooperate;

import java.sql.Timestamp;
import java.util.HashMap;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.cooperate.biz.AgentBindBiz;
import com.fordays.fdpay.cooperate.biz.GatewayBiz;
import com.fordays.fdpay.transaction.OrderDetails;

public class DirectPayment implements Runnable
{
	GatewayBiz biz;
	HashMap<String, String> map = null;
	String url = null;
	String encoding = null;
	Agent userAgent = null;
	boolean isPayByBank = false;
	private String result = "";

	GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);

	public GatewayBiz getBiz()
	{
		return biz;
	}

	public void setBiz(GatewayBiz biz)
	{
		this.biz = biz;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	public HashMap<String, String> getMap()
	{
		return map;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public DirectPayment(GatewayBiz biz,String result)
	{
		this.biz = biz;
		this.result=result;
	}
	
	public DirectPayment(AgentBindBiz Biz,String result)
	{
		this.biz = biz;
		this.result=result;
	}

	public void setMap(HashMap<String, String> map)
	{
		this.map = map;
	}

	public void run()
	{
		try
		{
			System.out.println("开启线程....");
						
			for (int i = 0; i < 3; i++)
			{
				try
				{ 
					Thread.sleep(30000);
					String context = HttpInvoker.readContentFromPost(url, java.net.URLEncoder.encode(result, encoding));
					System.out.println("返回结果:"+context);
					if (context != null && context.indexOf("SUCCESS") >= 0)
					{
						log(context,1);
						System.out.println("---通知完毕--结束");
						return;
					}
					else
					{
						log("失败,原因是: " + context+"    url:"+url,0);
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			System.out.println("线程结束....");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Agent getUserAgent()
	{
		return userAgent;
	}

	public void setUserAgent(Agent userAgent)
	{
		this.userAgent = userAgent;
	}

	public boolean isPayByBank()
	{
		return isPayByBank;
	}

	public void setPayByBank(boolean isPayByBank)
	{
		this.isPayByBank = isPayByBank;
	}

	public DirectPayment(String result)
	{
		super();
		this.result = result;
	}

	private void log(String context,int status) throws Exception
	{
		String orderno = "";
		OrderDetails orderDetails = new OrderDetails();
		if(map.get("out_trade_no")!=null&&!"".equals(map.get("out_trade_no")))
			orderno = map.get("out_trade_no");
		else
			orderno = map.get("batch_no");
		orderDetails = biz.getOrderDetails(orderno, map
		    .get("partner"));
		ActionLog actionLog = new ActionLog();
		actionLog.setStatus(new Long(status)); // 失败状态为0
		actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
		actionLog.setContent("外部订单号："+orderno+" "+context);
		if(orderDetails!=null)
		actionLog.setOrderDetails(orderDetails);
		actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
		biz.saveActionLog(actionLog);
	}
}