package com.fordays.fdpay.transaction.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentContact;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.transaction.Debit;
import com.fordays.fdpay.transaction.Logistics;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.TempTransactionBalance;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.TransactionBalance;
import com.fordays.fdpay.transaction.TransactionListForm;
import com.fordays.fdpay.transaction.biz.TransactionBiz;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.message.SMUtil;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.action.Gateway;
import com.fordays.qmpay.blackscreen.biz.BlackscreenBiz;
import com.fordays.qmpay.shop.agent19pay.Agent19pay;
import com.fordays.qmpay.shop.agent19pay.biz.Agent19payBiz;
import com.fordays.fdpay.bank.LogUtil;

public class TransactionAction extends BaseAction {
	private AgentBiz agentBiz;
	private TransactionBiz transactionBiz;
	private Agent19payBiz agent19payBiz;
	private AgentCoterieBiz agentCoterieBiz;
	private BlackscreenBiz blackscreenBiz;

	/**
	 * 商户后台交易列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSellerAndBuyerTransactionList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		// 控制菜单的参数
		String menuList = request.getParameter("menuList");
		String agentType = request.getParameter("agentType");
		String status = request.getParameter("status");// 交易状态
		String orderDetailsType = request.getParameter("orderDetailsType");// 订单交易类型
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		transactionListForm.setAgent(agent);
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			// transactionListForm.setBeginDate(null);
			// transactionListForm.setEndDate(null);
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}

		if (transactionListForm.getBeginDate() != null
				&& !transactionListForm.getBeginDate().equals("")
				&& transactionListForm.getEndDate() != null
				&& !transactionListForm.getEndDate().equals("")) {
			if (transactionListForm.getBeginDate().compareTo(
					transactionListForm.getEndDate()) > 0) {
				request.setAttribute("errorDateMessage", "开始日期不能大于结束日期");
				return mapping.findForward("listtransaction");
			}
		}

		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		if (status != null && !status.equals(""))
			transactionListForm.setStatus(status);
		else
			transactionListForm.setStatus(null);

		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);
		boolean isPass = agentBiz.isPass(agent);
		String fg = request.getParameter("fg");
		String forward = "sellertransaction";
		if (fg.equals("seller"))
			list = transactionBiz.getSellerTransactionList(transactionListForm,
					isPass);
		else {
			list = transactionBiz.getBuyerTransactionList(transactionListForm,
					isPass);
			forward = "buytransaction";
		}

		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
			transaction.setUri(uri);
		}
		transactionListForm.setList(list);
		request.setAttribute("menuList", menuList);
		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("status", status);
		request.setAttribute("agent", agent);
		request.setAttribute("flag", request.getParameter("flag"));

		return mapping.findForward(forward);
	}

	/**
	 * 得到登录账户的所有交易信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTransactions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());

		String menuList = request.getParameter("menuList");
		String agentType = request.getParameter("agentType");
		String status = request.getParameter("status");
		String orderDetailsType = request.getParameter("orderDetailsType");
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}

		transactionListForm.setAgent(agent);
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}

		if (transactionListForm.getBeginDate() != null
				&& !transactionListForm.getBeginDate().equals("")
				&& transactionListForm.getEndDate() != null
				&& !transactionListForm.getEndDate().equals("")) {
			if (transactionListForm.getBeginDate().compareTo(
					transactionListForm.getEndDate()) > 0) {
				request.setAttribute("errorDateMessage", "开始日期不能大于结束日期");
				return mapping.findForward("listtransaction");
			}
		}

		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		if (status != null && !status.equals(""))
			transactionListForm.setStatus(status);
		else
			transactionListForm.setStatus(null);

		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);
		boolean isPass = agentBiz.isPass(agent);
		list = transactionBiz.getTransactions(transactionListForm, isPass);

		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
			transaction.setUri(uri);
		}
		transactionListForm.setList(list);
		request.setAttribute("menuList", menuList);
		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("status", status);
		request.setAttribute("agent", agent);
		request.setAttribute("flag", request.getParameter("flag"));

		return mapping.findForward("listtransaction");
	}

	public ActionForward getTransactionByToAgentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		long orderId = new Long(request.getParameter("orderid"));
		long toAgentId = new Long(request.getParameter("toAgentId"));
		transactionListForm.setOrderId(orderId);
		transactionListForm.setToAgentId(toAgentId);
		List list = transactionBiz
				.getTransactionByToAgentId(transactionListForm);
		BigDecimal totalSum = new BigDecimal("0");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Transaction transaction = (Transaction) list.get(i);
				totalSum = totalSum.add(transaction.getAmount());
			}
		}
		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("totalNum", list.size());
		request.setAttribute("totalSum", totalSum);
		return mapping.findForward("listTransactionByToAgent");
	}

	/**
	 * 借款/还款控制方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBorrowAndRepaymentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		String menuList = request.getParameter("menuList");
		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");
		TransactionListForm transactionListForm = (TransactionListForm) form;
		transactionListForm.setAgent(agent);
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);

		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			/*
			 * transactionListForm.setBeginDate(null);
			 * transactionListForm.setEndDate(null);
			 */
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}
		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		list = transactionBiz.getBorrowAndRepaymentList(transactionListForm); // 调用业务方法
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
			transaction.setUri(uri);
		}
		transactionListForm.setList(list);
		request.setAttribute("menuList", menuList);
		request.setAttribute("tlf", transactionListForm);
		return mapping.findForward("borrowAndRepayment");
	}

	/**
	 * 授信/还款控制方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLetterAndRepaymentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		String menuList = request.getParameter("menuList");
		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");
		TransactionListForm transactionListForm = (TransactionListForm) form;
		transactionListForm.setAgent(agent);
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);

		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			/*
			 * transactionListForm.setBeginDate(null);
			 * transactionListForm.setEndDate(null);
			 */
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}
		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		list = transactionBiz.getLetterAndRepaymentList(transactionListForm); // 调用业务方法
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
			transaction.setUri(uri);
		}
		transactionListForm.setList(list);
		request.setAttribute("menuList", menuList);
		request.setAttribute("tlf", transactionListForm);

		return mapping.findForward("letterAndRepayment");
	}

	/**
	 * 红包
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRedPacketList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		String menuList = request.getParameter("menuList");
		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");
		TransactionListForm transactionListForm = (TransactionListForm) form;
		transactionListForm.setAgent(agent);
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);

		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			/*
			 * transactionListForm.setBeginDate(null);
			 * transactionListForm.setEndDate(null);
			 */
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}
		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		list = transactionBiz.getRedPacketList(transactionListForm); // 调用业务方法
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
			transaction.setUri(uri);
		}
		transactionListForm.setList(list);
		request.setAttribute("menuList", menuList);
		request.setAttribute("tlf", transactionListForm);

		return mapping.findForward("redPacket");
	}

	public ActionForward outTransactionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		TransactionListForm talf = (TransactionListForm) form;
		int i = transactionBiz.getTransactionBalanceRow(talf);
		if (i <= TempTransactionBalance.max_download_amount) {
			talf.setAgent(agent);
			// talf.setCheck("download");
			ArrayList<ArrayList<Object>> lists = transactionBiz
					.getTransactionReportData(talf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			// outFileName=new String(outFileName.getBytes("GBK"));
			String outText = FileUtil.createCSVFile(lists);
			outText = new String(outText.getBytes("UTF-8"));
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK");
			return null;
		} else {
			request.setAttribute("magMaxDownloadAmount",
					"您所需数据量太大，请缩小查询时间范围再进行下载！");
			return accountDetaillist(mapping, talf, request, response);
		}
	}

	/**
	 * 即时到账付款首页转向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward payIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tab = request.getParameter("tab");
		request.setAttribute("tab", tab);
		return mapping.findForward("payindex");
	}

	/**
	 * 即时到账付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fastPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String paytype = request.getParameter("ptype").toString();

		Agent agent = new Agent();
		agent = uri.getAgent();

		BigDecimal totalPriceByDay = transactionBiz.getAmountSum(agent);

		List listContact = new ArrayList();
		listContact = transactionBiz.getContactList(agent);
		request.setAttribute("listContact", listContact);
		request.setAttribute("counts", listContact.size());

		List listAgentAddress = new ArrayList();
		listAgentAddress = transactionBiz.getAgentAddressList(agent);
		request.setAttribute("listAgentAddress", listAgentAddress);

		request.setAttribute("totalPriceByDay", totalPriceByDay);
		request.setAttribute("loginAccount", uri.getAgent().getLoginName());
		request.setAttribute("paytype", paytype);
		return mapping.findForward("fastpay");
	}

	/**
	 * 搜索联系人功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getListContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String searchContactInput = request.getParameter("searchContactInput");
		// searchContactInput = new
		// String(searchContactInput.getBytes("ISO-8859-1"), "UTF-8");
		searchContactInput = new String(searchContactInput.getBytes("UTF-8"));
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		String begin = "<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"Contact_table\" style=\"margin-left:0\">";
		String end = "</table>";
		String xml_start = "<tr>";
		String xml_end = "</tr>";
		String xml = "";
		int num = 1;
		List list = new ArrayList();
		list = transactionBiz.getListContact(agent, searchContactInput);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			AgentContact ac = (AgentContact) it.next();
			xml += "<td><a href=\"javascript:selectBuyerEmail('"
					+ ac.getEmail() + "')\">" + ac.getName() + "</a>("
					+ ac.getEmail() + ")</td>";
			xml += "<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>";
			if (num % 2 == 0) {
				xml += "<tr><td></td></tr>";
			}
			num++;
		}
		String last_xml = xml_start + xml + xml_end;
		last_xml += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td align=\"right\">共有"
				+ list.size() + "个联系人</td></tr>";
		String end_xml = begin + last_xml + end;
		out.print(end_xml);
		return null;
	}

	/**
	 * 即时到账确认
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward payConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentByName(transaction.getSellerAccount());
		transaction.setToAgent(agent);

		String paytype = request.getParameter("ptype").toString();
		request.setAttribute("paytype", paytype);
		request.setAttribute("tranvo", transaction);
		request.setAttribute("loginAccount", uri.getAgent().getLoginName());
		AgentBalance ab = agentBiz.getAgentBalance(uri.getAgent().getId());
		request.setAttribute("allowBalance", ab.getAllowBalance());
		saveToken(request);
		return mapping.findForward("payconfirm");
	}

	/**
	 * 添加即时到账
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward paySave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		BigDecimal price = new BigDecimal("0");
		if (transaction.getPaytype().equals("0")) {
			price = transaction.getOrderDetails().getPaymentPrice();
		} else if (transaction.getPaytype().equals("1")) {
			price = transaction.getOrderDetails().getShopPrice();
		}
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		int enoughAmount = 0; // 不够钱
		// 是否允许支付
		// agentBiz.synallowBalance(agent); 修改同步金额烦方法 张威威 2009/12/25
		AgentBalance agentBlance = agentBiz.getAgentBalance(agent.getId());
		if (Gateway.equals(agentBlance.getAllowBalance(), price)) {

			enoughAmount = 1;
		}
		// 不够钱
		if (enoughAmount == 0) {

			request.setAttribute("msg", "帐号余额不足");
			return mapping.findForward("transactionError");
		}

		long transactionId = 0;
		if (isTokenValid(request, true)) {
			transactionId = transactionBiz.addTransaction(transaction, agent);
			transactionBiz.sendEmail(transactionId);
		} else {
			saveToken(request);
		}
		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	/**
	 * 转去我要收款页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transactionBySeller(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;
		String forward = "transactionbyseller";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		List listContact = new ArrayList();
		listContact = transactionBiz.getContactList(agent);
		request.setAttribute("listContact", listContact);
		request.setAttribute("counts", listContact.size());

		request.setAttribute("loginAccount", uri.getAgent().getLoginName());
		request.setAttribute("paytype", transaction.getPaytype());
		if (transaction.getPaytype().equals("4"))
			forward = "transactiondun";
		saveToken(request);
		return mapping.findForward(forward);
	}

	/**
	 * 实现还款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transactionRepayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transactionForm = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(uri.getAgent().getId());
		if (!agent.getPayPassword().equals(
				MD5.encrypt(request.getParameter("payPassword")))) {
			request.setAttribute("msgPass", "支付密码错误！");
			return repayment(mapping, transactionForm, request, response);
		}
		Transaction transaction = transactionBiz
				.getTransactionById(transactionForm.getTid());

		long transactionId = 0;
		transactionId = transactionBiz.addTransactionRepayment(transactionForm);
		if (transactionId > 0) {
			// transactionBiz.sendEmailByRepayment(transactionId);
		}
		request.setAttribute("trans", transaction);

		return mapping.findForward("transactionSuccessByRepayment");
	}

	public ActionForward transactionCreditRepayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transactionForm = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(uri.getAgent().getId());
		if (!agent.getPayPassword().equals(
				MD5.encrypt(request.getParameter("payPassword")))) {
			request.setAttribute("msgPass", "支付密码错误！");
			return repayment(mapping, transactionForm, request, response);
		}
		// Transaction transaction = transactionBiz
		// .getTransactionById(transactionForm.getTid());
		//
		System.out.println(transactionForm.getFromAgentId());
		System.out.println(transactionForm.getToAgentId());
		long transactionId = 0;
		transactionId = transactionBiz
				.transactionCreditRepayment(transactionForm);

		if (transactionId > 0) {
			transactionBiz.sendEmailByRepayment(transactionId);
		}

		return mapping.findForward("transactionSuccessByRepayment");
	}

	// 添加授信还款
	public ActionForward creditRepayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		agentBiz.synallowBalance(uri.getAgent());
		if (agent.getValidCertStatus() == 0) {
			return mapping.findForward("accessDeniedCert");
		}
		// Transaction transaction = transactionBiz.getTransactionById(new Long(
		// tid));
		// Agent fromAgent = transaction.getFromAgent();
		Transaction creditTransaction = transactionBiz
				.getCreditGiverTransactionByAgent(agent);

		// 判断信用支付圈是否有给过用户授信金额
		if (creditTransaction == null) {
			return mapping.findForward("noletterpay");// 无需还款
		}
		System.out.println("授信还款圈partner:"
				+ creditTransaction.getOrderDetails().getPartner());
		AgentCoterie this_agent_coterie = agentCoterieBiz.queryByAgentEmail(
				agent.getLoginName(), creditTransaction.getOrderDetails()
						.getPartner());
		// 判断是否加入信用支付圈
		if (this_agent_coterie == null) {
			return mapping.findForward("lettermes");
		}
		Transaction transaction = new Transaction();
		transaction.setFromAgent(agent);
		transaction.setToAgent(this_agent_coterie.getCoterie().getAgent());
		// 查询出授信累计欠款总额
		BigDecimal creditPaymentAmount = transactionBiz
				.getCreditPaymentArrearage(agent);
		System.out.println("-----" + agent.getLoginName() + "--未结算授信欠款总额:"
				+ creditPaymentAmount);
		BigDecimal creditRePaymentAmount = transactionBiz
				.getCreditRePaymentAmount(agent);
		System.out.println("-----" + agent.getLoginName() + "--未结算授信还款总额:"
				+ creditRePaymentAmount);
		transaction.setCreditPaymentAmount(creditPaymentAmount);
		transaction.setCreditRePaymentAmount(creditRePaymentAmount);
		// List list = transactionBiz.getTransactionByOrderNo(transaction
		// .getOrderDetails().getOrderDetailsNo());
		// BigDecimal sumRepaymentPrice = new BigDecimal("0");
		// if (list != null && list.size() > 0) {
		// for (int i = 0; i < list.size(); i++) {
		// Transaction t = (Transaction) list.get(i);
		// sumRepaymentPrice = sumRepaymentPrice.add(t.getAmount());
		// }
		// }
		// request.setAttribute("repaymentAmaut", transaction.getAmount()
		// .subtract(sumRepaymentPrice));
		request.setAttribute("transaction", transaction);
		// request.setAttribute("sumRepaymentPrice", sumRepaymentPrice);
		return mapping.findForward("creditRepayment");
	}

	/**
	 * 跳转到还款页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward repayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		if (agent.getValidCertStatus() == 0) {
			return mapping.findForward("accessDeniedCert");
		}

		String tid = request.getParameter("tid");
		Transaction transaction = transactionBiz.getTransactionById(new Long(
				tid));
		if (transaction == null) {
			return creditRepayment(mapping, form, request, response);
		}
		Agent fromAgent = transaction.getFromAgent();

		List list = transactionBiz.getTransactionByOrderNo(transaction
				.getOrderDetails().getOrderDetailsNo());
		BigDecimal sumRepaymentPrice = new BigDecimal("0");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Transaction t = (Transaction) list.get(i);
				sumRepaymentPrice = sumRepaymentPrice.add(t.getAmount());
			}
		}
		request.setAttribute("repaymentAmaut", transaction.getAmount()
				.subtract(sumRepaymentPrice));
		request.setAttribute("transaction", transaction);
		request.setAttribute("sumRepaymentPrice", sumRepaymentPrice);
		return mapping.findForward("repayment");
	}

	/**
	 * 转去借款给朋友页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transactionByBorrowing(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());

		List listContact = new ArrayList();
		listContact = transactionBiz.getContactList(agent);
		request.setAttribute("listContact", listContact);
		request.setAttribute("counts", listContact.size());

		request.setAttribute("agent", agent);
		request.setAttribute("paytype", transaction.getPaytype());
		return mapping.findForward("transactionBorrowing");
	}

	/**
	 * 申请预支
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward debit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());

		List listContact = new ArrayList();
		listContact = transactionBiz.getContactList(agent);
		request.setAttribute("listContact", listContact);
		request.setAttribute("counts", listContact.size());

		request.setAttribute("agent", agent);
		request.setAttribute("loginAccount", agent.getLoginName());
		request.setAttribute("paytype", transaction.getPaytype());
		return mapping.findForward("debit");
	}

	/**
	 * 添加申请预支
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addDebit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		Agent fromAgent = agentBiz.getAgentByLoginName(transaction
				.getBuyerAccount());
		if (agentBiz.getAgentAllowBalance(fromAgent.getId()).compareTo(
				transaction.getAmount()) == -1) {
			request.setAttribute("emgError", "预支方金额不足，不能完成预支操作！");
			return debit(mapping, transaction, request, response);
		} else {
			transactionBiz.addDebit(transaction, agent);
			return mapping.findForward("debitSuccess");
		}
	}

	/**
	 * 查看申请预支记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDebitList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		List list = transactionBiz.getDebitList(agent, transactionListForm);
		transactionListForm.setPerPageNum(5);
		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);
		return mapping.findForward("getDebitList");
	}

	/**
	 * 查看申请报销记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExpenseList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		List list = transactionBiz.getExpenseList(agent, transactionListForm);
		transactionListForm.setPerPageNum(5);
		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);
		return mapping.findForward("getExpenseList");
	}

	/**
	 * 添加申请报销
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		transactionBiz.addExpense(transaction, agent);
		return mapping.findForward("expenseSuccess");
	}

	/**
	 * 申请报销
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward expense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		Debit debit = transactionBiz
				.getDebitByDebitNo(transaction.getDebitNo());
		Transaction trans = transactionBiz.getTransactionById(transaction
				.getTid());
		request.setAttribute("agent", agent);
		request.setAttribute("trans", trans);
		request.setAttribute("debit", debit);
		return mapping.findForward("expense");
	}

	/**
	 * 预支/报销控制方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDebitAndExpenseList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = agentBiz.getAgentById(uri.getAgent().getId());
		String menuList = request.getParameter("menuList");
		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");
		TransactionListForm transactionListForm = (TransactionListForm) form;
		transactionListForm.setAgent(agent);
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);

		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			// transactionListForm.setBeginDate(null);
			// transactionListForm.setEndDate(null);
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}
		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		list = transactionBiz.getDebitAndExpenseList(transactionListForm); // 调用业务方法
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
			transaction.setUri(uri);
		}
		transactionListForm.setList(list);
		request.setAttribute("menuList", menuList);
		request.setAttribute("tlf", transactionListForm);
		return mapping.findForward("debitAndExpense");
	}

	/**
	 * 保存我要收款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTransactionSeller(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		long transactionId = 0;

		if (isTokenValid(request, true)) {
			transactionId = transactionBiz.addTransaction(transaction, agent);
			if (transactionId > 0) {
				transactionBiz.sendEmail(transactionId);
			}
		} else {
			saveToken(request);
		}

		request.setAttribute("loginAccount", uri.getAgent().getLoginName());
		request.setAttribute("paytype", transaction.getPaytype());
		request.setAttribute("trans", transaction);

		return mapping.findForward("transactionSuccess");
	}

	/**
	 * 保存借款给朋友
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTransactionBorrowing(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = agentBiz.getAgentById(uri.getAgent().getId());
		if (!agent.getPayPassword().equals(
				MD5.encrypt(request.getParameter("payPassword")))) {
			request.setAttribute("emsppw", "支付密码错误！");
			return transactionByBorrowing(mapping, transaction, request,
					response);
		}
		long transactionId = 0;

		transactionId = transactionBiz.addTransactionBorrowing(transaction,
				agent);
		if (transactionId > 0) {
			transactionBiz.sendEmailByRepayment(transactionId);
		}

		request.setAttribute("loginAccount", uri.getAgent().getLoginName());
		request.setAttribute("trans", transaction);

		return mapping.findForward("transactionSuccessByBorrowing");
	}

	/**
	 * 批量收款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchCollect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		AgentListForm alf = new AgentListForm();
		alf.setAgentId(uri.getAgent().getId());
		alf.setPerPageNum(500);
		List list = agentBiz.getContactList(alf);
		request.setAttribute("paytype", transaction.getPaytype());
		alf.setList(list);
		request.setAttribute("alf", alf);
		request.setAttribute("loginName", uri.getAgent().getLoginName());
		request.setAttribute("MaxTr", list.size());
		return mapping.findForward("batchcollect");
	}

	/**
	 * 批量付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		AgentListForm alf = new AgentListForm();
		alf.setPerPageNum(500);
		alf.setAgentId(uri.getAgent().getId());
		List list = agentBiz.getContactList(alf);
		request.setAttribute("paytype", transaction.getPaytype());
		alf.setList(list);
		request.setAttribute("alf", alf);
		request.setAttribute("loginName", uri.getAgent().getLoginName());
		// agentBiz.synallowBalance(uri.getAgent()); 修改同步金额的方法 张威威 2009-12-25
		AgentBalance agentBalance = agentBiz.getAgentBalance(uri.getAgent()
				.getId());
		request.setAttribute("allowBalance", agentBalance);
		request.setAttribute("MaxTr", list.size());
		saveToken(request);// 保存令牌

		return mapping.findForward("batchpayment");
	}

	/**
	 * 去到发红包page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchRedPacket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		AgentListForm alf = new AgentListForm();
		alf.setPerPageNum(500);
		alf.setAgentId(uri.getAgent().getId());
		List list = agentBiz.getContactList(alf);
		request.setAttribute("paytype", transaction.getPaytype());
		alf.setList(list);
		request.setAttribute("alf", alf);
		request.setAttribute("loginName", uri.getAgent().getLoginName());
		// agentBiz.synallowBalance(uri.getAgent()); 修改同步金额的方法 张威威 2009-12-25
		AgentBalance agentBalance = agentBiz.getAgentBalance(uri.getAgent()
				.getId());
		request.setAttribute("allowBalance", agentBalance);
		request.setAttribute("MaxTr", list.size());
		saveToken(request);// 保存令牌

		return mapping.findForward("batchRedPacket");
	}

	/**
	 * 添加批量收款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBatchCollect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;
		String[] agentNames = request.getParameterValues("emails");
		String[] payPrice = request.getParameterValues("prices");
		BigDecimal totalPrice = new BigDecimal("0");

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		for (int i = 0; i < agentNames.length; i++) {
			Agent age = agentBiz.getAgentByName(agentNames[i]);
			if (age != null) {
				totalPrice = totalPrice.add(new BigDecimal(payPrice[i]));
			}
		}
		transaction.setAgentName(agentNames);
		transaction.setPayPrice(payPrice);
		transaction.setTotalPrice(totalPrice);
		List<Long> list = transactionBiz.addBatchCollect(transaction, agent);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				transactionBiz.sendBatchEmail(list.get(i).longValue());
			}
		}
		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	/**
	 * 添加批量付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBatchPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;

		if (isTokenValid(request, true)) { // 验证令牌
			String[] agentNames = request.getParameterValues("emails");
			String[] payPrice = request.getParameterValues("prices");
		
			BigDecimal totalPrice = new BigDecimal("0");

			UserRightInfo uri = (UserRightInfo) request.getSession()
					.getAttribute("URI");
			Agent agent = new Agent();

			for (int i = 0; i < agentNames.length; i++) {
				Agent age = agentBiz.getAgentByName(agentNames[i]);
				if (age != null) {
					totalPrice = totalPrice.add(new BigDecimal(payPrice[i]));
				}
			}

			agent = agentBiz.getAgentById(uri.getAgent().getId());
			int enoughAmount = 0; // 不够钱
			// 是否允许支付

			// 同步商户可用余额
			// agentBiz.synallowBalance(agent);
			AgentBalance agentBalance = agentBiz.getAgentBalance(agent.getId());
			if (Gateway.equals(agentBalance.getAllowBalance(), totalPrice)) {
				enoughAmount = 1;
			}
			// 不够钱
			if (enoughAmount == 0) {
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}

			transaction.setAgentName(agentNames);
			transaction.setPayPrice(payPrice);
			transaction.setTotalPrice(totalPrice);
			
			List<Long> list = transactionBiz
					.addBatchPayment(transaction, agent);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					transactionBiz.sendBatchEmail(list.get(i).longValue());
				}
			}
			return new ActionForward(
					"/transaction/transactionlist.do?thisAction=listTransactions");
		} else {
			request.setAttribute("msg", "此订单已经付款");
			return mapping.findForward("transactionError");
		}
	}

	/**
	 * 发红包
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBatchRedPacket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transaction = (Transaction) form;

		if (isTokenValid(request, true)) { // 验证令牌
			String objtextarea = request.getParameter("players");
			String[] agentNames = objtextarea.split("[;,]");
			System.out.println("player:" + agentNames);
			String price = request.getParameter("assignPrice");
			System.out.println("单价" + price);
			BigDecimal totalPrice = new BigDecimal("0");
			String shopUrl=request.getParameter("shopUrl");
			UserRightInfo uri = (UserRightInfo) request.getSession()
					.getAttribute("URI");
			Agent agent = new Agent();
			String[] payPrice = new String[agentNames.length];
			for (int i = 0; i < agentNames.length; i++) {
				if (agentNames[i].equals("")) {
					break;
				}
				Agent age = agentBiz.getAgentByName(agentNames[i]);
				if (age != null) {
					totalPrice = totalPrice.add(new BigDecimal(price));
				} else {
					Agent tempAgent = new Agent();
					tempAgent.setLoginName(agentNames[i]);
					tempAgent.setPassword("123456");
					tempAgent.setPayPassword("123456");
					agentBiz.autoRegister(tempAgent);
					totalPrice = totalPrice.add(new BigDecimal(price));
				}
				payPrice[i] = price;
			}

			agent = agentBiz.getAgentById(uri.getAgent().getId());
			int enoughAmount = 0; // 不够钱
			// 是否允许支付

			// 同步商户可用余额
			// agentBiz.synallowBalance(agent);
			AgentBalance agentBalance = agentBiz.getAgentBalance(agent.getId());
			if (Gateway.equals(agentBalance.getAllowBalance(), totalPrice)) {
				enoughAmount = 1;
			}
			// 不够钱
			if (enoughAmount == 0) {
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}

			transaction.setAgentName(agentNames);
			transaction.setPayPrice(payPrice);
			transaction.setTotalPrice(totalPrice);
			transaction.setShopUrl(shopUrl);
			List<Long> list = transactionBiz.addBatchRedPacket(transaction,
					agent);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					transactionBiz.sendBatchEmail(list.get(i).longValue());
				}
			}
			return new ActionForward(
					"/transaction/transactionlist.do?thisAction=listTransactions");
		} else {
			request.setAttribute("msg", "此订单已经付款");
			return mapping.findForward("transactionError");
		}
		// return null;
	}
	
	/**
	 * 验证邮箱是否是钱门帐号
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkAgentEmails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			String emails = request.getParameter("emails");
			if(emails==null||emails==""){
				response.getWriter().print("");
				return null;
			}
			String[] agentNames = emails.split("[;,]");
			System.out.println("player:" + agentNames);
			StringBuffer result= new StringBuffer();
			for (int i = 0; i < agentNames.length; i++) {
				if (agentNames[i].equals("")) {
					break;
				}
				Agent age = agentBiz.getAgentByName(agentNames[i]);
				String name=null;
				if (age != null) {
					name=age.getName();
				} else {
					name=agentNames[i];
				}
				result.append(name+"("+agentNames[i]+");");
			}
			response.getWriter().write(result.toString());
			return null;
	}


	/**
	 * 查看批量收款记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchCollectDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		String paytype = request.getParameter("paytype");
		transactionListForm.setPaytype(paytype);
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		List list = new ArrayList();
		list = transactionBiz.getBatchCollectDetail(transactionListForm, agent);

		for (int i = 0; i < list.size(); i++) {
			OrderDetails od = (OrderDetails) list.get(i);
			Iterator it = od.getTransactions().iterator();
			int num = 0;
			while (it.hasNext()) {
				Transaction trans = (Transaction) it.next();
				if (trans.getStatus() == Transaction.STATUS_1
						|| trans.getStatus() == Transaction.STATUS_2) {
					num++;
				}
			}
			od.setBatchStatus(num);
		}

		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);

		return mapping.findForward("batchCollectDetail");
	}

	/**
	 * 查看批量付款记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchPaymentDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		String paytype = request.getParameter("paytype");
		transactionListForm.setPaytype(paytype);
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		List list = new ArrayList();
		list = transactionBiz.getBatchPaymentDetail(transactionListForm, agent);

		for (int i = 0; i < list.size(); i++) {
			OrderDetails od = (OrderDetails) list.get(i);
			od.setBatchStatus();
		}

		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);

		return mapping.findForward("batchPaymentDetail");
	}

	/**
	 * 查看批量收款明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchCollectDetailById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TransactionListForm transactionListForm = (TransactionListForm) form;
		String paytype = request.getParameter("paytype");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		String orderId = request.getParameter("orderid");
		transactionListForm.setOrderId(new Long(orderId));
		List list = new ArrayList();
		list = transactionBiz.getBatchCollectDetailById(transactionListForm);

		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
		}
		transactionListForm.setList(list);

		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("paytype", paytype);
		return mapping.findForward("getBatchCollectDetail");
	}

	/**
	 * 查看批量付款明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBatchPaymentDetailById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TransactionListForm transactionListForm = (TransactionListForm) form;
		String paytype = request.getParameter("paytype");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		String orderId = request.getParameter("orderid");
		transactionListForm.setOrderId(new Long(orderId));
		List list = new ArrayList();
		list = transactionBiz.getBatchPaymentDetailById(transactionListForm);

		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
		}
		transactionListForm.setList(list);

		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("paytype", paytype);
		return mapping.findForward("getBatchPaymentDetail");
	}

	/**
	 * 检查支付密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkPayPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		String payPassword = request.getParameter("payPassword");
		agent.setPayPassword(MD5.encrypt(payPassword));

		Agent agn = agentBiz.checkPayPassword(agent);
		PrintWriter pw = response.getWriter();
		if (agn == null)
			pw.print("0");
		else {
			// agentBiz.synallowBalance(agn);
			AgentBalance agentBalance = agentBiz.getAgentBalance(agn.getId());
			if (request.getParameter("paymentPrice") != null
					&& !request.getParameter("paymentPrice").equals("")) {
				BigDecimal paymentPrice = new BigDecimal(request
						.getParameter("paymentPrice"));
				if (!Gateway.equals(agentBalance.getAllowBalance(),
						paymentPrice))
					pw.print("2");
				else
					pw.print("1");
			} else if (request.getParameter("shopPrice") != null
					&& !request.getParameter("shopPrice").equals("")) {
				BigDecimal shopPrice = new BigDecimal(request
						.getParameter("shopPrice"));
				if (!Gateway.equals(agentBalance.getAllowBalance(), shopPrice))
					pw.print("2");
				else
					pw.print("1");
			} else
				pw.print("1");
		}
		pw.close();
		return null;
	}

	/**
	 * 检查支付余额
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkPayPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		BigDecimal paymentPrice = new BigDecimal(request
				.getParameter("paymentPrice"));
		AgentBalance agentBalance = agentBiz.getAgentBalance(agent.getId());
		PrintWriter pw = response.getWriter();
		if (paymentPrice.compareTo(agentBalance.getAllowBalance()) == 1)
			pw.print("0");
		else
			pw.print("1");
		pw.close();
		return null;
	}

	/**
	 * 检查支付余额是否开始或者关闭
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkCanPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mobileValidateCode = request.getParameter("mobileValidateCode");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		Agent tempAgent = null;
		PrintWriter pw = response.getWriter();
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		tempAgent = agentBiz.checkCanPay(agent);
		if (tempAgent == null) // 支付功能已关闭
			pw.print("0");
		else {
			if (tempAgent.getMobilePasswordStatus() == 1) { // 已经设置动态口令
				if (amount.compareTo(tempAgent.getMaxItermAmount()) == 1) { // 判断支付金额是否大于设置的单笔支付额度
					if (uri.getAgent().getValidateMobileCode() == null) {
						pw.print("1");
					} else {
						if (mobileValidateCode == null
								|| mobileValidateCode.equals("")) {
							pw.print("5");
						} else {
							if (uri.getAgent().getValidateMobileCode().equals(
									mobileValidateCode)) {
								uri.getAgent().setValidateMobileCode(null); // 重新清空验证码
								pw.print("3");
							} else
								pw.print("4");
						}
					}
				} else {
					BigDecimal amountSum = transactionBiz.getAmountSum(agent);
					if (amountSum.compareTo(tempAgent.getMaxDayAmount()) == 1) { // 判断支付金额是否大于设置的当天总支付额度
						if (uri.getAgent().getValidateMobileCode() == null)
							pw.print("2");
						else {
							if (uri.getAgent().getValidateMobileCode().equals(
									mobileValidateCode)) {
								uri.getAgent().setValidateMobileCode(null); // 重新清空验证码
								pw.print("3");
							} else
								pw.print("4");
						}
					} else
						pw.print("3");
				}
			} else
				pw.print("3");
		}
		pw.close();
		return null;
	}

	/**
	 * 检查支付余额是否开启或者关闭,并检查支付余额
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkCanPayByBatchPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String mobileValidateCode = request.getParameter("mobileValidateCode");
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();
		Agent tempAgent = null;
		Agent tempAgent2 = null;
		PrintWriter pw = response.getWriter();
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		tempAgent = agentBiz.checkCanPay(agent);
		tempAgent2 = agentBiz.getAgentById(agent.getId());
		// agentBiz.synallowBalance(tempAgent2);
		AgentBalance agentBalance = agentBiz.getAgentBalance(agent.getId());

		if (!Gateway.equals(agentBalance.getAllowBalance(), amount)) {
			pw.print("6"); // 不够余额支付
		} else {
			if (tempAgent == null) // 支付功能已关闭
				pw.print("0");
			else {
				if (tempAgent.getMobilePasswordStatus() == 1) { // 已经设置动态口令
					if (amount.compareTo(tempAgent.getMaxItermAmount()) == 1) { // 判断支付金额是否大于设置的单笔支付额度
						if (uri.getAgent().getMobileValidateCode() == null) {
							pw.print("1");
						} else {
							if (mobileValidateCode == null
									|| mobileValidateCode.equals("")) {
								pw.print("5");
							} else {
								if (uri.getAgent().getValidateMobileCode()
										.equals(mobileValidateCode)) {
									uri.getAgent().setMobileValidateCode(null); // 重新清空验证码
									pw.print("3");
								} else
									pw.print("4");
							}
						}
					} else {
						BigDecimal amountSum = transactionBiz
								.getAmountSum(agent);
						if (amountSum.compareTo(tempAgent.getMaxDayAmount()) == 1) { // 判断支付金额是否大于设置的当天总支付额度
							if (uri.getAgent().getValidateMobileCode() == null)
								pw.print("2");
							else {
								if (uri.getAgent().getValidateMobileCode()
										.equals(mobileValidateCode)) {
									uri.getAgent().setValidateMobileCode(null); // 重新清空验证码
									pw.print("3");
								} else
									pw.print("4");
							}
						} else
							pw.print("3");
					}
				} else
					pw.print("3");
			}
		}
		pw.close();
		return null;
	}

	public ActionForward getMobileValidateCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String mobilePhone = uri.getAgent().getMobilePhone();
		String dd = DateUtil.getDateString("yyyy年MM月dd日");
		String code = NoUtil.getRandom(6);
		uri.getAgent().setValidateMobileCode(code);
		request.setAttribute("mobilePhone", uri.getAgent().getMobilePhone());
		SMUtil.send(mobilePhone, dd + ",你支付的额度超过设置的支付额度,钱门手机动态口令验证码是:" + code
				+ "");
		PrintWriter pw = response.getWriter();
		pw.print(code);
		pw.close();
		return null;
	}

	/**
	 * 检查账户是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter pw = response.getWriter();
		Agent agent = new Agent();
		agent = agentBiz.getAgentByName(request.getParameter("agentName"));
		if (agent == null)
			pw.print("0");
		else
			pw.print("1");
		pw.close();
		return null;
	}

	/**
	 * 关闭交易
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward closeTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);

		request.setAttribute("transaction", transaction);
		return mapping.findForward("closeTransaction");
	}

	/**
	 * 确定关闭交易
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward closeTransactionConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transactionForm = (Transaction) form;
		Transaction tran = transactionBiz.getTransactionById(transactionForm
				.getTid());
		if (tran.getStatus() == 4) {
			request.setAttribute("msg", "关闭出错,此订单已经关闭!");
			return mapping.findForward("transactionError");
		}
		Transaction transaction = transactionBiz
				.closeTransactionConfirm(transactionForm);
		transactionBiz.sendEmail(transaction);
		request.setAttribute("transaction", transaction);
		return mapping.findForward("transactionSuccessAndFailDetail");
	}

	/**
	 * 根据ID得到transaction
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTransactionById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		String type = request.getParameter("type");
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		request.setAttribute("transaction", transaction);

		request.setAttribute("type", type);
		return mapping.findForward("modifyTransactionPrice");
	}

	/**
	 * 检查批量收款账号是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward ajaxCheckEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String email = request.getParameter("parEmail");
		if(email==null||email==""){
			return null;
		}
		PrintWriter p = response.getWriter();
		String name = "";
		try {
			Agent agent = agentBiz.checkAgentByEmail(email);
			if (agent != null) {
				name = agent.getName();
				p.write(agent.getName());
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 确认修改交易价格
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyTransactionPriceConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		String type = request.getParameter("type");
		BigDecimal emailPrice = new BigDecimal(request
				.getParameter("emailPrice"));
		BigDecimal totalFee = new BigDecimal(request.getParameter("totalFee"));
		BigDecimal salePrice = new BigDecimal(request.getParameter("salePrice"));
		Transaction transaction = new Transaction();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setEmailPrice(emailPrice);
		orderDetails.setId(orderid);
		transaction.setId(tid);
		transaction.setAmount(totalFee);
		transaction.setSalePrice(salePrice);
		transaction.setOrderDetails(orderDetails);

		request.setAttribute("trans", transaction);

		request.setAttribute("type", type);
		return new ActionForward(
				"/transaction/transaction.do?thisAction=getTransactionById&tid="
						+ tid);
	}

	public TransactionBiz getTransactionBiz() {
		return transactionBiz;
	}

	/**
	 * 查看交易明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTransactionDetailById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String forward = "transactionDetail";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddressInfo", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		return mapping.findForward(forward);
	}

	/**
	 * 查看交易成功或者失败详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTransactionSuccessAndFailDetail(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "transactionSuccessAndFailDetail";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));

		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddress", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("fg", request.getParameter("fg"));
		if (request.getParameter("allLetter") != null
				&& request.getParameter("allLetter") != "") {
			if (request.getParameter("allLetter").equals("allLetter"))
				request.setAttribute("letter", "letter");
			else
				request.setAttribute("letter", "");
		} else {
			request.setAttribute("letter", "");
		}
		return mapping.findForward(forward);
	}

	/**
	 * 查看商户后台失败详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTradeTransactionFail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forward = "viewTradeTransactionFail";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddress", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		return mapping.findForward(forward);
	}

	/**
	 * 查看交易明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transactionDetailByDunAndBatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddress", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("seller", request.getParameter("seller"));
		return mapping.findForward("transactionDetailByDunAndBatch");
	}

	/**
	 * 查看红包交易
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transactionDetailByRedPacket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddress", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("seller", request.getParameter("seller"));
		return mapping.findForward("transactionDetailByRedPacket");
	}

	/**
	 * 查看商户卖出成功信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTradeTransactionSuccess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddress", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		return mapping.findForward("viewTradeTransactionSuccess");
	}

	/**
	 * 查看商户买入成功信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTradeBuyTransactionSuccess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("agentAddress", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		return mapping.findForward("viewTradeBuyTransactionSuccess");
	}

	/**
	 * 用户备注
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userMark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);

		request.setAttribute("fg", request.getParameter("fg"));
		request.setAttribute("transaction", transaction);
		return mapping.findForward("addMark");
	}

	/**
	 * 添加用户备注
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Transaction transaction = (Transaction) form;

		long tid = new Long(request.getParameter("tid"));
		transaction.setId(tid);
		transactionBiz.addMark(transaction);
		request.setAttribute("transaction", transaction);

		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	/**
	 * 修改交易价格
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward updateTransactionPrice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		BigDecimal emailPrice = new BigDecimal(request
				.getParameter("newEmailPrice"));
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		BigDecimal salePrice = new BigDecimal(request.getParameter("salePrice"));

		Transaction tran = transactionBiz.getTransactionById(tid);
		if (tran.getStatus() == 4) {
			request.setAttribute("msg", "修改出错,此订单已经关闭!");
			return mapping.findForward("transactionError");
		}
		Transaction trans = new Transaction();
		Transaction transaction = new Transaction();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setId(orderid);
		orderDetails.setEmailPrice(emailPrice);
		orderDetails.setSalePrice(salePrice);
		transaction.setId(tid);
		transaction.setAmount(amount);
		transaction.setOrderDetails(orderDetails);

		try {
			trans = transactionBiz.updateTransactionPrice(transaction);
			transactionBiz.sendEmail(trans);
		} catch (AppException e) {
			e.printStackTrace();
		}

		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	// aaaaaaa
	public ActionForward accountDetaillist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String menuList = request.getParameter("menuList");
		TransactionListForm talf = (TransactionListForm) form;
		talf.setAgent(agent);
		// talf.setCheck("");

		String balanceType = request.getParameter("balanceType");
		if (balanceType != null && !"".equals(balanceType)) {
			talf.setBalanceType(Integer.parseInt(balanceType));
		} else {
			talf.setBalanceType(0);
		}

		if (talf == null) {
			talf = new TransactionListForm();
		}
		talf.setPerPageNum(10);

		if (talf.getBeginDate() != null && !talf.getBeginDate().equals("")
				&& talf.getEndDate() != null && !talf.getEndDate().equals("")) {
			if (talf.getBeginDate().compareTo(talf.getEndDate()) > 0) {
				request.setAttribute("errorDateMessage", "开始日期不能大于结束日期");
				request.setAttribute("talf", talf);
				return mapping.findForward("accountDetail");
			}
		}
		request.setAttribute("menuList", menuList);
		request.setAttribute("balanceType", balanceType);
		talf.setPerPageNum(10);
		List talflist = transactionBiz.getAgentTransactions(talf, agent);

		talf.setList(talflist);
		System.out.println(talf.getList());
		request.setAttribute("talf", talf);
		return mapping.findForward("accountDetail");
	}

	public ActionForward transactionPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		int enoughAmount = 0; // 不够钱
		// 是否允许支付
		// agentBiz.synallowBalance(transaction.getFromAgent());
		AgentBalance agentBalance = agentBiz.getAgentBalance(transaction
				.getFromAgent().getId());
		if (Gateway.equals(agentBalance.getAllowBalance(), transaction
				.getAmount())) {
			enoughAmount = 1;
		}

		request.setAttribute("enoughAmount", enoughAmount);
		request.setAttribute("agentAddressInfo", agentAddress);
		request.setAttribute("transaction", transaction);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("price", transaction.getAmount().subtract(
				agentBalance.getAllowBalance()));
		return mapping.findForward("transactionPayment");
	}

	/**
	 * 收取红包 张威威
	 * 
	 * @throws AppException
	 * @throws Exception
	 */
	public ActionForward transactionGatherRedPacket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		// long orderid = new Long(request.getParameter("orderid"));
		// long statusid = new Long(request.getParameter("statusid"));
		
		try {
			Transaction transaction = transactionBiz.getTransactionById(tid);
			if(transaction.getStatus()==Transaction.STATUS_3||transaction.getStatus()==Transaction.STATUS_4){
				return mapping.findForward("transactionRedPacketTip");// 跳到红包提示页面
			}
			if(transactionBiz.GatherRedPacket(tid)){
				return new ActionForward(
					"/transaction/transactionlist.do?thisAction=listTransactions");
			}else{
				return mapping.findForward("receiveRedPacketFail");
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 收取红包 邮件 张威威
	 * 
	 * @throws AppException
	 * @throws Exception
	 */
	public ActionForward transactionGatherRedPacketByEmail2(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pay_password = request.getParameter("pay_password");
		System.out.println("当前用户的支付密码:" + pay_password);
		Long tid = null;
		try {
			tid = new Long(request.getParameter("tid"));
		} catch (Exception ex) {
			return mapping.findForward("errorlink");
		}
		String sign = request.getParameter("sign");
		if (sign == "" || sign == null) {
			return mapping.findForward("errorlink");
		}
		try {
			String tempsign = MD5.encrypt("tid=" + tid
					+ AnalyseParameter.EMAIL_SIGN_KEY);
			String tempPaypwd = MD5.encrypt(pay_password);
			Transaction tran =transactionBiz.getTransactionById(tid);
			if (tempsign.endsWith(sign)) {
				if (tran.getStatus() == Transaction.STATUS_3
						|| tran.getStatus() == Transaction.STATUS_4) {
					return mapping.findForward("transactionRedPacketTip");// 跳到红包提示页面

				} else {
					if (!tempPaypwd.equals(transactionBiz.getTransactionById(tid)
							.getToAgent().getPayPassword())) {
						request.setAttribute("msgPayPassword", "您输入的支付密码有误!请重输!");
						
						return transactionGatherRedPacketByEmail(mapping, form,
								request, response);
					}
					if(transactionBiz.GatherRedPacket(tid)){// 收红包
					return mapping.findForward("receiveRedPacketSuccess");
					}else{
					return mapping.findForward("receiveRedPacketFail");
					}
				}
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return mapping.findForward("errorlink");
		}
		
		return null;
	}

	/**
	 * 收取红包 邮件 张威威 红包信息显示页面
	 * 
	 * @throws AppException
	 * @throws Exception
	 */
	public ActionForward transactionGatherRedPacketByEmail(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Long tid = null;
		try {
			tid = new Long(request.getParameter("tid"));
		} catch (Exception ex) {
			return mapping.findForward("errorlink");
		}
		Transaction tran = null;

		String sign = request.getParameter("sign");
		if (sign == "" || sign == null) {
			return mapping.findForward("errorlink");
		}
		String tempsign = MD5.encrypt("tid=" + tid
				+ AnalyseParameter.EMAIL_SIGN_KEY);
		if (tempsign.endsWith(sign)) {
			try {
				tran = transactionBiz.getTransactionById(tid);

				request.setAttribute("transactionRedPacket", tran);
				request.setAttribute("tid", tid);
				request.setAttribute("sign", sign);
				if (tran.getStatus() == Transaction.STATUS_3
						|| tran.getStatus() == Transaction.STATUS_4) {
					return mapping.findForward("transactionRedPacketTip");// 跳到红包提示页面

				} else {
					return mapping.findForward("transactionRedPacketgeView");// 跳到红包显示页面

				}
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				response.getWriter().write("OK");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return mapping.findForward("errorlink");
		}
		
		return mapping.findForward("errorlink");
	}

	/**
	 * 退还红包 张威威
	 * 
	 * @throws AppException
	 * @throws Exception
	 */
	public ActionForward transactionRetreatRedPacket(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		// long orderid = new Long(request.getParameter("orderid"));
		// long statusid = new Long(request.getParameter("statusid"));
		try {
			Transaction transaction = transactionBiz.getTransactionById(tid);
			if(transaction.getStatus()==Transaction.STATUS_3||transaction.getStatus()==Transaction.STATUS_4){
				return mapping.findForward("receiveRedPacketFail");
			}
			if(transactionBiz.RetreatRedPacket(tid)){
			return new ActionForward(
					"/transaction/transactionlist.do?thisAction=listTransactions");
			}else{
				return mapping.findForward("receiveRedPacketFail");
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public ActionForward transactionPaymentI(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		long qmagentId = new Long(request.getParameter("qmagentId"));
		Agent agent = agentBiz.getAgentById(qmagentId);
		if (agent != null) {
			Transaction transaction = transactionBiz.getTransactionById(tid,
					orderid);
			AgentAddress agentAddress = transactionBiz
					.getAgentAddressById(agent);
			int enoughAmount = 0; // 不够钱
			// 是否允许支付
			// agentBiz.synallowBalance(transaction.getFromAgent());
			AgentBalance agentBalance = agentBiz.getAgentBalance(transaction
					.getFromAgent().getId());
			if (Gateway.equals(agentBalance.getAllowBalance(), transaction
					.getAmount())) {
				enoughAmount = 1;
			}

			request.setAttribute("enoughAmount", enoughAmount);
			request.setAttribute("agentAddressInfo", agentAddress);
			request.setAttribute("transaction", transaction);
			request.setAttribute("statusid", statusid);
			request.setAttribute("flag", request.getParameter("flag"));
			request.setAttribute("price", transaction.getAmount().subtract(
					agentBalance.getAllowBalance()));
			return mapping.findForward("transactionPaymentI");
		} else {
			return null;
		}
	}

	/**
	 * 用来查询从邮件链接出来的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 * @throws IOException
	 */
	public ActionForward transactionPaymentByEmail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String no = request.getParameter("no").trim();

		Transaction transaction = transactionBiz.getTransactionByNo(no);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);

		request.setAttribute("agentAddressInfo", agentAddress);
		request.setAttribute("transaction", transaction);
		return mapping.findForward("transactionPayment");
	}

	/**
	 * 确认付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward transactionPaymentConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Transaction transaction = (Transaction) form;
		Transaction tran = null;
		long transactionId = 0;
		if (request.getParameter("transactionId") != null
				&& !request.getParameter("transactionId").equals("")) {
			transactionId = Long.parseLong(request
					.getParameter("transactionId"));
		} else {
			transactionId = transaction.getTid();
		}
		tran = transactionBiz.getTransactionById(transactionId);
		if (tran.getStatus() == 4) { // 表示已经关闭了
			request.setAttribute("msg", "付款出错,此订单已经关闭!");
			return mapping.findForward("transactionError");
		}
		if (tran.getType() == 3) { // 担保交易付款
			if (tran.getStatus() == 2) { // 判断是否已经付款了
				request.setAttribute("msg", "付款出错,此订单已经付过款!");
				return mapping.findForward("transactionError");
			} else if (tran.getStatus() == 3) { // 交易已经成功
				request.setAttribute("msg", "出错,此订单已经确认收货!");
				return mapping.findForward("transactionError");
			}
		} else { // 即时到账付款
			if (tran.getStatus() == 3) { // 判断是否已经付款了
				request.setAttribute("msg", "付款出错,此订单已经付过款!");
				return mapping.findForward("transactionError");
			}
		}
		Agent agent = agentBiz.getAgentById(tran.getFromAgent().getId());
		int enoughAmount = 0; // 不够钱
		// agentBiz.synallowBalance(agent);
		AgentBalance agentBalance = agentBiz.getAgentBalance(agent.getId());
		if (Gateway.equals(agentBalance.getAllowBalance(), tran.getAmount())) {
			enoughAmount = 1;
		}

		if (tran.getOrderDetails().getIsPayMobileType()) {
			System.out
					.println("==============19pay钱门余额手机话费充值====================");
			Agent19pay agent19pay = new Agent19pay();
			agent19pay.setProdid(tran.getOrderDetails().getProid());
			agent19pay.setReturntype(2);
			agent19pay.setMobilenum(tran.getOrderDetails().getMobileNum());
			agent19pay.setOrderid(tran.getOrderDetails().getOrderDetailsNo());
			String resultno = agent19payBiz.pay(agent19pay);
			// String resultno="0000";
			System.out.println("==============充值结果:" + resultno
					+ "====================");
			if ("0000".equals(resultno)) {
				if (enoughAmount == 1) {
					transactionBiz.updateTransactionPayment(transactionId);
				} else {
					request.setAttribute("msg", "帐号余额不足");
					return mapping.findForward("transactionError");
				}
			} else if ("1005".equals(resultno)) {
				request.setAttribute("msg", "没有对应充值产品,请联系客服办理退款");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1007".equals(resultno)) {
				request.setAttribute("msg", "商家余额不足,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1022".equals(resultno)) {
				request.setAttribute("msg", "充值号码格式错误,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1008".equals(resultno)) {
				request.setAttribute("msg", "此产品超出当天限额,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1006".equals(resultno)) {
				request.setAttribute("msg", "运营商系统异常，请稍后重试,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1013".equals(resultno)) {
				request.setAttribute("msg", "运营商系统升级，暂不能充值,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("0009".equals(resultno)) {
				request.setAttribute("msg", "运营商发货未成功,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else {
				request.setAttribute("msg", "运营商系统升级，暂不能充值,请联系客服办理退款");
				return mapping.findForward("transactionPayMobileFailse");
			}
		} else if (tran.getOrderDetails().getIsPayBlackscreenType()) {
			// 黑屏充值调用socket方法
			if (enoughAmount == 1) {
				if (blackscreenBiz.doNoticeBlackscreen(transaction)) {
					transactionBiz.updateTransactionPayment(transactionId);
				} else {
					request.setAttribute("msg", "充值失败！");
					return mapping.findForward("transactionError");
				}
			} else {
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}

		} else {
			if (enoughAmount == 1) {
				transactionBiz.updateTransactionPayment(transactionId);

			} else {
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}
		}
		transactionBiz.sendEmail(transactionId, null);
		request.setAttribute("toAgentLoginName", transaction
				.getToAgentLoginName());
		request.setAttribute("amount", transaction.getAmount());
		request.setAttribute("agreePayment", request
				.getParameter("agreePayment"));
		return mapping.findForward("transactionPaymentConfirm");
	}

	public void setAgent19payBiz(Agent19payBiz agent19payBiz) {
		this.agent19payBiz = agent19payBiz;
	}

	/**
	 * 从网上银行付款返回来调用这个方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward transactionPaymentReturnByBank(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		LogUtil myLog = new LogUtil(false, false, TransactionAction.class);
		myLog.info("transactionPaymentReturnByBank() transactionNo="
				+ request.getParameter("transactionNo"));
		// author:YanRui (勿删)

		String transactionNo = "";
		if (request.getParameter("transactionNo") != null
				&& !request.getParameter("transactionNo").equals("")) {
			transactionNo = request.getParameter("transactionNo");
		}

		Transaction tran = transactionBiz.getTransactionByNo(transactionNo);

		System.out.println("---get Transaction tran success...");

		Agent agent = agentBiz.getAgentById(tran.getFromAgent().getId());

		int enoughAmount = 0; // 不够钱
		// agentBiz.synallowBalance(agent);
		AgentBalance ab = agentBiz.getAgentBalance(agent.getId());
		System.out.println("=========================================");
		System.out.println(agent.getLoginName() + "帐户可用余额:"
				+ ab.getAllowBalance());
		System.out.println("商品名称:" + tran.getOrderDetails().getShopName());
		System.out.println("本次交易应付金额:" + tran.getAmount());
		System.out.println("=========================================");
		if (Gateway.equals(ab.getAllowBalance(), tran.getAmount())) {
			enoughAmount = 1;
		}

		Transaction transaction = null;

		if (tran.getOrderDetails().getIsPayMobileType()) {
			if (enoughAmount != 1) {
				System.out
						.println(" transactionPaymentReturnByBank ---- 帐户余额不足!");
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}
			System.out
					.println("=================19pay网银手机话费直冲========================");
			System.out.println("================网银充值信息:"
					+ tran.getOrderDetails().getShopName()
					+ "=====================");
			System.out.println("================钱门交易号:" + tran.getNo()
					+ "=====================");
			System.out.println("================订单号:"
					+ tran.getOrderDetails().getOrderDetailsNo()
					+ "=====================");
			System.out.println("================买家:"
					+ tran.getToAgent().getLoginName()
					+ "=====================");
			System.out.println("================卖家:"
					+ tran.getFromAgent().getLoginName()
					+ "=====================");
			Agent19pay agent19pay = new Agent19pay();
			agent19pay.setProdid(tran.getOrderDetails().getProid());
			agent19pay.setReturntype(2);
			agent19pay.setMobilenum(tran.getOrderDetails().getMobileNum());
			agent19pay.setOrderid(tran.getOrderDetails().getOrderDetailsNo());
			String resultno = agent19payBiz.pay(agent19pay);
			// String resultno="0000";
			System.out.println("=================网银充值结果:" + resultno
					+ "==============");
			if ("0000".equals(resultno)) {
				if (enoughAmount == 1) {
					transaction = transactionBiz
							.transaction19payByBank(transactionNo);

					request.setAttribute("amount", transaction.getAmount());
					return mapping.findForward("transactionPaymentConfirmI");
				} else {
					request.setAttribute("msg", "帐号余额不足");
					return mapping.findForward("transactionError");
				}
			} else if ("1005".equals(resultno)) {
				request.setAttribute("msg", "没有对应充值产品,请联系客服办理退款");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1007".equals(resultno)) {
				request.setAttribute("msg", "商家余额不足,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1022".equals(resultno)) {
				request.setAttribute("msg", "充值号码格式错误,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1008".equals(resultno)) {
				request.setAttribute("msg", "此产品超出当天限额,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1006".equals(resultno)) {
				request.setAttribute("msg", "运营商系统异常，请稍后重试,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("1013".equals(resultno)) {
				request.setAttribute("msg", "运营商系统升级，暂不能充值,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else if ("0009".equals(resultno)) {
				request.setAttribute("msg", "运营商发货未成功,请联系客服");
				return mapping.findForward("transactionPayMobileFailse");
			} else {
				request.setAttribute("msg", "运营商系统升级，暂不能充值,请联系客服办理退款");
				return mapping.findForward("transactionPayMobileFailse");
			}
		} else if (tran.getOrderDetails().getIsPayBlackscreenType()) {
			// 黑屏充值调用socket方法
			if (enoughAmount == 1) {
				if (blackscreenBiz.doNoticeBlackscreen(transaction)) {
					transaction = transactionBiz
							.transactionPaymentReturnByBank(transactionNo);
				} else {
					request.setAttribute("msg", "充值失败！");
					return mapping.findForward("transactionError");
				}
			} else {
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}

		} else {
			if (enoughAmount == 1) {
				transaction = transactionBiz
						.transactionPaymentReturnByBank(transactionNo);
				System.out
						.println("---transactionPaymentReturnByBank() success..");

			} else {
				request.setAttribute("msg", "帐号余额不足");
				return mapping.findForward("transactionError");
			}
		}

		System.out.println("----before request setAttribute()---");

		request.setAttribute("amount", transaction.getAmount());
		request.setAttribute("transaction", transaction);

		System.out.println("--before transaction.getToAgent....");

		request.setAttribute("toAgentLoginName", transaction.getToAgent()
				.getLoginName());
		System.out.println("--after transaction.getToAgent....");
		return mapping.findForward("transactionPaymentConfirm");
	}

	/**
	 * 查看交易明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTransactionPaymentDetailById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		if (request.getParameter("shipment") != null
				&& !request.getParameter("shipment").equals("")) { // 判断是否点立即发货按钮
			request.setAttribute("shipment", "1");
		}

		if (request.getParameter("type") != null
				&& !request.getParameter("type").equals("")) { // 判断点确认发货按钮还是查看按钮
			request.setAttribute("type", request.getParameter("type"));
		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		request.setAttribute("transaction", transaction);
		request.setAttribute("agentAddressInfo", agentAddress);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		return mapping.findForward("transactionPaymentDetail");
	}

	/**
	 * 查看交易明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTransactionShippingDetailById(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		long statusid = new Long(request.getParameter("statusid"));
		if (request.getParameter("agentid") != null
				&& !request.getParameter("agentid").equals("")) {
			agent = new Agent();
			agent.setId(new Long(request.getParameter("agentid")));

		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		AgentAddress agentAddress = transactionBiz.getAgentAddressById(agent);
		Logistics logistics = transactionBiz.getLogisticsByOrderId(orderid);
		request.setAttribute("transaction", transaction);
		request.setAttribute("agentAddressInfo", agentAddress);
		request.setAttribute("logistics", logistics);
		request.setAttribute("statusid", statusid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("type", request.getParameter("type"));
		return mapping.findForward("transactionShippingDetail");
	}

	/**
	 * 退款第一步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refundMent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		// 是否是证书用户
		if (agent.getValidCertStatus() == 0) {
			return mapping.findForward("accessDeniedCert");
		}

		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		String forward = "refundMent";
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			request.setAttribute("flag", request.getParameter("flag"));
		}

		if (request.getParameter("ptype") != null
				&& !request.getParameter("ptype").equals("")) {
			request.setAttribute("ptype", request.getParameter("ptype"));
		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		Logistics logistics = transactionBiz.getLogisticsByOrderId(orderid);

		request.setAttribute("transaction", transaction);
		request.setAttribute("logistics", logistics);
		if (request.getParameter("type") != null
				&& !request.getParameter("type").equals("")
				&& request.getParameter("type").equals("1"))
			forward = "refundMentByShipping";
		return mapping.findForward(forward);
	}

	/**
	 * 申请退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward applicationRefundMent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transactionForm = (Transaction) form;
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			request.setAttribute("flag", request.getParameter("flag"));
		}
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		if (trans.getStatus() == 4) {
			request.setAttribute("msg", "退款出错,此订单已经关闭!");
			return mapping.findForward("transactionError");
		}
		if (trans.getStatus() == 10) {
			if (transactionForm.getRefundEdit() == null
					&& transactionForm.getRefundEdit().equals("")) {
				request.setAttribute("msg", "退款出错,此订单已经申请过退款!");
				return mapping.findForward("transactionError");
			}
		}
		Transaction transaction = transactionBiz
				.getRefundMentById(transactionForm);
		transactionBiz.sendEmail(transaction);
		request.setAttribute("transaction", transaction);
		return mapping.findForward("refundMentDetail");
	}

	/**
	 * 申请退货
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward applicationRefundShop(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Transaction transactionForm = (Transaction) form;
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			request.setAttribute("flag", request.getParameter("flag"));
		}
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		if (trans.getStatus() == 7) {
			request.setAttribute("msg", "退款出错,此订单已经申请过退货!");
			return mapping.findForward("transactionError");
		}
		Transaction transaction = transactionBiz
				.applicationRefundShop(transactionForm);
		// transactionBiz.sendEmail(transaction);
		request.setAttribute("transaction", transaction);
		return mapping.findForward("refundMentDetail");
	}

	/**
	 * 同意买家的退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward agreeRefundMent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		Transaction transactionForm = (Transaction) form;
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		if (trans.getStatus() == 6) {
			request.setAttribute("msg", "退款错误,此订单已经发货,等待买家确认收货!");
			return mapping.findForward("transactionError");
		}
		if (trans.getStatus() == 11) {
			request.setAttribute("msg", "退款错误,此订单已经完成退款!");
			return mapping.findForward("transactionError");
		}

		Transaction transaction = transactionBiz.agreeRefundMent(
				transactionForm, agent);
		transactionBiz.sendEmail(transaction);
		request.setAttribute("transaction", transaction);
		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	public ActionForward offLineRefundFirst(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		// 是否是证书用户
		if (agent.getValidCertStatus() == 0) {
			return mapping.findForward("accessDeniedCert");
		}
		Transaction transactionForm = (Transaction) form;
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		request.setAttribute("transaction", trans);
		return mapping.findForward("offLineRefund");
	}

	/**
	 * 线下退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward offLineRefund(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		Transaction transactionForm = (Transaction) form;
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		if (trans.getStatus() == 11) {
			request.setAttribute("msg", "退款错误,此订单已经完成退款!");
			return mapping.findForward("transactionError");
		}

		Transaction transaction = transactionBiz.offLineRefund(trans, agent);
		// transactionBiz.sendEmail(transaction);
		request.setAttribute("transaction", transaction);
		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	public ActionForward offLineCreditRefundFirst(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		if (agent.getValidCertStatus() == 0) {
			return mapping.findForward("accessDeniedCert");
		}
		Transaction transactionForm = (Transaction) form;
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		request.setAttribute("transaction", trans);

		return mapping.findForward("offLineCreditRefund");
	}

	/**
	 * 信用支付线下退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward offLineCreditRefund(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		Transaction transactionForm = (Transaction) form;
		Transaction trans = transactionBiz.getTransactionById(transactionForm
				.getTid());
		if (trans.getStatus() == 11) {
			request.setAttribute("msg", "退款错误,此订单已经完成退款!");
			return mapping.findForward("transactionError");
		}

		Transaction transaction = transactionBiz.offLineCreditRefund(trans,
				agent);
		// transactionBiz.sendEmail(transaction);
		request.setAttribute("transaction", transaction);
		return new ActionForward(
				"/transaction/transactionlist.do?thisAction=listTransactions");
	}

	/**
	 * 查看退款详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRefundMentDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			request.setAttribute("flag", request.getParameter("flag"));
		}

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);

		request.setAttribute("transaction", transaction);
		request.setAttribute("agent", agent);
		if (request.getParameter("edit") != null
				&& !request.getParameter("edit").equals("")) { // 判断是否修改退款协议
			request.setAttribute("edit", request.getParameter("edit"));
		}
		return mapping.findForward("refundMentDetail");
	}

	/**
	 * 查看退款详情,买家在卖家未发货之前退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRefundMentBeforeShippingDetail(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String forwar = "refundMentBeforeShippingDetail";
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			if (request.getParameter("flag").equals("3"))
				forwar = "refundMentSuccess";
			request.setAttribute("flag", request.getParameter("flag"));
		}
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);

		request.setAttribute("transaction", transaction);
		request.setAttribute("agent", agent);
		request.setAttribute("fg", request.getParameter("fg"));
		return mapping.findForward(forwar);
	}

	/**
	 * 查看商户卖出的退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTradeSellerRefund(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String forwar = "viewTradeSellerRefund";
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);

		request.setAttribute("transaction", transaction);
		request.setAttribute("agent", agent);
		request.setAttribute("fg", request.getParameter("fg"));
		return mapping.findForward(forwar);
	}

	/**
	 * 查看商户买入的退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTradeBuyerRefund(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = uri.getAgent();
		String forwar = "viewTradeBuyerRefund";
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderid"));
		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);

		request.setAttribute("transaction", transaction);
		request.setAttribute("agent", agent);
		return mapping.findForward(forwar);
	}

	/**
	 * 退款管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refundMentManage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");
		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		transactionListForm.setAgent(agent);
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			// transactionListForm.setBeginDate(null);
			// transactionListForm.setEndDate(null);
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}

		if (transactionListForm.getBeginDate() != null
				&& !transactionListForm.getBeginDate().equals("")
				&& transactionListForm.getEndDate() != null
				&& !transactionListForm.getEndDate().equals("")) {
			if (transactionListForm.getBeginDate().compareTo(
					transactionListForm.getEndDate()) > 0) {
				request.setAttribute("errorDateMessage", "开始日期不能大于结束日期");
				return mapping.findForward("refundMentManage");
			}
		}

		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		agent = uri.getAgent();
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);
		list = transactionBiz.refundMentManage(transactionListForm);
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
		}

		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("agent", agent);

		return mapping.findForward("refundMentManage");
	}

	/**
	 * 商户后台卖出的退款管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSellerTransactionRefundList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");

		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		transactionListForm.setAgent(agent);
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			// transactionListForm.setBeginDate(null);
			// transactionListForm.setEndDate(null);
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}

		if (transactionListForm.getBeginDate() != null
				&& !transactionListForm.getBeginDate().equals("")
				&& transactionListForm.getEndDate() != null
				&& !transactionListForm.getEndDate().equals("")) {
			if (transactionListForm.getBeginDate().compareTo(
					transactionListForm.getEndDate()) > 0) {
				request.setAttribute("errorDateMessage", "开始日期不能大于结束日期");
				return mapping.findForward("listtransaction");
			}
		}

		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		agent = uri.getAgent();
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);
		list = transactionBiz
				.getSellerTransactionRefundList(transactionListForm);
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
		}

		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("agent", agent);

		return mapping.findForward("sellerTransactionRefundList");
	}

	/**
	 * 商户后台买入的退款管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getBuyerTransactionRefundList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Agent agent = new Agent();
		agent = uri.getAgent();

		String agentType = request.getParameter("agentType");
		String orderDetailsType = request.getParameter("orderDetailsType");

		TransactionListForm transactionListForm = (TransactionListForm) form;
		if (transactionListForm == null) {
			transactionListForm = new TransactionListForm();
		}
		transactionListForm.setAgent(agent);
		if (request.getParameter("flag") != null
				&& !request.getParameter("flag").equals("")) {
			// transactionListForm.setBeginDate(null);
			// transactionListForm.setEndDate(null);
			transactionListForm.setSearchOrderNo(null);
			transactionListForm.setSearchOutOrderNo(null);
			transactionListForm.setSearchShopName(null);
			transactionListForm.setIntPage(1);
		}

		if (transactionListForm.getBeginDate() != null
				&& !transactionListForm.getBeginDate().equals("")
				&& transactionListForm.getEndDate() != null
				&& !transactionListForm.getEndDate().equals("")) {
			if (transactionListForm.getBeginDate().compareTo(
					transactionListForm.getEndDate()) > 0) {
				request.setAttribute("errorDateMessage", "开始日期不能大于结束日期");
				return mapping.findForward("listtransaction");
			}
		}

		if (agentType != null && !agentType.equals("")) {
			transactionListForm.setAgentType(Integer.parseInt(agentType));
			request.setAttribute("atype", agentType);
		} else
			transactionListForm.setAgentType(0);

		if (orderDetailsType != null && !orderDetailsType.equals("")) {
			transactionListForm.setOrderDetailsType(new Long(orderDetailsType));
			request.setAttribute("otype", orderDetailsType);
		} else
			transactionListForm.setOrderDetailsType(0);

		agent = uri.getAgent();
		transactionListForm.setAId(agent.getId());
		List list = new ArrayList();
		transactionListForm.setPerPageNum(10);
		list = transactionBiz
				.getBuyerTransactionRefundList(transactionListForm);
		for (int i = 0; i < list.size(); i++) {
			Transaction transaction = (Transaction) list.get(i);
			transaction.setTransactioner(agent);
		}

		transactionListForm.setList(list);
		request.setAttribute("tlf", transactionListForm);
		request.setAttribute("agent", agent);

		return mapping.findForward("buyerTransactionRefundList");
	}

	/**
	 * 卖家确认发货
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward shippingConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Logistics logistics = (Logistics) form;
		long tid = new Long(request.getParameter("tid"));
		long orderid = new Long(request.getParameter("orderId"));

		Transaction transaction = transactionBiz.getTransactionById(tid,
				orderid);
		if (transaction.getStatus() == 4) { // 判断订单是否已经关闭
			request.setAttribute("msg", "发货错误,此订单已经关闭!");
			return mapping.findForward("transactionError");
		}
		if (transaction.getStatus() == 6) { // 判断订单是否已经发货
			request.setAttribute("msg", "发货错误,此订单已经发货!");
			return mapping.findForward("transactionError");
		}
		transactionBiz.shippingConfirm(logistics);
		transactionBiz.sendEmail(tid, logistics);
		request.setAttribute("transaction", transaction);
		return mapping.findForward("shippingConfirm");
	}

	/**
	 * 检查账户是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRegisterTypeByEmail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter pw = response.getWriter();
		Agent agent = new Agent();
		agent = agentBiz.getAgentByName(request.getParameter("agentName"));
		if (agent.getRegisterType() == 0)
			pw.print("0");
		else if (agent.getRegisterType() == 1)
			pw.print("1");
		pw.close();
		return null;
	}

	public void setAgentCoterieBiz(AgentCoterieBiz agentCoterieBiz) {
		this.agentCoterieBiz = agentCoterieBiz;
	}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public void setTransactionBiz(TransactionBiz transactionBiz) {
		this.transactionBiz = transactionBiz;
	}

	public void setBlackscreenBiz(BlackscreenBiz blackscreenBiz) {
		this.blackscreenBiz = blackscreenBiz;
	}
}
