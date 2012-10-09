package com.fordays.fdpay.bank.cmb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.bank.BankAction;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.cmb.CmbB2CResultFromBank;
import com.fordays.fdpay.bank.cmb.biz.CmbBankBiz;
import com.fordays.fdpay.bank.cmbc.CmbcB2CResultFromBank;
import com.fordays.fdpay.bank.cmbc.biz.CmbcBankBiz;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * @description:接收招商银行B2C订单处理结果
 */
public class CmbB2CResultAction extends BankAction {
	private CmbBankBiz cmbBankBiz;
	private ChargeBiz chargeBiz;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CmbB2CResultFromBank cmbcresult = cmbBankBiz.getB2CResult(request);

		FinishCharge returnMsg = chargeBiz.finishCharge(cmbcresult);

		return notifyForward(returnMsg, mapping, request);
	}

	public void setCmbBankBiz(CmbBankBiz cmbBankBiz) {
		this.cmbBankBiz = cmbBankBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}
}