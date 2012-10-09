package com.fordays.fdpay.transaction.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neza.exception.AppException;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeListForm;

/**
 * 充值业务接口
 */
public interface ChargeBiz {
	public Charge createCharge(Charge charge, HttpServletRequest request)
			throws AppException;

	public FinishCharge finishCharge(ResultFromBank result) throws AppException;

	/**
	 * @description 银行接口调用完毕，移除Session
	 * @以下接口调用执行此方法
	 * @chargetype:CHARGE_TYPE_NOACCOUNT
	 * @chargetype:CHARGE_TYPE_DIRECTPAYMENT
	 */
	public void removeSession(HttpServletRequest request, Charge charge)
			throws AppException;

	public long save(Charge charge) throws AppException;

	public Charge getChargeById(long id) throws AppException;

	public Charge getChargeByOrderNo(String orderno) throws AppException;

	public List getCharges(Agent agent, ChargeListForm clf) throws AppException;

	public int updateCharge(Charge charge) throws AppException;
}
