package com.fordays.fdpay.cooperate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.tool.DateUtil;

public class TestUrl {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException{
		// TODO Auto-generated method stub
		
		TestUrl.pay(); //支付
		
		//TestUrl.createpay();//信用支付
		
		//TestUrl.createpayout();//信用支付(外买)
		
		//TestUrl.allowURL();//解冻
		
		//TestUrl.repayURL();//退款
		
		//TestUrl.repayCreditURL();//信用支付(外买)退款
		
		//creditrepayURL();//授信还款
		
//		TestUrl.freezeURL();//委托冻结
		
		//TestUrl.unFreezeURL(); //委托解冻
		
		//TestUrl.freezeQueryURL();//X委托冻结查询
		
		//TestUrl.recruitFreezeURL();//冻结标款
		
		//TestUrl.recruitFreezepay();//招标冻结支付
		
		//TestUrl.sendConfirmURL();//手机支付发送订单短信接口
		
//		TestUrl.validataURL();  //手机支付验证码校验
		
		//TestUrl.mobilePay();// 手机支付
		
		//urlrecode();
	}

	/*
	 * 支付
	 */
	public static void pay() throws UnsupportedEncodingException {
		String url = "http://localhost:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
			
		String url_par = "body=" + URLEncoder.encode("支付人:649010333@qq.com","UTF-8");
		//url_par+="&buyer_email=649010333@qq.com";		
		url_par += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_par += "&out_trade_no=201003250004";
		url_par += "&partner=2088001636104898";		
		url_par += "&payment_type=1";
		url_par += "&price=220.00";
		url_par += "&quantity=1";
		url_par += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_par += "&royalty_parameters=" + URLEncoder.encode("zh-aptechbving@163.com^190^林尚沃测试","UTF-8")
		+"|"+URLEncoder.encode("kenny010@126.com^30^林尚沃测试","UTF-8");
//		+"|"+URLEncoder.encode("zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...","UTF-8");
		url_par += "&royalty_type=10";
		url_par += "&seller_email=zh-aptechbving@163.com";
		url_par += "&service=create_direct_pay_by_user";
		url_par += "&subject="+URLEncoder.encode("测试即时到帐支付","UTF-8");
		url_par += "&total_fee=220";
		
		
		String url_md5 = "body=支付人:649010333@qq.com";
		//url_md5+="&buyer_email=649010333@qq.com";
		url_md5 += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_md5 += "&out_trade_no=201003250004";
		url_md5 += "&partner=2088001636104898";	
		url_md5 += "&payment_type=1";
		url_md5 += "&price=220.00";
		url_md5 += "&quantity=1";
		url_md5 += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_md5 += "&royalty_parameters=zh-aptechbving@163.com^190^林尚沃测试"
		+"|kenny010@126.com^30^林尚沃测试";
//		+"|zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...";
		url_md5 += "&royalty_type=10";
		url_md5 += "&seller_email=zh-aptechbving@163.com";
		url_md5 += "&service=create_direct_pay_by_user";
		url_md5 += "&subject=测试即时到帐支付";
		url_md5 += "&total_fee=220";
		String url_md6 = "body=支付人：649010333@qq.com";
		
		System.out.println("要加密"+url_md5+"stpsyw03edwnr1oplj5cevnzj7r1qu78");
		
		
		String sign = "&sign="
				+ MD5.encrypt(url_md5 + "stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8")
				+ "&sign_type=MD5";	
		
		System.out.println("sign=    "+sign);
		
		
		System.out.println("原url:"+url+url_md5+sign);
		System.out.println(url+url_par+sign); 
	}
	
	
	/*
	 * 信用支付
	 */
	public static void createpay() throws UnsupportedEncodingException {
		String url = "http://localhost:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
			
		String url_par = "body=" + URLEncoder.encode("支付人:649010333@qq.com","UTF-8");
		url_par+="&buyer_email=649010333@qq.com";		
		url_par += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_par += "&out_trade_no=20100301000001";
		url_par += "&partner=2088001636104898";		
		url_par += "&payment_type=2";
		url_par += "&price=220.00";
		url_par += "&quantity=1";
		url_par += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_par += "&royalty_parameters=" + URLEncoder.encode("zh-aptechbving@163.com^190^林尚沃测试","UTF-8")
		+"|"+URLEncoder.encode("kenny010@126.com^30^林尚沃测试","UTF-8");
//		+"|"+URLEncoder.encode("zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...","UTF-8");
		url_par += "&royalty_type=10";
		url_par += "&seller_email=zh-aptechbving@163.com";
		url_par += "&service=create_credit_pay_by_user";
		url_par += "&subject="+URLEncoder.encode("测试即时到帐支付","UTF-8");
		url_par += "&total_fee=220";
		url_par += "&type=2";
		
		String url_md5 = "body=支付人:649010333@qq.com";
		url_md5+="&buyer_email=649010333@qq.com";
		url_md5 += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_md5 += "&out_trade_no=20100301000001";
		url_md5 += "&partner=2088001636104898";	
		url_md5 += "&payment_type=2";
		url_md5 += "&price=220.00";
		url_md5 += "&quantity=1";
		url_md5 += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_md5 += "&royalty_parameters=zh-aptechbving@163.com^190^林尚沃测试"
		+"|kenny010@126.com^30^林尚沃测试";
//		+"|zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...";
		url_md5 += "&royalty_type=10";
		url_md5 += "&seller_email=zh-aptechbving@163.com";
		url_md5 += "&service=create_credit_pay_by_user";
		url_md5 += "&subject=测试即时到帐支付";
		url_md5 += "&total_fee=220";
		url_md5 += "&type=2";
				
		
		String sign = "&sign="
				+ MD5.encrypt(url_md5 + "stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8")
				+ "&sign_type=MD5";		
		System.out.println("原url:"+url+url_md5+sign);
		System.out.println(url+url_par+sign); 
	}
	
