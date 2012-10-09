<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- security/confirmMessage.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/qianmen.css" type="text/css" />

		<style>
#contract {
	margin: 10px 0;
	width: 100%;
	font-style: normal;
	border: 1px solid #FFDEAD;
	font-size: 15px;
}

#contract td {
	border-bottom: 1px dashed #eee;
	padding: 1em;
}
</style>
	</head>

	<body>
		<html:form action="/security/certificate.do">
			<html:hidden property="thisAction" value="applyInstallCertificate" />
			<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>申请数字证书</strong>
									</p>
								</div>
								<img
									src="<%=request.getContextPath()%>/_img/security/zhengshu002.gif"
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
											<strong>确认个人信息</strong>
										</p>
									</div>
								</div>

								<table id="contract">
									<tr>
										<td>
											您的钱门账户：
										</td>
										<td>
											<c:out value="${URI.agent.loginName}" />
										</td>
									</tr>
									<tr>
										<td>
											您的姓名：
										</td>
										<td>
											<c:out value="${URI.agent.name}" />
										</td>
									</tr>
									<tr>
										<td>
											<input type="button" class="btn1" value="确定" onclick="install_root()" />
										</td>
									</tr>
								</table>

								<p>
									<strong> 点击“确定”后，请选择所有弹出窗口中的“是（Y）”完成申请。</strong>
								</p>
							</div>
						</div>
					</div>
				</div>
				<c:import url="/_jsp/footer.jsp" />
			</div>
		</html:form>		
		<object id="XEnroll" classid="clsid:127698e4-e730-4e5c-a2b1-21490a70c8a1" codebase="xenroll.dll">
		</object>
		<OBJECT id="OBJ1" height="0" width="0"
			classid="clsid:B524677D-10C3-4B77-A049-90B1264520A0"
			codeBase="cspex.cab#Version=1,0,3,1" VIEWASTEXT></OBJECT>
<script language="javascript"> 	  
function install_root(){
          var sPKCS7 ="-----BEGIN CERTIFICATE-----"+
                      "<%=request.getAttribute("certInfo")%>"+
           			  "-----END CERTIFICATE-----";
       try{   
            new ActiveXObject("CSPEx.ClientUI") ; 
            new ActiveXObject("CSPEx.ClientUI") ; 
                 
       }catch(e)   
       {   
               try{   
                     XEnroll.InstallPKCS7(sPKCS7);
                     document.forms[0].submit();
                                                  
               }catch(ex)   
               {   
                       alert("安装证书失败 :\n描述: " + ex.description + "\n代码:" + ex.number );   
               }   
       }             
	
}
</script>
	</body>
</html>
