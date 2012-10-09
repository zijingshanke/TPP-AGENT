package com.fordays.fdpay.transaction.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.base.Constant;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.transaction.Debit;
import com.fordays.fdpay.transaction.DebitLog;
import com.fordays.fdpay.transaction.Expense;
import com.fordays.fdpay.transaction.ExpenseLog;
import com.fordays.fdpay.transaction.Logistics;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.TempTransaction;
import com.fordays.fdpay.transaction.TempTransactionBalance;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.TransactionListForm;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.NoUtil;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.tool.DateUtil;
import com.neza.encrypt.MD5;

public class TransactionBizImpl implements TransactionBiz {

	private AgentDAO agentDAO;
	private NoUtil noUtil;
	private TransactionDAO transactionDAO;
	private TransactionTemplate transactionTemplate;
	private CoterieDAO coterieDAO;

	public void setCoterieDAO(CoterieDAO coterieDAO) {
		this.coterieDAO = coterieDAO;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public List getTransactions(TransactionListForm transactionListForm,
			boolean isPass) throws AppException {
		return transactionDAO.getTransactions(transactionListForm, isPass);
	}

	public List getSellerTransactionList(
			TransactionListForm transactionListForm, boolean isPass)
			throws AppException {
		return transactionDAO.getSellerTransactionList(transactionListForm,
				isPass);
	}

	public List getTransactionByToAgentId(
			TransactionListForm transactionListForm) throws AppException {

		return transactionDAO.getTransactionByToAgentId(transactionListForm);
	}

	/**
	 * 借款/还款方法
	 */
	public List getBorrowAndRepaymentList(
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO.getBorrowAndRepaymentList(transactionListForm);
	}

	/**
	 * 授信/还款方法 
	 */
	public List getLetterAndRepaymentList(
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO.getLetterAndRepaymentList(transactionListForm);
	}

	/**
	 * 红包
	 * 
	 */
	public List getRedPacketList(TransactionListForm transactionListForm)
			throws AppException {
		return transactionDAO.getRedPacketList(transactionListForm);
	}

	// 还款给朋友
	public long addTransactionRepayment(Transaction transaction)
			throws AppException {
		long lo = 0;
		OrderDetails orderDetails = new OrderDetails();
		Transaction tran = transactionDAO.getTransactionById(transaction
				.getTid());

		Agent toAgent = agentDAO.getAgentById(tran.getFromAgent().getId());
		Agent fromAgent = agentDAO.getAgentById(tran.getToAgent().getId());
		orderDetails.setPaymentReason(transaction.getOrderDetails()
				.getPaymentReason());
		orderDetails.setPaymentPrice(transaction.getRepaymentPrice());
		orderDetails.setShopName(transaction.getOrderDetails()
				.getPaymentReason());
		orderDetails.setShopPrice(tran.getOrderDetails().getShopPrice());
		orderDetails.setShopTotal(new Long(1));
		orderDetails.setStatus(Transaction.STATUS_3);

		Transaction tempTransaction = new Transaction();

		tempTransaction.setAmount(transaction.getRepaymentPrice());

		tempTransaction.setFromAgent(fromAgent);
		tempTransaction.setToAgent(toAgent);

		tempTransaction.setStatus(Transaction.STATUS_3);
		tempTransaction.setType(Transaction.TYPE_91);

		tempTransaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		agentDAO.reduceAmount(fromAgent, transaction.getRepaymentPrice());// 把钱从余额里面扣除
		agentDAO.addAmount(toAgent, transaction.getRepaymentPrice()); // 把钱加到余额里面

		orderDetails.setDetailsContent(transaction.getRepaymentContent());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setOrderNo(tran.getOrderDetails().getOrderDetailsNo());

		String no = noUtil.getTransactionNo();
		tempTransaction.setNo(no);

		this.addOrderDetails(orderDetails);
		tempTransaction.setOrderDetails(orderDetails);
		tempTransaction
				.setAccountDate(new Timestamp(System.currentTimeMillis()));
		transaction.setStatus(Transaction.STATUS_3);

		List list = transactionDAO.getTransactionByOrderNo(tran
				.getOrderDetails().getOrderDetailsNo());
		BigDecimal sumRepaymentPrice = new BigDecimal("0");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Transaction t = (Transaction) list.get(i);
				sumRepaymentPrice = sumRepaymentPrice.add(t.getAmount());
			}
		}
		if (tran.getAmount().compareTo(
				sumRepaymentPrice.add(transaction.getRepaymentPrice())) == 0) {
			tran.getOrderDetails().setStatus(OrderDetails.STATUS_8);
		} else {
			tran.getOrderDetails().setStatus(OrderDetails.STATUS_7);
		}
		lo = transactionDAO.saveTransaction(tempTransaction);
		return lo;
	}

	public long transactionCreditRepayment(Transaction transaction)
			throws AppException {
		long lo = 0;
		OrderDetails orderDetails = new OrderDetails();

		Agent fromAgent = agentDAO.getAgentById(transaction.getFromAgentId());
		Agent toAgent = agentDAO.getAgentById(transaction.getToAgentId());
		System.out.println(toAgent.getLoginName());
		System.out.println(fromAgent.getLoginName());
		orderDetails.setPaymentReason(transaction.getOrderDetails()
				.getPaymentReason());
		orderDetails.setPaymentPrice(transaction.getRepaymentPrice());
		orderDetails.setShopName(transaction.getOrderDetails()
				.getPaymentReason());
		orderDetails.setShopPrice(transaction.getRepaymentPrice());
		orderDetails.setShopTotal(new Long(1));
		orderDetails.setStatus(Transaction.STATUS_3);
		Transaction tempTransaction = new Transaction();
		tempTransaction.setAmount(transaction.getRepaymentPrice());
		tempTransaction.setFromAgent(fromAgent);
		tempTransaction.setToAgent(toAgent);
		tempTransaction.setStatus(Transaction.STATUS_3);
		tempTransaction.setType(Transaction.TYPE_102);

		tempTransaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		agentDAO.reduceAmount(fromAgent, transaction.getRepaymentPrice());// 把钱从余额里面扣除
		agentDAO.addAmount(toAgent, transaction.getRepaymentPrice()); // 把钱加到余额里面
		// agentDAO.addCreditAmount(toAgent, transaction.getRepaymentPrice());
		// // 把钱加到信用余额里面
		orderDetails.setDetailsContent(transaction.getRepaymentContent());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_3);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_8); // 标识授信支付
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());

		String no = noUtil.getTransactionNo();
		tempTransaction.setNo(no);

		this.addOrderDetails(orderDetails);
		tempTransaction.setOrderDetails(orderDetails);
		tempTransaction
				.setAccountDate(new Timestamp(System.currentTimeMillis()));
		transaction.setStatus(Transaction.STATUS_3);

		// List list =
		// transactionDAO.getTransactionByOrderNo(tran.getOrderDetails()
		// .getOrderDetailsNo());
		// BigDecimal sumRepaymentPrice = new BigDecimal("0");
		// if (list != null && list.size() > 0)
		// {
		// for (int i = 0; i < list.size(); i++)
		// {
		// Transaction t = (Transaction) list.get(i);
		// sumRepaymentPrice = sumRepaymentPrice.add(t.getAmount());
		// }
		// }
		// if (tran.getAmount().compareTo(
		// sumRepaymentPrice.add(transaction.getRepaymentPrice())) == 0)
		// {
		// tran.getOrderDetails().setStatus(OrderDetails.STATUS_8);
		// }
		// else
		// {
		// tran.getOrderDetails().setStatus(OrderDetails.STATUS_7);
		// }
		System.out.println("还款金额:" + transaction.getRepaymentPrice());
		update_credit_give_status(fromAgent, transaction.getRepaymentPrice());
		update_credit_payment_status(fromAgent, transaction.getRepaymentPrice());
		lo = transactionDAO.saveTransaction(tempTransaction);
		return lo;
	}

	// 借钱给朋友
	public long addTransactionBorrowing(Transaction transaction, Agent agent)
			throws AppException {
		long lo = 0;
		OrderDetails orderDetails = new OrderDetails();
		Agent fromAgent = agent;
		Agent toAgent = agentDAO.getAgentByName(transaction.getBuyerAccount());

		orderDetails.setPaymentReason(transaction.getOrderDetails()
				.getPaymentReason());
		orderDetails.setPaymentPrice(transaction.getOrderDetails()
				.getShopPrice());
		orderDetails.setShopName(transaction.getOrderDetails()
				.getPaymentReason());
		orderDetails.setShopPrice(transaction.getOrderDetails().getShopPrice());
		orderDetails.setShopTotal(new Long(1));
		transaction.setAmount(transaction.getOrderDetails().getShopPrice());
		// 设置的还款时间
		transaction.getBorrowingDate();

		transaction.setFromAgent(fromAgent);
		transaction.setToAgent(toAgent);

		transaction.setStatus(Transaction.STATUS_3);
		transaction.setType(Transaction.TYPE_9);

		transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		agentDAO.reduceAmount(fromAgent, transaction.getOrderDetails()
				.getShopPrice());// 把钱从余额里面扣除
		agentDAO.addAmount(toAgent, transaction.getOrderDetails()
				.getShopPrice()); // 把钱加到余额里面

		orderDetails.setDetailsContent(transaction.getOrderDetails()
				.getDetailsContent());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setStatus(OrderDetails.STATUS_6);
		String no = noUtil.getTransactionNo();
		transaction.setNo(no);

		this.addOrderDetails(orderDetails);
		transaction.setOrderDetails(orderDetails);
		transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));

		lo = transactionDAO.saveTransaction(transaction);
		return lo;
	}

	public long addTransaction(Transaction transaction, Agent agent)
			throws AppException {
		long lo = 0;
		OrderDetails orderDetails = new OrderDetails();
		Agent fromAgent = null;
		Agent toAgent = null;
		if (transaction.getPaytype().equals("0")) { // 即时到账给亲朋好友付款
			fromAgent = agentDAO.getAgentById(agent.getId());
			toAgent = agentDAO.getAgentByName(transaction.getSellerAccount());
			orderDetails.setPaymentReason(transaction.getOrderDetails()
					.getPaymentReason());
			orderDetails.setPaymentPrice(transaction.getOrderDetails()
					.getPaymentPrice());
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
			orderDetails.setShopPrice(transaction.getOrderDetails()
					.getPaymentPrice());
			orderDetails.setShopTotal(new Long(1));
			orderDetails.setShopName(transaction.getOrderDetails()
					.getPaymentReason());// 当给亲朋好友汇款时,汇款原因就等于商品名称

			transaction.setAmount(transaction.getOrderDetails()
					.getPaymentPrice());

			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);

			transaction.setStatus(Transaction.STATUS_3);
			transaction.setType(Transaction.TYPE_1);
			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
			AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
			fromAgent.setAllowBalance(ab.getAllowBalance().subtract(
					transaction.getOrderDetails().getPaymentPrice())); // 把钱从可用余额里面扣掉
			agentDAO.update(fromAgent);

			toAgent.setAllowBalance(ab1.getAllowBalance().add(
					transaction.getOrderDetails().getPaymentPrice())); // 把钱加到余额里面
			agentDAO.update(toAgent);
		} else if (transaction.getPaytype().equals("1")) { // 即时到账向陌生买家付款
			fromAgent = agentDAO.getAgentById(agent.getId());
			toAgent = agentDAO.getAgentByName(transaction.getSellerAccount());
			orderDetails.setShopName(transaction.getOrderDetails()
					.getShopName());
			orderDetails.setShopPrice(transaction.getOrderDetails()
					.getShopPrice());
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
			orderDetails.setShopUrl(transaction.getOrderDetails().getShopUrl());
			orderDetails.setShopTotal(new Long(1));
			orderDetails.setPaymentPrice(transaction.getOrderDetails()
					.getShopPrice());
			transaction.setAmount(transaction.getOrderDetails().getShopPrice());

			transaction.setFromAgent(fromAgent);
			transaction.setToAgent(toAgent);

			transaction.setStatus(Transaction.STATUS_3);
			transaction.setType(Transaction.TYPE_1);

			transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
			AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
			fromAgent.setAllowBalance(ab.getAllowBalance().subtract(
					transaction.getOrderDetails().getShopPrice())); // 把钱从可用余额里面扣掉
			/*
			 * fromAgent.setBalance(fromAgent.getBalance().subtract(
			 * transaction.getOrderDetails().getShopPrice())); // 同时把钱从总额额里面扣掉
			 */agentDAO.update(fromAgent);

			toAgent.setAllowBalance(ab1.getAllowBalance().add(
					transaction.getOrderDetails().getShopPrice())); // 把钱加到余额里面
			/*
			 * toAgent.setBalance(toAgent.getBalance().add(
			 * transaction.getOrderDetails().getShopPrice())); // 把钱加到总额里面
			 */agentDAO.update(toAgent);
		} else if (transaction.getPaytype().equals("2")) { // 担保交易付款

		} else if (transaction.getPaytype().equals("3")) { // 担保交易收款
			orderDetails.setShopName(transaction.getOrderDetails()
					.getShopName());
			orderDetails.setShopPrice(transaction.getOrderDetails()
					.getShopPrice());
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_2);
			orderDetails.setShopTotal(transaction.getOrderDetails()
					.getShopTotal());
			orderDetails.setShopUrl(transaction.getOrderDetails().getShopUrl());
			orderDetails.setLogisticsType(transaction.getOrderDetails()
					.getLogisticsType());
			orderDetails.setEmailPrice(transaction.getOrderDetails()
					.getEmailPrice());
			BigDecimal b1 = transaction.getOrderDetails().getShopPrice();
			BigDecimal b2 = new BigDecimal(transaction.getOrderDetails()
					.getShopTotal());
			BigDecimal b3 = transaction.getOrderDetails().getEmailPrice();
			transaction.setAmount(b1.multiply(b2).add(b3));

			transaction.setToAgent(agentDAO.getAgentById(agent.getId()));
			transaction.setFromAgent(agentDAO.getAgentByName(transaction
					.getBuyerAccount()));

			transaction.setStatus(Transaction.STATUS_1);
			transaction.setType(Transaction.TYPE_3);

		} else if (transaction.getPaytype().equals("4")) { // 即时到账收款
			orderDetails.setShopName(transaction.getOrderDetails()
					.getShopName());
			orderDetails.setShopPrice(transaction.getOrderDetails()
					.getShopPrice());
			orderDetails.setBuyType(transaction.getOrderDetails().getBuyType());
			orderDetails.setShopUrl(transaction.getOrderDetails().getShopUrl());
			orderDetails.setShopTotal(new Long(1)); // 即时到账收款没有商品数量,这里设置默认为1
			orderDetails.setEmailPrice(new BigDecimal("0")); // 即时到账收款没有邮费,这里设置默认为0.00

			transaction.setAmount(transaction.getOrderDetails().getShopPrice());

			transaction.setToAgent(agentDAO.getAgentById(agent.getId()));
			transaction.setFromAgent(agentDAO.getAgentByName(transaction
					.getBuyerAccount()));

			transaction.setStatus(Transaction.STATUS_1); // 等待买家付款
			transaction.setType(Transaction.TYPE_4);
		}

		orderDetails.setDetailsContent(transaction.getOrderDetails()
				.getDetailsContent());
		orderDetails.setConsigneeAddress(transaction.getOrderDetails()
				.getConsigneeAddress());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);

		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());

		String no = noUtil.getTransactionNo();
		transaction.setNo(no);

		this.addOrderDetails(orderDetails);
		transaction.setOrderDetails(orderDetails);
		transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));

		lo = transactionDAO.saveTransaction(transaction);
		// if(lo>0){
		// Agent fromAgent = null;
		// Agent toAgent = null;
		// if(transaction.getPaytype().equals("0") ||
		// transaction.getPaytype().equals("1")){
		// fromAgent = agentDAO.getAgentById(agent.getId());
		// toAgent = agentDAO.getAgentByName(transaction.getSellerAccount());
		// }
		// else if(transaction.getPaytype().equals("3") ||
		// transaction.getPaytype().equals("4")){
		// fromAgent = agentDAO.getAgentByName(transaction.getBuyerAccount());
		// }
		// sendEmail(transaction,agent,no,fromAgent,toAgent);
		// }
		return lo;
	}

	// public void sendEmail(long transactionId)throws AppException{
	// Transaction transaction =
	// transactionDAO.getTransactionById(transactionId);
	// Agent fromAgent = null;
	// Agent toAgent = null;
	// if(transaction.getType().equals("0") ||
	// transaction.getType().equals("1")){
	// fromAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
	// toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
	// }
	// else if(transaction.getType().equals("3") ||
	// transaction.getType().equals("4")){
	// fromAgent = agentDAO.getAgentByName(transaction.getBuyerAccount());
	// }
	// }

	public void sendBatchEmail(long transactionId) throws AppException {
		Transaction transaction = transactionDAO
				.getTransactionById(transactionId);
		Agent fromAgent = null;
		Agent toAgent = null;
		String sign = null;
		fromAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
		toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
		HashMap<String, String> params = new HashMap<String, String>();
		if (transaction.getType() == 5) {
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$amount$", transaction.getOrderDetails().getShopPrice()
					.toString());
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));
			params
					.put("$loginName$", transaction.getFromAgent()
							.getLoginName());

			sign = "flag=1&loginName="
					+ transaction.getFromAgent().getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=transactionPayment&tid="
					+ transaction.getId();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("有一笔交易等待您付款", "0020", transaction.getFromAgent()
					.getLoginName(), params, Certification.getProtocol()); // 发邮件
		} else if (transaction.getType() == 6) {
			params.put("$toAgentName$", toAgent.getName());
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopPrice$", transaction.getAmount().toString());
			params.put("$shopTotal$", "1");
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$amount$", transaction.getAmount().toString());
			params.put("$loginName$", toAgent.getLoginName());

			params.put("$type$", transaction.getType().toString());
			params.put("$agentid$", String.valueOf(fromAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));

			sign = "agentid=" + fromAgent.getId() + "&flag=1&loginName="
					+ toAgent.getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=getTransactionSuccessAndFailDetail&tid="
					+ transaction.getId() + "&type=" + transaction.getType();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("交易状态已改变为：交易成功", "0022", toAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		}

		else if (transaction.getType() == 7) {

			params.put("$RealyName$", toAgent.getName());
			params.put("$nicewords$", transaction.getOrderDetails()
					.getDetailsContent());
			params.put("$url$ ", transaction.getOrderDetails()
					.getDetailsContent());
			String url = com.neza.base.Constant.getLocalHost();
			params
					.put(
							"$url$",
							url
									+ "/transaction/transaction.do?thisAction=transactionGatherRedPacketByEmail&tid="
									+ transaction.getId()
									+ "&sign="
									+ MD5.encrypt("tid=" + transaction.getId()
											+ AnalyseParameter.EMAIL_SIGN_KEY));
			System.out.println("领取红包URL:" + params.get("$url$"));
			params.put("$imgUrl$", url + "/"
					+ transaction.getOrderDetails().getShopUrl());
			System.out.println("图片url:" + params.get("$imgUrl$"));
			MailUtil.sslSend("领取红包", "0032", toAgent.getLoginName(), params,
					Certification.getProtocol()); // 发邮件
		}
	}

	public void sendEmailByRepayment(long transactionId) throws AppException {
		Transaction transaction = transactionDAO
				.getTransactionById(transactionId);
		Agent fromAgent = null;
		Agent toAgent = null;
		fromAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
		toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
		HashMap<String, String> params = new HashMap<String, String>();
		if (transaction.getType() == Transaction.TYPE_9) {
			params.put("$toAgentName$", toAgent.getName());
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$paymentReason$", transaction.getOrderDetails()
					.getPaymentReason());
			params.put("$detailsContent$", transaction.getOrderDetails()
					.getDetailsContent());
			params.put("$amount$", transaction.getAmount().toString());

			MailUtil.sslSend("借款成功", "0090", toAgent.getLoginName(), params,
					Certification.getProtocol()); // 发邮件
		}
	}

	public void sendEmail(long transactionId) throws AppException {
		Transaction transaction = transactionDAO
				.getTransactionById(transactionId);
		Agent fromAgent = null;
		Agent toAgent = null;
		String sign = null;
		fromAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
		toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
		HashMap<String, String> params = new HashMap<String, String>();
		if (transaction.getPaytype().equals("0")) {
			params.put("$toAgentName$", toAgent.getName());
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopPrice$", transaction.getOrderDetails()
					.getPaymentPrice().toString());
			params.put("$shopTotal$", "1");
			params.put("$shopName$", transaction.getOrderDetails()
					.getPaymentReason());
			params.put("$amount$", transaction.getOrderDetails()
					.getPaymentPrice().toString());
			params.put("$loginName$", toAgent.getLoginName());

			params.put("$type$", transaction.getType().toString());
			params.put("$agentid$", String.valueOf(fromAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));

			sign = "agentid=" + fromAgent.getId() + "&flag=1&loginName="
					+ toAgent.getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=getTransactionSuccessAndFailDetail&tid="
					+ transaction.getId() + "&type=" + transaction.getType();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("交易状态已改变为：交易成功", "0022", toAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		} else if (transaction.getPaytype().equals("1")) {
			params.put("$toAgentName$", toAgent.getName());
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopPrice$", transaction.getOrderDetails()
					.getShopPrice().toString());
			params.put("$shopTotal$", "1");
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$amount$", transaction.getOrderDetails().getShopPrice()
					.toString());
			params.put("$loginName$", toAgent.getLoginName());

			params.put("$type$", transaction.getType().toString());
			params.put("$agentid$", String.valueOf(fromAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));

			sign = "agentid=" + fromAgent.getId() + "&flag=1&loginName="
					+ toAgent.getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=getTransactionSuccessAndFailDetail&tid="
					+ transaction.getId() + "&type=" + transaction.getType();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("交易状态已改变为：交易成功", "0022", toAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		} else if (transaction.getPaytype().equals("2")) {

		} else if (transaction.getPaytype().equals("3")) {
			BigDecimal b1 = transaction.getOrderDetails().getShopPrice();
			BigDecimal b2 = new BigDecimal(transaction.getOrderDetails()
					.getShopTotal());
			BigDecimal b3 = transaction.getOrderDetails().getEmailPrice();

			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$shopPrice$", b1.toString());
			params.put("$shopTotal$", b2.toString());
			params.put("$emailPrice$", b3.toString());
			params.put("$amount$", b1.multiply(b2).add(b3).toString());
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));
			params
					.put("$loginName$", transaction.getFromAgent()
							.getLoginName());

			sign = "flag=1&loginName="
					+ transaction.getFromAgent().getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=transactionPayment&tid="
					+ transaction.getId();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("有一笔交易等待您付款", "0023", transaction.getFromAgent()
					.getLoginName(), params, Certification.getProtocol()); // 发邮件

		} else if (transaction.getPaytype().equals("4")) {
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$amount$", transaction.getOrderDetails().getShopPrice()
					.toString());
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));
			params
					.put("$loginName$", transaction.getFromAgent()
							.getLoginName());

			sign = "flag=1&loginName="
					+ transaction.getFromAgent().getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=transactionPayment&tid="
					+ transaction.getId();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("有一笔交易等待您付款", "0020", transaction.getFromAgent()
					.getLoginName(), params, Certification.getProtocol()); // 发邮件
		}
	}

	public List<Long> addBatchCollect(Transaction transaction, Agent agent)
			throws AppException { // 批量收款
		long lo = 0;
		List<Long> tidList = new ArrayList<Long>();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setShopName(transaction.getOrderDetails().getShopName()); // 这里的收款理由也就是商品名称
		orderDetails.setShopPrice(transaction.getTotalPrice());
		orderDetails.setDetailsContent(transaction.getOrderDetails()
				.getDetailsContent());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_2);
		orderDetails.setShopTotal(new Long(1)); // 批量收款没有商品数量,这里设置默认为1
		orderDetails.setEmailPrice(new BigDecimal("0")); // 批量收款没有邮费,这里设置默认为0.00
		orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		this.addOrderDetails(orderDetails);

		for (int i = 0; i < transaction.getAgentName().length; i++) {
			Transaction tempTransaction = new Transaction();
			tempTransaction.setStatus(Transaction.STATUS_1); // 等待买家付款
			tempTransaction.setType(Transaction.TYPE_5); // 批量收款
			tempTransaction.setToAgent(agentDAO.getAgentById(agent.getId()));

			tempTransaction.setOrderDetails(orderDetails);
			tempTransaction.setAccountDate(new Timestamp(System
					.currentTimeMillis()));
			Agent age = agentDAO.getAgentByName(transaction.getAgentName()[i]);

			if (age != null) {
				tempTransaction.setFromAgent(age);
				tempTransaction.setNo(noUtil.getTransactionNo());
				tempTransaction.setAmount(new BigDecimal(transaction
						.getPayPrice()[i]));
				lo = transactionDAO.saveTransaction(tempTransaction);
				tidList.add(lo);
			}
		}
		return tidList;
	}

	public List<Long> addBatchPayment(Transaction transaction, Agent agent)
			throws AppException { // 批量付款
		long lo = 0;
		List<Long> tidList = new ArrayList<Long>();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setShopName(transaction.getOrderDetails().getShopName()); // 这里的付款理由也就是商品名称
		orderDetails.setShopPrice(transaction.getTotalPrice());
		orderDetails.setPaymentPrice(transaction.getTotalPrice());
		orderDetails.setDetailsContent(transaction.getOrderDetails()
				.getDetailsContent());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
		orderDetails.setShopTotal(new Long(1)); // 批量付款没有商品数量,这里设置默认为1
		orderDetails.setEmailPrice(new BigDecimal(0)); // 批量付款没有邮费,这里设置默认为0.00
		orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setShopUrl(transaction.getShopUrl());
		this.addOrderDetails(orderDetails);

		Agent myAgent = null;

		myAgent = agentDAO.getAgentById(agent.getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
		myAgent.setAllowBalance(ab.getAllowBalance().subtract(
				transaction.getTotalPrice())); // 把钱从可用余额里面扣掉
		// myAgent.setBalance(myAgent.getBalance().subtract(
		// transaction.getTotalPrice())); // 同时把钱从总额额里面扣掉
		agentDAO.update(myAgent);

		for (int i = 0; i < transaction.getAgentName().length; i++) {
			Transaction tempTransaction = new Transaction();
			tempTransaction.setStatus(Transaction.STATUS_3); // 交易成功
			tempTransaction.setType(Transaction.TYPE_6); // 批量付款
			tempTransaction.setFromAgent(agentDAO.getAgentById(agent.getId()));

			tempTransaction.setOrderDetails(orderDetails);
			tempTransaction.setAccountDate(new Timestamp(System
					.currentTimeMillis()));
			tempTransaction
					.setPayDate(new Timestamp(System.currentTimeMillis()));
			Agent toAgent = agentDAO
					.getAgentByName(transaction.getAgentName()[i]);
			agentDAO.synallowBalance(toAgent);
			if (toAgent != null) {
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
				toAgent.setAllowBalance(ab1.getAllowBalance().add(
						new BigDecimal(transaction.getPayPrice()[i]))); // 把钱加到余额里面
				// toAgent.setBalance(toAgent.getBalance().add(
				// new BigDecimal(transaction.getPayPrice()[i]))); // 把钱加到总额里面
				agentDAO.update(toAgent);

				tempTransaction.setToAgent(toAgent);
				tempTransaction.setNo(noUtil.getTransactionNo());
				tempTransaction.setAmount(new BigDecimal(transaction
						.getPayPrice()[i]));
				lo = transactionDAO.saveTransaction(tempTransaction);
				tidList.add(lo);
			}
		}
		return tidList;
	}

	public List<Long> addBatchRedPacket(Transaction transaction, Agent agent)
			throws AppException { // 批量付款
		long lo = 0;
		List<Long> tidList = new ArrayList<Long>();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setShopName(transaction.getOrderDetails()
				.getDetailsContent()); // 这里的付款理由也就是商品名称
		orderDetails.setShopPrice(transaction.getTotalPrice());
		orderDetails.setPaymentPrice(transaction.getTotalPrice());
		orderDetails.setDetailsContent(transaction.getOrderDetails()
				.getDetailsContent());
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_2);// 担保交易
		orderDetails.setShopTotal(new Long(1)); // 批量付款没有商品数量,这里设置默认为1
		orderDetails.setEmailPrice(new BigDecimal(0)); // 批量付款没有邮费,这里设置默认为0.00
		orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setShopUrl(transaction.getShopUrl());
		this.addOrderDetails(orderDetails);

		// Agent myAgent = null;
		// 把钱从可用余额里面扣掉
		// myAgent = agentDAO.getAgentById(agent.getId());
		// agentDAO.synallowBalance(myAgent);
		// myAgent.setAllowBalance(myAgent.getAllowBalance().subtract(
		// transaction.getTotalPrice()));
		// myAgent.setBalance(myAgent.getBalance().subtract(
		// transaction.getTotalPrice())); // 同时把钱从总额额里面扣掉
		// agentDAO.update(myAgent);

		for (int i = 0; i < transaction.getAgentName().length; i++) {
			Transaction tempTransaction = new Transaction();
			AgentBalance agentBalance = agentDAO.getAgentBalance(agent.getId());
			BigDecimal payAmount = new BigDecimal(transaction.getPayPrice()[i]);
			if (agentBalance.getAllowBalance().compareTo(payAmount) < 0) {
				break;
			}
			tempTransaction.setStatus(Transaction.STATUS_2); // 等待卖家发货
			tempTransaction.setType(Transaction.TYPE_7); // 发红包
			tempTransaction.setFromAgent(agentDAO.getAgentById(agent.getId()));

			tempTransaction.setOrderDetails(orderDetails);
			tempTransaction.setAccountDate(new Timestamp(System
					.currentTimeMillis()));
			tempTransaction
					.setPayDate(new Timestamp(System.currentTimeMillis()));
			Agent toAgent = agentDAO
					.getAgentByName(transaction.getAgentName()[i]);
			agentDAO.synallowBalance(toAgent);
			if (toAgent != null) {
				// toAgent.setAllowBalance(toAgent.getAllowBalance().add(
				// new BigDecimal(transaction.getPayPrice()[i]))); // 把钱加到余额里面
				// toAgent.setBalance(toAgent.getBalance().add(
				// new BigDecimal(transaction.getPayPrice()[i]))); // 把钱加到总额里面
				// agentDAO.update(toAgent);
				String url = com.neza.base.Constant.getLocalHost();

				tempTransaction.setToAgent(toAgent);
				tempTransaction.setNo(noUtil.getTransactionNo());
				tempTransaction.setAmount(new BigDecimal(transaction
						.getPayPrice()[i]));
				lo = transactionDAO.saveTransaction(tempTransaction);
				String mark2 = url
						+ "/transaction/transaction.do?thisAction=transactionGatherRedPacketByEmail&tid="
						+ tempTransaction.getId()
						+ "&sign="
						+ MD5.encrypt("tid=" + tempTransaction.getId()
								+ AnalyseParameter.EMAIL_SIGN_KEY);
				tempTransaction.setMark(mark2);
				String mark = "冻结发红包的余额,等待买加接受红包后解冻";
				moveAllowBalanceToNotallowBalance(tempTransaction
						.getFromAgent(), tempTransaction.getAmount(), mark,
						orderDetails.getDetailsContent());
				tidList.add(lo);
			}
		}
		return tidList;
	}

	public List getAgentAddressList(Agent agent) throws AppException {
		return transactionDAO.getAgentAddressList(agent);
	}

	public long addOrderDetails(OrderDetails orderDetails) throws AppException {
		return transactionDAO.addOrderDetails(orderDetails);
	}

	public List getContactList(Agent agent) throws AppException {
		return transactionDAO.getContactList(agent);
	}

	public List getListContact(Agent agent, String searchContactInput)
			throws AppException {
		return transactionDAO.getListContact(agent, searchContactInput);
	}

	public void updateTransactionStatus(long statusid, long tid)
			throws AppException {
		Transaction transaction = transactionDAO.getTransactionById(tid);
		transaction.setStatus(new Long(statusid));
		transactionDAO.updateTransactionStatus(transaction);
	}

	public Transaction getTransactionById(long tid, long orderid)
			throws AppException {
		// OrderDetails orderDetails =
		// transactionDAO.getOrderDetailsById(orderid);
		Transaction transaction = transactionDAO.getTransactionById(tid);
		// if(orderDetails!=null)
		// transaction.setOrderDetails(orderDetails);
		return transaction;
	}

	public Transaction updateTransactionPrice(Transaction transaction)
			throws AppException {
		Transaction trans = transactionDAO.getTransactionById(transaction
				.getId());
		OrderDetails orderDetails = transactionDAO.getOrderDetailsById(trans
				.getOrderDetails().getId());
		orderDetails.setEmailPrice(transaction.getOrderDetails()
				.getEmailPrice());
		orderDetails.setSalePrice(transaction.getOrderDetails().getSalePrice());
		transactionDAO.updateOrderDetails(orderDetails);

		trans.setAmount(transaction.getAmount());
		transactionDAO.updateTransactionPrice(trans);

		return trans;
		// Agent fromAgent =
		// agentDAO.getAgentById(trans.getFromAgent().getId());
		// Agent toAgent = agentDAO.getAgentById(trans.getToAgent().getId());
		// sendEmail(trans,fromAgent,toAgent);
	}

	public void sendEmail(Transaction transaction) throws AppException {
		HashMap<String, String> params = new HashMap<String, String>();
		Agent fromAgent = agentDAO.getAgentById(transaction.getFromAgent()
				.getId());
		Agent toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
		String sign = null;
		if (transaction.getStatus() == Transaction.STATUS_10) {
			if (transaction.getOrderDetails().getRefundsNote().indexOf("##") > -1) {
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", fromAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$loginName$", toAgent.getLoginName());

				String refundNote[] = transaction.getOrderDetails()
						.getRefundsNote().split("##");
				String Note = "";
				for (int i = 0; i < refundNote.length; i++) {
					Note = Note + refundNote[i] + "\n";
				}
				params.put("$refundNote$", Note);
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				sign = "flag=1&loginName="
						+ toAgent.getLoginName()
						+ "&orderid="
						+ transaction.getOrderDetails().getId()
						+ "&service=email_login_forward&thisAction=getRefundMentBeforeShippingDetail&tid="
						+ transaction.getId();
				params.put("$sign$", MD5.encrypt(sign
						+ AnalyseParameter.EMAIL_SIGN_KEY));

				MailUtil.sslSend("买家修改了申请退款,等待你处理", "0029", toAgent
						.getLoginName(), params, Certification.getProtocol()); // 发邮件
			} else {
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", fromAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$loginName$", toAgent.getLoginName());

				String refundNote1[] = transaction.getOrderDetails()
						.getRefundsNote().split("##");
				String Note1 = "";
				for (int i = 0; i < refundNote1.length; i++) {
					Note1 = Note1 + refundNote1[i] + "\n";
				}

				params.put("$refundNote$", Note1);
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				sign = "flag=1&loginName="
						+ toAgent.getLoginName()
						+ "&orderid="
						+ transaction.getOrderDetails().getId()
						+ "&service=email_login_forward&thisAction=getRefundMentBeforeShippingDetail&tid="
						+ transaction.getId();
				params.put("$sign$", MD5.encrypt(sign
						+ AnalyseParameter.EMAIL_SIGN_KEY));
				MailUtil.sslSend("买家申请退款,等待你处理", "0028",
						toAgent.getLoginName(), params, Certification
								.getProtocol()); // 发邮件
			}
		} else if (transaction.getStatus() == Transaction.STATUS_11) {
			BigDecimal b1 = transaction.getOrderDetails().getShopPrice();
			BigDecimal b2 = new BigDecimal(transaction.getOrderDetails()
					.getShopTotal());
			BigDecimal b3 = transaction.getOrderDetails().getEmailPrice();
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$shopPrice$", b1.toString());
			params.put("$shopTotal$", b2.toString());
			params.put("$emailPrice$", b3.toString());
			params.put("$salePrice$", transaction.getOrderDetails()
					.getSalePrice() == null ? "0.00" : transaction
					.getOrderDetails().getSalePrice().toString());
			params.put("$amount$", transaction.getAmount().toString());
			params.put("$loginName$", fromAgent.getLoginName());

			String refundNote2[] = transaction.getOrderDetails()
					.getRefundsNote().split("##");
			String Note2 = "";
			for (int i = 0; i < refundNote2.length; i++) {
				Note2 = Note2 + refundNote2[i] + "\n";
			}

			params.put("$refundNote$", Note2);
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));
			params.put("$agentid$", String.valueOf(fromAgent.getId()));

			sign = "flag=3&loginName="
					+ fromAgent.getLoginName()
					+ "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&thisAction=getRefundMentBeforeShippingDetail&tid="
					+ transaction.getId();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("卖家已同意退款,交易关闭", "0030", fromAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		} else if (transaction.getStatus() == Transaction.STATUS_4) {
			BigDecimal b1 = transaction.getOrderDetails().getShopPrice();
			BigDecimal b2 = new BigDecimal(transaction.getOrderDetails()
					.getShopTotal());
			BigDecimal b3 = transaction.getOrderDetails().getEmailPrice();
			String closeReson = "";
			if (transaction.getOtherMsg() != null
					&& !transaction.getOtherMsg().equals(""))
				closeReson = transaction.getOtherMsg();
			else
				closeReson = transaction.getCloseReson();
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$shopPrice$", b1.toString());
			params.put("$shopTotal$", b2.toString());
			params.put("$emailPrice$", b3.toString());
			params.put("$closeReson$", closeReson);
			params.put("$salePrice$", transaction.getOrderDetails()
					.getSalePrice() == null ? "0.00" : transaction
					.getOrderDetails().getSalePrice().toString());
			params.put("$amount$", transaction.getAmount().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));
			params.put("$agentid$", String.valueOf(fromAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$loginName$", fromAgent.getLoginName());

			sign = "agentid=" + fromAgent.getId() + "&flag=1&loginName="
					+ fromAgent.getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=getTransactionSuccessAndFailDetail&tid="
					+ transaction.getId() + "&type=3";
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("卖家主动关闭交易", "0031", fromAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		} else {
			BigDecimal b1 = transaction.getOrderDetails().getShopPrice();
			BigDecimal b2 = new BigDecimal(transaction.getOrderDetails()
					.getShopTotal());
			BigDecimal b3 = transaction.getOrderDetails().getEmailPrice();
			params.put("$fromAgentName$", fromAgent.getName());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$shopPrice$", b1.toString());
			params.put("$shopTotal$", b2.toString());
			params.put("$emailPrice$", b3.toString());
			// params.put("$salePrice$",
			// transaction.getOrderDetails().getSalePrice().toString());
			params.put("$salePrice$", transaction.getOrderDetails()
					.getSalePrice() == null ? "0.00" : transaction
					.getOrderDetails().getSalePrice().toString());
			params.put("$amount$", transaction.getAmount().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));
			params.put("$agentid$", String.valueOf(fromAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$loginName$", fromAgent.getLoginName());

			sign = "flag=1&loginName=" + fromAgent.getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=transactionPayment&tid="
					+ transaction.getId();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("卖家已修改交易价格,等待买家付款", "0027", fromAgent
					.getLoginName(), params, Certification.getProtocol()); // 发邮件
		}
	}

	public List getBatchCollectDetail(TransactionListForm transactionListForm,
			Agent agent) throws AppException {
		return transactionDAO.getBatchCollectDetail(transactionListForm, agent);
	}

	public List getBatchCollectDetailById(
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO.getBatchCollectDetailById(transactionListForm);
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public List getBatchPaymentDetail(TransactionListForm transactionListForm,
			Agent agent) throws AppException {
		return transactionDAO.getBatchPaymentDetail(transactionListForm, agent);
	}

	public List getBatchPaymentDetailById(
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO.getBatchPaymentDetailById(transactionListForm);
	}

	public void addMark(Transaction transaction) throws AppException {
		Transaction trans = transactionDAO.getTransactionById(transaction
				.getId());
		trans.setMark(transaction.getMark());
		trans.setFlagStatus(transaction.getFlagStatus());
		transactionDAO.addMark(trans);
	}

	public AgentAddress getAgentAddressById(Agent agent) throws AppException {
		return transactionDAO.getAgentAddressById(agent);
	}

	public Transaction transactionPaymentReturnByBank(String no)
			throws AppException {
		long lo = 0;
		Agent myAgent = null;
		Agent toAgent = null;
		Transaction transaction = transactionDAO.getTransactionByNo(no);
		if (transaction.getType() == 3) {
			if (transaction.getStatus() == Transaction.STATUS_6) { // 买家确认已经收到货,点同意付款
				transaction.setStatus(Transaction.STATUS_3); // 交易成功
				OrderDetails orderDetails = transactionDAO
						.getOrderDetailsById(transaction.getOrderDetails()
								.getId());
				orderDetails.setFinishDate(new Timestamp(System
						.currentTimeMillis())); // 更新交易结束时间
				transactionDAO.updateOrderDetails(orderDetails);
				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
				myAgent.setNotallowBalance(ab.getNotAllowBalance().subtract(
						transaction.getAmount())); // 把钱从冻结余额那里扣出来
				// myAgent.setBalance(myAgent.getBalance().subtract(
				// transaction.getAmount())); // 同时把账户总额的钱扣出来
				agentDAO.update(myAgent);
				toAgent = agentDAO.getAgentById(transaction.getToAgent()
						.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
				toAgent.setAllowBalance(ab1.getAllowBalance().add(
						transaction.getAmount())); // 再把从冻结余额扣出来的钱放到卖家账户的可用余额上
				// toAgent.setBalance(toAgent.getBalance().add(
				// transaction.getAmount())); // 同时把从冻结余额扣出来的钱放到卖家账户的账户总额上

				agentDAO.update(toAgent);
			} else {
				transaction.setStatus(Transaction.STATUS_2);// 担保交易付款后,状态变成等待卖家发货
				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
				myAgent.setAllowBalance(ab.getAllowBalance().subtract(
						transaction.getAmount())); // 先从可用余额那里扣钱
				myAgent.setNotallowBalance(ab.getNotAllowBalance().add(
						transaction.getAmount())); // 把钱放到冻结余额那里,在原有的基础上累加上去
				agentDAO.update(myAgent);
			}
		} else {
			OrderDetails orderDetails = transactionDAO
					.getOrderDetailsById(transaction.getOrderDetails().getId());
			orderDetails
					.setFinishDate(new Timestamp(System.currentTimeMillis())); // 更新交易结束时间
			transactionDAO.updateOrderDetails(orderDetails);
			transaction.setStatus(Transaction.STATUS_3);// 交易成功
			myAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
			myAgent.setAllowBalance(ab.getAllowBalance().subtract(
					transaction.getAmount())); // 把钱从余额里面扣掉
			// myAgent.setBalance(myAgent.getBalance().subtract(
			// transaction.getAmount())); // 同时把钱从总额额里面扣掉
			agentDAO.update(myAgent);
			toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
			toAgent.setAllowBalance(ab1.getAllowBalance().add(
					transaction.getAmount())); // 把钱加到余额里面
			// toAgent.setBalance(toAgent.getBalance()
			// .add(transaction.getAmount())); // 把钱加到总额里面
			agentDAO.update(toAgent);
		}
		transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		lo = transactionDAO.updateTransactionStatus(transaction);
		if (lo > 0) {
			// sendEmail(transaction,myAgent,toAgent);
		}
		return transaction;
	}

	// 手机网银充值完成
	public Transaction transaction19payByBank(String no) throws AppException {
		long lo = 0;
		Agent myAgent = null;
		Agent toAgent = null;
		Transaction transaction = transactionDAO.getTransactionByNo(no);

		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderDetails().getId());
		String r = "";
		try {
			r = orderDetails.getShopName().substring(
					orderDetails.getShopName().indexOf('$') + 1);
			System.out.println(r + "-------手续费");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// 获得手续费金额
		BigDecimal rate = new BigDecimal(0);
		if (r != null && !"".equals(r)) {
			rate = new BigDecimal(r);
		}
		System.out.println(rate + "--------手续费");
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis())); // 更新交易结束时间
		transactionDAO.updateOrderDetails(orderDetails);
		transaction.setStatus(Transaction.STATUS_3);// 交易成功
		myAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
		myAgent.setAllowBalance(ab.getAllowBalance().subtract(
				transaction.getAmount())); // 把钱从余额里面扣掉
		agentDAO.update(myAgent);
		toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
		toAgent.setAllowBalance(ab1.getAllowBalance().add(
				transaction.getAmount()).multiply(rate)); // 把钱加到余额里面//2009-11-5管昊：手机网银充值扣手续费
		agentDAO.update(toAgent);

		// 手续费收取账号 rate@qmpay.com.cn
		Agent newAgent = agentDAO.getAgentByEmail("rate@qmpay.com.cn");
		// 生成手续费收取交易
		OrderDetails rateOder = new OrderDetails();
		rateOder.setOrderNo(noUtil.getMobileOrderNo());
		rateOder.setShopName("手机网银充值手续费");
		rateOder.setShopTotal(new Long(1));
		rateOder.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
		rateOder.setShopPrice(rate);
		rateOder.setPaymentPrice(rate);
		rateOder.setDetailsContent("手机网银充值手续费");
		rateOder.setOrderDetailsNo(noUtil.getOrderDetailsNo());

		transactionDAO.addOrderDetails(rateOder);

		Transaction rateTransaction = new Transaction();
		rateTransaction.setNo(noUtil.getTransactionNo());
		rateTransaction.setAmount(rate);
		rateTransaction.setType(Transaction.TYPE_200);// 手续费
		rateTransaction.setStatus(Transaction.STATUS_3);// 成功
		rateTransaction
				.setAccountDate(new Timestamp(System.currentTimeMillis()));// 创建时间
		rateTransaction.setPayDate(new Timestamp(System.currentTimeMillis()));// 付款时间
		rateTransaction.setToAgent(newAgent);
		rateTransaction.setFromAgent(toAgent);
		rateTransaction.setOrderDetails(rateOder);
		rateTransaction.setMark("手机网银充值手续费");

		transactionDAO.saveTransaction(rateTransaction);
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab2 = agentDAO.getAgentBalance(newAgent.getId());
		newAgent.setAllowBalance(ab2.getAllowBalance().add(rate));
		agentDAO.update(newAgent);

		transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		lo = transactionDAO.updateTransactionStatus(transaction);

		return transaction;
	}

	public void updateTransactionPayment(long transactionId)
			throws AppException {
		Agent myAgent = null;
		Agent toAgent = null;
		Transaction transaction = transactionDAO
				.getTransactionById(transactionId);
		if (transaction.getType() == 3) {
			if (transaction.getStatus() == Transaction.STATUS_6) { // 买家确认已经收到货,点同意付款
				transaction.setStatus(Transaction.STATUS_3); // 交易成功
				OrderDetails orderDetails = transactionDAO
						.getOrderDetailsById(transaction.getOrderDetails()
								.getId());
				orderDetails.setFinishDate(new Timestamp(System
						.currentTimeMillis())); // 更新交易结束时间
				transactionDAO.updateOrderDetails(orderDetails);

				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
				myAgent.setNotallowBalance(ab.getNotAllowBalance().subtract(
						transaction.getAmount())); // 把钱从冻结余额那里扣出来
				// myAgent.setBalance(myAgent.getBalance().subtract(
				// transaction.getAmount())); // 同时把账户总额的钱扣出来

				agentDAO.update(myAgent);
				toAgent = agentDAO.getAgentById(transaction.getToAgent()
						.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
				toAgent.setAllowBalance(ab1.getAllowBalance().add(
						transaction.getAmount())); // 再把从冻结余额扣出来的钱放到卖家账户的可用余额上
				// toAgent.setBalance(toAgent.getBalance().add(
				// transaction.getAmount())); // 同时把从冻结余额扣出来的钱放到卖家账户的账户总额上
				agentDAO.update(toAgent);
			} else {
				transaction.setStatus(Transaction.STATUS_2);// 担保交易付款后,状态变成等待卖家发货
				toAgent = agentDAO.getAgentById(transaction.getToAgent()
						.getId());
				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
				myAgent.setAllowBalance(ab.getAllowBalance().subtract(
						transaction.getAmount())); // 先从可用余额那里扣钱
				myAgent.setNotallowBalance(ab.getNotAllowBalance().add(
						transaction.getAmount())); // 把钱放到冻结余额那里,在原有的基础上累加上去
				agentDAO.update(myAgent);
			}
		} else {
			OrderDetails orderDetails = transactionDAO
					.getOrderDetailsById(transaction.getOrderDetails().getId());
			orderDetails
					.setFinishDate(new Timestamp(System.currentTimeMillis())); // 更新交易结束时间
			transactionDAO.updateOrderDetails(orderDetails);
			transaction.setStatus(Transaction.STATUS_3);// 交易成功
			myAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
			myAgent.setAllowBalance(ab.getAllowBalance().subtract(
					transaction.getAmount())); // 把钱从余额里面扣掉
			// myAgent.setBalance(myAgent.getBalance().subtract(
			// transaction.getAmount())); // 同时把钱从总额额里面扣掉
			agentDAO.update(myAgent);
			toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
			// 获取AgentBalance对象（用于获取金额）
			AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
			toAgent.setAllowBalance(ab1.getAllowBalance().add(
					transaction.getAmount())); // 把钱加到余额里面
			// toAgent.setBalance(toAgent.getBalance()
			// .add(transaction.getAmount())); // 把钱加到总额里面
			agentDAO.update(toAgent);
		}
		transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateTransactionStatus(transaction);
	}

	public void sendEmail(long transactionId, Logistics logistics)
			throws AppException {
		Transaction transaction = transactionDAO
				.getTransactionById(transactionId);
		HashMap<String, String> params = new HashMap<String, String>();
		Agent myAgent = null;
		Agent toAgent = null;
		String sign = null;
		if (transaction.getType() == 3) {
			if (transaction.getStatus() == Transaction.STATUS_6) {
				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				toAgent = agentDAO.getAgentById(transaction.getToAgent()
						.getId());
				String companyName = "";
				String lNo = "";
				if (logistics.getCompanyName() != null
						&& !"".equals(logistics.getCompanyName()))
					companyName = logistics.getCompanyName();
				else
					companyName = "无";
				if (logistics.getNo() != null && !"".equals(logistics.getNo()))
					lNo = logistics.getNo();
				else
					lNo = "无";
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", myAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$companyName$", companyName);
				params.put("$shippingNo$", lNo);
				params.put("$note$", logistics.getNote());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$loginName$", myAgent.getLoginName());

				params.put("$agentid$", String.valueOf(myAgent.getId()));
				params.put("$statusid$", transaction.getStatus().toString());
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				sign = "agentid=" + myAgent.getId() + "&flag=0&loginName="
						+ myAgent.getLoginName() + "&orderid="
						+ transaction.getOrderDetails().getId()
						+ "&service=email_login_forward&statusid="
						+ transaction.getStatus()
						+ "&thisAction=getTransactionShippingDetailById&tid="
						+ transaction.getId() + "&type=1";
				params.put("$sign$", MD5.encrypt(sign
						+ AnalyseParameter.EMAIL_SIGN_KEY));

				MailUtil.sslSend("有一笔交易等待你确认收货", "0025",
						myAgent.getLoginName(), params, Certification
								.getProtocol()); // 发邮件
			} else if (transaction.getStatus() == Transaction.STATUS_3) {
				toAgent = agentDAO.getAgentById(transaction.getToAgent()
						.getId());
				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", myAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopPrice$", transaction.getOrderDetails()
						.getShopPrice().toString());
				params.put("$emailPrice$", transaction.getOrderDetails()
						.getEmailPrice().toString());
				params.put("$shopTotal$", transaction.getOrderDetails()
						.getShopTotal().toString());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$loginName$", toAgent.getLoginName());

				params.put("$type$", transaction.getType().toString());
				params.put("$agentid$", String.valueOf(myAgent.getId()));
				params.put("$statusid$", transaction.getStatus().toString());
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				sign = "agentid=" + myAgent.getId() + "&flag=1&loginName="
						+ toAgent.getLoginName() + "&orderid="
						+ transaction.getOrderDetails().getId()
						+ "&service=email_login_forward&statusid="
						+ transaction.getStatus()
						+ "&thisAction=getTransactionShippingDetailById&tid="
						+ transaction.getId() + "&type=0";
				params.put("$sign$", MD5.encrypt(sign
						+ AnalyseParameter.EMAIL_SIGN_KEY));

				MailUtil.sslSend("交易状态已改变为：交易成功", "0026", toAgent
						.getLoginName(), params, Certification.getProtocol()); // 发邮件
			} else {
				toAgent = agentDAO.getAgentById(transaction.getToAgent()
						.getId());
				myAgent = agentDAO.getAgentById(transaction.getFromAgent()
						.getId());
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", myAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$loginName$", toAgent.getLoginName());

				params.put("$agentid$", String.valueOf(myAgent.getId()));
				params.put("$statusid$", transaction.getStatus().toString());
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				sign = "agentid=" + myAgent.getId() + "&flag=1&loginName="
						+ toAgent.getLoginName() + "&orderid="
						+ transaction.getOrderDetails().getId()
						+ "&service=email_login_forward&shipment=1&statusid="
						+ transaction.getStatus()
						+ "&thisAction=getTransactionPaymentDetailById&tid="
						+ transaction.getId() + "&type=0";
				params.put("$sign$", MD5.encrypt(sign
						+ AnalyseParameter.EMAIL_SIGN_KEY));

				MailUtil.sslSend("有一笔交易等待你发货", "0024", toAgent.getLoginName(),
						params, Certification.getProtocol()); // 发邮件
			}
		} else if (transaction.getType() == 4 || transaction.getType() == 5) {
			myAgent = agentDAO.getAgentById(transaction.getFromAgent().getId());
			toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
			params.put("$toAgentName$", toAgent.getName());
			params.put("$fromAgentName$", myAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopPrice$", transaction.getOrderDetails()
					.getShopPrice().toString());
			params.put("$shopTotal$", transaction.getOrderDetails()
					.getShopTotal().toString());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$amount$", transaction.getAmount().toString());
			params.put("$loginName$", toAgent.getLoginName());

			params.put("$type$", transaction.getType().toString());
			params.put("$agentid$", String.valueOf(myAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));

			sign = "agentid=" + myAgent.getId() + "&flag=1&loginName="
					+ toAgent.getLoginName() + "&orderid="
					+ transaction.getOrderDetails().getId()
					+ "&service=email_login_forward&statusid="
					+ transaction.getStatus()
					+ "&thisAction=transactionDetailByDunAndBatch&tid="
					+ transaction.getId() + "&type=" + transaction.getType();
			params.put("$sign$", MD5.encrypt(sign
					+ AnalyseParameter.EMAIL_SIGN_KEY));

			MailUtil.sslSend("交易状态已改变为：交易成功", "0021", toAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		}
	}

	private void sendEmail(Transaction transaction, Agent myAgent,
			Agent toAgent, Logistics logistics) throws AppException {
		HashMap<String, String> params = new HashMap<String, String>();
		if (transaction.getType() == 3) {
			if (transaction.getStatus() == Transaction.STATUS_6) {
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", myAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$companyName$", logistics.getCompanyName());
				params.put("$shippingNo$", logistics.getNo());
				params.put("$note$", logistics.getNote());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$agentLoginName$", myAgent.getLoginName());

				params.put("$agentid$", String.valueOf(myAgent.getId()));
				params.put("$statusid$", transaction.getStatus().toString());
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				MailUtil.sslSend("有一笔交易等待你确认收货", "0025",
						myAgent.getLoginName(), params, Certification
								.getProtocol()); // 发邮件
			} else if (transaction.getStatus() == Transaction.STATUS_3) {
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", myAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopPrice$", transaction.getOrderDetails()
						.getShopPrice().toString());
				params.put("$emailPrice$", transaction.getOrderDetails()
						.getEmailPrice().toString());
				params.put("$shopTotal$", transaction.getOrderDetails()
						.getShopTotal().toString());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$agentLoginName$", toAgent.getLoginName());

				params.put("$type$", transaction.getType().toString());
				params.put("$agentid$", String.valueOf(myAgent.getId()));
				params.put("$statusid$", transaction.getStatus().toString());
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				MailUtil.sslSend("交易状态已改变为：交易成功", "0026", toAgent
						.getLoginName(), params, Certification.getProtocol()); // 发邮件
			} else {
				params.put("$toAgentName$", toAgent.getName());
				params.put("$fromAgentName$", myAgent.getName());
				params.put("$no$", transaction.getNo());
				params.put("$shopName$", transaction.getOrderDetails()
						.getShopName());
				params.put("$amount$", transaction.getAmount().toString());
				params.put("$agentLoginName$", toAgent.getLoginName());

				params.put("$agentid$", String.valueOf(myAgent.getId()));
				params.put("$statusid$", transaction.getStatus().toString());
				params.put("$tid$", String.valueOf(transaction.getId()));
				params.put("$orderid$", String.valueOf(transaction
						.getOrderDetails().getId()));

				MailUtil.sslSend("有一笔交易等待你发货", "0024", toAgent.getLoginName(),
						params, Certification.getProtocol()); // 发邮件
			}
		} else if (transaction.getType() == 4) {
			params.put("$toAgentName$", toAgent.getName());
			params.put("$fromAgentName$", myAgent.getName());
			params.put("$no$", transaction.getNo());
			params.put("$shopPrice$", transaction.getOrderDetails()
					.getShopPrice().toString());
			params.put("$shopTotal$", transaction.getOrderDetails()
					.getShopTotal().toString());
			params.put("$shopName$", transaction.getOrderDetails()
					.getShopName());
			params.put("$amount$", transaction.getAmount().toString());
			params.put("$agentLoginName$", toAgent.getLoginName());

			params.put("$type$", transaction.getType().toString());
			params.put("$agentid$", String.valueOf(myAgent.getId()));
			params.put("$statusid$", transaction.getStatus().toString());
			params.put("$tid$", String.valueOf(transaction.getId()));
			params.put("$orderid$", String.valueOf(transaction
					.getOrderDetails().getId()));

			MailUtil.sslSend("交易状态已改变为：交易成功", "0021", toAgent.getLoginName(),
					params, Certification.getProtocol()); // 发邮件
		}
	}

	public Transaction getRefundMentById(Transaction transaction)
			throws AppException {
		String refundNote = "";
		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderid());
		if (orderDetails.getRefundsNote() != null)
			refundNote = orderDetails.getRefundsNote() + "##"
					+ transaction.getOrderDetails().getRefundsNote();
		else
			refundNote = transaction.getOrderDetails().getRefundsNote();
		orderDetails.setRefundsNote(refundNote);
		orderDetails.setStatus(Transaction.STATUS_10);
		transactionDAO.updateOrderDetails(orderDetails);

		Transaction trans = transactionDAO.getTransactionById(transaction
				.getTid());
		trans.setRefundsDate(new Timestamp(System.currentTimeMillis()));
		trans.setStatus(Transaction.STATUS_10);
		trans.setRefundsStatus(new Long(1));
		trans.setOrderDetails(orderDetails);
		transactionDAO.updateTransactionStatus(trans);
		return trans;
	}

	public Transaction applicationRefundShop(Transaction transaction)
			throws AppException {

		// String refundNote ="";
		// OrderDetails orderDetails =
		// transactionDAO.getOrderDetailsById(transaction.getOrderid());
		// if(orderDetails.getRefundsNote()!=null)
		// refundNote =
		// orderDetails.getRefundsNote()+"##"+transaction.getOrderDetails().getRefundsNote();
		// else
		// refundNote = transaction.getOrderDetails().getRefundsNote();
		// orderDetails.setRefundsNote(refundNote);
		// orderDetails.setStatus(Transaction.STATUS_7);
		// transactionDAO.updateOrderDetails(orderDetails);
		//		

		String refundNote = "";
		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderid());
		if (orderDetails.getRefundsNote() != null)
			refundNote = orderDetails.getRefundsNote() + "##"
					+ transaction.getOrderDetails().getRefundsNote();
		else
			refundNote = transaction.getOrderDetails().getRefundsNote();
		orderDetails.setRefundsNote(refundNote);
		// orderDetails.setStatus(Transaction.STATUS_7);
		transactionDAO.updateOrderDetails(orderDetails);

		Transaction trans = transactionDAO.getTransactionById(transaction
				.getTid());

		// trans.setRefundsDate(new Timestamp(System.currentTimeMillis()));
		// trans.setStatus(Transaction.STATUS_7);
		// trans.setRefundsStatus(new Long(1));
		// trans.setOrderDetails(orderDetails);
		// transactionDAO.updateTransactionStatus(trans);
		// trans.setStatus(Transaction.STATUS_7);

		return trans;
	}

	public List refundMentManage(TransactionListForm transactionListForm)
			throws AppException {
		return transactionDAO.refundMentManage(transactionListForm);
	}

	public List getSellerTransactionRefundList(
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO
				.getSellerTransactionRefundList(transactionListForm);
	}

	public List getBuyerTransactionRefundList(
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO
				.getBuyerTransactionRefundList(transactionListForm);
	}

	public void shippingConfirm(Logistics logistics) throws AppException {
		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(logistics.getOrderId());

		Transaction transaction = transactionDAO.getTransactionById(logistics
				.getTid());
		transaction.setStatus(Transaction.STATUS_6);// 卖家已发货,等待买家确认
		transaction.getOrderDetails().setConsignmentDate(
				new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateTransactionStatus(transaction);

		logistics.setOrderDetails(orderDetails);
		logistics.setConsignmentDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.shippingConfirm(logistics);

		// Agent myAgent =
		// agentDAO.getAgentById(transaction.getFromAgent().getId());
		// Agent toAgent =
		// agentDAO.getAgentById(transaction.getToAgent().getId());
		// sendEmail(transaction,myAgent,toAgent,logistics);
	}

	public Logistics getLogisticsByOrderId(long orderid) throws AppException {
		return transactionDAO.getLogisticsByOrderId(orderid);
	}

	public List getAgentTransactions(TransactionListForm transactionListForm,
			Agent agent) throws AppException {
		transactionDAO.addTransactionBalance(agent);
		return transactionDAO.getTransactionBalanceByAgent(transactionListForm);
	}

	public Transaction agreeRefundMent(Transaction transaction, Agent agent)
			throws AppException {
		Agent myAgent = agentDAO.getAgentById(transaction.getFromAgentId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(myAgent.getId());
		myAgent.setNotallowBalance(ab.getNotAllowBalance().subtract(
				transaction.getAmount())); // 先把钱从冻结余额里面扣回来
		myAgent.setAllowBalance(ab.getAllowBalance().add(
				transaction.getAmount())); // 再把钱放回去可用余额那里

		agentDAO.update(myAgent);

		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderid());
		orderDetails.setStatus(Transaction.STATUS_11); // 退款成功状态
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis())); // 退款申请结束时间
		transactionDAO.updateOrderDetails(orderDetails);

		Transaction trans = transactionDAO.getTransactionById(transaction
				.getTid());
		trans.setStatus(Transaction.STATUS_11);
		trans.setCloseDate(new Timestamp(System.currentTimeMillis()));
		trans.setOrderDetails(orderDetails);
		transactionDAO.updateTransactionStatus(trans);

		// 往Transaction表里面加多一条退款的交易记录,这时卖家变买家,买家变卖家
		Transaction newTransaction = new Transaction();
		OrderDetails newOrderDetails = new OrderDetails();
		newOrderDetails.setShopName(orderDetails.getShopName());
		newOrderDetails.setShopPrice(orderDetails.getShopPrice());
		newOrderDetails.setShopTotal(orderDetails.getShopTotal());
		newOrderDetails.setShopUrl(orderDetails.getShopUrl());
		newOrderDetails.setEmailPrice(orderDetails.getEmailPrice());
		BigDecimal b1 = orderDetails.getShopPrice();
		BigDecimal b2 = new BigDecimal(orderDetails.getShopTotal());
		BigDecimal b3 = orderDetails.getEmailPrice();
		newTransaction.setAmount(b1.multiply(b2).add(b3));

		newTransaction.setToAgent(agentDAO.getAgentById(transaction
				.getFromAgentId()));
		newTransaction.setFromAgent(agentDAO.getAgentById(agent.getId()));

		newTransaction.setStatus(Transaction.STATUS_11); // 退款成功
		newTransaction.setType(Transaction.TYPE_3);

		newOrderDetails.setDetailsContent(orderDetails.getDetailsContent());
		newOrderDetails.setConsigneeAddress(orderDetails.getConsigneeAddress());
		newOrderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(orderDetails.getBuyType());
		// newOrderDetails.setOrderNo(orderDetails.getOrderDetailsNo());
		// newOrderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		newOrderDetails.setOrderNo(noUtil.getOrderDetailsNo());
		newOrderDetails.setOrderDetailsNo(orderDetails.getOrderDetailsNo());
		newOrderDetails.setStatus(Transaction.STATUS_11);
		newOrderDetails.setRefundsNote(orderDetails.getRefundsNote());
		newOrderDetails
				.setFinishDate(new Timestamp(System.currentTimeMillis())); // 退款申请结束时间

		this.addOrderDetails(newOrderDetails);
		newTransaction.setOrderDetails(newOrderDetails);
		newTransaction
				.setAccountDate(new Timestamp(System.currentTimeMillis()));
		newTransaction.setRefundsStatus(Transaction.STATUS_11); // 退款成功
		newTransaction.setRefundsDate(trans.getRefundsDate());
		// newTransaction.setAccountDate(trans.getAccountDate());
		newTransaction.setPayDate(trans.getPayDate());

		// newTransaction.setNo(trans.getNo());
		newTransaction.setNo(noUtil.getTransactionNo());
		newTransaction.setCloseDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.saveTransaction(newTransaction);

		return trans;
	}

	public Transaction offLineCreditRefund(Transaction transaction, Agent agent)
			throws Exception {
		Agent fromAgent = agentDAO.getAgentById(transaction.getFromAgent()
				.getId());
		Agent toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());

		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
		AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
		fromAgent.setCreditBalance(ab.getCreditBalance().add(
				transaction.getAmount()));
		agentDAO.update(fromAgent);

		toAgent.setAllowBalance(ab1.getAllowBalance().subtract(
				transaction.getAmount()));
		agentDAO.update(toAgent);

		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderDetails().getId());
		orderDetails.setStatus(Transaction.STATUS_4); // 退款成功状态
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis())); // 退款申请结束时间
		transactionDAO.updateOrderDetails(orderDetails);

		Transaction trans = transactionDAO.getTransactionById(transaction
				.getId());
		// trans.setStatus(Transaction.STATUS_4);
		trans.setCloseReson("信用线下退款");
		trans.setCloseDate(new Timestamp(System.currentTimeMillis()));
		// trans.setOrderDetails(orderDetails);
		// trans.setRefundsDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateTransactionStatus(trans);

		// 往Transaction表里面加多一条退款的交易记录,这时卖家变买家,买家变卖家
		Transaction newTransaction = new Transaction();
		OrderDetails newOrderDetails = new OrderDetails();
		newOrderDetails.setShopName(orderDetails.getShopName());
		newOrderDetails.setShopPrice(trans.getAmount());
		newOrderDetails.setShopTotal(orderDetails.getShopTotal());
		newOrderDetails.setShopUrl(orderDetails.getShopUrl());
		newOrderDetails.setEmailPrice(orderDetails.getEmailPrice());
		newOrderDetails.setPaymentPrice(trans.getAmount());
		newTransaction.setAmount(trans.getAmount());

		newTransaction.setToAgent(fromAgent);
		newTransaction.setFromAgent(toAgent);

		newTransaction.setStatus(Transaction.STATUS_11); // 退款成功
		newTransaction.setType(Transaction.TYPE_182); // 接口支付，前台手工退款

		newOrderDetails.setDetailsContent(orderDetails.getDetailsContent());
		newOrderDetails.setConsigneeAddress(orderDetails.getConsigneeAddress());
		newOrderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(orderDetails.getBuyType());
		newOrderDetails.setPartner(orderDetails.getPartner());
		newOrderDetails.setAgent(fromAgent);
		newOrderDetails.setOrderNo(orderDetails.getOrderNo());
		newOrderDetails.setOrderDetailsNo(orderDetails.getOrderDetailsNo());
		newOrderDetails.setStatus(Transaction.STATUS_11);
		newOrderDetails.setRefundsNote(orderDetails.getRefundsNote());
		newOrderDetails
				.setFinishDate(new Timestamp(System.currentTimeMillis())); // 退款申请结束时间

		this.addOrderDetails(newOrderDetails);
		newTransaction.setOrderDetails(newOrderDetails);
		newTransaction
				.setAccountDate(new Timestamp(System.currentTimeMillis()));
		newTransaction.setRefundsStatus(Transaction.STATUS_11); // 退款成功
		// newTransaction.setAccountDate(trans.getAccountDate());
		newTransaction.setPayDate(trans.getPayDate());

		newTransaction.setNo(trans.getNo());
		newTransaction.setCloseDate(new Timestamp(System.currentTimeMillis()));
		newTransaction
				.setRefundsDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.saveTransaction(newTransaction);

		return trans;
	}

	public Transaction offLineRefund(Transaction transaction, Agent agent)
			throws Exception {
		Agent fromAgent = agentDAO.getAgentById(transaction.getFromAgent()
				.getId());
		Agent toAgent = agentDAO.getAgentById(transaction.getToAgent().getId());
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab = agentDAO.getAgentBalance(fromAgent.getId());
		AgentBalance ab1 = agentDAO.getAgentBalance(toAgent.getId());
		fromAgent.setAllowBalance(ab.getAllowBalance().add(
				transaction.getAmount()));
		agentDAO.update(fromAgent);

		toAgent.setAllowBalance(ab1.getAllowBalance().subtract(
				transaction.getAmount()));
		agentDAO.update(toAgent);

		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderDetails().getId());
		orderDetails.setStatus(Transaction.STATUS_4); // 退款成功状态
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis())); // 退款申请结束时间
		transactionDAO.updateOrderDetails(orderDetails);

		Transaction trans = transactionDAO.getTransactionById(transaction
				.getId());
		// trans.setStatus(Transaction.STATUS_4);
		trans.setCloseReson("线下退款");
		trans.setCloseDate(new Timestamp(System.currentTimeMillis()));
		// trans.setOrderDetails(orderDetails);
		// trans.setRefundsDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateTransactionStatus(trans);

		// 往Transaction表里面加多一条退款的交易记录,这时卖家变买家,买家变卖家
		Transaction newTransaction = new Transaction();
		OrderDetails newOrderDetails = new OrderDetails();
		newOrderDetails.setShopName(orderDetails.getShopName());
		newOrderDetails.setShopPrice(trans.getAmount());
		newOrderDetails.setShopTotal(orderDetails.getShopTotal());
		newOrderDetails.setShopUrl(orderDetails.getShopUrl());
		newOrderDetails.setEmailPrice(orderDetails.getEmailPrice());
		newOrderDetails.setPaymentPrice(trans.getAmount());
		// BigDecimal b1 = orderDetails.getShopPrice();
		// BigDecimal b2 = new BigDecimal(orderDetails.getShopTotal());
		// BigDecimal b3 = orderDetails.getEmailPrice();
		newTransaction.setAmount(trans.getAmount());

		newTransaction.setToAgent(fromAgent);
		newTransaction.setFromAgent(toAgent);

		newTransaction.setStatus(Transaction.STATUS_11); // 退款成功
		newTransaction.setType(Transaction.TYPE_82); // 接口支付，前台手工退款

		newOrderDetails.setDetailsContent(orderDetails.getDetailsContent());
		newOrderDetails.setConsigneeAddress(orderDetails.getConsigneeAddress());
		newOrderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
		orderDetails.setBuyType(orderDetails.getBuyType());
		newOrderDetails.setPartner(orderDetails.getPartner());
		newOrderDetails.setAgent(fromAgent);
		newOrderDetails.setOrderNo(orderDetails.getOrderNo());
		newOrderDetails.setOrderDetailsNo(orderDetails.getOrderDetailsNo());
		newOrderDetails.setStatus(Transaction.STATUS_11);
		newOrderDetails.setRefundsNote(orderDetails.getRefundsNote());
		newOrderDetails
				.setFinishDate(new Timestamp(System.currentTimeMillis())); // 退款申请结束时间

		this.addOrderDetails(newOrderDetails);
		newTransaction.setOrderDetails(newOrderDetails);
		newTransaction
				.setAccountDate(new Timestamp(System.currentTimeMillis()));
		newTransaction.setRefundsStatus(Transaction.STATUS_11); // 退款成功
		// newTransaction.setAccountDate(trans.getAccountDate());
		newTransaction.setPayDate(trans.getPayDate());

		newTransaction.setNo(trans.getNo());
		newTransaction.setCloseDate(new Timestamp(System.currentTimeMillis()));
		newTransaction
				.setRefundsDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.saveTransaction(newTransaction);

		return trans;
	}

	public Transaction closeTransactionConfirm(Transaction transaction)
			throws AppException {
		OrderDetails orderDetails = transactionDAO
				.getOrderDetailsById(transaction.getOrderid());
		orderDetails.setStatus(Transaction.STATUS_4);// 关闭交易
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateOrderDetails(orderDetails);

		Transaction trans = transactionDAO.getTransactionById(transaction
				.getTid());
		if (transaction.getOtherMsg() != null
				&& !transaction.getOtherMsg().equals(""))
			trans.setCloseReson(transaction.getOtherMsg());
		else
			trans.setCloseReson(transaction.getCloseReson());
		trans.setCloseDate(new Timestamp(System.currentTimeMillis()));
		trans.setStatus(Transaction.STATUS_4);// 关闭交易
		trans.setOrderDetails(orderDetails);
		transactionDAO.updateTransactionStatus(trans);
		return trans;
	}

	public Transaction getTransactionByNo(String no) throws AppException {
		return transactionDAO.getTransactionByNo(no);
	}

	public BigDecimal getAmountSum(Agent agent) throws AppException {
		return transactionDAO.getAmountSum(agent);
	}

	public Transaction getTransactionById(long transactionId)
			throws AppException {
		return transactionDAO.getTransactionById(transactionId);
	}

	public void executeClose() throws AppException {
		System.out.println("==================进入自动关闭过期的交易=====================");
		System.out.println("==================executeClose=====================");
		List list = transactionDAO.getNeedCloseTransactions(1);
		List refundList = transactionDAO.getNeedCloseTransactions(2); // 查询退款的记录
		List shippingList = transactionDAO.getNeedCloseTransactions(3); // 查询发货的记录
		List redPacketList = transactionDAO.getNeedCloseTransactions(7);
		Agent fromAgent = null;
		Agent toAgent = null;
		Calendar cl = Calendar.getInstance();
		long firstDate = cl.getTime().getTime();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Transaction trans = (Transaction) list.get(i);
				boolean flag = false;
				if (trans.getStatus() == 1) {
					if ((firstDate - trans.getAccountDate().getTime())
							/ (24 * 60 * 60 * 1000) > 7) {
						flag = true;
					}
				} else if (trans.getStatus() == 2) {
					if ((firstDate - trans.getPayDate().getTime())
							/ (24 * 60 * 60 * 1000) > 7) {
						flag = true;
					}
				}

				if (flag) {
					if (trans.getStatus() == 2) { // 如果买家已经付款了,要把钱还给买家
						fromAgent = agentDAO.getAgentById(trans.getFromAgent()
								.getId());
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab = agentDAO.getAgentBalance(fromAgent
								.getId());

						fromAgent.setNotallowBalance(ab.getNotAllowBalance()
								.subtract(trans.getAmount())); // 先把钱从冻结余额里面扣回来
						fromAgent.setAllowBalance(ab.getAllowBalance().add(
								trans.getAmount())); // 再把钱放回去可用余额那里
						agentDAO.update(fromAgent);
					}
					trans.getOrderDetails().setStatus(Transaction.STATUS_4);
					trans.getOrderDetails().setFinishDate(
							new Timestamp(System.currentTimeMillis()));

					trans.setCloseReson("超过交易时限,系统自动关闭");

					trans
							.setCloseDate(new Timestamp(System
									.currentTimeMillis()));
					trans.setStatus(Transaction.STATUS_4);// 关闭交易

					transactionDAO.executeClose(trans);
				}

			}
		}

		if (refundList != null && refundList.size() > 0) {
			for (int i = 0; i < refundList.size(); i++) {
				Transaction trans = (Transaction) refundList.get(i);
				if (trans.getStatus() == 10) { // 如果买家申请了退款,要把钱还给买家
					fromAgent = agentDAO.getAgentById(trans.getFromAgent()
							.getId());
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab = agentDAO.getAgentBalance(fromAgent
							.getId());
					fromAgent.setNotallowBalance(ab.getNotAllowBalance()
							.subtract(trans.getAmount())); // 先把钱从冻结余额里面扣回来
					fromAgent.setAllowBalance(ab.getAllowBalance().add(
							trans.getAmount())); // 再把钱放回去可用余额那里
					agentDAO.update(fromAgent);
				}

				trans.getOrderDetails().setStatus(Transaction.STATUS_4);
				trans.getOrderDetails().setFinishDate(
						new Timestamp(System.currentTimeMillis()));

				trans.setCloseReson("超过交易时限,系统自动关闭");

				trans.setCloseDate(new Timestamp(System.currentTimeMillis()));
				trans.setStatus(Transaction.STATUS_4);// 关闭交易

				transactionDAO.executeClose(trans);
			}
		}

		if (shippingList != null && shippingList.size() > 0) {
			for (int i = 0; i < shippingList.size(); i++) {
				Transaction trans = (Transaction) shippingList.get(i);
				Logistics logistics = transactionDAO
						.getLogisticsByOrderId(trans.getOrderDetails().getId());
				boolean flag = false;
				if (logistics != null) {
					if (logistics.getType() == 0) { // 快递 10天
						if ((firstDate - logistics.getConsignmentDate()
								.getTime())
								/ (24 * 60 * 60 * 1000) > 10) {
							flag = true;
						}
					} else if (logistics.getType() == 1) { // 平邮 30天
						if ((firstDate - logistics.getConsignmentDate()
								.getTime())
								/ (24 * 60 * 60 * 1000) > 30) {
							flag = true;
						}
					} else if (logistics.getType() == 2) { // 不需要运输 3天
						if ((firstDate - logistics.getConsignmentDate()
								.getTime())
								/ (24 * 60 * 60 * 1000) > 3) {
							flag = true;
						}
					}
					if (flag) {
						fromAgent = agentDAO.getAgentById(trans.getFromAgent()
								.getId());
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab = agentDAO.getAgentBalance(fromAgent
								.getId());
						fromAgent.setNotallowBalance(ab.getNotAllowBalance()
								.subtract(trans.getAmount())); // 先把钱从冻结余额里面扣回来
						// fromAgent.setBalance(fromAgent.getBalance().subtract(trans.getAmount()));
						// // 再把钱从总额里面扣除掉
						agentDAO.update(fromAgent);

						toAgent = agentDAO.getAgentById(trans.getToAgent()
								.getId());
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab1 = agentDAO.getAgentBalance(toAgent
								.getId());
						toAgent.setAllowBalance(ab1.getAllowBalance().add(
								trans.getAmount())); // 把钱放到余额
						// toAgent.setBalance(toAgent.getBalance().add(trans.getAmount()));
						// //再把钱加到总额
						agentDAO.update(toAgent);

						trans.getOrderDetails().setStatus(Transaction.STATUS_4);
						trans.getOrderDetails().setFinishDate(
								new Timestamp(System.currentTimeMillis()));

						trans.setCloseReson("超过交易时限,系统自动关闭,钱自动转到卖家帐户上");

						trans.setCloseDate(new Timestamp(System
								.currentTimeMillis()));
						trans.setStatus(Transaction.STATUS_4);// 关闭交易

						transactionDAO.executeClose(trans);
					}
				}
			}
		}
		
		if (redPacketList != null && redPacketList.size() > 0) {
			System.out.println("================== 需要关闭的红包交易个数:"+redPacketList.size()+"================");
			// 过期关闭交易
			for (int i = 0; i < redPacketList.size(); i++) {
				Transaction transaction = (Transaction) redPacketList.get(i);

				String mark = "交易时间已过,系统自动,退还红包,解冻资金";
				AgentBalance agentBalance = agentDAO
						.getAgentBalance(transaction.getFromAgent().getId());
				if (agentBalance.getNotAllowBalance().compareTo(
						transaction.getAmount()) < 0) {
					System.out.println("买家"
							+ transaction.getFromAgent().getEmail()
							+ "不可以余额不足,无法退还红包");
					break;
				}
				moveNotallowBalanceToAllowBalance(transaction.getFromAgent(),
						transaction.getAmount(), mark, transaction
								.getOrderDetails().getDetailsContent());
				transaction.setStatus(Transaction.STATUS_4);
				transaction.setAccountDate(new Timestamp(System
						.currentTimeMillis()));
				System.out.println("========自动关闭交易:"+transaction.getNo()+"===========");
				transactionDAO.updateTransactionStatus(transaction);
			}

		}
	}

	public ArrayList<ArrayList<Object>> getTransactionReportData(
			TransactionListForm tlf) throws AppException {

		List data = transactionDAO.getAllTransactionBalanceByAgent(tlf);
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		if (tlf.getBalanceType() == 0) {
			list_title.add("#钱门商户账户明细查询");
		} else if (tlf.getBalanceType() == 1) {
			list_title.add("#钱门商户账户可用余额查询");
		} else if (tlf.getBalanceType() == 2) {
			list_title.add("#钱门商户账户冻结余额查询");
		} else if (tlf.getBalanceType() == 3) {
			list_title.add("#钱门商户账户信用余额查询");
		}
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#下载时间：");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(DateUtil.getDateString("yyyy年MM月dd日HH:mm:ss"));
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("商户名   （账号）");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(tlf.getAgent().getName() + "("
				+ tlf.getAgent().getLoginName() + ")");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("商户订单号");
		list_title.add("业务流水号");
		list_title.add("交易时间");
		list_title.add("钱门交易号");
		list_title.add("交易对方");
		list_title.add("收入(元)");
		list_title.add("支出(元)");
		if (tlf.getBalanceType() == 0 || tlf.getBalanceType() == 1) {
			list_title.add("账户可用余额(元)");
		}
		if (tlf.getBalanceType() == 0 || tlf.getBalanceType() == 2) {
			list_title.add("账户冻结余额(元)");
		}
		if (tlf.getBalanceType() == 0 || tlf.getBalanceType() == 3) {
			list_title.add("账户信用余额(元)");
		}
		if (tlf.getBalanceType() == 0) {
			list_title.add("账户总余额(元)");
		}
		list_title.add("交易状态");
		list_title.add("商品名称");
		list_title.add("交易类型");
		list_context.add(list_title);
		for (int i = 0; i < data.size(); i++) {
			TempTransactionBalance tb = (TempTransactionBalance) data.get(i);

			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(tb.getOrderDetailsNo());// 商户订单号
			list_context_item.add(tb.getOrderNoSplit());// 业务流水号
			list_context_item.add(DateUtil.getDateString(tb
					.getTransactionDate(), "yyyy-MM-dd   HH:mm:ss"));// 交易时间
			list_context_item.add(tb.getOrderDetailsNo());// 钱门交易号
			if (tb.getFromAgentId() == tlf.getAgent().getId()) {
				list_context_item.add(tb.getToAgentEmail() + "    "
						+ tb.getToAgentName());// 交易对方
			} else {
				list_context_item.add(tb.getFromAgentEmail() + "    "
						+ tb.getFromAgentName());// 交易对方
			}

			if (tb.getToAgentId() == tb.getFromAgentId()) {
				list_context_item.add(tb.getAmount());
				list_context_item.add("-" + tb.getAmount());
			} else if (tb.getToAgentId() == tlf.getAgent().getId()) {
				list_context_item.add(tb.getAmount());
				list_context_item.add("");
			} else if (tb.getFromAgentId() == tlf.getAgent().getId()) {
				list_context_item.add("");
				if (tb.getAmount().compareTo(new BigDecimal("0")) > 0)
					list_context_item.add("-" + tb.getAmount());
				else
					list_context_item.add("");
			}

			BigDecimal balance = new BigDecimal(0);
			BigDecimal notallowBalance = new BigDecimal(0);
			BigDecimal creditBalance = new BigDecimal(0);
			if (tb.getBalance() != null) {
				balance = tb.getBalance();
			}
			if (tb.getNotallowBalance() != null) {
				notallowBalance = tb.getNotallowBalance();
			}
			if (tb.getCreditBalance() != null) {
				creditBalance = tb.getCreditBalance();
			}
			if (tlf.getBalanceType() == 0 || tlf.getBalanceType() == 1) {
				list_context_item.add(balance);// 账户可用余额
			}
			if (tlf.getBalanceType() == 0 || tlf.getBalanceType() == 2) {
				list_context_item.add(notallowBalance);// 账户冻结余额
			}
			if (tlf.getBalanceType() == 0 || tlf.getBalanceType() == 3) {
				list_context_item.add(creditBalance);// 账户信用余额
			}
			if (tlf.getBalanceType() == 0) {
				list_context_item.add(tb.getTotalBalance());// 账户总余额
			}
			list_context_item.add(tb.getTypeCaption());// 交易状态
			list_context_item.add(tb.getShopName());// 商品名称
			if (tb.getBuyType() != null) {
				list_context_item.add(tb.getBuyType());// 交易类型
			} else {
				list_context_item.add("");
			}
			list_context.add(list_context_item);
		}

		Object[] fromAgent = transactionDAO.statToAgentTransaction(tlf, 0);
		Object[] toAgent = transactionDAO.statToAgentTransaction(tlf, 1);
		if (fromAgent[0] != null)
			fromAgent[0] = fromAgent[0];
		else
			fromAgent[0] = 0;
		if (fromAgent[1] != null)
			fromAgent[1] = fromAgent[1];
		else
			fromAgent[1] = 0;
		if (toAgent[0] != null)
			toAgent[0] = toAgent[0];
		else
			toAgent[0] = 0;
		if (toAgent[1] != null)
			toAgent[1] = toAgent[1];
		else
			toAgent[1] = 0;
		list_title = new ArrayList<Object>();
		list_title.add("#支出合计:共支出" + fromAgent[1] + "笔  支出总额:" + fromAgent[0]);
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#收入合计:共收入" + toAgent[1] + "笔  收入总额:" + toAgent[0]);
		list_context.add(list_title);
		return list_context;

	}

	public List getTransactionByOrderNo(String orderNo) throws AppException {

		return transactionDAO.getTransactionByOrderNo(orderNo);
	}

	public List getBuyerTransactionList(
			TransactionListForm transactionListForm, boolean isPass)
			throws AppException {
		return transactionDAO.getBuyerTransactionList(transactionListForm,
				isPass);
	}

	public long addDebit(Transaction transaction, Agent agent)
			throws AppException {
		long lo = 0;
		Agent fromAgent = agentDAO.getAgentByLoginName(transaction
				.getBuyerAccount());
		Agent toAgent = agentDAO.getAgentById(agent.getId());
		Debit debit = new Debit();
		debit.setFromAgent(fromAgent);
		debit.setAgent(toAgent);
		debit.setAmount(transaction.getAmount());
		debit.setRemark(transaction.getRemark());
		debit.setNote(transaction.getNote());
		debit.setApplyDate(new Timestamp(System.currentTimeMillis()));
		debit.setStatus(Debit.STATUS_0);
		String debitNo = noUtil.getDebitNo();
		debit.setNo(debitNo);

		// 插入日志
		DebitLog debitLog = new DebitLog();
		debitLog.setAgent(toAgent);
		debitLog.setDebitNo(debitNo);
		debitLog.setAmount(transaction.getAmount());
		debitLog.setContent(transaction.getNote());
		debitLog.setChargeDate(new Timestamp(System.currentTimeMillis()));
		debitLog.setOperater(toAgent.getName());
		debitLog.setStatus(Debit.STATUS_0);
		transactionDAO.addDebitLog(debitLog);

		lo = transactionDAO.addDebit(debit);
		return lo;
	}

	public List getDebitList(Agent agent,
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO.getDebitList(agent, transactionListForm);
	}

	/**
	 * 预支/报销方法
	 */
	public List getDebitAndExpenseList(TransactionListForm transactionListForm)
			throws AppException {
		return transactionDAO.getDebitAndExpenseList(transactionListForm);
	}

	public Debit getDebitByDebitNo(String debitNo) throws AppException {
		return transactionDAO.getDebitByDebitNo(debitNo);
	}

	public long addExpense(Transaction transaction, Agent agent)
			throws AppException {
		long lo = 0;
		Transaction tran = transactionDAO.getTransactionById(transaction
				.getTid());
		Agent fromAgent = agent;
		Agent toAgent = agentDAO.getAgentById(transaction.getToAgentId());
		Debit debit = transactionDAO
				.getDebitByDebitNo(transaction.getDebitNo());
		Expense expense = new Expense();
		expense.setFromAgent(fromAgent);
		expense.setAgent(toAgent);
		expense.setAmount(transaction.getAmount());
		expense.setRemark(transaction.getRemark());
		expense.setNote(transaction.getNote());
		expense.setApplyDate(new Timestamp(System.currentTimeMillis()));
		expense.setStatus(Expense.STATUS_0);
		expense.setDebit(debit);
		String expenseNo = noUtil.getExpenseNo();
		expense.setNo(expenseNo);

		// 插入日志
		ExpenseLog expenseLog = new ExpenseLog();
		expenseLog.setAgent(fromAgent);
		expenseLog.setExpenseNo(expenseNo);
		expenseLog.setAmount(transaction.getAmount());
		expenseLog.setContent(transaction.getNote());
		expenseLog.setChargeDate(new Timestamp(System.currentTimeMillis()));
		expenseLog.setOperater(fromAgent.getName());
		expenseLog.setStatus(Debit.STATUS_0);
		transactionDAO.addExpenseLog(expenseLog);

		// 更改一下ORDER_DETIALS的STATUS状态,表示已经申请报销了
		tran.getOrderDetails().setStatus(OrderDetails.STATUS_1);
		transactionDAO.updateTransactionStatus(tran);
		lo = transactionDAO.addExpense(expense);
		return lo;
	}

	public List getExpenseList(Agent agent,
			TransactionListForm transactionListForm) throws AppException {
		return transactionDAO.getExpenseList(agent, transactionListForm);
	}

	public int getTransactionBalanceRow(TransactionListForm tlf)
			throws AppException {
		return transactionDAO.getTransactionBalanceRow(tlf);
	}

	public BigDecimal getCreditPaymentArrearage(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return transactionDAO.getCreditPaymentArrearage(agent);
	}

	public BigDecimal getCreditRePaymentAmount(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return transactionDAO.getCreditRePaymentAmount(agent);
	}

	public void update_credit_payment_status(Agent agent,
			BigDecimal repayment_amount) throws AppException {
		// 查询所有信用支付记录
		System.out.println("---update_credit_payment_status---"
				+ agent.getLoginName());
		List paymentList = transactionDAO.getCreditPayListByAgent(agent);
		if (paymentList != null) {
			Transaction temp = (Transaction) paymentList.get(0);
			if (temp.getOrderDetails().getRepayAmount() != null)
				repayment_amount = repayment_amount.add(temp.getOrderDetails()
						.getRepayAmount());
			Transaction tran_payment = null;
			for (int i = 0; i < paymentList.size(); i++) {
				tran_payment = (Transaction) paymentList.get(i);

				if (repayment_amount.compareTo(tran_payment.getOrderDetails()
						.getPaymentPrice()) == -1) {
					tran_payment.getOrderDetails().setRepayAmount(
							repayment_amount);
					break;
				} else if (repayment_amount.compareTo(tran_payment
						.getOrderDetails().getPaymentPrice()) == 0) {
					tran_payment.getOrderDetails().setRepayAmount(
							repayment_amount);
					tran_payment.getOrderDetails().setStatus(
							OrderDetails.STATUS_12);
					break;
				} else if (repayment_amount.compareTo(tran_payment
						.getOrderDetails().getPaymentPrice()) == 1) {

					if (i + 2 <= paymentList.size()) {
						repayment_amount = repayment_amount
								.subtract(tran_payment.getOrderDetails()
										.getPaymentPrice());
					} else {
						System.out.println("还钱总额:" + repayment_amount);
						System.out.println("单比信用支付欠款:"
								+ tran_payment.getOrderDetails()
										.getPaymentPrice());
						tran_payment.getOrderDetails().setRepayAmount(
								tran_payment.getOrderDetails()
										.getPaymentPrice());
						System.out.println("------没有下一条,信用支付欠款全数还完-----");
					}
					tran_payment.getOrderDetails().setStatus(
							OrderDetails.STATUS_12);
				}
			}
			if (tran_payment != null)
				transactionDAO.saveTransaction(tran_payment);
		}

	}

	public void update_credit_give_status(Agent agent,
			BigDecimal repayment_amount) throws AppException {
		// 查询所有授信记录
		System.out.println("---update_credit_give_status---"
				+ agent.getLoginName());
		List creditgiveList = transactionDAO.getCreditGiverListByAgent(agent);
		if (creditgiveList != null) {
			Transaction temp = (Transaction) creditgiveList.get(0);
			if (temp.getOrderDetails().getRepayAmount() != null)
				repayment_amount = repayment_amount.add(temp.getOrderDetails()
						.getRepayAmount());
			Transaction tran_credit_give = null;
			for (int i = 0; i < creditgiveList.size(); i++) {
				tran_credit_give = (Transaction) creditgiveList.get(i);

				if (repayment_amount.compareTo(tran_credit_give
						.getOrderDetails().getPaymentPrice()) == -1) {
					tran_credit_give.getOrderDetails().setRepayAmount(
							repayment_amount);
					break;
				} else if (repayment_amount.compareTo(tran_credit_give
						.getOrderDetails().getPaymentPrice()) == 0) {
					tran_credit_give.getOrderDetails().setRepayAmount(
							repayment_amount);
					tran_credit_give.getOrderDetails().setStatus(
							OrderDetails.STATUS_12);
					break;
				} else if (repayment_amount.compareTo(tran_credit_give
						.getOrderDetails().getPaymentPrice()) == 1) {
					if (i + 2 <= creditgiveList.size())
						tran_credit_give.getOrderDetails().setRepayAmount(
								tran_credit_give.getOrderDetails()
										.getPaymentPrice());
					repayment_amount = repayment_amount
							.subtract(tran_credit_give.getOrderDetails()
									.getPaymentPrice());
					tran_credit_give.getOrderDetails().setStatus(
							OrderDetails.STATUS_12);
				}
			}
			if (tran_credit_give != null)
				transactionDAO.saveTransaction(tran_credit_give);
		}
	}

	public Transaction getCreditGiverTransactionByAgent(Agent agent)
			throws AppException {
		return transactionDAO.getCreditGiverTransactionByAgent(agent);
	}

	// 移动商户从冻结余额到可用余额
	public void moveNotallowBalanceToAllowBalance(Agent agent,
			BigDecimal amount, String mark, String shopName)
			throws AppException {
		agentDAO.moveNotallowBalanceToAllowBalance(agent, amount);
		Transaction transaction = new Transaction();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setPaymentPrice(amount);
		transaction.setType(Transaction.TYPE_97);
		orderDetails.setShopName(shopName);
		transaction.setMark(mark);
		transaction.setAmount(amount);
		transaction.setFromAgent(agent);
		transaction.setToAgent(agent);
		transaction.setStatus(Transaction.STATUS_3);
		transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setDetailsContent(mark);
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setShopPrice(amount);
		orderDetails.setShopTotal(new Long(1));
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_6);
		String no = noUtil.getTransactionNo();
		transaction.setNo(no);
		transactionDAO.addOrderDetails(orderDetails);
		transaction.setOrderDetails(orderDetails);
		transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.save(transaction);
	}

	// 移动商户从可用余额到冻结余额
	public void moveAllowBalanceToNotallowBalance(Agent agent,
			BigDecimal amount, String mark, String shopName)
			throws AppException {
		agentDAO.moveAllowBalanceToNotallowBalance(agent, amount);
		Transaction transaction = new Transaction();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setPaymentPrice(amount);
		transaction.setType(Transaction.TYPE_98);
		orderDetails.setShopName(shopName);
		transaction.setMark(mark);
		transaction.setAmount(amount);
		transaction.setFromAgent(agent);
		transaction.setToAgent(agent);
		transaction.setStatus(Transaction.STATUS_3);
		transaction.setPayDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setFinishDate(new Timestamp(System.currentTimeMillis()));
		orderDetails.setDetailsContent(mark);
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setShopPrice(amount);
		orderDetails.setShopTotal(new Long(1));
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_5);
		String no = noUtil.getTransactionNo();
		transaction.setNo(no);
		transactionDAO.addOrderDetails(orderDetails);
		transaction.setOrderDetails(orderDetails);
		transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.save(transaction);
	}

	// 收取红包
	public boolean GatherRedPacket(long tid) throws AppException {
		// TODO Auto-generated method stub
		Transaction transaction = transactionDAO.getTransactionById(tid);
		String mark = "红包资金解冻";
		AgentBalance agentBalance = agentDAO.getAgentBalance(transaction
				.getFromAgent().getId());
		if (agentBalance.getNotAllowBalance()
				.compareTo(transaction.getAmount()) < 0) {
			return false;
		}
		moveNotallowBalanceToAllowBalance(transaction.getFromAgent(),
				transaction.getAmount(), mark, transaction.getOrderDetails()
						.getDetailsContent());
		transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
		transaction.setStatus(Transaction.STATUS_3);
		transactionDAO.updateTransactionStatus(transaction);
		return true;
	}

	// 退还红包
	public boolean RetreatRedPacket(long tid) throws AppException {
		// TODO Auto-generated method stub
		Transaction transaction = transactionDAO.getTransactionById(tid);
		String mark = "退还红包,解冻资金";
		AgentBalance agentBalance = agentDAO.getAgentBalance(transaction
				.getFromAgent().getId());
		if (agentBalance.getNotAllowBalance()
				.compareTo(transaction.getAmount()) < 0) {
			return false;
		}
		moveNotallowBalanceToAllowBalance(transaction.getFromAgent(),
				transaction.getAmount(), mark, transaction.getOrderDetails()
						.getDetailsContent());
		transaction.setStatus(Transaction.STATUS_4);
		transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
		transactionDAO.updateTransactionStatus(transaction);
		return true;
	}

	public Transaction getRedPacketListByTid(long tid) throws AppException {
		// TODO Auto-generated method stub
		Transaction transaction = transactionDAO.getTransactionById(tid);
		return transaction;

	}

}
