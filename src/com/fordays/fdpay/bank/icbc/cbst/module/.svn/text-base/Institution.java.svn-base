package com.fordays.fdpay.bank.icbc.cbst.module;

import java.io.File;
import org.dom4j.Document;
import com.fordays.fdpay.bank.BankUtil;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;

/**
 * 机构信息
 */
public class Institution {
	// 要素
	private String InstitutionIdentifier = "";// 机构标识,<InstId>
	private String InstitutionName = "";// 机构名称,<InstNm>
	private String BranchIdentifier = "";// 分支机构编码,<BrchId>
	private String BranchName = "";// 分支机构名称,<BrchNm>
	private String SubBranchIdentifier = "";// 网点号, <SubBrchId>
	private String SubBranchName = "";// 网点名称, <SubBrchNm>
	// ------------------------------------
	private static String configXmlUrl = Constant.WEB_INFO_PATH + "bankkey"
			+ File.separator + "init" + File.separator
			+ "bankInterfaceConfig-ICBC-CBST.xml";
	private Document configDoc = null;

	public Institution() {
		init();
	}

	public void init() {
		configDoc = BankUtil.loadXml(configXmlUrl);
	}

	/**
	 * 获取默认的消息发送机构
	 */
	public Institution getDefaultSender() {
		return getInstitutionInfo("Sender");
	}

	/**
	 * 获取默认的消息接收机构
	 */
	public Institution getDefaultRecver() {
		return getInstitutionInfo("Recver");
	}

	/**
	 * 读取机构信息
	 */
	public Institution getInstitutionInfo(String type) {
		Institution ins = null;
		XmlUtil xml = new XmlUtil();
		InstitutionIdentifier = xml.getTextByNode(configDoc,
				"//BANK/ICBC/CBST/" + type + "/InstId");
		InstitutionName = xml.getTextByNode(configDoc, "//BANK/ICBC/CBST/"
				+ type + "/InstNm");
		BranchIdentifier = xml.getTextByNode(configDoc, "//BANK/ICBC/CBST/"
				+ type + "/BrchId");
		BranchName = xml.getTextByNode(configDoc, "//BANK/ICBC/CBST/" + type
				+ "/BrchNm");
		SubBranchIdentifier = xml.getTextByNode(configDoc, "//BANK/ICBC/CBST/"
				+ type + "/SubBrchId");
		SubBranchName = xml.getTextByNode(configDoc, "//BANK/ICBC/CBST/" + type
				+ "/SubBrchNm");

		ins = new Institution();
		ins.setInstitutionIdentifier(InstitutionIdentifier);
		ins.setInstitutionName(InstitutionName);
		ins.setBranchIdentifier(BranchIdentifier);
		ins.setBranchName(BranchName);
		ins.setSubBranchIdentifier(SubBranchIdentifier);
		ins.setSubBranchName(SubBranchName);
		return ins;
	}

	// ----
	public StringBuffer getInstitutionXML(String type) {
		Institution ins = getInstitutionInfo(type);
		StringBuffer con = new StringBuffer("<InstId>");
		con.append(ins.getInstitutionIdentifier());
		con.append("</InstId>");

		return con;
	}

	public String getInstitutionIdentifier() {
		return InstitutionIdentifier;
	}

	public void setInstitutionIdentifier(String institutionIdentifier) {
		InstitutionIdentifier = institutionIdentifier;
	}

	public String getInstitutionName() {
		return InstitutionName;
	}

	public void setInstitutionName(String institutionName) {
		InstitutionName = institutionName;
	}

	public String getBranchIdentifier() {
		return BranchIdentifier;
	}

	public void setBranchIdentifier(String branchIdentifier) {
		BranchIdentifier = branchIdentifier;
	}

	public String getBranchName() {
		return BranchName;
	}

	public void setBranchName(String branchName) {
		BranchName = branchName;
	}

	public String getSubBranchIdentifier() {
		return SubBranchIdentifier;
	}

	public void setSubBranchIdentifier(String subBranchIdentifier) {
		SubBranchIdentifier = subBranchIdentifier;
	}

	public String getSubBranchName() {
		return SubBranchName;
	}

	public void setSubBranchName(String subBranchName) {
		SubBranchName = subBranchName;
	}

	public Document getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(Document configDoc) {
		this.configDoc = configDoc;
	}

	public static String getConfigXmlUrl() {
		return configXmlUrl;
	}

	public static void setConfigXmlUrl(String configXmlUrl) {
		Institution.configXmlUrl = configXmlUrl;
	}
}