	/*
	 * 外买信用支付
	 */
	public static void createpayout() throws UnsupportedEncodingException {
		String url = "http://localhost:8080/fdpay-client/cooperate/credit.do?_input_charset=UTF-8&";
			
		String url_par = "body=" + URLEncoder.encode("支付人:649010333@qq.com","UTF-8");
		url_par+="&buyer_email=649010333@qq.com";
		url_par+="&buyer_fee=200";
		url_par+="&buyerpaner_email=fdays@163.com";
		url_par += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_par += "&out_trade_no=20100309001";
		url_par += "&partner=2088001636104898";
		url_par += "&pay_fee=150";
		url_par += "&payment_type=2";
		url_par += "&price=100";
		url_par += "&quantity=2";
		url_par += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_par += "&royalty_parameters=" + URLEncoder.encode("zh-aptechbving@163.com^150^林尚沃测试","UTF-8")
		+"|"+URLEncoder.encode("kenny010@126.com^50^林尚沃测试","UTF-8");
//		+"|"+URLEncoder.encode("zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...","UTF-8");
		url_par += "&royalty_type=10";
		url_par += "&seller_email=zh-aptechbving@163.com";
		url_par += "&service=create_credit_pay_by_user_out";
		url_par += "&subject="+URLEncoder.encode("测试(外买)","UTF-8");
		url_par += "&total_fee=200";
		url_par += "&type=2";
		
		
		String url_md5 = "body=支付人:649010333@qq.com";
		url_md5+="&buyer_email=649010333@qq.com";
		url_md5+="&buyer_fee=200";
		url_md5+="&buyerpaner_email=fdays@163.com";
		url_md5 += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_md5 += "&out_trade_no=20100309001";
		url_md5 += "&partner=2088001636104898";
		url_md5 += "&pay_fee=150";
		url_md5 += "&payment_type=2";
		url_md5 += "&price=100";
		url_md5 += "&quantity=2";
		url_md5 += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_md5 += "&royalty_parameters=zh-aptechbving@163.com^150^林尚沃测试"
		+"|kenny010@126.com^50^林尚沃测试";
//		+"|zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...";
		url_md5 += "&royalty_type=10";
		url_md5 += "&seller_email=zh-aptechbving@163.com";
		url_md5 += "&service=create_credit_pay_by_user_out";
		url_md5 += "&subject=测试(外买)";
		url_md5 += "&total_fee=200";
		url_md5 += "&type=2";
				
		
		String sign = "&sign="
				+ MD5.encrypt(url_md5 + "stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8")
				+ "&sign_type=MD5";		
		System.out.println("原url:"+url+url_md5+sign);
		System.out.println(url+url_par+sign); 
		
		
	}
	
