package com.fordays.qmpay.shop.agent19pay.action;

import java.io.IOException;
import java.math.BigDecimal;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;

import com.fordays.fdpay.right.UserRightInfo;

import com.fordays.fdpay.transaction.Transaction;

import com.fordays.qmpay.shop.agent19pay.Agent19pay;
import com.fordays.qmpay.shop.agent19pay.biz.Agent19payBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class Agent19payAction extends BaseAction {
	private Agent19payBiz agent19payBiz;
	
	public ActionForward viewMobilePay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		return mapping.findForward("viewmobilepay");
	}
	

	public ActionForward processOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent19pay agent19pay = (Agent19pay) form;
		
		Agent19pay ag19pay = agent19payBiz.getMobileProduct(request, response,
				agent19pay);
		if (ag19pay != null) {
			request.setAttribute("agent19pay", ag19pay);
			return mapping.findForward("viewmobileorder");
		} else {
			request.setAttribute("error", "目前没有您充值面额所对应的充值卡!请选择其他面额充值!");
			return mapping.findForward("viewmobilepay");
		}
	}
	public ActionForward processOrderI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent19pay agent19pay = (Agent19pay) form;
		Agent19pay ag19pay = agent19payBiz.getMobileProduct(request, response,
				agent19pay);	
		if (ag19pay != null) {
			ag19pay.setQmagentId(agent19pay.getQmagentId());
			request.setAttribute("agent19pay", ag19pay);
			return mapping.findForward("mobileorder");
		} else {
			request.setAttribute("qmagentId", agent19pay.getQmagentId());
			request.setAttribute("error", "目前没有您充值面额所对应的充值卡!请选择其他面额充值!");
			return mapping.findForward("mobilepay");
		}
	}
	
	
	public ActionForward pay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = URI.getAgent();
		Agent19pay agent19pay = (Agent19pay) form;
		Transaction transaction = agent19payBiz.createTransaction(agent19pay,
				agent);
		
		return new ActionForward(
				"/transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="
						+ transaction.getStatus() + "&tid="
						+ transaction.getId() + "&orderid="
						+ transaction.getOrderDetails().getId());

	}
	public ActionForward payI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {

		Agent19pay agent19pay = (Agent19pay) form;
		
		Transaction transaction = agent19payBiz.createTransaction(agent19pay);
		if(transaction==null){
			return mapping.findForward("error");
		}
		return new ActionForward(
				"/transaction/transaction.do?thisAction=transactionPaymentI&qmagentId="+agent19pay.getQmagentId()+"&flag=1&statusid="
						+ transaction.getStatus() + "&tid="
						+ transaction.getId() + "&orderid="
						+ transaction.getOrderDetails().getId());

	}
	
	public void notice19payResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			System.out.println("====================19pay回调==================");
			String orderid=request.getParameter("orderid");
			Long status=new Long(request.getParameter("status"));
			BigDecimal ordermoney= new BigDecimal(request.getParameter("ordermoney"));
			String verifystring=request.getParameter("verifystring");
			String result=agent19payBiz.return19payNotice(orderid, status, ordermoney,verifystring);
			if(result!=null){
				System.out.println("====================19pay回调,返回状态   "+result+"  ==================");
				response.getWriter().write(result);
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ActionForward mobileRechare(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response){
		UserRightInfo URI = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent =URI.getAgent();
		Agent loginAgent=(Agent)request.getSession().getAttribute("loginAgent");
		request.getSession().removeAttribute("URI");
		if(agent.getId()==loginAgent.getId()){
			request.setAttribute("qmagentId", loginAgent.getId());
			return mapping.findForward("mobilepay");
		}else{
			return mapping.findForward("login");
		}
		
	}

	public void setAgent19payBiz(Agent19payBiz agent19payBiz) {
		this.agent19payBiz = agent19payBiz;
	}

}
