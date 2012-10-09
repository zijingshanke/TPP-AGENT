package com.fordays.fdpay.security.action;

import java.io.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.CertInfo;
import com.fordays.fdpay.agent.QuestionsAndAnswers;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.security.biz.CertInfoBiz;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.biz.TaskTimestampBiz;
import com.ibm.misc.BASE64Encoder;
import com.neza.base.DownLoadFile;
import com.neza.base.NoUtil;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.message.SMUtil;
import com.neza.tool.SSLURLUtil;

public class CertificateAction extends DispatchAction {
	private CertInfoBiz certInfoBiz;

	private TaskTimestampBiz tasktimestampBiz;

	public void setTaskTimestampBiz(TaskTimestampBiz tasktimestampBiz) {
		this.tasktimestampBiz = tasktimestampBiz;
	}

	/**
	 * 数字证书
	 */
	public ActionForward certificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("digital");
	}

	/**
	 * 申请数字证书
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param responsesecuritySecurityAction.java
	 * @return
	 * @throws Exception
	 */
	public ActionForward apply_DigitalCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserRightInfo uri = (UserRightInfo) session.getAttribute("URI");
		Agent agent = uri.getAgent();
		
		CertificateActionForm actionForm = (CertificateActionForm) form;
		// 如果未绑定手机,则跳转到绑定手机页面
		 if (agent.getMobileBindStatus() == Agent.mobile_status_0) {
		 return mapping.findForward("applyPremise");
		 }
		if (agent != null && agent.getId() > 0) {
			actionForm.setUserName(agent.getName());
			actionForm.setMobile(agent.getMobilePhone());
			actionForm.setEmail(agent.getEmail());
		}

		System.out.println("**getMobile**"+agent.getMobile()+"  getMobilePhone---"+agent.getMobilePhone());
		request.setAttribute("agent", agent);
		request.setAttribute("CertificateActionForm", actionForm);
		return mapping.findForward("apply");

	}

	/**
	 * 发送手机校正码
	 */
	public ActionForward checkedApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertificateActionForm securityForm = (CertificateActionForm) form;
		String mobile = securityForm.getMobile();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
