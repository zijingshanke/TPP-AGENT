package com.fordays.fdpay.agent.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.AgentUser;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AgentUserDAOImpl extends BaseDAOSupport implements AgentUserDAO {

	public AgentUser add(AgentUser agentUser) throws AppException {
		// TODO Auto-generated method stub
		if(agentUser!=null){
			this.getHibernateTemplate().save(agentUser);
			return agentUser;
		}
		return new AgentUser();
	}

	public void delete(AgentUser agentUser) throws AppException {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(agentUser);
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			AgentUser agentUser = (AgentUser) this.getHibernateTemplate().get(
					AgentUser.class, new Long(id));
			this.getHibernateTemplate().delete(agentUser);

		}
	}

	public AgentUser getByQueryString(String no, String password)
			throws AppException {
		// TODO Auto-generated method stub
		Hql hql = new Hql("  from AgentUser where no=? and password=? ");
		hql.addParamter(no);
		hql.addParamter(password);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0) {
			return (AgentUser) query.list().get(0);
		}
		return null;
	}

	public AgentUser getByQueryString(String no)
			throws AppException {
		// TODO Auto-generated method stub
		Hql hql = new Hql("  from AgentUser where no=?");
		hql.addParamter(no);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0) {
			return (AgentUser) query.list().get(0);
		}
		return null;
	}
	
	public AgentUser isExist(String no,Long id) throws AppException{
		Hql hql = new Hql("  from AgentUser where id=? and no=?");
		hql.addParamter(id);
		hql.addParamter(no);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0) {
			return (AgentUser) query.list().get(0);
		}
		return null;
	}
	
	public AgentUser getById(Long id)
		throws AppException {
		// TODO Auto-generated method stub
		Hql hql = new Hql("  from AgentUser where id=?");
		hql.addParamter(id);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0) {
			return (AgentUser) query.list().get(0);
		}
			return null;
	}
	
	public void updateOrSave(AgentUser agentUser) throws AppException {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(agentUser);
	}

	public List list(Long agentId) throws AppException {
		// TODO Auto-generated method stub
		Hql hql=new Hql(" from AgentUser where agent.id=? order by id desc");
		hql.addParamter(agentId);
		Query query = this.getQuery(hql);
		if(query!=null && query.list().size()>0){
			return query.list();
		}
		return null;
	}

}
