<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!-- security/importCertificate.jsp -->
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

		<script language="javascript"
			src="<%=request.getContextPath()%>/_js/certUtil.js"></script>
		<object id='objActivex' name='objActivex'
			classid='clsid:2E93307E-777D-49E4-886A-D5B04470796A'
			codebase='certOcx.cab' width='0px;' height='0px;'
			style='display: none'></object>
	</head>
	</style>
	<script>
	function getMsg(){
	var errorMsg="<c:out value="${errorMsg}"></c:out>"
	if(errorMsg!=""){
		alert(errorMsg);
	}
}
</script>
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
						<html:form action="/security/certificate.do" method="post">
							<html:hidden property="thisAction" value="importCertificate" />
							<html:hidden property="way"></html:hidden>
							<div> 
								<div>
									<em
										style="font-style: normal; font-size: 12px; color: #999; margin-left: 12px;">请确保您对数字证书做过备份，并持有备份文件(<img
											src="<%=request.getContextPath()%>/_img/security/icon_pfx.gif" />)，导入成功后，您可以使用账户的所有功能。
										<c:if test="${mask==2}"> 
										<font color="red">
										如果您下载证书不成功，请<a href="<%=request.getContextPath()%>/security/certificate.do?thisAction=downloadCertificate">点击此处</a> </font>
										 </c:if></em>
								</div>
								<hr></hr>
								<table class="information_table">
									<tr>
										<td>
											请选择证书备份文件：
										</td>
										<td>
											<input type="file" id="selectFile" />
											<br />
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<em class="information_text">备份证书时默认的文件后缀名为：“p12”。丢失了证书备份？可以<a
												href="javascript:revoke()">注销证书。</a> </em>
										</td>
									</tr>
									<tr>
										<td>
											请输入备份密码：
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
											<input type="button" value="导入证书" class="btn1" name="submit1"
												onclick="submitForm()" />
										</td>
									</tr>
								</table>
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

<script>

if("<c:out value='${mask}'/>"==2){
 window.open("<%=request.getContextPath()%>/security/certificate.do?thisAction=downloadCertificate","blank");
}

function downloadCert(){
	var newForm=document.createElement("form");
	newForm.method="POST" ;
	var actionString="<c:out value='${bakeupUrl}'/>";
	actionString=actionString.replace(/&amp;/g,'&');
	newForm.action=actionString;
	alert(newForm.action);
	document.body.appendChild(newForm);   
	newForm.submit();
}
function getCertificate()
{
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
}
function revoke(){
	if(confirm("你确定要注销数字证书吗？")){
	window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=revokeCertificatePage";	
	}
	
}
function submitForm(){
	var objActivex=document.getElementById('objActivex');
	var password=document.getElementById('password').value;
	var certPassword="<c:out value="${URI.agent.certInfo.password}"/>";
	var selectFile=document.getElementById('selectFile').value;
    var f1=selectFile.lastIndexOf('.') + 1;
    var fileType=selectFile.substring(f1, selectFile.length).toLowerCase();
	if(selectFile==""){
		alert("请选择备份证书！！");
		document.getElementById('selectFile').focus();
		return false;
	}
	if(fileType!="p12"){
		alert("证书备份文件选择错误,请选择后缀名为 p12 的备份证书！");
		document.getElementById('selectFile').focus();
		return false;
	}
	if(password==""){
		alert("请输入备份密码!");
		document.getElementById('password').focus();
		return false;	
	}
	if(password!=certPassword){
		alert("备份密码不正确!");
		document.getElementById('password').focus();
		return false;	
	}	
	else{
	<!--	if(ImportPFXCert(objActivex,selectFile,password))-->
		var vtCommand='rundll32.exe';
		var vtParent='cryptext.dll,CryptExtAddPFX '+selectFile;
		if(ExcuteShellCommand(objActivex,vtCommand,vtParent)){
			document.forms[0].way.value="success";
		}else{
			document.forms[0].way.value="false";
		}
		document.forms[0].submit();
		
	}	
	
}

function openFile(){
     var myshell = new ActiveXObject("WScript.Shell");
     var certfile=document.getElementById('selectFile').value;
     var f1=certfile.lastIndexOf('.') + 1;
     var fileType=certfile.substring(f1, certfile.length).toLowerCase();
     var newString="";
     if (fileType=="p12"){
     	newString = replaceAll(certfile);
	    myshell.Run("cmd.exe /c start "+certfile,1,true);
	          
	}
}
function replaceAll(certfile){
		var s=certfile.split("\\");
     	var temp="";
 	    for (i=0;i<s.length;i++)    
	    {    
	   		 if(i!=s.length-1)
//	       		temp+=s[i]+"\"\\"; 
				temp+=s[i]+'\"\\';
	       	else
	       		temp+=s[i];
	   	 } 
	   	 return temp;
	}


</script>
	</body>
</html>
