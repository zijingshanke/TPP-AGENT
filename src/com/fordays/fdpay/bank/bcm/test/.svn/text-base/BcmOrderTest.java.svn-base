package com.fordays.fdpay.bank.bcm.test;

import com.bocom.netpay.b2cAPI.BOCOMB2COPReply;
import com.bocom.netpay.b2cAPI.OpResult;
import com.bocom.netpay.b2cAPI.OpResultSet;
import com.fordays.fdpay.bank.bcm.BcmOrderResult;

/**
 * @description:交通银行订单查询
 */
public class BcmOrderTest {	
	public static void main(String[] args) {
		String orderNo="C20091029000187 ";
		BcmOrderTest.getQueryDataResult_B2C(orderNo);
	}

	public static BcmOrderResult getQueryDataResult_B2C(String orderNo) {
		BcmOrderResult result = null;
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = BcmOrderTest
				.getBOCOMB2CClient_Locale();

		// 查询指定多笔订单号的支付结果，返回数据参看 5.2（交易接口返回 XML 包） 。
		// orders 是订单信息，多笔订单不超过 20 笔，之间用 ‘|’ 分隔；如“10001|10002|10003|10004”

		BOCOMB2COPReply rep = client.queryOrder(orderNo);
		String code, err, msg;
		if (rep == null) {
			err = client.getLastErr();
			System.out.print("交易错误信息：" + err + "<br>");
		} else {
			code = rep.getRetCode(); // 得到交易返回码
			err = rep.getLastErr();
			msg = rep.getErrorMessage();
			System.out.print("交易返回码：" + code + "<br>");
			System.out.print("交易错误信息：" + msg + "<br>");
			if ("0".equals(code)) {// 表示交易成功	
				int index;
				OpResultSet oprSet = rep.getOpResultSet();
				int iNum = oprSet.getOpresultNum();
				System.out.print("总交易记录数：" + iNum);

				for (index = 0; index <= iNum - 1; index++) {
					String order = oprSet.getResultValueByName(index, "order"); // 订单号
					String orderDate = oprSet.getResultValueByName(index,
							"orderDate"); // 订单日期
					String orderTime = oprSet.getResultValueByName(index,
							"orderTime"); // 订单时间
					String curType = oprSet.getResultValueByName(index,
							"curType"); // 币种
					String amount = oprSet
							.getResultValueByName(index, "amount"); // 金额
					String tranDate = oprSet.getResultValueByName(index,
							"tranDate"); // 交易日期
					String tranTime = oprSet.getResultValueByName(index,
							"tranTime"); // 交易时间
					String tranState = oprSet.getResultValueByName(index,
							"tranState"); // 支付交易状态
					String orderState = oprSet.getResultValueByName(index,
							"orderState"); // 订单状态
					String fee = oprSet.getResultValueByName(index, "fee"); // 手续费
					String bankSerialNo = oprSet.getResultValueByName(index,
							"bankSerialNo"); // 银行流水号
					String bankBatNo = oprSet.getResultValueByName(index,
							"bankBatNo"); // 银行批次号
					String cardType = oprSet.getResultValueByName(index,
							"cardType"); // 交易卡类型0:借记卡
					// 1：准贷记卡
					// 2:贷记卡
					String merchantBatNo = oprSet.getResultValueByName(index,
							"merchantBatNo"); // 商户批次号
					String merchantComment = oprSet.getResultValueByName(index,
							"merchantComment");// 商户备注				

					System.out.println("订单号：" + order);
					System.out.println("订单日期：" + orderDate);
					System.out.println("订单时间：" + orderTime);
					System.out.println("币种：" + curType);
					System.out.println("金额：" + amount);
					System.out.println("交易日期：" + tranDate);
					System.out.println("交易时间：" + tranTime);
					System.out.println("支付交易状态[1:成功]：" + tranState);

					System.out.println("手续费：" + fee);
					System.out.println("银行流水号：" + bankSerialNo);
					System.out.println("银行批次号：" + bankBatNo);
					System.out.println("交易卡类型[0:借记卡 1：准贷记卡 2:贷记卡]：" + cardType);
					System.out.println("商户批次号：" + merchantBatNo);
					System.out.println("商户备注：" + merchantComment);
				}
			}
		}
		return result;
	}

