package com.fordays.fdpay.agent.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentContact;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.CertInfo;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AgentDAO extends BaseDAO {
	Agent checkAgent(Agent ag) throws AppException;
	 Agent checkAgent2(Agent ag) throws AppException;
	Agent register(Agent ag) throws AppException;

	public Agent getAgentById(long id) throws AppException;

	void update(Agent ag) throws AppException;

	Agent getAgentByName(String name) throws AppException;

	Agent checkPayPassword(Agent agent) throws AppException;
	
	Agent checkCanPay(Agent agent) throws AppException;

	List getContactList(AgentListForm alf) throws AppException;

	Agent checkAgentByContactLoginName(String contactLoginName)
			throws AppException;

	void addOrUpdateContact(AgentContact ac) throws AppException;

	AgentContact checkContactByLoginName(Agent agent, String contactLoginName,
			String actionType, String aId) throws AppException;

	AgentContact getAgentContactById(String acId) throws AppException;

	void deleteContact(String deletelist[]) throws AppException;

	public Agent checkEmail(String Email) throws AppException;

	List getAgentAddressManage(AgentAddress agentAddress) throws AppException;

	long addAgentAddress(AgentAddress agentAddress) throws AppException;

	AgentAddress editAgentAddressById(long aid) throws AppException;

	void updateAgentAddressById(AgentAddress agentAddress) throws AppException;

	void deleteAgentAddress(long aid) throws AppException;

	public Agent getAgentByLoginName(String loginName) throws AppException;

	void setDefaultAddress(AgentAddress agentAddress) throws AppException;

	AgentAddress getAgentAddressById(Agent agent) throws AppException;
	
	public void addAmount(Agent agent, BigDecimal amount) throws AppException;

	public void addCreditAmount(Agent agent, BigDecimal amount) throws AppException;

	public void reduceAmount(Agent agent, BigDecimal amount)
			throws AppException;
	//20090507-ref001-jason-s add for cooperate
	Agent getAgentByEmailTempEmail(String loginName) throws AppException;
	
	Agent getAgentByEmail(String loginName) throws AppException;
	
	Agent queryByEmailAndPassword(String email, String password)throws AppException;
	
	Agent queryByPartner(String partner) throws AppException;
	
	
	//20090507-ref001-jason-e add for cooperate
	
	public boolean IsMobileBindingQuestion(Agent ag) throws AppException;
	public boolean IsMobileBindingLogin(Agent ag) throws AppException;
	public boolean IsMobileBindingPassword(Agent ag) throws AppException;
	public boolean IsMobileBindingPay(Agent ag) throws AppException;
	
	public AgentRelationship getAgentRelationship(Agent agent) throws AppException;
	public List getMobilePhones() throws AppException;
	public void moveNotallowBalanceToAllowBalance(Agent agent, BigDecimal amount)throws AppException;
	public void moveAllowBalanceToNotallowBalance(Agent agent, BigDecimal amount)throws AppException;
	public boolean isPass(Agent agent) throws AppException;
	public void synallowBalance(Agent agent) throws AppException ;
	public Agent getAgentByBindingMobilePay(String mobile) throws AppException;
	public CertInfo getCertById(long id) throws AppException;
	public BigDecimal getAgentAllowBalance(long agentId) throws AppException;
	public BigDecimal getAgentCreditBalance(long agentId) throws AppException;
	public BigDecimal getAgentNotAllowBalance(long agentId) throws AppException;
	public AgentBalance getAgentBalance(long agentId) throws AppException;
	public Agent getAgentByMobilePhone(String mobilePhone);
	
}
