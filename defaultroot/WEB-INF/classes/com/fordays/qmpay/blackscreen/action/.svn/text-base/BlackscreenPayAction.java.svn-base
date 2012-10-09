package com.fordays.qmpay.blackscreen.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.blackscreen.Blackscreen;

import com.fordays.fdpay.right.UserRightInfo;

import com.fordays.fdpay.transaction.Transaction;

import com.fordays.qmpay.blackscreen.biz.BlackscreenBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class BlackscreenPayAction extends BaseAction {
	private BlackscreenBiz blackscreenBiz;

	public ActionForward viewBlackscreenPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		return mapping.findForward("viewblackscreenpay");
	}
	

	public ActionForward processOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Blackscreen blackscreen = (Blackscreen) form;
		System.out.println(blackscreen.getBlackscreenAccount());
		if (blackscreen != null) {
		String msg=	blackscreenBiz.checkBlackscreenAccount(blackscreen);
			if("true".equals(msg)){
			request.setAttribute("blackscreen", blackscreen);
			return mapping.findForward("viewblackscreenorder");
			
			}else{
					request.setAttribute("error", msg);
					return mapping.findForward("viewblackscreenpay");
			}
		}
		else{
			return mapping.findForward("viewblackscreenpay");
		}
	}

	public ActionForward pay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = URI.getAgent();
		Blackscreen blackscreen = (Blackscreen) form;
		Transaction transaction = blackscreenBiz.createTransaction(blackscreen,
				agent);
		
		return new ActionForward(
				"/transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="
						+ transaction.getStatus() + "&tid="
						+ transaction.getId() + "&orderid="
						+ transaction.getOrderDetails().getId()+"&remark="
						+transaction.getMark()+"&repayBlackscreenPrice="+transaction.getAmount());

	}
	
	
	public BlackscreenBiz getBlackscreenBiz() {
		return blackscreenBiz;
	}


	public void setBlackscreenBiz(BlackscreenBiz blackscreenBiz) {
		this.blackscreenBiz = blackscreenBiz;
	}



}
