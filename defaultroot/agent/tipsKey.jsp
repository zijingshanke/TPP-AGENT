<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<element>
	<html xmlns="http://www.w3.org/1999/xhtml">
   <title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  </head>
  
  <body>

    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,0,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right" align="left">
              <p><strong>温馨提示：</strong></p>
            </div>
          </div>
          <div class="success">
          <c:if test="${massag==0}">
            <img src="../_img/worning.gif" class="ok_img" />
            </c:if>
          <c:if test="${massag==1}">
            <img src="../_img/OK_img.gif" class="ok_img" />
            </c:if>
            <div class="success_right" align="center">
            	<c:if test="${massag==1}">
             		您已经成功修改了您的密钥！！
             	</c:if>
             	<c:if test="${massag==0}">
             		修改密钥失败！！
             	</c:if>
             	<br/><br/>
             <br/><br/>
   	  	<html:link page="/agent/agent.do?thisAction=viewMyAgent">返回我的账户</html:link>
   	 
            </div>
            <div class="clear">
            
            </div>
          </div>
        </div>
      </div>
    </div>
    <c:import url="/_jsp/footer.jsp" />
  </body>
</html>