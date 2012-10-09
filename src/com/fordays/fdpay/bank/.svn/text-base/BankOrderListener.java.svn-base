package com.fordays.fdpay.bank;

import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.exception.AppException;

/**
 * 银行订单主动监听
 */
public class BankOrderListener implements Runnable {
	private ChargeBiz chargeBiz;
	private BankBiz bankBiz;
	private int count = 4;
	private String orderNo = "";
	private String version = "";
	private String chargeDate = "";
	private String requestHead = "";
	// -----
	private LogUtil myLog;

	/**
	 * 订单监听器(默认)
	 * 
	 * @param String
	 *            BankBiz
	 * @param ChargeBiz
	 *            chargeBiz
	 * @param String
	 *            orderNo
	 * @param String
	 *            version
	 * @param String
	 *            chargeDate
	 * @param String
	 *            requestHead
	 */
	public BankOrderListener(BankBiz bankBiz, ChargeBiz chargeBiz,
			String orderNo, String version, String chargeDate,
			String requestHead) {
		super();
		this.bankBiz = bankBiz;
		this.chargeBiz = chargeBiz;
		this.orderNo = orderNo;
		this.version = version;
		this.chargeDate = chargeDate;
		this.requestHead = requestHead;
	}

	/**
	 * 订单监听器（可自定义订单查询次数）
	 * 
	 * @param String
	 *            BankBiz
	 * @param ChargeBiz
	 *            chargeBiz
	 * @param String
	 *            orderNo
	 * @param String
	 *            version
	 * @param String
	 *            chargeDate
	 * @param String
	 *            requestHead
	 * @param int
	 *            customCount
	 */
	public BankOrderListener(BankBiz bankBiz, ChargeBiz chargeBiz,
			String orderNo, String version, String chargeDate,
			String requestHead, int customCount) {
		super();
		this.bankBiz = bankBiz;
		this.chargeBiz = chargeBiz;
		this.orderNo = orderNo;
		this.version = version;
		this.chargeDate = chargeDate;
		this.requestHead = requestHead;
		this.count = customCount;
	}

	public void run() {
		try {
			int sleepSecond = 90000;
			Thread.sleep(sleepSecond);
			myLog = new LogUtil(false, false, BankOrderListener.class);
			myLog.info(orderNo + ",提交" + sleepSecond + "ms后开启监听");

			for (int i = 0; i < count; i++) {
				ResultFromBank ordeResult = bankBiz.initiativeQueryOrder(
						orderNo, version);
				if (ordeResult != null) {
					long rChargeStatus = ordeResult.getRChargeStatus();
					if (rChargeStatus == 1) {
						// 更新钱门数据库，回调业务接口
						myLog.info(orderNo
								+ ",开始callbackOperation_HttpInvoker()-");
						boolean isCallBack = callbackOperation_HttpInvoker(
								bankBiz, ordeResult);
						if (isCallBack) {
							myLog.info(orderNo + "处理成功,退出监听.....");
							return;
						}
					} else {
						myLog.info("订单状态【1：成功】：" + rChargeStatus);
					}
				} else {
					myLog.error("after initiativeQueryOrder(),result==null");
				}
				if (i < (count - 1)) {
					Thread.sleep(100000 * (i + 1));// 100*i s 主动查询时间间隔
				}
			}
			myLog.info(orderNo + "监听线程结束....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回调业务接口
	 * 
	 * @param BankBiz
	 * @param ResultFromBank
	 * @return boolean
	 * @throws AppException
	 * 
	 */
	private boolean callbackOperation_HttpInvoker(BankBiz bankBiz,
			ResultFromBank ordeResult) throws AppException {
		myLog = new LogUtil(false, false, BankOrderListener.class);
		boolean flag = false;

		FinishCharge returnMsg = chargeBiz.finishCharge(ordeResult);
		final String returnKey = returnMsg.getReturnKey();

		if (("1001").equals(returnKey)) {
			Charge charge = returnMsg.getCharge();

			BankHttpInvoker invoker = new BankHttpInvoker();
			invoker.sendHttpInvoker(charge, requestHead);
			myLog.info("发送异步通知，补单成功");

			sendNotifyEmail(bankBiz, charge);
			myLog.info("发送温馨提示邮件");
			flag = true;
		} else if ("1002".equals(returnKey)) {
			flag = true;
		} else {
			String errorMsg = returnMsg.getOrderNo() + ","
					+ returnMsg.getReturnMsg();
			myLog.error(errorMsg);
			flag = false;
		}
		return flag;
	}

	/**
	 * 发送补单邮件通知
	 * 
	 * @param BankBiz
	 * @param Charge
	 * @return void
	 */
	public static void sendNotifyEmail(BankBiz bankBiz, Charge charge)
			throws AppException {
		bankBiz.sendNoticeMail(charge);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(String chargeDate) {
		this.chargeDate = chargeDate;
	}

	public ChargeBiz getChargeBiz() {
		return chargeBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}

	public BankBiz getBankBiz() {
		return bankBiz;
	}

	public void setBankBiz(BankBiz bankBiz) {
		this.bankBiz = bankBiz;
	}

	public String getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(String requestHead) {
		this.requestHead = requestHead;
	}
}