	public static com.bocom.netpay.b2cAPI.BOCOMB2CClient getBOCOMB2CClient_Locale() {
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = new com.bocom.netpay.b2cAPI.BOCOMB2CClient();
		final String fileName = "E:\\bocommjava\\B2CMerchant.xml";

		// final String fileName = Constant.WEB_INFO_PATH + File.separator
		// + "bankkey" + File.separator + "init" + File.separator
		// + "bankInterfaceConfig-BCM.xml";

		// System.out.println("交通银行接口配置文件,path:" + fileName);

		int ret = client.initialize(fileName);
		if (ret != 0) {
			System.out.println("初始化失败,错误信息：" + client.getLastErr());
		}
		return client;
	}

	// 查询帐户余额
	public static void getAccountBanlance() {
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = getBOCOMB2CClient_Locale();// 初始化指令对象
		BOCOMB2COPReply rep = client.queryAccountBalance(); // 结算帐户查询

		String code, err, msg;

		if (rep == null) {
			err = client.getLastErr();
			System.out.print("交易错误信息：" + err + "<br>");
		} else {
			code = rep.getRetCode(); // 得到交易返回码
			msg = rep.getErrorMessage();
			System.out.print("交易返回码：" + code + "<br>");
			System.out.print("交易错误信息：" + msg + "<br>");
			if ("0".equals(code)) {// 表示交易成功
				System.out.print("<br>------------------------<br>");
				OpResult opr = rep.getOpResult();
				String accountNo = opr.getValueByName("settAccount"); // 得到账号
				String accountName = opr.getValueByName("accountName"); // 得到账号名称
				String currType = opr.getValueByName("currType"); // 得到币种
				String currBalance = opr.getValueByName("currBalance"); // 得到当前余额
				String validBalance = opr.getValueByName("validBalance"); // 得到可用余额
				System.out.print("账号:" + accountNo);
				System.out.print("<br>");
				System.out.print("账号名称:" + accountName);
				System.out.print("<br>");
				System.out.print("币种:" + currType);
				System.out.print("<br>");
				System.out.print("当前余额:" + currBalance);
				System.out.print("<br>");
				System.out.print("可用余额:" + validBalance);
				System.out.print("<br>");
				System.out.print("<p></p>");
			}
		}
	}

	// 下载对账单
	public static void getB2C_Transaction_Details_Date(String date) {
		com.bocom.netpay.b2cAPI.BOCOMB2CClient client = getBOCOMB2CClient_Locale();// 初始化指令对象
		BOCOMB2COPReply rep = client.downLoadSettlement(date);

		String code, err, msg;

		if (rep == null) {
			err = client.getLastErr();
			System.out.print("交易错误信息：" + err + "<br>");
		} else {
			code = rep.getRetCode(); // 得到交易返回码
			err = rep.getLastErr();
			msg = rep.getErrorMessage();
			System.out.print("交易返回码：" + code + "<br>");
			System.out.print("交易错误信息：" + msg + "<br>");
			if ("0".equals(code)) {// 表示交易成功
				System.out.print("<br>------------------------<br>");
				OpResult opr = rep.getOpResult();
				String totalSum = opr.getValueByName("totalSum"); // 总金额
				String totalNumber = opr.getValueByName("totalNumber"); // 总笔数
				String totalFee = opr.getValueByName("totalFee"); // 总手续费

				System.out.print("总金额：" + totalSum);
				System.out.print("<br>");
				System.out.print("总笔数：" + totalNumber);
				System.out.print("<br>");
				System.out.print("总手续费：" + totalFee);
				System.out.print("<br>");
				System.out.print("<p></p>");
			}
		}
	}
}
