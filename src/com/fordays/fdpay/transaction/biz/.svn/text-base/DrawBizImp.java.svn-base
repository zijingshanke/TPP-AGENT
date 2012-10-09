package com.fordays.fdpay.transaction.biz;

import java.math.BigDecimal;
import java.util.List;
import java.sql.Timestamp;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawListForm;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.dao.DrawDAO;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.fordays.fdpay.transaction.Transaction;
import com.neza.exception.AppException;
import com.neza.base.NoUtil;

public class DrawBizImp implements DrawBiz {
	private DrawDAO drawDAO;
	private AgentDAO agentDAO;
	private NoUtil noUtil;
	private TransactionDAO transactionDAO;

	public BigDecimal getAmountSumToDay(Agent agent) throws AppException {
		return drawDAO.getAmountSumToDay(agent);
	}

	public Draw getDrawById(long id) throws AppException {
		return drawDAO.getDrawById(id);
	}

	public List getDraws(Draw draw) throws AppException {
		return drawDAO.list(draw);
	}

	public void saveDraw(Draw draw) throws AppException {
		drawDAO.save(draw);
	}

	public long updateInfo(Draw draw) throws AppException {
		drawDAO.update(draw);
		return 0;
	}

	public void deleteDraw(long id) throws AppException {
		drawDAO.deleteById(id);
	}

	public Draw queryDrawById(long id) throws AppException {
		return drawDAO.queryDrawById(id);
	}

	public List getDrawsByAgent(Agent agent, DrawListForm dlf)
			throws AppException {
		return drawDAO.getDrawsByAgent(agent, dlf);
	}

	public long addDraw(Draw draw) throws AppException {
		return drawDAO.save(draw);
	}
	

	/**
	 * 保存普通提现记录
	 * 
	 * */
	public long applyDraw(Draw draw, Agent agent)
			throws AppException {		
		String shopName = "提现金额冻结";
		BigDecimal drawAmount = draw.getAmount();
		Timestamp sysTime = new Timestamp(System.currentTimeMillis());
		
		// 冻结提现金额
		agentDAO.moveAllowBalanceToNotallowBalance(agent, drawAmount);		

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
		orderDetails.setShopName(shopName);
		orderDetails.setPaymentPrice(drawAmount);
		orderDetails.setFinishDate(sysTime);
		orderDetails.setDetailsContent(shopName);
		orderDetails.setOrderNo(draw.getOrderNo());
		orderDetails.setShopPrice(drawAmount);
		orderDetails.setShopTotal(new Long(1));
		orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_5);
		orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
		transactionDAO.addOrderDetails(orderDetails);

		Transaction transaction = new Transaction();
		transaction.setNo(noUtil.getTransactionNo());
		transaction.setMark(shopName);
		transaction.setType(Transaction.TYPE_40);
		transaction.setAmount(drawAmount);
		transaction.setFromAgent(agent);
		transaction.setToAgent(agent);
		transaction.setStatus(Transaction.STATUS_3);
		transaction.setPayDate(sysTime);
		transaction.setAccountDate(sysTime);
		transaction.setOrderDetails(orderDetails);
		transactionDAO.saveTransaction(transaction);

		return saveGeneralDraw(agent,draw);
	}

	/**
	 * 保存提现申请
	 */
	public Long saveGeneralDraw(Agent agent,Draw draw) throws AppException {
		Account account=drawDAO.getBindAccountByAgent(agent);
		
		draw.setCity(account.getCity());
		draw.setAccountBank(account.getAccountAddress());
		
		Long saveAppDrawFlag = drawDAO.save(draw);
		
		return saveAppDrawFlag;
	}

	public int getDrawViewsToDay(Agent agent) throws AppException {
		return drawDAO.getDrawViewsToDay(agent);
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setDrawDAO(DrawDAO drawDAO) {
		this.drawDAO = drawDAO;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}
}
