package com.fordays.fdpay.right.biz;


import java.util.List;

import com.fordays.fdpay.agent.AgentUser;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;


public interface RightBiz {
//	public void setRights(UserRightInfo uri,long userId) throws AppException;
	public List getAgentUserRoleRightByAgentUserId(long agentUserId) throws AppException;
	public List getAgentUsers(long agentId) throws AppException;
	public void setAgentUserRights(long agentUserId,long[] rightIds) throws AppException;
	
	//BankAction调用
	public UserRightInfo getUserRightInfobyCharge(Charge charge)throws AppException;
 
}
