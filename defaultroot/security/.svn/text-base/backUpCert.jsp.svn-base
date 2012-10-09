<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!--/security/backUpCert.jsp -->
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
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/_js/certUtil.js"></script>

	</head>
	<body>
		<br />
		<html:form action="/security/certificate.do" method="post">
			<html:hidden property="thisAction" value="backUpCertificate" />
			<html:hidden property="type"></html:hidden>
			<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />

				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>备份数字证书</strong>
									</p>
								</div>
							</div>
							<hr></hr>
							<div class="yellowBox_mes">
								<!-- <img src="<%=request.getContextPath()%>/_img/info_15.gif" /> -->
								<strong style="font-size: 18px;">请注意备份您的证书</strong>
								<br></br>

								<em
									style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">为了防止您因重装系统或删除证书等操作造成证书丢失，建议您将证书备份到移动存储上。</em>
								<hr></hr>
								<li>
									如：U盘、移动硬盘、软盘等
								</li>
								<br></br>
								<br></br>
								<table class="information_table">
									<tr>
										<td>
											<c:if test="${certmsg==null}">
											设置备份密码：
											</c:if>
											<c:if test="${certmsg!=null}">
											输入备份密码：
											</c:if>
										</td>
										<td>
											<html:password property="password" styleId="password"
												styleClass="text_style" value=""></html:password>
											<br />
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<em class="information_text">密码至少6位，在其他机器导入证书时使用，请务必牢记！</em>
										</td>
									</tr>

									<tr>
										<td>
											确认备份密码：
										</td>
										<td>
											<input type="password" id="confirmPwd" name="confirmPwd"
												class="text_style" value="" />
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<em class="information_text">温馨提示：为保障您的安全,钱门只提供一次备份机会,请妥善保管好您即将备份的数字证书。</em>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<!--  	<html:submit styleClass="btn1" value="备份"></html:submit>-->
											<c:if test="${certmsg==null}">
												<input type="button" value="生成证书" class="btn1"
													name="createBtn" onclick="create()" />
											</c:if>
											<c:if test="${certmsg!=null}">
												<input type="button" value="备份证书" class="btn1"
													name="submit1" onclick="submitForm()" /> &nbsp;
												
											</c:if>
										</td>
									</tr>
								</table>

							</div>
							<c:if test="${URI.agent.certStatus==1}">
								<span><img
										src="<%=request.getContextPath()%>/_img/leftArrow.gif" /> <a
									href="javascript:getCertificate()">返回数字证书管理 </a> </span>
							</c:if>
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

function create(){	
	if(validate()==false){
		return;
	}
	document.forms[0].thisAction.value="createCertificate";
//	document.forms[0].type.value="create";
	alert("温馨提示：生成证书的过程可能要花费您十多秒钟，请耐心等待……");
	document.forms[0].submit();
	document.forms[0].createBtn.disabled=true;
	afterBackUp();
}
function submitForm(){
//	document.forms[0].type.value="download";
	var confirmPwd=document.getElementById('confirmPwd').value;
	var password=document.getElementById('password').value;
	var certPassword="<c:out value="${certPassword}"/>";
	if(password==""){
		alert("备份密码是必填字段！");
		document.getElementById('password').focus();
		return false;
	}
	if(password!=certPassword){
		alert("备份密码不正确！");
		document.getElementById('password').focus();
		return false;
	}
	if(confirmPwd!=password){
		alert("确认备份密码和备份密码必须一致！");
		document.getElementById('confirmPwd').focus();
		return false;
	}
	document.forms[0].submit();
	document.forms[0].submit1.disabled=true;
	document.getElementById('installCert').style.display="block";
}
function validate(){
	var confirmPwd=document.getElementById('confirmPwd').value;
	var password=document.getElementById('password').value;
	if(password==""){
		alert("备份密码是必填字段！");
		document.getElementById('password').focus();
		return false;
	}
	if(password.length<6){
		alert("备份密码至少6位！");
		document.getElementById('password').focus();
		return false;		
	}
	if(confirmPwd!=password){
		alert("确认备份密码和备份密码必须一致！");
		document.getElementById('confirmPwd').focus();
		return false;
	}	
	return true;
}
function afterBackUp(){
	// alert("--");
	var secs =15;
    document.body.style.cursor="wait";
    for(i=1;i<=secs;i++) {
      window.setTimeout("update(" + i + ","+secs+")", i * 1000);
    }
 
}
function update(num){
		var secs =15;
		if(num == secs) { 
			document.body.style.cursor="default";
		} 
		else { 
			document.body.style.cursor="wait";
	} 
}
function getCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
}

function importCert(){
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=importCertificatePage";
}

</script>
