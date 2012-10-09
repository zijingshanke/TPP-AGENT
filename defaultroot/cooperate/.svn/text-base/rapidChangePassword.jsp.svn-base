<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: security/changePassword.jsp -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link href="<%=request.getContextPath()%>/_css/qianmen1.css"
			rel="stylesheet" type="text/css" />
		<script src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
		<script type="text/javascript" src="../_js/validateform.js"></script>

		<script>
 		function toSubmit()
{
	var formid = document.forms[0];
	
		if(validateForm(formid))
		{
		
			var flg=checkEquals('password','repassword','repasswordTip2','OK!','两次填写的密码不一致，请重新填写。')
			if(!flg){
				document.getElementById('repassword').focus();
				return;
			}
			flg=checkPayPassword();
			if(!flg){
				document.getElementById('payPassword').focus();
				return;
			}
			
			flg=checkEquals('payPassword','rePaypassword','repasswordTip4','OK!','两次填写的密码不一致，请重新填写。')
			if(!flg){
				document.getElementById('rePaypassword').focus();
				return;
			}
			formid.submit();
		}
	
}
   	function checkPayPassword(){
  	
  		if(lengthBetween('payPassword','repasswordTip3',6,20,'您设置的登录密码有误，登录密码应该由6-20个英文字母、数字或符号组成。')){
  			
  			return	checkNotEquals('password','payPassword','repasswordTip3','OK!','登录密码与支付密码一致，请重新填写。');
  			 
  		}
  		return true;
  	}
 	</script>
		<style>
	.id_card {
		border:1px solid #FFD327;
		background:#FFFFEE;
		padding:15px 0 15px 15px;
		margin-top:10px;
	}	
	</style>
	</head>

	<body>
		<div id="warp">
		<c:if test="${URI==null}">
   <c:import url="../selfHelp/head.jsp"></c:import>
   </c:if>
   <c:if test="${URI!=null}">
   <c:import url="../_jsp/mainTop.jsp"></c:import>
   </c:if>
		<div id="container" class="register">
			<html:form action="cooperate/gateway?">
				<html:hidden property="service" value="save_register_account"></html:hidden>
				<html:hidden property="url" value="${GatewayForm.url}"/>
				<table width="100%" border="0" align="left" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>

					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title" align="left">
								<span>设置密码</span>
							</div>
							<div>
								<table align="left">
									
									<tr>
										<td style="width: 20%; text-align: right;">
											密码:
										</td>
										<td style="width: 7%;"></td>
										<td>
											<input type="password" name="password" id="password"
												class="text_style"
												onclick="setText('repasswordTip','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置密码。')"
												onblur="lengthBetween('password','repasswordTip',6,20,'您设置的登录密码有误，登录密码应该由6-20个英文字母、数字或符号组成。')"
												rule="length:6:20" />
										</td>
										<td style="width: 470px;text-align: left">
											<div id="repasswordTip"></div>
										</td>
									</tr>
									<tr>
										<td style="width: 20%; text-align: right;">
											确认密码:
										</td>
										<td style="width: 7%;"></td>
										<td>
											<input type="password" name="repassword" id="repassword"
												class="text_style"
												onclick="setText('repasswordTip2','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置密码。')"
												onblur="checkEquals('password','repassword','repasswordTip2','OK!','两次填写的密码不一致，请重新填写。')"
												rule="length:6:20" />
										</td>
										<td style="width: 470px;text-align: left">
											<div id="repasswordTip2"></div>
										</td>
									</tr>
									<tr>
										<td style="width: 20%; text-align: right;">
											支付密码:
										</td>
										<td style="width: 7%;"></td>
										<td>
											<input type="password" name="payPassword" id="payPassword"
												class="text_style"
												onclick="setText('repasswordTip3','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置密码。')"
												onblur="checkPayPassword();"
												rule="length:6:20" />
										</td>
										<td style="width: 470px;text-align: left">
											<div id="repasswordTip3"></div>
										</td>
									</tr>
									<tr>
										<td style="width: 20%; text-align: right;">
											确认支付密码:
										</td>
										<td style="width: 7%;"></td>
										<td>
											<input type="password" name="rePaypassword" id="rePaypassword"
												class="text_style"
												onclick="setText('repasswordTip4','请再填写一遍您上面填写的支付密码。')"
												onblur="checkEquals('payPassword','rePaypassword','repasswordTip4','OK!','两次填写的密码不一致，请重新填写。')"
												rule="length:6:20" />
										</td>
										<td style="width: 470px;text-align: left">
											<div id="repasswordTip4"></div>
										</td>
									</tr>
									<tr align="center">
										<td colspan="4">
											<span class="simplyBtn_1"><input type="button"
													style="width: 120px" class="buttom_middle" value="确定"
													onclick="toSubmit()" />
											</span>
										</td>
									</tr>
								</table>
							</div>
						</td>
						<td class="tableRR"></td>
					</tr>

					<tr>
						<td class="tabelLB"></td>
						<td class="tableBB"></td>
						<td class="tabelRB"></td>
					</tr>

				</table>
			</html:form>
		</div>
		<c:import url="../_jsp/footer.jsp"></c:import>

		</div>

	</body>

</html>
