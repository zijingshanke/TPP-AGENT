package com.fordays.fdpay.bank.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.fordays.fdpay.bank.BankOrderStore;

public class BankOrderRemoteBizImp extends UnicastRemoteObject implements
		BankOrderRemoteBiz {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankOrderRemoteBizImp() throws RemoteException {
		super();
	}

	
	public String getRemoteObjName() throws RemoteException {
		String objName = "/bankOrder";
		return objName;
	}

	public void addBankOrder(String orderNo) throws RemoteException {
		BankOrderStore.addOrderString(orderNo);
	}

	public boolean containsBankOrder(String orderNo) throws RemoteException {
		boolean result = false;
		try {
			result = BankOrderStore.containsExistOrder_String(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void removeBankeOrder(String orderNo) throws RemoteException {
		BankOrderStore.removeOrder_String(orderNo);		
	}
}
