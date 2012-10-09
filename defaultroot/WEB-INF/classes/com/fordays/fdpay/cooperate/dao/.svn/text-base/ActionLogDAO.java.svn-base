package com.fordays.fdpay.cooperate.dao;

import com.fordays.fdpay.cooperate.ActionLog;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ActionLogDAO extends BaseDAO {
	long save(ActionLog actionLog) throws Exception;

	long update(ActionLog actionLog) throws Exception;

	ActionLog queryActionLogByOrderId(long orderId) throws Exception;

	ActionLog queryActionLogByOrderId(long orderId, long logType)
			throws Exception;
	
	public ActionLog queryActionLogByOrderId(long orderId, long logType,
			String content) throws AppException;

	ActionLog queryActionLogByContent(String content) throws AppException;

	ActionLog queryActionLogByTranNo(String partner, String tranNo, long logType)
			throws AppException;
}