package com.fordays.fdpay.right.dao;

import java.util.List;


import com.fordays.fdpay.agent.AgentRoleRight;
import com.fordays.fdpay.agent.AgentUserRoleRight;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;


public interface RightDAO extends BaseDAO {
	public List listRoleRightsByAgentUserId(long agentUserId) throws AppException;
	public List listAgentUsers(long agentId) throws AppException;
	public AgentRoleRight getAgentRoleRightById(long rightId) throws AppException;
	public AgentUserRoleRight update(AgentUserRoleRight agentUserRoleRight) throws AppException ;
	public void deleteAllAgentUserRoleRight(long agentUserId) throws AppException ;	
	public List listRoleRights() throws AppException;
	
}
