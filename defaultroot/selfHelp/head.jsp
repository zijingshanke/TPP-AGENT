<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<html>
<head>
<link href="<%=request.getContextPath()%>/_css/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div id="head"><span style="float: right;padding-top:40px;"> 您好，请 <html:link
	page="/agent/agent.do?thisAction=toRegister">立即注册</html:link></a>或<a
	href="../index.jsp">登录</a></span> <a href="../index.jsp"
	style="border: none; margin: 0; padding: 0;"><html:img
	page="/_img/logo.jpg" /></a></div>
</body>
</html>