package com.fordays.fdpay.bank;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import com.fordays.fdpay.bank.ssl.util.SSLUtil;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

/**
 * 网银支付接口通知发送器
 */
public class BankHttpInvoker {
	private LogUtil myLog;

	/**
	 * 根据订单信息,组装异步通知,回调钱门业务接口
	 * 
	 * @param Charge
	 *            charge
	 * @param String
	 *            requestHead
	 * 
	 * @return void
	 */
	public void sendHttpInvoker(Charge charge, String requestHead)
			throws AppException {
		myLog = new LogUtil(false, false, BankOrderListener.class);
		final String chargeType = charge.getType();
		final String remark = charge.getRemark();

		String url = requestHead;

		try {
			if (Charge.CHARGE_TYPE_SELF.equals(chargeType)) {
				url += "/agent/agent.do";
				url += "?thisAction=agentInfoById";
			} else if (Charge.CHARGE_TYPE_OTHER.equals(chargeType)) {
			} else if (Charge.CHARGE_TYPE_TRANSACTION.equals(chargeType)) {
				url += "/transaction/transaction.do";
				url += "?thisAction=transactionPaymentReturnByBank";
				url += "&transactionNo=" + remark;
			} else if (Charge.CHARGE_TYPE_NOACCOUNT.equals(chargeType)) {
				url += "/cooperate/gateway.do";
				url += "?service=direct_payment_for_no_account";
				url += "&remark=" + remark;
			} else if (Charge.CHARGE_TYPE_DIRECTPAYMENT.equals(chargeType)) {
				url += "/cooperate/gateway.do";
				url += "?service=direct_payment_by_bank";
				url += "&remark=" + remark;
			} else if (Charge.CHARGE_TYPE_GUARANTEE.equals(chargeType)) {
				url += "/cooperate/gateway.do";
				url += "?service=guarantee_payment_by_bank";
				url += "&remark=" + remark;
			} else {
				myLog.error("BankOrderListener:未知的ChargeType");
			}

			myLog.info(chargeType+ ":BankHttpInvoker postReissueMessage() url=" + url);

			String postresult = postReissueMessage(url, "");
			myLog.info("postReissueMessage result:" + postresult);
		} catch (Exception e) {
			myLog.error("BankOrderListener:" + e.getMessage());
		}
	}

	/**
	 * 异步通知发送方法。 兼容Http和不验证个人证书的Https
	 * 
	 * @param String
	 *            url
	 * @param String
	 *            parameters
	 * @return String
	 */
	private String postReissueMessage(String url, String parameters)
			throws AppException {
		myLog = new LogUtil(false, false, BankOrderListener.class);
		String invokerResult = "";
		try {
			String httpHead = url.substring(0, 5);
			if ("http:".equals(httpHead)) {
				invokerResult = HttpInvoker
						.readContentFromPost(url, parameters);
			} else if ("https".equals(httpHead)) {
				SSLUtil.trustAllHttpsCertificates();// --------------

				URL myURL = new URL(url);
				HttpsURLConnection connection = (HttpsURLConnection) myURL.openConnection();

				connection.setConnectTimeout(100000);
				connection.setReadTimeout(100000);

				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				connection.connect();

				DataOutputStream out = new DataOutputStream(connection.getOutputStream());

				// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
				String content = parameters;
				// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
				out.writeBytes(content);

				out.flush();
				out.close(); // flush and close

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer result = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
				connection.disconnect();
				invokerResult = result.toString();
			} else {
				invokerResult = "postReissueMessage(),非法的url" + url;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return invokerResult;
	}

}