package com.fordays.fdpay.cooperate.action;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.cooperate.HttpInvoker;
import com.fordays.fdpay.cooperate.QueryOrder;
import com.fordays.fdpay.cooperate.biz.QueryOrderBiz;
import com.neza.base.BaseAction;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;

/**
 * 商户订单查询接口
 */
public class QueryOrderAction extends BaseAction {
	private QueryOrderBiz queryOrderBiz;

	/**
	 * 单笔订单查询
	 */
	public ActionForward single_trade_query(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		QueryOrder queryOrder = queryOrderBiz
				.getQueryOrder_SingleQuery(request);

		queryOrderBiz.executeQueryOrder(queryOrder, response);
		return null;
	}

	public static void main(String[] args) {
		String url = "http://jp.fdays.com/Airplane/Stock/QmPayReturn.aspx?body=支付人:泰申假期-订单号:B09072315321604654-PNR:VKP0D-行程:北京-沈阳&buyer_email=971386511@qq.com&buyer_id=193&notify_id=2910&notify_time=2009-07-23 15:27:35&notify_type=trade_status_sync&out_trade_no=B09072315321604654&payment_type=1&seller_email=fdays@126.com&subject=机票预订:VKP0D&total_fee=1196.00&trade_no=O20090723000053&trade_status=TRADE_SUCCESS&sign=77044bf51b00d3fa3224d142a641beb6&sign_type=MD5";
		// printHttpInvokerResult(url);
		printTestUrl2();
	}

	public static void printHttpInvokerResult(String url) {
		try {
			String result = HttpInvoker.readContentFromPost(url, "");
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printTestUrl() {
		String requesturl = "http://localhost/fdpay-client/cooperate/queryOrder.do?_input_charset=UTF-8&";
		String url = "";
		url += "out_trade_no=B09072315321604654";
		url += "&partner=2088001636104894";
		url += "&service=single_trade_query";
		System.out.println(url);
		String myurl = url + "stpsyw03edwnr1oplj5cevnzj7r1qu73";
		System.out.println(myurl);
		String sign = MD5.encrypt(myurl, "UTF-8");
		System.out.println(sign);
		url += "&sign=" + sign;
		url += "&sign_type=MD5";
		System.out.println(requesturl + url);
	}

	public static void printTestUrl2() {
		String requesturl = "http://localhost/fdpay-client";
		requesturl = "https://www.qmpay.com";
		requesturl += "/cooperate/gateway.do?_input_charset=UTF-8&";
		String url = "";
		url += "agent_email=fdays@126.com";// 
		url += "&partner=2088001636104894";
		url += "&service=get_agent_balance";
		System.out.println(url);
		String myurl = url + "stpsyw03edwnr1oplj5cevnzj7r1qu73";
		System.out.println(myurl);
		String sign = MD5.encrypt(myurl, "UTF-8");
		System.out.println(sign);
		url += "&sign=" + sign;
		url += "&sign_type=MD5";
		System.out.println(requesturl + url);
	}

	public void setQueryOrderBiz(QueryOrderBiz queryOrderBiz) {
		this.queryOrderBiz = queryOrderBiz;
	}
}
