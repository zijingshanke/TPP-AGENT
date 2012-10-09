package com.fordays.fdpay.agent.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.neza.base.BaseAction;

public class AgentAddressAction extends BaseAction{
	AgentBiz agentBiz;

	/** 交易地址管理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAgentAddressManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
		AgentAddress agentAddress = new AgentAddress();
		AgentAddress agAddress = null;
		int count=0;
		if(request.getAttribute("agAddress")!=null)
			agAddress = (AgentAddress)request.getAttribute("agAddress");
		
		UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent= new Agent();
		agent = uri.getAgent();
		
		agentAddress.setAgent(agent);
		List list = agentBiz.getAgentAddressManage(agentAddress);
		if(list!=null && list.size()>0){
			count = list.size();
		}

		request.setAttribute("agAddress", agAddress);
		request.setAttribute("addresslist", list);
		request.setAttribute("listcount", count);
		saveToken(request);
		return mapping.findForward("addressmanage");
	}
	
	/** 添加交易地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAgentAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AgentAddress agentAddress = (AgentAddress)form;
		String message = "";
		
		UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent= new Agent();
		agent = uri.getAgent();
	
		if (isTokenValid(request, true)) {
			agentBiz.addAgentAddress(agentAddress,agent);
		}
		else{
			saveToken(request);
			message = "请不要重复提交!!";
			request.setAttribute("message", message);
		}
		
		return mapping.findForward("listaddress");
	}
	
	public ActionForward editAgentAddressById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long aid = new Long(request.getParameter("id"));
		AgentAddress agentAddress = agentBiz.editAgentAddressById(aid);
		request.setAttribute("agAddress", agentAddress);
		request.setAttribute("flag", "edit");
		return mapping.findForward("listaddress");
	}
	
	/** 修改交易地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAgentAddressById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AgentAddress agentAddress = (AgentAddress)form;
		long aid = new Long(request.getParameter("aid"));
		agentBiz.updateAgentAddressById(agentAddress,aid);
		return mapping.findForward("listaddress"); 
	}
	
	/** 删除交易地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAgentAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long aid = new Long(request.getParameter("aid"));
		agentBiz.deleteAgentAddress(aid);
		return mapping.findForward("listaddress"); 
	}
	
	/** 设置默认交易地址
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setDefaultAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long aid = new Long(request.getParameter("aid"));
		agentBiz.setDefaultAddress(aid);
		request.setAttribute("selectid", aid);
		return mapping.findForward("listaddress"); 
	}
	
	public AgentBiz getAgentBiz() {
		return agentBiz;
	}
	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
	
}
