package com.fordays.fdpay.cooperate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.transaction.OrderDetails;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;

public class ConnAnalyseParameter {

	public static final String EMAIL_SIGN_KEY = "fae79200286c90ab11c7b6eadc7129f7"; // 邮件签名专用KEY


	
	/**
	 * 分割提成信息集
	 * @param str
	 * @return
	 */
	public static List<String[]> analyseRoyaltyParameter(String str) {
		String[] agents = null;
		String[] agentInfo = null;
		List<String[]> list = new ArrayList<String[]>();
		if (str != null) {
			agents = str.split("\\|");
			if (agents != null) {
				for (String temp : agents) {
					agentInfo = temp.split("\\^");
					if (temp.lastIndexOf("^") == (temp.length() - 1)) {
						String[] str2 = new String[agentInfo.length + 1];
						for (int i = 0; i < str2.length - 1; i++) {
							str2[i] = agentInfo[i];
						}
						agentInfo = str2;
					}
					list.add(agentInfo);
				}
			}
		}
		return list;
	}
	
	/**
	 * 验证退交易,退分润,退收费信息;   不同类型之间用\\$分割
	 * @param str
	 * @return
	 */
	public static List<String[]> analyseRefundParameter(String str) {
		String[] trade = null;
		String[] tradeInfo = null;
		String[] feeInfo = null;
		List<String[]> list = new ArrayList<String[]>();
		if (str != null) {
			trade = str.split("\\$");
			if (trade != null && trade.length > 0) {
				tradeInfo = trade[0].split("\\^");
				list.add(tradeInfo);
				if (trade.length > 1) {
					feeInfo = trade[1].split("\\|");
					for (String temp : feeInfo) {
						tradeInfo = temp.split("\\^");
						list.add(tradeInfo);
					}
				}
			}
		}
		return list;
	}

	public static boolean msgParameterVerify(HashMap<String, String> map)
			throws AppException {
	
		if (map == null) {
			return false;
		}
		
		if (map.get("mobile") == null || "".equals(map.get(
		"mobile").trim())) {
			return false;
		}
		if (map.get("msg_content") == null || "".equals(map.get(
		"msg_content").trim())) {
			return false;
		}
		return true;
	}
	
	//判断Email
	public static boolean checkEmail(String str, boolean isAllowNull) {
		//验证邮箱的有效性
		Pattern regex = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		Matcher matcher = null;
		if (str != null) {
			matcher = regex.matcher(str);
			while (!matcher.find()) {
				return false;
			}
		} else {
			return isAllowNull;
		}
		return true;
	}
	//验证金额类型
	public static boolean checkFee(String str) {
		Pattern regex = Pattern.compile("(^\\d{1,10}\\.\\d{1,})|(^\\d{1,10}$)");
		Matcher matcher = null;
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		matcher = regex.matcher(str.trim());
		if (!matcher.find()) {
			return false;
		}
		return true;
	}
	
	//判断交易类型0,1
	public static boolean checkTranType(String str) {
		Pattern regex = Pattern.compile("(^\\d{0,1}$)");
		Matcher matcher = null;
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		matcher = regex.matcher(str.trim());
		if (!matcher.find()) {
			return false;
		}
		return true;
	}
	
	
	public static boolean checkType(String str) {
		Pattern regex = Pattern.compile("(^\\d{1,2}$)");
		Matcher matcher = null;
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		matcher = regex.matcher(str.trim());
		if (!matcher.find()) {
			//System.out.println("false");
			return false;
		}
		//System.out.println("true");
		return true;
	}
	
	//判断数字
	public static boolean checkNumber(String str) {
		Pattern regex = Pattern.compile("[0-9]*");
		Matcher matcher = null;
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		matcher = regex.matcher(str.trim());
//		if (!matcher.find()) {  //这种判断有问题
//			return false;
//		}
		if( !matcher.matches()){
			return false;
		}
		return true;
	}
	
