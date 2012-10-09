package com.fordays.fdpay.agent.biz;

import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.dao.AgentRelationshipDAO;
import com.neza.exception.AppException;

public class AgentRelationshipBizImpl implements AgentRelationshipBiz{

	public AgentRelationshipDAO agentRelationshipDAO;
	public List getAgentRelationshipByAgent(Agent agent) throws AppException
	{
		return agentRelationshipDAO.getAgentRelationshipByAgent(agent);
	}
	public void setAgentRelationshipDAO(AgentRelationshipDAO agentRelationshipDAO)
	{
		this.agentRelationshipDAO = agentRelationshipDAO;
	}
	public AgentRelationship getAgentRelationshipBySalesman(Agent agent) throws AppException {
		return agentRelationshipDAO.getAgentRelationshipBySalesman(agent);
	}
	
}
