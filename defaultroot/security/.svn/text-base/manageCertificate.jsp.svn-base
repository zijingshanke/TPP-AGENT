<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/manageCertificate.jsp -->
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
		<html:form action="/security/certificate.do">
			<html:hidden property="thisAction" value="checkedApply" />
			<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>管理数字证书&nbsp;<img
												src="<%=request.getContextPath()%>/_img/zhengshu.jpg" /> </strong>
									</p>
								</div>
							</div>
							<div>
								<table class="popTable" style="margin-top: 10px; padding: 15px;">
									&nbsp;
									<c:if test="${URI.agent.validCertStatus==1}">
										<tr>
											<td>
												<img src="<%=request.getContextPath()%>/_img/Arrow.gif" />
												<a href="javascript:review()">查看证书</a>
											</td>
										</tr>
										<!--  
										<tr>
											<td>
												<img src="<%=request.getContextPath()%>/_img/Arrow.gif" />
												<a href="javascript:backUp()">备份证书</a>
											</td>
										</tr>
										
										<tr>
											<td>
												<li>
													需要在另外一台电脑上使用到钱门证书的用户，请备份！
												</li>
											</td>
										</tr> 
										<tr>
											<td>
												<li>
													为防止因重装系统、意外删除造成证书丢失，我们建议您备份证书！
												</li>
											</td>
										</tr>-->
										<tr>
											<td>
												<img src="<%=request.getContextPath()%>/_img/Arrow.gif" />
												<a href="javascript:deleteCert()">删除本地证书</a>
											</td>
										</tr>
										<tr>
											<td>
												<li>
													删除前请确保已经备份。删除后您在这台电脑上登录账户仅能查询！
												</li>
											</td>
										</tr>
										<tr>
											<td>
												<li>
													在不常用的电脑上，安装了证书使用完毕后，我们建议您删除证书，带走您备份的证书！
												</li>
											</td>
										</tr>
									</c:if>
									<c:if test="${URI.agent.validCertStatus==0}">
										<tr>
											<td>
												<img src="<%=request.getContextPath()%>/_img/Arrow.gif" />
												<a href="javascript:importCert()">导入证书</a>
											</td>
										</tr>
										<tr>
											<td>
												<li>
													将您的证书备份文件在这台电脑上导入后，您可以使用账户的所有功能
												</li>
											</td>
										</tr>
									</c:if>
									<tr>
										<td>
											<img src="<%=request.getContextPath()%>/_img/Arrow.gif" />
											<a href="javascript:revoke()">注销证书</a>
										</td>
									</tr>
									<tr>
										<td>
											<li>
												注销后，您的帐户将失去证书保护，建议在注销后及时申请新证书！
											</li>
										</td>
									</tr>
								</table>
								<br></br>
							</div>

						</div>
					</div>
				</div>
				<c:import url="/_jsp/footer.jsp" />
			</div>
		</html:form>
	</body>
	<!-- importCertificatePage -->
</html>
<script>
function review(){
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=reviewCertificate&type=review";
}
function backUp(){
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=backUpTips";
}
function deleteCert(){
	if(confirm("您的证书已经做好备份了吗？")){
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=reviewCertificate&type=delete";
	}
	
}
function revoke(){
	if(confirm("你确定要注销数字证书吗？")){
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=revokeCertificatePage";	
	}
	
}
function importCert(){
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=importCertificatePage";
}

</script>
