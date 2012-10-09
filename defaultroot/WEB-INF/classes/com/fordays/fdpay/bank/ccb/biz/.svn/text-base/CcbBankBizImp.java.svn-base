package com.fordays.fdpay.bank.ccb.biz;

import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ccb.CcbB2BResultFromBank;
import com.fordays.fdpay.bank.ccb.CcbB2BcmdToBank;
import com.fordays.fdpay.bank.ccb.CcbB2CResultFromBank;
import com.fordays.fdpay.bank.ccb.CcbB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.FileUtil;
import CCBSign.RSASig;

/**
 * 建设银行业务处理类
 */
public class CcbBankBizImp implements CcbBankBiz {
	private LogUtil myLog;

	// 创建网银B2C订单
	public CcbB2CcmdToBank createCcbB2Ccmd(Charge charge) throws AppException {
		CcbB2CcmdToBank bank = new CcbB2CcmdToBank();
		bank.setPayment(charge.getAmount().toString());
		bank.setOrderid(charge.getOrderNo());
		bank.setRemark1(charge.getRequestHost());// 订单发起地址，支付结果通知原文返回
		return bank;
	}

	// 创建网银B2B订单
	public CcbB2BcmdToBank createCcbB2Bcmd(Charge charge) throws AppException {
		CcbB2BcmdToBank bank = new CcbB2BcmdToBank();
		bank.setPayment(charge.getAmount().toString());
		bank.setOrderid(charge.getOrderNo());
		bank.setRemark1(charge.getRequestHost());// 订单发起地址，支付结果通知原文返回
		return bank;
	}

