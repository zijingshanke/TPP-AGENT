package com.fordays.fdpay.bank.rmi;

/**
 * 单例模式管理RMIServer测试
 */
public class TestSingleton {

	public static void main(String[] args) {
		RMIServer2 server = new RMIServer2();
		server.run();

		try {
			Thread.sleep(new Long(2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		server.setFlag(false);
		server.run();
		// RMIServerManager.put(server);
	}
}