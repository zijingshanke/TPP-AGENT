package com.fordays.fdpay.agent.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentContact;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.AgentRoleRight;
import com.fordays.fdpay.agent.AgentUser;
import com.fordays.fdpay.agent.AgentUserRoleRight;
import com.fordays.fdpay.agent.CertInfo;
import com.fordays.fdpay.agent.QuestionsAndAnswers;
import com.fordays.fdpay.agent.dao.AccountDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.AgentUserDAO;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.right.dao.RightDAO;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.system.LoginLog;
import com.fordays.fdpay.system.SysLog;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.dao.LoginLogDAO;
import com.fordays.fdpay.system.dao.TaskTimestampDAO;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.Draw;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.DrawDAO;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.utility.RegexUtil;

public class AgentBizImpl implements AgentBiz {
	private TransactionTemplate transactionTemplate;
	private AgentDAO agentDAO;
	private LoginLogDAO loginlogDAO;
	private TaskTimestampDAO tasktimestampDAO;
	private AccountDAO accountDAO;
	private DrawDAO drawDAO;
	private AgentUserDAO agentUserDAO;
	private RightDAO rightDAO;
	private TransactionDAO transactionDAO;

	public String preprocessLogin(Agent ag, HttpServletRequest request)
			throws AppException {
		String rand = null;
		if (request.getSession().getAttribute("rand") != null)
			rand = request.getSession().getAttribute("rand").toString();
		if (rand != null && ag.getRand() != null && !rand.equals(ag.getRand())) {
			return "errorRand";
		}
		if (ag.getLoginName().contains("@")) {
			return login(ag, request);
		} else {
			return userLogin(ag, request);
		}
	}

	// 登陆验证 手机号码类型账户

	public String userLogin(Agent ag, HttpServletRequest request)
			throws AppException {
		AgentUser agentUser = null;
		agentUser = agentUserDAO.getByQueryString(ag.getLoginName());
		if (agentUser != null) {

			// if
			// (agentUser.getPassword().equals(MD5.encrypt(ag.getPassword()))) {
			if (agentUser.getPassword().equals(ag.getPassword())) {
				LoginLog loginlog = new LoginLog();
				loginlog.setIp(request.getRemoteAddr());
				loginlog.setLocate(new Long(1));
				loginlog.setStatus(new Long(3));// 操作员登录成功
				loginlog
						.setLoginDate(new Timestamp(System.currentTimeMillis()));
				loginlog.setLoginName(agentUser.getAgent().getLoginName() + ":"
						+ agentUser.getNo());
				loginlogDAO.save(loginlog);
				UserRightInfo uri = new UserRightInfo();
				uri.setAgent(agentUser.getAgent());
				if (agentUser.getAgent().getCertInfo() != null) {
					uri.getAgent().setSerialNo(
							agentUser.getAgent().getCertInfo().getSerialNo());
				} else {
					uri.getAgent().setSerialNo(null);
				}
				uri.setAgentUser(agentUser);

				request.getSession().setAttribute("URI", uri);
				System.out.println("=======操作员登录成功!=======");
				List list = rightDAO.listRoleRightsByAgentUserId(agentUser
						.getId());
				for (int i = 0; i < list.size(); i++) {
					AgentUserRoleRight arr = (AgentUserRoleRight) list.get(i);
					uri.addRight(arr.getAgentRoleRight().getRightCode(), arr
							.getAgentRoleRight().getRightAction());
				}

				return "loginSuccess";
			} else {
				LoginLog loginlog = new LoginLog();
				loginlog.setIp(request.getRemoteAddr());
				loginlog.setLocate(new Long(1));
				loginlog.setStatus(new Long(4));// 操作员登录失败
				loginlog
						.setLoginDate(new Timestamp(System.currentTimeMillis()));
				loginlog.setLoginName(agentUser.getAgent().getLoginName() + ":"
						+ agentUser.getNo());
				loginlogDAO.save(loginlog);
				System.out.println("=======操作员登录失败!=======");
				return "LoginFails";// 
			}
		} else {
			return "NonexistentAgent";
		}

	}

