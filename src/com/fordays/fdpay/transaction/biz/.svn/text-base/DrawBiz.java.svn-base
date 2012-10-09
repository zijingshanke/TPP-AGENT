package com.fordays.fdpay.transaction.biz;

import java.math.BigDecimal;
import java.util.List;
import com.neza.exception.AppException;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawListForm;

public interface DrawBiz {

	public Draw getDrawById(long id) throws AppException;

	public Draw queryDrawById(long id) throws AppException;

	public List getDraws(Draw draw) throws AppException;

	public long updateInfo(Draw draw) throws AppException;

	public long addDraw(Draw draw) throws AppException;

	public long applyDraw(Draw draw, Agent agent)
			throws AppException;

	public List getDrawsByAgent(Agent agent, DrawListForm dlf)
			throws AppException;

	public BigDecimal getAmountSumToDay(Agent agent) throws AppException;

	public int getDrawViewsToDay(Agent agent) throws AppException;
}
