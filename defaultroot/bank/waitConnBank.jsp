<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- JSP页面:waitConnBank -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
</head>
<body>
	<div id="warp">
		<div id="head">
			<a href="../index.jsp" style="border: none; margin: 0; padding: 0;"><img
					src="../_img/logo.jpg" /> </a>
		</div>
		<div class="nav">
			<div class="nav_a">
				<div class="nav_b">
				</div>
			</div>
		</div>
		<div class="main_top">
			<div class="main_bottom">
				<div class="main_mid" style="height: 400px;">
					<div class="loading">
						<img src="../_img/small-logo.gif" alt="" />
						<p>
							<img src="../_img/loading.gif" align="" />
						</p>
						<p style="color: #005C9C;">
							正在连接网上银行，请稍候...
						</p>
					</div>
				</div>
			</div>
		</div>
		<c:import url="../_jsp/footer.jsp" />
	</div>
</body>