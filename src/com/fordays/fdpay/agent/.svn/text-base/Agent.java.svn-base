package com.fordays.fdpay.agent;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.fordays.fdpay.agent._entity._Agent;
import com.fordays.fdpay.system.Bank;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.Province;

public class Agent extends _Agent
{
	private static final long serialVersionUID = 1L;
	private static final int CERT_STATUS_0 = 0;// 没有申请证书
	private static final int CERT_STATUS_1 = 1;// 有申请证书,但没带证书
	private static final int CERT_STATUS_2 = 2;// 有申请证书,也带了证书，但验证失败
	private static final int CERT_STATUS_3 = 1;// 有申请证书,也带了证书，验证成功
	private int validCertStatus = 1;
	private String repassword;// 预处理的password
	private String repassword2;// 重复的password
	private String repayPassword;// 预处理的交易密码
	private String reLoginName;// 预处理的用户名
	private String checkCode;// 验证码
	private String rand = null;//
	private Account account = new Account();// 银行
	private String contactName;
	private String contactLoginName;
	private FormFile imgFile;
	private String tempBankNum;
	private String beginDate;
	private String endDate;
	private String tempBalance;
	private Province province;
	private City city;
	private Bank bank = new Bank();
	private int provinceId;
	private int cityId;
	private int bankId;
	private int checkMoneyCount = 0;
	private String taskTimestampStatus = "";
	private List<QuestionsAndAnswers> listquestionanswer;
	private long coterieId;
	private String realName;
	private String forwardPage = "";
	public static long status_0 = 0;// 注册
	public static long status_1 = 1;// 激活
	public static long status_2 = 2;// 申请实名认证
	public static long status_3 = 3;// 实名认证通过
	public static long status_7 = 7;// 冻结
	public static long status_10 = 10;// 注销
	public static long status_31 = 31;// 打款成功

	public static long mobile_status_0 = 0;// 未绑定
	public static long mobile_status_1 = 1;// 绑定
	public static long msg_type_1 = 1;// 申请绑定手机
	public static long msg_type_2 = 2;// 申请取消绑定
	public static long msg_type_3 = 3;// 手机找回登录密码，支付密码
	public static int ACCOUNT_STATUS_0 = 0;// 未开通
	public static int ACCOUNT_STATUS_1 = 1;// 正常
	public static int ACCOUNT_STATUS_2 = 2;// 冻结
	public static int ACCOUNT_STATUS_3 = 3;// 待激活邮箱
	private String mobileValidateCode;
	private String tempMobileValidateCode;
	private String mobileBusiness = "";
	private String validateMobileCode;
	private BigDecimal balance;
	private BigDecimal balanceAndCheckAmount;
	private String fromDate;
	private String toDate;
	private long orderDetailsType;
	private String listAttachName;// 上传文件保存的Session名称
	private String serialNo;
	
	private String repaymentType;
	private String leaveDays;
	private String minAmount;
	private String transactionScope;
	private String expireDate;
  	private String createQmAccount;
	public String getCreateQmAccount() {
		Calendar ca = Calendar.getInstance(); 
		System.out.println(""+ca.get(Calendar.YEAR)+ca.get(Calendar.MONTH)+ca.get(Calendar.DATE));
		String left=""+ca.get(Calendar.YEAR)+ca.get(Calendar.MONTH)+ca.get(Calendar.DATE);
		Random random = new Random();
		int x = random.nextInt(998)+1;
		
		String s =x+"";
		System.out.println(s);
		System.out.println(s.length());
		if(s.length()==1){
			left+="00"+s;
		}else if(s.length()==2){
			left+="0"+s;
		}else if(s.length()==3){
			left+=s;
		}
		System.out.println("获取新创建的钱门帐号:"+left+"@qmpay.com");
		return left+"@qmpay.com";
	}

