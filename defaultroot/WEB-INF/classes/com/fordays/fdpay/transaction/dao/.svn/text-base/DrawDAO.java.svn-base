package com.fordays.fdpay.transaction.dao;

import java.math.BigDecimal;
import java.util.List;
import com.neza.exception.AppException;
import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawListForm;
import com.neza.base.BaseDAO;

public interface DrawDAO extends BaseDAO {

	public long save(Draw draw) throws AppException;
	
	public void deleteById(long id) throws AppException;
	
	public Draw getDrawById(long id) throws AppException;

	public Draw queryDrawById(long id) throws AppException;

	public List list(Draw draw) throws AppException;

	public List getDrawsByAgent(Agent agent, DrawListForm dlf)
			throws AppException;
	
	public List getDrawsByAgent(Agent agent) throws AppException;

	public BigDecimal getAmountSumToDay(Agent agent) throws AppException;

	public Account getBindAccountByAgent(Agent agent) throws AppException;

	public int getDrawViewsToDay(Agent agent) throws AppException;

	public long merge(Draw draw) throws AppException;

	public long update(Draw draw) throws AppException;



	
}
