//package com.hitrust.b2b.request;
//
//import com.hitrust.b2b.response.GenericResponse;
//import com.hitrust.b2b.util.DataVerify;
//import com.hitrust.b2b.util.XMLDocument;
//import org.apache.log4j.Category;
//
//public class BindMerchantCertificateRequest extends GenericRequest {
//
//	static Category LOG = Category
//			.getInstance("BindMerchantCertificateRequest");
//	private String MerchantPassword;
//	private String CertificationInfo;
//	private static final int MERCHANTPASSWORD_LENGTH = 40;
//	private static final int CERTIFICATIONINFO_LENGTH = 10000;
//
//	public BindMerchantCertificateRequest() {
//		MerchantPassword = "";
//		CertificationInfo = "";
//	}
//
//	protected void composeRequestMsg() {
//		composeRequestHeaderMsg();
//		String dataBodyMessage = "<DataBody><MerchantTrnxNo>" + MerchantTrnxNo
//				+ "</" + "MerchantTrnxNo" + ">" + "<" + "TrnxCode" + ">"
//				+ TrnxCode + "</" + "TrnxCode" + ">" + "<" + "MerchantPassword"
//				+ ">" + MerchantPassword + "</" + "MerchantPassword" + ">"
//				+ "<" + "CertificationInfo" + ">" + CertificationInfo + "</"
//				+ "CertificationInfo" + ">" + "</" + "DataBody" + ">";
//		dataBodyDocument = new XMLDocument(dataBodyMessage);
//		totalDocument = new XMLDocument(totalDocument.toString()
//				+ dataBodyDocument.toString());
//	}
//
//	protected void checkRequestMsg() throws TrxException {
//		checkRequestHeaderMsg();
//		DataVerify.checkEmptyAndLength(CertificationInfo, "商户证书信息", "Y", 10000);
//	}
//
//	public String getCertificationInfo() {
//		return CertificationInfo;
//	}
//
//	public void setCertificationInfo(String certificationInfo) {
//		CertificationInfo = certificationInfo;
//	}
//
//	public String getMerchantPassword() {
//		return MerchantPassword;
//	}
//
//	public void setMerchantPassword(String merchantPassword) {
//		MerchantPassword = merchantPassword;
//	}
//
//	public static void main(String args[]) {
//		BindMerchantCertificateRequest qr = new BindMerchantCertificateRequest();
//		qr.Version = "1.0";
//		qr.SignFlag = "1";
//		qr.Language = "UTF-8";
//		qr.ClientTime = "20051008160923";
//		qr.MerchantNo = "131981";
//		qr.MerchantTrnxNo = "123456";
//		qr.TrnxCode = "B001";
//		qr.MerchantPassword = "888888";
//		qr.CertificationInfo = "MIICWTCCAcKgAwIBAgIKUDrKEFNbAAADwDANBgkqhkiG9w0BAQUFADAfMQ8wDQYDVQQDEwZBQkMgQ0ExDDAKBgNVBAoTA0FCQzAeFw0wNDA0MjcwNzM2MzJaFw0wNjA0MjcwNzM0MjRaMGgxPzA9BgNVBAMTNmIyYy5iMmNtZXJjaGFudC4yMzEwMDAwMDAwMDAwMDAwUi4xOTc5MTAyNDE5Nzk0MDYuMDAwMTEXMBUGA1UECxMOUGF5bWVudEdhdGV3YXkxDDAKBgNVBAoTA0FCQzCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAyvvVIpEra0UYWYEoRvaCRMDtpFs87rptWBxelmZYGNgCDMDzAXNr7Cz1wLZo2lPICErqcs8gSe6n9jIwdu8/43cUvvteNe1JmM0q9lhQ44e97hohS4KoaWJFJvSRCt9PEQWCc0aqZ1wpsZYEYwMEDlgYHzytSf69SI5JufJ0ivsCAwEAAaNTMFEwPgYDVR0fBDcwNTAzoDGgL6QtMCsxDTALBgNVBAMTBGNybDQxDDAKBgNVBAsTA2NybDEMMAoGA1UEChMDQUJDMA8GA1UdYwQIAwYA/wAAAAAwDQYJKoZIhvcNAQEFBQADgYEAbReH9FbkWWdnCVuynqCaUWdQ0ddMeVyJqz9Y3zaelvkawcdSC1Z4DLtxy0ILjIMlEmjLOxd8CLrAzPtYJlDkCGsDeCkmNQn53oTO5S/oNi8MmG01JOYZgsRrmbQmsdMrQ+i8DLZadvcMdKxq7vreaBR4ypYf+Z54tErHzBFkU5c=";
//		GenericResponse rs = qr.postRequest();
//		System.out.println(rs.getCode() + " : " + rs.getMessage());
//		if (rs.getDocument() != null)
//			System.out.println(rs.getDocument().getFormatDocument(" "));
//	}
//
//}