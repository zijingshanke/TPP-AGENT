<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/applyInstallCert.jsp -->
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
			<html:hidden property="thisAction" value="backUpTips" />

			<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>申请安装数字证书</strong>
									</p>
								</div>
								<img
									src="<%=request.getContextPath()%>/_img/security/zhengshu003.gif"
									style="margin-top: 12px;" />
							</div>
						</div>
					</div>
					<div class="main_top">
						<div class="main_bottom">
							<div class="main_mid">
								<div class="main_title">
									<div class="main_title_right">
										<p>
											<strong>特别提醒</strong>
										</p>
									</div>
								</div>
								<div class="yellowBox_mes">
									<em style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">请备份数字证书：
									</em>
									<hr></hr>
									<li>
										请将数字证书备份到
										<font color="red"> 移动存储器上（如U盘，移动硬盘），请不要备份到系统盘上（如C盘）</font>，避免电脑系统重装删除！
									</li>
									<br></br>
									<br></br>
									<br></br>
									<br></br>
									<html:submit styleClass="btn1" value="备份"></html:submit>
								</div>
								
							</div>
						</div>
					</div>
				</div>
				<c:import url="/_jsp/footer.jsp" />

			</div>
		</html:form>
	</body>
</html>
<script>
</script>
