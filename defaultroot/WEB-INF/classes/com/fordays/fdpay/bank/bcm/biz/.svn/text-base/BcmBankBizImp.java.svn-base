package com.fordays.fdpay.bank.bcm.biz;

import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.bank.bcm.BcmB2CResultFromBank;
import com.fordays.fdpay.bank.bcm.BcmB2CcmdToBank;
import com.fordays.fdpay.bank.bcm.BcmOrder;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * 交通银行业务处理实现类
 */
public class BcmBankBizImp implements BcmBankBiz {
	private LogUtil myLog;

	/**
	 * 组装B2C订单
	 * 
	 * @param Charge
	 *            charge
	 * @return BcmB2CcmdToBank
	 */
	public BcmB2CcmdToBank createBCMB2CCmd(Charge charge) throws AppException {
		BcmB2CcmdToBank bank = new BcmB2CcmdToBank();
		bank.setAmount(charge.getAmount().toString());
		bank.setOrderid(charge.getOrderNo());
		bank.setOrderDate(DateUtil.getDateString("yyyyMMdd"));
		bank.setOrderTime(DateUtil.getDateString("HHmmss"));

		bank.setOrderContent(charge.getAgent().getLoginName()
				+ charge.getOrderNo());

		bank.setOrderMono(charge.getRequestHost());

		bank.getBCM_B2C_CMD();
		return bank;
	}

	/**
	 * 解析B2C订单处理结果
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return {@link ResultFromBank}
	 */
	public BcmB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, BcmBankBizImp.class);

		BcmB2CResultFromBank bcmresult = null;
		String notifyMsg = request.getParameter("notifyMsg");

		myLog.info("接收到交通银行notifyMsg=" + notifyMsg);

		try {
			int lastIndex = notifyMsg.lastIndexOf("|");
			String signMsg = notifyMsg.substring(lastIndex + 1, notifyMsg
					.length());// 获取签名信息
			String srcMsg = notifyMsg.substring(0, lastIndex + 1);
			int veriyCode = -1;
			// 初始化签名库,必须写全
			com.bocom.netpay.b2cAPI.NetSignServer nss = new com.bocom.netpay.b2cAPI.NetSignServer();
			try {
				nss.NSDetachedVerify(signMsg.getBytes(), srcMsg.getBytes());// 对通知结果进行验签
			} catch (Exception e) {
				e.printStackTrace();
			}
			veriyCode = nss.getLastErrnum();
			if (veriyCode < 0) {
				// 验签出错
				myLog.error("商户端验证签名失败：return code:" + veriyCode);
				return null;
			}
			StringTokenizer stName = new StringTokenizer(srcMsg, "|");// 拆解通知结果到Vector
			Vector<String> vc = new Vector<String>();
			int i = 0;
			while (stName.hasMoreTokens()) {
				String value = (String) stName.nextElement();
				if (value.equals(""))
					value = "&nbsp;";
				vc.add(i++, value);
			}
			bcmresult = new BcmB2CResultFromBank();
			bcmresult.setMERCHNO(vc.get(0).toString());
			bcmresult.setORDERNO(vc.get(1).toString());
			bcmresult.setTRANAMOUNT(vc.get(2).toString());
			bcmresult.setTRANCURRTYPE(vc.get(3).toString());
			bcmresult.setPAYBATNO(vc.get(4).toString());
			bcmresult.setMERCHBATCHNO(vc.get(5).toString());
			bcmresult.setTRANDATE(vc.get(6).toString());
			bcmresult.setTRANTIME(vc.get(7).toString());
			bcmresult.setSERIALNO(vc.get(8).toString());
			bcmresult.setTRANRST(vc.get(9).toString());
			bcmresult.setFEESUM(vc.get(10).toString());
			bcmresult.setCARDTYPE(vc.get(11).toString());
			bcmresult.setBankMoNo(vc.get(12).toString());
			bcmresult.setErrDis(vc.get(13).toString());
			// -----------------
			bcmresult.setROrderNo(bcmresult.getORDERNO());
			bcmresult.setRAmount(BankUtil.getBigDecimalByString(bcmresult
					.getTRANAMOUNT()));

			if ("1".equals(bcmresult.getTRANRST())) {// 成功
				bcmresult.setRChargeStatus(Charge.CHARGE_STATUS_1);
			} else {// 失败、放弃支付
				bcmresult.setRChargeStatus(Charge.CHARGE_STATUS_2);
			}

			bcmresult.setRRequestHost(request.getHeader("Host"));
			bcmresult.setRUrl(bcmresult.getRUrl());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bcmresult;
	}

	// ----------------------------订单查询与监听--------------------------
	/**
	 * @implement interface:BankBiz
	 * @description:检查订单状态,BankOrderListener调用的方法
	 * @return {@link ResultFromBank}
	 */
	public ResultFromBank initiativeQueryOrder(String orderNo, String version)
			throws AppException {
		ResultFromBank bcmresult = null;
		try {
			myLog = new LogUtil(false, true, BcmBankBizImp.class);
			myLog.info("initiativeQueryOrder(" + orderNo + "," + version + ")");

			BcmOrder order = new BcmOrder(version);
			order.setOrderNo(orderNo);
			// bcmresult = order.getBcmOrderResult();
			bcmresult = order.getOrderResult_HttpInvoker();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bcmresult;
	}

	public void sendNoticeMail(Charge charge) throws AppException {
		myLog = new LogUtil(false, true, BcmBankBizImp.class);
		myLog.info("sendNoticeMail(Charge charge)。。。。");
	}

	public static void main(String[] args) {
		BcmBankBizImp imp = new BcmBankBizImp();
		try {
			imp.initiativeQueryOrder("C20091030000001", "B2C");
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
}
