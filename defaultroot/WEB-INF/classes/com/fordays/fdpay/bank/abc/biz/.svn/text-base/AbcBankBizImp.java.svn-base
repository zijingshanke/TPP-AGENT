package com.fordays.fdpay.bank.abc.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.bank.abc.AbcB2BResultFromBank;
//import com.fordays.fdpay.bank.abc.AbcB2BcmdToBank;
import com.fordays.fdpay.bank.abc.AbcB2CResultFromBank;
import com.fordays.fdpay.bank.abc.AbcB2CcmdToBank;
import com.fordays.fdpay.bank.abc.AbcOrder;
import com.fordays.fdpay.bank.abc.AbcOrderResult;
import com.fordays.fdpay.transaction.Charge;
//import com.hitrust.b2b.trustpay.client.b2b.TrnxResult;
import com.hitrust.trustpay.client.TrxResponse;
import com.hitrust.trustpay.client.b2c.PaymentResult;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * @description:农业银行业务处理实现类
 */
public class AbcBankBizImp implements AbcBankBiz {
	private LogUtil myLog;

	// 组装B2C订单
	public AbcB2CcmdToBank createABCB2Ccmd(Charge charge) throws AppException {
		AbcB2CcmdToBank bank = new AbcB2CcmdToBank();
		bank.setOrderNo(charge.getOrderNo());
		bank.setOrderAmount(charge.getAmount().toString());
		bank.setOrderDate(DateUtil.getDateString("yyyy/MM/dd"));
		bank.setOrderTime(DateUtil.getDateString("HH:mm:ss"));

		final String requestHost = charge.getRequestHost();
		bank.setMerchantRemarks(requestHost);// 订单发起地址，支付结果通知原文返回

		bank.getTPaymentRequest();
		return bank;
	}

	// 组装B2B订单
//	public AbcB2BcmdToBank createABCB2Bcmd(Charge charge) throws AppException {
//		AbcB2BcmdToBank bank = new AbcB2BcmdToBank();
//		bank.setMerchantTrnxNo(charge.getOrderNo());
//		bank.setTrnxAmount(charge.getAmount().toString());
//		bank.setTrnxDate(DateUtil.getDateString("yyyy/MM/dd"));
//		bank.setTrnxTime(DateUtil.getDateString("HH:mm:ss"));
//		return bank;
//	}

	// 解析B2B订单支付结果
	public AbcB2BResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException {
		AbcB2BResultFromBank abcResult = null;
//		String MSG = request.getParameter("MSG");
//		
//		myLog = new LogUtil(false, false, AbcBankBizImp.class);
//		myLog.info("接到农业银行B2B--MSG=" + MSG);
//		try {
//			TrnxResult tResult = new TrnxResult(MSG);
//
//			if (tResult.isSuccess()) {
//				abcResult = new AbcB2BResultFromBank();
//				abcResult.setMerchantID(tResult.getValue("MerchantID"));
//				abcResult.setCorporationCustomerNo(tResult
//						.getValue("CorporationCustomerNo"));
//				abcResult.setMerchantTrnxNo(tResult.getValue("MerchantTrnxNo"));
//
//				String trOrderNo = tResult.getValue("TrnxSN");
//				abcResult.setTrnxSN(trOrderNo);
//
//				abcResult.setTrnxType(tResult.getValue("TrnxType"));
//				abcResult.setTrnxAMT(tResult.getValue("TrnxAMT"));
//				abcResult.setFreezeNo(tResult.getValue("FreezeNo"));
//				abcResult.setOrginalFreezeNo(tResult
//						.getValue("OrginalFreezeNo"));
//				abcResult.setAccountNo(tResult.getValue("AccountNo"));
//				abcResult.setAccountName(tResult.getValue("AccountName"));
//				abcResult.setAccountBank(tResult.getValue("AccountBank"));
//				abcResult.setAccountDBNo(tResult.getValue("AccountDBNo"));
//				abcResult.setAccountDBName(tResult.getValue("AccountDBName"));
//				abcResult.setAccountDBBank(tResult.getValue("AccountDBBank"));
//				abcResult.setTrnxTime(tResult.getValue("TrnxTime"));
//				abcResult.setTrnxStatus(tResult.getValue("TrnxStatus"));
//				// ----------------
//				abcResult.setROrderNo(trOrderNo);
//				abcResult.setRAmount(BankUtil.getBigDecimalByString(tResult
//						.getValue("TrnxAMT")));
//
//				String abcResultStatus = abcResult.getTrnxStatus();
//
//				myLog.info(trOrderNo + ",订单状态是:" + abcResultStatus);
//
//				if (TrnxResult.STATUS_CHECK.equals(abcResultStatus)) {
//					abcResult.setRChargeStatus(Charge.CHARGE_STATUS_8);// 等待授权
//				} else if (TrnxResult.STATUS_SUCCESS.equals(abcResultStatus)) {
//					abcResult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
//				} else {
//					abcResult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
//				}
//
//				abcResult.setRRequestHost(request.getHeader("Host"));
//				abcResult.setRUrl(request.getRequestURL().toString() + "?MSG="
//						+ MSG);
//			} else {
//				// 支付失败
//				myLog.error("ReturnCode = " + tResult.getReturnCode());
//				myLog.error("ReturnMsg=" + tResult.getErrorMessage());
//				// ---------
//				abcResult.setRChargeStatus(Charge.CHARGE_STATUS_2);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return abcResult;
	}

	/**
	 * @description:解析农业银行B2C订单支付结果
	 */
	public AbcB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		AbcB2CResultFromBank abcResult = null;
		String MSG = request.getParameter("MSG");

		myLog = new LogUtil(false, false, AbcBankBizImp.class);
		myLog.info("接收到农业银行B2C返回的--MSG=" + MSG);

