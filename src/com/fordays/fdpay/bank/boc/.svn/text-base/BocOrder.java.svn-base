package com.fordays.fdpay.bank.boc;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.transaction.Charge;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.URLUtil;

/**
 * 中国银行订单查询
 */
public class BocOrder {
	private String merchantNo = "";// 商户号
	private String orderNos = "";// 订单号
	private String signData = "";// 签名
	// -----
	private String queryPath = "";// 订单查询地址
	private Document configDoc = null;
	private String strSrc = "";
	private String url = "";
	// -----
	private LogUtil myLog;

	public BocOrder(String version) {
		init();
	}

	public void init() {
		XmlUtil xml = new XmlUtil();
		configDoc = BankUtil.loadXml(BocB2CcmdToBank.getConfigXmlUrl());

		merchantNo = xml.getTextByNode(configDoc, "//BANK/BOC/B2C/merchantNo");
		queryPath = xml.getTextByNode(configDoc, "//BANK/BOC/B2C/queryPath");
	}

	/**
	 * 获取中国银行B2C订单查询url
	 */
	public String getBoc_B2C_Url() {
		url = queryPath + "?" + "merchantNo=" + merchantNo + "&orderNo="
				+ orderNos + "&signData=" + signData;
		return url;
	}

	public String getSignData() {
		// 商户签名数据串格式， 各项数据分隔（其中多笔订单号使用管道隔）：
		// 商户号:商户订单号字符串
		// merchantNo:orderNos
		strSrc = merchantNo + orderNos;

		BocB2CcmdToBank bocBank = new BocB2CcmdToBank();

		try {
			signData = bocBank.getPKCS7Tool().sign(strSrc.getBytes());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signData;
	}

	public BocB2CResultFromBank getB2CResult_Query(BocOrder order)
			throws AppException {
		myLog = new LogUtil(false, false, this.getClass());
		String url = order.getBoc_B2C_Url();
		StringBuffer resultBuf = URLUtil.getResponseBodyAsGet(url);
		myLog.info("中国银行订单查询结果resultBuf=" + resultBuf);

		BocB2CResultFromBank result = getB2CResult_Query_Single(resultBuf);
		return result;
	}

	/**
	 * 解析B2C订单查询反馈结果(取单条)
	 */
	public BocB2CResultFromBank getB2CResult_Query_Single(
			StringBuffer stringBuffer) throws AppException {
		List<BocB2CResultFromBank> orderList = getB2CResultList(stringBuffer);
		BocB2CResultFromBank result = orderList.get(0);
		return result;
	}

	/**
	 * 解析B2C订单查询接口反馈结果集(可能含多个订单)
	 */
	public static List<BocB2CResultFromBank> getB2CResultList(StringBuffer stringBuffer)
			throws AppException {
		List<BocB2CResultFromBank> orderList = new ArrayList<BocB2CResultFromBank>();
		BocB2CResultFromBank result;

		XmlUtil xml = new XmlUtil();
		Document document = xml.readResult(stringBuffer);// 将银行返回的信息还原为XML

		Element elemRoot = document.getRootElement();// 根节点<res></res>
		System.out.println("elemName:" + elemRoot.getName());

		// 遍历根结点下的<body></body>
		for (Iterator iterBody = elemRoot.elementIterator("body"); iterBody
				.hasNext();) {
			Element elemBody = (Element) iterBody.next();
			System.out.println("elemName:" + elemBody.getName());

			// 遍历根结点下的<orderTrans></orderTrans>
			Iterator iterTrans = elemBody.elementIterator("orderTrans");

			while (iterTrans.hasNext()) {
				Element elemTrans = (Element) iterTrans.next();
				System.out.println("elemName:" + elemTrans.getName());

				String orderNo = elemTrans.elementText("orderNo");
				String orderSeq = elemTrans.elementText("orderSeq");
				String orderStatus = elemTrans.elementText("orderStatus");
				String cardTyp = elemTrans.elementText("cardTyp");
				String payTime = elemTrans.elementText("payTime");
				String payAmount = elemTrans.elementText("payAmount");

				result = new BocB2CResultFromBank();
				result.setOrderNo(orderNo);
				result.setOrderSeq(orderSeq);
				result.setOrderStatus(orderStatus);
				result.setCardTyp(cardTyp);
				result.setPayTime(payTime);
				result.setPayAmount(payAmount);
				// ----------------------------------------------------------
				result.setROrderNo(orderNo);
				result.setRAmount(BankUtil.getBigDecimalByString(payAmount));

				result.setRChargeStatus(getRChargeStatusByOrderStatus(orderStatus));

				result.setRRequestHost("www.qmpay.com");
				result.setRUrl("orderNo=" + orderNo + "&orderSeq=" + orderSeq
						+ "&orderStatus=" + orderStatus + "&cardTyp=" + cardTyp
						+ "&payTime=" + payTime + "&payAmount=" + payAmount);
				orderList.add(result);
			}
		}
		return orderList;
	}

	/**
	 * 根据银行反馈的orderStatus，得到ResultFromBank中需要的RChargeStatus
	 * 
	 * @param String
	 *            orderStatus
	 * @return Long
	 */
	public static Long getRChargeStatusByOrderStatus(String orderStatus) {
		Long rChargeStatus = new Long(0);

		if ("1".equals(orderStatus)) {// 支付
			rChargeStatus = Charge.CHARGE_STATUS_1;
		} else if ("5".equals(orderStatus)) {// 5-失败
			rChargeStatus = Charge.CHARGE_STATUS_2;
		} else if ("2".equals(orderStatus)) {// 2-撤销
			rChargeStatus = Charge.CHARGE_STATUS_5;
		} else if ("4".equals(orderStatus)) {// 4-未明
			rChargeStatus = Charge.CHARGE_STATUS_7;
		} else if ("0".equals(orderStatus)) {// 0-未处理
			rChargeStatus = Charge.CHARGE_STATUS_0;
		} else if ("3".equals(orderStatus)) {// 3-退货
			rChargeStatus = Charge.CHARGE_STATUS_7;
		} else {
			rChargeStatus = Charge.CHARGE_STATUS_7;
			System.out.println("BocOrder getRChargeStatusByOrderStatus 未知错误");
		}
		return rChargeStatus;
	}

	public static void main(String[] args) {
		StringBuffer buf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\" ?>");
		buf.append("<res>");
		buf.append("<header>");
		buf.append("<merchantNo>104110070118888</merchantNo>");
		buf.append("</header>");
		buf.append("<body>");
		buf.append("<orderTrans>");
		buf.append("<orderNo>4</orderNo>");
		buf.append("<orderSeq>9</orderSeq>");
		buf.append("<orderStatus>1</orderStatus>");
		buf.append("<cardTyp>04</cardTyp>");
		buf.append("<payTime>20090605000000</payTime>");
		buf.append("<payAmount>200.12</payAmount>");
		buf.append("</orderTrans>");
		buf.append("<orderTrans>");
		buf.append("<orderNo>4</orderNo>");
		buf.append("<orderSeq>9</orderSeq>");
		buf.append("<orderStatus>1</orderStatus>");
		buf.append("<cardTyp>04</cardTyp>");
		buf.append("<payTime>20090605000000</payTime>");
		buf.append("<payAmount>360.00</payAmount>");
		buf.append("</orderTrans>");
		buf.append("</body>");
		buf.append("</res>");

		try {
			BocOrder order = new BocOrder("B2C");
			order.getB2CResult_Query_Single(buf);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderNos() {
		return orderNos;
	}

	public void setOrderNos(String orderNos) {
		this.orderNos = orderNos;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}

	public String getQueryPath() {
		return queryPath;
	}

	public void setQueryPath(String queryPath) {
		this.queryPath = queryPath;
	}

	public String getStrSrc() {
		return strSrc;
	}

	public void setStrSrc(String strSrc) {
		this.strSrc = strSrc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
