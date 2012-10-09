package com.fordays.fdpay.bank.icbc.biz;

import javax.servlet.http.HttpServletRequest;

import com.fordays.fdpay.bank.BankBiz;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.bank.icbc.IcbcB2BCmdToBank;
import com.fordays.fdpay.bank.icbc.IcbcB2CResultFromBank;
import com.fordays.fdpay.bank.icbc.IcbcB2CcmdToBank;
import com.fordays.fdpay.bank.icbc.IcbcOrder;
import com.fordays.fdpay.bank.icbc.IcbcTelB2CResultFromBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 * @工商银行业务处理接口
 */
public interface IcbcBankBiz extends BankBiz {
	public IcbcB2CcmdToBank createIcbcB2CCmd(Charge charge) throws AppException;

	public IcbcB2BCmdToBank createIcbcB2BCmd(Charge charge) throws AppException;

	public IcbcOrder createIcbcOrderQuery(Charge charge) throws AppException;

	public IcbcB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;

	public ResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException;

	public IcbcTelB2CResultFromBank getTelB2CResult(HttpServletRequest request)
			throws AppException;	
}
