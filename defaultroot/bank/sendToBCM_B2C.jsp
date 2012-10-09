<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.fordays.fdpay.bank.bcm.BcmB2CcmdToBank"%>
<%@page import="com.bocom.netpay.b2cAPI.BOCOMSetting"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- JSP页面:/bank/sendToBCM_B2C -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
	</head>
	<%
				BcmB2CcmdToBank bank = (BcmB2CcmdToBank) request
				.getAttribute("BcmB2CcmdToBank");
		String interfacePath = bank.getInterfacePath();
		String interfaceVersion = bank.getInterfaceVersion();
		String merID = bank.getMerID();
		String orderid = bank.getOrderid();
		String orderDate = bank.getOrderDate();
		String orderTime = bank.getOrderTime();
		String tranType = bank.getTranType();
		String amount = bank.getAmount();
		String curType = bank.getCurType();
		String orderContent = bank.getOrderContent();
		String orderMono = bank.getOrderMono();
		String phdFlag = bank.getPhdFlag();
		String notifyType = bank.getNotifyType();
		String merURL = bank.getMerURL();
		String goodsURL = bank.getGoodsURL();
		String jumpSeconds = bank.getJumpSeconds();
		String payBatchNo = bank.getPayBatchNo();
		String proxyMerName = bank.getProxyMerName();
		String proxyMerType = bank.getProxyMerType();
		String proxyMerCredentials = bank.getProxyMerCredentials();
		String netType = bank.getNetType();
		String merSignMsg = bank.getMerSignMsg();
	%>
	<body>
		<c:import url="./waitConnBank.jsp" />
		<form name="sendOrder" method="post" action="<%=interfacePath%>">
			<input type="hidden" name="interfaceVersion"
				value="<%=interfaceVersion%>">
			<input type="hidden" name="merID" value="<%=merID%>">
			<input type="hidden" name="orderid" value="<%=orderid%>">
			<input type="hidden" name="orderDate" value="<%=orderDate%>">
			<input type="hidden" name="orderTime" value="<%=orderTime%>">
			<input type="hidden" name="tranType" value="<%=tranType%>">
			<input type="hidden" name="amount" value="<%=amount%>">
			<input type="hidden" name="curType" value="<%=curType%>">
			<input type="hidden" name="orderContent" value="<%=orderContent%>">
			<input type="hidden" name="orderMono" value="<%=orderMono%>">
			<input type="hidden" name="phdFlag" value="<%=phdFlag%>">
			<input type="hidden" name="notifyType" value="<%=notifyType%>">
			<input type="hidden" name="merURL" value="<%=merURL%>">
			<input type="hidden" name="goodsURL" value="<%=goodsURL%>">
			<input type="hidden" name="jumpSeconds" value="<%=jumpSeconds%>">
			<input type="hidden" name="payBatchNo" value="<%=payBatchNo%>">
			<input type="hidden" name="proxyMerName" value="<%=proxyMerName%>">
			<input type="hidden" name="proxyMerType" value="<%=proxyMerType%>">
			<input type="hidden" name="proxyMerCredentials"
				value="<%=proxyMerCredentials%>">
			<input type="hidden" name="netType" value="<%=netType%>">
			<input type="hidden" name="merSignMsg" value="<%=merSignMsg%>">
			<!-- <input type="submit" value="交通银行支付">
		 -->
		</form>
	</body>
	<script type="text/javascript">
		document.forms[0].submit();
	</script>
</html>

















