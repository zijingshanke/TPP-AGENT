<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/revokeCert.jsp -->
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
									<strong>注销数字证书 &nbsp;<img
											src="<%=request.getContextPath()%>/_img/zhengshu.jpg" /> </strong>
								</p>
							</div>
						</div>
						<html:form action="/security/certificate.do">
							<html:hidden property="thisAction" value="revokeCertificate" />
							<html:hidden property="way" value="noExistInIE" />
							<div>
								<div class="yellowBox_mes">
									<strong style="font-size: 18px;">确认操作</strong>
									<br></br>
									<div>
										<em
											style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">钱门数字证书被注销后，您的钱门账户会失去数字证书的保护，建议您不要轻易注销。</em>
									</div>
									<hr></hr>
									<ul style="font-size: 13px;">
										<li style="padding: 8px 3px;">
											证书一旦注销 ，你将失去数字证书保护功能，此操作请慎重！
										</li>
										<li style="padding: 8px 3px;">
											如有需要，您 可以在注销完成的1个小时后后重新申请证书。
										</li>
										<li style="padding: 8px 3px;">
											钱门数字证书 注销不会影响该证书在银行系统的使用。
										</li>

									</ul>
									<p style="margin-left: 600px;">
										<input type="button" value="确认注销" class="btn1" id="subBtn"
											onclick="isConfirm()" />
										<input type="button" value="取消注销" class="btn1"
											onclick="getCertificate()" />
									</p>

								</div>
							</div>
						</html:form>
						<span><img
								src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a
							href="javascript:getCertificate()">返回数字证书管理 </a> </span>
						<br></br>
					</div>
				</div>
			</div>
			<c:import url="/_jsp/footer.jsp" />
		</div>
	</body>
	<!-- importCertificatePage -->
</html>
<script>
function isConfirm(){
	if(confirm("注销证书成功后，使用钱门的所有功能都不再需要证书，时候确定注销证书？")){
		document.forms[0].submit();
		document.forms[0].subBtn.disabled=true;
	}
}
function getCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
}


</script>
