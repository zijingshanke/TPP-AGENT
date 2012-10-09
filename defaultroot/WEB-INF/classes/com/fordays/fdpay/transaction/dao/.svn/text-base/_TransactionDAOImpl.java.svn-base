package com.fordays.fdpay.transaction.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.support.TransactionTemplate;

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
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class _TransactionDAOImpl extends BaseDAOSupport implements
    TransactionDAO
{

	private TransactionTemplate transactionTemplate;

	public TransactionTemplate getTransactionTemplate()
	{
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate)
	    throws AppException
	{
		this.transactionTemplate = transactionTemplate;
	}

	public List getAgentTransactions(TransactionListForm transactionListForm,
	    boolean isPass) throws AppException
	{
		Hql hql0 = new Hql();
		Hql hql = new Hql();
		Hql[] hqls = parseHql(transactionListForm, isPass);
		hql0 = hqls[0];
		hql = hqls[1];
		System.out.println("HQL========="+hql);
		int totalRowCount = this.getTotalCount(hql);
		transactionListForm.setTotalRowCount(totalRowCount);
		Query query = this.getQuery(hql0);
		query.setFirstResult(transactionListForm.getStart());
		query.setMaxResults(transactionListForm.getPerPageNum());
		transactionListForm.setHql(hql.toString());
		List list;
		if (query != null)
		{
			list = query.list();
		}
		else
		{
			list = new ArrayList();
		}
		return list;
	}

	public List list(TransactionListForm transactionListForm, boolean isPass)
	    throws AppException
	{
		Hql hql0 = new Hql();
		Hql hql = new Hql();
		Hql[] hqls = parseHql(transactionListForm, isPass);
		hql0 = hqls[0];
		hql = hqls[1];

		Query query = this.getQuery(hql0);
		List list;
		if (query != null)
		{
			list = query.list();
		}
		else
		{
			list = new ArrayList();
		}
		return list;
	}

	private Hql[] parseHql(TransactionListForm transactionListForm, boolean isPass)
	    throws AppException
	{
		Hql hql0 = new Hql();

		hql0
		    .add(" select new com.fordays.fdpay.transaction.TempTransaction(orderDetails,t0.id,t0.accountDate,t0.no,t0.fromAgent,t0.toAgent,t0.amount,");
		hql0
		    .add(" (select nvl(sum(amount),0) from Transaction t1 where toAgent.id = ?  and t1.status in(?,?) and t1.id<=t0.id) , ");
		hql0
		    .add(" (select nvl(sum(amount),0) from Transaction t2 where fromAgent.id =?  and t2.status in(?,?) and t2.id<=t0.id),");
		hql0.add(" t0.type,");
		hql0.add(" t0.mark,");
		hql0.add(" t0.status");
		hql0.add(" ) ");
		Hql hql = new Hql();
		hql.add(" from Transaction as t0 ");
		hql
		    .add(" where (t0.fromAgent.id = ? or t0.toAgent.id = ?)  and t0.status in(?,?)   ");

		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();

	

		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");

		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss')<?");

		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql.add(" and (to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss') between ? and ?) ");
				 
		}

		if (!isPass)
			hql.add(" and t0.type<>" + Transaction.TYPE_70);
		hql.add("  order by t0.id desc ");
		hql0.add(hql.toString());

		hql0.addParamter(transactionListForm.getAgent().getId());
		hql0.addParamter(Transaction.STATUS_3);
		hql0.addParamter(Transaction.STATUS_11);
		hql0.addParamter(transactionListForm.getAgent().getId());
		hql0.addParamter(Transaction.STATUS_3);
		hql0.addParamter(Transaction.STATUS_11);
		hql0.addParamter(transactionListForm.getAgent().getId());
		hql0.addParamter(transactionListForm.getAgent().getId());
		hql0.addParamter(Transaction.STATUS_3);
		hql0.addParamter(Transaction.STATUS_11);

		hql.addParamter(transactionListForm.getAgent().getId());
		hql.addParamter(transactionListForm.getAgent().getId());
		hql.addParamter(Transaction.STATUS_3);
		hql.addParamter(Transaction.STATUS_11);

		if (minDate != null && maxDate == null)
		{
			hql0.addParamter(minDate);
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql0.addParamter(maxDate);
			hql.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{

			hql0.addParamter(minDate);
			hql0.addParamter(maxDate);
			hql.addParamter(minDate);
			hql.addParamter(maxDate);

			// hql0.addParamter(DateUtil.getDate(minDate + " 00:00:00",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql0.addParamter(DateUtil.getDate(maxDate + " 23:59:59",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql.addParamter(DateUtil.getDate(minDate + " 00:00:00",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql.addParamter(DateUtil.getDate(maxDate + " 23:59:59",
			// "yyyy-MM-dd HH:mm:ss"));
		}
		Hql[] hqls = new Hql[2];
		hqls[0] = hql0;
		hqls[1] = hql;
		return hqls;

	}

	public TempTransaction statToAgentTransaction(
	    TransactionListForm transactionListForm, boolean isPass)
	    throws AppException
	{
		Hql hql0 = new Hql();

		hql0
		    .add(" select new com.fordays.fdpay.transaction.TempTransaction(sum(amount),count(amount))");
		Hql hql = new Hql();
		hql.add(" from Transaction as t0 ");
		hql.add(" where t0.toAgent.id = ?  and t0.status in(?,?) ");
		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();

		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");

		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss')<?");
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			// hql.add(" and (to_char(t0.accountDate,'yyyy-MM-dd') between ? and ?)
			// ");
			hql
			    .add(" and (to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss') between ? and ?) ");
			//			 
		}

		if (!isPass)
			hql.add(" and t0.type<>" + Transaction.TYPE_70);
		hql.add("  order by t0.id desc ");
		hql0.add(hql.toString());
		hql0.addParamter(transactionListForm.getAgent().getId());
		hql0.addParamter(Transaction.STATUS_3);
		hql0.addParamter(Transaction.STATUS_11);

		if (minDate != null && maxDate == null)
		{

			hql0.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{

			hql0.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{

			hql0.addParamter(minDate);
			hql0.addParamter(maxDate);

			// hql0.addParamter(DateUtil.getDate(minDate + " 00:00:00",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql0.addParamter(DateUtil.getDate(maxDate + " 23:59:59",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql.addParamter(DateUtil.getDate(minDate + " 00:00:00",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql.addParamter(DateUtil.getDate(maxDate + " 23:59:59",
			// "yyyy-MM-dd HH:mm:ss"));
		}
		Query query = this.getQuery(hql0);
		if (query != null) { return (TempTransaction) query.list().get(0); }
		return null;

	}

	public TempTransaction statFromAgentTransaction(
	    TransactionListForm transactionListForm, boolean isPass)
	    throws AppException
	{
		Hql hql0 = new Hql();

		hql0
		    .add(" select new com.fordays.fdpay.transaction.TempTransaction(sum(amount),count(amount))");
		Hql hql = new Hql();
		hql.add(" from Transaction as t0 ");
		hql.add(" where t0.fromAgent.id = ?  and t0.status in(?,?)");
		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();

		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");

		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss')<?");

		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			// hql.add(" and (to_char(t0.accountDate,'yyyy-MM-dd') between ? and ?)
			// ");
			hql
			    .add(" and (to_char(t0.accountDate,'yyyy-mm-dd hh24:mi:ss') between ? and ?) ");
			//			 
		}

		if (!isPass)
			hql.add(" and t0.type<>" + Transaction.TYPE_70);
		hql.add("  order by t0.id desc ");
		hql0.add(hql.toString());
		hql0.addParamter(transactionListForm.getAgent().getId());
		hql0.addParamter(Transaction.STATUS_3);
		hql0.addParamter(Transaction.STATUS_11);

		if (minDate != null && maxDate == null)
		{
			hql0.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql0.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{

			hql0.addParamter(minDate);
			hql0.addParamter(maxDate);

			// hql0.addParamter(DateUtil.getDate(minDate + " 00:00:00",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql0.addParamter(DateUtil.getDate(maxDate + " 23:59:59",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql.addParamter(DateUtil.getDate(minDate + " 00:00:00",
			// "yyyy-MM-dd HH:mm:ss"));
			// hql.addParamter(DateUtil.getDate(maxDate + " 23:59:59",
			// "yyyy-MM-dd HH:mm:ss"));
		}
		Query query = this.getQuery(hql0);
		if (query != null && query.list().size() > 0) { return (TempTransaction) query
		    .list().get(0); }
		return null;

	}

	/**
	 * 借款/还款方法
	 */
	public List getBorrowAndRepaymentList(TransactionListForm transactionListForm)
	    throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Transaction where (fromAgent.id =? or toAgent.id =? ) and type in(9,91)");
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());

		if (transactionListForm.getSearchAgentName() != null
		    && !transactionListForm.getSearchAgentName().equals(""))
		{
			hql.add(" and (fromAgent.name like ? or toAgent.name like ?) ");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
		}

		if (transactionListForm.getSearchOutOrderNo() != null
		    && !transactionListForm.getSearchOutOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderNo like ? ");
			hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
			    + "%");
		}

		if (transactionListForm.getSearchOrderNo() != null
		    && !transactionListForm.getSearchOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderDetailsNo like ? ");
			hql
			    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
			        + "%");
		}

		if (transactionListForm.getSearchShopName() != null
		    && !transactionListForm.getSearchShopName().equals(""))
		{
			hql.add(" and orderDetails.shopName like ? ");
			hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
			    + "%");
		}

		if (transactionListForm.getAgentType() == 1)
		{ // 买入
			hql.add(" and (fromAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}
		else if (transactionListForm.getAgentType() == 2)
		{ // 卖出
			hql.add(" and (toAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}

		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		hql.add(" and (amount >= ? and amount <= ? )");
		hql.addParamter(transactionListForm.getSearchBeginMoney());
		hql.addParamter(transactionListForm.getSearchEndMoney());
		hql.add(" order by id desc");
		return this.list(hql, transactionListForm);
	}
	

	/**
	 * 授信/还款方法
	 */
	public List getLetterAndRepaymentList(TransactionListForm transactionListForm)
	    throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Transaction where (fromAgent.id =? or toAgent.id =? ) and type in(9,91)");
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());

		if (transactionListForm.getSearchOutOrderNo() != null
		    && !transactionListForm.getSearchOutOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderNo like ? ");
			hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
			    + "%");
		}

		if (transactionListForm.getSearchOrderNo() != null
		    && !transactionListForm.getSearchOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderDetailsNo like ? ");
			hql
			    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
			        + "%");
		}

		if (transactionListForm.getSearchShopName() != null
		    && !transactionListForm.getSearchShopName().equals(""))
		{
			hql.add(" and orderDetails.shopName like ? ");
			hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
			    + "%");
		}

		if (transactionListForm.getAgentType() == 1)
		{ // 买入
			hql.add(" and (fromAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}
		else if (transactionListForm.getAgentType() == 2)
		{ // 卖出
			hql.add(" and (toAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}

		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		hql.add(" and (amount >= ? and amount <= ? )");
		hql.addParamter(transactionListForm.getSearchBeginMoney());
		hql.addParamter(transactionListForm.getSearchEndMoney());
		hql.add(" order by id desc");
		return this.list(hql, transactionListForm);
	}
	
	

	public List getTransactions(TransactionListForm transactionListForm,
	    boolean isPass) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Transaction where (fromAgent.id =? or toAgent.id =? )");
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());

		if (transactionListForm.getSearchAgentName() != null
		    && !transactionListForm.getSearchAgentName().equals(""))
		{
			hql.add(" and (fromAgent.name like ? or toAgent.name like ?) ");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
		}

		if (transactionListForm.getSearchOutOrderNo() != null
		    && !transactionListForm.getSearchOutOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderNo like ? ");
			hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
			    + "%");
		}

		if (transactionListForm.getSearchOrderNo() != null
		    && !transactionListForm.getSearchOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderDetailsNo like ? ");
			hql
			    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
			        + "%");
		}

		if (transactionListForm.getSearchShopName() != null
		    && !transactionListForm.getSearchShopName().equals(""))
		{
			hql.add(" and orderDetails.shopName like ? ");
			hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
			    + "%");
		}

		if (transactionListForm.getAgentType() == 1)
		{ // 买入
			hql.add(" and (fromAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}
		else if (transactionListForm.getAgentType() == 2)
		{ // 卖出
			hql.add(" and (toAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}

		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		if (transactionListForm.getStatus() != null
		    && !transactionListForm.getStatus().equals(""))
		{
			if (transactionListForm.getStatus().equals("1"))
				hql
				    .add(" and (status=1 or status=2 or status=6 or status=10 or status=7 or orderDetails.status=6 or orderDetails.status=7)");
			else if (transactionListForm.getStatus().equals("3"))
			{
				hql.add(" and (status=3 or status=31)");

			}
			else if (transactionListForm.getStatus().equals("4"))
				hql.add(" and (status=4 or status=11)");
		}
		if (!isPass)
			hql.add(" and type<>" + Transaction.TYPE_70);

		hql.add(" and (amount >= ? and amount <= ? )");
		hql.addParamter(transactionListForm.getSearchBeginMoney());
		hql.addParamter(transactionListForm.getSearchEndMoney());
		//hql.add(" and (refundsStatus!=11 or refundsStatus is null) order by id desc");
		hql.add(" order by id desc");
		return this.list(hql, transactionListForm);
	}
	
	public List getTransactionByToAgentId(TransactionListForm transactionListForm)throws AppException{
		Hql hql = new Hql();
		hql.add("from Transaction where fromAgent.id =? and orderDetails.id=?");
		hql.addParamter(transactionListForm.getToAgentId());
		hql.addParamter(transactionListForm.getOrderId());
		return this.list(hql, transactionListForm);
	}
	
	public List getSellerTransactionList(TransactionListForm transactionListForm,
		    boolean isPass) throws AppException
		{
			Hql hql = new Hql();
			hql.add("from Transaction where toAgent.id =? ");
			hql.addParamter(transactionListForm.getAId());

			if (transactionListForm.getSearchAgentName() != null
			    && !transactionListForm.getSearchAgentName().equals(""))
			{
				hql.add(" and fromAgent.name like ? ");
				hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			}

			if (transactionListForm.getSearchOutOrderNo() != null
			    && !transactionListForm.getSearchOutOrderNo().equals(""))
			{
				hql.add(" and orderDetails.orderNo like ? ");
				hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
				    + "%");
			}

			if (transactionListForm.getSearchOrderNo() != null
			    && !transactionListForm.getSearchOrderNo().equals(""))
			{
				hql.add(" and orderDetails.orderDetailsNo like ? ");
				hql
				    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
				        + "%");
			}

			if (transactionListForm.getSearchShopName() != null
			    && !transactionListForm.getSearchShopName().equals(""))
			{
				hql.add(" and orderDetails.shopName like ? ");
				hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
				    + "%");
			}

//			if (transactionListForm.getAgentType() == 1)
//			{ // 买入
//				hql.add(" and (fromAgent.id=?) ");
//				hql.addParamter(transactionListForm.getAgent().getId());
//			}
//			else if (transactionListForm.getAgentType() == 2)
//			{ // 卖出
			//	hql.add(" and (toAgent.id=?) ");
			//	hql.addParamter(transactionListForm.getAgent().getId());
			//}

			String minDate = transactionListForm.getBeginDate().equals("") ? null
			    : transactionListForm.getBeginDate();
			String maxDate = transactionListForm.getEndDate().equals("") ? null
			    : transactionListForm.getEndDate();
			if (minDate != null && maxDate == null)
			{
				hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");
				hql.addParamter(minDate);
			}
			if (maxDate != null && minDate == null)
			{
				hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') < ?");
				hql.addParamter(maxDate);
			}
			if (minDate != null && !minDate.equals("") && maxDate != null
			    && !maxDate.equals(""))
			{
				hql
				    .add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
				hql.addParamter(minDate);
				hql.addParamter(maxDate);
			}

			if (transactionListForm.getStatus() != null
			    && !transactionListForm.getStatus().equals(""))
			{
				if (transactionListForm.getStatus().equals("1"))
					hql
					    .add(" and (status=1 or status=2 or status=6 or status=10 or status=7 or orderDetails.status=6 or orderDetails.status=7)");
				else if (transactionListForm.getStatus().equals("3"))
				{
					hql.add(" and (status=3 or status=31)");

				}
				else if (transactionListForm.getStatus().equals("4"))
					hql.add(" and (status=4 or status=11)");
			}
			if (!isPass)
				hql.add(" and type<>" + Transaction.TYPE_70);

			hql.add(" and (amount >= ? and amount <= ? )");
			hql.addParamter(transactionListForm.getSearchBeginMoney());
			hql.addParamter(transactionListForm.getSearchEndMoney());
			//hql.add(" and (refundsStatus!=11 or refundsStatus is null) order by id desc");
			hql.add(" order by id desc");
			return this.list(hql, transactionListForm);
		}
	
	public List getBuyerTransactionList(TransactionListForm transactionListForm,
		    boolean isPass) throws AppException
		{
			Hql hql = new Hql();
			hql.add("from Transaction where fromAgent.id =? ");
			hql.addParamter(transactionListForm.getAId());

			if (transactionListForm.getSearchAgentName() != null
			    && !transactionListForm.getSearchAgentName().equals(""))
			{
				hql.add(" and toAgent.name like ? ");
				hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			}

			if (transactionListForm.getSearchOutOrderNo() != null
			    && !transactionListForm.getSearchOutOrderNo().equals(""))
			{
				hql.add(" and orderDetails.orderNo like ? ");
				hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
				    + "%");
			}

			if (transactionListForm.getSearchOrderNo() != null
			    && !transactionListForm.getSearchOrderNo().equals(""))
			{
				hql.add(" and orderDetails.orderDetailsNo like ? ");
				hql
				    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
				        + "%");
			}

			if (transactionListForm.getSearchShopName() != null
			    && !transactionListForm.getSearchShopName().equals(""))
			{
				hql.add(" and orderDetails.shopName like ? ");
				hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
				    + "%");
			}

