package com.hitrust.b2b.util;

import com.hitrust.b2b.request.TrxException;
import org.apache.log4j.Category;

public class DataVerify {

	static Category LOG = Category.getInstance("DataVerify");
	public static final String MANDATORY_FLAG = "Y";

	public DataVerify() {
	}

	public static boolean isValidDateTime(String aString) {
		boolean tResult = false;
		if (aString.length() != 14)
			return false;
		try {
			int tYYYY = Integer.parseInt(aString.substring(0, 4));
			int tMM = Integer.parseInt(aString.substring(4, 6));
			int tDD = Integer.parseInt(aString.substring(6, 8));
			int tHH = Integer.parseInt(aString.substring(8, 10));
			int tMI = Integer.parseInt(aString.substring(10, 12));
			int tSS = Integer.parseInt(aString.substring(12, 14));
			if (tMM < 1 || tMM > 12)
				return false;
			if (tDD < 1 || tDD > 31)
				return false;
			if (tHH < 0 || tHH > 23)
				return false;
			if (tMI < 0 || tMI > 59)
				return false;
			if (tSS < 0 || tSS > 59)
				return false;
			tResult = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return tResult;
	}

	public static void isValidAmount(String aAmount, String targetName,
			int maxPart, int digiPart) throws TrxException {
		double value = 0.0D;
		try {
			value = Double.parseDouble(aAmount);
			if (value <= 0.0D)
				throw new Exception();
		} catch (Exception e) {
			throw new TrxException("1101", "商户提交的交易资料不合法", targetName
					+ "不是大于0的数字! ");
		}
		double max = Math.pow(10D, maxPart - digiPart);
		if (value >= max)
			throw new TrxException("1101", "商户提交的交易资料不合法", targetName + "值太大! ");
		if (aAmount.indexOf(".") > 0
				&& aAmount
						.substring(aAmount.indexOf(".") + 1, aAmount.length())
						.length() > digiPart)
			throw new TrxException("1101", "商户提交的交易资料不合法", targetName
					+ "小数位过长! ");
		else
			return;
	}

	public static boolean isValidDate(String aString) {
		boolean tResult = false;
		if (aString.length() != 8)
			return false;
		try {
			int tYYYY = Integer.parseInt(aString.substring(0, 4));
			int tMM = Integer.parseInt(aString.substring(4, 6));
			int tDD = Integer.parseInt(aString.substring(6, 8));
			if (tMM < 1 || tMM > 12)
				return false;
			if (tDD < 1 || tDD > 31)
				return false;
			tResult = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return tResult;
	}

	public static boolean isValidURL(String aString) {
		return aString.indexOf("http://") == 0
				|| aString.indexOf("https://") == 0;
	}

	public static void checkEmptyAndLength(String target, String targetName,
			String mandatoryFlag, int length) throws TrxException {
		try {
			LOG.debug("target:" + target);
			LOG.debug("length:" + length);
			if ("Y".equalsIgnoreCase(mandatoryFlag)
					&& (target == null || "".equalsIgnoreCase(target)))
				throw new TrxException("1100", "商户提交的交易资料不完整", targetName
						+ "为空白! ");
			if (target != null && target.getBytes("GBK").length > length) {
				LOG.debug("real length:" + target.getBytes("GBK").length);
				throw new TrxException("1101", "商户提交的交易资料不合法", targetName
						+ "过长! ");
			}
		} catch (TrxException e) {
			throw e;
		} catch (Exception e) {
			throw new TrxException("1999", "系统发生无法预期的错误", e.getMessage());
		}
	}

	public static void main(String argc[]) throws TrxException {
		isValidAmount("9999999999999.55", "版本号", 15, 2);
	}

}