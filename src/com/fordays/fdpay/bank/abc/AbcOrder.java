package com.fordays.fdpay.bank.abc;

import java.util.ArrayList;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.transaction.Charge;
import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.XMLDocument;
import com.hitrust.trustpay.client.b2c.Order;
import com.hitrust.trustpay.client.b2c.OrderItem;
import com.hitrust.trustpay.client.b2c.QueryOrderRequest;
import com.hitrust.trustpay.client.b2c.SettleFile;
import com.hitrust.trustpay.client.b2c.SettleRequest;

/**
 * 农业银行订单查询
 */
public class AbcOrder {
	private String orderNo = "";//
	private String version = "";// B2C/B2B
	// --B2C
	private String queryType = "";// 0：状态查询； 1：详细查询
	//
	private String settleDate = "";// 对账日期
	// ------
	private LogUtil myLog;

	public AbcOrder(String version) {
		init(version);
	}

	public void init(String rVersion) {
		version = rVersion;
		if ("B2C".equals(version)) {
			queryType = "1";
		} else {
			 myLog=new LogUtil(false, false, AbcOrder.class);
			myLog.error(version + "订单查询建设中--1");
		}
	}

	public AbcOrderResult getAbcOrderResult() {
		 myLog=new LogUtil(false, false, AbcOrder.class);
		AbcOrderResult result = null;
		if ("B2C".equals(version)) {
			result = getQueryDataResult_B2C();
			return result;
		} else {
			
			myLog.error(version + "订单查询建设中--2");
			return result;
		}
	}

	public AbcOrderResult getQueryDataResult_B2C() {
		 myLog=new LogUtil(false, false, AbcOrder.class);
		AbcOrderResult result = null;

		boolean tEnableDetailQuery = false;
		if (queryType.equals("1"))
			tEnableDetailQuery = true;

		// 2、生成商户订单查询请求对象
		QueryOrderRequest tRequest = new QueryOrderRequest();
		tRequest.setOrderNo(orderNo); // 订单号（必要信息）
		tRequest.enableDetailQuery(tEnableDetailQuery); // 是否查询详细信息（必要信息）

		// 3、传送商户订单查询请求并取得订单查询结果
		TrxResponse tResponse = tRequest.postRequest();

		// 4、判断商户订单查询结果状态，进行后续操作
		if (tResponse.isSuccess()) {
			// 5、生成订单对象
			Order tOrder = new Order(new XMLDocument(tResponse
					.getValue("Order")));

			result = new AbcOrderResult();

			result.setOrderNo(tOrder.getOrderNo());
			result.setOrderAmount(tOrder.getOrderAmount());
			result.setOrderDesc(tOrder.getOrderDesc());
			result.setOrderDate(tOrder.getOrderDate());
			result.setOrderTime(tOrder.getOrderTime());
			result.setOrderURL(tOrder.getOrderURL());
			result.setPayAmount(tOrder.getPayAmount());
			result.setRefundAmount(tOrder.getRefundAmount());
			result.setOrderStatus(tOrder.getOrderStatus());

			// 6、取得订单明细
			ArrayList tOrderItems = tOrder.getOrderItems();
			for (int i = 0; i < tOrderItems.size(); i++) {
				OrderItem tOrderItem = (OrderItem) tOrderItems.get(i);
				result.setProductID(tOrderItem.getProductID());
				result.setProductName(tOrderItem.getProductName());
				result.setUnitPrice(tOrderItem.getUnitPrice());
				result.setQty(tOrderItem.getQty());
			}
			// ------------------
			result.setROrderNo(tOrder.getOrderNo());
			result.setRAmount(BankUtil.getBigDecimalByDouble(tOrder
					.getOrderAmount()));

			final String tranStat = result.getOrderStatus();
			if ("00".equals(tranStat)) {// 00：订单已取消
				result.setRChargeStatus(Charge.CHARGE_STATUS_5);
			} else if ("01".equals(tranStat)) {// ?01：订单已建立，等待支付
				result.setRChargeStatus(Charge.CHARGE_STATUS_0);
			} else if ("02".equals(tranStat)) {// ?02：消费者已支付，等待支付结果(按支付失败处理)
				result.setRChargeStatus(Charge.CHARGE_STATUS_7);
			} else if ("03".equals(tranStat)) {// ?03：订单已支付（支付成功）
				result.setRChargeStatus(Charge.CHARGE_STATUS_1);
			} else if ("04".equals(tranStat)) {// ?04：订单已结算（支付成功）
				result.setRChargeStatus(Charge.CHARGE_STATUS_1);
			} else if ("05".equals(tranStat)) {// ?05：订单已退款
				result.setRChargeStatus(Charge.CHARGE_STATUS_9);
			} else if ("99".equals(tranStat)) {// ?99：订单支付失败
				result.setRChargeStatus(Charge.CHARGE_STATUS_2);
			}

			result.setRRequestHost("www.qmpay.com");
			result.setVersion("B2C");// 仅此方法需要
			result.setRUrl(result.getUrl());

			myLog.info("orderNo:" + result.getROrderNo() + ",status[03:已支付 04:已结算]:"
					+ result.getRChargeStatus());

		} else {// 商户订单查询失败
			myLog.error("ReturnCode =" + tResponse.getReturnCode());
			myLog.error("ErrorMessage = " + tResponse.getErrorMessage());
			result = null;
		}
		return result;
	}

	/**
	 * @description:下载对账单
	 * @param String
	 *            tSettleDate
	 * @return void
	 */
	public void DownloadSettleFile_B2C(String tSettleDate) {
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

			System.out.println("SettleDate = [" + tSettleFile.getSettleDate()
					+ "]<br>");
			System.out.println("SettleType = [" + tSettleFile.getSettleType()
					+ "]<br>");
			System.out.println("NumOfPayments = ["
					+ tSettleFile.getNumOfPayments() + "]<br>");
			System.out.println("SumOfPayAmount = ["
					+ tSettleFile.getSumOfPayAmount() + "]<br>");
			System.out.println("NumOfRefunds = ["
					+ tSettleFile.getNumOfRefunds() + "]<br>");
			System.out.println("SumOfRefundAmount = ["
					+ tSettleFile.getSumOfRefundAmount() + "]<br>");
			// 6、取得对账单明细
			String[] tRecords = tSettleFile.getDetailRecords();
			for (int i = 0; i < tRecords.length; i++) {
				System.out.println("Record-" + i + " = [" + tRecords[i]
						+ "]<br>");
			}
		} else {
			// 7、商户账单下载失败
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

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}
