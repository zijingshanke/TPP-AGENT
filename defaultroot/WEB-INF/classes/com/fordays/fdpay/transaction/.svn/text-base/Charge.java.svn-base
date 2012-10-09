package com.fordays.fdpay.transaction;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.transaction._entity._Charge;

public class Charge extends _Charge {
	private static final long serialVersionUID = 1L;
	private com.fordays.fdpay.user.SysUser sysUser1;
	private String beginDate = "";
	private String endDate = "";
	private String returnPage = "";
	private String agentId = "";
	private String agentOtherId = "";

	public static final long CHARGE_STATUS_0 = 0;// 申请充值
	public static final long CHARGE_STATUS_1 = 1;// 核准通过，充值成功
	public static final long CHARGE_STATUS_2 = 2;// 充值失败
	public static final long CHARGE_STATUS_3 = 3;// 已批准，待核准
	public static final long CHARGE_STATUS_5 = 5;// 已撤销
	public static final long CHARGE_STATUS_6 = 6;// 客户放弃支付
	public static final long CHARGE_STATUS_7 = 7;// 可疑交易
	public static final long CHARGE_STATUS_8 = 8;// 等待授权、审核（部分企业网银充值订单专用）
	public static final long CHARGE_STATUS_9 = 9;// 已退款
	

	public static final String CHARGE_TYPE_SELF = "0,0";// 给自己充值
	public static final String CHARGE_TYPE_TRANSACTION = "1,0";// 登录到钱门付款
	public static final String CHARGE_TYPE_NOACCOUNT = "2,0";// 无钱门帐号，外部订单充值付款
	public static final String CHARGE_TYPE_DIRECTPAYMENT = "3,0";// 有钱门帐号,外部订单充值付款
	public static final String CHARGE_TYPE_OTHER = "4,0";// 给别人充值
	public static final String CHARGE_TYPE_BACKGROUND = "5,0";// 后台线下充值
	public static final String CHARGE_TYPE_GUARANTEE = "6,0";//担保

	private String version = "";
	// -------网银----------
	// 建设个人 CCBB2C  建设企业CCBB2B
	// 工行个人 ICBCB2C 工行企业ICBCB2B
	// 交行个人 BCMB2C
	// 民生个人 CMBCB2C 民生个人CMBCB2B
	// 农业个人 ABCB2C
	// 中信个人 CITICB2C 

	// 校验订单发起地址标识
	public static final String REQUEST_HOST_ILLEGAL = "1009";// 非法
	public static final String REQUEST_HOST_LEGALITY = "1010";// 合法
	
	private String transactionNo = "";
	protected java.math.BigDecimal allowBalance;

	private String chargeStatus = "";
	private String chargeInfo = "";
	private String returnUrl = "";
	private String loginName = "";

	public String getState() {
		String state = "";
		long currenTime = new Date().getTime();
		long chargeTime = chargeDate.getTime();
		boolean isTimeOut = false;
		if (currenTime - chargeTime >= 1000 * 60 * 60 * 24) {
			isTimeOut = true;
		}

		if (status == CHARGE_STATUS_1) {
			state = "成功";
		} else if (status == CHARGE_STATUS_2) {
			state = "失败";
		} else if (status == CHARGE_STATUS_0) {
			state = "处理中";
			if (isTimeOut) {
				state = "支付超时";
			}
		} else if (status == CHARGE_STATUS_6) {
			state = "放弃支付";
		} else if (status == CHARGE_STATUS_7) {
			state = "可疑交易";
		} else if (status == CHARGE_STATUS_8) {
			state = "等待授权";
		}
		return state;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}

	public String getAgentOtherId() {
		return agentOtherId;
	}

	public void setAgentOtherId(String agentOtherId) {
		this.agentOtherId = agentOtherId;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public java.math.BigDecimal getAllowBalance() {
		return allowBalance;
	}

	public void setAllowBalance(java.math.BigDecimal allowBalance) {
		this.allowBalance = allowBalance;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public com.fordays.fdpay.user.SysUser getSysUser1() {
		return sysUser1;
	}

	public void setSysUser1(com.fordays.fdpay.user.SysUser sysUser1) {
		this.sysUser1 = sysUser1;
	}

	public String getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getChargeInfo() {
		return chargeInfo;
	}

	public void setChargeInfo(String chargeInfo) {
		this.chargeInfo = chargeInfo;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		transactionNo = "";
		amount = new BigDecimal(0);
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
