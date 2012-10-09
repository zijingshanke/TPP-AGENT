<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: security/changePayPassword.jsp -->
<html>
  <head>
   <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
	<script src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
	<script type="text/javascript" src="../_js/validateform.js"></script>
	
 	<script>
 		function toSubmit()
{
	var formid = document.forms[0];
	
	if(validateForm(formid))
	{
		formid.submit();
	}
}
 	</script>
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
		<div class="id_card">
			<c:if test="${error eq 'yes'}">
              <img src="../_img/worning.gif" />
             <div class="id_card_already_right">
           <c:forEach var="error" items="${errorlist}">
            <em class="tipsPayway" style="color: red;"><font size="4"><c:out value="${error}"></c:out></font></em>
           </c:forEach>
           </c:if>
          
         <c:if test="${error eq null or error eq ''}">
           <img src="../_img/alreday.gif" />
            <div class="id_card_already_right">
             <em class="tipsPayway" style="color: red;"><font size="6">请牢记您设置的支付密码</font></em>
           </c:if>
            
           </div>
           <div>
            <div class="clear"></div>
          </div>
         
          </div>
   <div id="container" class="register">
	<html:form action="/security/security.do?thisAction=changePayPassword">
		<html:hidden property="id" value="${URI.agent.id}"></html:hidden>
	  <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="personInfo">
    <tr>
      <td class="tableLT"></td>
      <td class="tableTT"></td>
      <td class="tableRT"></td>
    </tr>
 
    <tr>
      <td class="tableLL"></td>
      <td class="container">
      	<div class="title" align="left"><span>修改支付密码</span></div>
	  <div>
  <table align="left">
  <tr><td style="width: 20%; text-align: right;">当前支付密码:</td><td style="width: 7%;"></td><td><input type="password" name="payPassword" id="payPassword" class="text_style"  rule="length:6:20"/></td><td style="width: 470px;text-align: left"><div id="passwordTip"></div></td></tr>
   <tr ><td style="width: 20%; text-align: right;">新密码:</td><td style="width: 7%;"></td><td><input type="password" name="repassword" id="repassword" class="text_style" onclick="setText('repasswordTip','由6-20个英文字母、数字或符号组成。建议使用字母与数字混合设置密码。')" onblur="lengthBetween('repassword','repasswordTip',6,20,'您设置的支付密码有误，支付密码应该由6-20个英文字母、数字或符号组成。')" rule="length:6:20"/></td><td style="width: 470px;text-align: left"><div id="repasswordTip"></div>
   </td></tr>
     <tr ><td style="width: 20%; text-align: right;">确认新密码:</td><td style="width: 7%;"></td><td><input type="password" name="repassword2" id="repassword2" class="text_style" onclick="setText('repasswordTip2','由6-20个英文字母、数字或符号组成。建议使用与数字混合设置密码。')" onblur="lengthBetween('repassword2','repasswordTip2',6,20,'您设置的支付密码有误，支付密码应该由6-20个英文字母、数字或符号组成。')" rule="length:6:20"/></td><td style="width: 470px;text-align: left">
     <div id="repasswordTip2"></div>
     </td></tr>
      <tr align="center"><td colspan="4" ><span class="simplyBtn_1"><input type="button"  style="width: 120px" class="buttom_middle" value="确定" onclick="toSubmit()"/></span> </td></tr>
  </table>
  </div>
      </td>
      <td class="tableRR"></td>
    </tr>
    
    <tr>
      <td class="tabelLB"></td>
      <td class="tableBB"></td>
      <td class="tabelRB"></td>
    </tr>
   
  </table>
	</html:form>
	</div>
<c:import url="../_jsp/footer.jsp"></c:import>
	
	</div>
	
  </body>
 
</html>
