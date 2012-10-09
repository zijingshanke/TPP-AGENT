package com.fordays.fdpay.cooperate;

public class GatewayMessageCn implements GatewayMessage{
	public static final String SUCCESS = "处理成功"; // 处理成功。
	public static final String SYSTEM_ERROR = "钱门系统错误"; // 钱门系统错误。
	public static final String PARTNER_NOT_SIGN_PROTOCOL = "平台商未签署协议"; // 平台商未签署协议。
	public static final String SELLER_NOT_SIGN_PROTOCOL = "卖家未签协议"; // 。卖家未签协议
	public static final String ROYALTYER_NOT_SIGN_PROTOCOL = "分润方未签协议"; // 。分润方未签协议
	public static final String BATCH_NO_FORMAT_ERROR = "外部批次号格式错误"; // 外部批次号格式错误。
	public static final String DUPLICATE_BATCH_NO = "重复的外部批次号"; // 重复的外部批次号。
	public static final String REFUND_DATE_ERROR = "错误的外部退款时间"; // 错误的外部退款时间。
	public static final String BATCH_NUM_ERROR = "外部传入总笔数错误"; // 外部传入总笔数错误。
	public static final String BATCH_NUM_EXCEED_LIMIT = "总笔数查过限额"; // 总笔数查过限额（大于1000）。
	public static final String SINGLE_DETAIL_DATA_EXCEED_LIMIT = "单笔明细数据长度超过限制"; // 单笔明细数据长度超过限制（单笔不超过1000字）。
	public static final String DETAIL_DATA_FORMAT_ERROR = "数据集参数格式错误"; // 数据集参数格式错误。
	public static final String BATCH_NUM_NOT_EQUAL_TOTAL = "传入的退款条数不等于数据集解析出的退款条数"; // 传入的退款条数不等于数据集解析出的退款条数。
	public static final String EMAIL_USERID_NOT_MATCH = "数据集参数格式错误"; // 数据集参数格式错误。
	public static final String NOT_THIS_PARTNERS_TRADE = "该交易不属于这个合作伙伴"; // 该交易不属于这个合作伙伴。
	public static final String TRADE_NOT_EXISTS = "交易不存在"; // 交易不存在。
	public static final String TRADE_STATUS_ERROR = "交易状态错误"; // 交易状态错误。
	public static final String ACCOUNT_NOT_EXISTS = "账户不存在"; // 账户不存在。
	public static final String TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH = "账户余额不足"; // 账户余额不足。
	public static final String TXN_RESULT_ACCOUNT_STATUS_NOT_VALID = "账户状态不允许"; // 账户状态不允许。
	public static final String TXN_RESULT_NO_SUCH_ACCOUNT = "不存在该账户"; // 不存在该账户。
	public static final String RESULT_AMOUNT_NOT_VALID = "总价不对"; // 总价不对。
	public static final String RESULT_ACCOUNT_NO_NOT_VALID = "账户不可用"; // 账户不可用。
	public static final String TRADE_HAS_REFUNDED = "交易已退过"; // 交易已退过。
	public static final String TRADE_HAS_CLOSED = "交易已关闭"; // 交易已关闭。
	public static final String TRADE_HAS_FINISHED = "交易已结束"; // 交易已结束。
	public static final String REFUND_TRADE_FEE_ERROR = "退交易金额有误"; // 退交易金额有误。
	public static final String REFUND_ROYALTY_FEE_ERROR = "退分润金额有误"; // 退分润金额有误。
	public static final String REFUND_CHARGE_FEE_ERROR = "退收费金额有误"; // 退收费金额有误。
	public static final String REFUND_TYPE_NOTNULL = "退款类型不能为空"; // 退款类型不能为空
	public static final String REFUND_TYPE_ERROR = "退款类型参数格式错误"; // 退款类型参数格式错误
	public static final String UNKNOW_ERROR = "处理请求时发生异常"; // 处理请求时发生异常。
	public static final String RESULT_HAS_REFUND = "该笔退款已退过"; // 该笔退款已退过。
	// 签署电子协议错误码*******************
	public static final String REFUND_SIGN_SUCCESS = "平台商用户签约成功"; // 平台商用户签约成功。
	public static final String REFUND_SIGN_ALREADY_SIGN = "平台商用户重复签约"; // 平台商用户重复签约。
	public static final String REFUND_SIGN_OUT_TIME = "超时或服务不可用"; // 超时或服务不可用。
	public static final String REFUND_SIGN_PLAT_ERROR = "平台商不存在或者平台商未与支付宝签约"; // 平台商不存在或者平台商未与支付宝签约。
	public static final String REFUND_SIGN_ETRANCE = "请求来源不合法";// 请求来源不合法。
	public static final String ZERO_NO_NEED_PROCESS = "退款金额为零，不予处理";// 退款金额为零，不予处理。
	public static final String SAME_ACCOUNT_NO_NEED_PROCESS = "相同帐号不被处理";// 相同帐号不被处理。
	// 分润是否成功
	public static final String PAYMENT_ROYALTY_FEE_ERROR = "分润失败";
	
