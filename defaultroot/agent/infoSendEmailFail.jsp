<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- infoSendEailFail.jsp -->
  <head>
<title>钱门支付！--网上支付！安全放心！</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
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
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right" align="left">
              <p><strong>发送失败!</strong></p>
            </div>
          </div>
          <div class="success">
            <img src="../_img/worning.gif" class="ok_img" />
            <div class="success_right" align="left">
           发送邮件的过程中遇到未知的错误！请稍候再试........
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
