<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- selfHelpSetPassword.jsp -->
<html>
  <head>
  <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript">
function ck(){
	var repassword= document.getElementById("repassword").value;
	var repassword2=document.getElementById("repassword2").value;
  
	if(repassword=="" ){
  	 		document.getElementById("msgrepassword").innerText="密码不能为空!";
  	 		return false;
  	 	}
	if(repassword.length<6 || repassword.length>20){
  	 		document.getElementById("msgrepassword").innerText="密码长度要在6-20之间!";
  	 		return false;
  	 	}
  	 	document.forms[0].submit();
}
</script>
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
          <em class="tipsPayway">请重新设置您的登录密码</em>
<html:form action="/agent/agent.do?thisAction=setPassword">
   <input type="hidden" name="loginName" value="<c:out value="${loginName}" ></c:out>"/>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的账户名：</td>
              <td><span class="font_style2"><c:out value="${loginName}" ></c:out></span></td>
            </tr>
			<tr>
              <td class="right_td">输入新的登录密码：</td>
              <td><html:password property="repassword" styleId="repassword"></html:password><font color="red" id="msgrepassword"></font></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td valign="top" class="Request">由6-20个英文字母、数字或符号组成。建议适用大小写字母与数字混合设置密码。</td>
            </tr>
            <tr>
              <td class="right_td">再次输入登录密码：</td>
              <td><html:password property="repassword2" styleId="repassword2"></html:password></td>
            </tr>
            <tr><td colspan="2">   <div><c:if test="${error eq 'error'}">
				<font color="red">密码2次输入不一致!</font>   	
			    </c:if><c:if test="${error1 eq 'error1'}">
				<font color="red">登录密码不能和支付密码相同!</font></c:if>   
				</div></td></tr>
            <tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="button" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" onclick="ck()"/></span></td>
            </tr>
          </table>
</html:form>
          <div class="goBack"> 
          	<img src="../_img/goBack.gif" /><html:link page="/agent/agent.do?thisAction=jumpTermFashion&type=${type}&loginName=${loginName}">返回选择其他方式</html:link>
          </div>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
