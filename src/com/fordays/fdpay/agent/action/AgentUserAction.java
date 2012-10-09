package com.fordays.fdpay.agent.action;


import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentUser;
import com.fordays.fdpay.agent.biz.AgentBiz;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.biz.AccountBiz;

import com.fordays.fdpay.agent.biz.AgentUserBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.neza.base.BaseAction;

import com.neza.encrypt.MD5;
import com.neza.exception.AppException;

import com.neza.exception.AppException;




public class AgentUserAction extends BaseAction
{
	private AgentBiz agentBiz;
	private AgentUserBiz agentUserBiz;
	private AccountBiz accountBiz;
	
	public void setAgentUserBiz(AgentUserBiz agentUserBiz) {
		this.agentUserBiz = agentUserBiz;
	}
	//跳转到添加操作员页面
	public ActionForward addAgentUserPage(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws AppException{
		return mapping.findForward("addAgentUser");
	}
	//添加操作员
	public ActionForward addAgentUser(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws AppException{
		
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
			System.out.println(uri.getAgent().getId());
//			String loginPwd=request.getParameter("loginPwd");
			Agent agent =uri.getAgent();
			AgentUser ag = (AgentUser) form;
			AgentUser agentUser=new AgentUser();
			agentUser.setNo(ag.getNo());
			agentUser.setName(ag.getName());
			String md5 = MD5.encrypt(ag.getPassword());
			agentUser.setPassword(md5);
			agentUser.setCreateDate(new Timestamp(System.currentTimeMillis()));
			agentUser.setAgent(agent);
			agentUser.setStatus(ag.getStatus());
			String surePassword=ag.getPassword2();
			System.out.println("-------"+agentUser.getNo());
			
//			if(loginPwd.equals(agent.getPassword())){
//				
//			}
			//AgentUser newAgentUser=agentUserBiz.isExistAgentUser(ag.getNo());
//		System.out.println("agentUser"+agentUser+", 数据库商户"+agentUserBiz.isExistAgentUser(agentUser.getNo()));
			if(agentUserBiz.getAgentUserByNo(ag.getNo())!=null){				
				request.setAttribute("message","该登陆账号已存在，请查证后再添加！");				
				return mapping.findForward("addAgentUser");
			}else{
				//判断密码是否一致
				if((ag.getPassword()).equals(surePassword)){
					agentUserBiz.addAgentUser(agentUser);		
					return viewAgentUsers(mapping,form,request,response);
				}
				else
					return mapping.findForward("addAgentUser");
			}
			
			
		}
	//跳转到编辑操作员页面
	public ActionForward editAgentUserPage(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, AppException{
				
		String agentUserId=request.getParameter("agentUserId");		
		AgentUser agentUser=agentUserBiz.getAgentUserById(Long.valueOf(agentUserId).longValue());
		AgentUser ag = (AgentUser) form;
		ag.setId(agentUser.getId());
		ag.setName(agentUser.getName());
		ag.setPassword(agentUser.getPassword());
		request.setAttribute("agentUser", agentUser);
		return mapping.findForward("editAgentUser");
	}
	//操作员修改密码页面
	public ActionForward modifyAgentUserPasswordPage(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, AppException{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		AgentUser agentUser=uri.getAgentUser();
		request.setAttribute("agentUser", agentUser);

		if(agentUser!=null){
			return mapping.findForward("modifyAgentUserPassword");
		}
		
		return null;
	}	
	//操作员修改密码
	public ActionForward modifyAgentUserPassword(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, AppException{

		AgentUser agentUserForm = (AgentUser) form;
		Long agentUserId=agentUserForm.getId();
		AgentUser agentUser=agentUserBiz.getAgentUserById(agentUserId);
		System.out.println("当前操作员ID"+agentUserId+"----操作员修改后的密码：---"+agentUserForm.getPassword());
		if((agentUserForm.getPassword2()).equals(agentUserForm.getPassword())){
			String md5 = MD5.encrypt(agentUserForm.getPassword());			
			agentUser.setPassword(md5);
			agentUserBiz.updateAgentUser(agentUser);
			return mapping.findForward("modifyAgentUserPasswordSuccess");
		}
		return null;
	}
	
	//编辑操作员
	public ActionForward editAgentUser(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, AppException{
		AgentUser ag = (AgentUser) form;
		Long agentUserId=ag.getId();
//		String loginPwd=request.getParameter("loginPwd");
//		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
//		Agent agent =uri.getAgent();
//		System.out.println("操作密码"+agent.getPassword());
		System.out.println("----操作员ID---"+agentUserId+"</b>"+"姓名："+ag.getName()+"账号："+ag.getNo()+"  id:"+ag.getId());
		
//		if(loginPwd.equals(agent.getPassword()))
//		{			
//		}
//		System.out.println("当前表单用户账号："+ag.getNo()+"是否存在商户："+agentUserBiz.isExistAgentUser(ag.getNo(),new Long(ag.getId())));
		if(agentUserBiz.isExistAgentUser(ag.getNo(),new Long(ag.getId()))){
			request.setAttribute("message","该操作员账号已存在！");
			return mapping.findForward("editAgentUser");
			
		}else{
			AgentUser agentUser=agentUserBiz.getAgentUserById(agentUserId);
			agentUser.setName(ag.getName());
			agentUser.setNo(ag.getNo());
			String md5 = MD5.encrypt(ag.getPassword());			
			agentUser.setPassword(md5);
			String surePassword=ag.getPassword2();
			if((ag.getPassword()).equals(surePassword)){		
				if(agentUserBiz.updateAgentUser(agentUser))
					return viewAgentUsers(mapping,form,request,response);			
			}	
//		agentUserBiz.updateAgentUser(ag);
			return mapping.findForward("editAgentUser");
		}
		
	}
	
	//删除操作员
	public ActionForward deleteAgentUser(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, AppException{
					
		String agentUserId=request.getParameter("agentUserId");
		
//		AgentUser agentUser=agentUserBiz.getAgentUserById(Long.valueOf(agentUserId).longValue());
		agentUserBiz.deleteAgentUser(new Long(agentUserId));
		return  viewAgentUsers(mapping,form,request,response);
	}
	
	//跳转到删除操作员
	public ActionForward todeleteAgentUser(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, AppException{
		
		String agentUserId=request.getParameter("agentUserId");		
		AgentUser agentUser=agentUserBiz.getAgentUserById(new Long(agentUserId));
		System.out.println("操作员账号"+agentUser.getNo());
		request.setAttribute("agentUserNo", agentUser.getNo());
		request.setAttribute("agentUserId", agentUserId);
		return  viewAgentUsers(mapping,form,request,response);
	}
	
	//显示操作员列表
	public ActionForward viewAgentUsers(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws AppException
		{
			UserRightInfo URI = new UserRightInfo();
			URI=(UserRightInfo)request.getSession().getAttribute("URI");			
			Agent agent =URI.getAgent();
			List list=agentUserBiz.listAgentUser(agent.getId());
			request.setAttribute("agentUserlist", list);
			return mapping.findForward("viewAgentUsers");
		}
	
	public ActionForward viewCompanyInfo(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response)
		{
			UserRightInfo URI = (UserRightInfo)request.getSession().getAttribute("URI");
			Agent agent =URI.getAgent();
		try {
			Account account=accountBiz.getAccountByBind(agent.getId(),Account.bindStatus_Bind);
			request.setAttribute("account", account);
			request.setAttribute("agent",agent);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return mapping.findForward("viewCompanyInfo");
		}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}
	

}
