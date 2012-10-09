<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld " prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: transaction/transactionPaymentConfirmI.jsp -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>

		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css2/my_account.css" type="text/css" />

		<script src="../_js/common.js"></script>
	
	</head>
	<body>
	
			<div id="warp">
				 <div id="head">
      <span style="float:right; padding-top:40px;">
      
      <div class="quickLink">客服热线：<font color="red" size="4px;">0756-8801800</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，请 <a href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister">注册</a> 或 <a href="<%=request.getContextPath()%>/login.jsp">登录</a></div>
      </span>
      <a href="<%=request.getContextPath()%>/index.jsp" style="border:none; margin:0; padding:0;"><img src="<%=request.getContextPath()%>/_img/logo.jpg" /></a>
    </div>
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid" style="padding: 5px 8px;">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>手机充值成功</strong>
									</p>
								</div>
							</div>

							<div class="paymentSuccessful" style="height: auto;">
								<div class="paymentSuccessfulTitle" style="height: auto;">
		<em><strong>你已经成功向卖家付款  <fmt:formatNumber pattern="0.00" value="${amount}"/>  元! </strong></em><br>
		<em><strong>如果您在10分钟内还没有收到充值成功的信息请联系客服</strong></em><br>
		<em>办理退款!客服热线：<font color="red">0756-8801800</font></em>
								
								</div>
							</div>
						</div>
					</div>
				</div>
			<c:import url="/_jsp/footer.jsp" />
			</div>
			
			</div>
	
			
	</body>
</html>

