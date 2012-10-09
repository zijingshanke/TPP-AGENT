package com.fordays.fdpay.cooperate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.transaction.OrderDetails;
import com.neza.encrypt.MD5;

public class CoterieUtilURL {
	public static String[] directPayRedirectUrl(HashMap<String, String> map,
			String notifyId, String tranNo, Agent userAgent,
			String tradeStatus, String sysDate, String key,String encoding)
			throws UnsupportedEncodingException {
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
		parameter.append("&notify_id=");
		parameter.append(notifyId);
		parameter.append("&notify_time=");//
		parameter.append(sysDate);
		parameter.append("&notify_type=trade_status_sync");

		parameter.append("&out_trade_no=");
		parameter.append(map.get("out_trade_no"));
		parameter.append("&pay_email=");   
		parameter.append(map.get("pay_email"));
		
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
		parameter.append("&receive_email=");   
		parameter.append(map.get("receive_email"));
		
		if (map.get("royalty_parameters") != null) {
			parameter.append("&royalty_parameters=");
			//parameter.append(map.get("royalty_parameters"));
			parameter.append(URLEncoder.encode(map.get("royalty_parameters"), encoding));
		}
		parameter.append("&seller_email=");   
		parameter.append(map.get("seller_email"));
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
		

		//将中文进行不加密输出
		String[] str = parameter.toString().split("&");
		for (String temp : str) {
			if (temp.indexOf("body") >= 0) {
				temp = "body=" + map.get("body");
			} else if (temp.indexOf("subject") >= 0) {
				temp = "subject=" + map.get("subject");
			}else if(temp.indexOf("royalty_parameters")>=0){
				temp = "royalty_parameters=" + map.get("royalty_parameters");
			}
			sb.append("&");
			sb.append(temp);
			
		}
		

		// 添加签名
		String sign = MD5.encrypt(sb.substring(1).toString() + key,encoding);
		System.out.println(sb.substring(1).toString()+key);
		System.out.println(encoding);
		parameter.append("&sign=");
		parameter.append(sign);
		parameter.append("&sign_type=MD5");
		sb.append("&sign=");
		sb.append(sign);
		sb.append("&sign_type=MD5");
		
		
		System.out.println(">>>>>>>>url=:" + sb.toString());
		System.out.println(">>>>>>>>url1=:" + parameter.toString());
		String[] url = { parameter.toString(), sb.substring(1).toString() };
		return url;
	}
	
	//圈中圈信用支付退款（外买）异步通知url
	public static StringBuffer coterieDirectRefundRedirectUrl(String notifyId,String issucc,String service,
			String batchNo, String successNum, String payResultDetails,String buyerResultDetails,
			String sysDate, String key,String refund_type_buyer,String refund_type_pay) throws UnsupportedEncodingException {
		StringBuffer parameter = new StringBuffer();
		parameter.append("batch_no=");
		parameter.append(batchNo);
		parameter.append("&buyer_fee_result_details=");
		parameter.append(buyerResultDetails);
		parameter.append("&is_success=");
		parameter.append(issucc);
		parameter.append("&notify_id=");
		parameter.append(notifyId);
		parameter.append("&notify_time=");
		parameter.append(sysDate);
		parameter.append("&notify_type=batch_refund_notify");
		parameter.append("&pay_fee_result_details=");
		parameter.append(payResultDetails);
		parameter.append("&refund_type_buyer=");
		parameter.append(refund_type_buyer);
		parameter.append("&refund_type_pay=");
		parameter.append(refund_type_pay);
		parameter.append("&service=");
		parameter.append(service);
		parameter.append("&success_num=");
		parameter.append(successNum);

		String sign = MD5.encrypt(parameter.toString() + key);
		parameter.append("&sign=");
		parameter.append(sign);
		parameter.append("&sign_type=MD5");
		System.out.println(">>>>>>>>url=:" + parameter.toString());

		return parameter;
	}
	
	//交易委托解冻异步通知url
	public static StringBuffer unfreezeRedirectUrl(String key,String amount,
			String freeze_out_order_no,String notify_id,String notify_time,
			String notify_type,String pay_date,String result_code,String status,
			String trade_no,String unfreeze_out_order_no,String user_email,String user_id
			) throws UnsupportedEncodingException {
		StringBuffer parameter = new StringBuffer();
		parameter.append("amount=");
		parameter.append(amount);
		parameter.append("&freeze_out_order_no=");
		parameter.append(freeze_out_order_no);
		parameter.append("&notify_id=");
		parameter.append(notify_id);
		parameter.append("&notify_time=");//
		parameter.append(notify_time);
		parameter.append("&pay_date=");
		parameter.append(pay_date);
		parameter.append("&result_code=");
		parameter.append(result_code);
		parameter.append("&status=");
		parameter.append(status);
		parameter.append("&trade_no=");
		parameter.append(trade_no);
		parameter.append("&unfreeze_out_order_no=");
		parameter.append(unfreeze_out_order_no);
		parameter.append("&user_email=");
		parameter.append(user_email);
		parameter.append("&user_id=");
		parameter.append(user_id);
		String sign = MD5.encrypt(parameter.toString() + key);
		parameter.append("&sign=");
		parameter.append(sign);
		parameter.append("&sign_type=MD5");
		
		System.out.println(">>>>>>>>url=:" + parameter.toString());

		return parameter;
	}
}
