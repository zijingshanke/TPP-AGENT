package com.fordays.fdpay.agent.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AgentCoterieDAOImpl extends BaseDAOSupport implements AgentCoterieDAO {
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public long save(AgentCoterie agentCoterie) throws AppException {
		/*
		 * this.getHibernateTemplate().saveOrUpdate(agent); return
		 * agent.getId();
		 */
		
		this.getHibernateTemplate().merge(agentCoterie);
		return agentCoterie.getId();
	}

	public long merge(AgentCoterie agentCoterie) throws AppException {
		this.getHibernateTemplate().merge(agentCoterie);
		return agentCoterie.getId();
	}

	public long update(AgentCoterie agentCoterie) throws AppException {
		
		if (agentCoterie.getId() > 0)
			return save(agentCoterie);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	
	public void deleteById(long id) throws AppException {
		this.getHibernateTemplate().delete(this.getAgentCoterieById(id));
	}

	public AgentCoterie  getAgentCoterieById(long id) throws AppException {
		// Session session=HibernateServiceProvider.getSessin();
		AgentCoterie agentCoterie;
		if (id > 0) {
			agentCoterie = (AgentCoterie) this.getHibernateTemplate().get(AgentCoterie.class,
					new Long(id));
			return agentCoterie;
		} else
			return new AgentCoterie();
	}

	public AgentCoterie queryById(long id) throws AppException {
		Query query = this.getQuery("from AgentCoterie where id=" + id);
		if (query != null && query.list() != null && query.list().size() > 0)
			return (AgentCoterie) query.list().get(0);
		else
			return new AgentCoterie();
	}
	
	public AgentCoterie queryByAgentEmail(String  email,String partner) throws AppException {
		Query query = this.getQuery("from AgentCoterie where agent.loginName='"+email+"' and coterie.partner='"+partner+"' ");
		if (query != null && query.list() != null && query.list().size() > 0)
			return (AgentCoterie) query.list().get(0);
		else
			return null;
	}


	public List list(long coterieId) throws AppException {
		String hql= " from AgentCoterie aa where aa.coterie.id="+coterieId;
		return this.list(hql);
	}

	


	public AgentCoterie getAgentCoterieByCoterieAndAgent(String partner,String agentId,String email) throws AppException {
		Hql hql=new Hql();
		StringBuffer sb=new StringBuffer();
		sb.append("from AgentCoterie ac where ac.coterie.partner=? ");
		if(agentId!=null&&!"".equals(agentId)){
			sb.append(" and ac.agent.id=?");
		}else{
			sb.append(" and LOWER(agent.email)=LOWER(?)");			
		}
		hql.add(sb.toString());
		hql.addParamter(partner);
		if(agentId!=null&&!"".equals(agentId)){
			hql.addParamter(new Long(agentId));
		}else{
			hql.addParamter(email);		
		}
		Query query = this.getQuery(hql);		
		if (query != null && query.list() != null && query.list().size() > 0)
			return (AgentCoterie) query.list().get(0);
		else
			return null;
	}

	public List getCoterieByAgentId(long agentId) throws AppException {
		String hql="from AgentCoterie c where c.agent.id="+agentId;
		return this.list(hql);
	}

	public AgentCoterie checkAgentCoterie(Agent agent ,Coterie coterie) throws AppException{
		Hql hql=new Hql();
		hql.add("from AgentCoterie ac where ac.agent.id=? and ac.coterie.id=?");
		hql.addParamter(agent.getId());
		hql.addParamter(coterie.getId());
		Query query = this.getQuery(hql);		
		if (query != null && query.list() != null && query.list().size() > 0){
			return (AgentCoterie) query.list().get(0);
		}
		else
			return null;
	}

	public void deleteAgentCoterie(AgentCoterie ac) throws AppException {
		this.getHibernateTemplate().delete(ac);
	}

	public boolean checkCreditExpireDate(Agent agent, Coterie coterie) throws AppException {
		Hql hql=new Hql();
		hql.add("from AgentCoterie ac where ac.agent.id=? and ac.coterie.id=? and (sysdate between ac.fromDate and ac.expireDate)");
		hql.addParamter(agent.getId());
		hql.addParamter(coterie.getId());
		System.out.println(hql.getSql());
		Query query = this.getQuery(hql);		
		if (query != null && query.list() != null && query.list().size() > 0)
			return true;
		else
			return false;
	}

	public List getAllMemberByPartner(Coterie coterie) throws AppException {
		String hql= " from AgentCoterie aa where aa.coterie.id="+coterie.getId();
		return this.list(hql);
	}
	public boolean checkCoerieByAllowMult(long  agentId) throws AppException{
		
		boolean check=false;
		Hql hql=new Hql();
		hql.add(" select c.allowMultcoterie from Coterie c,AgentCoterie a where a.agent.id=? and  a.coterie.id=c.id");
		hql.addParamter(agentId);
		System.out.println("HQL=>>>"+hql);
		Query q=this.getQuery(hql);
		
		if(q.list().size()>0){
			
		for (int i = 0; i < q.list().size(); i++) {
			System.out.println(q.list().get(i));
			if((q.list().get(i)).equals("0")){
				check=true;
			}
		}
		
		}
			return check;
		
	}
	public int  getAgentCoterieByAgent_Id(long id) throws AppException {
		Hql hql=new Hql();
		hql.add("from AgentCoterie ac where ac.agent.id=?");
		hql.addParamter(id);
		System.out.println("HQL=>>>"+hql);
		Query q=this.getQuery(hql);
		return q.list().size();
	}
	
}
