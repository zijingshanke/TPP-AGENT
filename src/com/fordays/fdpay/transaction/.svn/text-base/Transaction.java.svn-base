package com.fordays.fdpay.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction._entity._Transaction;

public class Transaction extends _Transaction
{

	public static final long TYPE_1 = 1;// 即时到帐付款

	public static final long TYPE_2 = 2;// 担保交易付款

	public static final long TYPE_3 = 3;// 担保交易收款

	public static final long TYPE_4 = 4;// 即时到帐收款

	public static final long TYPE_5 = 5;// 批量收款

	public static final long TYPE_6 = 6;// 批量付款
	
	public static final long TYPE_7 = 7;// 发红包

	public static final long TYPE_20 = 20;// 网银充值
	public static final long TYPE_22 = 22;// 线下充值
	public static final long TYPE_23 = 23;// 信用金额线下充值

	public static final long TYPE_30 = 30;// 提现转账

	public static final long TYPE_40 = 40;// 申请提现
	public static final long TYPE_41 = 41;// 撤销提现 
	
	public static final long TYPE_44 = 44;// 线下扣款
	public static final long TYPE_69 = 69;// 实名申请提现
	public static final long TYPE_70 = 70;// 实名认证提现
	
	public static final long TYPE_99 = 99;// 冻结到可用
	public static final long TYPE_100 = 100;//  可用到冻结
	public static final long TYPE_97 = 97;//  系统解冻
	public static final long TYPE_98 = 98;//  系统冻结
	public static final long TYPE_197 = 197;//  委托解冻
	public static final long TYPE_198 = 198;//  委托冻结

	public static final long TYPE_80 = 80;// 接口支付 ,加多个从其他系统支付成功的状态,目的为了在页面添加个退款按钮
	public static final long TYPE_81 = 81;// 接口支付，接口退款
	public static final long TYPE_82 = 82;// 接口支付，前台手工退款
	
	public static final long TYPE_180 = 180;// 信用支付
	public static final long TYPE_181 = 181;// 信用支付，接口退款
	public static final long TYPE_182 = 182;// 信用支付，前台手工退款
	
	public static final long TYPE_130 = 130;// 担保支付
	public static final long TYPE_131 = 131;// 担保支付，接口退款
	public static final long TYPE_132 = 132;// 担保支付，前台手工退款
	
	public static final long TYPE_119 = 119;// 担保冻结支付解冻
	public static final long TYPE_120 = 120;// 担保冻结支付
	public static final long TYPE_121 = 121;// 担保冻结支付，接口退款
	public static final long TYPE_122 = 122;// 担保冻结支付，前台手工退款
	
	public static final long TYPE_298 = 298;// 冻结标款
	public static final long TYPE_299 = 299;// 标款解冻
	public static final long TYPE_140 = 140;// 招标冻结支付
	public static final long TYPE_141 = 141;// 招标冻结支付，接口退款
	public static final long TYPE_142 = 142;// 招标冻结支付，前台手工退款
	public static final long TYPE_297 = 297;// 招标支付解冻

	public static final long TYPE_9 = 9;// 借款
	public static final long TYPE_91 = 91;// 还款
	
	public static final long TYPE_109 = 109;// 借款(预支)
	public static final long TYPE_191 = 191;// 报销
	
	public static final long TYPE_101 = 101;// 授信支付
	public static final long TYPE_102 = 102;// 授信还款，前台手工还款
	public static final long TYPE_103 = 103;// 授信还款，接口还款
	
	public static final long TYPE_200 = 200;// 手机网银充值手续费

	public static final long STATUS_1 = 1;// 等待买家付款

	public static final long STATUS_2 = 2;// 等待卖家发货

	public static final long STATUS_3 = 3;// 交易成功

	public static final long STATUS_4 = 4;// 关闭交易

	public static final long STATUS_5 = 5;// 交易失败

	public static final long STATUS_6 = 6;// 卖家已发货,等待买家确认
	
	

	// public static final long STATUS_7=7 ;//买家收到货后退货

	public static final long STATUS_10 = 10;// 退款协议等待卖家确认中

	public static final long STATUS_11 = 11;// 退款成功

