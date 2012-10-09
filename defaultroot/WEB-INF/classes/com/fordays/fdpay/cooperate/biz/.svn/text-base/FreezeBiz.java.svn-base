package com.fordays.fdpay.cooperate.biz;

import java.util.HashMap;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentCoterieDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.FreezeMessage;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.NoUtil;
import com.neza.exception.AppException;

/**
 * 接口--接口
 */
public interface FreezeBiz {

	
	/*
	 * 根据外部订单号  查询交易信息
	 */
	public OrderDetails getOrderDetails(String orderNo, String partner)
	    throws AppException;
	
	/*
	 * 交易委托冻结接口参数验证方法
	 */
	public String validate_refund_freeze(HashMap<String, String> map, String url,
	    FreezeMessage freezeMessage) throws Exception;
	
	/* (non-Javadoc)
	 * @see 交易委托冻结订单方法
	 */
	public String credit_freeze_order(HashMap<String, String> map, String url,
			  FreezeMessage freezeMessage,FreezeBiz freezebiz) throws Exception;
	
	
	/*
	 * 交易委托冻结查询接口参数验证方法
	 */
	public String validate_refund_freeze_query(HashMap<String, String> map, String url,
	    FreezeMessage freezeMessage) throws Exception;
	
	//冻结明细
	public String queryFreeResult(HashMap<String, String> map,
			FreezeMessage freezeMessage) throws Exception;
	
	
	/* (non-Javadoc)
	 * @see 冻结标款验证
	 */
	public String validate_freeze(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage) throws Exception;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see 招标冻结标款订单方法
	 */
	public String credit_recruit_freeze_order(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage, FreezeBiz freezebiz) throws Exception;
			
	/*
	 * 验证支付接口gatewaybizimp
	 */
	public String validateFreezePay(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage) throws Exception;
	
	
	/*
	 * 招标冻结支付订单方法
	 */
	public String freeze_payment(HashMap<String, String> map,
		Agent userAgent,String url, String encode,
			GatewayMessage gatewayMessage) throws Exception;
	
	/*
	 * 招标冻结支付退款参数验证方法
	 */
	public String validateFreezeRefund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception;
	
	/*
	 * 招标冻结支付退款订单方法
	 */
	public String freezeRefund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception;
	
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
	
	/**
	 * 验证解冻结协议参数
	 */
	public String validateProtocolParameters(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception;
	
	public HashMap<Integer,String> unfreeze(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage,FreezeBiz freezeBiz) throws Exception;
	
	
	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo)
    throws AppException;
	
	public String validateAirtradeRefundUnfreezeQuery(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception;
	
	public String unfreezeQuery(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage, FreezeBiz freezeBiz)
			throws Exception ;
	
	public String validateOperatingparameters(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception;
}