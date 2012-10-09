package com.fordays.fdpay.transaction.dao;

import com.neza.exception.AppException;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeLog;
import com.neza.base.BaseDAO;

public interface ChargeLogDAO extends BaseDAO {
	public long save(ChargeLog chargeLog) throws AppException;

	public long merge(ChargeLog chargeLog) throws AppException;

	public long update(ChargeLog chargeLog) throws AppException;

	public ChargeLog getChargeLogById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public ChargeLog queryChargeLogById(long id) throws AppException;

	public long saveChargeLog(Charge charge, String url) throws AppException;
}
