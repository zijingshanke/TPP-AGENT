package com.fordays.fdpay.transaction.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class DrawDAOImp extends BaseDAOSupport implements DrawDAO {
	private TransactionTemplate drawTemplate;

	public long save(Draw draw) throws AppException {
		this.getHibernateTemplate().save(draw);
		return draw.getId();
	}

	public long merge(Draw draw) throws AppException {
		this.getHibernateTemplate().merge(draw);
		return draw.getId();
	}

	public long update(Draw draw) throws AppException {
		if (draw.getId() > 0)
			return save(draw);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Draw draw = (Draw) this.getHibernateTemplate().get(Draw.class,
					new Long(id));
			this.getHibernateTemplate().delete(draw);

		}
	}

	public Draw getDrawById(long id) throws AppException {

		Draw draw;
		if (id > 0) {
			draw = (Draw) this.getHibernateTemplate().get(Draw.class,
					new Long(id));
			return draw;

		} else
			return new Draw();
	}

	public Draw queryDrawById(long id) throws AppException {

		if (id > 0) {
			Draw draw = (Draw) this.getQuery("from Draw where id=" + id)
					.uniqueResult();
			if (draw != null) {
				return draw;
			} else {
				return new Draw();
			}

		} else
			return new Draw();
	}

	public List getDrawsByAgent(Agent agent, DrawListForm drf)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Draw d where d.agent.id=?");
		hql.addParamter(agent.getId());

		if (drf.getStatus() > 0) {
			hql.add(" and d.status=?");
			hql.addParamter(drf.getStatus());
		}
		if (drf.getStatus() == 0) {

			hql.add(" and (d.status=? or d.status=1 or d.status=2)");
			hql.addParamter(drf.getStatus());

		}
		String minDate = drf.getBeginDate();
		String maxDate = drf.getEndDate();
		if (minDate.equals("") == false && maxDate.equals("") == true) {
			hql.add(" and to_char(d.requestDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (minDate.equals("") == true && maxDate.equals("") == false) {
			hql.add(" and to_char(d.requestDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
		if (minDate.equals("") == false && maxDate.equals("") == false) {
			hql
					.add(" and to_char(d.requestDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		hql.add(" order by d.requestDate desc");
		System.out.println("HQL   =   " + hql);
		List list = this.list(hql, drf);
		return list;
	}

	public List getDrawsByAgent(Agent agent) throws AppException {
		Hql hql = new Hql();
		hql.add("from Draw d where d.agent.id=?");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0) {
			return query.list();
		}
		return null;
	}

	/**
	 * 获取商户绑定的提现银行账户信息
	 * 
	 * @param Agent
	 *            agent
	 * @return Account
	 */
	public Account getBindAccountByAgent(Agent agent) throws AppException {
		Account account = null;
		Long agentId = agent.getId();
		Hql hql = new Hql();
		hql.add("from Account a where a.bindStatus=2 and a.agent.id=?");
		hql.addParamter(agent.getId());

		if (agentId > 0) {
			account = (Account) this.getQuery(hql).uniqueResult();
		} else {
			System.out.println(DrawDAOImp.class
					+ "getBindAccountByAgent() agent is Null");
		}
		return account;
	}

	public List list(Draw draw) throws AppException {
		String hql = "from Draw tat where 1=1";
		return this.list(hql);
	}

	public BigDecimal getAmountSumToDay(Agent agent) throws AppException {
		BigDecimal amountSum = new BigDecimal(0.00);
		Hql hql = new Hql(
				"select sum(d.amount) from Draw d where d.agent.id=? and to_char(d.requestDate,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') and d.type=0");
		hql.addParamter(String.valueOf(agent.getId()));
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			if (query.list().get(0) != null) {
				amountSum = ((BigDecimal) query.list().get(0));
			}
		}
		return amountSum;
	}

	public int getDrawViewsToDay(Agent agent) throws AppException {
		int i = 0;
		Hql hql = new Hql(
				"from Draw d where d.agent.id=? and to_char(d.requestDate,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') and d.type=0 and d.status<>4");
		hql.addParamter(String.valueOf(agent.getId()));
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {

			i = query.list().size();

		}
		return i;
	}

	public void deleteByAgentId(Agent agent) throws AppException {
		Hql hql = new Hql("delete Draw where agent.id=?");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		query.executeUpdate();
	}

	public void setDrawManager(PlatformTransactionManager drawManager) {
		this.drawTemplate = new TransactionTemplate(drawManager);
	}

}
