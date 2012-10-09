<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!-- /#FFDEADsecurity/viewCertificate.jsp -->
<html>
<head>
<title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
	<script language="javascript" src="<%=request.getContextPath()%>/_js/common.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
<style>
#contract{
margin:12px 0;
width:100%;
border:1px solid #FFDEAD;
}
#contract td{
border-bottom:1px dashed #eee;
padding:1em;
text-align:right;
}
</style>
</head>

	<body>
<html:form action="/security/certificate.do">

	
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>查看证书</strong>
								</p>
							</div>
						</div>
							<div>
								<table cellpadding="0" cellspacing="0" boder="0" id="contract">
									<tr>
										<td width="15%">
											用户名称：
										</td>
										<td style="text-align:left;">
											<c:out value="${certInfo.userName}" />
										</td>
									</tr>
									<tr>
										<td width="15%">
											证书颁发者：
										</td>
										<td style="text-align:left;">
											<c:out value=" CN=www.qmpay.com CA Root, O=珠海市钱门网络科技有限公司, OU=钱门技术部, L=珠海, ST=广东省, C=CN" />
										</td>

									</tr>
									<tr>
										<td width="15%">
											证书序列号：
										</td>
										<td style="text-align:left;">
											<c:out value="${certInfo.serialNo}" />
										</td>

									</tr>
									<tr>
										<td width="15%">
											有效起始日期：
										</td>
										<td nowrap="nowrap" style="text-align:left;">
											<fmt:formatDate value="${certInfo.createDate}" pattern="yyyy年MM月dd日 a HH:mm:ss" />
										</td>
									</tr>
									<tr>
										<td width="10%">
											有效终止日期：
										</td>
										<td style="text-align:left;">
											<fmt:formatDate value="${certInfo.endTime}" pattern="yyyy年MM月dd日 a HH:mm:ss" />
										</td>
									</tr>
								</table>
							</div>
							<br/><br/>
						<span><img src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a href="javascript:getCertificate()">返回数字证书管理 </a></span>
						<br></br>
					</div>
				</div>
			</div>
				<c:import url="/_jsp/footer.jsp" />
		</div>
		</html:form>
<script>
function getCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
}
		
</script>
	</body>
</html>
