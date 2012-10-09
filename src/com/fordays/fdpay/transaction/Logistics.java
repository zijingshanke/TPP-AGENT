package com.fordays.fdpay.transaction;


import com.fordays.fdpay.transaction._entity._Logistics;

public class Logistics extends _Logistics{
  	private static final long serialVersionUID = 1L;
  	private long orderId;
  	private long tid;
  	private String logType;
  	
	public long getTid() {
		return tid;
	}
	public void setTid(long tid) {
		this.tid = tid;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getLogType() {
		if(this.type==0)
			return "快递";
		else if(this.type==1)
			return "平邮";
		else
			return "不需要运输";
	}
}
