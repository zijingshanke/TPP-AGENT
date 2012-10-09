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
import org.apache.struts.actions.DispatchAction;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentBalance;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.agent.biz.CoterieBiz;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.ConnAnalyseParameter;
import com.fordays.fdpay.cooperate.DirectPayment;
import com.fordays.fdpay.cooperate.DirectRefund;
import com.fordays.fdpay.cooperate.FreezeMessage;
import com.fordays.fdpay.cooperate.FreezeRefund;
import com.fordays.fdpay.cooperate.Freeze_Refund;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.GatewayMessageFactory;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.fordays.fdpay.cooperate.MainTask;
import com.fordays.fdpay.cooperate.biz.AgentBindBiz;
import com.fordays.fdpay.cooperate.biz.CreditBiz;
import com.fordays.fdpay.cooperate.biz.FreezeBiz;
import com.fordays.fdpay.cooperate.biz.GatewayBiz;
import com.fordays.fdpay.system.biz.TaskTimestampBiz;
import com.fordays.fdpay.transaction.OrderDetails;
import com.neza.encrypt.BASE64;
import com.neza.tool.DateUtil;

/**
 * 接口
 * 
 * @author 
 * 
 */
public class FreezeAction extends DispatchAction {
	private GatewayBiz gatewayBiz;
	private AgentBiz agentBiz;
	private CoterieBiz coterieBiz;
	private AgentCoterieBiz agentCoterieBiz;
	private TaskTimestampBiz tasktimestampBiz;
	private LogUtil myLog;
	private CreditBiz creditBiz;
	private FreezeBiz freezeBiz;