	// 解析B2C订单处理结果(页面通知)
	public CcbB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, CcbBankBizImp.class);
		CcbB2CResultFromBank ccbresult = null;
		String strSrc = "";

		String POSID = request.getParameter("POSID");
		String BRANCHID = request.getParameter("BRANCHID");
		String ORDERID = request.getParameter("ORDERID");
		String PAYMENT = request.getParameter("PAYMENT");
		String CURCODE = request.getParameter("CURCODE");
		String REMARK1 = request.getParameter("REMARK1");// 可以为空
		String REMARK2 = request.getParameter("REMARK2");// 可以为空
		String SUCCESS = request.getParameter("SUCCESS");
		String SIGN = request.getParameter("SIGN");

		myLog.info("接收到建设银行页面通知-----");
		myLog.info("1--POSID=" + POSID);
		myLog.info("2-BRANCHID=" + BRANCHID);
		myLog.info("3--ORDERID=" + ORDERID);
		myLog.info("4--PAYMENT=" + PAYMENT);
		myLog.info("5--CURCODE=" + CURCODE);
		myLog.info("6--REMARK1=" + REMARK1);
		myLog.info("7--REMARK2=" + REMARK2);
		myLog.info("8--SUCCESS=" + SUCCESS);
		myLog.info("9--SIGN=" + SIGN);

		try {
			StringBuffer plain = new StringBuffer();

			// 页面通知无ACC_TYPE字段
			plain.append("POSID=").append(POSID).append("&BRANCHID=").append(
					BRANCHID).append("&ORDERID=").append(ORDERID).append(
					"&PAYMENT=").append(PAYMENT).append("&CURCODE=").append(
					CURCODE).append("&REMARK1=").append(REMARK1).append(
					"&REMARK2=").append(REMARK2).append("&SUCCESS=").append(
					SUCCESS);

			strSrc = plain.toString();// 拼订单源串
			myLog.info("建行页面通知订单明文:" + strSrc);
			ccbresult = new CcbB2CResultFromBank();

			// 验证返回信息签名
			if (verifySigature(SIGN, strSrc)) {
				// tempresult.setAcctype(ACC_TYPE);
				ccbresult.setOrderid(ORDERID);
				ccbresult.setBranchid(BRANCHID);
				ccbresult.setCurcode(CURCODE);
				ccbresult.setOrderid(ORDERID);
				ccbresult.setPayment(PAYMENT);
				ccbresult.setPosid(POSID);
				ccbresult.setRemark1(REMARK1);
				ccbresult.setRemark2(REMARK2);
				ccbresult.setSign(SIGN);
				ccbresult.setSuccess(SUCCESS);// -------成功标志
				// -----------
				ccbresult.setROrderNo(ORDERID);
				ccbresult.setRAmount(BankUtil.getBigDecimalByString(PAYMENT));//

				// Y－成功；N－失败；E－客户放弃支付
				if ("Y".equals(SUCCESS)) {
					ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
				} else if ("N".equals(SUCCESS)) {
					ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
				} else if ("E".equals(SUCCESS)) {
					ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_6);// 放弃支付
				}

				ccbresult.setRRequestHost(REMARK1);
				ccbresult.setRUrl(request.getRequestURL() + "?"
						+ request.getQueryString());
			} else {
				ccbresult = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ccbresult;
	}

	// 解析B2C订单处理结果(服务器通知)
	public CcbB2CResultFromBank getB2CResult_Syn(HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, CcbBankBizImp.class);
		CcbB2CResultFromBank ccbresult = null;
		String strSrc = "";
		String POSID = request.getParameter("POSID");
		String BRANCHID = request.getParameter("BRANCHID");
		String ORDERID = request.getParameter("ORDERID");
		String PAYMENT = request.getParameter("PAYMENT");
		String CURCODE = request.getParameter("CURCODE");
		String REMARK1 = request.getParameter("REMARK1");// 可以为空
		String REMARK2 = request.getParameter("REMARK2");// 可以为空
		String ACC_TYPE = request.getParameter("ACC_TYPE");
		String SUCCESS = request.getParameter("SUCCESS");
		String SIGN = request.getParameter("SIGN");

		myLog.info("接收建设银行服务器通知--------");
		myLog.info("1-POSID=" + POSID);
		myLog.info("2-BRANCHID=" + BRANCHID);
		myLog.info("3-ORDERID=" + ORDERID);
		myLog.info("4-PAYMENT=" + PAYMENT);
		myLog.info("5-CURCODE=" + CURCODE);
		myLog.info("6-REMARK1=" + REMARK1);
		myLog.info("7-REMARK2=" + REMARK2);
		myLog.info("8-ACC_TYPE=" + ACC_TYPE);
		myLog.info("9-SUCCESS=" + SUCCESS);
		myLog.info("10-SIGN=" + SIGN);

		try {
			StringBuffer plain = new StringBuffer();
			// 服务器通知有 ACC_TYPE字段
			plain.append("POSID=").append(POSID).append("&BRANCHID=").append(
					BRANCHID).append("&ORDERID=").append(ORDERID).append(
					"&PAYMENT=").append(PAYMENT).append("&CURCODE=").append(
					CURCODE).append("&REMARK1=").append(REMARK1).append(
					"&REMARK2=").append(REMARK2).append("&ACC_TYPE=").append(
					ACC_TYPE).append("&SUCCESS=").append(SUCCESS);

			strSrc = plain.toString();// 拼订单源串
			myLog.info("建行服务器通知订单明文:" + strSrc);
			ccbresult = new CcbB2CResultFromBank();

			// 验证返回信息签名
			if (verifySigature(SIGN, strSrc)) {
				ccbresult.setAcctype(ACC_TYPE);
				ccbresult.setOrderid(ORDERID);
				ccbresult.setBranchid(BRANCHID);
				ccbresult.setCurcode(CURCODE);
				ccbresult.setOrderid(ORDERID);
				ccbresult.setPayment(PAYMENT);
				ccbresult.setPosid(POSID);
				ccbresult.setRemark1(REMARK1);
				ccbresult.setRemark2(REMARK2);
				ccbresult.setSign(SIGN);
				ccbresult.setSuccess(SUCCESS);// -------成功标志
				// ---------------------
				ccbresult.setROrderNo(ORDERID);
				ccbresult.setRAmount(BankUtil.getBigDecimalByString(PAYMENT));//

				if ("Y".equals(SUCCESS)) {
					ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
				} else if ("N".equals(SUCCESS)) {
					ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
				} else if ("E".equals(SUCCESS)) {
					ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_6);// 放弃支付
				}

				ccbresult.setRRequestHost(REMARK1);
				ccbresult.setRUrl(request.getRequestURL() + "?"
						+ request.getQueryString());

			} else {
				ccbresult = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ccbresult;
	}

	// 解析B2B订单处理结果
	public CcbB2BResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, CcbBankBizImp.class);
		
		// 方法同解析B2C订单处理结果
		String strSrc;

		// 页面通知
		String POSID = request.getParameter("POSID");
		String BRANCHID = request.getParameter("BRANCHID");
		String ORDERID = request.getParameter("ORDERID");
		String PAYMENT = request.getParameter("PAYMENT");
		String CURCODE = request.getParameter("CURCODE");
		String REMARK1 = request.getParameter("REMARK1");
		String REMARK2 = request.getParameter("REMARK2");
		String SUCCESS = request.getParameter("SUCCESS");
		String sign = request.getParameter("SIGN");

		StringBuffer plain = new StringBuffer();

		plain.append("POSID=").append(POSID).append("&BRANCHID=").append(
				BRANCHID).append("&ORDERID=").append(ORDERID).append(
				"&PAYMENT=").append(PAYMENT).append("&CURCODE=")
				.append(CURCODE).append("&REMARK1=").append(REMARK1).append(
						"&REMARK2=").append(REMARK2).append("&SUCCESS=")
				.append(SUCCESS);

		strSrc = plain.toString();// 拼订单源串
		System.out.println("src>>" + strSrc);
		CcbB2BResultFromBank ccbresult = new CcbB2BResultFromBank();

		// 验证返回信息签名
		if (verifySigature(sign, strSrc)) {
			ccbresult.setOrderid(ORDERID);
			ccbresult.setBranchid(BRANCHID);
			ccbresult.setCurcode(CURCODE);
			ccbresult.setOrderid(ORDERID);
			ccbresult.setPayment(PAYMENT);
			ccbresult.setPosid(POSID);
			ccbresult.setRemark1(REMARK1);
			ccbresult.setRemark2(REMARK2);
			ccbresult.setSign(sign);
			ccbresult.setSuccess(SUCCESS);// -------成功标志
			ccbresult.setRUrl(request.getRequestURL() + "?"
					+ request.getQueryString());
			// ----------------
			ccbresult.setROrderNo(ORDERID);
			ccbresult.setRAmount(BankUtil.getBigDecimalByString(PAYMENT));//

			// Y－成功；N－失败；E－客户放弃支付
			if ("Y".equals(SUCCESS)) {
				ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
			} else if ("N".equals(SUCCESS)) {
				ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
			} else if ("E".equals(SUCCESS)) {
				ccbresult.setRChargeStatus(Charge.CHARGE_STATUS_6);// 放弃支付
			}

			ccbresult.setRRequestHost(REMARK1);
			ccbresult.setRUrl(request.getRequestURL() + "?"
					+ request.getQueryString());
		} else {
			ccbresult = null;
		}
		return ccbresult;
	}

	// RSASig验签方法
	public boolean verifySigature(String strSign, String strSrc) {
		myLog = new LogUtil(false, false, CcbBankBizImp.class);
		
		Document configXml = BankUtil
				.loadXml(CcbB2CcmdToBank.getConfigXmlUrl());
		XmlUtil xml = new XmlUtil();
		String publicKey = Constant.WEB_INFO_PATH
				+ xml.getTextByNode(configXml, "//BANK/CCB/B2C/publicKey");
		myLog.info("建行验签公钥文件:" + publicKey);
		String strPubKey;
		try {
			strPubKey = FileUtil.read(publicKey);// 读取商户公钥文件
			RSASig rsa = new RSASig();
			if (strPubKey != null && !"".equals(strPubKey)) {
				rsa.setPublicKey(strPubKey);
				if (rsa.verifySigature(strSign, strSrc)) {
					myLog.info("建设银行验证签名成功");
					return true;
				} else {
					myLog.error("建设银行验证签名失败");
					return false;
				}
			} else {
				myLog.error("读取公钥为空!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}