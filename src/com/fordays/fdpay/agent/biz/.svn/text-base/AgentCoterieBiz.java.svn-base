package com.fordays.fdpay.agent.biz;

import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.neza.exception.AppException;

public interface AgentCoterieBiz {
	public List list(long coterieId)throws AppException;
	public long add(AgentCoterie agentCoterie)throws AppException;
	public List getCoterieByAgentId(long agentId) throws AppException;
	
	public AgentCoterie checkAgentCoterie(Agent agent ,Coterie coterie) throws AppException;
	
	public boolean checkCreditExpireDate(Agent agent ,Coterie coterie) throws AppException;
	
	public void deleteAgentCoterie(AgentCoterie ac) throws AppException;
	
	public long updateAgentCoterieStauts(AgentCoterie ac) throws AppException;
	
	public long updateRepaymentRule(AgentCoterie ac) throws AppException;
	
	public List getAllMemberByPartner(Coterie coterie)throws AppException;
	/**
	 * 修改圈员信息
	 * @param agentCoterie
	 * @return
	 * @throws AppException
	 */
	public long updateRepayment(AgentCoterie agentCoterie) throws AppException;
	public AgentCoterie queryByAgentEmail(String  email,String partner) throws AppException;
	
	public boolean checkCoerieByAllowMult(long   agentId) throws AppException;
	public int  getAgentCoterieByAgent_Id(long id) throws AppException;
	
}
