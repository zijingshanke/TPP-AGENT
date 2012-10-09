<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- inforegisterSuccess.jsp -->
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />

  </head>
  
  <body style="text-align: center">

  <div id="warp" align="left">
  <c:if test="${URI!=null}">
  <c:import url="../_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
  </c:if>
  <c:if test="${URI==null}">
<c:import url="../selfHelp/head.jsp"></c:import>
  </c:if>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right" align="left">
              <p><strong>恭喜您,您已经填写完注册信息!!</strong></p>
            </div>
          </div>
          <div class="success">
            <img src="../_img/OK_img.gif" class="ok_img" />
            <div class="success_right" align="left">
             钱门已经发送邮件到您的邮箱<font><c:out value="${email}"></c:out></font><br>
             您需要激活邮箱中连接！
            </div>
            <div class="clear"></div>
          </div>
          
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
</div>
</body>
</html>
