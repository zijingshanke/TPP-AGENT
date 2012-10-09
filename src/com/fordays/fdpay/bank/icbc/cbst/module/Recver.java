package com.fordays.fdpay.bank.icbc.cbst.module;

/**
 * 接收机构
 */
public class Recver {
	private String InstId = "";
	@SuppressWarnings("unused")
	private Institution institution=null;//机构信息

	public Institution getRecverInstitution() {
		Institution ins = new Institution();
		return ins.getDefaultRecver();
	}
	
	public StringBuffer getRecverXML() {
		Institution ins=getRecverInstitution();
		InstId=ins.getBranchIdentifier();
		
		StringBuffer con = new StringBuffer("<Recver>");
		con.append(ins.getInstitutionXML("Recver"));
		con.append("</Recver>");
		return con;
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

	public Institution getInstitution() {
		return institution;
	}
	
	
}