	//判断字符串只能包含字符、数字和-并且长度不大于64（冻结和解冻交易号）
	public static boolean checkFreeze(String str) {
		Pattern regex = Pattern.compile("[a-zA-Z0-9\\-]*");
		Matcher matcher = null;
		if (str == null || "".equals(str.trim())||str.length()>64) {
			return false;
		}
		matcher = regex.matcher(str.trim());
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	
	//判断URL
	public static boolean checkURL(String str, boolean isAllowNull) {
		Pattern regex = Pattern
				.compile("^(http|https)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)");
		Matcher matcher = null;
		if (str != null) {
			matcher = regex.matcher(str);
			while (!matcher.find()) {
				return false;
			}
		} else {
			return isAllowNull;
		}
		return true;
	}
	
	/*
	 * 对URL分割，并压入HashMap集合中
	 */
	public static HashMap<String, String> analyseURL(String url)
			throws UnsupportedEncodingException {
		if (url == null || "".equals(url)) {
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		String[] params = url.split("&");
		//循环出根据&分割出来的数
//		for(int i=0;i<params.length;i++){
//			System.out.println(i+"："+params[i]);
//		}
		String[] element = null;
		for (String temp : params) {
			if (temp == null) {
				continue;
			}
			//进一步分割
			element = temp.split("=");
			if (element == null || element.length != 2) {
				continue;
			}
			//循环出根据=分割出来的数
//			for(int i=0;i<element.length;i++){
//				System.out.println(i+"："+element[i]);
//			}
			map.put(element[0], element[1]);
		}

		return map;
	}
	
	//url decode
	public static String decode(String str, String inCharset)
			throws UnsupportedEncodingException {
		if (str == null || "".equals(str)) {
			return null;
		}
		if (inCharset == null || "".equals(inCharset.trim())) {
			return new String(str.getBytes("ISO-8859-1"), "GBK");
		} else {
			return new String(java.net.URLDecoder.decode(str, "ISO-8859-1")
					.getBytes("ISO-8859-1"), inCharset);
		}
	}

	public static HashMap<String, String> analyseURL(String[] keys,
			String[] values) {
		if (keys == null || keys.length == 0 || values == null
				|| keys.length != values.length) {
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < keys.length; i++) {

			map.put(keys[i], values[i]);
		}
		return map;
	}

	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Object[] a = list.toArray();
		Arrays.sort(a);
		ListIterator<T> i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set((T) a[j]);
		}
	}
	
	/*
	 * 实现签名产生
	 */
	public static boolean validateSign(String url, String sign,
			String signType, String partnerKey, String inCharSet)
			throws AppException {
		String encrypt = null;
		StringBuffer strTemp = new StringBuffer();
		String charSet = "GBK";
		if (url == null) {
			return false;
		}
		if (inCharSet != null && !"".equals(inCharSet)) {
			charSet = inCharSet;
		}
//		//把%20更换为" "
		url = url.replaceAll("%20", " ");

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
		//判断MD5后是否相同
		if (signType == null || "MD5".equals(signType)) {
			encrypt = MD5.encrypt(strTemp.substring(1).toString().trim(),
					charSet);
		} else if ("BASE64".equals(signType)) {
			encrypt = BASE64.encrypt(strTemp.substring(1).toString());
		}
		System.out.println(">>>>>>>>>>url=:" + url);
		System.out.println("strTemp=>>" + strTemp.substring(1).toString());
		System.out.println("sign=>>" + sign);
		System.out.println("encrypt=>>" + encrypt);
		System.out.println("charSet=>>" + charSet);
		//判断url加密后是否和签名相同
		if (encrypt == null || !encrypt.equals(sign)) {
			return false;
		}
		return true;
	}

