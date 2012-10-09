<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- jsp:tipsSetting.jsp -->
<element>
	<html xmlns="http://www.w3.org/1999/xhtml">
    <title>钱门支付!--网上支付！安全放心！-温馨提示 </title>
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
         
           <div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle" >
            <div align="center">
             	<p><strong><font size="4px;">您还没有通过实名认证！！</font></strong></p>
             	<c:if test="${URI.agent.status==1}">
 			<html:link page="/agent/agent.do?thisAction=checkCertification&id=${URI.agent.id}">申请实名认证</html:link><br/>
 			</c:if>
 			<c:if test="${URI.agent.status!=1}">
 			<html:link page="/agent/agent.do?thisAction=checkCertification&id=${URI.agent.id}">您已经申请了实名认证,请点击继续完成认证!</html:link><br/>
 			</c:if>
   	  		<html:link page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">返回我的钱门</html:link>
   	 		</div>
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