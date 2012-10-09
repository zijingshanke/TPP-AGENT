package com.fordays.fdpay.transaction.biz;

import java.util.List;
import com.neza.exception.AppException;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawListForm;
import com.fordays.fdpay.transaction.DrawLog;


public interface DrawLogBiz
{
	public long addDrawLog(DrawLog drawLog)throws AppException;
	public long merge(DrawLog drawLog)throws AppException;
	public long update(DrawLog drawLog)throws AppException;
	public void deleteById(long id)throws AppException; 
}
