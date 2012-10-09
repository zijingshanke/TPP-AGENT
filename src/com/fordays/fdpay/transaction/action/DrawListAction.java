package com.fordays.fdpay.transaction.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.biz.AccountBiz;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.DrawListForm;
import com.fordays.fdpay.transaction.biz.DrawBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class DrawListAction extends BaseAction {
	private AccountBiz accountBiz;
	private DrawBiz drawBiz;
	private AgentBiz agentBiz;

	// 查看提现信息
	public ActionForward listDraw(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException, ServletException, IOException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		DrawListForm dlf = (DrawListForm) form;
		if (dlf == null) {

			dlf = new DrawListForm();
		}
		if (!"".equals(dlf.getBeginDate()) && !"".equals(dlf.getEndDate())
				&& dlf.getBeginDate().compareTo(dlf.getEndDate()) > 0) {
			request.setAttribute("msDate", "起始时间比结束时间晚！");
			request.setAttribute("agent", agent);
			return mapping.findForward("recordDraw");
		}
		dlf.setPerPageNum(5);
		dlf.setList(drawBiz.getDrawsByAgent(agent, dlf));

		request.setAttribute("dlf", dlf);
		request.setAttribute("agent", agent);
		return mapping.findForward("recordDraw");
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setDrawBiz(DrawBiz drawBiz) {
		this.drawBiz = drawBiz;
	}
	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
}
