package com.fordays.fdpay.cooperate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.OrderDetails;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class UtilURL {
	
	//回调url说明：如果回调中url有中文则URL需编码  但加密是用没有编码URL 这里研究支付和退款就了然了
	
	//支付异步通知url
	public static String[] directPayRedirectUrl(HashMap<String, String> map,
			String notifyId, String tranNo, Agent userAgent,
			String tradeStatus, String sysDate, String key,String encoding)
			throws Exception {
		StringBuffer parameter = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		// set redirect url
		parameter.append("body=");
		if (map.get("body") != null) {
			parameter.append(URLEncoder.encode(map.get("body"),encoding));
		}
		parameter.append("&buyer_email=");
		parameter.append(userAgent.getEmail());
//		parameter.append("&buyer_id=");
//		parameter.append(String.valueOf(userAgent.getId()));
		parameter.append("&notify_id=");
		parameter.append(notifyId);
		parameter.append("&notify_time=");//
		parameter.append(sysDate);
		parameter.append("&notify_type=trade_status_sync");

		parameter.append("&out_trade_no=");
		parameter.append(map.get("out_trade_no"));
		parameter.append("&payment_type=");
		parameter.append(map.get("payment_type"));
		if (map.get("price") != null) {
			parameter.append("&price=");
			parameter.append(map.get("price"));
		}
		if (map.get("quantity") != null) {
			parameter.append("&quantity=");
			parameter.append(map.get("quantity"));
		}
//		if (map.get("seller_id") != null) {
//			parameter.append("&seller_id=");
//			parameter.append(map.get("seller_id"));
//		}
		if (map.get("seller_email") != null) {
			parameter.append("&seller_email=");
			parameter.append(map.get("seller_email"));
		}
		parameter.append("&service=");   
		parameter.append(map.get("service"));
		if (map.get("subject") != null) {
			parameter.append("&subject=");
			parameter.append(URLEncoder.encode(map.get("subject"), encoding));
		}
		parameter.append("&total_fee=");
		parameter.append(map.get("total_fee"));
		parameter.append("&trade_no=");
		parameter.append(tranNo);
		parameter.append("&trade_status=");   
		parameter.append(tradeStatus);

		String[] str = parameter.toString().split("&");
		for (String temp : str) {
			if (temp.indexOf("body") >= 0) {
				temp = "body=" + map.get("body");
			} else if (temp.indexOf("subject") >= 0) {
				temp = "subject=" + map.get("subject");
			}
			sb.append("&");
			sb.append(temp);
		}

		// 添加签名
		//String sign = MD5.encrypt(sb.substring(1).toString() + key,encoding);
		String urlsign = UtilURL.urlSort(sb.substring(1).toString(), "MD5", key,"UTF-8");
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");
		sb.append("&sign=");
		//sb.append(sign);
		sb.append(urlsign);
		sb.append("&sign_type=MD5");

		String[] url = { parameter.toString(), sb.substring(1).toString() };
		return url;
	}
	
	//支付退款异步通知url
	public static StringBuffer directRefundRedirectUrl(String notifyId,String issucc,String service,
			String batchNo, String successNum, String resultDetails,
			String sysDate, String key,String refundType) throws Exception {
		StringBuffer parameter = new StringBuffer();
		parameter.append("batch_no=");
		parameter.append(batchNo);
		parameter.append("&is_success=");
		parameter.append(issucc);
		parameter.append("&notify_id=");
		parameter.append(notifyId);
		parameter.append("&notify_time=");//
		parameter.append(sysDate);
		parameter.append("&notify_type=batch_refund_notify");
		parameter.append("&refund_type=");
		parameter.append(refundType);
		parameter.append("&result_details=");
		parameter.append(resultDetails);
		parameter.append("&service=");
		parameter.append(service);
		parameter.append("&success_num=");
		parameter.append(successNum);

		String urlsign = UtilURL.urlSort(parameter.toString(), "MD5", key,"UTF-8");
		//String sign = MD5.encrypt(parameter.toString() + key);
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");

		return parameter;
	}
	
	//授信还款异步通知url
	public static StringBuffer directRepaymentRedirectUrl (HashMap<String, String> map, String key,String encoding) throws Exception {
		StringBuffer parameter = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		parameter.append("agent_email="+map.get("agent_email"));
		sb.append("agent_email="+map.get("agent_email"));
		parameter.append("&body=");
		if (map.get("body") != null) {
			parameter.append(URLEncoder.encode(map.get("body"),encoding));
			sb.append("&body="+map.get("body"));
		}
		parameter.append("&out_trade_no="+map.get("out_trade_no"));
		sb.append("&out_trade_no="+map.get("out_trade_no"));
		parameter.append("&repayment_amount="+map.get("repayment_amount"));
		sb.append("&repayment_amount="+map.get("repayment_amount"));
		parameter.append("&repayment_type="+map.get("repayment_type"));
		sb.append("&repayment_type="+map.get("repayment_type"));
		parameter.append("&result="+map.get("result"));
		sb.append("&result="+map.get("result"));
		parameter.append("&subject=");
		if (map.get("subject") != null) {
			parameter.append(URLEncoder.encode(map.get("subject"),encoding));
			sb.append("&subject="+map.get("subject"));
		}
		System.out.println("签名数据:"+sb.toString());
		System.out.println("签名的key:"+key);
		//String sign = MD5.encrypt(sb.toString() + key,encoding);
		String urlsign = UtilURL.urlSort(sb.toString(), "MD5", key,"UTF-8");
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");
		
		return parameter;
	}

	// 信用支付外买 
	public static String[] directPayRedirectUrl(HashMap<String, String> map,
			String notifyId, String tranNo, Agent userAgent,
			String tradeStatus,String returnparam, String sysDate, String key,String encoding)
			throws Exception {
		StringBuffer parameter = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		// set redirect url
		parameter.append("body=");
		if (map.get("body") != null) {
			parameter.append(URLEncoder.encode(map.get("body"),encoding));
		}
		parameter.append("&buyer_email=");
		parameter.append(userAgent.getEmail());
		parameter.append("&buyer_fee=");
		parameter.append(map.get("buyer_fee"));
		//parameter.append("&buyer_id=");
		//parameter.append(String.valueOf(userAgent.getId()));
//		if (map.get("buyerpaner_id") != null) {
//			parameter.append("&buyerpaner_id=");
//			parameter.append(map.get("buyerpaner_id"));
//		} 
		if (map.get("buyerpaner_email") != null) {
			parameter.append("&buyerpaner_email=");
			parameter.append(map.get("buyerpaner_email"));
		}
		parameter.append("&notify_id=");
		parameter.append(notifyId);
		parameter.append("&notify_time=");//
		parameter.append(sysDate);
		parameter.append("&notify_type=trade_status_sync");
		parameter.append("&out_trade_no=");
		parameter.append(map.get("out_trade_no"));
		parameter.append("&pay_fee=");
		parameter.append(map.get("pay_fee"));
		parameter.append("&payment_type=");
		parameter.append(map.get("payment_type"));
		if (map.get("price") != null) {
			parameter.append("&price=");
			parameter.append(map.get("price"));
		}
		if (map.get("quantity") != null) {
			parameter.append("&quantity=");
			parameter.append(map.get("quantity"));
		}
		//parameter.append("&returnParameter=");// 返回处理情况集合
		//parameter.append(returnparam);
//		if (map.get("seller_id") != null) {
//			parameter.append("&seller_id=");
//			parameter.append(map.get("seller_id"));
//		} 
		if (map.get("seller_email") != null) {
			parameter.append("&seller_email=");
			parameter.append(map.get("seller_email"));
		}
		parameter.append("&service=");   
		parameter.append(map.get("service"));
		if (map.get("subject") != null) {
			parameter.append("&subject=");
			parameter.append(URLEncoder.encode(map.get("subject"), encoding));
		}
		parameter.append("&total_fee=");
		parameter.append(map.get("total_fee"));
		parameter.append("&trade_no=");
		parameter.append(tranNo);
		parameter.append("&trade_status=");   
		parameter.append(tradeStatus);

		String[] str = parameter.toString().split("&");
		for (String temp : str) {
			if (temp.indexOf("body") >= 0) {
				temp = "body=" + map.get("body");
			} else if (temp.indexOf("subject") >= 0) {
				temp = "subject=" + map.get("subject");
			}
			sb.append("&");
			sb.append(temp);
		}
		
		// 添加签名
		//String sign = MD5.encrypt(sb.substring(1).toString() + key,encoding);
		String urlsign = UtilURL.urlSort(sb.substring(1).toString(), "MD5", key,"UTF-8");
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");
		sb.append("&sign=");
		//sb.append(sign);
		sb.append(urlsign);
		sb.append("&sign_type=MD5");

		String[] url = { parameter.toString(), sb.substring(1).toString() };
		return url;
	}
	
	//信用支付退款（外买）异步通知url
	public static StringBuffer directRefundRedirectUrlOut(String notifyId,String issucc,String service,
			String batchNo, String[] brd, String[] prd,String sysDate, 
			String key,String refundType) throws Exception {
		int sNum = Integer.parseInt(brd[0])+Integer.parseInt(prd[0]);
		StringBuffer parameter = new StringBuffer();
		parameter.append("batch_no=");
		parameter.append(batchNo);
		parameter.append("&buyer_fee_result_details=");
		parameter.append(brd[1]);
		parameter.append("&is_success=");
		parameter.append(issucc);
		parameter.append("&notify_id=");
		parameter.append(notifyId);
		parameter.append("&notify_time=");//
		parameter.append(sysDate);
		parameter.append("&notify_type=batch_refund_notify");
		parameter.append("&pay_fee_result_details=");
		parameter.append(prd[1]);
		parameter.append("&refund_type=");
		parameter.append(refundType);
		parameter.append("&service=");
		parameter.append(service);
		parameter.append("&success_num=");
		parameter.append(sNum);

		String urlsign = UtilURL.urlSort(parameter.toString(), "MD5", key,"UTF-8");
		//String sign = MD5.encrypt(parameter.toString() + key);
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");
	
		return parameter;
	}
	
	// 解冻回调url
	public static String allow_directUrl(HashMap<String, String> map,
			OrderDetails order,String sysDate, String key,
			String fails_details,String success_details
			,String trade_status) throws Exception {
		StringBuffer parameter = new StringBuffer();
		parameter.append("fails_details=");
		parameter.append(fails_details);
		parameter.append("&notify_id=");
		parameter.append(order.getId());
		parameter.append("&notify_time=");
		parameter.append(sysDate);
		parameter.append("&notify_type=trade_status_sync");//
		parameter.append("&out_trade_no=");
		parameter.append(map.get("out_trade_no"));
		parameter.append("&success_details=");
		parameter.append(success_details);
		parameter.append("&trade_no=");
		parameter.append(order.getOrderDetailsNo());
		parameter.append("&trade_status=");
		parameter.append(trade_status);
		
		String urlsign = UtilURL.urlSort(parameter.toString(), "MD5", key,"UTF-8");
		//String sign = MD5.encrypt(parameter.toString() + key);
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");

		return parameter.toString();
	}
	
	//委托冻结异步通知url
	public static StringBuffer createFreezeUrl(Map<String, String> 
		map,String sb,OrderDetails order,String key,Date date) 
			throws Exception {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] free = sb.split("\\^");
		StringBuffer parameter = new StringBuffer();
		parameter.append("amount=");
		parameter.append(free[3]);
		parameter.append("&freeze_out_order_no=");
		parameter.append(free[0]);
		parameter.append("&notify_id=");
		parameter.append(order.getId());
		parameter.append("&notify_time=");
		parameter.append(f.format(date));
		parameter.append("&notify_type=trade_status_async");
		parameter.append("&pay_date=");
		parameter.append(free[4]);
		parameter.append("&result_code=");
		parameter.append(free[6]);
		parameter.append("&status=");
		parameter.append(free[5]);
		parameter.append("&trade_no=");
		parameter.append(order.getOrderDetailsNo());
		if(null!=free[1]&&!"".equals(free[1].trim())){
			parameter.append("&user_email=");
			parameter.append(free[1]);
		}
		if(null!=free[2]&&!"".equals(free[2].trim())){
			parameter.append("&user_id=");
			parameter.append(free[2]);
		}
		String urlsign = UtilURL.urlSort(parameter.toString(), "MD5", key,"UTF-8");
		//String sign = MD5.encrypt(parameter.toString() + key);
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");
	
		return parameter;
	}
	
	//url参数排序加密
    public static String urlSort(String url,String signType,
    		String partnerKey, String inCharSet)
			throws AppException{
    	String encrypt = null;
		StringBuffer strTemp = new StringBuffer();
		String charSet = "GBK";
		if (url == null||"".equals(url)) {
			return "";
		}
		if (inCharSet != null && !"".equals(inCharSet)) {
			charSet = inCharSet;
		}
		//把%20更换为" "
		//url = url.replaceAll("%20", " ");

		String[] strArray = url.split("&");
		List<String> list = new ArrayList<String>();
		for (String str : strArray) {
			if (str.indexOf("_input_charset") < 0 && str.indexOf("sign=") < 0
					&& str.indexOf("sign_type") < 0) {
				list.add(str);
			}
		}
		ConnAnalyseParameter.sort(list);

		strTemp.delete(0, strTemp.length());
		for (String str : list) {
			strTemp.append("&");
			strTemp.append(str);
		}
		//在末尾加上signkey
		strTemp.append(partnerKey);
		if (signType == null || "MD5".equals(signType)) {
			encrypt = MD5.encrypt(strTemp.substring(1).toString().trim(),
					charSet);
		} else if ("BASE64".equals(signType)) {
			encrypt = BASE64.encrypt(strTemp.substring(1).toString());
		}
    	return encrypt;
    }
    
  //招标冻结标款异步通知url
	public static StringBuffer create_recruit_FreezeUrl(Map<String, String> 
		map,String[] code,OrderDetails order,String key) 
			throws Exception {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = DateUtil.getTimestamp(DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss"),
				"yyyy-MM-dd HH:mm:ss");
		StringBuffer parameter = new StringBuffer();
		StringBuffer sb = new StringBuffer();
		parameter.append("body=");
		if (map.get("body") != null) {
			parameter.append(URLEncoder.encode(map.get("body"),"UTF-8"));
		}
		parameter.append("&freeze_email=");
		parameter.append(order.getAgent().getLoginName());
		if(map.get("freeze_id")!=null){
			parameter.append("&freeze_id=");
			parameter.append(map.get("freeze_id"));	
		}
		parameter.append("&notify_id=");
		parameter.append(order.getId());
		parameter.append("&notify_time=");
		parameter.append(f.format(date));
		parameter.append("&notify_type=trade_status_async");
		parameter.append("&out_trade_no=");
		parameter.append(order.getOrderNo());
		parameter.append("&result_code=");
		parameter.append(code[1]);
		parameter.append("&service=");
		parameter.append(map.get("service"));
		parameter.append("&status=");
		parameter.append(code[0]);
		parameter.append("&subject=");
		parameter.append(URLEncoder.encode(order.getShopName(),"UTF-8"));
		parameter.append("&total_fee=");
		parameter.append(order.getPaymentPrice());
		parameter.append("&trade_no=");
		parameter.append(order.getOrderDetailsNo());
		
		String[] str = parameter.toString().split("&");
		for (String temp : str) {
			if (temp.indexOf("body") >= 0) {
				temp = "body=" + map.get("body");
			} else if (temp.indexOf("subject") >= 0) {
				temp = "subject=" + map.get("subject");
			}
			sb.append("&");
			sb.append(temp);
		}
		
		//String sign = MD5.encrypt(sb.substring(1).toString() + key,encoding);
		String urlsign = UtilURL.urlSort(sb.substring(1).toString(), "MD5", key,"UTF-8");
		parameter.append("&sign=");
		//parameter.append(sign);
		parameter.append(urlsign);
		parameter.append("&sign_type=MD5");
		sb.append("&sign=");
		//sb.append(sign);
		sb.append(urlsign);
		sb.append("&sign_type=MD5");

		return parameter;
	}
	
    
    
    public static void main(String[] args) {
    	String key="fdshkfnds41364634";
		StringBuffer aa = new StringBuffer();
		aa.append("a=fdsaf");
		aa.append("&d=41534");
		aa.append("&e=13");
		aa.append("&f=ko;p");
		aa.append("&b=fdsfd");
		aa.append("&c=fdnsk");
		aa.append("&w_a=fdnsk");
		aa.append("gg=fdsfds");
		String sign = MD5.encrypt(aa.toString() + key);
		System.out.println(sign);
		try {
			String sign2 = UtilURL.urlSort(aa.toString(), "MD5", key, "UTF-8");
			System.out.println(sign2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
