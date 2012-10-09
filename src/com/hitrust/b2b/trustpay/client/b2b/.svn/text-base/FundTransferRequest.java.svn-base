//// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://kpdus.tripod.com/jad.html
//// Decompiler options: packimports(3) fieldsfirst ansi space 
//// Source File Name:   FundTransferRequest.java
//
//package com.hitrust.b2b.trustpay.client.b2b;
//
//import com.hitrust.b2b.trustpay.client.*;
//
//// Referenced classes of package com.hitrust.b2b.trustpay.client.b2b:
////			TrnxInfo
//
//public class FundTransferRequest extends TrxRequest {
//
//	private TrnxInfo iTrnxInfo;
//	private String iMerchantTrnxNo;
//	private String iTrnxDate;
//	private double iTrnxAmount;
//	private String iTrnxTime;
//	private String iAccountDBNo;
//	private String iAccountDBName;
//	private String iAccountDBBank;
//	private String iResultNotifyURL;
//	private String iMerchantRemarks;
//	public static final int MERCHANT_TRNX_URL_LEN = 100;
//	public static final int ACCOUNT_NO_URL_LEN = 20;
//	public static final int ACCOUNT_NAME_URL_LEN = 50;
//	public static final int ACCOUNT_BANK_URL_LEN = 50;
//	public static final int RESULT_NOTIFY_URL_LEN = 200;
//	public static final int MERCHANT_REMARKS_LEN = 200;
//
//	public FundTransferRequest() {
//		super("B2B");
//		iTrnxInfo = null;
//		iMerchantTrnxNo = "";
//		iTrnxDate = "";
//		iTrnxAmount = 0.0D;
//		iTrnxTime = "";
//		iAccountDBNo = "";
//		iAccountDBName = "";
//		iAccountDBBank = "";
//		iResultNotifyURL = "";
//		iMerchantRemarks = "";
//	}
//
//	public FundTransferRequest(XMLDocument aXMLDocument) {
//		super("B2B");
//		iTrnxInfo = null;
//		iMerchantTrnxNo = "";
//		iTrnxDate = "";
//		iTrnxAmount = 0.0D;
//		iTrnxTime = "";
//		iAccountDBNo = "";
//		iAccountDBName = "";
//		iAccountDBBank = "";
//		iResultNotifyURL = "";
//		iMerchantRemarks = "";
//		setTrnxInfo(new TrnxInfo(aXMLDocument));
//		setMerchantTrnxNo(aXMLDocument.getValueNoNull("MerchantTrnxNo"));
//		try {
//			setTrnxAmount(Double.parseDouble(aXMLDocument
//					.getValueNoNull("TrnxAmount")));
//		} catch (Exception exception) {
//		}
//		setTrnxDate(aXMLDocument.getValueNoNull("TrnxDate"));
//		setTrnxTime(aXMLDocument.getValueNoNull("TrnxTime"));
//		setAccountDBNo(aXMLDocument.getValueNoNull("AccountDBNo"));
//		setAccountDBName(aXMLDocument.getValueNoNull("AccountDBName"));
//		setAccountDBBank(aXMLDocument.getValueNoNull("AccountDBBank"));
//		setResultNotifyURL(aXMLDocument.getValueNoNull("ResultNotifyURL"));
//		setMerchantRemarks(aXMLDocument.getValueNoNull("MerchantRemarks"));
//	}
//
//	protected XMLDocument getRequestMessage() {
//		StringBuffer tXMLBuffer = new StringBuffer();
//		tXMLBuffer
//				.append("<TrxRequest>")
//				.append("<TrxType>FundTransfer</TrxType>")
//				.append(
//						"<MerchantTrnxNo>" + iMerchantTrnxNo
//								+ "</MerchantTrnxNo>")
//				.append(
//						"<TrnxAmount>"
//								+ DataVerifier.Double2String(iTrnxAmount)
//								+ "</TrnxAmount>")
//				.append("<TrnxDate>" + iTrnxDate + "</TrnxDate>")
//				.append("<TrnxTime>" + iTrnxTime + "</TrnxTime>")
//				.append("<AccountDB>")
//				.append("<AccountDBNo>" + iAccountDBNo + "</AccountDBNo>")
//				.append("<AccountDBName>" + iAccountDBName + "</AccountDBName>")
//				.append("<AccountDBBank>" + iAccountDBBank + "</AccountDBBank>")
//				.append("</AccountDB>").append(
//						iTrnxInfo.getXMLDocument().toString()).append(
//						"<ResultNotifyURL>" + iResultNotifyURL
//								+ "</ResultNotifyURL>").append(
//						"<MerchantRemarks>" + iMerchantRemarks
//								+ "</MerchantRemarks>").append("</TrxRequest>");
//		String tMessage = tXMLBuffer.toString();
//		return new XMLDocument(tMessage);
//	}
//
//	protected void checkRequest() throws TrxException {
//		if (iTrnxInfo == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定交易详细信息！");
//		if (iMerchantTrnxNo == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定商户交易编号！");
//		if (iTrnxAmount <= 0.0D)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定交易金额！");
//		if (iTrnxDate == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定交易日期！");
//		if (iTrnxTime == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定交易时间！");
//		if (iAccountDBNo == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定收款方账号！");
//		if (iAccountDBName == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定收款方账户名！");
//		if (iAccountDBBank == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定收款方账户开户行！");
//		if (iResultNotifyURL == null)
//			throw new TrxException("1100", "商户提交的交易资料不完整", "未设定交易结果回传网址！");
//		if (!iTrnxInfo.isValid())
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易详细信息不合法！");
//		if (iMerchantTrnxNo.length() == 0)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "商户交易编号不合法！");
//		if (iMerchantTrnxNo.getBytes().length > 100)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "商户交易编号不合法！");
//		if (iAccountDBNo.length() == 0)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "收款方账号不合法！");
//		if (iAccountDBNo.getBytes().length > 20)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "收款方账号不合法！");
//		if (iAccountDBName.length() == 0)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "收款方账户名不合法！");
//		if (iAccountDBName.getBytes().length > 50)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "收款方账户名不合法！");
//		if (iAccountDBBank.length() == 0)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "收款方账户开户行不合法！");
//		if (iAccountDBBank.getBytes().length > 50)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "收款方账户开户行不合法！");
//		if (!DataVerifier.isValidDate(iTrnxDate))
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易日期不合法！");
//		if (!DataVerifier.isValidTime(iTrnxTime))
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易时间不合法！");
//		if (!DataVerifier.isValidAmount(iTrnxAmount, 2))
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易金额不合法！");
//		if (!DataVerifier.isValidURL(iResultNotifyURL))
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易结果回传网址不合法！");
//		if (iResultNotifyURL.length() == 0)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易结果回传网址不合法！");
//		if (iResultNotifyURL.getBytes().length > 200)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "交易结果回传网址不合法！");
//		if (iMerchantRemarks.getBytes().length > 200)
//			throw new TrxException("1101", "商户提交的交易资料不合法", "商户备注信息不合法！");
//		else
//			return;
//	}
//
//	protected TrxResponse constructResponse(XMLDocument aResponseMessage)
//			throws TrxException {
//		return new TrxResponse(aResponseMessage);
//	}
//
//	public FundTransferRequest setTrnxInfo(TrnxInfo aTrnxInfo) {
//		iTrnxInfo = aTrnxInfo;
//		return this;
//	}
//
//	public TrnxInfo getTrnxInfo() {
//		return iTrnxInfo;
//	}
//
//	public FundTransferRequest setMerchantTrnxNo(String aMerchantTrnxNo) {
//		iMerchantTrnxNo = aMerchantTrnxNo;
//		return this;
//	}
//
//	public String getMerchantTrnxNo() {
//		return iMerchantTrnxNo;
//	}
//
//	public FundTransferRequest setTrnxAmount(double aTrnxAmount) {
//		iTrnxAmount = aTrnxAmount;
//		return this;
//	}
//
//	public double getTrnxAmount() {
//		return iTrnxAmount;
//	}
//
//	public FundTransferRequest setTrnxDate(String aTrnxDate) {
//		iTrnxDate = aTrnxDate.trim();
//		return this;
//	}
//
//	public String getTrnxDate() {
//		return iTrnxDate;
//	}
//
//	public FundTransferRequest setTrnxTime(String aTrnxTime) {
//		iTrnxTime = aTrnxTime;
//		return this;
//	}
//
//	public String getTrnxTime() {
//		return iTrnxTime;
//	}
//
//	public FundTransferRequest setAccountDBNo(String aAccountDBNo) {
//		iAccountDBNo = aAccountDBNo;
//		return this;
//	}
//
//	public String getAccountDBNo() {
//		return iAccountDBNo;
//	}
//
//	public FundTransferRequest setAccountDBName(String aAccountDBName) {
//		iAccountDBName = aAccountDBName;
//		return this;
//	}
//
//	public String getAccountDBName() {
//		return iAccountDBName;
//	}
//
//	public FundTransferRequest setAccountDBBank(String aAccountDBBank) {
//		iAccountDBBank = aAccountDBBank;
//		return this;
//	}
//
//	public String getAccountDBBank() {
//		return iAccountDBBank;
//	}
//
//	public FundTransferRequest setResultNotifyURL(String aResultNotifyURL) {
//		iResultNotifyURL = aResultNotifyURL.trim();
//		return this;
//	}
//
//	public String getResultNotifyURL() {
//		return iResultNotifyURL;
//	}
//
//	public FundTransferRequest setMerchantRemarks(String aMerchantRemarks) {
//		iMerchantRemarks = aMerchantRemarks.trim();
//		return this;
//	}
//
//	public String getMerchantRemarks() {
//		return iMerchantRemarks;
//	}
//}