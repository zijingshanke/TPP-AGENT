<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!-- security/installCertificate.jsp -->
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

		</style>
		<object id="myControl1" name="myControl1"
			classid="clsid:C844E2C9-1921-46FB-A564-0CCDB3116844"
			codebase="certOcx.cab" width="0px;" height="0px;"
			style="display: none"></object>
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
									<strong>导入证书</strong>
								</p>
							</div>
						</div>
						<br></br>
						<div>
							<div>
								<em
									style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">请确保您对数字证书做过备份，并持有备份文件(<img
										src="<%=request.getContextPath()%>/_img/security/icon_pfx.gif" />)，导入成功后，您可以使用账户的所有功能</em>
							</div>
							<hr></hr>
							
							<h5 style="padding:8px">&nbsp;&nbsp;
								请您按照向导的提示正确的导入证书，如果向导未显示,即导入失败，请
								<html:link page="/security/certificate.do?thisAction=importCertificatePage"> 点击此处 </html:link>重新导入
							</h5>
						</div>
						<span><img
								src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a
							href="javascript:getCertificate()">返回数字证书管理 </a> </span>
						<br></br>
					</div>
				</div>
			</div>

			<c:import url="/_jsp/footer.jsp" />
		</div>
<script>

<!--	hasIeCert(); -->
function hasIeCert()
{
	var myControl = document.getElementById("myControl1");	
	var serialNumber='<c:out value="${URI.agent.serialNo}"/>';
// 	serialNumber="4ae695d0";
	
	if(serialNumber!=null&&serialNumber.length>0){
		if(myControl.FindSertSerialNumber(serialNumber.toLowerCase())){
			window.location.href="/fdpay-client/security/suess.jsp";
		}
		window.setTimeout(hasIeCert,"5000");
	}	
}


function getCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
}

</script>
	</body>
</html>
