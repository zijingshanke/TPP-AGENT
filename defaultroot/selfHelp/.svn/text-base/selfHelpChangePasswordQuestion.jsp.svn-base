<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- selfHelpChangePasswordQuestion.jsp -->
<html>
  <head>
   <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  </head>
  <body>
  <div id="warp">
        <c:if test="${URI==null}">
   <c:import url="../selfHelp/head.jsp"></c:import>
   </c:if>
   <c:if test="${URI!=null}">
   <c:import url="../_jsp/mainTop.jsp"></c:import>
   </c:if>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>用安全保护问题找回</strong></p>
            </div>
          </div>
          <div class="pay_way">
            <img src="../_img/pay_pic.gif" />
          </div>
         
          <div class="Wronning Wronning1">
                <div class="wronningTitle">
             <c:if test="${error eq '' or error eq null}">
             <em class="tipsPayway">请回答您的安全保护问题</em>
             </c:if>                     
            <c:if test="${error eq 'error'}">
			<c:forEach var="error" items="${errorlist}">
			<em class="tipsPayway" style="color: red;"><c:out value="${error}"></c:out></em>
			</c:forEach>
		</c:if>
                </div>
                	


          </div>
         <html:form action="/agent/agent.do?thisAction=validateQuestions">
  <input type="hidden" name="loginName" value="<c:out value="${agent.loginName}"></c:out>" />
    <input type="hidden" name="type" value="<c:out value="${type}"></c:out>" />
          
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td><span class="font_style2"><c:out value="${agent.loginName}"></c:out></span></td>
            </tr>
            <tr>
              <td class="right_td">密码类型：</td>
              <td><c:if test="${type eq 'password'}">登录密码</c:if>
			<c:if test="${type eq 'paypassword'}">支付密码</c:if></td>
            </tr>
            <c:forEach var="questions" items="${listquestionanswer}" varStatus="statues">
		 <tr>
              <td class="right_td">安全保护问题<c:out value="${statues.count}"></c:out>：</td>
              <td><c:out value="${questions.question}"></c:out></td>
            </tr>
			<tr>
              <td class="right_td">您的答案<c:out value="${statues.count}"></c:out>：</td>
              <td><html:text property="answers" value=""></html:text> </td>
            </tr>
				</c:forEach>

            <tr>
              <td>&nbsp;</td>
              <td valign="top" class="Request">日期输入格式为19810101</td>
            </tr>
            <tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="submit" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" /></span></td>
            </tr>
          </table>
          </html:form>
          <div class="goBack">
          	<img src="../_img/goBack.gif" /><html:link page="/agent/agent.do?thisAction=jumpTermFashion&type=${type}&loginName=${agent.loginName}">返回选择其他方式</html:link>
          </div>
        </div>
      </div>
    </div>
	<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
  
</html>
