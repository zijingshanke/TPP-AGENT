package com.fordays.fdpay.transaction.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.biz.AccountBiz;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawLog;
import com.fordays.fdpay.transaction.biz.DrawBiz;
import com.fordays.fdpay.transaction.biz.DrawLogBiz;
import com.neza.base.BaseAction;
import com.neza.base.Constant;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;

public class DrawAction extends BaseAction{
	private NoUtil noUtil;
	private AccountBiz accountBiz;
	private DrawBiz drawBiz;
	private AgentBiz agentBiz;
	private DrawLogBiz drawLogBiz;

	// 提现
	public ActionForward withdrawing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tabMenuList = request.getParameter("showTabMenuIdList").toString();
		String subMenuList = request.getParameter("showSubMenuIdList").toString();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Agent agent = agentBiz.getAgentById(uri.getAgent().getId());
		Account account = accountBiz.getAccountByBind(agent.getId(),Account.bindStatus_Bind);
		agent.setAllowBalance(agentBiz.getAgentAllowBalance(agent.getId()));
		String forword = "";
		if(agent.getStatus()!=3){
			 return mapping.findForward("tipscertification");
		}
		if (account == null || account.getAccountName()==null || account.getCardNo()==null) {
			// 提示需要设置提现的银行账号
			request.setAttribute("agent", agent);
			forword = "tipssetting";
		}
		else{
			// 已经设置提现的银行账号
			request.setAttribute("agent", agent);
			request.setAttribute("account", account);
			request.setAttribute("views", drawBiz.getDrawViewsToDay(agent));
			forword = "withdrawing";
		}
		saveToken(request);
		return mapping.findForward(forword);
	}
  	
	// 添加新提现
	public ActionForward addDraw(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws AppException 
			{
				Draw draw=(Draw)form;
				
				BigDecimal amount =draw.getAmounts();
				String papwd=draw.getPayPassword();
				UserRightInfo uri  = (UserRightInfo)request.getSession().getAttribute("URI");
				Agent agent = agentBiz.getAgentById(uri.getAgent().getId());
				AgentBalance ab =agentBiz.getAgentBalance(agent.getId());
				BigDecimal allowBalance=ab.getAllowBalance();
				//BigDecimal allowBalance=agent.getAllowBalance();
				Account account = accountBiz.getAccountByBind(agent.getId(),
						Account.bindStatus_Bind);
				if(!(agent.getPayPassword().equals(MD5.encrypt(papwd.trim()))))
				{
					request.setAttribute("agent", agent);
					request.setAttribute("account", account);
					request.setAttribute("msgPayPassword", "您输入的支付密码有误!请重输!");
					return mapping.findForward("withdrawing");//返回提现页面
				}
				else if(amount.compareTo(allowBalance)>0)
				{
					request.setAttribute("agent", agent);
					request.setAttribute("account", account);
					request.setAttribute("msgPayPassword", "您账户上的金额不足提现要求!");
					return mapping.findForward("withdrawing");//返回提现页面
				}
				//判断该用户单笔提现金额
				else if(amount.compareTo(agent.getMaxItemDrawAmount())>0)
				{
						request.setAttribute("agent", agent);
						request.setAttribute("account", account);
						request.setAttribute("msgPayPassword", "您单笔提现金额大于"+agent.getMaxItemDrawAmount()+"元！");
						return mapping.findForward("withdrawing");//返回提现页面
				}
				//判断该用户当日提现总金额
				else if((drawBiz.getAmountSumToDay(agent).add(amount)).compareTo(agent.getMaxDrawAmount())>0)
				{
					request.setAttribute("agent", agent);
					request.setAttribute("account", account);
					request.setAttribute("msgPayPassword", "您当日提现金额大于"+agent.getMaxDrawAmount()+"元！");
					return mapping.findForward("withdrawing");//返回提现页面
				}
				else if(drawBiz.getDrawViewsToDay(agent)>=3)
				{
					request.setAttribute("agent", agent);
					request.setAttribute("account", account);
					request.setAttribute("msgAmount", "amount3");
					return mapping.findForward("withdrawing");//返回提现页面
				}
				else
				{	
					if (isTokenValid(request, true)) 
					{
						//添加提现
						Draw dr=new Draw();
						dr.setOrderNo(noUtil.getDrawNo());
						dr.setAgent(agent);
						dr.setAmount(amount);
						dr.setRequestDate(new Timestamp(System.currentTimeMillis()));
						dr.setStatus(new Long(0));
						dr.setBank(draw.getBank());
						dr.setCardNo(draw.getCardNo());
						dr.setType(Draw.type_0);
						
						drawBiz.applyDraw(dr,agent);
						
						//添加提现日志
						DrawLog dl=new DrawLog();
						dl.setAgent(agent);
						dl.setAmount(amount);
						dl.setOrderNo(dr.getOrderNo());
						dl.setContent("申请 当前商户可用余额："+ab.getAllowBalance());
						dl.setStatus(new Long(0));
						dl.setChargeDate(new Timestamp(System.currentTimeMillis()));
						drawLogBiz.addDrawLog(dl);
						
						
						//发送邮件
						HashMap<String, String> params = new HashMap<String, String>();
						String url=Constant.getLocalHost();
						params.put("$url$",url+"/transaction/drawlist.do?thisAction=listDraw");
						params.put("$toAgentName$", agent.getName());
						params.put("$amount$", amount.toString());
						String result = MailUtil.sslSend("钱门提示","0011", agent.getEmail(), params,Certification.getProtocol()); // 发邮件
						
					} else {
						saveToken(request);
					}	
					return mapping.findForward("tipsdraw");//跳转到等待页面！	
					
				}
			}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
	
	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setDrawBiz(DrawBiz drawBiz) {
		this.drawBiz = drawBiz;
	}

	public void setDrawLogBiz(DrawLogBiz drawLogBiz) {
		this.drawLogBiz = drawLogBiz;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}
}
