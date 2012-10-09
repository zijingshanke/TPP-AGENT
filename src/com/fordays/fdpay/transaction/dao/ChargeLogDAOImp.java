package com.fordays.fdpay.transaction.dao;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeLog;
import com.neza.base.BaseDAOSupport;
import com.neza.exception.AppException;

public class ChargeLogDAOImp extends BaseDAOSupport implements ChargeLogDAO {
	private TransactionTemplate chargeLogTemplate;

	/**
	 * @保存充值日志
	 * @param Charge
	 *            charge
	 * @param String
	 *            content
	 * @return long
	 * @author YanRui
	 */
	public long saveChargeLog(Charge charge, String content)
			throws AppException {
		ChargeLog cl = new ChargeLog();
		Agent agent = getAgentById(charge.getAgent().getId());// 勿改
		cl.setAgent(agent);
		cl.setAmount(charge.getAmount());
		cl.setContent(content);
		cl.setOrderNo(charge.getOrderNo());
		cl.setChargeDate(new java.sql.Timestamp(System.currentTimeMillis()));
		cl.setStatus(charge.getStatus());
		cl.setOperater(agent.getName());
		save(cl);
		return cl.getId();
	}

	// 勿删
	// author:YanRui
	public Agent getAgentById(long id) throws AppException {
		Agent agent;
		if (id > 0) {
			agent = (Agent) this.getHibernateTemplate().get(Agent.class,
					new Long(id));
			return agent;
		} else {
			return null;
		}
	}

	public void setChargeManager(PlatformTransactionManager chargeLogManager) {
		this.chargeLogTemplate = new TransactionTemplate(chargeLogManager);
	}

	public long save(ChargeLog chargeLog) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(chargeLog);
		return chargeLog.getId();
	}

	public long merge(ChargeLog chargeLog) throws AppException {
		this.getHibernateTemplate().merge(chargeLog);
		return chargeLog.getId();
	}

	public long update(ChargeLog chargeLog) throws AppException {
		if (chargeLog.getId() > 0)
			return save(chargeLog);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			ChargeLog chargeLog = (ChargeLog) this.getHibernateTemplate().get(
					ChargeLog.class, new Long(id));
			this.getHibernateTemplate().delete(chargeLog);
		}
	}

	public ChargeLog getChargeLogById(long id) throws AppException {
		ChargeLog chargeLog;
		if (id > 0) {
			chargeLog = (ChargeLog) this.getHibernateTemplate().get(
					ChargeLog.class, new Long(id));
			return chargeLog;
		} else
			return new ChargeLog();
	}

	public ChargeLog queryChargeLogById(long id) throws AppException {
		if (id > 0) {
			ChargeLog chargeLog = (ChargeLog) this.getQuery(
					"from ChargeLog where id=" + id).uniqueResult();
			if (chargeLog != null) {
				return chargeLog;
			} else {
				return new ChargeLog();
			}
		} else
			return new ChargeLog();
	}
}
