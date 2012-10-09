package com.fordays.fdpay.agent;

import com.fordays.fdpay.transaction._entity._Transaction;

public class AgentAccount extends _Transaction
{
	public static final int TYPE_1=1 ;//即时到帐
	
	public static final int TYPE_2=2 ;//担保交易
	
	public static final int STATUS_1=1 ;//等待买家付款
	
	public static final int STATUS_2=2 ;//等待卖家发货
	
	public static final int STATUS_3=3 ;//交易成功
	
	public static final int STATUS_4=4 ;//交易失败
	
	private static final long serialVersionUID = 1L;
	protected com.fordays.fdpay.agent.Agent fromAgent;
	protected com.fordays.fdpay.agent.Agent toAgent;
	protected com.fordays.fdpay.agent.Agent transactioner;
	protected com.fordays.fdpay.agent.Agent reverseTransactioner;
	protected String actionCaption;
	protected String sellerAccount;
	protected String buyerAccount;
	protected String transactionStatus;
	protected String paytype;
	protected String transactionType;
	protected String typeCaption;
	
	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getSellerAccount() {
		return sellerAccount;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public com.fordays.fdpay.agent.Agent getFromAgent()
		{
			return fromAgent;
		}

	public void setFromAgent(com.fordays.fdpay.agent.Agent fromAgent)
		{
			this.fromAgent = fromAgent;
		}

	public com.fordays.fdpay.agent.Agent getToAgent()
		{
			return toAgent;
		}

	public void setToAgent(com.fordays.fdpay.agent.Agent toAgent)
		{
			this.toAgent = toAgent;
		}

	public com.fordays.fdpay.agent.Agent getTransactioner()
		{
			return transactioner;
		}

	public void setTransactioner(com.fordays.fdpay.agent.Agent transactioner)
		{
			this.transactioner = transactioner;
			if (transactioner.getId() == this.getFromAgent().getId())
			{
				reverseTransactioner=this.getToAgent();
			}
			else if (transactioner.getId() == this.getToAgent().getId())
			{
				reverseTransactioner=this.getFromAgent();
			}
		}

	public String getActionCaption()
		{
			if (transactioner.getId() == this.getFromAgent().getId())
			{
				return "买入";
			}
			else if (transactioner.getId() == this.getToAgent().getId())
			{
				return "卖出";
			}
			else
				return "";
		}

	public com.fordays.fdpay.agent.Agent getReverseTransactioner()
		{
			return reverseTransactioner;
		}

	public String getTypeCaption(){
		if(this.getType().intValue()==AgentAccount.TYPE_1)
			return "即时到帐";
		else
			return "担保交易";
	}
	
	public String getTransactionStatus(){
		if(this.getStatus().intValue()==AgentAccount.STATUS_1)
			return "等待买家付款";
		else if(this.getStatus().intValue()==AgentAccount.STATUS_2)
			return "等待卖家发货";
		else if(this.getStatus().intValue()==AgentAccount.STATUS_3)
			return "交易成功";
		else
			return "交易失败";
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

}
