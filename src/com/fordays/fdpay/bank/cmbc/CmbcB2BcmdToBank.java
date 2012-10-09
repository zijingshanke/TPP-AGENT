//package com.fordays.fdpay.bank.cmbc;
//
//import com.hitrust.b2b.request.DirectPayRequest;
//import com.hitrust.b2b.request.entry.OrderDetailEntry;
//import com.hitrust.b2b.request.entry.OrderEntry;
//import com.hitrust.b2b.request.entry.PayInfoEntry;
//
///**
// * @民生银行网上银行B2B支付订单
// * @version 1.0
// */
//public class CmbcB2BcmdToBank {
//	// DirectPayRequest
//	private String MerchantTrnxNo = "";// 商户交易序号
//	private String TrnxCode = "";// 交易请求代码
//	private String ResultNotifyURL = "";
//	// PayInfoEntry
//	private String TrnxAmount = "";// 交易金额（必要信息）
//	private String Digest = "";// 消息摘要（非必要信息）
//	// OrderEntry
//	private String OrderNo = "";// 订单编号
//	private String OrderAmt = "";// 订单金额
//	private String OrderDate = "";// 订单日期
//	private String SellerBank = "";// 卖方开户行（卖方不是民生银行的网银签约客户时为必要信息）
//	private String SellerBankAddress = "";// 卖方开户行地址（卖方不是民生银行的网银签约客户时为必要信息）
//	private String PayeeAcctNo = "";// 卖方企业帐号（必要信息）
//	private String PayeeAcctName = "";// 卖方企业帐户名称（必要信息）
//	private String PayType = "";// 跨行表示（必要信息，0：本行，1：跨行）
//	// OrderDetailEntry
//	private String OperName = "";// 下单员姓名（非必要信息）
//	private String RemarkEntry[] = null;// 交易说明性信息的集合（非必要信息，包含域K和域V）
//	private String ItemEntry[] = null;// 订单商品信息（非必要信息，包含域产品代码PID，产品名称PN，产品单价UP，产品数量QTY，商品描述DES）
//
//	private DirectPayRequest directPayRequest = new DirectPayRequest();// 直接支付请求对象
//	private PayInfoEntry payInfoEntry = new PayInfoEntry();// 支付信息对象
//	private OrderEntry orderEntry = new OrderEntry();// 订单信息对象
//	private OrderDetailEntry orderDetailEntry = new OrderDetailEntry();// 订单明细对象（可选，如不需要，创建一个空的）
//
//	//
//	private String strSrc = "";
//	private String interfacePath = "";
//	private String url = "";
//
//	public CmbcB2BcmdToBank() {
//		init();
//	}
//
//	public void init() {
//		this.TrnxCode = "P006";// 直接支付交易代码
//		this.ResultNotifyURL = "https://www.qmpay.com/bank/cmbcb2bResult.do";
//		this.PayeeAcctNo = "1234567891012";
//		this.PayeeAcctName = "珠海市钱门网络科技有限公司";
//		this.PayType = "0";
//
//		this.interfacePath = "";
//	}
//
//	public DirectPayRequest getDirectPayRequest() {
//		directPayRequest.setMerchantTrnxNo(this.MerchantTrnxNo);
//		directPayRequest.setTrnxCode(this.TrnxCode);
//		directPayRequest.setResultNotifyURL(this.ResultNotifyURL);
//		directPayRequest.setPayInfo(getPayInfoEntry());
//		directPayRequest.setOrder(getOrderEntry());
//		return directPayRequest;
//	}
//
//	public PayInfoEntry getPayInfoEntry() {
//		payInfoEntry.setTrnxAmount(this.TrnxAmount);
//		payInfoEntry.setDigest(this.Digest);
//		// payInfoEntry.setPayerAcctNo("payerAcctNo");
//		// payInfoEntry.setPayerCustNo("payerCustNo");
//		// payInfoEntry.setPayerCustOperNo("payerCustOperNo");
//		return payInfoEntry;
//	}
//
//	public OrderEntry getOrderEntry() {
//		orderEntry.setOrderNo(this.OrderNo);
//		orderEntry.setOrderAmt(this.OrderAmt);
//		orderEntry.setOrderDate(this.OrderDate);
//		orderEntry.setSellerBank(this.SellerBank);
//		orderEntry.setSellerBankAddress(this.SellerBankAddress);
//		// orderEntry.setPayerAcctNo("");
//		// orderEntry.setPayeeAcctName(this.PayeeAcctName);
//		orderEntry.setPayType(this.PayType);
//
//		orderEntry.setOrderDetail(getOrderDetailEntry());
//		return orderEntry;
//	}
//
//	public OrderDetailEntry getOrderDetailEntry() {
//		orderDetailEntry.setOperName(this.OperName);
//		orderDetailEntry.setRemarks(null);
//		orderDetailEntry.setItems(null);
//		return orderDetailEntry;
//	}
//
//	public String getMerchantTrnxNo() {
//		return MerchantTrnxNo;
//	}
//
//	public void setMerchantTrnxNo(String merchantTrnxNo) {
//		MerchantTrnxNo = merchantTrnxNo;
//	}
//
//	public String getTrnxCode() {
//		return TrnxCode;
//	}
//
//	public void setTrnxCode(String trnxCode) {
//		TrnxCode = trnxCode;
//	}
//
//	public String getResultNotifyURL() {
//		return ResultNotifyURL;
//	}
//
//	public void setResultNotifyURL(String resultNotifyURL) {
//		ResultNotifyURL = resultNotifyURL;
//	}
//
//	public String getTrnxAmount() {
//		return TrnxAmount;
//	}
//
//	public void setTrnxAmount(String trnxAmount) {
//		TrnxAmount = trnxAmount;
//	}
//
//	public String getDigest() {
//		return Digest;
//	}
//
//	public void setDigest(String digest) {
//		Digest = digest;
//	}
//
//	public String getOrderNo() {
//		return OrderNo;
//	}
//
//	public void setOrderNo(String orderNo) {
//		OrderNo = orderNo;
//	}
//
//	public String getOrderAmt() {
//		return OrderAmt;
//	}
//
//	public void setOrderAmt(String orderAmt) {
//		OrderAmt = orderAmt;
//	}
//
//	public String getOrderDate() {
//		return OrderDate;
//	}
//
//	public void setOrderDate(String orderDate) {
//		OrderDate = orderDate;
//	}
//
//	public String getSellerBank() {
//		return SellerBank;
//	}
//
//	public void setSellerBank(String sellerBank) {
//		SellerBank = sellerBank;
//	}
//
//	public String getSellerBankAddress() {
//		return SellerBankAddress;
//	}
//
//	public void setSellerBankAddress(String sellerBankAddress) {
//		SellerBankAddress = sellerBankAddress;
//	}
//
//	public String getPayeeAcctNo() {
//		return PayeeAcctNo;
//	}
//
//	public void setPayeeAcctNo(String payeeAcctNo) {
//		PayeeAcctNo = payeeAcctNo;
//	}
//
//	public String getPayeeAcctName() {
//		return PayeeAcctName;
//	}
//
//	public void setPayeeAcctName(String payeeAcctName) {
//		PayeeAcctName = payeeAcctName;
//	}
//
//	public String getPayType() {
//		return PayType;
//	}
//
//	public void setPayType(String payType) {
//		PayType = payType;
//	}
//
//	public String getOperName() {
//		return OperName;
//	}
//
//	public void setOperName(String operName) {
//		OperName = operName;
//	}
//
//	public String[] getRemarkEntry() {
//		return RemarkEntry;
//	}
//
//	public void setRemarkEntry(String[] remarkEntry) {
//		RemarkEntry = remarkEntry;
//	}
//
//	public String[] getItemEntry() {
//		return ItemEntry;
//	}
//
//	public void setItemEntry(String[] itemEntry) {
//		ItemEntry = itemEntry;
//	}
//
//	public String getStrSrc() {
//		return strSrc;
//	}
//
//	public void setStrSrc(String strSrc) {
//		this.strSrc = strSrc;
//	}
//
//	public String getInterfacePath() {
//		return interfacePath;
//	}
//
//	public void setInterfacePath(String interfacePath) {
//		this.interfacePath = interfacePath;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public void setDirectPayRequest(DirectPayRequest directPayRequest) {
//		this.directPayRequest = directPayRequest;
//	}
//
//	public void setPayInfoEntry(PayInfoEntry payInfoEntry) {
//		this.payInfoEntry = payInfoEntry;
//	}
//
//	public void setOrderEntry(OrderEntry orderEntry) {
//		this.orderEntry = orderEntry;
//	}
//
//	public void setOrderDetailEntry(OrderDetailEntry orderDetailEntry) {
//		this.orderDetailEntry = orderDetailEntry;
//	}	
//}
