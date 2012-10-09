package com.fordays.fdpay.transaction.biz;

import java.sql.Timestamp;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.exception.AppException;

public class TransactionTaskBizImpl implements TransactionTaskBiz {

	private TransactionDAO transactionDAO;
	private TransactionTemplate transactionTemplate;
	
	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
	
	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}
	
	public void autoCloseTransaction(Transaction transaction) throws AppException {
		Transaction trans = transactionDAO.getTransactionById(transaction.getTid());
		OrderDetails orderDetails = transactionDAO.getOrderDetailsById(trans.getOrderid());
		orderDetails.setStatus(Transaction.STATUS_4);//关闭交易
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateOrderDetails(orderDetails);
		
		trans.setCloseReson(""); //暂时不知道原因,先留着,要根据情况判断是什么原因关闭
		
		trans.setCloseDate(new Timestamp(System.currentTimeMillis()));
		trans.setStatus(Transaction.STATUS_4);//关闭交易
		trans.setOrderDetails(orderDetails);
		transactionDAO.updateTransactionStatus(trans);
	}

}
