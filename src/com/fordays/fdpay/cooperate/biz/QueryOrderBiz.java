package com.fordays.fdpay.cooperate.biz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fordays.fdpay.cooperate.QueryOrder;
import com.neza.exception.AppException;

/**
 * 商户订单查询业务接口
 */
public interface QueryOrderBiz {

	public QueryOrder getQueryOrder_SingleQuery(HttpServletRequest request)
			throws AppException;

	public void executeQueryOrder(QueryOrder queryOrder,
			HttpServletResponse response) throws AppException;

	// public QueryOrder getQueryOrder_BatchQuery(HttpServletRequest request)
	// throws AppException;
}
