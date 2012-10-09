package com.fordays.fdpay.bank.rmi.test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import com.fordays.fdpay.bank.rmi.BankOrderRemoteBizImp;
import com.fordays.fdpay.bank.rmi.RMINode;
import com.fordays.fdpay.bank.rmi.RMIServer;
import com.fordays.fdpay.bank.rmi.RemoteBiz;

public class TestRMI {
	public static void main(String[] args) {
		RMINode rmiNode = new RMINode();
		rmiNode.setHost("192.168.1.91");
		rmiNode.setPort(8888);
		rmiNode.setRmiName("RHello");

		try {
			Registry registry = RMIServer.createRegistry(rmiNode.getPort());

			IHello biz = new IHelloImp();

			// registry.bind("RHello", biz);// 与下一句等效
			System.out.println("localregistry bind success..");

			String rmiUrl = rmiNode.getRMIURL(rmiNode);
			Naming.bind(rmiUrl, biz);
			System.out.println(rmiUrl + ",绑定成功");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
