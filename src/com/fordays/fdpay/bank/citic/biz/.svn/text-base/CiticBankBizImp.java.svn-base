package com.fordays.fdpay.bank.citic.biz;

import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ccb.biz.CcbBankBizImp;
import com.fordays.fdpay.bank.citic.CiticB2CResultFromBank;
import com.fordays.fdpay.bank.citic.CiticB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.lsy.baselib.crypto.exception.ECCryptoProcessorException;
import com.lsy.baselib.crypto.processor.ECCryptoProcessor;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * @中信银行业务实现类
 */
public class CiticBankBizImp implements CiticBankBiz {
	private LogUtil myLog;

	/**
	 * @description:创建B2C充值订单
	 */
	public CiticB2CcmdToBank createCiticB2Ccmd(Charge charge)
			throws AppException {
		CiticB2CcmdToBank bank = new CiticB2CcmdToBank();
		bank.setORDERNO(charge.getOrderNo());
		bank.setORDERDATE(DateUtil.getDateString("yyyy-MM-dd"));
		bank.setORDERTIME(DateUtil.getDateString("hh:mm:ss"));
		bank.setORDERAMT(charge.getAmount().toString());
		return bank;
	}

	/**
	 * @description: 解析B2C充值订单支付结果
	 */
	public CiticB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		myLog = new LogUtil(false, false, CcbBankBizImp.class);
		CiticB2CResultFromBank citicResult = null;
		String sourceMsg = "";
		String SIGNRESMSG = request.getParameter("SIGNRESMSG");
		
		myLog.info("接收中信银行 B2C 反馈SIGNRESMSG="+SIGNRESMSG);		

		ECCryptoProcessor processor = CiticB2CcmdToBank
				.getECCryptoProcessor_VerifySign();

		try {
			// 对密文数据进行验签
			processor.verify(SIGNRESMSG.getBytes());

			// 通过ECCryptoProcessor类定义的getOrderMessage方法读取订单明文数据
			sourceMsg = new String(processor.getOrderMessage(SIGNRESMSG
					.getBytes()));
			myLog.info("订单明文:" + sourceMsg);
		} catch (ECCryptoProcessorException e) {
			e.printStackTrace();
		}

		XmlUtil xml = new XmlUtil();
		StringBuffer notifyDataBuf = new StringBuffer(sourceMsg);
		Document document = xml.readResult(notifyDataBuf);// 将银行返回的信息还原为XML
		String MSGCODE = xml.getTextByNode(document, "//stream/MSGCODE");
		String MSGCN = xml.getTextByNode(document, "//stream/MSGCN");
		String E3RDPAYNO = xml.getTextByNode(document, "//stream/E3RDPAYNO");
		String ORDERDATE = xml.getTextByNode(document, "//stream/ORDERDATE");
		String ORDERTIME = xml.getTextByNode(document, "//stream/ORDERTIME");
		String ORDERNO = xml.getTextByNode(document, "//stream/ORDERNO");
		String ORDERAMT = xml.getTextByNode(document, "//stream/ORDERAMT");
		String CURRID = xml.getTextByNode(document, "//stream/CURRID");
		String PAYAMT = xml.getTextByNode(document, "//stream/PAYAMT");
		String PAYNO = xml.getTextByNode(document, "//stream/PAYNO");
		String ACCNO = xml.getTextByNode(document, "//stream/ACCNO");
		String ACCTYPE = xml.getTextByNode(document, "//stream/ACCTYPE");
		String PBCSTNAME = xml.getTextByNode(document, "//stream/PBCSTNAME");
		String ACCHASH = xml.getTextByNode(document, "//stream/ACCHASH");

		citicResult = new CiticB2CResultFromBank();
		citicResult.setMSGCODE(MSGCODE);
		citicResult.setMSGCN(MSGCN);
		citicResult.setE3RDPAYNO(E3RDPAYNO);
		citicResult.setORDERDATE(ORDERDATE);
		citicResult.setORDERTIME(ORDERTIME);
		citicResult.setORDERNO(ORDERNO);
		citicResult.setORDERAMT(ORDERAMT);
		citicResult.setCURRID(CURRID);
		citicResult.setPAYAMT(PAYAMT);
		citicResult.setPAYNO(PAYNO);
		citicResult.setACCNO(ACCNO);
		citicResult.setACCTYPE(ACCTYPE);
		citicResult.setPBCSTNAME(PBCSTNAME);
		citicResult.setACCHASH(ACCHASH);

		// ----
		citicResult.setROrderNo(ORDERNO);
		citicResult.setRAmount(BankUtil.getBigDecimalByString(PAYAMT));					
		
		if ("AAAAAAA".equals(MSGCODE)) {
			citicResult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
		} else {
			citicResult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
		}

		citicResult.setRRequestHost(request.getHeader("Host"));
		citicResult.setRUrl(request.getRequestURL().toString() + "?SIGNRESMSG="
				+ SIGNRESMSG);
		return citicResult;
	}
}
