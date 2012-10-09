package com.fordays.fdpay.cooperate.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentCoterieDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.base.Constant;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.UtilURL;
import com.fordays.fdpay.cooperate.action.Gateway;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.cooperate.dao.AgentBindDAO;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.dao.TaskTimestampDAO;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.ChargeDAO;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.mail.MailUtil;
import com.neza.message.SMUtil;
import com.neza.tool.DateUtil;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;

public class _GatewayBizImp implements GatewayBiz
{
	private AgentDAO agentDAO;
	private TransactionDAO transactionDAO;
	private ActionLogDAO actionLogDAO;
	private AgentCoterieDAO agentCoterieDAO;
	private CoterieDAO coterieDAO;
	private NoUtil noUtil;
	private TransactionTemplate transactionTemplate;
	private TaskTimestampDAO tasktimestampDAO;
	private ChargeDAO chargeDAO;
	private Agent seller;
	private static int count = 1;
	
	private AgentBindDAO agentBindDAO;

	public AgentBindDAO getAgentBindDAO() {
		return agentBindDAO;
	}

	public void setAgentBindDAO(AgentBindDAO agentBindDAO) {
		this.agentBindDAO = agentBindDAO;
	}

	public void setTransactionManager(HibernateTransactionManager thargeManager)
	{
		this.transactionTemplate = new TransactionTemplate(thargeManager);
	}

	public Agent getAgent(String eamil, String password) throws AppException
	{
		Agent agt = agentDAO.queryByEmailAndPassword(eamil, MD5.encrypt(password));
		return agt;
	}

	public BigDecimal getCreditAmount(Agent agent) throws AppException
	{
		return transactionDAO.getCreditAmount(agent);
	}

	public int _checkAgent(String eamil, String password) throws AppException{
		int loginStatus = 0;
		Agent agt = agentDAO.queryByEmailAndPassword(eamil, MD5.encrypt(password));
		Agent agt1 = agentDAO.getAgentByEmail(eamil);
		if (agt1 == null)
			loginStatus = 1; // 帐号不存在
		else
		{
			if (agt == null)
			{
				loginStatus = 2; // 密码错误
			}
		}
		return loginStatus;
	}

	public int checkAgent(String eamil, String password) throws AppException{
		int loginStatus = 0;
		Agent agent = agentDAO.getAgentByEmail(eamil);
		if (agent == null)
			loginStatus = 1; // 帐号不存在
		else if (!agent.getEmail().equalsIgnoreCase(eamil))
		{
			loginStatus = 1; // 帐号不存在
		}
		else
		{
			if (!agent.getPayPassword().equals(MD5.encrypt(password)))
			{
				loginStatus = 2; // 密码错误
			}
		}
		return loginStatus;
	}

	public Agent getAgent(long id) throws AppException
	{
		return agentDAO.getAgentById(id);
	}

	public boolean verifyPartner(String partner) throws AppException
	{
		Coterie cot = coterieDAO.getCoterieByPartner(partner);
		boolean isExist = false;
		if (cot != null && cot.getId() > 0)
		{
			isExist = true;
		}
		return isExist;
	}
	
	/**
	 * 根据交易号  查询交易信息
	 * 
	 */
	public OrderDetails getOrderDetails(String orderNo, String partner)
	    throws AppException
	{
		// check request has exist
		OrderDetails order = null;
		order = transactionDAO.queryOrderDetailByOrderNoAndPartner(orderNo,
		    partner);
		if (order != null && order.getId() > 0)
		{
			if (order.getAgent() != null)
				order.setBuyerEmail(order.getAgent().getLoginName());
		}
		return order;
	}

	public Agent getPartnerAccount(String partner) throws AppException
	{
		return agentDAO.queryByPartner(partner);
	}

	public ActionLog getActionLog(long orderId, long logType) throws Exception
	{
		return actionLogDAO.queryActionLogByOrderId(orderId, logType);
	}

	public boolean validateAmount(Agent agent, double price) throws Exception
	{
		if (agent != null){
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =agentDAO.getAgentBalance(agent.getId());
			if (ab.getAllowBalance() != null)
			{
				if (ab.getAllowBalance().doubleValue() < price) { return false; }
			}
			else
			{
				return false;
			}
		}else{
			return false;
		}
		return true;
	}

	// 验证加入商户圈
	public String validatePartner(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			System.out.println("合作伙伴号："+map.get("partner")); //打印出合作伙伴号查看
			// validate sing 验证登录
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}

	public String validateAddCoterie(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
/*			if (map.get("from_date") == null) { return gatewayMessage
			    .getFrom_Date_Cannot_Null(); }
			if (map.get("expire_date") == null) { return gatewayMessage
			    .getTo_Date_Cannot_Null(); }
			if (!AnalyseParameter.checkType(map.get("transaction_scope"))) { return gatewayMessage
			    .getTransaction_Type_Error(); }
			if (!AnalyseParameter.checkType(map.get("repayment_type"))) { return gatewayMessage
			    .getRepayment_Type_Error(); }
			if (!AnalyseParameter.checkFee(map.get("min_amount"))) { return gatewayMessage
			    .getMin_Amount_Error(); }

			if (!AnalyseParameter.checkType(map.get("leave_days"))) { return gatewayMessage
			    .getLeave_Days_Error(); }
*/			
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			// validate sing 验证登录
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}

