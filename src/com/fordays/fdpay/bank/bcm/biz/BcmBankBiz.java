package com.fordays.fdpay.bank.bcm.biz;

import javax.servlet.http.HttpServletRequest;

import com.fordays.fdpay.bank.BankBiz;
import com.fordays.fdpay.bank.bcm.BcmB2CResultFromBank;
import com.fordays.fdpay.bank.bcm.BcmB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 * @交通银行业务处理接口
 */
public interface BcmBankBiz extends BankBiz{
	public BcmB2CcmdToBank createBCMB2CCmd(Charge charge) throws AppException;

	public BcmB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;
}
