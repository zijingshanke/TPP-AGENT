<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
</head>
<%
String bankInfo = request.getAttribute("bankInfo").toString();
%>
<!-- JSP页面:bankInfo -->
<body style="text-align: center;">
	<div id="warp" align="left">
		<a href="../index.jsp" style="border: none; margin: 0; padding: 0;"><img
				src="../_img/logo.jpg" /> </a>
		<div class="main_top">
			<div class="main_bottom">
				<div class="main_mid">
					<div class="main_title">
						<div class="main_title_right">
							<p>
								<strong>钱门温馨提示信息</strong>
							</p>
						</div>
					</div>
					<div class="id_card_already">
						<img src="../_img/blue-loading.gif" />
						<div class="id_card_already_right">
							<br />
							<br />
							<br />
							<br />
							<br />
							<font size="4"><%=bankInfo%></font>
							<br />
							<br />
							<br />
							<br />							
							<font color="red" size="3">如有异常请联系钱门客服：0756-8801800</font>
							<br />
							<ul>
								<li>
								如果您的交易出现异常，请提供订单号、金额、支付方式(钱门余额、网上银行等)、银行交易明细截图等交易要素，便于客服人员帮您快速解决问题。
								</li>
							</ul>
							<div align="right">
								<input align="middle" type="button" class="btn1" value="确定"
									onclick="closeWindow();" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:import url="../_jsp/footer.jsp" />
	</div>
</body>
<script type="text/javascript">
function closeWindow(){
	window.close();
}
</script>

