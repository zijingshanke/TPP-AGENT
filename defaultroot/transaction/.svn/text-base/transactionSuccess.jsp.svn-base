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

<!--JSP页面: transaction/transactionSuccess.jsp -->

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
		<c:if test="${trans.paytype==3}">
	<c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,0,3" />
	</c:if>
	<c:if test="${trans.paytype==4}">
	<c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,1,3" />
	</c:if>
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
              <c:if test="${trans.paytype==3}">
              "担保交易"
              </c:if>
              <c:if test="${trans.paytype==4}">
              "即时到账交易"
              </c:if>
                收款，已经创建成功</strong></p>
              <p style="border-bottom:1px solid #D2D2D2; font-size:12px;">请随时关注<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3" style="margin-left:0;">交易管理</html:link>中有关本交易的进展</p>
              <ul class="success_ul">
                <li>付款方 <strong><c:out value="${trans.buyerAccount}"/></strong> 将会收到本交易通知，他会根据提示完成本次交易</li>
                <li>如果15天内对方未付款至您的钱门账户，本次交易将自动关闭</li>
              </ul>
            </div>
            <div class="clear"></div>`
          </div>
          
        </div>
      </div>
    </div>
 <c:import url="/_jsp/footer.jsp?a=1,0&b=0,1,0,3"/>
</div>
  </body>
</html>

