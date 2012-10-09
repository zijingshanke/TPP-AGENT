package com.fordays.fdpay.bank;

import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 * 银行基础业务接口
 */
public interface BankBiz {

	/**
	 * 检查订单状态,BankOrderListener调用的方法
	 * 
	 * @param String
	 *            orderNo
	 * @param String
	 *            version(B2C/B2B/C2C)
	 * @return {@link ResultFromBank}
	 */
	public ResultFromBank initiativeQueryOrder(String orderNo, String version)
			throws AppException;

	/**
	 * 发送通知邮件,BankOrderListener调用
	 * 
	 * @param Charge
	 */
	public void sendNoticeMail(Charge charge) throws AppException;
}
