package com.fordays.fdpay.bank.bcm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.bank.BankAction;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.bcm.BcmB2CResultFromBank;
import com.fordays.fdpay.bank.bcm.biz.BcmBankBiz;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * 交通银行网上银行B2C支付订单处理结果访问接口
 */
public class BcmB2CResultAction extends BankAction {
	private BcmBankBiz bcmBankBiz;
	private ChargeBiz chargeBiz;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		BcmB2CResultFromBank bcmresult = bcmBankBiz.getB2CResult(request);

		FinishCharge returnMsg = chargeBiz.finishCharge(bcmresult);

		return notifyServer(returnMsg, mapping, request);
//		return notifyForward(returnMsg, mapping, request);
	}

	public void setBcmBankBiz(BcmBankBiz bcmBankBiz) {
		this.bcmBankBiz = bcmBankBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}
}