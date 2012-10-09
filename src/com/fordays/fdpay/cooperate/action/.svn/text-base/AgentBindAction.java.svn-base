package com.fordays.fdpay.cooperate.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.AgentBind;
import com.fordays.fdpay.agent.AgentCoterie;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.agent.biz.CoterieBiz;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.DirectCoteriePayment;
import com.fordays.fdpay.cooperate.DirectCoterieRefund;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.GatewayMessageFactory;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.fordays.fdpay.cooperate.MainTask;
import com.fordays.fdpay.cooperate.biz.AgentBindBiz;
import com.fordays.fdpay.transaction.OrderDetails;
import com.neza.base.BaseAction;
import com.neza.encrypt.BASE64;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AgentBindAction extends BaseAction {
	private AgentBiz agentBiz;
	private CoterieBiz coterieBiz;
	private AgentCoterieBiz agentCoterieBiz;
	private AgentBindBiz agentBindBiz;

	/**
	 * 建立帐号关系圈接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_account_number(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		AgentBind agentBind = (AgentBind) form;
		PrintWriter pw = response.getWriter();
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = agentBindBiz.validateAgentBind(map, url,
					gatewayMessage);
			Agent agent;
			Agent tempAgent;

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				agentBind.setMsg(msg);
				request.setAttribute("GatewayForm", agentBind);
				return mapping.findForward("error");
			} else {
				// 验证主帐号
				agent = agentBiz.checkAgentByEmail(map.get("agent_email"));
				// 验证绑定帐号
				tempAgent = agentBiz.checkAgentByEmail(map
						.get("bind_agent_email"));
				// 根据ID查询是否存在
				AgentBind agen = agentBindBiz.queryAgentBindByAgentId(agent
						.getId(), tempAgent.getId());
				if (agent.getId() == 0) {// 不存在主帐号
					pw.print("0");
				} else if (tempAgent.getId() == 0) {// 不存在绑定帐号
					pw.print("1");
				} else if (agen != null) {// 已经绑定帐号
					pw.print("2");
				} else {
					AgentBind ab = new AgentBind();
					ab.setAgentId(agent.getId());
					ab.setBindAgentId(tempAgent.getId());
					ab.setCreateDate(new Timestamp(System.currentTimeMillis()));
					agentBindBiz.save(ab);
					pw.print("3");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 删除帐号关系圈接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward delete_account_number(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		System.out.println("------------ok---");
		AgentBind agentBind = (AgentBind) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = agentBindBiz.validateAgentBind(map, url,
					gatewayMessage);
			Agent agent;
			Agent tempAgent;

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				agentBind.setMsg(msg);
				request.setAttribute("GatewayForm", agentBind);
				return mapping.findForward("error");
			} else {
				// 验证主帐号
				agent = agentBiz.checkAgentByEmail(map.get("agent_email"));
				// 验证绑定帐号
				tempAgent = agentBiz.checkAgentByEmail(map
						.get("bind_agent_email"));
				// 根据ID查询是否存在
				AgentBind agen = agentBindBiz.queryAgentBindByAgentId(agent
						.getId(), tempAgent.getId());
				if (agent.getId() == 0) {// 不存在主帐号
					pw.print("0");
				} else if (tempAgent.getId() == 0) {// 不存在绑定帐号
					pw.print("1");
				} else if (agen == null) {// 没有绑定
					pw.print("2");
				} else {
					agentBindBiz.deleteById(agen.getId());
					pw.print("3");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 自动注册钱门帐号密码接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward update_register_password(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		AgentBind agentBind = (AgentBind) form;
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = agentBindBiz.validateUpdateRegisterPassword(map, url,
					gatewayMessage);
			// Agent agent = null;

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				agentBind.setMsg(msg);
				request.setAttribute("GatewayForm", agentBind);
				return mapping.findForward("error");
			} else {
				System.out.println(map.get("agent_email"));
				Agent agent = agentBiz.getAgentByLoginName(map
						.get("agent_email"));
				request.setAttribute("agent", agent);
				return mapping.findForward("updatepassword");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("0");// 未知异常
		}
		return null;
	}

	/**
	 * 圈中圈即时到账接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_coterie_direct_pay_by_user(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AgentBind agentBind = (AgentBind) form;
		System.out.println("******************create_coterie_direct_pay_by_user请求地址"+request.getRemoteAddr());
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			// validate request
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			System.out.println("create_coterie_direct_pay_by_user url "+ url);
			String msg=agentBindBiz.validateInfo(map);//验证四个邮箱
			System.out.println("返回信息是："+msg);
			if (!msg.equals("Success")) {
				agentBind.setMsg(msg);
				request.setAttribute("GatewayForm", agentBind);
				return mapping.findForward("error");
			} else {
				String msg1 = agentBindBiz.validateDirectPay(map, url,
						gatewayMessage); // 验证支付,成功则返回字符串
				if (!gatewayMessage.getSuccess().equals(msg1)) {
					agentBind.setMsg(msg1);
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				} else {
					Coterie coterieName=agentBindBiz.getByCoterie(map.get("partner"));
					partner = agentBindBiz.getPartnerAccount(map.get("partner"));
					agentBind.setPartnerInfo(partner);

					agentBind.setSellerInfo(seller);
					agentBind.setUrl(BASE64.encrypt(url)); // BASE64加密URL放到Form
					// 查询订单是否存在,存在的话,把买家的email放到Form里面
//					OrderDetails order = agentBindBiz.getOrderDetails(map
//							.get("out_trade_no"), map.get("partner"));
//					if (order != null && order.getId() > 0) {
//						agentBind.setBuyerEmail(order.getBuyerEmail());
//					}
					agentBind.setSubject(map.get("subject"));
					agentBind.setBody(map.get("body"));
					agentBind.setTotalFee(map.get("total_fee"));
					// 显示买家所在圈的圈名

					request.setAttribute("buyerEmail", map.get("buyer_email"));
					request.setAttribute("cName", coterieName.getName());
					request.setAttribute("agentBind", agentBind);
					saveToken(request);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			agentBind.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("agentBind", agentBind);
			return mapping.findForward("error");
		}
		return mapping.findForward("pengateway");
	}

	/**
	 * 买家输入钱门账号到这个方法付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward payment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AgentBind agentBind = (AgentBind) form;
		HashMap<String, String> map = null;
		Agent userAgent = null;
		Agent payAgent = null;
		String result = "";
		String myurl = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = BASE64.dencrypt(agentBind.getUrl()); // 把URL解密
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			System.out.println("----------圈中圈payment url "+ url);

			if (isTokenValid(request, true)) {
				String msg = agentBindBiz.validateDirectPay(map, url,
						gatewayMessage); // 验证支付,成功则返回字符串
				if (!gatewayMessage.getSuccess().equals(msg)) {
					agentBind.setMsg(msg);
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				}
				// 查询买家信息,buyerEmail 即由pengateway.jsp 中hidden压入的钱门帐号
				int loginStatus = agentBindBiz.checkAgent(agentBind
						.getBuyerEmail(), agentBind.getPassword());
				if (loginStatus == 1) {
					agentBind.setMsg(gatewayMessage.getAccount_Not_Exists());
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				} else if (loginStatus == 2) {
					agentBind.setMsg(gatewayMessage.getPassword_Error());
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				} else if (loginStatus == 0) {
					userAgent = agentBindBiz.getAgent(
							agentBind.getBuyerEmail(), agentBind.getPassword());
					System.out.println("****************userAgent.getEmail():"
							+ userAgent.getEmail());
				}
				
				//-------------------------------------------------------
				//买家所在圈
				Coterie coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
				//所在圈中商户
				AgentCoterie buyerAC = agentCoterieBiz.checkAgentCoterie(userAgent,coterie);
				if (buyerAC == null) {
					agentBind.setMsg(gatewayMessage.getEmail_Not_Exists());
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				}else{
					if (buyerAC.getStatus() == AgentCoterie.CHANGE_STATUS_0) { // 信用支付已经被禁用
						agentBind.setMsg(gatewayMessage.getCredit_Payment_Has_Stop());
						request.setAttribute("GatewayForm", agentBind);
						return mapping.findForward("error");
					}else{
						if (agentCoterieBiz.checkCreditExpireDate(userAgent,coterie)) {
							// 判断催还类型,2--表示多笔催还,1--表示逐笔催还
							System.out.println(buyerAC.getMinAmount());
							// 获取AgentBalance对象（用于获取金额）
							AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
							if (buyerAC.getRepaymentType() == 2) {
								if (ab.getCreditBalance().compareTo(
										buyerAC.getMinAmount()) < 0) { // 信用余额小于最少限制金额
									agentBind.setMsg(gatewayMessage
											.getCredit_Price_Less_Then());
									request.setAttribute("GatewayForm",agentBind);
									return mapping.findForward("error");
								}
							} else if (buyerAC.getRepaymentType() == 1) {
								List creditPayList = agentBindBiz.getCreditPayListByAgent(userAgent);	
								if (creditPayList != null && creditPayList.size() > 0) { 
										System.out.println("未还款的信用支付笔数："+creditPayList.size());
										agentBind.setMsg(gatewayMessage.getCredit_Not_Repayment());
										agentBind.setMsg(gatewayMessage
												.getNeed_First_Repayment());
										request.setAttribute("GatewayForm",agentBind);
										return mapping.findForward("error");
									}
								}
							
						} else {
							// 信用支付有效期已过
							agentBind.setMsg(gatewayMessage
									.getCredit_Payment_Date_Expire());
							request.setAttribute("GatewayForm", agentBind);
							return mapping.findForward("error");
						}
					}
				}
				
				//---------------------------------------------
//				//判断完买家还款方式后，在判断圈主次账号还款方式
				payAgent = agentBindBiz.getAgentByEmail(map.get("pay_email"));
				BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
				// 判断买家是否有钱,或者是否够钱付款
				// 同步信用金额，全部用信用金额
				// getAllowBalance()
				AgentBalance ab=agentBiz.getAgentBalance(userAgent.getId());
				System.out.println("买家同步信用金额:" + ab.getCreditBalance());
				if (ab.getCreditBalance() == null
						|| !Gateway.equals(ab.getCreditBalance(),
								totalFee)) {

					// 上面验证买家的钱够不够，如果差0.2以下，可以认为钱是够的。
					// 验证订单是否存在
//					OrderDetails orderDetails = agentBindBiz.getOrderDetails(
//							map.get("out_trade_no"), map.get("partner"));
					OrderDetails orderDetails = agentBindBiz
						.queryOrderDetailByOrderNoAndPartner(map
							.get("out_trade_no"));
					// 如果订单不存在,先加入一条订单记录到数据库
					if (orderDetails == null || orderDetails.getId() < 1) {
						// create the order details if this order not exist
						String strSysDate = DateUtil
								.getDateString("yyyy-MM-dd HH:mm:ss");
						// get system date

						orderDetails = agentBindBiz.appendOrder(map, userAgent,
								url, false,OrderDetails.BUY_TYPE_5);
						
					}
					// set order details
					agentBind.setOrderDetail(orderDetails);
					// set user info
					agentBind.setBuyerInfo(userAgent);

					// set partner info
					Agent partner = agentBindBiz.getPartnerAccount(map
							.get("partner"));
					agentBind.setPartnerInfo(partner);
					// set seller info
					Agent seller = agentBindBiz.getSeller();
					agentBind.setSellerInfo(seller);

					agentBind.setSubject(map.get("subject"));
					agentBind.setBody(map.get("body"));
					agentBind.setTotalFee(map.get("total_fee"));

					request.setAttribute("GatewayForm", agentBind);
					saveToken(request);
					// 不够钱付款,转到银行付款页面
					agentBind.setMsg("信用金额不足!");
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				}
				/** *********** */
				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				// 分润

				/** ****************************** */
				// 判断圈主次账号金额够不够
				//payAgent = agentBindBiz.getAgentByEmail(map.get("pay_email"));
				AgentBalance ab1=agentBiz.getAgentBalance(payAgent.getId());
				System.out.println("圈主次账号同步信用金额:" + ab1.getCreditBalance());
				BigDecimal payFee = new BigDecimal(map.get("pay_fee"));
				if (ab1.getCreditBalance() == null
						|| !Gateway.equals(ab1.getCreditBalance(),payFee)) {
					agentBind.setMsg("圈主次账号的信用额不足");
					request.setAttribute("GatewayForm", agentBind);
					return mapping.findForward("error");
				} else {
					result = agentBindBiz.payment(map, userAgent, url, charset,
							false, gatewayMessage);
					//分润失败
					if (result == null) {
						agentBind.setMsg(gatewayMessage
								.getLess_than_the_amount_of_credit());
						request.setAttribute("GatewayForm", agentBind);
						return mapping.findForward("error");
					}
					if (result.equals(gatewayMessage
							.getTxn_Result_Account_Balance_Not_Enough())) {
						agentBind.setMsg(gatewayMessage
								.getLess_than_the_amount_of_credit());
						request.setAttribute("GatewayForm", agentBind);
						return mapping.findForward("error");
					}
					myurl = map.get("notify_url") + "?" + result;
					/** ************************* */
					DirectCoteriePayment directPayment = new DirectCoteriePayment(
							agentBindBiz, result);
					directPayment.setEncoding(charset);
					directPayment.setMap(map);

					System.out.println("notify url:" + myurl);

					directPayment.setUrl(myurl);
					directPayment.setUserAgent(userAgent);
					MainTask.put(directPayment);
				}

			} else {
				// 表单重复提交
				resetToken(request);
				agentBind.setMsg(gatewayMessage.getRepeat_Commit());
				request.setAttribute("GatewayForm", agentBind);
				return mapping.findForward("error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			agentBind.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", agentBind);
			return mapping.findForward("error");
		}
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("return_url") + "?" + result);

		// --
		System.out.println("----123");
		System.out.println(myurl);
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

	private void savaActionLog(HashMap<String, String> map, String result,
			int status) throws Exception {
		if (status == 1) {
			OrderDetails orderDetails = new OrderDetails();
			if (map.containsKey("out_trade_no")) {
				orderDetails = agentBindBiz.getOrderDetails(map
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
			agentBindBiz.saveActionLog(actionLog);
		} else if (status == 0) {
			OrderDetails orderDetails = new OrderDetails();
			if (map.containsKey("out_trade_no")) {
				orderDetails = agentBindBiz.getOrderDetails(map
						.get("out_trade_no"), map.get("partner"));
			} else {
				orderDetails = null;
			}
			ActionLog actionLog = new ActionLog();
			actionLog.setStatus(new Long(0)); // 0为失败
			actionLog.setLogDate(new Timestamp(System.currentTimeMillis()));
			actionLog.setContent("失败,原因是: " + result);
			actionLog.setOrderDetails(orderDetails);
			actionLog.setLogType(ActionLog.LOG_TYPE_PAY_RETURN);
			agentBindBiz.saveActionLog(actionLog);
		}
	}

	/**
	 * 圈中圈即时退款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward coterie_refund_fastpay_by_platform_nopwd(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************coterie_refund_fastpay_by_platform_nopwd 请求地址: "
				+ request.getRemoteAddr());
		AgentBind agentBind = (AgentBind) form;
		HashMap<String, String> map = null;
		String url = "";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
		try {
			String encoding = request.getParameter("_input_charset");
			if ("get".equalsIgnoreCase(request.getMethod())) {
				url = AnalyseParameter.decode(request.getQueryString(),
						encoding);
			}
			// 分析URL,放到MAP里面
			map = AnalyseParameter.analyseURL(url);
			// 调用方法验证请求参数
			String msg = agentBindBiz.validateRefund(map, url, gatewayMessage);
			String isSuccess = "F";
			if (gatewayMessage.getSuccess().equals(msg)) {
				isSuccess = "T";
			}
			
			if ("T".equals(isSuccess)) {
				DirectCoterieRefund directRefund = new DirectCoterieRefund(
						agentBindBiz);
				directRefund.setEncoding(encoding);
				directRefund.setMap(map);
				directRefund.setUrl(url);
				MainTask.put(directRefund);
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
		} catch (Exception ex) {
			ex.printStackTrace();
			agentBind.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", agentBind);
			return mapping.findForward("error");// 未知异常
		}
		return null;
	}
	
	public void setAgentCoterieBiz(AgentCoterieBiz agentCoterieBiz) {
		this.agentCoterieBiz = agentCoterieBiz;
	}

	public void setCoterieBiz(CoterieBiz coterieBiz) {
		this.coterieBiz = coterieBiz;
	}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public void setAgentBindBiz(AgentBindBiz agentBindBiz) {
		this.agentBindBiz = agentBindBiz;
	}

}