//		uri.getAgent().setMobilePhone(mobile);
//		uri.getAgent().setMobileBindStatus(Agent.mobile_status_1); // 重新绑定 设置手机
		System.out.println("当前用户绑定的手机号码:" + mobile);
		String message = "钱门通知:您正在申请钱门的数字证书,验证码：";
		// this.sendMobileMessage(mobile, message, request);
		if (mobile != "" && mobile != null) {
			try {
				// send verify new message to input mobile
				String randomMsg = NoUtil.getRandom(6);
				SMUtil.send(mobile, message + randomMsg
						+ "，请正确填写验证码，完成数字证书的申请 ");
				request.getSession().setAttribute("RANDOM_MSG", randomMsg);
				System.out.println("手机验证码信息:--" + message + randomMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mapping.findForward("checkedApply");
	}

	/**
	 * 获得证书信息 验证申请
	 */
	public ActionForward confirmMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CertificateActionForm certificateActionForm = (CertificateActionForm) form;

		HttpSession session = request.getSession();
		System.out.println("是申请还是备份？" + certificateActionForm.getType());
		session.setAttribute("certificateActionForm", certificateActionForm);// certificateActionForm
		try {
			String rootcertInfo = Certification.getRootCertInfo();
			// String rootcertInfo=this.getRootCertInfo();
			request.setAttribute("certInfo", rootcertInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward("confirmMessage");
	}

	/**
	 * 获得证书内容
	 */
	private String getCertInfo(String fileRoute) throws Exception {

		InputStream in = new FileInputStream(fileRoute);
		byte[] certByte = new byte[in.available()];
		in.read(certByte); // 将文件中的内容读入到数组中
		String string = new BASE64Encoder().encode(certByte);
		in.close();
		System.out.println("xixiA:string--"
				+ string.replaceAll("[ \\r\\t\\n ]", ""));
		return string;
	}

	/**
	 * 确认用户信息
	 */
	public ActionForward applyInstallCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("---证书信息确认！！！");

		return mapping.findForward("applyInstallCert");

	}

	/**
	 * 备份数字证书提示
	 */
	public ActionForward backUpTips(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("backUpCert");
	}

	/**
	 * 生成证书
	 */
	public ActionForward createCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		CertificateActionForm certificateActionForm = (CertificateActionForm) session
				.getAttribute("certificateActionForm");
		String ip = request.getRemoteHost();

		try {
			CertInfo certInfo = certInfoBiz.createUpCert(certificateActionForm,
					agent, ip); // 备份数字证书前的工作

			if (certInfo != null || agent.getCertStatus() == 1) {
				request.setAttribute("certmsg", "证书生成!");
				if (certInfo != null)
					request.setAttribute("certPassword", certInfo.getPassword());
				request.setAttribute("certPassword", agent.getCertInfo()
						.getPassword());
				return mapping.findForward("backUpCert");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("backUpCert");
	}

	/**
	 * 生成并备份数字证书 backUpCertificate
	 */
	public ActionForward backUpCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("开始生成及备份数字证书");
		HttpSession session = request.getSession();
		CertificateActionForm securityForm = (CertificateActionForm) form;
		CertificateActionForm certificateActionForm = (CertificateActionForm) session
				.getAttribute("certificateActionForm");
		String password = securityForm.getPassword();
		// response.setContentType("application/p12-type");
		String confirmPwd = request.getParameter("confirmPwd");
		System.out.println("获得用户Email:--" + certificateActionForm.getEmail());
		System.out.println("  password--" + password + "  confirmPwd--"+ confirmPwd);
		
		try {
			if (confirmPwd.equals(password)){
				certificateActionForm.setPassword(password);
				request.setAttribute("mask", "2");

				return mapping.findForward("importCert");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
//	
	public ActionForward downloadCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String ip = request.getRemoteHost();

		String queryString="";
		CertificateActionForm certificateActionForm = (CertificateActionForm) session
		.getAttribute("certificateActionForm");
		try{
			
			CertInfo certInfo = certInfoBiz.backUpCert(
					certificateActionForm, agent, ip, request);

			String email = certInfo.getEmail();

			String strURL="https://qm.qmpay.com/security/certificate.do?";
			queryString="thisAction=getCertInputStreamOfP12&email="+email;
			HashMap<String, String> params =(HashMap<String, String>) Certification.getSignUrl(queryString, "thisAction");
			DownLoadFile download=new DownLoadFile();
			InputStream in= SSLURLUtil.getInputStreamAsPost(strURL, params, Certification.getProtocol());
			download.performTask(response,in, certInfo.getEmail()+".p12");

		}catch(Exception ex){
			ex.printStackTrace();
		}

		return null;

	}

	public ActionForward proxyUrl(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				try{
		
			String url=request.getParameter("url");
		 
			if(url.startsWith("/"))
				url="https://qm.qmpay.com"+url;		
			else
				url="https://qm.qmpay.com/"+url;		
			
			HashMap<String, String> params =new HashMap<String, String>();
			InputStream in= SSLURLUtil.getInputStreamAsPost(url, params, Certification.getProtocol());
			OutputStream out= response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(in);
		
			 ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			    int ch;
			    while ((ch = in.read()) != -1)
			    {
			    	out.write(ch);
			    }

		}catch(Exception ex){
			return null;
		}

		return null;

	}
	
	
	/**
	 * 管理数字证书
	 */
	public ActionForward manageCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("make", "1");
		return mapping.findForward("manageCertificate");
	}

	/**
	 * 查看数字证书 reviewCertificate
	 */
	public ActionForward reviewCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String type = request.getParameter("type");
		try {
			// 获得当前用户的证书
			CertInfo certInfo = certInfoBiz.reviewCertificate(agent);
			request.setAttribute("certInfo", certInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("review".equals(type))
			return mapping.findForward("viewCertificate");
		else
			return mapping.findForward("deleteCert");

	}

	/**
	 * 跳转到revokeCertificatePage注销证书页面
	 */
	public ActionForward revokeCertificatePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("～注销数字证书页面！！！～");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		if (agent.getValidCertStatus() == 0) { // 验证不通过(IE上未安装数字证书)
			return mapping.findForward("revokedWay");
		}

		return mapping.findForward("revokeCert");

	}

	/**
	 * 确认注销证书申请
	 */
	public ActionForward verifyRevokeCert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String mobile = uri.getAgent().getMobilePhone();
		System.out.println("--注销用户的手机号码:--" + mobile);
		try {
			String randomMsg = NoUtil.getRandom(6);

			String message = "钱门通知：您正在申请注销数字证书,验证码：";
			SMUtil.send(mobile, message + randomMsg + "，请输入正确的验证码完成注销");
			request.getSession().setAttribute("RANDOM_MSG", randomMsg);
			System.out.println("手机验证码信息:--" + message + randomMsg);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mapping.findForward("verifyRevokeCert");
	}

	/**
	 * revokeCertificate注销证书
	 */
	public ActionForward revokeCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("--即将注销数字证书！！！--");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		try {
			certInfoBiz.revokeCert(agent); // 执行注销
			agent.setValidCertStatus(1); // 设置证书验证成功
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("revokeSuccess");

	}

	/**
	 * importCertificatePage 导入本地数字证书页面
	 */
	public ActionForward importCertificatePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("～导入数字证书页面！！！～");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		try {
			CertInfo userCertInfo = certInfoBiz.reviewCertificate(uri
					.getAgent());
			System.out.println("证书密码：" + userCertInfo.getPassword());
			request.getSession().setAttribute("userCertInfo", userCertInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mapping.findForward("importCert");

	}

	/**
	 * importCertificate 导入本地数字证书---
	 */

	public ActionForward importCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("--导入数字证书！！！--");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		CertificateActionForm certificateForm = (CertificateActionForm) form;

		// request.setAttribute("mark", 1);
		// return mapping.findForward("installSuess");

		String way = certificateForm.getWay();
		System.out.println("提交结果:==" + way);
		if (!"".equals(way)) {
			if ("success".equals(way)) {
				uri.getAgent().setValidCertStatus(1);
				return mapping.findForward("installSuess");
			} else if ("fail".equals(way)) {
				return mapping.findForward("installCertificate");
			}
		}
		return mapping.findForward("installCertificate");

	}

	private String dealwithString(String fileRoute) {
		File file = new File(fileRoute);
		System.out.println("证书名称：" + file.getName());
		String fileName = file.getName();
		int f1 = fileName.lastIndexOf('.') + 1;
		String fileType = fileName.substring(f1, fileName.length())
				.toLowerCase();
		System.out.println("证书类型：" + fileType);
		String newString = "";
		if (fileType.equals("p12")) {
			if (fileRoute.indexOf(" ") != -1) {// 如果路径字符串中有空格，则做处理
				char a = '"';
				int f2 = fileRoute.indexOf(":", 0);
				System.out.println(":的位置为--" + f2);
				int f3 = fileRoute.lastIndexOf("\\");
				String str1 = fileRoute.substring(0, f2 + 1);
				String trimStr = fileRoute.substring(f2 + 1, f3);
				System.out.println("截取的带空格的文件夹字符：--" + trimStr);
				String str2 = a + trimStr + a;
				System.out.println("加引号的带空格的文件夹字符：--" + str2);
				String str3 = fileRoute.substring(f3, fileRoute.length());
				System.out.println("拼接之后的字符串：--" + str1 + str2 + str3);
				fileRoute = str1 + str2 + str3;
			}
			newString = fileRoute.replaceAll("\\\\", "\\\\\\\\");
			System.out.println("替换后的字符路径：--" + newString);
			String cmd = "cmd.exe /c start ";
			String cmdStr = cmd + newString;
			System.out.println("拼接后的字符路径：--" + cmdStr);

		}
		return newString;
	}

	/**
	 * deleteCertificate 删除本地数字证书
	 */
	public ActionForward deleteCertificate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("～即将删除数字证书！！！～");
		return null;
	}

	/**
	 * --------------------------------------------------------------------------
	 * --------------------------
	 */

	/**
	 * 检验手机验证码
	 */
	public ActionForward verifyValidationcode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String randomMsg = null;
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?> ");

		if (session.getAttribute("RANDOM_MSG") != null) {
			randomMsg = (String) session.getAttribute("RANDOM_MSG");
		}
		String verifyPwd = request.getParameter("verifyPwd");
		sb.append("<root>");
		sb.append("<is_success>");
		if (verifyPwd != null && verifyPwd.equals(randomMsg)) {
			sb.append("T");
		} else {
			sb.append("F");
		}
		sb.append("</is_success>");
		sb.append("</root>");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
		out.close();
		out = null;
		System.out.println(">>>>>" + sb.toString());
		return null;
	}

	/**
	 * 发送手机验证码
	 */
	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
//		String msg1 = new String(request.getParameter("msg1").getBytes(
//				"ISO-8859-1"), "UTF-8"); // js 单次编码 取值的方式
		
		String msg1 = java.net.URLDecoder.decode(request.getParameter("msg1"),
		"UTF-8"); // js 用了2次encodeURI 取值的方式

		String msg2 = java.net.URLDecoder.decode(request.getParameter("msg2"),
				"UTF-8"); // js 用了2次encodeURI 取值的方式

		System.out.println("信息前:--" + msg1 + "信息后:--" + msg2);
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?> ");

		String mobile = null;
		String isSend = "F";
		if (request.getParameter("mobile") != null) {
			mobile = request.getParameter("mobile");
			try {
				// send verify new password to input mobile
				String randomMsg = NoUtil.getRandom(6);
				SMUtil.send(mobile, msg1 + randomMsg + msg2);
				session.setAttribute("RANDOM_MSG", randomMsg);
				isSend = "T";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("<root>");
		sb.append("<is_send>");
		sb.append(isSend);
		sb.append("</is_send>");
		sb.append("</root>");
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
		out.close();
		out = null;
		System.out.println(">>>>>send=" + sb.toString());
		return null;
	}

	// If the first argument is null, return the second argument.
	// Otherwise, convert first argument to a String and
	// return that String.
	public CertInfoBiz getCertInfoBiz() {
		return certInfoBiz;
	}

	public void setCertInfoBiz(CertInfoBiz certInfoBiz) {
		this.certInfoBiz = certInfoBiz;
	}

	/**
	 * --------------------------------------------------------------------------
	 * -----------------------
	 */
	public AgentBiz agentBiz;

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public ActionForward securityCenter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		long id = agent.getId();
		agent = (Agent) agentBiz.getAgentById(id);

		request.setAttribute("viewAgentInfo", agent);
		return mapping.findForward("index");
	}

	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent) form;
		String password = agent.getPassword();
		String password1 = agent.getRepassword();
		String password2 = agent.getRepassword2();
		System.out.println(agent.getPassword());
		List<String> errorlist = new ArrayList<String>();
		if (password.trim() == null || "".equals(password.trim())) {
			errorlist.add("密码必填");
		} else {
			if (!password1.equals(password2)) {
				errorlist.add("确认密码与新密码必须一致");
			} else {
				Agent tempagent = agentBiz.getAgentById(agent.getId());
				if (tempagent.getPayPassword().equals(MD5.encrypt(password1))) {
					errorlist.add("登录密码与新支付密码一致,请重新设置");
				} else if (tempagent.getPassword()
						.equals(MD5.encrypt(password))) {
					tempagent.setPassword(MD5.encrypt(password1));
					agentBiz.updateAgent(tempagent);

					return mapping.findForward("infochangepasswordsuccess");
				} else {
					errorlist.add("密码不正确");
				}

			}
		}
		if (password1 == null || "".equals(password1.trim())) {
			request.setAttribute("msgrePassword", "新密码必填");
			errorlist.add("新密码必填");
		}
		if (password2 == null || "".equals(password2.trim())) {
			errorlist.add("确认密码必填");
		}
		if (errorlist.size() > 0) {
			request.setAttribute("error", "yes");
			request.setAttribute("errorlist", errorlist);
		}
		return mapping.findForward("changepassword");
	}

	public ActionForward changePayPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Agent agent = (Agent) form;
		String password = agent.getPayPassword();
		String password1 = agent.getRepassword();
		String password2 = agent.getRepassword2();
		List<String> errorlist = new ArrayList<String>();
		if (password == null || "".equals(password.trim())) {
			errorlist.add("支付密码必填");
		} else {
			if (!password1.equals(password2)) {
				errorlist.add("支付密码与新支付密码必须一致");
			} else if (password.equals(password1)) {
				errorlist.add("请重新设置您的支付密码！不能与原支付密码相同！");
			} else {
				Agent tempagent = agentBiz.getAgentById(agent.getId());
				if (tempagent.getPassword().equals(MD5.encrypt(password1))) {
					errorlist.add("登录密码与新支付密码一致,请重新设置");
				} else if (tempagent.getPayPassword().equals(
						MD5.encrypt(password))) {
					tempagent.setPayPassword(MD5.encrypt(password1));
					agentBiz.updateAgent(tempagent);

					return mapping.findForward("infochangepaypasswordsuccess");
				} else {
					errorlist.add("支付密码不正确");
				}

			}

		}
		if (password1 == null || "".equals(password1.trim())) {
			request.setAttribute("msgrePassword", "新支付密码必填");
			errorlist.add("新支付密码必填");
		}

		if (password2 == null || "".equals(password2.trim())) {
			errorlist.add("确认支付密码必填");
		}
		if (errorlist.size() > 0) {
			request.setAttribute("error", "yes");
			request.setAttribute("errorlist", errorlist);
		}
		return mapping.findForward("changepaypassword");
	}

	public ActionForward editQuestionAsk(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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
		return mapping.findForward("editquestionask");
	}

	@SuppressWarnings("unchecked")
	public ActionForward doQuestionAsk(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<QuestionsAndAnswers> listquestionandanswer = (List<QuestionsAndAnswers>) request
				.getSession().getAttribute("listquestionanswer");
		List<String> errorlist = new ArrayList<String>();
		String[] answers = request.getParameterValues("answers");
		for (int i = 0; i < listquestionandanswer.size(); i++) {
			if (!listquestionandanswer.get(i).answer.equals(answers[i])) {
				errorlist
						.add(listquestionandanswer.get(i).question + ":回答不正确!");
			}
		}
		if (errorlist.size() == 0) {
			request.setAttribute("error", "none");
			return mapping.findForward("editsecurityquestion");
		}
		request.setAttribute("errorlist", errorlist);
		request.setAttribute("size", errorlist.size());
		return mapping.findForward("editquestionask");
	}

	public ActionForward editSecurityQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String[] firstquestion = new String[3];
		String[] secondtquestion = new String[3];
		String[] answer = new String[3];
		firstquestion[0] = request.getParameter("firstquestionPart1");
		firstquestion[1] = request.getParameter("firstquestionPart2");
		firstquestion[2] = request.getParameter("firstquestionPart3");
		secondtquestion[0] = request.getParameter("secondquestionPart1");
		secondtquestion[1] = request.getParameter("secondquestionPart2");
		secondtquestion[2] = request.getParameter("secondquestionPart3");
		answer[0] = request.getParameter("answer1");
		answer[1] = request.getParameter("answer2");
		answer[2] = request.getParameter("answer3");
		String question = "我" + firstquestion[0] + "的" + secondtquestion[0]
				+ "是?";
		String question1 = "我" + firstquestion[1] + "的" + secondtquestion[1]
				+ "是?";
		String question2 = "我" + firstquestion[2] + "的" + secondtquestion[2]
				+ "是?";
		String isUpdate = request.getParameter("isUpdate");

		List<String[]> list = new ArrayList<String[]>();
		request.setAttribute("error1", null);
		request.setAttribute("error2", null);
		request.setAttribute("error3", null);
		for (int i = 0; i < 3; i++) {
			if (firstquestion[i].equals("请选择")
					|| secondtquestion[i].equals("请选择")) {
				String[] s = new String[2];
				s[0] = "问题" + (i + 1);
				s[1] = "请选择";
				list.add(s);
			}
			if ("".equals(answer[i])) {
				String[] s = new String[2];
				s[0] = "答案" + (i + 1);
				s[1] = "不能为空";
				list.add(s);
			}
			if (answer[i].equals("日期有误")) {
				String[] s = new String[2];
				s[0] = "答案" + (i + 1);
				s[1] = "格式不符。（日期的格式是:YYYYMMDD，如:19810101）";

				answer[i] = "";
				list.add(s);
			}
		}
		if (list.size() > 0) {
			request.setAttribute("error1", "errortype1");
		}

		if (!firstquestion[0].equals("请选择")) {
			if (question.equals(question1) || question.equals(question2)
					|| question1.equals(question2)) {
				request.setAttribute("error2", "errortype2");
			}
		}
		if (!"".equals(answer[0]) && !answer[0].equals("日期有误")) {
			if (answer[0].equals(answer[1]) || answer[1].equals(answer[2])
					|| answer[0].equals(answer[2])) {
				request.setAttribute("error3", "errortype3");
			}
		}
		request.setAttribute("firstquestionPart1", firstquestion[0]);
		request.setAttribute("firstquestionPart2", firstquestion[1]);
		request.setAttribute("firstquestionPart3", firstquestion[2]);
		request.setAttribute("secondquestionPart1", secondtquestion[0]);
		request.setAttribute("secondquestionPart2", secondtquestion[1]);
		request.setAttribute("secondquestionPart3", secondtquestion[2]);
		request.setAttribute("answer1", answer[0]);
		request.setAttribute("answer2", answer[1]);
		request.setAttribute("answer3", answer[2]);

		if (isUpdate == null) {
			if (request.getAttribute("error1") == null
					&& request.getAttribute("error2") == null
					&& request.getAttribute("error3") == null) {
				request.setAttribute("question", question);
				request.setAttribute("question1", question1);
				request.setAttribute("question2", question2);
				return mapping.findForward("acknowledgementchangequestion");

			}
		}
		if (isUpdate != null) {
			request.setAttribute("isUpdate", "yes");
			request.setAttribute("error", "none");
		}

		request.setAttribute("list", list);

		return mapping.findForward("editsecurityquestion");
	}

	public ActionForward changeSecurityQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo URI = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(URI.getAgent().getId());
		String question = request.getParameter("question");
		String question1 = request.getParameter("question1");
		String question2 = request.getParameter("question2");
		String answer = request.getParameter("answer1");
		String answer1 = request.getParameter("answer2");
		String answer2 = request.getParameter("answer3");
		agent.setQuestion(question);
		agent.setAnswer(answer);
		agent.setQuestion1(question1);
		agent.setAnswer1(answer1);
		agent.setQuestion2(question2);
		agent.setAnswer2(answer2);
		agentBiz.updateAgent(agent);
		return mapping.findForward("infosuccessquestion");
	}

	public ActionForward securityUseKey(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("securityusekey");
	}

	public ActionForward securityCert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("securitycert");
	}

	public ActionForward securityChecked(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		long id = agent.getId();
		agent = (Agent) agentBiz.getAgentById(id);
		request.setAttribute("viewAgentInfo", agent);
		return mapping.findForward("securitychecked");
	}

	// save保存用户证书状态到session中

	public ActionForward saveValidCertStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		System.out.println("-------come  saveValidCertStatus------");
		String validCertStatus = request.getParameter("validCertStatus");
		if (agent.getId() > 0 && agent.getCertStatus().intValue() == 1) {
			TaskTimestamp tt = tasktimestampBiz.getTaskTimestamp(agent,
					TaskTimestamp.type_9);
			if (tt != null) {
				if (com.neza.base.Constant.toInt(validCertStatus) == 1) {
					uri.getAgent().setValidCertStatus(1);
					return mapping.findForward("agentinfo");
				}
			}
		}

		if (agent.getCertStatus().intValue() == 0) {
			uri.getAgent().setValidCertStatus(1);
			return mapping.findForward("agentinfo");
		}

		uri.getAgent().setValidCertStatus(0);
		return mapping.findForward("agentinfo");
	}

	/**
	 * 接口过来修改密码 石立峰
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward interfaceChangePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		Agent agent = (Agent) form;
		String password = agent.getPassword();
		String password1 = agent.getRepassword();
		String password2 = agent.getRepassword2();
		System.out.println(agent.getPassword());
		List<String> errorlist = new ArrayList<String>();
		if (password.trim() == null || "".equals(password.trim())) {
			errorlist.add("密码必填");
		} else {
			if (!password1.equals(password2)) {
				errorlist.add("确认密码与新密码必须一致");
			} else {
				System.out.println(agent.getId());

				Agent tempagent = agentBiz.getAgentById(agent.getId());
				System.out.println(tempagent.getPayPassword());
				System.out.println(MD5.encrypt(password));
				if (!tempagent.getPayPassword().equals(MD5.encrypt(password))) {
					errorlist.add("支付密码不一致,重填");
				} else if (password1.equals("222222")) {

					errorlist.add("不能设初始密码");
					request.setAttribute("errorlist", errorlist);
					request.setAttribute("agent", tempagent);
					return mapping.findForward("updatePassword");
				} else {
					// 支付密码
					tempagent.setPayPassword(MD5.encrypt(password1));
					agentBiz.updateAgent(tempagent);
					return mapping.findForward("infochangepaypasswordsuccess");
				}
			}
		}
		if (password1 == null || "".equals(password1.trim())) {
			request.setAttribute("msgrePassword", "新密码必填");
			errorlist.add("新密码必填");
		}
		if (password2 == null || "".equals(password2.trim())) {
			errorlist.add("确认密码必填");
		}
		if (errorlist.size() > 0) {
			request.setAttribute("error", "yes");
			request.setAttribute("errorlist", errorlist);
		}
		return null;
		// return mapping.getInputForward();
	}
	
}
