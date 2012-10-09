package com.fordays.fdpay.cooperate.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.fordays.fdpay.agent.dao.AgentDAOImpl;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.base.Constant;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.ConnAnalyseParameter;
import com.fordays.fdpay.cooperate.CoterieUtilURL;
import com.fordays.fdpay.cooperate.FreezeMessage;
import com.fordays.fdpay.cooperate.Freeze_Refund;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.GatewayMessageFactory;
import com.fordays.fdpay.cooperate.MainTask;
import com.fordays.fdpay.cooperate.UtilURL;
import com.fordays.fdpay.cooperate.unfreezeDirectPayment;
import com.fordays.fdpay.cooperate.action.Gateway;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.cooperate.dao.AgentBindDAO;
import com.fordays.fdpay.system.dao.TaskTimestampDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.fordays.fdpay.transaction.dao.TransactionDAOImpl;
import com.neza.base.NoUtil;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * 
 * @author 接口方法
 */
public class FreezeBizImp implements FreezeBiz {

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

	public void setTransactionManager(HibernateTransactionManager thargeManager) {
		this.transactionTemplate = new TransactionTemplate(thargeManager);
	}

	public void setTasktimestampDAO(TaskTimestampDAO tasktimestampDAO) {
		this.tasktimestampDAO = tasktimestampDAO;
	}

	/*
	 * 根据外部订单号 查询交易信息
	 */
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