	/**
	 * 交易委托冻结接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward air_trade_refund_freeze(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************air_trade_refund_freeze 请求地址: "
				+ request.getRemoteAddr());
		// 获取formbean
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		String url = "";
		String encoding = "GBK";
			
		FreezeMessage freezeMessage = GatewayMessageFactory
				.getFreeMessage(false);
		try {
			if (request.getParameter("_input_charset") != null)
				encoding = request.getParameter("_input_charset");
			// 判断页面提交方式 equalsIgnoreCase:不区分大小写
			if ("get".equalsIgnoreCase(request.getMethod())) {
				// 调用方法获取url(页面用get方式提交)
				url = ConnAnalyseParameter.decode(request.getQueryString(),
						encoding); 
			} else {
				StringBuffer temp = new StringBuffer();
				if (request.getParameter("freeze_details") != null) {
					temp.append("freeze_details=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("freeze_details"), encoding));
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
				if (request.getParameter("trade_no") != null) {
					temp.append("trade_no=");
					temp.append(AnalyseParameter.decode(request
							.getParameter("trade_no"), encoding));
				}
				// 获取url(页面用post方式提交)
				url = temp.toString();
			}

			// 分析URL,放到MAP里面
			map = ConnAnalyseParameter.analyseURL(url);
			System.out.println("air_trade_refund_freeze  url:" + url);
			// 调用方法验证url参数 并返回信息
			String msg = freezeBiz.validate_refund_freeze(map, url,
					freezeMessage);
			System.out.println("验证返回信息是msg：" + msg);
			String isSuccess = "F";
			if (freezeMessage.getSUCCESS().equals(msg)) {
				isSuccess = "T";
			} else {
				response.setContentType("text/xml;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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
				return null;
			}
			// 执行冻结 放回处理结果
			String result = freezeBiz.credit_freeze_order(map, url,
					freezeMessage, freezeBiz);

			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<fdpay>");
			sb.append("<is_success>");
			sb.append(isSuccess);
			sb.append("</is_success>");
			sb.append("<request>");
			sb.append("<param name=\"_input_charset\">");
			sb.append(map.get("_input_charset"));
			sb.append("</param>");
			sb.append("<param name=\"freeze_details\">");
			sb.append(map.get("freeze_details"));
			sb.append("</param>");
			sb.append("<param name=\"notify_url\">");
			sb.append(map.get("notify_url"));
			sb.append("</param>");
			sb.append("<param name=\"partner\">");
			sb.append(map.get("partner"));
			sb.append("</param>");
			sb.append("<param name=\"service\">");
			sb.append(map.get("service"));
			sb.append("</param>");
			sb.append("<param name=\"sign\">");
			sb.append(map.get("sign"));
			sb.append("</param>");
			sb.append("<param name=\"sign_type\">");
			sb.append(map.get("sign_type"));
			sb.append("</param>");
			sb.append("<param name=\"trade_no\">");
			sb.append(map.get("trade_no"));
			sb.append("</param>");
			sb.append("</request>");

			sb.append("<response>");
			sb.append("<airFreeze>");
			sb.append("<freezed_details>");
			sb.append(result);
			sb.append("</freezed_details>");
			sb.append("<trade_no>");
			sb.append(map.get("trade_no"));
			sb.append("</trade_no>");
			sb.append("</airFreeze>");
			sb.append("</response>");
			sb.append("<sign>");
			sb.append(map.get("sign"));
			sb.append("</sign>");
			sb.append("<sign_type>");
			sb.append(map.get("sign_type"));
			sb.append("</sign_type>");
			sb.append("</fdpay>");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
			out.close();
			out = null;

		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error"); // 未知异常
		}
		// System.out.println("url paramets="+returnURL);
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("notify_url") + "?"+ returnURL);
		return null;
	}

	/**
	 * 交易委托冻结查询接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward air_trade_refund_freeze_query(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************:air_trade_refund_freeze_query 请求地址: "
				+ request.getRemoteAddr());
		// 获取formbean
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		String url = "";
		String encoding = "GBK";
		FreezeMessage freezeMessage = GatewayMessageFactory
				.getFreeMessage(false);
		try {
			if (request.getParameter("_input_charset") != null)
				encoding = request.getParameter("_input_charset");
			// 调用方法获取url(页面用get方式提交)
			url = ConnAnalyseParameter.decode(request.getQueryString(),
					encoding);
			map = ConnAnalyseParameter.analyseURL(url);
			System.out.println("air_trade_refund_freeze_query  url:" + url);
			// 调用方法验证url参数 并返回信息
			String msg = freezeBiz.validate_refund_freeze_query(map, url,
					freezeMessage);
			System.out.println("验证返回信息是msg：" + msg);
			String isSuccess = "F";
			if (freezeMessage.getSUCCESS().equals(msg)) {
				isSuccess = "T";
			} else {
				response.setContentType("text/xml;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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
				return null;
			}

			String result = freezeBiz.queryFreeResult(map, freezeMessage);
			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			
			sb.append("<fdpay>");
			sb.append("<is_success>");
			sb.append(isSuccess);
			sb.append("</is_success>");
			sb.append("<request>");
			sb.append("<param name=\"_input_charset\">");
			sb.append(map.get("_input_charset"));
			sb.append("</param>");
			sb.append("<param name=\"freeze_order_no\">");
			sb.append(map.get("freeze_order_no"));
			sb.append("</param>");
			sb.append("<param name=\"partner\">");
			sb.append(map.get("partner"));
			sb.append("</param>");
			sb.append("<param name=\"service\">");
			sb.append(map.get("service"));
			sb.append("</param>");
			sb.append("<param name=\"sign\">");
			sb.append(map.get("sign"));
			sb.append("</param>");
			sb.append("<param name=\"sign_type\">");
			sb.append(map.get("sign_type"));
			sb.append("</param>");
			sb.append("</request>");

			sb.append("<response>");
			sb.append("<airFreeze>");
			sb.append("<freezed_details>");
			sb.append(result);
			sb.append("</freezed_details>");
			sb.append("</airFreeze>");
			sb.append("</response>");
			sb.append("<sign>");
			sb.append(map.get("sign"));
			sb.append("</sign>");
			sb.append("<sign_type>");
			sb.append(map.get("sign_type"));
			sb.append("</sign_type>");
			sb.append("</fdpay>");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
			out.close();
			out = null;

		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error"); // 未知异常
		}
		return null;
	}


	/**
	 * 交易委托解冻结接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward air_trade_refund_unfreeze(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************air_trade_refund_unfreeze请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;

		HashMap<String, String> map = null;
		String url = null;
		FreezeMessage freezeMessage = GatewayMessageFactory
				.getFreeMessage(false);
		try {
			url = AnalyseParameter.decode(request.getQueryString(), request
					.getParameter("_input_charset"));
			// 分析URL,放到MAP里面
			map = AnalyseParameter.analyseURL(url);
			// 调用方法验证请求协议参数
			String msg = freezeBiz.validateProtocolParameters(map, url,
					freezeMessage);
			String isSuccess = "F";
			if (!freezeMessage.getSUCCESS().equals(msg)) {
				//协议参数返回的XML
				response.setContentType("text/xml;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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
			} else {
				//调用方法验证请求业务参数
				msg = freezeBiz.validateOperatingparameters(map, url,
						freezeMessage);
				if(!freezeMessage.getSUCCESS().equals(msg)){
					//业务参数返回的XML
					response.setContentType("text/xml;charset=GBK");
					response.setHeader("Cache-Control", "no-cache");
					StringBuffer sb = new StringBuffer();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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
				}else{
					isSuccess="T";
					HashMap<Integer, String> map1 = freezeBiz.unfreeze(map, url,
							freezeMessage, freezeBiz);
					response.setContentType("text/xml;charset=GBK");
					response.setHeader("Cache-Control", "no-cache");
					StringBuffer sb = new StringBuffer();
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					sb.append("<fdpay>");
					sb.append("<is_success>");
					sb.append(isSuccess);
					sb.append("</is_success>");
					sb.append("<request>");
					sb.append("<param name=\"_input_charset\">");
					sb.append("utf-8");
					sb.append("</param>");
					sb.append("<param name=\"notify_url\">");
					sb.append(map.get("notify_url"));
					sb.append("</param>");
					sb.append("<param name=\"partner\">");
					sb.append(map.get("partner"));
					sb.append("</param>");
					sb.append("<param name=\"service\">");
					sb.append(map.get("service"));
					sb.append("</param>");
					sb.append("<param name=\"sign\">");
					sb.append(map.get("sign"));
					sb.append("</param>");
					sb.append("<param name=\"sign_type\">");
					sb.append("MD5");
					sb.append("</param>");
					sb.append("<param name=\"trade_no\">");
					sb.append(map1.get(1));
					sb.append("</param>");
					sb.append("</request>");
					sb.append("<response>");
					sb.append("<airFreeze>");
					sb.append("<freezed_details>");
					sb.append(map1.get(0));
					sb.append("</freezed_details>");
					sb.append("<trade_no>");
					sb.append(map1.get(1));
					sb.append("</trade_no>");
					sb.append("</airFreeze>");
					sb.append("</response>");
					sb.append("<sign>");
					sb.append(map.get("sign"));
					sb.append("</sign>");
					sb.append("<sign_type>");
					sb.append("MD5");
					sb.append("</sign_type>");
					sb.append("</fdpay>");
					PrintWriter out = response.getWriter();
					out.write(sb.toString());
					out.close();
					out = null;
				}
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");// 未知异常
		}
		return null;
	}

	public ActionForward air_trade_refund_unfreeze_query(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************air_trade_refund_unfreeze_query请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;

		HashMap<String, String> map = null;
		String url = null;
		FreezeMessage freezeMessage = GatewayMessageFactory
				.getFreeMessage(false);
		try {
			url = AnalyseParameter.decode(request.getQueryString(), request
					.getParameter("_input_charset"));
			// 分析URL,放到MAP里面
			map = AnalyseParameter.analyseURL(url);
			// 调用方法验证请求参数
			String msg = freezeBiz.validateAirtradeRefundUnfreezeQuery(map,
					url, freezeMessage);
			String isSuccess = "F";
			if (freezeMessage.getSUCCESS().equals(msg)) {
				isSuccess = "T";
				String map1 = freezeBiz.unfreezeQuery(map, url, freezeMessage,
						freezeBiz);
				StringBuffer sb = new StringBuffer();
				
				response.setContentType("text/xml;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<fdpay>");
				sb.append("<is_success>");
				sb.append(isSuccess);
				sb.append("</is_success>");
				sb.append("<request>");
				sb.append("<param name=\"_input_charset\">");
				sb.append(map.get("_input_charset"));
				sb.append("</param>");
				sb.append("<param name=\"unfreeze_order_no\">");
				sb.append(map.get("unfreeze_order_no"));
				sb.append("</param>");
				sb.append("<param name=\"partner\">");
				sb.append(map.get("partner"));
				sb.append("</param>");
				sb.append("<param name=\"service\">");
				sb.append(map.get("service"));
				sb.append("</param>");
				sb.append("<param name=\"sign\">");
				sb.append(map.get("sign"));
				sb.append("</param>");
				sb.append("<param name=\"sign_type\">");
				sb.append(map.get("sign_type"));
				sb.append("</param>");
				sb.append("</request>");

				sb.append("<response>");
				sb.append("<airUnfreeze>");
				sb.append("<unfreezed_details>");
				sb.append(map1);
				sb.append("</unfreezed_details>");
				sb.append("</airUnfreeze>");
				sb.append("</response>");
				sb.append("<sign>");
				sb.append(map.get("sign"));
				sb.append("</sign>");
				sb.append("<sign_type>");
				sb.append(map.get("sign_type"));
				sb.append("</sign_type>");
				sb.append("</fdpay>");
				PrintWriter out = response.getWriter();
				out.write(sb.toString());
				out.close();
				out = null;
				
			} else {
				response.setContentType("text/xml;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sb.append("<fdpay>");
				sb.append("<request>");
				sb.append("<is_success>");
				sb.append(isSuccess);
				sb.append("</is_success>");
				sb.append("<error>");
				sb.append(msg);
				sb.append("</error>");
				sb.append("</request>");
				sb.append("</fdpay>");
				PrintWriter out = response.getWriter();
				out.write(sb.toString());
				out.close();
				out = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");// 未知异常
		}
		return null;
	}

	
	/**
	 * 招标冻结标款接口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward recruit_freeze(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("***************************:recruit_freeze 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		FreezeMessage freezeMessage = GatewayMessageFactory.getFreeMessage(true);
		String charset = "GBK";
		String msg="";
		HashMap<String, String> map = null;
		Agent partner = null;
		try {
			if(request.getParameter("_input_charset")!=null)
				charset=request.getParameter("_input_charset");
			String url = ConnAnalyseParameter.decode(request.getQueryString(),
					charset);
			map = ConnAnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			System.out.println("recruit_freeze  url："+url);
			msg = freezeBiz.validate_freeze(map, url, freezeMessage); // 验证冻结参数,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			if (!freezeMessage.getSUCCESS().equals(msg)){
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			// set partner info 查询 partner信息
			partner = gatewayBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			actionForm.setUrl(BASE64.encrypt(url)); // BASE64加密URL放到Form
			// 查询订单是否存在,存在的话,把买家的email放到Form里面
			OrderDetails order = gatewayBiz.getOrderDetails(map
					.get("out_trade_no"), map.get("partner"));
			if (order != null && order.getId() > 0) {
				actionForm.setBuyerEmail(order.getBuyerEmail());
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			
			Agent buyeragent = null;
			//这个接口有可能不传买家  但如果传了  则保存下买家的信息
			if(map.get("freeze_email")!=null&&!"".equals(map.get("freeze_email").trim())){
				actionForm.setBuyerEmail(map.get("freeze_email"));
				buyeragent = agentBiz.getAgentByLoginName(map.get("freeze_email"));				
			}
			if(map.get("freeze_id")!=null&&!"".equals(map.get("freeze_id").trim())){
				buyeragent = agentBiz.getAgentById(Long.parseLong(map.get("freeze_id")));
			}
			if(buyeragent!=null)
				actionForm.setSellerInfo(buyeragent); //交易对方是自己
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg("处理请求时发生异常");
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("recruitfreeze");
	}
	
	//冻结标款支付
	public ActionForward recruit_freeze_pay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		String result = null;
		FreezeMessage freezeMessage = GatewayMessageFactory.getFreeMessage(true);
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		String myurl = "";
		String charset = "GBK";
		Agent userAgent=null;
		String msg="";
		HashMap<String, String> map = null;
		try {
			String url = BASE64.dencrypt(actionForm.getUrl()); // 把URL解密
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面	
			if (isTokenValid(request, true)) {
				msg = freezeBiz.validate_freeze(map, url,
						freezeMessage); // 验证支付,成功则返回字符串

				// SUCCESS
				// AnalyseParameter 返回验证不等于
				// SUCCESS的话,则返回错误页面,cooperate/error.jsp
				if (!freezeMessage.getSUCCESS().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
		
				// 查询买家信息,buyerEmail 即由freezepayment.jsp 中手动输入的钱门帐号
				int loginStatus = gatewayBiz.checkAgent(actionForm
						.getBuyerEmail(), actionForm.getPassword());
				if (loginStatus == 1) {
					actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else if(loginStatus == 2) {
					actionForm.setMsg(gatewayMessage.getPassword_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else if (loginStatus == 0) {
					userAgent = gatewayBiz.getAgent(actionForm.getBuyerEmail(),
							actionForm.getPassword());
				}
				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				//调用方法执行修改   返回字符串   
				result = freezeBiz.credit_recruit_freeze_order(map, url,freezeMessage,freezeBiz);
				
				if (result == null) {
					actionForm.setMsg(freezeMessage
							.getRECRUIT_DEFEAT_ERROR());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				
				// 开启线程执行异步通知
				myurl = map.get("notify_url") + "?" + result;
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				directPayment.setUserAgent(userAgent);
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
	 * 招标冻结支付接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_freeze_pay_by_user(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************create_freeze_pay_by_user 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		FreezeMessage freezeMessage = GatewayMessageFactory.getFreeMessage(true);
		try {

			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			System.out.println("create_freeze_pay_by_user  url = "+ url);
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			String msg = freezeBiz.validateFreezePay(map, url, freezeMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			// SUCCESS
			// AnalyseParameter //返回验证不等于 SUCCESS的话,则返回错误页面,cooperate/error.jsp
			// if (!ExceptionConstant.SUCCESS.equals(msg)) {
			if (!freezeMessage.getSUCCESS().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			// *****************reset action form
			// set partner info 查询 partner信息
			partner = gatewayBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			// set seller info 设置卖家信息到Form
			seller = gatewayBiz.getSeller();
			actionForm.setSellerInfo(seller);
			actionForm.setUrl(BASE64.encrypt(url)); // BASE64加密URL放到Form
			// 查询订单是否存在,存在的话,把买家的email放到Form里面
			OrderDetails order = gatewayBiz.getOrderDetails(map
					.get("out_trade_no"), map.get("partner"));
			if (order != null && order.getId() > 0) {
				actionForm.setBuyerEmail(order.getBuyerEmail());
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			
			Agent buyeragent = null;
			//这个接口有可能不传买家  但如果传了  则保存下买家的信息
			if(map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email").trim())){
				actionForm.setBuyerEmail(map.get("buyer_email"));
				buyeragent = agentBiz.getAgentByLoginName(map.get("buyer_email"));
			}
			
			if(map.get("buyer_id")!=null&&!"".equals(map.get("buyer_id").trim())){
				buyeragent = agentBiz.getAgentById(
						Long.parseLong(map.get("buyer_id").trim()));
				actionForm.setBuyerEmail(buyeragent.getLoginName());
				
			}
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(freezeMessage.getUNKNOW_ERROR());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("freezepayment");
	}
	
	/*
	 * 招标冻结执行支付接口
	 */
	public ActionForward recruit_freeze_payment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		System.out.println("---------recruit_freeze_payment url:"+BASE64.dencrypt(actionForm.getUrl()));
		HashMap<String, String> map = null;
		Agent userAgent = null;
		String result = "";
		String myurl = "";
		String charset = "GBK";
		FreezeMessage freezeMessage = GatewayMessageFactory.getFreeMessage(true);
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = BASE64.dencrypt(actionForm.getUrl()); // 把URL解密
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面	
			if (isTokenValid(request, true)) {
				String msg = freezeBiz.validateFreezePay(map, url,
						freezeMessage); // 验证支付,成功则返回字符串

				// SUCCESS
				// AnalyseParameter 返回验证不等于
				// SUCCESS的话,则返回错误页面,cooperate/error.jsp
				if (!freezeMessage.getSUCCESS().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
		
				// 查询买家信息,buyerEmail 即由freezepayment.jsp 中手动输入的钱门帐号
				int loginStatus = gatewayBiz.checkAgent(actionForm
						.getBuyerEmail(), actionForm.getPassword());
				if (loginStatus == 1) {
					actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else if(loginStatus == 2) {
					actionForm.setMsg(gatewayMessage.getPassword_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else if (loginStatus == 0) {
					userAgent = gatewayBiz.getAgent(actionForm.getBuyerEmail(),
							actionForm.getPassword());
				}
				
				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				
				/******** 执行支付 *********/
				result = freezeBiz.freeze_payment(map, userAgent, url, 
						charset, gatewayMessage);
				
				if (result == null) {
					actionForm.setMsg(gatewayMessage
							.getPayment_Royalty_Fee_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				if(result.equals(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough())){
					actionForm.setMsg(gatewayMessage
							.getPayment_Royalty_Fee_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error"); 
				}
				myurl = map.get("notify_url") + "?" + result;
				/****************************/
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				directPayment.setUserAgent(userAgent);
				MainTask.put(directPayment);
				// isSuccess = "T";

			} else {
				// 表单重复提交
				resetToken(request);
				actionForm.setMsg(gatewayMessage.getRepeat_Commit());
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
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
		result = result.replaceAll("trade_status_sync", "trade_status_async");   //替换通知类型
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}
	
	/**
	 * 招标冻结支付无密退款
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward refund_freeze_by_platform_nopwd(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************refund_freeze_by_platform_nopwd 请求地址: "
			+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		String url = "";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
		try {
			String encoding = request.getParameter("_input_charset");
			if ("get".equalsIgnoreCase(request.getMethod())) {
				url = AnalyseParameter.decode(request.getQueryString(),
						encoding);
			} else {
				
				StringBuffer temp = new StringBuffer();
				if (request.getParameter("batch_num") != null) {
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
				url = temp.toString();
			}
			// 分析URL,放到MAP里面
			map = AnalyseParameter.analyseURL(url);
			System.out.println("refund_freeze_by_platform_nopwd url:"+url);
			//调用方法验证请求参数
			String msg = freezeBiz.validateFreezeRefund(map, url, gatewayMessage); 
			System.out.println("验证返回信息是msg："+msg);
			String isSuccess = "F";
			if (gatewayMessage.getSuccess().equals(msg)) {
				isSuccess = "T";
			}

			if ("T".equals(isSuccess)) {
				FreezeRefund freezeRefund = new FreezeRefund(freezeBiz);
				freezeRefund.setEncoding(encoding);
				freezeRefund.setMap(map);
				freezeRefund.setUrl(url);
				MainTask.put(freezeRefund);
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
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");//处理请求时发生异常
		} finally {
			
		}
		// System.out.println("url paramets="+returnURL);
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("notify_url") + "?"+ returnURL);
		return null;
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

	public FreezeBiz getFreezeBiz() {
		return freezeBiz;
	}

	public void setFreezeBiz(FreezeBiz freezeBiz) {
		this.freezeBiz = freezeBiz;
	}

	public CreditBiz getCreditBiz() {
		return creditBiz;
	}

	public void setCreditBiz(CreditBiz creditBiz) {
		this.creditBiz = creditBiz;
	}
	
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
			if (map.containsKey("out_trade_no")) {
				orderDetails = gatewayBiz.getOrderDetails(map
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
			gatewayBiz.saveActionLog(actionLog);
		}
	}
}
