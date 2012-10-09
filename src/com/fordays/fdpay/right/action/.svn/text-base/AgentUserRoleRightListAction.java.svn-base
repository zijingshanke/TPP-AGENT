package com.fordays.fdpay.right.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;

import com.fordays.fdpay.agent.AgentUser;
import com.fordays.fdpay.agent.AgentUserRoleRight;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentUserBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.right.biz.RightBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;
import com.neza.tool.XMLUtil;

public class AgentUserRoleRightListAction extends BaseAction
{
	private RightBiz rightBiz;
	private AgentUserBiz agentUserBiz;

	public void setRightBiz(RightBiz rightBiz)
	{
		this.rightBiz = rightBiz;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		com.fordays.fdpay.right.AgentUserRoleRightListForm aulf = (com.fordays.fdpay.right.AgentUserRoleRightListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		/*
		 * if (uri == null) return (mapping.findForward("valid"));
		 */

		List list = rightBiz.getAgentUsers(uri.getAgent().getId());
		request.setAttribute("list", list);
		forwardPage = "listagentuserofright";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward editAgentUserRoleRight(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		com.fordays.fdpay.right.AgentUserRoleRightListForm aulf = (com.fordays.fdpay.right.AgentUserRoleRightListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (uri == null)
			return (mapping.findForward("valid"));
		String action = aulf.getThisAction();
//		String agentUserName="";
		if (aulf.getAgentUserId() > 0)
		{
//			System.out.println("操作员ID"+aulf.getAgentUserId());
			AgentUser agentUser=agentUserBiz.getAgentUserById(aulf.getAgentUserId());
			String temp = XMLUtil.buildAgentRightXml();
			aulf.setXml(temp);
			List list = rightBiz.getAgentUserRoleRightByAgentUserId(aulf
			    .getAgentUserId());
			
			if (list != null)
			{
				int[] ids = new int[list.size()];
				for (int i = 0; i < list.size(); i++)
				{
					AgentUserRoleRight aurr = (AgentUserRoleRight) list.get(i);
					ids[i] = (int) aurr.getAgentRoleRight().getId();
//					agentUserName=aurr.getAgentUser().getName();
				}
				aulf.setSelectedItems(ids);
				aulf.setSelectedRightItemsStr(transStr(ids));
			}
			request.setAttribute("aulf", aulf);
//			request.setAttribute("list", list);
			request.setAttribute("agentUserName", agentUser.getName());
			forwardPage = "editagentuserright";
		}
		else
		{
			forwardPage = "error";
		}
		return (mapping.findForward(forwardPage));
	}

	public ActionForward updateAgentUserRight(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		com.fordays.fdpay.right.AgentUserRoleRightListForm aulf = (com.fordays.fdpay.right.AgentUserRoleRightListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (uri == null)
			return (mapping.findForward("valid"));

		rightBiz.setAgentUserRights(aulf.getAgentUserId(),aulf.getSelectedRightItems());
		List list = rightBiz.getAgentUsers(uri.getAgent().getId());
		request.setAttribute("agentUserlist", list);
		forwardPage = "viewAgentUsers";

		return (mapping.findForward(forwardPage));
	}

	private String transStr(int[] str)
	{
		if (str.length == 0)
			return "";
		String temp = "";
		for (int i = 0; i < str.length; i++)
		{
			if (str.length - 1 == i)
				temp = temp + str[i];
			else
				temp = temp + str[i] + ",";
		}
		return temp ;
	}

	public static void main(String[] args)
	{

		int[] str = { 1, 2, 3 };
		// System.out.println("trans=" + rla.transStr(str));
	}


	public void setAgentUserBiz(AgentUserBiz agentUserBiz) {
		this.agentUserBiz = agentUserBiz;
	}

}