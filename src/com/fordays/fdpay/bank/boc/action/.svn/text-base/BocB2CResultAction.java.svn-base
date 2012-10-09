package com.fordays.fdpay.bank.boc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.bank.BankAction;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.boc.BocB2CResultFromBank;
import com.fordays.fdpay.bank.boc.biz.BocBankBiz;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * @接收中国银行B2C订单处理结果
 */
public class BocB2CResultAction extends BankAction {
	private BocBankBiz bocBankBiz;
	private ChargeBiz chargeBiz;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		BocB2CResultFromBank bocresult = bocBankBiz.getB2CResult(request);
		
		FinishCharge returnMsg = chargeBiz.finishCharge(bocresult);

		return notifyForward(returnMsg, mapping, request);
	}

	public void setBocBankBiz(BocBankBiz bocBankBiz) {
		this.bocBankBiz = bocBankBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}
}