	// 登陆验证 邮箱类型账户
	public String login(Agent ag, HttpServletRequest request)
			throws AppException {
		Agent agent = agentDAO.checkAgent(ag);

		if (agent != null) {
			TaskTimestamp taskTimestamp = tasktimestampDAO.getTaskTimestamp(
					agent, new Long(4), new Long(0), TaskTimestamp.type_3);

			if (taskTimestamp != null && taskTimestamp.getCount() >= 3) {
				return "ConsecutiveLoginError";
			}
			if (agent.getStatus() != null && agent.getStatus() != 0) {
				System.out.println("===:" + agent.getPassword());
				System.out.println("===>>>:" + ag.getPassword());

				// if (agent.getPassword().equals(MD5.encrypt(ag.getPassword()))
				if (agent.getPassword().equals(ag.getPassword())
						|| !"".equals(ag.getForwardPage())) {
					LoginLog loginlog = new LoginLog();
					loginlog.setIp(request.getRemoteAddr());
					loginlog.setLocate(new Long(1));
					loginlog.setStatus(new Long(1));
					loginlog.setLoginDate(new Timestamp(System
							.currentTimeMillis()));
					loginlog.setLoginName(agent.getLoginName());
					loginlogDAO.save(loginlog);
					UserRightInfo uri = new UserRightInfo();
					agent.setCurrentLoginDate(agent.getLastLoginDate());
					agent.setLastLoginDate(new Timestamp(System
							.currentTimeMillis()));
					agentDAO.update(agent);
					uri.setAgent(agent);
					if (taskTimestamp != null) {
						taskTimestamp.setStatus(new Long(1));// �
						tasktimestampDAO.updateStatus(taskTimestamp);
					}

					List list = rightDAO.listRoleRights();
					for (int i = 0; i < list.size(); i++) {
						AgentRoleRight arr = (AgentRoleRight) list.get(i);
						uri.addRight(arr.getRightCode(), arr.getRightAction());
					}
					// 同步可用金额
					synallowBalance(agent);
					// agentDAO.getCertById(agent.getc)
					if (agent.getCertInfo() != null) {
						uri.getAgent().setSerialNo(
								agent.getCertInfo().getSerialNo());
						System.out.println("证书序列号:"
								+ uri.getAgent().getSerialNo());
					} else {
						System.out
								.println("------------------没有证书-------------");
						uri.getAgent().setSerialNo(null);
					}

					request.getSession().setAttribute("URI", uri);
					return "loginSuccess";
				} else {
					SysLog syslog = new SysLog();
					LoginLog loginlog = new LoginLog();
					loginlog.setIp(request.getRemoteAddr());
					loginlog.setLocate(new Long(1));
					loginlog.setStatus(new Long(0));
					loginlog.setLoginDate(new Timestamp(System
							.currentTimeMillis()));
					loginlog.setLoginName(agent.getLoginName());
					loginlogDAO.save(loginlog);
					if (taskTimestamp == null) {
						taskTimestamp = new TaskTimestamp();
						taskTimestamp.setTaskDate(new Timestamp(System
								.currentTimeMillis()));
						taskTimestamp.setTaskHour(new Long(3));
						taskTimestamp.setAgent(agent);
						taskTimestamp.setTaskType(TaskTimestamp.type_4);
						taskTimestamp.setCount(new Long(1));
						taskTimestamp.setStatus(new Long(0));// ����
						tasktimestampDAO.save(taskTimestamp);
						System.out.println("--���������!--");
					} else {
						if (taskTimestamp.getCount() >= 3) {
							// request.setAttribute("error2", "error2");
							// request.setAttribute("loginName",
							// ag.getLoginName());
							// return mapping.findForward("loginFail");
							return "ConsecutiveLoginError";// l���¼ʧ��!
						} else {
							taskTimestamp
								.setCount(taskTimestamp.getCount() + 1);
							tasktimestampDAO.updateStatus(taskTimestamp);
						}
					}
					// request.setAttribute("useRand", "yes");
					// request.setAttribute("errorPassword", "error");
					return "LoginFails";// ��¼ʧ��
				}

			} else {
				// request.setAttribute("error", "error");
				// request.setAttribute("loginName", ag.getLoginName());
				return "NotActivation";// û�м����û�
			}
		} else {
			// message = "�û������!";
			// request.setAttribute("loginName", ag.getLoginName());
			// request.setAttribute("message", message);
			return "NonexistentAgent";// �û�������
		}

	}

