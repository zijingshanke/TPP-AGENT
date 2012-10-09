package com.fordays.qmpay.blackscreen.biz;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.blackscreen.Blackscreen;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.qmpay.shop.agent19pay.Agent19pay;
import com.neza.exception.AppException;


/*******************************************************************************
 * 充值业务接口
 ******************************************************************************/
public interface BlackscreenBiz {
	
	public Transaction createTransaction(Blackscreen blackscreen,Agent agent) throws AppException;
	public  boolean doNoticeBlackscreen(Transaction transaction)throws AppException;
	public  String checkBlackscreenAccount(Blackscreen blackscreen)throws AppException;
}
