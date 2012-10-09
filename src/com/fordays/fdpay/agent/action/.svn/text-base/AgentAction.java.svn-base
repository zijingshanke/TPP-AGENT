package com.fordays.fdpay.agent.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.Josn;
import com.fordays.fdpay.agent.QuestionsAndAnswers;
import com.fordays.fdpay.agent.biz.AccountBiz;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.agent.biz.AgentRelationshipBiz;
import com.fordays.fdpay.agent.biz.CoterieBiz;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.right.dao.RightDAO;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.Province;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.biz.BankBiz;
import com.fordays.fdpay.system.biz.CityBiz;
import com.fordays.fdpay.system.biz.ProvinceBiz;
import com.fordays.fdpay.system.biz.TaskTimestampBiz;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.DrawLog;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.biz.DrawBiz;
import com.fordays.fdpay.transaction.biz.DrawLogBiz;
import com.neza.base.BaseAction;
import com.neza.base.Constant;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.message.SMUtil;
import com.neza.tool.DateUtil;
import com.fordays.fdpay.security.Certification;

public class AgentAction extends BaseAction {
	private AgentBiz agentBiz;
	private AccountBiz accountBiz;
	private TaskTimestampBiz tasktimestampBiz;
	private CoterieBiz coterieBiz;
	private AgentCoterieBiz agentCoterieBiz;
	private AgentRelationshipBiz agentRelationshipBiz;
	private ProvinceBiz provinceBiz;
	private CityBiz cityBiz;
	private BankBiz bankBiz;
	private DrawLogBiz drawLogBiz;
	private DrawBiz drawBiz;
	private NoUtil noUtil;
	private LogUtil myLog;