	public String loginMD5(Agent ag, HttpServletRequest request)
			throws AppException {
		Agent agent = agentDAO.checkAgent(ag);

		if (agent != null) {
			TaskTimestamp taskTimestamp = tasktimestampDAO.getTaskTimestamp(
					agent, new Long(4), new Long(0), TaskTimestamp.type_3);

			if (taskTimestamp != null && taskTimestamp.getCount() >= 3) {
				return "ConsecutiveLoginError";
			}
			if (agent.getStatus() != null && agent.getStatus() != 0) {
				if (agent.getPayPassword().equals(ag.getPayPassword())
						|| !"".equals(ag.getForwardPage())) {
					LoginLog loginlog = new LoginLog();
					loginlog.setIp(request.getRemoteAddr());
					loginlog.setLocate(new Long(1));
					loginlog.setStatus(new Long(1));
					loginlog.setLoginDate(new Timestamp(System
							.currentTimeMillis()));
					loginlog.setLoginName(agent.getLoginName());
					loginlogDAO.save(loginlog);
					UserRightInfo uri = new UserRightInfo();
					agent.setCurrentLoginDate(agent.getLastLoginDate());
					agent.setLastLoginDate(new Timestamp(System
							.currentTimeMillis()));
					agentDAO.update(agent);
					uri.setAgent(agent);
					if (taskTimestamp != null) {
						taskTimestamp.setStatus(new Long(1));// �ر�
						tasktimestampDAO.updateStatus(taskTimestamp);
					}

					List list = rightDAO.listRoleRights();
					for (int i = 0; i < list.size(); i++) {
						AgentRoleRight arr = (AgentRoleRight) list.get(i);
						uri.addRight(arr.getRightCode(), arr.getRightAction());
					}
					// 同步可用金额
					synallowBalance(agent);

					if (agent.getCertInfo() != null) {
						uri.getAgent().setSerialNo(
								agent.getCertInfo().getSerialNo());
					} else {
						uri.getAgent().setSerialNo(null);
					}

					request.getSession().setAttribute("URI", uri);
					return "loginSuccess";
				} else {
					SysLog syslog = new SysLog();
					LoginLog loginlog = new LoginLog();
					loginlog.setIp(request.getRemoteAddr());
					loginlog.setLocate(new Long(1));
					loginlog.setStatus(new Long(0));
					loginlog.setLoginDate(new Timestamp(System
							.currentTimeMillis()));
					loginlog.setLoginName(agent.getLoginName());
					loginlogDAO.save(loginlog);
					if (taskTimestamp == null) {
						taskTimestamp = new TaskTimestamp();
						taskTimestamp.setTaskDate(new Timestamp(System
								.currentTimeMillis()));
						taskTimestamp.setTaskHour(new Long(3));
						taskTimestamp.setAgent(agent);
						taskTimestamp.setTaskType(TaskTimestamp.type_4);
						taskTimestamp.setCount(new Long(1));
						taskTimestamp.setStatus(new Long(0));// ����
						tasktimestampDAO.save(taskTimestamp);
						System.out.println("--���������!--");
					} else {
						if (taskTimestamp.getCount() >= 3) {
							// request.setAttribute("error2", "error2");
							// request.setAttribute("loginName",
							// ag.getLoginName());
							// return mapping.findForward("loginFail");
							return "ConsecutiveLoginError";// l���¼ʧ��!
						} else {
							taskTimestamp
									.setCount(taskTimestamp.getCount() + 1);
							tasktimestampDAO.updateStatus(taskTimestamp);
						}
					}
					// request.setAttribute("useRand", "yes");
					// request.setAttribute("errorPassword", "error");
					return "LoginFails";// ��¼ʧ��
				}

			} else {
				// request.setAttribute("error", "error");
				// request.setAttribute("loginName", ag.getLoginName());
				return "NotActivation";// û�м����û�
			}
		} else {
			// message = "�û������!";
			// request.setAttribute("loginName", ag.getLoginName());
			// request.setAttribute("message", message);
			return "NonexistentAgent";// �û�������
		}

	}

	// 钱门账户注册
	public Agent register(Agent ag) throws AppException {

		ag.setStatus(Agent.status_0);
		ag.setRegisterDate(new Timestamp(System.currentTimeMillis()));
		ag.setMobileBindStatus(Agent.mobile_status_0);
		ag.setMobileLoginStatus(Agent.mobile_status_0);
		ag.setMobilePasswordStatus(Agent.mobile_status_0);
		ag.setMobilePayStatus(Agent.mobile_status_0);
		ag.setMobileQuestionStatus(Agent.mobile_status_0);
		ag.setCanPay(Agent.mobile_status_1);
		ag.setCheckAmount(new BigDecimal(0.00));
		ag.setAllowBalance(new BigDecimal(0.00));
		ag.setNotallowBalance(new BigDecimal(0.00));
		ag.setConsumeBalance(new BigDecimal(0.00));
		ag.setMaxDayAmount(new BigDecimal(0.00));
		ag.setMaxItermAmount(new BigDecimal(0.00));
		ag.setMaxDrawAmount(new BigDecimal(150000.00));
		ag.setMaxItemDrawAmount(new BigDecimal(50000.00));
		ag.setPassword(MD5.encrypt(ag.getPassword()));
		ag.setPayPassword(MD5.encrypt(ag.getPayPassword()));
		ag.setEmail(ag.getLoginName());
		ag.setTempEmail(ag.getLoginName());
		ag.setAccountStatus(new Long(1));
		ag.setCreditBalance(new BigDecimal(0.00));
		ag.setPhoto("defaultPhoto.gif");
		ag.setCertStatus(new Long(0));
		// ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		// ag.setTransactions(null);
		ag.setAccounts(null);
		agentDAO.register(ag);
		return ag;

	}

