package com.fordays.fdpay.bank.rmi.test;

import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import com.fordays.fdpay.bank.rmi.BankOrderRemoteBizImp;
import com.fordays.fdpay.bank.rmi.RemoteBiz;

/**
 * 创建RMI注册表，启动RMI服务，并将远程对象注册到RMI注册表中
 */
public class HelloServer {

	public static void main(String args[]) {
		// getLocalIP();

		statServer();

		// rebindRMIServer();
	}

	public static void statServer() {
		// 创建一个远程对象
		IHello rhello = null;
		// RemoteBiz rhello = null;

		try {
			new IHelloImp();
			// rhello = new BankOrderRemoteBizImp();

			// 本地主机上的远程对象注册表Registry的实例，并指定端口为8888，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上
			LocateRegistry.createRegistry(8888);
		} catch (Exception e) {
		}

		// 把远程对象注册到RMI注册服务器上，并命名为RHello
		// 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）

		try {
			Naming.bind("rmi://192.168.1.91:8888/RHello", rhello);
			// Naming.bind("rmi://192.168.1.91:8888/bankOrder", rhello);

			System.out.println(rhello + "绑定成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rebindRMIServer() {
		try {
			RemoteBiz rhello = new BankOrderRemoteBizImp();

			Registry registry = LocateRegistry.getRegistry(8888);

			printRegister(registry);// -------

			String rmiUrl = "rmi://192.168.1.91:8888/bankOrder";

			// Naming.rebind(rmiUrl, rhello);

			registry.unbind("bankOrder");
			UnicastRemoteObject.unexportObject(rhello, true);
			// PortableRemoteObject.unexportObject(registry);

			printRegister(registry);

			System.out.println(rmiUrl + ",解除绑定成功");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Naming.unbind("rmi://192.168.1.91:8888/bankOrder");
	// System.out.println("解除对象绑定");
	// Registry myLocalReg = LocateRegistry.createRegistry(8888);//
	// local端的register
	// Registry myLocalReg
	// =LocateRegistry.getRegistry();//remote端的register
	// UnicastRemoteObject.unexportObject(myLocalReg, true);
	// PortableRemoteObject.unexportObject(myLocalReg);
	// Naming.rebind("rmi://192.168.1.91:8889/RHello", rhello);
	// Naming.unbind("rmi://192.168.1.91:8888/bankOrder");

	// } catch (RemoteException e) {
	// System.out.println("创建远程对象发生异常！");
	// e.printStackTrace();
	// } catch (AlreadyBoundException e) {
	// System.out.println("发生重复绑定对象异常！");
	// e.printStackTrace();
	// } catch (MalformedURLException e) {
	// System.out.println("发生URL畸形异常！");
	// e.printStackTrace();

	public static void printRegister(Registry registry) {
		System.out.println("-----------print registry:" + registry);
		String[] list = null;
		try {
			list = registry.list();
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < list.length; i++) {
			System.out.println("-----------print registry.list()--");
			System.out.println(list[i]);
		}
	}

	public static void getLocalIP() {
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			System.out.println("localhost" + InetAddress.getLocalHost());
			System.out.println("localhost" + InetAddress.getLocalHost());
			System.out.println("address:" + address);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