	private static final long serialVersionUID = 1L;
	protected com.fordays.fdpay.agent.Agent fromAgent;
	protected com.fordays.fdpay.agent.Agent toAgent;
	protected com.fordays.fdpay.agent.Agent transactioner;
	protected com.fordays.fdpay.agent.Agent reverseTransactioner;
	protected String actionCaption;
	protected String sellerAccount;
	protected String buyerAccount;
	protected String transactionStatus;
	protected String paytype;
	protected String transactionType;
	protected String typeCaption;
	protected int flag;
	protected java.math.BigDecimal salePrice;
	protected String agentName[];
	protected String payPrice[];
	protected BigDecimal totalPrice;
	protected String batchCaption; // 批量收款时候的行为
	protected long toAgentId;
	protected long fromAgentId;
	protected String toAgentLoginName;
	protected long tid;
	protected long orderid;
	protected String otherMsg;
	protected List operate;
	protected String closeNote;	
	protected String refundEdit;
	protected String borrowingDate;
	protected BigDecimal repaymentPrice;
	protected String repaymentContent;
	protected List tradeOperate;
	protected String remark;
	protected String note;
	protected String debitNo;
	private UserRightInfo uri;
	private BigDecimal creditRePaymentAmount;//未结算授信还款总额
	private BigDecimal creditPaymentAmount;//未结算授信欠款总额
	private String showAmountType;         //交易金额类型
	private String shopUrl;
	
	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	
	public static long getUnFreezeType(String freezeType){
		if(freezeType.equals("120")){ //担保冻结支付
			return Transaction.TYPE_119;
		}else if(freezeType.equals("140")){ // 招标冻结支付
			return Transaction.TYPE_297;
		}else if(freezeType.equals("298")){ // 冻结标款
			return Transaction.TYPE_299;  
		}else{
			return -1;
		}
	}

	public String getShowAmountType() {
		if(this.type==101)
			return "这笔授信金额为:"+this.amount+"元"+"  "+this.orderDetails.getIsRepay();
		else if(this.type==180)
			return "这笔信用支出金额为:"+this.amount+"元"+"  "+this.orderDetails.getIsRepay();
		else
			return "这笔授信还款金额为:"+this.amount+"元";	
	}

	public long getTid()
	{
		return tid;
	}

	public void setTid(long tid)
	{
		this.tid = tid;
	}

	public long getToAgentId()
	{
		return toAgentId;
	}

	public void setToAgentId(long toAgentId)
	{
		this.toAgentId = toAgentId;
	}

	public String getToAgentLoginName()
	{
		return toAgentLoginName;
	}

	public void setToAgentLoginName(String toAgentLoginName)
	{
		this.toAgentLoginName = toAgentLoginName;
	}

	public Transaction()
	{
		super.orderDetails = new com.fordays.fdpay.transaction.OrderDetails();
	}

	public String[] getPayPrice()
	{
		return payPrice;
	}

	public void setPayPrice(String[] payPrice)
	{
		this.payPrice = payPrice;
	}

	public String[] getAgentName()
	{
		return agentName;
	}

	public void setAgentName(String[] agentName)
	{
		this.agentName = agentName;
	}

	public java.math.BigDecimal getSalePrice()
	{
		return salePrice;
	}

	public void setSalePrice(java.math.BigDecimal salePrice)
	{
		this.salePrice = salePrice;
	}

	public String getPaytype()
	{
		return paytype;
	}

	public void setPaytype(String paytype)
	{
		this.paytype = paytype;
	}

	public String getSellerAccount()
	{
		return sellerAccount;
	}

	public void setSellerAccount(String sellerAccount)
	{
		this.sellerAccount = sellerAccount;
	}

	public com.fordays.fdpay.agent.Agent getFromAgent()
	{
		return fromAgent;
	}

	public void setFromAgent(com.fordays.fdpay.agent.Agent fromAgent)
	{
		this.fromAgent = fromAgent;
	}

	public com.fordays.fdpay.agent.Agent getToAgent()
	{
		return toAgent;
	}

	public void setToAgent(com.fordays.fdpay.agent.Agent toAgent)
	{
		this.toAgent = toAgent;
	}

	public com.fordays.fdpay.agent.Agent getTransactioner()
	{
		return transactioner;
	}

	public void setTransactioner(com.fordays.fdpay.agent.Agent transactioner)
	{
		this.transactioner = transactioner;
		if (transactioner.getId() == this.getFromAgent().getId())
		{
			reverseTransactioner = this.getToAgent();
		}
		else if (transactioner.getId() == this.getToAgent().getId())
		{
			reverseTransactioner = this.getFromAgent();
		}
	}

	public int getFlag()
	{
		if (transactioner.getId() == this.getFromAgent().getId())
		{
			this.flag = 0;
		}
		else if (transactioner.getId() == this.getToAgent().getId())
		{
			this.flag = 1;
		}
		return flag;
	}

	public String getActionCaption()
	{
		if (transactioner.getId() == this.getFromAgent().getId())
		{
			return "买入";
		}
		else if (transactioner.getId() == this.getToAgent().getId())
		{
			return "卖出";
		}
		else
			return "";
	}

	public String getCloseNote()
	{
		if (this.getStatus().intValue() == Transaction.STATUS_4)
			return this.closeReson;
		else if (this.getStatus().intValue() == Transaction.STATUS_11)
			return "本交易已完成退款，交易结束";
		else
			return "";
	}

	public String getBatchCaption()
	{
		if (transactioner.getId() == this.getFromAgent().getId())
		{
			return "卖出";
		}
		else if (transactioner.getId() == this.getToAgent().getId())
		{
			return "买入";
		}
		else
			return "";
	}

