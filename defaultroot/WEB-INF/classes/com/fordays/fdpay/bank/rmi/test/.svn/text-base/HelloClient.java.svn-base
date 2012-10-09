package com.fordays.fdpay.bank.rmi.test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.fordays.fdpay.bank.rmi.BankOrderRemoteBiz;


/**
 * 客户端测试，在客户端调用远程对象上的远程方法，并返回结果。
 */

public class HelloClient {
	public static void main(String args[]) {

		try {
			// // 在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
			// IHello rhello = (IHello)
			// Naming.lookup("rmi://192.168.1.91:8888/RHello");
			// boolean result = rhello.containsOrder("123456");
			// System.out.println(result);
			// System.out.println(BankUtil.getLocalHost()+" order
			// 888888:"+rhello.containsOrder("888888"));
			// System.out.println(rhello.helloWorld());
			// System.out.println(rhello.sayHelloWordToSomeBody("熔岩"));

			// 在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
			BankOrderRemoteBiz rhello = (BankOrderRemoteBiz) Naming
					.lookup("rmi://202.104.150.234:3692/bankOrder");
			
			boolean result = rhello.containsBankOrder("123456");
			System.out.println("--------------");
			System.out.println(rhello.getRemoteObjName());
			System.out.println(result);

		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
