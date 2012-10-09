package com.fordays.fdpay.bank.abc.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.BankBiz;
import com.fordays.fdpay.bank.abc.AbcB2BResultFromBank;
//import com.fordays.fdpay.bank.abc.AbcB2BcmdToBank;
import com.fordays.fdpay.bank.abc.AbcB2CResultFromBank;
import com.fordays.fdpay.bank.abc.AbcB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.hitrust.trustpay.client.TrxResponse;
import com.neza.exception.AppException;

/**
 * @description:农业银行业务处理接口
 */
public interface AbcBankBiz extends BankBiz{
	public AbcB2CcmdToBank createABCB2Ccmd(Charge charge) throws AppException;

	public AbcB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;

//	public AbcB2BcmdToBank createABCB2Bcmd(Charge charge) throws AppException;

	public AbcB2BResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException;

	public HttpServletRequest printAbcB2CErrorMsg(TrxResponse tTrxResponse,
			HttpServletRequest request) throws AppException;

//	public HttpServletRequest printAbcB2BErrorMsg(
//			com.hitrust.b2b.trustpay.client.TrxResponse tTrxResponse,
//			HttpServletRequest request) throws AppException;
}
