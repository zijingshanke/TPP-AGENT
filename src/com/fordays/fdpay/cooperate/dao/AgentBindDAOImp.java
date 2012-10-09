package com.fordays.fdpay.cooperate.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBind;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.transaction.Logistics;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AgentBindDAOImp  extends BaseDAOSupport implements AgentBindDAO{
	public long save(AgentBind agentBind) throws AppException {		
		this.getHibernateTemplate().saveOrUpdate(agentBind);
		return agentBind.getId();
	}
	
	
	public void deleteById(long id) throws AppException {
		if (id > 0) {
			AgentBind agentBind = (AgentBind) this.getHibernateTemplate().get(
					AgentBind.class, new Long(id));
			this.getHibernateTemplate().delete(agentBind);
		}
	}
	
	public AgentBind queryAgentBindByAgentId(long agentId,long bindAgentId) throws AppException{
		Hql hql = new Hql("from AgentBind where agentId=? and bindAgentId=?");
		hql.addParamter(agentId);
		hql.addParamter(bindAgentId);
		Query query = this.getQuery(hql);
		AgentBind agentBind=null;
		if (query!=null&& query.list()!=null&&query.list().size() > 0)
		{
			agentBind=(AgentBind) query.list().get(0);
		}
		return agentBind;
		
	}
	
	/**
	 * 根据一个帐号判断有没有绑定
	 * @param agentId
	 * @return
	 * @throws AppException
	 */
	public AgentBind queryAgentBindByAgentBindId(long bindAgentId) throws AppException{
		
		Hql hql = new Hql("from AgentBind where bindAgentId=?");
		hql.addParamter(bindAgentId);
		Query query = this.getQuery(hql);
		AgentBind agentBind=null;
		if (query!=null&& query.list()!=null&&query.list().size() > 0)
		{
			
			agentBind=(AgentBind) query.list().get(0);
			
		}
		return agentBind;
		
	}
	
	public Agent getAgentByEmail(String loginName) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Agent a where LOWER(a.loginName)=LOWER(?) ");
		hql.addParamter(loginName);
		Query query = this.getQuery(hql);
		
		int listsize=query.list().size();
		
		if (query != null &&  listsize> 0){			
			Agent agent=(Agent) query.list().get(0);
			return agent;
		}
		else
		{
			return null;
		}
	}
	
	public Agent getAgentById(long id) throws AppException
	{
		Agent agent;
		if (id > 0)
		{
			agent = (Agent) this.getHibernateTemplate()
			    .get(Agent.class, new Long(id));
			return agent;
		}
		else
		{
			return null;
		}
	}
	
	public List getAgentCoterieById(long agentId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("select a.coterie.id from AgentCoterie a where a.agent.id=?");
		hql.addParamter(agentId);
		List list=null;
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0){
			return list=query.list();
		}
		else
		{
			return list;
		}
	}
	
	public Agent queryByEmailAndPassword(String email, String password)throws AppException
	{
		Query query = this
		    .getQuery("from Agent where LOWER(email)=LOWER(?) and payPassword=?");
		query.setString(0, email);
		query.setString(1, password);
		if (query.list() != null && query.list().size() > 0){
			return (Agent) query.list().get(0);
		}
		else
			return null;
	}
	
	/**
	 * 找买家的圈Id
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public AgentCoterie getCoterieId(long id) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AgentCoterie a where a.agent.id=?");
		hql.addParamter(id);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0){
			return (AgentCoterie)query.list().get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 根据圈主Id找到圈主信息
	 */
	public Agent getAgentByCoterieId(long id) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Agent a where a.id=(select c.agent.id from Coterie c where c.id=?)");
		hql.addParamter(id);
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0){
			return (Agent) query.list().get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 查找到圈名
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterieName(long id) throws AppException
	{
		Hql hql=new Hql();
		hql.add("from Coterie c where c.id=?");
		hql.addParamter(id);
		Query query=this.getQuery(hql);
		if(query!=null && query.list().size()>0){
			return (Coterie)query.list().get(0);
		}
		else
		{
			return null;
		}
	}
	
	
	public Transaction getByMoney(String orderDetailsNo) throws AppException{
		Hql hql=new Hql();
		hql.add("from Transaction t where t.orderDetails.id=(select o.id from OrderDetails o where o.orderDetailsNo=? and o.status in(11,12)) and t.status=3 order by id");
		hql.addParamter(orderDetailsNo);
		Query query=this.getQuery(hql);
		if(query!=null && query.list().size()>0){
			return (Transaction)query.list().get(0);
		}
		else
		{
			return null;
		}
		
	}

	public Transaction getBySellMoney(String orderDetailsNo) throws AppException{
		Hql hql=new Hql();
		hql.add("from Transaction t where t.orderDetails.id=(select o.id from OrderDetails o where o.orderDetailsNo=? and o.status in(11,12)) and t.status=3 order by id desc");
		hql.addParamter(orderDetailsNo);
		Query query=this.getQuery(hql);
		if(query!=null && query.list().size()>0){
			return (Transaction)query.list().get(0);
		}
		else
		{
			return null;
		}
		
	}

	public BigDecimal getByFee(String orderNo,long toAgentId,long fromAgentId) throws AppException{
		BigDecimal refundBalance = new BigDecimal("0.00");
		Hql hql=new Hql();
		hql.add("select SUM(t.amount) from Transaction t where t.orderDetails.id in(select o.id from OrderDetails o  where o.orderNo like ? and o.status=3) and t.toAgent.id=?  and t.fromAgent.id=? and t.status=11");
		hql.addParamter("%~"+orderNo);
		hql.addParamter(toAgentId);
		hql.addParamter(fromAgentId);
		Query query=this.getQuery(hql);

		if(query!=null && query.list().size()>0){
			return refundBalance=(BigDecimal)query.list().get(0);
		}
		else
		{
			return refundBalance;
		}
		
	}
	
	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo)
    throws AppException
	{
		
		Hql hql = new Hql("from OrderDetails o where o.orderNo=?");
		hql.addParamter(orderNo);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}
	
	public List getListCoterieId(long id) throws AppException
	{
		Hql hql = new Hql();
		hql.add("select a.coterie.agent.id from AgentCoterie a where a.agent.id=?");
		hql.addParamter(id);
		Query query = this.getQuery(hql);
		List list=null;
		if (query != null && query.list().size() > 0){
			return list=query.list();
		}
		else
		{
			return list;
		}
	}
	
	/**
	 * 根据合作伙伴查找到圈名
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public Coterie getByCoterie(String partner) throws AppException
	{
		Hql hql=new Hql();
		hql.add("from Coterie c where c.partner=?");
		hql.addParamter(partner);
		Query query=this.getQuery(hql);
		if(query!=null && query.list().size()>0){
			return (Coterie)query.list().get(0);
		}
		else
		{
			return null;
		}
	}
	
	
	public OrderDetails queryOrderDetailByOrderDetailsNo(String OrderDetailsNo)
    throws AppException
	{
		
		Hql hql = new Hql("from OrderDetails o where o.orderDetailsNo=?");
		hql.addParamter(OrderDetailsNo);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}
	
	/**
	 * 根据参数查询是否存在这条交易
	 */
	public OrderDetails queryOrderDetailByTransNo(String orderNo,String OrderDetailsNo,
	    String partnerId, Long status) throws AppException
	{
		Hql hql = new Hql(
		    "from OrderDetails where orderNo=? and orderDetailsNo=? and partner=? and status=?");
		hql.addParamter(orderNo);
		hql.addParamter(OrderDetailsNo);
		hql.addParamter(partnerId);
		hql.addParamter(status);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}
	
	
	
	/**
	 * 根据参数查询是否存在这条交易
	 */
	public OrderDetails queryOrderDetail(String orderNo,Long status) throws AppException
	{
		Hql hql = new Hql(
		    "from OrderDetails where orderNo=? and status=?");
		hql.addParamter(orderNo);
		hql.addParamter(status);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}
	
	/**
	 * 查找交易是否存在
	 * @param orderId
	 * @param fromAgentId
	 * @return
	 * @throws AppException
	 */
	public Transaction queryTransactionByOrderAndFromAgent(long orderId) throws AppException
		{
			Transaction transaction = null;
			if (orderId > 0)
			{
				Hql hql = new Hql(
				    "from Transaction t where orderDetails.id=? and status=? and type in(?,?,?) and rownum<=1 order by id");
				hql.addParamter(orderId);
				hql.addParamter(Transaction.STATUS_3);
				hql.addParamter(Transaction.TYPE_99);
				hql.addParamter(Transaction.TYPE_80);
				hql.addParamter(Transaction.TYPE_197);
				Query query = this.getQuery(hql);
				if (query != null && query.list() != null && query.list().size() > 0)
				{
					transaction = (Transaction) query.list().get(0);
				}
			}
			return transaction;
		}
	
	public Transaction getByUnfreeze(long id) throws AppException{
		Hql hql=new Hql();
		hql.add("from Transaction where orderDetails.id=? and status=3 and type=?");
		hql.addParamter(id);
		hql.addParamter(Transaction.TYPE_198);
		Query query=this.getQuery(hql);
		if(query!=null && query.list().size()>0){
			return (Transaction)query.list().get(0);
		}
		else
		{
			return null;
		}
		
	}
	
	public void saveOrderDetails(OrderDetails orderDetails)
	{
		this.getHibernateTemplate().save(orderDetails);
	}
	
	public List queryOrderDetailByLikeOrderNo(String OrderNo)throws AppException
	{
		Hql hql = new Hql("from OrderDetails o where o.orderNo like ?");
		hql.addParamter(OrderNo+"~%");
		Query query = this.getQuery(hql);
		List list=null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}
	
	public List queryOrderDetailsByLikeOrderNo(String OrderNo)throws AppException
	{
		Hql hql = new Hql("from OrderDetails o where o.orderNo like ?");
		hql.addParamter("%~"+OrderNo);
		Query query = this.getQuery(hql);
		List list=null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}
	
	/**
	 * 根据参数查询这条交易
	 */
	public OrderDetails queryOrderDetailByTransNo(String orderDetailsNo,
			String partnerId) throws AppException {
		Hql hql = new Hql(
				"from OrderDetails where orderDetailsNo=? and partner=? and status in(?,?) order by id");
		hql.addParamter(orderDetailsNo);
		hql.addParamter(partnerId);
		hql.addParamter(OrderDetails.STATUS_11);
		hql.addParamter(OrderDetails.STATUS_12);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}

}
