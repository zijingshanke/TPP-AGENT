<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/applyCert.jsp -->
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
		<html:form action="/security/certificate.do"
			onsubmit="return validate();">
			<html:hidden property="thisAction" value="checkedApply" />
			<html:hidden property="type" value="apply"></html:hidden>
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
									src="<%=request.getContextPath()%>/_img/security/zhengshu001.gif"
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
											<strong>填写资料</strong>
										</p>
									</div>
								</div>
								<div class="yellowBox_mes">
									请牢记您的申请信息，在后期证书管理（如关闭数字证书服务、再次申请数字证书）过程中需要用到。
								</div>

								<table cellpadding="0" cellspacing="0" width="100%"
									class="information_table">
									<tr>
										<td style="width: 20%; text-align: right;">
											姓名：
										</td>
										<td style="width: 80%;" style="text-align:left;">
											<html:text property="userName" styleId="userName" indexed="1"
												styleClass="text_style"></html:text>
											<em class="information_text">请输入您身份证上的真实姓名</em>
										</td>
									</tr>
									<tr>
										<td style="text-align: right;">
											证件类型：
										</td>
										<td style="text-align: left;">
											身份证
										</td>
									</tr>
									<tr>
										<td style="text-align: right;">
											证件号码：
										</td>
										<td style="text-align: left;">
											<html:text property="identity" styleId="identity" indexed="1"
												styleClass="text_style"></html:text>
											<em class="information_text">请输入正确的身份证号码，在后期找回或线下注销证书时需要用到</em>
										</td>
									</tr>
									<tr>
										<td style="text-align: right;"> 
											绑定的手机号码：
										</td>
										<td style="text-align: left;">
											<html:text property="mobile" styleId="mobilePhone" indexed="1"
												styleClass="text_style"></html:text>
											<em class="information_text">请输入您的手机号码以便接受通知，在后期进行证书管理时需要用到</em>
										</td>
									</tr>

									<tr>
										<td></td>
										<td><div id="moblieTips"><a href="javascript:bindMobilephone()">更换号码</a> </div>
										</td>
									</tr>

									<%
										/*<tr>
										<td style="text-align: right;">
										自定义证书名称：
										</td>
										<td style="text-align: left;">
										<html:text property="certName" styleId="certName" indexed="1" styleClass="text_style"></html:text>
										<em class="information_text">如：“公司笔记本证书”、“家里台式电脑证书”</em>
										</td>
										</tr>
										<tr>
										<td style="text-align: right;">
										附加码：
										</td>
										<td style="text-align: left;">
										<input type="text" style="width: 80px;" />
										<html:img border="0"
										page="/servlet/com.neza.base.NumberImage" align="absmiddle"
										height="21" width="64" />
										<em class="information_text">附加码看不清，<a href="#">换一张</a>
										</em>
										</td>
										</tr>
										 */
									%>
									<tr>
										<td>
											&nbsp;
										</td>
										<td style="text-align: left;">
											<span class="simplyBtn_1"> <input type="submit"
													class="buttom_middle" value="下一步" /> </span>
										</td>
									</tr>
								</table>
								</form>
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
function showTips(){
 	document.getElementById('moblieTips').style.display="block";
}
function bindMobilephone(){
	window.location.href="<%=request.getContextPath()%>/agent/agent.do?thisAction=viewMobileCenter";
}

function validate(){
	with(document.forms[0]){
	
		if(!required(userName)){
			alert('姓名是必填字段!');
			userName.focus();
			return false;
		}
		if(!required(identity)){
			alert('证件号码是必填字段!');
			identity.focus();
			return false;
		}else{
			if (!verifyIdentity(identity.value)){
				alert('证件号码输入有误!');
				identity.focus();
				return false;
			}
		}
		if(!required(mobilePhone)){
			alert('绑定的手机号码是必填字段!');
			mobile.focus();
			return false;
		}else{
			if(!verifyMobile(mobile.value)){
				alert('手机号码输入有误!');
				mobile.focus();
				return false;				
			}
			if('<c:out value="${agent.mobilePhone}" />'!= mobile.value ){
				alert('请输入您当前绑定的手机号码,若要重新绑定,请点击“更改号码” !');
				mobile.focus();
				return false;			
			}
		}

	}
	return true;
}
function verifyMobile(_mobile){
	if((/^13\d{9}$/g.test(_mobile))
	||(/^15\d{9}$/g.test(_mobile)) 
	||(/^18\d{9}$/g.test(_mobile))){
		return true;
	}
	return false;
}

</script>
