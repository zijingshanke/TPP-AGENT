package com.fordays.fdpay.transaction.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentRoleRight;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.bank.BankOrderStoreRemote;
import com.fordays.fdpay.bank.FinishCharge;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.right.dao.RightDAO;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.ChargeListForm;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.ChargeDAO;
import com.fordays.fdpay.transaction.dao.ChargeLogDAO;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.NoUtil;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;

/**
 * 充值业务实现类
 */

public class ChargeBizImp implements ChargeBiz {
	private NoUtil noUtil;
	private AgentDAO agentDAO;
	private RightDAO rightDAO;
	private ChargeDAO chargeDAO;
	private ChargeLogDAO chargeLogDAO;
	private TransactionDAO transactionDAO;
	private LogUtil myLog;

	/**
	 * 完成充值操作
	 * 
	 * 1、校验 2、更新充值订单交易状态,写入日志 3、如果充值成功,生成Transaction订单,更新金额 4、封装反馈信息
	 * 
	 * @param ResultFromBank
	 * @return FinishCharge
	 */

	public FinishCharge finishCharge(ResultFromBank result) throws AppException {
		myLog = new LogUtil(false, false, ChargeBizImp.class);

		FinishCharge finishCharge = new FinishCharge();
		finishCharge.setOrderNo("C88888888");
		if (result != null) {

			final long rTranStat = result.getRChargeStatus();
			final String orderNo = result.getROrderNo();
			final String rRequestHost = result.getRRequestHost();

			Charge charge = chargeDAO.getChargeByOrderNo(orderNo);
			long chargeId = charge.getId();

			myLog.info("result Host=" + rRequestHost + ",charge Id=" + chargeId
					+ ",charge no=" + charge.getOrderNo() + ",status="
					+ charge.getStatus());

			if (chargeId != 0) {
				final String cRequestHost = charge.getRequestHost();
				myLog.info("charge Host=" + cRequestHost);
				if (rRequestHost.equalsIgnoreCase(cRequestHost)) {// 订单请求地址一致
					finishCharge.setOrderNo(orderNo);// ----------
					finishCharge.setCharge(charge);// ------------

					if (rTranStat == 1) {// 支付成功
						int checkAmount = checkAmount(charge, result);

						if (charge.getStatus() == Charge.CHARGE_STATUS_1) {// 数据库中已支付成功，防止重复
							finishCharge.setReturnKey("1002");
							return finishCharge;
						} else if (checkAmount != 0) {// 反馈的金额与数据库记录的金额不符
							finishCharge.setReturnKey("1003");
							return finishCharge;
						} else if (charge.getStatus() == Charge.CHARGE_STATUS_0) {
							String chargeOrderNo = charge.getOrderNo();

							// --begin synchronized
							synchronized (new Object()) {
								boolean checkStoreRemote = BankOrderStoreRemote
										.containsBankOrder(chargeOrderNo);

								myLog.info("check BankOrderStore Remote ("
										+ chargeOrderNo + ")="
										+ checkStoreRemote);

								if (checkStoreRemote) {
									finishCharge.setReturnKey("1011");// 静态订单库中已存在此订单
									myLog.error("静态订单库中已存在此订单");
									return finishCharge;
								} else {
									BankOrderStoreRemote
											.addBankOrder(chargeOrderNo);

									charge.setStatus(Charge.CHARGE_STATUS_1);
									charge.setChargeDate(new Timestamp(System
											.currentTimeMillis()));
									finishCharge.setReturnKey("1001");// 支付成功
									finishCharge.setCharge(charge);

									finishTransaction(finishCharge);// 保存交易
								}
							}
							// --end synchronized
						} else {
							finishCharge.setReturnKey("1008");// 未知错误
							myLog.error("未知,after checkStatus,checkAmount");
							return finishCharge;
						}
					} else if (rTranStat == Charge.CHARGE_STATUS_8) {// 企业网银(接收到订单，等待企业审核)
						charge.setStatus(Charge.CHARGE_STATUS_8);
						finishCharge.setReturnKey("1004");
					} else if (rTranStat == Charge.CHARGE_STATUS_2) {// 支付失败
						charge.setStatus(Charge.CHARGE_STATUS_2);
						finishCharge.setReturnKey("1005");
					} else if (rTranStat == Charge.CHARGE_STATUS_6) {// 放弃支付
						charge.setStatus(Charge.CHARGE_STATUS_6);
						finishCharge.setReturnKey("1006");
					} else if (rTranStat == Charge.CHARGE_STATUS_7) {// 可疑交易
						charge.setStatus(Charge.CHARGE_STATUS_7);
						finishCharge.setReturnKey("1007");
					} else {
						finishCharge.setReturnKey("1008");// 未知错误
						myLog.error("解析银行订单反馈结果时，没有正确赋予订单状态");
						return finishCharge;
					}
					chargeDAO.update(charge);
					chargeLogDAO.saveChargeLog(charge, result.getRUrl());
				} else {
					finishCharge.setReturnKey("1009");// 订单请求地址不合法
					return finishCharge;
				}
			} else {
				finishCharge.setReturnKey("1000");// 不存在的订单
				return finishCharge;
			}
			return finishCharge;
		} else {
			finishCharge.setReturnKey("0000");// 银行返回数据非法
			return finishCharge;
		}
	}

