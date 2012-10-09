package com.fordays.fdpay.cooperate.biz;

import java.math.BigDecimal;
import java.util.HashMap;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.neza.exception.AppException;

public interface GatewayBiz {
	String validateDirectPay(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)throws Exception;
	
	String validateGuaranteePay(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)throws Exception;
	
	String validateCreditPay(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)throws Exception;
	
	String validateMobilePay(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)throws Exception;
	
	String validateDirectCreditPayment(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)throws Exception;
	
	String validateDirectCreditRepayment(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)throws Exception;
	
	//房产网测试的验证方法
	String testValidateDirectPay(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)
	throws Exception;
	
	String validateDirectPayForUsers(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)
	throws Exception;
	
	/**
	 * 退款验证
	 */
	String validateRefund(HashMap<String, String> map, String url,GatewayMessage gatewayMessage)
			throws Exception;
	
	/**
	 * 信用支付退款参数验证方法
	 */
	public String credit_validateRefund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception;
	
	String payment(HashMap<String, String> map, Agent userAgent, String url,
			String encode, boolean isPayByBank,GatewayMessage gatewayMessage) throws Exception;
	
	String guarantee_payment(HashMap<String, String> map, Agent userAgent, String url,
			String encode, boolean isPayByBank,GatewayMessage gatewayMessage) throws Exception;
	
	String guarantee_payment_by_password(HashMap<String, String> map, Agent userAgent, String url,
			String encode, GatewayMessage gatewayMessage) throws Exception;
	
	String credit_payment(HashMap<String, String> map, Agent userAgent, String url,
			String encode, boolean isPayByBank,GatewayMessage gatewayMessage) throws Exception;
	
	String direct_credit_payment(HashMap<String, String> map, Agent userAgent, String url,
			String encode, boolean isPayByBank,GatewayMessage gatewayMessage) throws Exception;
	
	String direct_credit_repayment(HashMap<String, String> map, Agent userAgent, String url,
			String encode, boolean isPayByBank,GatewayMessage gatewayMessage) throws Exception;
	
	String[] direct_pay_by_mobile(HashMap<String, String> map, Agent userAgent, String url,
			String encode, boolean isPayByBank,GatewayMessage gatewayMessage) throws Exception;
	/**
	 * 普通退款方法
	 */
	String refund(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	/**
	 * 信用支付退款方法
	 */
	 String credit_refund(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	 
	OrderDetails getOrderDetails(String orderNo, String partner)
			throws AppException;

	OrderDetails appendOrder(HashMap<String, String> map, Agent buyerAgent,
			 String url, boolean isPayByBank)
			throws Exception;

	Agent getAgent(String eamil, String password) throws AppException;
	
	int checkAgent(String eamil, String password) throws AppException;

	Agent getSeller();

	Agent getPartnerAccount(String partner) throws AppException;

	ActionLog getActionLog(long orderId, long logType) throws Exception;
	
	public void updateOrderDetailsForAgent(OrderDetails orderDetails) throws AppException;

	boolean notifyVerify(String partner, String tranNo, long logType)
			throws AppException;

	// 房产网网上银行接口
	Agent getAgent(long id) throws AppException;

	String paymentForNoAccount(HashMap<String, String> map, String encode,GatewayMessage gatewayMessage)
			throws Exception;
	
	String validateAgentRelationship(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateAgentBalance(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateExitCoterie(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateChangeCoterieStatus(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateRepaymentRule(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateAllMemberByPartner(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateCreditAmount(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateAgentCoterie(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateDirectLoginForward(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateEmailLoginForward(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validatePartner(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateAddCoterie(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	String validateConfirmMsg(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	String validateRegister(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;//验证注册输入的参数
	/**
	 * 测试验证自动注册钱门帐号
	 * @return 验证信息
	 */
	String testValidateRegister(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;//测试验证注册输入的参数
	String validateDirectChangePassword(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;//验证 接口23传入参数
	String validateChangePassword(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	//梁卓山 2009-06-15 添加新的方法,用来退款请求失败插入日志表
	public long saveActionLog(ActionLog actionLog) throws Exception;
	
	public BigDecimal getCreditAmount(Agent agent) throws AppException;
	public boolean sendMsg(HashMap<String, String> map,Agent agent);
	public boolean sendEmail(HashMap<String,String> params,Agent agent,long type);
	public Transaction getTransactionByAgent(Agent agent)throws AppException;
	
	
	/**
	 * 圈员属性验证
	 * @param map
	 * @param url
	 * @param gatewayMessage
	 * @return
	 * @throws Exception
	 */
	public String validateRepayment(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;
	
	/**
	 * 担保支付隐式接口（验证买家）
	 */
	public String validateDirectPay_buyer(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception;


	
	public String validateAutoRegister(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception;

	
	String validateRapidChangePassword(HashMap<String, String> map, String url,GatewayMessage gatewayMessage) throws Exception;//验证 接口23传入参数
	public void test()throws Exception;
	/**
	 *验证手机验证码
	 */
	public String validateMobileIdentifyingCode(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception;

}

