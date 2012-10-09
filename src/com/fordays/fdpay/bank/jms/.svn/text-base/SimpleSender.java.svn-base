//package com.fordays.fdpay.bank.jms;
//
//import java.util.Properties;
//import javax.jms.Queue;
//import javax.jms.QueueConnection;
//import javax.jms.QueueConnectionFactory;
//import javax.jms.QueueSender;
//import javax.jms.QueueSession;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//
///**
// * JMS 示例发送程序
// */
//public class SimpleSender {
//	public static void main(String argv[]) {
//		//new SimpleSender();
//		printInfo();
//	}
//	
//	public static void printInfo(){
//		System.out.println(Context.INITIAL_CONTEXT_FACTORY);//java.naming.factory.initial
//		System.out.println(Context.URL_PKG_PREFIXES);//java.naming.factory.url.pkgs
//		System.out.println(Context.PROVIDER_URL);//java.naming.provider.url
//		
//		System.out.println(System.getProperty("java.naming.factory.initial"));
//		System.out.println(System.getProperty("java.naming.factory.url.pkgs"));
//		System.out.println(System.getProperty("java.naming.provider.url"));		
//	}
//
//	public SimpleSender() {
//		try {
//			QueueConnectionFactory myQConnFactory;// 连接工厂
//			Queue myQueue;
//
//			Properties properties = new Properties();
//			properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
//			properties.put(Context.URL_PKG_PREFIXES, "org.jnp.interfaces");
//			properties.put(Context.PROVIDER_URL, "localhost");
//
//			Context ctx = new InitialContext(properties);
//
//			myQConnFactory = (QueueConnectionFactory) ctx.lookup("UIL2ConnectionFactory");
//			myQueue = (Queue) ctx.lookup("queue/testQueue");
//
//			ctx.bind("SimpleSender", myQueue);
//
//			//创建连接
//			QueueConnection con = myQConnFactory.createQueueConnection();
//
//			// 一个发送消息的线程
//			QueueSession session = con.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
//
//			/**
//			 * TextMessage,JMS API 定义的五种消息类型之一。java.lang.String对象，例如xml文件内容
//			 */
//			TextMessage textMessage = session.createTextMessage();
//			
//			
//			QueueSender sender = session.createSender(myQueue);
//			con.start();
//
//			for (int i = 0; i < 10; i++) {
//				textMessage.setText("Hello World #" + i);
//				sender.send(textMessage);//客户端发送消息到队列
//			}
//			con.close();
//			ctx.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
