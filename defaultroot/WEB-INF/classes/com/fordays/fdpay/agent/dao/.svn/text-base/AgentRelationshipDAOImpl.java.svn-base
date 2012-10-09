package com.fordays.fdpay.agent.dao;

import java.util.List;

import org.hibernate.Query;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentRelationship;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;


public class AgentRelationshipDAOImpl extends BaseDAOSupport implements AgentRelationshipDAO{

	

	public List getAgentRelationshipByAgent(Agent agent) throws AppException {
		Hql hql=new Hql("from AgentRelationship where parentAgent.id=?");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		return query.list();
	}

	public AgentRelationship getAgentRelationshipBySalesman(Agent agent) throws AppException {
		AgentRelationship agentRelationship=new AgentRelationship();
		Hql hql=new Hql("from AgentRelationship where agent.id=?");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		if(query.list().size()>0)
		 agentRelationship=(AgentRelationship) query.list().get(0);
		
		return agentRelationship;
	}

	
	
}
