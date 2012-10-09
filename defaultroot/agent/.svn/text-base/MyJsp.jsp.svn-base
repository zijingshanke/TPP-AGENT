<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="_js/topDiv.js"></script>

<script type="text/javascript">
	function initWindow(){
		var validCertStatus="<c:out value="${URI.agent.validCertStatus}"></c:out>";
		if(validCertStatus==0){
			msg();
		}
	}

</script>
  </head>
  
  <body onload="initWindow();">
    This is my JSP page. <br>
    证书用户:<c:out value="${URI.agent.email}"></c:out><br>
    验证证书状态:<c:out value="${URI.agent.validCertStatus}"></c:out>
   用户证书状态 <c:out value="${URI.agent.certStatus}"/>;
   <c:if test="${URI.agent.validCertStatus==0}">
   余额付款
   </c:if>
   <c:if test="${URI.agent.validCertStatus==1}">
  	 <a href="#">余额付款</a>
   </c:if>
  
    
  </body>
</html>
