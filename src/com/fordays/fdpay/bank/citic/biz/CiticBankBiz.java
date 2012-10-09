package com.fordays.fdpay.bank.citic.biz;

import javax.servlet.http.HttpServletRequest;

import com.fordays.fdpay.bank.citic.CiticB2CResultFromBank;
import com.fordays.fdpay.bank.citic.CiticB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 * @中信银行业务接口
 */
public interface CiticBankBiz {
	public CiticB2CcmdToBank createCiticB2Ccmd(Charge charge)
			throws AppException;

	public CiticB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;
}
