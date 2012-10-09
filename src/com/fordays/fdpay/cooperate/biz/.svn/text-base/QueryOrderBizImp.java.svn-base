package com.fordays.fdpay.cooperate.biz;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.cooperate.AnalyseParameter;
import com.fordays.fdpay.cooperate.CooperateLogUtil;
import com.fordays.fdpay.cooperate.QueryOrder;
import com.fordays.fdpay.cooperate.QueryOrderResult;
import com.fordays.fdpay.cooperate.action.QueryOrderAction;
import com.fordays.fdpay.cooperate.dao.ActionLogDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.exception.AppException;

/**
 * 商户订单查询业务实现类
 */
public class QueryOrderBizImp implements QueryOrderBiz {
	private AgentDAO agentDAO;
	private TransactionDAO transactionDAO;
	private CoterieDAO coterieDAO;
	private ActionLogDAO actionLogDAO;

	/**
	 * 执行查询请求校验，封装订单查询对象
	 */
	public QueryOrder getQueryOrder_SingleQuery(HttpServletRequest request)
			throws AppException {
		QueryOrder queryOrder = checkSingleQueryParameters(request);

		if (queryOrder.isValided()) {
			if (checkPatener(queryOrder).isValided()) {
				if (checkSingleQuerySign(queryOrder).isValided()) {
					return queryOrder;
				} else {
					return queryOrder;
				}
			} else {
				return queryOrder;
			}
		} else {
			return queryOrder;
		}
	}

	/**
	 * 处理订单请求，查询订单信息，反馈相关信息
	 * 
	 * @param QueryOrder
	 * @param HtttpServletResponse
	 */
	public void executeQueryOrder(QueryOrder queryOrder,
			HttpServletResponse response) throws AppException {
		QueryOrderResult queryResult = null;
		if (queryOrder.isValided()) {
			String service = queryOrder.getService();

			if ("single_trade_query".equals(service)) {
				queryResult = getQueryOrderResult_SingleQuery(queryOrder);
			} else {
				System.out.println("executeQueryOrder() 不可预期的错误。。");
			}

			if ("T".equals(queryResult.getIs_success())) {
				printQueryOrderResult(queryResult, response);
			} else {
				printQueryErrorInfo(queryResult.getQmpayRemark(), response);
			}
		} else {
			printQueryErrorInfo(queryOrder.getValidedRemark(), response);
		}
	}

	/**
	 * 单笔查询参数格式校验
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return QueryOrder queryOrder
	 */
	private QueryOrder checkSingleQueryParameters(HttpServletRequest request) {
		String service = request.getParameter("service");
		String partner = request.getParameter("partner");
		String sign = request.getParameter("sign");
		String sign_type = request.getParameter("sign_type");
		String out_trade_no = request.getParameter("out_trade_no");
		String _input_charset = request.getParameter("_input_charset");

		QueryOrder queryOrder = new QueryOrder();
		if (service == null || "".equals(service) || partner == null
				|| "".equals(partner) || sign == null || "".equals(sign)
				|| sign_type == null || "".equals(sign_type)
				|| out_trade_no == null || "".equals(out_trade_no)
				|| _input_charset == null || "".equals(_input_charset)) {
			queryOrder.setValided(false);
			queryOrder.setValidedRemark("参数不能为空");
		} else {
			queryOrder.setValided(true);
			queryOrder.setValidedRemark("参数基本验证成功");
			queryOrder.setService(service);
			queryOrder.setPartner(partner);
			queryOrder.setSign(sign);
			queryOrder.setSign_type(sign_type);
			queryOrder.setOut_trade_no(out_trade_no);
			queryOrder.set_input_charset(_input_charset);
		}
		printQueryOrderLog(queryOrder);
		return queryOrder;
	}

