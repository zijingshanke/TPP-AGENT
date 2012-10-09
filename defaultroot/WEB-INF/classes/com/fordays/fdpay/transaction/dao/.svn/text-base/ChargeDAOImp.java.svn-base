package com.fordays.fdpay.transaction.dao;

import java.util.List;

import org.hibernate.FlushMode;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ChargeDAOImp extends BaseDAOSupport implements ChargeDAO {
	private TransactionTemplate chargeTemplate;

	public void setChargeManager(PlatformTransactionManager chargeManager) {
		this.chargeTemplate = new TransactionTemplate(chargeManager);
	}

	public long save(Charge charge) throws AppException {		
		this.getHibernateTemplate().saveOrUpdate(charge);
		return charge.getId();
	}


	public long merge(Charge charge) throws AppException {
		this.getHibernateTemplate().merge(charge);
		return charge.getId();
	}

	public long update(Charge charge) throws AppException {
		if (charge.getId() > 0)
		{
			FlushMode tempMode=this.getSession().getFlushMode();
			this.getSession().setFlushMode(FlushMode.AUTO);
			save(charge);		
			this.getSession().flush();
			this.getSession().setFlushMode(tempMode);
			return charge.getId();
		}
			
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			Charge charge = (Charge) this.getHibernateTemplate().get(
					Charge.class, new Long(id));
			this.getHibernateTemplate().delete(charge);
		}
	}

	public Charge getChargeById(long id) throws AppException {
		Charge charge;
		if (id > 0) {
			charge = (Charge) this.getHibernateTemplate().get(Charge.class,
					new Long(id));
			return charge;
		} else
			return new Charge();
	}

	public Charge queryChargeById(long id) throws AppException {
		if (id > 0) {
			Charge charge = (Charge) this.getQuery(
					"from Transaction where id=" + id).uniqueResult();
			if (charge != null) {
				return charge;
			} else {
				return new Charge();
			}
		} else
			return new Charge();
	}

	public Charge getChargeByOrderNo(String orderno) throws AppException {
		Charge charge = (Charge) this.getQuery(
				"from Charge  where orderNo='" + orderno + "'").uniqueResult();
		if (charge != null) {
			return charge;
		} else {
			return new Charge();
		}
	}

	public List getChargeByAgent(Agent agent, ChargeListForm clf)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from  Charge c  where c.agent.id=?");
		hql.addParamter(agent.getId());

		String minDate = clf.getBeginDate();
		String maxDate = clf.getEndDate();
		if (minDate.equals("") == false && maxDate.equals("") == true) {
			hql.add(" and to_char(c.chargeDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (minDate.equals("") == true && maxDate.equals("") == false) {
			hql.add(" and to_char(c.chargeDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
		if (minDate.equals("") == false && maxDate.equals("") == false) {
			hql
					.add(" and to_char(c.chargeDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}
		if (clf.getStatus() >= 0) {
			hql.add(" and c.status=?");
			hql.addParamter(clf.getStatus());
		}

		hql.add("and c.type<>?");
		hql.addParamter(Charge.CHARGE_TYPE_BACKGROUND);

		hql.add(" order by c.chargeDate desc");
		System.out.println("HQL>>>>" + hql);

		return this.list(hql, clf);
	}
}