package com.fordays.fdpay.cooperate.biz;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.event.def.OnUpdateVisitor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentCoterieDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.AgentDAOImpl;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.base.Constant;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.ConnAnalyseParameter;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.UtilURL;
import com.fordays.fdpay.cooperate.action.Gateway;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.cooperate.dao.AgentBindDAO;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.dao.TaskTimestampDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.fordays.fdpay.transaction.dao.TransactionDAOImpl;
import com.neza.mail.MailUtil;
import com.neza.message.SMUtil;
import com.neza.tool.DateUtil;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;

/**
 *接口方法
 */
public class CreditBizImp implements CreditBiz {
	
	private AgentDAO agentDAO;
	private TransactionDAO transactionDAO;
	private ActionLogDAO actionLogDAO;
	private AgentCoterieDAO agentCoterieDAO;
	private CoterieDAO coterieDAO;
	private NoUtil noUtil;
	private TransactionTemplate transactionTemplate;
	private TaskTimestampDAO tasktimestampDAO;
	private Agent seller;
	private AgentBindDAO agentBindDAO;
	
	public void setTransactionManager(HibernateTransactionManager thargeManager)
	{
		this.transactionTemplate = new TransactionTemplate(thargeManager);
	}
	
	public void setTasktimestampDAO(TaskTimestampDAO tasktimestampDAO)
	{
		this.tasktimestampDAO = tasktimestampDAO;
	}
	
	/*
	 * 根据外部订单号  查询交易信息
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

	/* (non-Javadoc)
	 * @see 解冻验证
	 */
	public String allowvalidateDirectPay(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		System.out.println(">>>>>>>>>解冻验证开始");
		try {
			String message = ConnAnalyseParameter.allowpaymentParameterVerify(map,gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) {
				return message;
			}
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			/********* 验证合作伙伴是否在商户圈里面 ********/
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol(); // 合作伙伴不存在,返回
				// 平台商未签署协议。
			}
			
			/********* 验证签名 ********/ 
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();//验证签名失败
			}
			
			/*** 验证外部订单号****/
			OrderDetails o = transactionDAO.queryOrderLikeOrderNo(map.get("out_trade_no").trim());
			if (o != null && o.getTransactions().size() > 0) {
				return gatewayMessage.getDuplicate_Batch_No();//重复的外部批次号
			}
			OrderDetails order = transactionDAO.queryOrderDetailByorderDetailsNo
				(map.get("orderdetail_no"), map.get("partner"));
			/***验证订单是否已经存在  根据订单号****/
			if(order==null){
				return gatewayMessage.getOrder_Not_Exists(); //订单不存在
			}
			
			/** 解冻类型 ***/
			long freezeType = 0;
			long unfreezeType = 0;
			System.out.println("解冻类型freeze_type"+map.get("freeze_type"));
			unfreezeType=Transaction.getUnFreezeType(map.get("freeze_type"));
			if(unfreezeType!=-1){
				freezeType=Long.parseLong(map.get("freeze_type"));
			}else{
				return gatewayMessage.getFREEZE_TYPE_ERROR();
			}
			