	public Agent activateAgent(Agent ag) throws AppException {

		Agent agent = agentDAO.checkEmail(ag.getLoginName());
		if (agent != null) {
			agent.setCertNum(ag.getCertNum());
			agent.setQuestion(ag.getQuestion());
			agent.setAnswer(ag.getAnswer());
			agent.setName(ag.getName());
			agent.setMobile(ag.getMobile());
			agent.setTelephone(ag.getTelephone());
			agent.setStatus(Agent.status_1);
			agent.setRegisterType(ag.getRegisterType());
			agent.setPassword(MD5.encrypt(ag.getPassword()));
			agent.setPayPassword(MD5.encrypt(ag.getPayPassword()));
			agent.setCompanyName(ag.getCompanyName());
			System.out.println(agent.getCertNum());
			System.out.println(agent.getQuestion());
			System.out.println(agent.getAnswer());
			System.out.println(agent.getName());
			System.out.println(agent.getMobile());
			System.out.println(agent.getTelephone());
			System.out.println(agent.getRegisterType());
			System.out.println(agent.getPassword());
			System.out.println(agent.getPayPassword());
			System.out.println(agent.getCompanyName());
			agentDAO.update(agent);
		}
		return agent;

	}

	public void updateStatus(Agent ag) throws AppException {
		agentDAO.update(ag);
	}

	// ����Eail�Ƿ����
	public Agent checkAgentByEmail(String email) throws AppException {
		return agentDAO.checkEmail(email);
	}

	public Agent checkPayPassword(Agent agent) throws AppException {
		return agentDAO.checkPayPassword(agent);
	}

	public Agent checkCanPay(Agent agent) throws AppException {
		return agentDAO.checkCanPay(agent);
	}

	public List getContactList(AgentListForm alf) throws AppException {
		return agentDAO.getContactList(alf);
	}

	public void addOrUpdateContact(Agent ag, String contactName,
			String contactLoginName, String actionType, String acId)
			throws AppException {
		// Agent subAgent =
		// agentDAO.checkAgentByContactLoginName(contactLoginName);
		// Agent agentConatct = new Agent();
		// if(subAgent==null){
		// agentConatct.setName(contactName);
		// agentConatct.setLoginName(contactLoginName);
		// subAgent = agentDAO.register(agentConatct);
		// }
		// AgentCoterie ac = new AgentCoterie();
		// if(!acId.equals("")){
		// ac = agentDAO.getAgentCoterieById(acId);
		// //subAgent = agentDAO.getAgentById(ac.getSubAgent().getId());
		// subAgent.setName(contactName);
		// //ag = agentDAO.getAgentById(ac.getSubAgent().getId());
		// //ag.setName(contactName);
		// }
		// ac.setAgent(ag);
		// ac.setSubAgent(subAgent);
		// agentDAO.addOrUpdateContact(ac,actionType);
		AgentContact ac = new AgentContact();
		if (!actionType.equals("add")) {
			ac = agentDAO.getAgentContactById(acId);
		}
		ac.setAgent(ag);
		ac.setName(contactName);
		ac.setEmail(contactLoginName);
		ac.setActionType(actionType);
		agentDAO.addOrUpdateContact(ac);
	}

	public void update(Agent agent, BigDecimal amount) throws AppException {
		// 获取AgentBalance对象（用于获取金额）
		AgentBalance ab =agentDAO.getAgentBalance(agent.getId());
		BigDecimal dongjie = ab.getNotAllowBalance().add(amount);
		// agentDAO.synallowBalance(agent);
		BigDecimal keyong = ab.getAllowBalance().subtract(amount);
		agent.setNotallowBalance(dongjie);
		agent.setAllowBalance(keyong);
		this.agentDAO.update(agent);
	}

	public void updateAgent(Agent agent) throws AppException {
		this.agentDAO.update(agent);
	}

	public AgentContact checkContactByLoginName(Agent agent,
			String contactLoginName, String actionType, String aId)
			throws AppException {
		return agentDAO.checkContactByLoginName(agent, contactLoginName,
				actionType, aId);
	}

	public void deleteContact(String deletelist[]) throws AppException {
		agentDAO.deleteContact(deletelist);
	}

	public List getAgentAddressManage(AgentAddress agentAddress)
			throws AppException {
		return agentDAO.getAgentAddressManage(agentAddress);
	}

	public long addAgentAddress(AgentAddress agentAddress, Agent agent)
			throws AppException {
		if (agentDAO.getAgentAddressById(agent) == null) {
			agentAddress.setStatus(new Long(1));
		} else {
			agentAddress.setStatus(new Long(0));
		}
		agentAddress.setAgent(agent);
		return agentDAO.addAgentAddress(agentAddress);
	}

	public AgentAddress editAgentAddressById(long aid) throws AppException {
		return agentDAO.editAgentAddressById(aid);
	}

