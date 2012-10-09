package com.fordays.fdpay.bank.abc.test;

import java.util.ArrayList;

import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.XMLDocument;
import com.hitrust.trustpay.client.b2c.Order;
import com.hitrust.trustpay.client.b2c.OrderItem;
import com.hitrust.trustpay.client.b2c.QueryOrderRequest;
import com.hitrust.trustpay.client.b2c.SettleFile;
import com.hitrust.trustpay.client.b2c.SettleRequest;
import com.neza.tool.URLUtil;

/**
 * @description:农业银行B2C订单查询
 */
public class AbcOrderTest {

	private String orderNo = "";//
	private String settleDate = "";// 日期

	public AbcOrderTest() {
		init();
	}

	public void init() {

	}

	public static void DownloadSettleFile(String tSettleDate) {
		// 1、取得商户对账单下载所需要的信息

		// 2、生成商户对账单下载请求对象
		SettleRequest tRequest = new SettleRequest();
		tRequest.setSettleDate(tSettleDate); // 对账日期YYYY/MM/DD （必要信息）
		tRequest.setSettleType(SettleFile.SETTLE_TYPE_TRX);// 对账类型（必要信息）
		// SettleFile.SETTLE_TYPE_TRX：交易对账单
		// 3、传送商户对账单下载请求并取得对账单
		TrxResponse tResponse = tRequest.postRequest();
		// 4、判断商户对账单下载结果状态，进行后续操作
		if (tResponse.isSuccess()) {
			// 5、商户对账单下载成功，生成对账单对象
			SettleFile tSettleFile = new SettleFile(tResponse);

			System.out.println("SettleDate = [" + tSettleFile.getSettleDate());
			System.out.println("SettleType = [" + tSettleFile.getSettleType());
			System.out.println("NumOfPayments = ["
					+ tSettleFile.getNumOfPayments());
			System.out.println("SumOfPayAmount = ["
					+ tSettleFile.getSumOfPayAmount());
			System.out.println("NumOfRefunds = ["
					+ tSettleFile.getNumOfRefunds());
			System.out.println("SumOfRefundAmount = ["
					+ tSettleFile.getSumOfRefundAmount());
			// 6、取得对账单明细
			String[] tRecords = tSettleFile.getDetailRecords();
			for (int i = 0; i < tRecords.length; i++) {
				System.out.println("Record-" + i + " = [" + tRecords[i]);
			}
		} else {
			// 7、商户账单下载失败
			System.out.println("ReturnCode = " + tResponse.getReturnCode());
			System.out.println("ErrorMessage = " + tResponse.getErrorMessage());
		}
	}

	public static void PostQueryCmd(String orderNo) {
		// 1、取得商户订单查询所需要的信息
		String tOrderNo = orderNo;
		String tQueryType = "1";

		boolean tEnableDetailQuery = false;

		if (tQueryType.equals("1"))
			tEnableDetailQuery = true;

		// 2、生成商户订单查询请求对象
		QueryOrderRequest tRequest = new QueryOrderRequest();
		tRequest.setOrderNo(tOrderNo); // 订单号（必要信息）
		tRequest.enableDetailQuery(tEnableDetailQuery); // 是否查询详细信息（必要信息）
		// 3、传送商户订单查询请求并取得订单查询结果
		TrxResponse tResponse = tRequest.postRequest();

		System.out.println("-----------------");
		System.out.println(tResponse.toString());

		// 4、判断商户订单查询结果状态，进行后续操作
		if (tResponse.isSuccess()) {
			// 5、生成订单对象
			Order tOrder = new Order(new XMLDocument(tResponse
					.getValue("Order")));
			System.out.println("OrderNo = [" + tOrder.getOrderNo() + "]<br>");
			System.out.println("OrderAmount = [" + tOrder.getOrderAmount()
					+ "]<br>");
			System.out.println("OrderDesc = [" + tOrder.getOrderDesc()
					+ "]<br>");
			System.out.println("OrderDate = [" + tOrder.getOrderDate()
					+ "]<br>");
			System.out.println("OrderTime = [" + tOrder.getOrderTime()
					+ "]<br>");
			System.out.println("OrderURL = [" + tOrder.getOrderURL() + "]<br>");
			System.out.println("PayAmount = [" + tOrder.getPayAmount()
					+ "]<br>");
			System.out.println("RefundAmount = [" + tOrder.getRefundAmount()
					+ "]<br>");
			System.out.println("OrderStatus = [" + tOrder.getOrderStatus()
					+ "]<br>");
			// 6、取得订单明细
			ArrayList tOrderItems = tOrder.getOrderItems();
			for (int i = 0; i < tOrderItems.size(); i++) {
				OrderItem tOrderItem = (OrderItem) tOrderItems.get(i);
				System.out.println("ProductID = [" + tOrderItem.getProductID()
						+ "]<br>");
				System.out.println("ProductName = ["
						+ tOrderItem.getProductName() + "]<br>");
				System.out.println("UnitPrice = [" + tOrderItem.getUnitPrice()
						+ "]<br>");
				System.out.println("Qty = [" + tOrderItem.getQty() + "]<br>");
			}
		} else {
			// 7、商户订单查询失败
			System.out.println("ReturnCode = [" + tResponse.getReturnCode()
					+ "]<br>");
			System.out.println("ErrorMessage = [" + tResponse.getErrorMessage()
					+ "]<br>");
		}
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
}
