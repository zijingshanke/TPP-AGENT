package com.fordays.fdpay.right.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentRoleRight;
import com.fordays.fdpay.agent.AgentUser;
import com.fordays.fdpay.agent.AgentUserRoleRight;
import com.neza.base.BaseDAOSupport;
import com.neza.exception.AppException;

public class RightDAOImpl extends BaseDAOSupport implements RightDAO
{

	public List listRoleRightsByAgentUserId(long agentUserId) throws AppException
	{
		String hql ="from AgentUserRoleRight where agentUser.id="+agentUserId;
		Query query=this.getQuery(hql);
		
		if(query!=null)
		{
			List list=query.list();
			if(list!=null)
			{
				return list;
			}
		}		
		return new ArrayList();
	}
	
	
	public List listRoleRights() throws AppException
	{
		String hql ="from AgentRoleRight";
		Query query=this.getQuery(hql);
		
		if(query!=null)
		{
			List list=query.list();
			if(list!=null)
			{
				return list;
			}
		}		
		return new ArrayList();
	}
	
	
	public List listAgentUsers(long agentId) throws AppException
	{
		String hql ="from AgentUser where agent.id="+agentId+" order by id desc";
		Query query=this.getQuery(hql);
		
		if(query!=null)
		{
			List list=query.list();
			if(list!=null)
			{
				return list;
			}
		}		
		return new ArrayList();
	}
	
	public AgentUserRoleRight update(AgentUserRoleRight agentUserRoleRight) throws AppException {

		super.getHibernateTemplate().save(agentUserRoleRight);
		return agentUserRoleRight;
	}
	
	
	public void deleteAllAgentUserRoleRight(long agentUserId) throws AppException {
		String hql ="from AgentUserRoleRight where agentUser.id="+agentUserId;
		Query query=this.getQuery(hql);
		
		if(query!=null)
		{
			List list=query.list();
			if(list!=null)
			{
				for(int i=0;i<list.size();i++)
				{
					super.getHibernateTemplate().delete(((AgentUserRoleRight)list.get(i)));
				}
			}
		}	
	}
	

	
	public AgentRoleRight getAgentRoleRightById(long rightId) throws AppException
	{
		if (rightId > 0)
		{
			return (AgentRoleRight) this.getHibernateTemplate().get(AgentRoleRight.class,
			    new Long(rightId));
		}
    return new AgentRoleRight();
	}
}
