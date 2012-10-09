<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!-- uploadImg.jsp -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>

	<html:form action="/agent/agent.do?thisAction=uploadImg" enctype="multipart/form-data" method="post" >
	登录人Id:<c:out value="${param.agentId}"></c:out>
	<html:hidden property="id" value="${param.agentId}"/>
	<html:file property="imgFile">修改头像</html:file>
	<html:submit value="确定"></html:submit>
	<html:cancel value="取消"></html:cancel>
	</html:form>
</body>
</html>
