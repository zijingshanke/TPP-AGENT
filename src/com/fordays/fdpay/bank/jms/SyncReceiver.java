//package com.fordays.fdpay.bank.jms;
//
//import java.util.Properties;
//
//import javax.jms.Queue;
//import javax.jms.QueueConnection;
//import javax.jms.QueueConnectionFactory;
//import javax.jms.QueueReceiver;
//import javax.jms.QueueSession;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//
///**
// * JMS 接收程序
// */
//public class SyncReceiver {
//
//	public static void main(String argv[]) {
//		new SyncReceiver();
//	}
//
//	/**
//	 * PTP模型
//	 */
//	public SyncReceiver() {
//		try {
//			QueueConnectionFactory myQConnFactory;
//			Queue myQueue;
//
//			Properties properties = new Properties();
//			properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
//			properties.put(Context.URL_PKG_PREFIXES, "org.jnp.interfaces");
//			properties.put(Context.PROVIDER_URL, "localhost");
//
//			Context ctx = new InitialContext(properties);
//			myQConnFactory = (QueueConnectionFactory) ctx.lookup("UIL2ConnectionFactory");
//			myQueue = (Queue) ctx.lookup("queue/testQueue");
//
//			ctx.bind("SyncReceiver", myQueue);
//
//			QueueConnection con = myQConnFactory.createQueueConnection();
//			QueueSession session = con.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
//			QueueReceiver receiver = session.createReceiver(myQueue);
//
//			con.start();
//
//			for (int i = 0; i < 10; i++) {
//				TextMessage textMessage = (TextMessage) receiver.receive();
//				System.out.println("Got: " + textMessage.getText());
//			}
//
//			con.close();
//			ctx.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
