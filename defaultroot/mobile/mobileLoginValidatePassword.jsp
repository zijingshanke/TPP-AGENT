<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: mobile/mobileLoginValidatePassword.jsp -->

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
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />	
	 <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
            <c:if test="${URI.agent.mobileLoginStatus==0}">
              <p><strong>设为钱门账户登录名</strong></p>
              </c:if>
              <c:if test="${URI.agent.mobileLoginStatus==1}">
              <p><strong>取消手机号码为钱门账户登录名</strong></p>
              </c:if>
            </div>
          </div>
		 
           <c:if test="${URI.agent.mobileLoginStatus==0}">
              <div style="height:40px; line-height:40px; font-size:12px; color:#666; margin-left:12px;">
          	您确定设置手机号码:<c:out value="${URI.agent.mobilePhone}"></c:out>&nbsp;为钱门账户登录名。
          </div>
              </c:if>
              <c:if test="${URI.agent.mobileLoginStatus==1}">
              <div style="height:40px; line-height:40px; font-size:12px; color:#666; margin-left:12px;">
          	您确定取消手机号码:<c:out value="${URI.agent.mobilePhone}"></c:out>&nbsp;为钱门账户登录名。
          </div>
              </c:if>
          <html:form action="/agent/agent.do?thisAction=checkPayPasswordMobileLogin">
          <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <tr>
                  <td class="right_td">钱门账户的支付密码：</td>
                  <td><html:password property="payPassword" value="" styleClass="text_style"></html:password></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><a href="#" style="margin:0">找回支付密码</a></td>
                </tr>
                <tr>
               	  <td>&nbsp;</td>
                  <td><span class="simplyBtn_1" style="margin:0"><input type="submit" class="buttom_middle" value="确定"/></span><a href="#" style="margin-left:12px;">返回</a></td>
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
