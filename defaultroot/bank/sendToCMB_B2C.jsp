<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@page import="com.fordays.fdpay.bank.cmb.CmbB2CcmdToBank;"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- JSP页面:/bank/sendToCMB_B2C -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	CmbB2CcmdToBank bank = (CmbB2CcmdToBank) request
			.getAttribute("CmbB2CcmdToBank");
	String interfacePath = bank.getInterfacePath();
	String branchid = bank.getBranchid();
	String cono = bank.getCono();
	String date = bank.getDate();
	String billno = bank.getBillno();
	String amount = bank.getAmount();
	String merchantPara = bank.getMerchantPara();
	String merchantUrl = bank.getMerchantUrl();
	String merchantCode = bank.getMerchantCode();
%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
</head>
<body>
	<c:import url="./waitConnBank.jsp" />
	<form name="sendOrder" method="post" action="<%=interfacePath%>">
		<input type="hidden" name="branchid" value="<%=branchid%>" />
		<input type="hidden" name="cono" value="<%=cono%>" />
		<input type="hidden" name="date" value="<%=date%>" />
		<input type="hidden" name="BillNo" value="<%=billno%>" />
		<input type="hidden" name="amount" value="<%=amount%>" />
		<input type="hidden" name="merchantPara" value="<%=merchantPara%>" />
		<input type="hidden" name="merchantUrl" value="<%=merchantUrl%>" />
		<input type="hidden" name="merchantCode" value="<%=merchantCode%>" />
		<!-- --> <input type="submit" value="招商银行支付" /> 
	</form>
	<script language="javascript"> 		
 	    // document.forms[0].submit();	
	</script>
</body>

















