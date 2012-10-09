package com.fordays.qmpay.shop.agent19pay.biz;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.qmpay.shop.agent19pay.Agent19pay;
import com.neza.exception.AppException;


/*******************************************************************************
 * 充值业务接口
 ******************************************************************************/
public interface Agent19payBiz {
	
	public Agent19pay getMobileProduct(HttpServletRequest request, HttpServletResponse response,Agent19pay agent19pay);
	public Transaction createTransaction(Agent19pay agent19pay,Agent agent) throws AppException;
	public String pay(Agent19pay agent19pay);
	public String return19payNotice(String orderid,Long status,BigDecimal ordermoney,String verifystring)throws AppException;
	public Transaction createTransaction(Agent19pay agent19pay) throws AppException;
}