//			if (transactionListForm.getAgentType() == 1)
//			{ // 买入
//				hql.add(" and (fromAgent.id=?) ");
//				hql.addParamter(transactionListForm.getAgent().getId());
//			}
//			else if (transactionListForm.getAgentType() == 2)
//			{ // 卖出
			//	hql.add(" and (toAgent.id=?) ");
			//	hql.addParamter(transactionListForm.getAgent().getId());
			//}

			String minDate = transactionListForm.getBeginDate().equals("") ? null
			    : transactionListForm.getBeginDate();
			String maxDate = transactionListForm.getEndDate().equals("") ? null
			    : transactionListForm.getEndDate();
			if (minDate != null && maxDate == null)
			{
				hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') > ?");
				hql.addParamter(minDate);
			}
			if (maxDate != null && minDate == null)
			{
				hql.add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss') < ?");
				hql.addParamter(maxDate);
			}
			if (minDate != null && !minDate.equals("") && maxDate != null
			    && !maxDate.equals(""))
			{
				hql
				    .add(" and to_char(accountDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
				hql.addParamter(minDate);
				hql.addParamter(maxDate);
			}

			if (transactionListForm.getStatus() != null
			    && !transactionListForm.getStatus().equals(""))
			{
				if (transactionListForm.getStatus().equals("1"))
					hql
					    .add(" and (status=1 or status=2 or status=6 or status=10 or status=7 or orderDetails.status=6 or orderDetails.status=7)");
				else if (transactionListForm.getStatus().equals("3"))
				{
					hql.add(" and (status=3 or status=31)");

				}
				else if (transactionListForm.getStatus().equals("4"))
					hql.add(" and (status=4 or status=11)");
			}
			if (!isPass)
				hql.add(" and type<>" + Transaction.TYPE_70);

			hql.add(" and (amount >= ? and amount <= ? )");
			hql.addParamter(transactionListForm.getSearchBeginMoney());
			hql.addParamter(transactionListForm.getSearchEndMoney());
			//hql.add(" and (refundsStatus!=11 or refundsStatus is null) order by id desc");
			hql.add(" order by id desc");
			return this.list(hql, transactionListForm);
		}

	public long saveTransaction(Transaction transaction)
	{
		this.getHibernateTemplate().save(transaction);
		return transaction.getId();
	}

	public long addOrderDetails(OrderDetails orderDetails) throws AppException
	{
		this.getHibernateTemplate().save(orderDetails);
		return orderDetails.getId();
	}

	public List getAgentAddressList(Agent agent) throws AppException
	{
		String hql = " from AgentAddress a where a.agent.id='" + agent.getId()
		    + "'";
		Query query = this.getQuery(hql);
		return query.list();
	}

	public List getContactList(Agent agent) throws AppException
	{
		String hql = " from AgentContact a where a.agent.id='" + agent.getId()
		    + "'";
		Query query = this.getQuery(hql);
		return query.list();
	}

	public List getListContact(Agent agent, String searchContactInput)
	    throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AgentContact ac where 1=1 and ac.agent.id=?");
		hql.addParamter(agent.getId());
		if (!searchContactInput.equals("") && searchContactInput != null)
		{
			hql.add(" and ( ac.name like ?  or  LOWER(ac.email) like  LOWER(?))");
			hql.addParamter("%" + searchContactInput + "%");
			hql.addParamter("%" + searchContactInput + "%");
		}
		Query query = this.getQuery(hql);
		return query.list();
	}

	public Transaction getTransactionById(long tid) throws AppException
	{
		Transaction tran = null;

		if (tid > 0)
		{
			tran = (Transaction) this.getHibernateTemplate().get(Transaction.class,
			    new Long(tid));

		}
		return tran;
	}

	public long updateTransactionStatus(Transaction transaction)
	    throws AppException
	{
		this.getHibernateTemplate().update(transaction);
		return transaction.getId();
	}

	public OrderDetails getOrderDetailsById(long orderid) throws AppException
	{
		OrderDetails orderDetails;

		if (orderid > 0)
		{
			orderDetails = (OrderDetails) this.getHibernateTemplate().get(
			    OrderDetails.class, new Long(orderid));
			return orderDetails;
		}
		else
		{
			return new OrderDetails();
		}
	}

	public void updateTransactionPrice(Transaction transaction)
	    throws AppException
	{
		this.getHibernateTemplate().update(transaction);
	}

	public void updateOrderDetails(OrderDetails orderDetails) throws AppException
	{
		this.getHibernateTemplate().update(orderDetails);
	}

	public List getBatchCollectDetail(TransactionListForm transactionListForm,
	    Agent agent) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from OrderDetails o where exists (from Transaction where orderDetails.id=o.id and toAgent.id=? and type=5)");
		hql.addParamter(agent.getId());

		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(o.createDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(o.createDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(o.createDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		if (transactionListForm.getSearchKey() != null
		    && !transactionListForm.getSearchKey().equals(""))
		{
			hql.add(" and (o.shopName like ? or o.detailsContent like ? ) ");
			hql.addParamter("%" + transactionListForm.getSearchKey() + "%");
			hql.addParamter("%" + transactionListForm.getSearchKey() + "%");
		}

		hql.add(" order by o.createDate desc");

		List list = this.list(hql, transactionListForm);
		return list;
	}

	public List getBatchCollectDetailById(TransactionListForm transactionListForm)
	    throws AppException
	{
		String hql = "from Transaction t where t.orderDetails.id='"
		    + transactionListForm.getOrderId() + "' order by t.no desc";
		return this.list(hql, transactionListForm);
	}

	public List getBatchPaymentDetail(TransactionListForm transactionListForm,
	    Agent agent) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from OrderDetails o where exists (from Transaction where orderDetails.id=o.id and fromAgent.id=? and type=6)");
		hql.addParamter(agent.getId());

		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(o.createDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(o.createDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}

		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(o.createDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		if (transactionListForm.getSearchKey() != null
		    && !transactionListForm.getSearchKey().equals(""))
		{
			hql.add(" and (o.shopName like ? or o.detailsContent like ? ) ");
			hql.addParamter("%" + transactionListForm.getSearchKey() + "%");
			hql.addParamter("%" + transactionListForm.getSearchKey() + "%");
		}

		hql.add(" order by o.createDate desc");

		List list = this.list(hql, transactionListForm);
		return list;
	}

	public List getBatchPaymentDetailById(TransactionListForm transactionListForm)
	    throws AppException
	{
		String hql = "from Transaction t where t.orderDetails.id='"
		    + transactionListForm.getOrderId() + "' order by t.no desc";
		return this.list(hql, transactionListForm);
	}

	public void addMark(Transaction transaction) throws AppException
	{
		this.getHibernateTemplate().update(transaction);
	}

	public AgentAddress getAgentAddressById(Agent agent) throws AppException
	{
		AgentAddress agentAddress = null;
		Hql hql = new Hql();
		hql.add("from AgentAddress a where a.agent.id=? and a.status=1");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
		{
			agentAddress = (AgentAddress) query.list().get(0);
		}
		return agentAddress;
	}

	public List refundMentManage(TransactionListForm transactionListForm)
	    throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Transaction where (((fromAgent.id =? or toAgent.id =?) and status=10) or ((fromAgent.id =? or toAgent.id =?) and status=11) or ((fromAgent.id =? or toAgent.id =?) and status=7))");
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());

		if (transactionListForm.getSearchAgentName() != null
		    && !transactionListForm.getSearchAgentName().equals(""))
		{
			hql.add(" and (fromAgent.name like ? or toAgent.name like ?) ");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
		}

		if (transactionListForm.getSearchOutOrderNo() != null
		    && !transactionListForm.getSearchOutOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderNo like ? ");
			hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
			    + "%");
		}

		if (transactionListForm.getSearchOrderNo() != null
		    && !transactionListForm.getSearchOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderDetailsNo like ? ");
			hql
			    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
			        + "%");
		}

		if (transactionListForm.getSearchShopName() != null
		    && !transactionListForm.getSearchShopName().equals(""))
		{
			hql.add(" and orderDetails.shopName like ? ");
			hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
			    + "%");
		}

		if (transactionListForm.getAgentType() == 1)
		{ // 买入
			hql.add(" and (fromAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}
		else if (transactionListForm.getAgentType() == 2)
		{ // 卖出
			hql.add(" and (toAgent.id=?) ");
			hql.addParamter(transactionListForm.getAgent().getId());
		}
		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}

		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}

		// hql.add(" and status=10 or status=11");

		hql.add("order by refundsDate desc");

		return this.list(hql, transactionListForm);
	}
	
	public List getSellerTransactionRefundList(TransactionListForm transactionListForm)
    throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Transaction where ((toAgent.id =? and status=10) or (toAgent.id =? and status=11))");
		//hql.add("from Transaction where (((fromAgent.id =? or toAgent.id =?) and status=10) or ((fromAgent.id =? or toAgent.id =?) and status=11))");
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());
		//hql.addParamter(transactionListForm.getAId());
		//hql.addParamter(transactionListForm.getAId());
	
		if (transactionListForm.getSearchAgentName() != null
		    && !transactionListForm.getSearchAgentName().equals(""))
		{
			hql.add(" and (fromAgent.name like ? or toAgent.name like ?) ");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
		}
	
		if (transactionListForm.getSearchOutOrderNo() != null
		    && !transactionListForm.getSearchOutOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderNo like ? ");
			hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
			    + "%");
		}
	
		if (transactionListForm.getSearchOrderNo() != null
		    && !transactionListForm.getSearchOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderDetailsNo like ? ");
			hql
			    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
			        + "%");
		}
	
		if (transactionListForm.getSearchShopName() != null
		    && !transactionListForm.getSearchShopName().equals(""))
		{
			hql.add(" and orderDetails.shopName like ? ");
			hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
			    + "%");
		}
	
		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
	
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}
		hql.add("order by refundsDate desc");
	
		return this.list(hql, transactionListForm);
	}
	
	public List getBuyerTransactionRefundList(TransactionListForm transactionListForm)
    throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Transaction where ((fromAgent.id =? and status=10) or (fromAgent.id =? and status=11))");
		//hql.add("from Transaction where (((fromAgent.id =? or toAgent.id =?) and status=10) or ((fromAgent.id =? or toAgent.id =?) and status=11))");
		hql.addParamter(transactionListForm.getAId());
		hql.addParamter(transactionListForm.getAId());
		//hql.addParamter(transactionListForm.getAId());
		//hql.addParamter(transactionListForm.getAId());
	
		if (transactionListForm.getSearchAgentName() != null
		    && !transactionListForm.getSearchAgentName().equals(""))
		{
			hql.add(" and (fromAgent.name like ? or toAgent.name like ?) ");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
			hql.addParamter("%" + transactionListForm.getSearchAgentName() + "%");
		}
	
		if (transactionListForm.getSearchOutOrderNo() != null
		    && !transactionListForm.getSearchOutOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderNo like ? ");
			hql.addParamter("%" + transactionListForm.getSearchOutOrderNo().trim()
			    + "%");
		}
	
		if (transactionListForm.getSearchOrderNo() != null
		    && !transactionListForm.getSearchOrderNo().equals(""))
		{
			hql.add(" and orderDetails.orderDetailsNo like ? ");
			hql
			    .addParamter("%" + transactionListForm.getSearchOrderNo().trim()
			        + "%");
		}
	
		if (transactionListForm.getSearchShopName() != null
		    && !transactionListForm.getSearchShopName().equals(""))
		{
			hql.add(" and orderDetails.shopName like ? ");
			hql.addParamter("%" + transactionListForm.getSearchShopName().trim()
			    + "%");
		}
	
		String minDate = transactionListForm.getBeginDate().equals("") ? null
		    : transactionListForm.getBeginDate();
		String maxDate = transactionListForm.getEndDate().equals("") ? null
		    : transactionListForm.getEndDate();
		if (minDate != null && maxDate == null)
		{
			hql.add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss') > ?");
			hql.addParamter(minDate);
		}
		if (maxDate != null && minDate == null)
		{
			hql.add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss') < ?");
			hql.addParamter(maxDate);
		}
	
		if (minDate != null && !minDate.equals("") && maxDate != null
		    && !maxDate.equals(""))
		{
			hql
			    .add(" and to_char(refundsDate,'yyyy-mm-dd hh24:mi:ss')  between ? and ?");
			hql.addParamter(minDate);
			hql.addParamter(maxDate);
		}
		hql.add("order by refundsDate desc");
	
		return this.list(hql, transactionListForm);
	}

	public void shippingConfirm(Logistics logistics) throws AppException
	{
		if (logistics.getId() > 0)
			this.getHibernateTemplate().update(logistics);
		else
			this.getHibernateTemplate().save(logistics);
	}

	public Logistics getLogisticsByOrderId(long orderid) throws AppException
	{
		Hql hql = new Hql();
		hql
		    .add("from Logistics lo where exists (from OrderDetails o where lo.orderDetails.id=o.id and o.id=?)");
		hql.addParamter(orderid);

		Logistics logistics = null;
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
		{
			logistics = (Logistics) query.list().get(0);
		}
		return logistics;
	}

	public Transaction getTransactionByNo(String no) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Transaction t where t.no=?");
		hql.addParamter(no);
		Transaction transaction = null;
		Query query = this.getQuery(hql);
		if (query != null && query.list().size() > 0)
		{
			transaction = (Transaction) query.list().get(0);
		}
		return transaction;
	}

	public Long getTransactionTypeByNo(String no) throws AppException
	{
		return getTransactionByNo(no).getType();
	}

	// 20090507-001-jason-s-for-cooperate
	// *************transaction
	public double getRefundAmount(String orderDetailsNo, long fromAgentId,
	    long toAgentId) throws AppException
	{
		return getTransactionAmount(orderDetailsNo, fromAgentId, toAgentId,
		    Transaction.STATUS_11);
	}

	public double getTransactionAmount(String orderDetailsNo, long fromAgentId,
	    long toAgentId, long orderStatus) throws AppException
	{
		double refundBalance = 0l;

		Hql hql = new Hql(
		    "select SUM(t.amount) from Transaction t where t.orderDetails.orderNo=? and t.toAgent.id=?  and t.orderDetails.status=?");
		// hql = new Hql(
		// "select SUM(t.amount) from Transaction t where
		// t.orderDetails.orderDetailsNo=? and t.toAgent.id=? and
		// t.orderDetails.status=?");

		// Hql hql = new Hql("select SUM(t.amount) from Transaction t where
		// t.orderDetails.orderDetailsNo=? and t.toAgent.id=? and t.fromAgent.id=?
		// and t.orderDetails.status=?");
		// Hql hql = new Hql("select SUM(t.amount) from Transaction t where
		// t.orderDetails.orderDetailsNo=? and t.toAgent.id=? and t.status=?");
		hql.addParamter(orderDetailsNo);
		hql.addParamter(toAgentId);
		// hql.addParamter(fromAgentId);
		hql.addParamter(orderStatus);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			if (query.list().get(0) != null)
			{
				refundBalance = ((BigDecimal) query.list().get(0)).doubleValue();
			}
		}
		return refundBalance;
	}
	
	public double _getTransactionAmount(String orderDetailsNo, long fromAgentId,
		    long toAgentId, long orderStatus) throws AppException
		{
			double refundBalance = 0l;

			Hql hql = new Hql(
			    "select SUM(t.amount) from Transaction t where t.orderDetails.orderDetailsNo=? and t.toAgent.id=?  and t.orderDetails.status=?");
			hql.addParamter(orderDetailsNo);
			hql.addParamter(toAgentId);
			hql.addParamter(orderStatus);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				if (query.list().get(0) != null)
				{
					refundBalance = ((BigDecimal) query.list().get(0)).doubleValue();
				}
			}
			return refundBalance;
		}

	public BigDecimal getRefundTotalAmount(String orderDetailsNo,
	    long fromAgentId, long toAgentId, long status) throws AppException
	{
		BigDecimal refundBalance = new BigDecimal("0.00");

		Hql hql = new Hql(
		    "select SUM(t.amount) from Transaction t where t.orderDetails.orderNo=? and t.fromAgent.id=? and t.toAgent.id=?  and t.orderDetails.status=? ");

		hql.addParamter(orderDetailsNo);
		hql.addParamter(fromAgentId);
		hql.addParamter(toAgentId);
		hql.addParamter(status);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			if (query.list().get(0) != null)
			{
				refundBalance = (BigDecimal) query.list().get(0);
			}
		}
		return refundBalance;
	}
	
	public BigDecimal _getRefundTotalAmount(String orderDetailsNo,
		    long fromAgentId, long toAgentId, long status) throws AppException
		{
			BigDecimal refundBalance = new BigDecimal("0.00");

			Hql hql = new Hql(
			    "select SUM(t.amount) from Transaction t where t.orderDetails.orderDetailsNo=? and t.fromAgent.id=? and t.toAgent.id=?  and t.orderDetails.status=? ");

			hql.addParamter(orderDetailsNo);
			hql.addParamter(fromAgentId);
			hql.addParamter(toAgentId);
			hql.addParamter(status);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				if (query.list().get(0) != null)
				{
					refundBalance = (BigDecimal) query.list().get(0);
				}
			}
			return refundBalance;
		}


	public OrderDetails queryOrderDetailByIdAndPartner(long id, String partnerId)
	    throws AppException
	{
		Hql hql = new Hql("from OrderDetails where id=? and partner=?");
		hql.addParamter(id);
		hql.addParamter(partnerId);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}

	public OrderDetails getOrderDetailById(long id) throws AppException
	{

		OrderDetails orderDetails;
		if (id > 0)
		{
			orderDetails = (OrderDetails) this.getHibernateTemplate().get(
			    OrderDetails.class, new Long(id));
			return orderDetails;

		}
		else
			return new OrderDetails();
	}
	
	public OrderDetails getOrderDetailByOrderNo(String  orderNo) throws AppException {

		Hql hql = new Hql(" from OrderDetails where orderDetailsNo=?");
		hql.addParamter(orderNo);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}
	
	/**
	 * 根据参数查询是否存在这条交易
	 */
	public List<OrderDetails> queryOrderDetailsByorderDetailsNo(String orderDetailsNo,
	    String partnerId) throws AppException
	{
		List<OrderDetails> list = null;
		Hql hql = new Hql(
		    "from OrderDetails where orderDetailsNo=? and partner=?");
		hql.addParamter(orderDetailsNo);
		hql.addParamter(partnerId);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}
	
	/**
	 * 根据参数查询是否存在这条交易
	 */
	public OrderDetails queryOrderDetailByorderDetailsNo(String orderDetailsNo,
		    String partnerId) throws AppException
		{
			OrderDetails order = null;
			Hql hql = new Hql(
			    "from OrderDetails where orderDetailsNo=? and partner=?");
			hql.addParamter(orderDetailsNo);
			hql.addParamter(partnerId);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				order = (OrderDetails)query.list().get(1);
			}
			return order;
		}
	
	/**
	 * 根据订单编号查询是交易流水账
	 */
	public Transaction queryTransactionByOrderid(long orderId) throws AppException
		{
			Transaction transaction = null;
			if (orderId > 0)
			{
				Hql hql = new Hql(
				    "from Transaction t where orderDetails.id=?");
				hql.addParamter(orderId);
				Query query = this.getQuery(hql);
				if (query != null && query.list() != null && query.list().size() > 0)
				{
					transaction = (Transaction) query.list().get(0);
				}
			}
			return transaction;
		}
	
	public OrderDetails _queryOrderDetailByTransNo(String orderDetailsNo,
	    String partnerId, Long status) throws AppException
	{
		Hql hql = new Hql("from OrderDetails where orderDetailsNo=? and partner=? and status=?");
		hql.addParamter(orderDetailsNo);
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

	public OrderDetails queryOrderDetailByTransNo(String outTradeNo,
	    String partnerId, Long status) throws AppException
	{
		Hql hql = new Hql(
		    "from OrderDetails where orderNo=? and partner=? and status=?");
		hql.addParamter(outTradeNo);
		hql.addParamter(partnerId);
		hql.addParamter(status);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.uniqueResult();
		}
		return order;
	}
	
	public Transaction queryTransactionByOrderAndFromAgent(long orderId,
	    long fromAgentId) throws AppException
	{
		Transaction transaction = null;
		if (orderId > 0)
		{
			Hql hql = new Hql(
			    "from Transaction t where orderDetails.id=? and toAgent.id=? and status=? and type in(?,?) and rownum<=1 order by id");
			hql.addParamter(orderId);
			hql.addParamter(fromAgentId);
			hql.addParamter(Transaction.STATUS_3);
			hql.addParamter(Transaction.TYPE_4);
			hql.addParamter(Transaction.TYPE_80);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				transaction = (Transaction) query.list().get(0);
			}
		}
		return transaction;
	}

	public void closeTransaction(String orderDetailsNo, String partner)
	    throws AppException
	{
		Hql hql = new Hql(
		    "from Transaction where orderDetails.orderDetailsNo=? and orderDetails.partner=? and orderDetails.status=?");
		// hql.addParamter(Transaction.STATUS_4);
		hql.addParamter(orderDetailsNo);
		hql.addParamter(partner);
		hql.addParamter(Transaction.STATUS_11);

		Query query = this.getQuery(hql);
		List list = query.list();
		if (query != null)
		{
			List trans = query.list();
			if (trans != null && trans.size() > 0)
			{
				for (int i = 0; i < trans.size(); i++)
					((Transaction) trans.get(i)).setStatus(Transaction.STATUS_4);
			}
		}

		// query.executeUpdate();
	}

	// 20090507-001-jason-e-for-cooperate

	public BigDecimal getAmountSum(Agent agent) throws AppException
	{
		BigDecimal amountSum = new BigDecimal(0.00);
		Hql hql = new Hql(
		    "select sum(t.amount) from Transaction t where t.fromAgent.id=? and to_char(t.accountDate,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd')");
		hql.addParamter(String.valueOf(agent.getId()));
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			if (query.list().get(0) != null)
			{
				amountSum = ((BigDecimal) query.list().get(0));
			}
		}
		return amountSum;
	}

	public long getStatusById(long transactionId) throws AppException
	{
		long statusId = 0;
		Hql hql = new Hql("from Transaction where id=?");
		hql.addParamter(transactionId);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			if (query.list().get(0) != null)
			{
				statusId = ((Transaction) query.list().get(0)).getStatus();
			}
		}
		return statusId;
	}

	public void executeClose(Transaction transaction) throws AppException
	{
		this.getHibernateTemplate().update(transaction);
	}

	public List getNeedCloseTransactions(int type) throws AppException
	{
		List list = null;
		String str = "";
		if (type == 1)
			str = "from Transaction where status in(1,2)";
		else if (type == 2)
			str = "from Transaction where (sysdate-refundsDate)*24>=7*24 and status in(10)";
		else if (type == 3)
			str = "from Transaction where status =6";
		Hql hql = new Hql(str);
		// Hql hql = new Hql("from Transaction where (sysdate-accountDate)*24>=0.1
		// and status in(1,2)");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}

	public List getTransactionByOrderNo(String orderNo) throws AppException
	{
		List list = null;
		Hql hql = new Hql();
		hql.add("from Transaction t where t.orderDetails.orderNo=?");
		hql.addParamter(orderNo);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}

	public BigDecimal getCreditAmount(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getTransactionByOrderDetailsNo(String orderDetailsNo)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public long addDebit(Debit debit) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getDebitList(Agent agent, TransactionListForm transactionListForm) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public long addDebitLog(DebitLog debitLog) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getDebitAndExpenseList(TransactionListForm transactionListForm) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public Debit getDebitByDebitNo(String debitNo) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public long addExpense(Expense expense) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long addExpenseLog(ExpenseLog expenseLog) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getExpenseList(Agent agent, TransactionListForm transactionListForm) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public Transaction getTransactionByAgent(Agent agetn) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getHasRepaymentTotalAmount(String orderNo) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public Transaction getTransByOrderDetailsNo(String orderDetailsNo) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
	public Transaction queryTransactionByOrderAndFromAgentAndType(long orderId,
		    long fromAgentId) throws AppException{
		return null;
	}

	public long addTransactionBalance(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getTransactionBalanceByAgent(TransactionListForm tlf)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllTransactionBalanceByAgent(TransactionListForm tlf)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTransactionBalanceRow(TransactionListForm tlf)
			throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object[] statFromAgentTransaction(TransactionListForm tlf)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] statToAgentTransaction(TransactionListForm tlf)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * 信用支付退款调用
	 */
	public BigDecimal getDirectRefundTotalAmount(String orderDetailsNo,
		    long fromAgentId, long toAgentId, long status) throws AppException{
		return null;
	}
	/*
	 * 信用支付退款调用
	 */
	public double getDirectRefundAmount(String orderDetailsNo, long fromAgentId,
		    long toAgentId) throws AppException{
		return 0;
	}
	/*
	 * 信用支付（外买）退款调用
	 */
	public double getDirectTransactionAmountOut(String orderDetailsNo, long fromAgentId,
		    long toAgentId, long orderStatus,long tranType) throws AppException{
		return 0;
	}
	
	public Object[] statToAgentTransaction(TransactionListForm tlf, int type)
      throws AppException
  {
	  // TODO Auto-generated method stub
	  return null;
  }

	public long getNotCompletedTransactionArticle(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Transaction getTransByOrderNo(String orderNo) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getCreditPayAmount(Agent formAgent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getCreditRepaymentAmount(Agent formAgent)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCreditListByAgent(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCreditGiverListByAgent(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCreditPayListByAgent(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getCreditRepaymentList(Agent formAgent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getCreditArrearage(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getCreditPaymentArrearage(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getCreditRePaymentAmount(Agent agent) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public Transaction getCreditGiverTransactionByAgent(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public OrderDetails queryOrderDetailByorderDetailsNoandType(String orderDetailsNo,
		    String partnerId) throws AppException{
		return null;
	}
	
	
	/**
	 * 根据订单Id 查出交易信息
	 * @param orderId
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
				hql.addParamter(Transaction.TYPE_4);
				hql.addParamter(Transaction.TYPE_80);
				hql.addParamter(Transaction.TYPE_180);
				Query query = this.getQuery(hql);
				if (query != null && query.list() != null && query.list().size() > 0)
				{
					transaction = (Transaction) query.list().get(0);
				}
			}
			return transaction;
		}
	
	/**
	 * 根据订单Id 查出交易信息的转入人ID
	 * @param orderId
	 * @return
	 */
	public List queryTransactionByOrderAndFromAgentId(long orderId) throws AppException
	{
		List list=null;
		if (orderId > 0)
		{
			Hql hql = new Hql(
			    "from Transaction t where orderDetails.id=? and status=? and type in(?,?,?) order by id desc");
			hql.addParamter(orderId);
			hql.addParamter(Transaction.STATUS_3);
			hql.addParamter(Transaction.TYPE_4);
			hql.addParamter(Transaction.TYPE_80);
			hql.addParamter(Transaction.TYPE_180);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				list = query.list();
			}
		}
		return list;
	}
	
	public OrderDetails queryOrderDetailByOrderNoAndPartner(String orderNo, String partnerId)
    throws AppException
	{
		Hql hql = new Hql("from OrderDetails where orderNo=? and partner=?");
		hql.addParamter(orderNo);
		hql.addParamter(partnerId);
		Query query = this.getQuery(hql);
		OrderDetails order = null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails) query.list().get(0);
		}
		return order;
	}
	
	public List queryTransactionByOrder(long orderId) throws AppException{
	List list=null;
		//Transaction transaction = null;
		if (orderId > 0)
		{
			Hql hql = new Hql(
			    "select t.fromAgent.id,t.toAgent.id from Transaction t where orderDetails.id=? and status=? and type in(?,?,?) and rownum<=1 order by id");
			hql.addParamter(orderId);
			hql.addParamter(Transaction.STATUS_3);
			hql.addParamter(Transaction.TYPE_4);
			hql.addParamter(Transaction.TYPE_80);
			hql.addParamter(Transaction.TYPE_180);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				list = query.list();
			}
		}
		return list;
	}
	
	/**
	 * 根据订单Id 查出付款人到卖家交易信息
	 * @param orderId
	 * @return
	 * @throws AppException
	 */
	public Transaction queryTransactionByOrderAndPayFee(long orderId) throws AppException
	{
		Transaction transaction = null;
		if (orderId > 0)
		{
			Hql hql = new Hql(
			    "from Transaction t where orderDetails.id=? and status=? and type in(?,?,?) and rownum<=1 order by id desc");
			hql.addParamter(orderId);
			hql.addParamter(Transaction.STATUS_3);
			hql.addParamter(Transaction.TYPE_4);
			hql.addParamter(Transaction.TYPE_80);
			hql.addParamter(Transaction.TYPE_180);
			Query query = this.getQuery(hql);
			if (query != null && query.list() != null && query.list().size() > 0)
			{
				transaction = (Transaction) query.list().get(0);
			}
		}
		return transaction;
	}
	//查询退款信息
	public BigDecimal getCreditRePayListByAgent(Agent  agent,String orderDeNo) throws AppException{
		return null;
	}
	public BigDecimal getFreeAmount(String orderDetailsNo,
		    long AgentId, long status,long type) throws AppException{
		return null;
	}

	public long save(Transaction transaction) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}


	public QueryOrderResult getQueryOrderResult(String out_trade_no,
			String partnerId) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getRedPacketList(TransactionListForm transactionListForm)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	public long getNotCompletedTransactionRedPacketArticle(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Transaction getNotCompletedTransactionRedPacket(Agent agent)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
	/*********退款 委托冻结  解冻会用到的方法***********/
	//查找金额
	public BigDecimal getAlreadyAmount(List freezeOrders,
			long fromAgentId,long toAgentId,long status, long type) throws AppException{
		
		BigDecimal refundBalance = new BigDecimal(0);
		String sql = "select SUM(t.amount) from Transaction t where t.fromAgent.id=? and t.toAgent.id=?  and t.status=? and t.type=?";
	
		String param = "";
		if(freezeOrders!=null){
			for(int i=0;i<freezeOrders.size();i++){
				OrderDetails o = (OrderDetails)freezeOrders.get(i);
				if(o.getTransactions().size()>0){
					param = param+o.getId();
					param = param+",";
				}
			}
			//去掉最后一个,号
			param = param.substring(0, param.lastIndexOf(','));
			System.out.println("已冻结的所有交易id："+param);
		}
		sql = sql + " and t.orderDetails.id in ("+param+")";
		System.out.println(sql);
		Hql hql = new Hql(sql);
		hql.addParamter(fromAgentId);
		hql.addParamter(toAgentId);
		hql.addParamter(status);
		hql.addParamter(type);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			if (query.list().get(0) != null) {
				refundBalance = (BigDecimal) query.list().get(0);
			}
		}
		return refundBalance;
		
	}
	
	//查找金额
	public BigDecimal getAlreadyAmount(List freezeOrders,
			long toAgentId,long status, long type) throws AppException{
		
		BigDecimal refundBalance = new BigDecimal(0);
		String sql = "select SUM(t.amount) from Transaction t where t.toAgent.id=?  and t.status=? and t.type=?";
	
		String param = "";
		if(freezeOrders!=null){
			for(int i=0;i<freezeOrders.size();i++){
				OrderDetails o = (OrderDetails)freezeOrders.get(i);
				if(o.getTransactions().size()>0){
					param = param+o.getId();
					param = param+",";
				}
			}
		}
		if("".equals(param)){
			return refundBalance;
		}
		//去掉最后一个,号
		param = param.substring(0, param.lastIndexOf(','));
		System.out.println("所有交易id："+param);
		sql = sql + " and t.orderDetails.id in ("+param+")";
		System.out.println(sql);
		Hql hql = new Hql(sql);
		hql.addParamter(toAgentId);
		hql.addParamter(status);
		hql.addParamter(type);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			if (query.list().get(0) != null) {
				refundBalance = (BigDecimal) query.list().get(0);
			}
		}
		return refundBalance;
		
	}
	
	
	//根据外部订单号查找订单
	public OrderDetails queryOrderLikeOrderNo(String OrderNo)throws AppException
	{
		Hql hql = new Hql("from OrderDetails o where o.orderNo like ?");
		hql.addParamter(OrderNo+"~%");
		Query query = this.getQuery(hql);
		OrderDetails order=null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			order = (OrderDetails)query.uniqueResult();
		}
		return order;
	}
	
	//查找和原支付交易有关的冻结订单
	public List queryOrderLikeOrderDetailsNo(String OrderDetailsNo)throws AppException
	{
		Hql hql = new Hql("from OrderDetails o where o.orderNo like ?");
		hql.addParamter("%~"+OrderDetailsNo);
		Query query = this.getQuery(hql);
		List list=null;
		if (query != null && query.list() != null && query.list().size() > 0)
		{
			list = query.list();
		}
		return list;
	}

}