	public ActionForward forwardLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String loginName = request.getParameter("email");
		String forward = request.getParameter("forward");
		Agent agent = null;
		agent = agentBiz.getAgentByLoginName(loginName);
		UserRightInfo uri = new UserRightInfo();
		request.getSession().setAttribute("loginAgent", agent);
		uri.setAgent(agent);
		request.getSession().setAttribute("URI", uri);
		if (forward.equals("charge")) {
			return new ActionForward(
					"/transaction/charge.do?thisAction=rechargeable");
		} else if (forward.equals("transaction")) {
			return new ActionForward(
					"/transaction/transactionlist.do?thisAction=listTransactions");
		} else if (forward.equals("viewMyAgent")) {
			return new ActionForward("/agent/agent.do?thisAction=viewMyAgent");
		} else if (forward.equals("viewagentInfo")) {
			return new ActionForward("/agent/agent.do?thisAction=agentInfoById");
		} else if (forward.equals("mobileRechare")) {
			return new ActionForward(
					"/mobile/agent19pay.do?thisAction=mobileRechare");
		}
		return mapping.findForward("login");
	}

	public ActionForward forwardEmailLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Agent agent = null;
		Agent ag = (Agent) form;
		agent = agentBiz.getAgentByLoginName(ag.getLoginName());
		UserRightInfo uri = new UserRightInfo();
		uri.setAgent(agent);
		request.getSession().setAttribute("URI", uri);
		return null;
	}

	public ActionForward jumpTremUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Agent agent = (Agent) form;
		String selectPid = request.getParameter("provincesId");
		String selectCid = request.getParameter("citysId");
		String selectBid = request.getParameter("bankId");
		request.setAttribute("selectPid", selectPid);
		request.setAttribute("selectCid", selectCid);
		request.setAttribute("selectBid", selectBid);
		request.setAttribute("agent", agent);
		return mapping.findForward("editpersoninfo");
	}

	// 登陆提交后调用的方法
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String message = "";
		Agent ag = (Agent) form;

		if (ag == null) {
			message = "用户不存在!";
			request.setAttribute("error3", "error3");
			// 用户名出错 转到登陆页
			return mapping.findForward("login");
		}
		String loginResult = agentBiz.preprocessLogin(ag, request);
		UserRightInfo URI = new UserRightInfo();

		URI = (UserRightInfo) request.getSession().getAttribute("URI");
		if (loginResult.equals("errorRand")) {
			request.setAttribute("useRand", "yes");
			request.setAttribute("errorRand", "error");
			return mapping.findForward("login");
		} else if (loginResult.equals("NonexistentAgent")) {
			// 用户不存在
			message = "用户名不存在!";
			request.setAttribute("loginName", ag.getLoginName());
			request.setAttribute("error3", "error3");
			return mapping.findForward("login");

		} else if (loginResult.equals("NotActivation")) {
			// 没有激活用户
			request.setAttribute("error", "error");
			request.setAttribute("loginName", ag.getLoginName());
			return mapping.findForward("login");
		} else if (loginResult.equals("ConsecutiveLoginError")) {
			// 连续登录失败
			request.setAttribute("error2", "error2");
			request.setAttribute("loginName", ag.getLoginName());
			return mapping.findForward("login");
		} else if (loginResult.equals("LoginFails")) {
			// 登录失败
			request.setAttribute("useRand", "yes");
			request.setAttribute("errorPassword", "error");
			return mapping.findForward("login");
		} else {
			if (ag.getForwardPage().equals("yes")) {
				List list = agentCoterieBiz.getCoterieByAgentId(ag.getId());
				request.setAttribute("list", list);
				Coterie coterie = coterieBiz.getCoterieByAgent(ag);
				if (coterie != null && coterie.getId() > 0) {
					Agent at = agentBiz.getAgentById(coterie.getTempAgent()
							.getId());
					request.setAttribute("at", at);
					request.setAttribute("coterie", coterie);
				}
				return mapping.findForward("listCoterie");
			} else if ("fastpay".equals(ag.getForwardPage())) {
				return null;
			} else {
				if (URI.getAgentUser() != null)
					return viewCompanyInfo(mapping, form, request, response);
				// 测试特用帐号:zwwlmzy@vip.qq.com
				return mapping.findForward("mytest1");
			}
		}

	}

	public ActionForward loginByPaypassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String message = "";
		Agent ag = (Agent) form;

		if (ag == null) {
			message = "用户不存在!";
			request.setAttribute("error3", "error3");
			return mapping.findForward("login");
		}
		String loginResult = agentBiz.loginMD5(ag, request);
		UserRightInfo URI = new UserRightInfo();
		URI = (UserRightInfo) request.getSession().getAttribute("URI");
		if (loginResult.equals("errorRand")) {
			request.setAttribute("useRand", "yes");
			request.setAttribute("errorRand", "error");
			return mapping.findForward("login");
		} else if (loginResult.equals("NonexistentAgent")) {
			// 用户不存在
			message = "用户名不存在!";
			request.setAttribute("loginName", ag.getLoginName());
			request.setAttribute("error3", "error3");
			return mapping.findForward("login");

		} else if (loginResult.equals("NotActivation")) {
			// 没有激活用户
			request.setAttribute("error", "error");
			request.setAttribute("loginName", ag.getLoginName());
			return mapping.findForward("login");
		} else if (loginResult.equals("ConsecutiveLoginError")) {
			// 连续登录失败
			request.setAttribute("error2", "error2");
			request.setAttribute("loginName", ag.getLoginName());
			return mapping.findForward("login");
		} else if (loginResult.equals("LoginFails")) {
			// 登录失败
			request.setAttribute("useRand", "yes");
			request.setAttribute("errorPassword", "error");
			return mapping.findForward("login");
		} else {
			if (ag.getForwardPage().equals("yes")) {
				List list = agentCoterieBiz.getCoterieByAgentId(ag.getId());
				request.setAttribute("list", list);
				Coterie coterie = coterieBiz.getCoterieByAgent(ag);
				if (coterie != null && coterie.getId() > 0) {
					Agent at = agentBiz.getAgentById(coterie.getTempAgent()
							.getId());
					request.setAttribute("at", at);
					request.setAttribute("coterie", coterie);
				}
				return mapping.findForward("listCoterie");
			} else if ("fastpay".equals(ag.getForwardPage())) {
				return null;
			} else {

				if (URI.getAgentUser() != null)
					return viewCompanyInfo(mapping, form, request, response);
				// 测试特用帐号:zwwlmzy@vip.qq.com
				if (ag.getLoginName().equals("zwwlmzy@vip.qq.com")) {
					return mapping.findForward("mytest1");
				}
				return mapping.findForward("loginSuccess");
			}
		}

	}

	public ActionForward viewCompanyInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = URI.getAgent();
		try {
			Account account = accountBiz.getAccountByBind(agent.getId(),
					Account.bindStatus_Bind);
			request.setAttribute("account", account);
			request.setAttribute("agent", agent);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("viewCompanyInfo");
	}

	public ActionForward toRegister(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("registerFail");
	}

	public ActionForward activationLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("loginName");
		Agent agent = agentBiz.getAgentByLoginName(loginName);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("$RealyName$", agent.getName());
		String url = Constant.getLocalHost();
		String md5 = MD5.encrypt(agent.getLoginName());
		NoUtil noUtil = new NoUtil();
		String no = noUtil.getRandom(12);
		String mdno = MD5.encrypt(no);
		params.put("$url$", url
				+ "/agent/agent.do?thisAction=registerActivate&md5=" + md5
				+ "&email=" + agent.getLoginName() + "&no=" + no + "&mdno="
				+ mdno);
		String result = MailUtil.sslSend("激活帐户", "0001", agent.getLoginName(),
				params,Certification.getProtocol()); // 发邮件
		if (result.equals("")) {
			request.setAttribute("email", agent.getLoginName());
			TaskTimestamp tasktimestamp = new TaskTimestamp();
			tasktimestamp.setAgent(agent);
			tasktimestamp.setNo(no);
			tasktimestamp
					.setTaskDate(new Timestamp(System.currentTimeMillis()));
			tasktimestamp.setTaskHour(new Long(72));
			tasktimestamp.setTaskType(new Long(1));
			tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
			// 创建激活任务戳!
			return mapping.findForward("registerSuccess");
		} else {
			return mapping.findForward("sendemailfail");
		}
	}

	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		System.out.println("bbbbbbbbbbb");

		Agent ag = (Agent) form;
		String newemail = request.getParameter("newEmail");
		if (newemail != null && !newemail.equals("")) {
			ag.setName(newemail.substring(0, newemail.indexOf("@")));
			ag.setLoginName(newemail);
			ag.setReLoginName(newemail);
			ag.setPassword("123456");
			ag.setPayPassword("123456");
			ag.setEmail(newemail);
			ag.setTempEmail(newemail);
			Date dt = new Date();
			ag.setRegisterDate(new java.sql.Timestamp(dt.getTime()));
			PrintWriter pw = response.getWriter();
			if (agentBiz.register(ag) == null)
				pw.print("0");
			else {
				pw.print("1"); // 自动创建用户成功,发邮件
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("$RealyName$", newemail);
				String url = Constant.getLocalHost();
				String md5 = MD5.encrypt(newemail);
				NoUtil noUtil = new NoUtil();
				String no = noUtil.getRandom(12);
				String mdno = MD5.encrypt(no);
				params.put("$url$", url
						+ "/agent/agent.do?thisAction=registerActivate&md5="
						+ md5 + "&email=" + newemail + "&no=" + no + "&mdno="
						+ mdno);
				String result = MailUtil
						.sslSend("请激活帐号", "0001", newemail, params,Certification.getProtocol()); // 发邮件
				if (result.equals("")) {
					request.setAttribute("email", newemail);
					TaskTimestamp tasktimestamp = new TaskTimestamp();
					tasktimestamp.setAgent(ag);
					tasktimestamp.setNo(no);
					tasktimestamp.setTaskDate(new Timestamp(System
							.currentTimeMillis()));
					tasktimestamp.setTaskHour(new Long(72));
					tasktimestamp.setTaskType(new Long(1));
					tasktimestamp.setStatus(new Long(1));
					tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
					// 创建激活任务戳!
					// return mapping.findForward("registerSuccess");
				}
			}
			pw.close();
			return null;
		} else {

			String message = "";
			String rand = "";
			if (request.getSession().getAttribute("rand") != null)
				rand = request.getSession().getAttribute("rand").toString();

			if (ag.getLoginName() == null)
				return mapping.findForward("registerFail");

			if (rand.equals(ag.getRand())) {
				if (agentBiz.checkAgentByEmail(ag.getLoginName()) != null) {
					request.setAttribute("message", "该用户已经被使用");
					request.setAttribute("ag", ag);
					return mapping.findForward("registerFail");
				}
				if (ag.getLoginName() != null && ag.getPayPassword() != null
						&& ag.getLoginName().equals(ag.getReLoginName())
						&& ag.getPassword().equals(ag.getRepassword())
						&& ag.getPayPassword().equals(ag.getRepayPassword())) {
					ag.setTempEmail(ag.getLoginName());
					Agent agent = agentBiz.register(ag);
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("$RealyName$", agent.getName());
					String url = Constant.getLocalHost();
					String md5 = MD5.encrypt(agent.getEmail());
					NoUtil noUtil = new NoUtil();
					String no = noUtil.getRandom(12);
					String mdno = MD5.encrypt(no);
					params
							.put(
									"$url$",
									url
											+ "/agent/agent.do?thisAction=registerActivate&md5="
											+ md5 + "&email="
											+ agent.getLoginName() + "&no="
											+ no + "&mdno=" + mdno
											+ "&registerType="
											+ agent.getRegisterType()
											+ "&companyName="
											+ agent.getCompanyName()
											+ "&question="
											+ agent.getQuestion() + "&answer="
											+ agent.getAnswer() + "&password="
											+ agent.getPassword()
											+ "&payPassword="
											+ agent.getPayPassword());
					String result = MailUtil.sslSend("注册成功,请激活帐号", "0001", agent
							.getEmail(), params,Certification.getProtocol()); // 发邮件
					if (result.equals("")) {
						request.setAttribute("email", agent.getEmail());
						TaskTimestamp tasktimestamp = new TaskTimestamp();
						tasktimestamp.setAgent(agent);
						tasktimestamp.setNo(no);
						tasktimestamp.setTaskDate(new Timestamp(System
								.currentTimeMillis()));
						tasktimestamp.setTaskHour(new Long(72));
						tasktimestamp.setTaskType(new Long(1));
						tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
						// 创建激活任务戳!
						return mapping.findForward("registerSuccess");
					} else {
						return mapping.findForward("sendemailfail");
					}

				} else {
					message = "注册失败!!!输入的信息有误,请检查!";
					request.setAttribute("ag", ag);
					request.setAttribute("message", message);
				}
			} else {
				message = "注册失败!!!输入的验证码有误!";
				request.setAttribute("ag", ag);
				request.setAttribute("message", message);
			}

			return mapping.findForward("registerFail");
		}

	}

	public ActionForward checkExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Agent ag = (Agent) form;
		Agent agent = agentBiz.checkAgent2(ag);
		PrintWriter pw = response.getWriter();
		if (agent != null)
			pw.print("0");
		else
			pw.print("1");
		pw.close();
		return null;

	}

	public ActionForward removeSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return null;
	}

	public ActionForward registerActivate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("email");
		String tempMD5 = MD5.encrypt(loginName);
		String md5 = request.getParameter("md5");
		if (tempMD5.equals(md5)) {
			Agent agent = agentBiz.checkAgentByEmail(loginName);
			if (agent != null && agent.getStatus() == Agent.status_1) {
				return mapping.findForward("activedisabled");
			}
			request.setAttribute("agent", agent);
			return mapping.findForward("registeractivate");
		} else {
			return mapping.findForward("activedisabled");
		}

	}

	public ActionForward activate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Agent agent = (Agent) form;
		agent = agentBiz.activateAgent(agent);
		if (agent != null) {
			return mapping.findForward("activatesuccess");
		} else {
			return mapping.findForward("activatefail");
		}

	}

	public ActionForward agentInfoById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(uri.getAgent().getId());
		 agent.setAllowBalance(agentBiz.getAgentAllowBalance(agent.getId()));
		 agent.setNotallowBalance(agentBiz.getAgentNotAllowBalance(agent.getId()));
		 agent.setCreditBalance(agentBiz.getAgentCreditBalance(agent.getId()));
		long article = agentBiz.getNotCompletedTransactionArticle(agent);
		long articleRedPacket = agentBiz.getNotCompletedTransactionRedPacketArticle(agent);
		Transaction transaction =agentBiz.getNotCompletedTransactionRedPacket(agent);
		if(articleRedPacket>0){
		transaction.setTransactioner(agent);
		request.setAttribute("shopUrl", transaction.getOrderDetails().getShopUrl());
		}
		request.setAttribute("article", article);
		request.setAttribute("articleRedPacket", articleRedPacket);
		request.setAttribute("trans", transaction);
		request.setAttribute("viewAgentInfo",agent);
		
		return mapping.findForward("viewagentinfo");
	}

	// 跳转到删除银行卡号页面
	public ActionForward editBankByAgent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		try {
			Account bindAccount = accountBiz.getAccountByBind(agent.getId(),
					Account.bindStatus_Bind);
			List list = accountBiz.getAccounts(agent.getId());
			request.setAttribute("bindAccount", bindAccount);
			request.setAttribute("list", list);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("removeBankByAgent");
	}

	// 删除银行卡号
	public ActionForward removeBankByAgent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		Agent tempagent = agentBiz.getAgentById(agent.getId());
		String bindAccount = request.getParameter("bindAccount");
		Long accountId = new Long(bindAccount);
		List accounts = accountBiz.getAccountByAgentId(agent.getId());
		try {
			if (tempagent.getPayPassword().equals(
					MD5.encrypt(agent.getPayPassword()))) {
				for (int i = 0; i < accounts.size(); i++) {
					Account account = (Account) accounts.get(i);
					if (account.getId() == accountId
							&& account.getBindStatus() != Account.bindStatus_Bind) {
						accountBiz.removeAccountById(account.getId());
						return viewMyAgent(mapping, form, request, response);
					} else if (account.getId() == accountId
							&& account.getBindStatus() == Account.bindStatus_Bind) {
						request.setAttribute("msg", "您不能删除正在绑定的银行!");
						return editBankByAgent(mapping, form, request, response);
					}
				}
			} else {
				request.setAttribute("msg", "支付密码不正确!");
				return editBankByAgent(mapping, form, request, response);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("");
	}

	public ActionForward viewMyAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		try {
			agent = agentBiz.getAgentById(uri.getAgent().getId());
			AgentBalance agentBalance = agentBiz.getAgentBalance(uri.getAgent()
					.getId());
			agent.setAgentBalance(agentBalance);
			request.setAttribute("agent", agent);
		} catch (AppException e1) {
			e1.printStackTrace();
		}
		AgentListForm alf = new AgentListForm();
		try {

			alf.setList(accountBiz.getAccountByAgentId(agent.getId()));
			request.setAttribute("alf", alf);
		} catch (AppException e) {
			e.printStackTrace();
		}

		if (agent.getRegisterType() == 1) {// 企业账户是否创建商户圈
			boolean checkCoterie = coterieBiz.checkAgentInCoterie(agent);
			request.setAttribute("checkCoterie", checkCoterie);
		}

		AgentRelationship agentRelationship = agentRelationshipBiz
				.getAgentRelationshipBySalesman(agent);
		if (agentRelationship.getParentAgent() != null) {
			request.setAttribute("agentRelationship", agentRelationship);
		}
		return mapping.findForward("myagentinfo");
	}

	public ActionForward editEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		try {
			agent = agentBiz.getAgentById(agent.getId());
			request.setAttribute("agent", agent);
			return mapping.findForward("editemail");
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}

	}

	public ActionForward editAgentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Long id = new Long(request.getParameter("id"));

		try {
			Agent agent = agentBiz.getAgentById(id);
			request.setAttribute("agent", agent);

			return mapping.findForward("editagentinfo");
		} catch (AppException e) {

			e.printStackTrace();
			return null;
		}

	}

	public ActionForward updateAgentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Long id = new Long(request.getParameter("id"));
		Agent agent = (Agent) form;
		try {
			Agent tempagent = agentBiz.getAgentById(id);
			if (agent.getRegisterType() == 1) {
				tempagent.setCompanyName(agent.getCompanyName());
			}
			tempagent.setCertNum(agent.getCertNum());
			tempagent.setName(agent.getName());
			agentBiz.updateAgent(tempagent);
			return viewMyAgent(mapping, form, request, response);
		} catch (AppException e) {

			e.printStackTrace();
			return null;
		}

	}

	// 验证实名认证任务
	public ActionForward checkCertification(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Long id = new Long(request.getParameter("id"));
		String forward = null;
		String res = agentBiz.isCertification(id);

		AgentBalance ab = agentBiz.getAgentBalance(id);
		BigDecimal allBalance = ab.getAllowBalance().add(
				ab.getNotAllowBalance()).add(ab.getCreditBalance());
		// 判断用户余额是否是负数
		if (allBalance.compareTo(BigDecimal.ZERO) != -1) {
			// System.out.println("ok*********"+allBalance);
			if ("certificationfials".equals(res)) {
				forward = "certificationfials";
			} else if ("certifygate".equals(res)) {
				forward = "certifygate";
			}

		} else {
			// System.out.println("no*******"+allBalance);
			forward = "certificationError";
		}

		if ("toacknowledgementmoney".equals(res)) {
			forward = "toacknowledgementmoney";
		}
		Agent agent = agentBiz.getAgentById(id);
		Account account = accountBiz.getAccountByAgentIdcertificationStatus(
				agent.getId(), new Long(1));
		TaskTimestamp certifyTask = tasktimestampBiz.getTaskTimestamp(agent,
				TaskTimestamp.type_6);
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (certifyTask != null) {
			URI.getAgent()
					.setCheckMoneyCount(certifyTask.getCount().intValue());
		}
		request.setAttribute("agent", agent);
		request.setAttribute("account", account);

		return mapping.findForward(forward);
	}

	public ActionForward newCertification(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		TaskTimestamp tasktimestamp = new TaskTimestamp();
		tasktimestamp.setStatus(TaskTimestamp.status_1);
		tasktimestamp.setTaskType(TaskTimestamp.type_7);
		tasktimestamp.setTaskDate(new Timestamp(System.currentTimeMillis()));
		tasktimestamp.setTaskHour(new Long(24));// 任务期为一天
		tasktimestamp.setAgent(URI.getAgent());
		tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
		return mapping.findForward("mainlandpersonbaseinput");
	}

	public ActionForward updateEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		Agent agent = (Agent) form;
		Agent tempagent;
		try {
			tempagent = agentBiz.getAgentById(uri.getAgent().getId());
			if (tempagent.getPayPassword().equals(
					MD5.encrypt(agent.getPayPassword()))) {
				if (agentBiz.checkAgentByEmail(agent.getTempEmail()) != null) {
					request.setAttribute("msgEmail", "您输入的Email已经存在!");
				} else {
					tempagent.setTempEmail(agent.getTempEmail());
					agentBiz.updateAgent(tempagent);
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("$RealyName$", tempagent.getName());
					String url = Constant.getLocalHost();
					String md5 = MD5.encrypt(tempagent.getTempEmail());
					NoUtil noUtil = new NoUtil();
					String no = noUtil.getRandom(13);
					String mdno = MD5.encrypt(no);
					params.put("$url$", url
							+ "/agent/agent.do?thisAction=activateEmail&md5="
							+ md5 + "&email=" + tempagent.getTempEmail()
							+ "&no=" + no + "&mdno=" + mdno);
					String result = MailUtil.sslSend("修改邮件地址", "0001", tempagent
							.getTempEmail(), params,Certification.getProtocol()); // 发邮件
					String email = tempagent.getTempEmail();
					String mailUrl = "";
					if (email.indexOf(".") != email.lastIndexOf(".")) {
						mailUrl = email.replaceAll(email.substring(0,
								email.lastIndexOf(".")).substring(
								0,
								email.substring(0, email.lastIndexOf("."))
										.lastIndexOf(".")), "mail");
					} else {
						mailUrl = email.replaceAll(email.substring(0, email
								.indexOf("@") + 1), "mail.");
					}
					request.setAttribute("mailUrl", mailUrl);

					request.setAttribute("loginName", tempagent.getLoginName());
					if (result.equals("")) {

						tempagent.setEmail(tempagent.getTempEmail());
						request.setAttribute("agent", tempagent);
						// 创建任务戳
						TaskTimestamp tasktimestamp = new TaskTimestamp();
						tasktimestamp.setAgent(agent);
						tasktimestamp.setNo(no);
						tasktimestamp.setTaskDate(new Timestamp(System
								.currentTimeMillis()));
						tasktimestamp.setTaskHour(new Long(72));
						tasktimestamp.setTaskType(new Long(3));
						tasktimestamp.setStatus(new Long(1));
						tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
						return mapping.findForward("infosuccessemail");
					} else {
						return mapping.findForward("sendemailfail");
					}
				}
			} else {
				request.setAttribute("msgPayPassword", "您输入的支付密码有误!请重输!");
			}

			request.setAttribute("agent", tempagent);
			return mapping.findForward("editemail");
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}

	}

	public ActionForward sendactivationEmail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String email = request.getParameter("email");

		Agent agent = agentBiz.getAgentByLoginName(email);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("$RealyName$", agent.getName());
		String url = Constant.getLocalHost();
		String md5 = MD5.encrypt(email);
		NoUtil noUtil = new NoUtil();
		String no = noUtil.getRandom(13);
		String mdno = MD5.encrypt(no);
		params.put("$url$", url
				+ "/agent/agent.do?thisAction=activateEmail&md5=" + md5
				+ "&email=" + agent.getTempEmail() + "&no=" + no + "&mdno="
				+ mdno);
		String result = MailUtil.sslSend("激活邮件", "0001", agent.getTempEmail(),
				params,Certification.getProtocol()); // 发邮件
		String mailUrl = "";
		if (email.indexOf(".") != email.lastIndexOf(".")) {
			mailUrl = email.replaceAll(email.substring(0,
					email.lastIndexOf("."))
					.substring(
							0,
							email.substring(0, email.lastIndexOf("."))
									.lastIndexOf(".")), "mail");
		} else {
			mailUrl = email.replaceAll(email.substring(0,
					email.indexOf("@") + 1), "mail.");
		}
		request.setAttribute("mailUrl", mailUrl);

		request.setAttribute("email", agent.getTempEmail());
		// 创建任务戳
		TaskTimestamp tasktimestamp = new TaskTimestamp();
		tasktimestamp.setAgent(agent);
		tasktimestamp.setNo(no);
		tasktimestamp.setTaskDate(new Timestamp(System.currentTimeMillis()));
		tasktimestamp.setTaskHour(new Long(72));
		tasktimestamp.setTaskType(new Long(3));
		tasktimestamp.setStatus(new Long(1));
		tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
		return mapping.findForward("infosuccessemail");

	}

	public ActionForward activateEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String md5 = request.getParameter("md5");
		String requestmd5 = request.getParameter("md5");
		String no = request.getParameter("no");
		String mdno = request.getParameter("mdno");
		String tempmdno = MD5.encrypt(no);
		try {

			if (md5.equals(requestmd5) && mdno.equals(tempmdno)) {
				Agent agent = agentBiz.getAgentByName(email);
				TaskTimestamp tasktimestamp = tasktimestampBiz
						.getTaskTimestamp(agent, new Long(3));
				if (no.equals(tasktimestamp.getNo())
						&& tasktimestamp.getStatus() == 1) {
					agent.setEmail(email);
					agent.setLoginName(email);
					agent.setTempEmail(email);
					agentBiz.updateAgent(agent);
					tasktimestamp.setStatus(new Long(0));
					tasktimestampBiz.updateTaskTimestampStatus(tasktimestamp);
					request.setAttribute("email", email);
					return mapping.findForward("activatesuccess");
				} else {
					return mapping.findForward("activedisabled");
				}
			} else {
				return mapping.findForward("activedisabled");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

		return mapping.findForward("activatesuccess");
	}

	public ActionForward editTelephone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent tempAgent = uri.getAgent();
		try {
			tempAgent = agentBiz.getAgentById(tempAgent.getId());
			request.setAttribute("agent", tempAgent);

			return mapping.findForward("eidttelephone");
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}

	}

	public ActionForward updateTelephone(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;

		try {
			Agent tempagent = agentBiz.getAgentById(agent.getId());

			tempagent.setTelephone(agent.getTelephone());
			agentBiz.updateAgent(tempagent);
			UserRightInfo uri = (UserRightInfo) request.getSession()
					.getAttribute("URI");
			uri.setAgent(tempagent);
			return mapping.findForward("viewMyAgentAction");
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}

	}

	// 身份验证
	public ActionForward checkCertNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent tempAgent = agentBiz.getAgentById(uri.getAgent().getId());

		String returnMsg = "";
		if (tempAgent.getName().equals(agent.getRealName().trim())) {
			if (tempAgent.getPayPassword().equals(
					MD5.encrypt(agent.getPayPassword()))) {
				// 身份认证通过
				
				request.setAttribute("agent", tempAgent);
				return mapping.findForward("fillincertifyinfo");
			} else {
				returnMsg = "您输入的支付密码不正确,请核对！";
			}
		} else {
			returnMsg = "您输入的真实姓名必须与注册时填写的一致！";
		}

		request.setAttribute("agent", agent);
		request.setAttribute("smgYanZheng", returnMsg);
		return mapping.findForward("mainlandpersonbaseinput");

	}

	public ActionForward fillinPersonInfonext(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException,
			AppException {
		Agent agent = (Agent) form;
		Account account = agent.getAccount();
		String selectPid = request.getParameter("provincesId");
		String selectCid = request.getParameter("citysId");
		String selectBid = request.getParameter("bankId");
		request.setAttribute("selectPid", selectPid);
		request.setAttribute("selectBid", selectBid);
		request.setAttribute("", selectCid);
		if (selectPid == null || selectCid == null || selectBid == null) {
			mapping.findForward("error");
		}
		agent.setProvince(provinceBiz
				.getProvinceById(Long.parseLong(selectPid)));
		agent.setCity(cityBiz.getCityById(Long.parseLong(selectCid)));
		agent.getAccount().setBankId(new Long(selectBid));
		agent.setBankId(Integer.parseInt(selectBid));
		request.setAttribute("agent", agent);
		request.setAttribute("msg", "");
		// 查找所有省List

		try {
			if (accountBiz.getAccountByBanknum(account.getCardNo()) != null) {
				request.setAttribute("msg", "您输入的银行卡号已经被绑定过！");
				return mapping.findForward("fillincertifyinfo");
			}
			saveToken(request);
			return mapping.findForward("acknowledgementcertifyinfo");

		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ActionForward fillinPersonInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;

		if (isTokenValid(request, true)) {
			Account account = agent.getAccount();
			String selectCid = request.getParameter("citysId");
			account.setAgent(agent);
			Account tempaccount = new Account();
			if (agent.getRegisterType() == 0) {
				tempaccount.setAccountName(agent.getName());
			} else {
				tempaccount.setAccountName(agent.getCompanyName());
			}

			tempaccount.setCardNo(account.getCardNo());
			tempaccount.setAccountAddress(account.getAccountAddress());
			tempaccount.setCertificationStatus(Account.certificationStatus_1);
			Agent tempagent = new Agent();

			try {
				tempagent = agentBiz.getAgentById(agent.getId());
				tempagent.setTelephone(agent.getTelephone());
				tempagent.setMobile(agent.getMobile());
				tempagent.setAddress(agent.getAddress());
				tempaccount.setBankId(new Long(agent.getBankId()));
				tempagent.setCertNum(agent.getCertNum());
				tempagent.setBankId(agent.getBankId());
				tempagent.setCityId(Integer.parseInt(selectCid));
				tempagent.setAccount(tempaccount);
				tempagent.setName(agent.getName());
				tempagent.setCompanyName(agent.getCompanyName());
				tempagent.setStatus(Agent.status_2);
				accountBiz.addAccount(tempagent);

				// 申请一个提现
				Draw draw = new Draw();
				draw.setOrderNo(noUtil.getDrawNo());
				draw.setAgent(tempagent);
				Random random = new Random();
				int x = random.nextInt(19) + 1;
				float f = Float.parseFloat(String.valueOf(x)) / 100;
				System.out.println(f);
				BigDecimal authAmount = new BigDecimal(f + "");
				System.out.println("实名认证金额:" + authAmount);
				draw.setAmount(authAmount);
				draw.setRequestDate(new Timestamp(System.currentTimeMillis()));
				draw.setStatus(new Long(0));
				draw.setBank(tempaccount.getBankSname());
				draw.setCardNo(tempaccount.getCardNo());
				draw.setType(Draw.type_1);// 用于认证

				// ----------------------------
				draw.setCity(cityBiz.getCityById(Long.parseLong(selectCid)));// 城市
				draw.setAccountBank(account.getAccountAddress());// 开户行
				drawBiz.addDraw(draw);
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab =agentBiz.getAgentBalance(agent.getId());
				// 添加提现日志
				DrawLog dl = new DrawLog();
				dl.setAgent(agent);
				dl.setAmount(new BigDecimal(f + ""));
				dl.setOrderNo(draw.getOrderNo());
				dl.setContent("申请  当前商户可用余额：" + ab.getAllowBalance());
				dl.setStatus(new Long(0));
				dl.setChargeDate(new Timestamp(System.currentTimeMillis()));
				drawLogBiz.addDrawLog(dl);
				// 发送邮件
				//
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("$RealyName$", tempagent.getName());
				String url = Constant.getLocalHost();
				String md5 = MD5.encrypt(tempagent.getLoginName());
				NoUtil noUtil = new NoUtil();
				String no = noUtil.getRandom(13);
				String mdno = MD5.encrypt(no);
				params.put("$url$", url + "/login.jsp");
				String mailResult = MailUtil.sslSend("实名认证", "0004", tempagent
						.getEmail(), params,Certification.getProtocol()); // 发邮件
				if (mailResult.equals("")) {
					return mapping.findForward("applycertify");
				} else {
					return mapping.findForward("sendemailfail");
				}
			} catch (AppException e) {
				e.printStackTrace();
			}
			return mapping.findForward("applycertify");
		} else {
			return mapping.findForward("error");
		}

	}

	// 修改个人信息

	public ActionForward updatePersonInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException,
			AppException {

		Agent agent = (Agent) form;
		Account account = agent.getAccount();
		String selectPid = request.getParameter("provincesId");
		String selectCid = request.getParameter("citysId");
		String selectBid = request.getParameter("bankId");
		request.setAttribute("selectPid", selectPid);
		request.setAttribute("selectBid", selectBid);
		request.setAttribute("", selectCid);
		agent.setProvince(provinceBiz
				.getProvinceById(Long.parseLong(selectPid)));
		agent.setCity(cityBiz.getCityById(Long.parseLong(selectCid)));
		agent.getAccount().setBankId(new Long(selectBid));
		agent.setBankId(Integer.parseInt(selectBid));
		request.setAttribute("agent", agent);

		return mapping.findForward("fillincertifyinfo");
	}

	public ActionForward toCheckMoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws AppException {
		Agent tempagent =agentBiz.getAgentByRequest(request);
		try {
			tempagent.setAccount(accountBiz.getAccountByAgentIdcertificationStatus(tempagent.getId(),Account.certificationStatus_1));
			request.setAttribute("agent", tempagent);
			if (tempagent.getStatus() != 31) {
				return mapping.findForward("waitformoney");
			} else
				return mapping.findForward("acknowledgementmoney");
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ActionForward checkMoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws AppException {
		Agent agent = (Agent) form;
	
		Agent tempagent =agentBiz.getAgentByRequest(request);
		try {		
			tempagent.setAccount(accountBiz
					.getAccountByAgentIdcertificationStatus(tempagent.getId(),
							Account.certificationStatus_1));

			String result = agentBiz.checkMoney(agent, tempagent, request);
			request.setAttribute("agent", tempagent);

			if (result.equals("notmoney")) {
				request.setAttribute("notmoner", "输入的金额有误");
				return mapping.findForward("acknowledgementmoney");
			} else if (result.equals("errorcheckdisabled")) {
				return mapping.findForward("notpasscertift");
			} else if (result.equals("errormoney")) {
				request.setAttribute("errormoney", "输入的金额有误");
				return mapping.findForward("acknowledgementmoney");
			} else if (result.equals("passcertift")) {
				return mapping.findForward("passcertift");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		// 认证成功!
		return mapping.findForward("acknowledgementmoney");
	}

	public ActionForward attestation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long agentId = new Long(request.getParameter("agentId").toString());
		request.setAttribute("msg", true);
		try {
			if (agentBiz.attestation(agentId)) {
				UserRightInfo URI = (UserRightInfo) request.getSession()
						.getAttribute("URI");
				Agent agent = agentBiz.getAgentById(agentId);
				URI.setAgent(agent);
				request.setAttribute("msg", false);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("msgjsp");
	}

	public ActionForward toEditPersonInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		request.setAttribute("agent", agent);
		return mapping.findForward("editpersoninfo");
	}

	public ActionForward editPersonInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Agent agent = (Agent) form;
		int selectPid = agent.getProvinceId();
		int selectCid = agent.getCityId();
		int selectBid = agent.getBankId();
		request.setAttribute("selectPid", selectPid);
		request.setAttribute("selectBid", selectBid);
		request.setAttribute("", selectCid);
		try {
			agent.setProvince(provinceBiz.getProvinceById(new Long(selectPid)));
			agent.setCity(cityBiz.getCityById(new Long(selectCid)));
		} catch (AppException e) {			
			e.printStackTrace();
		}

		agent.getAccount().setBankId(new Long(selectBid));
		agent.setBankId(selectBid);
		request.setAttribute("agent", agent);

		return mapping.findForward("fillincertifyinfo");
	}

	// 更换银行帐号
	public ActionForward tochangeBindBank(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		try {
			Account bindAccount = accountBiz.getAccountByBind(agent.getId(),
					Account.bindStatus_Bind);
			List list = accountBiz.getAccounts(agent.getId());
			request.setAttribute("bindAccount", bindAccount);
			request.setAttribute("list", list);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("editbindaccount");
	}

	// 更改绑定的支付银行
	public ActionForward changeBindBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Agent agent = (Agent) form;
		String bindAccount = request.getParameter("bindAccount");
		Long accountId = new Long(bindAccount);
		Agent tempagent;
		try {
			tempagent = agentBiz.getAgentById(agent.getId());
			if (tempagent != null
					&& tempagent.getPayPassword().equals(
							MD5.encrypt(agent.getPayPassword()))) {

				Account tempaccount = accountBiz.getAccountByBind(
						agent.getId(), Account.bindStatus_Bind);
				tempaccount.setBindStatus(Account.bindStatus_NoBind);
				accountBiz.updateAccount(tempaccount);
				tempaccount = accountBiz.getAccountById(accountId);
				if (tempaccount != null) {
					tempaccount.setBindStatus(Account.bindStatus_Bind);
					accountBiz.updateAccount(tempaccount);
				}
			} else {
				request.setAttribute("msg", "支付密码不正确!");
				return tochangeBindBank(mapping, form, request, response);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("toviewmyagentaction");
	}

	//
	public ActionForward newAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent) form;
		String res = accountBiz.fillinAccountInfo(agent);
		if (res.equals("existentCardNo")) {
			request.setAttribute("error", "existentCardNo");
			return mapping.findForward("fillinaccountinfo");
		} else {
			return addAccountNext(mapping, form, request, response);// 下一步
		}
	}

	public ActionForward addAccountNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent) form;
		String selectPid = request.getParameter("provincesId");
		String selectCid = request.getParameter("citysId");
		String selectBid = request.getParameter("banksId");
		request.setAttribute("selectPid", selectPid);
		request.setAttribute("selectCid", selectCid);
		request.setAttribute("selectBid", selectBid);
		agent.setProvince(provinceBiz
				.getProvinceById(Long.parseLong(selectPid)));
		agent.setCity(cityBiz.getCityById(Long.parseLong(selectCid)));
		// agent.setBank(bankBiz.getBankById(Long.parseLong(selectBid)));
		request.setAttribute("bankName", com.fordays.fdpay.base.Bank
				.getCName(agent.getBankId() + "c"));
		request.setAttribute("agent", agent);
		return mapping.findForward("viewaccountinfo");
	}

	public ActionForward updateAccountInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		String selectPid = request.getParameter("provincesId");
		String selectCid = request.getParameter("citysId");
		String selectBid = request.getParameter("banksId");
		request.setAttribute("selectPid", selectPid);
		request.setAttribute("selectCid", selectCid);
		request.setAttribute("selectBid", selectBid);
		List accounts;
		try {
			accounts = accountBiz.getAccountByAgentId(agent.getId());
			if (accounts != null && accounts.size() > 0) {
				request.setAttribute("accounts", accounts);
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		request.setAttribute("agent", agent);
		return mapping.findForward("fillinaccountinfo");
	}

	public ActionForward addAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Agent agent = (Agent) form;
		String selectBid = request.getParameter("banksId");
		String selectCid = request.getParameter("citysId");
		if (selectBid != null && selectCid != null) {
			agent.setBankId(Integer.parseInt(selectBid));
			agent.setCityId(Integer.parseInt(selectCid));
			try {
				accountBiz.addAccount(agent);
			} catch (AppException e) {
				e.printStackTrace();
			}
		} else {
			return mapping.findForward("error");
		}
		return mapping.findForward("infosuccessaddaccount");
	}

	public ActionForward getJosnCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Province province = new Province();
		province.setId(1);
		try {
			String pid = request.getParameter("pId");

			province.setId(Long.parseLong(pid));
			List<City> list = cityBiz.getCityByProvince(province);
			Josn josn = new Josn();
			String resjosn = josn.toJosnCitys(list);
			try {
				response.getWriter().write(resjosn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward getJosnBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List list = bankBiz.getBankList();// 获取银行名称列表
			Josn josn = new Josn();
			String resjosn = josn.toJosnBank(list);
			try {
				response.getWriter().write(resjosn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward getJosnProvince(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<Province> list = provinceBiz.getProvinces();
			Josn josn = new Josn();
			String resjosn = josn.toJosnProvince(list);
			try {
				response.getWriter().write(resjosn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 忘了登录密码!
	public ActionForward forgetPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");// 类型:登录密码，支付密码
		request.setAttribute("type", type);
		return mapping.findForward("selfhelpchangepasswordfirst");
	}

	public ActionForward forgetPasswordNext(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		try {
			agent = agentBiz.checkAgentByEmail(agent.getLoginName());
			if (agent != null) {
				request.setAttribute("loginName", agent.getLoginName());
				request.setAttribute("mobileBindStatus", agent
						.getMobileBindStatus());
				return mapping.findForward("selfhelpchangepasswordfashion");// 选择自助修改的方式
			} else {
				request.setAttribute("msg", "用户不存在");
				return mapping.findForward("selfhelpchangepasswordfirst");// 先输入帐号再修改密码
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward jumpTermFashion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String loginName = request.getParameter("loginName");
		try {
			agent = agentBiz.checkAgentByEmail(loginName);
			if (agent != null) {
				request.setAttribute("mobileBindStatus", agent
						.getMobileBindStatus());
			}
		} catch (AppException e) {
			return mapping.findForward("error");
		}
		request.setAttribute("loginName", loginName);
		return mapping.findForward("selfhelpchangepasswordfashion");// 选择自助修改的方式
	}

	public ActionForward sendEmailChangePasswordQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		// 发邮件
		Agent agent;
		try {
			agent = agentBiz.getAgentByLoginName(loginName);
			if (agent == null) {
				return mapping.findForward("error");
			}
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("$RealyName$", agent.getName());
			String url = Constant.getLocalHost();
			String md5 = MD5.encrypt(agent.getEmail());
			NoUtil noUtil = new NoUtil();
			String no = noUtil.getRandom(13);
			String mdno = MD5.encrypt(no);
			params.put("$url$", url
					+ "/agent/agent.do?thisAction=changePasswordQuestion&md5="
					+ md5 + "&email=" + agent.getLoginName()
					+ "&type=password&no=" + no + "&mdno=" + mdno);
			
			
			String result = MailUtil.sslSend("修改密码", "0002", agent.getEmail(),
					params,Certification.getProtocol()); // 发邮件

			if (result.equals("")) {
				TaskTimestamp tasktimestamp = new TaskTimestamp();
				tasktimestamp.setAgent(agent);
				tasktimestamp.setNo(no);
				tasktimestamp.setTaskDate(new Timestamp(System
						.currentTimeMillis()));
				tasktimestamp.setTaskHour(new Long(72));
				tasktimestamp.setTaskType(new Long(2));
				tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
			} else {
				return mapping.findForward("sendemailfail");
			}
			request.setAttribute("type", "password");
			request.setAttribute("agent", agent);
		} catch (AppException e) {
			e.printStackTrace();
		}

		return mapping.findForward("infosendemail");
	}

	public ActionForward sendEmailChangePasswordCertNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		// 发邮件
		Agent agent;
		try {
			agent = agentBiz.getAgentByLoginName(loginName);
			if (agent == null) {
				return mapping.findForward("error");
			}
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("$RealyName$", agent.getName());
			String url = Constant.getLocalHost();
			String md5 = MD5.encrypt(agent.getEmail());
			NoUtil noUtil = new NoUtil();
			String no = noUtil.getRandom(13);
			String mdno = MD5.encrypt(no);
			params.put("$url$", url
					+ "/agent/agent.do?thisAction=changePasswordCertNo&md5="
					+ md5 + "&email=" + agent.getLoginName()
					+ "&type=password&no=" + no + "&mdno=" + mdno);
			System.out
					.println("---------------------------start send email ------url="
							+ url);
			String result = MailUtil.sslSend("找回密码", "0002", agent.getEmail(),
					params,Certification.getProtocol()); // 发邮件
			System.out
					.println("---------------------------send email over ------result="
							+ result);
			if (result.equals("")) {
				TaskTimestamp tasktimestamp = new TaskTimestamp();
				tasktimestamp.setAgent(agent);
				tasktimestamp.setNo(no);
				tasktimestamp.setTaskDate(new Timestamp(System
						.currentTimeMillis()));
				tasktimestamp.setTaskHour(new Long(72));
				tasktimestamp.setTaskType(new Long(2));
				tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
			} else {
				return mapping.findForward("sendemailfail");
			}
			request.setAttribute("type", "password");
			request.setAttribute("agent", agent);
		} catch (AppException e) {
			e.printStackTrace();
		}
		String mailUrl = "";
		if (loginName.indexOf(".") != loginName.lastIndexOf(".")) {
			mailUrl = loginName.replaceAll(loginName.substring(0,
					loginName.lastIndexOf(".")).substring(
					0,
					loginName.substring(0, loginName.lastIndexOf("."))
							.lastIndexOf(".")), "mail");
		} else {
			mailUrl = loginName.replaceAll(loginName.substring(0, loginName
					.indexOf("@") + 1), "mail.");
		}
		request.setAttribute("mailUrl", mailUrl);
		return mapping.findForward("infosendemail");
	}

	public ActionForward sendEmailChangePayPasswordQuestion(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		// 发邮件
		Agent agent;
		try {
			agent = agentBiz.getAgentByLoginName(loginName);
			if (agent == null) {
				return mapping.findForward("error");
			}
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("$RealyName$", agent.getName());
			String url = Constant.getLocalHost();
			String md5 = MD5.encrypt(agent.getEmail());
			NoUtil noUtil = new NoUtil();
			String no = noUtil.getRandom(13);
			String mdno = MD5.encrypt(no);
			params.put("$url$", url
					+ "/agent/agent.do?thisAction=changePasswordQuestion&md5="
					+ md5 + "&email=" + agent.getLoginName()
					+ "&type=paypassword&no=" + no + "&mdno=" + mdno);
			String result = MailUtil.sslSend("找回支付密码", "0002", agent.getEmail(),
					params,Certification.getProtocol()); // 发邮件
			String mailUrl = "";
			if (loginName.indexOf(".") != loginName.lastIndexOf(".")) {
				mailUrl = loginName.replaceAll(loginName.substring(0,
						loginName.lastIndexOf(".")).substring(
						0,
						loginName.substring(0, loginName.lastIndexOf("."))
								.lastIndexOf(".")), "mail");
			} else {
				mailUrl = loginName.replaceAll(loginName.substring(0, loginName
						.indexOf("@") + 1), "mail.");
			}
			request.setAttribute("mailUrl", mailUrl);
			if (result.equals("")) {
				TaskTimestamp tasktimestamp = new TaskTimestamp();
				tasktimestamp.setAgent(agent);
				tasktimestamp.setNo(no);
				tasktimestamp.setTaskDate(new Timestamp(System
						.currentTimeMillis()));
				tasktimestamp.setTaskHour(new Long(72));
				tasktimestamp.setTaskType(new Long(5));
				tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
			} else {
				return mapping.findForward("sendemailfail");
			}
			request.setAttribute("type", "paypassword");
			request.setAttribute("agent", agent);
		} catch (AppException e) {
			e.printStackTrace();
		}

		return mapping.findForward("infosendemail");
	}

	public ActionForward sendEmailChangePayPasswordCertNo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		// 发邮件
		Agent agent;
		try {
			agent = agentBiz.getAgentByLoginName(loginName);
			if (agent == null) {
				return mapping.findForward("error");
			}
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("$RealyName$", agent.getName());
			String url = Constant.getLocalHost();
			String md5 = MD5.encrypt(agent.getEmail());
			NoUtil noUtil = new NoUtil();
			String no = noUtil.getRandom(13);
			String mdno = MD5.encrypt(no);
			params.put("$url$", url
					+ "/agent/agent.do?thisAction=changePasswordCertNo&md5="
					+ md5 + "&email=" + agent.getLoginName()
					+ "&type=paypassword&no=" + no + "&mdno=" + mdno);
			String result = MailUtil.sslSend("找回支付密码", "0002", agent.getEmail(),
					params,Certification.getProtocol()); // 发邮件
			String mailUrl = "";
			if (loginName.indexOf(".") != loginName.lastIndexOf(".")) {
				mailUrl = loginName.replaceAll(loginName.substring(0,
						loginName.lastIndexOf(".")).substring(
						0,
						loginName.substring(0, loginName.lastIndexOf("."))
								.lastIndexOf(".")), "mail");
			} else {
				mailUrl = loginName.replaceAll(loginName.substring(0, loginName
						.indexOf("@") + 1), "mail.");
			}
			request.setAttribute("mailUrl", mailUrl);
			if (result.equals("")) {
				TaskTimestamp tasktimestamp = new TaskTimestamp();
				tasktimestamp.setAgent(agent);
				tasktimestamp.setNo(no);
				tasktimestamp.setTaskDate(new Timestamp(System
						.currentTimeMillis()));
				tasktimestamp.setTaskHour(new Long(72));
				tasktimestamp.setTaskType(new Long(5));
				tasktimestampBiz.saveTaskTimestamp(tasktimestamp);
			} else {
				return mapping.findForward("sendemailfail");
			}
			request.setAttribute("type", "paypassword");
			request.setAttribute("agent", agent);
		} catch (AppException e) {
			e.printStackTrace();
		}

		return mapping.findForward("infosendemail");
	}

	public ActionForward changePasswordQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String email = request.getParameter("email");
		String demd5 = MD5.encrypt(email);
		String md5 = request.getParameter("md5");
		String type = request.getParameter("type");
		String no = request.getParameter("no");
		String mdno = request.getParameter("mdno");
		String tempmdno = MD5.encrypt(no);
		if (email != null && md5 != null && type != null && no != null
				&& mdno != null) {
			try {
				if (demd5.equals(md5) && mdno.equals(tempmdno)) {
					Agent agent;
					agent = agentBiz.getAgentByLoginName(email);
					if (agent == null) {
						return mapping.findForward("error");
					}
					TaskTimestamp taskTimestamp = new TaskTimestamp();
					if (type.equals("password")) {
						taskTimestamp = tasktimestampBiz.getTaskTimestamp(
								agent, new Long(2));
					}
					if (type.equals("paypassword")) {
						taskTimestamp = tasktimestampBiz.getTaskTimestamp(
								agent, new Long(5));
					}

					if (taskTimestamp.getNo().equals(no)) {
						List<QuestionsAndAnswers> listquestionanswer = agentBiz
								.RandQuestions(agent);
						request.getSession().setAttribute("listquestionanswer",
								listquestionanswer);
						request.setAttribute("agent", agent);
						if (type.equals("password")) {
							request.setAttribute("type", "password");
							return mapping
									.findForward("selfhelpchangepasswordquestion");
						}
						if (type.equals("paypassword")) {
							request.setAttribute("type", "paypassword");
							return mapping
									.findForward("selfhelpchangepasswordquestion");
						}
					} else {
						return mapping.findForward("activedisabled");
					}
				} else {
					return mapping.findForward("activatefail");
				}
			} catch (AppException e) {
				e.printStackTrace();
			}
		} else {
			return mapping.findForward("activedisabled");
		}
		return null;
	}

	public ActionForward validateQuestions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String[] answers = request.getParameterValues("answers");
		String loginName = request.getParameter("loginName");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("loginName", loginName);
		try {
			List<QuestionsAndAnswers> listquestionandanswer = (List<QuestionsAndAnswers>) request
					.getSession().getAttribute("listquestionanswer");
			List errorlist = new ArrayList();
			for (int i = 0; i < listquestionandanswer.size(); i++) {
				if (!listquestionandanswer.get(i).answer.equals(answers[i])) {
					errorlist.add(listquestionandanswer.get(i).question
							+ ":回答不正确!");
				}
			}
			if (errorlist.size() > 0) {
				request.setAttribute("error", "error");
				request.setAttribute("errorlist", errorlist);
				return mapping.findForward("selfhelpchangepasswordquestion");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (type.equals("password")) {
			return mapping.findForward("selfhelpsetpassword");
		}
		if (type.equals("paypassword")) {
			return mapping.findForward("setpaypassword");
		}
		return null;
	}

	public ActionForward setPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		Agent tempagent = (Agent) form;
		try {
			Agent agent = agentBiz.getAgentByLoginName(loginName);
			if (agent == null) {
				return mapping.findForward("error");
			}
			if (agent.getPayPassword().equals(
					MD5.encrypt(tempagent.getRepassword()))) {
				request.setAttribute("error1", "error1");
				request.setAttribute("loginName", loginName);
				return mapping.findForward("selfhelpsetpassword");
			}
			if (!"".equals(tempagent.getRepassword())
					&& tempagent.getRepassword().equals(
							tempagent.getRepassword2())) {

				agent.setPassword(MD5.encrypt(tempagent.getRepassword()));
				agentBiz.updateAgent(agent);
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("$RealyName$", agent.getName());
				String url = Constant.getLocalHost();
				String md5 = MD5.encrypt(agent.getEmail());
				params.put("$url$", url + "index.jsp");
				String result = MailUtil.sslSend("修改密码", "0003", agent.getEmail(),
						params,Certification.getProtocol()); // 发邮件

				TaskTimestamp taskTimestamp = tasktimestampBiz
						.getTaskTimestamp(agent, new Long(4), new Long(0),
								new Long(3));
				if (taskTimestamp != null) {
					taskTimestamp.setStatus(new Long(1));// 关闭
					tasktimestampBiz.updateTaskTimestampStatus(taskTimestamp);
					System.out.println("关闭任务戳!");
				}

				return mapping.findForward("infochangepasswordsuccess");
			} else {
				request.setAttribute("error", "error");
				request.setAttribute("loginName", loginName);
				return mapping.findForward("selfhelpsetpassword");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}

		return mapping.findForward("selfhelpchangepasswordquestion");
	}

	public ActionForward setPayPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		Agent tempagent = (Agent) form;
		System.out.println(tempagent.getPassword());
		try {
			Agent agent = agentBiz.getAgentByLoginName(loginName);
			if (agent == null) {
				return mapping.findForward("error");
			}
			if (agent.getPassword().equals(
					MD5.encrypt(tempagent.getRepassword()))) {
				request.setAttribute("error1", "error1");
				request.setAttribute("loginName", loginName);
				return mapping.findForward("setpaypassword");
			}
			if (!tempagent.getRepassword().equals("")
					&& tempagent.getRepassword().equals(
							tempagent.getRepassword2())) {

				agent.setPayPassword(MD5.encrypt(tempagent.getRepassword()));
				agentBiz.updateAgent(agent);
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("$RealyName$", agent.getName());
				String url = Constant.getLocalHost();
				String md5 = MD5.encrypt(agent.getEmail());
				params.put("$url$", url + "index.jsp");
				String result = MailUtil.sslSend("修改支付密码", "0003", agent
						.getEmail(), params,Certification.getProtocol()); // 发邮件
				return mapping.findForward("infochangepaypasswordsuccess");
			} else {
				request.setAttribute("error", "error");
				request.setAttribute("loginName", loginName);
				return mapping.findForward("setpaypassword");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("selfhelpchangepasswordquestion");
	}

	public ActionForward changePasswordCertNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginName = request.getParameter("email");
		String type = request.getParameter("type");
		String md5 = MD5.encrypt(loginName);
		String requestmd5 = request.getParameter("md5");
		String no = request.getParameter("no");
		String mdno = request.getParameter("mdno");
		String tempmdno = MD5.encrypt(no);

		if (md5.equals(requestmd5) && mdno.equals(tempmdno)) {
			try {
				Agent agent = agentBiz.getAgentByLoginName(loginName);
				if (agent == null) {
					return mapping.findForward("error");
				}
				TaskTimestamp tasktimestamp = new TaskTimestamp();
				if (type.equals("password")) {
					tasktimestamp = tasktimestampBiz.getTaskTimestamp(agent,
							new Long(2));
				}
				if (type.equals("paypassword")) {
					tasktimestamp = tasktimestampBiz.getTaskTimestamp(agent,
							new Long(5));
				}
				if (tasktimestamp != null && tasktimestamp.getNo().equals(no)) {
					request.setAttribute("loginName", loginName);
					request.setAttribute("type", type);
					return mapping.findForward("selfhelpchangepasswordcertno");
				} else {
					return mapping.findForward("activedisabled");
				}
			} catch (AppException e) {
				e.printStackTrace();
			}
		} else {
			return mapping.findForward("activefail");
		}
		return null;
	}

	public ActionForward validateCertNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String loginName = request.getParameter("loginName");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String certNo = request.getParameter("certNo");
		request.setAttribute("loginName", loginName);
		try {
			Agent agent = agentBiz.getAgentByLoginName(loginName);
			if (agent != null) {
				if (certNo.equals(agent.getCertNum())) {
					if (type.equals("password"))
						return mapping.findForward("selfhelpsetpassword");
					if (type.equals("paypassword"))
						return mapping.findForward("setpaypassword");
				} else {
					request.setAttribute("error", "errorCertNo");
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("selfhelpchangepasswordcertno");
	}

	public ActionForward checkPayPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		if (URI.getAgent().getStatus() != 3) {
			return mapping.findForward("tipscertification");
		}

		if (agent.getPayPassword() != null
				&& !URI.getAgent().getPayPassword().equals(
						MD5.encrypt(agent.getPayPassword()))) {
			request.setAttribute("errorPayPassword", "true");
			return mapping.findForward("checkpaypassword");
		}
		if (agent.getPayPassword() == null) {
			return mapping.findForward("checkpaypassword");
		}
		agent.setThisAction("checkPayPassword");
		List accounts = accountBiz.getAccountByAgentId(URI.getAgent().getId());
		if (accounts != null && accounts.size() > 0) {
			request.setAttribute("accounts", accounts);
		}
		return mapping.findForward("fillinaccountinfo");
	}

	// 加入商户圈
	public ActionForward saveAgentCoterie(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		Coterie co = coterieBiz.getCoterieById(agent.getCoterieId());

		AgentCoterie ac = agentCoterieBiz.queryByAgentEmail(agent
				.getLoginName(), co.getPartner());

		Agent myagent = agentBiz.getAgentByLoginName(agent.getLoginName());

		if (myagent.getName() == null) {
			request.setAttribute("coterie", co);
			request.setAttribute("msLongName", "此商户不存在！");
			request.setAttribute("mspwd", "");
			return mapping.findForward("addagentcoterie");
		} else if (!myagent.getPayPassword().equals(
				MD5.encrypt(agent.getPayPassword()))) {
			request.setAttribute("coterie", co);
			request.setAttribute("mspwd", "支付密码错误！");
			request.setAttribute("msLongName", "");
			return mapping.findForward("addagentcoterie");
		}
		if (ac != null) {
			request.setAttribute("coterie", co);
			request.setAttribute("msLongName", "此商户在商户圈中已经存在！");
			request.setAttribute("mspwd", "");
			return mapping.findForward("addagentcoterie");
		}

		boolean check = agentCoterieBiz.checkCoerieByAllowMult(myagent.getId());
		int count = agentCoterieBiz.getAgentCoterieByAgent_Id(myagent.getId());
		if (check) {

			request.setAttribute("coterie", co);
			request.setAttribute("msLongName", "该账户已经加入了独立的商户圈，不能加入："
					+ co.getName() + "！");
			request.setAttribute("mspwd", "");
			return mapping.findForward("addagentcoterie");

		} else {

			if (co.getAllowMultcoterie().equals("0") && count > 0) {
				request.setAttribute("coterie", co);
				request.setAttribute("msLongName", "该账户已加入商户圈！不能加入独立商户圈！！");
				request.setAttribute("mspwd", "");
				return mapping.findForward("addagentcoterie");

			}

		}

		AgentCoterie acc = new AgentCoterie();
		acc.setAgent(myagent);
		acc.setCoterie(co);
		acc.setStatus(new Long(1));
		acc.setCreateDate(new Timestamp(System.currentTimeMillis()));
		if (agent.getLeaveDays() != null && !"".equals(agent.getLeaveDays()))
			acc.setLeaveDays(Long.parseLong(agent.getLeaveDays()));
		/*
		 * else acc.setLeaveDays(0l);
		 */
		if (agent.getFromDate() != null && !"".equals(agent.getFromDate())) {
			Timestamp fromDate = DateUtil.getTimestamp(agent.getFromDate()
					.replaceAll("%20", " "), "yyyy-MM-dd");
			acc.setFromDate(fromDate);
		}
		if (agent.getExpireDate() != null && !"".equals(agent.getExpireDate())) {

			Timestamp expireDate = DateUtil.getTimestamp(agent.getExpireDate()
					.replaceAll("%20", " "), "yyyy-MM-dd");
			acc.setExpireDate(expireDate);
		}

		if (agent.getTransactionScope() != null
				&& !"".equals(agent.getTransactionScope()))
			acc
					.setTransactionScope(Long.parseLong(agent
							.getTransactionScope()));
		/*
		 * else acc.setTransactionScope(0l);
		 */
		if (agent.getMinAmount() != null && !"".equals(agent.getMinAmount()))
			acc.setMinAmount(new BigDecimal(agent.getMinAmount()));
		/*
		 * else acc.setMinAmount(new BigDecimal(0));
		 */
		if (agent.getRepaymentType() != null
				&& !"".equals(agent.getRepaymentType()))
			acc.setRepaymentType(Long.parseLong(agent.getRepaymentType()));
		/*
		 * else acc.setRepaymentType(0l);
		 */

		agentCoterieBiz.add(acc);

		request.setAttribute("agent", acc.getAgent());
		request.setAttribute("coterie", co);
		return mapping.findForward("waitagentcoterie");
	}

	// 查询商户圈列表
	public ActionForward listConterie(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String loginName = request.getParameter("name");
		if (loginName != null) {
			try {
				Agent agent = agentBiz.getAgentByLoginName(loginName);
				agent.setForwardPage("yes");
				return login(mapping, agent, request, response);
			} catch (Exception e) {
				return mapping.findForward("login");
			}
		} else {
			return mapping.findForward("login");
		}
	}

	public ActionForward listChildAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(URI.getAgent().getId());
		List list = agentRelationshipBiz.getAgentRelationshipByAgent(agent);
		request.setAttribute("list", list);
		return mapping.findForward("listchildagent");
	}

	public ActionForward viewMobileCenter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(URI.getAgent().getId());
		request.setAttribute("agent", agent);
		return mapping.findForward("mobilecenter");
	}

	public ActionForward viewMobileBinding(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		return mapping.findForward("mobilebinding");
	}

	public ActionForward sendValidateCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		if (agent != null
				&& !agent.getRand().equals(
						request.getSession().getAttribute("rand"))) {
			request.setAttribute("rand_Error", "error");
			return mapping.findForward("mobilebinding");
		}
		
		
		String mobilePhone = agent.getMobilePhone();
		if(!agentBiz.IsBindingMobilePhone(mobilePhone)){
		String dd = DateUtil.getDateString("yyyy年MM月dd日");
		NoUtil noUtil = new NoUtil();
		String code = noUtil.getRandom(6);
		System.out.println(code);
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		URI.getAgent().setMobileValidateCode(code);
		request.setAttribute("mobilePhone", agent.getMobilePhone());
		SMUtil.send(mobilePhone, dd + ",您申请钱门手机服务,验证码是:[" + code + "]");
		return mapping.findForward("mobilebindingvalidate");
		}else{
			request.setAttribute("rand_Error", "error2");
			return mapping.findForward("mobilebinding");
		}
	}

	public ActionForward ajxSendValidateCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String mobilePhone = request.getParameter("mobile");
		String dd = DateUtil.getDateString("yyyy年MM月dd日");
		long msgType = new Long(request.getParameter("msgType"));
		NoUtil noUtil = new NoUtil();
		String code = noUtil.getRandom(6);
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI != null) {
			URI.getAgent().setMobileValidateCode(code);
		}
		if (msgType == Agent.msg_type_1) {
			SMUtil.send(mobilePhone, dd + ",您申请钱门手机服务,验证码是:" + code
					+ ",如非本人操作，请联系钱门客服:");
		} else if (msgType == Agent.msg_type_2) {
			SMUtil.send(mobilePhone, dd + ",您申请了解除手机绑定,您所有的手机服务都将被停止。验证码是:"
					+ code + "");
		} else if (msgType == Agent.msg_type_3) {
			request.getSession().setAttribute("tempValidateCode", code);
			SMUtil.send(mobilePhone, dd + ",您使用手机找回密码业务,验证码是:[" + code + "]");
		} else if (msgType == 4) {
			SMUtil.send(mobilePhone, dd + ",您申请取消钱门手机动态口令业务,验证码是:[" + code
					+ "]");
		} else if (msgType == 5) {
			SMUtil.send(mobilePhone, dd + ",您开启允许使用余额支付，验证码是:[" + code + "]");
		}
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward bindingMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent) form;
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		request.setAttribute("mobilePhone", agent.getMobilePhone());

		if (agent != null
				&& !agent.getTempMobileValidateCode().equals(
						URI.getAgent().getMobileValidateCode())) {
			request.setAttribute("codeError", "error");
			return mapping.findForward("mobilebindingvalidate");
		} else if (!MD5.encrypt(agent.getPayPassword()).equals(
				URI.getAgent().getPayPassword())) {
			request.setAttribute("payPasswordError", "error");
			return mapping.findForward("mobilebindingvalidate");
		} else {
			URI.getAgent().setMobileBindStatus(Agent.mobile_status_1);
			URI.getAgent().setMobilePhone(agent.getMobilePhone());
			agentBiz.updateStatus(URI.getAgent());
			if (URI.getAgent().getMobileBusiness().equals(
					"updateMobileQuestionStatus")) {
				return startupMobileQuestion(mapping, form, request, response);
			} else if (URI.getAgent().getMobileBusiness().equals(
					"updateMobilePasswordStatus")) {
				return startupMobilePassword(mapping, form, request, response);
			} else if (URI.getAgent().getMobileBusiness().equals(
					"updateMobileLoginStatus")) {
				return startupMobileLogin(mapping, form, request, response);
			} else if (URI.getAgent().getMobileBusiness()
					.equals("updateCanpay")) {
				return viewPayBalance(mapping, form, request, response);
			}
			return mapping.findForward("mobilebindingsuccess");
		}
	}

	public ActionForward unbindingMobile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI.getAgent().getValidCertStatus() == 0) {
			return mapping.findForward("accessDeniedCert");
		}
		request.setAttribute("mobilePhone", agent.getMobilePhone());
		String dd = DateUtil.getDateString("yyyy年MM月dd日");
		NoUtil noUtil = new NoUtil();
		String code = noUtil.getRandom(6);
		System.out.println(code);
		URI.getAgent().setMobileValidateCode(code);
		String mobilePhone = URI.getAgent().getMobilePhone();
		SMUtil.send(mobilePhone, dd + ",您申请了解除手机绑定,您所有的手机服务都将被停止。验证码是:[" + code
				+ "]");
		return mapping.findForward("mobileunbinding");
	}

	public ActionForward unbindingMobileValidate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (agent != null
				&& !agent.getMobileValidateCode().equals(
						URI.getAgent().getMobileValidateCode())) {
			request.setAttribute("codeError", "error");
			return mapping.findForward("mobileunbinding");
		} else if (!MD5.encrypt(agent.getPayPassword()).equals(
				URI.getAgent().getPayPassword())) {
			request.setAttribute("payPasswordError", "error");
			return mapping.findForward("mobileunbinding");
		} else {

			Agent tempagent = agentBiz.getAgentById(URI.getAgent().getId());
			tempagent.setMobileBindStatus(Agent.mobile_status_0);
			tempagent.setMobileLoginStatus(Agent.mobile_status_0);
			tempagent.setMobilePayStatus(Agent.mobile_status_0);
			tempagent.setMobileQuestionStatus(Agent.mobile_status_0);
			tempagent.setMobilePasswordStatus(Agent.mobile_status_0);
			tempagent.setMobilePhone("");
			agentBiz.updateStatus(tempagent);
			URI.setAgent(tempagent);
			return mapping.findForward("mobileunbindingsuccess");
		}

	}

	public ActionForward getBackPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = null;
		String loginName = request.getParameter("loginName");
		agent = agentBiz.checkAgentByEmail(loginName);
		if (agent != null) {
			String dd = DateUtil.getDateString("yyyy年MM月dd日");
			NoUtil noUtil = new NoUtil();
			String code = noUtil.getRandom(6);
			agent.setMobileValidateCode(code);
			String mobilePhone = agent.getMobilePhone();
			request.getSession().setAttribute("tempValidateCode", code);

			SMUtil.send(mobilePhone, dd + ",您使用手机找回登录密码,验证码是:[" + code + "]");

		}
		request.setAttribute("type", "password");
		request.setAttribute("loginName", loginName);
		request.setAttribute("mobilePhone", agent.getMobilePhone());
		return mapping.findForward("selfhelpchangepasswordmobile");
	}

	public ActionForward getBackPayPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = null;
		String loginName = request.getParameter("loginName");
		agent = agentBiz.getAgentByLoginName(loginName);
		if (agent != null) {
			String dd = DateUtil.getDateString("yyyy年MM月dd日");
			NoUtil noUtil = new NoUtil();
			String code = noUtil.getRandom(6);
			agent.setMobileValidateCode(code);
			String mobilePhone = agent.getMobilePhone();
			request.getSession().setAttribute("tempValidateCode", code);
			SMUtil.send(mobilePhone, dd + ",您使用手机找回支付密码，支付密码,验证码是:[" + code
					+ "]");
		}
		request.setAttribute("type", "payPassword");
		request.setAttribute("loginName", loginName);
		request.setAttribute("mobilePhone", agent.getMobilePhone());
		return mapping.findForward("selfhelpchangepasswordmobile");
	}

	public ActionForward validateCodeGetBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		String type = request.getParameter("type");
		String loginName = request.getParameter("loginName");
		request.setAttribute("type", type);
		request.setAttribute("loginName", loginName);
		String mobilePhone = request.getParameter("mobilePhone");
		if (agent != null
				&& !agent.getMobileValidateCode().equals(
						request.getSession().getAttribute("tempValidateCode"))) {
			request.setAttribute("mobilePhone", mobilePhone);
			request.setAttribute("codeError", "error");
			return mapping.findForward("selfhelpchangepasswordmobile");
		} else {
			if (type.equals("password")) {
				return mapping.findForward("selfhelpsetpassword");
			} else if (type.equals("payPassword")) {
				return mapping.findForward("setpaypassword");
			}
			return null;
		}
	}

	public ActionForward unBindingMobileQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		try {
			Agent agent = agentBiz.getAgentById(URI.getAgent().getId());
			List<QuestionsAndAnswers> listquestionanswer = agentBiz
					.RandQuestions(agent);
			request.getSession().setAttribute("listquestionanswer",
					listquestionanswer);
			request.setAttribute("agent", agent);

		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("mobileunbindingquestion");
	}

	public ActionForward unBindingMobileValidateQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		List<QuestionsAndAnswers> listquestionandanswer = (List<QuestionsAndAnswers>) request
				.getSession().getAttribute("listquestionanswer");
		List errorlist = new ArrayList();
		String[] answers = request.getParameterValues("answers");
		boolean isError = false;
		for (int i = 0; i < listquestionandanswer.size(); i++) {
			listquestionandanswer.get(i).setAnswerError(null);
			if (!listquestionandanswer.get(i).answer.equals(answers[i])) {
				// errorlist.add(listquestionandanswer.get(i).question+":回答不正确!");
				listquestionandanswer.get(i).setAnswerError("回答有误!");
				isError = true;
			}
		}
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (isError) {
			try {

				Agent agent = agentBiz.getAgentById(URI.getAgent().getId());
				request.getSession().setAttribute("listquestionanswer",
						listquestionandanswer);
				request.setAttribute("agent", agent);
			} catch (AppException e) {
				e.printStackTrace();
			}
			return mapping.findForward("mobileunbindingquestion");
		} else {

			Agent agent = agentBiz.getAgentById(URI.getAgent().getId());
			agent.setMobileBindStatus(Agent.mobile_status_0);
			agent.setMobileLoginStatus(Agent.mobile_status_0);
			agent.setMobilePayStatus(Agent.mobile_status_0);
			agent.setMobileQuestionStatus(Agent.mobile_status_0);
			agent.setMobilePasswordStatus(Agent.mobile_status_0);
			agentBiz.updateStatus(agent);
			URI.setAgent(agent);
			return mapping.findForward("mobileunbindingsuccess");
		}
	}

	public ActionForward startupMobileQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI.getAgent().getMobileBindStatus() == 0) {
			URI.getAgent().setMobileBusiness("updateMobileQuestionStatus");
			return viewMobileBinding(mapping, form, request, response);
		} else {
			String res = agentBiz.updateMobileQuestionStatus(URI.getAgent(),
					request);
			if (res.equals("mobileclosequestionsuccess")) {
				return mapping.findForward("mobileclosequestionsuccess");
			} else if (res.equals("mobilequestionsuccess")) {
				return mapping.findForward("mobilequestionsuccess");
			} else {
				return mapping.findForward("mobilebusinessfails");
			}
		}
	}

	public ActionForward startupMobilePay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI.getAgent().getMobileBindStatus() == 0) {
			URI.getAgent().setMobileBusiness("updateMobileQuestionStatus");
			return viewMobileBinding(mapping, form, request, response);
		} else {
			String res = agentBiz
					.updateMobilePayStatus(URI.getAgent(), request);
			if (res.equals("mobileclosepaysuccess")) {
				return mapping.findForward("mobileclosepaysuccess");
			} else if (res.equals("mobilepaysuccess")) {
				return mapping.findForward("mobilepaysuccess");
			} else {
				return mapping.findForward("mobilebusinessfails");
			}
		}
	}

	public ActionForward startupMobilePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI.getAgent().getMobileBindStatus() == 0) {
			URI.getAgent().setMobileBusiness("updateMobileLoginStatus");
			return viewMobileBinding(mapping, form, request, response);
		} else {
			return mapping.findForward("mobilepasswordprotocol");
		}
	}

	public ActionForward checkPayPasswordMobileLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		Agent tempagent = agentBiz.getAgentById(agent.getId());
		if (!MD5.encrypt(agent.getPayPassword()).equals(
				tempagent.getPayPassword())) {
			return mapping.findForward("mobileloginvalidatepassword");
		} else {
			String res = agentBiz.updateMobileLoginStatus(tempagent, request);
			if (res.equals("startupsuccess")) {
				request.setAttribute("mobilePhone", tempagent.getMobilePhone());
				request.setAttribute("loginName", tempagent.getLoginName());
				return mapping.findForward("mobileloginsuccess");
			} else if (res.equals("closesuccess")) {
				return mapping.findForward("mobilecloseloginsuccess");
			} else {
				return mapping.findForward("mobilebusinessfails");
			}
		}
	}

	public ActionForward startupMobileLogin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI.getAgent().getMobileBindStatus() == 0) {
			URI.getAgent().setMobileBusiness("updateMobileLoginStatus");
			return viewMobileBinding(mapping, form, request, response);
		} else {
			return mapping.findForward("mobileloginvalidatepassword");
		}
	}

	public ActionForward closeMobilePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String mobilePhone = URI.getAgent().getMobilePhone();
		String dd = DateUtil.getDateString("yyyy年MM月dd日");
		NoUtil noUtil = new NoUtil();
		String code = noUtil.getRandom(6);

		URI.getAgent().setMobileValidateCode(code);
		request.setAttribute("mobilePhone", URI.getAgent().getMobilePhone());
		SMUtil.send(mobilePhone, dd + ",您申请取消钱门手机动态口令业务,验证码是:[" + code + "]");
		return mapping.findForward("mobilepasswordcancel");
	}

	public ActionForward closeMobilePasswordValidateCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		if (agent != null && agentBiz.closeMobilePassword(agent, request)) {
			return mapping.findForward("mobileclosepasswordsuccess");
		} else {
			request.setAttribute("codeError", "error");
			return mapping.findForward("mobilepasswordcancel");
		}
	}

	public ActionForward jumpTermMobileSettings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("mobilepasswordsettings");
	}

	public ActionForward settingMobilePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Agent agent = (Agent) form;
			String res = agentBiz.settingMobilePassword(agent, request);
			if (res.equals("MaxItermAmountError")) {
				request.setAttribute("MaxItermAmountError", "error");
				return mapping.findForward("mobilepasswordsettings");
			} else if (res.equals("MaxDayAmountError")) {
				request.setAttribute("MaxDayAmountError", "error");
				return mapping.findForward("mobilepasswordsettings");
			} else if (res.equals("PayPasswordError")) {
				request.setAttribute("PayPasswordError", "error");
				return mapping.findForward("mobilepasswordsettings");
			} else if (res.equals("PasswordStatusEorror")) {
				return mapping.findForward("mobilebusinessfails");
			} else {
				return mapping.findForward("mobilepasswordsuccess");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return mapping.findForward("mobilepasswordsettings");
	}

	public ActionForward closePayBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		try {
			Agent tempagent = agentBiz.getAgentById(agent.getId());
			tempagent.setCanPay(Agent.status_0);
			agentBiz.updateAgent(tempagent);
			request.setAttribute("agent", tempagent);
		} catch (AppException e) {
			e.printStackTrace();
		}
		request.setAttribute("mobilePhone", agent.getMobilePhone());
		return mapping.findForward("viewstartuppaybalance");
	}

	public ActionForward startupPayBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		String mobilePhone = agent.getMobilePhone();
		String dd = DateUtil.getDateString("yyyy年MM月dd日");
		NoUtil noUtil = new NoUtil();
		String code = noUtil.getRandom(6);
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		URI.getAgent().setMobileValidateCode(code);
		SMUtil.send(mobilePhone, dd + ",您开启允许使用余额支付，验证码是:[" + code + "]");
		request.setAttribute("data", dd);
		request.setAttribute("mobilePhone", mobilePhone);
		return mapping.findForward("viewpaybalancevalidate");
	}

	public ActionForward startupPayBalanceValidate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Agent agent = (Agent) form;
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		if (agent != null
				&& !agent.getMobileValidateCode().equals(
						URI.getAgent().getMobileValidateCode())) {
			request.setAttribute("codeError", "error");
			request.setAttribute("mobilePhone", agent.getMobilePhone());
			return mapping.findForward("viewpaybalancevalidate");
		} else {
			try {
				Agent tempagent = agentBiz.getAgentById(URI.getAgent().getId());
				tempagent.setCanPay(Agent.status_1);
				agentBiz.updateAgent(tempagent);
				request.setAttribute("agent", tempagent);
			} catch (AppException e) {
				e.printStackTrace();
			}
			request.setAttribute("agent", agent);
			return mapping.findForward("viewclosepaybalance");
		}
	}

	public ActionForward viewPayBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (URI.getAgent().getMobileBindStatus() == Agent.mobile_status_0) {
			URI.getAgent().setMobileBusiness("updateCanpay");
		}
		Agent agent;
		try {
			agent = agentBiz.getAgentById(URI.getAgent().getId());
			request.setAttribute("agent", agent);
			if (agent.getCanPay() == Agent.mobile_status_1) {
				return mapping.findForward("viewclosepaybalance");
			} else {
				return mapping.findForward("viewstartuppaybalance");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward removeCertification(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = URI.getAgent();
		try {
			agentBiz.removeCertification(agent.getId());
			return mapping.findForward("removecertificationsuccess");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 显示头像
	public ActionForward viewPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = URI.getAgent();
		agent = agentBiz.getAgentById(agent.getId());
		request.setAttribute("agent", agent);
		return mapping.findForward("editphoto");
	}

	public ActionForward updatePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Agent agent = (Agent) form;
		try {
			if (agent.getId() > 0) {
				String photo = agent.getPhoto();
				String listAttachName = agent.getListAttachName();
				agent = agentBiz.getAgentById(agent.getId());
				if (agent != null) {
					agent.setPhoto(photo);
				}
				request.getSession().removeAttribute(listAttachName);
			}
			return viewMyAgent(mapping, form, request, response);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setTasktimestampBiz(TaskTimestampBiz tasktimestampBiz) {
		this.tasktimestampBiz = tasktimestampBiz;
	}

	public void setAgentCoterieBiz(AgentCoterieBiz agentCoterieBiz) {
		this.agentCoterieBiz = agentCoterieBiz;
	}

	public void setAgentRelationshipBiz(
			AgentRelationshipBiz agentRelationshipBiz) {
		this.agentRelationshipBiz = agentRelationshipBiz;
	}

	public void setProvinceBiz(ProvinceBiz provinceBiz) {
		this.provinceBiz = provinceBiz;
	}

	public void setCityBiz(CityBiz cityBiz) {
		this.cityBiz = cityBiz;
	}

	public void setBankBiz(BankBiz bankBiz) {
		this.bankBiz = bankBiz;
	}

	public void setCoterieBiz(CoterieBiz coterieBiz) {
		this.coterieBiz = coterieBiz;
	}

	public void setDrawLogBiz(DrawLogBiz drawLogBiz) {
		this.drawLogBiz = drawLogBiz;
	}

	public void setDrawBiz(DrawBiz drawBiz) {
		this.drawBiz = drawBiz;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}
	
	
	public static void main(String[] args)
	{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("$RealyName$", "张三");
		String url = Constant.getLocalHost();
		String md5 = MD5.encrypt("276628@qq.com");
		NoUtil noUtil = new NoUtil();
		String no = noUtil.getRandom(13);
		String mdno = MD5.encrypt(no);
		params.put("$url$", url
				+ "/agent/agent.do?thisAction=changePasswordQuestion&md5="
				+ md5 + "&email=276628@qq.com" 
				+ "&type=password&no=" + no + "&mdno=" + mdno);
		String result = MailUtil.sslSend("修改密码", "0002", "276628@qq.com",
				params,Certification.getProtocol()); // 发邮件

	}
}
