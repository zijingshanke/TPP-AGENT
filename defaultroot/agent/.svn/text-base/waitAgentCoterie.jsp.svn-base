<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<html>
  <head>
  <!--JSP页面: agent/waitAgentCoterie.jsp -->

 <title>钱门支付！--网上支付！安全放心！</title>
    <link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
  <script type="text/javascript">
  function sbm()
  {
  	var name="<c:out value='${agent.loginName}'/>";
  	document.forms[0].action+="&name="+name;
  	document.forms[0].submit();
  }
  </script>
  </head>
   
  <body style="text-align: center;">
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,4,6" />
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right" align="left">
              <p><strong>温馨提示：</strong></p>
            </div>
          </div>
         
           <div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle" >
	        <html:form action="/agent/agent.do?thisAction=listConterie&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">
            <div align="center">
             	<p><strong><font size="4px;">您已经签约成功！！</font></strong></p>
             	<p> 您现在可以使用钱门为您钱门账户提供的增值服务 </p>
   	  			<input type="button" class="btn1" value="立即体验" onclick="sbm();"/>
   	  			<p>&nbsp;</p>
   	 		</div>
   	 		</html:form>
   	 		</div>
           </div>
            <div class="clear">
            </div>
          </div>
        </div>
      </div>

    <c:import url="/_jsp/footer.jsp" />
    </div>
</body>
</html>
