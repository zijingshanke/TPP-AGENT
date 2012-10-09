<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: security/editQuestionAsk.jsp -->	

  <head>
    <title>钱门支付！--网上支付！安全放心！</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
 	
 	<style>
.id_card {
	border:1px solid #FFD327;
	background:#FFFFEE;
	padding:15px 0 15px 15px;
	margin-top:10px;
}
 	</style>
 	</head> 
  <body>
  <div id="warp">
  <c:import url="../_jsp/topMenu.jsp?a=1,4&b=0,5,0,2" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>设置保护问题</strong><a href="../agent/agent.do?thisAction=viewMyAgent" class="a_style1">返回我的账户</a></p>
            </div>
          </div>
          <c:if test="${size eq '1' or size eq '2' }">
	  <div class="id_card">
            <img src="../_img/alreday.gif" />
            <div class="id_card_already_right">
            请认真回答提示问题:
            <c:forEach var="error" items="${errorlist}">
			<ul>			
				<li><font color="red"><c:out value="${error}"></c:out></font></li>
			</ul>
			</c:forEach>
   				 </div>
            <div class="clear"></div>
          </div>
          </c:if>
<html:form action="/security/security.do?thisAction=doQuestionAsk">
		<html:hidden property="id" value="${URI.agent.id}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">

              <c:forEach var="questionandanswer" items="${listquestionanswer}">
 <tr><td>您的问题:</td><td><c:out value="${questionandanswer.question}"></c:out></td></tr>
  <tr><td>答案:</td><td><input type="text" class="text_style" name="answers"></input></td></tr>
 </c:forEach><tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="submit" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" /></span></td>
            </tr>
          </table>
          </html:form>
        </div>
      </div>
    </div>
 <c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