	public void setCreateQmAccount(String createQmAccount) {
		this.createQmAccount = createQmAccount;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getListAttachName()
	{
		return listAttachName;
	}

	public void setListAttachName(String listAttachName)
	{
		this.listAttachName = listAttachName;
	}

	public String getLoginName()
	{

		return this.loginName.trim();
	}

	public String getMobileBusiness()
	{
		return mobileBusiness;
	}

	public void setMobileBusiness(String mobileBusiness)
	{
		this.mobileBusiness = mobileBusiness;
	}

	public String getMobileValidateCode()
	{
		return mobileValidateCode;
	}

	public void setMobileValidateCode(String mobileValidateCode)
	{
		this.mobileValidateCode = mobileValidateCode;
	}

	public String getTempMobileValidateCode()
	{
		return tempMobileValidateCode;
	}

	public void setTempMobileValidateCode(String tempMobileValidateCode)
	{
		this.tempMobileValidateCode = tempMobileValidateCode;
	}

	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public long getCoterieId()
	{
		return coterieId;
	}

	public void setCoterieId(long coterieId)
	{
		this.coterieId = coterieId;
	}

	public List<QuestionsAndAnswers> getListquestionanswer()
	{
		return listquestionanswer;
	}

	public void setListquestionanswer(List<QuestionsAndAnswers> listquestionanswer)
	{
		this.listquestionanswer = listquestionanswer;
	}

	public String getTempBalance()
	{
		if (this.tempBalance == null)
		{
			this.tempBalance = "";
		}
		return tempBalance;
	}

	public void setTempBalance(String tempBalance)
	{
		this.tempBalance = tempBalance;
	}

	public String getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(String beginDate)
	{

		this.beginDate = beginDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getTempBankNum()
	{
		return tempBankNum;
	}

	public void setTempBankNum(String tempBankNum)
	{
		this.tempBankNum = tempBankNum;
	}

	public FormFile getImgFile()
	{

		return imgFile;
	}

	public void setImgFile(FormFile imgFile)
	{
		this.imgFile = imgFile;
	}

	public String getContactLoginName()
	{
		return contactLoginName;
	}

	public void setContactLoginName(String contactLoginName)
	{
		this.contactLoginName = contactLoginName;
	}

	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public String getReLoginName()
	{
		return reLoginName;
	}

	public void setReLoginName(String reLoginName)
	{
		this.reLoginName = reLoginName;
	}

	public String getRepassword()
	{
		return repassword;
	}

	public void setRepassword(String repassword)
	{
		this.repassword = repassword;
	}

	public String getRepayPassword()
	{
		return repayPassword;
	}

	public void setRepayPassword(String repayPassword)
	{
		this.repayPassword = repayPassword;
	}

	public String getCheckCode()
	{
		return checkCode;
	}

	public void setCheckCode(String checkCode)
	{
		this.checkCode = checkCode;
	}

	public String getRand()
	{
		return rand;
	}

	public void setRand(String rand)
	{
		this.rand = rand;
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Province getProvince()
	{
		return province;
	}

	public void setProvince(Province province)
	{
		this.province = province;
	}

	public City getCity()
	{
		return city;
	}

	public void setCity(City city)
	{
		this.city = city;
	}

	public Bank getBank()
	{
		return bank;
	}

	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	public String getRepassword2()
	{
		return repassword2;
	}

	public void setRepassword2(String repassword2)
	{
		this.repassword2 = repassword2;
	}

	public String getTaskTimestampStatus()
	{
		return taskTimestampStatus;
	}

	public void setTaskTimestampStatus(String taskTimestampStatus)
	{
		this.taskTimestampStatus = taskTimestampStatus;
	}

	public int getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(int provinceId)
	{
		this.provinceId = provinceId;
	}

	public int getCityId()
	{
		return cityId;
	}

	public void setCityId(int cityId)
	{
		this.cityId = cityId;
	}

	public int getBankId()
	{
		return bankId;
	}

	public void setBankId(int bankId)
	{
		this.bankId = bankId;
	}

	public int getCheckMoneyCount()
	{
		return checkMoneyCount;
	}

	public void setCheckMoneyCount(int checkMoneyCount)
	{
		this.checkMoneyCount = checkMoneyCount;
	}

	public String getForwardPage()
	{
		return forwardPage;
	}

	public void setForwardPage(String forwardPage)
	{
		this.forwardPage = forwardPage;
	}

	public String getMobilePayStatusCaption()
	{
		if (this.getMobilePayStatus() == 1)
		{
			return "开通";
		}
		else
			return "未开通";
	}

	public String getMobileQuestionStatusCaption()
	{
		if (this.getMobileQuestionStatus() == 1)
		{
			return "开通";
		}
		else
			return "未开通";
	}

	public String getMobileLoginStatusCaption()
	{
		if (this.getMobileLoginStatus() == 1)
		{
			return "开通";
		}
		else
			return "未开通";
	}

	public String getMobilePasswordStatusCaption()
	{
		if (this.getMobilePasswordStatus() == 1)
		{
			return "开通";
		}
		else
			return "未开通";
	}

	public String getMobileBindStatusCaption()
	{
		if (this.getMobileBindStatus() == 1)
		{
			return "开通";
		}
		else
			return "未开通";
	}

	public String getCanPayCaption()
	{
		if (this.getMobileBindStatus() == Agent.mobile_status_1)
		{
			if (this.canPay == Agent.mobile_status_0)
			{
				return "未开通";
			}
			else
			{
				return "已开通";
			}
		}
		else
		{
			return "未开通";
		}
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
		loginName = "";
	}

	public String getMobilePhone()
	{
		if (mobilePhone == null)
			return "";
		return this.mobilePhone;
	}

	public String getValidateMobileCode()
	{
		return validateMobileCode;
	}

	public void setValidateMobileCode(String validateMobileCode)
	{
		this.validateMobileCode = validateMobileCode;
	}

	public BigDecimal getAllowBalanceAndCheckAmount()
	{
		return this.allowBalance.add(this.checkAmount);
	}

	public BigDecimal getBalanceAndCheckAmount()
	{
		return this.allowBalance.add(this.notallowBalance).add(this.checkAmount)
		    .add(this.creditBalance);
	}



	public BigDecimal getCheckAmount()
	{
		if (this.checkAmount == null)
		{
			this.checkAmount = new BigDecimal(0);
		}
		return this.checkAmount;
	}

	public BigDecimal getAllowBalance()
	{
		if (this.allowBalance == null)
		{
			this.allowBalance = new BigDecimal(0);
		}
		return this.allowBalance;
	}

	public BigDecimal getCreditBalance()
	{
		if (this.creditBalance == null)
		{
			this.creditBalance = new BigDecimal(0);
		}
		return this.creditBalance;
	}

	public BigDecimal getNotallowBalance()
	{
		if (this.notallowBalance == null)
		{
			this.notallowBalance = new BigDecimal(0);
		}
		return this.notallowBalance;
	}

	public Long getRegisterType()
	{
		if (this.registerType == null) { return new Long(0); }
		return this.registerType;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public long getOrderDetailsType()
	{
		return orderDetailsType;
	}

	public void setOrderDetailsType(long orderDetailsType)
	{
		this.orderDetailsType = orderDetailsType;
	}

	public int getValidCertStatus()
	{
		
		return validCertStatus;
	}

	public void setValidCertStatus(int validCertStatus)
	{
		this.validCertStatus = validCertStatus;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}

	public String getTransactionScope() {
		return transactionScope;
	}

	public void setTransactionScope(String transactionScope) {
		this.transactionScope = transactionScope;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
	private AgentBalance agentBalance;
	public AgentBalance getAgentBalance()
  {
  	return agentBalance;
  }

	public void setAgentBalance(AgentBalance agentBalance)
  {
  	this.agentBalance = agentBalance;
  }
	
	
}
