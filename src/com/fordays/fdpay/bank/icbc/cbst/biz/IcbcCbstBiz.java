package com.fordays.fdpay.bank.icbc.cbst.biz;

import com.neza.exception.AppException;

/**
 * 工商银行银商转帐系统业务接口
 */
public interface IcbcCbstBiz {
	/**
	 * 获取业务报文
	 * 
	 * @param String
	 *            businessCode
	 * @return String
	 */
	public String getBussinessXML(String businessCode) throws AppException;

	public void getCbstResult(String result) throws AppException;

}
