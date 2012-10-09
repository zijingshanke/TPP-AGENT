package com.fordays.fdpay.cooperate.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.fordays.fdpay.agent.CertInfo;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.action.AgentAction;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.agent.biz.AgentCoterieBiz;
import com.fordays.fdpay.agent.biz.CoterieBiz;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.cooperate.ActionLog;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.CreditRefund;
import com.fordays.fdpay.cooperate.DirectPayment;
import com.fordays.fdpay.cooperate.DirectRefund;
import com.fordays.fdpay.cooperate.GatewayMessage;
import com.fordays.fdpay.cooperate.GatewayMessageFactory;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.fordays.fdpay.cooperate.MainTask;
import com.fordays.fdpay.cooperate.UtilURL;
import com.fordays.fdpay.cooperate.biz.GatewayBiz;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.biz.TaskTimestampBiz;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.action.TransactionAction;
import com.neza.base.Constant;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.tool.DateUtil;
import com.neza.tool.SSLURLUtil;

public class GatewayAction extends DispatchAction {
	private GatewayBiz gatewayBiz;
	private AgentBiz agentBiz;
	private CoterieBiz coterieBiz;
	private AgentCoterieBiz agentCoterieBiz;
	private TaskTimestampBiz tasktimestampBiz;
	private LogUtil myLog;
	
	/* 
	 * 获取用户信息验证证书
	 */
	public ActionForward validCert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		GatewayForm actionForm = (GatewayForm) form;
		String email=request.getParameter("email");
		PrintWriter p;
		System.out.println("获取用户信息用于证书验证");
		try {
			p = response.getWriter();
			//System.out.println("根据email:"+email+"查找Agent");
			Agent valiagent = agentBiz.getAgentByLoginName(email);
			StringBuffer sb = new StringBuffer();
			if(valiagent!=null){   //获取用户的余额，证书状态，序列号等信息
				AgentBalance ab = agentBiz.getAgentBalance(valiagent.getId());
				CertInfo ci = null;
				if(null!=valiagent.getCertInfo()&&valiagent.getCertInfo().getId()>0){
					ci =  agentBiz.getCertById(valiagent.getCertInfo().getId());
				}
				String serNo="";
				if(ci!=null&&ci.getId()>0&&ci.getStatus()==1){
					serNo = ci.getSerialNo();  
					System.out.println("证书编号:"+ci.getId());
				}
				String allowBalance = ab.getAllowBalance().toString();
				String certStatus = valiagent.getCertStatus().toString();
				sb.append(allowBalance);
				sb.append(",");
				sb.append(serNo);
				sb.append(",");
				sb.append(certStatus);
				System.out.println("证书序列号serNo:"+serNo+"  certStatus:"+certStatus+"  allowBalance:"+allowBalance);
				//System.out.println("获取验证证书的用户信息="+sb.toString());
				p.write(sb.toString());
			}else{
				System.out.println("该钱门账户不存在！");
				p.write("该钱门账户不存在！");
			}  
			p.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			
			try{
				System.err.println("获取用户信息用于证书验证失败");
				response.getWriter().write("程序异常");
				response.getWriter().close();
			}
			catch(Exception ec){
				ec.printStackTrace();
			}
		}

