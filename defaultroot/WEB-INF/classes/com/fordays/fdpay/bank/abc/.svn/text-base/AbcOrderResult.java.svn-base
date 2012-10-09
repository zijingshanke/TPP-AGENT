package com.fordays.fdpay.bank.abc;

import com.fordays.fdpay.bank.ResultFromBank;

/**
 * 农业银行订单查询结果 目前仅支持b2c
 */
public class AbcOrderResult extends ResultFromBank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String OrderNo = "";
	private double OrderAmount = new Double(0);
	private String OrderDesc = "";
	private String OrderDate = "";
	private String OrderTime = "";
	private String OrderURL = "";
	private double PayAmount = new Double(0);
	private double RefundAmount = new Double(0);
	private String OrderStatus = "";
	// --------order item
	private String ProductID = "";
	private String ProductName = "";
	private double UnitPrice = new Double(0);
	private int Qty;

	// ----------------------
	private String version = "";
	private String url = "";
	private long chargeStatus = new Long(0);// 对应Charge订单处理状态

	public String getUrl() {
		if ("B2C".equals(version)) {
			url = "OrderNo=" + OrderNo + "&OrderAmount=" + OrderAmount
					+ "&OrderDesc=" + OrderDesc + "&OrderDate=" + OrderDate
					+ "&OrderTime=" + OrderTime + "&OrderURL=" + OrderURL
					+ "&PayAmount=" + PayAmount + "&RefundAmount="
					+ RefundAmount + "&OrderStatus=" + OrderStatus
					+ "&ProductID=" + ProductID + "&ProductName=" + ProductName
					+ "&UnitPrice=" + UnitPrice + "&Qty=" + Qty;
		} else {
			System.out.println("AbcOrderResult getUrl() 未知异常。。");
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(long chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public double getOrderAmount() {
		return OrderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		OrderAmount = orderAmount;
	}

	public double getPayAmount() {
		return PayAmount;
	}

	public void setPayAmount(double payAmount) {
		PayAmount = payAmount;
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

	public String getOrderURL() {
		return OrderURL;
	}

	public void setOrderURL(String orderURL) {
		OrderURL = orderURL;
	}

	public double getRefundAmount() {
		return RefundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		RefundAmount = refundAmount;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public double getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}

	public int getQty() {
		return Qty;
	}

	public void setQty(int qty) {
		Qty = qty;
	}
}
