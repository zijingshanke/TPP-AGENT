package com.fordays.fdpay.agent.biz;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentContact;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.CertInfo;
import com.fordays.fdpay.agent.QuestionsAndAnswers;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.Transaction;
import com.neza.exception.AppException;

public interface AgentBiz {
	public String login(Agent ag, HttpServletRequest request)
			throws AppException;

	public Agent register(Agent ag) throws AppException;

	public Agent checkAgent(Agent agent) throws AppException;
	public Agent checkAgent2(Agent agent) throws AppException;
	
	public Agent getAgentById(long id) throws AppException;
	public Agent getAgentByLoginName(String loginName) throws AppException;
	public Agent getAgentByName(String name) throws AppException;
	
	/**
	 * 获取Session中的Agent
	 */	
	public Agent getAgentByRequest(HttpServletRequest request)throws AppException;	
	/**
	 * 获取Charge表单中的Agent
	 */	
	public Agent getAgentByChargeForm(Charge charge)throws AppException;	
	
	public CertInfo getCertById(long id) throws AppException;
	
	
	public void updateStatus(Agent ag) throws AppException;
	
	public void synallowBalance(Agent ag) throws AppException;

	public Agent checkPayPassword(Agent agent) throws AppException;

	public Agent checkCanPay(Agent agent) throws AppException;

	public List getContactList(AgentListForm alf) throws AppException;

	public void addOrUpdateContact(Agent agent, String contactName,
			String contactLoginName, String actionType, String acId)
			throws AppException;

	public AgentContact checkContactByLoginName(Agent agent,
			String contactLoginName, String actionType, String aId)
			throws AppException;

	public void updateAgent(Agent agent) throws AppException;

	public Agent checkAgentByEmail(String email) throws AppException;

	public void deleteContact(String deletelist[]) throws AppException;

	public List getAgentAddressManage(AgentAddress agentAddress)
			throws AppException;

	public long addAgentAddress(AgentAddress agentAddress, Agent agent)
			throws AppException;

	public AgentAddress editAgentAddressById(long aid) throws AppException;

	public void updateAgentAddressById(AgentAddress agentAddress, long aid)
			throws AppException;

	public void deleteAgentAddress(long aid) throws AppException;

	public List<QuestionsAndAnswers> RandQuestions(Agent agent)
			throws AppException;

	public void setDefaultAddress(long aid) throws AppException;

	public String registerActivate(HttpServletRequest request)
			throws AppException;// 注册激活

	public String checkMoney(Agent agent, Agent tempagent,
			HttpServletRequest request) throws AppException;

	public boolean attestation(Long agentId) throws AppException;

	public String isCertification(Long id) throws AppException;

	public Agent activateAgent(Agent ag) throws AppException;

	public void update(Agent agent, BigDecimal amount) throws AppException;

	public String updateMobileQuestionStatus(Agent agent,
			HttpServletRequest request) throws AppException;
	public String updateMobilePayStatus(Agent agent,
			HttpServletRequest request) throws AppException;
	public String settingMobilePassword(Agent agent, HttpServletRequest request)
			throws AppException;

	public boolean closeMobilePassword(Agent agent, HttpServletRequest request)
			throws AppException;

	public String updateMobileLoginStatus(Agent agent,
			HttpServletRequest request) throws AppException;

	public AgentRelationship getAgentRelationship(Agent agent)
			throws AppException;

	public List getMobilePhones() throws AppException;
	
	public boolean isPass(Agent agent) throws AppException;
	public Agent autoRegister(Agent agent);
	public void removeCertification(Long id) throws AppException;
	public String preprocessLogin(Agent ag, HttpServletRequest request)throws AppException;
	public String loginMD5(Agent ag, HttpServletRequest request)throws AppException;
	public Agent getAgentByBindingMobilePay(String mobile) throws AppException;
	public long getNotCompletedTransactionArticle(Agent agent)throws AppException;
	
	public BigDecimal getAgentAllowBalance(long agentId) throws AppException;
	public BigDecimal getAgentCreditBalance(long agentId) throws AppException;
	public BigDecimal getAgentNotAllowBalance(long agentId) throws AppException;
	public AgentBalance getAgentBalance(long agentId) throws AppException;
	public long getNotCompletedTransactionRedPacketArticle(Agent agent)throws AppException;
	public Transaction getNotCompletedTransactionRedPacket(Agent agent)throws AppException;
	public boolean  IsBindingMobilePhone(String mobilePhone);
}
