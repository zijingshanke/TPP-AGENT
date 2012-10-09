<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- infoChangePayPasswordSuccess.jsp -->
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
              <p><strong>用安全保护问题找回</strong></p>
            </div>
          </div>
          
          <div class="sendToEmail modifySuccess">
          	<div class="sendToEmailTitle modifySuccessTitle">
            	<em>您已经成功修改了支付密码！</em>
            </div>
            <div><a href="../agent/agent.do?thisAction=viewMyAgent&showTabMenuIdList=1,6&showSubMenuIdList=0,7,5,6">返回我的钱门</a></div>
            </ul>
          </div>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
</div>
</body>
</html>