	public static final String FORWARD_PAGE_ERROR = "转向页面参数有误"; //转向页面参数有误
	
	public static final String ROYALTY_FEE_ERROR = "分润总金额过大"; //分润总金额过大
	
	public static final String THISROYALTY_FEE_ERROR = "分润金额错误"; //分润金额错误
	
	public static final String ALLOW_FEE_ERROR = "解冻金额错误"; //解冻金额错误
	
	public static final String FREEZE_TYPE_ERROR = "解冻类型错误"; //解冻金额错误
	
	public static final String ALLOWBIG_FEE_ERROR = "解冻金额过大"; //解冻金额过大
	
	public static final String BUYER_ACCOUNT_BALANCE_NOT_ENOUGH = "买家账户余额不足"; // 买家账户余额不足。
	
	public static final String BUYER_PANER_ACCOUNT_BALANCE_NOT_ENOUGH = "买家圈主账户余额不足"; // 买家圈主账户余额不足
	
	public static final String BUYER_ACCOUNT_NOTALLOWBALANCE_NOT_ENOUGH = "账户冻结余额不足"; // 账户冻结余额不足。
	
	public static final String CREDIT_ACCOUNT_BALANCE_NOT_ENOUGH = "授信余额不足"; // 授信余额不足。
	
	public static final String BUYER_FEE_ERROR = "买家给圈主信用金额参数错误"; // 买家给圈主信用金额参数错误
	
	public static final String BUYER_FEE_SOMORE = "买家给圈主信用金额过大"; // 买家给圈主信用金额过大
	
	public static final String PAY_FEE_ERROR = "圈主给卖家可用金额参数错误"; // 圈主给卖家可用金额参数错误
	
	public static final String PAY_FEE_SOMORE = "圈主给卖家可用金额过大"; // 圈主给卖家可用金额过大
	
	public static final String REFUND_ACCOUNT_ERROR = "退款帐号错误"; // 退款帐号错误。
	
	public static final String PASSWORD_ERROR = "密码错误"; // 密码错误。
	
	public static final String PARAMETER_CANNOT_NULL = "参数不能为空"; // 参数不能为空。
	
	public static final String PAYMENT_TYPE_CANNOT_NULL = "支付类型不能为空"; // 支付类型不能为空。
	
	public static final String FROM_DATE_CANNOT_NULL = "开始时间不能为空"; // 开始时间不能为空。
	
	public static final String TO_DATE_CANNOT_NULL = "结束时间不能为空"; // 结束时间不能为空。
	
	public static final String MOBILE_CANNOT_NULL = "手机号码不能为空"; // 手机号码不能为空。
	
	public static final String MOBILE_VALIDATE_CODE_CANNOT_NULL = "手机验证码不能为空"; // 手机验证码不能为空。
	
	public static final String REPEAT_COMMIT = "表单重复提交"; // 表单重复提交。
	
	public static final String VALIDATE_SIGN_FAIL = "验证签名失败"; //验证签名失败
	
	public static final String ORDER_NOT_EXISTS = "订单不存在"; // 订单不存在。
	
	public static final String PAYMENT_TYPE_ERROR = "支付类型参数错误"; // 支付类型参数错误。
	
	public static final String ROYALTY_TYPE_ERROR = "提成类型参数错误"; // 提成类型参数错误。
	
	public static final String QUANTITY_ERROR = "购买数量参数错误"; // 购买数量参数错误。
	
	public static final String TOTAL_FEE_ERROR = "交易金额参数错误"; // 交易金额参数错误。
	
	public static final String SELLER_EMAIL_ERROR = "卖家帐号参数错误"; // 卖家帐号参数错误。
	
	public static final String BUYER_PARAMETERS_ERROR = "买家信息集参数错误"; // 买家信息集参数错误。
	
	public static final String ROYALTY_PARAMETERS_ERROR = "提成信息集参数错误"; // 提成信息集参数错误。
	
	public static final String TOTAL_FEE_OR_ROYALTY_FEE_ERROR = "交易金额不能小于零或者分润金额不能大于交易金额"; // 交易金额不能小于零或者分润金额不能大于交易金额。
	
	public static final String TOTAL_FEE_OR_BUYER_FEE_ERROR = "交易金额不能小于零或者买家信息集金额有误"; // 交易金额不能小于零或者买家信息集金额有误。
	
