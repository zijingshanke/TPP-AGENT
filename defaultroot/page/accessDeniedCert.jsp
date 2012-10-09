<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,com.fordays.fdpay.right.UserRightInfo,com.fordays.fdpay.agent.Agent"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
			UserRightInfo uri = (UserRightInfo) request.getSession()
			.getAttribute("URI");
	Agent agent = new Agent();
	if (uri != null) {
		agent = uri.getAgent();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP证书: page/accessDeniedCert.jsp -->

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../_css/global_v2.css" />

		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/prototype.js"></script>

	</head>
	<body>
		<div id="warp">
		<c:if test="${URI!=null}">
    <c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,0,3"/>
   </c:if>
			
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="Worg">
							<div class="attentionTitle attentionTitle1">
								<em>您本台电脑上未安装数字证书，所以无法继续进行刚才的操作，您可以先申请注销证书，以便完成当前操作。</em>
							</div>
							<ul class="attentionList">
								<li>
									如果您有证书备份文件，您可以选择
									<a href="javascript:importCert()">导入证书备份文件</a>，导入后即可继续您刚才的操作。
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<c:import url="/_jsp/footer.jsp" />
		</div>
		
		<script>
function importCert(){
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=importCertificatePage";
}
</script>
	</body>
</html>