	/*
	 * 圈中圈信用支付
	 */
	public static void createpaycredit() throws UnsupportedEncodingException {
		String url = "http://localhost:8080/fdpay-client/cooperate/agentBind.do?_input_charset=UTF-8&";
			
		String url_par = "body=" + URLEncoder.encode("支付人:649010333@qq.com","UTF-8");
		url_par+="&buyer_email=649010333@qq.com";
		url_par+="&buyer_fee=200";
		url_par+="&buyerpaner_email=fdays@163.com";
		url_par += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_par += "&out_trade_no=20100309001";
		url_par += "&partner=2088001636104898";
		url_par += "&pay_email=fdays_002@126.com";
		url_par += "&pay_fee=150";
		url_par += "&payment_type=2";
		url_par += "&price=100";
		url_par += "&quantity=2";
		url_par += "&receive_email=fdays@163.com";
		url_par += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_par += "&royalty_parameters=" + URLEncoder.encode("zh-aptechbving@163.com^150^林尚沃测试","UTF-8")
		+"|"+URLEncoder.encode("kenny010@126.com^50^林尚沃测试","UTF-8");
//		+"|"+URLEncoder.encode("zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...","UTF-8");
		url_par += "&royalty_type=10";
		url_par += "&seller_email=zh-aptechbving@163.com";
		url_par += "&service=create_coterie_direct_pay_by_user";
		url_par += "&subject="+URLEncoder.encode("测试(外买)","UTF-8");
		url_par += "&total_fee=200";
		url_par += "&type=2";
		
		
		String url_md5 = "body=支付人:649010333@qq.com";
		url_md5+="&buyer_email=649010333@qq.com";
		url_md5+="&buyer_fee=200";
		url_md5+="&buyerpaner_email=fdays@163.com";
		url_md5 += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_md5 += "&out_trade_no=20100309001";
		url_md5 += "&partner=2088001636104898";
		url_md5 += "&pay_email=fdays_002@126.com";
		url_md5 += "&pay_fee=150";
		url_md5 += "&payment_type=2";
		url_md5 += "&price=100";
		url_md5 += "&quantity=2";
		url_md5 += "&receive_email=fdays@163.com";
		url_md5 += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_md5 += "&royalty_parameters=zh-aptechbving@163.com^150^林尚沃测试"
		+"|kenny010@126.com^50^林尚沃测试";
//		+"|zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...";
		url_md5 += "&royalty_type=10";
		url_md5 += "&seller_email=zh-aptechbving@163.com";
		url_md5 += "&service=create_coterie_direct_pay_by_user";
		url_md5 += "&subject=测试(外买)";
		url_md5 += "&total_fee=200";
		url_md5 += "&type=2";
				
		
		String sign = "&sign="
				+ MD5.encrypt(url_md5 + "stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8")
				+ "&sign_type=MD5";		
		System.out.println("原url:"+url+url_md5+sign);
		System.out.println(url+url_par+sign); 	
	}
	
	
	/*
	 * 退款
	 */
	public static void repayURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
		String url1 = url ;
		String md5_url;
		
