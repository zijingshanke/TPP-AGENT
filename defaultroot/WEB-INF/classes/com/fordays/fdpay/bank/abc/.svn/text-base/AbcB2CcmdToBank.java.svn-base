package com.fordays.fdpay.bank.abc;

import java.util.ResourceBundle;

import com.fordays.fdpay.bank.BankUtil;
import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.b2c.Order;
import com.hitrust.trustpay.client.b2c.OrderItem;
import com.hitrust.trustpay.client.b2c.PaymentRequest;

//import com.hitrust.trustpay.client.b2c.CardVerifyRequest;

/*******************************************************************************
 * 农业银行网上银行B2C订单
 * 
 * @接口名：
 * @版本号：V2.0.1
 ******************************************************************************/
public class AbcB2CcmdToBank {
	// ---------------------Order------------------
	private String OrderNo = "";// 订单编号
	private String OrderDesc = "";// 订单说明
	private String OrderDate = "";// 订单日期(YYYY/MM/DD）
	private String OrderTime = "";// 订单时间(HH:MM:SS）
	private String OrderAmount = "";// 订单金额
	private String OrderURL = "";// 订单网址
	// -------------PaymentRequest-----------------
	private String ProductType = "";// 商品种类
	private String PaymentType = "";// 1：农行卡支付 2：国际卡支付
	private String PaymentLinkType = "";// 1：Internet网络接入 2：手机网络接入 3:数字电视网络接入
	private String NotifyType = "";// 0：URL页面通知 1：服务器通知
	// 如果支付结果通知方式选择了页面通知，此处填写就是支付结果回传网址；
	// 如果支付结果通知方式选择了服务器通知，此处填写的就是接收支付平台服务器发送响应信息的地址。
	private String ResultNotifyURL = "";// 支付结果地址
	private String MerchantRemarks = "";// 商户备注信息
	// ---------------------------------------------
	private Order tOrder = new Order();// 订单对象
	private OrderItem tOrderItem = new OrderItem();// 订单明细对象(可选)
	private PaymentRequest tPaymentRequest = new PaymentRequest();// 支付请求对象
	private TrxResponse tTrxResponse = null;// 交易响应对象
	// private CardVerifyRequest tCardVerifyRequest = new CardVerifyRequest();//
	// 卡身份验证请求对象

	private String url = "";

	public AbcB2CcmdToBank() {
		init();
	}

	public void init() {
		ProductType = PaymentRequest.PRD_TYPE_TWO;
		// PaymentRequest.PRD_TYPE_ONE：非实体商品，如服务、IP卡、下载MP3、...
		// PaymentRequest.PRD_TYPE_TWO：实体商品

		PaymentType = PaymentRequest.PAY_TYPE_ABC;
		// PaymentRequest.PAY_TYPE_ABC：农行卡支付
		// PaymentRequest.PAY_TYPE_INT：国际卡支付

		PaymentLinkType = PaymentRequest.PAY_LINK_TYPE_NET;
		// PaymentRequest.PAY_LINK_TYPE_NET internet 网络接入
		// PaymentRequest.PAY_LINK_TYPE_MOBILE mobile 网络接入
		// PaymentRequest.PAY_LINK_TYPE_TV 数字电视网络接入

		String res = "com.fordays.fdpay.bank.abc.biz.TrustMerchantB2C";
		ResourceBundle reb = BankUtil.getResourceBundle(res);
		NotifyType = BankUtil.getParameterByName(reb, "NotifyType");
		ResultNotifyURL = BankUtil.getParameterByName(reb, "ResultNotifyURL");
	}

	public Order getTOrder() {
		tOrder.setOrderNo(this.OrderNo);
		tOrder.setOrderDesc(this.OrderDesc);
		tOrder.setOrderDate(this.OrderDate);
		tOrder.setOrderTime(this.OrderTime);
		tOrder.setOrderAmount(Double.parseDouble(this.OrderAmount));
		tOrder.setOrderURL(this.OrderURL);
		tOrder.addOrderItem(getTOrderItem());// -----------
		return tOrder;
	}

	public OrderItem getTOrderItem() {
		tOrderItem.setProductID("0001");
		tOrderItem.setProductName("qmpay");
		tOrderItem.setUnitPrice(Double.parseDouble(this.getOrderAmount()));
		tOrderItem.setQty(1);
		return tOrderItem;
	}

	public PaymentRequest getTPaymentRequest() {
		tPaymentRequest.setOrder(getTOrder());
		tPaymentRequest.setProductType(this.ProductType);
		tPaymentRequest.setPaymentType(this.PaymentType);
		tPaymentRequest.setPaymentLinkType(this.PaymentLinkType);
		tPaymentRequest.setNotifyType(this.NotifyType);
		tPaymentRequest.setMerchantRemarks(this.MerchantRemarks);
		tPaymentRequest.setResultNotifyURL(this.ResultNotifyURL);
		return tPaymentRequest;
	}

	public TrxResponse getTTrxResponse() {
		this.tTrxResponse = this.tPaymentRequest.postRequest();
		return tTrxResponse;
	}

	public String getUrl() {
		this.url = "&OrderNo=" + this.OrderNo + "&OrderDesc=" + this.OrderDesc
				+ "&OrderDate=" + this.OrderDate + "&OrderTime="
				+ this.OrderTime + "&OrderAmount=" + this.OrderAmount
				+ "&OrderURL=" + this.OrderURL + "&ProductType="
				+ this.ProductType + "&PaymentType=" + this.PaymentType
				+ "&PaymentLinkType=" + this.PaymentLinkType + "&NotifyType="
				+ this.NotifyType + "&ResultNotifyURL=" + this.ResultNotifyURL
				+ "&MerchantRemarks=" + this.MerchantRemarks;
		return url;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getOrderDesc() {
		return OrderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		OrderDesc = orderDesc;
	}

	public String getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}

	public String getOrderTime() {
		return OrderTime;
	}

	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}

	public String getOrderAmount() {
		return OrderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		OrderAmount = orderAmount;
	}

	public String getOrderURL() {
		return OrderURL;
	}

	public void setOrderURL(String orderURL) {
		OrderURL = orderURL;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public String getPaymentType() {
		return PaymentType;
	}

	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}

	public String getPaymentLinkType() {
		return PaymentLinkType;
	}

	public void setPaymentLinkType(String paymentLinkType) {
		PaymentLinkType = paymentLinkType;
	}

	public String getNotifyType() {
		return NotifyType;
	}

	public void setNotifyType(String notifyType) {
		NotifyType = notifyType;
	}

	public String getResultNotifyURL() {
		return ResultNotifyURL;
	}

	public void setResultNotifyURL(String resultNotifyURL) {
		ResultNotifyURL = resultNotifyURL;
	}

	public String getMerchantRemarks() {
		return MerchantRemarks;
	}

	public void setMerchantRemarks(String merchantRemarks) {
		MerchantRemarks = merchantRemarks;
	}

	public void setTOrder(Order order) {
		tOrder = order;
	}

	public void setTPaymentRequest(PaymentRequest paymentRequest) {
		tPaymentRequest = paymentRequest;
	}

	public void setTTrxResponse(TrxResponse trxResponse) {
		tTrxResponse = trxResponse;
	}

	public void setTOrderItem(OrderItem orderItem) {
		tOrderItem = orderItem;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}