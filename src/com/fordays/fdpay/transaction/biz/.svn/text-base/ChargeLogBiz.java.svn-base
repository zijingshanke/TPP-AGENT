package com.fordays.fdpay.transaction.biz;

import com.neza.exception.AppException;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeLog;

public interface ChargeLogBiz {
	public void saveChargeLog(ChargeLog chargelog) throws AppException;

	public long saveChargeLog(Charge charge, String url) throws AppException;

	public ChargeLog getChargeLogById(int id) throws AppException;

	public ChargeLog queryChargeLogById(int id) throws AppException;

	public int updateInfo(ChargeLog chargeLog) throws AppException;

	public void logCharge(Charge charge) throws AppException;
}
