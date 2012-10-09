package com.fordays.fdpay.bank.cmbc.biz;

import javax.servlet.http.HttpServletRequest;

import com.fordays.fdpay.bank.cmbc.CmbcB2BResultFromBank; //import com.fordays.fdpay.bank.cmbc.CmbcB2BcmdToBank;
import com.fordays.fdpay.bank.cmbc.CmbcB2CResultFromBank;
import com.fordays.fdpay.bank.cmbc.CmbcB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/*******************************************************************************
 * @中国民生银行业务处理接口
 ******************************************************************************/
public interface CmbcBankBiz {
	public CmbcB2CcmdToBank createCmbcB2Ccmd(Charge charge) throws AppException;

	public CmbcB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;

	// public CmbcB2BcmdToBank createCmbcB2Bcmd(Charge charge) throws
	// AppException;

	public CmbcB2CcmdToBank createCmbcChinaPayB2Ccmd(Charge charge)
			throws AppException;

	public CmbcB2BResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException;
}
