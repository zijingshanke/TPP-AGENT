//package com.hitrust.b2b.request.entry;
//
//import com.hitrust.b2b.request.TrxException;
//import com.hitrust.b2b.util.*;
//
//public class PayInfoEntry implements RequestEntry {
//
//	private String PayerCustNo;
//	private String PayerCustOperNo;
//	private String PayerAcctNo;
//	private String TrnxAmount;
//	private String Digest;
//	private static final int PAYERCUSTNO_LENGTH = 15;
//	private static final int PAYERCUSTOPERNO_LENGTH = 15;
//	private static final int PAYERACCTNO_LENGTH = 32;
//	private static final int PAYEEACCTNO_LENGTH = 32;
//	private static final int TRNXAMOUNT_LENGTH = 16;
//	private static final int DIGEST_LENGTH = 200;
//
//	public PayInfoEntry() {
//		PayerCustNo = "";
//		PayerCustOperNo = "";
//		PayerAcctNo = "";
//		TrnxAmount = "";
//		Digest = "";
//	}
//
//	public void checkEntry(String requestType) throws TrxException {
//		if (requestType.equals("P006") || requestType.equals("P001")
//				|| requestType.equals("P002"))
//			DataVerify.checkEmptyAndLength(PayerCustNo, "买方客户号", "Y", 15);
//		else if ("3".equals(MerchantConfig.getMerchantPayType())
//				&& (requestType.equals("P003") || requestType.equals("P004")))
//			DataVerify.checkEmptyAndLength(PayerCustNo, "卖方客户号", "Y", 15);
//		else
//			DataVerify.checkEmptyAndLength(PayerCustNo, "买方客户号", "N", 15);
//		DataVerify.checkEmptyAndLength(TrnxAmount, "交易金额", "Y", 16);
//		DataVerify.isValidAmount(TrnxAmount, "交易金额", 15, 2);
//		if (requestType.equals("P004") || requestType.equals("P003"))
//			DataVerify.checkEmptyAndLength(Digest, "摘要", "Y", 200);
//		else
//			DataVerify.checkEmptyAndLength(Digest, "摘要", "N", 200);
//	}
//
//	public XMLDocument composeEntry() {
//		String payInfoMsg = "<PayInfo><PayerCustNo>" + PayerCustNo + "</"
//				+ "PayerCustNo" + ">" + "<" + "TrnxAmount" + ">" + TrnxAmount
//				+ "</" + "TrnxAmount" + ">" + "<" + "Digest" + ">" + Digest
//				+ "</" + "Digest" + ">" + "</" + "PayInfo" + ">";
//		return new XMLDocument(payInfoMsg);
//	}
//
//	public String getDigest() {
//		return Digest;
//	}
//
//	public void setDigest(String digest) {
//		Digest = digest;
//	}
//
//	public String getPayerCustNo() {
//		return PayerCustNo;
//	}
//
//	public void setPayerCustNo(String payerCustNo) {
//		PayerCustNo = payerCustNo;
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
//	public String getPayerAcctNo() {
//		return PayerAcctNo;
//	}
//
//	public void setPayerAcctNo(String payerAcctNo) {
//		PayerAcctNo = payerAcctNo;
//	}
//
//	public String getPayerCustOperNo() {
//		return PayerCustOperNo;
//	}
//
//	public void setPayerCustOperNo(String payerCustOperNo) {
//		PayerCustOperNo = payerCustOperNo;
//	}
//}