	/*
	 * 交易委托冻结接口参数验证方法
	 */
	public String validate_refund_freeze(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception {
		System.out.println(">>>>>>>>>>交易委托冻结验证开始");
		String partner = null; // 合作伙伴号
		Coterie coterie = null; // 平台账户圈
		OrderDetails order = null; // 原来的交易订单
		try {
			String message = ConnAnalyseParameter.validate_freeze_parameter(
					map, freezeMessage);

			if (!freezeMessage.getSUCCESS().equals(message)) {
				return message;
			}

			/** 验证partner是否存在商户圈里面 * */
			partner = map.get("partner");
			if (partner != null && !"".equals(partner.trim()))
				coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0) {
				return freezeMessage.getPARTNER_NOT_SIGN_PROTOCOL();
			}

			/** 验证签名 * */
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return freezeMessage.getVALIDATE_SIGN_FAIL();
			}

			/** * 验证订单 ** */
			if (map.get("trade_no") != null
					&& !"".equals(map.get("trade_no").trim()))
				order = transactionDAO.queryOrderDetailByorderDetailsNo(map
						.get("trade_no"), partner);
			if (order == null || order.getId() == 0) {
				return freezeMessage.getTRADE_ITEM_NOT_EXIST(); // 交易部存在
			}

			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>交易委托冻结验证结束");
		return freezeMessage.getSUCCESS();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see 交易委托冻结订单方法
	 */
	public String credit_freeze_order(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage, FreezeBiz freezebiz) throws Exception {
		System.out.println(">>>>>>>>>>交易委托冻结订单方法");
		Timestamp sysDate = null;
		OrderDetails payOrder = null; // 原交易订单
		OrderDetails refundOrder = null; // 委托冻结订单
		ActionLog actionLog = null; // 日志
		StringBuffer result = new StringBuffer(); // 返回的结果
		String outUrl = null; // 异步回调的URL参数集合
		String myUrl = null; // 异步回调url
		Agent freeAgent = null; // 冻结账户
		boolean repeat = true; // 重复冻结订单号

		String charset = "GBK";
		try {
			if (map.get("_input_charset") != null
					&& !"".equals(map.get("_input_charset"))) {
				charset = map.get("_input_charset");
			}
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");
			// 原交易订单
			payOrder = transactionDAO.queryOrderDetailByorderDetailsNo(map
					.get("trade_no"), map.get("partner"));
			// 根据partner查出平台帐号
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));

			long paymentType = OrderDetails.getOrderDetailsType(5); // 现金到冻结
			List<String[]> listFreeze = ConnAnalyseParameter
					.analyseRoyaltyParameter(map.get("freeze_details"));
			String[] reFree = null;
			for (int i = 0; i < listFreeze.size(); i++) { // 有多少条冻结信息就生成多少条订单
				reFree = listFreeze.get(i);
				OrderDetails o = transactionDAO.queryOrderLikeOrderNo(reFree[0].trim());
				if (o != null && o.getTransactions().size() > 0) {
					repeat = false;   //验证冻结订单号是否重复
				}
				if (reFree[1] != null && !"".equals(reFree[1].trim())) {
					freeAgent = agentDAO.getAgentByEmail(reFree[1].trim());
				} else {
					freeAgent = agentDAO
							.getAgentById(Long.parseLong(reFree[2]));
				}
				
				if(freeAgent!=null&&repeat){
					//外部订单号   把冻结订单号加上原支付交易订单号 以~分割  
					String on = reFree[0]+"~"+payOrder.getOrderDetailsNo();
					
					// 添加新定单（信用到信用） 这条订单是针对平台账户退款给平台
					refundOrder = appendOrder(payOrder, paymentType, url, payOrder
							.getPaymentPrice().toString(), OrderDetails.BUY_TYPE_6);
					// 修改订单相关信息
					refundOrder.setOrderDetailsNo(noUtil.getOrderDetailsNo());//新生成订单号
					refundOrder.setOrderNo(on); // 外部订单号
					refundOrder.setShopPrice(new BigDecimal(reFree[3]));
					refundOrder.setCreateDate(sysDate);
					refundOrder.setFinishDate(sysDate);
					refundOrder.setPaymentPrice(new BigDecimal(reFree[3]));
					refundOrder.setAgent(freeAgent);
					refundOrder.setShopName("【委托冻结】");
				}
				System.out.println("冻结账户email:"+reFree[1]);
				System.out.println(">>>>>>>>>>开始冻结");
				if (i != 0)
					result.append("|");

				String sb = credit_freeze_tran(map, url, freezeMessage,
						refundOrder,payOrder, reFree,repeat, sysDate); // 异步通知url

				// 执行冻结明细 返回每一条冻结的处理结果
				System.out.println(">>>>>>>>>>冻结结束");

				outUrl = UtilURL.createFreezeUrl(
						map,
						sb,
						refundOrder,
						coterie.getSignKey(),
						DateUtil.getTimestamp(DateUtil
								.getDateString("yyyy-MM-dd HH:mm:ss"),
								"yyyy-MM-dd HH:mm:ss")).toString();

				// 开启线程执行异步通知
				myUrl = map.get("notify_url") + "?" + outUrl;
				Freeze_Refund Freeze = new Freeze_Refund(freezebiz, outUrl);
				Freeze.setEncoding(charset);
				Freeze.setMap(map);
				Freeze.setUrl(myUrl);
				Freeze.setUserAgent(freeAgent);
				Freeze.setOrderNo(refundOrder.getOrderNo());
				MainTask.put(Freeze);

				result.append(sb);
			}
			// set action log
			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("notify_url") + "?" + result);
			aLog.setOrderDetails(refundOrder);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);
			actionLogDAO.save(aLog);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return result.toString();
	}

	/*
	 * 交易委托冻结明细方法
	 */
	public String credit_freeze_tran(HashMap<String, String> map, String url,
		FreezeMessage freezeMessage, OrderDetails order,OrderDetails payorder,
			String[] reFree,boolean repeat,Date date) throws AppException {
		Agent freeAgent = null; // 冻结账户
		BigDecimal hasPayAmout = new BigDecimal(0);// 交易中的分润金额
		BigDecimal freeAmout = new BigDecimal(0); // 本次要冻结的金额
		BigDecimal alreadyFreeAmout = new BigDecimal(0);// 已冻结的金额
		boolean notExist = true;// 要冻结的账户不存在
		boolean greatrFee = true; // 用于判断是否冻结金额过大
		boolean noEnoughFee = true; // 用于判断是否冻结金额不足
		String msgAgentEmail = "";
		String msgAgentId = "";
		String state = "";
		String msg = "";
		StringBuffer sb = new StringBuffer(); // 冻结结果明细
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (reFree[1] != null && !"".equals(reFree[1].trim())) {
			msgAgentEmail = reFree[1];
			freeAgent = agentDAO.getAgentByEmail(reFree[1].trim());
		} else {
			msgAgentId = reFree[2];
			freeAgent = agentDAO.getAgentById(Long.parseLong(reFree[2]));
		}
		if (freeAgent == null || freeAgent.getId() == 0) { // 要冻结的账户不存在
			notExist = false;
		}

		freeAmout = new BigDecimal(reFree[3].trim());// 账户本次要冻结的金额
		if(payorder.getRepayAmount()!=null){
			if ((freeAmout.add(payorder.getRepayAmount())).compareTo(
				payorder.getPaymentPrice()) == 1) {
					greatrFee = false; // 本次要冻结的金额加上已冻结的金额不能大于交易金额
			}
		}
		if (freeAmout.compareTo(new BigDecimal("0")) == 1) {
			if (repeat) {
				if (notExist) {
					System.out.println("账户本次要冻结的金额freeAmout=" + freeAmout);
					hasPayAmout = transactionDAO.getDirectRefundTotalAmount(payorder
						.getOrderDetailsNo(), 0, freeAgent.getId(),
							Transaction.STATUS_3);// 账户的分润金额
					System.out.println("账户的分润金额hasPayAmout=" + hasPayAmout);
					
					//账户在payorder这条支付交易中冻结的所有交易
					List freezeOrders = transactionDAO.queryOrderLikeOrderDetailsNo(payorder.getOrderDetailsNo());
				
					if(null!=freezeOrders&&freezeOrders.size()>0){
						alreadyFreeAmout = transactionDAO.getAlreadyAmount(
							freezeOrders, freeAgent.getId(), freeAgent.getId(),
								Transaction.STATUS_3, Transaction.TYPE_198);// 账户已冻结的金额
					}
					System.out.println("账户已冻结的金额alreadyFreeAmout=" + alreadyFreeAmout);
		
					if (freeAmout.compareTo(hasPayAmout.subtract// 还可冻金额=分润金额-(已冻结的金额)
							(alreadyFreeAmout)) == 1) {// 本次要冻结的金额不能大于还可冻金额
						greatrFee = false;
					}
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(freeAgent.getId());
					if (ab.getAllowBalance() == null
							|| !Gateway.equals(ab.getAllowBalance(), freeAmout)) {
						noEnoughFee = false;
					}
		
					try {
						if (hasPayAmout.compareTo(new BigDecimal("0")) == 1) { // 分润金额为0
							if (greatrFee) {
								if (noEnoughFee) {
									buildAllowTofreeTransaction(order,
										freeAgent, freeAgent,
											Transaction.TYPE_198, freeAmout,
												"委托冻结", Transaction.STATUS_3);
									//把冻结的金额累加到原支付交易的repayamount字段
									if(payorder.getRepayAmount()!=null)
										payorder.setRepayAmount(payorder.getRepayAmount().add(freeAmout));
									else
										payorder.setRepayAmount(freeAmout);
									state = "T";
									msg = freezeMessage.getSUCCESS(); // 冻结成功
								} else {
									state = "F";
									msg = freezeMessage
											.getAVAILABLE_AMOUNT_NOT_ENOUGH(); // 账户余额不足
								}
							} else {
								state = "F";
								msg = freezeMessage.getGREATER_FREEZE_MONEY();// 冻结金额过大
							}
		
						} else {
							BigDecimal closeSmount = transactionDAO
									.getDirectRefundTotalAmount(order
											.getOrderDetailsNo(), 0, freeAgent
											.getId(), Transaction.STATUS_4);// 交易是否关闭
							state = "F";
							if (closeSmount.compareTo(new BigDecimal("0")) == 1) {
								msg = freezeMessage
										.getTRADE_ITEM_STATUS_NOT_ALLOW();// 交易状态不允许（交易已关闭）
							} else {
								msg = freezeMessage
										.getTRADE_FOREIGN_TO_ACCOUNTNO();// 交易与账户无关
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("委托冻结失败："+e.getMessage());
					}
				}  else {
					state = "F";
					msg = freezeMessage.getACCOUNT_NOT_EXIST(); // 要冻结的账户不存在
					}
			} else {
				state = "F";
				msg = freezeMessage.getFREEZE_ORDER_NO_REPEAT(); // 重复冻结订单号
			}
		}else {
			state = "F";
			msg = freezeMessage.getAMOUNT_FREEZE_MONEY(); // 金额为0不予处理
		}
		sb.append(reFree[0]);
		sb.append("^");
		sb.append(msgAgentEmail);
		sb.append("^");
		sb.append(msgAgentId);
		sb.append("^");
		sb.append(reFree[3]);
		sb.append("^");
		sb.append(f.format(date));
		sb.append("^");
		sb.append(state);
		sb.append("^");
		sb.append(msg);
		order.setDetailsContent(msg);
		return sb.toString();
	}

	/*
	 * 交易委托冻结查询接口参数验证方法
	 */
	public String validate_refund_freeze_query(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception {
		System.out.println(">>>>>>>>>>交易委托冻结查询验证开始");
		String partner = null; // 合作伙伴号
		Coterie coterie = null; // 平台账户圈
		OrderDetails order = null; // 冻结订单
		try {
			/** 验证partner是否存在商户圈里面 * */
			if (null == map.get("partner") || "".equals(map.get("partner")))
				return freezeMessage.getPARTNER_CAN_NOT_NULL();
			partner = map.get("partner");
			if (partner != null && !"".equals(partner.trim()))
				coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0) {
				return freezeMessage.getPARTNER_NOT_SIGN_PROTOCOL();
			}

			/** 验证签名 * */
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return freezeMessage.getVALIDATE_SIGN_FAIL();
			}

			/** ***验证冻结订单号集合**** */
			if (null == map.get("freeze_order_no")
					|| "".equals(map.get("freeze_order_no")))
				return freezeMessage.getILLEGAL_ARGUMENT();

			String[] order_no = map.get("freeze_order_no").split("\\^");
			if ("".equals(order_no) || order_no.length < 1) {
				return freezeMessage.getILLEGAL_ARGUMENT();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>交易委托冻结查询验证结束");
		return freezeMessage.getSUCCESS();
	}

	// 查询冻结明细
	public String queryFreeResult(HashMap<String, String> map,
			FreezeMessage freezeMessage) throws Exception {
		System.out.println(">>>>>>>>>>执行交易委托冻结查询开始");
		StringBuffer sb = new StringBuffer();
		String[] order_no = map.get("freeze_order_no").split("\\^");
		String statu = "F";
		String resultCode = freezeMessage.getFREEZE_ITEM_NOT_EXIST(); // 明细不存在
		String price = "N/Q";
		String odno = "N/Q";
		String date = "N/Q";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (String no : order_no) {
			System.out.println("冻结订单号：" + no);
			// 根据冻结订单号查询冻结明细
			OrderDetails order = transactionDAO.queryOrderLikeOrderNo(no.trim());
			if (null != order&&order.getTransactions().size()>0) {
				if(order.getDetailsContent().equals(freezeMessage.getSUCCESS())){
					statu = "T";
				}
				resultCode = order.getDetailsContent();
				price = order.getPaymentPrice().toString();
				odno = order.getOrderDetailsNo();
				date = f.format(order.getCreateDate());
			} 
			sb.append(no);
			sb.append("^");
			sb.append(price);
			sb.append("^");
			sb.append(odno);
			sb.append("^");
			sb.append(date);
			sb.append("^");
			sb.append(statu);
			sb.append("^");
			sb.append(resultCode);
			sb.append("|");
		}
		String res = sb.toString();
		int i = res.lastIndexOf("|"); // 去掉最后一个”|“
		String result = res.substring(0, i);
		System.out.println("查询结果："+result);
		System.out.println(">>>>>>>>>>执行交易委托冻结查询结束");
		return result;
	}

	
	/* (non-Javadoc)
	 * @see 冻结标款验证
	 */
	public String validate_freeze(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage) throws Exception {
		System.out.println(">>>>>>>>>>冻结标款验证开始");
		Agent freeAgent = null; // 冻结账户
		try {
			String message = ConnAnalyseParameter.freezeParameterVerify(map,freezeMessage);
			if (!freezeMessage.getSUCCESS().equals(message)) {
				return message;
			}
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return freezeMessage.getPARTNER_CAN_NOT_NULL();
			/********* 验证合作伙伴是否在商户圈里面 ********/
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return freezeMessage.getPARTNER_NOT_SIGN_PROTOCOL(); // 合作伙伴不存在,返回
				// 平台商未签署协议。
			}
			
			/********* 验证签名 ********/ 
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return freezeMessage.getVALIDATE_SIGN_FAIL();//验证签名失败
			}


			/**********验证外部订单号**********/
			OrderDetails order = getOrderDetails(map.get("out_trade_no"), map.get("partner")); //根据外部订单号
			if(order!=null&&order.getId()>0&&order.getTransactions().size()>0){
				return freezeMessage.getDUPLICATE_BATCH_NO(); //重复的外部批次号
			}
			
			/*********验证冻结账户*********/
			if(map.get("freeze_id")!=null&&!"".equals(map.get("freeze_id").trim())){
				freeAgent = agentDAO.getAgentById(Long.parseLong(map.get("freeze_id")));
			}else{
				freeAgent = agentDAO.getAgentByLoginName(map.get("freeze_email"));
			}
			
			if(freeAgent==null||freeAgent.getId()<=0){
				return freezeMessage.getRECRUIT_EMAIL_ERROR();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>>冻结标款验证结束");
		return freezeMessage.getSUCCESS();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see 招标冻结标款订单方法
	 */
	public String credit_recruit_freeze_order(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage, FreezeBiz freezebiz) throws Exception {
		System.out.println(">>>>>>>>>>冻结标款订单方法开始");
		Timestamp sysDate = null;
		OrderDetails orderDetails = null; // 冻结订单
		ActionLog actionLog = null; // 日志
		String outUrl = null; // 异步回调的URL参数集合
		String myUrl = null; // 异步回调url
		Agent freeAgent = null; // 冻结账户
		BigDecimal freezeFee = new BigDecimal(map.get("total_fee"));
		
		String charset = "GBK";
		try {
			if (map.get("_input_charset") != null
					&& !"".equals(map.get("_input_charset"))) {
				charset = map.get("_input_charset");
			}
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");	
			// 根据partner查出平台帐号
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			//查出冻结账户
			if(map.get("freeze_id")!=null&&!"".equals(map.get("freeze_id").trim())){
				freeAgent = agentDAO.getAgentById(Long.parseLong(map.get("freeze_id")));
			}else{
				freeAgent = agentDAO.getAgentByLoginName(map.get("freeze_email"));
			}
			long paymentType = OrderDetails.getOrderDetailsType(5); // 现金到冻结
			long buyType = OrderDetails.BUY_TYPE_9;//招标冻结
			//添加新订单
			orderDetails = new OrderDetails();
			if(map.get("subject")!=null){
				orderDetails.setShopName(map.get("subject"));
			}else{
				orderDetails.setShopName("招标冻结");
			}
			orderDetails.setPartner(map.get("partner"));
			orderDetails.setOrderNo(map.get("out_trade_no"));
			orderDetails.setDetailsContent(map.get("body"));
			// orderDetails.setReferenceOrderDetailsNo();
			orderDetails.setOrderDetailsType(paymentType);
			orderDetails.setBuyType(buyType);
			if(freeAgent!=null){
				orderDetails.setAgent(freeAgent);
			}
			orderDetails.setStatus(Transaction.STATUS_3); // 交易成功
			orderDetails.setPaymentPrice(freezeFee);
			orderDetails.setShopPrice(freezeFee);
			// set quantity 设置数量
			orderDetails.setShopTotal(1L);
			// 设置邮费默认为 0.00
			orderDetails.setEmailPrice(new BigDecimal(0.00));
			orderDetails.setCreateDate(sysDate);
			orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
			transactionDAO.addOrderDetails(orderDetails); // 添加订单

			System.out.println(">>>>>>>>>开始冻结标款");
			String[] re = credit_recruit_freeze_tran(freeAgent, freezeMessage,
					orderDetails, freezeFee, sysDate); // 异步通知url
			// 执行冻结明细 返回每一条冻结的处理结果
			System.out.println(">>>>>>>>>冻结标款结束");

			outUrl = UtilURL.create_recruit_FreezeUrl(map,re,orderDetails,
					coterie.getSignKey()).toString();
	
			// set action log
			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("notify_url") + "?" + outUrl);
			aLog.setOrderDetails(orderDetails);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);
			actionLogDAO.save(aLog);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return outUrl;
	}
		
	/*
	 * 招标冻结标款明细方法
	 */
	public String[] credit_recruit_freeze_tran(Agent freeAgent,FreezeMessage freezeMessage, 
			OrderDetails order, BigDecimal freezeFee,Date date) throws AppException {
		boolean noEnoughFee = true; // 用于判断是否冻结金额不足
		String statu = "T";
		String msg = "SUCCESS";
		StringBuffer sb = new StringBuffer(); // 冻结结果明细
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(freeAgent.getId());
		if (ab.getAllowBalance() == null
				|| !Gateway.equals(ab.getAllowBalance(), freezeFee)) {
			noEnoughFee = false;
		}

		try {	
			if(freezeFee.compareTo(new BigDecimal(0))==1){
				if (noEnoughFee) {
					buildAllowTofreeTransaction(order,
							freeAgent, freeAgent,
							Transaction.TYPE_298, freezeFee,
							order.getShopName(), Transaction.STATUS_3);
				} else {
					statu = "F";
					msg = freezeMessage
							.getAVAILABLE_AMOUNT_NOT_ENOUGH(); // 账户余额不足
					System.out.println("账户余额不足");
				}	
			}else{
				statu = "F";
				msg = freezeMessage.getAMOUNT_FREEZE_MONEY(); // 金额为0不予处理
				System.out.println("金额为0不予处理");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String[] result = new String[2];
		result[0] = statu;
		result[1] = msg;
		return result;
	}
	
	
	/*
	 * 验证招标冻结支付接口validateFreezePay
	 */
	public String validateFreezePay(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage) throws Exception{
		System.out.println(">>>>>>>>>招标冻结支付验证开始");
		Agent fromAgent = null;
		Agent resultAgent = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try{
			String message = AnalyseParameter.paymentParameterVerify(map,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(message)) { return message; }
			
			if(map.get("partner")==null||"".equals(map.get("partner").trim()))
				return freezeMessage.getPARTNER_CAN_NOT_NULL();
			// validate partner 验证合作伙伴是否在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
			    || coterie.getAgent() == null || coterie.getAgent().getId() == 0l) { return freezeMessage
			    .getPARTNER_NOT_SIGN_PROTOCOL(); // 不存在,返回
			// 平台商未签署协议。
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
			    .get("sign_type"), coterie.getSignKey(), map.get("_input_charset"))) { return freezeMessage
			    .getVALIDATE_SIGN_FAIL(); }
			
			if(!map.get("payment_type").equals("4")){
				return gatewayMessage.getPayment_Type_Error();    //支付类型错误
			}
			
			// 根据seller_id 或者 seller_email 取得中标方的信息
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
			
			// 根据seller_id 或者 seller_email 取得中标方的信息
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

			// 根据buyer_id 或者 buyer_email 取得招标方的信息
			if (map.get("buyer_id") != null
			    && !"".equals(map.get("buyer_id").trim()))
			{
				fromAgent = agentDAO.getAgentById(Long.parseLong(map.get("buyer_id")
				    .trim()));
			}
			else if (map.get("buyer_email") != null)
			{
				fromAgent = agentDAO.getAgentByEmail(map.get("buyer_email"));
			}
			
			OrderDetails order=null;
			// 验证订单是否已经存在
			if(map.get("out_trade_no")!=null&&!"".equals(map.get("out_trade_no"))){
				order = getOrderDetails(map.get("out_trade_no"), map
				    .get("partner"));
			}
			if (order != null && order.getId() > 0
			    && order.getStatus() == OrderDetails.STATUS_3 &&
			    order.getTransactions().size()>0) { return freezeMessage
			    .getDUPLICATE_BATCH_NO(); }

			List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
			    .get("royalty_parameters"));

			// 验证付款的总额是否小于或者等于所有分润帐号的金额+中间帐号
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			BigDecimal calc = totalFee.multiply(coterie.getRate()).divide(
			    new BigDecimal("100"));
			BigDecimal flatFee = calc.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal tempTotalFee = new BigDecimal("0");
			int flag = 0;
			if (listRoyalty != null && listRoyalty.size() > 0)
			{
				for (String[] royaltyParameters : listRoyalty)
				{
					tempTotalFee = new BigDecimal(royaltyParameters[1].trim())
					    .add(tempTotalFee);
				}
				BigDecimal ca = flatFee.add(tempTotalFee);//分润中和加上钱门手续费
				if(ca.compareTo(totalFee)==1){
					gatewayMessage.getRoyalty_Fee_Error(); //分润金额过大
				}
				if(fromAgent!=null&&fromAgent.getId()>0){
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
					if(totalFee.compareTo(ab.getNotAllowBalance())==1){
						return freezeMessage.getFREEZE_NOTALLOW_NOTENOUGH_TOBID();
					}
				}
			}
			this.setSeller(resultAgent);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		System.out.println(">>>>>>>>>招标冻结支付验证结束");
		return freezeMessage.getSUCCESS();
	}

	
	/*
	 * 招标冻结支付订单方法
	 */
	public String freeze_payment(HashMap<String, String> map,
		Agent userAgent,String url, String encode,
			GatewayMessage gatewayMessage) throws Exception{
		
		String orderNo = map.get("out_trade_no");
		System.out.println(">>>>>>>>>招标冻结支付订单方法开始"+ orderNo);
		Timestamp sysDate = null;
		OrderDetails orderDetails = null;
		String[] returnURL = null;
		String strSysDate = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
		System.out.println("/**********当前时间strSysDate*********/"+strSysDate);
		Agent userInfo = userAgent;
		String returnParameter = ""; // 分润返回的参数
		try{
			orderDetails = appendOrder(map, userInfo, url, false);
				
			System.out.println(">>>>>>>>>>招标冻结支付" + orderNo+ " 开始分润");
			//调用方法执行插入明细
			returnParameter = freezePaymentTran(map, orderDetails, userInfo, sysDate,
			    gatewayMessage);	
			System.out.println(">>>>>>>>>>招标冻结支付" + orderNo + " 分润结束");
			if (returnParameter.equals(gatewayMessage
			    .getTxn_Result_Account_Balance_Not_Enough())) { return gatewayMessage
			    .getTxn_Result_Account_Balance_Not_Enough(); }
			if (returnParameter != null && returnParameter.length() > 0){
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
			}else{
				System.out.println(">>>>>>>>>>>>>>>>>>>>> 分润返回结果为空!");
			}
		}catch (Exception ex)
		{
			throw ex;
		}
		System.out.println(">>>>>>>>>招标冻结支付订单方法结束"+ orderNo);
		return returnURL[0];
	}
	
	
	/*
	 * 招标冻结支付明细方法
	 */
	public String freezePaymentTran(HashMap<String, String> map,
	    OrderDetails orderDetails, Agent userAgent, Timestamp sysDate,
	    GatewayMessage gatewayMessage) throws Exception{

		StringBuffer sb = new StringBuffer();
		BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
		List<String[]> listRoyalty = AnalyseParameter.analyseRoyaltyParameter(map
		    .get("royalty_parameters"));
		// temp parameter
		BigDecimal tempPay = new BigDecimal("0");
		BigDecimal finalFee = new BigDecimal("0");			
		Coterie coterie = coterieDAO.getCoterieByPartner(map.get("partner"));
		Agent tempAgent = agentDAO.getAgentById(coterie.getTempAgent().getId()); 
		// 查询平台帐号,根据partner查出平台帐号
		Agent avouchAgent = agentDAO.getAgentById(coterie.getAgent().getId());
		// 先检查是否够钱付款
		Agent paymentAgent = agentDAO.getAgentById(userAgent.getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(paymentAgent.getId());
		BigDecimal paymentAgent_ontallowBalance = ab.getNotAllowBalance();
		
		//BigDecimal paymentAgent_allowBalance = paymentAgent.getAllowBalance();
		System.out.println("------  " +"同步后招标账户冻结余额:" + paymentAgent_ontallowBalance);
		
		if (!Gateway.equals(paymentAgent_ontallowBalance, totalFee)) { 
			return sb.append(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough()).toString(); 
		}
		
		// 先把买家付款的金额打到平台帐号上
		buildAllowTransaction(orderDetails, paymentAgent, avouchAgent,
		Transaction.TYPE_140, totalFee, null, Transaction.STATUS_3);	
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
//						if (avouchAgent_notallowBalance != null  注释是因为平台作为一个中转账户 买家先把钱打给平台
//						    && Gateway.equals(avouchAgent_notallowBalance, tempFee)){平台给分润账户，但两步处理都在同一个事物，所以不用验证平台的钱够不够
							System.out.println("分润金额tempFee："+tempFee);
							buildAllowTransaction(orderDetails, avouchAgent, resultAgent,
							    Transaction.TYPE_140, tempFee, null, Transaction.STATUS_3);
							sb.append(gatewayMessage.getSuccess());		
//						}
//						else
//						{
							// 账户余额不足,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
//							sb.append(gatewayMessage
//							    .getTxn_Result_Account_Balance_Not_Enough());
//							tempPay = tempPay.add(tempFee);
//							System.out.println("平台账户余额不足");
//						}
					}
					else
					{
						// 不存在该账户,用tempPay记录起来,如果tempPay大于0的话,就把钱放到临时帐号里面
						sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
						tempPay = tempPay.add(tempFee);
						System.out.println("不存在该账户:"+resultAgent.getEmail());
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
				System.out.println("分润金额tempFee："+tempFee);
				if (resultAgent != null && resultAgent.getId() > 0){
					buildAllowTransaction(orderDetails, avouchAgent, resultAgent,
					    Transaction.TYPE_140, tempFee, null, Transaction.STATUS_3);
							sb.append(gatewayMessage.getSuccess());
				}else{
					sb.append(gatewayMessage.getTxn_Result_No_Such_Account());
					tempPay = tempFee;
					System.out.println("不存在该账户:"+resultAgent.getEmail());
				}
			}
			finally
			{
				sb.append("|");
			}
		}

		// pay to temp account if poyalty account error
		// 如果分润的时候,有些分润帐号错误或者某些原因分润失败的话,把金额打到临时帐号上
		System.out.println("分润支付失败的金额："+tempPay);
		if (tempPay.doubleValue() > 0){	
			buildAllowTransaction(orderDetails, avouchAgent, tempAgent,
				    Transaction.TYPE_140, tempPay, null, Transaction.STATUS_3);
		}

		// 验证分润钱+给平台的费用是否小于总额,如果小于,直接把剩下的钱打到平台帐号上,不需要用费率计算
		BigDecimal ca = tempTotalFee.add(flatFee);
		if (ca.compareTo(totalFee) < 0)
		{
			finalFee = totalFee.subtract(ca.setScale(2, BigDecimal.ROUND_HALF_UP));
			System.out.println("分润和已扣除手续费多出来金额："+finalFee);
			buildAllowTransaction(orderDetails, avouchAgent, tempAgent,
					Transaction.TYPE_140, finalFee, null, Transaction.STATUS_3);
			
		}
		if (Constant.platRateAgent == null)
		{
			System.out.println("*********平台收费账号为空..");
		}
		else
		{
			System.out.println("*********平台收费账号不为空ID="
			    + Constant.platRateAgent.getId()+"平台费用："+flatFee);
			buildAllowTransaction(orderDetails, avouchAgent, Constant.platRateAgent,
			    Transaction.TYPE_140, flatFee, null, Transaction.STATUS_3);
		}
		
		// update order details status
		orderDetails.setStatus(Transaction.STATUS_3);
		orderDetails.setFinishDate(sysDate);
		return sb.substring(0, sb.length() - 1).toString();
	}
	
	
	/*
	 * 招标冻结支付退款参数验证方法
	 */
	public String validateFreezeRefund(HashMap<String, String> map, String url,
	    GatewayMessage gatewayMessage) throws Exception{
		System.out.println(">>>>>>>>>>招标冻结支付退款验证-------");
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
	
	
	/*
	 * 招标冻结支付退款订单方法
	 */
	public String freezeRefund(HashMap<String, String> map, String url,
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
			long tranType = Transaction.TYPE_141; //设置退款产生的明细类型
			 
			
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
			result = freezeToRefund(map, refundOrder, payOrder, coterie,
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
	 * (即时到账/担保冻结)支付退款方法
	 */
	public String freezeToRefund(HashMap<String, String> map,
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
									buildAllowTransaction(refundOrder, payoutAgent, avouchAgent,
										tranType, amount, mark, Transaction.STATUS_11);
								}
								else
								{
									buildAllowTransaction(refundOrder, payoutAgent, inceptAgent, // 这里是平台帐号直接打钱到原来的买家帐号
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
						buildAllowTransaction(refundOrder, avouchAgent, inceptAgent,
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
			buildAllowTransaction(refundOrder, platRateAgent, avouchAgent,
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
	 * 从冻结到可用支付明细
	 */
	public void buildAllowTransaction(OrderDetails orderDetails,
			Agent fromAgent, Agent toAgent, Long type, BigDecimal paymentPrice,
			String remark, long status) throws Exception {
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
				// 获取AgentBalance对象拥有获取金额
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				fromAgent.setNotallowBalance(ab.getNotAllowBalance()
						.subtract( // 冻结金额减去要退款的金额
								paymentPrice));
				
				toAgent
						.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getAllowBalance() != null) {
					toAgent.setAllowBalance(ab2.getAllowBalance().add(
							paymentPrice)); // 可以用余额加上退款的金额
					/*
					 * toAgent.setBalance(BigDecimal.valueOf(toAgent
					 * .getBalance().doubleValue() + paymentPrice));
					 */
				} else {
					toAgent.setAllowBalance(paymentPrice);
					// toAgent.setBalance(BigDecimal.valueOf(paymentPrice));
				}
				
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

	
	/**
	 * 从可用到冻结支付明细
	 */
	public void buildAllowTofreeTransaction(OrderDetails orderDetails,
			Agent fromAgent, Agent toAgent, Long type, BigDecimal paymentPrice,
			String remark, long status) throws Exception {
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
				// 获取AgentBalance对象拥有获取金额
				AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
				AgentBalance ab2 = agentDAO.getAgentBalance(toAgent.getId());
				fromAgent.setNotallowBalance(ab.getAllowBalance()
						.subtract( // 冻结金额减去要金额
								paymentPrice));
				toAgent
						.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				if (ab2.getAllowBalance() != null) {
					toAgent.setAllowBalance(ab2.getNotAllowBalance().add(
							paymentPrice)); // 可以用余额加上退款的金额
					/*
					 * toAgent.setBalance(BigDecimal.valueOf(toAgent
					 * .getBalance().doubleValue() + paymentPrice));
					 */
				} else {
					toAgent.setAllowBalance(paymentPrice);
					// toAgent.setBalance(BigDecimal.valueOf(paymentPrice));
				}
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

	//添加订单
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
	
	
	/**
	 * 添加一条新订单
	 */
	public OrderDetails appendOrder(OrderDetails order, long paymentType,
			String url, String pay_fee, long buyType) throws Exception {
		OrderDetails orderDetails = new OrderDetails();
		Long logStatus = null;
		long orderDetailsType = OrderDetails.getOrderDetailsType(paymentType);

		orderDetails.setShopName(order.getShopName());
		orderDetails.setPartner(order.getPartner());
		orderDetails.setOrderNo(order.getOrderNo());
		orderDetails.setDetailsContent(order.getDetailsContent());
		// orderDetails.setReferenceOrderDetailsNo();
		orderDetails.setOrderDetailsType(orderDetailsType);
		orderDetails.setBuyType(buyType);
		orderDetails.setAgent(order.getAgent());
		orderDetails.setStatus(Transaction.STATUS_3); // 交易成功
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
		transactionDAO.addOrderDetails(orderDetails); // 添加订单

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
	 * 同步可用金额
	 */
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

	// 根据合作伙伴号查询圈主信息
	public Agent getPartnerAccount(String partner) throws AppException {
		return agentDAO.queryByPartner(partner);
	}

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public AgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public Agent getSeller() {
		return seller;
	}

	public void setSeller(Agent seller) {
		this.seller = seller;
	}

	public NoUtil getNoUtil() {
		return noUtil;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public ActionLogDAO getActionLogDAO() {
		return actionLogDAO;
	}

	public void setActionLogDAO(ActionLogDAO actionLogDAO) {
		this.actionLogDAO = actionLogDAO;
	}

	public AgentCoterieDAO getAgentCoterieDAO() {
		return agentCoterieDAO;
	}

	public void setAgentCoterieDAO(AgentCoterieDAO agentCoterieDAO) {
		this.agentCoterieDAO = agentCoterieDAO;
	}

	public CoterieDAO getCoterieDAO() {
		return coterieDAO;
	}

	public void setCoterieDAO(CoterieDAO coterieDAO) {
		this.coterieDAO = coterieDAO;
	}

	public long saveActionLog(ActionLog actionLog) throws Exception {
		return actionLogDAO.save(actionLog);
	}

	public AgentBindDAO getAgentBindDAO() {
		return agentBindDAO;
	}

	public void setAgentBindDAO(AgentBindDAO agentBindDAO) {
		this.agentBindDAO = agentBindDAO;
	}
	

	// 查找没还款的信用交易
	public List getCreditPayListByAgent(Agent buyerAgent) throws AppException {
		return transactionDAO.getCreditPayListByAgent(buyerAgent);
	}

	public static void test() {
		TransactionDAOImpl dao = new TransactionDAOImpl();
		AgentDAOImpl adao = new AgentDAOImpl();
		try {
			Agent buyerAgent = adao.getAgentById(4660);
			List creditPayList = dao.getCreditPayListByAgent(buyerAgent);
			if (creditPayList != null && creditPayList.size() != 0) {
				System.out.println("未还款在支付数量：" + creditPayList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证解冻协议参数
	 */
	public String validateProtocolParameters(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception {
		try {
			// 合作伙伴ID
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return freezeMessage.getPARTNER_NOT_SIGN_PROTOCOL();
			}
			// 签名方式
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return freezeMessage.getVALIDATE_SIGN_FAIL();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// TODO: handle exception
		}
		return freezeMessage.getSUCCESS();
	}

	/**
	 * 验证解冻业务参数
	 */
	public String validateOperatingparameters(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage) throws Exception {
		try {
			String message = ConnAnalyseParameter.validate_unfreeze_parameter(
					map, freezeMessage);
			if (!freezeMessage.getSUCCESS().equals(message)) {
				return message;
			}
			List<String[]> unfreeList = AnalyseParameter
					.analyseRoyaltyParameter(map.get("unfreeze_details"));
			for (int i = 0; i < unfreeList.size(); i++) {
				String[] refund = unfreeList.get(i);
				// 判断冻结订单号是否存在
				OrderDetails freezeNo = transactionDAO.queryOrderLikeOrderNo(refund[1]);
				System.out.println("----原冻结订单号-----"+refund[1]);
				if (freezeNo == null) {
					return freezeMessage.getTRADE_ITEM_NOT_EXIST();// 交易不存在
				} 
				System.out.println("原冻结交易号："+freezeNo.getOrderDetailsNo());
				System.out.println("冻结的金额：" + freezeNo.getPaymentPrice());
				// 根据冻结订单号在订单表中查询有没有解冻过
				System.out.println("已经解冻的金额：" + freezeNo.getRepayAmount());
				if (!Gateway.equals(freezeNo.getPaymentPrice(),
						new BigDecimal(refund[2]))) {
					// 解冻金额过大
					return freezeMessage.getGREATER_UNFREEZE_MONEY();
				}
				if (freezeNo.getRepayAmount() != null) {
					if (!Gateway.equals(freezeNo.getPaymentPrice(),
						freezeNo.getRepayAmount().add(
							new BigDecimal(refund[2])))) {
						// 解冻金额过大
						freezeMessage.getGREATER_UNFREEZE_MONEY();
					}
				}

				// 判断解冻结订单号是否存在
				OrderDetails unfreezeNo = agentBindDAO
						.queryOrderDetailByOrderNoAndPartner(refund[0] + "~"
								+ refund[1]);
				if (unfreezeNo != null) {
					// 解冻结订单号重复
					return freezeMessage.getUNFREEZE_ORDER_NO_REPEAT();
				}
				// 两次判断
				List unfreezeNoTwo = agentBindDAO
						.queryOrderDetailByLikeOrderNo(refund[0]);
				if (unfreezeNoTwo != null) {
					return freezeMessage.getUNFREEZE_ORDER_NO_REPEAT();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// TODO: handle exception
		}
		return freezeMessage.getSUCCESS();
	}

	/**
	 * 解冻
	 * 
	 * @param map
	 * @param url
	 * @param freezeMessage
	 * @return
	 * @throws Exception
	 */
	public HashMap<Integer, String> unfreeze(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage, FreezeBiz freezeBiz)
			throws Exception {
		Timestamp sysDate = null;
		OrderDetails freezeOrder = null;
		OrderDetails unfreezeOrder = null;
		StringBuffer sb = new StringBuffer();
		String msg = null;
		HashMap<Integer, String> map1 = new HashMap<Integer, String>();
		try {
			// get system date
			sysDate = DateUtil.getTimestamp(DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"),
					"yyyy-MM-dd HH:mm:ss");

			System.out.println("解冻订单方法："+map.get("unfreeze_details"));
			// 调用方法获取解冻交易集合
			List<String[]> unfreeList = AnalyseParameter
					.analyseRoyaltyParameter(map.get("unfreeze_details"));
			// 有多少个冻结订单号就有多少条回调
			for (int i = 0; i < unfreeList.size(); i++) {
				// 交易解冻信息,格式:解冻结订单号^冻结订单号^解冻结金额，多个之间用|连接。
				String[] refundInfo = unfreeList.get(i);

				// 根据冻结订单号查出订单信息
				freezeOrder = transactionDAO.queryOrderLikeOrderNo(refundInfo[1]);
				
				// 查询冻结用户
				Transaction freezelist = agentBindDAO.getByUnfreeze(freezeOrder
						.getId());
				long paymentType = OrderDetails.getOrderDetailsType(6);
				// 生成解冻订单
				unfreezeOrder = appendOrder(freezeOrder, paymentType, url,
						unfreeList.get(i), OrderDetails.BUY_TYPE_7);			
				// 进行解冻
				System.out.println("解冻开始~~~~~~~~~~");
				if (freezelist.getType() == Transaction.TYPE_198) {
					msg = freezeGoCeditForUnfreeze(map, url, freezeMessage,
						unfreezeOrder, freezeOrder, refundInfo[2],
							unfreeList.get(i));
				}
				System.out.println("进行解冻返回的数据："+msg);
				System.out.println("解冻结束~~~~~~~~~~~");
				// 每执行一次解冻后就回调一次
				String notify_type = "trade_status_sync";// 通知类型
				Coterie coterie = coterieDAO.getCoterieByPartner(map
						.get("partner"));
				String isSuccess = "F";
				if (freezeMessage.getSUCCESS().equals(msg)) {
					isSuccess = "T";
				}
				StringBuffer result = CoterieUtilURL.unfreezeRedirectUrl(
						coterie.getSignKey(), refundInfo[2].toString(),
						refundInfo[1], String.valueOf(unfreezeOrder.getId()),
						sysDate.toString(), notify_type, sysDate.toString(),
						msg, isSuccess, unfreezeOrder.getOrderNo(),
						refundInfo[0], unfreezeOrder.getAgent().getLoginName(),
						String.valueOf(unfreezeOrder.getAgent().getId()));
				String myUrl = map.get("notify_url") + "?" + result;
				System.out.println("myUrl："+myUrl);
				unfreezeDirectPayment unfreezeDirectPayment = new unfreezeDirectPayment(
						freezeBiz, result.toString());
				unfreezeDirectPayment.setEncoding("GBK");
				unfreezeDirectPayment.setMap(map);
				unfreezeDirectPayment.setUrl(myUrl);
				unfreezeDirectPayment.setUserAgent(freezeOrder.getAgent());
				MainTask.put(unfreezeDirectPayment);

				sb.append(refundInfo[0]);
				sb.append("^");
				sb.append(refundInfo[1]);
				sb.append("^");
				sb.append(refundInfo[2]);
				sb.append("^");
				sb.append(freezeOrder.getOrderDetailsNo());
				sb.append("^");
				sb.append(sysDate);
				sb.append("^");
				sb.append(isSuccess);
				sb.append("^");
				sb.append(msg);
				if (i < unfreeList.size() - 1) {
					sb.append("|");
				}
			}
			// set action log
			ActionLog aLog = new ActionLog();
			aLog.setStatus(new Long(1));
			aLog.setLogDate(sysDate);
			aLog.setContent(map.get("notify_url") + "?" + sb);
			aLog.setOrderDetails(unfreezeOrder);
			aLog.setLogType(ActionLog.LOG_TYPE_REFUND_RETURN);

			map1.put(0, sb.toString());
			map1.put(1, freezeOrder.getOrderDetailsNo());
			actionLogDAO.save(aLog);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return map1;
	}

	public String freezeGoCeditForUnfreeze(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage,
			OrderDetails unfreezeOrder, OrderDetails freezeOrder, String free,
			String[] refundInfo) throws AppException {

		try {
			int num;
			BigDecimal freezeFee = freezeOrder.getPaymentPrice();
			if (freezeOrder.getRepayAmount() != null) {
				BigDecimal unfreezeFee = freezeOrder.getRepayAmount().add(
						new BigDecimal(free));
				num = freezeFee.compareTo(unfreezeFee);
			} else {
				num = freezeFee.compareTo(new BigDecimal(free));
			}
			// 判断解冻金额
			if (num == -1) {
				return freezeMessage.getGREATER_UNFREEZE_MONEY();
			} else {
				buildAllowTransaction(unfreezeOrder, freezeOrder.getAgent(),
						freezeOrder.getAgent(), Transaction.TYPE_197,
						new BigDecimal(free), "委托解冻", Transaction.STATUS_3);

				// 修改冻结订单repayAmount的金额
				BigDecimal repayAmount = new BigDecimal(0);
				if (freezeOrder.getRepayAmount() != null) {
					repayAmount = repayAmount.add(freezeOrder.getRepayAmount());
				}
				freezeOrder.setRepayAmount(repayAmount
						.add(new BigDecimal(free)));
				agentBindDAO.saveOrderDetails(freezeOrder);

				String msg = credit_validateRefund(map, url, freezeMessage,
						refundInfo);
				if (!freezeMessage.getSUCCESS().equals(msg)) {
					return msg;
				}
			}

		} catch (Exception e) {
			return freezeMessage.getGENERAL_FAIL();
			// TODO: handle exception
		}

		return freezeMessage.getSUCCESS();
	}

	/**
	 * 回调前的验证
	 * 
	 * @param map
	 * @param url
	 * @param freezeMessage
	 * @return
	 * @throws Exception
	 */
	public String credit_validateRefund(HashMap<String, String> map,
			String url, FreezeMessage freezeMessage, String[] refundInfo)
			throws Exception {
		try {
			String partner = map.get("partner");
			// 验证退款URL参数,返回true or false

			// validate partner 验证partner是否存在商户圈里面
			Coterie coterie = coterieDAO.getCoterieByPartner(partner);
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return freezeMessage.getPARTNER_NOT_SIGN_PROTOCOL();
			}

			// 验证签名
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return freezeMessage.getVALIDATE_SIGN_FAIL();
			}

			List<String[]> listRefund = AnalyseParameter
					.analyseRoyaltyParameter(map.get("unfreeze_details"));
			if (listRefund == null || listRefund.size() < 1) {
				return freezeMessage.getILLEGAL_ARGUMENT();
			}
			// orderNo 验证订单是否存在
			OrderDetails order = agentBindDAO.queryOrderDetail((refundInfo[0]
					+ "~" + refundInfo[1]), Transaction.STATUS_3);
			if (order == null) {
				return freezeMessage.getTRADE_ITEM_NOT_EXIST(); // 交易不存在
			}
			Transaction trans = agentBindDAO
					.queryTransactionByOrderAndFromAgent(order.getId());
			if (trans == null || trans.getToAgent() == null) {
				return freezeMessage.getTRADE_ITEM_NOT_EXIST(); // 交易不存在
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		// return ExceptionConstant.SUCCESS;
		return freezeMessage.getSUCCESS();
	}

	/**
	 * 添加一条新订单
	 */
	public OrderDetails appendOrder(OrderDetails order, long paymentType,
			String url, String[] refundInfo, long buyType) throws Exception {
		OrderDetails orderDetails = new OrderDetails();
		Long logStatus = null;
		long orderDetailsType = OrderDetails.getOrderDetailsType(paymentType);

		orderDetails.setShopName("【委托解冻】");
		orderDetails.setPartner(order.getPartner());
		orderDetails.setOrderNo(refundInfo[0] + "~" + refundInfo[1]);
		orderDetails.setDetailsContent(order.getDetailsContent());
		// orderDetails.setReferenceOrderDetailsNo();
		orderDetails.setOrderDetailsType(orderDetailsType);
		orderDetails.setBuyType(buyType);
		orderDetails.setAgent(order.getAgent());
		orderDetails.setStatus(Transaction.STATUS_3); // 交易成功
		logStatus = new Long(1);
		orderDetails.setPaymentPrice(new BigDecimal(refundInfo[2]));
		orderDetails.setShopPrice(new BigDecimal(refundInfo[2]));
		// set quantity 设置数量
		orderDetails.setShopTotal(order.getShopTotal());
		// 设置邮费默认为 0.00
		orderDetails.setEmailPrice(order.getEmailPrice());
		orderDetails.setCreateDate(order.getCreateDate());
		orderDetails.setPartner(order.getPartner());
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		transactionDAO.addOrderDetails(orderDetails); // 添加订单

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

	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo)
			throws AppException {
		return agentBindDAO.queryOrderDetailByOrderNoAndPartner(orderNo);
	}

	/**
	 * 验证解冻查询参数
	 */
	public String validateAirtradeRefundUnfreezeQuery(
			HashMap<String, String> map, String url, FreezeMessage freezeMessage)
			throws Exception {
		try {
			// 合作伙伴ID
			Coterie coterie = coterieDAO
					.getCoterieByPartner(map.get("partner"));
			if (coterie == null || coterie.getId() == 0l
					|| coterie.getAgent() == null
					|| coterie.getAgent().getId() == 0l) {
				return freezeMessage.getPARTNER_NOT_SIGN_PROTOCOL();
			}
			// 签名方式
			if (!AnalyseParameter.validateSign(url, map.get("sign"), map
					.get("sign_type"), coterie.getSignKey(), map
					.get("_input_charset"))) {
				return freezeMessage.getVALIDATE_SIGN_FAIL();
			}
			String unfreeze_order_no = map.get("unfreeze_order_no");
			String[] refundInfo = unfreeze_order_no.split("\\^");
			if (refundInfo == null || refundInfo.length < 1) {
				return freezeMessage.getUNFREEZE_ITEM_NOT_EXIST();
			}
			if (refundInfo.length > 20) {
				// 超过最大限制
				return freezeMessage.getEXCEED_MAX_LIMIT();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return freezeMessage.getSUCCESS();
	}

	public String unfreezeQuery(HashMap<String, String> map, String url,
			FreezeMessage freezeMessage, FreezeBiz freezeBiz) throws Exception {
		Timestamp sysDate = null;
		OrderDetails unfreezeOrder = null;
		StringBuffer sb = new StringBuffer();
		try {
			String unfreeze_order_no = map.get("unfreeze_order_no");
			String[] refundInfo = unfreeze_order_no.split("\\^");
			// 判断解冻结订单号是否存在
			for (int i = 0; i < refundInfo.length; i++) {
				unfreezeOrder = transactionDAO.queryOrderLikeOrderNo(refundInfo[i]);
				if (unfreezeOrder == null) {
					sb.append(refundInfo[i] + "^" + "N/Q" + "^" + "N/Q" + "^"
							+"N/Q" + "^" + "N/Q" + "^" + "F" + "^" + 
							freezeMessage.getUNFREEZE_ORDER_NOT_EXIST());
				} else {	
					String[] orderNo;
					orderNo= unfreezeOrder.getOrderNo().split("\\~");	
					sb.append(refundInfo[i]);
					sb.append("^");
					sb.append(orderNo[1]);			
					BigDecimal payment_price = unfreezeOrder.getPaymentPrice();
					sysDate = unfreezeOrder.getCreateDate();
					sb.append("^");
					sb.append(payment_price);
					sb.append("^");
					sb.append(unfreezeOrder.getOrderDetailsNo());
					sb.append("^");
					sb.append(sysDate);
					sb.append("^");
					if(unfreezeOrder.getDetailsContent().equals("SUCCESS"))
						sb.append("T");
					else
						sb.append("F");
					sb.append("^");
					sb.append(unfreezeOrder.getDetailsContent());
				}
				if (i < refundInfo.length - 1) {
					sb.append("|");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// test();
		String[] sp = new String[5];
		sp[0] = "aaaaa-aabbcc";
		sp[3] = "bbbbb-aabbcc";
		sp[4] = "ccccc-aabbcc";
		sp[1] = "fffff-aabbcc";
		sp[2] = "ggggg-aabbcch";
		StringBuffer idbuff = new StringBuffer();
		for (int i = 0; i < sp.length; i++) {
			int index = sp[i].lastIndexOf("-") + 1;
			String split = sp[i].substring(index, sp[i].length());
			String isplit = sp[i].substring(0, sp[i].lastIndexOf("-"));
			if (split.equals("aabbcc")) {
				if (i != 0)
					idbuff.append(",");
				idbuff.append(sp[i].substring(0, sp[i].lastIndexOf("-")));
			}
			System.out.println(isplit);
		}
		System.out.println(idbuff);
	}
}


