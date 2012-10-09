package com.fordays.fdpay.bank.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.fordays.fdpay.bank.BankUtil;

public class RMIServer2 implements Runnable {
	private String host = "";
	private int port = 0;
	private RemoteBiz object = null;
	private String objectName = "";

	private boolean flag = true;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public RMIServer2() {
	}

	public void run() {
		System.out.println("begin run..");
		while (!flag) {
			System.out.println("flag false exit...");
			return;
		}

		System.out.println("flag true  run...");
	}

	/**
	 * 初始化RMI服务端监听，单独使用RMIServer
	 */
	public void initServer(String myHost, int myPort, RemoteBiz obj)
			throws RemoteException {
		init(myHost, myPort, obj);
	}

	/**
	 * 初始化RMI参数，单独使用RMIServer
	 */
	public void init(String myHost, int myPort, RemoteBiz obj)
			throws RemoteException {
		host = myHost;
		port = myPort;
		object = obj;
		objectName = obj.getRemoteObjName();
	}

	/**
	 * 初始化RMI服务端监听,子类扩展实现
	 * 
	 */
	public void initServer() throws RemoteException {

	}

	/**
	 * 批量绑定远程对象
	 */
	public static void init(List<RMINode> nodelist, RemoteBiz biz) {
		int nodeSize = nodelist.size();

		if (nodeSize < 1) {
			System.out.println("没有加载需要绑定的节点,检查rmi-config.xml和加载RMINode的方法");
		}

		System.out.println("开始批量绑定远程对象");

		for (int i = 0; i < nodeSize; i++) {
			RMINode rmiNode = (RMINode) nodelist.get(i);
			startListener(rmiNode, biz);
		}
		System.out.println("结束批量绑定远程对象");
	}

	/**
	 * 批量解除远程对象绑定
	 */
	public static void unbind(List<RMINode> nodelist, RemoteBiz biz) {
		int nodeSize = nodelist.size();

		if (nodeSize < 1) {
			System.out.println("没有加载需要绑定的节点,检查rmi-config.xml和加载RMINode的方法");
		}

		System.out.println("开始批量解除远程对象绑定");

		for (int i = 0; i < nodeSize; i++) {
			RMINode rmiNode = (RMINode) nodelist.get(i);
			unbindListener(rmiNode, biz);
		}
		System.out.println("结束批量解除远程对象绑定");
	}

	/**
	 * 启动RMI对象监听
	 * 
	 * @param RMINode
	 *            rmiNode
	 * @param RemoteBiz
	 *            biz
	 */
	public static void startListener(RMINode rmiNode, RemoteBiz biz) {
		String host = rmiNode.getHost();
		int port = rmiNode.getPort();
		String rmiName = rmiNode.getRmiName();
		String rmiUrl = RMINode.getRMIURL(rmiNode);

		if (BankUtil.isLocalHost(host)) {

			try {
				Registry registry = createRegistry(port);

				// 方法一:registry bind rmiName
				registry.bind(rmiName, biz);// 与下一句等效
				System.out.println(host + ":" + port + "/" + rmiName + ",绑定成功");
				// ---------------------------

				// 方法二:Name bind rmiUrl
				// Naming.bind(rmiUrl, biz);
				// System.out.println(rmiUrl + "绑定成功!");
				// } catch (MalformedURLException e) {
				// System.out.println("发生URL畸形异常！rmiUrl:" + rmiUrl);
				// e.printStackTrace();
			} catch (RemoteException e) {
				System.out.println("创建远程对象发生异常!rmiUrl:" + rmiUrl);
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				System.out.println("发生重复绑定对象异常!rmiUrl:" + rmiUrl);
				e.printStackTrace();
			}
		} else {
			System.out.println("不能远程创建" + host + "的RMI Server");
		}
	}

	/**
	 * 创建一个Registry对象. LocateRegistry用于获取名字服务或创建名字服务.
	 * 调用LocateRegistry.createRegistry(int
	 * port)方法可以在某一特定端口创建名字服务,从而用户无需再手工启动rmiregistry
	 * 
	 * @return 返回一个Registry对象
	 */
	public static Registry createRegistry(int port) {
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(port); // 如果该端口未被注册，则抛异常
			registry.list(); // 拿到该端口注册的rmi对象
		} catch (final Exception e) {
			try {
				registry = LocateRegistry.createRegistry(port);// 捕获异常，端口注册
			} catch (final Exception ee) {
				ee.printStackTrace();
			}
		}
		return registry;
	}

	/**
	 * 解除RMI对象绑定
	 * 
	 * @param RMINode
	 *            rmiNode
	 * @param RemoteBiz
	 *            biz
	 */
	public static void unbindListener(RMINode rmiNode, RemoteBiz biz) {
		String host = rmiNode.getHost();
		int port = rmiNode.getPort();
		String rmiName = rmiNode.getRmiName();

		if (BankUtil.isLocalHost(host)) {
			String rmiUrl = RMINode.getRMIURL(rmiNode);
			try {
				// 方法取决于startListener()使用的是registry还是Naming
				// 方法一：registry unbind
				try {
					Registry registry = LocateRegistry.getRegistry(port);
					registry.unbind(rmiName);// 取消服务绑定
				} catch (NotBoundException e) {
					e.printStackTrace();
				}

				// ---方法二：Naming unbind
				// try {
				// Naming.unbind(rmiUrl, biz);//取消绑定()
				// } catch (MalformedURLException e) {
				// e.printStackTrace();
				// }

				// 注销端口占用
				// logoutPort(registry);

				System.out.println(host + biz.getRemoteObjName() + "解除绑定成功!");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {

			System.out.println("不能远程访问" + host + "的RMI Server");
		}
	}

	/**
	 * 注销RMI端口占用(调试中，有问题，暂不使用)
	 */
	public static void logoutPort(Registry registry) {
		try {
			// 方案一：
			UnicastRemoteObject.unexportObject(registry, true);
			/**
			 * 异常:java.rmi.NoSuchObjectException: object not exported
			 */

			// 方案二:据说与上一句等效
			PortableRemoteObject.unexportObject(registry);
			/**
			 * 异常：java.rmi.NoSuchObjectException: Can only unexport a server
			 * object.
			 */
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ServletContextListener接口实现,当RMIServer做为项目单独启动项时使用
	 */
	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		try {
			initServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ServletContextListener接口实现,当RMIServer做为项目单独启动项时使用
	 */
	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {

	}

	/**
	 * 单机测试方法
	 */
	public void testStartListener() {
		try {
			// 创建一个远程对象
			// IHello rhello = new IHelloImp();

			// 本地主机上的远程对象注册表Registry的实例，并指定端口为8888，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上
			LocateRegistry.createRegistry(8888);

			// 把远程对象注册到RMI注册服务器上，并命名为RHello
			// 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）

			Naming.bind("rmi://" + host + ":" + port + objectName, object);

			System.out.println(">>>>>INFO:远程" + object + "对象绑定成功！");
		} catch (RemoteException e) {
			System.out.println("创建远程对象发生异常！");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("发生重复绑定对象异常！");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("发生URL畸形异常！");
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public RemoteBiz getObject() {
		return object;
	}

	public void setObject(RemoteBiz object) {
		this.object = object;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
