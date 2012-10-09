package com.fordays.fdpay.bank.boc.biz;

import javax.servlet.http.HttpServletRequest;

import oracle.sql.Datum;

import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.bank.boc.BocB2CResultFromBank;
import com.fordays.fdpay.bank.boc.BocB2CcmdToBank;
import com.fordays.fdpay.bank.boc.BocOrder;
import com.fordays.fdpay.bank.icbc.biz.IcbcBankBizImp;
import com.fordays.fdpay.transaction.Charge;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.pgw.test.security.PKCS7Tool;

/**
 * 中国银行业务处理实现类
 */
public class BocBankBizImp implements BocBankBiz {
	private LogUtil myLog;

	/**
	 * @网上银行
	 * @接口名：
	 * @版本号:
	 * @方法描述:创建B2C订单
	 */
	public BocB2CcmdToBank createBOCB2CCmd(Charge charge) throws AppException {
		BocB2CcmdToBank bank = new BocB2CcmdToBank();
		bank.setOrderAmount(charge.getAmount().toString());
		bank.setOrderNo(charge.getOrderNo());
		bank.setOrderTime(DateUtil.getDateString("yyyyMMddHHmmss"));
		bank.setOrderNote(charge.getRequestHost());
		return bank;
	}

	// 解析B2C支付订单反馈结果
	public BocB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, BocBankBizImp.class);

		BocB2CResultFromBank bocresult = null;
		String merchantNo = request.getParameter("merchantNo");// 商户ID
		String orderNo = request.getParameter("orderNo");// 商户系统产生的订单号
		String orderSeq = request.getParameter("orderSeq");// 银行订单流水号
		String cardTyp = request.getParameter("cardTyp");// 银行卡类别：
		String payTime = request.getParameter("payTime");// 支付时间
		String orderStatus = request.getParameter("orderStatus");// 订单状态
		String payAmount = request.getParameter("payAmount");// 支付金额
		String signData = request.getParameter("signData");// 网关签名

		// merchantNo|orderNo|orderSeq|cardTyp|payTime|orderStatus|payAmount
		String data = merchantNo + "|" + orderNo + "|" + orderSeq + "|"
				+ cardTyp + "|" + payTime + "|" + orderStatus + "|" + payAmount;

		myLog.info("接收到中国银行的反馈信息----");
		myLog.info("1-orderNo=" + orderNo);
		myLog.info("2-orderSeq=" + orderSeq);
		myLog.info("3-cardTyp=" + cardTyp);
		myLog.info("4-payTime=" + payTime);
		myLog.info("5-orderStatus[0-未处理 1-支付 2-撤销 3-退货 4-未明 5-失败]="
				+ orderStatus);
		myLog.info("6-payAmount=" + payAmount);
		myLog.info("7-signData=" + signData);
		myLog.info("组装交易明文-data=" + data);

		try {
			XmlUtil xml = new XmlUtil();
			Document configDoc = BankUtil.loadXml(BocB2CcmdToBank
					.getConfigXmlUrl());
			String rootCertificatePath = xml.getTextByNode(configDoc,
					"//BANK/BOC/B2C/rootCertificatePath");// 根证书路径

			PKCS7Tool tool = PKCS7Tool.getVerifier(rootCertificatePath);
			String dn = null;// 银行签名证书DN，如果为空则不验证DN
			tool.verify(signData, data.getBytes(), dn);// 验证签名
		} catch (Exception e) {
			myLog.error("中国银行订单验证签名失败,原因:" + e.getMessage());
			e.printStackTrace();
		}

		bocresult = new BocB2CResultFromBank();
		bocresult.setMerchantNo(merchantNo);
		bocresult.setOrderNo(orderNo);
		bocresult.setOrderSeq(orderSeq);
		bocresult.setCardTyp(cardTyp);
		bocresult.setPayTime(payTime);
		bocresult.setOrderStatus(orderStatus);
		bocresult.setPayAmount(payAmount);
		// ---------------------------
		bocresult.setROrderNo(orderNo);
		bocresult.setRAmount(BankUtil.getBigDecimalByString(payAmount));

		bocresult.setRChargeStatus(BocOrder
				.getRChargeStatusByOrderStatus(orderStatus));

		bocresult.setRRequestHost("219.131.194.194");
		bocresult.setRUrl(request.getRequestURL().toString() + "?" + data
				+ "&signData=" + signData);
		return bocresult;
	}

	public ResultFromBank initiativeQueryOrder(String orderNo, String version)
			throws AppException {
		myLog = new LogUtil(false, false, IcbcBankBizImp.class);
		myLog.info("initiativeQueryOrder(" + orderNo + "," + version + ")");

		BocOrder order = new BocOrder(version);
		order.setOrderNos(orderNo);
		BocB2CResultFromBank result = order.getB2CResult_Query(order);
		return result;
	}

	/**
	 * 
	 */
	public void sendNoticeMail(Charge charge) throws AppException {
		System.out.println("中国银行提示邮件建设中");
	}
}