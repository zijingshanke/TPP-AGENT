package com.fordays.fdpay.bank.icbc.cbst.module;

/**
 * 发送机构
 */
public class Sender {
	private String InstId = "";
	@SuppressWarnings("unused")
	private Institution institution = null;// 机构信息

	public Institution getSenderInstitution() {
		Institution ins = new Institution();
		return ins.getDefaultSender();
	}

	public StringBuffer getSenderXML() {
		Institution ins = getSenderInstitution();
		InstId = ins.getBranchIdentifier();
		
		StringBuffer con = new StringBuffer("<Sender>");
		con.append(ins.getInstitutionXML("Sender"));
		con.append("</Sender>");
		return con;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getInstId() {
		return InstId;
	}

	public void setInstId(String instId) {
		InstId = instId;
	}
}
