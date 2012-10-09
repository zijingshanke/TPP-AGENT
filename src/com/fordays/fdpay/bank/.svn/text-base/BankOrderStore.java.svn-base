package com.fordays.fdpay.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import com.neza.tool.DateUtil;

public class BankOrderStore {
	/**
	 * Note that this implementation is not synchronized. If multiple threads
	 * access a set concurrently, and at least one of the threads modifies the
	 * set, it must be synchronized externally. This is typically accomplished
	 * by synchronizing on some object that naturally encapsulates the set. If
	 * no such object exists, the set should be "wrapped" using the
	 * Collections.synchronizedSet method. This is best done at creation time,
	 * to prevent accidental unsynchronized access to the HashSet instance:
	 * 
	 * Set s = Collections.synchronizedSet(new HashSet(...));
	 * 
	 * HashMap、ArrayList等同理； Map m =
	 * Collections.synchronizedMap(newHashMap(...)); List list =
	 * Collections.synchronizedList(new ArrayList(...));
	 * 
	 */

	public static Set<String> orderSet = Collections
			.synchronizedSet(new HashSet<String>());//
	public static ArrayList<String> orderList = new ArrayList<String>();//
	public static String orderString = "";//

	/**
	 * 
	 */

	/**
	 * 添加订单
	 */
	public static void addOrderSet(String orderNo) {
		BankOrderStore.orderSet.add(orderNo);
	}

	public static void addOrderList(String orderNo) {
		BankOrderStore.orderList.add(orderNo);
	}

	public static void addOrderString(String orderNo) {
		String orderNos = BankUtil.appendString(BankOrderStore.orderString,
				orderNo, ",");

		BankOrderStore.orderString = orderNos;
	}

	/**
	 * 判断静态订单库中是否存在此订单
	 * 
	 * @param String
	 *            orderNo
	 * @return boolean
	 */
	public static boolean containsExistOrder_Set(String orderNo) {
		Iterator<String> iterator = orderSet.iterator();
		while (iterator.hasNext()) {
			String order = (String) iterator.next();
			if (orderNo.equals(order)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static boolean containsExistOrder_List(String orderNo) {
		for (int i = 0; i < orderList.size(); i++) {
			String order = orderList.get(i);
			if (orderNo.equals(order)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	
	public static boolean containsExistOrder_String(String orderNo) {
		int flag = orderString.indexOf(orderNo);

		if (flag >= 0) { // 大于0 则表示存在 为-1 则表示不存在
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除过期订单，Set，非当日
	 */
	public static void removeOverdueOrders_Set() {
		Iterator<String> iterator = orderSet.iterator();
		while (iterator.hasNext()) {
			String order = (String) iterator.next();
			String orderDate = order.substring(1, 9);

			String today = DateUtil.getDateString("yyyyMMdd");

			if (orderDate.equals(today) == false) {
				orderSet.remove(order);
				iterator = orderSet.iterator();
			}
		}
	}

	/**
	 * 删除过期订单，List，非当日
	 */
	public static void removeOverdueOrders_List() {
		for (int i = 0; i < orderList.size(); i++) {
			String order = orderList.get(i);
			String orderDate = order.substring(1, 9);

			String today = DateUtil.getDateString("yyyyMMdd");
			if (orderDate.equals(today) == false) {
				orderList.remove(order);
			}
		}
	}

	/**
	 * 删除过期订单，String，非当日
	 */
	public static void removeOverdueOrders_String() {
		String[] orderArray = BankUtil.getSplitString(orderString, ",");
		int orderLength = orderArray.length;
		String today = DateUtil.getDateString("yyyyMMdd");

		for (int i = 0; i < orderLength; i++) {
			String order = orderArray[i];
			if (order == null || "".equals(order)) {

			} else {
				String orderDate = order.substring(1, 9);
				if (orderDate.equals(today) == false) {
					orderArray = BankUtil.delArrayCellByStr(orderArray, order);
					orderString = BankUtil.getStringByStringArray(orderArray,
							",");
					orderLength = orderArray.length;
				}
			}
		}
	}

	/**
	 * 删除指定的订单（订单库为String）
	 */
	public static void removeOrder_String(String orderNo) {
		String[] orderArray = BankUtil.getSplitString(orderString, ",");
		int orderLength = orderArray.length;
		for (int i = 0; i < orderLength; i++) {
			String order = orderArray[i];
			if (orderNo.equals(order)) {
				orderArray = BankUtil.delArrayCellByStr(orderArray, orderNo);
				orderString = BankUtil.getStringByStringArray(orderArray, ",");
				orderLength = orderArray.length;
			}
		}
	}

	/**
	 * 测试
	 */
	public static void main(String[] args) {
		System.out.println("---begin--Test orderSet------");
		addOrderSet("C20091104000261");
		boolean flag = containsExistOrder_Set("C20091104000261");
		System.out.println("f==" + flag);
		removeOverdueOrders_Set();
		flag = containsExistOrder_Set("C20091104000261");
		System.out.println("f==" + flag);
		System.out.println("---end--Test orderSet------" + "\n");

		System.out.println("---begin--Test orderList------");
		addOrderList("C20091104000261");
		boolean flag2 = containsExistOrder_List("C20091104000261");
		System.out.println("f==" + flag2);
		removeOverdueOrders_List();
		flag2 = containsExistOrder_List("C20091104000261");
		System.out.println("f==" + flag2);
		System.out.println("---end--Test orderList------" + "\n");

		System.out.println("---begin--Test orderString------");
		addOrderString("C20091109000001");
		addOrderString("C20091109000002");
		addOrderString("C20091108000055");
		System.out.println("--orders String--" + orderString);
		boolean flag3 = containsExistOrder_String("C20091108000055");
		System.out.println("f=" + flag3);

		removeOrder_String("C20091108000055");
		flag3 = containsExistOrder_String("C20091108000055");
		System.out.println("orders String--" + orderString);
		System.out.println("f=" + flag3);

		removeOverdueOrders_String();
		System.out.println("orders String--" + orderString);
		System.out.println("---end--Test orderString------" + "\n");
	}
}
