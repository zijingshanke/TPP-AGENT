package com.fordays.fdpay.cooperate.biz;

import java.util.HashMap;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBind;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.neza.exception.AppException;

public interface AgentBindBiz {

	public abstract String validateAgentBind(HashMap<String, String> map,
			String url,GatewayMessage gatewayMessage) throws Exception;
	
	public String validateUpdateRegisterPassword(HashMap<String, String> map, 
			String url,GatewayMessage gatewayMessage) throws Exception;
	
	public String validateDirectPay(HashMap<String, String> map, 
			String url,GatewayMessage gatewayMessage) throws Exception;

	public long save(AgentBind agentBind) throws AppException;
	
	public void deleteById(long id) throws AppException;
	
	public AgentBind queryAgentBindByAgentId(long agentId,long bindAgentId) throws AppException;

	public Agent getPartnerAccount(String partner) throws AppException;
	
	Agent getSeller();
	
	OrderDetails getOrderDetails(String orderNo, String partner)throws AppException;
	
	public Agent getAgentByEmail(String loginName) throws AppException;
	
	//public AgentCoterie getAgentCoterieById(long agentId) throws AppException;
	
	public int checkAgent(String eamil, String password) throws AppException;
	
	public OrderDetails appendOrder(HashMap<String, String> map, Agent userAgent,
		    String url, boolean isPayByBank,long buyType) throws Exception;
	
	public Agent getAgent(String eamil, String password) throws AppException;
	
	public List getAgentCoterieById(long agentId) throws AppException;
	
	public String payment(HashMap<String, String> map, Agent userAgent,
		    String url, String encode, boolean isPayByBank,
		    GatewayMessage gatewayMessage) throws Exception;
	
	public long saveActionLog(ActionLog actionLog) throws Exception;
	
	public void updateOrderDetailsForAgent(OrderDetails orderDetails)throws AppException;
	
	public String validateRefund(HashMap<String, String> map, String url,
		    GatewayMessage gatewayMessage) throws Exception;
	
	/**
	 * 普通退款方法
	 */
	String refund(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	public Agent getAgentById(long id) throws AppException;
	
	/**
	 * 查找到圈名
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterieName(long id) throws AppException;
	
	public String validateInfo(HashMap<String, String> map) throws Exception;
	
	public Transaction getTransactionByAgent(Agent agent) throws AppException;
	
	//查找没还款的信用交易
	public List getCreditPayListByAgent(Agent buyerAgent) throws AppException;
	
	/**
	 * 根据合作伙伴查找到圈名
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterie(String partner) throws AppException;
	
	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo)
		throws AppException;
}