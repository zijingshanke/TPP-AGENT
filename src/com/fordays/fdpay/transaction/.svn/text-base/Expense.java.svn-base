package com.fordays.fdpay.transaction;


import com.fordays.fdpay.transaction._entity._Expense;

public class Expense extends _Expense{
  	private static final long serialVersionUID = 1L;
  	
  	public static final long STATUS_0 = 0;// 申请报销
  	
  	public static final long STATUS_1 = 1;// 批准
  	
  	public static final long STATUS_2 = 2;// 核准
  	
  	public static final long STATUS_3 = 3;// 转账
  	
  	public static final long STATUS_4 = 4;// 撤销
  	
  	protected String expenseStatus;
  	
    protected com.fordays.fdpay.agent.Agent fromAgent;

    
		public com.fordays.fdpay.agent.Agent getFromAgent()
    {
    	return fromAgent;
    }
		public void setFromAgent(com.fordays.fdpay.agent.Agent fromAgent)
    {
    	this.fromAgent = fromAgent;
    }
		
		public String getExpenseStatus(){
			if(this.status==STATUS_0)
				return "申请中";
			else if(this.status==STATUS_1)
				return "待核准";
			else if(this.status==STATUS_2)
				return "待转账";
			else if(this.status==STATUS_3)
				return "报销成功";
			else
				return "已撤销";
		}
}
