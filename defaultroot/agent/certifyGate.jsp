<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- certifyGate.jsp -->
<html>
	<head>
	<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	</head>
	<body style="text-align: center;">
		<div id="warp" align="left">
			<c:import url="/_jsp/certifyTop.jsp" />
			<div class="main_top" style="margin-top: 30px">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="pad_certification">
							<img src="../_img/shenfen.gif" align="absmiddle" alt=""
								style="float: left;" />
							<div class="application_certification">
								<p>
									<span class="font_style4">钱门实名认证</span>
								</p>
								<p>
									这是一项身份识别服务，通过认证后就拥有了一张钱门“互联网身份证”
								</p>
								<div class="application_function">
									<p class="font_style5">
										认证会员独享功能：
									</p>
									<p>
										1.使用“我要收款”功能，使用“提现”功能
									</p>
									<p>
										2.安装钱门数字证书，让您的账户更安全
									</p>
								</div>
								<p>
									<!-- certifyAgreement.jsp  -->
									<a href="<%=request.getContextPath()%>/agent/certifyAgreement.jsp" class="a_btn2">立即申请</a>
								
								</p>
							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<c:import url="../_jsp/footer.jsp"></c:import>
		</div>
	</body>
</html>