			/**********验证解冻集合信息**********/
			List<String[]> listRoyalty = ConnAnalyseParameter.analyseRoyaltyParameter(map.get("royalty_parameters"));	
			if (listRoyalty != null && listRoyalty.size() > 0) {
				for (String[] royaltyParameters : listRoyalty) {
					System.out.println("解冻账户："+royaltyParameters[0]);
					System.out.println("解冻金额："+royaltyParameters[1]);
					List<Transaction> listtran = transactionDAO.getTransactionByOrderDetailsNo(order.getOrderDetailsNo());
					if(listtran==null||listtran.size()==0){
						return gatewayMessage.getOrder_Not_Exists(); //订单不存在
					}
					
					Agent allowagent = agentDAO.getAgentByEmail(royaltyParameters[0]); //查找出要解冻的账户
					if(allowagent==null||allowagent.getId()<=0){
						return gatewayMessage.getAgent_Email_Error();
					}
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(allowagent.getId());
				
					BigDecimal allowtran = new BigDecimal("0");  //已经解冻了的金额
					System.out.println("账号冻结余额："+ab.getNotAllowBalance());
					//账户在payorder这条支付交易中解冻的所有交易
					List freezeOrders = transactionDAO.queryOrderLikeOrderDetailsNo(order.getOrderDetailsNo());
				
					if(null!=freezeOrders&&freezeOrders.size()>0){
						allowtran = transactionDAO.getAlreadyAmount(
							freezeOrders, allowagent.getId(),allowagent.getId()
								,Transaction.STATUS_3, unfreezeType);// 账户已解冻的金额
					}
					System.out.println("账户已解冻金额："+allowtran);
					int acount = 0;
					for(Transaction tran : listtran){
						if(tran.getToAgent().getLoginName().equals(allowagent.getLoginName())
								&&(tran.getType()==freezeType)){
							acount++;
							System.out.println("账户已冻结金额"+tran.getAmount());
							//这次要解冻的金额加上已解冻的金额与交易金额比较
							if(new BigDecimal(royaltyParameters[1]).add(allowtran).compareTo(tran.getAmount())==1){   //判断要解冻的金额是否大于交易金额
								return gatewayMessage.getALLOWBIG_FEE_ERROR();// 解冻金额过大
							}  
							if(new BigDecimal(royaltyParameters[1]).compareTo(ab.getNotAllowBalance())==1){//判断要解冻的金额是否大于账户冻结金额
								return gatewayMessage.getBuyer_Account_NotAllowBalance_Not_Enough();// 账户冻结余额不足
							}
						}
					}
					if(acount==0){   //判断该账户是否是分润账户
						return "解冻账户在订单未分润，不可解冻";// 解冻信息集参数错误。
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>解冻验证结束");
		return gatewayMessage.getSuccess();
	}
	
	/* (non-Javadoc)
	 * @see 解冻订单方法
	 */
	public String allow_direct_pay(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		Timestamp sysDate = null;     //系统时间
		OrderDetails thisOrder = null; //原订单
		ActionLog actionLog = null;  //定义日志
		String result = null;     //通知url
		System.out.println(">>>>>>>>>>解冻订单方法开始");
		try {
			// 获取系统时间
			sysDate = DateUtil.getTimestamp(DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
			//根据外部交易号查询订单
			thisOrder =  transactionDAO.queryOrderDetailByorderDetailsNo
				(map.get("orderdetail_no"), map.get("partner"));			
			// 根据partner查出平台帐号
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));	
			//外部订单号字段放入  传进来的out_trade_no加上原支付订单号 用~分割
			String orderno = map.get("out_trade_no")+"~"+thisOrder.getOrderDetailsNo();
			// 添加解冻 order
			OrderDetails refundOrder = new OrderDetails();
			refundOrder.setShopName(thisOrder.getShopName());
			refundOrder.setDetailsContent(thisOrder.getDetailsContent());
			refundOrder.setPartner(map.get("partner"));
			refundOrder.setOrderNo(orderno);
			refundOrder.setEmailPrice(thisOrder.getEmailPrice());
			refundOrder.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_6);   //冻结到现金			
			refundOrder.setAgent(thisOrder.getAgent());
			refundOrder.setStatus(Transaction.STATUS_3);
			refundOrder.setPaymentPrice(thisOrder.getPaymentPrice());
			refundOrder.setShopTotal(thisOrder.getShopTotal());
			refundOrder.setCreateDate(sysDate);
			refundOrder.setOrderDetailsNo(noUtil.getOrderDetailsNo());//新生成的订单号
			refundOrder.setFinishDate(sysDate);
			transactionDAO.addOrderDetails(refundOrder);
			System.out.println(">>>>>>>>>>解冻开始");
			result= allow_direct(map, refundOrder, thisOrder, coterie, gatewayMessage, url);
			System.out.println(">>>>>>>>>>解冻结束");
			List<Transaction> list = transactionDAO.getTransactionByOrderDetailsNo(thisOrder.getOrderDetailsNo());
			for(Transaction t : list){
				t.setStatus(Transaction.STATUS_3);
			}
			// add log
			actionLog = new ActionLog();
			actionLog.setStatus(new Long(1));
			actionLog.setLogDate(sysDate);
			actionLog.setContent(url);
			actionLog.setOrderDetails(refundOrder);
			actionLog.setLogType(ActionLog.LOG_TYPE_REFUND_REQUEST);
			actionLogDAO.save(actionLog);

			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("return_url") + "?" + result);
			aLog.setOrderDetails(refundOrder);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);
			actionLogDAO.save(aLog);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>解冻订单方法结束");
		return result;
	}
	
	/* (non-Javadoc)
	 * 解冻控制
	 */
	public String allow_direct(HashMap<String, String> map,
		    OrderDetails refundOrder, OrderDetails payOrder, Coterie coterie,
		    GatewayMessage gatewayMessage, String url) throws Exception{
		String result=null;
		Agent roAgent = null; //解冻账户
		BigDecimal addallow = new BigDecimal("0");    //解冻金额
		List<String[]> ropayresult = null;
		String success_details = "";
		String fails_details = "";
		String trade_status = com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS;
		
		long unfreezeType = 0;
		unfreezeType=Transaction.getUnFreezeType(map.get("freeze_type")); //获取解冻类型
		/*************分润帐户解冻****************/
		
		String royaltyParams = map.get("royalty_parameters");
		//分割提成信息集 并方到list中
		ropayresult = AnalyseParameter.analyseRoyaltyParameter(royaltyParams);
		int f = 0;
		int ff = 0;
		int s = 0;
		int ss = 0;
		if (ropayresult != null && ropayresult.size() > 0) {
			for (String[] agentInfo : ropayresult) {   //循环解冻信息集		
				roAgent = agentDAO.getAgentByEmail(agentInfo[0]);
				addallow = new BigDecimal(agentInfo[1]);
				if(roAgent==null||roAgent.getId()<=0){  //验证用户是否存在
					if(f>ff)
						fails_details=fails_details+"|"+agentInfo[0]+"^"+agentInfo[1]+"^"
							+gatewayMessage.getAccount_Not_Exists(); //失败信息集
					else
						fails_details=fails_details+agentInfo[0]+"^"+agentInfo[1]+"^"
							+gatewayMessage.getAccount_Not_Exists(); //失败信息集
					
					ff = f;
					f++;
					trade_status=com.fordays.fdpay.cooperate.Constant.TRADE_FAIL;
				}else{
					AgentBalance ab = agentDAO.getAgentBalance(roAgent.getId());
					if(ab.getNotAllowBalance().compareTo(addallow)==-1){  //验证用户冻结金额是否大于解冻金额
						if(f>ff)
							fails_details=fails_details+"|"+agentInfo[0]+"^"+agentInfo[1]+"^"
								+gatewayMessage.getBuyer_Account_NotAllowBalance_Not_Enough(); //失败信息集
						else
							fails_details=fails_details+agentInfo[0]+"^"+agentInfo[1]+"^"
								+gatewayMessage.getBuyer_Account_NotAllowBalance_Not_Enough(); //失败信息集
							
						ff = f;
						f++;
						trade_status=com.fordays.fdpay.cooperate.Constant.TRADE_FAIL;
					}else{
						try {
							buildAllowTransaction(refundOrder, roAgent, roAgent,
			 						unfreezeType, addallow, "金额解冻", Transaction.STATUS_3);
						} catch (Exception e) {
							e.printStackTrace();
							throw e;
						}
						if(s>ss) 
							success_details=success_details+"|"+agentInfo[0]+"^"+agentInfo[1];
						else
							success_details=success_details+agentInfo[0]+"^"+agentInfo[1];
						
						ss = s;//????
						s++;
					}
				}
			}
		}
		refundOrder.setAgent(roAgent);
		refundOrder.setShopPrice(addallow);
		refundOrder.setPaymentPrice(addallow);
		
		result = UtilURL.allow_directUrl(map,refundOrder, DateUtil.getDateString(new Date(),
        "yyyy-MM-dd HH:mm:ss"), coterie.getSignKey(),fails_details,success_details,trade_status);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see 信用支付（外买）订单方法
	 */
	public String credit_paymentout(HashMap<String, String> map, Agent userAgent,
		    String url, String encode, boolean isPayByBank,
		    GatewayMessage gatewayMessage) throws Exception{
			String orderNo = map.get("out_trade_no");
			System.out.println(">>>>>>>>>信用支付（外买）订单方法开始" + orderNo);
			Timestamp sysDate = null;
			OrderDetails orderDetails = null;
			OrderDetails orderDetails2 = null;
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
				System.out.println(">>>>>>>>>>>>>>>>>>>>>如果订单 " + orderNo
				    + " 不存在,往数据库加一条新的订单这个地方-----" + f);
				if (f)
				{
					// 信用到信用Long.parseLong("2") 这条订单是针对买家账户支付给平台账户
					orderDetails = appendOrder(map, userInfo, url, false,Long.parseLong(map.get("payment_type")));
					
					// 现金到现金Long.parseLong("1") 这条订单是针对平台账户支付给分润账户
					orderDetails2 = appendOrder(orderDetails,Long.parseLong("1"),url,map.get("pay_fee"));
					orderDetails.setBuyType(OrderDetails.BUY_TYPE_4);   //标识为信用外买支付
					orderDetails2.setBuyType(OrderDetails.BUY_TYPE_4);
					
					System.out.println("orderDetailsType="+orderDetails.getOrderDetailsType());
					System.out.println("orderDetailsType2="+orderDetails2.getOrderDetailsType());
					isNewOrder = true;
				}
				// royalty fee
				f = isNewOrder
				    || (!isNewOrder && Transaction.STATUS_3 != orderDetails.getStatus());

				if (f)
				{
					// 开始分润*********
					System.out.println(">>>>>>>>>> 信用支付（外买）分润开始");
					String returnParameter = directCreditPaymentout(map, orderDetails,
							orderDetails2,userInfo, sysDate, gatewayMessage);
					System.out.println(">>>>>>>>>> 信用支付（外买）分润结束");
					/***************交易结果集设置**************/
					returnParameter = returnParameter.substring
					(0, returnParameter.lastIndexOf("|")); //去掉returnParameter最后的"|"
					List<String[]> listRoyalty = AnalyseParameter  //分割结果集
					.analyseRoyaltyParameter(returnParameter); 
					String trade_status = com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS; //定义交易状态
					if(listRoyalty!=null&&listRoyalty.size()!=0){
						for(String[] s : listRoyalty){
							if(!s[3].equals(gatewayMessage.getSuccess())) //判断每笔交易是否都成功
								trade_status = com.fordays.fdpay.  //只要有一笔失败交易失败状态的值改为TRADE_FAIL
								cooperate.Constant.TRADE_FAIL;
						}
					}
					/***************交易结果集设置结束**************/
					orderDetails.setStatus(OrderDetails.STATUS_11);  //修改状态
					System.out.println("外部订单号： " + orderNo
					    + "  分润结束处理结果returnParameter："+returnParameter);
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
						    userInfo, trade_status, returnParameter,strSysDate,
						    coterie.getSignKey(), encode);

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
			System.out.println(">>>>>>>>>信用支付（外买）订单方法开始" + orderNo);
			return returnURL[0];
		}

	/* (non-Javadoc)
	 * 信用支付（外买）控制
	 */
	public String directCreditPaymentout(HashMap<String, String> map,
	    OrderDetails orderDetails,OrderDetails orderDetails2, Agent userAgent, Timestamp sysDate,
	    GatewayMessage gatewayMessage) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee")); //交易金额
		BigDecimal buyerFee = new BigDecimal(map.get("buyer_fee")); //买家付款金额
		
		Long type = new Long(map.get("type")); //金额交易类型（放到余额或信用余额）
		List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("royalty_parameters"));   //提成信息集
		
		// temp parameter
		BigDecimal tempPay = new BigDecimal("0");
		BigDecimal finalFee = new BigDecimal("0");

		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));  //查出商户圈
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId());  //商户圈临时账户
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());  //商户圈账户

		// 先检查是否够钱付款
		Agent paymentAgent = agentDAO.getAgentById(userAgent.getId());
		
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(paymentAgent.getId());	
		if (!Gateway.equals2(ab.getCreditBalance(), buyerFee)) { return sb
		    .append(gatewayMessage.getCredit_Account_Balance_Not_Enough())
		    .toString(); }//判断信用金额是否比买家付款金额大
		
		// 先把买家的授信金额打到平台帐号上
		buildTransaction(orderDetails, paymentAgent, avouchAgent,
		    Transaction.TYPE_180, buyerFee, null, Transaction.STATUS_3);

		// pay flat fee from partner's avouch account
		// 收取平台费用,费用 = 交易金额 * 费率 / 100
		BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
		    new BigDecimal("100"));
		
		BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal tempTotalFee = new BigDecimal("0"); //总分润金额
		
	
		// 把平台帐号的钱分润到各个需要分润的帐号上
		if (listRoyalty != null && listRoyalty.size() > 0)
		{
			for (String[] royaltyParameters : listRoyalty)
			{
				Agent resultAgent = agentDAO.getAgentByEmail(royaltyParameters[0]); //分润账户
				System.out.println(">>>>>>>>>>分润账户:"+resultAgent.getEmail());
				BigDecimal tempFee = new BigDecimal(royaltyParameters[1].trim());
				System.out.println(">>>>>>>>>>分润金额:"+tempFee);
				tempTotalFee = tempTotalFee.add(tempFee);
				System.out.println(">>>>>>>>>>总分润金额:"+tempTotalFee);
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
//						AgentBalance ab1 = agentDAO.getAgentBalance(avouchAgent.getId());
//						if (ab1.getCreditBalance() != null
//						    && ab1.getCreditBalance().compareTo(tempFee) >= 0)
//						{	//圈主给分润账户
							buildTransaction(orderDetails2, avouchAgent, resultAgent,
							    Transaction.TYPE_1, tempFee, null, Transaction.STATUS_3);
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
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id") //卖家
				    .trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}

			tempTotalFee = new BigDecimal(map.get("pay_fee")); //圈主给卖家的金额
			BigDecimal tempFee = new BigDecimal(map.get("pay_fee"));
			System.out.println("没有分润账户，圈主给卖家的金额="+tempFee);
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
						buildTransaction(orderDetails2, avouchAgent, resultAgent,
						    Transaction.TYPE_1, tempFee, null, Transaction.STATUS_3);
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
		System.out.println("分润失败的金额:tempPay="+tempPay);
		if (tempPay.doubleValue() > 0)
		{
			// Agent tempAgent = agentDAO.getAgentById(tempAccount);
			buildTransaction(orderDetails2, avouchAgent, tempAgent,
			    Transaction.TYPE_1, tempPay, null, Transaction.STATUS_3);
		}
		
		// 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
		BigDecimal ca = tempTotalFee.add(flatFee);
		if (ca.compareTo(totalFee) < 0)
		{
			finalFee = totalFee.subtract(ca.setScale(2, BigDecimal.ROUND_HALF_UP));
			System.out.println("分润和收取手续费剩下的金额:finalFee="+finalFee);
			buildTransaction(orderDetails2, avouchAgent, tempAgent,
			    Transaction.TYPE_1, finalFee, null, Transaction.STATUS_3);
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

		if(Constant.platRateAgent!=null&&flatFee.compareTo(new BigDecimal("0"))==1){  //收取手续费账号
			System.out.println("手续费flatFee："+flatFee);
			buildTransaction(orderDetails2, avouchAgent, Constant.platRateAgent,
			    Transaction.TYPE_1, flatFee, null, Transaction.STATUS_3);//手续费
		}

		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(sysDate);
		transactionDAO.updateOrderDetails(orderDetails);

		System.out.println(sb.toString());
		return sb.toString();
	}
	
	/*
	 * (non-Javadoc) 
	 * 验证信用支付(外买)
	 */
	public String validateCreditPayout(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>>信用支付(外买)验证开始");
		Agent resultAgent = null; //卖家用户
		Agent buyerAgent = null;  //买家用户
		Agent panerAgent = null;  //买家圈主用户
		AgentCoterie this_agent_coterir = null;  //买家用户圈
		try
		{
			String message = ConnAnalyseParameter.paymentParameterVerify(map,
			    gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			
			/****  验证标志类型 ****/
			if (!AnalyseParameter.checkType(map.get("type"))) { return gatewayMessage
			    .getType_Error(); }
			
			/******* 验证商户圈 ******/
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0
				    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return gatewayMessage
				    .getPartner_Not_Sign_Protocol(); // 平台未签协议
			}
			
			if(coterie.getStatus()==0){
				return gatewayMessage.getCREDIT_PAYMENT_IS_STOP();  //信用圈已经被禁用
			}
				
			/**** 验证签名 ****/
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
			
			/**********验证买家***********/
			if(map.get("buyer_id")!=null&&!"".equals(map.get("buyer_id").trim())){
				buyerAgent = agentDAO.getAgentById(Long.parseLong(map.get("buyer_id").trim()));
			}
			if(map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email"))){
				buyerAgent = agentDAO.getAgentByEmail(map.get("buyer_email"));
			}
			if(buyerAgent==null){
				return gatewayMessage.getBuyer_Email_Error();
			}
			
			/**********验证买家圈主***********/
			if((map.get("buyerpaner_email")==null&&"".equals(map.get("buyerpaner_email")))
					&&(map.get("buyerpaner_id")==null&&"".equals(map.get("buyerpaner_id").trim()))){
				 return gatewayMessage.getBUYER_PANER_EMAIL_ERROR();
			}
			if(map.get("buyerpaner_id")!=null&&!"".equals(map.get("buyerpaner_id").trim())){
				panerAgent = agentDAO.getAgentById(Long.parseLong(map.get("buyerpaner_id").trim()));
			}
			if(map.get("buyerpaner_email")!=null&&!"".equals(map.get("buyerpaner_email"))){
				if (!AnalyseParameter.checkEmail(map.get("buyerpaner_email"), true)) { return gatewayMessage  
				    .getBUYER_PANER_EMAIL_ERROR(); } //验证买家圈主参数
				panerAgent = agentDAO.getAgentByEmail(map.get("buyerpaner_email"));
			}
			if(panerAgent==null){
				 return gatewayMessage.getBUYER_PANER_EMAIL_ERROR();
			}
			System.out.println("信用支付外买商户圈主id="+panerAgent.getId());
			System.out.println("信用支付外买买家圈主id="+coterie.getAgent().getId());
			if(coterie.getAgent().getId()!=panerAgent.getId()){  //验证买家圈主是否商户圈圈主
				 return gatewayMessage.getBUYER_PANER_EMAIL_ERROR();
			}
			
			/**********验证买家信用支付圈***********/
			this_agent_coterir = agentCoterieDAO.queryByAgentEmail(
					buyerAgent.getLoginName(), map.get("partner"));
			if (this_agent_coterir == null) {
				return gatewayMessage.getAgent_Not_credit_Member(); 
			}
			if(this_agent_coterir.getStatus()==AgentCoterie.CHANGE_STATUS_0){  //验证买家是否可信用支付
				return "买家"+gatewayMessage.getCredit_Payment_Has_Stop();
			}
			if(this_agent_coterir.getTransactionScope()!=AgentCoterie.CHANGE_STATUS_1){ //验证买家是否可外买
				return "买家"+gatewayMessage.getCredit_No_Payout_Stop();
			}
			if (!agentCoterieDAO.checkCreditExpireDate(
					buyerAgent,coterie)) {
				return gatewayMessage.getCredit_Payment_Date_Expire();// 信用支付有效期已过
			}
			System.out.println("该用户的信用还款类型【1 逐笔】【2 多笔】是："+this_agent_coterir.getRepaymentType());
			if (this_agent_coterir != null
			    && this_agent_coterir.getRepaymentType() == 1) //逐笔还款 判断是否还了上一笔金额
			{
				List creditPayList = getCreditPayListByAgent(buyerAgent);		
				if (creditPayList != null && creditPayList.size() > 0) { 
					System.out.println("未还款的信用支付笔数："+creditPayList.size());
					return gatewayMessage.getCredit_Not_Repayment();
				}
			}
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
			if (this_agent_coterir != null
				    && this_agent_coterir.getRepaymentType() == 2) //多笔还款 判断信用金额是否小于最少授信金额
				{	System.out.println("买家信用余额："+ab.getCreditBalance());
					System.out.println("买家最低授信金额："+this_agent_coterir.getMinAmount());
					if (ab.getCreditBalance().compareTo(
							this_agent_coterir.getMinAmount()) < 0)  // 信用余额小于最少限制金额
						return gatewayMessage.getCredit_Price_Less_Then();
				}
			
			/**********验证卖家***********/
			if (map.get("seller_id") != null
			    && !"".equals(map.get("seller_id").trim()))
			{
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get("seller_id").trim()));
			}
			else if (map.get("seller_email") != null)
			{
				resultAgent = agentDAO.getAgentByEmail(map.get("seller_email"));
			}
			if (resultAgent == null || resultAgent.getId() == 0) { return gatewayMessage
			    .getSeller_Email_Error(); }

			/******* 验证订单 *******/
//			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
//				    .get("partner"));
			System.out.println(map.get("out_trade_no"));
			OrderDetails order = agentBindDAO.queryOrderDetailByOrderNoAndPartner(map.get("out_trade_no"));
			if (order != null && order.getId() > 0&& order.getTransactions().size()>0) {
				return gatewayMessage.getDuplicate_Batch_No();  //重复在批次号
			}
			
			/******* 验证买家给圈主信用金额 *******/
			if(map.get("buyer_fee")==null||"".equals(map.get("buyer_fee"))){
				return gatewayMessage.getBUYER_FEE_ERROR();
			}else{
				if(!AnalyseParameter.checkFee(map.get("buyer_fee")))
					return gatewayMessage.getBUYER_FEE_ERROR(); 
			}
			
			/******* 验证圈主给卖家现金金额 *******/
			if(map.get("pay_fee")==null||"".equals(map.get("pay_fee"))){
				return gatewayMessage.getPAY_FEE_ERROR();
			}else{
				if(!AnalyseParameter.checkFee(map.get("pay_fee")))
					return gatewayMessage.getPAY_FEE_ERROR(); 
			}
			
			/******* 验证分润信息集 *******/
			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));  //交易金额
			BigDecimal buyerFee = new BigDecimal(map.get("buyer_fee"));  //买家给圈主信用金额
			BigDecimal payFee = new BigDecimal(map.get("pay_fee"));  //圈主给卖家可用金额
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(  //平台收费金额
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP); //平台收费金额
			BigDecimal tempTotalFee = new BigDecimal("0");  //总的分润金额
			BigDecimal ca = new BigDecimal("0");	//平台收费金额加上总分润金额
			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
				    .get("royalty_parameters"));
			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);//总的分润金额
				}
				ca = flatFee.add(tempTotalFee);
				System.out.println("交易金额："+totalFee);
				System.out.println("收取平台手续费:"+flatFee);
				System.out.println("收取平台手续费加上总分润金额："+ca);
				if(ca.compareTo(totalFee)==1){
					return gatewayMessage.getRoyalty_Fee_Error();//分润金额过大
				}
			}else{
				if(payFee.add(flatFee).compareTo(totalFee)>0){//圈主给卖家金额过大或过小
					return gatewayMessage.getPAY_FEE_ERROR();
				}
			}	
			
			if(ca.compareTo(new BigDecimal("0"))==0)
				ca=flatFee.add(payFee);
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab1 = agentDAO.getAgentBalance(panerAgent.getId());
			System.out.println("圈主佘额:"+ab1.getAllowBalance());
			if(ab1.getAllowBalance()==null||ca
					.compareTo(ab1.getAllowBalance())==1){
				return gatewayMessage.getBUYER_PANER_ACCOUNT_BALANCE_NOT_ENOUGH();//买家圈主余额不足
			}					
			if(buyerFee.compareTo(totalFee)==1){//买家给圈主金额过大
				return gatewayMessage.getBUYER_FEE_SOMORE();
			}
			
			if(ab.getCreditBalance()==null||totalFee.compareTo(ab.getCreditBalance())==1){
				return gatewayMessage.getCredit_Account_Balance_Not_Enough();//买家信用余额不足
			}
			
			this.setSeller(resultAgent);//return seller info
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>>信用支付(外买)验证结束");
		return gatewayMessage.getSuccess();
	}
	
	/* (non-Javadoc)
	 * @see 信用退款（外买）订单方法
	 */
	public String credit_refund_out(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>>信用退款（外买）订单方法开始");
		Timestamp sysDate = null;
		OrderDetails payOrder = null;
		OrderDetails refundOrder = null;
		OrderDetails refundOrder2 = null;
		ActionLog actionLog = null;
		String result = null;
		try
		{
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
			    .getDateString("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
	
			// 调用方法获取退款交易集合
			List<String[]> listRefund = ConnAnalyseParameter.analyseRefundParameter(map
			    .get("buyer_fee_detail_data"));
			// 调用方法获取退款交易集合
			List<String[]> listRefund2 = ConnAnalyseParameter.analyseRefundParameter(map
			    .get("pay_fee_detail_data"));
			// 交易退款信息,格式:原付款交易号^退交易金额^退款理由
			String[] refundInfo = listRefund.get(0);
			String[] refundInfo2 = listRefund2.get(0);
			// 根据付款交易号查出订单信息
			payOrder = transactionDAO.queryOrderDetailByorderDetailsNo(refundInfo[0],
			    map.get("partner"));
			// 根据partner查出平台帐号
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			
			System.out.println("refund_type:"+map.get("refund_type"));//退款类型
			long refundType = OrderDetails.getOrderDetailsType(
					Long.parseLong(map.get("refund_type"))); //根据退款类型获取订单支付类型
			
			//添加新定单（信用到信用） 这条订单是针对平台账户退款给平台
			refundOrder = appendOrder(payOrder,refundType, map,refundInfo,sysDate);
			//添加新定单（现金到现金） 这条订单是针对分润账户退款给平台账户
			refundOrder2 = appendOrder(payOrder,Long.parseLong("1"), map,refundInfo2,sysDate);
			refundOrder.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			refundOrder2.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			String orderNo = map.get("batch_no")+"~"+payOrder.getOrderDetailsNo();
			System.out.println("orderNo:"+orderNo);
			refundOrder.setOrderNo(orderNo);
			refundOrder2.setOrderNo(orderNo);
			refundOrder2.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			refundOrder.setBuyType(payOrder.getBuyType());  //标识为外买
			refundOrder2.setBuyType(payOrder.getBuyType());
			// ****************************************

			System.out.println(">>>>>>>>>>信用支付外买开始退款");
			result = directRefundForCreditout(map, refundOrder,refundOrder2,payOrder, coterie,
			    gatewayMessage, url,sysDate);  //异步通知url
			System.out.println(">>>>>>>>>>信用支付外买退款结束");
			
			
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
		System.out.println(">>>>>>>>>>>信用退款（外买）订单方法结束");
		return result;
	}

	
	/* (non-Javadoc)
	 * @see 信用退款（外买）数据集控制
	 */
	public String directRefundForCreditout(HashMap<String, String> map,
		    OrderDetails refundOrder, OrderDetails refundOrder2,OrderDetails payOrder, Coterie coterie,
		    GatewayMessage gatewayMessage, String url,Timestamp sysDate) throws Exception{	
		
		//账户在payorder这条支付交易中退款的所有交易
		List freezeOrders = transactionDAO.queryOrderLikeOrderDetailsNo(payOrder.getOrderDetailsNo());
		long tranTypeBuy = Transaction.TYPE_181;//设置退款产生的明细类型
		long tranTypePay = Transaction.TYPE_81;//设置退款产生的明细类型
		
		// 获取退款交易集合(圈主到买家)
		List<String[]> listRefund = ConnAnalyseParameter.analyseRefundParameter(map
		    .get("buyer_fee_detail_data"));
		String re[] = refundToOut(listRefund, refundOrder, refundOrder2, payOrder,
			coterie, gatewayMessage, sysDate, 
			"buy",freezeOrders,tranTypeBuy);
		
		// 获取退款交易集合(卖家到圈主)
		List<String[]> listRefund2 = ConnAnalyseParameter.analyseRefundParameter(map
		    .get("pay_fee_detail_data"));
		String re2[] = refundToOut(listRefund2, refundOrder, refundOrder2, payOrder,
			coterie, gatewayMessage, sysDate, 
			"pay",freezeOrders,tranTypePay);
		
		BigDecimal hasRefundAmoutBuy = new BigDecimal(re[2]);
		BigDecimal hasRefundAmoutAvouch = new BigDecimal(re2[2]);
		System.out.println("退给买家的总金额hasRefundAmoutBuy："+hasRefundAmoutBuy);
		System.out.println("退给平台的总金额hasRefundAmoutAvouch："+hasRefundAmoutAvouch);
		if ((hasRefundAmoutBuy.compareTo(//如果退给买家的钱和分润账户退给平台的钱
			payOrder.getPaymentPrice())>=0)&&(//都等于交易金额就把交易关闭了
				hasRefundAmoutAvouch.compareTo(payOrder.getPaymentPrice())>=0)){
					payOrder.setStatus(Transaction.STATUS_4);
						payOrder.setFinishDate(DateUtil.getTimestamp(DateUtil.getDateString(
							"yyyy-MM-dd hh:mm:ss"),"yyyy-MM-dd hh:mm:ss"));
					transactionDAO.addOrderDetails(payOrder);
		}
		
		
		// 加两个参数
		String service = map.get("service");
		String isSuccess = "T";

		// 这里修改一下,应该是回调原来的batch_no 回去,refundOrder.getOrderDetailsNo() 应该改为
		StringBuffer result = UtilURL.directRefundRedirectUrlOut(String
		    .valueOf(refundOrder.getId()), isSuccess, service, map.get("batch_no"),
		    re,re2, DateUtil.getDateString(new Date(),
		        "yyyy-MM-dd HH:mm:ss"), coterie.getSignKey(),
		        map.get("refund_type"));

		return result.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see 信用退款（外买）明细
	 */
	public String[] refundToOut(List<String[]> listRefund, OrderDetails refundOrder, 
		OrderDetails refundOrder2,OrderDetails payOrder, Coterie coterie,
		    GatewayMessage gatewayMessage,Timestamp sysDate,String buyOrPay
		    	,List freezeOrders,long tranType)throws Exception{
		
		StringBuffer sb = new StringBuffer();
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId()); // 临时帐号
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId()); // 圈主帐号

		Agent buyerAgent = payOrder.getAgent();  //买家
		Agent payAgent =null;  //卖家
		OrderDetails order2 = null;//订单2
		Transaction trans2 = null; //卖家与圈主的交易明细
		BigDecimal rateAmount = new BigDecimal("0");
		BigDecimal refundAmount = new BigDecimal("0");
		BigDecimal buyRefundAmount = new BigDecimal("0");
		BigDecimal hasRefundAmoutBuy = new BigDecimal(0);  //获取退给买家的总金额（已退款加本次退款）
		BigDecimal hasRefundAmoutPay = new BigDecimal(0);  //获取卖家退给圈主的总金额（已退款加本次退款）
		BigDecimal hasRefundAmoutAvouch = new BigDecimal(0);  //获取所有分润账户退圈主的总金额（已退款加本次退款）
		int succnum =  0;
		for (int i = 0; i < listRefund.size(); i++)  //循环退款集合
		{
			String[] refund = listRefund.get(i);
			Agent payoutAgent = null;
			Agent inceptAgent = null;
			BigDecimal amount = new BigDecimal("0");
			String mark = null;
			boolean isRefund = false;
			boolean isSame = false; // 判断退款的帐号是否是一样的
			boolean isFlag = false;
			boolean isRefundChargeFee = false; // 判断退收费金额是否有误
			if (i == 0)
			{ // 交易退款信息,格式:原付款交易号^退交易金额^退款理由
				// refund if refund fee >0
				if (refund[1] != null && !"".equals(refund[1].trim()))
				{
					amount = new BigDecimal(refund[1].trim());  //退款金额
					refundAmount = new BigDecimal(refund[1].trim()); 
					
					//判断是哪个数据集
					if(buyOrPay.equals("buy")){
						buyRefundAmount = refundAmount;
						payoutAgent = avouchAgent; // 圈主帐号退回给买家
						inceptAgent = buyerAgent; // 是原来付款的买家
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
					}else{
						 order2= transactionDAO
							.queryOrderDetailByorderDetailsNoandType(refund[0]
							 , payOrder.getPartner());
						 System.out.println("order2.getId()="+order2.getId());
						trans2 = transactionDAO
					    	.queryTransactionByOrderAndFromAgentAndType(order2.getId(), coterie
					        .getAgent().getId());
						
						payoutAgent = trans2.getToAgent(); // 卖家退给圈主
						payAgent = trans2.getToAgent();
						inceptAgent = avouchAgent; // 圈主
						BigDecimal hasPayAmout = order2.getPaymentPrice();			
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefundAmoutPay = transactionDAO.getAlreadyAmount(
								freezeOrders, payoutAgent.getId(), inceptAgent.getId(),
									Transaction.STATUS_11, tranType);// 账户已退款的金额
						}
						System.out.println("卖家分润金额hasPayAmout:"+hasPayAmout);
						System.out.println("卖家已退款金额hasRefundAmoutPay："+hasRefundAmoutPay);
						System.out.println("本次要退款金额amount:"+amount);
						
						if (hasRefundAmoutPay.add(amount).compareTo(hasPayAmout)==1)
						{
							isRefundChargeFee = true;
						}		
					}
					
					mark = refund[2];
					if (amount.compareTo(new BigDecimal("0")) > 0)
					{
						isFlag = true;
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
						isSame = true;  //退款（收款）账号相同
					}
				}

				if (refund[4] != null && !"".equals(refund[4].trim()))
				{ // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());  //退给平台的金额
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
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);//payoutAgent转出人
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim()))
						{
							inceptAgent = agentDAO.getAgentById(Long.parseLong(refund[3]
							    .trim()));
						}
						else if (refund[2] != null && !"".equals(refund[2].trim()))
						{
							inceptAgent = agentDAO.getAgentByEmail(refund[2]); //inceptAgent 转入人
						}else{  
							if(buyOrPay.equals("buy")){
								//如果没有传收款人账号 或 id   默认退给买家
								inceptAgent = agentDAO.getAgentByEmail(buyerAgent.getLoginName());
							}else{
								//如果没有传收款人账号 或 id   默认退给圈主
								inceptAgent = agentDAO.getAgentByEmail(coterie.getAgent().getLoginName());
							}
							System.out.println("买家email"+buyerAgent.getLoginName());
							System.out.println("圈主email"+coterie.getAgent().getLoginName());
							System.out.println("收款人email"+inceptAgent.getEmail());
						}
						long agentId = payoutAgent.getId();
						BigDecimal hasPayAmout = new BigDecimal(0);
						if(buyOrPay.equals("buy")){
							hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
							    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
							    Transaction.STATUS_11);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						}else{
							hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
								    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
								    Transaction.STATUS_3);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						}
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
				if((refund[2]==null||"".equals(refund[2]))&&amount.doubleValue() > 0){
					sb.append(inceptAgent.getLoginName());
				}else{
					sb.append(refund[2]);
				}
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
						else if(refund[2] != null && !"".equals(refund[2].trim()))
						{
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
						else{ 
							if(buyOrPay.equals("buy")){
								//如果没有传收款人账号 或 id   默认退给买家
								inceptAgent = agentDAO.getAgentByEmail(buyerAgent.getLoginName());
							}else{
								//如果没有传收款人账号 或 id   默认退给圈主
								inceptAgent = agentDAO.getAgentByEmail(coterie.getAgent().getLoginName());
							}
							System.out.println("买家email"+buyerAgent.getLoginName());
							System.out.println("圈主email"+coterie.getAgent().getLoginName());
							System.out.println("收款人email"+inceptAgent.getEmail());
						}
						long agentId = payoutAgent.getId();
						BigDecimal hasPayAmout = new BigDecimal(0);
						if(buyOrPay.equals("buy")){
							hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
							    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
							    Transaction.STATUS_11);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						}else{
							hasPayAmout = transactionDAO.getRefundTotalAmount(payOrder
								    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
								    Transaction.STATUS_3);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
						}
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
				

				sb.append("|");
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
				if((refund[2]==null||"".equals(refund[2]))&&amount.doubleValue() > 0){
					sb.append(inceptAgent.getLoginName());
				}else{
					sb.append(refund[2]);
				}
				sb.append("^");
				sb.append(refund[3]);
				sb.append("^");
				sb.append(refund[4]);
				sb.append("^");
				mark = refund[5];
			}
			
			try{
				if (payoutAgent != null && payoutAgent.getId() > 0
						&& amount.compareTo(new BigDecimal("0")) > 0){
						if (isSame){
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
								// 获取AgentBalance对象（用于获取金额）
								AgentBalance ab = agentDAO.getAgentBalance(payoutAgent.getId());
								if (!isFlag)
								{ 
									if(buyOrPay.equals("buy")){// 这里是分润账户打给买家分润账户 信用额	
										if (ab.getCreditBalance() != null
												&& Gateway.equals(ab.getCreditBalance(), amount)){
											//暂时买方没有分润者
										}else{
											sb.append(gatewayMessage
											        .getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
										}
									}else{
										if (ab.getAllowBalance() != null
											&& Gateway.equals(ab.getAllowBalance(), amount)){
											// 这里是分润账户打给平台账户  可用额
											buildTransaction(refundOrder2, payoutAgent, avouchAgent,
												tranType, amount, mark, Transaction.STATUS_11);
											hasRefundAmoutAvouch = hasRefundAmoutAvouch.add(amount);//把分润账户退款的钱加到退给平台的总金额
										}else{
											sb.append(gatewayMessage
											        .getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
										}
									}
								}
								else
								{//i=0时 并且退交易金额大于0时才会执行这里（退交易）
									if(buyOrPay.equals("buy")){//圈主退给买家 信用额
										if (ab.getCreditBalance() != null
											&& Gateway.equals(ab.getCreditBalance(), amount)){
												buildTransaction(refundOrder, payoutAgent, inceptAgent,
													tranType, amount, mark, Transaction.STATUS_11);  //圈主退给买家  
													hasRefundAmoutBuy = hasRefundAmoutBuy.add(amount);//把分润账户退款的钱加到退给买家的总金额
										}else{
											sb.append(gatewayMessage
											    .getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
										}
									}else{ 
										if (ab.getAllowBalance() != null
											&& Gateway.equals(ab.getAllowBalance(), amount)){
											//卖家退给圈主  现金额
											buildTransaction(refundOrder2, payoutAgent, inceptAgent, 
												tranType, amount, mark, Transaction.STATUS_11); //卖家退给圈主  
													hasRefundAmoutAvouch = hasRefundAmoutAvouch.add(amount);//把分润账户退款的钱加到退给平台的总金额
										}else{
											sb.append(gatewayMessage
											    .getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
										}
									}
								}
								sb.append(gatewayMessage.getSuccess());
								isRefund = true;
								succnum = succnum+1; //成功的退款数
							}
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
			
			if (!isFlag)
			{
				if (isRefund || (!isRefund && amount.doubleValue() > 0))
				{
					if (avouchAgent.getId() != inceptAgent.getId())
					{ // 判断如果退款分润方已经是平台帐号了,就不用添加这条记录
						buildTransaction(refundOrder2, avouchAgent, inceptAgent,
							tranType, amount, mark, Transaction.STATUS_11);
					}
				}
			}
		}
		

		// 先把钱门收费率帐号打钱到平台帐号,如果退款金额小于0,就不用执行这个操作
		if (buyRefundAmount.doubleValue() > 0)
		{
			Agent platRateAgent = Constant.platRateAgent;// 收费率帐号
			BigDecimal calc = buyRefundAmount.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			rateAmount = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			System.out.println("收费账户："+platRateAgent.getLoginName());
			System.out.println("退收费金额："+rateAmount);

			buildTransaction(refundOrder2, platRateAgent, avouchAgent,
				tranType, rateAmount, null, Transaction.STATUS_11);
		
			BigDecimal repayAmount = new BigDecimal("0");
			if(payOrder.getRepayAmount()!=null){
				repayAmount = payOrder.getRepayAmount(); //已还款金额
			}
			payOrder.setRepayAmount(buyRefundAmount.add(repayAmount));  //退款金额加进还款金额里
			if((buyRefundAmount.add(repayAmount)).compareTo
					(payOrder.getPaymentPrice())!=-1)//如果退款金额加上这条支付已还款金额不小于交易金额
				payOrder.setStatus(OrderDetails.STATUS_12);  //则修改这条信用支付的还款状态
		}
		
		//这是支付时把分润余留下的金额打给了平台临时账户上的钱
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
		
		String alredyAmout = null;
		if(buyOrPay.equals("buy")){
			alredyAmout=hasRefundAmoutBuy.toString();
		}else{
			alredyAmout=hasPayAmout.add(hasRefundAmoutAvouch).toString();
		}
		
		String[] re = new String[3];
		re[0]=String.valueOf(succnum);
		re[1]=sb.toString();
		re[2]=alredyAmout;
		return re;
	}
	
	
	/*
	 * 信用支付(外买)退款参数验证方法
	 */
	public String credit_validateRefundout(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println("信用支付(外买)退款参数验证开始");
		String partner = null;  //合作伙伴号
		Coterie coterie = null;  //平台账户圈
		try
		{
			String message = ConnAnalyseParameter.validateRefundParameter(map,
			    gatewayMessage);
			
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			
			/** 验证partner是否存在商户圈里面 **/
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return gatewayMessage.getPartner_Can_Not_Null();
			partner = map.get("partner");
			
			coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0) { return gatewayMessage
			    .getPartner_Not_Sign_Protocol(); }

			 /** 验证签名 **/
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return gatewayMessage
			    .getValidate_Sign_Fail(); }
			
			/** 验证退款类型 **/
			if(map.get("refund_type")==null){
				return gatewayMessage.getREFUND_TYPE_NOTNULL(); //退款类型不能为空
			}
			if(!AnalyseParameter.checkType(map.get("refund_type"))){
				return gatewayMessage.getREFUND_TYPE_ERROR(); //退款类型参数格式错误
			}
			
			/*** 验证外部批次号****/
			OrderDetails o = transactionDAO.queryOrderLikeOrderNo(map.get("batch_no").trim());
			if (o != null && o.getTransactions().size() > 0) {
				return gatewayMessage.getDuplicate_Batch_No();//重复的外部批次号
			}

			/**** 验证数据集参数圈主给买家 ****/
			List<String[]> listRefund = ConnAnalyseParameter.analyseRefundParameter(map
			    .get("buyer_fee_detail_data"));
			String mes = validataRefund(listRefund,"buy"
					, gatewayMessage, partner, coterie); //验证圈主退款给买家数据集
			if(!mes.equals(gatewayMessage.getSuccess())){
				return mes;
			}
			
			
			/**** 验证数据集参数卖家给圈主 ****/
			List<String[]> listRefund2 = ConnAnalyseParameter.analyseRefundParameter(map
				    .get("pay_fee_detail_data"));
			System.out.println("退款给圈主数据集笔数listRefund2："+listRefund2.size());
			String mes2 = validataRefund(listRefund2, "pay"
				    , gatewayMessage, partner, coterie); //验证卖家退款给圈主数据集
			
			if(!mes2.equals(gatewayMessage.getSuccess())){
				return mes2;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		System.out.println("信用支付(外买)退款参数验证结束");
		return gatewayMessage.getSuccess();
	}
	
	
	//信用支付(外买)退款数据集验证
	public String validataRefund(List<String[]> listRefund,
			String buyOrPay, GatewayMessage gatewayMessage,
			String partner,Coterie coterie)throws Exception{
		OrderDetails order = null;
		BigDecimal tempFee = new BigDecimal("0");
		BigDecimal refundFee = new BigDecimal("0");
		System.out.println(">>>>>>>>>>buyOrPay="+buyOrPay);
		List freezeOrders = null;
		long tranType=0;
		if(buyOrPay.equals("buy"))
			tranType = Transaction.TYPE_181;//设置退款产生的明细类型
		else
			tranType = Transaction.TYPE_81;
		Agent buyerAgent = null;  //买家账户
		Agent payAgent = null;   //卖家账户
		Agent avouchAgent = coterie.getAgent();  //平台账户
		for (int i = 0; i < listRefund.size(); i++) //平台退给买家（信用到信用）
		{
			String[] refund = listRefund.get(i);
			BigDecimal allowBalance = new BigDecimal("0");
			// 先验证交易退款信息,格式:原付款交易号^退交易金额^退款理由
			if (i == 0)
			{	
				/*** 验证订单 ***/
				if(buyOrPay.equals("buy")){
					order = transactionDAO.queryOrderDetailByorderDetailsNo(refund[0], partner);
					System.out.println(">>>>>>>>>> orderbuyid："+order.getId());
				}else{
					order = transactionDAO.queryOrderDetailByorderDetailsNoandType(refund[0], partner);
					System.out.println(">>>>>>>>>> orderpayid："+order.getId());
				}	
				if (order == null || order.getId() == 0)
				{
					return gatewayMessage.getTrade_Not_Exists(); //交易部存在
				}
				if (order != null && order.getStatus() == Transaction.STATUS_4){ 
					return gatewayMessage.getTrade_Has_Closed(); 
				}
				//账户在payorder这条支付交易中退款的所有交易
				freezeOrders = transactionDAO.queryOrderLikeOrderDetailsNo(order.getOrderDetailsNo());
				
				/*** 验证交易明细 ***/
				Transaction trans = null;
				if(buyOrPay.equals("buy")){
					trans = transactionDAO
					    .queryTransactionByOrderAndFromAgent(order.getId(), coterie
					        .getAgent().getId());
					System.out.println(">>>>>>>>>> transbuyid:"+trans.getId());
				}else{
					trans = transactionDAO
				    .queryTransactionByOrderAndFromAgentAndType(order.getId(), coterie
				        .getAgent().getId());
					System.out.println(">>>>>>>>>> transpayid:"+trans.getId());
				}
				if (trans == null || trans.getToAgent() == null) { return gatewayMessage
				    .getTrade_Not_Exists(); // 交易不存在
				}
				if (trans != null && trans.getStatus() == Transaction.STATUS_4) { 
					return gatewayMessage.getTrade_Has_Closed();   //交易已关闭
				}	
				if(buyOrPay.equals("buy")){
					AgentCoterie payoutCoterie = agentCoterieDAO
			    		.getAgentCoterieByCoterieAndAgent(partner, null
			    			, trans.getFromAgent().getLoginName());
					if (payoutCoterie == null || payoutCoterie.getId() == 0) { return gatewayMessage
					    .getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
				}else{//2010-03-03  去掉卖方卖家是否加入商户圈验证
//					AgentCoterie payoutCoterie = agentCoterieDAO
//		    			.getAgentCoterieByCoterieAndAgent(partner, null
//		    				, trans.getToAgent().getLoginName());
//					if (payoutCoterie == null || payoutCoterie.getId() == 0) { return gatewayMessage
//					    .getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
//					}
				}
				
				if (refund[1] != null && !"".equals(refund[1])){	
					refundFee = new BigDecimal(refund[1].trim());//本次退款金额
					BigDecimal hasRefund = new BigDecimal(0);
					
					if(buyOrPay.equals("buy")){
						//获取agentbalance对象得到金额
						AgentBalance ab = agentDAO.getAgentBalance(avouchAgent.getId());
						buyerAgent = order.getAgent();
						//平台退给买家的金额hasRefund
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefund = transactionDAO.getAlreadyAmount(
								freezeOrders, avouchAgent.getId(), buyerAgent.getId(),
									Transaction.STATUS_11, tranType);// 账户已退款的金额
						}
						System.out.println("圈主已退给买家的金额hasRefund="+hasRefund);
						System.out.println("本次退款金额="+refundFee);
						System.out.println("交易金额="+order.getPaymentPrice());
					}else{
						//获取agentbalance对象得到金额
						AgentBalance ab1 = agentDAO.getAgentBalance(order.getAgent().getId());
						payAgent = trans.getToAgent(); // 卖家退给圈主
						if(null!=freezeOrders&&freezeOrders.size()>0){
							hasRefund = transactionDAO.getAlreadyAmount(
									freezeOrders, payAgent.getId(), avouchAgent.getId(),
										Transaction.STATUS_11, tranType);// 账户已退款的金额
							}
						System.out.println("卖家退给圈主的金额hasRefund="+hasRefund);
						System.out.println("本次退款金额="+refundFee);
						System.out.println("交易金额="+order.getPaymentPrice());
					}
										
					//平台退给买家的金额+退款金额不能大于交易金额
 					if((hasRefund.add(refundFee)).compareTo(order.getPaymentPrice())==1){
						return gatewayMessage.getRefund_Trade_Fee_Error();   //退交易金额有误
					}
					
					if(buyOrPay.equals("buy")){
						if (avouchAgent.getCreditBalance() == null
						    || !Gateway.equals(avouchAgent.getCreditBalance(), refundFee)){
							return gatewayMessage.getTxn_Result_Account_Balance_Not_Enough(); //账户余额不足
						}
					}else{
						if (trans.getToAgent().getAllowBalance() == null
							|| !Gateway.equals(trans.getToAgent().getAllowBalance(), refundFee)){
							return gatewayMessage.getTxn_Result_Account_Balance_Not_Enough(); //账户余额不足
						}
					}
				}
			}
			else if (i >= 1 && refund.length == 6)  //分润账户退给(买家或圈主)（现金到现金）
			{// 判断金额是否为空
				BigDecimal amount = new BigDecimal(refund[4].trim());  //退给平台的金额
				Agent payoutAgent = null;
				Agent inceptAgent = null;
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
						payoutAgent = agentDAO.getAgentByEmail(refund[0]);//payoutAgent转出人
					}
					// get the incept agent info
					if (refund[3] != null && !"".equals(refund[3].trim()))
					{
						inceptAgent = agentDAO.getAgentById(Long.parseLong(refund[3]
						    .trim()));
					}
					else if (refund[2] != null && !"".equals(refund[2].trim()))
					{
						inceptAgent = agentDAO.getAgentByEmail(refund[2]); //inceptAgent 转入人
					}else{  
						if(buyOrPay.equals("buy")){
							//如果没有传收款人账号 或 id   默认退给买家
							inceptAgent = agentDAO.getAgentByEmail(buyerAgent.getLoginName());
							System.out.println("收款人-买家email:"+buyerAgent.getLoginName());
						}else{
							//如果没有传收款人账号 或 id   默认退给圈主
							inceptAgent = agentDAO.getAgentByEmail(coterie.getAgent().getLoginName());
							System.out.println("收款人-圈主email:"+coterie.getAgent().getLoginName());
						}
					}
					long agentId = payoutAgent.getId();
					BigDecimal hasPayAmout = new BigDecimal(0);
					if(buyOrPay.equals("buy")){
						hasPayAmout = transactionDAO.getRefundTotalAmount(order
						    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
						    Transaction.STATUS_11);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
					}else{
						hasPayAmout = transactionDAO.getRefundTotalAmount(order
							    .getOrderDetailsNo(), avouchAgent.getId(), agentId, //avouchAgent.getId() 这里根据平台账号查
							    Transaction.STATUS_3);						//因为不管是退款或支付都会先到平台账号然后再转给其他用户
					}
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
						return gatewayMessage.getTxn_Result_Account_Balance_Not_Enough(); //账户余额不足
					}
				}
			}
			else
			{
				return gatewayMessage.getRefund_Sign_Etrance();
			}
		}
		
		return gatewayMessage.getSuccess();
	}
	
	
	/**
	 * 可用金额支付明细
	 */
	public void buildTransaction(OrderDetails orderDetails, Agent fromAgent,
		    Agent toAgent, Long type, BigDecimal paymentPrice, String remark,
		    long status) throws Exception
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
					AgentBalance ab1 = agentDAO.getAgentBalance(fromAgent.getId());
					AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
					//System.out.println("/***交易前可用余额***fromAgent"+fromAgent.getAllowBalance());
					fromAgent.setAllowBalance(ab1.getAllowBalance().subtract(
					    paymentPrice));
					toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				//	System.out.println("/***交易前可用余额***toAgent"+toAgent.getAllowBalance());
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
	
	/**
	 *从冻结到可用支付明细
	 */
	public void buildAllowTransaction(OrderDetails orderDetails,
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
					AgentBalance ab1 = agentDAO.getAgentBalance(fromAgent.getId());
					AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
					fromAgent.setNotallowBalance(ab1.getNotAllowBalance().subtract(  //冻结金额减去要退款的金额
					    paymentPrice));
					
					/*
					 * fromAgent.setBalance(BigDecimal.valueOf(fromAgent
					 * .getBalance().doubleValue() - paymentPrice));
					 */
					// agentDAO.update(fromAgent);
					// add balance for to agent
					toAgent.setUpdateDate(new Timestamp(System.currentTimeMillis()));
					if (ab2.getAllowBalance() != null)
					{
						toAgent.setAllowBalance(ab2.getAllowBalance().add(paymentPrice));  //可以用余额加上退款的金额
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

	/*
	 * 验证激活/禁用圈员交易类型接口
	 */
	public String validate_tran_scope(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception
	{
		try
		{
			// validate agent_email
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) { return gatewayMessage
			    .getAgent_Email_Error(); }
			if (!AnalyseParameter.checkTranType(map.get("transaction_scope"))) { return gatewayMessage.getType_Error(); }
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
	 * 添加一条新订单
	 */
	public OrderDetails appendOrder(HashMap<String, String> map, Agent userAgent,
		    String url, boolean isPayByBank,long paymentType) throws Exception
		{
			String strPrice = map.get("total_fee");
			double paymentPrice = Double.parseDouble(strPrice);
			String price = map.get("price");
			String quantity = map.get("quantity");
			// String paymethod = map.get("paymethod");
			
			long orderDetailsType = 0;
			orderDetailsType = OrderDetails.getOrderDetailsType(paymentType);	//获取支付类型		
			Long logStatus = null;

			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setShopName(map.get("subject"));
			orderDetails.setPartner(map.get("partner"));
			orderDetails.setOrderNo(map.get("out_trade_no"));
			orderDetails.setDetailsContent(map.get("body"));
			// orderDetails.setReferenceOrderDetailsNo();
			orderDetails.setOrderDetailsType(orderDetailsType);
			orderDetails.setAgent(userAgent);
			if (!isPayByBank)
			{
				if (validateAmount(userAgent, paymentPrice))
				{
					orderDetails.setStatus(Transaction.STATUS_3); //交易成功
					logStatus = new Long(1);
				}
				else
				{
					orderDetails.setStatus(Transaction.STATUS_1); //等待买家付款
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
			orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());  //生成新的订单号

			transactionDAO.addOrderDetails(orderDetails); //添加订单

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
	
	/**
	 * 添加一条新订单
	 */
	public OrderDetails appendOrder(OrderDetails order,long paymentType,String url,String pay_fee) throws Exception{
		OrderDetails orderDetails = new OrderDetails();
		Long logStatus = null;
		long orderDetailsType = OrderDetails.getOrderDetailsType(paymentType);
		
		orderDetails.setShopName(order.getShopName());
		orderDetails.setPartner(order.getPartner());
		orderDetails.setOrderNo(order.getOrderNo());
		orderDetails.setDetailsContent(order.getDetailsContent());
		// orderDetails.setReferenceOrderDetailsNo();
		orderDetails.setOrderDetailsType(orderDetailsType);
		orderDetails.setAgent(order.getAgent());
		orderDetails.setStatus(Transaction.STATUS_3); //交易成功
		logStatus = new Long(1);
		orderDetails.setPaymentPrice(new BigDecimal(pay_fee));
		orderDetails.setShopPrice(order.getShopPrice());
		// set quantity 设置数量
		orderDetails.setShopTotal(order.getShopTotal());
		// 设置邮费默认为 0.00
		orderDetails.setEmailPrice(order.getEmailPrice());
		orderDetails.setCreateDate(order.getCreateDate());
		orderDetails.setPartner(order.getPartner());
		orderDetails.setOrderDetailsNo(order.getOrderDetailsNo());
		transactionDAO.addOrderDetails(orderDetails); //添加订单

		String urlStr = "https://www.qmpay.com/cooperate/credit.do?" + url;
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
	
	/**
	 * 添加一条新订单
	 */
	public OrderDetails appendOrder(OrderDetails payOrder,long paymentType,
			Map<String, String> map,String[] refundInfo,Timestamp sysDate) throws Exception{
		long orderDetailsType = OrderDetails.getOrderDetailsType(paymentType);
		
		//添加退款订单
		OrderDetails refundOrder = new OrderDetails();
		refundOrder.setShopName(payOrder.getShopName());
		refundOrder.setDetailsContent(payOrder.getDetailsContent());
		refundOrder.setPartner(map.get("partner"));
		refundOrder.setOrderNo(map.get("batch_no"));
		refundOrder.setEmailPrice(payOrder.getEmailPrice());
		// refundOrder.setOrderDetailsType(payOrder.getOrderDetailsType());
		refundOrder.setOrderDetailsType(orderDetailsType);   
		refundOrder.setAgent(payOrder.getAgent());
		refundOrder.setStatus(Transaction.STATUS_11);
		refundOrder.setPaymentPrice(BigDecimal.valueOf(Double
		    .parseDouble(refundInfo[1])));
		refundOrder.setShopPrice(BigDecimal.valueOf(Double
		    .parseDouble(refundInfo[1])));
		// set quantity
		refundOrder.setShopTotal(payOrder.getShopTotal());
	
		refundOrder.setCreateDate(sysDate);
		refundOrder.setOrderDetailsNo(payOrder.getOrderDetailsNo());
		refundOrder.setFinishDate(sysDate);
		transactionDAO.addOrderDetails(refundOrder);
		
		return refundOrder;
	}
	
	/**
	 * 同步可用金额
	 */
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
	
	//根据合作伙伴号查询圈主信息
	public Agent getPartnerAccount(String partner) throws AppException
	{
		return agentDAO.queryByPartner(partner);
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

	public AgentBindDAO getAgentBindDAO() {
		return agentBindDAO;
	}

	public void setAgentBindDAO(AgentBindDAO agentBindDAO) {
		this.agentBindDAO = agentBindDAO;
	}
	
	//查找没还款的信用交易
	public List getCreditPayListByAgent(Agent buyerAgent) throws AppException{
		return transactionDAO.getCreditPayListByAgent(buyerAgent);	 
	}
	
	public static void main(String[] args) {
		//test();
	}
	
	public static void test() {
		TransactionDAOImpl dao = new TransactionDAOImpl();
		AgentDAOImpl adao = new AgentDAOImpl();
		try {
			Agent buyerAgent = adao.getAgentById(4660);
			List creditPayList = dao.getCreditPayListByAgent(buyerAgent);
			if(creditPayList!=null&&creditPayList.size()!=0){
				System.out.println("未还款在支付数量："+creditPayList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
