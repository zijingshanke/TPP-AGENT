package com.fordays.fdpay.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import com.fordays.fdpay.bank.rmi.BankOrderRemoteBiz;
import com.fordays.fdpay.bank.rmi.BankOrderRemoteBizImp;
import com.fordays.fdpay.bank.rmi.RMINode;
import com.fordays.fdpay.bank.rmi.RMIServer;

/**
 * 网上银行订单库 远程方法调用,适应服务器集群
 */
public class BankOrderStoreRemote {

	public static void addBankOrder(String orderNo) {
		LogUtil myLog = new LogUtil(false, false, BankOrderStoreRemote.class);
		List<RMINode> nodeList = RMINode.getRMINodeList("bankOrder");
		for (int i = 0; i < nodeList.size(); i++) {
			RMINode rmiNode = (RMINode) nodeList.get(i);

			String rmiUrl = RMINode.getRMIURL(rmiNode);

			// 调用在RMI服务注册表中查找对象，并调用其上的方法
			BankOrderRemoteBiz remoteBiz;
			try {
				remoteBiz = (BankOrderRemoteBiz) Naming.lookup(rmiUrl);
				remoteBiz.addBankOrder(orderNo);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
				try {
					remoteBiz=new BankOrderRemoteBizImp();
					myLog.info(rmiUrl+"访问异常,手动启动RMI监听");
					RMIServer.startListener(rmiNode, remoteBiz);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}	
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean containsBankOrder(String orderNo) {
		LogUtil myLog = new LogUtil(false, false, BankOrderStoreRemote.class);
		myLog.info("---begin---BankOrderStoreRemote.containsBankOrder()");

		boolean result = false;

		List<RMINode> nodeList = RMINode.getRMINodeList("bankOrder");
		for (int i = 0; i < nodeList.size(); i++) {
			RMINode rmiNode = (RMINode) nodeList.get(i);
			String rmiUrl = RMINode.getRMIURL(rmiNode);
			myLog.info("远程访问对象:" + rmiUrl);

			// 调用在RMI服务注册表中查找对象，并调用其上的方法
			BankOrderRemoteBiz remoteBiz;
			try {
				remoteBiz = (BankOrderRemoteBiz) Naming.lookup(rmiUrl);
				result = remoteBiz.containsBankOrder(orderNo);
			} catch (MalformedURLException e) {
				myLog.error("URL格式错误");
				e.printStackTrace();					
			} catch (RemoteException e) {
				myLog.error("创建远程对象发生异常");
				e.printStackTrace();				
				try {
					remoteBiz=new BankOrderRemoteBizImp();
					myLog.info(rmiUrl+"访问异常,手动启动RMI监听");
					RMIServer.startListener(rmiNode, remoteBiz);
				} catch (RemoteException e1) {					
					e1.printStackTrace();
				}			
			} catch (NotBoundException e) {
				myLog.error("没有找到远程对象");
				e.printStackTrace();
			}

			myLog.info("result:" + result);
			if (result == true) {
				myLog.info("---end-1-containsBankOrder(),result:" + result);
				return true;
			}
		}
		myLog.info("---end-2-containsBankOrder(),result:" + result);
		return result;
	}
	
	

	public static void removeBankOrder(String orderNo) {
		try {
			List<RMINode> nodeList = RMINode.getRMINodeList("bankOrder");
			for (int i = 0; i < nodeList.size(); i++) {
				RMINode rmiNode = (RMINode) nodeList.get(i);

				String rmiUrl = RMINode.getRMIURL(rmiNode);

				// 调用在RMI服务注册表中查找对象，并调用其上的方法
				BankOrderRemoteBiz remoteBiz = (BankOrderRemoteBiz) Naming
						.lookup(rmiUrl);

				remoteBiz.removeBankeOrder(orderNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试方法
	 */
	public static void TestBankOrderStoreRemote(String orderNo) {
		boolean flag = containsBankOrder(orderNo);
		System.out.println("---1---containsBankOrder(" + orderNo + ")-" + flag);

		addBankOrder(orderNo);
		flag = containsBankOrder(orderNo);
		System.out.println("---2---addBankOrder(" + orderNo + ")-" + flag);

		System.out.println("---3---containsBankOrder(" + orderNo + ")-" + flag);

		removeBankOrder(orderNo);
		flag = containsBankOrder(orderNo);
		System.out.println("---4---containsBankOrder(" + orderNo + ")-" + flag);
	}
}
