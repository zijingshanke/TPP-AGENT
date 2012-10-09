<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/applyCert.jsp -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
		<script language="javascript"
			src="<%=request.getContextPath()%>/_js/common.js"></script>
		<script language="javascript"
			src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
	</head>
	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
			<html:form action="/security/certificate.do">
				<html:hidden property="thisAction" value="confirmMessage" />
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>申请证书验证</strong>
									</p>
								</div>
							</div>
							<br></br>
							<div class="yellowBox_mes">
								<div>
									<em
										style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">为了保证账户安全，钱门需要您首先绑定手机（免费）</em>
								</div>
								<hr></hr>
								<h4 style="padding: 8px">
									&nbsp;&nbsp; 绑定手机后,您可以返回安全中心,继续申请数字证书。请
									<a href="javascript:bindMobilephone()">绑定手机</a>
								</h4>

							</div>
						</div>
					</div>
				</div>

			</html:form>
			<c:import url="/_jsp/footer.jsp" />

		</div>

	</body>
</html>
<script>
<!--
function bindMobilephone(){
	window.location.href="<%=request.getContextPath()%>/agent/agent.do?thisAction=viewMobileCenter";
}
//-->
</script>
