<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page import="com.fordays.fdpay.bank.icbc.IcbcB2CcmdToBank"%>
<!-- JSP页面:/bank/sendToICBC_B2C -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
			IcbcB2CcmdToBank bank = (IcbcB2CcmdToBank) request
			.getAttribute("IcbcB2CcmdToBank");
	String interfacePath = bank.getInterfacePath().trim();
	String interfaceName = bank.getInterfaceName().trim();
	String interfaceVersion = bank.getInterfaceVersion().trim();
	String tranData = bank.getTranData().trim();
	String merSignMsg = bank.getMerSignMsg().trim();
	String merCert = bank.getMerCert().trim();
%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
</head>
<body>
	<c:import url="./waitConnBank.jsp" />
	<form name="sendOrder" method="post" action="<%=interfacePath%>">
		<!--接口名称-->
		<input type="hidden" name="interfaceName" value="<%=interfaceName%>" />
		<!--接口版本号-->
		<input type="hidden" name="interfaceVersion"
			value="<%=interfaceVersion%>" />
		<!--交易数据-->
		<input type="hidden" name="tranData" value="<%=tranData%>" />
		<!--订单签名数据-->
		<input type="hidden" name="merSignMsg" value="<%=merSignMsg%>" />
		<!--商城证书公钥 -->
		<input type="hidden" name="merCert" value="<%=merCert%>" />
		<!--<input type="submit" value="工行支付" />-->
	</form>
	<script language="javascript"> 		
 		document.forms[0].submit();	
	</script>
</body>