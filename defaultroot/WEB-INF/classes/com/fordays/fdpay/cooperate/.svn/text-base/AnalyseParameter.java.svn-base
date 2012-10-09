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

import com.fordays.fdpay.transaction.biz.TransactionBiz;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.exception.AppException;

public class AnalyseParameter {

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

	public static String msgParameterVerify(HashMap<String, String> map)
			throws AppException {
	
		if (map == null) 
			return "url不能为空";
		
		
		if (map.get("mobile") == null || "".equals(map.get(
		"mobile").trim())) 
			return "手机号码不能为空";
		
		if(!checkNumber(map.get("mobile"))||map.get("mobile").length()!=11)
			return "手机号码格式错误";
		
		if (map.get("msg_content") == null || "".equals(map.get(
		"msg_content").trim())) 
			return "短信通知内容不能为空";
		
		return "success";
	}
	
	
	/**
	 * 验证即时到账接口参数
	 * 
	 * @param map
	 * @param gatewayMessage
	 * @return
	 * @throws AppException
	 */
	public static String paymentParameterVerify(HashMap<String, String> map,GatewayMessage gatewayMessage)
			throws AppException {
		BigDecimal totalFee = new BigDecimal("0");
		BigDecimal balance = new BigDecimal("0");
		System.out.println(">>>>>>>>>>进入 验证支付接口参数paymentParameterVerify");
		if (map == null) {
			return gatewayMessage.getCommit_Url_Is_Null(); // 提交URL参数为空
		}
		// validate payment type 验证支付类型
		if (!AnalyseParameter.checkType(map.get("payment_type"))) {
			return gatewayMessage.getPayment_Type_Error(); // 支付类型参数错误。
		}
			
		// validate quantity 验证购买数量,必须大于0
		if (map.get("quantity") != null) {
			if (!AnalyseParameter.checkType(map.get("quantity"))) {
				return gatewayMessage.getQuantity_Error();  // 购买数量参数错误。-----------
			}
			if (Integer.parseInt(map.get("quantity")) <= 0) {
				return gatewayMessage.getQuantity_Error();  // 购买数量参数错误。
			}
		}
		
//		if (map.get("price") == null) {
//			return gatewayMessage.getPrice_Error(); // 商品单价参数错误。
//		}
//		
		if (map.get("price") != null) {
			if (!AnalyseParameter.checkFee(map.get("price"))) {
				return gatewayMessage.getPrice_Error(); // 商品单价参数错误。
			}
		}
		// validate total fee 验证交易金额,单位为RMB Yuan
		// 0.01～1000000.00,上限取决于买卖双方的即时交易金额限制
		if (!AnalyseParameter.checkFee(map.get("total_fee"))) {
			return gatewayMessage.getTotal_Fee_Error();  // 交易金额参数错误。
		}
		totalFee = new BigDecimal(map.get("total_fee"));
		System.out.println("交易金额:"+totalFee);
		// validate seller email 验证email
		if (!AnalyseParameter.checkEmail(map.get("seller_email"), false)) {
			return gatewayMessage.getSeller_Email_Error();   // 卖家帐号参数错误。  
		}
		// validate seller
		if ((map.get("seller_email") == null || "".equals(map.get(
				"seller_email").trim()))
				&& (map.get("seller_id") == null || "".equals(map
						.get("seller_id")))) {
			return gatewayMessage.getSeller_Email_Error(); // 卖家帐号参数错误。
		}
		
		// validate buyer parameters 验证买家信息集
		List<String[]> buyerResult = null;
		String buyerParams = map.get("buyer_parameters");
		if (buyerParams != null && buyerParams.length() > 1000) {
			return gatewayMessage.getBuyer_Parameters_Error();  // 买家信息集参数错误。
		}
		buyerResult = AnalyseParameter.analyseRoyaltyParameter(buyerParams);
		BigDecimal buyerTotalPrice = new BigDecimal("0");
		if (buyerResult != null && buyerResult.size() > 0) {
			for (String[] buyerInfo : buyerResult) {
				if (buyerInfo == null || buyerInfo.length < 2) { // 每一条买家信息格式:买家账号1^付款金额1,判断长度不能小于2
					return gatewayMessage.getBuyer_Parameters_Error();  // 买家信息集参数错误。
				}
		
				if (!AnalyseParameter.checkEmail(buyerInfo[0], false)) {
					return gatewayMessage.getBuyer_Parameters_Error();  // 买家信息集参数错误。
				}
		
				if (!AnalyseParameter.checkFee(buyerInfo[1])) {
					return gatewayMessage.getBuyer_Parameters_Error();  // 买家信息集参数错误。
				}
				buyerTotalPrice = buyerTotalPrice.add(new BigDecimal(
						buyerInfo[1]));
		}
}
		
		// validate royalty parameters 验证提成信息集
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
		if (buyerResult != null && buyerResult.size() > 0) {
			if (totalFee.doubleValue() <= 0
					|| totalFee.add(buyerTotalPrice).compareTo(balance) == 1) { // 验证提交过来的交易金额是否大于0
				// 或者
				// 提成信息集里面的所有提成金额是否大于这个交易金额
				return gatewayMessage.getTotal_Fee_Or_Buyer_Fee_Error();
			}
		} else {
			if (totalFee.doubleValue() <= 0 || balance.compareTo(totalFee) == 1) { // 验证提交过来的交易金额是否大于0
				// 或者
				// 提成信息集里面的所有提成金额是否大于这个交易金额
				return gatewayMessage.getTotal_Fee_Or_Royalty_Fee_Error();
			}
		}
		return gatewayMessage.getSuccess();
	}

	
	public static boolean paymentParameterVerifyForUsers(
			HashMap<String, String> map) throws AppException {
		double totalFee = 0d;
		double balance = 0d;
		if (map == null) {
			return false;
		}
		// validate payment type 验证支付类型
		if (!AnalyseParameter.checkType(map.get("payment_type"))) {
			return false;
		}
		// validate royalty type 验证提成类型,目前只支持一种类型：卖家给第三方提成（10）
		if (!AnalyseParameter.checkType(map.get("royalty_type"))) {
			return false;
		}
		// validate quantity 验证购买数量,必须大于0
		if (map.get("quantity") != null) {
			if (!AnalyseParameter.checkType(map.get("quantity"))) {
				return false;
			}
			if (Integer.parseInt(map.get("quantity")) <= 0) {
				return false;
			}
		}
		// validate total fee 验证交易金额,单位为RMB Yuan
		// 0.01～1000000.00,上限取决于买卖双方的即时交易金额限制
		if (!AnalyseParameter.checkFee(map.get("total_fee"))) {
			return false;
		}
		totalFee = Double.parseDouble(map.get("total_fee"));
		// validate seller email 验证email
		if (!AnalyseParameter.checkEmail(map.get("seller_email"), true)) {
			return false;
		}
		// validate seller
		if ((map.get("seller_email") == null || "".equals(map.get(
				"seller_email").trim()))
				&& (map.get("seller_id") == null || "".equals(map
						.get("seller_id")))) {
			return false;
		}

		// validate buyer parameters 验证买家信息集
		List<String[]> buyerResult = null;
		String buyerParams = map.get("buyer_parameters");
		if (buyerParams == null && buyerParams.length() > 1000) {
			return false;
		}
		buyerResult = AnalyseParameter.analyseRoyaltyParameter(buyerParams);
		if (buyerResult != null && buyerResult.size() > 0) {
			for (String[] buyerInfo : buyerResult) {
				if (buyerInfo == null || buyerInfo.length < 2) { // 每一条买家信息格式:买家账号1^付款金额1,判断长度不能小于2
					return false;
				}

				if (!AnalyseParameter.checkEmail(buyerInfo[0], false)) {
					return false;
				}

				if (!AnalyseParameter.checkFee(buyerInfo[1])) {
					return false;
				}
			}
		}

		// validate royalty parameters 验证提成信息集
		List<String[]> result = null;
		String royaltyParams = map.get("royalty_parameters");
		if (royaltyParams != null && royaltyParams.length() > 1000) {
			return false;
		}
		result = AnalyseParameter.analyseRoyaltyParameter(royaltyParams);
		if (result != null && result.size() > 0) {
			for (String[] agentInfo : result) {
				if (agentInfo == null || agentInfo.length < 3) { // 每一笔提成信息格式:分润收款账号1^提成金额1^说明1
					// ,判断长度不能小于3
					return false;
				}
				// validate royalty email 验证分润收款帐号是否正确
				if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
					return false;
				}
				// validate royalty fee 验证提成金额
				if (!AnalyseParameter.checkFee(agentInfo[1])) {
					return false;
				}
				balance += Double.parseDouble(agentInfo[1]);
			}
		}
		if (totalFee <= 0 || balance > totalFee) { // 验证提交过来的交易金额是否大于0 或者
			// 提成信息集里面的所有提成金额是否大于这个交易金额
			return false;
		}
		return true;
	}
	/**
	 * 验证退款参数格式
	 * @param map
	 * @param gatewayMessage
	 * @return
	 * @throws AppException
	 */
	public static String validateRefundParameter(HashMap<String, String> map,GatewayMessage gatewayMessage)
			throws AppException {
		System.out.println("--------------参数格式验证");
		if (map.get("batch_no") == null) {
			return gatewayMessage.getBatch_No_Format_Error();  // 外部批次号不能为空
		}
		// validate royalty parameters
		List<String[]> result = null;
		if (map.get("detail_data") == null){
			return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误
		}
		//调用analyseRefundParameter方法参数分割交易集  
		result = AnalyseParameter.analyseRefundParameter(map.get("detail_data"));// 验证单笔数据集
		//循环输出交易集
		
		if (result == null || result.size() < 1){
			return gatewayMessage.getDetail_Data_Format_Error();// 数据集参数格式错误
		}
		// validate quantity
		if (!AnalyseParameter.checkType(map.get("batch_num"))){  //验证“单笔数据集”里面的交易总笔数
			return gatewayMessage.getBatch_Num_Error();  // 外部传入总笔数错误。
		}
		if (Integer.parseInt(map.get("batch_num")) <= 0) {
			return gatewayMessage.getBatch_Num_Error(); // 外部传入总笔数错误。
		}
		String[] agentInfo = null;
		// double partnerRefundFee=0d;
		// double coterieRefundFee=0d;
		for (int i = 0; i < result.size(); i++) {
			// for(String[] agentInfo:result){
			
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
					// partnerRefundFee=Double.parseDouble(agentInfo[1]);
				}
				//收费退款信息为：“被收费人Email^被收费人userId^退款金额^退款理由”， 参数长度为4
			} else if (i == 1 && agentInfo.length == 4) {
				// email和id  至少有一个
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
					// coterieRefundFee+=Double.parseDouble(agentInfo[2]);
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
				if ((agentInfo[2] == null || "".equals(agentInfo[2]))
						&& (agentInfo[3] == null || "".equals(agentInfo[3]))) {
					return gatewayMessage.getDetail_Data_Format_Error(); // 数据集参数格式错误。
				}
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
		System.out.println("--------------参数格式验证通过");
		return gatewayMessage.getSuccess();   //验证通过
	}
	
	/**
	 * 验证退款
	 * @param map
	 * @param gatewayMessage
	 * @return
	 * @throws AppException
	 */
	public static boolean validateRefundParameter(HashMap<String, String> map)
			throws AppException {
		// validate royalty parameters
		List<String[]> result = null;
		if (map.get("detail_data") == null) {
			return false;
		}
		result = AnalyseParameter
				.analyseRefundParameter(map.get("detail_data"));
		if (result == null || result.size() < 1) {
			return false;
		}
		// validate quantity
		if (!AnalyseParameter.checkType(map.get("batch_num"))) {
			return false;
		}
		if (Integer.parseInt(map.get("batch_num")) <= 0) {
			return false;
		}
		String[] agentInfo = null;
		// double partnerRefundFee=0d;
		// double coterieRefundFee=0d;
		for (int i = 0; i < result.size(); i++) {
			// for(String[] agentInfo:result){
			agentInfo = result.get(i);
			if (i == 0) {
				if (agentInfo == null || agentInfo.length < 3) {
					return false;
				}
				// validate refund fee
				if (agentInfo[1] != null && !"".equals(agentInfo[1])) {
					if (!AnalyseParameter.checkFee(agentInfo[1])) {
						return false;
					}
					// partnerRefundFee=Double.parseDouble(agentInfo[1]);
				}
			} else if (i == 1 && agentInfo.length == 4) {
				// validate royalty email
				if ((agentInfo[0] == null || "".equals(agentInfo[0]))
						&& (agentInfo[1] == null || "".equals(agentInfo[1]))) {
					return false;
				}
				if (agentInfo[0] != null && !"".equals(agentInfo[0])) {
					if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
						return false;
					}
				}
				// validate refund fee
				if (agentInfo[2] != null && !"".equals(agentInfo[2])) {
					if (!AnalyseParameter.checkFee(agentInfo[2])) {
						return false;
					}
					// coterieRefundFee+=Double.parseDouble(agentInfo[2]);
				}

			} else {
				if (agentInfo == null || agentInfo.length < 6) {
					return false;
				}
				// validate royalty email
				if ((agentInfo[0] == null || "".equals(agentInfo[0]))
						&& (agentInfo[1] == null || "".equals(agentInfo[1]))) {
					return false;
				}
				if (agentInfo[0] == null || "".equals(agentInfo[0])) {
					if (!AnalyseParameter.checkEmail(agentInfo[0], false)) {
						return false;
					}
				}
				if ((agentInfo[2] == null || "".equals(agentInfo[2]))
						&& (agentInfo[3] == null || "".equals(agentInfo[3]))) {
					return false;
				}
				if (agentInfo[2] == null || "".equals(agentInfo[2])) {
					if (!AnalyseParameter.checkEmail(agentInfo[2], false)) {
						return false;
					}
				}
				// validate refund fee
				if (agentInfo[4] != null && !"".equals(agentInfo[4])) {
					if (!AnalyseParameter.checkFee(agentInfo[4])) {
						return false;
					}
					// coterieRefundFee+=Double.parseDouble(agentInfo[4]);
				}
			}
		}
		System.out.println("[AnalyseParameter|getRoyalty_parameters]>>>>>");
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
			return false;
		}
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
		if (!matcher.find()) {
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
	
	/**
	 * 对URL分割，并压入HashMap集合中
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
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
		//把%20更换为" "
		url = url.replaceAll("%20", " ");

		String[] strArray = url.split("&");
		List<String> list = new ArrayList<String>();
		
		//把下面三个参数去掉
		for (String str : strArray) {
			if (str.indexOf("_input_charset") < 0 && str.indexOf("sign=") < 0
					&& str.indexOf("sign_type") < 0) {
				list.add(str);
			}
		}
		AnalyseParameter.sort(list);

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
		
		System.out.println(">>>>url=:" + url);
		System.out.println(">>>>strTemp=:" + strTemp.substring(1).toString());
		System.out.println("sign=>>" + sign);
		System.out.println("encrypt=>>" + encrypt);
		System.out.println("charSet=>>" + charSet);
		//判断url加密后是否和签名相同
		if (encrypt == null || !encrypt.equals(sign)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		// String
		// str="partner=2088001636104894&service=protocol_with_partner&sign=3a2c94f8c016f641c46e354b632744ca&sign_type=MD5&_input_charset=UTF-8";
		// System.out.println(validateSign(str,
		// "3a2c94f8c016f641c46e354b632744ca", "MD5",
		// "stpsyw03edwnr1oplj5cevnzj7r1qu73","GBK"));
		// 3a2c94f8c016f641c46e354b632744ca
		// 3a2c94f8c016f641c46e354b632744ca

		// System.out.println("00="+MD5.encrypt("partner=2088001636104894&service=protocol_with_partnerFLDJGLSJKGLSDJ","GBK"));
		// 7a6b8dfb388d53e18556f96e9899800e
		// String str="qmpay";
		// System.out.println(MD5.encrypt(str));
		String str = "batch_no=20090817175947175947&batch_num=1&detail_data=R20090817000248^0.01^ceshi&notify_url=http://192.168.1.93/qm_notify.asp&partner=2088001636104896&refund_date=2009-08-17 17:59:47&service=refund_fastpay_by_platform_nopwd";
		System.out.println("aa " + str);
		str = str.replaceAll("%20", " ");
		System.out.println("bb " + str);
		// String str1=
		// "agentid=115&flag=1&loginName=465883067@qq.com&orderid=2568&service=email_login_forward&statusid=3&thisAction=getTransactionSuccessAndFailDetail&tid=4959&type=1fae79200286c90ab11c7b6eadc7129f7";
		System.out.println(validateSign(str,
				"2f6c2604895a0cd40f3c6e28694eae78", "MD5",
				"stpsyw03edwnr1oplj5cevnzj7r1qu76", "UTF-8"));
	}
}
