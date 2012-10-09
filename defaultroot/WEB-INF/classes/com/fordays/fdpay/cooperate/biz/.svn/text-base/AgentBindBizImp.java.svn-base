package com.fordays.fdpay.cooperate.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentBind;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentCoterieDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.CoterieUtilURL;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.action.Gateway;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.cooperate.dao.AgentBindDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AgentBindBizImp implements AgentBindBiz {
	private CoterieDAO coterieDAO;
	private AgentBindDAO agentBindDAO;
	private AgentDAO agentDAO;
	private TransactionDAO transactionDAO;
	private Agent seller;
	private NoUtil noUtil;
	private ActionLogDAO actionLogDAO;
	private AgentCoterieDAO agentCoterieDAO;
	private static int count = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fordays.fdpay.cooperate.biz.AgentBindBiz#validateAgentBind(java.util.HashMap,
	 *      java.lang.String, com.fordays.fdpay.cooperate.GatewayMessage)
	 */
	public String validateAgentBind(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		try {
			// 主帐号
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) {
				return gatewayMessage.getAgent_Email_Error();
			}
			// 绑定帐号
			if (!AnalyseParameter.checkEmail(map.get("bind_agent_email"), true)) {
				return gatewayMessage.getAgent_Email_Error();
			}
			// 合作伙伴ID
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol();
			}
			// 签名方式
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return gatewayMessage.getSuccess();
	}

	public String validateUpdateRegisterPassword(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception {
		try {
			if (!AnalyseParameter.checkEmail(map.get("agent_email"), true)) {
				return gatewayMessage.getAgent_Email_Error();
			}
			// 合作伙伴ID
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol();
			}
			// 签名方式
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return gatewayMessage.getSuccess();
	}
	
	public String validateInfo(HashMap<String, String> map) throws Exception {
		Agent buyer = null;
		Agent receiveEmail = null;
		Agent payEmail = null;
		String msg = null;

		buyer = agentBindDAO.getAgentByEmail(map.get("buyer_email"));
		System.out.println("------------------------");
		if (buyer == null) {
			msg = "不存在买家账号";
		} else {
			receiveEmail = agentBindDAO.getAgentByEmail(map
					.get("receive_email"));
			if (receiveEmail == null) {
				msg = "不存在圈主主账号";
			} else {
				int num = 0;
				List c = agentBindDAO.getListCoterieId(buyer.getId());
				for (int i = 0; i < c.size(); i++) {
					if ((Long) c.get(i) == receiveEmail.getId()) {
						num++;
					}
				}
				if (num == 0) {
					msg = "圈主主账号不是圈主或跟买家不在同一个圈里";
				} else {
					payEmail = agentBindDAO.getAgentByEmail(map
							.get("pay_email"));
					if (payEmail == null) {
						msg = "不存在圈主次账号";
					} else {
						// 判断有没有绑定
						AgentBind agen = agentBindDAO.queryAgentBindByAgentId(
								receiveEmail.getId(), payEmail.getId());
						if (agen == null) {
							msg = "圈主两个账号没有绑定";
						} else {
							seller = agentBindDAO.getAgentByEmail(map
									.get("seller_email"));
							if (seller == null) {
								msg = "不存在卖家账号";
							} else {
								int num1 = 0;
								List c1 = agentBindDAO
										.getListCoterieId(payEmail.getId());
								for (int i = 0; i < c1.size(); i++) {
									if ((Long) c1.get(i) == seller.getId()) {
										num1++;
									}
								}
								if (num1 == 0) {
									msg = "卖家不是圈主或跟圈主次账号不在同一个圈里";
								} else {
									msg = "Success";
								}
							}
						}
					}
				}
			}
		}
		return msg;
	}

	/**
	 * 验证担保支付接口
	 * 
	 */
	public String validateDirectPay(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		Agent resultAgent = null;
		Agent buyerAgent = null; // 买家账号
		// Agent payAgent=null; //圈主次账号
		try {
			System.out
					.println("-圈中圈支付 方法验证开始,map = "
							+ map);
			String message = paymentParameterVerifyPen(map, gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) {
				return message;
			}

			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol(); // 不存在,返回
				// 平台商未签署协议。
			}
			if(coterie.getStatus()==0){
				return gatewayMessage.getCREDIT_PAYMENT_IS_STOP();  //信用圈已经被禁用
			}
			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();
			}

			// 根据seller_id 或者 seller_email 取得卖家的信息
			if (map.get("seller_id") != null
					&& !"".equals(map.get("seller_id").trim())) {
				resultAgent = agentDAO.getAgentById(Long.parseLong(map.get(
						"seller_id").trim()));
			} else if (map.get("seller_email") != null) {
				System.out.println("map get seller_emial:"
						+ map.get("seller_email"));
				resultAgent = agentBindDAO.getAgentByEmail(map
						.get("seller_email"));
			}

			// 验证圈主主账号
			if (!AnalyseParameter.checkEmail(map.get("receive_email"), true)) {
				return gatewayMessage.getAgent_Email_Error();
			}
			// 验证圈主次账号
			if (!AnalyseParameter.checkEmail(map.get("pay_email"), true)) {
				return gatewayMessage.getAgent_Email_Error();
			}
			// 验证买家邮箱
			if (!AnalyseParameter.checkEmail(map.get("buyer_email"), true)) {
				return gatewayMessage.getAgent_Email_Error();
			}

			// 如果查不到卖家信息,返回 卖家未签协议
			if (resultAgent == null || resultAgent.getId() == 0) {
				return gatewayMessage.getSeller_Not_Sign_Protocol();
			}

			// 验证订单是否已经存在
			// OrderDetails order = getOrderDetails(map.get("out_trade_no"), map
			// .get("partner"));
			OrderDetails order = agentBindDAO
					.queryOrderDetailByOrderNoAndPartner(map
							.get("out_trade_no"));

			if (order != null && order.getId() > 0
					&& order.getTransactions().size() > 0) {
				return gatewayMessage.getDuplicate_Batch_No();
			}

			List<String[]> listRoyalty = AnalyseParameter
					.analyseRoyaltyParameter(map.get("royalty_parameters"));

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
					new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");
			BigDecimal buyerTotalFee = new BigDecimal("0");
			int flag = 0;
			List<String[]> listBuyer = AnalyseParameter
					.analyseRoyaltyParameter(map.get("buyer_parameters"));
			if (listBuyer != null && listBuyer.size() > 0) {
				for (String[] buyerParameters : listBuyer) {
					buyerAgent = agentDAO.getAgentByEmail(buyerParameters[0]);
					BigDecimal buyPrice = new BigDecimal(buyerParameters[1]);
					buyerTotalFee = buyerTotalFee.add(buyPrice);
					if(buyerAgent!=null){
						AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
						if (!Gateway.equals(ab.getCreditBalance(), buyPrice))
							flag++;
					}
				}
			}
			if (flag > 0) {
				return gatewayMessage.getBuyer_Account_Balance_Not_Enough(); // 其中有买家帐户余额不足
			}

			if (listRoyalty != null && listRoyalty.size() > 0) {
				for (String[] royaltyParameters : listRoyalty) {
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
							.add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);
				if (listBuyer != null && listBuyer.size() > 0) {
					if (ca.compareTo(totalFee.add(buyerTotalFee)) == 1) {
						return gatewayMessage.getRoyalty_Fee_Error();
					}
				} else {
					if (ca.compareTo(totalFee) == 1) {
						return gatewayMessage.getRoyalty_Fee_Error();
					}
				}
			}

			// 判断还款方式
			// -------------------------买家用户圈
			buyerAgent = agentBindDAO.getAgentByEmail(map.get("buyer_email"));
			if(buyerAgent==null||buyerAgent.getId()<0){
				return gatewayMessage.getAccount_Not_Exists();
			}
			AgentCoterie buyer_agent_coterir = null; // 买家用户圈
			buyer_agent_coterir = agentCoterieDAO.queryByAgentEmail(buyerAgent
					.getLoginName(), map.get("partner"));
			if (buyer_agent_coterir == null) {
				return gatewayMessage.getAgent_Not_credit_Member();
			}
			/** 还款类型为逐笔还款,判断是否还了上一笔支付款项 * */
			if (buyer_agent_coterir != null
					&& buyer_agent_coterir.getRepaymentType() == 1) // 逐笔还款
																	// 判断是否还了上一笔金额
			{

				List creditPayList = transactionDAO
						.getCreditPayListByAgent(buyerAgent);
				if (creditPayList != null && creditPayList.size() > 0) {
					return gatewayMessage.getCredit_Not_Repayment();
				}
			}
			AgentBalance ab = agentDAO.getAgentBalance(buyerAgent.getId());
			if (buyer_agent_coterir != null
					&& buyer_agent_coterir.getRepaymentType() == 2) // 多笔还款
																	// 判断信用金额是否小于最少授信金额
			{
				if (ab.getCreditBalance().compareTo(
						buyer_agent_coterir.getMinAmount()) < 0) // 信用余额小于最少限制金额
					return gatewayMessage.getCredit_Price_Less_Then();
			}
			// -------------------------圈主次账号用户圈
			// AgentCoterie pay_agent_coterir = null; //圈主次账号用户圈
			// payAgent=agentBindDAO.getAgentByEmail(map.get("pay_email"));
			// //卖家账号
			// Agent
			// sellerAgent=agentBindDAO.getAgentByEmail(map.get("seller_email"));
			//
			// AgentCoterie
			// sellerAgentConterie=agentBindDAO.getCoterieId(sellerAgent.getId());
			//
			// Coterie
			// sellerCoterie=agentBindDAO.getByCoterieName(sellerAgentConterie.getCoterie().getId());
			// pay_agent_coterir = agentCoterieDAO.queryByAgentEmail(
			// payAgent.getLoginName(), sellerCoterie.getPartner());
			// if (pay_agent_coterir == null) {
			// return gatewayMessage.getAgent_Not_credit_Member();
			// }
			// /** 还款类型为逐笔还款,判断是否还了上一笔支付款项 **/
			// System.out.println(pay_agent_coterir.getId());
			// System.out.println(pay_agent_coterir.getRepaymentType());
			// if (pay_agent_coterir != null
			// && pay_agent_coterir.getRepaymentType() == 1) //逐笔还款 判断是否还了上一笔金额
			// {
			// List creditPayList =
			// transactionDAO.getCreditPayListByAgent(buyerAgent);
			// if (creditPayList != null && creditPayList.size() > 0) { return
			// gatewayMessage
			// .getCredit_Not_Repayment(); }
			// }
			// if (pay_agent_coterir != null
			// && pay_agent_coterir.getRepaymentType() == 2) //多笔还款
			// 判断信用金额是否小于最少授信金额
			// {
			// if (buyerAgent.getCreditBalance().compareTo(
			// pay_agent_coterir.getMinAmount()) < 0) // 信用余额小于最少限制金额
			// return gatewayMessage.getCredit_Price_Less_Then();
			// }

			this.setSeller(resultAgent);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}

	public int checkAgent(String eamil, String password) throws AppException {
		int loginStatus = 0;
		Agent agent = agentDAO.getAgentByEmail(eamil);
		if (agent == null)
			loginStatus = 1; // 帐号不存在
		else if (!agent.getEmail().equalsIgnoreCase(eamil)) {
			loginStatus = 1; // 帐号不存在
		} else {
			if (!agent.getPayPassword().equals(MD5.encrypt(password))) {
				loginStatus = 2; // 密码错误
			}
		}
		return loginStatus;
	}

	public OrderDetails appendOrder(HashMap<String, String> map,
			Agent userAgent, String url, boolean isPayByBank, long buyType)
			throws Exception {
		String strPrice = map.get("total_fee");
		double paymentPrice = Double.parseDouble(strPrice);
		String price = map.get("price");
		String quantity = map.get("quantity");
		// String paymethod = map.get("paymethod");
		long orderDetailsType = 0;
		// 只能用信用余额(2)
		orderDetailsType = OrderDetails.ORDER_DETAILS_TYPE_2;

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
		if (!isPayByBank) {
			if (validateAmount(userAgent, paymentPrice)) {
				orderDetails.setStatus(Transaction.STATUS_3);
				logStatus = new Long(1);
			} else {
				orderDetails.setStatus(Transaction.STATUS_1);
				logStatus = new Long(0);
			}
		} else {
			orderDetails.setStatus(Transaction.STATUS_1);
			logStatus = new Long(0);
		}

		List<String[]> listBuyer = AnalyseParameter.analyseRoyaltyParameter(map
				.get("buyer_parameters"));
		if (listBuyer != null && listBuyer.size() > 0) {
			BigDecimal buyPrice = new BigDecimal("0");
			for (String[] buyerParameters : listBuyer) {
				buyPrice = new BigDecimal(buyerParameters[1]).add(buyPrice)
						.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			orderDetails.setPaymentPrice(buyPrice.add(new BigDecimal(strPrice))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
		} else {
			orderDetails.setPaymentPrice(BigDecimal.valueOf(paymentPrice));
		}
		// set price 设定价格
		if (price != null && !"".equals(price)) {
			orderDetails.setShopPrice(BigDecimal.valueOf(Double
					.parseDouble(price)));
		} else {
			orderDetails.setShopPrice(BigDecimal.valueOf(paymentPrice));
		}
		// set quantity 设置数量
		if (quantity != null && !"".equals(quantity)) {
			orderDetails.setShopTotal(new Long(quantity));
		} else {
			orderDetails.setShopTotal(new Long(1));
		}

		// 设置邮费默认为 0.00
		orderDetails.setEmailPrice(new BigDecimal("0"));

		orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());

		transactionDAO.addOrderDetails(orderDetails);

		String urlStr = "https://www.qmpay.com/cooperate/agentBind.do?" + url;

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

	public boolean validateAmount(Agent agent, double price) throws Exception {
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
	

	/**
	 * 圈中圈支付及时到账
	 */
	public String payment(HashMap<String, String> map, Agent userAgent,
			String url, String encode, boolean isPayByBank,
			GatewayMessage gatewayMessage) throws Exception {

		String orderNo = map.get("out_trade_no");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>进入payment-----" + orderNo); // 外部订单号
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		Agent userInfo = userAgent;
		boolean isNewOrder = false;
		try {
			// get system date
			sysDate = DateUtil.getTimestamp(strSysDate, "yyyy-MM-dd HH:mm:ss");
			// *******************************************************************
			// check request order has exist 验证订单是否已经存在
			// orderDetails = getOrderDetails(map.get("out_trade_no"), map
			// .get("partner"));
			orderDetails = agentBindDAO.queryOrderDetailByOrderNoAndPartner(map
					.get("out_trade_no"));
			// 如果订单不存在,往数据库加一条新的订单
			boolean f = !isPayByBank
					&& (orderDetails == null || orderDetails.getId() < 1);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>如果订单 " + orderNo
					+ " 不存在,往数据库加一条新的订单 这个地方-----" + f);
			if (f) {
				// 只能用信用余额付款
				orderDetails = appendOrder(map, userInfo, url, false,
						OrderDetails.BUY_TYPE_5);

				isNewOrder = true;
			} else {
				if (isPayByBank) {
					userInfo = orderDetails.getAgent();
				} else {
					userInfo = userAgent;
					orderDetails.setAgent(userInfo);
					transactionDAO.updateOrderDetails(orderDetails);
				}
			}

			f = isNewOrder
					|| (!isNewOrder && Transaction.STATUS_3 != orderDetails
							.getStatus());
			System.out.println(">>>>>>>>>>>>>>>>>>>>> " + orderNo
					+ " royalty fee-----" + f);

			if (f) {
				// if (validateAmount(userAgent, paymentPrice)) {
				System.out.println("----------------------------- " + orderNo
						+ " 开始分润-----");
				String returnParameter = "";
				synchronized (new Object()) {
					returnParameter = coterieDirectPayment(map, orderDetails,
							userInfo, sysDate, gatewayMessage);
				}
				System.out.println("------------------------- " + orderNo
						+ " 分润结束-----");
				if (returnParameter.equals(gatewayMessage
						.getTxn_Result_Account_Balance_Not_Enough())) {
					return gatewayMessage
							.getTxn_Result_Account_Balance_Not_Enough();
				}
				if (returnParameter != null && returnParameter.length() > 0) {
					// return success
					// update action log status
					if (!isNewOrder) {
						ActionLog actionLog = actionLogDAO
								.queryActionLogByOrderId(orderDetails.getId());
						actionLog.setStatus(new Long(1));
						actionLogDAO.update(actionLog);
					}
					// patch up redirect url
					Coterie coterie = coterieDAO.getCoterieByPartner(map
							.get("partner"));
					// patch up redirect url
					returnURL = CoterieUtilURL.directPayRedirectUrl(map, String
							.valueOf(orderDetails.getId()), orderDetails
							.getOrderDetailsNo(), userInfo,
							com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS,
							strSysDate, coterie.getSignKey(), encode);

					// set action log
					ActionLog actionLog = new ActionLog();
					actionLog.setStatus(new Long(1));
					actionLog.setLogDate(sysDate);
					actionLog.setContent(map.get("return_url") + "?"
							+ returnURL[1]);
					actionLog.setOrderDetails(orderDetails);
					actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
					actionLogDAO.save(actionLog);
				} else {
					System.out.println(">>>>>>>>>>>>>>>>>>>>> 分润返回结果为空!");
				}
			} else {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>> isNewOrder="
						+ isNewOrder
						+ " >>>>>>>>>>> orderDetails.getStatus() = "
						+ orderDetails.getStatus());
			}
			// }
		} catch (Exception ex) {
			throw ex;
		}
		return returnURL[0];
	}

	/**
	 * 圈中圈即时到账支付
	 */
	public String coterieDirectPayment(HashMap<String, String> map,
			OrderDetails orderDetails, Agent userAgent, Timestamp sysDate,
			GatewayMessage gatewayMessage) throws Exception {

		// System.out.println(">>>>>>>>gatebiz行2452>>>>>>> 开始执行分润**支付类型："
		// + map.get("payment_type"));

		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
		List<String[]> listRoyalty = AnalyseParameter
				.analyseRoyaltyParameter(map.get("royalty_parameters"));

		BigDecimal buyerReceiveFee = new BigDecimal(map.get("buyer_fee"));
		BigDecimal payRoyaltyFee = new BigDecimal(map.get("pay_fee"));

		// 查到圈主主账号
		Agent ReceiveAgent = agentDAO.checkEmail(map.get("receive_email"));

		// 查到圈主次账号
		Agent PayAgent = agentDAO.checkEmail(map.get("pay_email"));

		// 卖家
		Agent sellerAgent = agentDAO.checkEmail(map.get("seller_email"));

		// 查到买家
		Agent paymentAgent = agentDAO.getAgentById(userAgent.getId());

		// 同步信用金额
		// AgentBalance ab = agentBiz.getAgentBalance(paymentAgent.getId());
		agentDAO.getAgentBalance(paymentAgent.getId());
		BigDecimal paymentAgent_allowBalance = agentDAO
				.getAgentCreditBalance(paymentAgent.getId());
		System.out.println("------  " + map.get("out_trade_no")
				+ "   --------买家同步后信用余额:" + paymentAgent_allowBalance);
		AgentBindBizImp.count++;

		if (!Gateway.equals(paymentAgent_allowBalance, totalFee)) {
			return sb.append(
					gatewayMessage.getTxn_Result_Account_Balance_Not_Enough())
					.toString();
		} else {
			agentDAO.getAgentBalance(PayAgent.getId());
			BigDecimal PayAgent_allowBalance = agentDAO
					.getAgentCreditBalance(PayAgent.getId());
			System.out.println("------  " + map.get("out_trade_no")
					+ "   --------圈主次账号同步后信用余额:" + PayAgent_allowBalance);
			AgentBindBizImp.count++;

			if (!Gateway.equals(PayAgent_allowBalance, payRoyaltyFee)) {
				return sb.append(
						gatewayMessage
								.getTxn_Result_Account_Balance_Not_Enough())
						.toString();
			} else {
				// ---------------------------------------------------------------------

				// 先把买家付款的金额打到圈主主账号上
				buildTransaction(orderDetails, paymentAgent, ReceiveAgent,
						Transaction.TYPE_180, buyerReceiveFee, null,
						Transaction.STATUS_3);

				// 分润时能用到
				// royalty_parameters不为空时
				// 圈主次账号把钱分润到各个需要分润的账号上
				if (listRoyalty != null && listRoyalty.size() > 0) {
					for (String[] royaltyParameters : listRoyalty) {
						//
						Agent resultAgent = agentDAO
								.getAgentByEmail(royaltyParameters[0]);

						BigDecimal tempFee = new BigDecimal(
								royaltyParameters[1].trim());
						// tempTotalFee = tempTotalFee.add(tempFee);
						sb.append(royaltyParameters[0]);
						sb.append("^");
						sb.append(royaltyParameters[1]);
						sb.append("^");
						sb.append(royaltyParameters[2]);
						sb.append("^");
						try {
							if (resultAgent != null
									&& resultAgent.getId() > 0
									&& tempFee.compareTo(new BigDecimal("0")) > 0) {

								// 分润无错，就开始打钱
								if (map.get("payment_type").equals("2")) {

									buildTransaction(orderDetails, PayAgent,
											resultAgent, Transaction.TYPE_180,
											tempFee, null, Transaction.STATUS_3);
									sb.append(gatewayMessage.getSuccess());

								}
							}
						} finally {
							sb.append("|");
						}

					}
				} else {
					System.out.println("金额：" + payRoyaltyFee);
					// royalty_parameters为空时
					if (map.get("payment_type").equals("2")) {
						// 圈主次账号的金额打到卖家账号
						buildTransaction(orderDetails, PayAgent, sellerAgent,
								Transaction.TYPE_180, payRoyaltyFee, null,
								Transaction.STATUS_3);
						sb.append(gatewayMessage.getSuccess());

					}
				}

				// update order details status
				orderDetails.setStatus(Transaction.STATUS_11);
				orderDetails.setFinishDate(sysDate);
				transactionDAO.updateOrderDetails(orderDetails);
				// ----------------------------------------------------------------------
				System.out.println(">>>>>>>>>>>>>>> 分润结束");
				return sb.substring(0, sb.length() - 1).toString();
			}
		}
	}

	// 信用到信用
	public void buildTransaction(OrderDetails orderDetails, Agent fromAgent,
			Agent toAgent, Long type, BigDecimal paymentPrice, String remark,
			long status) throws Exception {
		try {
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
			transaction
					.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			if (status == Transaction.STATUS_11) {
				transaction.setRefundsDate(new Timestamp(System
						.currentTimeMillis()));
				transaction.setRefundsStatus(status);
			}
			transaction.setMark(remark);
			if (transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue() != 0) {
				transactionDAO.saveTransaction(transaction);
				// deduction balance the from agent
				fromAgent.setUpdateDate(new Timestamp(System
						.currentTimeMillis()));

				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab2 =agentDAO.getAgentBalance(toAgent.getId());
				fromAgent.setAllowBalance(ab.getCreditBalance().subtract(
						paymentPrice));
				System.out.println(">>>>>>>>>>>>>>>>>>>>fromAgent = "
						+ fromAgent.getLoginName());
				/*
				 * fromAgent.setBalance(BigDecimal.valueOf(fromAgent
				 * .getBalance().doubleValue() - paymentPrice));
				 */
				// agentDAO.update(fromAgent);
				// add balance for to agent
				toAgent
						.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getCreditBalance() != null) {
					toAgent.setAllowBalance(ab2.getCreditBalance().add(
							paymentPrice));
					/*
					 * toAgent.setBalance(BigDecimal.valueOf(toAgent
					 * .getBalance().doubleValue() + paymentPrice));
					 */
				} else {
					toAgent.setAllowBalance(paymentPrice);
					// toAgent.setBalance(BigDecimal.valueOf(paymentPrice));
				}
				System.out.println(">>>>>>>>>>>>>>>>>>>>toAgent = "
						+ toAgent.getLoginName());
			} else {
				// 这里是等于0的transaction，不记录到数据库里。
				System.out.println("----------------old amount = "
						+ transaction.getAmount()
						+ ",---------new amount="
						+ transaction.getAmount().setScale(2,
								BigDecimal.ROUND_HALF_UP));
			}
			// agentDAO.update(toAgent);
		} catch (Exception ex) {
			throw ex;
		}
		// return true;
	}

	public OrderDetails getOrderDetails(String orderNo, String partner)
			throws AppException {
		// check request has exist
		OrderDetails order = null;
		order = transactionDAO.queryOrderDetailByOrderNoAndPartner(orderNo,
				partner);
		if (order != null && order.getId() > 0) {
			if (order.getAgent() != null)
				order.setBuyerEmail(order.getAgent().getLoginName());
		}
		return order;
	}

	
	
	/**
	 * 圈中圈即时退款验证
	 */
	public String validateRefund(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		try {
			String partner = map.get("partner");
			// 验证退款URL参数,返回true or false
			String message = validateRefundParameter(map, gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) {
				return message;
			}

			// validate partner 验证partner是否存在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol();
			}

			if(coterie.getStatus()==0){
				return gatewayMessage.getCREDIT_PAYMENT_IS_STOP();  //信用圈已经被禁用
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();
			}
			String reBuyType=map.get("refund_type_buyer");
			if (reBuyType == null) {
				return gatewayMessage.getREFUND_TYPE_NOTNULL(); // 退款类型不能为空
			}
			if (!AnalyseParameter.checkType(reBuyType)) {
				return gatewayMessage.getREFUND_TYPE_ERROR(); // 退款类型参数格式错误
			}
			
			String rePayType = map.get("refund_type_pay");
			if (rePayType == null) {
				return gatewayMessage.getREFUND_TYPE_NOTNULL(); // 退款类型不能为空
			}
			if (!AnalyseParameter.checkType(rePayType)) {
				return gatewayMessage.getREFUND_TYPE_ERROR(); // 退款类型参数格式错误
			}
			
			// buyer_fee_detail_data 验证数据集参数
			List<String[]> listRefund = AnalyseParameter
					.analyseRefundParameter(map.get("buyer_fee_detail_data"));
			String orderNo1 = listRefund.get(0)[0];// buyer_fee_detail_data里的订单号
			BigDecimal buyerFee = new BigDecimal(listRefund.get(0)[1]); // buyerFee退款金额
			String msg=validateDataset(listRefund,partner,gatewayMessage);
			if(!gatewayMessage.getSuccess().equals(msg)){
				return msg;
			}
			
			// pay_fee_detail_data 验证数据集参数
			List<String[]> listRefund1 = AnalyseParameter
					.analyseRefundParameter(map.get("pay_fee_detail_data"));
			String orderNo2 = listRefund1.get(0)[0];// pay_fee_detail_data里的订单号
			BigDecimal payFee = new BigDecimal(listRefund1.get(0)[1]); // payFee退款金额
			String msg1=validateDataset(listRefund1,partner,gatewayMessage);
			if(!gatewayMessage.getSuccess().equals(msg1)){
				return msg1;
			}
			
			//判断两个订单号是否相同
			if (!orderNo1.equals(orderNo2)) {
				return gatewayMessage.getORDER_NOT_IDENTICAL_EXISTS();
			}

			// 判断金额
			Transaction list = agentBindDAO.getByMoney(orderNo1);
			System.out.println("买家付给圈主主账号总金额：" + list.getAmount());
			Transaction list1 = agentBindDAO.getBySellMoney(orderNo1);
			System.out.println("圈主次账号付给卖家总金额：" + list1.getAmount());
			// 查收款人退给买家金额，并判断不能大于给的总金额
			BigDecimal buyerFeeSum = agentBindDAO.getByFee(orderNo1, list
					.getFromAgent().getId(), list.getToAgent().getId());
			System.out.println("buyerFeeSum已退的金额：" + buyerFeeSum);
			BigDecimal payFeeSum = agentBindDAO.getByFee(orderNo1, list1
					.getFromAgent().getId(), list1.getToAgent().getId());
			System.out.println("payFeeSum已退的金额：" + payFeeSum);

			if (payFeeSum != null || buyerFeeSum != null) {
				payFeeSum = payFeeSum.add(payFee);  //已退款金额加上本次要退款的金额
				buyerFeeSum = buyerFeeSum.add(buyerFee);
				if (!Gateway.equals(list1.getAmount(), payFeeSum)) {  //退款总金额与交易金额比较
					return gatewayMessage.getRefund_Trade_Fee_Error();//退交易金额有误
				}
				if (!Gateway.equals(list.getAmount(), buyerFeeSum)) {
					return gatewayMessage.getRefund_Trade_Fee_Error();
				}
			}
			if (!Gateway.equals(list1.getAmount(), payFee)) {
				return gatewayMessage.getRefund_Trade_Fee_Error();  
			}
			if (!Gateway.equals(list.getAmount(), buyerFee)) {
				return gatewayMessage.getRefund_Trade_Fee_Error();
			}

			AgentBalance ab = agentDAO.getAgentBalance(list1.getToAgent()
					.getId());
			if(reBuyType.equals("1") ||reBuyType.equals("3")){ //判断卖家是以何总类型退给圈主次账户
				System.out.println("卖家可用金额:" + ab.getAllowBalance());
				if (ab.getAllowBalance() == null
						|| !Gateway.equals(ab.getAllowBalance(), buyerFee)) {
					return gatewayMessage.getLess_than_the_amount_of_credit(); //账户余额不足
				}
			}else{
				System.out.println("卖家信用金额:" + ab.getCreditBalance());
				if (ab.getCreditBalance() == null
						|| !Gateway.equals(ab.getCreditBalance(), payFee)) {
					return gatewayMessage.getLess_than_the_amount_of_credit();
				}
			}
			
			AgentBalance ab1 = agentDAO.getAgentBalance(list.getToAgent()
					.getId());
			if(rePayType.equals("1") ||rePayType.equals("3")){//判断圈主主账户是以何总类型退给买家
				System.out.println("圈主主账号可用金额:" + ab1.getAllowBalance());
				if (ab1.getAllowBalance() == null
						|| !Gateway.equals(ab1.getAllowBalance(), buyerFee)) {
					return gatewayMessage.getLess_than_the_amount_of_credit();
				}
			}else{
				System.out.println("圈主主账号信用金额:" + ab1.getCreditBalance());
				if (ab1.getCreditBalance() == null
						|| !Gateway.equals(ab1.getCreditBalance(), buyerFee)) {
					return gatewayMessage.getLess_than_the_amount_of_credit();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return gatewayMessage.getSuccess();
	}
	
	/**
	 * 圈中圈退款数据集验证
	 * @param map
	 * @param url
	 * @param gatewayMessage
	 * @return
	 * @throws Exception
	 */
	public String validateDataset(List<String[]> listRefund, String partner,
			GatewayMessage gatewayMessage) throws Exception {
		if (listRefund == null || listRefund.size() < 1) {
			return gatewayMessage.getDetail_Data_Format_Error();
		}

		// transaction verify
		OrderDetails order = null;
		BigDecimal refundFee = new BigDecimal("0");
		for (int i = 0; i < listRefund.size(); i++) {
			String[] refund = listRefund.get(i);
			BigDecimal allowBalance = new BigDecimal("0");
			// 先验证交易退款信息,格式:原付款交易号^退交易金额^退款理由
			if (i == 0) {
				// order details 验证订单是否存在

				order = agentBindDAO.queryOrderDetailByTransNo(refund[0],
						partner);
				if (order == null || order.getId() == 0) {
					if (order != null
							&& order.getStatus() == Transaction.STATUS_4) {
						return gatewayMessage.getTrade_Has_Closed();
					}
					return gatewayMessage.getTrade_Not_Exists();
				}

				Transaction trans = transactionDAO
						.queryTransactionByOrderAndPayFee(order.getId());
				if (trans == null || trans.getToAgent() == null) {
					return gatewayMessage.getTrade_Not_Exists(); // 交易不存在
				}

				if (refund[1] != null && !"".equals(refund[1])) {
					refundFee = new BigDecimal(refund[1].trim());
				}
			}

			else if (i >= 1 && refund.length == 6) {
				// payout corterie verify

				AgentCoterie payoutCoterie = null;
				AgentCoterie inceptCoterie = null;
				if (refund[0] != null && !"".equals(refund[0])) {
					// payout corterie verify
					payoutCoterie = agentCoterieDAO
							.getAgentCoterieByCoterieAndAgent(partner, "",
									refund[0]);
				}

				if (refund[1] != null && !"".equals(refund[1])) {
					// payout corterie verify
					payoutCoterie = agentCoterieDAO
							.getAgentCoterieByCoterieAndAgent(partner,
									refund[1], "");
				}

				if (payoutCoterie == null || payoutCoterie.getId() == 0) {
					return gatewayMessage.getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
				}

				if (refund[2] != null && !"".equals(refund[2])) {
					// payout corterie verify
					inceptCoterie = agentCoterieDAO
							.getAgentCoterieByCoterieAndAgent(partner, "",
									refund[2]);
				}

				if (refund[3] != null && !"".equals(refund[3])) {
					// payout corterie verify
					inceptCoterie = agentCoterieDAO
							.getAgentCoterieByCoterieAndAgent(partner,
									refund[3], "");
				}

				if (inceptCoterie == null || inceptCoterie.getId() == 0) {
					return gatewayMessage.getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
				}

				// check the allow balance
				// 同步信用金额

				AgentBalance ab = agentDAO.getAgentBalance(payoutCoterie
						.getAgent().getId());
				if (ab.getCreditBalance() != null) {
					allowBalance = ab.getCreditBalance();
				}
			} else {
				return gatewayMessage.getRefund_Sign_Etrance();
			}
		}	
		return gatewayMessage.getSuccess();
	}
	
	/**
	 * 生成退款新的订单交易记录
	 * @param map
	 * @param payOrder
	 * @param paylist
	 * @param refundInfo
	 * @return
	 * @throws Exception
	 */
	public OrderDetails CreateOrderDetails(HashMap<String, String> map,OrderDetails payOrder
			,Transaction paylist,String[] refundInfo,String refundType) throws Exception {
		Timestamp  sysDate = DateUtil.getTimestamp(DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss"),
				"yyyy-MM-dd HH:mm:ss");
		// 进行退款保存订单记录
		OrderDetails refundOrder = new OrderDetails();
		refundOrder.setShopName(payOrder.getShopName());
		refundOrder.setDetailsContent("退款记录："+payOrder.getDetailsContent());
		refundOrder.setPartner(map.get("partner"));
		refundOrder.setOrderNo(map.get("batch_no") + "~"
				+ payOrder.getOrderDetailsNo());
		refundOrder.setEmailPrice(payOrder.getEmailPrice());
		// refundOrder.setOrderDetailsType(payOrder.getOrderDetailsType());
		if (refundType.equals("1")) {
			refundOrder
					.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1); // 现金到现金
		} else if (refundType.equals("2")) {
			refundOrder
					.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_2); // 信用到信用
		} else if (refundType.equals("3")) {
			refundOrder
					.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_3); // 现金到信用
		} else if (refundType.equals("4")) {
			refundOrder
					.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_4); // 信用到现金
		} else {
			refundOrder
					.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_2); // 信用到信用
		}
		refundOrder.setBuyType(payOrder.getBuyType());
		refundOrder.setAgent(paylist.getToAgent());
		refundOrder.setStatus(Transaction.STATUS_3);
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
		return refundOrder;
	}
	
	/**
	 * 普通退款控制方法
	 */
	public String refund(HashMap<String, String> map, String url,
			GatewayMessage gatewayMessage) throws Exception {
		Timestamp sysDate = null;
		OrderDetails payOrder = null;
		ActionLog actionLog = null;

		String result = null;
		try {
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
			// long orderId = Long.parseLong(map.get("batch_no"));

			// --------------------pay_fee_detail_data
			// 进行退款保存订单记录-----------------------
			System.out.println(map.get("pay_fee_detail_data"));
			// 调用方法获取退款交易集合
			List<String[]> listRefund = AnalyseParameter
					.analyseRefundParameter(map.get("pay_fee_detail_data"));
			// 交易退款信息,格式:原付款交易号^退交易金额^退款理由
			String[] refundInfo = listRefund.get(0);

			// 根据付款交易号查出订单信息
			payOrder = agentBindDAO.queryOrderDetailByTransNo(refundInfo[0],
					map.get("partner"));
			
			// 圈主次账号到卖家
			Transaction paylist = agentBindDAO.getBySellMoney(refundInfo[0]);

			long fromAgentid = paylist.getToAgent().getId();
			String refund_type_pay=map.get("refund_type_pay");
			//生成卖家退款给圈主次账号的交易记录
			OrderDetails refundOrderPay=CreateOrderDetails(map,payOrder,paylist,listRefund.get(0),refund_type_pay);
			
			//-----------------buyer_fee_detail_data
			// ****************************************
			System.out.println(map.get("buyer_fee_detail_data"));
			// 调用方法获取退款交易集合
			List<String[]> listRefund1 = AnalyseParameter
					.analyseRefundParameter(map.get("buyer_fee_detail_data"));
			// 交易退款信息,格式:原付款交易号^退交易金额^退款理由
			String[] refundInfo1 = listRefund1.get(0);
			
			// 买家到圈主主账号
			Transaction buyerlist = agentBindDAO.getByMoney(refundInfo1[0]);
			
			String refund_type_buyer=map.get("refund_type_buyer");
			//生成圈主主账号退款给买家的交易记录
			OrderDetails refundOrderBuy=CreateOrderDetails(map,payOrder,buyerlist,listRefund1.get(0),refund_type_buyer);

			
			// 开始退款,payOrder.getAgent() 是原来付款的买家
			System.out.println("订单类型是" + payOrder.getOrderDetailsType());
			
			if (paylist.getType() == Transaction.TYPE_180 
					& buyerlist.getType() == Transaction.TYPE_180) {
				result = directRefundForCredit(map, refundOrderPay,refundOrderBuy, payOrder,
						refundInfo[0], gatewayMessage, url);
			}

			// add log
			actionLog = new ActionLog();
			actionLog.setStatus(new Long(1));
			actionLog.setLogDate(sysDate);
			actionLog.setContent(url);
			actionLog.setOrderDetails(refundOrderBuy);
			actionLog.setLogType(ActionLog.LOG_TYPE_REFUND_REQUEST);
			actionLogDAO.save(actionLog);

			// close main order if refund all balance

			double hasRefund = transactionDAO.getRefundAmount(payOrder
					.getOrderDetailsNo(), payOrder.getAgent().getId(),
					fromAgentid);

			BigDecimal hasRefundAmout2 = transactionDAO.getRefundTotalAmount(
					payOrder.getOrderDetailsNo(), fromAgentid, payOrder
							.getAgent().getId(), Transaction.STATUS_11);

			if (payOrder.getPaymentPrice().doubleValue() == hasRefund
					&& hasRefundAmout2.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue() == hasRefund) {
				payOrder.setStatus(Transaction.STATUS_4);
				payOrder.setFinishDate(sysDate);
				transactionDAO.addOrderDetails(payOrder);
			}
			// set action log
			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("notify_url") + "?" + result);
			aLog.setOrderDetails(refundOrderBuy);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);
			actionLogDAO.save(aLog);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}

	/**
	 * 信用支付退款方法
	 * 
	 * @param map
	 * @param refundOrder 圈中圈退款产生的订单交易记录
	 * @param payOrder 圈中圈支付产生的订单交易记录
	 * @param coterie
	 * @param gatewayMessage
	 * @return
	 * @throws Exception
	 */
	public String directRefundForCredit(HashMap<String, String> map,
			OrderDetails refundOrderPay,OrderDetails refundOrderBuy, OrderDetails payOrder
			, String orderNo,GatewayMessage gatewayMessage, String url) throws Exception {
		// 根据partner查出平台帐号
		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();

		// 买家到圈主主账号
		Transaction buyerlist = agentBindDAO.getByMoney(orderNo);
		// 圈主次账号到卖家
		Transaction paylist = agentBindDAO.getBySellMoney(orderNo);

		long buyAgentId = buyerlist.getFromAgent().getId();
		System.out.println("买家ID：" + buyAgentId);

		Agent receiveAgent = buyerlist.getToAgent();
		System.out.println("圈主主账号ID：" + receiveAgent.getId());

		long fromAgentid = paylist.getFromAgent().getId();
		System.out.println("圈主次账号ID：" + fromAgentid);

		Agent sellAgent = paylist.getToAgent();
		System.out.println("卖家ID：" + sellAgent.getId());

		// 先退付款到卖家的金额
		System.out.println("卖家退款给圈主次账号---------");
		List<String[]> listRefund = AnalyseParameter.analyseRefundParameter(map
				.get("pay_fee_detail_data"));
		//执行退款
		sb=sb.append(RefundDataset(listRefund,sellAgent,refundOrderPay
				,fromAgentid,payOrder,gatewayMessage));
		System.out.println("退款结束：---------"+sb);
		// 后退买家到收款金额
		System.out.println("圈主主账号退款给买家---------");
		List<String[]> listRefund1 = AnalyseParameter
				.analyseRefundParameter(map.get("buyer_fee_detail_data"));
		BigDecimal refundAmount = new BigDecimal("0");
		refundAmount=new BigDecimal(listRefund1.get(0)[1].trim());
		sb1=sb1.append(RefundDataset(listRefund1,receiveAgent,refundOrderBuy
				,buyAgentId,payOrder,gatewayMessage));
		//退款同时RepayAmount累加退款金额
		System.out.println("退款金额："+refundAmount);
		if(payOrder.getRepayAmount()==null){
			payOrder.setRepayAmount(refundAmount);
		}else{
			if(payOrder.getStatus()!=OrderDetails.STATUS_12){
				payOrder.setRepayAmount(payOrder.getRepayAmount().add(refundAmount));
			}
		}
		agentBindDAO.saveOrderDetails(payOrder);
		System.out.println("退款结束---------"+sb1);
		// 加两个参数
		String service = "coterie_refund_fastpay_by_platform_nopwd";
		String msg = credit_validateRefund(map, url, gatewayMessage);
		String isSuccess = "F";
		if (gatewayMessage.getSuccess().equals(msg)) {
			isSuccess = "T";
		}
		int isSuccess_num=0;   //成功退款的笔数
		if(sb.toString().indexOf(gatewayMessage.getSuccess())!=-1)
			isSuccess_num=isSuccess_num+1;
		if(sb1.toString().indexOf(gatewayMessage.getSuccess())!=-1)
			isSuccess_num=isSuccess_num+1;
		StringBuffer result = CoterieUtilURL.coterieDirectRefundRedirectUrl(
				String.valueOf(refundOrderBuy.getId()),isSuccess, service
				, map.get("batch_no"), String.valueOf(isSuccess_num), sb.toString()	, sb1.toString()
				, DateUtil.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss")
				, coterie.getSignKey(),map.get("refund_type_buyer"),map.get("refund_type_pay"));

		BigDecimal repayAmount = new BigDecimal("0");
		if (payOrder.getRepayAmount() != null) {
			repayAmount = payOrder.getRepayAmount(); // 已还款金额
		}
		//不用加refundAmount金额
		if ((repayAmount).compareTo(payOrder
				.getPaymentPrice()) != -1)// 如果退款金额加上这条支付已还款金额不小于交易金额
			payOrder.setStatus(OrderDetails.STATUS_12); // 则修改这条信用支付的还款状态
		
//		// 查收款人退给买家金额，并判断不能大于给的总金额
//		Transaction list = agentBindDAO.getByMoney(listRefund1.get(0)[0]);
//		if(list!=null){
//			BigDecimal buyerFeeSum = agentBindDAO.getByFee(listRefund1.get(0)[0], buyAgentId
//					, receiveAgent.getId());
//			System.out.println("buyerFeeSum已退的金额：" + buyerFeeSum);
//			if(list.getAmount().compareTo(buyerFeeSum)==0){
//				System.out.println("信用支付的还款状态修改为12");
//				payOrder.setStatus(OrderDetails.STATUS_12); // 则修改这条信用支付的还款状态
//			}
//		}
		System.out.println("回调："+result);
		return result.toString();
	}
	
	/**
	 * 退款
	 * @param listRefund
	 * @param sellAgent
	 * @param refundOrder 圈中圈退款产生的订单交易记录
	 * @param fromAgentid
	 * @param payOrder 圈中圈支付产生的订单交易记录
	 * @param gatewayMessage
	 * @return
	 * @throws Exception
	 */
	public String RefundDataset(List<String[]> listRefund,Agent sellAgent
			,OrderDetails refundOrder,long fromAgentid,OrderDetails payOrder
			,GatewayMessage gatewayMessage) throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < listRefund.size(); i++) {
			String[] refund = listRefund.get(i);

			Agent payoutAgent = null;
			Agent inceptAgent = null;
			BigDecimal amount = new BigDecimal("0");
			String mark = null;
			boolean isRefund = false;
			boolean isSame = false; // 判断退款的账号是否是一样的
			boolean isFlag = false;
			boolean isRefundChargeFee = false; // 判断退收费金额是否有误
			if (i == 0) { // 交易退款信息,格式:原付款交易号^退交易金额^退款理由
				// refund if refund fee >0
				if (refund[1] != null && !"".equals(refund[1].trim())) {
					amount = new BigDecimal(refund[1].trim());
					// /////////////////////////////////////////////////////////////
					payoutAgent = sellAgent; // 卖家

					inceptAgent = agentBindDAO.getAgentById(fromAgentid); // 付款人

					mark = refund[2];

					if (amount.compareTo(new BigDecimal("0")) > 0) {
						isFlag = true;
					}
				}
				sb.append(refund[0]);
				sb.append("^");
				sb.append(refund[1]);
				sb.append("^");
			}

			else if (i == 1 && refund.length == 6) { // 分润退款信息,格式:
				// 转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由
				if (refund[0] != null && !refund[0].equals("")
						&& refund[2] != null && !refund[2].equals("")) {
					if (refund[0].equals(refund[2])) {
						isSame = true;
					}
				}

				if (refund[4] != null && !"".equals(refund[4].trim())) { // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());
					if (amount.doubleValue() > 0) {
						// get the payout agent info
						if (refund[1] != null && !"".equals(refund[1].trim())) {
							payoutAgent = agentDAO.getAgentById(Long
									.parseLong(refund[1].trim()));
						} else {
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim())) {
							inceptAgent = agentDAO.getAgentById(Long
									.parseLong(refund[3].trim()));
						} else {
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
					}
				}

				long agentId = agentDAO.getAgentByLoginName(refund[0]).getId();

				BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(
						payOrder.getOrderDetailsNo(), fromAgentid, agentId,
						Transaction.STATUS_3);
				BigDecimal hasRefundAmout = transactionDAO
						.getRefundTotalAmount(payOrder.getOrderDetailsNo(),
								agentId, fromAgentid, Transaction.STATUS_11);

				BigDecimal hasRefundAmout2 = transactionDAO
						.getRefundTotalAmount(payOrder.getOrderDetailsNo(),
								fromAgentid, agentId, Transaction.STATUS_11);

				if (hasRefundAmout.add(new BigDecimal(refund[4])).compareTo(
						hasPayAmout) == 1) {
					isRefundChargeFee = true;
				}
				if (hasRefundAmout2.add(new BigDecimal(refund[4])).compareTo(
						payOrder.getPaymentPrice()) == 1) {
					isRefundChargeFee = true;
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

			} else if (i > 1 && refund.length == 6) { // 分润退款信息,格式:
				// 转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由
				if (refund[0] != null && !refund[0].equals("")
						&& refund[2] != null && !refund[2].equals("")) {
					if (refund[0].equals(refund[2])) {
						isSame = true;
					}
				}

				if (refund[4] != null && !"".equals(refund[4].trim())) { // 判断金额是否为空
					amount = new BigDecimal(refund[4].trim());
					if (amount.doubleValue() > 0) {
						// get the payout agent info
						if (refund[1] != null && !"".equals(refund[1].trim())) {
							payoutAgent = agentDAO.getAgentById(Long
									.parseLong(refund[1].trim()));
						} else {
							payoutAgent = agentDAO.getAgentByEmail(refund[0]);
						}
						// get the incept agent info
						if (refund[3] != null && !"".equals(refund[3].trim())) {
							inceptAgent = agentDAO.getAgentById(Long
									.parseLong(refund[3].trim()));
						} else {
							inceptAgent = agentDAO.getAgentByEmail(refund[2]);
						}
					}
				}

				long agentId = agentDAO.getAgentByLoginName(refund[0]).getId();

				BigDecimal hasPayAmout = transactionDAO.getRefundTotalAmount(
						payOrder.getOrderDetailsNo(), fromAgentid, agentId,
						Transaction.STATUS_3);
				BigDecimal hasRefundAmout = transactionDAO
						.getRefundTotalAmount(payOrder.getOrderDetailsNo(),
								agentId, fromAgentid, Transaction.STATUS_11);
				BigDecimal hasRefundAmout2 = transactionDAO
						.getRefundTotalAmount(payOrder.getOrderDetailsNo(),
								fromAgentid, agentId, Transaction.STATUS_11);

				if (hasRefundAmout.add(new BigDecimal(refund[4])).compareTo(
						hasPayAmout) == 1) {
					isRefundChargeFee = true;
				}
				if (hasRefundAmout2.add(new BigDecimal(refund[4]))
						.doubleValue() > payOrder.getPaymentPrice()
						.doubleValue()) {
					isRefundChargeFee = true;
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

			try {
				if (payoutAgent != null && payoutAgent.getId() > 0
						&& amount.compareTo(new BigDecimal("0")) > 0) {
					// 获取AgentBalance对象拥有获取金额
					AgentBalance ab = agentDAO.getAgentBalance(payoutAgent.getId());
					System.out.println("同步卖家信用金额：" + ab.getCreditBalance());
					if (ab.getCreditBalance() != null
							&& Gateway.equals(ab.getCreditBalance(), amount)) {
						if (isSame) {
							sb.append(gatewayMessage
									.getSame_Account_No_Need_Process()); // 相同帐号不被处理
						} else {
							if (!isFlag) { // 如果detail_data
								// 参数的金额大于0,就不用付到平台,直接到下面打到原来买家的帐号上
								buildTransaction(refundOrder, payoutAgent,
										inceptAgent, Transaction.TYPE_181,
										amount, mark, Transaction.STATUS_11);
							} else {

								// 卖家把退款打到圈主次账号上
								buildCreditRefundTransaction(refundOrder,
										payoutAgent, inceptAgent,
										Transaction.TYPE_181, amount, mark,
										Transaction.STATUS_11);
							}
							sb.append(gatewayMessage.getSuccess());
							isRefund = true;
						}

					} else {
						sb.append(gatewayMessage
								.getTxn_Result_Account_Balance_Not_Enough()); // 账户余额不足。
					}
				} else {
					if (amount.compareTo(new BigDecimal("0")) <= 0)
						sb.append(gatewayMessage.getZero_No_Need_Process()); // 退款金额为零，不予处理。
					else
						sb.append(gatewayMessage
								.getTxn_Result_No_Such_Account()); // 帐号不存在
				}
			} catch (Exception ex) {
				sb.append(gatewayMessage.getUnknow_Error()); // 未知异常
			}

		}
		return sb.toString();
	}

	/**
	 * 信用支付退款参数验证方法
	 */
	public String credit_validateRefund(HashMap<String, String> map,
			String url, GatewayMessage gatewayMessage) throws Exception {
		try {
			String partner = map.get("partner");
			// 验证退款URL参数,返回true or false

			String message = validateRefundParameter(map, gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) {
				return message;
			}

			// validate partner 验证partner是否存在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return gatewayMessage.getPartner_Not_Sign_Protocol();
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return gatewayMessage.getValidate_Sign_Fail();
			}

			// buyer_fee_detail_data 验证数据集参数
			List<String[]> listRefund = AnalyseParameter
					.analyseRefundParameter(map.get("buyer_fee_detail_data"));
			if (listRefund == null || listRefund.size() < 1) {
				return gatewayMessage.getDetail_Data_Format_Error();
			}

			// transaction verify
			OrderDetails order = null;
			BigDecimal refundFee = new BigDecimal("0");
			for (int i = 0; i < listRefund.size(); i++) {
				String[] refund = listRefund.get(i);
				BigDecimal allowBalance = new BigDecimal("0");
				// 先验证交易退款信息,格式:原付款交易号^退交易金额^退款理由
				if (i == 0) {
					// order details 验证订单是否存在
					order = transactionDAO.queryOrderDetailByTransNo(refund[0],
							partner, Transaction.STATUS_11);
					if (order == null || order.getId() == 0
							|| order.getStatus() != Transaction.STATUS_11) {
						if (order != null
								&& order.getStatus() == Transaction.STATUS_4) {
							return gatewayMessage.getTrade_Has_Closed();
						}
						return gatewayMessage.getTrade_Not_Exists();
					}
					System.out.println(order.getId());
					// 验证退款金额不能大于支付金额,这里根据订单ID和平台ID查出所有交易记录,取第一条,即第一条默认为卖家的信息
					// transactionDAO.queryTransactionByOrderAndFromAgent(order.getId(),avouchAccount);
					Transaction trans = transactionDAO
							.queryTransactionByOrderAndFromAgent(order.getId());
					if (trans == null || trans.getToAgent() == null) {
						return gatewayMessage.getTrade_Not_Exists(); // 交易不存在
					}

					if (refund[1] != null && !"".equals(refund[1])) {
						refundFee = new BigDecimal(refund[1].trim());
					}
				}

				else if (i >= 1 && refund.length == 6) {
					// payout corterie verify

					AgentCoterie payoutCoterie = null;
					AgentCoterie inceptCoterie = null;
					if (refund[0] != null && !"".equals(refund[0])) {
						// payout corterie verify
						payoutCoterie = agentCoterieDAO
								.getAgentCoterieByCoterieAndAgent(partner, "",
										refund[0]);
					}

					if (refund[1] != null && !"".equals(refund[1])) {
						// payout corterie verify
						payoutCoterie = agentCoterieDAO
								.getAgentCoterieByCoterieAndAgent(partner,
										refund[1], "");
					}

					if (payoutCoterie == null || payoutCoterie.getId() == 0) {
						return gatewayMessage.getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}

					if (refund[2] != null && !"".equals(refund[2])) {
						// payout corterie verify
						inceptCoterie = agentCoterieDAO
								.getAgentCoterieByCoterieAndAgent(partner, "",
										refund[2]);
					}

					if (refund[3] != null && !"".equals(refund[3])) {
						// payout corterie verify
						inceptCoterie = agentCoterieDAO
								.getAgentCoterieByCoterieAndAgent(partner,
										refund[3], "");
					}

					if (inceptCoterie == null || inceptCoterie.getId() == 0) {
						return gatewayMessage.getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
					// check the allow balance
					// 同步信用金额

					AgentBalance ab = agentDAO.getAgentBalance(payoutCoterie
							.getAgent().getId());
					if (ab.getCreditBalance() != null) {
						allowBalance = ab.getCreditBalance();
					}
				} else {
					return gatewayMessage.getRefund_Sign_Etrance();
				}
			}
			// pay_fee_detail_data 验证数据集参数
			List<String[]> listRefund1 = AnalyseParameter
					.analyseRefundParameter(map.get("pay_fee_detail_data"));
			if (listRefund1 == null || listRefund1.size() < 1) {
				return gatewayMessage.getDetail_Data_Format_Error();
			}
			// transaction verify
			OrderDetails order1 = null;
			BigDecimal refundFee1 = new BigDecimal("0");
			for (int i = 0; i < listRefund.size(); i++) {
				String[] refund = listRefund.get(i);
				BigDecimal allowBalance = new BigDecimal("0");
				// 先验证交易退款信息,格式:原付款交易号^退交易金额^退款理由
				if (i == 0) {
					// order details 验证订单是否存在
					order1 = transactionDAO.queryOrderDetailByTransNo(
							refund[0], partner, Transaction.STATUS_11);
					if (order1 == null || order1.getId() == 0
							|| order1.getStatus() != Transaction.STATUS_11) {
						if (order1 != null
								&& order1.getStatus() == Transaction.STATUS_4) {
							return gatewayMessage.getTrade_Has_Closed();
						}
						return gatewayMessage.getTrade_Not_Exists();
					}

					Transaction trans = transactionDAO
							.queryTransactionByOrderAndPayFee(order1.getId());
					if (trans == null || trans.getToAgent() == null) {
						return gatewayMessage.getTrade_Not_Exists(); // 交易不存在
					}

					if (refund[1] != null && !"".equals(refund[1])) {
						refundFee1 = new BigDecimal(refund[1].trim());
					}
				}

				else if (i >= 1 && refund.length == 6) {
					// payout corterie verify

					AgentCoterie payoutCoterie = agentCoterieDAO
							.getAgentCoterieByCoterieAndAgent(partner,
									refund[1], refund[0]);
					if (payoutCoterie == null || payoutCoterie.getId() == 0) {
						return gatewayMessage.getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}
					// incept corterie verify
					AgentCoterie inceptCoterie = agentCoterieDAO
							.getAgentCoterieByCoterieAndAgent(partner,
									refund[3], refund[2]);
					if (inceptCoterie == null || inceptCoterie.getId() == 0) {
						return gatewayMessage.getRoyaltyer_Not_Sign_Protocol(); // 分润方未签协议
					}

					// check the allow balance
					AgentBalance ab = agentDAO.getAgentBalance(payoutCoterie
							.getAgent().getId());
					if (ab.getCreditBalance() != null) {
						allowBalance = ab.getCreditBalance();
					}
				} else {
					return gatewayMessage.getRefund_Sign_Etrance();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		// return ExceptionConstant.SUCCESS;
		return gatewayMessage.getSuccess();
	}

	public void buildCreditRefundTransaction(OrderDetails orderDetails,
			Agent fromAgent, Agent toAgent, Long type, BigDecimal paymentPrice,
			String remark, long status) throws Exception {
		try {
			// add the transaction
			Transaction transaction = new Transaction();
			transaction.setOrderDetails(orderDetails);
			transaction.setNo(noUtil.getTransactionNo());
			transaction.setAmount(paymentPrice);
			transaction.setType(type);
			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);
			transaction
					.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(status);
			if (status == Transaction.STATUS_11) {
				transaction.setRefundsDate(new Timestamp(System
						.currentTimeMillis()));
				transaction.setRefundsStatus(status);
			}
			transaction.setMark(remark);
			if (transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue() != 0) {
				transactionDAO.saveTransaction(transaction);
				fromAgent.setUpdateDate(new Timestamp(System
						.currentTimeMillis()));
				// 获取AgentBalance对象拥有获取金额
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				fromAgent.setAllowBalance(ab.getCreditBalance()
						.subtract(paymentPrice));
				System.out.println(">>>>>>>>>>>>>>>>>>>>fromAgent = "
						+ fromAgent.getLoginName());

				// add balance for to agent
				toAgent
						.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getCreditBalance() != null) {
					toAgent.setCreditBalance(ab2.getCreditBalance().add(
							paymentPrice));
				} else {
					toAgent.setCreditBalance(paymentPrice);
				}
				System.out.println(">>>>>>>>>>>>>>>>>>>>toAgent = "
						+ toAgent.getLoginName());
			} else {
				// 这里是等于0的transaction，不记录到数据库里。
				System.out.println("----------------old amount = "
						+ transaction.getAmount()
						+ ",---------new amount="
						+ transaction.getAmount().setScale(2,
								BigDecimal.ROUND_HALF_UP));
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void updateOrderDetailsForAgent(OrderDetails orderDetails)
			throws AppException {
		transactionDAO.updateOrderDetails(orderDetails);
	}

	public long saveActionLog(ActionLog actionLog) throws Exception {
		return actionLogDAO.save(actionLog);
	}

	public long save(AgentBind agentBind) throws AppException {
		return agentBindDAO.save(agentBind);
	}

	public Agent getPartnerAccount(String partner) throws AppException {
		return agentDAO.queryByPartner(partner);
	}

	/**
	 * 查找到圈名
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterieName(long id) throws AppException {
		return agentBindDAO.getByCoterieName(id);
	}

	public AgentBind queryAgentBindByAgentId(long agentId, long bindAgentId)
			throws AppException {
		return agentBindDAO.queryAgentBindByAgentId(agentId, bindAgentId);
	}

	public void deleteById(long id) throws AppException {
		agentBindDAO.deleteById(id);
	}

	public Agent getAgentByEmail(String loginName) throws AppException {
		return agentBindDAO.getAgentByEmail(loginName);
	}

	public List getAgentCoterieById(long agentId) throws AppException {
		return agentBindDAO.getAgentCoterieById(agentId);
	}

	public Agent getAgentById(long id) throws AppException {
		return agentBindDAO.getAgentById(id);
	}

	public Agent getAgent(String eamil, String password) throws AppException {
		Agent agt = agentDAO.queryByEmailAndPassword(eamil, MD5
				.encrypt(password));
		return agt;
	}

	/**
	 * 验证退款参数格式
	 * 
	 * @param map
	 * @param gatewayMessage
	 * @return
	 * @throws AppException
	 */
	public static String validateRefundParameter(HashMap<String, String> map,
			GatewayMessage gatewayMessage) throws AppException {
		// validate royalty parameters
		List<String[]> result = null;
		List<String[]> result1 = null;
		System.out
				.println("validateRefundParameter-------" + map.get("buyer_fee_detail_data"));
		if (map.get("buyer_fee_detail_data") == null) {
			return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误
		}
		if (map.get("pay_fee_detail_data") == null) {
			return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误
		}
		// 调用analyseRefundParameter方法参数分割交易集
		result = AnalyseParameter.analyseRefundParameter(map
				.get("buyer_fee_detail_data"));// 验证单笔数据集
		// 循环输出交易集
		for (String re : result.get(0)) {
			System.out.println("-------------交易参数：" + re);
		}
		if (result == null || result.size() < 1) {
			return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误
		}

		// 调用analyseRefundParameter方法参数分割交易集
		result1 = AnalyseParameter.analyseRefundParameter(map
				.get("pay_fee_detail_data"));// 验证单笔数据集
		// 循环输出交易集
		for (String re : result1.get(0)) {
			System.out.println("-------------交易参数：" + re);
		}
		if (result1 == null || result1.size() < 1) {
			return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误
		}

		// validate quantity
		if (!AnalyseParameter.checkType(map.get("batch_num"))) { // 验证“单笔数据集”里面的交易总笔数
			return gatewayMessage.getBatch_Num_Error(); // 外部传入总笔数错误。
		}
		if (Integer.parseInt(map.get("batch_num")) <= 0) {
			return gatewayMessage.getBatch_Num_Error(); // 外部传入总笔数错误。
		}
		String[] agentInfo = null;
		// double partnerRefundFee=0d;
		// double coterieRefundFee=0d;
		System.out.println("----------------resut集合个数" + result.size());
		for (int i = 0; i < result.size(); i++) {
			// for(String[] agentInfo:result){

			agentInfo = result.get(i);
			if (i == 0) {
				System.out.println("----------------resut集合：" + (i + 1));
				// 交易退款信息为：“原付款交易号^退交易金额^退款理由”交易最少参数为3个
				if (agentInfo == null || agentInfo.length < 3) {
					return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误。
				}
				// 验证退款金额 要为数字
				if (agentInfo[1] != null && !"".equals(agentInfo[1])) {
					if (!AnalyseParameter.checkFee(agentInfo[1])) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
					// partnerRefundFee=Double.parseDouble(agentInfo[1]);
				}
				// 收费退款信息为：“被收费人Email^被收费人userId^退款金额^退款理由”， 参数长度为4
			} else if (i == 1 && agentInfo.length == 4) {
				System.out.println("----------------resut集合" + (i + 1));
				// email和id 至少有一个
				if ((agentInfo[0] == null || "".equals(agentInfo[0]))
						&& (agentInfo[1] == null || "".equals(agentInfo[1]))) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}

				// 验证email有效性
				if (agentInfo[0] != null && !"".equals(agentInfo[0])) {
					if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				// 验证退款金额
				if (agentInfo[2] != null && !"".equals(agentInfo[2])) {
					if (!AnalyseParameter.checkFee(agentInfo[2])) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
					// coterieRefundFee+=Double.parseDouble(agentInfo[2]);
				}

			} else {
				// 分润退款信息为：“转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由”
				// 参数长度为6
				if (agentInfo == null || agentInfo.length < 6) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
				// 转出人email和id 至少有一个
				if ((agentInfo[0] == null || "".equals(agentInfo[0]))
						&& (agentInfo[1] == null || "".equals(agentInfo[1]))) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
				// 转出人 验证email有效性
				if (agentInfo[0] != null && !"".equals(agentInfo[0])) {
					if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				// 转入人email和id 至少有一个
				if ((agentInfo[2] == null || "".equals(agentInfo[2]))
						&& (agentInfo[3] == null || "".equals(agentInfo[3]))) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
				// 转入人 验证email有效性
				if (agentInfo[2] != null && !"".equals(agentInfo[2])) {
					if (!AnalyseParameter.checkEmail(agentInfo[2], false)) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				// validate 退款金额^
				if (agentInfo[4] != null && !"".equals(agentInfo[4])) {
					if (!AnalyseParameter.checkFee(agentInfo[4])) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
					// coterieRefundFee+=Double.parseDouble(agentInfo[4]);
				}
			}
		}
		System.out.println("--------------验证通过");
		return gatewayMessage.getSuccess(); // 验证通过
	}

	/**
	 * 圈中圈验证即时到账接口参数
	 * 
	 * @param map
	 * @param gatewayMessage
	 * @return
	 * @throws AppException
	 */
	public static String paymentParameterVerifyPen(HashMap<String, String> map,
			GatewayMessage gatewayMessage) throws AppException {
		BigDecimal totalFee = new BigDecimal("0");
		BigDecimal balance = new BigDecimal("0");
		System.out
				.println("进入 验证接口参数 analyseparameter类 paymentParameterVerify 方法,map返回 "
						+ map);
		if (map == null) {
			return gatewayMessage.getCommit_Url_Is_Null(); // 提交URL参数为空
		}
		// validate payment type 验证支付类型
		if (!AnalyseParameter.checkType(map.get("payment_type"))) {
			return gatewayMessage.getPayment_Type_Error(); // 支付类型参数错误。
		}
		// validate royalty type 验证提成类型,目前只支持一种类型：卖家给第三方提成（10）
		// if (!AnalyseParameter.checkType(map.get("royalty_type"))) {
		// return gatewayMessage.getRoyalty_Type_Error(); //
		// 提成类型参数错误。-*-----------
		// }

		// validate quantity 验证购买数量,必须大于0
		if (map.get("quantity") != null) {
			if (!AnalyseParameter.checkType(map.get("quantity"))) {
				return gatewayMessage.getQuantity_Error(); // 购买数量参数错误。-----------
			}
			if (Integer.parseInt(map.get("quantity")) <= 0) {
				return gatewayMessage.getQuantity_Error(); // 购买数量参数错误。
			}
		}

		// if (map.get("price") == null) {
		// return gatewayMessage.getPrice_Error(); // 商品单价参数错误。
		// }

		if (map.get("price") != null) {
			if (!AnalyseParameter.checkFee(map.get("price"))) {
				return gatewayMessage.getPrice_Error(); // 商品单价参数错误。
			}
		}
		// validate total fee 验证交易金额,单位为RMB Yuan
		// 0.01～1000000.00,上限取决于买卖双方的即时交易金额限制
		if (!AnalyseParameter.checkFee(map.get("total_fee"))) {
			return gatewayMessage.getTotal_Fee_Error(); // 交易金额参数错误。
		}
		totalFee = new BigDecimal(map.get("total_fee"));
		System.out.println("---------------交易金额:" + totalFee);

		if (map.get("buyer_fee") == null || "".equals(map.get("buyer_fee"))) {
			return gatewayMessage.getBUYER_FEE_ERROR();
		} else {
			if (!AnalyseParameter.checkFee(map.get("buyer_fee")))
				return gatewayMessage.getBUYER_FEE_ERROR();
		}
		if (map.get("pay_fee") == null || "".equals(map.get("pay_fee"))) {
			return gatewayMessage.getPAY_FEE_ERROR();
		} else {
			if (!AnalyseParameter.checkFee(map.get("pay_fee")))
				return gatewayMessage.getPAY_FEE_ERROR();
		}

		// validate seller email 验证email
		if (!AnalyseParameter.checkEmail(map.get("seller_email"), false)) {
			return gatewayMessage.getSeller_Email_Error(); // 卖家帐号参数错误。
		}
		// validate seller
		if ((map.get("seller_email") == null || "".equals(map.get(
				"seller_email").trim()))
				&& (map.get("seller_id") == null || "".equals(map
						.get("seller_id")))) {
			return gatewayMessage.getSeller_Email_Error(); // 卖家帐号参数错误。
		}

		// validate buyer parameters 验证买家信息集
		List<String[]> buyerResult = null;
		String buyerParams = map.get("buyer_parameters");
		if (buyerParams != null && buyerParams.length() > 1000) {
			return gatewayMessage.getBuyer_Parameters_Error(); // 买家信息集参数错误。
		}
		buyerResult = AnalyseParameter.analyseRoyaltyParameter(buyerParams);
		BigDecimal buyerTotalPrice = new BigDecimal("0");
		if (buyerResult != null && buyerResult.size() > 0) {
			for (String[] buyerInfo : buyerResult) {
				if (buyerInfo == null || buyerInfo.length < 2) { // 每一条买家信息格式:买家账号1^付款金额1,判断长度不能小于2
					return gatewayMessage.getBuyer_Parameters_Error(); // 买家信息集参数错误。
				}

				if (!AnalyseParameter.checkEmail(buyerInfo[0], false)) {
					return gatewayMessage.getBuyer_Parameters_Error(); // 买家信息集参数错误。
				}

				if (!AnalyseParameter.checkFee(buyerInfo[1])) {
					return gatewayMessage.getBuyer_Parameters_Error(); // 买家信息集参数错误。
				}
				buyerTotalPrice = buyerTotalPrice.add(new BigDecimal(
						buyerInfo[1]));
			}
		}

		// validate royalty parameters 验证提成信息集
		List<String[]> result = null;
		String royaltyParams = map.get("royalty_parameters");
		if (royaltyParams != null && royaltyParams.length() > 1000) {
			return gatewayMessage.getRoyalty_Parameters_Error();
		}
		result = AnalyseParameter.analyseRoyaltyParameter(royaltyParams);
		if (result != null && result.size() > 0) {
			for (String[] agentInfo : result) {
				if (agentInfo == null || agentInfo.length < 3) { // 每一笔提成信息格式:分润收款账号1^提成金额1^说明1
					// ,判断长度不能小于3
					return gatewayMessage.getRoyalty_Parameters_Error();
				}
				// validate royalty email 验证分润收款帐号是否正确
				if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
					return gatewayMessage.getRoyalty_Parameters_Error();
				}
				// validate royalty fee 验证提成金额
				if (!AnalyseParameter.checkFee(agentInfo[1])) {
					return gatewayMessage.getRoyalty_Parameters_Error();
				}
				// balance += Double.parseDouble(agentInfo[1]);
				balance = balance.add(new BigDecimal(agentInfo[1]));
			}
		}
		if (buyerResult != null && buyerResult.size() > 0) {
			if (totalFee.doubleValue() <= 0
					|| totalFee.add(buyerTotalPrice).compareTo(balance) == 1) { // 验证提交过来的交易金额是否大于0
				// 或者
				// 提成信息集里面的所有提成金额是否大于这个交易金额
				return gatewayMessage.getTotal_Fee_Or_Buyer_Fee_Error();
			}
		} else {
			if (totalFee.doubleValue() <= 0 || balance.compareTo(totalFee) == 1) { // 验证提交过来的交易金额是否大于0
				// 或者
				// 提成信息集里面的所有提成金额是否大于这个交易金额
				return gatewayMessage.getTotal_Fee_Or_Royalty_Fee_Error();
			}
		}
		return gatewayMessage.getSuccess();
	}

	// 查找没还款的信用交易
	public List getCreditPayListByAgent(Agent buyerAgent) throws AppException {
		return transactionDAO.getCreditPayListByAgent(buyerAgent);
	}

	/**
	 * 根据合作伙伴查找到圈名
	 * 
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterie(String partner) throws AppException {
		return agentBindDAO.getByCoterie(partner);
	}

	public Transaction getTransactionByAgent(Agent agent) throws AppException {
		return transactionDAO.getTransactionByAgent(agent);
	}
	
	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo)
    	throws AppException
	{
		return agentBindDAO.queryOrderDetailByOrderNoAndPartner(orderNo);
	}

	public void setActionLogDAO(ActionLogDAO actionLogDAO) {
		this.actionLogDAO = actionLogDAO;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public Agent getSeller() {
		return seller;
	}

	public void setSeller(Agent seller) {
		this.seller = seller;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setCoterieDAO(CoterieDAO coterieDAO) {
		this.coterieDAO = coterieDAO;
	}

	public void setAgentBindDAO(AgentBindDAO agentBindDAO) {
		this.agentBindDAO = agentBindDAO;
	}

	public void setAgentCoterieDAO(AgentCoterieDAO agentCoterieDAO) {
		this.agentCoterieDAO = agentCoterieDAO;
	}
}
