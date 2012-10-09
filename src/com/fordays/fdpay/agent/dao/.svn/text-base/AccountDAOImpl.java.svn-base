package com.fordays.fdpay.agent.dao;

import java.util.List;

import org.hibernate.Query;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AccountDAOImpl extends BaseDAOSupport implements AccountDAO {
	public AccountDAOImpl(){
		
	}
	public Account getAccountByAgentIdcertificationStatus(Long agentId,Long status)throws AppException {
		Hql hql=new Hql("from Account where agent.id=? and certificationStatus=? order by id desc ");
		hql.addParamter(agentId);
		hql.addParamter(status);
		Query query = this.getQuery(hql);
		Account reaccount=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			reaccount =(Account)query.list().get(0);
		}
		return reaccount;
	}
	
	public List getAccountByAgentId(Long agentId)throws AppException {
		Hql hql=new Hql("from Account where agent.id=? ");
		hql.addParamter(agentId);
		Query query = this.getQuery(hql);	
		if(query.list().size()==0||query.list()==null){
			return null;
		}
		return query.list();
	}

	public Account getAccountByBind(Long agentId,Long bindStatus)throws AppException{
		Hql hql=new Hql("from Account where agent.id=? and bindStatus=?");
		hql.addParamter(agentId);
		hql.addParamter(bindStatus);
		Query query = this.getQuery(hql);
		Account reaccount=null;
		
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			reaccount =(Account)query.list().get(0);
		}
		return reaccount;
	}
	public List getAccounts(Long agentId)throws AppException {
		Hql hql=new Hql("from Account where agent.id=?");
		hql.addParamter(agentId);
		Query query = this.getQuery(hql);
		if(query!=null&&query.list().size()>0){
			return query.list();
		}
		return null;
	}

	
	public void saveAccount(Account account)throws AppException {
		this.getHibernateTemplate().saveOrUpdate(account);
		
	}

	public void updateAccount(Account account) throws AppException{
		this.getHibernateTemplate().update(account);
		
	}

	public Account getAccountByBanknum(String banknum) throws AppException {	
		Hql hql=new Hql("from Account where cardNo=? ");
		hql.addParamter(banknum);
		Query query = this.getQuery(hql);
		Account reaccount=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			reaccount =(Account)query.list().get(0);
		}
		return reaccount;
	}
	
	public Account getAccountById(Long accountId) throws AppException {	
		Hql hql=new Hql("from Account where id=? ");
		hql.addParamter(accountId);
		Query query = this.getQuery(hql);
		Account reaccount=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			reaccount =(Account)query.list().get(0);
		}
		return reaccount;
	}
	
	public void deleteById(long id) throws AppException
	{
		if (id > 0)
		{
			Account account = (Account) this.getHibernateTemplate().get(Account.class,
			    new Long(id));
			this.getHibernateTemplate().delete(account);

		}
	}

}
