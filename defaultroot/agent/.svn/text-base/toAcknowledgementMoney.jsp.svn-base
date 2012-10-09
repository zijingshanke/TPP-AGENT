<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- toAcknowledgementMoney.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />

  </head>
  
  <body style="text-align:center;">
    <div id="warp" align="left">
   <c:import url="/_jsp/certifyTop.jsp"/>
    <div style="padding:8px;">当前您的实名认证申请状态：<strong>进行中</strong> 钱门已向您的 <strong><c:out value="${account.bankName}"/></strong> 账户（<c:out value="${account.haskCardNo}"/>）汇入一笔确认资金</div>
    
    <div class="main_top" style="margin-top:0;">
      <div class="main_bottom">
        <div class="main_mid">
        
          <h2>第二步：确认汇款金额</h2>
          <p>输入银行账户内的来自钱门打款的金额。</p>
          <p style="margin:20px 0 5px 0;"><a href="agent.do?thisAction=toCheckMoney" class="a_btn2" style="margin-left:0;">输入汇款金额</a></p>
        </div>
      </div>
    </div>
   
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
