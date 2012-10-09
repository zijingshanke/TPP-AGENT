package com.fordays.fdpay.transaction.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.transaction.Debit;
import com.fordays.fdpay.transaction.Logistics;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.TransactionListForm;
import com.neza.exception.AppException;

public interface TransactionBiz {

	public List getTransactions(TransactionListForm transactionListForm,boolean isPass)throws AppException;
	
	public List getSellerTransactionList(TransactionListForm transactionListForm,boolean isPass)throws AppException;
	
	public List getBuyerTransactionList(TransactionListForm transactionListForm,boolean isPass)throws AppException;
	
	public List getTransactionByToAgentId(TransactionListForm transactionListForm)throws AppException;
	
	public List getBorrowAndRepaymentList(TransactionListForm transactionListForm)throws AppException;
	
	/**
	 * 授信/还款方法
	 */
	public List getLetterAndRepaymentList(TransactionListForm transactionListForm)throws AppException;
	
	public List getDebitAndExpenseList(TransactionListForm transactionListForm)throws AppException;
	
	public List getDebitList(Agent agent,TransactionListForm transactionListForm)throws AppException;
	
	public List getExpenseList(Agent agent,TransactionListForm transactionListForm)throws AppException;
	
	public long addDebit(Transaction transaction,Agent agent)throws AppException;
	
	public long addExpense(Transaction transaction,Agent agent)throws AppException;
	
	public long addTransaction(Transaction transaction,Agent agent)throws AppException;
	
	public long addTransactionBorrowing(Transaction transaction,Agent agent)throws AppException;
	
	public long addTransactionRepayment(Transaction transaction)throws AppException;
	
	public long transactionCreditRepayment(Transaction transaction)throws AppException;
	
	public List getTransactionByOrderNo(String orderNo)throws AppException;
	
	public long addOrderDetails(OrderDetails orderDetails)throws AppException;
	
	public List getAgentAddressList(Agent agent)throws AppException;
	
	public List getContactList(Agent agent)throws AppException;
	
	public List getListContact(Agent agent,String searchContactInput)throws AppException;
	
	public void updateTransactionStatus(long statusid,long tid)throws AppException;
	
	public Transaction getTransactionById(long tid,long orderid)throws AppException;
	
	public Transaction updateTransactionPrice(Transaction transaction)throws AppException;
	
	public List<Long> addBatchCollect(Transaction transaction,Agent agent)throws AppException;
	
	public List<Long> addBatchPayment(Transaction transaction,Agent agent)throws AppException;
	public List<Long> addBatchRedPacket(Transaction transaction,Agent agent)throws AppException;
	
	public List getBatchCollectDetail(TransactionListForm transactionListForm,Agent agent)throws AppException;
	
	public List getBatchCollectDetailById(TransactionListForm transactionListForm)throws AppException;
	
	public List getBatchPaymentDetail(TransactionListForm transactionListForm,Agent agent)throws AppException;
	
	public List getBatchPaymentDetailById(TransactionListForm transactionListForm)throws AppException;
	
	public void addMark(Transaction transaction)throws AppException;
	
	public AgentAddress getAgentAddressById(Agent agent)throws AppException;
	
	public void updateTransactionPayment(long transactionId)throws AppException;
	
	public Transaction getRefundMentById(Transaction transaction)throws AppException;
	
	public Transaction applicationRefundShop(Transaction transaction)throws AppException;
	
	public List refundMentManage(TransactionListForm transactionListForm)throws AppException;
	
	public List getSellerTransactionRefundList(TransactionListForm transactionListForm)throws AppException;
	
	public List getBuyerTransactionRefundList(TransactionListForm transactionListForm)throws AppException;
	
	public void shippingConfirm(Logistics logistics)throws AppException;
	
	public Logistics getLogisticsByOrderId(long orderid)throws AppException;
	
	public List getAgentTransactions(TransactionListForm transactionListForm,Agent agent)throws AppException;
	
	public Transaction agreeRefundMent(Transaction transaction,Agent agent)throws AppException;
	
	public Transaction offLineRefund(Transaction transaction,Agent agent)throws Exception;
	
	public Transaction offLineCreditRefund(Transaction transaction,Agent agent)throws Exception;
	
	public Transaction closeTransactionConfirm(Transaction transaction)throws AppException;
	
	public Transaction getTransactionByNo(String no)throws AppException;
	
	public Transaction transactionPaymentReturnByBank(String no)throws AppException;
	
	public Transaction transaction19payByBank(String no)throws AppException;
	
	public void sendBatchEmail(long transactionId)throws AppException;
	
	public void sendEmail(long transactionId)throws AppException;
	
	public void sendEmailByRepayment(long transactionId)throws AppException;
	
	public void sendEmail(Transaction transaction)throws AppException;
	
	public void sendEmail(long transactionId,Logistics logistics)throws AppException;
	
	public BigDecimal getAmountSum(Agent agent) throws AppException;
	
	public Transaction getTransactionById(long transactionId) throws AppException;
	
	public Debit getDebitByDebitNo(String debitNo) throws AppException;
	
	public ArrayList<ArrayList<Object>> getTransactionReportData(TransactionListForm tlf) throws AppException;//导出报表
	
	public void executeClose()throws AppException;
	
	public int getTransactionBalanceRow(TransactionListForm tlf)throws AppException ;
	public BigDecimal getCreditPaymentArrearage(Agent  agent) throws AppException;
	public BigDecimal getCreditRePaymentAmount(Agent  agent) throws AppException;
	

	public Transaction getCreditGiverTransactionByAgent(Agent  agent) throws AppException;
	//收红包
	public boolean GatherRedPacket(long tid)throws AppException;

//	public List getRedPacket(long tid) throws AppException;
	//退还红包
	public boolean  RetreatRedPacket(long tid) throws AppException;
//	public Transaction getRedPacketListByTid(long tid) throws AppException; //根据transaction id 查询
	public List getRedPacketList(TransactionListForm transactionListForm)throws AppException; //显示红包
}

