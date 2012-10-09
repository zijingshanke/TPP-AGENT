package com.fordays.fdpay.cooperate;


import com.fordays.fdpay.cooperate._entity._ActionLog;

public class ActionLog extends _ActionLog{
  	private static final long serialVersionUID = 1L;
  	
  	public static final Long LOG_TYPE_REFUND_REQUEST=new Long(0);//退款请求类型
  	public static final Long LOG_TYPE_PAY_REQUEST=new Long(1);//支付请求类型
  	public static final Long LOG_TYPE_PAY_RETURN=new Long(2);//支付返回类型
  	public static final Long LOG_TYPE_REFUND_RETURN=new Long(3);//退款返回类型
  	public static final Long LOG_TYPE_REMARK=new Long(4);
  	public static final Long LOG_TYPE_REFUND_FAIL_REQUEST=new Long(5);//退款请求URL失败类型
  	
}
