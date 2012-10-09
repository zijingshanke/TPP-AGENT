<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/revokedWay.jsp -->
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
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>选择证书注销方式 &nbsp;<a
										href="javascript:getCertificate()">返回数字证书管理 </a> </strong>
								</p>
							</div>
						</div>
						<hr style="color: #FFDEAD"></hr>
						<div>
							<html:form action="/security/certificate.do">
								<html:hidden property="thisAction" value="verifyRevokeCert" />
								<html:hidden property="way" value="existInIE"/>
									<div>
										<p>
											手机注销：
											<font color="#F17B1E"> 即刻注销成功（推荐），您的手机号码为： <input
													id="mobilephone" type="text"
													style="border: 0px; width: 70px;"
													value="<c:out value='${URI.agent.mobilePhone}' />" />

												，我们将把确认信息发送到你的手机上。如果手机号码有误，请选择Email注销或者人工注销。</font>
										</p>
									</div>
									<div>
										<table>
											<tr>
												<td>
													<img src="<%=request.getContextPath()%>/_img/Mobile.gif" />
												</td>
												<td>
													<img
														src="<%=request.getContextPath()%>/_img/security/arrow.jpg" />
												</td>
												<td>
													<img src="<%=request.getContextPath()%>/_img/Mobile.gif" />
												</td>
												<td>
													<img
														src="<%=request.getContextPath()%>/_img/security/arrow.jpg" />
												</td>
												<td>
													<img
														src="<%=request.getContextPath()%>/_img/Mobile_gou.gif" />
												</td>
											</tr>
											<tr>
												<td>
													<img
														src="<%=request.getContextPath()%>/_img/security/1.bmp" />
												</td>
												<td>
												</td>
												<td>
													<img
														src="<%=request.getContextPath()%>/_img/security/2.bmp" />
												</td>
												<td>
												</td>
												<td>
													<img
														src="<%=request.getContextPath()%>/_img/security/revokesuccess.bmp" />
												</td>
											</tr>

										</table>
									</div>
									<br></br>
									<div>
										<input type="submit" value="申请注销" />
										<input type="button" value="取消申请" onclick="getCertificate()" />
									</div>

								
							</html:form>
						</div>
						<br></br>
						<span><img src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a
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
var mobilephone=document.getElementById('mobilephone').value;
var tempMobile;
if(mobilephone!=""){
	tempMobile=mobilephone.substring(6,mobilephone.length);
	document.getElementById('mobilephone').value="******"+tempMobile;
}

function getCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
}

</script>

