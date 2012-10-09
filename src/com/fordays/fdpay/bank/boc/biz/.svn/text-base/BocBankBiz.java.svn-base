package com.fordays.fdpay.bank.boc.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.BankBiz;
import com.fordays.fdpay.bank.boc.BocB2CResultFromBank;
import com.fordays.fdpay.bank.boc.BocB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 * 中国银行业务处理接口
 */
public interface BocBankBiz extends BankBiz {
	public BocB2CcmdToBank createBOCB2CCmd(Charge charge) throws AppException;

	public BocB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;

//	public BocB2CResultFromBank getB2CResult_Query_Single(
//			StringBuffer stringBuffer) throws AppException;

//	public List<BocB2CResultFromBank> getB2CResultList(StringBuffer stringBuffer)
//			throws AppException;
}