	public static final String CREDIT_AMOUNT_ERROR = "授信金额参数错误"; // 授信金额参数错误。
	
	public static final String AGENT_EMAIL_ERROR = "商家帐号参数错误"; // 商家帐号参数错误。
	
	public static final String CREDIT_EMAIL_ERROR = "授信帐号参数错误"; // 授信帐号参数错误。
	
	public static final String COMMIT_URL_IS_NULL = "提交URL参数为空"; // 提交URL参数为空。
	
	public static final String BATCH_NUM_IS_ERROR = "总笔数参数错误"; // 总笔数参数错误。
	
	public static final String PRICE_ERROR = "商品单价参数错误"; // 商品单价参数错误。
	
	public static final String SHOPNAME_ERROR = "商品名称参数错误"; // 商品名称参数错误。
	
	public static final String EMAIL_NOT_EXISTS = "支付圈中不存在此用户"; // 支付圈中不存在此用户。
	
	public static final String CHANGE_STATUS_ERROR = "更改状态参数错误"; // 更改状态参数错误。
	
	public static final String CREDIT_PAYMENT_IS_STOP = "信用圈已经被禁用"; // 信用圈已经被禁用。
	
	public static final String CREDIT_PAYMENT_HAS_STOP = "信用支付已经被禁用"; // 信用支付已经被禁用。
	
	public static final String CREDIT_PAYMENT_DATE_EXPIRE = "信用支付有效期已过"; // 信用支付有效期已过
	
	public static final String CREDIT_NO_PAYOUT_STOP= "信用支付外买已经被禁用"; // 信用支付外买已经被禁用
	
	public static final String CREDIT_PRICE_LESS_THEN = "信用余额小于最少限制金额"; // 信用余额小于最少限制金额
	
	public static final String NEED_FIRST_REPAYMENT = "请先还款再进行支付"; // 请先还款再进行支付
	
	public static final String REPAYMENT_TYPE_ERROR = "催还类型参数错误"; // 催还类型参数错误
	
	public static final String MIN_AMOUNT_ERROR = "最低还款金额参数错误"; // 最低还款金额
	
	public static final String LEAVE_DAYS_ERROR = "限期还款天数参数错误"; // 限期还款天数
	
	public static final String TRANSACTION_TYPE_ERROR = "交易限制参数错误"; // 交易限制参数错误
	
	public static final String PARTNER_CAN_NOT_NULL = "合作伙伴参数不能为空"; // 合作伙伴参数不能为空
	
	public static final String REPAYMENT_AMOUNT_IS_TOO_BIG = "还款金额过大"; // 还款金额过大
	
	public static final String REPAYMENT_AMOUNT_ERROR = "还款金额有误"; // 还款金额有误
	
	public static final String TYPE_ERROR = "标志类型参数错误"; // 标志类型参数错误
	
	public static final String BUYER_EMAIL_ERROR = "买家帐号错误"; // 买家帐号错误
	
	public static final String BUYER_PANER_EMAIL_ERROR = "买家圈主帐号错误"; // 买家圈主帐号错误
	
	public static final String ALREADY_REPAYMENT_ERROR="授信欠款已经还完";//授信欠款已经还完
	
	public static final String REQUEST_PARAMETER_ERROR="请求参数有误";//请求参数有误
	
	public static final String NO_NOTIFY_URL="通知URL不能为空";//通知URL不能为空
	
	public static final String NO_RETURN_URL="返回URL不能为空";//返回URL不能为空
	