		try {
			// 取得MSG参数，并利用此参数值生成支付结果对象
			PaymentResult tResult = new PaymentResult(MSG);
			abcResult = new AbcB2CResultFromBank();
			if (tResult.isSuccess()) {
				abcResult.setOrderNo(tResult.getValue("OrderNo"));
				abcResult.setAmount(tResult.getValue("Amount"));
				abcResult.setBatchNo(tResult.getValue("BatchNo"));
				abcResult.setVoucherNo(tResult.getValue("VoucherNo"));
				abcResult.setHostDate(tResult.getValue("HostDate"));
				abcResult.setHostTime(tResult.getValue("HostTime"));

				final String requestHost = tResult.getValue("MerchantRemarks");
				abcResult.setMerchantRemarks(requestHost);

				abcResult.setPayType(tResult.getValue("PayType"));
				abcResult.setNotifyType(tResult.getValue("NotifyType"));
				// -------------------
				abcResult.setROrderNo(tResult.getValue("OrderNo"));
				abcResult.setRAmount(BankUtil.getBigDecimalByString(tResult
						.getValue("Amount")));

				abcResult.setRChargeStatus(Charge.CHARGE_STATUS_1);

				abcResult.setRRequestHost(requestHost);
				abcResult.setRUrl(request.getRequestURL().toString() + "?MSG="
						+ MSG);
			} else {
				// 支付失败
				myLog.error("农行ReturnCode = " + tResult.getReturnCode());
				myLog.error("农行ReturnMsg=" + tResult.getErrorMessage());
				// --------
				abcResult.setRChargeStatus(Charge.CHARGE_STATUS_2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return abcResult;
	}

	// =============================订单查询与监听==========================
	/**
	 * @网上银行
	 * @接口名:
	 * @方法描述:创建单笔订单查询对象
	 */
	public AbcOrder createAbcOrderQuery(Charge charge) throws AppException {
		String version = charge.getVersion();// 版本
		int versionLen = version.length();
		version = version.substring(versionLen - 3, versionLen);
		
		myLog = new LogUtil(false, true, AbcBankBizImp.class);
		myLog.info("查询农业银行订单:" + version);
		AbcOrder order = new AbcOrder(version);
		order.setOrderNo(charge.getOrderNo());
		return order;
	}

	/**
	 * @implement interface:BankBiz
	 * @description:检查订单状态,BankOrderListener调用的方法
	 * @return {@link ResultFromBank}
	 */
	public ResultFromBank initiativeQueryOrder(String orderNo, String version)
			throws AppException {
		myLog = new LogUtil(false, true, AbcBankBizImp.class);
		myLog.info("initiativeQueryOrder(" + orderNo + "," + version + ")");

		AbcOrder order = new AbcOrder(version);
		order.setOrderNo(orderNo);

		AbcOrderResult orderResult = order.getAbcOrderResult();
		return orderResult;
	}

	/**
	 * @implement interface:BankBiz
	 * @description:补单成功，发送通知邮件
	 * @param Charge
	 *            charge
	 * @return void
	 * @remark:订单监听器调用
	 */
	public void sendNoticeMail(Charge charge) throws AppException {
		myLog = new LogUtil(false, true, AbcBankBizImp.class);
		myLog.info("sendNoticeMail(Charge charge)。。。。");

		// HashMap<String, String> params = new HashMap<String, String>();
		// params.put("$orderNo$", charge.getOrderNo());
		// params.put("$amount$", charge.getAmount().toString());
		// params.put("$tranDate$", DateUtil.getDateString("yyyy-MM-dd
		// hh:mm:ss"));
		//
		// Agent agent = agentDAO.getAgentById(charge.getAgent().getId());// 勿改
		// params.put("$agentName$", agent.getLoginName());
		// MailUtil.send("0041", agent.getEmail(), params); //增加农业银行提醒邮件
	}

	// -----------------------------------------------------------------------
	/**
	 * @description:获得B2C异常信息
	 * @param TrxResponse
	 *            tTrxResponse
	 * @param HttpServletRequest
	 *            request
	 * @return HttpServletRequest
	 * @remark:ChargeAction调用
	 */
	public HttpServletRequest printAbcB2CErrorMsg(TrxResponse tTrxResponse,
			HttpServletRequest request) throws AppException {
		String ReturnCode = tTrxResponse.getReturnCode();
		String ErrorMsg = tTrxResponse.getErrorMessage();
		
		myLog = new LogUtil(false, true, AbcBankBizImp.class);		
		myLog.info("ReturnCode =" + ReturnCode);
		myLog.info("ErrorMsg=" + ErrorMsg);

		request.setAttribute("bankInfo", "错误码:" + ReturnCode + "," + ErrorMsg);
		return request;
	}

	/**
	 * @description:获得B2B异常信息
	 * @param TrxResponse
	 *            tTrxResponse
	 * @param HttpServletRequest
	 *            request
	 * @return HttpServletRequest
	 * @remark:ChargeAction调用
	 */
//	public HttpServletRequest printAbcB2BErrorMsg(
//			com.hitrust.b2b.trustpay.client.TrxResponse tTrxResponse,
//			HttpServletRequest request) throws AppException {
//		String ReturnCode = tTrxResponse.getReturnCode();
//		String ErrorMsg = tTrxResponse.getErrorMessage();
//		
//		myLog = new LogUtil(false, true, AbcBankBizImp.class);		
//		myLog.info("ReturnCode =" + ReturnCode);
//		myLog.info("ErrorMsg=" + ErrorMsg);
//		
//		request.setAttribute("bankInfo", "错误码:" + ReturnCode + "," + ErrorMsg);
//		return request;
//	}
}