package com.fordays.fdpay.bank.rmi;

import java.rmi.RemoteException;
import java.util.List;

/**
 * 网上银行订单RMI Server监听(生产环境未使用)
 * 
 * 当需要将BankOrderRMI做为单独启动项时使用
 */
public class BankOrderRMI extends RMIServer {

	public BankOrderRMI() {
		super();
	}

	public void initServer() throws RemoteException {
//		List<RMINode> nodeList = RMINode.getRMINodeList("bankOrder");
//		BankOrderRemoteBiz bizObj = new BankOrderRemoteBizImp();
//		init(nodeList, bizObj);
	}
}