	public void updateAgentAddressById(AgentAddress agentAddress, long aid)
			throws AppException {
		AgentAddress agAddress = agentDAO.editAgentAddressById(aid);
		agAddress.setName(agentAddress.getName());
		agAddress.setAddress(agentAddress.getAddress());
		agAddress.setPostCode(agentAddress.getPostCode());
		agAddress.setTel(agentAddress.getTel());
		agAddress.setMtel(agentAddress.getMtel());
		agentDAO.updateAgentAddressById(agAddress);
	}

	public void deleteAgentAddress(long aid) throws AppException {
		agentDAO.deleteAgentAddress(aid);
	}

	public Agent getAgentByLoginName(String loginName) throws AppException {
		return agentDAO.getAgentByLoginName(loginName);
	}

	public List<QuestionsAndAnswers> RandQuestions(Agent agent)
			throws AppException {
		int[] intRet = new int[2];
		int intRd = 0; // ��������
		int count = 0; // ��¼��ɵ���������
		int flag = 0; // 
		while (count < 2) {
			Random rdm = new Random(System.currentTimeMillis());
			intRd = Math.abs(rdm.nextInt()) % 3 + 1;
			for (int i = 0; i < count; i++) {
				if (intRet[i] == intRd) {
					flag = 1;
					break;
				} else {
					flag = 0;
				}
			}
			if (flag == 0) {
				intRet[count] = intRd;
				count++;
			}
		}
		List<QuestionsAndAnswers> list = new ArrayList<QuestionsAndAnswers>();
		QuestionsAndAnswers qas1 = new QuestionsAndAnswers();
		qas1.setQuestion(agent.getQuestion());
		qas1.setAnswer(agent.getAnswer());
		if (agent.getQuestion1() != null && !"".equals(agent.getQuestion1())) {
			QuestionsAndAnswers qas2 = new QuestionsAndAnswers();
			qas2.setQuestion(agent.getQuestion1());
			qas2.setAnswer(agent.getAnswer1());
			QuestionsAndAnswers qas3 = new QuestionsAndAnswers();
			qas3.setQuestion(agent.getQuestion2());
			qas3.setAnswer(agent.getAnswer2());
			for (int t = 0; t < 2; t++) {
				System.out.println(t + "->" + intRet[t]);
				if (intRet[t] == 1) {
					list.add(qas1);
				} else if (intRet[t] == 2) {
					list.add(qas2);
				} else if (intRet[t] == 3) {
					list.add(qas3);
				}
			}
		} else {
			list.add(qas1);
		}
		return list;
	}

	public void setDefaultAddress(long aid) throws AppException {
		AgentAddress agAddress = agentDAO.editAgentAddressById(aid);
		agAddress.setStatus(new Long(1));
		agentDAO.setDefaultAddress(agAddress);
	}

	public Agent checkAgent(Agent agent) throws AppException {
		return agentDAO.checkAgent(agent);
	}

	public Agent checkAgent2(Agent agent) throws AppException {
		return agentDAO.checkAgent2(agent);
	}

	public String registerActivate(HttpServletRequest request)
			throws AppException {
		String md5 = request.getParameter("md5").toString();
		String email = request.getParameter("email").toString();
		String no = request.getParameter("no").toString();
		String mdno = request.getParameter("mdno").toString();
		String md5temp = MD5.encrypt(email);
		String tempno = MD5.encrypt(no);
		if (md5.equals(md5temp) && mdno.equals(mdno)) {
			Agent agent = agentDAO.getAgentByLoginName(email);
			TaskTimestamp taskTimestamp = tasktimestampDAO.getTaskTimestamp(
					agent, new Long(1));

			if (taskTimestamp.getNo().equals(no)) {
				if (agent.getStatus() == 1) {
					return "activatefail";
				} else {
					agent.setStatus(new Long(1));
					agentDAO.update(agent);
				}
				return "activatesuccess";
			} else {
				return "activedisabled";
			}
		} else {
			return "activatefail";
		}
	}

