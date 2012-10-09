package com.fordays.fdpay.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fordays.fdpay.transaction._entity._OrderDetails;
import com.pintexx.base.util.Convert;

public class OrderDetails extends _OrderDetails {
	private static final long serialVersionUID = 1L;
	public static final long STATUS_0 = 0; // 表示没申请过报销
	public static final long STATUS_1 = 1; // 表示已经申请过报销
	public static final long STATUS_3 = 3; // 成功
	public static final long STATUS_6 = 6; // 借款还款时用到的状态，未还款，
	public static final long STATUS_7 = 7; // 借款还款时用到的状态，未还款，
	public static final long STATUS_8 = 8; // 借款还款时用到的状态，全部还完款时值为8，
	public static final long STATUS_11 = 11; // 授信,信用支付未还款或部分还款
	public static final long STATUS_12 = 12; // 授信,信用支付已经还款

	public static final long ORDER_DETAILS_TYPE_1 = 1; // 现金到现金
	public static final long ORDER_DETAILS_TYPE_2 = 2; // 信用金额到信用金额
	public static final long ORDER_DETAILS_TYPE_3 = 3; // 现金到信用金额
	public static final long ORDER_DETAILS_TYPE_4 = 4; // 授信金额到现金
	public static final long ORDER_DETAILS_TYPE_5 = 5; // 现金到冻结
	public static final long ORDER_DETAILS_TYPE_6 = 6; // 冻结到现金
	public static final long ORDER_DETAILS_TYPE_7 = 7; // 冻结到冻结

	public static final long BUY_TYPE_1 = 1; // 即时到帐支付
	public static final long BUY_TYPE_2 = 2; // 担保支付
	public static final long BUY_TYPE_3 = 3; // 信用支付
	public static final long BUY_TYPE_4 = 4; // 信用外买支付
	public static final long BUY_TYPE_5 = 5; // 信用圈中圈支付
	public static final long BUY_TYPE_6 = 6; // 委托冻结支付
	public static final long BUY_TYPE_7 = 7; // 委托解冻支付
	public static final long BUY_TYPE_8 = 8; // 授信支付
	public static final long BUY_TYPE_9 = 9; // 招标冻结支付
	public static final long BUY_TYPE_10 = 10; // 招标解冻

	private String batchStatus;

	private String logistics;

	// 是否还款
	private String isRepay;

	// 是否还款
	public String getIsRepay() {
		if (this.status == 11 && (!this.shopName.equals("给圈员授信(收回)")))
			return "授信金额未还";
		else if (this.status == 12 && (!this.shopName.equals("给圈员授信(收回)")))
			return "授信金额已还";
		else
			return "";
	}

	/**
	 * OrderDetails 状态说明
	 */
	public static String getOrderDetailStatusRem(long status) {
		String remark = "";
		if (status == STATUS_0) {
			remark = "没有申请过报销";
		} else if (status == STATUS_1) {
			remark = "已经申请过报销";
		} else if (status == STATUS_3) {
			remark = "成功";
		} else if (status == STATUS_6) {
			remark = "未还款";
		} else if (status == STATUS_7) {
			remark = "未还款";
		} else if (status == STATUS_8) {
			remark = "全部还完款";
		} else if (status == STATUS_11) {
			remark = "未还款或部分还款";
		} else if (status == STATUS_12) {
			remark = "已经还款";
		} else {
			remark = "交易状态异常";
		}
		return remark;
	}

	private List formatRefundNote;

	private String buyerEmail;

	public List getFormatRefundNote() {
		List list = new ArrayList();
		String str[] = this.refundsNote.split("##");
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		return list;
	}

	public String getLogistics() {
		if (this.getLogisticsType() == 0)
			return "快递";
		else if (this.getLogisticsType() == 1)
			return "平邮";
		else
			return "不需要运输";
	}

	public void setBatchStatus(int num) {
		if (num == 0)
			this.batchStatus = "全部已交";
		else
			this.batchStatus = num + " 人未交";
	}