	public static final String CHANGE_PASSWORD_TYPE_ERROR="修改密码类型参数错误";
	public static final String CREDIT_NOT_REPAYMENT="逐笔还款类型的圈员必须还款后再执行信用支付";
	public static final String AGENT_NOT_CANT_CREDIT="非本圈商户";
	public static final String CREDIT_REPAYMENT_SLOP_OVER="信用还款越界,还款金额超出欠款金额";
	public static final String ORDER_NOT_IDENTICAL_EXISTS="两个订单号不相同";
	public static final String OUTWEIGH_THE_AMOUNT_OF="超出支付金额";
	public static final String Less_than_the_amount_of_credit="账户金额不足";
	public static final String MOBILE_NOT_FOUND_IN_AGENT="帐户中不存在绑定此手机";
	public String getLess_than_the_amount_of_credit() {
		return Less_than_the_amount_of_credit;
	}
	public  String getOUTWEIGH_THE_AMOUNT_OF() {
		return OUTWEIGH_THE_AMOUNT_OF;
	}
	public static String getSUCCESS() {
		return SUCCESS;
	}
	public static String getSYSTEM_ERROR() {
		return SYSTEM_ERROR;
	}
	public static String getPARTNER_NOT_SIGN_PROTOCOL() {
		return PARTNER_NOT_SIGN_PROTOCOL;
	}
	public static String getSELLER_NOT_SIGN_PROTOCOL() {
		return SELLER_NOT_SIGN_PROTOCOL;
	}
	public static String getROYALTYER_NOT_SIGN_PROTOCOL() {
		return ROYALTYER_NOT_SIGN_PROTOCOL;
	}
	public static String getBATCH_NO_FORMAT_ERROR() {
		return BATCH_NO_FORMAT_ERROR;
	}
	public static String getDUPLICATE_BATCH_NO() {
		return DUPLICATE_BATCH_NO;
	}
	public static String getREFUND_DATE_ERROR() {
		return REFUND_DATE_ERROR;
	}
	public static String getBATCH_NUM_ERROR() {
		return BATCH_NUM_ERROR;
	}
	public static String getBATCH_NUM_EXCEED_LIMIT() {
		return BATCH_NUM_EXCEED_LIMIT;
	}
	public static String getSINGLE_DETAIL_DATA_EXCEED_LIMIT() {
		return SINGLE_DETAIL_DATA_EXCEED_LIMIT;
	}
	public static String getDETAIL_DATA_FORMAT_ERROR() {
		return DETAIL_DATA_FORMAT_ERROR;
	}
	public static String getBATCH_NUM_NOT_EQUAL_TOTAL() {
		return BATCH_NUM_NOT_EQUAL_TOTAL;
	}
	public static String getEMAIL_USERID_NOT_MATCH() {
		return EMAIL_USERID_NOT_MATCH;
	}
	public static String getNOT_THIS_PARTNERS_TRADE() {
		return NOT_THIS_PARTNERS_TRADE;
	}
	public static String getTRADE_NOT_EXISTS() {
		return TRADE_NOT_EXISTS;
	}
	public static String getTRADE_STATUS_ERROR() {
		return TRADE_STATUS_ERROR;
	}
	public static String getACCOUNT_NOT_EXISTS() {
		return ACCOUNT_NOT_EXISTS;
	}
	public static String getTXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH() {
		return TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public static String getTXN_RESULT_ACCOUNT_STATUS_NOT_VALID() {
		return TXN_RESULT_ACCOUNT_STATUS_NOT_VALID;
	}
	public static String getTXN_RESULT_NO_SUCH_ACCOUNT() {
		return TXN_RESULT_NO_SUCH_ACCOUNT;
	}
	public static String getRESULT_AMOUNT_NOT_VALID() {
		return RESULT_AMOUNT_NOT_VALID;
	}
	public static String getRESULT_ACCOUNT_NO_NOT_VALID() {
		return RESULT_ACCOUNT_NO_NOT_VALID;
	}
	public static String getTRADE_HAS_REFUNDED() {
		return TRADE_HAS_REFUNDED;
	}
	public static String getTRADE_HAS_CLOSED() {
		return TRADE_HAS_CLOSED;
	}
	public static String getTRADE_HAS_FINISHED() {
		return TRADE_HAS_FINISHED;
	}
	public static String getREFUND_TRADE_FEE_ERROR() {
		return REFUND_TRADE_FEE_ERROR;
	}
	public static String getREFUND_ROYALTY_FEE_ERROR() {
		return REFUND_ROYALTY_FEE_ERROR;
	}
	public static String getREFUND_CHARGE_FEE_ERROR() {
		return REFUND_CHARGE_FEE_ERROR;
	}
	public static String getUNKNOW_ERROR() {
		return UNKNOW_ERROR;
	}
	public static String getRESULT_HAS_REFUND() {
		return RESULT_HAS_REFUND;
	}
	public static String getREFUND_SIGN_SUCCESS() {
		return REFUND_SIGN_SUCCESS;
	}
	public static String getREFUND_SIGN_ALREADY_SIGN() {
		return REFUND_SIGN_ALREADY_SIGN;
	}
	public static String getREFUND_SIGN_OUT_TIME() {
		return REFUND_SIGN_OUT_TIME;
	}
	public static String getREFUND_SIGN_PLAT_ERROR() {
		return REFUND_SIGN_PLAT_ERROR;
	}
	public static String getREFUND_SIGN_ETRANCE() {
		return REFUND_SIGN_ETRANCE;
	}
	public static String getZERO_NO_NEED_PROCESS() {
		return ZERO_NO_NEED_PROCESS;
	}
	public static String getSAME_ACCOUNT_NO_NEED_PROCESS() {
		return SAME_ACCOUNT_NO_NEED_PROCESS;
	}
	public static String getPAYMENT_ROYALTY_FEE_ERROR() {
		return PAYMENT_ROYALTY_FEE_ERROR;
	}
	public static String getFORWARD_PAGE_ERROR() {
		return FORWARD_PAGE_ERROR;
	}
	public static String getROYALTY_FEE_ERROR() {
		return ROYALTY_FEE_ERROR;
	}
	public static String getBUYER_ACCOUNT_BALANCE_NOT_ENOUGH() {
		return BUYER_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public static String getBUYER_ACCOUNT_NOTALLOWBALANCE_NOT_ENOUGH() {
		return BUYER_ACCOUNT_NOTALLOWBALANCE_NOT_ENOUGH;
	}
	public static String getCREDIT_ACCOUNT_BALANCE_NOT_ENOUGH() {
		return CREDIT_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public static String getREFUND_ACCOUNT_ERROR() {
		return REFUND_ACCOUNT_ERROR;
	}
	public static String getPASSWORD_ERROR() {
		return PASSWORD_ERROR;
	}
	public static String getPARAMETER_CANNOT_NULL() {
		return PARAMETER_CANNOT_NULL;
	}
	public static String getPAYMENT_TYPE_CANNOT_NULL() {
		return PAYMENT_TYPE_CANNOT_NULL;
	}
	public static String getFROM_DATE_CANNOT_NULL() {
		return FROM_DATE_CANNOT_NULL;
	}
	public static String getTO_DATE_CANNOT_NULL() {
		return TO_DATE_CANNOT_NULL;
	}
	public static String getMOBILE_CANNOT_NULL() {
		return MOBILE_CANNOT_NULL;
	}
	public static String getMOBILE_VALIDATE_CODE_CANNOT_NULL() {
		return MOBILE_VALIDATE_CODE_CANNOT_NULL;
	}
	public static String getREPEAT_COMMIT() {
		return REPEAT_COMMIT;
	}
	public static String getVALIDATE_SIGN_FAIL() {
		return VALIDATE_SIGN_FAIL;
	}
	public static String getORDER_NOT_EXISTS() {
		return ORDER_NOT_EXISTS;
	}
	public static String getPAYMENT_TYPE_ERROR() {
		return PAYMENT_TYPE_ERROR;
	}
	public static String getROYALTY_TYPE_ERROR() {
		return ROYALTY_TYPE_ERROR;
	}
	public static String getQUANTITY_ERROR() {
		return QUANTITY_ERROR;
	}
	public static String getTOTAL_FEE_ERROR() {
		return TOTAL_FEE_ERROR;
	}
	public static String getSELLER_EMAIL_ERROR() {
		return SELLER_EMAIL_ERROR;
	}
	public static String getBUYER_PARAMETERS_ERROR() {
		return BUYER_PARAMETERS_ERROR;
	}
	public static String getROYALTY_PARAMETERS_ERROR() {
		return ROYALTY_PARAMETERS_ERROR;
	}
	public static String getTOTAL_FEE_OR_ROYALTY_FEE_ERROR() {
		return TOTAL_FEE_OR_ROYALTY_FEE_ERROR;
	}
	public static String getTOTAL_FEE_OR_BUYER_FEE_ERROR() {
		return TOTAL_FEE_OR_BUYER_FEE_ERROR;
	}
	public static String getCREDIT_AMOUNT_ERROR() {
		return CREDIT_AMOUNT_ERROR;
	}
	public static String getAGENT_EMAIL_ERROR() {
		return AGENT_EMAIL_ERROR;
	}
	public static String getCREDIT_EMAIL_ERROR() {
		return CREDIT_EMAIL_ERROR;
	}
	public static String getCOMMIT_URL_IS_NULL() {
		return COMMIT_URL_IS_NULL;
	}
	public static String getBATCH_NUM_IS_ERROR() {
		return BATCH_NUM_IS_ERROR;
	}
	public static String getPRICE_ERROR() {
		return PRICE_ERROR;
	}
	public static String getEMAIL_NOT_EXISTS() {
		return EMAIL_NOT_EXISTS;
	}
	public static String getCHANGE_STATUS_ERROR() {
		return CHANGE_STATUS_ERROR;
	}
	public static String getCREDIT_PAYMENT_HAS_STOP() {
		return CREDIT_PAYMENT_HAS_STOP;
	}
	public static String getCREDIT_PAYMENT_DATE_EXPIRE() {
		return CREDIT_PAYMENT_DATE_EXPIRE;
	}
	public static String getCREDIT_PRICE_LESS_THEN() {
		return CREDIT_PRICE_LESS_THEN;
	}
	public static String getNEED_FIRST_REPAYMENT() {
		return NEED_FIRST_REPAYMENT;
	}
	public static String getREPAYMENT_TYPE_ERROR() {
		return REPAYMENT_TYPE_ERROR;
	}
	public static String getMIN_AMOUNT_ERROR() {
		return MIN_AMOUNT_ERROR;
	}
	public static String getLEAVE_DAYS_ERROR() {
		return LEAVE_DAYS_ERROR;
	}
	public static String getTRANSACTION_TYPE_ERROR() {
		return TRANSACTION_TYPE_ERROR;
	}
	public static String getPARTNER_CAN_NOT_NULL() {
		return PARTNER_CAN_NOT_NULL;
	}
	public static String getREPAYMENT_AMOUNT_IS_TOO_BIG() {
		return REPAYMENT_AMOUNT_IS_TOO_BIG;
	}
	public static String getREPAYMENT_AMOUNT_ERROR() {
		return REPAYMENT_AMOUNT_ERROR;
	}
	public static String getTYPE_ERROR() {
		return TYPE_ERROR;
	}
	public static String getBUYER_EMAIL_ERROR() {
		return BUYER_EMAIL_ERROR;
	}
	public static String getALREADY_REPAYMENT_ERROR() {
		return ALREADY_REPAYMENT_ERROR;
	}
	public static String getREQUEST_PARAMETER_ERROR() {
		return REQUEST_PARAMETER_ERROR;
	}
	public String getNO_NOTIFY_URL() {
		return NO_NOTIFY_URL;
	}
	public String getNO_RETURN_URL() {
		return NO_RETURN_URL;
	}
	public static String getCHANGE_PASSWORD_TYPE_ERROR() {
		return CHANGE_PASSWORD_TYPE_ERROR;
	}
	public static String getCREDIT_NOT_REPAYMENT() {
		return CREDIT_NOT_REPAYMENT;
	}
	public static String getAGENT_NOT_CANT_CREDIT() {
		return AGENT_NOT_CANT_CREDIT;
	}
	public static String getCREDIT_REPAYMENT_SLOP_OVER() {
		return CREDIT_REPAYMENT_SLOP_OVER;
	}
	public String getORDER_NOT_IDENTICAL_EXISTS(){
		return ORDER_NOT_IDENTICAL_EXISTS;
	}
	public String getAccount_Not_Exists() {
		return ACCOUNT_NOT_EXISTS;
	}
	public String getBatch_No_Format_Error() {
		return BATCH_NO_FORMAT_ERROR;
	}
	public String getBatch_Num_Error() {
		return BATCH_NUM_ERROR;
	}
	public String getBatch_Num_Exceed_Limit() {
		return BATCH_NUM_EXCEED_LIMIT;
	}
	public String getBatch_Num_Not_Equal_Total() {
		return BATCH_NUM_NOT_EQUAL_TOTAL;
	}
	public String getDetail_Data_Format_Error() {
		return DETAIL_DATA_FORMAT_ERROR;
	}
	public String getDuplicate_Batch_No() {
		return DUPLICATE_BATCH_NO;
	}
	public String getEmail_Userid_Not_Match() {
		return EMAIL_USERID_NOT_MATCH;
	}
	public String getNot_This_Partners_Trade() {
		return NOT_THIS_PARTNERS_TRADE;
	}
	public String getPartner_Not_Sign_Protocol() {
		return PARTNER_NOT_SIGN_PROTOCOL;
	}
	public String getPayment_Royalty_Fee_Error() {
		return PAYMENT_ROYALTY_FEE_ERROR;
	}
	public String getRefund_Charge_Fee_Error() {
		return REFUND_CHARGE_FEE_ERROR;
	}
	public String getRefund_Date_Error() {
		return REFUND_DATE_ERROR;
	}
	public String getRefund_royalty_Fee_Error() {
		return REFUND_ROYALTY_FEE_ERROR;
	}
	public String getRefund_Sign_Already_Sign() {
		return REFUND_SIGN_ALREADY_SIGN;
	}
	public String getRefund_Sign_Etrance() {
		return REFUND_SIGN_ETRANCE;
	}
	public String getRefund_Sign_Out_Time() {
		return REFUND_SIGN_OUT_TIME;
	}
	public String getRefund_Sign_Plat_Error() {
		return REFUND_SIGN_PLAT_ERROR;
	}
	public String getRefund_Sign_Success() {
		return REFUND_SIGN_SUCCESS;
	}
	public String getRefund_Trade_Fee_Error() {
		return REFUND_TRADE_FEE_ERROR;
	}
	public String getResult_Account_No_Not_Valid() {
		return RESULT_ACCOUNT_NO_NOT_VALID;
	}
	public String getResult_Amount_Not_Valid() {
		return RESULT_AMOUNT_NOT_VALID;
	}
	public String getResult_Has_Refund() {
		return RESULT_HAS_REFUND;
	}
	public String getRoyaltyer_Not_Sign_Protocol() {
		return ROYALTYER_NOT_SIGN_PROTOCOL;
	}
	public String getSeller_Not_Sign_Protocol() {
		return SELLER_NOT_SIGN_PROTOCOL;
	}
	public String getSingle_Detail_Data_Exceed_Limit() {
		return SINGLE_DETAIL_DATA_EXCEED_LIMIT;
	}
	public String getSuccess() {
		return SUCCESS;
	}
	public String getSystem_Error() {
		return SYSTEM_ERROR;
	}
	public String getTrade_Has_Closed() {
		return TRADE_HAS_CLOSED;
	}
	public String getTrade_Has_Finished() {
		return TRADE_HAS_FINISHED;
	}
	public String getTrade_Has_Refunded() {
		return TRADE_HAS_REFUNDED;
	}
	public String getTrade_Not_Exists() {
		return TRADE_NOT_EXISTS;
	}
	public String getTrade_Status_Error() {
		return TRADE_STATUS_ERROR;
	}
	public String getTxn_Result_Account_Balance_Not_Enough() {
		return TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public String getTxn_Result_Account_Status_Not_Valid() {
		return TXN_RESULT_ACCOUNT_STATUS_NOT_VALID;
	}
	public String getTxn_Result_No_Such_Account() {
		return TXN_RESULT_NO_SUCH_ACCOUNT;
	}
	public String getUnknow_Error() {
		return UNKNOW_ERROR;
	}
	public String getForward_Page_Error() {
		return FORWARD_PAGE_ERROR;
	}
	public String getRoyalty_Fee_Error() {
		return ROYALTY_FEE_ERROR;
	}
	public String getBuyer_Account_Balance_Not_Enough() {
		return BUYER_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public String getRefund_Account_Error() {
		return REFUND_ACCOUNT_ERROR;
	}
	public String getPassword_Error() {
		return PASSWORD_ERROR;
	}
	public String getZero_No_Need_Process()
	{
	  	return ZERO_NO_NEED_PROCESS;
	}
	public String getSame_Account_No_Need_Process() {
		return SAME_ACCOUNT_NO_NEED_PROCESS;
	}
	public String getParameter_Cannot_Null() {
		return PARAMETER_CANNOT_NULL;
	}
	public String getPayment_Type_Cannot_Null() {
		return PAYMENT_TYPE_CANNOT_NULL;
	}
	public String getFrom_Date_Cannot_Null() {
		return FROM_DATE_CANNOT_NULL;
	}
	public String getTo_Date_Cannot_Null() {
		return TO_DATE_CANNOT_NULL;
	}
	public String getBuyer_Account_NotAllowBalance_Not_Enough() {
		return BUYER_ACCOUNT_NOTALLOWBALANCE_NOT_ENOUGH;
	}
	public String getCredit_Account_Balance_Not_Enough() {
		return CREDIT_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public String getMobile_Cannot_Null() {
		return MOBILE_CANNOT_NULL;
	}
	public String getMobile_Validate_Code_Cannot_Null() {
		return MOBILE_VALIDATE_CODE_CANNOT_NULL;
	}
	public String getRepeat_Commit() {
		return REPEAT_COMMIT;
	}
	public String getValidate_Sign_Fail() {
		return VALIDATE_SIGN_FAIL;
	}
	public String getOrder_Not_Exists() {
		return ORDER_NOT_EXISTS;
	}	
	public String getPayment_Type_Error() {
		return PAYMENT_TYPE_ERROR;
	}
	public String getRoyalty_Type_Error() {
		return ROYALTY_TYPE_ERROR;
	}
	public String getQuantity_Error() {
		return QUANTITY_ERROR;
	}
	public String getTotal_Fee_Error() {
		return TOTAL_FEE_ERROR;
	}
	public String getSeller_Email_Error() {
		return SELLER_EMAIL_ERROR;
	}
	public String getBuyer_Parameters_Error() {
		return BUYER_PARAMETERS_ERROR;
	}
	public String getRoyalty_Parameters_Error() {
		return ROYALTY_PARAMETERS_ERROR;
	}
	public String getTotal_Fee_Or_Royalty_Fee_Error() {
		return TOTAL_FEE_OR_ROYALTY_FEE_ERROR;
	}
	public String getTotal_Fee_Or_Buyer_Fee_Error() {
		return TOTAL_FEE_OR_BUYER_FEE_ERROR;
	}
	public String getCredit_Amount_Error() {
		return CREDIT_AMOUNT_ERROR;
	}
	public String getAgent_Email_Error() {
		return AGENT_EMAIL_ERROR;
	}
	public String getCredit_Email_Error() {
		return CREDIT_EMAIL_ERROR;
	}
	public String getCommit_Url_Is_Null() {
		return COMMIT_URL_IS_NULL;
	}
	public String getBatch_Num_Is_Error() {
		return BATCH_NUM_IS_ERROR;
	}
	public String getPrice_Error() {
		return PRICE_ERROR;
	}
	public String getEmail_Not_Exists() {
		return EMAIL_NOT_EXISTS;
	}
	public String getChange_Status_Error() {
		return CHANGE_STATUS_ERROR;
	}
	public String getCredit_Payment_Has_Stop() {
		return CREDIT_PAYMENT_HAS_STOP;
	}
	public String getCredit_Payment_Date_Expire() {
		return CREDIT_PAYMENT_DATE_EXPIRE;
	}
	public String getCredit_Price_Less_Then() {
		return CREDIT_PRICE_LESS_THEN;
	}
	public String getNeed_First_Repayment() {
		return NEED_FIRST_REPAYMENT;
	}
	public String getRepayment_Type_Error() {
		return REPAYMENT_TYPE_ERROR;
	}
	public String getMin_Amount_Error() {
		return MIN_AMOUNT_ERROR;
	}
	public String getLeave_Days_Error() {
		return LEAVE_DAYS_ERROR;
	}
	public String getTransaction_Type_Error() {
		return TRANSACTION_TYPE_ERROR;
	}
	public String getPartner_Can_Not_Null() {
		return PARTNER_CAN_NOT_NULL;
	}
	public String getRepayment_Amount_Is_Too_Big() {
		return REPAYMENT_AMOUNT_IS_TOO_BIG;
	}
	public String getRepayment_Amount_Error() {
		return REPAYMENT_AMOUNT_ERROR;
	}
	public String getType_Error() {
		return TYPE_ERROR;
	}
	public String getBuyer_Email_Error() {
		return BUYER_EMAIL_ERROR;
	}
	public String getTrade_Already_Repayment() {
		// TODO Auto-generated method stub
		return ALREADY_REPAYMENT_ERROR;
	}
	public String getNot_Found__Parameter() {
		// TODO Auto-generated method stub
		return REQUEST_PARAMETER_ERROR;
	}
	public String getChange_Password_Type_Error() {
		// TODO Auto-generated method stub
		return CHANGE_PASSWORD_TYPE_ERROR;
	}
	public String getCredit_Not_Repayment() {
		// TODO Auto-generated method stub
		return CREDIT_NOT_REPAYMENT;
	}
	public String getAgent_Not_credit_Member() {
		// TODO Auto-generated method stub
		return AGENT_NOT_CANT_CREDIT;
	}
	public String getCredit_RepaymentAmount_Sole_Over() {
		// TODO Auto-generated method stub
		return CREDIT_REPAYMENT_SLOP_OVER;
	}
	public String getSHOPNAME_ERROR() {
		return SHOPNAME_ERROR;
	}
	public String getTHISROYALTY_FEE_ERROR() {
		return THISROYALTY_FEE_ERROR;
	}
	public String getALLOW_FEE_ERROR() {
		return ALLOW_FEE_ERROR;
	}
	public String getALLOWBIG_FEE_ERROR() {
		return ALLOWBIG_FEE_ERROR;
	}
	public String getBUYER_PANER_EMAIL_ERROR() {
		return BUYER_PANER_EMAIL_ERROR;
	}
	public String getBUYER_PANER_ACCOUNT_BALANCE_NOT_ENOUGH() {
		return BUYER_PANER_ACCOUNT_BALANCE_NOT_ENOUGH;
	}
	public String getBUYER_FEE_ERROR() {
		return BUYER_FEE_ERROR;
	}
	public String getPAY_FEE_ERROR() {
		return PAY_FEE_ERROR;
	}
	public String getCredit_No_Payout_Stop() {
		return CREDIT_NO_PAYOUT_STOP;
	}
	public String getBUYER_FEE_SOMORE() {
		return BUYER_FEE_SOMORE;
	}
	public String getPAY_FEE_SOMORE() {
		return PAY_FEE_SOMORE;
	}
	public String getREFUND_TYPE_NOTNULL() {
		return REFUND_TYPE_NOTNULL;
	}
	public String getREFUND_TYPE_ERROR() {
		return REFUND_TYPE_ERROR;
	}
	public String getFREEZE_TYPE_ERROR() {
		return FREEZE_TYPE_ERROR;
	}
	public String getCREDIT_PAYMENT_IS_STOP() {
		return CREDIT_PAYMENT_IS_STOP;
	}
	public String getMobile_NOT_FOUND() {
		// TODO Auto-generated method stub
		return MOBILE_NOT_FOUND_IN_AGENT;
	}
}
