package com.fordays.fdpay.agent;

import org.apache.struts.action.*;

import com.neza.base.ListActionForm;



import javax.servlet.http.*;

public class AgentListForm extends ListActionForm
{

	private long agentId;
	private int agentType=0;
	
	private int subAgentId;
	
	private String agentName = "";
	private String agentEmail = "";
	private String key = "";
  	private String beginDate;
  	private String endDate;

	public String getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest)
	{

		thisAction = "";
	}


	public long getAgentId() {
		return agentId;
	}


	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}


	public int getAgentType() {
		return agentType;
	}


	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}


	public String getAgentName() {
		return agentName;
	}


	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}


	public String getAgentEmail() {
		return agentEmail;
	}


	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public int getSubAgentId() {
		return subAgentId;
	}


	public void setSubAgentId(int subAgentId) {
		this.subAgentId = subAgentId;
	}



	


}
