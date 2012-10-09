package com.fordays.fdpay.transaction.dao;

import java.math.BigDecimal;
import java.util.List;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.cooperate.QueryOrderResult;
import com.fordays.fdpay.transaction.Debit;
import com.fordays.fdpay.transaction.DebitLog;
import com.fordays.fdpay.transaction.Expense;
import com.fordays.fdpay.transaction.ExpenseLog;
import com.fordays.fdpay.transaction.Logistics;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.TempTransaction;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.TransactionListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface TransactionDAO extends BaseDAO {
	public QueryOrderResult getQueryOrderResult(String out_trade_no,
			String partnerId) throws AppException;

	public List getTransactions(TransactionListForm transactionListForm,
			boolean isPass) throws AppException;

	public List getSellerTransactionList(
			TransactionListForm transactionListForm, boolean isPass)
			throws AppException;

	public List getTransactionByToAgentId(
			TransactionListForm transactionListForm) throws AppException;

	/**
	 * 借款/还款方法
	 */
	public List getBorrowAndRepaymentList(
			TransactionListForm transactionListForm) throws AppException;

	/**
	 * 
	 * 授信/还款方法
	 */
	public List getLetterAndRepaymentList(
			TransactionListForm transactionListForm) throws AppException;

	public OrderDetails queryOrderDetailByorderDetailsNoandType(
			String orderDetailsNo, String partnerId) throws AppException;

	public Transaction queryTransactionByOrderAndFromAgentAndType(long orderId,
			long fromAgentId) throws AppException;

	/*
	 * 信用支付退款调用
	 */
	public BigDecimal getDirectRefundTotalAmount(String orderDetailsNo,
			long fromAgentId, long toAgentId, long status) throws AppException;

	/*
	 * 信用支付退款调用
	 */
	public double getDirectRefundAmount(String orderDetailsNo,
			long fromAgentId, long toAgentId) throws AppException;

	/*
	 * 信用支付（外买）退款调用
	 */
	public double getDirectTransactionAmountOut(String orderDetailsNo,
			long fromAgentId, long toAgentId, long orderStatus, long tranType)
			throws AppException;

	public List getDebitAndExpenseList(TransactionListForm transactionListForm)
			throws AppException;

	public long saveTransaction(Transaction transaction) throws AppException;

	public long addOrderDetails(OrderDetails orderDetails) throws AppException;

	public long addDebitLog(DebitLog debitLog) throws AppException;

	public long addExpenseLog(ExpenseLog expenseLog) throws AppException;

	public long addDebit(Debit debit) throws AppException;

	public long addExpense(Expense expense) throws AppException;

	public List getAgentAddressList(Agent agent) throws AppException;

	public List getTransactionByOrderNo(String orderNo) throws AppException;

	public List getContactList(Agent agent) throws AppException;

	public List getDebitList(Agent agent,
			TransactionListForm transactionListForm) throws AppException;

	public List getExpenseList(Agent agent,
			TransactionListForm transactionListForm) throws AppException;

	public List getListContact(Agent agent, String searchContactInput)
			throws AppException;

	public Transaction getTransactionById(long tid) throws AppException;

	public long updateTransactionStatus(Transaction transaction)
			throws AppException;

	public OrderDetails getOrderDetailsById(long orderid) throws AppException;

	public Debit getDebitByDebitNo(String debitNo) throws AppException;

	public void updateTransactionPrice(Transaction transaction)
			throws AppException;

	public void updateOrderDetails(OrderDetails orderDetails)
			throws AppException;

	public List getBatchCollectDetail(TransactionListForm transactionListForm,
			Agent agent) throws AppException;

	public List getBatchCollectDetailById(
			TransactionListForm transactionListForm) throws AppException;

	public List getBatchPaymentDetail(TransactionListForm transactionListForm,
			Agent agent) throws AppException;

	public List getBatchPaymentDetailById(
			TransactionListForm transactionListForm) throws AppException;

	public void addMark(Transaction transaction) throws AppException;

	public AgentAddress getAgentAddressById(Agent agent) throws AppException;

	public List refundMentManage(TransactionListForm transactionListForm)
			throws AppException;

	public List getSellerTransactionRefundList(
			TransactionListForm transactionListForm) throws AppException;

	public List getBuyerTransactionRefundList(
			TransactionListForm transactionListForm) throws AppException;

	public List getBuyerTransactionList(
			TransactionListForm transactionListForm, boolean isPass)
			throws AppException;

	public void shippingConfirm(Logistics logistics) throws AppException;

	public Logistics getLogisticsByOrderId(long orderid) throws AppException;

	public List getAgentTransactions(TransactionListForm transactionListForm,
			boolean isPass) throws AppException;

	public Transaction getTransactionByNo(String no) throws AppException;

	public Transaction getTransByOrderDetailsNo(String orderDetailsNo)
			throws AppException;

	public Long getTransactionTypeByNo(String no) throws AppException;


	// 20090507-ref001-jason-s for cooperate
	// double getTotalRefundByOrderDetailsNo(String orderDetailsNo,long
	// avouchAccount) throws AppException;

	// boolean checkRefundAmount(String orderDetailsNo,long fromAgentId,long
	// toAgentId,double refundAmount) throws AppException;

	double getRefundAmount(String orderDetailsNo, long fromAgentId,
			long toAgentId) throws AppException;

	public BigDecimal getRefundTotalAmount(String orderDetailsNo,
			long fromAgentId, long toAgentId, long status) throws AppException;

	public BigDecimal getHasRepaymentTotalAmount(String orderNo)
			throws AppException;

	double getTransactionAmount(String orderDetailsNo, long fromAgentId,
			long toAgentId, long orderStatus) throws AppException;


	Transaction getTransByOrderNo(String orderNo) throws AppException;


	OrderDetails getOrderDetailById(long id) throws AppException;

	OrderDetails queryOrderDetailByTransNo(String orderDetailsNo,
			String partnerId, Long status) throws AppException;

	/**
	 *  根据参数查询是否存在这条交易
	 */
	public List<OrderDetails> queryOrderDetailsByorderDetailsNo(
			String orderDetailsNo, String partnerId) throws AppException;

	/**
	 * 根据参数查询是否存在这条交易
	 * 
	 */
	public OrderDetails queryOrderDetailByorderDetailsNo(String orderDetailsNo,
			String partnerId) throws AppException;

	/**
	 *  根据订单编号查询是交易流水账
	 * 
	 */
	public Transaction queryTransactionByOrderid(long orderId)
			throws AppException;


	Transaction queryTransactionByOrderAndFromAgent(long orderId,
			long fromAgentId) throws AppException;

	OrderDetails queryOrderDetailByIdAndPartner(long id, String partnerId)
			throws AppException;

	void closeTransaction(String orderDetailsNo, String partner)
			throws AppException;

	// 20090507-ref001-jason-e for cooperate

	public BigDecimal getAmountSum(Agent agent) throws AppException;

	public BigDecimal getCreditAmount(Agent agent) throws AppException;

	public void executeClose(Transaction transaction) throws AppException;

	public List getNeedCloseTransactions(int type) throws AppException;

	public List list(TransactionListForm transactionListForm, boolean isPass)
			throws AppException;

	public TempTransaction statToAgentTransaction(
			TransactionListForm transactionListForm, boolean isPass)
			throws AppException;

	public TempTransaction statFromAgentTransaction(
			TransactionListForm transactionListForm, boolean isPass)
			throws AppException;

	public OrderDetails getOrderDetailByOrderNo(String orderNo)
			throws AppException;

	public List getTransactionByOrderDetailsNo(String orderDetailsNo)
			throws AppException;

	public Transaction getTransactionByAgent(Agent agetn) throws AppException;

	public long addTransactionBalance(Agent agent) throws AppException;

	public List getTransactionBalanceByAgent(TransactionListForm tlf)
			throws AppException;

	public List getAllTransactionBalanceByAgent(TransactionListForm tlf)
			throws AppException;

	public int getTransactionBalanceRow(TransactionListForm tlf)
			throws AppException;

	public Object[] statToAgentTransaction(TransactionListForm tlf, int type)
			throws AppException;

	public long getNotCompletedTransactionArticle(Agent agent)
			throws AppException;

	public List getCreditGiverListByAgent(Agent agent) throws AppException;

	public List getCreditPayListByAgent(Agent agent) throws AppException;

	public List getCreditRepaymentList(Agent formAgent) throws AppException;

	public BigDecimal getCreditArrearage(Agent agent) throws AppException;

	public BigDecimal getCreditPaymentArrearage(Agent agent)
			throws AppException;

	public BigDecimal getCreditRePaymentAmount(Agent agent) throws AppException;

	public Transaction getCreditGiverTransactionByAgent(Agent agent)
			throws AppException;

	/**
	 * 根据订单Id 查出交易信息的转出人ID
	 * 
	 * @param orderId
	 * @return
	 */
	public List queryTransactionByOrderAndFromAgentId(long orderId)
			throws AppException;

	/**
	 * 根据订单Id 查出交易信息
	 * 
	 * @param orderId
	 * @return
	 * @throws AppException
	 */
	public Transaction queryTransactionByOrderAndFromAgent(long orderId)
			throws AppException;

	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo,
			String partnerId) throws AppException;

	public List queryTransactionByOrder(long orderId) throws AppException;


	public Transaction queryTransactionByOrderAndPayFee(long orderId)
			throws AppException;

	// 查询退款信息
	public BigDecimal getCreditRePayListByAgent(Agent agent, String orderDeNo)
			throws AppException;

	public long save(Transaction transaction) throws AppException;
	public List getRedPacketList(TransactionListForm transactionListForm)throws AppException;
	public long getNotCompletedTransactionRedPacketArticle(Agent agent)throws AppException; 
	public Transaction getNotCompletedTransactionRedPacket(Agent agent)throws AppException;
	
	
	public BigDecimal getAlreadyAmount(List freezeOrders,long fromAgentId,long toAgentId,long status, long type) throws AppException;
	
	public BigDecimal getAlreadyAmount(List freezeOrders,long toAgentId,long status, long type) throws AppException;
	
	public OrderDetails queryOrderLikeOrderNo(String OrderNo)throws AppException;
	
	public List queryOrderLikeOrderDetailsNo(String OrderDetailsNo)throws AppException;
}
