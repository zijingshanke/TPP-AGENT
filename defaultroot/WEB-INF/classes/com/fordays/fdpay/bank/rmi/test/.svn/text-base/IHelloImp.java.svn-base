package com.fordays.fdpay.bank.rmi.test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.fordays.fdpay.bank.BankOrderStore;
import com.fordays.fdpay.bank.BankUtil;

public class IHelloImp extends UnicastRemoteObject implements IHello {

	protected IHelloImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String helloWorld() throws RemoteException {
		return "Hello World";
	}

	public String sayHelloWordToSomeBody(String somebody)
			throws RemoteException {

		return "hello world," + somebody;
	}

	public String addOrder(String orderNO) throws RemoteException {
		System.out.println(BankUtil.getLocalHost());
		System.out.println(BankUtil.getLocalHost() + "addOrder(" + orderNO
				+ ")");

		BankOrderStore.addOrderString(orderNO);
		return null;
	}

	public boolean containsOrder(String orderNO) throws RemoteException {
		System.out.println(BankUtil.getLocalHost() + "containsOrder(" + orderNO
				+ ")");

		return BankOrderStore.containsExistOrder_String(orderNO);
	}

	public void removeOrder(String orderNO) throws RemoteException {
		BankOrderStore.removeOverdueOrders_String();
	}

}
