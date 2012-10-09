package com.fordays.fdpay.bank.rmi.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程接口，必须继承Remote接口，其中需要远程调用的方法必须抛出RemoteException异常
 * 
 */
public interface IHello extends Remote {
	public String addOrder(String orderNO) throws RemoteException;

	public boolean containsOrder(String orderNO) throws RemoteException;
	
	public void removeOrder(String orderNO)throws RemoteException;

	public String helloWorld() throws RemoteException;

	public String sayHelloWordToSomeBody(String somebody)
			throws RemoteException;
}