	/**
	 * 单笔查询参数签名校验
	 */
	private QueryOrder checkSingleQuerySign(QueryOrder queryOrder) {
		try {
			boolean flag = AnalyseParameter.validateSign(queryOrder
					.getSigleQueryUrl(), queryOrder.getSign(), queryOrder
					.getSign_type(), queryOrder.getPartenerKey(), queryOrder
					.get_input_charset());
			if (flag) {
				queryOrder.setValided(true);
				queryOrder.setValidedRemark("验证签名成功");
			} else {
				queryOrder.setValided(false);
				queryOrder.setValidedRemark("签名错误");
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		printQueryOrderLog(queryOrder);
		return queryOrder;
	}

	/**
	 * 检查合作伙伴ID
	 * 
	 * @param QueryOrder
	 *            queryOrder
	 */
	private QueryOrder checkPatener(QueryOrder queryOrder) {
		Coterie coterie = null;
		try {
			String partner = queryOrder.getPartner();
			coterie = coterieDAO.getCoterieByPartner(partner);
		} catch (AppException e) {
			e.printStackTrace();
		}
		if (coterie == null || coterie.getId() == 0l) {
			queryOrder.setValided(false);
			queryOrder.setValidedRemark("coterie is null..");
		} else {
			if (coterie.getAgent() == null || coterie.getAgent().getId() == 0l) {
				queryOrder.setValided(false);
				queryOrder.setValidedRemark("商户未签署协议");
			} else {
				queryOrder.setValided(true);
				queryOrder.setPartenerKey(coterie.getSignKey());
				queryOrder.setValidedRemark("合作伙伴ID校验成功");
			}
		}
		printQueryOrderLog(queryOrder);
		return queryOrder;
	}

	/**
	 * 根据查询订单请求获取订单结果
	 */
	private QueryOrderResult getQueryOrderResult_SingleQuery(
			QueryOrder queryOrder) throws AppException {
		QueryOrderResult orderResult = null;

		String out_trade_no = queryOrder.getOut_trade_no();
		String partnerId = queryOrder.getPartner();

		QueryOrderResult queryResult = transactionDAO.getQueryOrderResult(
				out_trade_no, partnerId);

		if (queryResult == null) {
			queryResult = new QueryOrderResult();
			queryResult.setIs_success("F");
			queryResult.setQmpayRemark("不存在的订单号");
			printQueryOrderResultLog(queryResult);			
		} else {
			OrderDetails detail = queryResult.getOrderDetails();
			Agent buyer = queryResult.getBuyer();

			orderResult = new QueryOrderResult();

			orderResult.setIs_success("T");
			orderResult.setBuyer_email(buyer.getEmail());
			orderResult.setFinish_date(detail.getFinishDate().toString());//
			orderResult.setOut_trade_no(out_trade_no);
			orderResult.setShop_price(BankUtil.getStringByBigDecimal(detail
					.getShopPrice()));
			orderResult.setQuantity(BankUtil.getStringByLong(detail
					.getShopTotal()));
			orderResult.setTotal_fee(BankUtil.getStringByBigDecimal(detail
					.getPaymentPrice()));
			orderResult.setTrade_no(detail.getOrderDetailsNo());

			long status = detail.getStatus();
			orderResult.setStatus(String.valueOf(status));
			orderResult.setQmpayRemark(OrderDetails
					.getOrderDetailStatusRem(status));
			printQueryOrderResultLog(orderResult);
			return orderResult;
		}

		return queryResult;
	}

	/**
	 * 打印订单查询结果XML
	 * 
	 * @param QueryOrderResult
	 *            result
	 * @param HttpServletResponse
	 *            response
	 */
	private void printQueryOrderResult(QueryOrderResult result,
			HttpServletResponse response) throws AppException {
		LogUtil myLog = new CooperateLogUtil(false, false,
				QueryOrderAction.class, "");
		PrintWriter pw = getPrintWriter(response);

		StringBuffer xmlresult = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

		xmlresult.append("<qmpay>");
		xmlresult.append("<is_success>").append("T").append("</is_success>");
		xmlresult.append("<response>");
		xmlresult.append("<trade>");
		xmlresult.append("<buyer_email>").append(result.getBuyer_email())
				.append("</buyer_email>");
		xmlresult.append("<finish_date>").append(result.getFinish_date())
				.append("</finish_date>");
		xmlresult.append("<out_trade_no>").append(result.getOut_trade_no())
				.append("</out_trade_no>");
		xmlresult.append("<quantity>").append(result.getQuantity()).append(
				"</quantity>");
		xmlresult.append("<shop_price>").append(result.getShop_price()).append(
				"</shop_price>");
		xmlresult.append("<total_fee>").append(result.getTotal_fee());
		xmlresult.append("</total_fee>");
		xmlresult.append("<trade_no>").append(result.getTrade_no()).append(
				"</trade_no>");
		xmlresult.append("<status>").append(result.getStatus()).append(
				"</status>");
		xmlresult.append("<qmpayRemark>").append(result.getQmpayRemark())
				.append("</qmpayRemark>");
		xmlresult.append("</trade>");
		xmlresult.append("</response>");
		xmlresult.append("</qmpay>");

		pw.println(xmlresult);

		myLog.info(xmlresult.toString());
	}

	/**
	 * 
	 */
	private void printQueryErrorInfo(String error,
			HttpServletResponse response) throws AppException {
		PrintWriter pw = getPrintWriter(response);
		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		pw.println("<qmpay>");
		pw.println("<is_success>");
		pw.println("F");
		pw.println("</is_success>");
		pw.println("<error>");
		pw.println(error);
		pw.println("</error>");
		pw.println("</qmpay>");
	}

	private PrintWriter getPrintWriter(HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pw;
	}

	/**
	 * 打印QueryOrder相关信息到Log
	 */
	private static void printQueryOrderLog(QueryOrder queryOrder) {
		LogUtil myLog = new CooperateLogUtil(false, false,
				QueryOrderAction.class, "");
		myLog.info("QueryOrder no:" + queryOrder.getOut_trade_no());
		myLog.info("isValided:" + queryOrder.isValided());
		myLog.info("validinfo:" + queryOrder.getValidedRemark());
	}

	/**
	 * 打印QueryOrderResult相关信息到Log
	 */
	private static void printQueryOrderResultLog(
			QueryOrderResult queryOrderResult) {
		LogUtil myLog = new CooperateLogUtil(false, false,
				QueryOrderAction.class, "");
		myLog.info("Is_success:" + queryOrderResult.getIs_success());
		myLog.info("qmpayRemark:" + queryOrderResult.getQmpayRemark());
	}

	public QueryOrderResult getQueryOrderResult_BatchQuery(QueryOrder queryOrder)
			throws AppException {
		return null;
	}

	public QueryOrder getQueryOrder_BatchQuery(HttpServletRequest request)
			throws AppException {
		return null;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public void setCoterieDAO(CoterieDAO coterieDAO) {
		this.coterieDAO = coterieDAO;
	}

	public void setActionLogDAO(ActionLogDAO actionLogDAO) {
		this.actionLogDAO = actionLogDAO;
	}
}
