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
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,3,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right" align="left">
              <p><strong>温馨提示：</strong></p>
            </div>
          </div>
          <div class="success">
            <img src="../_img/OK_img.gif" class="ok_img" />
            <div class="success_right" align="center">
             		您的线下充值请求系统已经接受！请及时查看您的钱门账户！！<br/><br/>
             <br/><br/>
   	  	<html:link page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">返回我的钱门</html:link>
   	 
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