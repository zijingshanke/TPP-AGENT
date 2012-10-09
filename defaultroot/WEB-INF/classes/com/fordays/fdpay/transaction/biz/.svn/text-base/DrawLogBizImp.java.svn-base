package com.fordays.fdpay.transaction.biz;

import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.transaction.DrawLog;
import com.fordays.fdpay.transaction.dao.DrawLogDAO;
import com.neza.exception.AppException;

public class DrawLogBizImp implements DrawLogBiz {

	private DrawLogDAO drawLogDAO;
	private TransactionTemplate transactionTemplate;

	public DrawLogDAO getDrawLogDAO() {
		return drawLogDAO;
	}

	public void setDrawLogDAO(DrawLogDAO drawLogDAO) {
		this.drawLogDAO = drawLogDAO;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void deleteById(long id) throws AppException {
		drawLogDAO.deleteById(id);
	}

	public long merge(DrawLog drawLog) throws AppException {

		return drawLogDAO.merge(drawLog);
	}

	public long addDrawLog(DrawLog drawLog) throws AppException {

		return drawLogDAO.save(drawLog);
	}

	public long update(DrawLog drawLog) throws AppException {

		return drawLogDAO.update(drawLog);
	}

}
