package com.fordays.fdpay.bank.icbc.cbst.biz;

import org.dom4j.Document;

import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.icbc.cbst.module.GroupHeader;
import com.fordays.fdpay.bank.icbc.cbst.util.CbstLogUtil;
import com.neza.encrypt.MD5;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;

/**
 * 工商银行银商转帐系统业务报文实现类
 */
public class IcbcCbstContentBizImp {

	private String headPlainText = "";// 部分签名项目
	private LogUtil myLog;

	/**
	 * 获取业务报文
	 * 
	 * @param String
	 *            businessCode
	 * @return String
	 */
	public String getBussinessXML(String businessCode) throws AppException {
		String xmlcontent = "";
		if ("20001".equals(businessCode)) {
			xmlcontent = getSignInXML(businessCode);
		} else if ("20002".equals(businessCode)) {
			xmlcontent = getSignOffXML(businessCode);
		} else if ("20003".equals(businessCode)) {
			xmlcontent = getCheckConnectionXML(businessCode);
		} else if ("20004".equals(businessCode)) {
			xmlcontent = getKeysynchronizationXML(businessCode);
		} else {
			xmlcontent = "error msg 2";
		}
		return xmlcontent;
	}

	// ===================== 系统类 ==================
	/**
	 * 20001 签到 商户发起
	 */
	private String getSignInXML(String businessCode) {
		return "test1";
	}

	/**
	 * 20002 签退 商户发起
	 */
	private String getSignOffXML(String businessCode) {
		return "test2";
	}

	/**
	 * 20003 通信检测 银行发起/商户发起
	 */
	private String getCheckConnectionXML(String businessCode) {
		myLog = new CbstLogUtil(true, false, this.getClass(), "");

		StringBuffer xmlcon = new StringBuffer("<MsgText>");
		xmlcon.append(getGroupHeaderXML(businessCode));// header
		xmlcon.append("<FtSeq>");
		// 商户流水号 FutureSeqNo
		String futureSeqNo = "666666";// noUtil.getChargeNo()
		xmlcon.append(futureSeqNo);
		xmlcon.append("</FtSeq>");
		// ------------------
		headPlainText = headPlainText + futureSeqNo;

		myLog.info("allPlainText:" + headPlainText);
		// ---------
		xmlcon.append("<MacKey>");
		xmlcon.append("1234567890123456");
		xmlcon.append("</MacKey>");
		xmlcon.append("<Mac>");
		
		String mac="maccontent";//调用工行
		
		xmlcon.append("mac");
		xmlcon.append("</Mac>");
		xmlcon.append("</MsgText>");

		return xmlcon.toString();
	}

	/**
	 * 20004 密钥同步 商户发起
	 */
	private String getKeysynchronizationXML(String businessCode) {
		return "test4";
	}

	// ===================== 帐户类 ==================
	// ===================== 交易类 ==================
	// ===================== 对账类 ==================

	/**
	 * 获取消息包头
	 */
	private StringBuffer getGroupHeaderXML(String businessCode) {
		myLog = new CbstLogUtil(false, false, this.getClass(), "");

		GroupHeader header = new GroupHeader(businessCode);
		StringBuffer xmlcon = new StringBuffer(header.getGroupHeaderXML());
		// 业务功能码|交易发起方|发送机构机构标识|接收机构机构标识|交易日期
		headPlainText = header.getHeadPlainText();

		myLog.info("headPlainText:" + headPlainText);

		return xmlcon;
	}

	// ================================================
	/**
	 * 从银行返回的报文中判断业务类型
	 */
	public String getBusinessCodeFromRequest(String request)
			throws AppException {
		String businessCode = "";

		if (request != null && "".equals(request) == false) {
			XmlUtil xml = new XmlUtil();
			Document doc = xml.readResult(new StringBuffer(request));
			businessCode = xml.getTextByNode(doc, "//MsgText/GrpHdr/BusCd");
			int codelength = businessCode.length();

			if (codelength!=5) {
				System.out.println("银行报文返回码异常");
			}
		} else {
			System.out.println("银行报文为空！");
		}
		System.out.println(businessCode);
		return businessCode;
	}

	public static void main(String[] args) {
		// <?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>
		String requestXml = "<MsgText><GrpHdr><Version>1.0.0</Version><SysType>8</SysType><BusCd>20003</BusCd><TradSrc>F</TradSrc><Sender><InstId>123</InstId></Sender><Recver><InstId>963</InstId></Recver><Date>20091211</Date><Time>154733</Time></GrpHdr><FtSeq>666666</FtSeq><MacKey>mackey</MacKey><Mac>mac</Mac></MsgText>";
		try {
			IcbcCbstContentBizImp biz = new IcbcCbstContentBizImp();
			biz.getBusinessCodeFromRequest(requestXml);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	// ================================================
	public String getHeadPlainText() {
		return headPlainText;
	}

	public void setHeadPlainText(String headPlainText) {
		this.headPlainText = headPlainText;
	}

	public void setMyLog(CbstLogUtil myLog) {
		this.myLog = myLog;
	}
}
