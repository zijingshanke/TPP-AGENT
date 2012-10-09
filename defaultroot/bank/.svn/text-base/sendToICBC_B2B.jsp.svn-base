<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@page import="com.fordays.fdpay.bank.icbc.IcbcB2BCmdToBank"%>

<!-- JSP页面:/bank/sendToICBC_B2B -->
<%
			IcbcB2BCmdToBank bank = (IcbcB2BCmdToBank) request
			.getAttribute("IcbcB2BCmdToBank");
	String APIName = bank.getAPIName().trim();
	String APIVersion = bank.getAPIVersion().trim();
	String Shop_code = bank.getShop_code().trim();
	String MerchantURL = bank.getMerchantURL().trim();
	String ContractNo = bank.getContractNo().trim();
	String ContractAmt = bank.getContractAmt().trim();
	String Account_cur = bank.getAccount_cur().trim();
	String JoinFlag = bank.getJoinFlag().trim();
	String Mer_Icbc20_signstr = bank.getMer_Icbc20_signstr().trim();
	String Cert = bank.getCert().trim();
	String SendType = bank.getSendType().trim();
	String TranTime = bank.getTranTime().trim();
	String Shop_acc_num = bank.getShop_acc_num().trim();
	String PayeeAcct = bank.getPayeeAcct().trim();
	String GoodsCode = bank.getGoodsCode().trim();
	String GoodsName = bank.getGoodsName().trim();
	String Amount = bank.getAmount().trim();
	String TransFee = bank.getTransFee().trim();
	String ShopRemark = bank.getShopRemark().trim();
	String ShopRem = bank.getShopRem().trim();

	String interfacePath = bank.getInterfacePath().trim();
%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
</head>
<body>
	<c:import url="./waitConnBank.jsp" />
	<form id="SendOrder" action="<%=interfacePath%>" method="post">
		<input type=hidden name="APIName" value="<%=APIName%>" />
		<input type=hidden name="APIVersion" value="<%=APIVersion%>" />
		<input type=hidden name="Shop_code" value="<%=Shop_code%>" />
		<input type=hidden name="MerchantURL" value="<%=MerchantURL%>" />
		<input type=hidden name="ContractNo" value="<%=ContractNo%>" />
		<input type=hidden name="ContractAmt" value="<%=ContractAmt%>" />
		<input type=hidden name="Account_cur" value="<%=Account_cur%>" />
		<input type=hidden name="JoinFlag" value="<%=JoinFlag%>" />
		<input type=hidden name="Mer_Icbc20_signstr"
			value="<%=Mer_Icbc20_signstr%>" />
		<input type=hidden name="Cert" value="<%=Cert%>" />
		<input type=hidden name="SendType" value="<%=SendType%>" />
		<input type=hidden name="TranTime" value="<%=TranTime%>" />
		<input type=hidden name="Shop_acc_num" value="<%=Shop_acc_num%>" />
		<input type=hidden name="PayeeAcct" value="<%=PayeeAcct%>" />
		<input type=hidden name="GoodsCode" value="<%=GoodsCode%>" />
		<input type=hidden name="GoodsName" value="<%=GoodsName%>" />
		<input type=hidden name="Amount" value="<%=Amount%>" />
		<input type=hidden name="TransFee" value="<%=TransFee%>" />
		<input type=hidden name="ShopRemark" value="<%=ShopRemark%>" />
		<input type=hidden name="ShopRem" value="<%=ShopRem%>" />
		<!--<input type="submit" value="工行企业网银" />-->
	</form>
	<script language="javascript"> 		
 		document.forms[0].submit();	
	</script>
</body>