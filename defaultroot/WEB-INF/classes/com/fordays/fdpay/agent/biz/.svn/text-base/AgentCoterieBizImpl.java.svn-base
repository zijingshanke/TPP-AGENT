package com.fordays.fdpay.agent.biz;

import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentCoterieDAO;
import com.neza.exception.AppException;

public class AgentCoterieBizImpl implements AgentCoterieBiz{
	AgentCoterieDAO agentCoterieDAO;
	public List list(long coterieId) throws AppException {
		
		return agentCoterieDAO.list(coterieId);
	}
	public AgentCoterieDAO getAgentCoterieDAO() {
		return agentCoterieDAO;
	}
	public void setAgentCoterieDAO(AgentCoterieDAO agentCoterieDAO) {
		this.agentCoterieDAO = agentCoterieDAO;
	}
	public long add(AgentCoterie agentCoterie) throws AppException {
		
		return agentCoterieDAO.save(agentCoterie);
	}
	public List getCoterieByAgentId(long agentId) throws AppException {
		return agentCoterieDAO.getCoterieByAgentId(agentId);
	}
	public AgentCoterie checkAgentCoterie(Agent agent ,Coterie coterie) throws AppException {
		return agentCoterieDAO.checkAgentCoterie(agent, coterie);
	}
	public void deleteAgentCoterie(AgentCoterie ac) throws AppException {
		agentCoterieDAO.deleteAgentCoterie(ac);
	}
	public long updateAgentCoterieStauts(AgentCoterie ac) throws AppException {
		return agentCoterieDAO.update(ac);
	}
	public long updateRepaymentRule(AgentCoterie ac) throws AppException {
		return agentCoterieDAO.update(ac);
	}
	
	/**
	 * 修改圈员信息
	 * @param agentCoterie
	 * @return
	 * @throws AppException
	 */
	public long updateRepayment(AgentCoterie agentCoterie) throws AppException{
		return agentCoterieDAO.update(agentCoterie);
	}
	
	
	public boolean checkCreditExpireDate(Agent agent, Coterie coterie) throws AppException {
		return agentCoterieDAO.checkCreditExpireDate(agent, coterie);
	}
	public List getAllMemberByPartner(Coterie coterie) throws AppException {
		return agentCoterieDAO.getAllMemberByPartner(coterie);
	}
	public AgentCoterie queryByAgentEmail(String  email,String partner) throws AppException{
		return agentCoterieDAO.queryByAgentEmail(email,partner);
	}
	public boolean checkCoerieByAllowMult(long   agentId) throws AppException{
		return agentCoterieDAO.checkCoerieByAllowMult(agentId);
	}
	public int  getAgentCoterieByAgent_Id(long id) throws AppException{
		return agentCoterieDAO.getAgentCoterieByAgent_Id(id);
	}
	
}
