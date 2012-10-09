<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<!-- /security/deleteCert.jsp -->
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
ul
{
	font-size:13px;
}
li
{
  padding:8px 3px;
}
</style>
	</head>
	
	<body>
		<html:form action="/security/certificate.do">
			<html:hidden property="thisAction" value="deleteCertificate" />
			<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>删除本地证书&nbsp;<img
												src="<%=request.getContextPath()%>/_img/zhengshu.jpg" /> </strong>
									</p>
								</div>
							</div>
							<div>
								<div class="yellowBox_mes">
									<strong style="font-size:18px;">您的证书备份了吗？</strong>
									<div>
										<em
											style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">删除前请确保已经备份证书。删除证书后，您在这台电脑上登录账户仅能做查询操作。</em>
									</div>
									<hr></hr>
									<ul>
										<li>
											证书一旦删除，您在这台电脑上登录账户仅能查询，请慎重操作；
										</li>
										<li>
											如果您尚未备份证书，请
											<a href="javascript:backUp()">备份证书</a>。
										</li>
										<li>
											以下是您正在使用中的证书信息：
										</li>
									</ul>
										<div style="margin-left:40px;">
											<table id="contract" style="background:#FBF9F7;width:70%;height:120px;border: 1px solid #eee;">
												<tr>
													<td style="width:15%;border-bottom: 1px dashed #eee;">
														用户名称：
													</td>
													<td style="text-align: left;border-bottom: 1px dashed #eee;">
														<c:out value="${certInfo.userName}" />
													</td>
												</tr>
												<tr>
													<td style="width:15%;border-bottom: 1px dashed #eee;">
														证书颁发者：
													</td>
													<td style="text-align: left;border-bottom: 1px dashed #eee;">
														<c:out value=" CN=www.qmpay.com CA Root, O=珠海市钱门网络科技有限公司, OU=钱门技术部, L=珠海, ST=广东省, C=CN" />
													</td>

												</tr>
												<tr>
													<td style="width:15%;border-bottom: 1px dashed #eee;">
														证书序列号：
													</td>
													<td style="text-align: left;border-bottom: 1px dashed #eee;">
														<c:out value="${certInfo.serialNo}" />
													</td>

												</tr>
												<tr>
													<td style="width:18%;border-bottom: 1px dashed #eee;">
														有效起始日期：
													</td>
													<td nowrap="nowrap" style="text-align: left;border-bottom: 1px dashed #eee;">
														<fmt:formatDate value="${certInfo.createDate}"
															pattern="yyyy年MM月dd日 a HH:mm:ss" />
													</td>
												</tr>
												<tr>
													<td style="width:18%;border-bottom: 1px dashed #eee;">
														有效终止日期：
													</td>
													<td style="text-align: left;border-bottom: 1px dashed #eee;">
														<fmt:formatDate value="${certInfo.endTime}"
															pattern="yyyy年MM月dd日 a HH:mm:ss" />
													</td>
												</tr>
											</table>
									</div>
									<p style="margin-left:600px;">
											<input type="button" value="确认删除" class="btn1" readonly="readonly" />
									</p>
									
								</div>
							</div>
						<span><img src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a href="javascript:getCertificate()">返回数字证书管理 </a></span>
						<br></br>
						</div>
					</div>
				</div>
				<c:import url="/_jsp/footer.jsp" />
			</div>
		</html:form>
	</body>
</html>
<script>
	function getCertificate()
	{
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
	}
function backUp(){
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=backUpTips";
}
</script>
