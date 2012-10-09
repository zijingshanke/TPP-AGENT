package com.fordays.fdpay.bank.icbc.test;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.icbc.IcbcOrderResult;
import com.neza.exception.AppException;

public class exportIcbcOrders {

	public static void main(String[] args) {
		String company = "qmpay";// 品尚fdays 钱门qmpay
		String version = "B2C";
		String date = "20091206";
		String orderNo = "C20091206000052";
		int orderSize = 50;
		String fileName = "E:\\" + date + "工行交易明细.txt";

		querySingleOrder(company, version, date, orderNo);

		// -------------导出单日明细
		// List<String> orderList_Day = getOrderNoList_Day(date, orderSize);
		// exportIcbcOrders_Day(company, orderList_Day, version, date,
		// fileName);

		// --------------导出多日明细(目前仅支持同一个月内)234
		// exportICBCOrder_Multi(company, version, orderSize, "20091001",
		// "20091003", "yyyyMMdd");
	}

	/**
	 * @description:多日查询导出
	 * @param String
	 *            beginDate
	 * @param String
	 *            endDate
	 * @param String
	 *            version
	 * @remark:开始先支持
	 */
	public static void exportICBCOrder_Multi(String company, String version,
			int orderSize, String beginDate, String endDate, String dateType) {
		String date = "";
		String fileName = "";

		List<String> dateList = getMultiDateList(beginDate, endDate, dateType);
		for (int i = 0; i < dateList.size(); i++) {
			date = dateList.get(i);
			fileName = "E:\\" + date + "工行交易明细.txt";
			List<String> orderList_Day = getOrderNoList_Day(date, orderSize);

			// for (int j = 0; j < orderList_Day.size(); j++) {
			// System.out.println("---orderlist:" + orderList_Day.get(j));
			// }

			exportIcbcOrders_Day(company, orderList_Day, version, date,
					fileName);
		}
	}

	// 根据beginDate和endDate获取dateList
	public static List<String> getMultiDateList(String beginDate,
			String endDate, String dateType) {
		List<String> dateList = new ArrayList<String>();
		String preStr = beginDate.substring(0, 6);
		String beginStr = beginDate.substring(6, 8);
		String endStr = endDate.substring(6, 8);

		int beginInt = Integer.parseInt(beginStr);
		int endInt = Integer.parseInt(endStr);
		int firstInt = beginInt;
		int dateSize = (endInt - beginInt) + 1;

		boolean flag = checkDate(beginDate, endDate, dateType);
		if (flag) {
			// 02 05
			if (beginInt < 10 && endInt < 10) {
				for (int i = 0; i < dateSize; i++) {
					dateList.add(preStr + "0" + firstInt);
					firstInt++;
				}
			}// 10 15
			else if (beginInt > 9 && endInt > 9) {
				for (int i = 0; i < dateSize; i++) {
					dateList.add(preStr + firstInt);
					firstInt++;
				}
			}//
			else if (beginInt < 10 && endInt > 9) {
				for (int i = 0; i < dateSize; i++) {
					if (firstInt < 10) {
						dateList.add(preStr + "0" + firstInt);
					} else {
						dateList.add(preStr + firstInt);
					}
					firstInt++;
				}
			}
		}
		return dateList;
	}

	/**
	 * @description: 检查开始和结束时间格式
	 * @param
	 * @remark:目前仅支持时间类型:yyyyMMdd
	 * @param String
	 *            beginDate
	 * @param String
	 *            endDate
	 * @param String
	 *            dateType
	 * @return boolean
	 */
	public static boolean checkDate(String beginDate, String endDate,
			String dateType) {
		if (dateType.equals("yyyyMMdd")) {
			if (beginDate.length() != 8) {
				System.out.println("开始时间长度不合法");
				return false;
			} else if (endDate.length() != 8) {
				System.out.println("结束时间长度不合法");
				return false;
			} else if (isInteger(beginDate) == false) {
				System.out.println("开始时间包含非法字符");
				return false;
			} else if (isInteger(beginDate) == false) {
				System.out.println("结束时间包含非法字符");
				return false;
			} else {
				String beginStr = beginDate.substring(6, 8);
				String endStr = endDate.substring(6, 8);

				int beginInt = Integer.parseInt(beginStr);
				int endInt = Integer.parseInt(endStr);
				if (beginInt > endInt) {
					System.out.println("开始时间必须早于结束时间");
					return false;
				} else {
					return true;
				}
			}
		} else {
			System.out.println("暂时不支持此时间类型");
			return false;
		}
	}

	/**
	 * @description:单笔订单查询
	 * @param String
	 *            version
	 * @param String
	 *            date
	 * @param String
	 *            orderNo
	 */
	public static void querySingleOrder(String company, String version,
			String date, String orderNo) {
		IcbcOrderTest test = new IcbcOrderTest(company, version);
		test.setTranDate(date);
		test.setOrderNum(orderNo);
		test.PostQueryCmd();
	}

