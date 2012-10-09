package com.fordays.fdpay.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fordays.fdpay.agent.Agent;

public class TempTransaction
{
	private static final long serialVersionUID = 1L;
	private OrderDetails orderDetails;
	private long id = 0;
	private Date accountDate;
	private String transactionNo = "";
	private Agent fromAgent;
	private Agent toAgent;
	private BigDecimal amount;
	private BigDecimal inaccount;
	private BigDecimal outaccount;
	private long type = 0;
	private String mark;
	private String typeCatption;
	private Long status;
	private int flag;
	protected List operate;
	protected com.fordays.fdpay.agent.Agent transactioner;
	protected com.fordays.fdpay.agent.Agent reverseTransactioner;
	protected BigDecimal Sum;
	protected long Count=0;

	
	
	

	public TempTransaction(BigDecimal sum, long count) {
		super();
		Sum = sum;
		Count = count;
	}

	public BigDecimal getSum() {
		if(this.Sum==null){
			return new BigDecimal(0);
		}
		return Sum;
	}

	public void setSum(BigDecimal sum) {
		Sum = sum;
	}

	public long getCount() {
		return Count;
	}

	public void setCount(long count) {
		Count = count;
	}

	public Long getStatus()
	{
		return status;
	}

	public void setStatus(Long status)
	{
		this.status = status;
	}

	public String getTypeCatption()
	{
		if (this.type == Transaction.TYPE_20||this.type == Transaction.TYPE_22)
		{
			return "充值";
		} else if (this.type == Transaction.TYPE_30)
			return "提现转账";
		else if (this.type == Transaction.TYPE_40)
			return "申请提现";
		else
			return "在线支付";
	}
	public String getActionCaption(){
		if (transactioner.getId() == this.getFromAgent().getId()){
			return "买入";
		}
		else if (transactioner.getId() == this.getToAgent().getId()){
			return "卖出";
		}
		else
			return "";
	}
	public String getTypeCaptionStat()
	{
		if (this.getType() == Transaction.TYPE_1)
			return "即时到帐付款";
		else if (this.getType() == Transaction.TYPE_2)
			return "担保交易付款";
		else if (this.getType() == Transaction.TYPE_3)
			return "担保交易收款";
		else if (this.getType() == Transaction.TYPE_4)
			return "即时到帐收款";
		else if (this.getType() == Transaction.TYPE_5)
			return "批量收款";
		else if (this.getType() == Transaction.TYPE_6)
			return "批量付款";
		else if (this.getType() == Transaction.TYPE_20)
			return "网银充值";
		else if (this.getType() == Transaction.TYPE_22)
			return "线下充值";
		else if (this.getType() == Transaction.TYPE_23)
			return "信用金额线下充值";
		else if (this.getType() == Transaction.TYPE_30)
			return "提现转账";
		else if (this.getType() == Transaction.TYPE_40)
			return "申请提现";
		else if (this.getType() == Transaction.TYPE_44)
			return "线下扣款";
		else if (this.getType() == Transaction.TYPE_80)
			return "线上支付";//接口支付
		else if (this.getType() == Transaction.TYPE_81)
			return "线上退款"; //接口支付的接口退款
		else if (this.getType() == Transaction.TYPE_82)
			return "线下退款";//接口支付的手工退款
		else if(this.getType()==Transaction.TYPE_70)
				return "实名认证提现";//return "70"; //
		else if(this.getType()==Transaction.TYPE_9)
			return "借款";//return "9"; //
		else if(this.getType()==Transaction.TYPE_91)
			return "还款";//return "91"; //
		else
			return "即时到账退款";
	}
	
	public TempTransaction(OrderDetails orderDetails, long id,
			Date accountDate, String transactionNo, Agent fromAgent,
			Agent toAgent, BigDecimal amount, BigDecimal inaccount,
			BigDecimal outaccount, long type, String mark,Long status)
	{
		super();
		this.orderDetails = orderDetails;
		this.id = id;
		this.accountDate = accountDate;
		this.transactionNo = transactionNo;
		this.fromAgent = fromAgent;
		this.toAgent = toAgent;
		this.amount = amount;
		this.inaccount = inaccount;
		this.outaccount = outaccount;
		this.type = type;
		this.mark = mark;
		this.status=status;
	}

	
	