		return null;
	}
	
	/*
	 * 即时到账（无钱门）接口
	 */
	public ActionForward create_direct_pay_by_user_no_qm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************create_direct_pay_by_user_no_qm 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
		
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			System.out.println("create_direct_pay_by_user_no_qm  url="+ url);
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面	
			String msg = gatewayBiz.validateDirectPay(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("errornoqm");
			}
			/*****************/
			//调用验证买家
			msg = gatewayBiz.validateDirectPay_buyer(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("errornoqm");
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
			if (order != null && order.getId() > 0){
				actionForm.setBuyerEmail(order.getBuyerEmail());
			}
			
			//保存买家email
			Agent buyeragent=null;
			if (map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email").trim())){
				buyeragent = agentBiz.getAgentByLoginName(map.get("buyer_email"));
				request.setAttribute("buyeremailnoqm", map.get("buyer_email"));
			}	
			if (map.get("buyer_id")!=null&&!"".equals(map.get("buyer_id").trim())){
				buyeragent = agentBiz.getAgentById(Long.parseLong(map.get("buyer_id")));
				request.setAttribute("buyeremailnoqm", buyeragent.getEmail());
			}
			
			if(null!=buyeragent){
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab =agentBiz.getAgentBalance(buyeragent.getId());
				if( ab.getAllowBalance().compareTo(new BigDecimal(map.get("total_fee")))==-1){
					actionForm.setRequestType("master");  //买家钱不够
				}
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("errornoqm");
		}
		return mapping.findForward("gatewaynoqm");
	}

	/**
	 * 即时到账接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_direct_pay_by_user(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************create_direct_pay_by_user 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);//设置返回的错误码信息是中文还是英文
		try {

			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			System.out.println("create_direct_pay_by_user  url = "+ url);
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			String msg = gatewayBiz.validateDirectPay(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			// SUCCESS
			// AnalyseParameter //返回验证不等于 SUCCESS的话,则返回错误页面,cooperate/error.jsp
			// if (!ExceptionConstant.SUCCESS.equals(msg)) {
			if (!gatewayMessage.getSuccess().equals(msg)) {
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
			
			if(null!=buyeragent){
				// 获取AgentBalance对象（用于获取金额）
				AgentBalance ab =agentBiz.getAgentBalance(buyeragent.getId());
				if( ab.getAllowBalance().compareTo(new BigDecimal(map.get("total_fee")))==-1){
					actionForm.setRequestType("master");  //买家钱不够
				}
			}
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("gateway");
	}
	
	
	/**
	 * 买家输入钱门账号到这个方法付款
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
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent userAgent = null;
		String result = "";
		String myurl = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = BASE64.dencrypt(actionForm.getUrl()); // 把URL解密
			System.out
					.println(">>>>>>>>>>payment 方法,url = "
							+ url);
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			if (isTokenValid(request, true)) {
				String msg = gatewayBiz.validateDirectPay(map, url,
					gatewayMessage); // 验证支付,成功则返回字符串
				// SUCCESS
				// AnalyseParameter 返回验证不等于
				// SUCCESS的话,则返回错误页面,cooperate/error.jsp
				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				
				// 查询买家信息,buyerEmail 即由gateway.jsp 中手动输入的钱门帐号
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
				 // the user not exists 验证这个买家是否钱门帐号
				 if (userAgent == null || userAgent.getId() == 0) {
					 actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					 request.setAttribute("GatewayForm", actionForm);
					 return mapping.findForward("error");
				 }
				BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
				// 判断买家是否有钱,或者是否够钱付款
				AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
				System.out.println("同步金额:"+ab.getAllowBalance());
				if (ab.getAllowBalance() == null
						|| !Gateway.equals(ab.getAllowBalance(),
								totalFee)) {
					// 上面验证买家的钱够不够，如果差0.2以下，可以认为钱是够的。
					// 验证订单是否存在
					OrderDetails orderDetails = gatewayBiz.getOrderDetails(map
							.get("out_trade_no"), map.get("partner"));
					// 如果订单不存在,先加入一条订单记录到数据库
					if (orderDetails == null || orderDetails.getId() < 1) {
						// create the order details if this order not exist
						String strSysDate = DateUtil
							.getDateString("yyyy-MM-dd HH:mm:ss");
						// get system date

						orderDetails = gatewayBiz.appendOrder(map, userAgent,
							url, false);
					}
					
					// set order details
					actionForm.setOrderDetail(orderDetails);
					// set user info
					actionForm.setBuyerInfo(userAgent);

					// set partner info
					Agent partner = gatewayBiz.getPartnerAccount(map
						.get("partner"));
					actionForm.setPartnerInfo(partner);
					// set seller info
					Agent seller = gatewayBiz.getSeller();
					actionForm.setSellerInfo(seller);

					actionForm.setSubject(map.get("subject"));
					actionForm.setBody(map.get("body"));
					actionForm.setTotalFee(map.get("total_fee"));

					request.setAttribute("GatewayForm", actionForm);
					saveToken(request);
					// 不够钱付款,转到银行付款页面
					return mapping.findForward("directPayByAccount");
				}
				/**************/
				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				// 分润
			//	map.remove("payment_type");
			//	map.put("payment_type", "1");
				
				/*********************************/
				result = gatewayBiz.payment(map, userAgent, url, charset,
					false, gatewayMessage);
				
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
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}
	
	public ActionForward paymentnoqm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent userAgent = null;
		String result = "";
		String myurl = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = BASE64.dencrypt(actionForm.getUrl()); // 把URL解密
			System.out.println(">>>>>>>>>>paymentnoqm url = "+ url);
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			if (isTokenValid(request, true)) {
				String msg = gatewayBiz.validateDirectPay(map, url,
					gatewayMessage); // 验证支付,成功则返回字符串

				// SUCCESS
				// AnalyseParameter 返回验证不等于
				// SUCCESS的话,则返回错误页面,cooperate/error.jsp
				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("errornaqm");
				}
				// 查询买家信息,buyerEmail 即由gateway.jsp 中手动输入的钱门帐号
				int loginStatus = gatewayBiz.checkAgent(actionForm
						.getBuyerEmail(), actionForm.getPassword());
				if (loginStatus == 1) {
					actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("errornaqm");
				} else if (loginStatus == 2) {
					actionForm.setMsg(gatewayMessage.getPassword_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("errornaqm");
				} else if (loginStatus == 0) {
					userAgent = gatewayBiz.getAgent(actionForm.getBuyerEmail(),
						actionForm.getPassword());
					System.out.println("****************userAgent.getEmail():"+userAgent.getEmail());
				}
				// // the user not exists 验证这个买家是否钱门帐号
				 if (userAgent == null || userAgent.getId() == 0) {
					 actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					 request.setAttribute("GatewayForm", actionForm);
					 return mapping.findForward("error");
				 }
				BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
				// 判断买家是否有钱,或者是否够钱付款
				AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
				System.out.println("同步金额:"+ab.getAllowBalance());
				if (ab.getAllowBalance() == null
					|| !Gateway.equals(ab.getAllowBalance(),
								totalFee)) {
					// 上面验证买家的钱够不够，如果差0.2以下，可以认为钱是够的。
					// 验证订单是否存在
					OrderDetails orderDetails = gatewayBiz.getOrderDetails(map
						.get("out_trade_no"), map.get("partner"));
					// 如果订单不存在,先加入一条订单记录到数据库
					if (orderDetails == null || orderDetails.getId() < 1) {
						// create the order details if this order not exist
						String strSysDate = DateUtil
							.getDateString("yyyy-MM-dd HH:mm:ss");
						// get system date

						orderDetails = gatewayBiz.appendOrder(map, userAgent,
							url, false);
					}
					// set order details
					actionForm.setOrderDetail(orderDetails);
					// set user info
					actionForm.setBuyerInfo(userAgent);

					// set partner info
					Agent partner = gatewayBiz.getPartnerAccount(map
							.get("partner"));
					actionForm.setPartnerInfo(partner);
					// set seller info
					Agent seller = gatewayBiz.getSeller();
					actionForm.setSellerInfo(seller);

					actionForm.setSubject(map.get("subject"));
					actionForm.setBody(map.get("body"));
					actionForm.setTotalFee(map.get("total_fee"));

					request.setAttribute("GatewayForm", actionForm);
					saveToken(request);
					// 不够钱付款,转到银行付款页面
					return mapping.findForward("directPayByAccount");
				}
				/**************/
				if (map.get("_input_charset") != null
					&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				// 分润
			//	map.remove("payment_type");
			//	map.put("payment_type", "1");
				
				/*********************************/
				result = gatewayBiz.payment(map, userAgent, url, charset,
						false, gatewayMessage);
				
				if (result == null) {
					actionForm.setMsg(gatewayMessage
						.getPayment_Royalty_Fee_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("errornaqm");
				}
				if(result.equals(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough())){
					actionForm.setMsg(gatewayMessage
						.getPayment_Royalty_Fee_Error());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("errornaqm"); 
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
				return mapping.findForward("errornaqm");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("errornaqm");
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

	/**
	 * 即时退款
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward refund_fastpay_by_platform_nopwd(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************refund_fastpay_by_platform_nopwd 请求地址: "
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
			System.out.println("refund_fastpay_by_platform_nopwd url:"+url);
			//调用方法验证请求参数
			String msg = gatewayBiz.validateRefund(map, url, gatewayMessage); 
			System.out.println("验证返回信息是msg："+msg);
			String isSuccess = "F";
			if (gatewayMessage.getSuccess().equals(msg)) {
				isSuccess = "T";
			}

			if ("T".equals(isSuccess)) {
				DirectRefund directRefund = new DirectRefund(gatewayBiz);
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
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");//未知异常
		} finally {
		}
		// System.out.println("url paramets="+returnURL);
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("notify_url") + "?"+ returnURL);
		return null;
	}
	
	/**
	 * 信用支付退款
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward credit_refund_by_platform(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************credit_refund_by_platform 请求地址: "
				+ request.getRemoteAddr());
		//获取formbean
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		String url = "";
		//创建message对象    这里传false  创建的是gatewayMessageEn对象
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
		try {
			String encoding = request.getParameter("_input_charset");
			//判断页面提交方式    equalsIgnoreCase:不区分大小写
			if ("get".equalsIgnoreCase(request.getMethod())) {
				//调用方法获取url(页面用get方式提交)
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
				//获取url(页面用post方式提交)
				url = temp.toString();
			}
			System.out.println("credit_refund_by_platform  url"+url);
			// 分析URL,放到MAP里面
			map = AnalyseParameter.analyseURL(url);
			
			//调用方法验证url参数 并返回信息
			String msg = gatewayBiz.credit_validateRefund(map, url, gatewayMessage);
			System.out.println("验证返回信息是msg："+msg);
			String isSuccess = "F";
			if (gatewayMessage.getSuccess().equals(msg)) {
				isSuccess = "T";
			}

			if ("T".equals(isSuccess)) {
				CreditRefund creditRefund = new CreditRefund(gatewayBiz);
				creditRefund.setEncoding(encoding);
				creditRefund.setMap(map);
				creditRefund.setUrl(url);
				MainTask.put(creditRefund);
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
			return mapping.findForward("error"); //未知异常
		}
		// System.out.println("url paramets="+returnURL);
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("notify_url") + "?"+ returnURL);
		return null;
	}
	

	// 网上银行接口
	public ActionForward fastpay_by_bank(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		GatewayForm actionForm = (GatewayForm) form;
		Agent userAgent = null;
		HashMap<String, String> map = null;
		OrderDetails orderDetails = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			System.out.println("*************************** fastpay_by_bank");
			if (isTokenValid(request, true)) {
				String url = BASE64.dencrypt(actionForm.getUrl());
				map = AnalyseParameter.analyseURL(url);
				/*
				 * validate form
				 */
				String msg = gatewayBiz.validateDirectPay(map, url,
						gatewayMessage);
				// AnalyseParameter
				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}

				int result = gatewayBiz.checkAgent(actionForm.getBuyerEmail(),
						actionForm.getPassword());

				if (result == 0) {
					userAgent = gatewayBiz.getAgent(actionForm
						.getBuyerEmail(), actionForm.getPassword());
					orderDetails = gatewayBiz.getOrderDetails(map
						.get("out_trade_no"), map.get("partner"));
					if (orderDetails == null || orderDetails.getId() == 0) {
						orderDetails = gatewayBiz.appendOrder(map,
							userAgent, url, true);
					} else {
						orderDetails.setAgent(userAgent);
						gatewayBiz.updateOrderDetailsForAgent(orderDetails);
					}
					userAgent.setForwardPage("fastpay");
				} else {
					if (result == 1)
						actionForm.setMsg(gatewayMessage
							.getAccount_Not_Exists());
					else if (result == 2)
						actionForm.setMsg(gatewayMessage.getPassword_Error());

					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
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
		AgentAction agentAction = new AgentAction();
		agentAction.setAgentBiz(agentBiz);
		agentAction.loginByPaypassword(mapping, userAgent, request, response);

		// *****************************
		ActionRedirect redirect = new ActionRedirect(mapping
			.findForward("fastpay"));
		BigDecimal amount = null;
		if(null!=request.getParameter("allowBalance")&&request.getParameter("allowBalance").equals("allowBalance")){
			amount = new BigDecimal(map.get("total_fee")).subtract(userAgent.getAllowBalance());
		}else{
			amount = new BigDecimal(map.get("total_fee"));
		}
		redirect.addParameter("thisAction", "sendToBank");
		redirect.addParameter("type", "3,0");
		redirect.addParameter("version", actionForm.getBank()
			+ actionForm.getVersion());
		redirect.addParameter("bank", actionForm.getBank());
		redirect.addParameter("amount", amount);
		redirect.addParameter("agentId", userAgent.getId());
		redirect.addParameter("remark", "order_no:" + orderDetails.getId());
		return redirect;
	}

	// 担保交易网上银行接口
	public ActionForward guarantee_fastpay_by_bank(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		GatewayForm actionForm = (GatewayForm) form;
		Agent userAgent = null;
		HashMap<String, String> map = null;
		OrderDetails orderDetails = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			System.out.println("***************************:guarantee_fastpay_by_bank");
			if (isTokenValid(request, true)) {
				String url = BASE64.dencrypt(actionForm.getUrl());
				map = AnalyseParameter.analyseURL(url);
				/*
				 * validate form
				 */
				String msg = gatewayBiz.validateDirectPay(map, url,gatewayMessage);
				// AnalyseParameter
				if (!gatewayMessage.getSuccess().equals(msg)){
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}

				int result = gatewayBiz.checkAgent(actionForm.getBuyerEmail(),
						actionForm.getPassword());

				if (result == 0) {
					{
						userAgent = gatewayBiz.getAgent(actionForm
							.getBuyerEmail(), actionForm.getPassword());
						orderDetails = gatewayBiz.getOrderDetails(map
							.get("out_trade_no"), map.get("partner"));
						if (orderDetails == null || orderDetails.getId() == 0) {
							orderDetails = gatewayBiz.appendOrder(map,
								userAgent, url, true);
						} else {
							orderDetails.setAgent(userAgent);
							gatewayBiz.updateOrderDetailsForAgent(orderDetails);
						}
						userAgent.setForwardPage("fastpay");
					}
				} else {
					if (result == 1)
						actionForm.setMsg(gatewayMessage
							.getAccount_Not_Exists());
					else if (result == 2)
						actionForm.setMsg(gatewayMessage.getPassword_Error());

					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
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
		AgentAction agentAction = new AgentAction();
		agentAction.setAgentBiz(agentBiz);
		agentAction.loginByPaypassword(mapping, userAgent, request, response);

		// *****************************
		ActionRedirect redirect = new ActionRedirect(mapping
			.findForward("fastpay"));
		redirect.addParameter("thisAction", "sendToBank");
		redirect.addParameter("type", Charge.CHARGE_TYPE_GUARANTEE);
		redirect.addParameter("version", actionForm.getBank()
			+ actionForm.getVersion());
		redirect.addParameter("bank", actionForm.getBank());
		redirect.addParameter("amount", map.get("total_fee"));
		redirect.addParameter("agentId", userAgent.getId());
		redirect.addParameter("remark", "order_no:" + orderDetails.getId());
		return redirect;
	}

	public ActionForward guarantee_payment_by_bank(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		myLog = new LogUtil(false, false, TransactionAction.class);
		myLog.info("guarantee_payment_by_bank() remark:"
				+ request.getParameter("remark"));

		GatewayForm actionForm = (GatewayForm) form;
		String encoding = "GBK";
		String strOrderId = null;
		HashMap<String, String> map = null;
		String url = "";
		String result = "";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);

		try {
			String remark = request.getParameter("remark");
			strOrderId = remark.substring(9);
			long orderId = Long.parseLong(strOrderId);
			ActionLog actionLog = gatewayBiz.getActionLog(orderId,
				ActionLog.LOG_TYPE_PAY_REQUEST);
			map = AnalyseParameter.analyseURL(actionLog.getContent());

			// 分润
			System.out
					.println(">>>>>>>>>>>>>>>>>>>>>开始执行gatewayBiz.guarantee_payment-----"
							+ map.get("out_trade_no"));

			result = gatewayBiz.guarantee_payment(map, null, null, encoding,
					true, gatewayMessage);
			url = map.get("notify_url") + "?" + result;
			DirectPayment directPayment = new DirectPayment(gatewayBiz, result);
			directPayment.setEncoding(encoding);
			directPayment.setMap(map);
			directPayment.setUrl(url);
			directPayment.setPayByBank(true);
			MainTask.put(directPayment);

		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		String responseResult = HttpInvoker.readContentFromPost(url,
			java.net.URLEncoder.encode(result, encoding));
		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0){
			this.savaActionLog(map, responseResult, 1);
		} else {
			this.savaActionLog(map, responseResult, 0);
		}
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}

	public ActionForward direct_payment_by_bank(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		myLog = new LogUtil(false, false, TransactionAction.class);
		myLog.info("direct_payment_by_bank() remark:"
				+ request.getParameter("remark"));

		GatewayForm actionForm = (GatewayForm) form;
		String encoding = "GBK";
		String strOrderId = null;
		HashMap<String, String> map = null;
		String url = "";
		String result = "";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String remark = request.getParameter("remark");
			strOrderId = remark.substring(9);
			long orderId = Long.parseLong(strOrderId);
			ActionLog actionLog = gatewayBiz.getActionLog(orderId,
				ActionLog.LOG_TYPE_PAY_REQUEST);
			map = AnalyseParameter.analyseURL(actionLog.getContent());

			// 分润
			System.out
					.println(">>>>>>>>>>>>>>>>>>>>>开始执行gatewayBiz.payment-----"
							+ map.get("out_trade_no"));

			result = gatewayBiz.payment(map, null, null, encoding, true,
					gatewayMessage);
			url = map.get("notify_url") + "?" + result;
			DirectPayment directPayment = new DirectPayment(gatewayBiz, result);
			directPayment.setEncoding(encoding);
			directPayment.setMap(map);
			directPayment.setUrl(url);
			directPayment.setPayByBank(true);
			MainTask.put(directPayment);

		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("return_url") + "?" + result);

		String responseResult = HttpInvoker.readContentFromPost(url,
			java.net.URLEncoder.encode(result, encoding));
		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0){
			this.savaActionLog(map, responseResult, 1);
		} else {
			this.savaActionLog(map, responseResult, 0);
		}
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}

	// 房产网网上银行接口*******************************start******************************

	public ActionForward create_direct_pay_for_no_account(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			// validate request
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			/*
			 * validate form
			 */
			// String msg = gatewayBiz.testValidateDirectPay(map,
			// url,gatewayMessage);
			String msg = gatewayBiz.validateDirectPay(map, url, gatewayMessage);
			// AnalyseParameter
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			// *****************reset action form
			// set partner info
			partner = gatewayBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			// set seller info
			seller = gatewayBiz.getSeller();
			actionForm.setSellerInfo(seller);
			actionForm.setUrl(BASE64.encrypt(url));
			actionForm.setBank(map.get("bank"));
			OrderDetails order = gatewayBiz.getOrderDetails(map
				.get("out_trade_no"), map.get("partner"));
			if (order != null && order.getId() > 0) {
				actionForm.setBuyerEmail(order.getBuyerEmail());
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			request.setAttribute("GatewayForm", actionForm);
			// saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		actionForm.setRequestType("HOUSE_PERPORTY_REQUEST");
		// return mapping.findForward("gateway");
		return new ActionForward(
			"/cooperate/gateway.do?service=fastpay_for_no_account");
	}

	public ActionForward test_create_direct_pay_for_no_account(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			// validate request
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			/*
			 * validate form
			 */
			String msg = gatewayBiz.testValidateDirectPay(map, url,
					gatewayMessage);
			// String msg = gatewayBiz.validateDirectPay(map,
			// url,gatewayMessage);
			// AnalyseParameter
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			// *****************reset action form
			// set partner info
			partner = gatewayBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			// set seller info
			seller = gatewayBiz.getSeller();
			actionForm.setSellerInfo(seller);
			actionForm.setUrl(BASE64.encrypt(url));
			actionForm.setBank(map.get("bank"));
			OrderDetails order = gatewayBiz.getOrderDetails(map
				.get("out_trade_no"), map.get("partner"));
			if (order != null && order.getId() > 0) {
				actionForm.setBuyerEmail(order.getBuyerEmail());
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			request.setAttribute("GatewayForm", actionForm);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		actionForm.setRequestType("HOUSE_PERPORTY_REQUEST");
		// return mapping.findForward("gateway");
		return new ActionForward(
			"/cooperate/gateway.do?service=fastpay_for_no_account");
	}

	public ActionForward _create_direct_pay_for_no_account(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			// validate request
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));// new
			// String(request.getQueryString().getBytes("ISO-8859-1"),
			// encoding);
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			/*
			 * validate form
			 */
			String msg = gatewayBiz.validateDirectPay(map, url, gatewayMessage);
			// AnalyseParameter
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			// *****************reset action form
			// set partner info
			partner = gatewayBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			// set seller info
			seller = gatewayBiz.getSeller();
			actionForm.setSellerInfo(seller);
			actionForm.setUrl(BASE64.encrypt(url));
			OrderDetails order = gatewayBiz.getOrderDetails(map
				.get("out_trade_no"), map.get("partner"));
			if (order != null && order.getId() > 0) {
				actionForm.setBuyerEmail(order.getBuyerEmail());
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			request.setAttribute("GatewayForm", actionForm);

		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		actionForm.setRequestType("HOUSE_PERPORTY_REQUEST");
		// return mapping.findForward("gateway");
		return new ActionForward(
				"/cooperate/gateway.do?service=fastpay_for_no_account");
	}
	
	
	public ActionForward test1(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// String
		// S_url="http://192.168.1.89:1234/fdpay-client/cooperate/gateway.do?service=test";
		// URL url = new URL(S_url);
		// HttpURLConnection connection = (HttpURLConnection)
		// url.openConnection();
		String strURL = "https://qm.qmpay.com/security/certificate.do?";
		String quertString = "thisAction=getCertInputStreamOfP12&email=276628@qq.com";
		HashMap<String, String> params = (HashMap<String, String>) Certification
			.getSignUrl(quertString, "thisAction");
		BufferedInputStream bis = new BufferedInputStream(SSLURLUtil
			.getInputStreamAsPost(strURL, params, Certification
				.getProtocol()));
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Content-disposition", "attachment;filename="
				+ java.net.URLEncoder.encode("276628@qq.com.p12", "UTF-8"));
		response.setContentType("application/p12");
		OutputStream bos = response.getOutputStream();
		byte[] buf = new byte[1024];
		int size = 0;
		while ((size = bis.read(buf)) != -1)
			bos.write(buf, 0, size);

		bos.close();
		bis.close();
		return null;
	}

	// ----------------------------------------------------------------------------
	public ActionForward test(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// GatewayForm actionForm = (GatewayForm) form;
		// System.out.println(" 请求成功 ");
		// System.out.println("email:"+request.getParameter("email"));
		// System.out.println("queryString:"+request.getQueryString());
		// System.out.println("ParameterMap:"+request.getParameterMap());
		// String[]
		// map_values=(String[])(request.getParameterMap().get("thisAction"));
		// System.out.println("数组个数:"+map_values.length);
		// System.out.println("map:"+map_values[0].toString());
		// System.out.println(Certification.getQueryString(request.getParameterMap()));
		// String s=Certification.getQueryString(request.getParameterMap());
		// Certification.validateSignUrl(s);
		// System.out.println(Certification.validateSignUrl(s));
//		File file = new File("D:\\text.txt");
//
//		System.out.println("file.getName()===" + file.getName());
//		if (file.exists()) {
//			System.out.println("file.canRead()===" + file.canRead());
//			FileInputStream bis = new FileInputStream(file);
//			response.reset();
			// response.setHeader("Pragma", "public");
			// response.setHeader("Cache-Control", "max-age=30");
			// response.setHeader("Content-disposition", "attachment;filename="
			// + java.net.URLEncoder.encode("text.txt", "UTF-8"));
			// response.setContentType("application/p12");

			// response.setContentType("");
//			System.out.println("response.getContentType()="
//					+ response.getContentType());
//
//			OutputStream bos = response.getOutputStream();
//			byte[] buff = new byte[1024];
//			int readCount = 0;
//			int i = 0;
//			readCount = bis.read(buff);
//			while (readCount != -1) {
//				bos.write(buff, 0, readCount);
//
//				readCount = bis.read(buff);
//			}
//			if (bis != null)
//				bis.close();
//			if (bos != null)
//				bos.close();
//
//			return null;
//		} else {
//			response.getWriter().write(BASE64.encrypt("证书文件不存在!"));
//		}
		
		String url="http://192.168.1.89:1234/fdpay/cooperate/gateway.do?_input_charset=UTF-8&body=%E6%94%AF%E4%BB%98%E4%BA%BA%3Azh-aptechbving%40163.com&buyer_email=zh-aptechbving@163.com&mobile=15820573753&mobile_validate_code=893733&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm&out_trade_no=201002240000110&partner=2004898&payment_type=1&price=220.00&quantity=1&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult&royalty_parameters=649010333%40qq.com%5E190%5E%E6%9E%97%E5%B0%9A%E6%B2%83%E6%B5%8B%E8%AF%95|kenny010%40126.com%5E30%5E%E6%9E%97%E5%B0%9A%E6%B2%83%E6%B5%8B%E8%AF%95&royalty_type=10&seller_email=zh-aptechbving@163.com&service=direct_pay_by_mobile&subject=%E6%B5%8B%E8%AF%95%E6%89%8B%E6%9C%BA%E6%94%AF%E4%BB%98&total_fee=220&sign=6d0b95b6ca40f5f433979c493c4f4e96&sign_type=MD5";
		URL connection_url;
		try {
			connection_url = new URL(url);
			HttpURLConnection connection;
			java.io.InputStream in;
			String res ="";
			connection = (HttpURLConnection) connection_url.openConnection();
			// 模拟成ie
			connection.connect();
			in = connection.getInputStream();
			java.io.BufferedReader breader = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
			String str = breader.readLine();
			
			while (str != null) {

				res+=str;
				str = breader.readLine();
			}
			response.getWriter().print(res);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public ActionForward fastpay_for_no_account(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		GatewayForm actionForm = (GatewayForm) form;
		Timestamp sysDate = DateUtil.getTimestamp(DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		Agent userAgent = null;
		HashMap<String, String> map = null;
		OrderDetails orderDetails = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			System.out.println("房产网网上银行接口");
			String url = BASE64.dencrypt(actionForm.getUrl());
			map = AnalyseParameter.analyseURL(url);
			
			// 这里为了给房产网测试不用验证签名而屏蔽validateDirectPay方法,测试完后把testValidateDirectPay
			// 改回来为 validateDirectPay
			// String msg = gatewayBiz.validateDirectPay(map,
			// url,gatewayMessage);
			String msg = gatewayBiz.testValidateDirectPay(map, url,
					gatewayMessage);
			// AnalyseParameter
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			Coterie coterie = coterieBiz
				.getCoterieByPartner(map.get("partner"));
			userAgent = gatewayBiz.getAgent(coterie.getAgent().getId());
			if (userAgent != null && userAgent.getId() > 0) {
				orderDetails = gatewayBiz.getOrderDetails(map
					.get("out_trade_no"), map.get("partner"));
				if (orderDetails == null || orderDetails.getId() == 0) {
					orderDetails = gatewayBiz.appendOrder(map, userAgent, url,
						true);
				}
				userAgent.setForwardPage("fastpay");
			} else {
				actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		AgentAction agentAction = new AgentAction();
		agentAction.setAgentBiz(agentBiz);
		agentAction.loginByPaypassword(mapping, userAgent, request, response);
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("fastpay"));
		redirect.addParameter("thisAction", "sendToBank");
		redirect.addParameter("type", Charge.CHARGE_TYPE_NOACCOUNT);
		redirect.addParameter("version", actionForm.getBank() + "B2C");
		redirect.addParameter("bank", actionForm.getBank());
		redirect.addParameter("amount", map.get("total_fee"));
		redirect.addParameter("agentId", userAgent.getId()); // 从银行充值后,把钱先打到临时帐号里面
		redirect.addParameter("remark", "order_no:" + orderDetails.getId());
		return redirect;
	}

	public ActionForward direct_payment_for_no_account(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		myLog = new LogUtil(false, false, TransactionAction.class);
		myLog.info("direct_payment_for_no_account "
				+ request.getParameter("remark"));// author YanRui

		// 房产网测试加的
		PrintWriter out = response.getWriter();
		// response.setContentType("text/xml;charset=GBK");
		// response.setHeader("Cache-Control", "no-cache");
		System.out.println("--------- 执行完银行操作,调用这个方法 ----------------");
		GatewayForm actionForm = (GatewayForm) form;
		String encoding = "GBK";
		String strOrderId = null;
		HashMap<String, String> map = null;
		String result = "";
		String url = "";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String remark = request.getParameter("remark");
			strOrderId = remark.substring(9);
			long orderId = Long.parseLong(strOrderId);
			ActionLog actionLog = gatewayBiz.getActionLog(orderId,
					ActionLog.LOG_TYPE_PAY_REQUEST);
			map = AnalyseParameter.analyseURL(actionLog.getContent());
			// 分润
			result = gatewayBiz.paymentForNoAccount(map, encoding,
					gatewayMessage);

			System.out
					.println("------------------------------------房产网分润完返回的result = "
							+ result);
			url = map.get("notify_url") + "?" + result;
			
			DirectPayment directPayment = new DirectPayment(gatewayBiz, result);
			directPayment.setEncoding(encoding);
			directPayment.setMap(map);
			directPayment.setUrl(url);
			directPayment.setPayByBank(true);
			MainTask.put(directPayment);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("return_url") + "?" + result);
		String responseResult = HttpInvoker.readContentFromPost(url,
			java.net.URLEncoder.encode(result, encoding));
		System.out.println("---------------------------- responseResult= "
				+ responseResult + "  ------------------------------");
		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0) {
			System.out
					.println("---------------------------- responseResult 返回了 SUCCESS");
			out.print("SUCCESS");
			out.close();
			out = null;
			this.savaActionLog(map, responseResult, 1);
		} else {
			System.out
					.println("---------------------------- responseResult 返回了 FAIL");
			out.print("FAIL");
			out.close();
			out = null;
			this.savaActionLog(map, responseResult, 0);
		}
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}

	public ActionForward test_direct_payment_for_no_account(
		ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 房产网测试加的
		PrintWriter out = response.getWriter();
		// response.setContentType("text/xml;charset=GBK");
		// response.setHeader("Cache-Control", "no-cache");
		System.out
				.println("---------------- 执行完银行操作,调用这个方法 test_direct_payment_for_no_account -----------------------");
		GatewayForm actionForm = (GatewayForm) form;
		String encoding = "GBK";
		String strOrderId = null;
		HashMap<String, String> map = null;
		String result = "";
		String url = "";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String remark = request.getParameter("remark");
			strOrderId = remark.substring(9);
			long orderId = Long.parseLong(strOrderId);
			ActionLog actionLog = gatewayBiz.getActionLog(orderId,
				ActionLog.LOG_TYPE_PAY_REQUEST);
			map = AnalyseParameter.analyseURL(actionLog.getContent());
			// 分润
			result = gatewayBiz.paymentForNoAccount(map, encoding,
					gatewayMessage);

			System.out
					.println("------------------------------------房产网分润完返回的result = "
							+ result);
			url = map.get("notify_url") + "?" + result;

			// DirectPayment directPayment = new
			// DirectPayment(gatewayBiz,result);
			// directPayment.setEncoding(encoding);
			// directPayment.setMap(map);
			// directPayment.setUrl(url);
			// directPayment.setPayByBank(true);
			// MainTask.put(directPayment);

		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		response.setContentType("text/html;charset=GBK");

		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		// String responseResult=HttpInvoker.readContentFromPost(url,
		// java.net.URLEncoder.encode(
		// result, encoding));
		// System.out.println("---------------------------- responseResult=
		// "+responseResult +" ------------------------------");
		// if(responseResult != null && responseResult.indexOf("SUCCESS") >= 0){
		// System.out.println("---------------------------- responseResult 返回了
		// SUCCESS");
		// out.print("SUCCESS");
		// out.close();
		// out = null;
		// this.savaActionLog(map, responseResult, 1);
		// }
		// else{
		// System.out.println("---------------------------- responseResult 返回了
		// FAIL");
		// out.print("FAIL");
		// out.close();
		// out = null;
		// this.savaActionLog(map, responseResult, 0);
		// }
		return null;
	}

	// 这个方法只供房产网回调的时候测试用
	public ActionForward returnResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = null;
		// response.setContentType("text/xml;charset=GBK");
		// response.setHeader("Cache-Control", "no-cache");
		try {
			out = response.getWriter();
			out.write("SUCCESS");
			out.close();
			out = null;
		} catch (Exception e) {
			out.write("FAIL");
			e.printStackTrace();
		}
		return null;
	}

	// 房产网网上银行接口*******************************end******************************

	public ActionForward notify_verify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		String isSuccess = "false";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
		try {
			String partner = request.getParameter("partner"); 
			String notifyId = request.getParameter("notify_id");
			if (this.isNumeric(notifyId)) {
				if (partner != null && !"".equals(partner) && notifyId != null
						&& !"".equals(notifyId)) {
					if (gatewayBiz.notifyVerify(partner, notifyId,
							ActionLog.LOG_TYPE_PAY_RETURN)) {
						isSuccess = "true";
					}
				}
			}
			PrintWriter out = null;
			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			out = response.getWriter();
			out.write(isSuccess);
			out.close();
			out = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return null;
	}

	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 商户业务关系接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward get_agent_relationship(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			//验证url是否可行
			String msg = gatewayBiz.validateAgentRelationship(map, url,
					gatewayMessage);
			Agent agent = null;
			Agent agent2 = null;
			AgentRelationship agentRelationship = null;
			PrintWriter pw = response.getWriter();
			//判断验证是否成功
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				pw.print(msg);
			} else {
				System.out.println("--------------------");
				agent = agentBiz.getAgentByLoginName(map.get("agent_email1"));
				agent2 = agentBiz.getAgentByLoginName(map.get("agent_email2"));
				if (agent == null||agent2==null) { // 不存在本商户钱门帐号
					pw.print("0"); // 不存在本商户钱门帐号  （其中一个账号错误）
				} else{
					//判断第二个用户是不是圈主
					Coterie coterie = coterieBiz.getCoterieByAgent(agent2);
					if(coterie!=null){
						//查找第一个用户所加入圈的集合
						List<AgentCoterie> list = agentCoterieBiz.getCoterieByAgentId(agent.getId());
						if(list!=null&&list.size()!=0){
							int isc=0;
							for(AgentCoterie agentco : list){
								//循环比较两个用户所在圈的id是否想等
								if(agentco.getCoterie().getId()==coterie.getId()){
									isc=1;
									pw.print("2");   //存在圈主与圈员的关系
									break;
								}	
							}
							if(isc==0){
								pw.print("1");//不存在圈主与圈员的关系
							}
						}else{
							pw.print("1");//不存在圈主与圈员的关系
						}
					}else{
						pw.print("1");//不存在圈主与圈员的关系
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 商户余额接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward get_agent_balance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************get_agent_balance 请求地址: " + request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		String charSet="GBK";
		try {
			if(request.getParameter("_input_charset")!=null)
				charSet=request.getParameter("_input_charset");
			String url = AnalyseParameter.decode(request.getQueryString(),
					charSet);
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateAgentBalance(map, url,
					gatewayMessage);
			Agent agent = null;
			

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				pw.print("2,"+msg);
			} else {
				if (null!=map.get("agent_email")&&!"".equals(map.get("agent_email"))){		
					agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
				}else{
					agent = agentBiz.getAgentByBindingMobilePay(map.get("mobile"));
				}
				if (agent == null) { // 不存在本商户钱门帐号
					pw.print(0);
				} else {
					AgentBalance ab =agentBiz.getAgentBalance(agent.getId());
					BigDecimal agent_creditBalance=ab.getCreditBalance();
					BigDecimal agent_allowBalance=ab.getAllowBalance();
					pw.print(agent_allowBalance+ ","
							+ ab.getNotAllowBalance() + ","
							+ agent_creditBalance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print(1);
//			actionForm.setMsg(gatewayMessage.getUnknow_Error());
//			request.setAttribute("GatewayForm", actionForm);
//			return mapping.findForward("error");
		}
		return null;
	}
	
	/** 加入商户圈接口  （添加参数 return_type 2009/11/11）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws AppException
	 */
	public ActionForward add_coterie(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = new HashMap<String, String>();
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		Agent agent = null;
		PrintWriter pw = response.getWriter();
		String returnType="";
		String forward="addagentcoterie";
		
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateAddCoterie(map, url, gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
		returnType=map.get("return_type");
		System.out.println("return_type=>>"+returnType);
		Coterie coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
		agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
		if (agent == null) { // 不存在本商户钱门帐号
			pw.print("0");
		} else {
			AgentCoterie ac = agentCoterieBiz.queryByAgentEmail(map.get("agent_email"),map.get("partner"));
				if("0".equals(returnType)||returnType==null){
					if(ac!= null){
						pw.print("1");
					}else{
						boolean check=agentCoterieBiz.checkCoerieByAllowMult(agent.getId());
						int count=agentCoterieBiz.getAgentCoterieByAgent_Id(agent.getId());	
						if(check){
						//"该账户已经加入了独立商户圈，不能加入商户圈"
							pw.print("4");
						}else if(coterie.getAllowMultcoterie().equals("0")&&count>0){
							//"该账户已加入商户圈！不能加入独立商户圈！！
							pw.print("5");
						}else{
							AgentCoterie acc = new AgentCoterie();
							if (map.get("from_date") != null) { 
								Timestamp fromDate = DateUtil.getTimestamp(map.get("from_date").replaceAll("%20", " "), "yyyy-MM-dd");
									acc.setFromDate(fromDate);
								}
							if (map.get("expire_date") != null) 
							{ 
								Timestamp expireDate = DateUtil.getTimestamp(map.get("expire_date").replaceAll("%20", " "), "yyyy-MM-dd");
								acc.setExpireDate(expireDate);
							}
							if (AnalyseParameter.checkType(map.get("transaction_scope"))) { 
								acc.setTransactionScope(new Long(map.get("transaction_scope")));
							}
							if (AnalyseParameter.checkType(map.get("repayment_type"))) { 
								acc.setRepaymentType(new Long(map.get("repayment_type")));
								
							}
							if (AnalyseParameter.checkFee(map.get("min_amount"))) { 
								acc.setMinAmount(new BigDecimal(map.get("min_amount")));
							}

							if (AnalyseParameter.checkType(map.get("leave_days"))) { 
								acc.setLeaveDays(new Long(map.get("leave_days")));
							}
							acc.setAgent(agent);
							acc.setCoterie(coterie);
							acc.setStatus(new Long(1));
							acc.setCreateDate(new Timestamp(System.currentTimeMillis()));
							
							agentCoterieBiz.add(acc);
							pw.print("2");
						}
					}
				}else if("1".equals(returnType)){
				
					AgentCoterie acc = new AgentCoterie();
			/*		acc.setLeaveDays(new Long(map.get("leave_days")));
					acc.setFromDate(fromDate);
					acc.setExpireDate(expireDate);
					acc.setTransactionScope(new Long(map.get("transaction_scope")));
					acc.setMinAmount(new BigDecimal(map.get("min_amount")));
					acc.setRepaymentType(new Long(map.get("repayment_type")));*/
					if (map.get("from_date") != null) { 
						Timestamp fromDate = DateUtil.getTimestamp(map.get("from_date").replaceAll("%20", " "), "yyyy-MM-dd");
							acc.setFromDate(fromDate);
						}
					if (map.get("expire_date") != null) 
					{ 
						Timestamp expireDate = DateUtil.getTimestamp(map.get("expire_date").replaceAll("%20", " "), "yyyy-MM-dd");
						acc.setExpireDate(expireDate);
					}
					if (AnalyseParameter.checkType(map.get("transaction_scope"))) { 
						acc.setTransactionScope(new Long(map.get("transaction_scope")));
					}
					if (AnalyseParameter.checkType(map.get("repayment_type"))) { 
						acc.setRepaymentType(new Long(map.get("repayment_type")));
						
					}
					if (AnalyseParameter.checkFee(map.get("min_amount"))) { 
						acc.setMinAmount(new BigDecimal(map.get("min_amount")));
					}

					if (AnalyseParameter.checkType(map.get("leave_days"))) { 
						acc.setLeaveDays(new Long(map.get("leave_days")));
					}
					acc.setStatus(new Long(1));
					
					request.setAttribute("acc", acc);
					request.setAttribute("agent", agent);
					request.setAttribute("coterie", coterie);
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
				pw.print("3");
		}
		if("0".equals(returnType)||returnType==null)
			return null;
		else if("1".equals(returnType) && agent!=null)
			return mapping.findForward(forward);
		else
			return null;
//		ct.setFromDate(map.get("from_date"));
//		ct.setExpireDate(map.get("expire_date"));
//		ct.setRepaymentType(map.get("repayment_type"));
//		ct.setLeaveDays(map.get("leave_days"));
//		ct.setMinAmount(map.get("min_amount"));
//		ct.setTransactionScope(map.get("transaction_scope"));
//		
//		request.setAttribute("coterie", ct);
//		return mapping.findForward(forward);
	}

	/**
	 * 直接登录转向页面接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward direct_login_forward(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		String email = "";
		String forward = "";
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateDirectLoginForward(map, url,
					gatewayMessage);

			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}

			email = map.get("email");
			forward = map.get("forward");
		} catch (Exception e) {
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return new ActionForward(
				"/agent/agent.do?thisAction=forwardLogin&email=" + email
						+ "&forward=" + forward);
	}

	/**
	 * 从邮件点过来自动登录并查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward email_login_forward(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		String loginName = "";
		String forward = "";
		Agent agent = null;
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateEmailLoginForward(map, url,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}

			loginName = map.get("loginName");
			agent = agentBiz.getAgentByLoginName(loginName);
			AgentAction agentAction = new AgentAction();
			agentAction.setAgentBiz(agentBiz);
			agentAction.forwardEmailLogin(mapping, agent, request, response);
			forward = "/transaction/transaction.do?"
					+ url.replaceAll("service=email_login_forward&", "");
		} catch (Exception e) {
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}

		return new ActionForward(forward);
	}

	/**
	 * 查询商户圈接口一
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward get_agent_coterie(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateAgentCoterie(map, url,
					gatewayMessage);
			Agent agent = null;
			Coterie coterie = null;
			PrintWriter pw = response.getWriter();

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				pw.print(msg);
			} else {
				if (map.get("agent_email") == null
						|| map.get("partner") == null) {
					actionForm
							.setMsg(gatewayMessage.getParameter_Cannot_Null());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
				coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
				if (agent == null) {
					actionForm
							.setMsg(gatewayMessage.getAccount_Not_Exists());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				if(coterie==null){
					actionForm
						.setMsg(gatewayMessage.getPartner_Not_Sign_Protocol());
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				AgentCoterie agentCoterie = agentCoterieBiz.checkAgentCoterie(
						agent, coterie);
				if (agentCoterie == null) { // 没有加入商户圈
					pw.print("0");
				} else { // 加入商户圈
					pw.print("1");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return null;
	}

	/**
	 * 查询商户圈接口二
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward get_agent_coterie_to(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateAgentCoterie(map, url,
					gatewayMessage);
			Agent agent = null;
			Coterie coterie = null;

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				pw.print(msg);
			} else {
				if (map.get("agent_email") == null
						|| map.get("partner") == null) {
					pw.print(gatewayMessage.getParameter_Cannot_Null());
					return null;
				}
				coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
				if(coterie==null){
					pw.print(gatewayMessage.getPartner_Not_Sign_Protocol());
					return null;
				}
				agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
				if (agent == null) {
					pw.print("2");
					return null;
				}
				AgentCoterie agentCoterie = agentCoterieBiz.checkAgentCoterie(
						agent, coterie);
				if (agentCoterie == null) { // 没有加入商户圈
					pw.print("0");
				} else { // 加入商户圈
					pw.print("1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print(gatewayMessage.getUnknow_Error());
			return null;
		}
		return null;
	}
	
	
	/**
	 * 即时到账接口,多买家的
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_direct_pay_by_multbuyers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************create_direct_pay_by_multbuyers 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			// validate request
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面

			String msg = gatewayBiz.validateDirectPay(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			// SUCCESS
			// AnalyseParameter //返回验证不等于 SUCCESS的话,则返回错误页面,cooperate/error.jsp
			if (!gatewayMessage.getSuccess().equals(msg)) {
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
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("gateway");
	}

	/**
	 * 统计信用支付金额接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward get_credit_amount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateCreditAmount(map, url,
					gatewayMessage);
			Agent agent = null;
			PrintWriter pw = response.getWriter();
			BigDecimal creditAmount = new BigDecimal("0");

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {
				agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
				if (agent == null) { // 不存在本商户钱门帐号
					actionForm.setMsg("用户名不存在");
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else {
					String fromDate = map.get("from_date");
					String toDate = map.get("to_date");
					String orderDetailsType = map.get("payment_type");
					agent.setFromDate(fromDate);
					agent.setToDate(toDate);
					agent.setOrderDetailsType(new Long(orderDetailsType));

					creditAmount = gatewayBiz.getCreditAmount(agent);
					pw.print(creditAmount);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return null;
	}


	/**
	 * 授信支付金额接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward direct_credit_payment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("***************************direct_credit_payment 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent fromAgent = null;
		String result = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {

			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			System.out.println("direct_credit_payment  url："+url);
			String msg = gatewayBiz.validateDirectCreditPayment(map, url,
					gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			// AnalyseParameter 返回验证不等于
			// SUCCESS的话,则返回错误页面,cooperate/error.jsp
			if (!gatewayMessage.getSuccess().equals(msg)) {	
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			fromAgent = agentBiz.getAgentByLoginName(map.get("agent_email"));
			// // the user not exists 验证这个买家是否钱门帐号
			 if (fromAgent == null || fromAgent.getId() == 0) {
				 actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
				 request.setAttribute("GatewayForm", actionForm);
				 return mapping.findForward("error");
			 }
			BigDecimal credit_amount = new BigDecimal(map.get("credit_amount"));
			// 判断买家是否有钱,或者是否够钱付款
			AgentBalance ab =agentBiz.getAgentBalance(fromAgent.getId());
			long paymentType = new Long(map.get("payment_type"));
			// 如果是payment_type =1 就判断可用余额 ,如果等于 2,就判断授信金额
			if (paymentType == OrderDetails.ORDER_DETAILS_TYPE_1) {
				if (ab.getAllowBalance() == null
						|| !Gateway.equals(ab.getAllowBalance(),
								credit_amount)) {
					// actionForm.setMsg(gatewayMessage.getTxn_Result_Account_Balance_Not_Enough());
					// request.setAttribute("GatewayForm", actionForm);
					// // 不够钱付款,就转到错误页面
					// return mapping.findForward("error");
					pw.print("2"); // // 账户余额不足
					return null;
				}
			} else if (paymentType == OrderDetails.ORDER_DETAILS_TYPE_2) {
				if (ab.getCreditBalance() == null
						|| !Gateway.equals2(ab.getCreditBalance(),
								credit_amount)) {
					// actionForm.setMsg(gatewayMessage.getCredit_Account_Balance_Not_Enough());
					// request.setAttribute("GatewayForm", actionForm);
					// // 不够钱付款,就转到错误页面
					// return mapping.findForward("error");
					pw.print("3"); // // 授信余额不足
					return null;
				}
			}

			if (map.get("_input_charset") != null
					&& !"".equals(map.get("_input_charset"))) {
				charset = map.get("_input_charset");
			}
			// 直接授信金额打到对方帐号上
			result = gatewayBiz.direct_credit_payment(map, fromAgent, url,
					charset, false, gatewayMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
			pw.print("1"); // 授信失败
			return null;
		}
		if (result.indexOf("SUCCESS") >= 0) { // 授信成功
			pw.print("0" + result.substring(result.indexOf(",")));
		} else {
			pw.print("1"); // 授信失败
		}
		return null;
	}

	/**
	 * 授信还款接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward direct_credit_repayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("***************************direct_credit_repayment 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent fromAgent = null;
		String result = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		// try {
		//			
		String url = AnalyseParameter.decode(request.getQueryString(), request
				.getParameter("_input_charset"));
		map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
		System.out.println("direct_credit_repayment  url："+url);
		String msg = gatewayBiz.validateDirectCreditRepayment(map, url,
				gatewayMessage); // 验证支付,成功则返回字符串
		System.out.println("验证返回信息是msg："+msg);
		if (!gatewayMessage.getSuccess().equals(msg)) {
			actionForm.setMsg(msg);
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}

		if (map.get("_input_charset") != null
				&& !"".equals(map.get("_input_charset"))) {
			charset = map.get("_input_charset");
		}
		Coterie coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
		Agent partner = gatewayBiz.getPartnerAccount(map.get("partner"));
		actionForm.setPartnerInfo(partner);
		actionForm.setSubject(map.get("subject"));
		actionForm.setBody(map.get("body"));
		actionForm.setCoterie(coterie);
		System.out.println(url);
		actionForm.setUrl(BASE64.encrypt(url));
		actionForm.setRepaymentAmount(new BigDecimal(map
				.get("repayment_amount")));
		request.setAttribute("GatewayForm", actionForm);
		saveToken(request);

		return mapping.findForward("creditrepayment");
		// result = gatewayBiz.direct_credit_repayment(map, fromAgent, url,
		// charset,
		// false, gatewayMessage);

		// } catch (Exception ex) {
		// ex.printStackTrace();
		// pw.print("1"); //授信还款失败
		// return null;
		// }
		// if(result.equals("SUCCESS")){ //授信还款成功
		// pw.print("0");
		// }
		// else{
		// pw.print("1"); //授信还款失败
		// }

	}

	/**
	 * 授信还款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward credit_repayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent fromAgent = null;
		String result = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		String myurl = null;
		String sign = null;
		try {

			String url = BASE64.dencrypt(actionForm.getUrl());
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			System.out.println(actionForm.getPassword());
			map.put("payPassword", MD5.encrypt(actionForm.getPassword()));
			String msg = gatewayBiz.validateDirectCreditRepayment(map, url,
					gatewayMessage); // 验证支付,成功则返回字符串
			if (isTokenValid(request, true)) {
				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}

				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				result = gatewayBiz.direct_credit_repayment(map, fromAgent,
						url, charset, false, gatewayMessage);
				Coterie coterie = coterieBiz.getCoterieByPartner(map
						.get("partner"));
				map.put("result", result);
				result = UtilURL.directRepaymentRedirectUrl(map,
						coterie.getSignKey(), "UTF-8").toString();

				myurl = map.get("notify_url") + "?" + result;
				System.out.println("--------信用还款 notify_url --------");
				System.out.println(myurl);
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				MainTask.put(directPayment);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}

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
	 * 信用支付接口
	 */
	public ActionForward create_credit_pay_by_user(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("***************************create_credit_pay_by_user 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			System.out.println("create_credit_pay_by_user  url："+url);	
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			String msg = gatewayBiz.validateCreditPay(map, url, gatewayMessage); // 验证支付,成功则返回成功的字符串
			System.out.println("验证返回信息是msg："+msg);
			// AnalyseParameter 返回验证不等于 SUCCESS的话,则返回错误页面,cooperate/error.jsp
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
			
			Coterie coterie = coterieBiz
				.getCoterieByPartner(map.get("partner"));
		
			// *****************reset action form
			// set partner info 查询 partner信息
			partner = gatewayBiz.getPartnerAccount(map.get("partner"));
			actionForm.setPartnerInfo(partner);
			// set seller info 设置卖家信息到Form
			seller = gatewayBiz.getSeller();
			actionForm.setSellerInfo(seller);
			actionForm.setUrl(BASE64.encrypt(url)); // BASE64加密URL放到Form
			
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			actionForm.setBuyerEmail(map.get("buyer_email"));
			actionForm.setCoterie(coterie);

			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("creditpay");
	}

	public ActionForward credit_payment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

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
			if (isTokenValid(request, true)) {
				String msg = gatewayBiz.validateCreditPay(map, url,
						gatewayMessage); // 验证支付,成功则返回字符串

				// AnalyseParameter 返回验证不等于
				// SUCCESS的话,则返回错误页面,cooperate/error.jsp
				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm",actionForm);
					return mapping.findForward("error");
				}
				// 查询买家信息,buyerEmail 即由creditpay.jsp 中手动输入的钱门帐号
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
				// // the user not exists 验证这个买家是否钱门帐号
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
					System.out.println("信用支付圈id:"+ac.getId());
					System.out.println("信用支付启用状态："+ac.getStatus());
					if (ac.getStatus() == AgentCoterie.CHANGE_STATUS_0) { // 信用支付已经被禁用
						actionForm.setMsg(gatewayMessage
								.getCredit_Payment_Has_Stop());
						request.setAttribute("GatewayForm", actionForm);
						return mapping.findForward("error");
					} else {	
						if (agentCoterieBiz.checkCreditExpireDate(userAgent,
								coterie)) {
							// 判断催还类型,2--表示多笔催还,1--表示逐笔催还
							if (ac.getRepaymentType() == 2) {
								// 获取AgentBalance对象（用于获取金额）
								AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
								if (ab.getCreditBalance().compareTo(
										ac.getMinAmount()) < 0){ //信用余额小于最少限制金额
									actionForm.setMsg(gatewayMessage
											.getCredit_Price_Less_Then());
									request.setAttribute("GatewayForm",
											actionForm);
									return mapping.findForward("error");
								}
								// else{
								//									
								// }
							} else if (ac.getRepaymentType() == 1) {
								// 获取AgentBalance对象（用于获取金额）
								AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
								if (ab.getCreditBalance().compareTo(
										ac.getMinAmount()) < 0) { // 信用余额小于最少限制金额
									Transaction transaction = gatewayBiz
											.getTransactionByAgent(userAgent);
									if (transaction != null) { // 逐笔催还,信用余额已经小于限制金额,就判断是否有交易
										actionForm.setMsg(gatewayMessage
												.getNeed_First_Repayment());
										request.setAttribute("GatewayForm",
												actionForm);
										return mapping.findForward("error");
									}
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
				// 同步可用金额
				
				AgentBalance ab=agentBiz.getAgentBalance(userAgent.getId());
				System.out.println(ab.getCreditBalance());
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
				// 分润
				result = gatewayBiz.credit_payment(map, userAgent, url,
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
		request.getSession().removeAttribute("URI");
		request.getSession().invalidate();
		response.sendRedirect(map.get("return_url") + "?" + result);
		return null;
	}

	/**
	 * 信用支付（无密）接口
	 */
	public ActionForward create_credit_pay_by_user_nopwd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("***************************create_credit_pay_by_user_nopwd 请求地址: "
				+ request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		HashMap<String, String> map = null;
		Agent userAgent = null;
		String result = "";
		String myurl = "";
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(false);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面

			String msg = gatewayBiz.validateCreditPay(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("验证返回信息是msg："+msg);
			/***验证买家***/
			if((map.get("buyer_email")==null||"".equals(map.get("buyer_email").trim()))
					&&(map.get("buyer_id")==null||"".equals(map.get("buyer_id").trim()))){
				msg = gatewayMessage.getBuyer_Email_Error();
			}
			
			if(map.get("buyer_email")!=null){
				userAgent = agentBiz.getAgentByLoginName(map.get("buyer_email"));
			}else{
				userAgent = agentBiz.getAgentById(Long.parseLong((map.get("buyer_id"))));
			}
			// // the user not exists 验证这个买家是否钱门帐号
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
					msg = gatewayMessage.getCredit_Payment_Has_Stop();
				} else {
					if (agentCoterieBiz.checkCreditExpireDate(userAgent,
							coterie)) {
						// 获取AgentBalance对象（用于获取金额）
						AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
						// 判断催还类型,2--表示多笔催还,1--表示逐笔催还
						if (ac.getRepaymentType() == 2) {
							if (ab.getCreditBalance().compareTo(
									ac.getMinAmount()) < 0) { // 信用余额小于最少限制金额
								msg = gatewayMessage.getCredit_Price_Less_Then();
							}
							// else{
							//									
							// }
						} else if (ac.getRepaymentType() == 1) {
							if (ab.getCreditBalance().compareTo(
									ac.getMinAmount()) < 0) { // 信用余额小于最少限制金额
								Transaction transaction = gatewayBiz
										.getTransactionByAgent(userAgent);
								if (transaction != null) { // 逐笔催还,信用余额已经小于限制金额,就判断是否有交易
									msg = gatewayMessage.getNeed_First_Repayment();
								}
							}
						}
					} else {
						// 信用支付有效期已过
						msg = gatewayMessage.getCredit_Payment_Date_Expire();
					}
				}
			}
			
			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			// 判断买家是否有钱,或者是否够钱付款
			// 同步可用金额
			
			AgentBalance ab=agentBiz.getAgentBalance(userAgent.getId());
			System.out.println(ab.getCreditBalance());
			if (ab.getCreditBalance() == null
					|| !Gateway.equals2(ab.getCreditBalance(),
							totalFee)) {
				msg = gatewayMessage.getCredit_Account_Balance_Not_Enough();
				saveToken(request);
			}

			if (map.get("_input_charset") != null
					&& !"".equals(map.get("_input_charset"))) {
				charset = map.get("_input_charset");
			}
			
			String isSuccess = "F";
			if (gatewayMessage.getSuccess().equals(msg)) {
				// 分润
				result = gatewayBiz.credit_payment(map, userAgent, url,
						charset, false, gatewayMessage);
				isSuccess = "T";
			}
			if (result == null) {
				msg = gatewayMessage.getPayment_Royalty_Fee_Error();
			}
			if ("T".equals(isSuccess)) {
				//调用线程  异步通知
				myurl = map.get("notify_url") + "?" + result;
				DirectPayment directPayment = new DirectPayment(gatewayBiz,
						result);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				directPayment.setUserAgent(userAgent);
				MainTask.put(directPayment);
			}
			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Cache-Control", "no-cache");
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?> ");
			sb.append("<fdpay>");
			sb.append("<is_success>");
			sb.append(isSuccess);
			sb.append("</is_success>");
			if("F".equals(isSuccess)){
				sb.append("<error>");
				sb.append(msg);
				sb.append("</error>");
			}
			sb.append("</fdpay>");
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
			out.close();
			out = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		
//		String responseResult = HttpInvoker.readContentFromPost(myurl,
//				java.net.URLEncoder.encode(result, charset));
//		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0) {
//			this.savaActionLog(map, responseResult, 1);
//		} else {
//			this.savaActionLog(map, responseResult, 0);
//		}
//		request.getSession().removeAttribute("URI");
//		request.getSession().invalidate();
//		response.sendRedirect(map.get("return_url") + "?" + result);
		
		return null;
	}
	
	/**
	 * 手机支付接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward direct_pay_by_mobile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GatewayForm actionForm = (GatewayForm) form;
		System.out.println("***************************direct_pay_by_mobile 请求地址: "
				+ request.getRemoteAddr());
		System.out.println("***************************direct_pay_by_mobile 请求ServerName: "
				+ request.getServerName());
		HashMap<String, String> map = null;
		Agent userAgent = null;
		String result[] = new String[2];
		String myurl = "";
		TaskTimestamp ts = null;
		String charset = "GBK";
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			System.out.println("direct_pay_by_mobile  url："+url);
			map = AnalyseParameter.analyseURL(url); // 分析URL,放到MAP里面
			String msg = gatewayBiz.validateMobilePay(map, url, gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("返回信息是："+msg);
			// AnalyseParameter 返回验证不等于
			// SUCCESS的话,则返回错误页面,cooperate/error.jsp
			if (!gatewayMessage.getSuccess().equals(msg)) {
				pw.print(msg);
				pw.close();
				return null;
			}

			userAgent = agentBiz.getAgentByBindingMobilePay(map.get("mobile"));
			if (userAgent == null) {
				pw.print("手机号码有误或者您绑定的手机还没有开通手机支付的服务");
				pw.close();
				return null;
			} else {
				ts = tasktimestampBiz.getTaskTimestamp(userAgent,
					TaskTimestamp.type_8);
				
				if (null==ts||!map.get("mobile_validate_code").equals(ts.getNo())) {
					pw.print("手机验证码有误");
					pw.close();
					return null;
				}
				if(ts.getStatus()==0){
					pw.print("验证码已过期");
					pw.close();
					return null;
				}
			}

			BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
			// 判断买家是否有钱,或者是否够钱付款
			AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
			if (ab.getAllowBalance() == null
					|| !Gateway.equals(ab.getAllowBalance(), totalFee)) {
				pw.print(gatewayMessage
						.getBuyer_Account_Balance_Not_Enough());  //不够钱付款
				pw.close();
				return null;
			}

			if (map.get("_input_charset") != null
					&& !"".equals(map.get("_input_charset"))) {
				charset = map.get("_input_charset");
			}
			// 分润
			result = gatewayBiz.direct_pay_by_mobile(map, userAgent, url,
					charset, false, gatewayMessage);

			if (result == null || result[0]==null || "".equals(result[0])) {
				pw.print(gatewayMessage.getPayment_Royalty_Fee_Error());
				pw.close();
				return null;
			}else{
				ts.setStatus(0L); //修改手机支付信息验证状态  （0：停用1：启用）
				tasktimestampBiz.updateTaskTimestampStatus(ts);
				pw.print(result[1]);
				pw.close();
				myurl = map.get("notify_url") + "?" + result[0];
				DirectPayment directPayment = new DirectPayment(gatewayBiz, result[0]);
				directPayment.setEncoding(charset);
				directPayment.setMap(map);
				directPayment.setUrl(myurl);
				directPayment.setUserAgent(userAgent);
				MainTask.put(directPayment);
				return null;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			pw.print(gatewayMessage.getUnknow_Error());
			return null;
		}
		// response.setContentType("text/html;charset=GBK");
		// response.sendRedirect(map.get("return_url") + "?" + result[0]);

//		String responseResult = HttpInvoker.readContentFromPost(myurl,
//				java.net.URLEncoder.encode(result, charset));
//		if (responseResult != null && responseResult.indexOf("SUCCESS") >= 0) {
//			this.savaActionLog(map, responseResult, 1);
//		} else {
//			this.savaActionLog(map, responseResult, 0);
//		}
//		request.getSession().removeAttribute("URI");
//		request.getSession().invalidate();
//		response.sendRedirect(map.get("return_url") + "?" + result[0]);
	}

	/**
	 * 发送短信确认码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward send_confirm_msg_of_order(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz
					.validateConfirmMsg(map, url, gatewayMessage);
			System.out.println("发送短信确认码验证返回信息是："+msg);
			Agent agent = null;
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				pw.print("0003");
			} else {
				agent = agentBiz.getAgentByBindingMobilePay(map.get("mobile"));
				if (agent == null) { // 不存在本商户钱门帐号
					pw.print("0002");
				} else {
					if (gatewayBiz.sendMsg(map, agent)) {
						pw.print("0000");
					} else {
						pw.print("0001");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("0004");
		}
		pw.close();
		return null;
	}
	

	public static void main(String[] s) {
		String url = "http://219.131.194.194:5555/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
		String sign_url = "mobile=15018863219";
		sign_url += "&msg_content=sjahjsajkhhjh";
		sign_url += "&partner=2088001636104895";
		sign_url += "&service=send_confirm_msg_of_order";
		String signMD5 = MD5.encrypt(sign_url
				+ "stpsyw03edwnr1oplj5cevnzj7r1qu73", "utf-8");
		url += sign_url + "&sign=" + signMD5 + "&sign_type=MD5";
		System.out.println(url);
	}

	/**
	 * 担保交易接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_guarantee_pay_by_user(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("*************************** create_guarantee_pay_by_user请求地址："
				+request.getRemoteAddr());
		GatewayForm actionForm = (GatewayForm) form;
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面
			System.out.println("create_guarantee_pay_by_user url:"+url);
			String msg = gatewayBiz.validateDirectPay(map, url, gatewayMessage); // 验证支付,成功则返回字符串

			if (!gatewayMessage.getSuccess().equals(msg)) {
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
			}else{
				//保存买家email
				Agent buyeragent=null;
				if (map.get("buyer_email")!=null&&!"".equals(map.get("buyer_email").trim())){
					buyeragent = agentBiz.getAgentByLoginName(map.get("buyer_email"));
					request.setAttribute("buyeremailnoqm", map.get("buyer_email"));
				}	
				if (map.get("buyer_id")!=null&&!"".equals(map.get("buyer_id").trim())){
					buyeragent = agentBiz.getAgentById(Long.parseLong(map.get("buyer_id")));
					request.setAttribute("buyeremailnoqm", buyeragent.getEmail());
				}
				if(null!=buyeragent){
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab =agentBiz.getAgentBalance(buyeragent.getId());
					if( ab.getAllowBalance().compareTo(new BigDecimal(map.get("total_fee")))==-1){
						actionForm.setRequestType("master");  //买家钱不够
					}
				}
			}
			actionForm.setSubject(map.get("subject"));
			actionForm.setBody(map.get("body"));
			actionForm.setTotalFee(map.get("total_fee"));
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("guaranteePayment");
	}
	
	
	public ActionForward guarantee_payment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
			if (isTokenValid(request, true)) {
				String msg = gatewayBiz.validateDirectPay(map, url,
						gatewayMessage); // 验证支付,成功则返回字符串

				if (!gatewayMessage.getSuccess().equals(msg)){
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				// 查询买家信息,buyerEmail 即由gateway.jsp 中手动输入的钱门帐号
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
				// // the user not exists 验证这个买家是否钱门帐号
				 if (userAgent == null || userAgent.getId() == 0) {
					 actionForm.setMsg(gatewayMessage.getAccount_Not_Exists());
					 request.setAttribute("GatewayForm", actionForm);
					 return mapping.findForward("error");
				 }
				BigDecimal totalFee = new BigDecimal(map.get("total_fee"));
				// 判断买家是否有钱,或者是否够钱付款
				// 同步可用金额
				
				AgentBalance ab =agentBiz.getAgentBalance(userAgent.getId());
				if (ab.getAllowBalance() == null
						|| !Gateway.equals(ab.getAllowBalance(),
								totalFee)) {
					// 上面验证买家的钱够不够，如果差0.2以下，可以认为钱是够的。
					// 验证订单是否存在
					OrderDetails orderDetails = gatewayBiz.getOrderDetails(map
							.get("out_trade_no"), map.get("partner"));
					// 如果订单不存在,先加入一条订单记录到数据库
					if (orderDetails == null || orderDetails.getId() < 1) {
						// create the order details if this order not exist
						orderDetails = gatewayBiz.appendOrder(map, userAgent,
								url, false);
					}
					// set order details
					actionForm.setOrderDetail(orderDetails);
					// set user info
					actionForm.setBuyerInfo(userAgent);

					// set partner info
					Agent partner = gatewayBiz.getPartnerAccount(map
							.get("partner"));
					actionForm.setPartnerInfo(partner);
					// set seller info
					Agent seller = gatewayBiz.getSeller();
					actionForm.setSellerInfo(seller);

					actionForm.setSubject(map.get("subject"));
					actionForm.setBody(map.get("body"));
					actionForm.setTotalFee(map.get("total_fee"));

					request.setAttribute("GatewayForm", actionForm);
					saveToken(request);
					// 不够钱付款,转到银行付款页面
					return mapping.findForward("guaranteePayByAccount");
				}

				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				// 分润
				result = gatewayBiz.guarantee_payment(map, userAgent, url,
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
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
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
	 * 担保交易需密码支付接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward create_guarantee_pay_by_password(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		System.out.println("*************************** create_guarantee_pay_by_password请求地址："
				+request.getRemoteAddr());
		Agent partner = null;
		Agent seller = null;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		try {
			// validate request
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url); // 把传过来的URL分析,装到一个MAP里面

			String msg = gatewayBiz.validateGuaranteePay(map, url,
					gatewayMessage); // 验证支付,成功则返回字符串
			System.out.println("返回信息是："+msg);
			if (!gatewayMessage.getSuccess().equals(msg)) {
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			}
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
			request.setAttribute("GatewayForm", actionForm);
			saveToken(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
		return mapping.findForward("guaranteePaymentByPassword");
	}

	public ActionForward guarantee_payment_by_password(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
			if (isTokenValid(request, true)) {
				String msg = gatewayBiz.validateGuaranteePay(map, url,
						gatewayMessage); // 验证支付,成功则返回字符串

				if (!gatewayMessage.getSuccess().equals(msg)) {
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				}
				// 查询买家信息,buyerEmail 即由gateway.jsp 中手动输入的钱门帐号
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
				if (map.get("_input_charset") != null
						&& !"".equals(map.get("_input_charset"))) {
					charset = map.get("_input_charset");
				}
				// 分润
				result = gatewayBiz.guarantee_payment_by_password(map,
						userAgent, url, charset, gatewayMessage);

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
			actionForm.setMsg(gatewayMessage.getUnknow_Error());
			request.setAttribute("GatewayForm", actionForm);
			return mapping.findForward("error");
		}
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
	 * 退出支付圈接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward exit_coterie(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateExitCoterie(map, url,
					gatewayMessage);
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
					// 获取AgentBalance对象（用于获取金额）
					AgentBalance ab =agentBiz.getAgentBalance(agent.getId());
					// 如果信用余额里面还有钱,就不能退出支付圈
					if (ab.getCreditBalance().compareTo(new BigDecimal("0")) > 0) {
						pw.print("4");
					} else {
						AgentCoterie ac = agentCoterieBiz.checkAgentCoterie(
								agent, coterie);
						if (ac == null) {
							pw.print("1");
						} else {
							if(coterie.getAgent().getId()!=agent.getId()){
							agentCoterieBiz.deleteAgentCoterie(ac);
							pw.print("2");
							}else{
								pw.print("5");
							}
						}
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
	 * 更改支付圈圈员状态接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward change_coterie_status(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateChangeCoterieStatus(map, url,
					gatewayMessage);
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
						if (new Long(map.get("change_status")) == AgentCoterie.CHANGE_STATUS_0) { // 停用
							ac.setStatus(AgentCoterie.CHANGE_STATUS_0);
						} else if (new Long(map.get("change_status")) == AgentCoterie.CHANGE_STATUS_1) { // 启用
							ac.setStatus(AgentCoterie.CHANGE_STATUS_1);
						}
						agentCoterieBiz.updateAgentCoterieStauts(ac);
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
	 * 修改圈员还款规则接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward update_repayment_rule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateRepaymentRule(map, url,
					gatewayMessage);
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
						ac.setRepaymentType(new Long(map.get("repayment_type")));
						ac.setMinAmount(new BigDecimal(map.get("min_amount")));
						ac.setLeaveDays(new Long(map.get("leave_days")));
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
	 * 修改圈员信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward update_repayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			GatewayForm actionForm = (GatewayForm) form;
			GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
			PrintWriter pw = response.getWriter();
			System.out.println("********************update_repayment");
			try {
				//进行转码
				String url=AnalyseParameter.decode(request.getQueryString(),
						request.getParameter("_input_charset"));
				System.out.println(url);
				//解释URL
				HashMap<String, String> map = AnalyseParameter.analyseURL(url);
				//验证各参数
				String msg = gatewayBiz.validateRepayment(map, url,gatewayMessage);
				Agent agent = null;

				if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else {
					//验证圈员的邮箱
					agent = agentBiz.getAgentByLoginName(map.get("agent_email"));
					//验证合作伙伴ID
					Coterie coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
					if (agent == null) { // 不存在本商户钱门帐号
						pw.print("0");
					} else {
						//根据圈员邮箱和合作伙伴ID进行查询
						AgentCoterie ac = agentCoterieBiz.checkAgentCoterie(agent,coterie);
						if(ac == null){//存在本商户钱门帐号，但没有加入该支付圈
							pw.print("1");
						}
						else{
							System.out.println(map.get("expire_date").replaceAll("%20", " "));
							Timestamp fromDate=DateUtil.getTimestamp(map.get("from_date").replaceAll("%20", " "), "yyyy-MM-dd");
							Timestamp expireDate=DateUtil.getTimestamp(map.get("expire_date").replaceAll("%20", " "), "yyyy-MM-dd");
							ac.setFromDate(fromDate);
							ac.setExpireDate(expireDate);

							//进行修改
							agentCoterieBiz.updateRepayment(ac);
							pw.print("2");//更新成功
						}
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				pw.print("3");//更新失败
			}
			return null;
		}
	


	public ActionForward get_allmember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateAllMemberByPartner(map, url,
					gatewayMessage);
			Agent agent = null;
			String memberList = "";
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {
				Coterie coterie = coterieBiz.getCoterieByPartner(map
						.get("partner"));

				if (coterie == null) { // 不存在该圈
					pw.print("0");
				} else {
					List list = agentCoterieBiz.getAllMemberByPartner(coterie);
					if (list != null && list.size() > 0) {
						for (int i = 0; i < list.size(); i++) {
							AgentCoterie ac = (AgentCoterie) list.get(i);
							agent = agentBiz
									.getAgentById(ac.getAgent().getId());
							memberList = agent.getEmail() + "," + memberList;
						}
						pw.print(memberList.substring(0, memberList
								.lastIndexOf(",")));
					} else {
						pw.print("2");// 没有任何成员
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("1");
		}
		return null;
	}
	
	/**
	 * 自动注册钱门帐号接口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward auto_register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateRegister(map, url, gatewayMessage);
			// Agent agent = null;

			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {

				Agent registerAgent = new Agent();
				registerAgent.setLoginName(map.get("agent_email"));
				if (agentBiz.checkAgent(registerAgent) == null) {
					registerAgent = agentBiz.autoRegister(registerAgent);
					if (registerAgent == null) {
						pw.print("1");
					} else {
						pw.print("2");
					}
				} else {
					pw.print("0");// 商户已经存在
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("1");
		}
		return null;
	}
	
	/**
	 * 自动注册钱门帐号测试接口
	 * @author 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward test_register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		//获取formbean
		GatewayForm actionForm = (GatewayForm) form;
		//创建信息提示类  这里传入true  通过抽象工厂创建的是gatewayMessageCn对象
		GatewayMessage gatemessage = GatewayMessageFactory.getMessage(true);
		//创建输出对象
		PrintWriter pw = response.getWriter();
		try {
			//获取url   request.getQueryString()获取传过来的参数集合
			String url = AnalyseParameter.decode(request.getQueryString(), request.getParameter("_input_charset"));
			//调用方法分割url   并返回hashmap
			HashMap<String, String> map =  AnalyseParameter.analyseURL(url);
			//调用方法验证url   返回提示信息
			String mes = gatewayBiz.testValidateRegister(map,url,gatemessage);
			//判断验证后的信息是否是成功
			if (!gatemessage.getSuccess().equals(mes)){ // 验证URL
				actionForm.setMsg(mes);
				request.setAttribute("GatewayForm", actionForm);
				//跳到错误页面
				return mapping.findForward("error");
			} else {
				//把传过来的email赋值给agent
				Agent registerAgent = new Agent();
				registerAgent.setLoginName(map.get("agent_email"));
				//判断是否存在agent
				if (agentBiz.checkAgent(registerAgent) == null) {
					registerAgent = agentBiz.autoRegister(registerAgent);
					if (registerAgent == null) {
						pw.print("1");  //返回未知异常
					} else {
						pw.print("2");  //成功
					}
				} else {
					pw.print("0"); //商户已经存在
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			pw.print("1");
		}
		return null;   
	}
	

	public ActionForward direct_change_agent_password(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		boolean relust_send_email = false;
		long type = TaskTimestamp.type_2;// 默认修改登录密码
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateDirectChangePassword(map, url,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {
				HashMap<String, String> params = new HashMap<String, String>();
				Agent agent = agentBiz.getAgentByLoginName(map
						.get("agent_email"));
				params.put("$RealyName$", agent.getLoginName());
				String paramUrl = Constant.getLocalHost();
				String url_content = "agent_email=" + map.get("agent_email");
				url_content += "&partner=" + map.get("partner");
				url_content += "&service=change_agent_password";
				if (map.get("type").equals("1")) {
					type = TaskTimestamp.type_2;
				} else if (map.get("type").equals("2")) {
					type = TaskTimestamp.type_5;// 修改支付密码
				}
				url_content += "&type=" + type;
				paramUrl += "/cooperate/gateway.do?";
				paramUrl += url_content;
				Coterie coterie = coterieBiz.getCoterieByPartner(map.get("partner"));
				paramUrl += "&sign="
						+ MD5.encrypt(url_content + coterie.getSignKey());
				paramUrl += "&sign_type=MD5";
				params.put("$url$", paramUrl);

				relust_send_email = gatewayBiz.sendEmail(params, agent, type);
				if (relust_send_email) {
					System.out.println("--------------发送邮件成功---------------");
					return mapping.findForward("infoSendEmail");
				} else {
					System.out.println("------------------发送失败---------------------");
					return mapping.findForward("infoSendEmailFail");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward change_agent_password(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		boolean relust_send_email = false;
		long TaskTimestampType = 0;
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateChangePassword(map, url,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {

				if (map.get("type").equals("2")) {
					TaskTimestampType = TaskTimestamp.type_2;
				} else if (map.get("type").equals("5")) {
					TaskTimestampType = TaskTimestamp.type_5;
				}
				Agent agent = agentBiz.getAgentByLoginName(map
						.get("agent_email"));
				if (tasktimestampBiz.getTaskTimestamp(agent, TaskTimestampType) != null) {

					actionForm.setUrl(BASE64.encrypt(url));
					actionForm.setEmail(map.get("agent_email"));
					request.setAttribute("GatewayForm", actionForm);
					if (map.get("type").equals("2")) {
						return mapping.findForward("setPassword");
					} else if (map.get("type").equals("5")) {
						return mapping.findForward("setPayPassword");
					}
				} else {
					return mapping.findForward("activeDisabled");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public ActionForward set_password(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		boolean relust_send_email = false;
		try {
			String url = BASE64.dencrypt(actionForm.getUrl());
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateChangePassword(map, url,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {
				Agent agent = agentBiz.getAgentByLoginName(map
						.get("agent_email"));

				if (agent.getPayPassword().equals(
						MD5.encrypt(actionForm.getRepassword()))) {
					request.setAttribute("error1", "error1");
					actionForm.setUrl(BASE64.encrypt(url));
					actionForm.setEmail(map.get("agent_email"));
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("setPassword");
				}
				if (!actionForm.getRepassword().equals("")
						&& actionForm.getRepassword().equals(
								actionForm.getPassword())) {

					agent.setPassword(MD5.encrypt(actionForm.getPassword()));
					agentBiz.updateAgent(agent);
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("$RealyName$", agent.getName());
					String url1 = Constant.getLocalHost();
					String md5 = MD5.encrypt(agent.getEmail());
					params.put("$url$", url1 + "index.jsp");
					String result = MailUtil.sslSend("修改登录密码", "0003", agent
							.getEmail(), params,Certification.getProtocol()); // 发邮件
					return mapping.findForward("infochangepasswordsuccess");
				} else {
					request.setAttribute("error", "error");
					actionForm.setUrl(BASE64.encrypt(url));
					actionForm.setEmail(map.get("agent_email"));
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("setPassword");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 无参数自动注册钱门帐号
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward auto_register_no_account(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		String result="0";
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateAutoRegister(map, url,
					gatewayMessage);
			// Agent agent = null;
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {

				Agent registerAgent = new Agent();
				// 如果email已经存在递归调用,再次获取新帐号验证
				for (int i = 0; i <= 0;) {
					registerAgent.setLoginName(registerAgent
							.getCreateQmAccount());
					registerAgent.setPassword("111111");
					registerAgent.setPayPassword("222222");
					if (agentBiz.checkAgent(registerAgent) == null) {
						registerAgent = agentBiz.autoRegister(registerAgent);
						if (registerAgent == null) {
							result = "0";
						} else {
							result = registerAgent.getLoginName();
						}

						i++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("0");
		}
		pw.print(result);
		return null;
	}

//	public ActionForward save_register_account(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		GatewayForm actionForm = (GatewayForm) form;
//		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
//		PrintWriter pw = response.getWriter();
//		String myurl = "";
//		String charset = "GBK";
//		String result = "";
//		HashMap<String, String> map = null;
//		try {
//			String url = BASE64.dencrypt(actionForm.getUrl());
//			System.out.println("---URL--" + url);
//			map = AnalyseParameter.analyseURL(url);
//			String msg = gatewayBiz.validateRegister(map, url, gatewayMessage);
//			// Agent agent = null;
//
//			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
//				actionForm.setMsg(msg);
//				request.setAttribute("GatewayForm", actionForm);
//				return mapping.findForward("error");
//			} else {
//
//				Agent registerAgent = new Agent();
//				// 如果email已经存在递归调用,再次获取新帐号验证
//				int i = 0;
//				for (i = 0; i <= 0;) {
//					registerAgent.setLoginName(registerAgent
//							.getCreateQmAccount());
//					registerAgent.setPassword(actionForm.getPassword());
//					registerAgent.setPayPassword(actionForm.getPayPassword());
//					if (agentBiz.checkAgent(registerAgent) == null) {
//						registerAgent = agentBiz.autoRegister(registerAgent);
//						if (registerAgent == null) {
//							result = "0";
//						} else {
//							result = registerAgent.getLoginName();
//						}
//
//						i++;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			pw.print("0");
//		}
//		pw.print(result);
//		return null;
//	}
	public ActionForward set_pay_password(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		boolean relust_send_email = false;
		try {
			String url = BASE64.dencrypt(actionForm.getUrl());
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateDirectChangePassword(map, url,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				actionForm.setMsg(msg);
				request.setAttribute("GatewayForm", actionForm);
				return mapping.findForward("error");
			} else {
				Agent agent = agentBiz.getAgentByLoginName(map
						.get("agent_email"));

				if (agent.getPassword().equals(
						MD5.encrypt(actionForm.getRepassword()))) {
					request.setAttribute("error1", "error1");
					actionForm.setUrl(BASE64.encrypt(url));
					actionForm.setEmail(map.get("agent_email"));
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("setPayPassword");
				}
				if (!actionForm.getRepassword().equals("")
						&& actionForm.getRepassword().equals(
								actionForm.getPassword())) {

					agent.setPayPassword(MD5.encrypt(actionForm.getPassword()));
					agentBiz.updateAgent(agent);
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("$RealyName$", agent.getName());
					String url1 = Constant.getLocalHost();
					String md5 = MD5.encrypt(agent.getEmail());
					params.put("$url$", url1 + "index.jsp");
					String result = MailUtil.sslSend("修改支付密码", "0003", agent.getEmail(), params,Certification.getProtocol()); // 发邮件
					return mapping.findForward("infochangepaypasswordsuccess");
				} else {
					request.setAttribute("error", "error");
					actionForm.setUrl(BASE64.encrypt(url));
					actionForm.setEmail(map.get("agent_email"));
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("setPayPassword");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	public ActionForward rapid_change_agent_password(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws IOException {
			GatewayForm actionForm = (GatewayForm) form;
			GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
			PrintWriter pw = response.getWriter();
			try {
				String url = AnalyseParameter.decode(request.getQueryString(),
						request.getParameter("_input_charset"));
				HashMap<String, String> map = AnalyseParameter.analyseURL(url);
				String msg = gatewayBiz.validateRapidChangePassword(map, url,
						gatewayMessage);
				if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
					actionForm.setMsg(msg);
					request.setAttribute("GatewayForm", actionForm);
					return mapping.findForward("error");
				} else {
					Agent agent = agentBiz.getAgentByLoginName(map
							.get("agent_email"));
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("rapidChangePassword");
	}
	
	/**
	 * 手机支付验证码校验
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward validate_mobile_identifying_code(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		GatewayForm actionForm = (GatewayForm) form;
		GatewayMessage gatewayMessage = GatewayMessageFactory.getMessage(true);
		PrintWriter pw = response.getWriter();
		try {
			String url = AnalyseParameter.decode(request.getQueryString(),
					request.getParameter("_input_charset"));
			HashMap<String, String> map = AnalyseParameter.analyseURL(url);
			String msg = gatewayBiz.validateMobileIdentifyingCode(map, url,
					gatewayMessage);
			if (!gatewayMessage.getSuccess().equals(msg)) { // 验证URL
				pw.print("3,"+msg);
				pw.close();
				return null;
			} else {
				Agent userAgent =agentBiz.getAgentByBindingMobilePay(map.get("mobile"));
				if (userAgent == null) {
					pw.print("4");
					pw.close();
					return null;
				} else {
					TaskTimestamp ts = tasktimestampBiz.getTaskTimestamp(userAgent,
						TaskTimestamp.type_8);
					if (null==ts||!map.get("code").equals(ts.getNo())) {
						//pw.print("手机验证码有误");
						pw.print("0");
						pw.close();
						return null;
					}
					if(ts.getStatus()==0){
						//pw.print("验证码已过期");
						pw.print("1");
						pw.close();
						return null;
					}
					pw.print("2");//验证通过
					pw.close();
					return null;
				}
				
			}
		} catch (Exception e) {
			
			pw.print(e.getMessage());
			pw.print("5");
			pw.close();
			return null;
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
	

}