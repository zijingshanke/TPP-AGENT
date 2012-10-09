package com.fordays.fdpay.bank;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.Charge;

public class FinishCharge {
	private String orderNo = "";
	private String returnKey = "";
	private String returnMsg = "";
	private Charge charge = null;
	private UserRightInfo uri = new UserRightInfo();

	public String getReturnMsg() {
		returnMsg = getBankErrorMsg(returnKey);
		return returnMsg;
	}

	/**
	 * 根据错误码获取错误信息说明
	 * 
	 * @param String
	 *            paraName
	 * @return errorMsg
	 */
	public static String getBankErrorMsg(String paraName) {
		String res = "com.fordays.fdpay.bank.bankReturnMsg";
		ResourceBundle reb = BankUtil.getResourceBundle(res);
		String paraValue = BankUtil.getParameterByName(reb, paraName);

		try {
			paraValue = new String(paraValue.getBytes("ISO-8859-1"), "UTF-8");
			// System.out.println(paraName + "--" + paraValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paraValue;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReturnKey() {
		return returnKey;
	}

	public void setReturnKey(String returnKey) {
		this.returnKey = returnKey;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

	public UserRightInfo getUri() {
		return uri;
	}

	public void setUri(UserRightInfo uri) {
		this.uri = uri;
	}
}
