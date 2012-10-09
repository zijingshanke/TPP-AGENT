package com.fordays.fdpay.agent.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentContact;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.CertInfo;

import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.database.SelectDataBean;
import com.neza.exception.AppException;

public class AgentDAOImpl extends BaseDAOSupport implements AgentDAO
{
	public Agent checkAgent(Agent ag) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Agent a where LOWER(a.loginName)= LOWER(?) or LOWER(a.mobilePhone)= LOWER(?) and a.mobileLoginStatus= ? ");
		hql.addParamter(ag.getLoginName());
		hql.addParamter(ag.getLoginName());
		hql.addParamter(new Long(1));
		System.out.println("hql>>>>>>>>>>>>>>>>" + hql);
		Query query = this.getQuery(hql);
		Agent agent = null;
		if (query.list().size() > 0)
		{
			agent = (Agent) query.list().get(0);
		}
		return agent;
	}

	public Agent checkAgent2(Agent ag) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Agent a where LOWER(a.loginName)= LOWER(?) or LOWER(a.tempEmail)= LOWER(?)");
		hql.addParamter(ag.getLoginName());
		hql.addParamter(ag.getLoginName());
		hql.addParamter(new Long(1));
		System.out.println("hql>>>>>>>>>>>>>>>>" + hql);
		Query query = this.getQuery(hql);
		Agent agent = null;
		if (query.list().size() > 0)
		{
			agent = (Agent) query.list().get(0);
		}
		return agent;
	}

	public boolean IsMobileBindingQuestion(Agent ag) throws AppException
	{
		String hql = "from Agent a where a.mobilePhone='" + ag.getMobilePhone()
		    + "' and a.mobileQuestionStatus=1 ";
		Query query = this.getQuery(hql);
		if (query != null)
		{
			List list = query.list();
			if (list != null && list.size() > 0)
			{
				Agent aa = (Agent) list.get(0);
				if (list.size() == 1 && aa.getId() == ag.getId()) { return true; }
				return false;
			}
		}
		return true;
	}

	public Agent getAgentByMobilePhone(String mobilePhone)
	{
		String hql = "from Agent a where a.mobilePhone='" + mobilePhone+"'";
		Query query = this.getQuery(hql);
		Agent agent=null;
		if (query != null)
		{
			try{
			 agent=(Agent)query.uniqueResult();
			}catch(HibernateException he){
				System.out.println("重复绑定的手机号码:"+mobilePhone);
				he.printStackTrace();
				return new Agent();
			}	
		}
		return agent;
	}
	public boolean IsMobileBindingLogin(Agent ag) throws AppException
	{
		String hql = "from Agent a where a.mobilePhone='" + ag.getMobilePhone()
		    + "' and a.mobileLoginStatus=1 ";
		Query query = this.getQuery(hql);

		if (query != null)
		{

			List list = query.list();

			if (list != null && list.size() > 0)
			{
				Agent aa = (Agent) list.get(0);
				if (list.size() == 1 && aa.getId() == ag.getId()) { return true; }
				return false;
			}
		}

		return true;
	}

	public boolean IsMobileBindingPassword(Agent ag) throws AppException
	{
		String hql = "from Agent a where a.mobilePhone='" + ag.getMobilePhone()
		    + "' and a.mobilePasswordStatus=1 ";
		Query query = this.getQuery(hql);

		if (query != null)
		{

			List list = query.list();

			if (list != null && list.size() > 0)
			{
				Agent aa = (Agent) list.get(0);
				if (list.size() == 1 && aa.getId() == ag.getId()) { return true; }
				return false;
			}
		}

		return true;
	}

	public boolean IsMobileBindingPay(Agent ag) throws AppException
	{
		String hql = "from Agent a where a.mobilePhone='" + ag.getMobilePhone()
		    + "' and a.mobilePayStatus=1 ";
		Query query = this.getQuery(hql);
		if (query != null)
		{

			List list = query.list();

			if (list != null && list.size() > 0)
			{
				Agent aa = (Agent) list.get(0);
				if (list.size() == 1 && aa.getId() == ag.getId()) { return true; }
				return false;
			}
		}

		return true;
	}

	public Agent register(Agent ag) throws AppException
	{
		try
		{
			super.getHibernateTemplate().save(ag);
			Connection con = super.getHibernateTemplate().getSessionFactory()
			    .getCurrentSession().connection();
			con.setAutoCommit(true);
			CallableStatement call = con.prepareCall("{Call S_P_A}");
			call.execute();
			call.close();
			con.commit();
			con.close();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return ag;
	}

	public Agent getAgentById(long id) throws AppException
	{
		Agent agent;
		if (id > 0)
		{
			agent = (Agent) this.getHibernateTemplate()
			    .get(Agent.class, new Long(id));
			return agent;
		}
		else
		{
			return null;
		}
	}

	public void update(Agent ag) throws AppException
	{

		this.getHibernateTemplate().saveOrUpdate(ag);// update(ag);
	}

	public Agent getAgentByName(String name) throws AppException
	{
		String hql = "from Agent a where LOWER(a.loginName)=LOWER('" + name
		    + "') or LOWER(a.tempEmail)=LOWER('" + name + "') or a.mobilePhone='"
		    + name + "'";
		Query query = this.getQuery(hql);
		Agent agent = new Agent();
		if (query != null)
		{
			if (query.list().size() != 0)
				return (Agent) query.list().get(0);
			else
				return null;
		}

		return agent;
	}

	public Agent checkPayPassword(Agent agent) throws AppException
	{
		String hql = "from Agent a where LOWER(a.loginName)=LOWER('"
		    + agent.getLoginName() + "') and a.payPassword='"
		    + agent.getPayPassword() + "'";
		Query query = this.getQuery(hql);

		if (query != null)
		{
			if (query.list().size() != 0)
				return (Agent) query.list().get(0);
			else
				return null;
		}
		else
			return null;

	}

	public Agent checkCanPay(Agent agent) throws AppException
	{
		String hql = "from Agent a where LOWER(a.loginName)=LOWER('"
		    + agent.getLoginName() + "') and canPay=1";
		Query query = this.getQuery(hql);

		if (query != null)
		{
			if (query.list().size() != 0)
				return (Agent) query.list().get(0);
			else
				return null;
		}
		else
			return null;

	}

	public List getContactList(AgentListForm alf) throws AppException
	{
		String hql = " from AgentContact a where 1=1";

		if (alf.getAgentId() != 0)
		{
			hql += " and a.agent.id=" + alf.getAgentId();
		}
		return this.list(hql, alf);
	}

	public Agent checkAgentByContactLoginName(String contactLoginName)
	    throws AppException
	{
		String hql = "from Agent a where LOWER(a.loginName)=LOWER('"
		    + contactLoginName + "')";
		Query query = this.getQuery(hql);
		Agent agent = new Agent();
		if (query != null)
		{
			if (query.list().size() > 0)
			{
				agent = (Agent) query.list().get(0);
			}
			return agent;
		}
		return null;
	}

	public void addOrUpdateContact(AgentContact ac) throws AppException
	{
		if (ac.getActionType().equals("add"))
			super.getHibernateTemplate().save(ac);
		else
			super.getHibernateTemplate().update(ac);
	}

	public AgentContact checkContactByLoginName(Agent agent,
	    String contactLoginName, String actionType, String aId)
	    throws AppException
	{
		AgentContact ac = null;
		String hql = "";
		if (actionType.equals("add"))
			hql = "from AgentContact ac where ac.agent.id='" + agent.getId()
			    + "' and LOWER(ac.email)=LOWER('" + contactLoginName + "')";
		else
			hql = "from AgentContact ac where ac.agent.id='" + agent.getId()
			    + "' and LOWER(ac.email)=LOWER('" + contactLoginName
			    + "') and ac.id<>'" + aId + "'";
		Query query = this.getQuery(hql);

		if (query != null)
		{
			if (query.list().size() > 0)
			{
				ac = (AgentContact) query.list().get(0);
			}
		}
		return ac;
	}

	public AgentContact getAgentContactById(String acId) throws AppException
	{
		String hql = "from AgentContact ac where ac.id='" + acId + "'";
		Query query = this.getQuery(hql);
		AgentContact ac = new AgentContact();
		if (query != null)
		{
			if (query.list().size() > 0)
			{
				ac = (AgentContact) query.list().get(0);
			}
		}
		return ac;
	}

	public List findListByHql(String deletelist[])
	{
		List list = new ArrayList();
		AgentContact ac = new AgentContact();
		for (int i = 0; i < deletelist.length; i++)
		{
			String hql = "from AgentContact ac where ac.id='" + deletelist[i] + "'";
			Query query = this.getQuery(hql);
			ac = (AgentContact) query.list().get(0);
			list.add(ac);
		}
		return list;
	}

	public void deleteContact(String deletelist[]) throws AppException
	{
		List list = this.findListByHql(deletelist);
		this.getHibernateTemplate().deleteAll(list);
	}

	public Agent checkEmail(String Email) throws AppException
	{
		String hql = "from Agent a where LOWER(a.loginName)=LOWER('" + Email
		    + "') or LOWER(a.tempEmail)=LOWER('" + Email + "')";
		Agent agent = null;

		Query query = this.getQuery(hql);

		if (query != null)
		{

			List list = query.list();

			if (list != null && list.size() > 0)
			{
				agent = (Agent) list.get(0);
			}

		}
		return agent;
	}

	public List getAgentAddressManage(AgentAddress agentAddress)
	    throws AppException
	{
		String hql = " from AgentAddress a where a.agent.id='"
		    + agentAddress.getAgent().getId() + "'";
		Query query = this.getQuery(hql);
		return query.list();
	}

	public long addAgentAddress(AgentAddress agentAddress) throws AppException
	{
		this.getHibernateTemplate().save(agentAddress);
		return agentAddress.getId();
	}

	public AgentAddress editAgentAddressById(long aid) throws AppException
	{
		AgentAddress agentAddress;
		if (aid > 0)
		{
			agentAddress = (AgentAddress) this.getHibernateTemplate().get(
			    AgentAddress.class, new Long(aid));
			return agentAddress;
		}
		else
			return null;
	}

	public void updateAgentAddressById(AgentAddress agentAddress)
	    throws AppException
	{
		this.getHibernateTemplate().update(agentAddress);
	}

	public void deleteAgentAddress(long aid) throws AppException
	{
		if (aid > 0)
		{
			AgentAddress agentAddress = (AgentAddress) this.getHibernateTemplate()
			    .get(AgentAddress.class, new Long(aid));
			this.getHibernateTemplate().delete(agentAddress);
		}
	}

	public Agent getAgentByLoginName(String loginName) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Agent a where LOWER(a.loginName)=LOWER(?) or LOWER(a.tempEmail)=LOWER(?)");
		hql.addParamter(loginName);
		hql.addParamter(loginName);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
			return (Agent) query.list().get(0);
		else
		{
			return null;
		}
	}

	private void updateAddressStatusIsNull(AgentAddress agentAddress)
	    throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("update AgentAddress a set a.status=0 where a.id<>? and a.agent.id=?");
		hql.addParamter(agentAddress.getId());
		hql.addParamter(agentAddress.getAgent().getId());
		Query query = this.getQuery(hql);
		query.executeUpdate();
	}

	public void setDefaultAddress(AgentAddress agentAddress) throws AppException
	{
		this.updateAddressStatusIsNull(agentAddress);
		this.getHibernateTemplate().update(agentAddress);
	}

	public AgentAddress getAgentAddressById(Agent agent) throws AppException
	{
		AgentAddress agentAddress = null;
		Hql hql = new Hql();
		hql.add("from AgentAddress a where a.agent.id=?");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
		{
			agentAddress = (AgentAddress) query.list().get(0);
		}
		return agentAddress;
	}

	// 增加商户余额
	public void addAmount(Agent agent, BigDecimal amount) throws AppException
	{	
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =this.getAgentBalance(agent.getId());
		FlushMode tempMode = this.getSession().getFlushMode();
		this.getSession().setFlushMode(FlushMode.AUTO);
		agent.setAllowBalance(ab.getAllowBalance().add(amount));
		this.update(agent);
		this.getSession().flush();
		this.getSession().setFlushMode(tempMode);
	}

	// 减少商户余额
	public void reduceAmount(Agent agent, BigDecimal amount) throws AppException
	{
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =this.getAgentBalance(agent.getId());
		FlushMode tempMode = this.getSession().getFlushMode();
		this.getSession().setFlushMode(FlushMode.AUTO);
		agent.setAllowBalance(ab.getAllowBalance().subtract(amount));
		this.update(agent);
		this.getSession().flush();
		this.getSession().setFlushMode(tempMode);
	}

	public void addCreditAmount(Agent agent, BigDecimal amount)
	    throws AppException
	{
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =this.getAgentBalance(agent.getId());
		FlushMode tempMode = this.getSession().getFlushMode();
		this.getSession().setFlushMode(FlushMode.AUTO);
		agent.setCreditBalance(ab.getCreditBalance().add(amount));
		this.update(agent);
		this.getSession().flush();
		this.getSession().setFlushMode(tempMode);
	}

	// 移动商户从冻结余额到可用余额
	public void moveNotallowBalanceToAllowBalance(Agent agent, BigDecimal amount)
	    throws AppException
	{
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =this.getAgentBalance(agent.getId());
		agent.setNotallowBalance(ab.getNotAllowBalance().subtract(amount));
		agent.setAllowBalance(ab.getAllowBalance().add(amount));
		this.update(agent);
	}

	// 移动商户从可用余额到冻结余额
	public void _moveAllowBalanceToNotallowBalance(Agent agent, BigDecimal amount)
	    throws AppException
	{
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =this.getAgentBalance(agent.getId());
		agent.setNotallowBalance(ab.getNotAllowBalance().add(amount));
		agent.setAllowBalance(ab.getAllowBalance().subtract(amount));
		this.update(agent);
	}

	// 移动商户从可用余额到冻结余额
	public void moveAllowBalanceToNotallowBalance(Agent agent, BigDecimal amount)
	    throws AppException
	{
		String sql = "update agent set allow_balance = allow_balance - " + amount
		    + " , NOTALLOW_BALANCE = NOTALLOW_BALANCE + " + amount + " where id="
		    + agent.getId();
		System.out.println("----移动商户从可用余额到冻结余额----" + sql);
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	// 20090507-ref001-jason-s add for cooperate
	public Agent getAgentByEmailTempEmail(String loginName) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Agent a where LOWER(a.loginName)=LOWER(?) or LOWER(a.tempEmail)=LOWER(?)");
		hql.addParamter(loginName);
		hql.addParamter(loginName);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
			return (Agent) query.list().get(0);
		else
		{
			return null;
		}
	}

	public Agent getAgentByEmail(String loginName) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Agent a where LOWER(a.loginName)=LOWER(?) ");
		hql.addParamter(loginName);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
			return (Agent) query.list().get(0);
		else
		{
			return null;
		}
	}

	public Agent queryByEmailAndPassword(String email, String password)
	    throws AppException
	{
		Query query = this
		    .getQuery("from Agent where LOWER(email)=LOWER(?) and payPassword=?");
		query.setString(0, email);
		query.setString(1, password);
		if (query.list() != null && query.list().size() > 0)
			return (Agent) query.list().get(0);
		else
			return null;
	}

	public Agent queryByPartner(String partner) throws AppException

	{
		Query query = this
		    .getQuery("from Agent where id =(select agent.id from Coterie where partner=?)");
		query.setString(0, partner);
		if (query != null && query.list() != null && query.list().size() > 0)
			return (Agent) query.list().get(0);
		else
			return null;
	}

	// 20090507-ref001-jason-e add for cooperate

	public AgentRelationship getAgentRelationship(Agent agent)
	    throws AppException
	{
		AgentRelationship agentRelationship = null;
		Hql hql = new Hql();
		// hql.add("from Agent a where exists (from AgentRelationship where
		// parentAgent.id=a.id and agent.id=? and expireDate>=sysdate)");
		hql.add("from AgentRelationship where agent.id=? and expireDate>=sysdate");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
			agentRelationship = (AgentRelationship) query.list().get(0);

		return agentRelationship;
	}

	public List getMobilePhones() throws AppException
	{
		Hql hql = new Hql("select mobilePhone from Agent where 1=1");
		Query query = this.getQuery(hql);
		return query.list();
	}

	public boolean isPass(Agent agent) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Agent where id=? and status=3");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0) { return true; }
		return false;
	}

	public void synallowBalance(Agent agent) throws AppException
	{
		Session session = this.getSessionFactory().getCurrentSession();

		// Transaction tx= session.beginTransaction();
		try
		{
			synchronized (agent)
			{
				Connection con = session.connection();
				con.setAutoCommit(true);
				CallableStatement call = con.prepareCall("{Call S_B(?)}");
				call.setInt("agentId", (int) agent.getId());
				call.execute();
				call.close();
				con.commit();
				con.close();
			}
			/*
			 * Connection con = session.connection();
			 * 
			 * String procedure = "{Call S_B(?)}"; CallableStatement cstmt =
			 * con.prepareCall(procedure); cstmt.setInt("agentId", (int)
			 * agent.getId()); cstmt.executeUpdate(); tx.commit();
			 */

		}
		catch (Exception ex)
		{
			System.out
			    .println("---------------------------同步余额失败：" + ex.getMessage());

			/*
			 * if (tx != null) { tx.rollback(); }
			 */

		}

	}

	public void _synallowBalance(Agent agent) throws AppException
	{
		Hql hql = new Hql();

		hql.add("update Agent ");
		hql.add("set allow_balance  = cal_curr_bal_by_date(?,  sysdate),");
		hql.add("notallow_balance = cal_notallow_bal_by_date(?,  sysdate),");
		hql.add(" credit_balance = cal_credit_bal_by_date(?,  sysdate)");
		hql.add(" where id = ?");
		if (agent != null)
		{
			hql.addParamter(agent.getId());
			hql.addParamter(agent.getId());
			hql.addParamter(agent.getId());
			hql.addParamter(agent.getId());
			this.getQuery(hql).executeUpdate();
		}
	}

	public BigDecimal getAllowBalance(long agentId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("select cal_curr_bal_by_date(?,sysdate) from agent ");
		hql.addParamter(0, agentId);
		Query query = this.getQuery(hql);
		if (query != null)
		{
			Object obj = query.uniqueResult();
			if (obj != null) { return ((BigDecimal) obj); }
		}
		return new BigDecimal("0");
	}

	public Agent getAgentByBindingMobilePay(String mobile) throws AppException
	{
		Hql hql = new Hql();

		hql
		    .add("from Agent a where a.mobilePhone=? and a.mobilePayStatus=1 and a.mobileBindStatus=1 ");
		hql.addParamter(mobile);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
			return (Agent) query.list().get(0);
		else
		{
			return null;
		}
	}

	public CertInfo getCertById(long id) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from CertInfo where id =?");
		hql.addParamter(id);
		Query query = this.getQuery(hql);
		if (query != null)
		{
			List list = query.list();
			if (list != null) { return (CertInfo) list.get(0); }
		}

		return new CertInfo();

	}

	public BigDecimal getAgentAllowBalance(long agentId) throws AppException
	{
		Hql hql = new Hql("select cal_curr_bal_by_date(" + agentId
		    + ",to_date('3000-01-01','yyyy-MM-dd')) as bal from dual");

		Query query = this.getSessionFactory().openSession().createSQLQuery(
		    hql.getSql()).addScalar("bal", Hibernate.BIG_DECIMAL);
		if (query != null)
		{
			BigDecimal balance = (BigDecimal) query.uniqueResult();
			if (balance != null) { return balance; }
		}
		return new BigDecimal("0");
	}

	public AgentBalance getAgentBalance(long agentId) throws AppException
	{

		String temp = "to_date('3000-01-01','yyyy-MM-dd')";
		Hql hql = new Hql();

		hql.add("select cal_curr_bal_by_date(" + agentId + "," + temp
		    + ") as curr,");
		hql.add("cal_notallow_bal_by_date(" + agentId + "," + temp
		    + ") as notallow,");
		hql.add("cal_credit_bal_by_date(" + agentId + "," + temp + ") ");
		hql.add("as credit from dual");

		Query query = this.getSessionFactory().openSession().createSQLQuery(
		    hql.getSql()).addScalar("curr", Hibernate.BIG_DECIMAL).addScalar(
		    "notallow", Hibernate.BIG_DECIMAL).addScalar("credit",
		    Hibernate.BIG_DECIMAL);
		try
		{

			if (query != null)
			{

				Object[] bals = (Object[]) query.uniqueResult();
				// System.out.println("-----------"+bals[0].toString()+"--------"+bals[1].toString()+"------"+bals[1].toString());
				if (bals.length == 3)
					return new AgentBalance(agentId, new BigDecimal(bals[0].toString()),
					    new BigDecimal(bals[1].toString()), new BigDecimal(bals[2]
					        .toString()));
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new AgentBalance(agentId, new BigDecimal("0"), new BigDecimal("0"),
		    new BigDecimal("0"));
	}

	public BigDecimal _getAgentAllowBalance(long agentId) throws AppException
	{
		SelectDataBean sdb = new SelectDataBean();
		Hql hql = new Hql("select cal_curr_bal_by_date(" + agentId
		    + ",sysdate) as bal from dual");
		sdb.setQuerySQL(hql.getSql());
		sdb.executeQuery();

		if (sdb.getRowCount() == 1)
		{
			BigDecimal balance = new BigDecimal(sdb.getColValue(1, 1));
			if (balance != null) { return balance; }
		}
		return new BigDecimal("0");
	}

	public BigDecimal getAgentNotAllowBalance(long agentId) throws AppException
	{
		Hql hql = new Hql("select cal_notallow_bal_by_date(" + agentId
		    + ",to_date('3000-01-01','yyyy-MM-dd')) as bal from dual");

		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(
		    hql.getSql()).addScalar("bal", Hibernate.BIG_DECIMAL);
		if (query != null)
		{
			BigDecimal balance = (BigDecimal) query.uniqueResult();
			if (balance != null) { return balance; }
		}
		return new BigDecimal("0");
	}

	public BigDecimal getAgentCreditBalance(long agentId) throws AppException
	{
		Hql hql = new Hql("select cal_credit_bal_by_date(" + agentId
		    + ",to_date('3000-01-01','yyyy-MM-dd')) as bal from dual");

		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(
		    hql.getSql()).addScalar("bal", Hibernate.BIG_DECIMAL);
		if (query != null)
		{
			BigDecimal balance = (BigDecimal) query.uniqueResult();
			if (balance != null) { return balance; }
		}
		return new BigDecimal("0");
	}
}
