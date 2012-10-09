package com.fordays.fdpay.cooperate.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBind;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AgentBindDAO extends BaseDAO {
	public long save(AgentBind agentBind) throws AppException;
	
	public void deleteById(long id) throws AppException;
	
	public AgentBind queryAgentBindByAgentId(long agentId,long bindAgentId) throws AppException;

	public Agent getAgentByEmail(String loginName) throws AppException;
	
	//public AgentCoterie getAgentCoterieById(long agentId) throws AppException;
	
	public Agent queryByEmailAndPassword(String email, String password)throws AppException;
	
	public List getAgentCoterieById(long agentId) throws AppException;
	
	/**
	 * 根据一个帐号判断有没有绑定
	 * @param agentId
	 * @return
	 * @throws AppException
	 */
	public AgentBind queryAgentBindByAgentBindId(long bindAgentId) throws AppException;
	
	public Agent getAgentById(long id) throws AppException;
	
	/**
	 * 找买家的圈Id
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public AgentCoterie getCoterieId(long id) throws AppException;
	
	/**
	 * 根据圈主Id找到圈主信息
	 */
	public Agent getAgentByCoterieId(long id) throws AppException;
	
	/**
	 * 查找到圈名
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterieName(long id) throws AppException;
	
	public Transaction getByMoney(String orderDetailsNo) throws AppException;
	
	public Transaction getBySellMoney(String orderDetailsNo) throws AppException;
	
	public BigDecimal getByFee(String orderNo,long toAgentId,long fromAgentId) throws AppException;
	
	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo)throws AppException;
	
	public List getListCoterieId(long id) throws AppException;
	
	/**
	 * 根据合作伙伴查找到圈名
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterie(String partner) throws AppException;
	
	public OrderDetails queryOrderDetailByOrderDetailsNo(String OrderDetailsNo)
    throws AppException;
	
	public OrderDetails queryOrderDetailByTransNo(String orderNo,String OrderDetailsNo,
		    String partnerId, Long status) throws AppException;
	
	public Transaction queryTransactionByOrderAndFromAgent(long orderId) throws AppException;
	
	public Transaction getByUnfreeze(long id) throws AppException;
	
	public OrderDetails queryOrderDetail(String orderNo,Long status) throws AppException;
	
	public void saveOrderDetails(OrderDetails orderDetails);
	
	public List queryOrderDetailByLikeOrderNo(String OrderDetailsNo)throws AppException;
	
	public List queryOrderDetailsByLikeOrderNo(String OrderNo)throws AppException;
	
	public OrderDetails queryOrderDetailByTransNo(String orderDetailsNo,
			String partnerId) throws AppException;
}


