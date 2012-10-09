package com.fordays.fdpay.bank;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.Charge;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class BankAction extends BaseAction {
	private LogUtil myLog;
	
	/**
	 * 银行服务器通知调用
	 * 
	 * @param FinishCharge
	 *            returnMsg
	 * @param ActionMapping
	 *            mapping
	 * @param HttpServletRequest
	 *            request
	 * @return ActionForward
	 */
	public ActionForward notifyServer(FinishCharge returnMsg,
			ActionMapping mapping, HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, BankAction.class);

		ActionForward actionForward = null;
		String callBackMsg = "";

		String orderNo = returnMsg.getOrderNo();
		String returnKey = returnMsg.getReturnKey();

		myLog.info("notifyServer()," + orderNo + ",returnKey=" + returnKey);

		if ("".equals(returnKey) || returnKey == null) {
			callBackMsg = orderNo + ",错误:returnKey 不能为空";
		} else if ("1001".equals(returnKey)) {
			// 获得Session,回调业务接口
			UserRightInfo uri = returnMsg.getUri();
			request.getSession().setAttribute("URI", uri);

			Charge charge = returnMsg.getCharge();

			boolean isCallBack = callbackOperation_Server(charge, request);
			if (isCallBack) {
				myLog.info(orderNo + "回调业务接口完毕");
				callBackMsg = orderNo + "处理成功";
			}
		} else {
			callBackMsg = orderNo + "," + returnMsg.getReturnMsg();
		}
		myLog.info("notifyServer callBackMsg:" + callBackMsg);
		request.setAttribute("bankInfo", callBackMsg);
		actionForward = mapping.findForward("bankInfo");
		return actionForward;
	}

	/**
	 * 银行页面跳转通知调用
	 * 
	 * @param FinishCharge
	 *            returnMsg
	 * @param ActionMapping
	 *            mapping
	 * @param HttpServletRequest
	 *            request
	 * @return ActionForward
	 */
	public ActionForward notifyForward(FinishCharge returnMsg,
			ActionMapping mapping, HttpServletRequest request)
			throws AppException {
		ActionForward actionForward = null;
		myLog = new LogUtil(false, false, BankAction.class);
		String forward = "bankInfo";

		String orderNo = returnMsg.getOrderNo();
		String returnKey = returnMsg.getReturnKey();

		myLog.info("notifyForward()," + orderNo + ",returnKey=" + returnKey);

		if ("".equals(returnKey) || returnKey == null) {
			myLog.error(orderNo + ",错误:returnKey 不能为空");
			return null;
		} else if ("1001".equals(returnKey)) {
			// 获得Session,回调业务接口
			UserRightInfo uri = returnMsg.getUri();
			request.getSession().setAttribute("URI", uri);

			Charge charge = returnMsg.getCharge();
			return callbackOperation(charge, request);
		} else {
			String errorMsg = orderNo + "," + returnMsg.getReturnMsg();
			myLog.info("errorMsg=" + errorMsg);

			request.setAttribute("bankInfo", errorMsg);
			actionForward = mapping.findForward(forward);
		}
		return actionForward;
	}



	/**
	 * 服务器通知方式： 网银处理订单成功后,回调业务接口
	 * 
	 * @param Charge
	 *            charge
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 */
	private boolean callbackOperation_Server(Charge charge,
			HttpServletRequest request) throws AppException {
		boolean isCallBack = false;
		String requestHead = BankUtil.getRequestHead(request);

		BankHttpInvoker invoker = new BankHttpInvoker();
		try {
			invoker.sendHttpInvoker(charge, requestHead);
			isCallBack = true;
		} catch (AppException e) {
			e.printStackTrace();
		}
		return isCallBack;
	}

	/**
	 * 页面跳转方式：网银处理订单成功后,回调业务接口
	 * 
	 * @param Charge
	 *            charge
	 * @param HttpServletRequest
	 *            request
	 * @param HttpServletResponse
	 *            response
	 * @return ActionForward
	 */
	private ActionForward callbackOperation(Charge charge,
			HttpServletRequest request) throws AppException {
		myLog = new LogUtil(false, false, BankAction.class);
		myLog.info("开始执行callbackOperation----------");

		ActionRedirect redirect = new ActionRedirect("/agent/agent.do");
		redirect.addParameter("thisAction", "agentInfoById");

		final String chargeType = charge.getType();
		final String remark = charge.getRemark();

		try {
			if (Charge.CHARGE_TYPE_SELF.equals(chargeType)) {
			} else if (Charge.CHARGE_TYPE_TRANSACTION.equals(chargeType)) {
				redirect = new ActionRedirect("/transaction/transaction.do");
				redirect.addParameter("thisAction",
						"transactionPaymentReturnByBank");
				redirect.addParameter("transactionNo", remark);
			} else if (Charge.CHARGE_TYPE_NOACCOUNT.equals(chargeType)) {
				redirect = new ActionRedirect("/cooperate/gateway.do");
				redirect.addParameter("service",
						"direct_payment_for_no_account");
				redirect.addParameter("remark", remark);
			} else if (Charge.CHARGE_TYPE_DIRECTPAYMENT.equals(chargeType)) {
				redirect = new ActionRedirect("/cooperate/gateway.do");
				redirect.addParameter("service", "direct_payment_by_bank");
				redirect.addParameter("remark", remark);
			} else if (Charge.CHARGE_TYPE_OTHER.equals(chargeType)) {
			} else if (Charge.CHARGE_TYPE_GUARANTEE.equals(chargeType)) {
				redirect = new ActionRedirect("/cooperate/gateway.do");
				redirect.addParameter("service", "guarantee_payment_by_bank");
				redirect.addParameter("remark", remark);
			}
		} catch (Exception ex) {
			myLog.info("callbackOperation异常," + ex.getMessage());
			return null;
		}
		myLog.info(chargeType + ":redirect=" + redirect);
		return redirect;
	}
}