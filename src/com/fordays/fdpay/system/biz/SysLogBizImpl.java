package com.fordays.fdpay.system.biz;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.system.SysLog;
import com.fordays.fdpay.system.dao.SysLogDAO;
import com.neza.exception.AppException;

public class SysLogBizImpl implements SysLogBiz {
	private SysLogDAO sysLogDAO;
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void saveSysLog(SysLog user) throws AppException {
		sysLogDAO.save(user);
	}

	public void setSysLogDAO(SysLogDAO sysLogDAO) {
		this.sysLogDAO = sysLogDAO;
	}

}
