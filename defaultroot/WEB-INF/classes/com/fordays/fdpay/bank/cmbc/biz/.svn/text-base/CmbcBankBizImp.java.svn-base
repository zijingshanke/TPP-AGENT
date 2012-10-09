package com.fordays.fdpay.bank.cmbc.biz;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import Union.JnkyServer;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.cmbc.CmbcB2BResultFromBank; //import com.fordays.fdpay.bank.cmbc.CmbcB2BcmdToBank;
import com.fordays.fdpay.bank.cmbc.CmbcB2CResultFromBank;
import com.fordays.fdpay.bank.cmbc.CmbcB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.hitrust.b2b.response.GenericResponse;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * @民生银行业务实现类
 */
public class CmbcBankBizImp implements CmbcBankBiz {
	private LogUtil myLog;

	// 组装B2B订单
	// public CmbcB2BcmdToBank createCmbcB2Bcmd(Charge charge) throws
	// AppException {
	// CmbcB2BcmdToBank bank = new CmbcB2BcmdToBank();
	// String orderNo = charge.getOrderNo();
	// bank.setOrderNo(orderNo);
	// String amount = charge.getAmount().toString();
	// bank.setTrnxAmount(amount);
	// bank.setOrderAmt(amount);
	// bank.setMerchantTrnxNo(orderNo);
	// String orderDate = DateUtil.getDateString("yyyyMMddHHmmss");
	// bank.setOrderDate(orderDate);
	// return bank;
	// }

	// 组装B2C订单
	public CmbcB2CcmdToBank createCmbcB2Ccmd(Charge charge) throws AppException {
		CmbcB2CcmdToBank bank = new CmbcB2CcmdToBank();
		String orderNo = charge.getOrderNo();
		String billNo = bank.getCorpID() + orderNo;// 订单号=商户号+15位自定义序号
		bank.setBillNo(billNo);
		bank.setTxAmt(charge.getAmount().toString());
		bank.setTxDate(DateUtil.getDateString("yyyyMMdd"));
		bank.setTxTime(DateUtil.getDateString("HHmmss"));

		final String requestHost = charge.getRequestHost();
		bank.setBillremark1(requestHost);
		bank.setBillremark2("0");
		bank.getCMBC_B2C_CMD();
		return bank;
	}

	// 组装银联B2C订单
	public CmbcB2CcmdToBank createCmbcChinaPayB2Ccmd(Charge charge)
			throws AppException {
		CmbcB2CcmdToBank bank = new CmbcB2CcmdToBank("ChinaPay");
		String orderNo = charge.getOrderNo();
		String billNo = bank.getCorpID() + orderNo;// 订单号=商户号+15位自定义序号
		bank.setBillNo(billNo);
		bank.setTxAmt(charge.getAmount().toString());
		bank.setTxDate(DateUtil.getDateString("yyyyMMdd"));
		bank.setTxTime(DateUtil.getDateString("HHmmss"));

		final String requestHost = charge.getRequestHost();
		bank.setBillremark1(requestHost);
		bank.setBillremark2("0");
		bank.getCMBC_B2C_CMD();
		return bank;
	}

	// 解析B2C订单反馈结果
	public CmbcB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		CmbcB2CResultFromBank cmbcResult = null;
		String payresult = request.getParameter("payresult");

		myLog = new LogUtil(false, false, CmbcBankBizImp.class);
		myLog.info("接收民生银行B2C返回的-payresult=" + payresult);

		JnkyServer jnkyServer = null;
		String plainText = "";
		try {
			jnkyServer = CmbcB2CcmdToBank.getJnkyServer();// 初始化解密函数
			plainText = jnkyServer.DecryptData(payresult);// 解密
			myLog.info("解密后反馈信息:" + plainText);

			// 结果信息按照顺序，并且每项以“|”分隔：
			// 订单号|商户代码|交易金额 |交易日期|交易时间|订单状态
			// 例如：
			// 0001|1234|111.23|20021010|121212|01001|remark1|remark2

			Vector<String> vec = BankUtil.getVectorString(plainText, "|");

			String billNo = (String) vec.get(0);
			String corpID = (String) vec.get(1);
			String txAmt = (String) vec.get(2);
			String txDate = (String) vec.get(3);
			String txTime = (String) vec.get(4);
			String billstatus = (String) vec.get(5);
			String Billremark1 = (String) vec.get(6);
			String Billremark2 = (String) vec.get(7);

			cmbcResult = new CmbcB2CResultFromBank();
			cmbcResult.setBillNo(billNo);
			cmbcResult.setCorpID(corpID);
			cmbcResult.setTxAmt(txAmt);
			cmbcResult.setTxDate(txDate);
			cmbcResult.setTxTime(txTime);
			cmbcResult.setBillstatus(billstatus);
			cmbcResult.setBillremark1(Billremark1);
			cmbcResult.setBillremark2(Billremark2);
			// -----------------
			cmbcResult.setROrderNo(billNo.substring(5));// 去除前五位商户号
			cmbcResult.setRAmount(BankUtil.getBigDecimalByString(txAmt));
			if ("0".equals(billstatus)) {
				cmbcResult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
			} else {
				cmbcResult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
			}

			cmbcResult.setRRequestHost(Billremark1);
			cmbcResult.setRUrl(request.getRequestURL().toString()
					+ "?payresult=" + payresult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmbcResult;
	}

	// 解析B2B订单处理结果
	public CmbcB2BResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException {
		com.hitrust.b2b.response.GenericResponse re = new GenericResponse(null);
		re.isSuccessed();

		CmbcB2BResultFromBank cmbcResult = new CmbcB2BResultFromBank();

		return cmbcResult;
	}
}
