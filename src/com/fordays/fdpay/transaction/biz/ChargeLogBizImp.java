package com.fordays.fdpay.transaction.biz;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeLog;
import com.fordays.fdpay.transaction.dao.ChargeLogDAO;
import com.neza.exception.AppException;

public class ChargeLogBizImp implements ChargeLogBiz {
	private ChargeLogDAO chargeLogDAO;
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(HibernateTransactionManager thargeManager) {
		this.transactionTemplate = new TransactionTemplate(thargeManager);
	}

	public void setChargeLogDAO(ChargeLogDAO chargeLogDAO) throws AppException {
		this.chargeLogDAO = chargeLogDAO;
	}

	public ChargeLog getChargeLogById(int id) throws AppException {
		return chargeLogDAO.getChargeLogById(id);
	}

	public void saveChargeLog(ChargeLog chargeLog) throws AppException {
		chargeLogDAO.save(chargeLog);
	}

	public long saveChargeLog(Charge charge, String url) throws AppException {
		return chargeLogDAO.saveChargeLog(charge, url);
	}

	public int updateInfo(ChargeLog chargeLog) throws AppException {
		chargeLogDAO.update(chargeLog);
		return 0;
	}

	public void deleteChargeLog(int id) throws AppException {
		chargeLogDAO.deleteById(id);
	}

	public ChargeLog queryChargeLogById(int id) throws AppException {
		return chargeLogDAO.queryChargeLogById(id);
	}

	public void logCharge(Charge charge) throws AppException {

	}
}