	/**
	 * 验证圈中圈
	 */
	public String validateAgentRelationship(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email 验证 agent_email 商家帐号参数
			if (!AnalyseParameter.checkEmail(map.get("agent_email1"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }

			// 验证 agent_email 商家帐号参数
			if (!AnalyseParameter.checkEmail(map.get("agent_email2"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}

	public String validateCreditAmount(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			if (map.get("payment_type") == null) { return gatewayMessage
			    .getPayment_Type_Cannot_Null(); }
			if (map.get("from_date") == null) { return gatewayMessage
			    .getFrom_Date_Cannot_Null(); }
			if (map.get("to_date") == null) { return gatewayMessage
			    .getTo_Date_Cannot_Null(); }
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateAgentBalance(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			// validate partner
			if (map.get("partner") == null) { return gatewayMessage
			    .getPartner_Can_Not_Null(); }
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateExitCoterie(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateChangeCoterieStatus(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			if (!AnalyseParameter.checkType(map.get("change_status"))) { return gatewayMessage
			    .getChange_Status_Error(); }
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateRepaymentRule(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			if (!AnalyseParameter.checkType(map.get("repayment_type"))) { return gatewayMessage
			    .getRepayment_Type_Error(); }
			if (!AnalyseParameter.checkFee(map.get("min_amount"))) { return gatewayMessage
			    .getMin_Amount_Error(); }

			if (!AnalyseParameter.checkNumber(map.get("leave_days"))) { return gatewayMessage
			    .getLeave_Days_Error(); }
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	// <<<<<<< .mine

	/**
	 * 圈员属性验证
	 * 
	 * @param map
	 * @param url
	 * @param gatewayMessage
	 * @return
	 * @throws Exception
	 */
	public String validateRepayment(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{

			// 开始时间
			if (map.get("from_date") == null) { return gatewayMessage
			    .getFrom_Date_Cannot_Null(); }
			// 结束时间
			if (map.get("expire_date") == null) { return gatewayMessage
			    .getFrom_Date_Cannot_Null(); }
			// 商户帐号
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// 合作伙伴ID
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// 签名方式
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset")))
			{
				// 为什么
				return gatewayMessage.getValidate_Sign_Fail();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateAllMemberByPartner(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// >>>>>>> .r2811
			
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateAgentCoterie(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateDirectLoginForward(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("email"), true))
			{
				return gatewayMessage.getAgent_Email_Error();
			}
			else
			{
				Agent agent = null;
				agent = agentDAO.getAgentByEmail(map.get("email"));
				if (agent == null) { return gatewayMessage
				    .getTxn_Result_No_Such_Account(); }
			}
			if (map.get("forward") == null)
			{
				return gatewayMessage.getRefund_Sign_Etrance();
			}
			else
			{
				if (!map.get("forward").equals("charge")
				    && !map.get("forward").equals("transaction")
				    && !map.get("forward").equals("account")
				    && !map.get("forward").equals("balance")
				    && !map.get("forward").equals("mobileRechare")) { return gatewayMessage
				    .getForward_Page_Error(); // 转向页面参数有误
				}
			}
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateEmailLoginForward(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("loginName"), true))
			{
				return gatewayMessage.getRefund_Sign_Etrance();
			}
			else
			{
				Agent agent = null;
				agent = agentDAO.getAgentByEmail(map.get("loginName"));
				if (agent == null) { return gatewayMessage
				    .getTxn_Result_No_Such_Account(); }
			}

			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), AnalyseParameter.EMAIL_SIGN_KEY, map
			    .get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	// 房产网测试的验证方法
	public String testValidateDirectPay(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;
		try
		{
			String message = AnalyseParameter.paymentParameterVerify(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }

			// if (!AnalyseParameter.paymentParameterVerify(map))
			// { // 首先验证整个URL是否合法,返回true or false;
			// return gatewayMessage.getRefund_Sign_Etrance();
			// }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l)
			{
				// 平台商未签署协议。
				return gatewayMessage.getPartner_Not_Sign_Protocol(); // 不存在,返回
				// 平台商未签署协议。
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset")))
			{
				// return ExceptionConstant.REFUND_SIGN_ETRANCE;
				return gatewayMessage.getValidate_Sign_Fail();
			}

			// 根据seller_id 或者 seller_email 取得卖家的信息
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0) { return gatewayMessage
			    .getSeller_Not_Sign_Protocol(); }

			// 验证订单是否已经存在
			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			if (order != null && order.getId() > 0
				    && order.getStatus() == OrderDetails.STATUS_3 && order.getTransactions().size()>0) { return gatewayMessage
			    .getDuplicate_Batch_No(); }

			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");
			int flag = 0;
			List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("buyer_parameters"));
			if (listBuyer != null && listBuyer.size() > 0)
			{
				for (String[] buyerParameters : listBuyer)
				{
					Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
					BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
					if(buyerAgent!=null){
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
						if (!Gateway.equals(ab.getAllowBalance(), buyPrice))
							flag++;
					}
				}
			}
			if (flag > 0) { return gatewayMessage
			    .getBuyer_Account_Balance_Not_Enough(); // 其中有买家帐户余额不足
			}

			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);
				if (ca.compareTo(totalFee) == 1) { return gatewayMessage
				    .getRoyalty_Fee_Error(); }

			}
			this.setSeller(resultAgent);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}
	
	/**
	 * 验证支付接口
	 * 
	 */
	public String validateDirectPay(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;

		try{
			String message = AnalyseParameter.paymentParameterVerify(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); // 不存在,返回
			// 平台商未签署协议。
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }

			// 根据seller_id 或者 seller_email 取得卖家的信息
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0) { return gatewayMessage
			    .getSeller_Not_Sign_Protocol(); }
			
			OrderDetails order=null;
			// 验证订单是否已经存在
			if(map.get("out_trade_no")!=null&&!"".equals(map.get("out_trade_no"))){
				order = getOrderDetails(map.get("out_trade_no"), map
				    .get("partner"));
			}
			if (order != null && order.getId() > 0
			    && order.getStatus() == OrderDetails.STATUS_3 && order.getTransactions().size()>0) { return gatewayMessage
			    .getDuplicate_Batch_No(); }

			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");
			BigDecimal buyerTotalFee = new BigDecimal("0");
			int flag = 0;
			List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("buyer_parameters"));
			if (listBuyer != null && listBuyer.size() > 0)
			{
				for (String[] buyerParameters : listBuyer)
				{
					Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
					BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
					buyerTotalFee = buyerTotalFee.add(buyPrice);
					if(buyerAgent!=null){
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
						if (!Gateway.equals(ab.getAllowBalance(), buyPrice))
							flag++;
					}
				}
			}
			if (flag > 0) { return gatewayMessage
			    .getBuyer_Account_Balance_Not_Enough(); // 其中有买家帐户余额不足
			}

			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);
				if (listBuyer != null && listBuyer.size() > 0)
				{
					if (ca.compareTo(totalFee.add(buyerTotalFee)) == 1) { return gatewayMessage
					    .getRoyalty_Fee_Error(); }
				}
				else
				{
					if (ca.compareTo(totalFee) == 1) { return gatewayMessage
					    .getRoyalty_Fee_Error(); }
				}
			}
			this.setSeller(resultAgent);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	/**
	 * 验证隐式担保支付接口(买家)gatewaybizimp
	 */
	public String validateDirectPay_buyer(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		Agent buyeragent = null;
		if ((map.get("buyer_email")==null||"".equals(map.get("buyer_email").trim()))
				&&(map.get("buyer_id")==null||"".equals(map.get("buyer_id").trim()))) {
			return gatewayMessage.getBuyer_Parameters_Error(); // 买家信息集参数错误。
		}
		
		if (map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email").trim())){
			if(!AnalyseParameter.checkEmail(map.get("buyer_email"), false)){
				return gatewayMessage.getBuyer_Email_Error(); // 买家账户错误。
			}
			buyeragent = agentDAO.getAgentByEmail(map.get("buyer_email"));
		}
		
		if (map.get("buyer_id")!=null&&!"".equals(map.get("buyer_id").trim())){
			buyeragent = agentDAO.getAgentById(Long.parseLong(map.get("buyer_id")));
		}
		
		if(buyeragent==null){
			return gatewayMessage.getBuyer_Parameters_Error(); // 买家信息集参数错误。
		}
		
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(buyeragent.getId());
		if(ab.getAllowBalance().compareTo(new BigDecimal(map.get("total_fee")))==-1){
			return gatewayMessage.getBuyer_Account_Balance_Not_Enough();  //// 买家账户余额不足。
		}
		return gatewayMessage.getSuccess();	
	}

	public String validateGuaranteePay(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;
		try
		{

			String message = AnalyseParameter.paymentParameterVerify(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); // 不存在,返回
			// 平台商未签署协议。
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }

			// 根据seller_id 或者 seller_email 取得卖家的信息
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0) { return gatewayMessage
			    .getSeller_Not_Sign_Protocol(); }

			// 验证订单是否已经存在
			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			if (order == null) { return gatewayMessage.getOrder_Not_Exists(); }

			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");
			BigDecimal buyerTotalFee = new BigDecimal("0");
			int flag = 0;
			List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("buyer_parameters"));
			if (listBuyer != null && listBuyer.size() > 0)
			{
				for (String[] buyerParameters : listBuyer)
				{
					Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
					BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
					buyerTotalFee = buyerTotalFee.add(buyPrice);
					if(buyerAgent!=null){
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
						if (!Gateway.equals(ab.getAllowBalance(), buyPrice))
							flag++;
					}
				}
			}
			if (flag > 0) { return gatewayMessage
			    .getBuyer_Account_Balance_Not_Enough(); // 其中有买家帐户余额不足
			}

			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);
				if (listBuyer != null && listBuyer.size() > 0)
				{
					if (ca.compareTo(totalFee.add(buyerTotalFee)) == 1) { return gatewayMessage
					    .getRoyalty_Fee_Error(); }
				}
				else
				{
					if (ca.compareTo(totalFee) == 1) { return gatewayMessage
					    .getRoyalty_Fee_Error(); }
				}
			}
			this.setSeller(resultAgent);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	/*
	 * (non-Javadoc) 验证信用支付
	 * @see com.fordays.fdpay.cooperate.biz.GatewayBiz#validateCreditPay(java.util.HashMap, java.lang.String, com.fordays.fdpay.cooperate.GatewayMessage)
	 */
	public String validateCreditPay(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;  //卖家
		Agent buyerAgent = null;   //买家
		Coterie coterie = null;  //商户圈
		AgentCoterie this_agent_coterir = null; //买家信用支付圈
		try
		{
			//调用方法验证参数格式
			String message = AnalyseParameter.paymentParameterVerify(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			//验证类型
			if (!AnalyseParameter.checkType(map.get("type"))) { return gatewayMessage
			    .getType_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			//验证商户圈
			coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); // 不存在,返回
			}
			
			if(coterie.getStatus()==0){
				return gatewayMessage.getCREDIT_PAYMENT_IS_STOP();  //信用圈已经被禁用
			}
			
			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }

			/******验证买家******/
			if(map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email"))){
				if (!AnalyseParameter.checkEmail(map.get("buyer_email"), true)) { 
					return gatewayMessage.getBuyer_Email_Error(); }
				buyerAgent = agentDAO.getAgentByLoginName(map.get("buyer_email"));
			}else{
				buyerAgent = agentDAO.getAgentById(Long.parseLong(map.get("buyer_id")));
			}
			// 如果查不到买家信息
			if (buyerAgent == null || buyerAgent.getId() == 0) { return gatewayMessage
			    .getBuyer_Email_Error(); }
			
			
			/******验证卖家******/
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				if (!AnalyseParameter.checkEmail(map.get("seller_email"), true)) { 
					return gatewayMessage.getSeller_Email_Error(); }
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}
			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0) { return gatewayMessage
			    .getSeller_Not_Sign_Protocol(); }

			/*******订单是否已经存在*******/
//			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
//			    .get("partner"));
			OrderDetails order = agentBindDAO.queryOrderDetailByOrderNoAndPartner(map.get("out_trade_no"));
			if (order != null && order.getId() > 0
				    && order.getTransactions().size()>0) { return gatewayMessage
			    .getDuplicate_Batch_No(); }
			
			/********验证用户信用圈*********/
			System.out.println("买家账户="+buyerAgent.getLoginName());
			this_agent_coterir = agentCoterieDAO.queryByAgentEmail(
					buyerAgent.getLoginName(), map.get("partner"));
			System.out.println("用户信用圈id="+this_agent_coterir.getId());
			if (this_agent_coterir == null) { return gatewayMessage
			    .getAgent_Not_credit_Member(); }
			if(this_agent_coterir.getStatus()==AgentCoterie.CHANGE_STATUS_0){  //验证买家是否可信用支付
				return "买家"+gatewayMessage.getCredit_Payment_Has_Stop();
			}
			if (!agentCoterieDAO.checkCreditExpireDate(
					buyerAgent,coterie)) {
				return gatewayMessage.getCredit_Payment_Date_Expire();// 信用支付有效期已过
			}
			if (this_agent_coterir != null
			    && this_agent_coterir.getRepaymentType() == 1) //逐笔还款 判断是否还了上一笔金额
			{
				List creditPayList = transactionDAO.getCreditPayListByAgent(buyerAgent);
				if (creditPayList != null && creditPayList.size() > 0) { return gatewayMessage
				    .getCredit_Not_Repayment(); }
			}
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
			if (this_agent_coterir != null
				    && this_agent_coterir.getRepaymentType() == 2) //逐笔还款 判断是否还了上一笔金额
				{
				System.out.println("买家信用佘额："+ab.getCreditBalance());
				System.out.println("最小信用金额："+this_agent_coterir.getMinAmount());
					if (ab.getCreditBalance().compareTo(
							this_agent_coterir.getMinAmount()) < 0) { // 信用余额小于最少限制金额
						return gatewayMessage.getCredit_Price_Less_Then();
					}
				}
			
			/**********验证数据集***********/
			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));
			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));  //交易金额
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(  //平台收费金额
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");  //总的分润金额

			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);
				System.out.println("交易金额："+totalFee);
				System.out.println("平台收费金额加上总分润金额："+ca);
				if(ca.compareTo(totalFee)==10){//ca不能大佘totalFee
					return gatewayMessage.getRoyalty_Fee_Error();//分润金额有误
				}				
			}
			
			if(ab.getCreditBalance()==null||totalFee.compareTo(ab.getCreditBalance())==1){
				return gatewayMessage.getCredit_Account_Balance_Not_Enough();//买家授信佘额不足
			}

			this.setSeller(resultAgent);// return seller info
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	public String validateDirectCreditPayment(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;
		try
		{
			if (!(map.containsKey("payment_type") && map.containsKey("agent_email")
			    && map.containsKey("credit_email") && map.containsKey("subject") && map
			    .containsKey("credit_amount")))
			{
				System.out.println(map.containsKey("payment_type"));
				System.out.println(map.containsKey("agent_email"));
				System.out.println(map.containsKey("credit_email"));
				System.out.println(map.containsKey("subject"));
				System.out.println(map.containsKey("body"));
				System.out.println(map.containsKey("credit_amount"));
				return gatewayMessage.getNot_Found__Parameter();
			}

			if (!AnalyseParameter.checkType(map.get("payment_type"))) { return gatewayMessage
			    .getPayment_Type_Error(); }
			if (!AnalyseParameter.checkFee(map.get("credit_amount"))) { return gatewayMessage
			    .getCredit_Amount_Error(); }

			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if (!AnalyseParameter.checkEmail(map.get("credit_email"), true)) { return gatewayMessage
			    .getCredit_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); // 不存在,返回
			}

			// 验证订单是否已经存在
			// OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
			// .get("partner"));
			// if (order != null && order.getId() > 0
			// && order.getStatus() == Transaction.STATUS_3) {
			// return gatewayMessage.getDuplicate_Batch_No();
			// }
			AgentCoterie this_agent_coterir = agentCoterieDAO.queryByAgentEmail(map
			    .get("credit_email"), map.get("partner"));
			System.out.println("圈主:" + coterie.getAgent().getLoginName());
			System.out.println("授信商户:" + map.get("credit_email"));
			if (this_agent_coterir == null
			    || !coterie.getAgent().getLoginName().equals(map.get("credit_email")))
			{
				gatewayMessage.getAgent_Not_credit_Member();
			}
			else if (!this_agent_coterir.getCoterie().getPartner().equals(
			    map.get("partner")))
			{
				gatewayMessage.getAgent_Not_credit_Member();
			}
			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); // 验证签名失败
			}
			this.setSeller(resultAgent);// return seller info

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	public String validateDirectCreditRepayment(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception{
		Agent resultAgent = null;
		try
		{
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			if (map.get("out_trade_no") == null || map.get("return_url") == null
			    || map.get("notify_url") == null || map.get("body") == null
			    || map.get("repayment_amount") == null||map.get("agent_email")==null
			    || map.get("repayment_type") == null) { return gatewayMessage
			    .getNot_Found__Parameter(); }
			if (!AnalyseParameter.checkType(map.get("repayment_type"))) { return gatewayMessage
			    .getPayment_Type_Error(); }

			if (!AnalyseParameter.checkFee(map.get("repayment_amount"))) { return gatewayMessage
			    .getRepayment_Amount_Error(); }

			
			OrderDetails order = agentBindDAO.queryOrderDetailByOrderNoAndPartner(map.get("out_trade_no"));
			if (order != null && order.getId() > 0
				    && order.getTransactions().size()>0) { return gatewayMessage
			    .getDuplicate_Batch_No(); }

			Agent fromAgent = agentDAO.getAgentByEmail(map.get("agent_email"));
			if (fromAgent == null) { return gatewayMessage.getAccount_Not_Exists(); }
			// 验证是不是本圈成员
			AgentCoterie this_agent_coterie = agentCoterieDAO.queryByAgentEmail(map
			    .get("agent_email"), map.get("partner"));
			if (this_agent_coterie == null)
			{
				return gatewayMessage.getAgent_Not_credit_Member();
			}
			else if (!this_agent_coterie.getCoterie().getPartner().equals(
			    map.get("partner"))) { return gatewayMessage
			    .getAgent_Not_credit_Member(); }		
			
			// 验证还款金额必须小于等于欠款金额
			BigDecimal this_repayment_amount = new BigDecimal(map
			        .get("repayment_amount"));
			BigDecimal this_credit_arrearage = transactionDAO
			    .getCreditArrearage(fromAgent);
			System.out.println("----" + fromAgent.getLoginName() + "累积欠款:"
			    + this_credit_arrearage);
			System.out.println("----" + fromAgent.getLoginName() + "本次还款:"
			    + this_repayment_amount);
			if (this_repayment_amount.compareTo(this_credit_arrearage) == 1) { return gatewayMessage
			    .getCredit_RepaymentAmount_Sole_Over(); }
			System.out.println("fromAgent = " + fromAgent.getLoginName());
			if (map.get("payPassword") != null)
			{
				if (!fromAgent.getPayPassword().equals(map.get("payPassword"))) { return gatewayMessage
				    .getPassword_Error(); }
			}
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
			if (ab.getAllowBalance() == null
			    || !Gateway.equals(ab.getAllowBalance(),this_repayment_amount )) { return gatewayMessage
			    .getTxn_Result_Account_Balance_Not_Enough(); }
		
			
			
			// BigDecimal hasRepaymentAmount =
			// transactionDAO.getHasRepaymentTotalAmount(trans.getOrderDetails().getOrderDetailsNo());
			// BigDecimal readyRepayment = new
			// BigDecimal(map.get("repayment_amount"));
			// if(readyRepayment.add(hasRepaymentAmount).compareTo(trans.getAmount())>0){
			// return gatewayMessage.getRepayment_Amount_Is_Too_Big();
			// }
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); // 不存在,返回
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); // 验证签名失败
			}
			this.setSeller(resultAgent);// return seller info

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	public String validateDirectPayForUsers(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;
		try
		{
			String message = AnalyseParameter.paymentParameterVerify(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }

			// if (!AnalyseParameter.paymentParameterVerifyForUsers(map))
			// { // 首先验证整个URL是否合法,返回true or false;
			// // return ExceptionConstant.REFUND_SIGN_ETRANCE;
			// return gatewayMessage.getRefund_Sign_Etrance();
			// }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); // 不存在,返回
			// 平台商未签署协议。
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }

			// 根据seller_id 或者 seller_email 取得卖家的信息
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0) { return gatewayMessage
			    .getSeller_Not_Sign_Protocol(); }

			// 验证订单是否已经存在
			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			if (order != null && order.getId() > 0
				    && order.getStatus() == OrderDetails.STATUS_3 && order.getTransactions().size()>0) { return gatewayMessage
			    .getDuplicate_Batch_No(); }

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");
			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));
			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);
				if (ca.compareTo(totalFee) == 1) { return gatewayMessage
				    .getRoyalty_Fee_Error(); }
			}
			this.setSeller(resultAgent);// return seller info

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	public OrderDetails appendOrder(HashMap<String, String> map, Agent userAgent,
	    String url, boolean isPayByBank) throws Exception
	{
		String strPrice = map.get("total_fee");
		double paymentPrice = Double.parseDouble(strPrice);
		String price = map.get("price");
		String quantity = map.get("quantity");
		// String paymethod = map.get("paymethod");
		String payment_type = map.get("payment_type");
		long orderDetailsType = 0;
		long buyType = 0;

		if (payment_type.equals("1")){
			orderDetailsType = OrderDetails.ORDER_DETAILS_TYPE_1;
			buyType = OrderDetails.BUY_TYPE_1;    //标识为即时到帐支付
		}else if (payment_type.equals("2")){
			orderDetailsType = OrderDetails.ORDER_DETAILS_TYPE_2;
			buyType = OrderDetails.BUY_TYPE_3;    //标识为信用支付
		}else if (payment_type.equals("3")){
			orderDetailsType = OrderDetails.ORDER_DETAILS_TYPE_5;
			buyType = OrderDetails.BUY_TYPE_2;    //标识为担保支付
		}else if (payment_type.equals("4")){
			orderDetailsType = OrderDetails.ORDER_DETAILS_TYPE_7;
			buyType = OrderDetails.BUY_TYPE_9;    //标识为冻结支付
		}else
		{
			orderDetailsType = new Long(-1);
			buyType = new Long(-1);    //标识为无效支付
		}
		Long logStatus = null;

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setShopName(map.get("subject"));
		orderDetails.setPartner(map.get("partner"));
		orderDetails.setOrderNo(map.get("out_trade_no"));
		orderDetails.setDetailsContent(map.get("body"));
		// orderDetails.setReferenceOrderDetailsNo();
		orderDetails.setOrderDetailsType(orderDetailsType);
		orderDetails.setBuyType(buyType);
		orderDetails.setAgent(userAgent);
		if (!isPayByBank)
		{
			if (validateAmount(userAgent, paymentPrice))
			{
				orderDetails.setStatus(Transaction.STATUS_3);
				logStatus = new Long(1);
			}
			else
			{
				orderDetails.setStatus(Transaction.STATUS_1);
				logStatus = new Long(0);
			}
		}
		else
		{
			orderDetails.setStatus(Transaction.STATUS_1);
			logStatus = new Long(0);
		}

		List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("buyer_parameters"));
		if (listBuyer != null && listBuyer.size() > 0)
		{
			BigDecimal buyPrice = new BigDecimal("0");
			for (String[] buyerParameters : listBuyer)
			{
				buyPrice = new BigDecimal(buyerParameters[1]).add(buyPrice).setScale(2,
				    BigDecimal.ROUND_HALF_UP);
			}
			orderDetails.setPaymentPrice(buyPrice.add(new BigDecimal(strPrice))
			    .setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		else
		{
			orderDetails.setPaymentPrice(BigDecimal.valueOf(paymentPrice));
		}
		// set price 设定价格
		if (price != null && !"".equals(price))
		{
			orderDetails.setShopPrice(BigDecimal.valueOf(Double.parseDouble(price)));
		}
		else
		{
			orderDetails.setShopPrice(BigDecimal.valueOf(paymentPrice));
		}
		// set quantity 设置数量
		if (quantity != null && !"".equals(quantity))
		{
			orderDetails.setShopTotal(new Long(quantity));
		}
		else
		{
			orderDetails.setShopTotal(new Long(1));
		}

		// 设置邮费默认为 0.00
		orderDetails.setEmailPrice(new BigDecimal("0"));

		// if (paymethod != null && !"".equals(paymethod))
		// {
		// orderDetails.setLogisticsType(new Long(paymethod));
		// }
		orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());

		transactionDAO.addOrderDetails(orderDetails);

		String urlStr = "https://www.qmpay.com/cooperate/gateway.do?" + url;
		// set action log
		ActionLog actionLog = new ActionLog();
		actionLog.setStatus(logStatus);
		actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
		actionLog.setContent(urlStr);
		actionLog.setOrderDetails(orderDetails);
		actionLog.setLogType(ActionLog.LOG_TYPE_PAY_REQUEST);
		actionLogDAO.save(actionLog);
		return orderDetails;
	}

	public void updateOrderDetailsForAgent(OrderDetails orderDetails)
	    throws AppException
	{
		transactionDAO.updateOrderDetails(orderDetails);
	}

	public String direct_credit_payment(HashMap<String, String> map,
	    Agent userAgent, String url, String encode, boolean isPayByBank,
	    GatewayMessage gatewayMessage) throws Exception
	{
		String isSuccess = "";
		try
		{
			OrderDetails orderDetails = new OrderDetails();
			long paymentType = new Long(map.get("payment_type"));
			BigDecimal creditAmount = new BigDecimal(map.get("credit_amount"));
			orderDetails.setOrderDetailsType(paymentType);
			orderDetails.setAgent(userAgent);
			orderDetails.setShopName(map.get("subject"));
			orderDetails.setPartner(map.get("partner"));
			orderDetails.setDetailsContent(map.get("body"));
			orderDetails.setStatus(OrderDetails.STATUS_11);// 授信未还款
			orderDetails.setPaymentPrice(creditAmount);
			orderDetails.setShopPrice(creditAmount);
			orderDetails.setEmailPrice(new BigDecimal("0"));
			orderDetails.setShopTotal(new Long(1));
			orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
			orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			orderDetails.setOrderNo(map.get("out_trade_no"));
			orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_2);
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_8);//标识授信支付
			transactionDAO.addOrderDetails(orderDetails);

			Agent fromAgent = userAgent;
			Agent toAgent = agentDAO.getAgentByEmail(map.get("credit_email"));

			createCreditTransaction(orderDetails, fromAgent, toAgent,
			    Transaction.TYPE_101, creditAmount, null, Transaction.STATUS_3,
			    new Timestamp(System.currentTimeMillis()), paymentType);

			isSuccess = "SUCCESS," + orderDetails.getOrderDetailsNo();
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			Coterie this_coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			System.out.println("----圈主----" + this_coterie.getAgent().getLoginName());
			System.out.println("----授信商户----" + map.get("credit_email"));
			if (this_coterie.getAgent().getLoginName()
			    .equals(map.get("credit_email")))
			{
				// 此为收回授信,把收回授信当作还款来处理金额
				BigDecimal credit_amount = new BigDecimal(map.get("credit_amount"));
				Agent temp_agent = agentDAO.getAgentByLoginName(map.get("agent_email"));
				update_credit_give_status(temp_agent, credit_amount);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return isSuccess;
	}

	public String direct_credit_repayment(HashMap<String, String> map,
	    Agent userAgent, String url, String encode, boolean isPayByBank,
	    GatewayMessage gatewayMessage) throws Exception
	{
		String isSuccess = "";
		try
		{
			OrderDetails orderDetails = new OrderDetails();
			// Transaction tran = transactionDAO.getTransByOrderNo(map
			// .get("out_trade_no"));
			AgentCoterie this_agent_coterie = agentCoterieDAO.queryByAgentEmail(map
			    .get("agent_email"), map.get("partner"));
			Agent fromAgent = this_agent_coterie.getAgent();
			Agent toAgent = this_agent_coterie.getCoterie().getAgent();
			orderDetails.setPaymentReason(map.get("body"));
			orderDetails.setPaymentPrice(new BigDecimal(map.get("repayment_amount")));
			orderDetails.setShopName(map.get("subject"));
			orderDetails.setShopPrice(new BigDecimal(map.get("repayment_amount")));
			orderDetails.setShopTotal(new Long(1));
			orderDetails.setStatus(OrderDetails.STATUS_11);
			orderDetails.setOrderDetailsType(Long
			    .parseLong(map.get("repayment_type")));
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_8);//标识授信支付
			orderDetails.setOrderDetailsType(new Long(map.get("repayment_type")));
			orderDetails.setPartner(map.get("partner"));
			orderDetails.setOrderNo(map.get("out_trade_no"));
			Transaction tempTransaction = new Transaction();

			tempTransaction.setAmount(new BigDecimal(map.get("repayment_amount")));

			tempTransaction.setFromAgent(fromAgent);
			tempTransaction.setToAgent(toAgent);

			tempTransaction.setStatus(Transaction.STATUS_3);
			tempTransaction.setType(Transaction.TYPE_103);

			tempTransaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			long repaymentType = new Long(map.get("repayment_type"));
			System.out.println("fromAgent = " + fromAgent.getLoginName());
			System.out.println("toAgent = " + toAgent.getLoginName());
			agentDAO.reduceAmount(fromAgent, new BigDecimal(map
			    .get("repayment_amount")));// 把钱从余额里面扣除
			if (repaymentType == 1)
			{
				agentDAO
				    .addAmount(toAgent, new BigDecimal(map.get("repayment_amount"))); // 把钱加到可用余额里面
				orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
			}
			else if (repaymentType == 2)
			{
				agentDAO.addCreditAmount(toAgent, new BigDecimal(map
				    .get("repayment_amount"))); // 把钱加到信用余额里面
				orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_3);
			}

			orderDetails.setDetailsContent(map.get("body"));

			orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			// orderDetails.setOrderNo(tran.getOrderDetails().getOrderDetailsNo());

			String no = noUtil.getTransactionNo();
			tempTransaction.setNo(no);

			transactionDAO.addOrderDetails(orderDetails);
			tempTransaction.setOrderDetails(orderDetails);
			tempTransaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			// tran.setStatus(Transaction.STATUS_3);
			transactionDAO.saveTransaction(tempTransaction);
			BigDecimal repayment_amount = new BigDecimal(map.get("repayment_amount"));
			//修改授信状态
			update_credit_give_status(tempTransaction.getFromAgent(),
			    repayment_amount);
			//修改信用支付状态
			update_credit_payment_status(tempTransaction.getFromAgent(),
			    repayment_amount);
			isSuccess = "0";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			isSuccess = "1";
		}
		return isSuccess;
	}

	public void update_credit_payment_status(Agent agent,
	    BigDecimal repayment_amount) throws AppException{
		System.out.println("---update_credit_payment_status---"
		    + agent.getLoginName());
		// 查询所有信用支付记录
		List paymentList = transactionDAO.getCreditPayListByAgent(agent);
		
		if (paymentList != null&&paymentList.size()!=0)
		{	
			System.out.println("未还款数量paymentList.size()："+paymentList.size());
			Transaction temp = (Transaction) paymentList.get(0);
			System.out.println("本次要还的金额repayment_amount："+repayment_amount);
			//已还金额加上当前要还的金额
			if (temp.getOrderDetails().getRepayAmount()!= null)
				repayment_amount = repayment_amount.add(temp.getOrderDetails()
				    .getRepayAmount());
			System.out.println("以前还款的金额getRepayAmount："+temp.getOrderDetails()
				    .getRepayAmount());
			System.out.println("相加后的还款金额repayment_amount："+repayment_amount);
			
			Transaction tran_payment = null;
			for (int i = 0; i < paymentList.size(); i++)
			{
				tran_payment = (Transaction)paymentList.get(i); //当前信用支付交易明细
				
				BigDecimal payMentPrice = tran_payment.getOrderDetails().getPaymentPrice();  //当前信用支付交易金额
				//调用方法获取当前这条支付已退款金额
				BigDecimal refundCredit = transactionDAO.getCreditRePayListByAgent(agent, 
						tran_payment.getOrderDetails().getOrderDetailsNo());
				BigDecimal torepayCredit = payMentPrice.subtract(refundCredit);
				System.out.println("交易明细id："+tran_payment.getId());
				//System.out.println("交易金额payMentPrice："+payMentPrice);
				//System.out.println("已退款金额refundCredit："+refundCredit);
				//System.out.println("还要还的信用金额torepayCredit："+torepayCredit);
				if (repayment_amount.compareTo(torepayCredit) == -1)
				{	
					tran_payment.getOrderDetails().setRepayAmount(repayment_amount);
					break;
				}
				else if (repayment_amount.compareTo(torepayCredit) == 0)
				{
					tran_payment.getOrderDetails().setRepayAmount(repayment_amount);
					tran_payment.getOrderDetails().setStatus(OrderDetails.STATUS_12);
					break;
				}
				else if (repayment_amount.compareTo(torepayCredit) == 1)
				{
					if (i + 2 <= paymentList.size())
					{
						repayment_amount = repayment_amount.subtract(torepayCredit);//还可以还的还款金额
					}
					else
					{
						System.out.println("还钱总额:" + repayment_amount);
						System.out.println("单比信用支付欠款:"
						    + tran_payment.getOrderDetails().getPaymentPrice());
						tran_payment.getOrderDetails().setRepayAmount(
						    tran_payment.getOrderDetails().getPaymentPrice());
						System.out.println("------没有下一条,信用支付欠款全数还完-----");
					}
					tran_payment.getOrderDetails().setStatus(OrderDetails.STATUS_12);
				}
			}
			if (tran_payment != null)
				transactionDAO.saveTransaction(tran_payment);
		}

	}

	public void update_credit_give_status(Agent agent, BigDecimal repayment_amount)
	    throws AppException
	{
		// 查询所有授信记录
		System.out
		    .println("---update_credit_give_status---" + agent.getLoginName());
		List creditgiveList = transactionDAO.getCreditGiverListByAgent(agent);
		if (creditgiveList != null)
		{
			Transaction temp = (Transaction) creditgiveList.get(0);
			if (temp.getOrderDetails().getRepayAmount() != null)
				repayment_amount = repayment_amount.add(temp.getOrderDetails()
				    .getRepayAmount());
			Transaction tran_credit_give = null;
			for (int i = 0; i < creditgiveList.size(); i++)
			{
				tran_credit_give = (Transaction) creditgiveList.get(i);
				if (repayment_amount.compareTo(tran_credit_give.getOrderDetails()
				    .getPaymentPrice()) == -1)
				{
					tran_credit_give.getOrderDetails().setRepayAmount(repayment_amount);
					break;
				}
				else if (repayment_amount.compareTo(tran_credit_give.getOrderDetails()
				    .getPaymentPrice()) == 0)
				{
					tran_credit_give.getOrderDetails().setRepayAmount(repayment_amount);
					tran_credit_give.getOrderDetails().setStatus(OrderDetails.STATUS_12);
					break;
				}
				else if (repayment_amount.compareTo(tran_credit_give.getOrderDetails()
				    .getPaymentPrice()) == 1)
				{
					if (i + 2 <= creditgiveList.size())
						tran_credit_give.getOrderDetails().setRepayAmount(
						    tran_credit_give.getOrderDetails().getPaymentPrice());
					repayment_amount = repayment_amount.subtract(tran_credit_give
					    .getOrderDetails().getPaymentPrice());
					tran_credit_give.getOrderDetails().setStatus(OrderDetails.STATUS_12);
				}
			}
			if (tran_credit_give != null)
				transactionDAO.saveTransaction(tran_credit_give);
		}
	}

	public String credit_payment(HashMap<String, String> map, Agent userAgent,
	    String url, String encode, boolean isPayByBank,
	    GatewayMessage gatewayMessage) throws Exception
	{
		String orderNo = map.get("out_trade_no");
		System.out.println(">>>>>>>>>>>>信用支付订单方法开始" + orderNo);
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		Agent userInfo = userAgent;
		boolean isNewOrder = false;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist 验证订单是否已经存在
			orderDetails = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			// 如果订单不存在,往数据库加一条新的订单
			boolean f = orderDetails == null || orderDetails.getId() < 1;
			System.out.println(">>>>>>>>>>如果订单 " + orderNo
			    + " 不存在,往数据库加一条新的订单 这个地方-----" + f);
			if (f)
			{
				// create the order details if this order not exist
				orderDetails = appendOrder(map, userInfo, url, false);
				isNewOrder = true;
			}
			// royalty fee
			f = isNewOrder
			    || (!isNewOrder && Transaction.STATUS_3 != orderDetails.getStatus());
			

			if (f)
			{
				System.out.println(">>>>>>>>>> 开始分润");

				String returnParameter = directCreditPayment(map, orderDetails,
				    userInfo, sysDate, gatewayMessage);
				orderDetails.setStatus(OrderDetails.STATUS_11);
				System.out.println(">>>>>>>>>> 分润结束");
				if (returnParameter.equals(gatewayMessage
				    .getCredit_Account_Balance_Not_Enough())) { return null; }
				if (returnParameter != null && returnParameter.length() > 0)
				{
					if (!isNewOrder)
					{
						ActionLog actionLog = actionLogDAO
						    .queryActionLogByOrderId(orderDetails.getId());
						actionLog.setStatus(new Long(1));
						actionLogDAO.update(actionLog);
					}
					// patch up redirect url
					Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
					// patch up redirect url
					returnURL = UtilURL.directPayRedirectUrl(map, String
					    .valueOf(orderDetails.getId()), orderDetails.getOrderDetailsNo(),
					    userInfo, com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
					    strSysDate, coterie.getSignKey(), encode);

					// set action log
					ActionLog actionLog = new ActionLog();
					actionLog.setStatus(new Long(1));
					actionLog.setLogDate(sysDate);
					actionLog.setContent(map.get("return_url") + "?" + returnURL[1]);
					actionLog.setOrderDetails(orderDetails);
					actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
					actionLogDAO.save(actionLog);
				}
				else
				{
					System.out.println(">>>>>>>>>>>>>>>>>>>>> 分润返回结果为空!");
				}
			}
			else
			{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> isNewOrder=" + isNewOrder
				    + " >>>>>>>>>>> orderDetails.getStatus() = "
				    + orderDetails.getStatus());
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return returnURL[0];
	}

	public String directCreditPayment(HashMap<String, String> map,
	    OrderDetails orderDetails, Agent userAgent, Timestamp sysDate,
	    GatewayMessage gatewayMessage) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
		Long type = new Long(map.get("type"));
		List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("royalty_parameters"));
		List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("buyer_parameters"));
		// temp parameter
		BigDecimal tempPay = new BigDecimal("0");
		BigDecimal finalFee = new BigDecimal("0");

		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId());

		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());

		if (listBuyer != null && listBuyer.size() > 0)
		{
			for (String[] buyerParameters : listBuyer)
			{
				Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
				BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
				if(buyerAgent!=null){
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
					System.out.println(buyerAgent.getCertification());
					if (!Gateway.equals2(ab.getCreditBalance(), buyPrice))
					{
						sb.append(buyerParameters[0]);
						sb.append("^");
						sb.append(buyerParameters[1]);
						sb.append("^");
						sb.append(gatewayMessage.getCredit_Account_Balance_Not_Enough());// 账户授信余额不足。
					}
					else
					{
						buildCreditTransaction(orderDetails, buyerAgent, avouchAgent, type,
						    Transaction.TYPE_180, buyPrice.setScale(2,
						        BigDecimal.ROUND_HALF_UP), null, Transaction.STATUS_3,
						    sysDate);
						sb.append(gatewayMessage.getSuccess());
					}
				}
			}
		}

		// 先检查是否够钱付款
		Agent paymentAgent = agentDAO.getAgentById(userAgent.getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(paymentAgent.getId());
		System.out.println(ab.getCreditBalance());
		if (!Gateway.equals2(ab.getCreditBalance(), totalFee)) { return sb
		    .append(gatewayMessage.getCredit_Account_Balance_Not_Enough())
		    .toString(); }
		// 先把买家的授信金额打到平台帐号上
		buildCreditTransaction(orderDetails, paymentAgent, avouchAgent,
		    Transaction.TYPE_180, type, totalFee, null, Transaction.STATUS_3,
		    sysDate);
		

		// pay flat fee from partner's avouch account
		// 收取平台费用,费用 = 交易金额 * 费率 / 100
		BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
		    new BigDecimal("100"));
		BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal tempTotalFee = new BigDecimal("0");

		// 把平台帐号的钱分润到各个需要分润的帐号上
		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			for (String[] royaltyParameters : listRoyalty)
			{
				Agent resultAgent = agentDAO.getAgentByEmail(royaltyParameters[0]);
				BigDecimal tempFee = new BigDecimal(royaltyParameters[1].trim());
				tempTotalFee = tempTotalFee.add(tempFee);
				sb.append(royaltyParameters[0]);
				sb.append("^");
				sb.append(royaltyParameters[1]);
				sb.append("^");
				sb.append(royaltyParameters[2]);
				sb.append("^");
				try
				{
					if (resultAgent != null && resultAgent.getId() > 0
					    && tempFee.compareTo(new BigDecimal("0")) > 0)
					{
//						// 获取AgentBalance对象（用于获取金额）
//						AgentBalance ab1 = agentDAO.getAgentBalance(avouchAgent.getId());
//						System.out.println(ab1.getCreditBalance());
//						if (ab1.getCreditBalance() != null
//						    && ab1.getCreditBalance().compareTo(tempFee) >= 0)
//						{
							buildTransaction(orderDetails, avouchAgent, resultAgent,
							    Transaction.TYPE_180, tempFee, null, Transaction.STATUS_3);
							sb.append(gatewayMessage.getSuccess());
//						}
//						else
//						{
//							// 账户余额不足,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
//							sb.append(gatewayMessage
//							    .getTxn_Result_Account_Balance_Not_Enough());
//							tempPay = tempPay.add(tempFee);
//						}
					}
					else
					{
						// 不存在该账户,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
						sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
						tempPay = tempPay.add(tempFee);
					}
				}
				finally
				{
					sb.append("|");
				}

			}
		}
		else
		{
			// 如果没有分润参数royalty_parameters,分润方就默认为卖家
			Agent resultAgent = null;
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			tempTotalFee = new BigDecimal(map.get("total_fee")).subtract(flatFee);
			BigDecimal tempFee = new BigDecimal(map.get("total_fee"))
			    .subtract(flatFee);

			sb.append(map.get("seller_email"));
			sb.append("^");
			sb.append(tempFee);
			sb.append("^");
			sb.append("^");
			try
			{
				if (resultAgent != null && resultAgent.getId() > 0)
				{
					if (avouchAgent.getId() != resultAgent.getId())
					{
						buildTransaction(orderDetails, avouchAgent, resultAgent,
						    Transaction.TYPE_180, tempFee, null, Transaction.STATUS_3);
					}
					sb.append(gatewayMessage.getSuccess());
				}
				else
				{
					sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
					tempPay = tempFee;
				}
			}
			finally
			{
				sb.append("|");
			}
		}

		// pay to temp account if poyalty account error
		// 如果分润的时候,有些分润帐号错误或者某些原因分润失败的话,把金额打到临时帐号上
		System.out.println("分润失败的金额tempPay："+tempPay);
		if (tempPay.doubleValue() > 0)
		{
			// Agent tempAgent = agentDAO.getAgentById(tempAccount);
			buildTransaction(orderDetails, avouchAgent, tempAgent,
			    Transaction.TYPE_180, tempPay, null, Transaction.STATUS_3);
		}

		if (Constant.platRateAgent == null)
		{
			System.out.println("*********平台收费账号为空..");
		}
		else
		{
			System.out.println("*********平台收费账号不为空ID="
			    + Constant.platRateAgent.getId());
		}

		// 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
		BigDecimal ca = tempTotalFee.add(flatFee);
		if (ca.compareTo(totalFee) < 0)
		{
			finalFee = totalFee.subtract(ca.setScale(2, BigDecimal.ROUND_HALF_UP));
			System.out.println("分润和收取手续费后剩下的金额finalFee："+finalFee);
			buildTransaction(orderDetails, avouchAgent, tempAgent,
			    Transaction.TYPE_180, finalFee, null, Transaction.STATUS_3);
		}
		if(Constant.platRateAgent!=null&&flatFee.compareTo(new BigDecimal("0"))==1){
			System.out.println("手续费flatFee："+flatFee);
			buildTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
			    Transaction.TYPE_180, flatFee, null, Transaction.STATUS_3);
		}

		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(sysDate);
		transactionDAO.updateOrderDetails(orderDetails);

		System.out.println(">>>>>>>>>>>>>>> 分润结束");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 * 支付及时到账
	 */
	public String payment(HashMap<String, String> map, Agent userAgent,
	    String url, String encode, boolean isPayByBank,
	    GatewayMessage gatewayMessage) throws Exception
	{

		String orderNo = map.get("out_trade_no");
		System.out.println(">>>>>>>>>即时到帐支付订单方法 外部订单号：" + orderNo); //外部订单号
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		System.out.println("/**********当前时间strSysDate*********/"+strSysDate);
		Agent userInfo = userAgent;
		boolean isNewOrder = false;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist 验证订单是否已经存在
			orderDetails = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			// 如果订单不存在,往数据库加一条新的订单
			boolean f = !isPayByBank
			    && (orderDetails == null || orderDetails.getId() < 1);
			System.out.println(">>>>>>>如果订单 " + orderNo
			    + " 不存在,往数据库加一条新的订单 这个地方-----" + f);
			if (f)
			{
				// create the order details if this order not exist
				orderDetails = appendOrder(map, userInfo, url, false);
				isNewOrder = true;
			}
			else
			{
				if (isPayByBank)
				{
					userInfo = orderDetails.getAgent();
				}
				else
				{
					userInfo = userAgent;
					orderDetails.setAgent(userInfo);
					transactionDAO.updateOrderDetails(orderDetails);
				}
			}
			
			f = isNewOrder
			    || (!isNewOrder && Transaction.STATUS_3 != orderDetails.getStatus());

			if (f)
			{
				// if (validateAmount(userAgent, paymentPrice)) {
				// 开始分润********
				String returnParameter = "";
		
				//调用方法执行插入明细
				returnParameter = directPayment(map, orderDetails, userInfo, sysDate,
				    gatewayMessage);
					
				// 分润结束*********
				if (returnParameter.equals(gatewayMessage
				    .getTxn_Result_Account_Balance_Not_Enough())) { return gatewayMessage
				    .getTxn_Result_Account_Balance_Not_Enough(); }
				if (returnParameter != null && returnParameter.length() > 0)
				{
					// return success
					// update action log status
					if (!isNewOrder)
					{
						ActionLog actionLog = actionLogDAO
						    .queryActionLogByOrderId(orderDetails.getId());
						actionLog.setStatus(new Long(1));
						actionLogDAO.update(actionLog);
					}
					// patch up redirect url
					Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
					// patch up redirect url
					returnURL = UtilURL.directPayRedirectUrl(map, String
					    .valueOf(orderDetails.getId()), orderDetails.getOrderDetailsNo(),
					    userInfo, com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
					    strSysDate, coterie.getSignKey(), encode);

					// set action log
					ActionLog actionLog = new ActionLog();
					actionLog.setStatus(new Long(1));
					actionLog.setLogDate(sysDate);
					actionLog.setContent(map.get("return_url") + "?" + returnURL[1]);
					actionLog.setOrderDetails(orderDetails);
					actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
					actionLogDAO.save(actionLog);
				}
				else
				{
					System.out.println(">>>>>>>>>>>>>>>>>>>>> 分润返回结果为空!");
				}
			}
			else
			{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> isNewOrder=" + isNewOrder
				    + " >>>>>>>>>>> orderDetails.getStatus() = "
				    + orderDetails.getStatus());
			}
			// }
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return returnURL[0];
	}

	public String guarantee_payment(HashMap<String, String> map, Agent userAgent,
	    String url, String encode, boolean isPayByBank,
	    GatewayMessage gatewayMessage) throws Exception
	{

		String orderNo = map.get("out_trade_no");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>进入guarantee_payment-----"
		    + orderNo);
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		Agent userInfo = userAgent;
		boolean isNewOrder = false;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist 验证订单是否已经存在
			orderDetails = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			// 如果订单不存在,往数据库加一条新的订单
			boolean f = !isPayByBank
			    && (orderDetails == null || orderDetails.getId() < 1);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>如果订单 " + orderNo
			    + " 不存在,往数据库加一条新的订单 这个地方-----" + f);
			if (f)
			{
				// create the order details if this order not exist
				orderDetails = appendOrder(map, userInfo, url, false);
				isNewOrder = true;
			}
			else
			{
				if (isPayByBank)
				{
					userInfo = orderDetails.getAgent();
				}
				else
				{
					userInfo = userAgent;
					orderDetails.setAgent(userInfo);
					transactionDAO.updateOrderDetails(orderDetails);
				}
			}
			// System.out.println("userInfo.email = "+userInfo.getEmail()+"
			// userAgent.email"+userAgent.getEmail());
			// royalty fee
			f = isNewOrder
			    || (!isNewOrder && Transaction.STATUS_3 != orderDetails.getStatus());

			if (f)
			{
				// if (validateAmount(userAgent, paymentPrice)) {
				//开始分润**********
				String returnParameter = guaranteePayment(map, orderDetails, userInfo,
				    sysDate, gatewayMessage);
				// 分润结束*********
				if (returnParameter.equals(gatewayMessage
				    .getTxn_Result_Account_Balance_Not_Enough())) { return null; }
				if (returnParameter != null && returnParameter.length() > 0)
				{
					// return success
					// update action log status
					if (!isNewOrder)
					{
						ActionLog actionLog = actionLogDAO
						    .queryActionLogByOrderId(orderDetails.getId());
						actionLog.setStatus(new Long(1));
						actionLogDAO.update(actionLog);
					}
					// patch up redirect url
					Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
					// patch up redirect url
					returnURL = UtilURL.directPayRedirectUrl(map, String
					    .valueOf(orderDetails.getId()), orderDetails.getOrderDetailsNo(),
					    userInfo, com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
					    strSysDate, coterie.getSignKey(), encode);

					// set action log
					ActionLog actionLog = new ActionLog();
					actionLog.setStatus(new Long(1));
					actionLog.setLogDate(sysDate);
					actionLog.setContent(map.get("return_url") + "?" + returnURL[1]);
					actionLog.setOrderDetails(orderDetails);
					actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
					actionLogDAO.save(actionLog);
				}
				else
				{
					System.out.println(">>>>>>>>>>>>>>>>>>>>> 分润返回结果为空!");
				}
			}
			else
			{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> isNewOrder=" + isNewOrder
				    + " >>>>>>>>>>> orderDetails.getStatus() = "
				    + orderDetails.getStatus());
			}
			// }
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return returnURL[0];
	}

	public String guarantee_payment_by_password(HashMap<String, String> map,
	    Agent userAgent, String url, String encode, GatewayMessage gatewayMessage)
	    throws Exception
	{

		String orderNo = map.get("out_trade_no");
		System.out
		    .println(">>>>>>>>>>>>>>>>>>>>>进入guarantee_payment_by_password-----"
		        + orderNo);
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		Agent userInfo = userAgent;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist 验证订单是否已经存在
			orderDetails = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			if (orderDetails != null && orderDetails.getId() > 0)
			{
				// 开始分润**********
				String returnParameter = guaranteePaymentByPassword(map, orderDetails,
				    userInfo, sysDate, gatewayMessage);
				// 分润结束**********
				
				if (returnParameter != null && returnParameter.length() > 0)
				{
					// patch up redirect url
					Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
					// patch up redirect url
					returnURL = UtilURL.directPayRedirectUrl(map, String
					    .valueOf(orderDetails.getId()), orderDetails.getOrderDetailsNo(),
					    userInfo, com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
					    strSysDate, coterie.getSignKey(), encode);

					// set action log
					ActionLog actionLog = new ActionLog();
					actionLog.setStatus(new Long(1));
					actionLog.setLogDate(sysDate);
					actionLog.setContent(map.get("return_url") + "?" + returnURL[1]);
					actionLog.setOrderDetails(orderDetails);
					actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
					actionLogDAO.save(actionLog);
				}
				else
				{
					System.out.println(">>>>>>>>>>>>>>>>>>>>> 分润返回结果为空!");
				}
			}
			else
			{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> orderDetails为空");
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return returnURL[0];
	}
	/**
	 * 即时到账支付
	 */
	public String directPayment(HashMap<String, String> map,
	    OrderDetails orderDetails, Agent userAgent, Timestamp sysDate,
	    GatewayMessage gatewayMessage) throws Exception
	{
		System.out.println(">>>>>>>>>>>>>>> 分润开始");
		System.out.println(">>>>>>>>支付类型："+map.get("payment_type"));
		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
		List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("royalty_parameters"));
		List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("buyer_parameters"));
		// temp parameter
		BigDecimal tempPay = new BigDecimal("0");
		BigDecimal finalFee = new BigDecimal("0");
		
		
		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId()); 
		// 查询平台帐号,根据partner查出平台帐号
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());
		/************买家信息集合不为空*************/
		if (listBuyer != null && listBuyer.size() > 0)
		{
			for (String[] buyerParameters : listBuyer)
			{
				Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
				BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
				if(buyerAgent!=null){
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
					if (!Gateway.equals(ab.getAllowBalance(), buyPrice))
					{
						sb.append(buyerParameters[0]);
						sb.append("^");
						sb.append(buyerParameters[1]);
						sb.append("^");
						sb.append(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough());
					}
					else
					{
						buildTransaction(orderDetails, buyerAgent, avouchAgent,
						Transaction.TYPE_80, buyPrice.setScale(2,
						BigDecimal.ROUND_HALF_UP), null, Transaction.STATUS_3);
						sb.append(gatewayMessage.getSuccess());
					}
				}
			}
		}

		// 先检查是否够钱付款
		Agent paymentAgent = agentDAO.getAgentById(userAgent.getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab1 = agentDAO.getAgentBalance(paymentAgent.getId());
		BigDecimal paymentAgent_allowBalance = ab1.getAllowBalance();
		
		//BigDecimal paymentAgent_allowBalance = paymentAgent.getAllowBalance();
		_GatewayBizImp.count++;
		
		if (!Gateway.equals(paymentAgent_allowBalance, totalFee)) { 
			return sb.append(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough()).toString(); 
		}
		/*****买家付款的金额打到平台不用冻结*****/
		if (map.get("payment_type").equals("3"))  //判断是否担保交易
		{
			// 先把买家付款的金额打到平台帐号上
			buildTransaction(orderDetails, paymentAgent, avouchAgent,
			Transaction.TYPE_120, totalFee, null, Transaction.STATUS_3);
		}else{
			// 先把买家付款的金额打到平台帐号上
			buildTransaction(orderDetails, paymentAgent, avouchAgent,
			Transaction.TYPE_80, totalFee, null, Transaction.STATUS_3);
		}
		
		// 收取平台费用,费用 = 交易金额 * 费率 / 100
		BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
		    new BigDecimal("100"));
		BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal tempTotalFee = new BigDecimal("0");

		// 把平台帐号的钱分润到各个需要分润的帐号上
		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			for (String[] royaltyParameters : listRoyalty)
			{
				Agent resultAgent = agentDAO.getAgentByEmail(royaltyParameters[0]);
				BigDecimal tempFee = new BigDecimal(royaltyParameters[1].trim());
				tempTotalFee = tempTotalFee.add(tempFee);
				sb.append(royaltyParameters[0]);
				sb.append("^");
				sb.append(royaltyParameters[1]);
				sb.append("^");
				sb.append(royaltyParameters[2]);
				sb.append("^");
				try
				{
					if (resultAgent != null && resultAgent.getId() > 0
					    && tempFee.compareTo(new BigDecimal("0")) > 0)
					{
						// 获取AgentBalance对象（用于获取金额）
						//AgentBalance ab = agentDAO.getAgentBalance(avouchAgent.getId());
						//BigDecimal avouchAgent_allowBalance = ab.getAllowBalance();
						//BigDecimal avouchAgent_allowBalance = avouchAgent.getAllowBalance(); 
					/*	if (ab.getAllowBalance() != null
						    && ab.getAllowBalance().compareTo(tempFee) >= 0)
							
						{*/
							if (map.get("payment_type").equals("3"))
							{
								buildNotAllowTransaction(orderDetails, avouchAgent, resultAgent,
								    Transaction.TYPE_120, tempFee, null, Transaction.STATUS_3);
								sb.append(gatewayMessage.getSuccess());
							}
							else
							{
								buildTransaction(orderDetails, avouchAgent, resultAgent,
								    Transaction.TYPE_80, tempFee, null, Transaction.STATUS_3);
								sb.append(gatewayMessage.getSuccess());
							}
						/*}
						else
						{
							System.out.println("--------------------ab.getAllowBalance() != null--");
							// 账户余额不足,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
							sb.append(gatewayMessage
							    .getTxn_Result_Account_Balance_Not_Enough());
							tempPay = tempPay.add(tempFee);
						}*/
					}
					else
					{
						// 不存在该账户,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
						System.out.println("---resultAgent != null--");
						sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
						tempPay = tempPay.add(tempFee);
					}
				}
				finally
				{
					sb.append("|");
				}

			}
		}
		else
		{
			// 如果没有分润参数royalty_parameters,分润方就默认为卖家
			Agent resultAgent = null;
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			tempTotalFee = new BigDecimal(map.get("total_fee")).subtract(flatFee);
			BigDecimal tempFee = new BigDecimal(map.get("total_fee"))
			    .subtract(flatFee);

			sb.append(map.get("seller_email"));
			sb.append("^");
			sb.append(tempFee);
			sb.append("^");
			sb.append("^");
			try
			{
				if (resultAgent != null && resultAgent.getId() > 0)
				{
					if (map.get("payment_type").equals("3"))
					{
						buildNotAllowTransaction(orderDetails, avouchAgent, resultAgent,
						    Transaction.TYPE_120, tempFee, null, Transaction.STATUS_3);
						sb.append(gatewayMessage.getSuccess());
					}
					else
					{
						buildTransaction(orderDetails, avouchAgent, resultAgent,
						    Transaction.TYPE_80, tempFee, null, Transaction.STATUS_3);
						sb.append(gatewayMessage.getSuccess());
					}
				}
				else
				{
					sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
					tempPay = tempFee;
				}
			}
			finally
			{
				sb.append("|");
			}
		}

		// pay to temp account if poyalty account error
		// 如果分润的时候,有些分润帐号错误或者某些原因分润失败的话,把金额打到临时帐号上
		System.out.println("分润失败的金额tempPay："+tempPay);
		if (tempPay.doubleValue() > 0)
		{
			if (map.get("payment_type").equals("3"))
			{
				buildNotAllowTransaction(orderDetails, avouchAgent, tempAgent,
				    Transaction.TYPE_120, tempPay, null, Transaction.STATUS_3);
			}
			else
			{
				// Agent tempAgent = agentDAO.getAgentById(tempAccount);
				buildTransaction(orderDetails, avouchAgent, tempAgent,
				    Transaction.TYPE_80, tempPay, null, Transaction.STATUS_3);
			}
		}

	

		// 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
		BigDecimal ca = tempTotalFee.add(flatFee);
		if (ca.compareTo(totalFee) < 0)
		{
			finalFee = totalFee.subtract(ca.setScale(2, BigDecimal.ROUND_HALF_UP));
			System.out.println("分润和收取手续费剩下的金额:finalFee="+finalFee);
			if (map.get("payment_type").equals("3"))
			{
				buildNotAllowTransaction(orderDetails, avouchAgent, tempAgent,
				    Transaction.TYPE_120, finalFee, null, Transaction.STATUS_3);
			}
			else
			{
				buildTransaction(orderDetails, avouchAgent, tempAgent,
				Transaction.TYPE_80, finalFee, null, Transaction.STATUS_3);
			}
		}
		if (Constant.platRateAgent == null)
		{
			System.out.println("*******平台收费账号为空..");
		}
		else
		{
			System.out.println("*******平台收费账号不为空ID="
			    + Constant.platRateAgent.getId()+"平台费用："+flatFee);
		}
		if (map.get("payment_type").equals("3")){
			buildNotAllowTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
			    Transaction.TYPE_120, finalFee, null, Transaction.STATUS_3);
		
		}
		else{
			buildTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
			    Transaction.TYPE_80, flatFee, null, Transaction.STATUS_3);
		}

		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(sysDate);
		transactionDAO.updateOrderDetails(orderDetails);

		System.out.println(">>>>>>>>>>>>>>> 分润结束");
		return sb.substring(0, sb.length() - 1).toString();
	}

		
	public String guaranteePaymentByPassword(HashMap<String, String> map,
	    OrderDetails orderDetails, Agent userAgent, Timestamp sysDate,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>>>>>> 开始执行分润");
		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
		List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("royalty_parameters"));

		BigDecimal tempPay = new BigDecimal("0");
		BigDecimal finalFee = new BigDecimal("0");

		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId());
		// 查询平台帐号,根据partner查出平台帐号
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());

		// pay flat fee from partner's avouch account
		// 收取平台费用,费用 = 交易金额 * 费率 / 100
		BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
		    new BigDecimal("100"));
		BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal tempTotalFee = new BigDecimal("0");

		// 把平台帐号的钱分润到各个需要分润的帐号上
		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			for (String[] royaltyParameters : listRoyalty)
			{
				Agent resultAgent = agentDAO.getAgentByEmail(royaltyParameters[0]);
				BigDecimal tempFee = new BigDecimal(royaltyParameters[1].trim());
				tempTotalFee = tempTotalFee.add(tempFee);
				sb.append(royaltyParameters[0]);
				sb.append("^");
				sb.append(royaltyParameters[1]);
				sb.append("^");
				sb.append(royaltyParameters[2]);
				sb.append("^");
				try
				{
					if (resultAgent != null && resultAgent.getId() > 0
					    && tempFee.compareTo(new BigDecimal("0")) > 0)
					{
//						// 获取AgentBalance对象（用于获取金额）	
//						AgentBalance ab = agentDAO.getAgentBalance(avouchAgent.getId());
//						System.out.println("同步平台帐号金额:" + ab.getAllowBalance());
//						if (ab.getAllowBalance() != null
//						    && ab.getAllowBalance().compareTo(tempFee) >= 0)
//						{
							buildTransaction(orderDetails, avouchAgent, resultAgent,
							    Transaction.TYPE_130, tempFee, null, Transaction.STATUS_3);
							sb.append(gatewayMessage.getSuccess());
//						}
//						else
//						{
//							// 账户余额不足,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
//							sb.append(gatewayMessage
//							    .getTxn_Result_Account_Balance_Not_Enough());
//							tempPay = tempPay.add(tempFee);
//						}
					}
					else
					{
						// 不存在该账户,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
						sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
						tempPay = tempPay.add(tempFee);
					}
				}
				finally
				{
					sb.append("|");
				}
			}
		}
		// pay to temp account if poyalty account error
		// 如果分润的时候,有些分润帐号错误或者某些原因分润失败的话,把金额打到临时帐号上
		System.out.println("分润失败的钱tempPay："+tempPay);
		if (tempPay.doubleValue() > 0)
		{
			// Agent tempAgent = agentDAO.getAgentById(tempAccount);
			buildTransaction(orderDetails, avouchAgent, tempAgent,
			    Transaction.TYPE_130, tempPay, null, Transaction.STATUS_3);
		}

		if (Constant.platRateAgent == null)
		{
			System.out.println("*********平台收费账号为空..");
		}
		else
		{
			System.out.println("*********平台收费账号不为空ID="
			    + Constant.platRateAgent.getId());
		}

		// 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
		BigDecimal ca = tempTotalFee.add(flatFee);
		if (ca.compareTo(totalFee) < 0)
		{
			finalFee = totalFee.subtract(ca.setScale(2, BigDecimal.ROUND_HALF_UP));
			System.out.println("分润和收取手续费剩下的金额:finalFee="+finalFee);
			buildTransaction(orderDetails, avouchAgent, tempAgent,
			    Transaction.TYPE_130, finalFee, null, Transaction.STATUS_3);
		}
		buildTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
		    Transaction.TYPE_130, flatFee, null, Transaction.STATUS_3);

		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(sysDate);
		transactionDAO.updateOrderDetails(orderDetails);

		System.out.println(">>>>>>>>>>>>>>> 分润结束");
		return sb.substring(0, sb.length() - 1).toString();
	}

	public String guaranteePayment(HashMap<String, String> map,
	    OrderDetails orderDetails, Agent userAgent, Timestamp sysDate,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>>>>>> 开始执行分润");
		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));

		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());

		// 先检查是否够钱付款
		Agent paymentAgent = agentDAO.getAgentById(userAgent.getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(paymentAgent.getId());
		if (!Gateway.equals(ab.getAllowBalance(), totalFee)) { return sb
		    .append(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough())
		    .toString(); }
		// 先把买家付款的金额打到平台帐号上
		try
		{
			buildTransaction(orderDetails, paymentAgent, avouchAgent,
			    Transaction.TYPE_130, totalFee, null, Transaction.STATUS_3);

			sb.append(avouchAgent.getLoginName());
			sb.append("^");
			sb.append(totalFee);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			sb.append("|");
		}
		// pay flat fee from partner's avouch account
		// 收取平台费用,费用 = 交易金额 * 费率 / 100
		// BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
		// new BigDecimal("100"));
		// BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
		//
		// BigDecimal tempTotalFee = new BigDecimal("0");
		//
		// // 把平台帐号的钱分润到各个需要分润的帐号上
		// if (listRoyalty != null && listRoyalty.size() > 0)
		// {
		// for (String[] royaltyParameters : listRoyalty)
		// {
		// Agent resultAgent = agentDAO.getAgentByEmail(royaltyParameters[0]);
		// BigDecimal tempFee = new BigDecimal(royaltyParameters[1].trim());
		// tempTotalFee = tempTotalFee.add(tempFee);
		// sb.append(royaltyParameters[0]);
		// sb.append("^");
		// sb.append(royaltyParameters[1]);
		// sb.append("^");
		// sb.append(royaltyParameters[2]);
		// sb.append("^");
		// try
		// {
		// if (resultAgent != null && resultAgent.getId() > 0
		// && tempFee.compareTo(new BigDecimal("0")) > 0)
		// {
		// if (avouchAgent.getAllowBalance() != null
		// && avouchAgent.getAllowBalance().compareTo(tempFee) >= 0)
		// {
		// buildTransaction(orderDetails, avouchAgent, resultAgent,
		// Transaction.TYPE_80, tempFee, null, Transaction.STATUS_3);
		// sb.append(gatewayMessage.getSuccess());
		// }
		// else
		// {
		// // 账户余额不足,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
		// sb.append(gatewayMessage
		// .getTxn_Result_Account_Balance_Not_Enough());
		// tempPay = tempPay.add(tempFee);
		// }
		// }
		// else
		// {
		// // 不存在该账户,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
		// sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
		// tempPay = tempPay.add(tempFee);
		// }
		// }
		// finally
		// {
		// sb.append("|");
		// }
		//
		// }
		// }
		// else
		// {
		// // 如果没有分润参数royalty_parameters,分润方就默认为卖家
		// Agent resultAgent = null;
		// if (map.get("seller_id") != null
		// && !"".equals(map.get("seller_id").trim()))
		// {
		// resultAgent =
		// agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
		// .trim()));
		// }
		// else if (map.get("seller_email") != null)
		// {
		// resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
		// }
		//
		// tempTotalFee = new
		// BigDecimal(map.get("total_fee")).subtract(flatFee);
		// BigDecimal tempFee = new BigDecimal(map.get("total_fee"))
		// .subtract(flatFee);
		//
		// sb.append(map.get("seller_email"));
		// sb.append("^");
		// sb.append(tempFee);
		// sb.append("^");
		// sb.append("^");
		// try
		// {
		// if (resultAgent != null && resultAgent.getId() > 0)
		// {
		// buildTransaction(orderDetails, avouchAgent, resultAgent,
		// Transaction.TYPE_80, tempFee, null, Transaction.STATUS_3);
		// sb.append(gatewayMessage.getSuccess());
		// }
		// else
		// {
		// sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
		// tempPay = tempFee;
		// }
		// }
		// finally
		// {
		// sb.append("|");
		// }
		// }
		//
		// // pay to temp account if poyalty account error
		// // 如果分润的时候,有些分润帐号错误或者某些原因分润失败的话,把金额打到临时帐号上
		// if (tempPay.doubleValue() > 0)
		// {
		// // Agent tempAgent = agentDAO.getAgentById(tempAccount);
		// buildTransaction(orderDetails, avouchAgent, tempAgent,
		// Transaction.TYPE_80, tempPay, null, Transaction.STATUS_3);
		// }
		//
		// if (Constant.platRateAgent == null)
		// {
		// System.out.println("*********平台收费账号为空..");
		// }
		// else
		// {
		// System.out.println("*********平台收费账号不为空ID="
		// + Constant.platRateAgent.getId());
		// }
		//
		// // 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
		// BigDecimal ca = tempTotalFee.add(flatFee);
		// if (ca.compareTo(totalFee) < 0)
		// {
		// finalFee = totalFee.subtract(ca.setScale(2,
		// BigDecimal.ROUND_HALF_UP));
		//
		// buildTransaction(orderDetails, avouchAgent, tempAgent,
		// Transaction.TYPE_80, finalFee, null, Transaction.STATUS_3);
		// }
		// buildTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
		// Transaction.TYPE_80, flatFee, null, Transaction.STATUS_3);

		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(sysDate);
		transactionDAO.updateOrderDetails(orderDetails);

		System.out.println(">>>>>>>>>>>>>>> 分润结束");
		return sb.substring(0, sb.length() - 1).toString();
	}

	/**
	 * 退款参数验证方法
	 */
	public String validateRefund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>退款验证-------");
		try{
			String message = AnalyseParameter.validateRefundParameter(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			String partner = map.get("partner");
			// validate partner 验证partner是否存在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
			
			/*** 验证外部批次号****/
			OrderDetails o = transactionDAO.queryOrderLikeOrderNo(map.get("batch_no").trim());
			if (o != null && o.getTransactions().size() > 0) {
				return gatewayMessage.getDuplicate_Batch_No();//重复的外部批次号
			}
			
			if(map.get("refund_type")==null){
				return gatewayMessage.getREFUND_TYPE_NOTNULL(); //退款类型不能为空
			}
			if(!AnalyseParameter.checkType(map.get("refund_type"))){
				return gatewayMessage.getREFUND_TYPE_ERROR(); //退款类型参数格式错误
			}
			
			// detail date verify 验证数据集参数
			List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
			    .get("detail_data"));
			if (listRefund == null || listRefund.size() < 1) { return gatewayMessage
			    .getDetail_Data_Format_Error(); }
			// transaction verify
			OrderDetails order = null;
			BigDecimal tempFee = new BigDecimal("0");
			for (int i = 0; i < listRefund.size(); i++)
			{
				String[] refund = listRefund.get(i);
				// 先验证交易退款信息,格式:原付款交易号^退交易金额^退款理由
				if (i == 0)
				{
					// order details 验证订单是否存在
					order = transactionDAO.queryOrderDetailByorderDetailsNo(refund[0].trim(), partner);
					if (order == null || order.getId() == 0)
					{
						return gatewayMessage.getTrade_Not_Exists(); //交易不存在
					}
					if (order != null && order.getStatus() == Transaction.STATUS_4) {
						return gatewayMessage.getTrade_Has_Closed(); //交易关闭
					}
					System.out.println("订单id:"+order.getId());
					System.out.println("平台账户id："+coterie.getAgent().getId());
					// 验证退款金额不能大于支付金额,这里根据订单ID和平台ID查出所有交易记录,取第一条,即第一条默认为卖家的信息
					// transactionDAO.queryTransactionByOrderAndFromAgent(order.getId(),avouchAccount);
					Transaction trans = transactionDAO
					    .queryTransactionByOrderAndFromAgent(order.getId(), coterie
					        .getAgent().getId());
					if (trans == null || trans.getToAgent() == null) { 
						return gatewayMessage.getTrade_Not_Exists(); // 交易不存在
					}
				}

				else if (i >= 1 && refund.length == 6)
				{
					// payout corterie verify
					AgentCoterie payoutCoterie = agentCoterieDAO
					    .getAgentCoterieByCoterieAndAgent(partner, refund[1], refund[0]);
					if (payoutCoterie == null || payoutCoterie.getId() == 0) { return gatewayMessage
					    .getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
					// incept corterie verify
					AgentCoterie inceptCoterie = agentCoterieDAO
					    .getAgentCoterieByCoterieAndAgent(partner, refund[3], refund[2]);
					if (inceptCoterie == null || inceptCoterie.getId() == 0) { return gatewayMessage
					    .getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
				}
				else
				{
					return gatewayMessage.getRefund_Sign_Etrance();
				}
			}

			// check temp account has enough balance
			if (tempFee.doubleValue() > 0)//??这个验证有什么用
			{
				// Agent resultAgent = agentDAO.getAgentById(tempAccount);
				Agent resultAgent = agentDAO.getAgentById(coterie.getTempAgent()
				    .getId());
				// 确认临时账号是否够钱扣
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab1 = agentDAO.getAgentBalance(resultAgent.getId());
				if (ab1.getAllowBalance() == null
				    || tempFee.doubleValue() > ab1.getAllowBalance()
				        .doubleValue()) { return gatewayMessage
				    .getRefund_Charge_Fee_Error(); }
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}
	
	/**
	 * 信用支付退款参数验证方法
	 */
	public String credit_validateRefund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			String message = AnalyseParameter.validateRefundParameter(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			String partner = map.get("partner");
			// validate partner 验证partner是否存在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
			
			/*** 验证外部批次号****/
			OrderDetails o = transactionDAO.queryOrderLikeOrderNo(map.get("batch_no").trim());
			if (o != null && o.getTransactions().size() > 0) {
				return gatewayMessage.getDuplicate_Batch_No();//重复的外部批次号
			}
			
			if(map.get("refund_type")==null){
				return gatewayMessage.getREFUND_TYPE_NOTNULL(); //退款类型不能为空
			}
			if(!AnalyseParameter.checkType(map.get("refund_type"))){
				return gatewayMessage.getREFUND_TYPE_ERROR(); //退款类型参数格式错误
			}

			// detail date verify 验证数据集参数
			List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
			    .get("detail_data"));
			if (listRefund == null || listRefund.size() < 1) { return gatewayMessage
			    .getDetail_Data_Format_Error(); }
			// transaction verify
			OrderDetails order = null;
			BigDecimal tempFee = new BigDecimal("0");
			for (int i = 0; i < listRefund.size(); i++)
			{
				String[] refund = listRefund.get(i);
				// 先验证交易退款信息,格式:原付款交易号^退交易金额^退款理由
				if (i == 0)
				{
					// order details 验证订单是否存在	
					order = transactionDAO.queryOrderDetailByorderDetailsNo(refund[0], partner);
					if (order == null || order.getId() == 0)
					{
						return gatewayMessage.getTrade_Not_Exists(); //交易部存在
					}
					
					if (order != null && order.getStatus() == Transaction.STATUS_4) { return gatewayMessage
					    .getTrade_Has_Closed(); }
					
					//根据订单id和平台账户id查询明细
					Transaction trans = transactionDAO
					    .queryTransactionByOrderAndFromAgent(order.getId(), coterie
					        .getAgent().getId());
					if (trans == null || trans.getToAgent() == null) { return gatewayMessage
					    .getTrade_Not_Exists(); // 交易不存在
					}
					
					if (trans != null && trans.getStatus() == Transaction.STATUS_4) { 
						return gatewayMessage.getTrade_Has_Closed();   //交易已关闭
					}
				}

				else if (i >= 1 && refund.length == 6)
				{
					// payout corterie verify
					AgentCoterie payoutCoterie = agentCoterieDAO
					    .getAgentCoterieByCoterieAndAgent(partner, refund[1], refund[0]);
					if (payoutCoterie == null || payoutCoterie.getId() == 0) { return gatewayMessage
					    .getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
					// incept corterie verify
					AgentCoterie inceptCoterie = agentCoterieDAO
					    .getAgentCoterieByCoterieAndAgent(partner, refund[3], refund[2]);
					if (inceptCoterie == null || inceptCoterie.getId() == 0) { return gatewayMessage
					    .getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
				}
				else
				{
					return gatewayMessage.getRefund_Sign_Etrance();
				}
			}

			// check temp account has enough balance
			if (tempFee.doubleValue() > 0)
			{
				// Agent resultAgent = agentDAO.getAgentById(tempAccount);
				Agent resultAgent = agentDAO.getAgentById(coterie.getTempAgent()
				    .getId());
				// 确认临时账号是否够钱扣
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(resultAgent.getId());
				if (ab.getAllowBalance() == null
				    || tempFee.doubleValue() > ab.getAllowBalance()
				        .doubleValue()) { return gatewayMessage
				    .getRefund_Charge_Fee_Error(); }
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}
	
	
	/**
	 * 普通退款订单方法
	 */
	public String refund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>即时到账退款订单方法开始");
		Timestamp sysDate = null;
		OrderDetails payOrder = null;
		ActionLog actionLog = null;
		String result = null;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
			    .getDateString("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
			// 调用方法获取退款交易集合
			List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
			    .get("detail_data"));
			// 交易退款信息,格式:原付款交易号^退交易金额^退款理由
			String[] refundInfo = listRefund.get(0);
			//获取订单
			payOrder = transactionDAO.queryOrderDetailByorderDetailsNo(refundInfo[0], map
			    .get("partner"));
			//账户在payorder这条支付交易中退款的所有交易
			List freezeOrders = transactionDAO.queryOrderLikeOrderDetailsNo(payOrder.getOrderDetailsNo());
			long tranType = 0;
			if(payOrder.getOrderDetailsType()==1){  //设置退款产生的明细类型
				tranType = Transaction.TYPE_81;
			}else if(payOrder.getOrderDetailsType()==5){
				tranType = Transaction.TYPE_121;
			}
			// 根据partner查出平台帐号
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			// 根据订单Id 查出交易信息
			Transaction trans = transactionDAO.queryTransactionByOrderAndFromAgent(
			    payOrder.getId(), coterie.getAgent().getId());
			
			// add new order
			OrderDetails refundOrder = new OrderDetails();
			String orderNo = map.get("batch_no")+"~"+payOrder.getOrderDetailsNo();
			System.out.println("orderNo:"+orderNo);
			refundOrder.setShopName(payOrder.getShopName());
			refundOrder.setDetailsContent(payOrder.getDetailsContent());
			refundOrder.setPartner(map.get("partner"));
			refundOrder.setOrderNo(orderNo);
			refundOrder.setEmailPrice(payOrder.getEmailPrice());
			
			System.out.println("refund_type:"+map.get("refund_type"));//退款类型
			long orderType = OrderDetails.getOrderDetailsType(
				Long.parseLong(map.get("refund_type"))); //根据退款类型获取订单支付类型
			refundOrder.setOrderDetailsType(orderType);
			
			refundOrder.setBuyType(payOrder.getBuyType());
			refundOrder.setAgent(trans.getToAgent());
			refundOrder.setStatus(Transaction.STATUS_11);
			refundOrder.setPaymentPrice(BigDecimal.valueOf(Double
			    .parseDouble(refundInfo[1])));
			refundOrder.setShopPrice(BigDecimal.valueOf(Double
			    .parseDouble(refundInfo[1])));
			// set quantity
			refundOrder.setShopTotal(payOrder.getShopTotal());

			refundOrder.setCreateDate(sysDate);
			refundOrder.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			refundOrder.setFinishDate(sysDate);
			transactionDAO.addOrderDetails(refundOrder);

			// ************开始退款**********
			result = directRefund(map, refundOrder, payOrder, coterie,
			    gatewayMessage, url,freezeOrders,tranType);
			
			// add log
			actionLog = new ActionLog();
			actionLog.setStatus(new Long(1));
			actionLog.setLogDate(sysDate);
			actionLog.setContent(url);
			actionLog.setOrderDetails(refundOrder);
			actionLog.setLogType(ActionLog.LOG_TYPE_REFUND_REQUEST);
			actionLogDAO.save(actionLog);
			
			// set action log
			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("notify_url") + "?" + result);
			aLog.setOrderDetails(refundOrder);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);
			actionLogDAO.save(aLog);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>即时到账退款订单方法结束");
		return result;
	}

	/**
	 * 信用支付退款订单方法
	 */
	public String credit_refund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>信用支付退款订单方法开始");
		Timestamp sysDate = null;
		OrderDetails payOrder = null;
		ActionLog actionLog = null;

		String result = null;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
			    .getDateString("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
			// long orderId = Long.parseLong(map.get("batch_no"));

			// 调用方法获取退款交易集合
			List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
			    .get("detail_data"));
			// 交易退款信息,格式:原付款交易号^退交易金额^退款理由
			String[] refundInfo = listRefund.get(0);
			String temp = listRefund.get(0)[2];
			// 获取交易号
			String outTradeNo = temp.substring(temp.lastIndexOf(":") + 1);
			// 根据付款交易号查出订单信息
			payOrder = transactionDAO.queryOrderDetailByorderDetailsNo(refundInfo[0],
			    map.get("partner"));
			//账户在payorder这条支付交易中退款的所有交易
			List freezeOrders = transactionDAO.queryOrderLikeOrderDetailsNo(payOrder.getOrderDetailsNo());
			long tranType = Transaction.TYPE_181;//设置退款产生的明细类型
			// 根据partner查出平台帐号
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			// 根据订单Id 查出交易信息
			Transaction trans = transactionDAO.queryTransactionByOrderAndFromAgent(
			    payOrder.getId(), coterie.getAgent().getId());

			// add new order
			OrderDetails refundOrder = new OrderDetails();
			String orderNo = map.get("batch_no")+"~"+payOrder.getOrderDetailsNo();
			System.out.println("orderNo:"+orderNo);
			refundOrder.setShopName(payOrder.getShopName());
			refundOrder.setDetailsContent(payOrder.getDetailsContent());
			refundOrder.setPartner(map.get("partner"));
			refundOrder.setOrderNo(orderNo);
			refundOrder.setEmailPrice(payOrder.getEmailPrice());
			
			System.out.println("refund_type:"+map.get("refund_type"));//退款类型
			long orderType = OrderDetails.getOrderDetailsType(
				Long.parseLong(map.get("refund_type"))); //根据退款类型获取订单支付类型
			refundOrder.setOrderDetailsType(orderType);
			
			refundOrder.setBuyType(payOrder.getBuyType());
			refundOrder.setAgent(trans.getToAgent());// ----------------
			refundOrder.setStatus(Transaction.STATUS_11);
			refundOrder.setPaymentPrice(BigDecimal.valueOf(Double
			    .parseDouble(refundInfo[1])));
			refundOrder.setShopPrice(BigDecimal.valueOf(Double
			    .parseDouble(refundInfo[1])));
			// set quantity
			refundOrder.setShopTotal(payOrder.getShopTotal());

			refundOrder.setCreateDate(sysDate);
			refundOrder.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			refundOrder.setFinishDate(sysDate);
			// 保存订单信息
			transactionDAO.addOrderDetails(refundOrder); 

			// ***********开始退款**********
			// 信用支付,调用信用支付退款
			result = directRefundForCredit(map, refundOrder, payOrder, coterie,
			    gatewayMessage, url,freezeOrders,tranType);
			// 保存日志
			actionLog = new ActionLog();
			actionLog.setStatus(new Long(1));
			actionLog.setLogDate(sysDate);
			actionLog.setContent(url);
			actionLog.setOrderDetails(refundOrder);
			actionLog.setLogType(ActionLog.LOG_TYPE_REFUND_REQUEST);
			actionLogDAO.save(actionLog);


			// set action log
			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("notify_url") + "?" + result);
			aLog.setOrderDetails(refundOrder);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);
			actionLogDAO.save(aLog);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>即时到账退款订单方法结束");
		return result;
	}


	/**
	 * (即时到账/担保冻结)支付退款方法
	 */
	public String directRefund(HashMap<String, String> map,
	    OrderDetails refundOrder, OrderDetails payOrder, Coterie coterie,
	    	GatewayMessage gatewayMessage, String url,List freezeOrders,long tranType) throws Exception
	    {
		System.out.println(">>>>>>>>>>开始退款");
		StringBuffer sb = new StringBuffer();
		List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
		    .get("detail_data"));
		
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId()); // 合作伙伴平台帐号
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId()); // 合作伙伴平台帐号
		Agent buyerAgent = payOrder.getAgent();  //买家账号

		BigDecimal rateAmount = new BigDecimal("0");  //退手续费
		BigDecimal refundAmount = new BigDecimal("0"); //本次退给买家的金额
		BigDecimal hasRefundAmoutBuy = new BigDecimal(0);  //获取退给买家的总金额（已退款加本次退款）
		BigDecimal hasRefundAmoutAvouch = new BigDecimal(0);  //获取所有分润账户退给平台的总金额（已退款加本次退款）
		boolean isSucc = false;
		for (int i = 0; i < listRefund.size(); i++)
		{
			String[] refund = listRefund.get(i);
			Agent payoutAgent = null;  //退款转出人
			Agent inceptAgent = null;  //退款转入人
			BigDecimal amount = new BigDecimal("0"); //退款金额
			
			String mark = null;
			boolean isRefund = false;
			boolean isSame = false; // 判断退款的帐号是否是一样的
			boolean isFlag = false; //退款金额是否大于0
			boolean isRefundChargeFee = false; // 判断退收费金额是否有误
			if (i == 0)
			{ // 交易退款信息,格式:原付款交易号^退交易金额^退款理由
				// refund if refund fee >0
				if (refund[1] != null && !"".equals(refund[1].trim()))
				{
					amount = new BigDecimal(refund[1].trim());
					refundAmount = new BigDecimal(refund[1].trim());
					payoutAgent = avouchAgent; // 应该是从平台帐号退回给买家
					inceptAgent = buyerAgent; // 是原来付款的买家
					mark = refund[2];
					if (amount.compareTo(new BigDecimal("0")) > 0)
					{
						isFlag = true;
					}
					if(null!=freezeOrders&&freezeOrders.size()>0){
						hasRefundAmoutBuy = transactionDAO.getAlreadyAmount(
							freezeOrders, payoutAgent.getId(), inceptAgent.getId(),
								Transaction.STATUS_11, tranType);// 账户已退款的金额
					}
			
					System.out.println("交易金额paymentPrice："+payOrder.getPaymentPrice());
					System.out.println("买家已退款金额hasRefundAmoutBuy："+hasRefundAmoutBuy);
					System.out.println("本次要退款金额amount:"+amount);
					if (hasRefundAmoutBuy.add(amount).compareTo(payOrder.getPaymentPrice())==1)
					{
						isRefundChargeFee = true;
					}
				}
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
			}
			else if (i == 1 && refund.length == 6)
			{ // 分润退款信息,格式:
				// 转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由
				if (refund[0] != null && !refund[0].equals("") && refund[2] != null
				    && !refund[2].equals(""))
				{
					if (refund[0].equals(refund[2]))
					{
						isSame = true;
					}
				}
				 if(refund[1]!=null && !refund[1].equals("") &&
					refund[3]!=null&&!refund[3].equals("")){
					 if(refund[1].equals(refund[3])){
						 isSame = true;
					 }
				 }
				if (refund[4] != null && !"".equals(refund[4].trim()))
				{ // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());
					if (amount.doubleValue() > 0)
					{
						// get the payout agent info
						if (refund[1] != null && !"".equals(refund[1].trim()))
						{
							payoutAgent = agentDAO.getAgentById(Long.parseLong(refund[1]
							    .trim()));
						}
						else
						{
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim()))
						{
							inceptAgent = agentDAO.getAgentById(Long.parseLong(refund[3]
							    .trim()));
						}
						else
						{
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
						long agentId = payoutAgent.getId();
		
						BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
						    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
						    Transaction.STATUS_3);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						System.out.println("退款账户email："+payoutAgent.getLoginName());
						System.out.println("账户分润金额hasPayAmout:"+hasPayAmout);
						BigDecimal hasRefundAmout = new BigDecimal(0);
						
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefundAmout = transactionDAO.getAlreadyAmount(
								freezeOrders, agentId, avouchAgent.getId(),
									Transaction.STATUS_11, tranType);// 账户已冻结的金额
						}
						System.out.println("账户已退款金额hasRefundAmout："+hasRefundAmout);
						System.out.println("本次要退款金额amount:"+amount);
						if (hasRefundAmout.add(amount)
						    .compareTo(hasPayAmout) == 1)
						{
							isRefundChargeFee = true;
						}
					}
				}

				sb.append("$");
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
				sb.append(refund[2]);
				sb.append("^");
				sb.append(refund[3]);
				sb.append("^");
				sb.append(refund[4]);
				sb.append("^");
				mark = refund[5];

			}
			else if (i > 1 && refund.length == 6)
			{ // 分润退款信息,格式:
				// 转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由
				if (refund[0] != null && !refund[0].equals("") && refund[2] != null
				    && !refund[2].equals(""))
				{
					if (refund[0].equals(refund[2]))
					{
						isSame = true;
					}
				}
				if(refund[1]!=null && !refund[1].equals("") &&
					refund[3]!=null&&!refund[3].equals("")){
					 if(refund[1].equals(refund[3])){
						 isSame = true;
					 }
				 }
				if (refund[4] != null && !"".equals(refund[4].trim()))
				{ // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());
					if (amount.doubleValue() > 0)
					{
						// get the payout agent info
						if (refund[1] != null && !"".equals(refund[1].trim()))
						{
							payoutAgent = agentDAO.getAgentById(Long.parseLong(refund[1]
							    .trim()));
						}
						else
						{
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim()))
						{
							inceptAgent = agentDAO.getAgentById(Long.parseLong(refund[3]
							    .trim()));
						}
						else
						{
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
				
						long agentId = payoutAgent.getId();
						BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
						    .getOrderDetailsNo(), avouchAgent.getId(), agentId,  //avouchAgent.getId() 这里根据平台账号查
						    Transaction.STATUS_3);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						System.out.println("退款账户email："+payoutAgent.getLoginName());
						System.out.println("账户分润金额hasPayAmout:"+hasPayAmout);
						BigDecimal hasRefundAmout = new BigDecimal(0);
					
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefundAmout = transactionDAO.getAlreadyAmount(
								freezeOrders, payoutAgent.getId(), avouchAgent.getId(),
									Transaction.STATUS_11, tranType);// 账户已冻结的金额
						}
						System.out.println("账户已退款金额hasRefundAmout:"+hasRefundAmout);
						System.out.println("本次要退款金额amount:"+amount);
						if (hasRefundAmout.add(amount)
						    .compareTo(hasPayAmout) == 1)
						{
							isRefundChargeFee = true;
						}
					}
				}
				sb.append("|");
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
				sb.append(refund[2]);
				sb.append("^");
				sb.append(refund[3]);
				sb.append("^");
				sb.append(refund[4]);
				sb.append("^");
				mark = refund[5];
			}

			try
			{
				if (payoutAgent != null && payoutAgent.getId() > 0
				    && amount.compareTo(new BigDecimal("0")) > 0)
				{
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(payoutAgent.getId());
					if (ab.getAllowBalance() != null
					    && Gateway.equals(ab.getAllowBalance(), amount))
					{
						if (isSame)
						{
							sb.append(gatewayMessage.getSame_Account_No_Need_Process()); // 相同帐号不被处理
						}
						else
						{
							if (isRefundChargeFee)
							{
								sb.append(gatewayMessage.getRefund_Charge_Fee_Error()); // 退收费金额有误。
							}
							else
							{
								if (!isFlag)
								{ 
									// 参数的金额大于0,就不用付到平台,直接到下面打到原来买家的帐号上
									buildTransaction(refundOrder, payoutAgent, avouchAgent,
										tranType, amount, mark, Transaction.STATUS_11);
								}
								else
								{
									buildTransaction(refundOrder, payoutAgent, inceptAgent, // 这里是平台帐号直接打钱到原来的买家帐号
										tranType, amount, mark, Transaction.STATUS_11);
									isSucc=true;
								}
								sb.append(gatewayMessage.getSuccess());
								isRefund = true;
							}
						}
					}
					else
					{
						sb
						    .append(gatewayMessage
						        .getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
					}
				}
				else
				{
					if (amount.compareTo(new BigDecimal("0")) <= 0)
						sb.append(gatewayMessage.getZero_No_Need_Process()); // 退款金额为零，不予处理。
					else
						sb.append(gatewayMessage.getTxn_Result_No_Such_Account()); // 帐号不存在
				}
			}
			catch (Exception ex)
			{
				sb.append(gatewayMessage.getUnknow_Error()); // 未知异常
			}
			// refund to buyer from avouch account
			if (!isFlag)
			{
				if (isRefund || (!isRefund && amount.doubleValue() > 0))
				{
					if (avouchAgent.getId() != inceptAgent.getId())
					{ // 判断如果退款分润方已经是平台帐号了,就不用添加这条记录
						buildTransaction(refundOrder, avouchAgent, inceptAgent,
							tranType, amount, mark, Transaction.STATUS_11);
					}
				}
				if(isRefund){
					hasRefundAmoutAvouch = hasRefundAmoutAvouch.add(amount);//把分润账户退款的钱加到退给平台的总金额
				}
			}
		}

		// 先把钱门收费率帐号打钱到平台帐号,如果退款金额小于0,就不用执行这个操作
		if (refundAmount.doubleValue() > 0&&isSucc)
		{	
			hasRefundAmoutBuy = hasRefundAmoutBuy.add(refundAmount);//退给买家的总金额=已退+本次退款金额
			Agent platRateAgent = Constant.platRateAgent;// 钱门收费率帐号
			BigDecimal calc = refundAmount.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			rateAmount = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			System.out.println("收费账户："+platRateAgent.getLoginName());
			System.out.println("退收费金额："+rateAmount);
			buildTransaction(refundOrder, platRateAgent, avouchAgent,
				tranType, rateAmount, null, Transaction.STATUS_11);
		}
		
		//这是支付时把分润余留下的金额打给了平台临时账户上
		BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(
			payOrder.getOrderDetailsNo(), avouchAgent.getId(), 
				tempAgent.getId(), Transaction.STATUS_3);
		
	
		if(null!=freezeOrders&&freezeOrders.size()>0){
			BigDecimal alredyRefundAmout = new BigDecimal(0);
			alredyRefundAmout = transactionDAO.getAlreadyAmount(
				freezeOrders,avouchAgent.getId(),Transaction.STATUS_11
				, tranType);// 分润账户已退给平台的金额的金额
			hasRefundAmoutAvouch = hasRefundAmoutAvouch.add(alredyRefundAmout);//总退款金额=本次退+已退
		}
		
		if ((hasRefundAmoutBuy.compareTo(//如果退给买家的钱和分润账户退给平台的钱
			payOrder.getPaymentPrice())>=0)&&(hasPayAmout.add(//都等于交易金额就把交易关闭了
				hasRefundAmoutAvouch).compareTo(payOrder.getPaymentPrice())>=0)){
			payOrder.setStatus(Transaction.STATUS_4);
			payOrder.setFinishDate(DateUtil.getTimestamp(DateUtil.getDateString(
					"yyyy-MM-dd hh:mm:ss"),"yyyy-MM-dd hh:mm:ss"));
			transactionDAO.addOrderDetails(payOrder);
		}
		
		
		String service = "credit_refund_by_platform";
		String isSuccess = "T";
		// 这里修改一下,应该是回调原来的batch_no 回去,refundOrder.getOrderDetailsNo() 应该改为
		StringBuffer result = UtilURL.directRefundRedirectUrl(String
		    .valueOf(refundOrder.getId()), isSuccess, service, map.get("batch_no"),
		    String.valueOf(1), sb.toString(), DateUtil.getDateString(new Date(),
		        "yyyy-MM-dd HH:mm:ss"), coterie.getSignKey(),
		        map.get("refund_type"));
		System.out.println(">>>>>>>>>>退款结束");
		return result.toString();
	}

	/**
	 * 信用支付退款方法
	 */
	public String directRefundForCredit(HashMap<String, String> map,
	    OrderDetails refundOrder, OrderDetails payOrder, Coterie coterie,
	    GatewayMessage gatewayMessage, String url,List freezeOrders,long tranType) throws Exception
	{
		System.out.println(">>>>>>>>>>开始退款");
		StringBuffer sb = new StringBuffer();
		List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
		    .get("detail_data"));
		
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId()); // 合作伙伴平台帐号
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId()); // 合作伙伴平台帐号
		Agent buyerAgent = payOrder.getAgent();  //买家账号

		BigDecimal rateAmount = new BigDecimal("0");  //退手续费
		BigDecimal refundAmount = new BigDecimal("0"); //本次退给买家的金额
		BigDecimal hasRefundAmoutBuy = new BigDecimal(0);  //获取退给买家的总金额（已退款加本次退款）
		BigDecimal hasRefundAmoutAvouch = new BigDecimal(0);  //获取所有分润账户退给平台的总金额（已退款加本次退款）
		boolean isSucc = false;
		for (int i = 0; i < listRefund.size(); i++)
		{
			String[] refund = listRefund.get(i);
			Agent payoutAgent = null;  //退款转出人
			Agent inceptAgent = null;  //退款转入人
			BigDecimal amount = new BigDecimal("0"); //退款金额
			
			String mark = null;
			boolean isRefund = false;
			boolean isSame = false; // 判断退款的帐号是否是一样的
			boolean isFlag = false; //退款金额是否大于0
			boolean isRefundChargeFee = false; // 判断退收费金额是否有误
			if (i == 0)
			{ // 交易退款信息,格式:原付款交易号^退交易金额^退款理由
				// refund if refund fee >0
				if (refund[1] != null && !"".equals(refund[1].trim()))
				{
					amount = new BigDecimal(refund[1].trim());
					refundAmount = new BigDecimal(refund[1].trim());
					payoutAgent = avouchAgent; // 应该是从平台帐号退回给买家
					inceptAgent = buyerAgent; // 是原来付款的买家
					mark = refund[2];
					if (amount.compareTo(new BigDecimal("0")) > 0)
					{
						isFlag = true;
					}
					if(null!=freezeOrders&&freezeOrders.size()>0){
						hasRefundAmoutBuy = transactionDAO.getAlreadyAmount(
							freezeOrders, payoutAgent.getId(), inceptAgent.getId(),
								Transaction.STATUS_11, tranType);// 账户已退款的金额
					}
			
					System.out.println("交易金额paymentPrice："+payOrder.getPaymentPrice());
					System.out.println("买家已退款金额hasRefund："+hasRefundAmoutBuy);
					System.out.println("本次要退款金额amount:"+amount);
					if (hasRefundAmoutBuy.add(amount).compareTo(payOrder.getPaymentPrice())==1)
					{
						isRefundChargeFee = true;
					}
				}
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
			}
			else if (i == 1 && refund.length == 6)
			{ // 分润退款信息,格式:
				// 转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由
				if (refund[0] != null && !refund[0].equals("") && refund[2] != null
				    && !refund[2].equals(""))
				{
					if (refund[0].equals(refund[2]))
					{
						isSame = true;
					}
				}
				 if(refund[1]!=null && !refund[1].equals("") &&
					refund[3]!=null&&!refund[3].equals("")){
					 if(refund[1].equals(refund[3])){
						 isSame = true;
					 }
				 }
				if (refund[4] != null && !"".equals(refund[4].trim()))
				{ // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());
					if (amount.doubleValue() > 0)
					{
						// get the payout agent info
						if (refund[1] != null && !"".equals(refund[1].trim()))
						{
							payoutAgent = agentDAO.getAgentById(Long.parseLong(refund[1]
							    .trim()));
						}
						else
						{
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim()))
						{
							inceptAgent = agentDAO.getAgentById(Long.parseLong(refund[3]
							    .trim()));
						}
						else
						{
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
						long agentId = payoutAgent.getId();
		
						BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
						    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
						    Transaction.STATUS_11);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						System.out.println("退款账户email："+payoutAgent.getLoginName());
						System.out.println("账户分润金额hasPayAmout:"+hasPayAmout);
						BigDecimal hasRefundAmout = new BigDecimal(0);
						
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefundAmout = transactionDAO.getAlreadyAmount(
								freezeOrders, agentId, avouchAgent.getId(),
									Transaction.STATUS_11, tranType);// 账户已退款的金额
						}
						System.out.println("账户已退款金额hasRefundAmout："+hasRefundAmout);
						System.out.println("本次要退款金额amount:"+amount);
						if (hasRefundAmout.add(amount)
						    .compareTo(hasPayAmout) == 1)
						{
							isRefundChargeFee = true;
						}
					}
				}

				sb.append("$");
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
				sb.append(refund[2]);
				sb.append("^");
				sb.append(refund[3]);
				sb.append("^");
				sb.append(refund[4]);
				sb.append("^");
				mark = refund[5];

			}
			else if (i > 1 && refund.length == 6)
			{ // 分润退款信息,格式:
				// 转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由
				if (refund[0] != null && !refund[0].equals("") && refund[2] != null
				    && !refund[2].equals(""))
				{
					if (refund[0].equals(refund[2]))
					{
						isSame = true;
					}
				}
				if(refund[1]!=null && !refund[1].equals("") &&
					refund[3]!=null&&!refund[3].equals("")){
					 if(refund[1].equals(refund[3])){
						 isSame = true;
					 }
				 }
				if (refund[4] != null && !"".equals(refund[4].trim()))
				{ // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());
					if (amount.doubleValue() > 0)
					{
						// get the payout agent info
						if (refund[1] != null && !"".equals(refund[1].trim()))
						{
							payoutAgent = agentDAO.getAgentById(Long.parseLong(refund[1]
							    .trim()));
						}
						else
						{
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim()))
						{
							inceptAgent = agentDAO.getAgentById(Long.parseLong(refund[3]
							    .trim()));
						}
						else
						{
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
				
						long agentId = payoutAgent.getId();
						BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
						    .getOrderDetailsNo(), avouchAgent.getId(), agentId,  //avouchAgent.getId() 这里根据平台账号查
						    Transaction.STATUS_11);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						System.out.println("退款账户email："+payoutAgent.getLoginName());
						System.out.println("账户分润金额hasPayAmout:"+hasPayAmout);
						BigDecimal hasRefundAmout = new BigDecimal(0);
					
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefundAmout = transactionDAO.getAlreadyAmount(
								freezeOrders, payoutAgent.getId(), avouchAgent.getId(),
									Transaction.STATUS_11, tranType);// 账户已冻结的金额
						}
						System.out.println("账户已退款金额hasRefundAmout:"+hasRefundAmout);
						System.out.println("本次要退款金额amount:"+amount);
						if (hasRefundAmout.add(amount)
						    .compareTo(hasPayAmout) == 1)
						{
							isRefundChargeFee = true;
						}
					}
				}
				sb.append("|");
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
				sb.append(refund[2]);
				sb.append("^");
				sb.append(refund[3]);
				sb.append("^");
				sb.append(refund[4]);
				sb.append("^");
				mark = refund[5];
			}

			try
			{
				if (payoutAgent != null && payoutAgent.getId() > 0
				    && amount.compareTo(new BigDecimal("0")) > 0)
				{
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(payoutAgent.getId());
					if (ab.getAllowBalance() != null
					    && Gateway.equals(ab.getAllowBalance(), amount))
					{
						if (isSame)
						{
							sb.append(gatewayMessage.getSame_Account_No_Need_Process()); // 相同帐号不被处理
						}
						else
						{
							if (isRefundChargeFee)
							{
								sb.append(gatewayMessage.getRefund_Charge_Fee_Error()); // 退收费金额有误。
							}
							else
							{
								if (!isFlag)
								{ 
									// 参数的金额大于0,就不用付到平台,直接到下面打到原来买家的帐号上
									buildTransaction(refundOrder, payoutAgent, avouchAgent,
										tranType, amount, mark, Transaction.STATUS_11);
								}
								else
								{
									buildTransaction(refundOrder, payoutAgent, inceptAgent, // 这里是平台帐号直接打钱到原来的买家帐号
										tranType, amount, mark, Transaction.STATUS_11);
									isSucc=true;
								}
								sb.append(gatewayMessage.getSuccess());
								isRefund = true;
							}
						}
					}
					else
					{
						sb
						    .append(gatewayMessage
						        .getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
					}
				}
				else
				{
					if (amount.compareTo(new BigDecimal("0")) <= 0)
						sb.append(gatewayMessage.getZero_No_Need_Process()); // 退款金额为零，不予处理。
					else
						sb.append(gatewayMessage.getTxn_Result_No_Such_Account()); // 帐号不存在
				}
			}
			catch (Exception ex)
			{
				sb.append(gatewayMessage.getUnknow_Error()); // 未知异常
			}
			// refund to buyer from avouch account
			if (!isFlag)
			{
				if (isRefund || (!isRefund && amount.doubleValue() > 0))
				{
					if (avouchAgent.getId() != inceptAgent.getId())
					{ // 判断如果退款分润方已经是平台帐号了,就不用添加这条记录
						buildTransaction(refundOrder, avouchAgent, inceptAgent,
							tranType, amount, mark, Transaction.STATUS_11);
					}
				}
				if(isRefund){
					hasRefundAmoutAvouch = hasRefundAmoutAvouch.add(amount);//把分润账户退款的钱加到退给平台的总金额
				}
			}
		}

		// 先把钱门收费率帐号打钱到平台帐号,如果退款金额小于0,就不用执行这个操作
		if (refundAmount.doubleValue() > 0&&isSucc)
		{	
			hasRefundAmoutBuy = hasRefundAmoutBuy.add(refundAmount);//退给买家的总金额=已退+本次退款金额
			Agent platRateAgent = Constant.platRateAgent;// 钱门收费率帐号
			BigDecimal calc = refundAmount.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			rateAmount = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			System.out.println("收费账户："+platRateAgent.getLoginName());
			System.out.println("退收费金额："+rateAmount);
			buildTransaction(refundOrder, platRateAgent, avouchAgent,
				tranType, rateAmount, null, Transaction.STATUS_11);
		}
		
		//这是支付时把分润余留下的金额打给了平台临时账户上
		BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(
			payOrder.getOrderDetailsNo(), avouchAgent.getId(), 
				tempAgent.getId(), Transaction.STATUS_3);
		
	
		if(null!=freezeOrders&&freezeOrders.size()>0){
			BigDecimal alredyRefundAmout = new BigDecimal(0);
			alredyRefundAmout = transactionDAO.getAlreadyAmount(
				freezeOrders,avouchAgent.getId(),Transaction.STATUS_11
				, tranType);// 分润账户已退给平台的金额的金额
			hasRefundAmoutAvouch = hasRefundAmoutAvouch.add(alredyRefundAmout);//总退款金额=本次退+已退
		}
		
		System.out.println("退给买家的总金额hasRefundAmoutBuy："+hasRefundAmoutBuy);
		System.out.println("退给平台的总金额hasPayAmout+hasRefundAmoutAvouch："+hasPayAmout.add(hasRefundAmoutAvouch));
		if ((hasRefundAmoutBuy.compareTo(//如果退给买家的钱和分润账户退给平台的钱
			payOrder.getPaymentPrice())>=0)&&(hasPayAmout.add(//都等于交易金额就把交易关闭了
				hasRefundAmoutAvouch).compareTo(payOrder.getPaymentPrice())>=0)){
			payOrder.setStatus(Transaction.STATUS_4);
			payOrder.setFinishDate(DateUtil.getTimestamp(DateUtil.getDateString(
					"yyyy-MM-dd hh:mm:ss"),"yyyy-MM-dd hh:mm:ss"));
			transactionDAO.addOrderDetails(payOrder);
		}
		
		// 加两个参数
		String service = map.get("service");
		String isSuccess = "T";
		// 这里修改一下,应该是回调原来的batch_no 回去,refundOrder.getOrderDetailsNo() 应该改为
		StringBuffer result = UtilURL.directRefundRedirectUrl(String
		    .valueOf(refundOrder.getId()), isSuccess, service, map.get("batch_no"),
		    String.valueOf(1), sb.toString(), DateUtil.getDateString(new Date(),
		        "yyyy-MM-dd HH:mm:ss"), coterie.getSignKey(),
		        map.get("refund_type"));
		
		BigDecimal repayAmount = new BigDecimal("0");
		if(payOrder.getRepayAmount()!=null){
			repayAmount = payOrder.getRepayAmount(); //已还款金额
		}
		payOrder.setRepayAmount(refundAmount.add(repayAmount));  //退款金额加进还款金额里
		if((refundAmount.add(repayAmount)).compareTo
				(payOrder.getPaymentPrice())!=-1)//如果退款金额加上这条支付已还款金额不小于交易金额
			payOrder.setStatus(OrderDetails.STATUS_12);  //则修改这条信用支付的还款状态
		System.out.println(">>>>>>>>>>退款结束");
		return result.toString();
	}

		
	// *****************************payment for house property 支付房产
	// start****************** 开始
	public String paymentForNoAccount(HashMap<String, String> map, String encode,
	    GatewayMessage gatewayMessage) throws Exception
	{
		System.out
		    .println("---------------房产网分润开始 paymentForNoAccount ------------------");
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		Agent userAgent = null;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist
			orderDetails = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			userAgent = orderDetails.getAgent();
			// royalty fee
			String returnParameter = directPaymentNoAccount(map, orderDetails,
			    userAgent, gatewayMessage);
			if (returnParameter != null && returnParameter.length() > 0)
			{
				// return success
				// update action log status
				ActionLog actionLog = actionLogDAO.queryActionLogByOrderId(orderDetails
				    .getId());
				actionLog.setStatus(new Long(1));
				actionLogDAO.update(actionLog);
				// patch up redirect url
				Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
				// patch up redirect url
				returnURL = UtilURL.directPayRedirectUrl(map, String
				    .valueOf(orderDetails.getId()), orderDetails.getOrderDetailsNo(),
				    userAgent, com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
				    strSysDate, coterie.getSignKey(), encode);

				// set action log
				ActionLog log = new ActionLog();
				log.setStatus(new Long(1));
				log.setLogDate(sysDate);
				log.setContent(map.get("return_url") + "?" + returnURL[1]);
				log.setOrderDetails(orderDetails);
				log.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
				actionLogDAO.save(log);
			}
			System.out
			    .println("---------------房产网分润结束 paymentForNoAccount ------------------");
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return returnURL[0];
	}

	public String directPaymentNoAccount(HashMap<String, String> map,
	    OrderDetails orderDetails, Agent userAgent, GatewayMessage gatewayMessage)
	    throws Exception
	{
		System.out.println(">>>>>>>>>>>>>>> 开始执行房产网分润");
		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
		List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("royalty_parameters"));
		List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("buyer_parameters"));
		// temp parameter
		BigDecimal tempPay = new BigDecimal("0");
		BigDecimal finalFee = new BigDecimal("0");

		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId());
		// 查询平台帐号,根据partner查出平台帐号
		// Agent avouchAgent = agentDAO.getAgentById(avouchAccount);
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());

		// take the dealing balance to avouch account from the buyer account

		if (listBuyer != null && listBuyer.size() > 0)
		{
			for (String[] buyerParameters : listBuyer)
			{
				Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
				BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
				if(buyerAgent!=null){
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
					if (!Gateway.equals(ab.getAllowBalance(), buyPrice))
					{
						sb.append(buyerParameters[0]);
						sb.append("^");
						sb.append(buyerParameters[1]);
						sb.append("^");
						sb.append(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough());
					}
					else
					{
						buildTransaction(orderDetails, buyerAgent, avouchAgent,
						    Transaction.TYPE_80, buyPrice.setScale(2,
						        BigDecimal.ROUND_HALF_UP), null, Transaction.STATUS_3);
						sb.append(gatewayMessage.getSuccess());
					}
				}
			}
		}
		// 先把买家付款的金额打到平台帐号上
		// if (listRoyalty != null && listRoyalty.size() > 0){
		// buildTransaction(orderDetails, userAgent, avouchAgent,
		// Transaction.TYPE_4,
		// totalFee, null, Transaction.STATUS_3);
		// }

		// pay flat fee from partner's avouch account
		// 收取平台费用,费用 = 交易金额 * 费率 / 100
		BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
		    new BigDecimal("100"));
		BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal tempTotalFee = new BigDecimal("0");

		// 把平台帐号的钱分润到各个需要分润的帐号上
		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			for (String[] royaltyParameters : listRoyalty)
			{
				Agent resultAgent = agentDAO.getAgentByEmail(royaltyParameters[0]);
				BigDecimal tempFee = new BigDecimal(royaltyParameters[1].trim());
				tempTotalFee = tempTotalFee.add(tempFee);
				sb.append(royaltyParameters[0]);
				sb.append("^");
				sb.append(royaltyParameters[1]);
				sb.append("^");
				sb.append(royaltyParameters[2]);
				sb.append("^");
				try
				{
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(avouchAgent.getId());
					if (resultAgent != null && resultAgent.getId() > 0
					    && tempFee.compareTo(new BigDecimal("0")) > 0)
					{
						if (ab.getAllowBalance() != null
						    && ab.getAllowBalance().compareTo(tempFee) >= 0)
						{
							buildTransaction(orderDetails, avouchAgent, resultAgent,
							    Transaction.TYPE_80, tempFee, null, Transaction.STATUS_3);
							// sb.append(ExceptionConstant.SUCCESS);
							sb.append(gatewayMessage.getSuccess());
						}
						else
						{
							// sb.append(ExceptionConstant.TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH);
							sb.append(gatewayMessage
							    .getTxn_Result_Account_Balance_Not_Enough());
							tempPay = tempPay.add(tempFee);
						}
					}
					else
					{
						// sb.append(ExceptionConstant.TXN_RESULT_NO_SUCH_ACCOUNT);
						if (tempFee.compareTo(new BigDecimal("0")) <= 0)
							sb.append(gatewayMessage.getZero_No_Need_Process());
						else
							sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
						tempPay = tempPay.add(tempFee);
					}
				}
				finally
				{
					sb.append("|");
				}

			}
		}
		// else
		// {
		// // 如果没有分润参数royalty_parameters,分润方就默认为卖家
		// Agent resultAgent = null;
		// if (map.get("seller_id") != null
		// && !"".equals(map.get("seller_id").trim()))
		// {
		// resultAgent =
		// agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
		// .trim()));
		// }
		// else if (map.get("seller_email") != null)
		// {
		// resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
		// }
		//		
		// tempTotalFee =new BigDecimal(map.get("total_fee")).subtract(
		// flatFee);
		// BigDecimal tempFee =new BigDecimal(map.get("total_fee")).subtract(
		// flatFee);
		//			
		// sb.append(map.get("seller_email"));
		// sb.append("^");
		// sb.append(tempFee);
		// sb.append("^");
		// sb.append("^");
		// try
		// {
		// if (resultAgent != null && resultAgent.getId() > 0)
		// {
		// buildTransaction(orderDetails, avouchAgent, resultAgent,
		// Transaction.TYPE_4, tempFee, null, Transaction.STATUS_3);
		// sb.append(gatewayMessage.getSuccess());
		// }
		// else
		// {
		// sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
		// tempPay = tempFee;
		// }
		// }
		// finally
		// {
		// sb.append("|");
		// }
		// }

		// pay to temp account if poyalty account error
		// 如果分润的时候,有些分润帐号错误或者某些原因分润失败的话,把金额打到临时帐号上
		if (tempPay.doubleValue() > 0)
		{
			// Agent tempAgent = agentDAO.getAgentById(tempAccount);
			buildTransaction(orderDetails, avouchAgent, tempAgent,
			    Transaction.TYPE_80, tempPay, null, Transaction.STATUS_3);
		}

		if (Constant.platRateAgent == null)
		{
			System.out.println("*********平台收费账号为空..");
		}
		else
		{
			System.out.println("*********平台收费账号不为空ID="
			    + Constant.platRateAgent.getId());
		}

		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			// 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
			BigDecimal ca = tempTotalFee.add(flatFee);
			if (ca.compareTo(totalFee) < 0)
			{
				finalFee = totalFee.subtract(ca.setScale(2, BigDecimal.ROUND_HALF_UP));

				buildTransaction(orderDetails, avouchAgent, tempAgent,
				    Transaction.TYPE_80, finalFee, null, Transaction.STATUS_3);
			}
		}
		else
		{
			// 如果没有分润参数了,就把剩下来的钱打到临时帐号里面,这时候临时帐号的钱应该是平台帐号的钱-给收费帐号的钱
			buildTransaction(orderDetails, avouchAgent, tempAgent,
			    Transaction.TYPE_80, totalFee.subtract(flatFee).setScale(2,
			        BigDecimal.ROUND_HALF_UP), null, Transaction.STATUS_3);
			sb.append(gatewayMessage.getSuccess());
		}
		buildTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
		    Transaction.TYPE_80, flatFee, null, Transaction.STATUS_3);
		sb.append(gatewayMessage.getSuccess());

		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateOrderDetails(orderDetails);

		System.out.println(">>>>>>>>>>>>>>> 房产网分润结束");
		String returnStr = "";
		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			returnStr = sb.substring(0, sb.length() - 1).toString();
		}
		else
		{
			returnStr = sb.toString();
		}
		return returnStr;
	}

	// *****************************payment for house property
	// end****************** 结束
	public void buildTransaction(OrderDetails orderDetails, Agent fromAgent,
	    Agent toAgent, Long type, BigDecimal paymentPrice, String remark,
	    long status) throws Exception
	{
		try
		{
			// add the transaction
			Transaction transaction = new Transaction();
			transaction.setOrderDetails(orderDetails);
			// transaction.setNo(orderDetails.getOrderDetailsNo());
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setAmount(paymentPrice);
			transaction.setType(type);
			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			if (status == Transaction.STATUS_11)
			{
				transaction.setRefundsDate(new Timestamp(System.currentTimeMillis()));
				transaction.setRefundsStatus(status);
			}
			transaction.setMark(remark);
			if (transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
			    .doubleValue() != 0)
			{
				transactionDAO.saveTransaction(transaction);
				// deduction balance the from agent
				fromAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				fromAgent.setAllowBalance(ab.getAllowBalance().subtract(
				    paymentPrice));
				
				// agentDAO.update(fromAgent);
				// add balance for to agent
				toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getAllowBalance() != null)
				{
					toAgent.setAllowBalance(ab2.getAllowBalance().add(paymentPrice));
					/*
					 * toAgent.setBalance(BigDecimal.valueOf(toAgent
					 * .getBalance().doubleValue() + paymentPrice));
					 */
				}
				else
				{
					toAgent.setAllowBalance(paymentPrice);
					// toAgent.setBalance(BigDecimal.valueOf(paymentPrice));
				}
			}
			else
			{
				// 这里是等于0的transaction，不记录到数据库里。
				System.out.println("----------------old amount = "
				    + transaction.getAmount() + ",---------new amount="
				    + transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			// agentDAO.update(toAgent);
		}
		catch (Exception ex)
		{
			throw ex;
		}
		// return true;
	}

	// **********payment for house property***现金到用户冻结余额
	// end****************** 结束
	public void buildNotAllowTransaction(OrderDetails orderDetails,
	    Agent fromAgent, Agent toAgent, Long type, BigDecimal paymentPrice,
	    String remark, long status) throws Exception
	{
		try
		{
			// if (fromAgent.getAllowBalance() == null
			// || fromAgent.getAllowBalance().doubleValue() < paymentPrice) {
			// return false;
			// }
			// add the transaction
			Transaction transaction = new Transaction();
			transaction.setOrderDetails(orderDetails);
			// transaction.setNo(orderDetails.getOrderDetailsNo());
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setAmount(paymentPrice);
			transaction.setType(type);
			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			if (status == Transaction.STATUS_11)
			{
				transaction.setRefundsDate(new Timestamp(System.currentTimeMillis()));
				transaction.setRefundsStatus(status);
			}
			transaction.setMark(remark);
			if (transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
			    .doubleValue() != 0)
			{
				transactionDAO.saveTransaction(transaction);
				// deduction balance the from agent
				fromAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				fromAgent.setAllowBalance(ab.getAllowBalance().subtract(
				    paymentPrice));
				/*
				 * fromAgent.setBalance(BigDecimal.valueOf(fromAgent
				 * .getBalance().doubleValue() - paymentPrice));
				 */
				// agentDAO.update(fromAgent);
				// add balance for to agent
				toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getNotAllowBalance() != null)
				{
					toAgent.setNotallowBalance(ab2.getNotAllowBalance().add(paymentPrice));
					/*
					 * toAgent.setBalance(BigDecimal.valueOf(toAgent
					 * .getBalance().doubleValue() + paymentPrice));
					 */
				}
				else
				{
					toAgent.setNotallowBalance(paymentPrice);
					// toAgent.setBalance(BigDecimal.valueOf(paymentPrice));
				}
			}
			else
			{
				// 这里是等于0的transaction，不记录到数据库里。
				System.out.println("----------------old amount = "
				    + transaction.getAmount() + ",---------new amount="
				    + transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			// agentDAO.update(toAgent);
		}
		catch (Exception ex)
		{
			throw ex;
		}
		// return true;
	}

	
	public void buildCreditTransaction(OrderDetails orderDetails,
	    Agent fromAgent, Agent toAgent, Long ptype, Long type,
	    BigDecimal paymentPrice, String remark, long status, Timestamp sysDate)
	    throws Exception
	{
		try
		{
			// add the transaction
			Transaction transaction = new Transaction();
			transaction.setOrderDetails(orderDetails);
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setAmount(paymentPrice);
			transaction.setType(ptype);
			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);
			agentDAO.synallowBalance(fromAgent);
			agentDAO.synallowBalance(toAgent);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			if (status == Transaction.STATUS_11)
			{
				transaction.setRefundsDate(new Timestamp(System.currentTimeMillis()));
				transaction.setRefundsStatus(status);
			}
			transaction.setMark(remark);
			if (transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
			    .doubleValue() != 0)
			{
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				transactionDAO.saveTransaction(transaction);
				fromAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				fromAgent.setCreditBalance(ab.getCreditBalance().subtract(
				    paymentPrice));

				// add balance for to agent
				toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (type == 1)
				{
					if (ab2.getAllowBalance() != null)
					{
						toAgent
						    .setAllowBalance(ab2.getAllowBalance().add(paymentPrice));
					}
					else
					{
						toAgent.setAllowBalance(paymentPrice);
					}
				}
				else
				{
					if (ab2.getCreditBalance() != null)
					{
						toAgent.setCreditBalance(ab2.getCreditBalance().add(
						    paymentPrice));
					}
					else
					{
						toAgent.setCreditBalance(paymentPrice);
					}
				}
			}
			else
			{
				// 这里是等于0的transaction，不记录到数据库里。
				System.out.println("----------------old amount = "
				    + transaction.getAmount() + ",---------new amount="
				    + transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void buildCreditRefundTransaction(OrderDetails orderDetails,
	    Agent fromAgent, Agent toAgent, Long type, BigDecimal paymentPrice,
	    String remark, long status) throws Exception
	{
		try
		{
			// add the transaction
			Transaction transaction = new Transaction();
			transaction.setOrderDetails(orderDetails);
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setAmount(paymentPrice);
			transaction.setType(type);
			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			if (status == Transaction.STATUS_11)
			{
				transaction.setRefundsDate(new Timestamp(System.currentTimeMillis()));
				transaction.setRefundsStatus(status);
			}
			transaction.setMark(remark);
			if (transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
			    .doubleValue() != 0)
			{
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				transactionDAO.saveTransaction(transaction);
				fromAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				fromAgent.setAllowBalance(ab.getAllowBalance().subtract(
				    paymentPrice));

				// add balance for to agent
				toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getCreditBalance() != null)
				{
					toAgent
					    .setCreditBalance(ab2.getCreditBalance().add(paymentPrice));
				}
				else
				{
					toAgent.setCreditBalance(paymentPrice);
				}
			}
			else
			{
				// 这里是等于0的transaction，不记录到数据库里。
				System.out.println("----------------old amount = "
				    + transaction.getAmount() + ",---------new amount="
				    + transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void createCreditTransaction(OrderDetails orderDetails,
	    Agent fromAgent, Agent toAgent, Long type, BigDecimal paymentPrice,
	    String remark, long status, Timestamp sysDate, long paymentType)
	    throws Exception
	{
		try
		{
			// add the transaction
			Transaction transaction = new Transaction();
			transaction.setOrderDetails(orderDetails);
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setAmount(paymentPrice);
			transaction.setType(type);
			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);
			agentDAO.synallowBalance(fromAgent);
			agentDAO.synallowBalance(toAgent);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			transaction.setMark(remark);
			transactionDAO.saveTransaction(transaction);
			fromAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
			AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
			if (paymentType == OrderDetails.ORDER_DETAILS_TYPE_1)
			{
				fromAgent.setAllowBalance(ab.getAllowBalance().subtract(
				    paymentPrice));
			}
			else if (paymentType == OrderDetails.ORDER_DETAILS_TYPE_2)
			{
				fromAgent.setCreditBalance(ab.getCreditBalance().subtract(
				    paymentPrice));
			}
			toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			if (ab2.getCreditBalance() != null)
			{
				toAgent.setCreditBalance(ab2.getCreditBalance().add(paymentPrice));
			}
			else
			{
				toAgent.setCreditBalance(paymentPrice);
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	// *************************notify verify 通知确认************************
	public boolean notifyVerify(String partner, String notifyId, long logType)
	    throws AppException
	{
		if (!verifyPartner(partner)) { return false; }
		boolean flag = false;
		OrderDetails order = transactionDAO.queryOrderDetailByIdAndPartner(Long
		    .parseLong(notifyId), partner);
		if (order != null
		    && order.getId() > 0
		    && (order.getStatus() == Transaction.STATUS_3 || order.getStatus() == Transaction.STATUS_11))
		{
			flag = true;
		}

		return flag;
	}

	public TransactionDAO getTransactionDAO()
	{
		return transactionDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO)
	{
		this.transactionDAO = transactionDAO;
	}

	public AgentDAO getAgentDAO()
	{
		return agentDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO)
	{
		this.agentDAO = agentDAO;
	}

	public Agent getSeller()
	{
		return seller;
	}

	public void setSeller(Agent seller)
	{
		this.seller = seller;
	}

	public NoUtil getNoUtil()
	{
		return noUtil;
	}

	public void setNoUtil(NoUtil noUtil)
	{
		this.noUtil = noUtil;
	}

	public ActionLogDAO getActionLogDAO()
	{
		return actionLogDAO;
	}

	public void setActionLogDAO(ActionLogDAO actionLogDAO)
	{
		this.actionLogDAO = actionLogDAO;
	}

	public AgentCoterieDAO getAgentCoterieDAO()
	{
		return agentCoterieDAO;
	}

	public void setAgentCoterieDAO(AgentCoterieDAO agentCoterieDAO)
	{
		this.agentCoterieDAO = agentCoterieDAO;
	}

	public CoterieDAO getCoterieDAO()
	{
		return coterieDAO;
	}

	public void setCoterieDAO(CoterieDAO coterieDAO)
	{
		this.coterieDAO = coterieDAO;
	}

	public long saveActionLog(ActionLog actionLog) throws Exception
	{
		return actionLogDAO.save(actionLog);
	}

	//手机支付订单方法
	public String[] direct_pay_by_mobile(HashMap<String, String> map,
	    Agent userAgent, String url, String encode, boolean isPayByBank,
	    GatewayMessage gatewayMessage) throws Exception
	{
		String orderNo = map.get("out_trade_no");
		System.out.println(">>>>>>>>>>手机支付订单方法开始"
		    + orderNo);
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		Agent userInfo = userAgent;
		boolean isNewOrder = false;
		String msg = "";
		
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist 验证订单是否已经存在
			orderDetails = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			// 如果订单不存在,往数据库加一条新的订单
			boolean f = orderDetails == null || orderDetails.getId() < 1;
			System.out.println(">>>>>>>>>如果订单 " + orderNo
			    + " 不存在,往数据库加一条新的订单 这个地方-----" + f);
			if (f)
			{
				// create the order details if this order not exist
				orderDetails = appendOrder(map, userInfo, url, false);
				isNewOrder = true;
			}
			// royalty fee
			f = isNewOrder
			    || (!isNewOrder && Transaction.STATUS_3 != orderDetails.getStatus());
			if (f)
			{
				System.out.println(">>>>>>>>>> 开始分润");
				String returnParameter = directPayment(map, orderDetails, userInfo,
				    sysDate, gatewayMessage);
				System.out.println(">>>>>>>>>> 分润结束");
				if (returnParameter.indexOf(gatewayMessage
				    .getCredit_Account_Balance_Not_Enough())!=-1) { 
					msg =  gatewayMessage.getCredit_Account_Balance_Not_Enough();
				}else if(returnParameter.indexOf(gatewayMessage.getTxn_Result_No_Such_Account())!=-1){
					msg =  gatewayMessage.getTxn_Result_No_Such_Account();
				}else if(returnParameter.indexOf(gatewayMessage.getSuccess())==-1){
					msg =  com.fordays.fdpay.cooperate.Constant.TRADE_FAIL;
				}else{
					msg = com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS;
				}
				if (returnParameter != null && returnParameter.length() > 0)
				{
					if (!isNewOrder)
					{
						ActionLog actionLog = actionLogDAO
						    .queryActionLogByOrderId(orderDetails.getId());
						actionLog.setStatus(new Long(1));
						actionLogDAO.update(actionLog);
					}
					// patch up redirect url
					Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
					// patch up redirect url
					returnURL = UtilURL.directPayRedirectUrl(map, String
					    .valueOf(orderDetails.getId()), orderDetails.getOrderDetailsNo(),
					    userInfo, com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
					    strSysDate, coterie.getSignKey(), encode);
					System.out.println("手机支付回调url： "+returnURL);
					// set action log
					ActionLog actionLog = new ActionLog();
					actionLog.setStatus(new Long(1));
					actionLog.setLogDate(sysDate);
					actionLog.setContent(map.get("return_url") + "?" + returnURL[1]);
					actionLog.setOrderDetails(orderDetails);
					actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
					actionLogDAO.save(actionLog);
				}
				else
				{
					System.out.println(">>>>>>>>>> 分润返回结果为空!");
					return null;
				}
			}
			else
			{
				System.out.println(">>>>>>>>>> isNewOrder=" + isNewOrder
				    + " >>>>>>>>>>> orderDetails.getStatus() = "
				    + orderDetails.getStatus());
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
		System.out.println(">>>>>>>>>>手机支付订单方法结束");
		return new String[]{returnURL[0],msg};
	}

	public String validateMobilePay(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		Agent resultAgent = null;
		try
		{
			System.out.println("<<<<<<<<<< 手机支付验证开始");
			if (map.get("mobile") == null || "".equals(map.get("mobile"))) { return gatewayMessage
			    .getMobile_Cannot_Null(); }

			if (map.get("mobile_validate_code") == null
			    || "".equals(map.get("mobile_validate_code"))) { return gatewayMessage
			    .getMobile_Validate_Code_Cannot_Null(); }
			String msg = AnalyseParameter.paymentParameterVerify(map,gatewayMessage);
			if (msg.equals(gatewayMessage.getSuccess()))
			{ // 首先验证整个URL是否合法,返回true
				return msg;
			}
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l)
			{
				// 平台商未签署协议。
				return gatewayMessage.getPartner_Not_Sign_Protocol(); // 不存在,返回
				// 平台商未签署协议。
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset")))
			{
				return gatewayMessage.getValidate_Sign_Fail();
			}

			// 根据seller_id 或者 seller_email 取得卖家的信息
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id")
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0)
			{
				// return ExceptionConstant.SELLER_NOT_SIGN_PROTOCOL;
				return gatewayMessage.getSeller_Not_Sign_Protocol();
			}

			// 验证订单是否已经存在
			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
			    .get("partner"));
			if (order != null && order.getId() > 0
				 && order.getTransactions().size()>0)
			{
				// return ExceptionConstant.DUPLICATE_BATCH_NO;
				return gatewayMessage.getDuplicate_Batch_No();
			}

			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			// double totalFee = Double.parseDouble(map.get("total_fee"));
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			// double tempTotalFee = 0d;
			BigDecimal tempTotalFee = new BigDecimal("0");
			BigDecimal buyerTotalFee = new BigDecimal("0");
			int flag = 0;
			List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("buyer_parameters"));
			if (listBuyer != null && listBuyer.size() > 0)
			{
				for (String[] buyerParameters : listBuyer)
				{
					Agent buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
					BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
					buyerTotalFee = buyerTotalFee.add(buyPrice);
					if(buyerAgent!=null){
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
						if (!Gateway.equals(ab.getAllowBalance(), buyPrice))
							flag++;
					}
				}
			}
			if (flag > 0) { return gatewayMessage
			    .getBuyer_Account_Balance_Not_Enough(); // 其中有买家帐户余额不足
			}

			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					// tempTotalFee +=
					// Double.parseDouble(royaltyParameters[1].trim());
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				// BigDecimal ca = new BigDecimal(flatFee+tempTotalFee);
				BigDecimal ca = flatFee.add(tempTotalFee);
				// if((ca.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue())>totalFee){
				// return gatewayMessage.getRoyalty_Fee_Error();
				// }
				if (listBuyer != null && listBuyer.size() > 0)
				{
					if (ca.compareTo(totalFee.add(buyerTotalFee)) == 1) { return gatewayMessage
					    .getRoyalty_Fee_Error(); }
				}
				else
				{
					if (ca.compareTo(totalFee) == 1) { return gatewayMessage
					    .getRoyalty_Fee_Error(); }
				}

			}
			// else{
			// tempTotalFee = totalFee - flatFee;
			// }
			this.setSeller(resultAgent);// return seller info

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}

	public String validateConfirmMsg(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		String mes = AnalyseParameter.msgParameterVerify(map);
		if (!mes.equals("success"))
		{ // 首先验证整个URL是否合法,返回true
			// or false;
			// return ExceptionConstant.REFUND_SIGN_ETRANCE;
			return mes;
		}
		if(map.get("partner")==null||"".equals(map.get("partner").trim()))
			return gatewayMessage.getPartner_Can_Not_Null();
		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		if (coterie == null || coterie.getId() == 0l || coterie.getAgent() == null
		    || coterie.getAgent().getId() == 0l)
		{
			// return ExceptionConstant.PARTNER_NOT_SIGN_PROTOCOL; //不存在,返回
			// 平台商未签署协议。
			return gatewayMessage.getPartner_Not_Sign_Protocol(); // 不存在,返回
			// 平台商未签署协议。
		}
		if (!AnalyseParameter.validateSign(url, map.get("sign"), map
		    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset")))
		{
			// return ExceptionConstant.REFUND_SIGN_ETRANCE;
			return gatewayMessage.getRefund_Sign_Etrance();
		}
		return gatewayMessage.getSuccess();
	}

	public boolean sendMsg(HashMap<String, String> map, Agent agent)
	{
		String code = NoUtil.getRandom(6);
		agent.setMobileValidateCode(code);
		String mobilePhone = map.get("mobile");
		System.out.println(map.get("msg_content") + "  [验证码:" + code + "] ");

		TaskTimestamp task_msg = new TaskTimestamp();
		task_msg.setAgent(agent);
		task_msg.setTaskType(TaskTimestamp.type_8);
		task_msg.setTaskHour(TaskTimestamp.type_8_hour);
		task_msg.setStatus(1L);
		task_msg.setCount(TaskTimestamp.count_3);
		task_msg.setNo(code);
		task_msg.setTaskDate(new Timestamp(System.currentTimeMillis()));
		try
		{
			tasktimestampDAO.save(task_msg);
			SMUtil.send(mobilePhone, map.get("msg_content") + "     [验证码:" + code
			    + "]");
			System.out.println("========创建发送短信验证任务成功==========");
		}
		catch (AppException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void setTasktimestampDAO(TaskTimestampDAO tasktimestampDAO)
	{
		this.tasktimestampDAO = tasktimestampDAO;
	}

	public Transaction getTransactionByAgent(Agent agent) throws AppException
	{
		return transactionDAO.getTransactionByAgent(agent);
	}

	public String validateRegister(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		// TODO Auto-generated method stub
		try
		{

			// validate agent_email
			System.out.println(map.get("agent_email"));
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			// validate partner
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	/**
	 * 测试验证自动注册钱门帐号
	 * @return验证信息
	 */
	public String testValidateRegister(HashMap<String, String> map, String url,
	    GatewayMessage gatemessage)
	{
		try
		{
			System.out.println(map.get("agent_email"));
			// 调用方法判断帐号是否合法
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatemessage
			    .getAgent_Email_Error(); }
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatemessage.getPartner_Can_Not_Null();
			// 调用方法查询合作伙伴
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 01
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 01) { return gatemessage
			    .getPartner_Not_Sign_Protocol(); }
			// 调用方法判断签名是否正确
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatemessage
			    .getValidate_Sign_Fail(); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "未知异常";
		}
		// 验证通过
		return gatemessage.getSuccess();
	}

	public String validateDirectChangePassword(HashMap<String, String> map,
	    String url, GatewayMessage gatewayMessage) throws Exception
	{
		// TODO Auto-generated method stub

		if (map.get("agent_email") == null || map.get("partner") == null
		    || map.get("service") == null || map.get("type") == null
		    || map.get("sign") == null || map.get("sign_type") == null) { return gatewayMessage
		    .getNot_Found__Parameter();

		}
		if (!AnalyseParameter.checkEmail(map.get("agent_email"), true))
		{
			return gatewayMessage.getAgent_Email_Error();
		}
		else
		{
			Agent agent = null;
			agent = agentDAO.getAgentByEmail(map.get("agent_email"));
			if (agent == null) { return gatewayMessage
			    .getTxn_Result_No_Such_Account(); }
		}

		if (!(map.get("type").equals("1") || map.get("type").equals("2"))) { return gatewayMessage
		    .getChange_Password_Type_Error(); }
		// validate partner
		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		if (coterie == null || coterie.getId() == 0l || coterie.getAgent() == null
		    || coterie.getAgent().getId() == 0l) { return gatewayMessage
		    .getPartner_Not_Sign_Protocol(); }
		// validate sing
		if (!AnalyseParameter.validateSign(url, map.get("sign"), map
		    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
		    .getValidate_Sign_Fail(); }
		return gatewayMessage.getSuccess();
	}

	public String validateChangePassword(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		// TODO Auto-generated method stub

		if (map.get("agent_email") == null || map.get("partner") == null
		    || map.get("service") == null || map.get("sign") == null
		    || map.get("sign_type") == null) { return gatewayMessage
		    .getNot_Found__Parameter();

		}
		if (!AnalyseParameter.checkEmail(map.get("agent_email"), true))
		{
			return gatewayMessage.getAgent_Email_Error();
		}
		else
		{
			Agent agent = null;
			agent = agentDAO.getAgentByEmail(map.get("agent_email"));
			if (agent == null) { return gatewayMessage
			    .getTxn_Result_No_Such_Account(); }
		}

		// validate partner
		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		if (coterie == null || coterie.getId() == 0l || coterie.getAgent() == null
		    || coterie.getAgent().getId() == 0l) { return gatewayMessage
		    .getPartner_Not_Sign_Protocol(); }
		// validate sing
		if (!AnalyseParameter.validateSign(url, map.get("sign"), map
		    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
		    .getValidate_Sign_Fail(); }
		return gatewayMessage.getSuccess();
	}

	// 发送邮件
	public boolean sendEmail(HashMap<String, String> params, Agent agent,
	    long type)
	{
		// HashMap<String, String> params = new HashMap<String, String>();
		// params.put("$RealyName$", agent.getName());
		// String url = Constant.getLocalHost();
		// String md5 = MD5.encrypt(agent.getLoginName());
		// NoUtil noUtil = new NoUtil();
		// String no = noUtil.getRandom(12);
		// String mdno = MD5.encrypt(no);
		// params.put("$url$", url
		// + "/agent/agent.do?thisAction=registerActivate&md5=" + md5
		// + "&email=" + agent.getLoginName() + "&no=" + no + "&mdno="
		// + mdno);
		String result = MailUtil.sslSend("修改密码", "0002", agent.getLoginName(), params,Certification.getProtocol()); // 发邮件
		if (result.equals(""))
		{
			TaskTimestamp tasktimestamp = new TaskTimestamp();
			tasktimestamp.setAgent(agent);
			tasktimestamp.setTaskDate(new Timestamp(System.currentTimeMillis()));
			tasktimestamp.setTaskHour(new Long(72));
			tasktimestamp.setTaskType(type);
			try
			{
				tasktimestampDAO.save(tasktimestamp);
			}
			catch (AppException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 创建激活任务戳!
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String validateAutoRegister(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		// TODO Auto-generated method stub
		try {

			
			// validate partner
			System.out.println(map.containsKey("partner"));
			if(!(map.containsKey("partner")&&map.containsKey("service")&&map.containsKey("sign"))){
				return gatewayMessage.getNot_Found__Parameter();
			}
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol();
			}
			// validate sing
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gatewayMessage.getSuccess();
	}

	public String validateRapidChangePassword(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception {
		// TODO Auto-generated method stub
		if(!(map.containsKey("partner")&&map.containsKey("service")&&map.containsKey("sign")&&map.containsKey("agent_email"))){
			return gatewayMessage.getNot_Found__Parameter();
		}
		if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { 
			return gatewayMessage.getAgent_Email_Error(); 
		}
		Coterie coterie = coterieDAO
		.getCoterieByPartner(map.get("partner"));
		if (coterie == null || coterie.getId() == 0l
				|| coterie.getAgent() == null
				|| coterie.getAgent().getId() == 0l) {
			return gatewayMessage.getPartner_Not_Sign_Protocol();
		}
		// validate sing
		if (!AnalyseParameter.validateSign(url, map.get("sign"), map
				.get("sign_type"), coterie.getSignKey(), map
				.get("_input_charset"))) {
			return gatewayMessage.getValidate_Sign_Fail();
		}
		return gatewayMessage.getSuccess();
	}
	
	public String validateMobileIdentifyingCode(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception {
		// TODO Auto-generated method stub
		if(!(map.containsKey("partner")&&map.containsKey("service")&&map.containsKey("sign")&&map.containsKey("code"))){
			return gatewayMessage.getNot_Found__Parameter();
		}
		Agent agent=agentDAO.getAgentByMobilePhone(map.get("mobile"));
		if(agent==null){
			return gatewayMessage.getMobile_NOT_FOUND();
		}
		Coterie coterie = coterieDAO
		.getCoterieByPartner(map.get("partner"));
		if (coterie == null || coterie.getId() == 0l
				|| coterie.getAgent() == null
				|| coterie.getAgent().getId() == 0l) {
			return gatewayMessage.getPartner_Not_Sign_Protocol();
		}
		// validate sing
		if (!AnalyseParameter.validateSign(url, map.get("sign"), map
				.get("sign_type"), coterie.getSignKey(), map
				.get("_input_charset"))) {
			return gatewayMessage.getValidate_Sign_Fail();
		}
		return gatewayMessage.getSuccess();
	}
	
	public void test() throws AppException{
		Charge charge = chargeDAO.getChargeByOrderNo("C20090729000094");
		BigDecimal amount=charge.getAmount();
		Agent agent=charge.getAgent();
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrderNo(charge.getOrderNo());
		orderDetails.setShopName("充值");
		orderDetails.setDetailsContent("网银充值");
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
		orderDetails.setShopPrice(amount);
		orderDetails.setShopTotal(new Long(1));
		orderDetails.setPaymentPrice(amount);
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		transactionDAO.addOrderDetails(orderDetails);// 保存OrderDetails

		Transaction tempTransaction = new Transaction();
		tempTransaction.setNo(noUtil.getTransactionNo());
		tempTransaction.setAmount(amount);
		tempTransaction.setType(Transaction.TYPE_20);// 充值
		tempTransaction.setStatus(Transaction.STATUS_3);// 充值成功
		tempTransaction.setAccountDate(charge.getChargeDate());// 创建时间
		tempTransaction
				.setPayDate(new Timestamp(System.currentTimeMillis()));// 付款时间
		tempTransaction.setToAgent(agent); // 转入人
		tempTransaction
				.setFromAgent(com.fordays.fdpay.base.Constant.platChargeAgent);// 转出入(平台)
		tempTransaction.setOrderDetails(orderDetails);

		final String chargeType = charge.getType();
		if (Charge.CHARGE_TYPE_SELF.equals(chargeType)) {
			tempTransaction.setMark("充值");
		} else if (Charge.CHARGE_TYPE_TRANSACTION.equals(chargeType)) {
			tempTransaction.setMark("付款");
		} else if (Charge.CHARGE_TYPE_NOACCOUNT.equals(chargeType)) {
			tempTransaction.setMark("无钱门帐号充值付款");
		} else if (Charge.CHARGE_TYPE_DIRECTPAYMENT.equals(chargeType)) {
			tempTransaction.setMark("外部订单");
		} else if (Charge.CHARGE_TYPE_OTHER.equals(chargeType)) {
			tempTransaction.setMark("他人代充,代充者(" + charge.getRemark()
					+ ",金额:" + amount + ")");
		}
		transactionDAO.saveTransaction(tempTransaction);
	}

	public void setChargeDAO(ChargeDAO chargeDAO) {
		this.chargeDAO = chargeDAO;
	}

}
