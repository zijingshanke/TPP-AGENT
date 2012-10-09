package com.fordays.fdpay.cooperate.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.core.Block;

import org.apache.poi.hssf.util.HSSFColor.BLACK;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.action.AgentAction;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.agent.biz.CoterieBiz;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.ConnAnalyseParameter;
import com.fordays.fdpay.cooperate.CreditRefund;
import com.fordays.fdpay.cooperate.DirectPayment;
import com.fordays.fdpay.cooperate.DirectRefund;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.GatewayMessageFactory;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.fordays.fdpay.cooperate.MainTask;
import com.fordays.fdpay.cooperate.UtilURL;
import com.fordays.fdpay.cooperate.biz.CreditBiz;
import com.fordays.fdpay.cooperate.biz.GatewayBiz;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.biz.TaskTimestampBiz;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.action.TransactionAction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.base.Constant;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.tool.DateUtil;

/**
 * 接口
 * @author 
 *
 */
public class CreditAction extends DispatchAction {
	private GatewayBiz gatewayBiz;
	private AgentBiz agentBiz;
	private CoterieBiz coterieBiz;
	private AgentCoterieBiz agentCoterieBiz;
	private TaskTimestampBiz tasktimestampBiz;
	private LogUtil myLog;
	private CreditBiz creditBiz;