	/**
	 * @param FinishCharge
	 *            finishCharge
	 * @return FinishCharge
	 */
	private void finishTransaction(FinishCharge finishCharge)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeBizImp.class);

		String returnKey = finishCharge.getReturnKey();
		myLog.info("finishTransaction()---order_no="
				+ finishCharge.getOrderNo() + ",returnKey=" + returnKey);

		if ("1001".equals(returnKey)) {
			Charge charge = finishCharge.getCharge();
			saveTransactionByCharge(charge);//				
			sendChargeSuccessMail(charge);// 发送通知邮件

			// 获取Session，权限
			UserRightInfo uri = getUserRightInfobyCharge(charge);
			myLog.info("UserRightInfo uri agentName:"
					+ uri.getAgent().getName());
			finishCharge.setUri(uri);

			// BankOrderStore.removeOverdueOrders();// 移除静态订单库中的过期订单
		}
		// return finishCharge;
	}

	/**
	 * 
	 * @param Charge
	 *            charge
	 * @return UserRightInfo
	 * @remark: BankAction调用
	 */
	public UserRightInfo getUserRightInfobyCharge(Charge charge)
			throws AppException {
		UserRightInfo uri = new UserRightInfo();
		uri.setAgent(charge.getAgent());

		List list = rightDAO.listRoleRights();
		for (int i = 0; i < list.size(); i++) {
			AgentRoleRight arr = (AgentRoleRight) list.get(i);
			uri.addRight(arr.getRightCode(), arr.getRightAction());
		}
		return uri;
	}

	/**
	 * 检查金额
	 * 
	 * @param Charge
	 *            charge
	 * @param ResultFromBank
	 *            result
	 * @return int
	 * @remark: return 0:金额相符
	 */
	public int checkAmount(Charge charge, ResultFromBank result)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeBizImp.class);

		int checkAmount = 1;
		final BigDecimal dAmount = charge.getAmount();
		final BigDecimal rAmount = result.getRAmount();
		checkAmount = rAmount.compareTo(dAmount);

		myLog.info("resultAmount=" + rAmount + "," + "dataBaseAmount=="
				+ dAmount);
		myLog.info("金额检查[0:金额相符]:" + checkAmount);
		return checkAmount;
	}

	/**
	 * 根据充值记录保存交易记录,变更金额
	 * 
	 * @param Charge
	 *            charge
	 * @return long
	 */
	private long saveTransactionByCharge(Charge charge) throws AppException {
		myLog = new LogUtil(false, false, ChargeBizImp.class);

		// 银行充值成功
		if (charge.getStatus() == Charge.CHARGE_STATUS_1) {
			final BigDecimal amount = charge.getAmount();
			final Agent agent = charge.getAgent();

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

			myLog.info(charge.getOrderNo() + ",saveTransaction success..");

			// synchronized (this) {
			// Agent toAgent = agentDAO.getAgentById(charge.getAgent().getId());
			// Agent fromAgent =
			// agentDAO.getAgentById(com.fordays.fdpay.base.Constant.platChargeAgent.getId());
			// agentDAO.addAmount(toAgent, charge.getAmount());// 增加账户金额
			// agentDAO.reduceAmount(fromAgent, amount);// 减少平台账户金额
			// }
			// myLog.info("---change amount success...");
		}
		return new Long(1);
	}

	/**
	 * 创建充值订单
	 * 
	 * @param Charge
	 *            (ActionForm/HttpParameter)
	 * @param HttpServletRequest
	 *            request
	 * @return Charge charge
	 */
	public Charge createCharge(Charge charge, HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeBizImp.class);

		Charge tempCharge = new Charge();
		String orderNo = noUtil.getChargeNo();
		tempCharge.setOrderNo(orderNo);
		tempCharge.setAmount(charge.getAmount());
		long agentId = new Long(charge.getAgentId());
		Agent agent = agentDAO.getAgentById(agentId);
		tempCharge.setAgent(agent);
		tempCharge.setChargeDate(new Timestamp(System.currentTimeMillis()));
		tempCharge.setStatus(Charge.CHARGE_STATUS_0);
		tempCharge.setType(charge.getType());
		tempCharge.setBank(charge.getBank());
		tempCharge.setRemark(charge.getRemark());

		String requestHost = request.getHeader("Host");
		myLog.info("requestHost:" + requestHost);
		tempCharge.setRequestHost(requestHost);// 订单请求发起地

		final String type = tempCharge.getType();// 充值类型
		final String remark = tempCharge.getRemark();// 外部订单号

		if (Charge.CHARGE_TYPE_TRANSACTION.equals(type)) {
			String tempTransactionNo = charge.getTransactionNo().toString();
			tempCharge.setRemark(tempTransactionNo);
		} else if (Charge.CHARGE_TYPE_NOACCOUNT.equals(type)) {
			tempCharge.setRemark(remark);
		} else if (Charge.CHARGE_TYPE_DIRECTPAYMENT.equals(type)) {
			tempCharge.setRemark(remark);
		} else if (Charge.CHARGE_TYPE_OTHER.equals(type)) {
			tempCharge.setRemark(agent.getLoginName());
		} else if (Charge.CHARGE_TYPE_BACKGROUND.equals(type)) {
			tempCharge.setNote(charge.getNote());
		}
		chargeDAO.save(tempCharge);
		return tempCharge;
	}

	/**
	 * 充值成功，发送通知邮件
	 * 
	 * @param Charge
	 *            charge
	 * @return void
	 */
	private void sendChargeSuccessMail(Charge charge) throws AppException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("$orderId$", charge.getOrderNo());
		params.put("$amount$", charge.getAmount().toString());

		Agent agent = agentDAO.getAgentById(charge.getAgent().getId());// 勿改
		params.put("$toAgentName$", agent.getLoginName());
		MailUtil.sslSend("钱门提示", "0008", agent.getEmail(), params,
				Certification.getProtocol());

	}

	/**
	 * 银行接口调用完毕，移除Session
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param Charge
	 *            charge
	 * @return void
	 */
	public void removeSession(HttpServletRequest request, Charge charge)
			throws AppException {
		final String chargeType = charge.getType();

		if (Charge.CHARGE_TYPE_NOACCOUNT.equals(chargeType)) {
			removeAttribute(request, "URI");
		} else if (Charge.CHARGE_TYPE_DIRECTPAYMENT.equals(chargeType)) {
			removeAttribute(request, "URI");
		} else if (Charge.CHARGE_TYPE_GUARANTEE.equals(chargeType)) {
			removeAttribute(request, "URI");
		}
	}

	/**
	 * 移除Session中指定的Attribute
	 */
	public void removeAttribute(HttpServletRequest request, String attribute)
			throws AppException {
		request.getSession().removeAttribute(attribute);
		request.getSession().invalidate();
	}

	public List getCharges(Agent agent, ChargeListForm clf) throws AppException {
		return chargeDAO.getChargeByAgent(agent, clf);
	}

	public int updateCharge(Charge charge) throws AppException {
		chargeDAO.update(charge);
		return 0;
	}

	public Charge getChargeById(int id) throws AppException {
		return chargeDAO.getChargeById(id);
	}

	public Charge getChargeById(long id) throws AppException {
		return chargeDAO.getChargeById(id);
	}

	public Charge getChargeByOrderNo(String orderNo) throws AppException {
		return chargeDAO.getChargeByOrderNo(orderNo);
	}

	public long save(Charge charge) throws AppException {
		return chargeDAO.save(charge);
	}

	public void setChargeDAO(ChargeDAO chargeDAO) throws AppException {
		this.chargeDAO = chargeDAO;
	}

	public void setChargeLogDAO(ChargeLogDAO chargeLogDAO) {
		this.chargeLogDAO = chargeLogDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setRightDAO(RightDAO rightDAO) {
		this.rightDAO = rightDAO;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}
}