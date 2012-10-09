package com.fordays.fdpay.bank.ccb.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.ccb.CcbB2BResultFromBank;
import com.fordays.fdpay.bank.ccb.CcbB2BcmdToBank;
import com.fordays.fdpay.bank.ccb.CcbB2CResultFromBank;
import com.fordays.fdpay.bank.ccb.CcbB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 *@description: 建设银行业务接口
 */
public interface CcbBankBiz {
	public CcbB2CcmdToBank createCcbB2Ccmd(Charge charge) throws AppException;

	public CcbB2BcmdToBank createCcbB2Bcmd(Charge charge) throws AppException;

	public CcbB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;

	public CcbB2CResultFromBank getB2CResult_Syn(HttpServletRequest request)
			throws AppException;

	public CcbB2BResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException;
}
