<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- security/setPayPassword.jsp -->
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
              <p><strong>修改支付密码</strong></p>
            </div>
          </div>
          <em class="tipsPayway">请重新设置您的支付密码</em>
<html:form action="/cooperate/gateway.do?">
   <html:hidden property="email" value="${GatewayForm.email}"></html:hidden>
      <html:hidden property="service" value="set_pay_password"></html:hidden>
       <html:hidden property="url" value="${GatewayForm.url}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的账户名：</td>
              <td style="text-align: left"><span class="font_style2"><c:out value="${GatewayForm.email}" ></c:out></span></td>
            </tr>
			<tr>
              <td class="right_td">输入新的支付密码：</td>
              <td style="text-align: left"><html:password property="password" styleClass="inputText"></html:password></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td valign="top" style="text-align: left">由6-20个英文字母、数字或符号组成。建议适用大小写字母与数字混合设置支付密码。</td>
            </tr>
            <tr>
              <td class="right_td">再次输入支付密码：</td>
              <td style="text-align: left"><html:password property="repassword" styleClass="inputText"></html:password></td>
            </tr>
            <tr><td>&nbsp;</td>
            <td style="text-align: left"><div><c:if test="${error eq 'error'}">
				<font color="red">密码为空，或者2次输入不一致!</font></c:if>
				<c:if test="${error1 eq 'error1'}">
				<font color="red">支付密码不能和登录密码相同!</font>   	
			    </c:if></div></td></tr>
            <tr>
            <td>&nbsp;</td>
              <td style="text-align: left"><span class="simplyBtn_1 simplyBtn_11"><input type="submit" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" /></span></td>
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
