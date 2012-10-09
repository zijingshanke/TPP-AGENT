package com.fordays.fdpay.cooperate.dao;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.cooperate.ActionLog;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ActionLogDAOImp extends BaseDAOSupport implements ActionLogDAO {
	private TransactionTemplate actionLogTemplate;

	public void setActionLogManager(PlatformTransactionManager actionLogTemplate) {
		this.actionLogTemplate = new TransactionTemplate(actionLogTemplate);
	}

	public long save(ActionLog actionLog) throws Exception {
		this.getHibernateTemplate().saveOrUpdate(actionLog);
		return actionLog.getId();
	}

	public long update(ActionLog actionLog) throws Exception {
		if (actionLog.getId() > 0)
			return save(actionLog);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public ActionLog queryActionLogByOrderId(long orderId) throws AppException {
		/*
		 * Hql hql = new Hql("from ActionLog where exists (from OrderDetails
		 * where id=?)"); hql.addParamter(orderId); Query query =
		 * this.getQuery(hql); ActionLog actionLog=null; if (query!=null&&
		 * query.list()!=null&&query.list().size() > 0) { actionLog=(ActionLog)
		 * query.list().get(0); } return actionLog;
		 */

		return queryActionLogByOrderId(orderId, 1);
	}

	public ActionLog queryActionLogByOrderId(long orderId, long logType)
			throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("from ActionLog where orderDetails.id=? and logType=? ");
		Hql hql = new Hql(sb.toString());
		hql.addParamter(orderId);
		hql.addParamter(logType);
		Query query = this.getQuery(hql);
		ActionLog actionLog = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			actionLog = (ActionLog) query.list().get(0);
		}
		return actionLog;
	}

	/**
	 * @param long 
	 * @param long
	 * @param String content 部分或全部
	 */
	public ActionLog queryActionLogByOrderId(long orderId, long logType,
			String content) throws AppException {
		StringBuffer sb = new StringBuffer();
		sb.append("from ActionLog where orderDetails.id=? and logType=? and content like ? ");
		Hql hql = new Hql(sb.toString());
		hql.addParamter(orderId);
		hql.addParamter(logType);
		hql.addParamter(content);
		Query query = this.getQuery(hql);
		ActionLog actionLog = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			actionLog = (ActionLog) query.list().get(0);
		}
		return actionLog;
	}

	public ActionLog queryActionLogByContent(String content)
			throws AppException {
		Hql hql = new Hql("from ActionLog where content=?");
		hql.addParamter(content);
		Query query = this.getQuery(hql);
		ActionLog actionLog = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			actionLog = (ActionLog) query.list().get(0);
		}
		return actionLog;

	}

	public ActionLog queryActionLogByTranNo(String partner, String tranNo,
			long logType) throws AppException {
		StringBuffer sb = new StringBuffer();
		// "exists (from OrderDetails where id=?)"
		sb.append("from ActionLog where orderDetails.id=?  and logType=? "); // in
																				// (select
																				// id
																				// from
																				// OrderDetails
																				// where
																				// partner=?
																				// and
																				// order_details_no=?)
																				// and
																				// logType=?
																				// ");
		Hql hql = new Hql(sb.toString());
		hql.addParamter(new Long(tranNo));
		hql.addParamter(logType);
		Query query = this.getQuery(hql);
		ActionLog actionLog = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			actionLog = (ActionLog) query.list().get(0);
		}
		return actionLog;
	}

}