	public com.fordays.fdpay.agent.Agent getReverseTransactioner()
	{
		return reverseTransactioner;
	}

	public String getTypeCaption()
	{
		return Transaction.getTypeCaption(this.getType().intValue());
		
	}

	public static String getTypeCaption(int type)
	{
		if (type == Transaction.TYPE_1)
			return "即时到帐付款";
		else if (type == Transaction.TYPE_2)
			return "担保交易付款";
		else if (type == Transaction.TYPE_3)
			return "担保交易收款";
		else if (type == Transaction.TYPE_4)
			return "即时到帐收款";
		else if (type == Transaction.TYPE_5)
			return "批量收款";
		else if (type == Transaction.TYPE_6)
			return "批量付款";
		else if (type == Transaction.TYPE_7)
			return "发红包";
		else if (type == Transaction.TYPE_20)
			return "网银充值";
		else if (type == Transaction.TYPE_22)
			return "线下充值";
		else if (type == Transaction.TYPE_23)
			return "信用金额线下充值";
		else if (type == Transaction.TYPE_30)
			return "提现转账";
		else if (type == Transaction.TYPE_40)
			return "申请提现";
		else if (type == Transaction.TYPE_41)
			return "撤销提现";
		else if (type == Transaction.TYPE_44)
			return "线下扣款";
		else if (type == Transaction.TYPE_80)
			return "线上支付";//接口支付
		else if (type == Transaction.TYPE_81)
			return "线上退款"; //接口支付的接口退款
		else if (type == Transaction.TYPE_82)
			return "线下退款";//接口支付的手工退款
		else if (type == Transaction.TYPE_180)
			return "信用支付";//信用支付
		else if (type == Transaction.TYPE_181)
			return "信用线上退款";//信用线上退款
		else if (type == Transaction.TYPE_182)
			return "信用线下退款";//信用线下退款
		else if(type==Transaction.TYPE_70)
				return "实名认证提现";//return "70"; //
		else if(type==Transaction.TYPE_69)
			return "实名申请提现";//return "69"; //
		else if(type==Transaction.TYPE_9)
			return "借款";//return "9"; //
		else if(type==Transaction.TYPE_91)
			return "还款";//return "91"; //
		else if(type==Transaction.TYPE_101)
			return "授信支付";//return "101"; //
		else if(type==Transaction.TYPE_102)
			return "授信线下还款";//return "102"; 
		else if(type==Transaction.TYPE_103)
			return "授信线上还款";//return "103";
		else if(type==Transaction.TYPE_109)
			return "预支";//return "109"; //
		else if(type==Transaction.TYPE_191)
			return "报销";//return "191"; //
		else if(type==Transaction.TYPE_130)
			return "担保支付";//return "130"; //
		else if(type==Transaction.TYPE_131)
			return "担保线上退款";//return "131"; //
		else if(type==Transaction.TYPE_132)
			return "担保线下退款";//return "132"; //
		else if(type==Transaction.TYPE_200)
			return "手机网银充值手续费";//return "200"; //
		else if(type==Transaction.TYPE_100)
			return "可用到冻结";//return "100"; //
		else if(type==Transaction.TYPE_99)
			return "冻结到可用";//return "99"; //
		else if(type==Transaction.TYPE_97)
			return "系统解冻";//return "97"; //
		else if(type==Transaction.TYPE_98)
			return "系统冻结";//return "98"; //
		else if(type==Transaction.TYPE_197)
			return "委托解冻";//return "197"; //
		else if(type==Transaction.TYPE_198)
			return "委托冻结";//return "198"; //
		else if(type==Transaction.TYPE_297)
			return "招标支付解冻";//return "297"; //
		else if(type==Transaction.TYPE_298)
			return "冻结标款";//return "298"; //
		else if(type==Transaction.TYPE_299)
			return "标款解冻";//return "299"; //
		else if(type==Transaction.TYPE_140)
			return "招标冻结支付";//return "140"; //
		else if(type==Transaction.TYPE_141)
			return "招标冻结支付线上退款";//return "141"; //
		else if(type==Transaction.TYPE_142)
			return "招标冻结支付线下退款";//return "142"; //
		else if(type==Transaction.TYPE_119)
			return "担保冻结支付解冻";//return "119"; //
		else if(type==Transaction.TYPE_120)
			return "担保冻结支付";//return "120"; //
		else if(type==Transaction.TYPE_121)
			return "担保冻结支付线上退款";//return "121"; //
		else if(type==Transaction.TYPE_122)
			return "担保冻结支付线下退款";//return "122"; //
		else
			return "即时到账退款";
	}

	
	public String getTransactionStatus()
	{
		if (this.getStatus().intValue() == Transaction.STATUS_1)
			return "等待买家付款";
		else if(this.getStatus().intValue()==Transaction.STATUS_2&&this.getType().intValue()==Transaction.TYPE_7)
			return "等待卖家接受红包";
		else if (this.getStatus().intValue() == Transaction.STATUS_2)
			return "买家已付款，等待卖家发货";
		else if (this.getStatus().intValue() == Transaction.STATUS_3)
			return "交易成功";
		else if (this.getStatus().intValue() == Transaction.STATUS_4)
			return "关闭交易";
		else if (this.getStatus().intValue() == Transaction.STATUS_5)
			return "交易失败";
		else if (this.getStatus().intValue() == Transaction.STATUS_6)
			return "卖家已发货,等待买家确认";
		else if (this.getStatus().intValue() == Transaction.STATUS_10)
			return "退款协议等待卖家确认中";
		// else if(this.getStatus().intValue()==Transaction.STATUS_10 ||
		// this.getStatus().intValue()==Transaction.STATUS_7)
		// return "退款协议等待卖家确认中";
		else if (this.getStatus().intValue() == Transaction.STATUS_11)
			return "退款成功"; // 这里的退款成功在交易管理页面显示关闭交易
		else
			return "";
	}
	