	public String checkMoney(Agent agent, Agent tempagent,
			HttpServletRequest request) throws AppException {
		if (RegexUtil.isMoney(agent.getTempBalance())) {
			System.out.println("1--:" + agent.getTempBalance());
			System.out.println("2--:" + tempagent.getCheckAmount());
			if (agent.getTempBalance() != null
					&& tempagent.getCheckAmount() != null
					&& new BigDecimal(agent.getTempBalance())
							.compareTo(tempagent.getCheckAmount()) == 0) {
				Account account = accountDAO.getAccountByBanknum(agent
						.getAccount().getCardNo());
				if (account.getCertificationStatus() != 1) {
					// return mapping.findForward("errorpage");
				} else {
					accountDAO.updateAccount(account);
					tempagent.setStatus(Agent.status_3);
					tempagent.setCheckAmount(new BigDecimal(0));
					agentDAO.update(tempagent);
					UserRightInfo URI = (UserRightInfo) request.getSession()
							.getAttribute("URI");
					URI.getAgent().setStatus(Agent.status_3);
					// return mapping.findForward("passcertift");
					return "passcertift";
				}
			} else {
				// request.setAttribute("msg", "����Ľ������֤����!");
				Account account = accountDAO.getAccountByBanknum(agent
						.getAccount().getCardNo());
				TaskTimestamp tasktimestamp = tasktimestampDAO
						.getTaskTimestamp(agent, TaskTimestamp.type_6);
				if (tasktimestamp.getCount() <= 0) {
					TaskTimestamp ttt = new TaskTimestamp();
					ttt.setAgent(agent);
					ttt.setStatus(TaskTimestamp.status_1);
					ttt.setTaskType(TaskTimestamp.type_7);
					ttt.setTaskHour(new Long(24));
					ttt.setTaskDate(new Timestamp(System.currentTimeMillis()));
					tasktimestampDAO.save(ttt);
					tempagent.setStatus(Agent.status_1);
					tempagent.setCheckAmount(new BigDecimal(0));
					agentDAO.update(tempagent);
					accountDAO.deleteById(account.getId());
					System.out.println("---删除实名认证失败的卡号---");
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("$RealyName$", tempagent.getName());
					String mailResult = MailUtil.sslSend("实名认证失败", "0005",
							tempagent.getEmail(), params, Certification
									.getProtocol()); // ���ʼ�
					return "errorcheckdisabled";
				}
				Long count = tasktimestamp.getCount();
				UserRightInfo URI = (UserRightInfo) request.getSession()
						.getAttribute("URI");

				count--;
				tasktimestamp.setCount(count);
				URI.getAgent().setCheckMoneyCount(count.intValue());
				tasktimestampDAO.updateStatus(tasktimestamp);
				return "errormoney";
			}
		} else {
			return "notmoney";
		}
		return "";

	}

	public boolean attestation(Long agentId) throws AppException {
		Account account = accountDAO.getAccountByAgentIdcertificationStatus(
				agentId, Account.certificationStatus_1);
		if (account != null) {
			return true;
		} else {
			return false;
		}
	}

	public String isCertification(Long id) throws AppException {
		Agent agent = agentDAO.getAgentById(id);
		TaskTimestamp tasktimestamp = tasktimestampDAO.getTaskTimestamp(agent,
				TaskTimestamp.type_6);
		TaskTimestamp tasktimestampType7 = tasktimestampDAO.getTaskTimestamp(
				agent, TaskTimestamp.type_7);

		if (tasktimestamp != null
				&& tasktimestamp.getStatus() == TaskTimestamp.status_1
				&& tasktimestamp.getCount() <= 0) {
			return "certificationfials";
		} else if (tasktimestamp != null
				&& tasktimestamp.getStatus() == TaskTimestamp.status_1) {
			return "toacknowledgementmoney";
		} else if (tasktimestampType7 != null) {
			return "certificationfials";
		} else {
			return "certifygate";
		}
	}

	public String updateMobileQuestionStatus(Agent agent,
			HttpServletRequest request) throws AppException {
		if (agentDAO.IsMobileBindingQuestion(agent)) {
			UserRightInfo URI = (UserRightInfo) request.getSession()
					.getAttribute("URI");
			if (agent.getMobileQuestionStatus() == Agent.mobile_status_0) {
				Agent tempagent = agentDAO.getAgentById(agent.getId());
				tempagent.setMobileQuestionStatus(Agent.mobile_status_1);
				URI.getAgent().setMobileQuestionStatus(Agent.mobile_status_1);
				agentDAO.update(tempagent);
				return "mobilequestionsuccess";
			} else {
				Agent tempagent = agentDAO.getAgentById(agent.getId());
				tempagent.setMobileQuestionStatus(Agent.mobile_status_0);
				URI.getAgent().setMobileQuestionStatus(Agent.mobile_status_0);
				agentDAO.update(tempagent);
				return "mobileclosequestionsuccess";
			}
		}
		return "";
	}

	public String updateMobilePayStatus(Agent agent, HttpServletRequest request)
			throws AppException {
		if (agentDAO.IsMobileBindingPay(agent)) {
			UserRightInfo URI = (UserRightInfo) request.getSession()
					.getAttribute("URI");
			if (agent.getMobilePayStatus() == Agent.mobile_status_0) {
				Agent tempagent = agentDAO.getAgentById(agent.getId());
				tempagent.setMobilePayStatus(Agent.mobile_status_1);
				URI.getAgent().setMobilePayStatus(Agent.mobile_status_1);
				agentDAO.update(tempagent);
				return "mobilepaysuccess";
			} else {
				Agent tempagent = agentDAO.getAgentById(agent.getId());
				tempagent.setMobilePayStatus(Agent.mobile_status_0);
				URI.getAgent().setMobilePayStatus(Agent.mobile_status_0);
				agentDAO.update(tempagent);
				return "mobileclosepaysuccess";
			}
		}
		return "";
	}

