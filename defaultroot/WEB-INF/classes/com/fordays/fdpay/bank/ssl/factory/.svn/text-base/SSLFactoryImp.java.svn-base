package com.fordays.fdpay.bank.ssl.factory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import com.neza.exception.AppException;

public class SSLFactoryImp {
	private ManagerFactory managerFactory;

	public SSLSocketFactory getSSLSocketFactory_IBM(String keystore,
			String keypass, String truststore, String trustpass)
			throws AppException {
		SSLSocketFactory factory = null;
		try {
			KeyManager[] km = managerFactory.getKeyManager("IbmX509", "JKS",
					keystore, keypass);

			TrustManager[] tm = managerFactory.getTrustManager("IbmX509",
					"JKS", truststore, trustpass);

			// 获得 SSLContext 类的实例
			SSLContext sslContext = SSLContext.getInstance("SSL", "IbmJSSE");

			// 使用当前的信任管理员（封装带信任的 CA）以及密钥管理员（封装客户端的证书）初始化该实例
			sslContext.init(km, tm, new java.security.SecureRandom());

			// 使用当前的 SSLContext 对象来获取 SSL Socket Factory 并且实例化其中的 SSL套接字
			factory = sslContext.getSocketFactory();

			// 关键是 SSLContext 类并不是唯一的；也就是说，每个 getInstance 调用返回了不同的
			// SSLContext 对象。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
	}

	public SSLSocketFactory getSSLSocketFactory_SUN() throws AppException {
		return null;
	}

	public void printProvider(KeyManagerFactory kmf, TrustManagerFactory tmf)
			throws AppException {
		System.out.println("KeymanagerFactory--------");
		KeyManager[] km = kmf.getKeyManagers();
		for (int i = 0; i < km.length; i++) {
			System.out.println(kmf.getKeyManagers()[i] + "---"
					+ kmf.getProvider());
		}

		System.out.println("TrustManagerFactory--------");
		TrustManager[] tm = tmf.getTrustManagers();
		for (int j = 0; j < tm.length; j++) {
			System.out.println(tm[j] + "--provider:" + tmf.getProvider());
		}
	}

	public void setManagerFactory(ManagerFactory managerFactory) {
		this.managerFactory = managerFactory;
	}
}
