//package com.hitrust.b2b.request;
//import com.hitrust.b2b.request.entry.OrderDetailEntry;
//import com.hitrust.b2b.request.entry.OrderEntry;
//import com.hitrust.b2b.request.entry.PayInfoEntry;
//import com.hitrust.b2b.response.GenericResponse;
//import com.hitrust.b2b.util.DataVerify;
//import com.hitrust.b2b.util.XMLDocument;
//import org.apache.log4j.Category;
//
//public class DirectPayRequest extends GenericRequest {
//	static Category LOG = Category.getInstance("DirectPayRequest");
//	private String ResultNotifyURL;
//	private OrderEntry Order;
//	private PayInfoEntry PayInfo;
//	private static final int RESULTNOTIFYURL_LENGTH = 100;
//
//	public DirectPayRequest() {
//		ResultNotifyURL = "";
//	}
//
//	protected void composeRequestMsg() {
//		composeRequestHeaderMsg();
//		String dataBodyMessage = "<DataBody><MerchantTrnxNo>" + MerchantTrnxNo
//				+ "</" + "MerchantTrnxNo" + ">" + "<" + "TrnxCode" + ">"
//				+ TrnxCode + "</" + "TrnxCode" + ">" + "<" + "ResultNotifyURL"
//				+ ">" + ResultNotifyURL + "</" + "ResultNotifyURL" + ">"
//				+ Order.composeEntry().toString()
//				+ PayInfo.composeEntry().toString() + "</" + "DataBody" + ">";
//		dataBodyDocument = new XMLDocument(dataBodyMessage);
//		totalDocument = new XMLDocument(totalDocument.toString()
//				+ dataBodyDocument.toString());
//	}
//
//	protected void checkRequestMsg() throws TrxException {
//		LOG.debug("检查公共属性");
//		checkRequestHeaderMsg();
//		LOG.debug("检查商户系统URL");
//		DataVerify.checkEmptyAndLength(ResultNotifyURL, "商户系统URL", "Y", 100);
//		if (!DataVerify.isValidURL(ResultNotifyURL))
//			throw new TrxException("1101", "商户提交的交易资料不合法", "商户系统URL格式错误! ");
//		LOG.debug("检查订单信息");
//		if (Order == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "订单信息为空白! ");
//		Order.checkEntry(TrnxCode);
//		LOG.debug("检查支付信息");
//		if (PayInfo == null) {
//			throw new TrxException("1100", "商户提交的交易资料不完整", "支付信息为空白! ");
//		} else {
//			PayInfo.checkEntry(TrnxCode);
//			return;
//		}
//	}
//
//	public OrderEntry getOrder() {
//		return Order;
//	}
//
//	public void setOrder(OrderEntry order) {
//		Order = order;
//	}
//
//	public PayInfoEntry getPayInfo() {
//		return PayInfo;
//	}
//
//	public void setPayInfo(PayInfoEntry payInfo) {
//		PayInfo = payInfo;
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
//	public static void main(String args[]) {
//		DirectPayRequest qr = new DirectPayRequest();
//		qr.Version = "1.0";
//		qr.SignFlag = "1";
//		qr.Language = "GB2312";
//		qr.ClientTime = "20051008160923";
//		qr.MerchantNo = "601100";
//		qr.MerchantTrnxNo = "123456";
//		qr.TrnxCode = "P006";
//		qr.ResultNotifyURL = "http://127.0.0.1";
//		OrderEntry order = new OrderEntry();
//		order.setOrderNo("order2");
//		order.setOrderAmt("1000");
//		order.setOrderDate("20051027093100");
//		order.setBuyerCorporationName("CMBC");
//		order.setSellerCorporationName("CMBC");
//		order.setPayType("0");
//		OrderDetailEntry detail = new OrderDetailEntry();
//		detail.setOperName("lmx");
//		order.setOrderDetail(detail);
//		PayInfoEntry payInfo = new PayInfoEntry();
//		payInfo.setPayerCustNo("2006529089");
//		payInfo.setPayerCustOperNo("lmx");
//		payInfo.setTrnxAmount("1000");
//		payInfo.setDigest("direct pay!!!");
//		qr.setOrder(order);
//		qr.setPayInfo(payInfo);
//		GenericResponse rs = qr.postRequest();
//		System.out.println(rs.getCode() + " : " + rs.getMessage());
//		if (rs.getDocument() != null)
//			System.out.println(rs.getDocument().getFormatDocument(" "));
//	}
//}