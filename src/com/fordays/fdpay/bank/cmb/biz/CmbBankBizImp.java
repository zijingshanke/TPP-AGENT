package com.fordays.fdpay.bank.cmb.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.cmb.CmbB2CResultFromBank;
import com.fordays.fdpay.bank.cmb.CmbB2CcmdToBank;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * @招商银行业务实现类
 */
public class CmbBankBizImp implements CmbBankBiz {
	private LogUtil myLog;

	// 组装B2C订单
	public CmbB2CcmdToBank createCmbB2Ccmd(Charge charge) throws AppException {
		CmbB2CcmdToBank bank = new CmbB2CcmdToBank();

		String orderNo = charge.getOrderNo();
		// orderNo=orderNo.substring(1, 6);
		bank.setBillno(orderNo);

		bank.setDate(DateUtil.getDateString("yyyyMMdd"));
		bank.setAmount(charge.getAmount().toString());
		bank.setPayeeID(charge.getAgent().getEmail());
		bank.getCMB_B2C_CMD();
		return bank;
	}

	// 解析B2C订单反馈结果
	public CmbB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		CmbB2CResultFromBank cmbcResult = null;

		myLog = new LogUtil(false, false, CmbBankBizImp.class);

		try {
			cmb.netpayment.Security securityTool = CmbB2CcmdToBank
					.getSecurityTool();

			if (securityTool != null) {
				// byte[] baSig =
				// "Succeed=Y&BillNo=000000&Amount=12.00&Date=20001221&Msg=付款请求已被银行接受.&Signature=9|96|42|124|72|152|158|163|254|181|233|185|138|15|6|89|43|167|41|171|28|218|209|216|211|47|169|5|243|235|2|225|189|233|84|130|206|204|49|236|196|127|109|65|193|110|229|29|107|135|174|44|185|109|250|70|163|225|137|18|84|205|236|82|".getBytes("GB2312");
				// byte[] baSig =
				// "Succeed=Y&CoNo=000004&BillNo=8104700022&Amount=60&Date=20071213&MerchantPara=8120080420080414701013700022&Msg=00270000042007121307321387100000002470&Signature=177|48|67|121|22|40|125|29|39|162|103|204|103|156|74|196|63|148|45|142|206|139|243|120|224|193|84|46|216|23|42|29|25|64|232|213|114|3|22|51|131|76|169|143|183|229|87|164|138|77|185|198|116|254|224|68|26|169|194|160|94|35|111|150|"
				// .getBytes();

				String Succeed = request.getParameter("Succeed");
				String CoNo = request.getParameter("CoNo");
				String BillNo = request.getParameter("BillNo");
				String Amount = request.getParameter("Amount");
				String Date = request.getParameter("Date");
				String MerchantPara = request.getParameter("MerchantPara");
				String Msg = request.getParameter("Msg");
				String Signature = request.getParameter("Signature");

				String requestStr = "Succeed=" + Succeed + "&CoNo=" + CoNo
						+ "&BillNo=" + BillNo + "&Amount=" + Amount + "&Date="
						+ Date + "&MerchantPara=" + MerchantPara + "&Msg="
						+ Msg + "&Signature=" + Signature;

				boolean flag = securityTool.checkInfoFromBank(requestStr);
				System.out.println("checkInfoFromBank: " + flag);

				if (flag) {
					cmbcResult = new CmbB2CResultFromBank();
					cmbcResult.setBillNo(BillNo);
					// -----------------
					cmbcResult.setROrderNo(BillNo);
					cmbcResult.setRAmount(BankUtil
							.getBigDecimalByString(Amount));
					if ("Y".equals(Succeed)) {
						cmbcResult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
					} else {
						cmbcResult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
					}

					// cmbcResult.setRRequestHost(Billremark1);
					cmbcResult.setRUrl(request.getRequestURL().toString() + "?"
							+ requestStr);
				}else{
					myLog.info("非法请求数据=");
				}
			} else {
				myLog.info("初始化招商银行验签类失败=");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmbcResult;
	}
}
