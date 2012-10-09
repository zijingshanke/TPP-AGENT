<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	System.out.println(">>>>>>>>>>>>>chargeInfo.jsp>>>>>");
	String chargeType = request.getAttribute("chargeType").toString();
	String chargeStatus = request.getAttribute("chargeStatus")
			.toString();
	String chargeInfo = request.getAttribute("chargeInfo").toString();
	String returnUrl = request.getAttribute("returnUrl").toString();

	System.out.println("type=" + chargeType + "status=" + chargeStatus);
	System.out.println("info=" + chargeInfo);
	System.out.println("returnUrl=" + returnUrl);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<title>钱门支付！--网上支付！安全放心！</title>
		<script type="text/javascript" language="javascript">
		//倒计时
		<!--
		var secs =5; //倒计时的秒数 
		var URL; 
		function Load(url){ 
			URL =url; 
			for(var i=secs;i>=0;i--){ 
				window.setTimeout('doUpdate(' + i + ')', (secs-i) * 1000); 
			} 
		} 
		function doUpdate(num){ 
			document.getElementById('ShowDiv').innerHTML = '将在'+num+'秒后自动跳转' ; 
			if(num == 0) {
				window.location=URL;
			} 
		} 
		-->
		</script>
	</head>
	<body>
		<div>
			<c:import url="../_jsp/mainTop.jsp"></c:import>
		</div>
		<div id="chargeInfo"></div>
		<div id="ShowDiv" />
			<input type="button" value="确定" />
	</body>
	<script type="text/javascript" language="javascript">			
	//var returnUrl="../agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6"
	var returnUrl="<c:out value='${returnUrl}'/>";
	var returnUrl="http://www.qmpay.com.cn/fdpay-client/"+returnUrl;
		alert(returnUrl)
		window.location.href=returnUrl;	
		
		//Load(returnUrl); //要跳转到的页面 		
	</script>
</html>