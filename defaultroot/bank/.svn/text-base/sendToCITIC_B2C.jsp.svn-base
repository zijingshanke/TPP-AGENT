<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@page import="com.fordays.fdpay.bank.citic.CiticB2CcmdToBank;"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!-- JSP页面:/bank/sendToCITIC_B2C -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
			CiticB2CcmdToBank bank = (CiticB2CcmdToBank) request
			.getAttribute("CiticB2CcmdToBank");
	String interfacePath = bank.getInterfacePath().trim();
	String ORDERNO = bank.getORDERNO().trim();
	String ORDERDATE = bank.getORDERDATE().trim();
	String ORDERTIME = bank.getORDERTIME().trim();
	String ORDERAMT = bank.getORDERAMT().trim();
	String E3RDPAYNO = bank.getE3RDPAYNO().trim();
	String NOTIFYURL = bank.getNOTIFYURL().trim();
	String SIGNREQMSG = bank.getSIGNREQMSG().trim();
%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
</head>
<body>
	<c:import url="./waitConnBank.jsp" />
	<form name="sendOrder" method="post" action="<%=interfacePath%>">
		<input type="hidden" name="ORDERNO" value="<%=ORDERNO%>" />
		<input type="hidden" name="ORDERDATE" value="<%=ORDERDATE%>" />
		<input type="hidden" name="ORDERAMT" value="<%=ORDERAMT%>" />
		<input type="hidden" name="ORDERTIME" value="<%=ORDERTIME%>" />
		<input type="hidden" name="E3RDPAYNO" value="<%=E3RDPAYNO%>" />
		<input type="hidden" name="NOTIFYURL" value="<%=NOTIFYURL%>" />
		<input type="hidden" name="SIGNREQMSG" value="<%=SIGNREQMSG%>" />
		<!-- <input type="submit" value="中信银行支付" />-->
	</form>
	<script language="javascript"> 		
 		document.forms[0].submit();	
	</script>
</body>