<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>

<%@page import="com.fordays.fdpay.bank.rmi.BankOrderRemoteBiz"%>
<%@page import="com.fordays.fdpay.bank.rmi.BankOrderRemoteBizImp"%>
<%@page import="java.rmi.*"%>
<%@page import="java.rmi.registry.*"%>
<%@page import="java.net.MalformedURLException"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
</head>
<!-- JSP页面:bank/rmi/startRMIListener.jsp -->
<%
	String host = request.getParameter("host");
	String port = request.getParameter("port");
	String objectName = request.getParameter("objectName");

	out.println("host:" + host + ",port:" + port + ",objectName:"
			+ objectName + "\n");
			
	out.println("-------------" + "\n");
	//手工启动RMI监听实例
	try {
		// 创建一个远程对象
		BankOrderRemoteBiz object = new BankOrderRemoteBizImp();

		// 本地主机上的远程对象注册表Registry的实例，并指定端口为8888，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上

		int myport=Integer.parseInt(port);
		LocateRegistry.createRegistry(myport);

		// 把远程对象注册到RMI注册服务器上，并命名为RHello
		// 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）

		Naming.bind("rmi://" + host + ":" + port + objectName, object);

		out.println(">>>>>INFO:" + object + "对象绑定成功！");
	} catch (RemoteException e) {
		out.println("创建远程对象发生异常！");
		e.printStackTrace();
	} catch (AlreadyBoundException e) {
		out.println("发生重复绑定对象异常！");
		e.printStackTrace();
	} catch (MalformedURLException e) {
		out.println("发生URL畸形异常！");
		e.printStackTrace();
	}
%>

