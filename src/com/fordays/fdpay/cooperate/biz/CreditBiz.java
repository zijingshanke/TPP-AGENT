package com.fordays.fdpay.cooperate.biz;

import java.util.HashMap;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentCoterieDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.NoUtil;
import com.neza.exception.AppException;

/**
 * 接口--接口
 */
public interface CreditBiz {

	/**
	 * 验证解冻
	 */
	public abstract String allowvalidateDirectPay(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception;

	/**
	 * 解冻订单
	 */
	public abstract String allow_direct_pay(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception;
	
	/**
	 *信用支付（外买）
	 */
	public String credit_paymentout(HashMap<String, String> map, Agent userAgent,
		    String url, String encode, boolean isPayByBank,
		    GatewayMessage gatewayMessage) throws Exception;
	
	/*
	 * (non-Javadoc) 
	 * 验证信用支付(外买)
	 */
	public String validateCreditPayout(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception;
	
	/* (non-Javadoc)
	 * @see 信用退款（外买）订单方法
	 */
	public String credit_refund_out(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception;
	
	/*
	 * 信用支付(外买)退款参数验证方法
	 */
	public String credit_validateRefundout(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception;
	
	
	/*
	 * 验证激活/禁用圈员交易类型接口
	 */
	public String validate_tran_scope(HashMap<String, String> map, String url,
		    GatewayMessage gatewayMessage) throws Exception;
	
	//查询圈主信息
	public Agent getPartnerAccount(String partner) throws AppException;
	
	//查找没还款的信用交易
	public List getCreditPayListByAgent(Agent buyerAgent) throws AppException;
	
	public abstract TransactionDAO getTransactionDAO();

	public abstract void setTransactionDAO(TransactionDAO transactionDAO);

	public abstract AgentDAO getAgentDAO();

	public abstract void setAgentDAO(AgentDAO agentDAO);

	public abstract Agent getSeller();

	public abstract void setSeller(Agent seller);

	public abstract NoUtil getNoUtil();

	public abstract void setNoUtil(NoUtil noUtil);

	public abstract ActionLogDAO getActionLogDAO();

	public abstract void setActionLogDAO(ActionLogDAO actionLogDAO);

	public abstract AgentCoterieDAO getAgentCoterieDAO();

	public abstract void setAgentCoterieDAO(AgentCoterieDAO agentCoterieDAO);

	public abstract CoterieDAO getCoterieDAO();

	public abstract void setCoterieDAO(CoterieDAO coterieDAO);

	public abstract long saveActionLog(ActionLog actionLog) throws Exception;
}