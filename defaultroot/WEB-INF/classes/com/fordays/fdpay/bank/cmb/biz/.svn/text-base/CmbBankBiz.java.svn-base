package com.fordays.fdpay.bank.cmb.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.cmb.CmbB2CResultFromBank;
import com.fordays.fdpay.bank.cmb.CmbB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/*******************************************************************************
 * @中国招商银行业务处理接口
 ******************************************************************************/
public interface CmbBankBiz {
	public CmbB2CcmdToBank createCmbB2Ccmd(Charge charge) throws AppException;

	public CmbB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException;

}
