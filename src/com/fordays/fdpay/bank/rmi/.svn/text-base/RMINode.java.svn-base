package com.fordays.fdpay.bank.rmi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;

/**
 * 远程方法调用节点
 */
public class RMINode {
	private String host = "";
	private int port = 0;
	private String rmiName = "";

	/**
	 * RMI配置文件路径
	 */
	private final static String configXmlUrl = Constant.WEB_INFO_PATH
			+ "rmi-config.xml";
	private static Document configDoc = null;

	public static Document getConfigDoc() {
		configDoc = BankUtil.loadXml(configXmlUrl);
		return configDoc;
	}

	/**
	 * 获取远程方法调用节点
	 * 
	 * @param String
	 *            rmiName
	 * @return List<RMINode> nodeList
	 */
	public static List<RMINode> getRMINodeList(String rmiName) {
		List<RMINode> nodelist = new ArrayList<RMINode>();
		Document doc = getConfigDoc();
		List nodes = doc.selectNodes("//rmi-config/listeners/listener/node");
		Iterator it = nodes.iterator();
		while (it.hasNext()) {
			Element elm = (Element) it.next();

			if (rmiName.equals(elm.attribute("rmiName").getValue())) {
				RMINode rminode = new RMINode();
				rminode.setHost(elm.attribute("host").getValue());
				rminode.setPort(Integer.parseInt(elm.attribute("port")
						.getValue()));
				rminode.setRmiName(rmiName);
				nodelist.add(rminode);
			}
		}
		return nodelist;
	}

	/**
	 * 获取远程方法绑定URL
	 * 
	 * @param RMINode
	 *            rmiNode
	 * @return String rmiUrl
	 */
	public static String getRMIURL(RMINode rmiNode) {
		String host = rmiNode.getHost();
		int port = rmiNode.getPort();
		String rmiName = rmiNode.getRmiName();
		String rmiUrl = "rmi://" + host + ":" + port + "/" + rmiName;

//		System.out.println("rmiUrl:" + rmiUrl);

		return rmiUrl;
	}
	
	

	/**
	 * 测试方法
	 */
	public static void TestGetNodeList(String rmiName) {
		List nodeList = new ArrayList();
		Document doc = BankUtil.loadXml("E:\\rmi-config2.xml");

		List nodes = doc.selectNodes("//rmi-config/listeners/listener/node");
		Iterator it = nodes.iterator();
		// System.out.println("nodes:" + nodes);
		while (it.hasNext()) {
			Element elm = (Element) it.next();
			if (rmiName.equals(elm.attribute("rmiName").getValue())) {
				RMINode rminode = new RMINode();
				rminode.setHost(elm.attribute("host").getValue());
				rminode.setPort(Integer.parseInt(elm.attribute("port")
						.getValue()));
				rminode.setRmiName(rmiName);
				nodeList.add(rminode);
			}
		}

		for (int i = 0; i < nodeList.size(); i++) {
			RMINode node = (RMINode) nodeList.get(i);
			System.out.println(node.getHost());
			System.out.println(node.getPort());
		}

		RMINode node0 = (RMINode) nodeList.get(0);
		System.out.println("----node0----");
		System.out.println(node0.getHost());
		System.out.println(node0.getPort());
	}

	public static void main(String[] args) {
		TestGetNodeList("bankOrder");
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

	public String getRmiName() {
		return rmiName;
	}

	public void setRmiName(String rmiName) {
		this.rmiName = rmiName;
	}

	public static void setConfigDoc(Document configDoc) {
		RMINode.configDoc = configDoc;
	}

	public static String getConfigXmlUrl() {
		return configXmlUrl;
	}
}
