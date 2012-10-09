//package com.fordays.fdpay.bank.abc;
//
//import java.util.ResourceBundle;
//
//import com.fordays.fdpay.bank.BankUtil;
//import com.hitrust.b2b.trustpay.client.b2b.FundTransferRequest;
//import com.hitrust.b2b.trustpay.client.b2b.TrnxInfo;
//import com.hitrust.b2b.trustpay.client.b2b.TrnxItem;
//import com.hitrust.b2b.trustpay.client.b2b.TrnxItems;
//import com.hitrust.b2b.trustpay.client.b2b.TrnxRemark;
//import com.hitrust.b2b.trustpay.client.b2b.TrnxRemarks;
//
//public class AbcB2BcmdToBank {
//	private String MerchantTrnxNo = "";// 商户交易编号（必要信息）
//	private String TrnxDate = "";// 交易日期（必要信息）
//	private String TrnxTime = "";// 交易时间（必要信息）
//	private String TrnxAmount = "";// 交易金额（必要信息）
//	private String AccountDBNo = "";// 收款方账号（必要信息）
//	private String AccountDBName = "";// 收款方账户名
//	private String AccountDBBank = "";// 收款方账户开户行联行号
//	private String ResultNotifyURL = "";// 交易结果接收页面网址（必要信息）
//	private String TMerchantRemarks = "";
//
//	private TrnxItem trnxItem = new TrnxItem();
//	private TrnxItems trnxItems = new TrnxItems();
//	private TrnxRemark trnxRemark = new TrnxRemark();// 交易备注信息对象(必要信息)
//	private TrnxRemarks trnxRemarks = new TrnxRemarks();// 商户备注信息
//	private TrnxInfo trnxInfo = new TrnxInfo();// 交易信息对象（必要信息）
//	private FundTransferRequest fundTransferRequest = new FundTransferRequest();//
//
//	public AbcB2BcmdToBank() {
//		init();
//	}
//
//	public void init() {
//		String res = "com.fordays.fdpay.bank.abc.biz.TrustMerchantB2B";
//		ResourceBundle reb = BankUtil.getResourceBundle(res);
//
//		AccountDBNo = BankUtil.getParameterByName(reb, "AccountDBNo");
//		AccountDBName = BankUtil.getParameterByName(reb, "AccountDBName");
//		AccountDBBank = BankUtil.getParameterByName(reb, "AccountDBBank");
//		ResultNotifyURL = BankUtil.getParameterByName(reb, "ResultNotifyURL");
//	}
//
//	public TrnxInfo getTrnxInfo() {
//		trnxInfo.setTrnxItems(getTrnxItems());
//		trnxInfo.setTrnxRemarks(getTrnxRemarks());
//		trnxInfo.setTrnxOpr(MerchantTrnxNo);
//		return trnxInfo;
//	}
//
//	public TrnxItems getTrnxItems() {
//		trnxItems.addTrnxItem(getTrnxItem());
//		return trnxItems;
//	}
//
//	public TrnxItem getTrnxItem() {
//		trnxItem.setProductID("001");
//		trnxItem.setProductName("钱门在线支付");
//		trnxItem.setQty(1);
//		trnxItem.setUnitPrice(Double.parseDouble(this.getTrnxAmount()));
//		return trnxItem;
//	}
//
//	public TrnxRemarks getTrnxRemarks() {
//		trnxRemarks.addTrnxRemark(getTrnxRemark());
//		return trnxRemarks;
//	}
//
//	public TrnxRemark getTrnxRemark() {
//		trnxRemark.setKey("qmpay");
//		trnxRemark.setValue("www.qmpay.com");
//		return trnxRemark;
//	}
//
//	public FundTransferRequest getFundTransferRequest() {
//		fundTransferRequest.setTrnxInfo(getTrnxInfo());
//		fundTransferRequest.setMerchantTrnxNo(this.MerchantTrnxNo); // 设定商户交易编号（必要）
//		fundTransferRequest.setTrnxAmount(Double.parseDouble(this.TrnxAmount)); // 设定交易金额（必要）
//		fundTransferRequest.setTrnxDate(this.TrnxDate); // 设定交易日期 （必要）
//		fundTransferRequest.setTrnxTime(this.TrnxTime); // 设定交易时间 （必要）
//		fundTransferRequest.setAccountDBNo(this.AccountDBNo); // 设定收款方账号 （必要）
//		fundTransferRequest.setAccountDBName(this.AccountDBName); // 设定收款方账户名（必要）
//		fundTransferRequest.setAccountDBBank(this.AccountDBBank); // 设定收款方账户开户行联行号（必要）
//		fundTransferRequest.setResultNotifyURL(this.ResultNotifyURL); // 设定交易结果回传网址
//		fundTransferRequest.setMerchantRemarks("www.qmpay.com");
//		return fundTransferRequest;
//	}
//
//	public void setTrnxItem(TrnxItem trnxItem) {
//		this.trnxItem = trnxItem;
//	}
//
//	public void setTrnxRemark(TrnxRemark trnxRemark) {
//		this.trnxRemark = trnxRemark;
//	}
//
//	public void setTrnxInfo(TrnxInfo trnxInfo) {
//		this.trnxInfo = trnxInfo;
//	}
//
//	public void setFundTransferRequest(FundTransferRequest fundTransferRequest) {
//		this.fundTransferRequest = fundTransferRequest;
//	}
//
//	public String getMerchantTrnxNo() {
//		return MerchantTrnxNo;
//	}
//
//	public void setMerchantTrnxNo(String merchantTrnxNo) {
//		MerchantTrnxNo = merchantTrnxNo;
//	}
//
//	public String getTrnxDate() {
//		return TrnxDate;
//	}
//
//	public void setTrnxDate(String trnxDate) {
//		TrnxDate = trnxDate;
//	}
//
//	public String getTrnxTime() {
//		return TrnxTime;
//	}
//
//	public void setTrnxTime(String trnxTime) {
//		TrnxTime = trnxTime;
//	}
//
//	public String getTrnxAmount() {
//		return TrnxAmount;
//	}
//
//	public void setTrnxAmount(String trnxAmount) {
//		TrnxAmount = trnxAmount;
//	}
//
//	public String getAccountDBNo() {
//		return AccountDBNo;
//	}
//
//	public void setAccountDBNo(String accountDBNo) {
//		AccountDBNo = accountDBNo;
//	}
//
//	public String getAccountDBName() {
//		return AccountDBName;
//	}
//
//	public void setAccountDBName(String accountDBName) {
//		AccountDBName = accountDBName;
//	}
//
//	public String getAccountDBBank() {
//		return AccountDBBank;
//	}
//
//	public void setAccountDBBank(String accountDBBank) {
//		AccountDBBank = accountDBBank;
//	}
//
//	public String getResultNotifyURL() {
//		return ResultNotifyURL;
//	}
//
//	public void setResultNotifyURL(String resultNotifyURL) {
//		ResultNotifyURL = resultNotifyURL;
//	}
//
//	public void setTrnxItems(TrnxItems trnxItems) {
//		this.trnxItems = trnxItems;
//	}
//
//	public void setTrnxRemarks(TrnxRemarks trnxRemarks) {
//		this.trnxRemarks = trnxRemarks;
//	}
//
//	public String getTMerchantRemarks() {
//		return TMerchantRemarks;
//	}
//
//	public void setTMerchantRemarks(String merchantRemarks) {
//		TMerchantRemarks = merchantRemarks;
//	}
//
//}
