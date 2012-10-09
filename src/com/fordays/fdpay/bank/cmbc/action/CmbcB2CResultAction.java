package com.fordays.fdpay.bank.cmbc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.bank.BankAction;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.cmbc.CmbcB2CResultFromBank;
import com.fordays.fdpay.bank.cmbc.biz.CmbcBankBiz;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * @description:接收民生银行B2C订单处理结果
 */
public class CmbcB2CResultAction extends BankAction {
	private CmbcBankBiz cmbcBankBiz;
	private ChargeBiz chargeBiz;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CmbcB2CResultFromBank cmbcresult = cmbcBankBiz.getB2CResult(request);

		FinishCharge returnMsg = chargeBiz.finishCharge(cmbcresult);

		return notifyForward(returnMsg, mapping, request);
	}

	public void setCmbcBankBiz(CmbcBankBiz cmbcBankBiz) {
		this.cmbcBankBiz = cmbcBankBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}
}