	/**
	 * 解冻
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward allow_notallow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("***************************CreditAction:allow_notallow 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		String result = "";
		PrintWriter pw = response.getWriter();
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		String myurl = "";
		String charset = "GBK";
		String msg="";
		HashMap<String, String> map = null;
		try {
			
			String url = ConnAnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			map = ConnAnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			System.out.println("allow_notallow  url："+url);
			msg = creditBiz.allowvalidateDirectPay(map, url, gatewayMessage); // 验证解冻参数,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}else {
				//调用方法执行修改   返回字符串
				result = creditBiz.allow_direct_pay(map, url,gatewayMessage);
				myurl = map.get("notify_url") + "?" + result;
				
				if (result == null) {
					actionForm.setMsg(gatewayMessage
							.getPayment_Royalty_Fee_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				directPayment.setUserAgent(new Agent());
				MainTask.put(directPayment);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		String responseResult = HttpInvoker.readContentFromPost(myurl,
				java.net.URLEncoder.encode(result, charset));

		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0) {
			this.savaActionLog(map, responseResult, 1);   //保存日志   操作成功
		} else {
			this.savaActionLog(map, responseResult, 0);  //保存日志   操作没全部成功
		}
		
		result = result.replaceAll("trade_status_sync", "trade_status_async");   //替换通知类型
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();  //注销session
		response.sendRedirect(map.get("return_url") + "?" + result);  //重定向(返回url)地址
		return null;
	}

	
	/**
	 * 信用支付（外买）接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_credit_pay_by_user_out(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************CreditAction:create_credit_pay_by_user_out 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = ConnAnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			System.out.println("create_credit_pay_by_user_out  url:"+url);
			HashMap<String, String> map = ConnAnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			String msg = creditBiz.validateCreditPayout(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			// AnalyseParameter 返回验证不等于 SUCCESS的话,则返回错误页面,cooperate/error.jsp
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			
			Coterie coterie = coterieBiz
				.getCoterieByPartner(map.get("partner"));
			Agent userAgent = null;
			if(map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email")))
				userAgent = agentBiz.getAgentByLoginName(map.get("buyer_email"));
			else
				userAgent = agentBiz.getAgentById(Long.parseLong(map.get("buyer_id")));
			
			if (!agentCoterieBiz.checkCreditExpireDate(userAgent,
					coterie)) {
				// 信用支付有效期已过
				actionForm.setMsg(gatewayMessage
						.getCredit_Payment_Date_Expire());
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			
			partner = creditBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			// set seller info 设置卖家信息到Form
			seller = creditBiz.getSeller();
			actionForm.setSellerInfo(seller);
			actionForm.setUrl(BASE64.encrypt(url)); // BASE64加密URL放到Form
	
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			actionForm.setBuyerEmail(map.get("buyer_email"));  //前面有验证不会为空
			actionForm.setCoterie(coterie);

			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("creditpayout");
	}

	
	/**
	 * 信用支付金额(外买)接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward credit_paymentout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("-------------------跳转页面外买支付");
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent userAgent = null;
		String result = "";
		String myurl = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = BASE64.dencrypt(actionForm.getUrl()); // 把URL解密
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			if(isTokenValid(request, true)) {
				String msg = creditBiz.validateCreditPayout(map, url,
						gatewayMessage); // 验证支付,成功则返回字符串
				
				// AnalyseParameter 返回验证不等于
				// SUCCESS的话,则返回错误页面,cooperate/error.jsp
				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				// 查询买家信息,buyerEmail 即由creditpay.jsp传过来的钱门帐号
				int loginStatus = gatewayBiz.checkAgent(actionForm
						.getBuyerEmail(), actionForm.getPassword());
				if (loginStatus == 1) {
					actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else if (loginStatus == 2) {
					actionForm.setMsg(gatewayMessage.getPassword_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else if (loginStatus == 0) {
					userAgent = gatewayBiz.getAgent(actionForm.getBuyerEmail(),
							actionForm.getPassword());
				}
				 // the user not exists 验证这个买家是否钱门帐号
				 if (userAgent == null || userAgent.getId() == 0) {
					 actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					 request.setAttribute("GatewayForm", actionForm);
					 return mapping.findForward("error");
				 }
				Coterie coterie = coterieBiz.getCoterieByPartner(map
						.get("partner"));
				AgentCoterie ac = agentCoterieBiz.checkAgentCoterie(userAgent,
						coterie);
				if (ac == null) {
					actionForm.setMsg(gatewayMessage.getEmail_Not_Exists());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else {
					if (ac.getStatus() == AgentCoterie.CHANGE_STATUS_0) { // 信用支付已经被禁用
						actionForm.setMsg(gatewayMessage
								.getCredit_Payment_Has_Stop());
						request.setAttribute("GatewayForm", actionForm);
						return mapping.findForward("error");
					} else {
						if (agentCoterieBiz.checkCreditExpireDate(userAgent,
								coterie)) {
							// 判断催还类型,2--表示多笔催还,1--表示逐笔催还
							if (ac.getRepaymentType() == 2){
								// 获取AgentBalance对象（用于获取金额）
								AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
								if (ab.getCreditBalance().compareTo(
										ac.getMinAmount()) < 0) { // 信用余额小于最少限制金额
									actionForm.setMsg(gatewayMessage
											.getCredit_Price_Less_Then());
									request.setAttribute("GatewayForm",
											actionForm);
									return mapping.findForward("error");
								}					
							} else if (ac.getRepaymentType() == 1) {
								List creditPayList = creditBiz.getCreditPayListByAgent(userAgent);	
								if (creditPayList != null && creditPayList.size() > 0) { 
									System.out.println("未还款的信用支付笔数："+creditPayList.size());
									actionForm.setMsg(gatewayMessage.getCredit_Not_Repayment());
									request.setAttribute("GatewayForm",actionForm);
									return mapping.findForward("error");
								}
							}
						} else {
							// 信用支付有效期已过
							actionForm.setMsg(gatewayMessage
									.getCredit_Payment_Date_Expire());
							request.setAttribute("GatewayForm", actionForm);
							return mapping.findForward("error");
						}
					}
				}
				BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
				// 判断买家是否有钱,或者是否够钱付款
				
				AgentBalance ab = agentBiz.getAgentBalance(userAgent.getId());
				if (ab.getCreditBalance() == null
						|| !Gateway.equals2(ab.getCreditBalance(),
								totalFee)) {

					actionForm.setMsg(gatewayMessage
							.getCredit_Account_Balance_Not_Enough());
					request.setAttribute("GatewayForm", actionForm);
					saveToken(request);
					// 不够钱付款,就转到错误页面
					return mapping.findForward("error");
				}

				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				// 调用外买处理方法
				result = creditBiz.credit_paymentout(map, userAgent, url,
						charset, false, gatewayMessage);
				if (result == null) {
					actionForm.setMsg(gatewayMessage
							.getPayment_Royalty_Fee_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				myurl = map.get("notify_url") + "?" + result;
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				directPayment.setUserAgent(userAgent);
				MainTask.put(directPayment);
			} else {
				// 表单重复提交
				resetToken(request);
				actionForm.setMsg(gatewayMessage.getRepeat_Commit());
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("return_url") + "?" + result);

		String responseResult = HttpInvoker.readContentFromPost(myurl,
				java.net.URLEncoder.encode(result, charset));
		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0) {
			this.savaActionLog(map, responseResult, 1);
		} else {
			this.savaActionLog(map, responseResult, 0);
		}
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}
	/**
	 * 信用支付(外买)退款
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward credit_refund_by_platform_out(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************CreditAction:credit_refund_by_platform_out 请求地址: "
				+ request.getRemoteAddr());
		//获取formbean
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		String url = "";
		String encoding = "GBK";
		//创建message对象    这里传false  创建的是gatewayMessageEn对象
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
		try {
			if (request.getParameter("_input_charset") != null) 
				encoding = request.getParameter("_input_charset");
			//判断页面提交方式    equalsIgnoreCase:不区分大小写
			if ("get".equalsIgnoreCase(request.getMethod())) {
				//调用方法获取url(页面用get方式提交)
				url = ConnAnalyseParameter.decode(request.getQueryString(),
						encoding);
			} else {
				StringBuffer temp = new StringBuffer();
				if (request.getParameter("batch_num")!=null) {
					temp.append("batch_num=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("batch_num"), encoding));
					temp.append("&");
				}
				if (request.getParameter("batch_no") != null) {
					temp.append("batch_no=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("batch_no"), encoding));
					temp.append("&");
				}
				if (request.getParameter("detail_data") != null) {
					temp.append("detail_data=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("detail_data"), encoding));
					temp.append("&");
				}
				if (request.getParameter("notify_url") != null) {
					temp.append("notify_url=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("notify_url"), encoding));
					temp.append("&");
				}
				if (request.getParameter("partner") != null) {
					temp.append("partner=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("partner"), encoding));
					temp.append("&");
				}
				if (request.getParameter("refund_date") != null) {
					temp.append("refund_date=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("refund_date"), encoding));
					temp.append("&");
				}
				if (request.getParameter("refund_type") != null) {
					temp.append("refund_type=");
					temp.append(AnalyseParameter.decode(request
						.getParameter("refund_type"), encoding));
					temp.append("&");
				}
				if (request.getParameter("service") != null) {
					temp.append("service=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("service"), encoding));
					temp.append("&");
				}
				if (request.getParameter("sign") != null) {
					temp.append("sign=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("sign"), encoding));
					temp.append("&");
				}
				if (request.getParameter("sign_type") != null) {
					temp.append("sign_type=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("sign_type"), encoding));
				}
				//获取url(页面用post方式提交)
				url = temp.toString();
			}
			
			// 分析URL,放到MAP里面
			map = ConnAnalyseParameter.analyseURL(url);
			System.out.println("credit_refund_by_platform_out  url:"+url);
			//调用方法验证url参数 并返回信息
			String msg = creditBiz.credit_validateRefundout(map, url, gatewayMessage);
			System.out.println("验证返回信息是msg："+msg);
			String isSuccess = "F";
			if (gatewayMessage.getSuccess().equals(msg)) {
				isSuccess = "T";
			}
			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?> ");
			sb.append("<fdpay>");
			sb.append("<is_success>");
			sb.append(isSuccess);
			sb.append("</is_success>");
			sb.append("<error>");
			sb.append(msg);
			sb.append("</error>");
			sb.append("</fdpay>");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
			out.close();
			out = null;
			if(isSuccess.equals("T")){
				//调用方法执行退款
				String result = creditBiz.credit_refund_out(map, url, gatewayMessage);
				//调用异步通知 MainTask.put(directPayment);			
				String myurl = map.get("notify_url") + "?" + result;
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(encoding);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				//directPayment.setUserAgent(userAgent);
				MainTask.put(directPayment);			
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error"); //未知异常
		}
		// System.out.println("url paramets="+returnURL);
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("notify_url") + "?"+ returnURL);
		return null;
	}
	
	
	
	
	
	/**
	 * 激活/禁用圈员交易类型
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward update_transaction_scope(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************CreditAction:update_transaction_scope 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = creditBiz.validate_tran_scope(map, url, gatewayMessage);
			Agent agent = null;

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {
				agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
				Coterie coterie = coterieBiz.getCoterieByPartner(map
						.get("partner"));
				if (agent == null) { // 不存在本商户钱门帐号
					pw.print("0");
				} else {
					AgentCoterie ac = agentCoterieBiz.checkAgentCoterie(agent,
							coterie);
					if (ac == null) {
						pw.print("1");
					} else {
						ac.setTransactionScope(new Long(map.get("transaction_scope")));
						agentCoterieBiz.updateRepaymentRule(ac);
						pw.print("2");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("3");
		}
		return null;
	}
	
	/**
	 * 保存日志
	 * @param map
	 * @param result
	 * @param status
	 * @throws Exception
	 */
	private void savaActionLog(HashMap<String, String> map, String result,
			int status) throws Exception {
		if (status == 1) {
			OrderDetails orderDetails = new OrderDetails();
			if (map.containsKey("out_trade_no")) {
				orderDetails = gatewayBiz.getOrderDetails(map
						.get("out_trade_no"), map.get("partner"));
			} else {
				orderDetails = null;
			}
			ActionLog actionLog = new ActionLog();
			actionLog.setStatus(new Long(1)); // 1为成功
			actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
			actionLog.setContent(result);
			actionLog.setOrderDetails(orderDetails);
			actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
			gatewayBiz.saveActionLog(actionLog);
		} else if (status == 0) {
			OrderDetails orderDetails = new OrderDetails();
			if (map.containsKey("out_trade_no")){
				orderDetails = gatewayBiz.getOrderDetails(map
						.get("out_trade_no"), map.get("partner"));
			} else {
				orderDetails = null;
			}
			
			ActionLog actionLog = new ActionLog();
			actionLog.setStatus(new Long(0)); //0为失败
			actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
			actionLog.setContent("失败,原因是: " + result);
			if(orderDetails!=null)
			actionLog.setOrderDetails(orderDetails);
			actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
			gatewayBiz.saveActionLog(actionLog);
		}
	}
	
	public GatewayBiz getGatewayBiz() {
		return gatewayBiz;
	}
	public void setGatewayBiz(GatewayBiz gatewayBiz) {
		this.gatewayBiz = gatewayBiz;
	}
	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
	public void setCoterieBiz(CoterieBiz coterieBiz) {
		this.coterieBiz = coterieBiz;
	}
	public void setAgentCoterieBiz(AgentCoterieBiz agentCoterieBiz) {
		this.agentCoterieBiz = agentCoterieBiz;
	}
	public void setTasktimestampBiz(TaskTimestampBiz tasktimestampBiz) {
		this.tasktimestampBiz = tasktimestampBiz;
	}
	public CreditBiz getcreditBiz() {
		return creditBiz;
	}
	public void setcreditBiz(CreditBiz creditBiz) {
		this.creditBiz = creditBiz;
	}
}
