package com.fordays.fdpay.agent.dao;

import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;


public interface AgentCoterieDAO extends BaseDAO {
	public AgentCoterie getAgentCoterieById(long id)throws AppException;

	public List list(long coterieId)throws AppException;
	
	public long save(AgentCoterie agentCoterie)throws AppException;

	public long merge(AgentCoterie agentCoterie)throws AppException;

	public long update(AgentCoterie agentCoterie)throws AppException;
	

	public void deleteById(long id)throws AppException;

	public AgentCoterie queryById(long id)throws AppException;
	
	public List getCoterieByAgentId(long agentId) throws AppException ;
	
	public AgentCoterie getAgentCoterieByCoterieAndAgent(String coterieId,String agentId,String email) throws AppException;
	
	public AgentCoterie checkAgentCoterie(Agent agent ,Coterie coterie) throws AppException;
	
	public boolean checkCreditExpireDate(Agent agent ,Coterie coterie) throws AppException;
	
	public void deleteAgentCoterie(AgentCoterie ac) throws AppException;
	
	public List getAllMemberByPartner(Coterie coterie)throws AppException;
	public AgentCoterie queryByAgentEmail(String  email,String partner) throws AppException;
	public boolean checkCoerieByAllowMult(long   agentId) throws AppException;
	public int  getAgentCoterieByAgent_Id(long id) throws AppException;
}
