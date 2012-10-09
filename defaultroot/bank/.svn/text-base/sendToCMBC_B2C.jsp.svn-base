<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@page import="com.fordays.fdpay.bank.cmbc.CmbcB2CcmdToBank;"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- JSP页面:/bank/sendToCMBC_B2C -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
			CmbcB2CcmdToBank bank = (CmbcB2CcmdToBank) request
			.getAttribute("CmbcB2CcmdToBank");
	String interfacePath = bank.getInterfacePath();
	String billNo = bank.getBillNo();
	String txAmt = bank.getTxAmt();
	String PayerCurr = bank.getPayerCurr();
	String txDate = bank.getTxDate();
	String txTime = bank.getTxTime();
	String corpID = bank.getCorpID();
	String corpName = bank.getCorpName();
	String Billremark1 = bank.getBillremark1();
	String Billremark2 = bank.getBillremark2();
	String CorpRetType = bank.getCorpRetType();
	String retUrl = bank.getRetUrl();
	String langMAC = bank.getLangMAC();
	String orderinfo = bank.getMerSignMsg();
%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
</head>
<body>
	<c:import url="./waitConnBank.jsp" />
	<form name="sendOrder" method="post" action="<%=interfacePath%>">
		<input type="hidden" name="billNo" value="<%=billNo%>" />
		<input type="hidden" name="txAmt" value="<%=txAmt%>" />
		<input type="hidden" name="PayerCurr" value="<%=PayerCurr%>" />
		<input type="hidden" name="txDate" value="<%=txDate%>" />
		<input type="hidden" name="txTime" value="<%=txTime%>" />
		<input type="hidden" name="corpID" value="<%=corpID%>" />
		<input type="hidden" name="corpName" value="<%=corpName%>" />
		<input type="hidden" name="Billremark1" value="<%=Billremark1%>" />
		<input type="hidden" name="Billremark2" value="<%=Billremark2%>" />
		<input type="hidden" name="CorpRetType" value="<%=CorpRetType%>" />
		<input type="hidden" name="retUrl" value="<%=retUrl%>" />
		<input type="hidden" name="langMAC" value="<%=langMAC%>" />
		<input type="hidden" name="orderinfo" value="<%=orderinfo%>" />
		<!--<input type="submit" value="民生银行支付" /> -->
	</form>
	<script language="javascript"> 		
 	     document.forms[0].submit();	
	</script>
</body>

















