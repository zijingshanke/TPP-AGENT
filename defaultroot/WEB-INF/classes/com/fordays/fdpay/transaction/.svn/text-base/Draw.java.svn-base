package com.fordays.fdpay.transaction;

import java.math.BigDecimal;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction._entity._Draw;

public class Draw extends _Draw
{
	private static final long serialVersionUID = 1L;
	protected com.fordays.fdpay.user.SysUser sysUser1;// 核准人
	protected com.fordays.fdpay.user.SysUser sysUser3;// 转帐人
	public static long STATUS_0=0;//待批准
	public static long STATUS_1=1;//待核准
	public static long STATUS_2=2;//待转账
	public static long STATUS_3=3;//已转账
	public static long STATUS_4=4;//已撤销
	public com.fordays.fdpay.user.SysUser getSysUser1()
		{
			return sysUser1;
		}

	public void setSysUser1(com.fordays.fdpay.user.SysUser sysUser1)
		{
			this.sysUser1 = sysUser1;
		}

	private String payPassword;
	private BigDecimal amounts;
	private Account account;
	private String beginDate;
	private String endDate;
	private String whereState;
	public static Long type_0=0L;//普通提现
	public static Long type_1=1L;//实名认证
	public String getWhereState() {
		whereState="";
		if(status==STATUS_3){
			whereState= "成功";
		}
		else if(status==STATUS_4){
			whereState= "撤销";
		}
		else if(status==STATUS_2)
		{
		whereState= "银行处理中";
		}
		else if(status==STATUS_0 || status==STATUS_1)
		{
			whereState= "提交成功";
		}
		
		return whereState;
	}
	public void setWhereState(String whereState) {
		this.whereState = whereState;
	}
	public String getBeginDate()
		{
			return beginDate;
		}

	public void setBeginDate(String beginDate)
		{
			this.beginDate = beginDate;
		}

	public String getEndDate()
		{
			return endDate;
		}

	public void setEndDate(String endDate)
		{
			this.endDate = endDate;
		}

	public String getPayPassword()
		{
			return payPassword;
		}

	public void setPayPassword(String payPassword)
		{
			this.payPassword = payPassword;
		}



	public BigDecimal getAmounts() {
		return amounts;
	}

	public void setAmounts(BigDecimal amounts) {
		this.amounts = amounts;
	}

	public Agent getAgent()
		{
			return agent;
		}

	public void setAgent(Agent agent)
		{
			this.agent = agent;
		}

	public Account getAccount()
		{
			return account;
		}

	public void setAccount(Account account)
		{
			this.account = account;
		}
	
	public String getHaskCardNo()
	{
		String countxing = "";
		for (int i = 0; i <= (cardNo.length() - 5); i++)
		{
			countxing += "*";
		}
		if(cardNo.length()> 5)
		  return countxing + cardNo.substring(cardNo.length() - 5, cardNo.length());
		else
			return "************";

	}
	public String getNoteCaption() {
		
		if(note!=null)
		{
			return note.replace("\\n","<br/>");
		}
		else
			return "";
	}

	public com.fordays.fdpay.user.SysUser getSysUser3()
  {
  	return sysUser3;
  }

	public void setSysUser3(com.fordays.fdpay.user.SysUser sysUser3)
  {
  	this.sysUser3 = sysUser3;
  }
}
