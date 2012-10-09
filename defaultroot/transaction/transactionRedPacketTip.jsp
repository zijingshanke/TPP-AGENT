<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- transactionRedPacketTip.jsp -->
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
              <p><strong>领取红包</strong></p>
            </div> 
          </div> 
          <div class="Attention Attention6">
          	<div class="attentionTitle attentionTitle5">
            	<em><strong style="font-size:14px;">您的红包已经领取,详细信息请<html:link page="/login.jsp">登录钱门</html:link>查看!</strong></em>
                <p style="margin:0 0 0 8px; padding:0; color:red;"><c:out value="${GatewayForm.msg}"/></p>
            </div>
          </div>
        </div>
      </div>
    </div>
      <div id="footer">
        Copyright 2009-2012, www.qmpay.com .All Rights Reserved 增值电信业务经营许可证 粤B2-20090217
      </div>
  </div>
</body>
</html>
