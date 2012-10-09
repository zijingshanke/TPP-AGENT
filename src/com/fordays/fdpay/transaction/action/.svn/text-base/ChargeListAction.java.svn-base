package com.fordays.fdpay.transaction.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.ChargeListForm;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class ChargeListAction extends BaseAction {
	private ChargeBiz chargeBiz;
	private AgentBiz agentBiz;

	// 查看充值信息
	public ActionForward listCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException, ServletException, IOException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		ChargeListForm clf = (ChargeListForm) form;
		if ( !"".equals(clf.getBeginDate())&& !"".equals(clf.getEndDate())
				&& clf.getBeginDate().compareTo(clf.getEndDate()) > 0) {
			request.setAttribute("msDate", "起始时间比结束时间晚！");
			request.setAttribute("agent", agent);
			return mapping.findForward("recordcharge");
		}
		clf.setPerPageNum(5);
		clf.setList(chargeBiz.getCharges(agent, clf));
		request.setAttribute("clf", clf);
		request.setAttribute("agent", agent);
		return mapping.findForward("recordcharge");
	}
	
	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
}
