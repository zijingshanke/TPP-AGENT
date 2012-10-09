<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!-- security/revorkCertSuccess.jsp -->
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
		<style>

</style>
	</head>

	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />

			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>注销数字证书&nbsp;<img src="<%=request.getContextPath()%>/_img/zhengshu.jpg" /> </strong>
								</p>
							</div>
						</div>
						<html:form action="/security/certificate.do">
							<html:hidden property="thisAction" value="revokeCertificate" />
							<div>
								<div class="yellowBox_mes">
									<strong style="font-size: 18px;">注销成功</strong>
									<br></br>
									<div>
										<em
											style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">卸载数字证书成功，感谢您的使用！</em>
									</div>
									<hr></hr>
									<ul style="font-size:13px;">
										<li padding:8px 3px;>
											如果您能留下好的建议，我们会尽快改进，以便能够为您提供更好的服务
										</li>
										<li style="padding:8px 3px;">
											您可以申请支付盾，就能在任何电脑上进行操作，免除数字证书备份的困扰。
										</li>
										<li padding:8px 3px;>
											为了保护您的帐户安全，建议您开通手机自主服务，随时掌握帐户资金和安全信息变更的情况。
										</li>

									</ul>

								</div>
							</div>
						</html:form>
						<span><img
								src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a
							href="javascript:returnCertificate()">返回钱门安全中心 </a> </span>
						<br></br>
					</div>
				</div>
			</div>
		</div>

		<c:import url="/_jsp/footer.jsp" />
		<script>
function returnCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=certificate";
}

</script>
	</body>
</html>
