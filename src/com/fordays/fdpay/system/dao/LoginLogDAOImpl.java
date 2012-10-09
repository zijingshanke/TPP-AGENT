package com.fordays.fdpay.system.dao;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.system.LoginLog;
import com.neza.base.BaseDAOSupport;
import com.neza.exception.AppException;

public class LoginLogDAOImpl extends BaseDAOSupport implements LoginLogDAO {
	private TransactionTemplate transactionTemplate;	
	
	public long save(LoginLog loginlog) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(loginlog);
		return loginlog.getId();
	}
	
	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}
}
