package com.hitrust.b2b.request.entry;

import com.hitrust.b2b.request.TrxException;
import com.hitrust.b2b.util.DataVerify;
import com.hitrust.b2b.util.XMLDocument;
import org.apache.log4j.Category;

public class OrderEntry implements RequestEntry {
	static Category LOG = Category.getInstance("OrderEntry");
	private String OrderNo;
	private String OrderAmt;
	private String OrderDate;
	private String SellerCorporationAcctNo;
	private String SellerCorporationAcctName;
	private String SellerBank;
	private String SellerBankAddress;
	private String BuyerCorporationName;
	private String SellerCorporationName;
	private String PayType;
	private OrderDetailEntry OrderDetail;
	private String Status;
	private static final int ORDERNO_LENGTH = 50;
	private static final int ORDERAMT_LENGTH = 16;
	private static final int ORDERDATE_LENGTH = 14;
	private static final int SELLERCORPORATIONACCTNO_LENGTH = 32;
	private static final int SELLERCORPORATIONACCTNAME_LENGTH = 90;
	private static final int SELLERBANK_LENGTH = 60;
	private static final int SELLERBANKADDRESS_LENGTH = 100;
	private static final int BUYERCORPORATIONNAME_LENGTH = 60;
	private static final int SELLERCORPORATIONNAME_LENGTH = 90;
	private static final int PAYTYPE_LENGTH = 1;
	private static final int STATUS_LENGTH = 1;
	private static final int ORDERDETAIL_LENGTH = 5000;

	public OrderEntry() {
		OrderNo = "";
		OrderAmt = "";
		OrderDate = "";
		SellerCorporationAcctNo = "";
		SellerCorporationAcctName = "";
		SellerBank = "";
		SellerBankAddress = "";
		BuyerCorporationName = "";
		SellerCorporationName = "";
		PayType = "";
		Status = "";
	}

	public void checkEntry(String requestType) throws TrxException {
		LOG.debug("检查Order[OrderNo] 属性");
		DataVerify.checkEmptyAndLength(OrderNo, "订单号", "Y", 50);
		LOG.debug("检查Order[OrderAmt] 属性");
		DataVerify.checkEmptyAndLength(OrderAmt, "订单金额", "Y", 16);
		DataVerify.isValidAmount(OrderAmt, "订单金额", 15, 2);
		LOG.debug("检查Order[OrderDate] 属性");
		DataVerify.checkEmptyAndLength(OrderDate, "订单日期", "Y", 14);
		if (!DataVerify.isValidDateTime(OrderDate))
			throw new TrxException("1101", "商户提交的交易资料不合法", "订单日期格式错误! ");
		LOG.debug("检查Order[SellerCorporationAcctNo] 属性");
		if (requestType.equals("P001"))
			DataVerify.checkEmptyAndLength(SellerCorporationAcctNo, "卖方企业帐号",
					"Y", 32);
		else
			DataVerify.checkEmptyAndLength(SellerCorporationAcctNo, "卖方企业帐号",
					"N", 32);
		LOG.debug("Order[SellerCorporationAcctName] 属性");
		if (requestType.equals("P001"))
			DataVerify.checkEmptyAndLength(SellerCorporationAcctName,
					"卖方企业帐户名称", "Y", 90);
		else
			DataVerify.checkEmptyAndLength(SellerCorporationAcctName,
					"卖方企业帐户名称", "N", 90);
		if (!"P006".equals(requestType)) {
			DataVerify.checkEmptyAndLength(PayType, "跨行标识", "Y", 1);
			if (!"0".equals(PayType) && !"1".equals(PayType))
				throw new TrxException("1101", "商户提交的交易资料不合法", "跨行标识只能为0或1! ");
		}
		LOG.debug("检查Order[SellerBank] 属性");
		if ("1".equals(PayType))
			DataVerify.checkEmptyAndLength(SellerBank, "卖方卡开户行", "Y", 60);
		else
			DataVerify.checkEmptyAndLength(SellerBank, "卖方卡开户行", "N", 60);
		if ("1".equals(PayType))
			DataVerify.checkEmptyAndLength(SellerBankAddress, "卖方开户行地址", "Y",
					100);
		else
			DataVerify.checkEmptyAndLength(SellerBankAddress, "卖方开户行地址", "N",
					100);
		if (OrderDetail != null
				&& OrderDetail.composeEntry().getValueNoNull("OrderDetail")
						.length() > 5000) {
			throw new TrxException("1101", "商户提交的交易资料不合法", "订单明细过长! ");
		} else {
			DataVerify.checkEmptyAndLength(Status, "订单状态", "N", 1);
			return;
		}
	}

	public XMLDocument composeEntry() {
		String detailMsg = "";
		if (OrderDetail != null)
			detailMsg = OrderDetail.composeEntry().toString();
		String entryMsg = "<Order><OrderNo>" + OrderNo + "</" + "OrderNo" + ">"
				+ "<" + "OrderAmt" + ">" + OrderAmt + "</" + "OrderAmt" + ">"
				+ "<" + "OrderDate" + ">" + OrderDate + "</" + "OrderDate"
				+ ">" + "<" + "SellerCorporationAcctNo" + ">"
				+ SellerCorporationAcctNo + "</" + "SellerCorporationAcctNo"
				+ ">" + "<" + "SellerCorporationAcctName" + ">"
				+ SellerCorporationAcctName + "</"
				+ "SellerCorporationAcctName" + ">" + "<" + "SellerBank" + ">"
				+ SellerBank + "</" + "SellerBank" + ">" + "<"
				+ "SellerBankAddress" + ">" + SellerBankAddress + "</"
				+ "SellerBankAddress" + ">" + "<" + "PayType" + ">" + PayType
				+ "</" + "PayType" + ">" + detailMsg + "<" + "Status" + ">"
				+ Status + "</" + "Status" + ">" + "</" + "Order" + ">";
		return new XMLDocument(entryMsg);
	}

	public String getBuyerCorporationName() {
		return BuyerCorporationName;
	}

	public void setBuyerCorporationName(String buyerCorporationName) {
		BuyerCorporationName = buyerCorporationName;
	}

	public String getOrderAmt() {
		return OrderAmt;
	}

	public void setOrderAmt(String orderAmt) {
		OrderAmt = orderAmt;
	}

	public String getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}

	public OrderDetailEntry getOrderDetail() {
		return OrderDetail;
	}

	public void setOrderDetail(OrderDetailEntry orderDetail) {
		OrderDetail = orderDetail;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getSellerCorporationName() {
		return SellerCorporationName;
	}

	public void setSellerCorporationName(String sellerCorporationName) {
		SellerCorporationName = sellerCorporationName;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getPayType() {
		return PayType;
	}

	public void setPayType(String payType) {
		PayType = payType;
	}

	public String getSellerBank() {
		return SellerBank;
	}

	public void setSellerBank(String sellerBank) {
		SellerBank = sellerBank;
	}

	public String getSellerBankAddress() {
		return SellerBankAddress;
	}

	public void setSellerBankAddress(String sellerBankAddress) {
		SellerBankAddress = sellerBankAddress;
	}

	public String getSellerCorporationAcctName() {
		return SellerCorporationAcctName;
	}

	public void setSellerCorporationAcctName(String sellerCorporationAcctName) {
		SellerCorporationAcctName = sellerCorporationAcctName;
	}

	public String getSellerCorporationAcctNo() {
		return SellerCorporationAcctNo;
	}

	public void setSellerCorporationAcctNo(String sellerCorporationAcctNo) {
		SellerCorporationAcctNo = sellerCorporationAcctNo;
	}

}