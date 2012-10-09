//// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://kpdus.tripod.com/jad.html
//// Decompiler options: packimports(3) fieldsfirst ansi space 
//// Source File Name:   TrxResponse.java
//
//package com.hitrust.b2b.trustpay.client;
//
//
//// Referenced classes of package com.hitrust.b2b.trustpay.client:
////			TrxException, XMLDocument
//
//public class TrxResponse
//{
//
//	protected String iReturnCode;
//	public static final String RC_SUCCESS = "0000";
//	public static final String STATUS_ORIGINAL = "0";
//	public static final String STATUS_CHECK = "1";
//	public static final String STATUS_SUCCESS = "2";
//	public static final String STATUS_FAILURE = "3";
//	public static final String STATUS_REJECT = "4";
//	public static final String STATUS_CANCEL = "5";
//	public static final String STATUS_NO_RESPONSE = "9";
//	protected String iErrorMessage;
//	protected XMLDocument iResponseMessage;
//
//	public TrxResponse(XMLDocument aXMLDocument)
//		throws TrxException
//	{
//		iReturnCode = "";
//		iErrorMessage = "";
//		iResponseMessage = new XMLDocument("");
//		init(aXMLDocument);
//	}
//
//	public TrxResponse(String aReturnCode, String aErrorMessage)
//	{
//		iReturnCode = "";
//		iErrorMessage = "";
//		iResponseMessage = new XMLDocument("");
//		setReturnCode(aReturnCode);
//		setErrorMessage(aErrorMessage);
//	}
//
//	protected TrxResponse()
//	{
//		iReturnCode = "";
//		iErrorMessage = "";
//		iResponseMessage = new XMLDocument("");
//	}
//
//	protected void init(XMLDocument aXMLDocument)
//		throws TrxException
//	{
//		XMLDocument tReturnCode = aXMLDocument.getValue("ReturnCode");
//		if (tReturnCode == null)
//			throw new TrxException("1303", "无法辨识网上支付平台的交易结果", "无法取得[ReturnCode]!");
//		setReturnCode(tReturnCode.toString());
//		XMLDocument tErrorMessage = aXMLDocument.getValue("ErrorMessage");
//		if (tErrorMessage != null)
//			setErrorMessage(tErrorMessage.toString());
//		iResponseMessage = aXMLDocument;
//	}
//
//	public boolean isSuccess()
//	{
//		return iReturnCode.equals("0000");
//	}
//
//	public TrxResponse setReturnCode(String aReturnCode)
//	{
//		iReturnCode = aReturnCode.trim();
//		return this;
//	}
//
//	public String getReturnCode()
//	{
//		return iReturnCode;
//	}
//
//	public TrxResponse setErrorMessage(String aErrorMessage)
//	{
//		iErrorMessage = aErrorMessage.trim();
//		return this;
//	}
//
//	public String getErrorMessage()
//	{
//		return iErrorMessage;
//	}
//
//	public String getValue(String aTag)
//	{
//		return iResponseMessage.getValueNoNull(aTag);
//	}
//}