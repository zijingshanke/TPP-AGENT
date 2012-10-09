<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- index.jsp -->
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
        <c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,0,2" />
    <div class="SecurityCenter">
        <div class="left_bg">
        </div>
        <div class="rightTop_bg">
        	<!--  p class="contactText">如信息不符，请<a href="#">联系客服</a></p -->
        	<p class="contactText">&nbsp;</p>
        	<table class="yourLogin_tab">
            	<tr>
                	<td colspan="2" class="yourLogin"><strong>您上次的登录时间</strong></td>
                </tr>
                <tr>
                	<td class="yourLogin">&nbsp;</td>
                </tr>
                <tr>
                	<td class="yourLogin">时间：</td>
                    <td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${viewAgentInfo.currentLoginDate}"/></td>
                </tr>
            </table>
        </div>
        <div class="rightBottom_bg">
        	<div class="leftQuestions">
            	<lable>如果您偶尔在网上购物，网上付款，账户上有余额，请使用以下的安全服务：</lable>
                <ul>
                	  <LI>第一步 <html:link page="/security/changePassword.jsp">设置安全易记的登录密码</html:link> 
                    <LI>第二步 <html:link page="/security/security.do?thisAction=editQuestionAsk">设置安全保护问题</html:link> 
                    <li>第三步 <a href="../agent/agent.do?thisAction=viewMobileCenter">绑定手机</a>
                </ul>
            </div>
            <div class="leftQuestions rightQuestions">
            	<lable>如果您经常购物，进行网上收款、付款，为了确保您交易的安全性，请使用以下的安全服务：</lable>
                <ul>
                	  <LI>第一步 <html:link page="/security/changePassword.jsp">设置安全易记的登录密码</html:link> 
                    <LI>第二步 <html:link page="/security/security.do?thisAction=editQuestionAsk">设置安全保护问题</html:link> 
                    <li>第三步 <a href="../agent/agent.do?thisAction=viewMobileCenter">绑定手机</a>
                    <LI>第四步 <c:if test="${viewAgentInfo.status==3}">
                  <a href="#">已认证</a>
                  </c:if>                  
                  <c:if test="${viewAgentInfo.status!=3}">
                 <html:link page="/agent/agent.do?thisAction=checkCertification&id=${URI.agent.id}" >申请认证</html:link>
                  </c:if>
                    <li>第五步 <html:link page="/security/security.do?thisAction=certificate">安装数字证书</html:link>&nbsp;或<html:link page="/security/security.do?thisAction=securityUseKey">申请支付盾</html:link></li>
                </ul>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    
    
    <c:import url="/_jsp/footer.jsp"/>
  </div>
</body>
</html>
