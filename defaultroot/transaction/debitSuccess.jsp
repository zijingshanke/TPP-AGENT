<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/debitSuccess.jsp -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		
	</head>

	<body>
		<div id="warp">
		<c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,3,3" />
			<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>成功提交</strong></p>
            </div>
          </div>
          <div class="success">
            <img src="../_img/OK_img.gif" class="ok_img" />
            <div class="success_right">
              <p><strong style="font-size:14px;">
                申请预支已经创建成功</strong></p>
              <p style="border-bottom:1px solid #D2D2D2; font-size:12px;"><html:link page="/transaction/transaction.do?thisAction=debit" style="margin-left:0;">返回</html:link></p>
            </div>
            <div class="clear"></div>`
          </div>
          
        </div>
      </div>
    </div>
 <c:import url="/_jsp/footer.jsp"/>
</div>
  </body>
</html>

