package com.fordays.fdpay.bank.citic.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.bank.BankAction;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.citic.CiticB2CResultFromBank;
import com.fordays.fdpay.bank.citic.biz.CiticBankBiz;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * @接收中信银行B2C订单处理结果
 */
public class CiticB2CResultAction extends BankAction {
	private CiticBankBiz citicBankBiz;
	private ChargeBiz chargeBiz;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CiticB2CResultFromBank citicresult = citicBankBiz.getB2CResult(request);

		FinishCharge returnMsg = chargeBiz.finishCharge(citicresult);

		return notifyForward(returnMsg, mapping, request);
	}

	public void setCiticBankBiz(CiticBankBiz citicBankBiz) {
		this.citicBankBiz = citicBankBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}
}
