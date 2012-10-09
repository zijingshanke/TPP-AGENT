package com.fordays.fdpay.agent;

import com.fordays.fdpay.agent._entity._AgentUser;

public class AgentUser extends _AgentUser
{
	private static final long serialVersionUID = 1L;
	private String password2;

	// private String tempNo;
	public String getPassword2()
	{
		return password2;
	}

	public void setPassword2(String password2)
	{
		this.password2 = password2;
	}
}
