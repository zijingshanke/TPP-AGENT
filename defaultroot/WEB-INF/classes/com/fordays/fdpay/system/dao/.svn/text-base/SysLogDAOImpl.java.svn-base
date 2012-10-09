package com.fordays.fdpay.system.dao;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.system.SysLog;
import com.neza.base.BaseDAOSupport;
import com.neza.exception.AppException;

public class SysLogDAOImpl extends BaseDAOSupport implements SysLogDAO {
	private TransactionTemplate transactionTemplate;

	public long save(SysLog SysLog) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(SysLog);
		return SysLog.getId();
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}
}
