package com.fordays.fdpay.transaction.dao;

import java.util.List;
import com.neza.exception.AppException;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawListForm;
import com.fordays.fdpay.transaction.DrawLog;
import com.neza.base.BaseDAO;



public interface DrawLogDAO extends BaseDAO
{
	
	public long save(DrawLog drawLog)throws AppException;
	public long merge(DrawLog drawLog)throws AppException;
	public long update(DrawLog drawLog)throws AppException;
	public void deleteById(long id)throws AppException; 
}
