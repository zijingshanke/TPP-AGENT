package com.fordays.fdpay.cooperate;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.OrderDetails;

/**
 * 商户订单查询接口反馈结果
 */
public class QueryOrderResult {
	
	private Agent buyer;
	private Agent seller;
	private OrderDetails orderDetails;
	// -----
	private String serviceName="";//接口名称
	private String returnUrl="";
	
	// 协议参数
	private String is_success = "";// 成功标志,T/F
	private String sign = "";// 签名 见签名机制
	private String sign_type = ""; // 签名方式
	// 业务参数
	private String body = "";// 订单信息摘要
	private String buyer_email = ""; // 买家
	private String discount = "";// 折扣 两位小数，单位人民币元
	private String email_price = "";// 邮费 两位小数，单位人民币元
	private String finish_date = ""; // 交易结束时间
	private String out_trade_no = ""; // 外部交易号,合作商家定义
	private String payment_type = ""; // 购买类型
	private String shop_price = "";// 商品价格 两位小数，单位人民币元
	private String quantity = "";// 商品数量
	private String seller_email = ""; // 卖家
	private String subject = ""; // 商品名称
	private String total_fee = "";// 付款金额 两位小数，单位人民币元
	private String trade_no = "";// 钱门订单号 钱门定义
	private String status = "";// 订单处理状态 详见订单状态说明
	private String qmpayRemark = ""; // 指令错误信息
	
	public QueryOrderResult() {

	}
	/**
	 * 
	 */
	public QueryOrderResult(Agent myBuyer, OrderDetails myOrderDetails) {
		buyer = myBuyer;
		orderDetails = myOrderDetails;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getEmail_price() {
		return email_price;
	}

	public void setEmail_price(String email_price) {
		this.email_price = email_price;
	}

	public String getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getShop_price() {
		return shop_price;
	}

	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQmpayRemark() {
		return qmpayRemark;
	}

	public void setQmpayRemark(String qmpayRemark) {
		this.qmpayRemark = qmpayRemark;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public Agent getBuyer() {
		return buyer;
	}

	public void setBuyer(Agent buyer) {
		this.buyer = buyer;
	}

	public Agent getSeller() {
		return seller;
	}

	public void setSeller(Agent seller) {
		this.seller = seller;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
	
}