		url+="batch_no=20100301000008";
		url+="&batch_num=3";
		url+="&detail_data=" + URLEncoder.encode("O20100301000002^20.00^(退给买家)"
				+"$zh-aptechbving@163.com^^fdays@163.com^^10.00^货少一件:卖家zh-aptech"
				+"|kenny010@126.com^^fdays@163.com^^0^货少一件:分润账户kenny","UTF-8");;	
		url+="&notify_url=http://125.89.68.86:9012/Airplane/Ticket/CreditRefundNotify.aspx";
		url+="&partner=2088001636104898";
		url+="&refund_date=2010-02-19 11:10:45";
		url+="&refund_type=1";
		url+="&service=refund_fastpay_by_platform_nopwd";
		
	
		md5_url="batch_no=20100301000008";
		md5_url+="&batch_num=3";
		md5_url+="&detail_data=O20100301000002^20.00^(退给买家)"
			+"$zh-aptechbving@163.com^^fdays@163.com^^10.00^货少一件:卖家zh-aptech"
			+"|kenny010@126.com^^fdays@163.com^^0^货少一件:分润账户kenny";	
		md5_url+="&notify_url=http://125.89.68.86:9012/Airplane/Ticket/CreditRefundNotify.aspx";
		md5_url+="&partner=2088001636104898";
		md5_url+="&refund_date=2010-02-19 11:10:45";
		md5_url+="&refund_type=1";
		md5_url+="&service=refund_fastpay_by_platform_nopwd";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		url1+=md5_url+"&sign="+sign+ "&sign_type=MD5";
		System.out.println("原url="+url1);
		System.out.println(url);	
	}
	
	/*
	 * 信用支付（外买）退款
	 */
	public static void repayCreditURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/credit.do?_input_charset=UTF-8&";
		String url1 = url ;
		String md5_url;
		
		url+="batch_no=201003090000001";
		url+="&batch_num=3";
		url+="&buyer_fee_detail_data=" + URLEncoder.encode("O20100309000003^200.00^(退给买家)","UTF-8");
		url+="&notify_url=http://125.89.68.86:9012/Airplane/Ticket/CreditRefundNotify.aspx";
		url+="&partner=2088001636104898";
		url+="&pay_fee_detail_data=" + URLEncoder.encode("O20100309000003^150.00^外买退款测试(卖家退给圈主)"
				+"$kenny010@126.com^^^^50^货少一件","UTF-8");	
		url+="&refund_date=2010-02-24 11:10:45";
		url+="&refund_type=2";
		url+="&service=credit_refund_by_platform_out";
		
	
		md5_url="batch_no=201003090000001";
		md5_url+="&batch_num=3";
		md5_url+="&buyer_fee_detail_data=O20100309000003^200.00^(退给买家)";	
		md5_url+="&notify_url=http://125.89.68.86:9012/Airplane/Ticket/CreditRefundNotify.aspx";
		md5_url+="&partner=2088001636104898";
		md5_url+="&pay_fee_detail_data=O20100309000003^150.00^外买退款测试(卖家退给圈主)"
			+"$kenny010@126.com^^^^50^货少一件";
		md5_url+="&refund_date=2010-02-24 11:10:45";
		md5_url+="&refund_type=2";
		md5_url+="&service=credit_refund_by_platform_out";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		url1+=md5_url+"&sign="+sign+ "&sign_type=MD5";
		System.out.println("原url="+url1);
		System.out.println(url);	
	}
	
	/*
	 * 授信还款
	 */
	public static void creditrepayURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
		String md5_url;
		
		url+="agent_email=649010333@qq.com";
		url+= "&body=" + URLEncoder.encode("授信还款","UTF-8");
		url+="&notify_url=http://125.89.68.86:9012/Airplane/Ticket/CreditRefundNotify.aspx";
		url+="&out_trade_no=C20091228000001";
		url+="&partner=2088001636104898";
		url+="&repayment_amount=50";
		url+="&repayment_type=2";
		url+="&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url+="&service=direct_credit_repayment";
		url+="&subject=ccccccccccc";
		
		md5_url= "agent_email=649010333@qq.com";
		md5_url+= "&body=支付人:649010333@qq.com";
		md5_url +="&notify_url=http://125.89.68.86:9012/Airplane/Ticket/CreditRefundNotify.aspx";
		md5_url +="&out_trade_no=C20091228000001";
		md5_url+="&partner=2088001636104898";
		md5_url+="&repayment_amount=50";
		md5_url+="&repayment_type=2";
		md5_url +="&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		md5_url+="&service=direct_credit_repayment";
		md5_url+="&subject=ccccccccccc";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}
	
	/*
	 * 解冻/招标冻结支付解冻
	 */
	public static void allowURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/credit.do?_input_charset=UTF-8&";
		String md5_url;
		//2010020900001
		url +="freeze_type=140";
		url += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url +="&orderdetail_no=O20100226000022";
		url+="&out_trade_no=201002250007";
		url+="&partner=2088001636104898";
		url += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url+="&royalty_parameters=zh-aptechbving@163.com^20|kenny010@126.com^5.00";
		url+="&service=allow_notallow";
		
		md5_url="freeze_type=140";
		md5_url += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		md5_url +="&orderdetail_no=O20100226000022";
		md5_url +="&out_trade_no=201002250007";
		md5_url+="&partner=2088001636104898";
		md5_url += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		md5_url+="&royalty_parameters=zh-aptechbving@163.com^20|kenny010@126.com^5.00";
		md5_url+="&service=allow_notallow";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}
	
	/*
	 *委托冻结
	 */
	public static void freezeURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/freeze.do?_input_charset=UTF-8&";
		String md5_url;
		
		url+="freeze_details=D201002170007^zh-aptechbving@163.com^^25|D201002170008^kenny010@126.com^^6";
		url+= "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url+="&partner=2088001636104898";
		url+="&service=air_trade_refund_freeze";
		url+="&trade_no=O20100218000007";
		
		md5_url="freeze_details=D201002170007^zh-aptechbving@163.com^^25|D201002170008^kenny010@126.com^^6";
		md5_url+= "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		md5_url+="&partner=2088001636104898";
		md5_url+="&service=air_trade_refund_freeze";
		md5_url+="&trade_no=O20100218000007";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}
	
	/*
	 *委托解冻
	 */
	public static void unFreezeURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/freeze.do?_input_charset=UTF-8&";
		String md5_url;
			
		url+= "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url+="&partner=2088001636104898";
		url+="&service=air_trade_refund_unfreeze";
		url+="&unfreeze_details=J0007^F20100123161051765^25|J0008^D201002170007^1";
		
		md5_url= "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		md5_url+="&partner=2088001636104898";
		md5_url+="&service=air_trade_refund_unfreeze";
		md5_url+="&unfreeze_details=J0007^F20100123161051765^25|J0008^D201002170007^1";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}
	
	/*
	 *委托冻结查询
	 */
	public static void freezeQueryURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/freeze.do?_input_charset=UTF-8&";
		String md5_url;
		
		url+="freeze_order_no=D201002170007^D201002170008";
		url+="&partner=2088001636104898";
		url+="&service=air_trade_refund_freeze_query";

		
		md5_url="freeze_order_no=D201002170007^D201002170008";
		md5_url+="&partner=2088001636104898";
		md5_url+="&service=air_trade_refund_freeze_query";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}
	
	
	/*
	 * 冻结标款
	 */
	public static void recruitFreezeURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/freeze.do?_input_charset=UTF-8&";
		String md5_url;
		
		url+="body="+ URLEncoder.encode("冻结标款14：00 am","UTF-8");
		url+="&freeze_email=649010333@qq.com";
		//url+="&freeze_id=4546";
		url += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url+="&out_trade_no=20100310002";
		url+="&partner=2088001636104898";
		url += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url+="&service=recruit_freeze";
		url+="&subject="+ URLEncoder.encode("林尚沃冻结标款","UTF-8");
		url+="&total_fee=200";
		
		md5_url="body=冻结标款14：00 am";
		md5_url+="&freeze_email=649010333@qq.com";
		//md5_url+="&freeze_id=4546";
		md5_url += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		md5_url +="&out_trade_no=20100310002";
		md5_url+="&partner=2088001636104898";
		md5_url += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		md5_url+="&service=recruit_freeze";
		md5_url+="&subject=林尚沃冻结标款";
		md5_url+="&total_fee=200";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println("原url:http://192.168.1.213:8080/fdpay-client/cooperate/freeze.do?_input_charset=UTF-8&"+md5_url+sign+ "&sign_type=MD5");
		System.out.println(url);	
	}
	
	/*
	 * 招标冻结支付
	 */
	public static void recruitFreezepay() throws UnsupportedEncodingException {
		String url = "http://localhost:8080/fdpay-client/cooperate/freeze.do?_input_charset=UTF-8&";
			
		String url_par = "body=" + URLEncoder.encode("支付人:649010333@qq.com","UTF-8");
		url_par+="&buyer_email=649010333@qq.com";		
		url_par += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_par += "&out_trade_no=20100225000002";
		url_par += "&partner=2088001636104898";		
		url_par += "&payment_type=4";
		url_par += "&price=100.00";
		url_par += "&quantity=1";
		url_par += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_par += "&royalty_parameters=" + URLEncoder.encode("zh-aptechbving@163.com^80^林尚沃测试","UTF-8")
		+"|"+URLEncoder.encode("kenny010@126.com^20^林尚沃测试","UTF-8");
//		+"|"+URLEncoder.encode("zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...","UTF-8");
		url_par += "&royalty_type=10";
		url_par += "&seller_email=zh-aptechbving@163.com";
		url_par += "&service=create_freeze_pay_by_user";
		url_par += "&subject="+URLEncoder.encode("测试招标冻结支付","UTF-8");
		url_par += "&total_fee=100";
		
		
		String url_md5 = "body=支付人:649010333@qq.com";
		url_md5+="&buyer_email=649010333@qq.com";
		url_md5 += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_md5 += "&out_trade_no=20100225000002";
		url_md5 += "&partner=2088001636104898";	
		url_md5 += "&payment_type=4";
		url_md5 += "&price=100.00";
		url_md5 += "&quantity=1";
		url_md5 += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_md5 += "&royalty_parameters=zh-aptechbving@163.com^80^林尚沃测试"
		+"|kenny010@126.com^20^林尚沃测试";
//		+"|zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...";
		url_md5 += "&royalty_type=10";
		url_md5 += "&seller_email=zh-aptechbving@163.com";
		url_md5 += "&service=create_freeze_pay_by_user";
		url_md5 += "&subject=测试招标冻结支付";
		url_md5 += "&total_fee=100";
				
		
		String sign = "&sign="
				+ MD5.encrypt(url_md5 + "stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8")
				+ "&sign_type=MD5";		
		System.out.println("原url:"+url+url_md5+sign);
		System.out.println(url+url_par+sign); 
	}
	
	//手机支付发送订单短信接口
	public static void sendConfirmURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
		String md5_url;
		
		url+="mobile=13112372069";
		url+="&msg_content="+URLEncoder.encode("您好！验证码：","UTF-8");
		url+="&partner=2088001636104899";
		url+="&service=send_confirm_msg_of_order";

		
		md5_url="mobile=13112372069";
		md5_url+="&msg_content=您好！验证码：";
		md5_url+="&partner=2088001636104899";
		md5_url+="&service=send_confirm_msg_of_order";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu79", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}

	//validata_code_of_order
	public static void validataURL() throws UnsupportedEncodingException{
		String url="http://192.168.1.213:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
		String md5_url;
		
		url+="mobile=15820573753";
		url+="&mobile_validate_code=758286";
		url+="&partner=2088001636104899";
		url+="&service=validata_code_of_order";

		
		md5_url="mobile=15820573753";
		md5_url+="&mobile_validate_code=758286";
		md5_url+="&partner=2088001636104899";
		md5_url+="&service=validata_code_of_order";
		
		String sign=MD5.encrypt(md5_url+"stpsyw03edwnr1oplj5cevnzj7r1qu79", "UTF-8");
		url+="&sign="+sign+ "&sign_type=MD5";
		System.out.println(url);	
	}
	
	/*
	 * 手机支付
	 */
	public static void mobilePay() throws UnsupportedEncodingException {
		String url = "http://192.168.1.213:8080/fdpay-client/cooperate/gateway.do?_input_charset=UTF-8&";
			
		String url_par = "body=" + URLEncoder.encode("支付人:200834899@qq.com","UTF-8");
		url_par+="&buyer_email=zh-aptechbving@qq.com";
		url_par+="&mobile=13112372069";
		url_par+="&mobile_validate_code=555906";
		url_par += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_par += "&out_trade_no=201002250000110";
		url_par += "&partner=2088001636104898";		
		url_par += "&payment_type=1";
		url_par += "&price=220.00";
		url_par += "&quantity=1";
		url_par += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_par += "&royalty_parameters=" + URLEncoder.encode("649010333@qq.com^190^林尚沃测试","UTF-8")
		+"|"+URLEncoder.encode("kenny010@126^30^林尚沃测试","UTF-8");
//		+"|"+URLEncoder.encode("zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...","UTF-8");
		url_par += "&royalty_type=10";
		url_par += "&seller_email=2010021182@qmpay.com";
		url_par += "&service=direct_pay_by_mobile";
		url_par += "&subject="+URLEncoder.encode("测试手机支付","UTF-8");
		url_par += "&total_fee=220";
		
		
		String url_md5 = "body=支付人:200834899@qq.com";
		url_md5+="&buyer_email=zh-aptechbving@qq.com";
		url_md5+="&mobile=13112372069";
		url_md5+="&mobile_validate_code=555906";
		url_md5 += "&notify_url=http://192.168.1.16:12345/FrOrder/QmpayResultBuyerAffirm";
		url_md5 += "&out_trade_no=201002250000110";
		url_md5 += "&partner=2088001636104898";	
		url_md5 += "&payment_type=1";
		url_md5 += "&price=220.00";
		url_md5 += "&quantity=1";
		url_md5 += "&return_url=http://192.168.1.16:12345/FrOrder/QmPayResult";
		url_md5 += "&royalty_parameters=649010333@qq.com^190^林尚沃测试"
		+"|kenny010@126.com^30^林尚沃测试";
//		+"|zh-aptechbving@163.com^1^林尚沃测试（45*45+5c...";
		url_md5 += "&royalty_type=10";
		url_md5 += "&seller_email=2010021182@qmpay.com";
		url_md5 += "&service=direct_pay_by_mobile";
		url_md5 += "&subject=测试手机支付";
		url_md5 += "&total_fee=220";
				
		
		String sign = "&sign="+ MD5.encrypt(url_md5 + "stpsyw03edwnr1oplj5cevnzj7r1qu78", "UTF-8")
				+ "&sign_type=MD5";		
		System.out.println("原url:"+url+url_md5+sign);
		System.out.println(url+url_par+sign); 
	}
	
	//url编码后 反编
	public static void urlrecode () throws UnsupportedEncodingException{
		String noMd5="agent_email=200834899@qq.com&partner=2088001636104899&service=get_agent_balancestpsyw03edwnr1oplj5cevnzj7r1qu79";
		System.out.println(MD5.encrypt(noMd5,"UTF-8"));
		String urlcode = "https://www.qmpay.com/cooperate/freeze.do?_input_charset=UTF-8&notify_url=http%3a%2f%2fjp.fdays.com%2fAirplane%2fStock%2fQmUnFreezeFundNotify.aspx&partner=2088001636104894&service=air_trade_refund_unfreeze&unfreeze_details=U20100302103830702%5eF20100207152829398%5e910.50&sign=3f8d9ebdf3c82c81e6ae4c8e5e728012&sign_type=MD5";
		System.out.println(new String(java.net.URLDecoder.decode(urlcode, "ISO-8859-1").getBytes("ISO-8859-1"),"UTF-8"));
		System.out.println(new String(urlcode.getBytes("ISO-8859-1"),"GBK"));
	}
}
