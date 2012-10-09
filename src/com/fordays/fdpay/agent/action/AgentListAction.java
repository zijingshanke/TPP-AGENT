package com.fordays.fdpay.agent.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.biz.TransactionBiz;
import com.neza.base.BaseAction;


public class AgentListAction extends BaseAction{
	AgentBiz agentBiz;
	TransactionBiz transactionBiz;
	/**管理联系人查询列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String acId = "";
		if(request.getParameter("acId")!=null && !request.getParameter("acId").equals(""))
			acId = request.getParameter("acId");
		
		UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent= new Agent();
		agent = uri.getAgent();
		
		AgentListForm alf = (AgentListForm) form;
		if (alf == null)
			alf = new AgentListForm();
		
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			alf.setIntPage(1);
		}
		List list = new ArrayList();
		alf.setAgentId(agent.getId());
		list = agentBiz.getContactList(alf);
		alf.setList(list);
		request.setAttribute("alf", alf);
			
		request.setAttribute("contactId", acId);
		request.setAttribute("aEmail", agent.getLoginName());

		saveToken(request);
		return mapping.findForward("contactlist");
	}
	
	/** 添加联系人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOrUpdateContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = "";
		UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent= new Agent();
		agent = uri.getAgent();
		String actionType = request.getParameter("actionType");
		String contactName = "";
		String contactLoginName = "";
		if(actionType.equals("add")){
			contactName = request.getParameter("agentName");
			contactLoginName = request.getParameter("agentEmail");
		}
		else{
			
			contactName = new String(request.getParameter("aName").getBytes("UTF-8"));
			contactLoginName = request.getParameter("lName");
		}
		String aId ="";
		if(request.getParameter("aId")!=null && !request.getParameter("aId").equals("")){
			aId = request.getParameter("aId");
		}
		
		
		if (isTokenValid(request, true)) {
			agentBiz.addOrUpdateContact(agent, contactName, contactLoginName, actionType,aId);
		}
		else{
			saveToken(request);
			message = "请不要重复提交!!";
			request.setAttribute("message", message);
		}

		return new ActionForward("/agent/agentlist.do?thisAction=listContact");
	}
	
	public ActionForward deleteContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = "";
		String deletelist[] = request.getParameter("deletelist").split(",");
		if (isTokenValid(request, true)) {
			agentBiz.deleteContact(deletelist);
		}
		else{
			resetToken(request);
			message = "请不要重复提交!!";
			request.setAttribute("message", message);
		}
		return new ActionForward("/agent/agentlist.do?thisAction=listContact");
	}
	
	public ActionForward checkContactByLoginName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent= new Agent();
		agent = uri.getAgent();
		String contactLoginName = request.getParameter("loginName");
		String actionType = request.getParameter("actionType");
		String aId ="";
		if(request.getParameter("aId")!=null && !request.getParameter("aId").equals("")){
			aId = request.getParameter("aId");
		}
		PrintWriter pw = response.getWriter();
		if(agentBiz.checkContactByLoginName(agent,contactLoginName,actionType,aId)==null)
			pw.print("0");
		else
			pw.print("1");
		pw.close();
		return null;
	}
	
	/** 编辑联系人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent= new Agent();
		agent = uri.getAgent();
		
		String contactName = request.getParameter("agentName");
		contactName = new String(contactName.getBytes("UTF-8"));
		String acId = request.getParameter("acId");
		
		agent.setId(new Integer(acId).intValue());
		return new ActionForward("/agent/agentlist.do?thisAction=listContact");
	}
	
	public AgentBiz getAgentBiz() {
		return agentBiz;
	}
	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
	

	
}