	public void setBatchStatus() {
		this.batchStatus = "全部已付";
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String Email) {
		this.buyerEmail = buyerEmail;
	}

	public String getProid() {
		if (this.shopName != null) {
			if (this.shopName.indexOf(":") == -1
					|| this.shopName.indexOf("-") == -1) {
				return null;
			} else {
				return this.shopName.substring(this.shopName.indexOf(":") + 1,
						this.shopName.indexOf("-"));
			}
		}
		return null;
	}

	public boolean getIsPayMobileType() {
		if (this.shopName != null) {
			if (this.shopName.indexOf(":") == -1) {
				return false;
			} else {
				String payType = this.shopName.substring(0, this.shopName
						.indexOf(":"));
				if ("手机充值".equals(payType)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean getIsPayBlackscreenType() {
		if (this.shopName != null) {
			if (this.shopName.indexOf(":") == -1) {
				return false;
			} else {
				String payType = this.shopName.substring(0, this.shopName
						.indexOf(":"));
				if ("黑屏充值".equals(payType)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getMobileNum() {
		if (this.shopName != null) {
			if (this.shopName.indexOf("-") == -1) {
				return null;
			} else if (this.shopName.indexOf("$") == -1) {
				return this.shopName.substring(this.shopName.indexOf("-") + 1);
			} else {
				return this.shopName.substring(this.shopName.indexOf("-") + 1,
						this.shopName.indexOf("$"));
			}
		}
		return null;
	}

	/*
	 * 订单金额支付类型
	 */
	public static long getOrderDetailsType(long type) {
		if (type == ORDER_DETAILS_TYPE_1) { // 现金到现金
			return ORDER_DETAILS_TYPE_1;
		} else if (type == ORDER_DETAILS_TYPE_2) { // 信用到信用
			return ORDER_DETAILS_TYPE_2;
		} else if (type == ORDER_DETAILS_TYPE_3) { // 现金到信用
			return ORDER_DETAILS_TYPE_3;
		} else if (type == ORDER_DETAILS_TYPE_4) { // 信用到现金
			return ORDER_DETAILS_TYPE_4;
		} else if (type == ORDER_DETAILS_TYPE_5) { // 现金到冻结
			return ORDER_DETAILS_TYPE_5;
		} else if (type == ORDER_DETAILS_TYPE_6) { // 冻结到现金
			return ORDER_DETAILS_TYPE_6;
		}else if (type == ORDER_DETAILS_TYPE_7) { // 冻结到冻结
			return ORDER_DETAILS_TYPE_7;
		} else {
			return new Long(-1);
		}
	}
	public static String getBuyTypeCaption(Long buyType){
	    if(buyType!=null){
		if(buyType==OrderDetails.BUY_TYPE_1)
			return "即时到帐支付";
		else if (buyType==OrderDetails.BUY_TYPE_2)
			return "担保支付";
		else if (buyType==OrderDetails.BUY_TYPE_3)
			return "信用支付";
		else if (buyType==OrderDetails.BUY_TYPE_4)
			return "信用外买支付";
		else if (buyType==OrderDetails.BUY_TYPE_5)
			return "信用圈中圈支付";
		else if (buyType==OrderDetails.BUY_TYPE_6)
			return "委托冻结支付";
		else if (buyType==OrderDetails.BUY_TYPE_7)
			return "委托解冻支付";
		else if (buyType==OrderDetails.BUY_TYPE_8)
			return "授信支付";
		else if (buyType==OrderDetails.BUY_TYPE_9)
			return "招标冻结支付";
		else if (buyType==OrderDetails.BUY_TYPE_10)
			return "招标解冻";
		else
			return "";
		}else{
			return "";
		}
	}
	// public static void main(String[] args) {
	// String s="手机充值:8115-13926989491";
	// OrderDetails or = new OrderDetails();
	// or.shopName=s;
	// System.out.println(or.getIsPayMobileType());
	//		
	// }

}