	public String settingMobilePassword(Agent agent, HttpServletRequest request)
			throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (RegexUtil.isMobiles(agent.getMaxItermAmount().toString())) {
			return "MaxItermAmountError";
		} else if (RegexUtil.isMobiles(agent.getMaxDayAmount().toString())) {
			return "MaxDayAmountError";
		} else if (agent.getMaxItermAmount().intValue() < 200) {
			return "MaxItermAmountError";
		} else if (agent.getMaxDayAmount().intValue() < agent
				.getMaxItermAmount().intValue()) {
			return "MaxDayAmountError";
		} else if (agent.getPayPassword() == null) {
			return "PayPasswordError";
		} else if (!MD5.encrypt(agent.getPayPassword()).equals(
				URI.getAgent().getPayPassword())) {
			return "PayPasswordError";
		} else {
			Agent tempagent = agentDAO.getAgentById(URI.getAgent().getId());
			if (agentDAO.IsMobileBindingPassword(tempagent)) {
				tempagent.setMaxItermAmount(agent.getMaxItermAmount());
				tempagent.setMaxDayAmount(agent.getMaxDayAmount());
				tempagent.setMobilePasswordStatus(Agent.mobile_status_1);
				URI.getAgent().setMobilePasswordStatus(Agent.mobile_status_1);
				agentDAO.update(tempagent);
				return "";
			}
			return "PasswordStatusEorror";
		}
	}

	public boolean closeMobilePassword(Agent agent, HttpServletRequest request)
			throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (agent.getMobileValidateCode().equals(
				URI.getAgent().getMobileValidateCode())) {
			Agent tempagent = agentDAO.getAgentById(URI.getAgent().getId());
			tempagent.setMobilePasswordStatus(Agent.mobile_status_0);
			URI.getAgent().setMobilePasswordStatus(Agent.mobile_status_0);
			return true;
		} else {
			return false;
		}
	}

	public String updateMobileLoginStatus(Agent agent,
			HttpServletRequest request) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		if (agentDAO.IsMobileBindingLogin(agent)) {
			if (agent.getMobileLoginStatus() == Agent.mobile_status_0) {
				agent.setMobileLoginStatus(Agent.mobile_status_1);
				agentDAO.update(agent);
				URI.setAgent(agent);
				return "startupsuccess";
			} else {
				agent.setMobileLoginStatus(Agent.mobile_status_0);
				agentDAO.update(agent);
				URI.setAgent(agent);
				return "closesuccess";
			}
		}
		return "";
	}

	public AgentRelationship getAgentRelationship(Agent agent)
			throws AppException {
		return agentDAO.getAgentRelationship(agent);
	}

	public List getMobilePhones() throws AppException {

		return agentDAO.getMobilePhones();
	}

	public boolean isPass(Agent agent) throws AppException {
		return agentDAO.isPass(agent);
	}

	public void removeCertification(Long id) throws AppException {
		// 删除提现申请
		// 删除申请的卡号
		// 删除任务戳
		// 修改商户状态为正常(status=1)
		Agent agent = agentDAO.getAgentById(id);
		List draws = drawDAO.getDrawsByAgent(agent);
		if (draws != null) {
			for (int i = 0; i < draws.size(); i++) {
				Draw draw = (Draw) draws.get(i);
				if (draw.getStatus() != 3) {
					draw.setStatus(new Long(4));// 手动撤销
				}
				drawDAO.update(draw);
			}
		}
		List accounts = accountDAO.getAccounts(agent.getId());
		if (accounts != null) {
			for (int i = 0; i < accounts.size(); i++) {
				Account account = (Account) accounts.get(i);
				accountDAO.deleteById(account.getId());
			}
		}
		TaskTimestamp taskTimestamp = tasktimestampDAO.getTaskTimestamp(agent,
				TaskTimestamp.type_6);
		if (taskTimestamp != null) {
			tasktimestampDAO.deleteById(taskTimestamp.getId());
		}
		TaskTimestamp taskTimestamp2 = tasktimestampDAO.getTaskTimestamp(agent,
				TaskTimestamp.type_7);
		if (taskTimestamp2 != null) {
			tasktimestampDAO.deleteById(taskTimestamp2.getId());
		}
		agent.setStatus(Agent.status_1);
		agentDAO.update(agent);

	}

	public Agent getAgentByBindingMobilePay(String mobile) throws AppException {
		return agentDAO.getAgentByBindingMobilePay(mobile);
	}

	public Agent getAgentById(long id) throws AppException {
		return agentDAO.getAgentById(id);
	}

	public Agent getAgentByName(String name) throws AppException {
		return agentDAO.getAgentByName(name);
	}

	/**
	 * 获取Session中的Agent
	 */
	public Agent getAgentByRequest(HttpServletRequest request)
			throws AppException {
		Agent agent = new Agent();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		agent = uri.getAgent();
		agent = agentDAO.getAgentById(agent.getId());
		return agent;
	}

	public Agent getAgentByChargeForm(Charge charge) throws AppException {
		Agent agent = agentDAO
				.getAgentById(Long.parseLong(charge.getAgentId()));
		return agent;
	}
	
	public CertInfo getCertById(long id) throws AppException
	{
		return agentDAO.getCertById(id);
	}
	
	public Agent autoRegister(Agent agent) {
		try {
			agent.setStatus(Agent.status_1);// 激活过了
			agent.setRegisterDate(new Timestamp(System.currentTimeMillis()));
			agent.setMobileBindStatus(Agent.mobile_status_0);
			agent.setMobileLoginStatus(Agent.mobile_status_0);
			agent.setMobilePasswordStatus(Agent.mobile_status_0);
			agent.setMobilePayStatus(Agent.mobile_status_0);
			agent.setMobileQuestionStatus(Agent.mobile_status_0);
			agent.setCanPay(Agent.mobile_status_1);
			agent.setCheckAmount(new BigDecimal(0.00));
			agent.setAllowBalance(new BigDecimal(0.00));
			agent.setNotallowBalance(new BigDecimal(0.00));
			agent.setConsumeBalance(new BigDecimal(0.00));
			agent.setMaxDayAmount(new BigDecimal(0.00));
			agent.setMaxItermAmount(new BigDecimal(0.00));
			agent.setMaxDrawAmount(new BigDecimal(150000.00));
			agent.setMaxItemDrawAmount(new BigDecimal(50000.00));
			agent.setPassword(MD5.encrypt(agent.getPassword()));
			agent.setPayPassword(MD5.encrypt(agent.getPayPassword()));
			agent.setEmail(agent.getLoginName());
			agent.setAccountStatus(new Long(1));
			agent.setCreditBalance(new BigDecimal(0.00));
			agent.setPhoto("defaultPhoto.gif");
			agent.setCertStatus(new Long(0));
			agent.setName(agent.getLoginName());
			agent.setTempEmail(agent.getLoginName());
			// ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			// ag.setTransactions(null);
			agent.setAccounts(null);
			agentDAO.register(agent);
			return agent;
		} catch (Exception ex) {
			return null;
		}

	}
	
	public boolean  IsBindingMobilePhone(String mobilePhone){
		Agent agent=null;
		agent=agentDAO.getAgentByMobilePhone(mobilePhone);
			if(agent!=null)
				return true;
			else
				return false;
	}
	
	public Transaction getNotCompletedTransactionRedPacket(Agent agent)
	throws AppException {
			// 	TODO Auto-generated method stub
		return transactionDAO.getNotCompletedTransactionRedPacket(agent);
	}

	public long getNotCompletedTransactionArticle(Agent agent)
			throws AppException {
		return transactionDAO.getNotCompletedTransactionArticle(agent);
	}

	public long getNotCompletedTransactionRedPacketArticle(Agent agent)
			throws AppException {
		return transactionDAO.getNotCompletedTransactionRedPacketArticle(agent);
	}

	public BigDecimal getAgentAllowBalance(long agentId) throws AppException {
		return agentDAO.getAgentAllowBalance(agentId);
	}

	public BigDecimal getAgentCreditBalance(long agentId) throws AppException {
		return agentDAO.getAgentCreditBalance(agentId);
	}

	public BigDecimal getAgentNotAllowBalance(long agentId) throws AppException {
		return agentDAO.getAgentNotAllowBalance(agentId);
	}

	public AgentBalance getAgentBalance(long agentId) throws AppException {
		return agentDAO.getAgentBalance(agentId);
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setAgentUserDAO(AgentUserDAO agentUserDAO) {
		this.agentUserDAO = agentUserDAO;
	}

	public void setDrawDAO(DrawDAO drawDAO) {
		this.drawDAO = drawDAO;
	}

	public void setRightDAO(RightDAO rightDAO) {
		this.rightDAO = rightDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setLoginlogDAO(LoginLogDAO loginlogDAO) {
		this.loginlogDAO = loginlogDAO;
	}

	public void setTasktimestampDAO(TaskTimestampDAO tasktimestampDAO) {
		this.tasktimestampDAO = tasktimestampDAO;
	}

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void synallowBalance(Agent agent) throws AppException {
		this.agentDAO.synallowBalance(agent);
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}
}
