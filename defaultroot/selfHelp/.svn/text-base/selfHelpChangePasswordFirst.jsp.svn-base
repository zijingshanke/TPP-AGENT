<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- selfHelpChangePasswordFirst.jsp -->
<html>
  <head>
 <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />

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
            <c:if test="${type eq 'password'}"> <p><strong>找回登录密码</strong></p></c:if>
             <c:if test="${type eq 'paypassword'}"> <p><strong>找回支付密码</strong></p></c:if>
            </div>
          </div>
          <div class="retrieve_password">
            <img src="../_img/people_pi.gif" />
            <div class="retrieve_password_right">
            <c:if test="${type eq 'password'}"> <p class="right_top_p">您正在进行的操作：找回登录密码</p></c:if>
             <c:if test="${type eq 'paypassword'}"><p class="right_top_p">您正在进行的操作：找回支付密码</p></c:if>
              
              <p>请输入您的钱门账户名（钱门账户名为Email地址或手机号码），方便您有效的进行业务受理</p>
            </div>
            <div class="clear"></div>
          </div>
             <html:form action="/agent/agent.do?thisAction=forgetPasswordNext">
   <input type="hidden" name="type" value="<c:out value="${type}"></c:out>"/>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table" >
            <tr>
              <td align="right" style="width:100px;"><span class="font_style6">*</span> 钱门账户：</td>
              <td><c:if test="${URI!=null}"><c:out value="${URI.agent.loginName}"></c:out>
	<html:hidden property="loginName" value="${URI.agent.loginName}"></html:hidden>
</c:if>
<c:if test="${URI==null}">
<html:text property="loginName" value="" styleClass="text_style"/>
</c:if>
    &nbsp;<font color="red"><c:out value="${msg}"></c:out></font></td>        
    </tr><tr>
              <td>&nbsp;</td>
              <td><html:submit value="下一步" styleClass="btn1"/></td>
              <td></td>
            </tr>
          </table>
          </html:form>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
  </div>
</body>
</html>
