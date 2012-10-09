package com.fordays.fdpay.agent.action;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.agent.biz.CoterieBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.neza.base.BaseAction;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class CoterieAction extends BaseAction{
	AgentBiz agentBiz;
	CoterieBiz coterieBiz;
	AgentCoterieBiz agentCoterieBiz;
	//跳转修改Key,2009-9-5
	public ActionForward editKeyByCoterie(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws AppException{
		UserRightInfo URI = (UserRightInfo)request.getSession().getAttribute("URI");
		Agent agent=agentBiz.getAgentById(URI.getAgent().getId());
		Coterie coterie=coterieBiz.getCoterieByAgent(agent);
		if(coterie!=null){
			request.setAttribute("coterie", coterie);
			return mapping.findForward("editKeyByCoterie");
		}else{
			//该企业商户没有创建商户圈
		}
		return null;
	}

	//修改密钥
	public ActionForward setKeyByCoterie(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response) throws AppException{
		Coterie coterie=(Coterie)form;
		Agent tempAgent=agentBiz.getAgentByLoginName(coterie.getAgent().getLoginName());
		if(!MD5.encrypt(coterie.getAgent().getPayPassword()).equals(tempAgent.getPayPassword())){
			request.setAttribute("msg", "交易密码错误！");
			return editKeyByCoterie(mapping,form,request,response);
		}else{
			try{
				coterieBiz.setKeyByAgent(tempAgent,coterie.getSignKey());
				request.setAttribute("massag", 1);
				return mapping.findForward("tipsKey");
			}catch (Exception e) {
				request.setAttribute("massag", 0);
				return mapping.findForward("tipsKey");
			}
		}
	}

	
	
	
	public AgentBiz getAgentBiz() {
		return agentBiz;
	}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public CoterieBiz getCoterieBiz() {
		return coterieBiz;
	}

	public void setCoterieBiz(CoterieBiz coterieBiz) {
		this.coterieBiz = coterieBiz;
	}
}
