package com.fordays.fdpay.bank.icbc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.bank.BankAction;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.icbc.IcbcB2CResultFromBank;
import com.fordays.fdpay.bank.icbc.biz.IcbcBankBiz;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * 接收工商银行B2C订单处理结果
 */
public class IcbcB2CResultAction extends BankAction {
	private IcbcBankBiz icbcBankBiz;
	private ChargeBiz chargeBiz;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		IcbcB2CResultFromBank icbcresult = icbcBankBiz.getB2CResult(request);

		FinishCharge returnMsg = chargeBiz.finishCharge(icbcresult);

		return notifyForward(returnMsg, mapping, request);
//		return notifyServer(returnMsg, mapping, request);
	}

	public void setIcbcBankBiz(IcbcBankBiz icbcBankBiz) {
		this.icbcBankBiz = icbcBankBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}
}
