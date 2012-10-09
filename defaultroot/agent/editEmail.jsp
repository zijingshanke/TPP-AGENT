<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- agent/editEmail.jsp -->

 <head> 
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
  </head>
<script type="text/javascript">
function f_check_email(obj){  
	var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 
	if(myReg.test( obj.value )) return true; 
	else 	return false; } 

function check(){

	var email = document.forms[0].tempEmail;
	if(f_check_email(email)){
	document.forms[0].submit();
	}else{
		document.getElementById("msgemail").innerHTML="*请输入合法的Email";
	}
}

</script>
<body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>修改账户登录名</strong><a href="agent.do?thisAction=viewMyAgent" class="a_style1">返回我的账户</a></p>
            </div>
          </div>
          <html:form action="/agent/agent.do?thisAction=updateEmail">
	<html:hidden property="id" value="${agent.id}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table" style="text-align: left">
            <tr>
              <td class="right_td">账户名：</td>
              <td><span class="font_style2"><c:out value="${agent.loginName}"></c:out></span></td>
            </tr>
            <tr>
              <td class="right_td">支付密码：</td>
              <td><html:password property="payPassword" value="" styleClass="text_style" style="width:200px"/><font color="red"><c:out value="${msgPayPassword}"></c:out></font></td>

            </tr>
            	<c:if test="${agent.tempEmail!=agent.loginName}">
			<tr><td>等待激活的帐号:<c:out value="${agent.tempEmail}"></c:out></td><td><a href="../agent/agent.do?thisAction=sendactivationEmail&email=<c:out value="${agent.tempEmail}"></c:out>">如果您还没有收到邮件或者邮件已经失效点击请重发!</a></td></tr>
</c:if>
            <tr>
              <td class="right_td">新账户名：</td>
              <td><html:text property="tempEmail" value="" styleClass="text_style" style="width:200px"/><div id="msgemail" style="color: red"></div><c:out value="${msgEmail}"></c:out></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td valign="top" class="Request">请输入Email地址</td>
            </tr>
            <tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="button" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" onclick="check();" /></span></td>
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
