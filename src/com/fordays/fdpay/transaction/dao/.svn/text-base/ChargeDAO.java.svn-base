package com.fordays.fdpay.transaction.dao;

import java.util.List;
import com.neza.exception.AppException;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeListForm;
import com.neza.base.BaseDAO;

public interface ChargeDAO extends BaseDAO {
	public long save(Charge charge) throws AppException;

	public long merge(Charge charge) throws AppException;

	public long update(Charge charge) throws AppException;

	public Charge getChargeById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public Charge queryChargeById(long id) throws AppException;

	public Charge getChargeByOrderNo(String orderno) throws AppException;

	public List getChargeByAgent(Agent agent, ChargeListForm clf)
			throws AppException;

}
