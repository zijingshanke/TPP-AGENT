<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- infoSendEmail.jsp -->
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
              <p><strong>修改密码</strong></p>
            </div>
          </div>
         
          <div class="sendToEmail">
          	<div class="sendToEmailTitle">
            	<em>钱门已向您的邮箱 <c:out value="${agent.email}"></c:out> 发送了一封邮件。</em>
                <p>若邮箱始终收不到邮件，可查看帮助信息。</p>
            </div>
            <ul class="sendToEmailList">
            	<li>为确保您的操作顺利完成，请尽量使用IE浏览器收取邮件。 </li>
                <li>如果您多次点击“确定”，请以最后一次“确认信”为准。 </li>
            </ul>
          </div>
          
        </div>
      </div>
    </div><c:import url="../_jsp/footer.jsp"></c:import>
    </div>

</div>
</body>
</html>
