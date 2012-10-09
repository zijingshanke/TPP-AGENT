<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!-- selfHelpChangePayPasswordQuestion.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	

  </head>
  
  <body>
  <html:form action="/agent/agent.do?thisAction=validateQuestions">
  <input type="hidden" name="loginName" value="<c:out value="${agent.loginName}"></c:out>" />
		<fieldset >
		<legend>请回答您的安全保护问题</legend>
				<p>
			<label>您的钱门账户：</label>
			<c:out value="${agent.loginName}"></c:out>
		</p>
		<p>
			<label>密码类型：</label>
			登录密码
		</p>
			<c:forEach var="questions" items="${listquestionanswer}" varStatus="statues">
				<p>
					<label>安全保护问题<c:out value="${statues.count}"></c:out>：</label>
					<c:out value="${questions.question}"></c:out>
				</p>
				<p>
					<label>答案1：</label>
				<html:text property="answers" value=""></html:text> 			
				</p>
				</c:forEach>
			</fieldset>
		<div>
		<c:if test="${error eq 'error'}">
			<c:forEach var="error" items="${errorlist}">
			<c:out value="${error}"></c:out><br>
			</c:forEach>
		</c:if>
		</div>
	<div class="Submit">
		<button class="AlipayButton WordTwo" type="submit" name="button_form_submit">确定</button>
	</div>
</html:form>
  </body>
</html>