	/**
	 * @description:获取订单号(单日)
	 * @param String
	 *            date
	 * @return List<String>
	 */
	public static List<String> getOrderNoList_Day(String date, int orderSize) {
		List<String> orderNoList = new ArrayList<String>(orderSize);
		String orderNo = "";
		String preOrderNo = "C" + date;
		// orderSize: 0000001 -- 999999
		for (int i = 1; i < orderSize + 1; i++) {
			if (i > 0 && i < 10) {
				orderNo = preOrderNo + "00000" + i;
			} else if (9 < i && i < 100) {
				orderNo = preOrderNo + "0000" + i;
			} else if (99 < i && i < orderSize + 1) {// 1000
				orderNo = preOrderNo + "000" + i;
			}
			// else if (999 < i && i < 10000) {
			// orderNo = preOrderNo + "00" + i;
			// } else if (9999 < i && i < 100000) {
			// orderNo = preOrderNo + "0" + i;
			// } else if (99999 < i && i < 1000000) {
			// orderNo = preOrderNo + i;
			// }
			orderNoList.add(orderNo);
		}
		// System.out.println("getOrderNoList() orderNoList size:"
		// + orderNoList.size());
		return orderNoList;
	}

	/**
	 * @description:报表导出
	 * @param List
	 *            <String> orderNoList
	 * @param String
	 *            version
	 * @param String
	 *            date
	 * @param String
	 *            fileName
	 */
	public static void exportIcbcOrders_Day(String company,
			List<String> orderNoList, String version, String date,
			String fileName) {
		IcbcOrderTest test = null;
		List<IcbcOrderResult> orderResultList = new ArrayList<IcbcOrderResult>();

		int orderNoListSize = orderNoList.size();
		System.out.println("orderNoListSize:" + orderNoListSize);

		for (int i = 0; i < orderNoListSize; i++) {
			String orderNo = orderNoList.get(i);

			test = new IcbcOrderTest(company, version);
			test.setTranDate(date);
			test.setOrderNum(orderNo);
			IcbcOrderResult result = test.PostQueryCmd();
			if (result != null) {
				if ("1".equals(result.getTranStat())) {
					orderResultList.add(result);
				}
			}
		}
		String content = getExportInfoByOrderResultList(orderResultList);
		try {
			boolean flag = exportTxtFile(content, fileName);
			if (flag) {
				System.out.println("报表导出成功");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description:获取导出报表内容
	 * @param List
	 *            <IcbcOrderResult> list
	 * @param String
	 *            date
	 * @return String
	 */
	public static String getExportInfoByOrderResultList(
			List<IcbcOrderResult> list) {
		String content = "";
		BigDecimal totalAmount = new BigDecimal(0);
		int count = 0;
		StringBuffer str = new StringBuffer("工行订单明细" + "\n");
		str.append("id" + "		" + "钱门充值订单号" + "		");
		str.append("银行指令序号" + "		");
		str.append("tranDate" + "		" + "tranTime" + "		");
		str.append("Stat" + "		" + "amount" + "\n");
		for (int i = 0; i < list.size(); i++) {
			IcbcOrderResult result = list.get(i);
			String centAmount = result.getAmount();
			BigDecimal bAmount = BankUtil.getDollarAmount(centAmount);
			String serialNo = getserialNo(i);

			str.append(serialNo + "		");
			str.append(result.getOrderNum() + "		");
			str.append(result.getTranSerialNum() + "		");
			str.append(result.getTranDate() + "		");
			str.append(result.getTranTime() + "		");
			str.append(result.getTranStat() + "		");
			str.append(bAmount + "\n");

			totalAmount = totalAmount.add(bAmount);
			count++;
		}

		IcbcOrderResult firstOrder = list.get(0);
		IcbcOrderResult lastOrder = list.get(list.size() - 1);
		String beginTime = firstOrder.getTranDate() + " "
				+ firstOrder.getTranTime();
		String endTime = lastOrder.getTranDate() + " "
				+ lastOrder.getTranTime();

		StringBuffer totalStr = new StringBuffer("--------------------" + "\n");
		totalStr.append("开始时间:" + beginTime + "\n");
		totalStr.append("结束时间:" + endTime + "\n");
		totalStr.append("合计:" + "\n");
		totalStr.append("笔数：" + count + "\n");
		totalStr.append("总额:" + totalAmount + "\n");
		content = str.append(totalStr).toString();
		return content;
	}

	/**
	 * @description:获取序列号
	 * @param int
	 *            i
	 * @return String serialNo
	 */
	public static String getserialNo(int i) {
		String serialNo = "00";
		if (i == 0) {
			serialNo = "0" + String.valueOf(1);
		} else if (0 < i && i < 9) {
			serialNo = "0" + String.valueOf(i + 1);
		} else if (i == 9) {
			serialNo = String.valueOf(10);
		} else if (i == 10) {
			serialNo = String.valueOf(11);
		} else {
			serialNo = String.valueOf(i + 1);
		}
		return serialNo;
	}

	/**
	 * 导出文本文件到指定路径
	 */
	public static boolean exportTxtFile(String content, String fullFileName)
			throws AppException {
		boolean isSuccess = false;
		try {
			char buffer[] = new char[(content.length())];
			content.getChars(0, content.length(), buffer, 0);
			FileWriter writer = new FileWriter(fullFileName, true);
			for (int i = 0; i < content.length(); i++) {
				writer.write(buffer[i]);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
