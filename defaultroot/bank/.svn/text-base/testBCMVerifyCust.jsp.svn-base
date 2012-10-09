<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.fordays.fdpay.bank.bcm.BcmB2CcmdToBank"%>
<!-- JSP页面:testBCMVerifyCust -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
	</head>
	<body bgcolor="#FFFFFF" text="#000000">
		<%
			String card = request.getParameter("card");
			String custName = request.getParameter("custName");
			String certType = request.getParameter("certType");
			String certNo = request.getParameter("certNo");
			BcmB2CcmdToBank bank = new BcmB2CcmdToBank();
			bank.setCardNo(card);
			bank.setCustName(custName);
			bank.setCertType(certType);
			bank.setCertNo(certNo);
			bank.getVerifyCustCmd();
		%>
	</body>
</html>