	public List getTradeOperate(){
		
		List<Operate> list = new ArrayList<Operate>();
		Operate op = new Operate();
		String url = "";
		String url2 = "";
		String url3 = "";

		if (this.getType().intValue() == Transaction.TYPE_1
		    || this.getType().intValue() == Transaction.TYPE_6
		    || this.getType().intValue() == Transaction.TYPE_30
		    || this.getType().intValue() == Transaction.TYPE_44
		    || this.getType().intValue() == Transaction.TYPE_70
		    || this.getType().intValue() == Transaction.TYPE_200)
		{ // 即时到账付款,包括向亲朋好友和向陌生人付款,还有即时退款的
			url = "javascript:viewTradeTransactionSuccess('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_2)
		{

		}
		else if (this.getType().intValue() == Transaction.TYPE_3)
		{ // 担保交易收款
			if (this.getFlag() == 0)
			{ // 接收人
				if (this.getStatus().intValue() == Transaction.STATUS_1)
				{ // 还没付款
					url = "javascript:void(0)";
					url2 = "javascript:transactionPayment('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "')";
					url3 = "javascript:getTransactionDetailById('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
					    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("关闭交易");
					op.setTitle("拍下以后7天不付款,交易会自动关闭;或者联系卖家关闭此交易");
					op.setStyle("cursor:help");
					if(uri.hasRight("sb02")){
						op.setUrl2(url2);
						op.setUrlName2("付款");
					}					
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_2) 
				{ // 担保交易付款后,状态变成等待卖家发货
					Date date = new Date();
					long day = (date.getTime() - this.payDate.getTime())
					    / (3600L * 1000 * 24);
					if (day >= 1)
					{
						url = "javascript:refundMent('" + this.getId() + "','"
						    + this.getOrderDetails().getId() + "',0)";
					}
					else
					{
						url = "javascript:void(0)";
						op.setTitle("付款24小时后才能退款");
						op.setStyle("cursor:help");
					}
					url2 = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					if(uri.hasRight("sb13")){
						op.setUrl(url);
						op.setUrlName("退款");
					}
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_3)
				{ // 买家确认收货
					url = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{ // 关闭交易
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_6)
				{ // 卖家发货已经发货,里面的退款还要做判断,这里如果卖家已经发货了,就暂时屏蔽退款的按钮,留到以后业务需要的时候再做
					url = "javascript:refundMent('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "',0)";
					url2 = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					url3 = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',2)";
					if(uri.hasRight("sb13")){
						op.setUrl(url); 
						op.setUrlName("退款");
					}
					op.setUrl2(url2);
					op.setUrlName2("确认收货");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				// else if(this.getStatus().intValue()==Transaction.STATUS_7){
				// //卖家已经发货了,但买家退款,并且选择已经收到货,但不用退货的情况
				// url =
				// "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
				// url2 =
				// "javascript:getRefundMentDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"',0,0)";
				// url3 =
				// "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',2)";
				// op.setUrl(url);
				// op.setUrlName("确认收货");
				// op.setUrl2(url2);
				// op.setUrlName2("查看退款详情");
				// op.setUrl3(url3);
				// op.setUrlName3("查看");
				// list.add(op);
				// }
				else if (this.getStatus().intValue() == Transaction.STATUS_10)
				{ // 买家在卖家未发货之前退款
					// url =
					// "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')";
					url = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					// op.setUrl(url);
					// op.setUrlName("查看退款详情");
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_11)
				{ // 卖家同意退款,退款成功
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
			else
			{ // 发起人
				if (this.getStatus().intValue() == Transaction.STATUS_1)
				{ // 还没付款
					url = "javascript:closeTransaction('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "')";
					url2 = "javascript:modifyTransactionPrice('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url3 = "javascript:getTransactionDetailById('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
					    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("关闭交易");
					op.setUrl2(url2);
					op.setUrlName2("修改交易价格");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_2)
				{ // 担保交易付款后,状态变成等待卖家发货
					url = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					url2 = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					op.setUrl(url);
					op.setUrlName("发货并确认");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_3)
				{ // 买家确认收货
					url = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{ // 关闭交易
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_6)
				{ // 卖家发货已经发货,支付宝网址是有个叫延长超时时间的,现在暂时不做,只有查看操作
					url = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}

				// else if(this.getStatus().intValue()==Transaction.STATUS_7){
				// //卖家已经发货了,但买家退款,并且选择已经收到货,但不用退货的情况
				// url =
				// "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')";
				// url2 =
				// "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
				// op.setUrl(url);
				// op.setUrlName("查看退款详情");
				// op.setUrl2(url2);
				// op.setUrlName2("查看");
				// list.add(op);
				// }

				else if (this.getStatus().intValue() == Transaction.STATUS_10)
				{ // 买家在卖家未发货之前退款
					url = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					url2 = "javascript:getRefundMentBeforeShippingDetail('"
					    + this.getId() + "','" + this.getOrderDetails().getId() + "','"
					    + this.getFlag() + "')";
					url3 = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					op.setUrl(url);
					op.setUrlName("发货并确认");
					op.setUrl2(url2);
					op.setUrlName2("查看退款详情");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_11)
				{ // 卖家同意退款,退款成功
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}   
		}
		else if (this.getType().intValue() == Transaction.TYPE_4
		    || this.getType().intValue() == Transaction.TYPE_5)
		{ // 即时到账收款和AA批量收款
			if (this.getFlag() == 0)
			{ // 接收人
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{ // 表示已经付款,等待卖家发货,这2种情况都不能退款
					url = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + Transaction.STATUS_3 + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else
				{
					url = "javascript:transactionPayment('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "')";
					url2 = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					
					if(uri.hasRight("sb02")){
						op.setUrl(url);
						op.setUrlName("付款");
					}
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
			}
			else
			{ // 发起人
				if (this.getStatus().intValue() == Transaction.STATUS_1)
				{
					url = "javascript:transactionDetailByDunAndBatch('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else
				{
					url = "javascript:viewTradeTransactionSuccess('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_20
		    || this.getType().intValue() == Transaction.TYPE_22|| this.getType().intValue() == Transaction.TYPE_23)
		{ // 充值
			url = "javascript:viewTradeTransactionSuccess('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_80)
		{ // 接口支付
			if (this.getFlag() == 0)
			{ // 接收人
				url = "javascript:viewTradeBuyTransactionSuccess('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else
			{
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{
					url = "javascript:offLineRefund('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url2 = "javascript:viewTradeTransactionSuccess('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					if(uri.hasRight("sb13")){
						op.setUrl(url);				
						op.setUrlName("线下退款");
					}			
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:viewTradeTransactionFail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_9){
			if (this.getOrderDetails().getStatus()==OrderDetails.STATUS_6 || this.getOrderDetails().getStatus()==OrderDetails.STATUS_7){ 
					if (this.getFlag() == 0){
						url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
						    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
						    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
						    + "','" + this.getType() + "')";
						op.setUrl(url);
						op.setUrlName("查看");
						list.add(op);
					}
					else{
						url ="javascript:transactionRepayment('"+this.getId()+"')";
						url2 = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
					    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
					    + "','" + this.getType() + "')";
						if(uri.hasRight("sb04")){
							op.setUrl(url);
							op.setUrlName("还款");
						}
						op.setUrl2(url2);
						op.setUrlName2("查看");
						list.add(op);
					}
			}
			else if(this.getOrderDetails().getStatus()==OrderDetails.STATUS_8){
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_91){
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_81 || this.getType().intValue() == Transaction.TYPE_82 || this.getType().intValue() == Transaction.TYPE_181 || this.getType().intValue() == Transaction.TYPE_182){
			url = "javascript:viewTradeSellerRefund('" + this.getId()
		    + "','" + this.getOrderDetails().getId()
		    + "',3,'"+this.getFlag()+"')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_109){
			if (this.getFlag() == 0){
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else{
				if(this.getOrderDetails().getStatus()!=null && this.getOrderDetails().getStatus()==OrderDetails.STATUS_1){
					url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else{
					url ="javascript:expense('"+this.getId()+"','"+this.getOrderDetails().getOrderNo()+"')";
					url2 = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("报销");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_191){
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_101){
			if (this.getFlag() == 0){
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else{
				if(this.getOrderDetails().getStatus()!=null && this.getOrderDetails().getStatus()==OrderDetails.STATUS_8){
					url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else{
					url ="javascript:creditRepayment('"+this.getId()+"')";
					url2 = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					if(uri.hasRight("sb04")){
						op.setUrl(url);
						op.setUrlName("还款");
					}
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_102 || this.getType().intValue() == Transaction.TYPE_103){
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_180)
		{ // 接口支付
			if (this.getFlag() == 0)
			{ // 接收人
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else
			{
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{
					url = "javascript:offLineCreditRefund('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url2 = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("信用线下退款");
					op.setUrl2(url2);
					op.setUrlName2("查看");
				//	list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_130)
		{ // 接口支付
			if (this.getFlag() == 0)
			{ // 接收人
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else
			{
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{
					url = "javascript:offLineCreditRefund('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url2 = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("担保线下退款");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		return list;
	}

	public List getOperate()
	{
		List<Operate> list = new ArrayList<Operate>();
		Operate op = new Operate();
		String url = "";
		String url2 = "";
		String url3 = "";

		if (this.getType().intValue() == Transaction.TYPE_1
		    || this.getType().intValue() == Transaction.TYPE_6
		    || this.getType().intValue() == Transaction.TYPE_30
		    || this.getType().intValue() == Transaction.TYPE_44
		    || this.getType().intValue() == Transaction.TYPE_70)
		{ // 即时到账付款,包括向亲朋好友和向陌生人付款,还有即时退款的
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_2)
		{

		}
		else if (this.getType().intValue() == Transaction.TYPE_3)
		{ // 担保交易收款
			if (this.getFlag() == 0)
			{ // 接收人（买家）
				if (this.getStatus().intValue() == Transaction.STATUS_1)
				{ // 还没付款
					url = "javascript:void(0)";
					url2 = "javascript:transactionPayment('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "')";
					url3 = "javascript:getTransactionDetailById('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
					    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("关闭交易");
					op.setTitle("拍下以后7天不付款,交易会自动关闭;或者联系卖家关闭此交易");
					op.setStyle("cursor:help");
					op.setUrl2(url2);
					op.setUrlName2("付款");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_2)
				{ // 担保交易付款后,状态变成等待卖家发货
					Date date = new Date();
					long day = (date.getTime() - this.payDate.getTime())
					    / (3600L * 1000 * 24);
					if (day >= 1)
					{
						url = "javascript:refundMent('" + this.getId() + "','"
						    + this.getOrderDetails().getId() + "',0)";
					}
					else
					{
						url = "javascript:void(0)";
						op.setTitle("付款24小时后才能退款");
						op.setStyle("cursor:help");
					}
					url2 = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("退款");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_3)
				{ // 买家确认收货
					url = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{ // 关闭交易
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_6)
				{ // 卖家发货已经发货,里面的退款还要做判断,这里如果卖家已经发货了,就暂时屏蔽退款的按钮,留到以后业务需要的时候再做
					url = "javascript:refundMent('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "',0)";
					url2 = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					url3 = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',2)";
					op.setUrl(url);
					op.setUrlName("退款");
					op.setUrl2(url2);
					op.setUrlName2("确认收货");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				// else if(this.getStatus().intValue()==Transaction.STATUS_7){
				// //卖家已经发货了,但买家退款,并且选择已经收到货,但不用退货的情况
				// url =
				// "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
				// url2 =
				// "javascript:getRefundMentDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"',0,0)";
				// url3 =
				// "javascript:getTransactionShippingDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',2)";
				// op.setUrl(url);
				// op.setUrlName("确认收货");
				// op.setUrl2(url2);
				// op.setUrlName2("查看退款详情");
				// op.setUrl3(url3);
				// op.setUrlName3("查看");
				// list.add(op);
				// }
				else if (this.getStatus().intValue() == Transaction.STATUS_10)
				{ // 买家在卖家未发货之前退款
					// url =
					// "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')";
					url = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					// op.setUrl(url);
					// op.setUrlName("查看退款详情");
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_11)
				{ // 卖家同意退款,退款成功
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
			else
			{ // 发起人（卖家）
				if (this.getStatus().intValue() == Transaction.STATUS_1)
				{ // 还没付款
					url = "javascript:closeTransaction('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "')";
					url2 = "javascript:modifyTransactionPrice('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url3 = "javascript:getTransactionDetailById('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
					    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("关闭交易");
					op.setUrl2(url2);
					op.setUrlName2("修改交易价格");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_2)
				{ // 担保交易付款后,状态变成等待卖家发货
					url = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					url2 = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					op.setUrl(url);
					op.setUrlName("发货并确认");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_3)
				{ // 买家确认收货
					url = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{ // 关闭交易
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_6)
				{ // 卖家发货已经发货,支付宝网址是有个叫延长超时时间的,现在暂时不做,只有查看操作
					url = "javascript:getTransactionShippingDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}

				// else if(this.getStatus().intValue()==Transaction.STATUS_7){
				// //卖家已经发货了,但买家退款,并且选择已经收到货,但不用退货的情况
				// url =
				// "javascript:getRefundMentBeforeShippingDetail('"+this.getId()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"')";
				// url2 =
				// "javascript:getTransactionPaymentDetailById('"+this.getId()+"','"+this.getStatus()+"','"+this.getOrderDetails().getId()+"','"+this.getFlag()+"','"+this.getFromAgent().getId()+"',1)";
				// op.setUrl(url);
				// op.setUrlName("查看退款详情");
				// op.setUrl2(url2);
				// op.setUrlName2("查看");
				// list.add(op);
				// }

				else if (this.getStatus().intValue() == Transaction.STATUS_10)
				{ // 买家在卖家未发货之前退款
					url = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',0)";
					url2 = "javascript:getRefundMentBeforeShippingDetail('"
					    + this.getId() + "','" + this.getOrderDetails().getId() + "'"+
					    ",'3',"+ "'"+ this.getFlag() + "')";
					url3 = "javascript:getTransactionPaymentDetailById('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "',1)";
					op.setUrl(url);
					op.setUrlName("发货并确认");
					op.setUrl2(url2);
					op.setUrlName2("查看退款详情");
					op.setUrl3(url3);
					op.setUrlName3("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_11)
				{ // 卖家同意退款,退款成功
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_4
		    || this.getType().intValue() == Transaction.TYPE_5)
		{ // 即时到账收款和AA批量收款
			if (this.getFlag() == 0)
			{ // 接收人
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{ // 表示已经付款,等待卖家发货,这2种情况都不能退款
					url = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + Transaction.STATUS_3 + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else
				{
					url = "javascript:transactionPayment('" + this.getId() + "','"
					    + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "')";
					url2 = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("付款");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
			}
			else
			{ // 发起人
				if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else
				{
					url = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}else if(this.getType().intValue() == Transaction.TYPE_7){
			if(this.getFlag() == 1){
				if(this.getStatus().intValue()==Transaction.STATUS_3||this.getStatus().intValue()==Transaction.STATUS_4){
					url = "javascript:transactionDetailByRedPacket('" + this.getId()
				    + "','" + this.getStatus() + "','"
				    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
				    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}else{
					url = "javascript:transactionDetailByRedPacket('" + this.getId()
				    + "','" + this.getStatus() + "','"
				    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
				    + this.getFromAgent().getId() + "','" + this.getType() + "')";
				url2 = "javascript:transactionDetailByRedPacket('" + this.getId()
			    + "','" + this.getStatus() + "','"
			    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
			    + this.getFromAgent().getId() + "','" + this.getType() + "')";
				url3 = "javascript:transactionDetailByRedPacket('" + this.getId()
			    + "','" + this.getStatus() + "','"
			    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
			    + this.getFromAgent().getId() + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("收红包");
				op.setUrlName3("退红包");
				op.setUrl2(url2);
				op.setUrl3(url3);
				op.setUrlName2("查看");
				list.add(op);
				}
			}else{
				url = "javascript:transactionDetailByRedPacket('" + this.getId()
			    + "','" + this.getStatus() + "','"
			    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
			    + this.getFromAgent().getId() + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
		}
		
		else if (this.getType().intValue() == Transaction.TYPE_20
		    || this.getType().intValue() == Transaction.TYPE_22|| this.getType().intValue() == Transaction.TYPE_23)
		{ // 充值
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_80)
		{ // 接口支付
			if (this.getFlag() == 0)
			{ // 接收人
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else
			{
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{
					url = "javascript:offLineRefund('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url2 = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("线下退款");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_9){  //借款
			if (this.getOrderDetails().getStatus()==OrderDetails.STATUS_6 || this.getOrderDetails().getStatus()==OrderDetails.STATUS_7){ 
					if (this.getFlag() == 0){
						url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
						    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
						    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
						    + "','" + this.getType() + "')";
						op.setUrl(url);
						op.setUrlName("查看");
						list.add(op);
					}
					else{
						//url ="javascript:transactionRepayment('"+this.getId()+"')";
						url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
					    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
					    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
					    + "','" + this.getType() + "')";
//						op.setUrl(url);
//						op.setUrlName("还款");
						op.setUrl(url);
						op.setUrlName("查看");
						list.add(op);
					}
			}
			else if(this.getOrderDetails().getStatus()==OrderDetails.STATUS_8){
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_91){  //还款
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_81 
				|| this.getType().intValue() == Transaction.TYPE_82 
				|| this.getType().intValue() == Transaction.TYPE_181 
				|| this.getType().intValue() == Transaction.TYPE_182){//退款
			url = "javascript:getRefundMentBeforeShippingDetail('" + this.getId()
		    + "','" + this.getOrderDetails().getId()
		    + "','3" + "','" + this.getFlag()+"')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_101){ //授信支付
			if (this.getFlag() == 0){
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else{
				if(this.getOrderDetails().getStatus()!=null 
						&& this.getOrderDetails().getStatus()==OrderDetails.STATUS_8){//全部还款
					url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else{
					//url ="javascript:creditRepayment('"+this.getId()+"')";
					url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
//					if(uri.hasRight("sb04")){
//						op.setUrl(url);
//						op.setUrlName("还款");
//					}
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_102 
				|| this.getType().intValue() == Transaction.TYPE_103){ //授信还款
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_109){  //借款（预支）
			if (this.getFlag() == 0){
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
			    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
			    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
			    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else{
				if(this.getOrderDetails().getStatus()!=null 
						&& this.getOrderDetails().getStatus()==OrderDetails.STATUS_1){//已申请报销
					url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
				else{
					url ="javascript:expense('"+this.getId()+"','"+this.getOrderDetails().getOrderNo()+"')";
					url2 = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("报销");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_191){ //报销
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		else if (this.getType().intValue() == Transaction.TYPE_180)//信用支付
		{ // 接口支付
			if (this.getFlag() == 0)
			{ // 接收人
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else
			{
				if (this.getStatus().intValue() == Transaction.STATUS_3)//交易成功
				{
//					url = "javascript:offLineCreditRefund('" + this.getId() + "','"
//					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
//					op.setUrlName("信用线下退款");
//					op.setUrl2(url2);
					op.setUrlName("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}
		else if (this.getType().intValue() == Transaction.TYPE_130)//担保支付
		{ // 接口支付
			if (this.getFlag() == 0)
			{ // 接收人
				url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
				    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
				    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
				    + "','" + this.getType() + "')";
				op.setUrl(url);
				op.setUrlName("查看");
				list.add(op);
			}
			else
			{
				if (this.getStatus().intValue() == Transaction.STATUS_3)
				{
					url = "javascript:offLineCreditRefund('" + this.getId() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getType() + "')";
					url2 = "javascript:transactionDetailByDunAndBatch('" + this.getId()
					    + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("担保线下退款");
					op.setUrl2(url2);
					op.setUrlName2("查看");
					list.add(op);
				}
				else if (this.getStatus().intValue() == Transaction.STATUS_4)
				{
					url = "javascript:getTransactionSuccessAndFailDetail('"
					    + this.getId() + "','" + this.getStatus() + "','"
					    + this.getOrderDetails().getId() + "','" + this.getFlag() + "','"
					    + this.getFromAgent().getId() + "','" + this.getType() + "')";
					op.setUrl(url);
					op.setUrlName("查看");
					list.add(op);
				}
			}
		}else{
			url = "javascript:getTransactionSuccessAndFailDetail('" + this.getId()
		    + "','" + this.getStatus() + "','" + this.getOrderDetails().getId()
		    + "','" + this.getFlag() + "','" + this.getFromAgent().getId()
		    + "','" + this.getType() + "')";
			op.setUrl(url);
			op.setUrlName("查看");
			list.add(op);
		}
		return list;
	}

	public String getTransactionType()
	{
		return transactionType;
	}

	public String getBuyerAccount()
	{
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount)
	{
		this.buyerAccount = buyerAccount;
	}

	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public long getOrderid()
	{
		return orderid;
	}

	public void setOrderid(long orderid)
	{
		this.orderid = orderid;
	}

	public long getFromAgentId()
	{
		return fromAgentId;
	}

	public void setFromAgentId(long fromAgentId)
	{
		this.fromAgentId = fromAgentId;
	}

	public String getOtherMsg()
	{
		return otherMsg;
	}

	public void setOtherMsg(String otherMsg)
	{
		this.otherMsg = otherMsg;
	}

	public void reset(ActionMapping actionMapping,
	    HttpServletRequest httpServletRequest)
	{
		this.mark = "";
		this.flagStatus = null;
	}

	public String getRefundEdit()
	{
		return refundEdit;
	}

	public void setRefundEdit(String refundEdit)
	{
		this.refundEdit = refundEdit;
	}

	public String getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(String borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	public BigDecimal getRepaymentPrice() {
		return repaymentPrice;
	}

	public void setRepaymentPrice(BigDecimal repaymentPrice) {
		this.repaymentPrice = repaymentPrice;
	}

	public String getRepaymentContent() {
		return repaymentContent;
	}

	public void setRepaymentContent(String repaymentContent) {
		this.repaymentContent = repaymentContent;
	}

	public UserRightInfo getUri() {
		return uri;
	}

	public void setUri(UserRightInfo uri) {
		this.uri = uri;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDebitNo() {
		return debitNo;
	}

	public void setDebitNo(String debitNo) {
		this.debitNo = debitNo;
	}

	public BigDecimal getCreditRePaymentAmount() {
		return creditRePaymentAmount;
	}

	public void setCreditRePaymentAmount(BigDecimal creditRePaymentAmount) {
		this.creditRePaymentAmount = creditRePaymentAmount;
	}

	public BigDecimal getCreditPaymentAmount() {
		return creditPaymentAmount;
	}

	public void setCreditPaymentAmount(BigDecimal creditPaymentAmount) {
		this.creditPaymentAmount = creditPaymentAmount;
	}
}