	/*
	 * 验证解冻参数有效性
	 */
	public static String allowpaymentParameterVerify(HashMap<String, String> map,GatewayMessage gatewayMessage)
			throws AppException {

		if (map == null) {
			return gatewayMessage.getCommit_Url_Is_Null(); // 提交URL参数为空
		}
		/******** 验证合作伙伴ID ********/
		if (map.get("partner") == null) {
			return gatewayMessage.getPartner_Can_Not_Null();  // 合作伙伴参数不能为空
		}
		
		/******** 验证notify_url********/
		if (map.get("notify_url") == null) {
			return gatewayMessage.getNO_NOTIFY_URL(); //notify_url不能为空
		}
		
		/******** 验证return_url********/
		if (map.get("return_url") == null) {
			return gatewayMessage.getNO_RETURN_URL(); //return_url不能为空
		}
		
		/******** 验证订单号********/
		if (map.get("orderdetail_no") == null) {
			return gatewayMessage.getOrder_Not_Exists(); //订单不存在
		}
		
		/******** 验证外部订单号********/
		if (map.get("out_trade_no") == null) {
			return gatewayMessage.getBatch_No_Format_Error();  // 外部订单号不能为空
		}
		
		/******** 验证解冻类型********/
		if (map.get("freeze_type") == null) {
			return gatewayMessage.getFREEZE_TYPE_ERROR();  // 外部订单号不能为空
		}
	
		
		
		/******** 验证解冻信息集********/ 
		List<String[]> result = null;
		String royaltyParams = map.get("royalty_parameters");
		
		if (map.get("royalty_parameters") == null || "".equals(map.get("royalty_parameters").trim())) {
			return gatewayMessage.getRoyalty_Parameters_Error(); //解冻信息集参数错误。
		}
		if (royaltyParams != null && royaltyParams.length() > 1000) {
			return gatewayMessage.getRoyalty_Parameters_Error(); // 解冻信息集参数错误。
		}
		
		//分割解冻信息集 并方到list中
		result = analyseRoyaltyParameter(royaltyParams);
		
		if (result != null && result.size() > 0) {
			for (String[] agentInfo : result) {   //循环解冻信息集
				if (agentInfo == null || agentInfo.length <2) { // 每一笔解冻信息格式:解冻收款账号1^解冻金额1
					// ,判断长度不能小于2
					return gatewayMessage.getRoyalty_Parameters_Error(); // 解冻信息集参数错误。
				}
				// validate royalty email 验证分润收款帐号是否正确
				if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
					return gatewayMessage.getAgent_Email_Error(); //解冻账户参数格式错误
				}
				// validate royalty fee 验证提成金额
				if (!AnalyseParameter.checkFee(agentInfo[1])) {
					return gatewayMessage.getALLOW_FEE_ERROR(); //解冻金额参数格式错误
				}						
			}
		}
		return gatewayMessage.getSuccess();
}
	
	/*
	 * 验证信用支付（外买）接口参数有效性
	 */
	public static String paymentParameterVerify(HashMap<String, String> map,GatewayMessage gatewayMessage)
			throws AppException {
		BigDecimal totalFee = new BigDecimal("0");
		BigDecimal balance = new BigDecimal("0");	
		if (map == null) {
			return gatewayMessage.getCommit_Url_Is_Null(); // 提交URL参数为空
		}
		/** 验证支付类型 **/
		if (!checkType(map.get("payment_type"))) {
			return gatewayMessage.getPayment_Type_Error(); // 支付类型参数错误。
		}
		
		/*** 验证提成类型,目前只支持一种类型：卖家给第三方提成（10）***/
		if (!checkType(map.get("royalty_type"))) {
			return gatewayMessage.getRoyalty_Type_Error();  // 提成类型参数错误。-*-----------
		}
			
		/** 验证购买数量,必须大于0 **/
		if (map.get("quantity") != null) {
			if (!checkType(map.get("quantity"))) {
				return gatewayMessage.getQuantity_Error();  // 购买数量参数错误。-----------
			}
			if (Integer.parseInt(map.get("quantity")) <= 0) {
				return gatewayMessage.getQuantity_Error();  // 购买数量参数错误。
			}
		}
		
		if (map.get("price") != null) {
			if (!checkFee(map.get("price"))) {
				return gatewayMessage.getPrice_Error(); // 商品单价参数错误。
			}
		}
		
		/** 验证交易金额,单位为RMB Yuan **/
		if (!AnalyseParameter.checkFee(map.get("total_fee"))) {
			return gatewayMessage.getTotal_Fee_Error();  // 交易金额参数错误。
		}
		totalFee = new BigDecimal(map.get("total_fee"));
		
		/** 验证seller_email **/
		if (!AnalyseParameter.checkEmail(map.get("seller_email"), false)) {
			return gatewayMessage.getSeller_Email_Error();   // 卖家帐号参数错误。  
		}
		if ((map.get("seller_email") == null || "".equals(map.get(
				"seller_email").trim()))
				&& (map.get("seller_id") == null || "".equals(map
						.get("seller_id")))) {
			return gatewayMessage.getSeller_Email_Error(); // 卖家帐号参数错误。
		}
		
		/** 验证buyer_email **/
		if((map.get("buyer_email")==null&&"".equals(map.get("buyer_email")))
				&&(map.get("buyer_id")==null&&"".equals(map.get("buyer_id").trim()))){
			 return gatewayMessage.getBuyer_Email_Error();
		}
		if (!AnalyseParameter.checkEmail(map.get("buyer_email"), true)) { return gatewayMessage  
		    .getBuyer_Email_Error(); } //验证买家参数
		
		/*** 验证提成信息集 ***/
		List<String[]> result = null;
		String royaltyParams = map.get("royalty_parameters");
		if (royaltyParams != null && royaltyParams.length() > 1000) {
			return gatewayMessage.getRoyalty_Parameters_Error();
		}
		result = AnalyseParameter.analyseRoyaltyParameter(royaltyParams);
		if (result != null && result.size() > 0) {
			for (String[] agentInfo : result) {
				if (agentInfo == null || agentInfo.length < 3) { // 每一笔提成信息格式:分润收款账号1^提成金额1^说明1
					// ,判断长度不能小于3
					return gatewayMessage.getRoyalty_Parameters_Error();
				}
				// validate royalty email 验证分润收款帐号是否正确
				if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
					return gatewayMessage.getRoyalty_Parameters_Error();
				}
				// validate royalty fee 验证提成金额
				if (!AnalyseParameter.checkFee(agentInfo[1])) {
					return gatewayMessage.getRoyalty_Parameters_Error();
				}
				// balance += Double.parseDouble(agentInfo[1]);
				balance = balance.add(new BigDecimal(agentInfo[1]));
			}
		}
		
		/** 验证交易金额 **/
		if (totalFee.doubleValue() <= 0 || balance.compareTo(totalFee) == 1) {
			return gatewayMessage.getTotal_Fee_Or_Royalty_Fee_Error();
		}
		return gatewayMessage.getSuccess();
	}

	/*
	 * 验证信用支付（外买）退款参数有效性
	 */
	public static String validateRefundParameter(HashMap<String, String> map,GatewayMessage gatewayMessage)
			throws AppException {
		
		if (map == null) {
			return gatewayMessage.getCommit_Url_Is_Null(); // 提交URL参数为空
		}
		System.out.println("map.get(batch_num)="+map.get("batch_num"));
		
		if(map.get("refund_type")==null){
			return gatewayMessage.getREFUND_TYPE_NOTNULL(); //退款类型不能为空
		}
		if(!AnalyseParameter.checkType(map.get("refund_type"))){
			return gatewayMessage.getREFUND_TYPE_ERROR(); //退款类型参数格式错误
		}
		/** 验证“单笔数据集”里面的交易总笔数 **/
		if (!checkType(map.get("batch_num"))){  
			return gatewayMessage.getBatch_Num_Error();  //外部传入总笔数错误。
		}
		if (Integer.parseInt(map.get("batch_num")) <= 0) {
			return gatewayMessage.getBatch_Num_Error(); // 外部传入总笔数错误。
		}
		
		/*** 验证退款数据集 ***/
		List<String[]> result = null;
		List<String[]> result2 = null;
		if (map.get("buyer_fee_detail_data") == null){
			return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误
		}
		if (map.get("pay_fee_detail_data") == null){
			return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误
		}
		//调用analyseRefundParameter方法参数分割交易集  
		result = analyseRefundParameter(map.get("buyer_fee_detail_data"));// 单笔数据集(圈主到买家)
		result2 = analyseRefundParameter(map.get("pay_fee_detail_data"));//单笔数据集(卖家到圈主)
		if (result == null || result.size() < 1){
			return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误
		}
		if (result2 == null || result2.size() < 1){
			return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误
		}
		
		if(!result.get(0)[0].equals(result2.get(0)[0]))//验证交易号是否相等
			return gatewayMessage.getDetail_Data_Format_Error();
		
		String message = validata_detail_data(result,gatewayMessage);
		String message2 = validata_detail_data(result2,gatewayMessage);
		if(!message.equals(gatewayMessage.getSuccess())){
			return message;
		}
		if(!message2.equals(gatewayMessage.getSuccess())){
			return message2;
		}
		
		return gatewayMessage.getSuccess();   //验证通过
	}

	//验证退款参数集有效性
	public static String validata_detail_data(List<String[]> result,GatewayMessage gatewayMessage){
		String[] agentInfo = null;
		for (int i = 0; i < result.size(); i++) {
			agentInfo = result.get(i);
			if (i == 0) {
				//交易退款信息为：“原付款交易号^退交易金额^退款理由”交易最少参数为3个
				if (agentInfo == null || agentInfo.length < 3) {
					return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误。
				}
				// 验证退款金额   要为数字
				if (agentInfo[1] != null && !"".equals(agentInfo[1])) {
					if (!AnalyseParameter.checkFee(agentInfo[1])) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				//收费退款信息为：“被收费人Email^被收费人userId^退款金额^退款理由”， 参数长度为4
			} else if (i == 1 && agentInfo.length == 4) {
				
				if ((agentInfo[0] == null || "".equals(agentInfo[0]))
						&& (agentInfo[1] == null || "".equals(agentInfo[1]))) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
				//验证email有效性
				if (agentInfo[0] != null && !"".equals(agentInfo[0])) {
					if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				// 验证退款金额
				if (agentInfo[2] != null && !"".equals(agentInfo[2])) {
					if (!AnalyseParameter.checkFee(agentInfo[2])) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
		
			} else {
				//分润退款信息为：“转出人Email^转出人userId^转入人Email^转入人userId^退款金额^退款理由”
				//参数长度为6
				if (agentInfo == null || agentInfo.length < 6) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
				// 转出人email和id  至少有一个
				if ((agentInfo[0] == null || "".equals(agentInfo[0]))
						&& (agentInfo[1] == null || "".equals(agentInfo[1]))) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
				//转出人 验证email有效性
				if (agentInfo[0] != null && !"".equals(agentInfo[0])) {
					if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				// 转入人email和id  至少有一个
//				if ((agentInfo[2] == null || "".equals(agentInfo[2]))
//						&& (agentInfo[3] == null || "".equals(agentInfo[3]))) {
//					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
//				}
				//转入人 验证email有效性
				if (agentInfo[2] != null && !"".equals(agentInfo[2])) {
					if (!AnalyseParameter.checkEmail(agentInfo[2], false)) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
				}
				// validate 退款金额^
				if (agentInfo[4] != null && !"".equals(agentInfo[4])) {
					if (!AnalyseParameter.checkFee(agentInfo[4])) {
						return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
					}
					// coterieRefundFee+=Double.parseDouble(agentInfo[4]);
				}
			}
		}
		return gatewayMessage.getSuccess();   //验证通过
	}
	
	/*
	 * 交易委托冻结接口参数有效性验证方法
	 */
	public static String validate_freeze_parameter(HashMap<String, String> map,
	    FreezeMessage freezeMessage) throws Exception{
			String partner = null;  //合作伙伴号
			Coterie coterie = null;  //平台账户圈
			OrderDetails order = null;
			try
			{
				/** 验证partner**/
				if(map.get("partner")==null||"".equals(map.get("partner").trim()))
					return freezeMessage.getPARTNER_CAN_NOT_NULL();
				
				/*** 验证订单号 ***/
				if(map.get("trade_no")==null||"".equals(map.get("trade_no").trim()))
					return freezeMessage.getTRADE_ITEM_NOT_EXIST();
				
				if(map.get("notify_url")==null||"".equals(map.get("notify_url").trim()))
					return freezeMessage.getNO_NOTIFY_URL();
				
				if (map.get("freeze_details") == null)
					return freezeMessage.getFREEZE_ITEM_ERROR(); // 冻结明细格式错误
				
				/**** 验证冻结明细 ****/
				List<String[]> listFreeze = ConnAnalyseParameter.analyseRoyaltyParameter(map
				    .get("freeze_details"));
				if (listFreeze == null || listFreeze.size() < 1)
					return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
				
				if (listFreeze.size() > 20) 
					return freezeMessage.getEXCEED_MAX_LIMIT(); // 超过最大限制	
				
				String[] reFree = null;
				for(int i=0;i<listFreeze.size();i++){
					reFree = listFreeze.get(i);
					
					if(reFree==null||reFree.length!=4)
						return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
					
					if(reFree[0]==null||"".equals(reFree[0].trim()))
						return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
					
					if(!checkFreeze(reFree[0]))
						return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
					
					if((reFree[1]==null||"".equals(reFree[1].trim()))&&  //email和id都为空
							(reFree[2]==null||"".equals(reFree[2].trim())))
						return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
					
					if(reFree[1]!=null&&!"".equals(reFree[1])){
						if(!checkEmail(reFree[1],true))
							return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
					}
					
					if(reFree[3]==null||"".equals(reFree[3]))
						return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
					
					if(!checkFee(reFree[3]))
						return freezeMessage.getFREEZE_ITEM_ERROR();//  冻结明细格式错误
				}	
				
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				throw ex;
			}
			// return ExceptionConstant.SUCCESS;
			return freezeMessage.getSUCCESS();
		}
	
	
	/*
	 * 交易委托解冻接口参数有效性验证方法
	 */
	public static String validate_unfreeze_parameter(HashMap<String, String> map,
	    FreezeMessage freezeMessage) throws Exception{
			try
			{
				/** 验证partner**/
				if(map.get("partner")==null||"".equals(map.get("partner").trim()))
					return freezeMessage.getPARTNER_CAN_NOT_NULL();
								
				if(map.get("notify_url")==null||"".equals(map.get("notify_url").trim()))
					return freezeMessage.getNO_NOTIFY_URL();
				
				if (map.get("unfreeze_details") == null)
					return freezeMessage.getUNFREEZE_ITEM_ERROR(); // 冻结明细格式错误
				
				/**** 验证冻结明细 ****/
				List<String[]> listFreeze = ConnAnalyseParameter.analyseRoyaltyParameter(map
				    .get("unfreeze_details"));
				if (listFreeze == null || listFreeze.size() < 1)
					return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
				
				if (listFreeze.size() > 20) 
					return freezeMessage.getEXCEED_MAX_LIMIT(); // 超过最大限制		
				
				String[] reFree = null;
				for(int i=0;i<listFreeze.size();i++){
					reFree = listFreeze.get(i);
					
					if(reFree==null||reFree.length!=3)
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
					
					if(reFree[0]==null||"".equals(reFree[0].trim()))
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
					
					if(!checkFreeze(reFree[0]))
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
					
					if(reFree[1]==null||"".equals(reFree[1].trim()))
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
					
					if(!checkFreeze(reFree[1]))
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
					
					
					if(reFree[2]==null||"".equals(reFree[2]))
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
					
					if(!checkFee(reFree[2]))
						return freezeMessage.getUNFREEZE_ITEM_ERROR();//  解冻明细格式错误
				}	
				
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				throw ex;
			}
			// return ExceptionConstant.SUCCESS;
			return freezeMessage.getSUCCESS();
		}
	
	/*
	 * 验证冻结标款参数有效性
	 */
	public static String freezeParameterVerify(HashMap<String, String> map,FreezeMessage freezeMessage)
			throws AppException {

		if (map == null) 
			return freezeMessage.getCOMMIT_URL_IS_NULL(); // 提交URL参数为空
		
		/******** 验证合作伙伴ID ********/
		if (map.get("partner") == null) 
			return freezeMessage.getPARTNER_CAN_NOT_NULL();  // 合作伙伴参数不能为空
		
		
		/******** 验证外部订单号********/
		if (map.get("out_trade_no") == null) 
			return freezeMessage.getBATCH_NO_FORMAT_ERROR();  // 外部订单号格式错误
		
		/** 验证notify url **/
		if(map.get("notify_url")==null||"".equals(map.get("notify_url").trim()))
			return freezeMessage.getNO_NOTIFY_URL();
		
		/** 验证return url **/
		if(map.get("return_url")==null||"".equals(map.get("return_url").trim()))
			return freezeMessage.getNO_RETURN_URL();
		
		/******验证冻结账户*****/
		if((map.get("freeze_email")==null||"".equals(map.get("freeze_email").trim()))
				&&(map.get("freeze_id")==null||"".equals(map.get("freeze_id").trim()))){
			return freezeMessage.getRECRUIT_EMAIL_ERROR(); //冻结账户错误
		}
		if (!AnalyseParameter.checkEmail(map.get("freeze_email"), false)) 
			return freezeMessage.getRECRUIT_EMAIL_ERROR(); //冻结账户参数格式错误
		
		/**** 冻结金额 ****/
		if (!AnalyseParameter.checkFee(map.get("total_fee"))) 
			return freezeMessage.getRECRUIT_FREEZE_ERROR(); //冻结金额参数格式错误
		
		
		return freezeMessage.getSUCCESS();
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(checkNumber("12"));
	}
	
}