	public OrderDetails getOrderDetails()
	{
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails)
	{
		this.orderDetails = orderDetails;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Date getAccountDate()
	{
		return accountDate;
	}

	public void setAccountDate(Date accountDate)
	{
		this.accountDate = accountDate;
	}

	public String getTransactionNo()
	{
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo)
	{
		this.transactionNo = transactionNo;
	}

	public Agent getFromAgent()
	{
		return fromAgent;
	}

	public void setFromAgent(Agent fromAgent)
	{
		this.fromAgent = fromAgent;
	}

	public Agent getToAgent()
	{
		return toAgent;
	}

	public void setToAgent(Agent toAgent)
	{
		this.toAgent = toAgent;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public BigDecimal getInaccount()
	{
		return inaccount;
	}

	public void setInaccount(BigDecimal inaccount)
	{
		this.inaccount = inaccount;
	}

	public BigDecimal getOutaccount()
	{
		return outaccount;
	}

	public void setOutaccount(BigDecimal outaccount)
	{
		this.outaccount = outaccount;
	}

	public BigDecimal getCurrentBalance()
	{
		if (inaccount == null)
		{
			if (outaccount == null)
				return new BigDecimal("0").setScale(2);
			else
				return outaccount.multiply(new BigDecimal("-1"));
		} else
		{
			if (outaccount == null)
				return inaccount;
			else
				return inaccount.subtract(outaccount);

		}

	}

	public long getType()
	{
		return type;
	}

	public void setType(long type)
	{
		this.type = type;
	}

	public String getMark()
	{
		return mark;
	}

	public void setMark(String mark)
	{
		this.mark = mark;
	}
	
	public List getOperate(){
		List<Operate> list = new ArrayList<Operate>();
		Operate op = new Operate();
		String url = "";
		String url2= "";
		String url3= "";
		
		url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
		op.setUrl(url);
		op.setUrlName("查看");
		list.add(op);
		
//		if(this.getType()==Transaction.TYPE_1 || this.getType()==Transaction.TYPE_6 || this.getType() == Transaction.TYPE_30 || this.getType()==Transaction.TYPE_81 ||this.getType()==Transaction.TYPE_82 || this.getType()==Transaction.TYPE_70){  //即时到账付款,包括向亲朋好友和向陌生人付款,还有即时退款的
//			url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//			op.setUrl(url);
//			op.setUrlName("查看");
//			list.add(op);
//		}
//		else if(this.getType()==Transaction.TYPE_2){
//			
//		}
//		else if(this.getType()==Transaction.TYPE_3){ //担保交易收款
//			if(this.getFlag()==0){ //接收人
//				if(this.getStatus().intValue()==Transaction.STATUS_1){  //还没付款
//					url = "javascript:void(0)";
//					url2 = "javascript:transactionPayment('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"')";
//					url3 = "javascript:getTransactionDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("关闭交易");
//					op.setTitle("拍下以后7天不付款,交易会自动关闭;或者联系卖家关闭此交易");
//					op.setStyle("cursor:help");
//					op.setUrl2(url2);
//					op.setUrlName2("付款");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_2){ //担保交易付款后,状态变成等待卖家发货
////					Date date = new Date();
////					long day = (date.getTime()-this.payDate.getTime())/(3600L*1000*24);
////					if(day>=1){
////						url = "javascript:refundMent('"+this.getId()+"','"+this.getOrderDetails().getId()+"',0)";
////					}
////					else{
////						url = "javascript:void(0)";
////						op.setTitle("付款24小时后才能退款");
////						op.setStyle("cursor:help");
////					}
////					url2 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
////					op.setUrl(url);
////					op.setUrlName("退款");
////					op.setUrl2(url2);
////					op.setUrlName2("查看");
////					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_3){ //买家确认收货
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_4){ //关闭交易
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_6){ //卖家发货已经发货,里面的退款还要做判断,这里如果卖家已经发货了,就暂时屏蔽退款的按钮,留到以后业务需要的时候再做
//					url = "javascript:refundMent('"+this.getId()+"','"+this.getOrderDetails().getId()+"',0)";
//					url2 = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					url3 = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',2)";
//					op.setUrl(url);
//					op.setUrlName("退款");
//					op.setUrl2(url2);
//					op.setUrlName2("确认收货");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}	
////				else if(this.getStatus().intValue()==Transaction.STATUS_7){ //卖家已经发货了,但买家退款,并且选择已经收到货,但不用退货的情况
////					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
////					url2 = "javascript:getRefundMentDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"',0,0)";
////					url3 = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',2)";
////					op.setUrl(url);
////					op.setUrlName("确认收货");
////					op.setUrl2(url2);
////					op.setUrlName2("查看退款详情");
////					op.setUrl3(url3);
////					op.setUrlName3("查看");
////					list.add(op);
////				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_10){ //买家在卖家未发货之前退款
//					//url = "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')"; 
//					url = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					//op.setUrl(url);
//					//op.setUrlName("查看退款详情");
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_11){ //卖家同意退款,退款成功
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//			}
//			else{  //发起人
//				if(this.getStatus().intValue()==Transaction.STATUS_1){  //还没付款
//					url = "javascript:closeTransaction('"+this.getId()+"','"+this.getOrderDetails().getId()+"')";
//					url2 = "javascript:modifyTransactionPrice('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getType()+"')";
//					url3 = "javascript:getTransactionDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("关闭交易");
//					op.setUrl2(url2);
//					op.setUrlName2("修改交易价格");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_2){ //担保交易付款后,状态变成等待卖家发货
//					url = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					url2 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					op.setUrl(url);
//					op.setUrlName("发货并确认");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_3){ //买家确认收货
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_4){ //关闭交易
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_6){ //卖家发货已经发货,支付宝网址是有个叫延长超时时间的,现在暂时不做,只有查看操作
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				
////				else if(this.getStatus().intValue()==Transaction.STATUS_7){ //卖家已经发货了,但买家退款,并且选择已经收到货,但不用退货的情况
////					url = "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')"; 
////					url2 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
////					op.setUrl(url);
////					op.setUrlName("查看退款详情");
////					op.setUrl2(url2);
////					op.setUrlName2("查看");
////					list.add(op);
////				}
//				
//				else if(this.getStatus().intValue()==Transaction.STATUS_10){ //买家在卖家未发货之前退款
//					url = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					url2 = "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')"; 
//					url3 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					op.setUrl(url);
//					op.setUrlName("发货并确认");
//					op.setUrl2(url2);
//					op.setUrlName2("查看退款详情");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_11){ //卖家同意退款,退款成功
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//			}
//		}
//		else if(this.getType()==Transaction.TYPE_4 || this.getType()==Transaction.TYPE_5){  //即时到账收款和AA批量收款
//			if(this.getFlag()==0){  //接收人
//				if(this.getStatus().intValue()==Transaction.STATUS_3){ //表示已经付款,等待卖家发货,这2种情况都不能退款
//					url = "javascript:transactionDetailByDunAndBatch('"+this.getId()+"','"+Transaction.STATUS_3+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_4){
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else{
//					url = "javascript:transactionPayment('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"')";
//					url2 = "javascript:transactionDetailByDunAndBatch('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("付款");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}
//			}
//			else{  //发起人
//				if(this.getStatus().intValue()==Transaction.STATUS_4){
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else{
//					url = "javascript:transactionDetailByDunAndBatch('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//			}
//		}
//		else if(this.getType()==Transaction.TYPE_20||this.getType()==Transaction.TYPE_22){ //充值
//			url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//			op.setUrl(url);
//			op.setUrlName("查看");
//			list.add(op);
//		}
//		else if (this.getType() == Transaction.TYPE_80)
//		{ // 接口支付
//			if (this.getFlag() == 0)
//			{ // 接收人
//				url = "javascript:getTransactionSuccessAndFailDetail('"
//				    + this.getId() + "','" + this.getStatus() + "','"
//				    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
//				    + this.getFromAgent().getId() + "','" + this.getType() + "')";
//				op.setUrl(url);
//				op.setUrlName("查看");
//				list.add(op);
//			}
//			else
//			{
//				if (this.getStatus().intValue() == Transaction.STATUS_3)
//				{
//					//url = "javascript:offLineRefund('" + this.getId() + "','"
//					//    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
//					url = "javascript:transactionDetailByDunAndBatch('" + this.getId()
//					    + "','" + this.getStatus() + "','"
//					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
//					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
//					//op.setUrl(url);
//					//op.setUrlName("线下退款");
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if (this.getStatus().intValue() == Transaction.STATUS_4){
//					url = "javascript:getTransactionSuccessAndFailDetail('"
//					    + this.getId() + "','" + this.getStatus() + "','"
//					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
//					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//			}
//		}
//		else if (this.getType() == Transaction.TYPE_9){
//			if (this.getOrderDetails().getStatus()==OrderDetails.STATUS_6 || this.getOrderDetails().getStatus()==OrderDetails.STATUS_7){ 
//					//if (this.getFlag() == 0){
//						url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
//						    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
//						    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
//						    + "','" + this.getType() + "')";
//						op.setUrl(url);
//						op.setUrlName("查看");
//						list.add(op);
////					}
////					else{
////						url ="javascript:transactionRepayment('"+this.getId()+"')";
////						url2 = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
////					    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
////					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
////					    + "','" + this.getType() + "')";
////						op.setUrl(url);
////						op.setUrlName("还款");
////						op.setUrl2(url2);
////						op.setUrlName2("查看");
////						list.add(op);
////					}
//			}
//			else if(this.getOrderDetails().getStatus()==OrderDetails.STATUS_8){
//				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
//			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
//			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
//			    + "','" + this.getType() + "')";
//				op.setUrl(url);
//				op.setUrlName("查看");
//				list.add(op);
//			}
//		}
//		else if (this.getType() == Transaction.TYPE_91){
//			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
//		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
//		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
//		    + "','" + this.getType() + "')";
//			op.setUrl(url);
//			op.setUrlName("查看");
//			list.add(op);
//		}
		return list;
	}

//	public List getOperate(){
//		List list = new ArrayList();
//		Operate op = new Operate();
//		String url = "";
//		String url2= "";
//		String url3= "";
//		
//		
//		if(this.getType()==Transaction.TYPE_1 || this.getType()==Transaction.TYPE_6 || this.getType()==Transaction.TYPE_0){  //即时到账付款,包括向亲朋好友和向陌生人付款
//			url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//			op.setUrl(url);
//			op.setUrlName("查看");
//			list.add(op);
//		}
//		else if(this.getType()==Transaction.TYPE_2){
//			
//		}
//		else if(this.getType()==Transaction.TYPE_3){ //担保交易收款
//			if(this.getFlag()==0){ //接收人
//				if(this.getStatus().intValue()==Transaction.STATUS_1){  //还没付款
//					url = "#";
//					url2 = "javascript:transactionPayment('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"')";
//					url3 = "javascript:getTransactionDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("关闭交易");
//					op.setTitle("拍下以后7天不付款,交易会自动关闭;或者联系卖家关闭此交易");
//					op.setStyle("cursor:help");
//					op.setUrl2(url2);
//					op.setUrlName2("付款");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_2){ //担保交易付款后,状态变成等待卖家发货
//					url = "javascript:refundMent('"+this.getId()+"','"+this.getOrderDetails().getId()+"',0)";
//					url2 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("退款");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_3){ //买家确认收货
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_4){ //关闭交易
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_6){ //卖家发货已经发货,里面的退款还要做判断,这里如果卖家已经发货了,就暂时屏蔽退款的按钮,留到以后业务需要的时候再做
//					//url = "javascript:refundMent('"+this.getId()+"','"+this.getOrderDetails().getId()+"',1)";
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					url2 = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',2)";
//					//op.setUrl(url);
//					//op.setUrlName("退款");
//					op.setUrl(url);
//					op.setUrlName("确认收货");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}	
//				else if(this.getStatus().intValue()==Transaction.STATUS_10){ //买家在卖家未发货之前退款
//					url = "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')"; 
//					url2 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					op.setUrl(url);
//					op.setUrlName("查看退款详情");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_11){ //卖家同意退款,退款成功
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//			}
//			else{  //发起人
//				if(this.getStatus().intValue()==Transaction.STATUS_1){  //还没付款
//					url = "javascript:closeTransaction('"+this.getId()+"','"+this.getOrderDetails().getId()+"')";
//					url2 = "javascript:modifyTransactionPrice('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getType()+"')";
//					url3 = "javascript:getTransactionDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("关闭交易");
//					op.setUrl2(url2);
//					op.setUrlName2("修改交易价格");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_2){ //担保交易付款后,状态变成等待卖家发货
//					url = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					url2 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					op.setUrl(url);
//					op.setUrlName("发货并确认");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_3){ //买家确认收货
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_4){ //关闭交易
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_6){ //卖家发货已经发货,支付宝网址是有个叫延长超时时间的,现在暂时不做,只有查看操作
//					url = "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				
//				else if(this.getStatus().intValue()==Transaction.STATUS_10){ //买家在卖家未发货之前退款
//					url = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',0)";
//					url2 = "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')"; 
//					url3 = "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
//					op.setUrl(url);
//					op.setUrlName("发货并确认");
//					op.setUrl2(url2);
//					op.setUrlName2("查看退款详情");
//					op.setUrl3(url3);
//					op.setUrlName3("查看");
//					list.add(op);
//				}
//				else if(this.getStatus().intValue()==Transaction.STATUS_11){ //卖家同意退款,退款成功
//					url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//			}
//		}
//		else if(this.getType()==Transaction.TYPE_4 || this.getType()==Transaction.TYPE_5){  //即时到账收款和AA批量收款
//			if(this.getFlag()==0){  //接收人
//				if(this.getStatus().intValue()==Transaction.STATUS_3){ //表示已经付款,等待卖家发货,这2种情况都不能退款
//					url = "javascript:transactionDetailByDunAndBatch('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("查看");
//					list.add(op);
//				}
//				else{
//					url = "javascript:transactionPayment('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"')";
//					url2 = "javascript:transactionDetailByDunAndBatch('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//					op.setUrl(url);
//					op.setUrlName("付款");
//					op.setUrl2(url2);
//					op.setUrlName2("查看");
//					list.add(op);
//				}
//			}
//			else{  //发起人
//				url = "javascript:transactionDetailByDunAndBatch('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//				op.setUrl(url);
//				op.setUrlName("查看");
//				list.add(op);
//			}
//		}
//		else if(this.getType()==Transaction.TYPE_20){ //充值
//			url = "javascript:getTransactionSuccessAndFailDetail('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"','"+this.getType()+"')";
//			op.setUrl(url);
//			op.setUrlName("查看");
//			list.add(op);
//		}
//		return list;
//	}
	public com.fordays.fdpay.agent.Agent getTransactioner(){
		return transactioner;
	}
	public void setTransactioner(com.fordays.fdpay.agent.Agent transactioner){
		this.transactioner = transactioner;
		if (transactioner.getId() == this.getFromAgent().getId()){
			this.flag = 0;
			reverseTransactioner=this.getToAgent();
		}
		else if (transactioner.getId() == this.getToAgent().getId()){
			this.flag = 1;
			reverseTransactioner=this.getFromAgent();
		}
	}
	public com.fordays.fdpay.agent.Agent getReverseTransactioner(){
		return reverseTransactioner;
	}
	public String getTypeCaption(){
		if(this.getType()==Transaction.TYPE_1)
			return "即时到帐付款";	//return "1"; 
		else if(this.getType()==Transaction.TYPE_2)
			return "担保交易付款";//return "2"; //
		else if(this.getType()==Transaction.TYPE_3)
			return "担保交易收款";//return "3"; //
		else if(this.getType()==Transaction.TYPE_4)
			return "即时到帐收款";//return "4"; //
		else if(this.getType()==Transaction.TYPE_5)
			return "批量收款";//return "5"; //
		else if(this.getType()==Transaction.TYPE_6)
			return "批量付款";//return "6"; //
		else if(this.getType()==Transaction.TYPE_20||this.getType()==Transaction.TYPE_22)
			return "充值";//return "20"; //
		else if(this.getType()==Transaction.TYPE_30)
			return "提现";//return "30"; //
		else if(this.getType()==Transaction.TYPE_40)
			return "转账";//return "40"; //
		else if(this.getType()==Transaction.TYPE_70)
			return "实名认证提现";//return "70"; //
		else
			return "即时到账退款";//return "0"; //
	}
	public String getTransactionStatus(){
		if(this.getStatus().intValue()==Transaction.STATUS_1)
			return "等待买家付款";
		else if(this.getStatus().intValue()==Transaction.STATUS_2)
			return "买家已付款，等待卖家发货";
		else if(this.getStatus().intValue()==Transaction.STATUS_3)
			return "交易成功";
		else if(this.getStatus().intValue()==Transaction.STATUS_4)
			return "关闭交易";
		else if(this.getStatus().intValue()==Transaction.STATUS_5)
			return "交易失败";
		else if(this.getStatus().intValue()==Transaction.STATUS_6)
			return "卖家已发货,等待买家确认";
		else if(this.getStatus().intValue()==Transaction.STATUS_10)
			return "退款协议等待卖家确认中";
		//else if(this.getStatus().intValue()==Transaction.STATUS_10 || this.getStatus().intValue()==Transaction.STATUS_7)
		//	return "退款协议等待卖家确认中";
		else if(this.getStatus().intValue()==Transaction.STATUS_11)
			return "关闭交易"; //这里的退款成功在交易管理页面显示关闭交易
		else
			return "";
	}
	public int getFlag(){
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
	}

	public void setOperate(List operate)
	{
		this.operate = operate;
	}



	

}
