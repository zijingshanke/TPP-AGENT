<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: agent/register.jsp -->


	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<link href="<%=request.getContextPath()%>/_css/reset.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/_css/qianmen1.css"
			rel="stylesheet" type="text/css" />
		<script src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
		<script src="<%=request.getContextPath()%>/_js/validateform.js"></script>
		<script src="<%=request.getContextPath()%>/_js/prototype.js"></script>
		<script>
	  	function checkPayPassword(){
  	
  		if(lengthBetween('payPassword','paypasswordTip',6,20,'您设置的登录密码有误，登录密码应该由6-20个英文字母、数字或符号组成。')){
  			
  			checkNotEquals('password','payPassword','paypasswordTip','OK!','登录密码与支付密码一致，请重新填写。');
  			
  		}
  	}
  	
  	function showRegisterType(no){
  		var table2=document.getElementById("TABLE2");
  		if(no=="0"){
  			table2.style.display = "none";
  		}else if(no=="1"){
  			table2.style.display = "block";
  		}
  		
  	}
  	
  	function download(){
  	   var companyName=	document.getElementById("companyName");
  	   companyName.disabled="";
  	}
</script>
	</head>
	<body>
		<div id="header">
			<div id="logo">
				<a href="<%=request.getContextPath()%>/index.jsp" class="logo"></a>
			</div>
			<span style="float: right; padding-top: 40px;">
				<div class="quickLink">
					<img src="../_img/phone.gif"/>&nbsp;：
					<i style="font-size: 20px; color: red;">0756-8801800</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，请
					<a
						href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister">注册</a>
					或
					<a href="<%=request.getContextPath()%>/login.jsp">登录</a>
				</div> </span>
			<div class="clear"></div>
			<div class="nav">
				<div>
					注册钱门账户
					<span class="otMsg">通过Email地址，您可以安全，简单，快捷的进行网上付款和收款</span>
				</div>
			</div>
		</div>
		<html:form action="/agent/agent.do?thisAction=register" method="post">
			<div id="container" class="register">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>
					<font color="red"><c:if test="${message!=null}">
							<c:out value="${message}" />
						</c:if>
					</font>
					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title">
								<span>1、填写个人信息</span>
							</div>
							<table width="98%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 帐户名：
									</td>
									<td width="220">
										<input class="text_style" type="text" id="loginName"
											value="<c:out value='${ag.loginName}'/>" name="loginName"
											id="loginName" onblur="checkLogin()"
											onclick="chkemail('loginName','loginMessage','Email地址是您登录钱门的帐户名，请正确填写。您可以通过此邮箱收款或付款，轻松完成网上交易。')" />
									</td>
									<td width="590">
										<div id="loginMessage"></div>
									</td>
								</tr>
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 确认帐户名：
									</td>
									<td width="220">
										<input onpaste="return false;" class="text_style" type="text"
											value="<c:out value='${ag.reLoginName}'/>" name="reLoginName"
											id="reLoginName"
											onclick="setText('checkLogin','请再填写一遍您上面填写的Email地址')"
											onblur="setText('checkLogin','');checkEquals('loginName','reLoginName','checkLogin','OK!','两次填写的Email地址不一致，请重新填写。')" />
									</td>
									<td>
										<div id="checkLogin"></div>
									</td>
								</tr>
							</table>
						</td>
						<td class="tableRR"></td>
					</tr>
					<tr>
						<td class="tabelLB"></td>
						<td class="tableBB"></td>
						<td class="tabelRB"></td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>
					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title">
								<span>2、设置登录密码</span>
							</div>
							<table width="98%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 登录密码：
									</td>
									<td width="220">
										<input type="password" class="text_style" name="password"
											id="password"
											onclick="setText('passwordTip','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置登录密码。')"
											onblur="lengthBetween('password','passwordTip',6,20,'您设置的登录密码有误，登录密码应该由6-20个英文字母、数字或符号组成。')" />
									</td>
									<td>
										<div id="passwordTip"></div>
									</td>
								</tr>
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 确认登录密码：
									</td>
									<td width="220">
										<input type="password" class="text_style" name="repassword"
											id="repassword"
											onclick="setText('repasswordTip','请再填写一遍您上面填写的登录密码。')"
											onblur="checkEquals('password','repassword','repasswordTip','OK!','两次填写的密码不一致，请重新填写。')" />
									</td>
									<td width="590">
										<div id="repasswordTip"></div>
									</td>
								</tr>
							</table>
						</td>
						<td class="tableRR"></td>
					</tr>
					<tr>
						<td class="tabelLB"></td>
						<td class="tableBB"></td>
						<td class="tabelRB"></td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>
					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title">
								<span>3、设置支付密码</span>
							</div>
							<table width="98%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 支付密码：
									</td>
									<td width="220">
										<input type="password" class="text_style" name="payPassword"
											id="payPassword"
											onclick="setText('paypasswordTip','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置支付密码。')"
											onblur="checkPayPassword();" />
									</td>
									<td width="590">
										<div id="paypasswordTip"></div>
									</td>
								</tr>
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 确认支付密码：
									</td>
									<td width="220">
										<input type="password" class="text_style" name="repayPassword"
											id="repayPassword"
											onclick="setText('repaypasswordTip','请再填写一遍您上面填写的支付密码。')"
											onblur="checkEquals('payPassword','repayPassword','repaypasswordTip','OK!','两次填写的密码不一致，请重新填写。')" />
									</td>
									<td width="590">
										<div id="repaypasswordTip"></div>
									</td>
								</tr>
							</table>
						</td>
						<td class="tableRR"></td>
					</tr>
					<tr>
						<td class="tabelLB"></td>
						<td class="tableBB"></td>
						<td class="tabelRB"></td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>
					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title">
								<span>4、设置安全保护问题</span>
							</div>
							<table width="98%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 安全保护问题：
									</td>
									<td width="220">
										<select name="question" id="question">
											<option value="我爸爸妈妈的名字各是什么?">
												我爸爸妈妈的名字各是什么?
											</option>
											<option value="我婆婆或岳母的名字叫什么?">
												我婆婆或岳母的名字叫什么?
											</option>
											<option value="我爸爸的出生地在哪里?">
												我爸爸的出生地在哪里?
											</option>
											<option value="我妈妈的出生地在哪里?">
												我妈妈的出生地在哪里?
											</option>
											<option value="我的小学校名是什么?">
												我的小学校名是什么?
											</option>
											<option value="我的中学校名是什么?">
												我的中学校名是什么?
											</option>
											<option value="我的一个好朋友的手机号码是什么?">
												我的一个好朋友的手机号码是什么?
											</option>
											<option value="我的一个好朋友的爸爸名字是什么?">
												我的一个好朋友的爸爸名字是什么?
											</option>
										</select>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td width="130" align="right">
										<span class="require">*</span> 您的答案：
									</td>
									<td width="220">
										<input class="text_style" type="text"
											value="<c:out value='${ag.answer}'/>" name="answer"
											id="answer"
											onclick="setText('answerTip','这个问题和答案是在找回密码时使用，请您牢记。')"
											onblur="lengthBetween('answer','answerTip',4,32,'安全保护问题答案必须在4-32位字符之间,请重新填写。')" />
									</td>
									<td width="590">
										<div id="answerTip"></div>
									</td>
								</tr>
							</table>
						</td>
						<td class="tableRR"></td>
					</tr>
					<tr>
						<td class="tabelLB"></td>
						<td class="tableBB"></td>
						<td class="tabelRB"></td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>
					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title">
								<span>5、填写您的个人信息（请如实填写，否则将无法正常收款或付款）</span>
							</div>
							<table width="98%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="130" align="right" valign="top">
										<span class="require">*</span> 用户类型：
									</td>
									<td colspan="2">
										<label>
											<input type="radio" name="registerType" id="registerType"
												value="0" class="radio" checked
												onclick="showRegisterType(0)" />
											个人
											<p class="persionAccount">
												以个人身份姓名来开设钱门账户
											</p>
										</label>
										<label>
											<input type="radio" name="registerType" id="registerType"
												value="1" class="radio" onclick="showRegisterType(1)" />
											企业
											<p class="businessAccount">
												以营业执照上的公司名称（包括个体工商户）开设钱门账户，此类账户必须拥有公司类型的银行账户
											</p>
										</label>
									</td>
								</tr>
							</table>
							<div>
								<div id="TABLE2" style="display: none;">
									<table>
									<tr>
											<td></td>
											<td colspan="2">
												<div class="yellowBox">
													<font color="red">请先下载企业账户注册申请表，以传真或者以Email的方式提交到钱门</font>
											</td>
										</tr>
										<tr>
											<td></td>
											<td colspan="2">
												<div class="yellowBox">
													钱门账户企业版办理流程：
													<br />
													<a href="../_file/EnterpriseCertification.doc"
														onclick="download();"><img src="../_img/calendar1.gif" />请先下载申请表</a>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<img src="../_img/rightArrow.gif" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正确填写后Email到
													qmpay@qmpay.com ！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<img src="../_img/rightArrow.gif" />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审核认证后您会得到钱门的通知邮件！
													<br />
													<font color="red">公司名称填写营业执照上的公司名称（包括个体工商户），此类账户须拥有对公银行账户。真实姓名填写法人身份证姓名，您所填写的信息都将会严格保密，不会在任何场所公开。</font>
												</div>
											</td>
										</tr>
										<tr>
											<td width="130" align="right">
												<span class="require">*</span> 公司名称：
											</td>
											<td width="220">
												<input class="text_style" type="text" value=""
													name="companyName" DISABLED id="companyName"
													onclick="setText('companyNameTip','请如实填写您公司的名称。注册后不可更改。')"
													onblur="lengthBetween('companyName','companyNameTip',2,32,'公司名称长度必须在2到32位以内,请重新填写。')"
													onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/(^\s*)|(\s*$)/g,''))"
													onkeyup="value=value.replace(/(^\s*)|(\s*$)/g,'')" />
											</td>
											<td width="590">
												<div id="companyNameTip"></div>
											</td>
										</tr>

									</table>
								</div>
								<table>
									<tr>
										<td width="130" align="right">
											<span class="require">*</span> 真实姓名：
										</td>
										<td width="220">
											<input class="text_style" type="text"
												value="<c:out value='${ag.name}'/>" name="name" id="name"
												onclick="setText('nameTip','请如实填写您身份证上的姓名。注册后不可更改。')"
												onblur="lengthBetween('name','nameTip',2,32,'真实姓名长度必须在2到32位以内,请重新填写。')"
												onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"
												onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))" />
										</td>
										<td width="590">
											<div id="nameTip"></div>
										</td>
									</tr>
									<tr>
										<td align="right">
											<span class="require">*</span> 证件类型：
										</td>
										<td width="220">
											<select name="certType" id="certType">
												<option value="1">
													身份证
												</option>
											</select>
										</td>
										<td width="590">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td align="right" valign="top">
											<span class="require">*</span> 证件号码：
										</td>
										<td width="220">
											<input class="text_style" type="text"
												value="<c:out value='${ag.certNum}'/>" name="certNum"
												id="certNum"
												onclick="setText('certNumTip','请如实填写您真实的身份证号码。')"
												onblur="checkCard('certNum','certNumTip','请填写您真实的身份证号码。')" />
											<br />
										</td>
										<td width="590">
											<div id="certNumTip"></div>
										</td>
									</tr>
									<tr>
										<td align="right">
											<span class="require">*</span> 手机号码：
										</td>
										<td width="220">
											<input type="text" class="text_style"
												value="<c:out value='${ag.mobile}'/>" name="mobile"
												id="mobile"
												onclick="chkmobile('mobile','mobileTip','请填写正确的手机号码。')"
												onblur="chkmobile('mobile','mobileTip','手机号码必须以13、15或18开头，共11位数字。')" />
										</td>
										<td width="590">
											<div id="mobileTip"></div>
										</td>
									</tr>
									<tr>
										<td align="right">
											<span class="require">*</span> 联系电话：
										</td>
										<td width="220">
											<input type="text" class="text_style"
												value="<c:out value='${ag.telephone}'/>" name="telephone"
												id="telephone"
												onclick="chktelephone('telephone','telephoneTip','请您填写联系电话作为联系方式。')"
												onblur="chktelephone('telephone','telephoneTip','联系电话必须填写座机电话号码或者区号加 - 的座机电话号码。')" />
										</td>
										<td width="590">
											<div id="telephoneTip"></div>
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
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="personInfo">
					<tr>
						<td class="tableLT"></td>
						<td class="tableTT"></td>
						<td class="tableRT"></td>
					</tr>
					<tr>
						<td class="tableLL"></td>
						<td class="container">
							<div class="title">
								<span>请阅读钱门服务协议</span>
							</div>
							<div class="regArticleMsg">
								<p>
									<b>钱门服务协议</b>
									<br />
									“钱门服务”（以下简称本服务）是由钱门网络科技有限公司（以下简称本公司）向钱门用户提供的“钱门”软件系统（以下简称本系统）及(或)附随的货款代收代付的中介服务。本协议由您和本公司签订。
									<br />
									<br />
									<strong>一、声明与承诺</strong>
									<br />
									（一）您确认，在您注册成为钱门用户以接受本服务之前，你已充分阅读、理解并接受本协议的全部内容，一旦您使用本服务，即表示您同意遵循本协议之所有约定。
									<br />
									（二）您同意，本公司有权随时对本协议内容进行单方面的变更，并以在本公司网站公告的方式予以公布，无需另行单独通知您；若您在本协议内容公告变更后继续使用本服务的，表示您已充分阅读、理解并接受修改后的协议内容，也将遵循修改后的协议内容使用本服务；若您不同意修改后的协议内容，您应停止使用本服务。
									<br />
									（三）您声明，在您同意接受本协议并注册成为钱门用户时，您是具有法律规定的完全民事权利能力和民事行为能力，能够独立承担民事责任的自然人、法人或其他组织；本协议内容不受您所属国家或地区的排斥。不具备前述条件的，您应立即终止注册或停止使用本服务。
									<br />
									<br />
									<strong>二、“钱门服务”概要</strong>
									<br />
									（一）“钱门账户”：指在您使用本服务时，本公司向您提供的唯一编号。您可自行设置密码，并用以查询或计量您的预付、应收或应付款。
									<br />
									（二） 钱门中介服务：即本公司向您提供的货款代收代付的中介服务，其中包含
									<br />
									1、 代管：您可以使用本服务指定的方式向您的钱门账户充值，并委托本公司代为保管。
									<br />
									2、 代收：您可以要求本公司代为收取其他钱门用户向您支付的各类款项。
									<br />
									3、
									代付：您可以要求本公司将代管或代收的您的款项支付给您指定的第三方。您同意，本公司代付后，非经法律程序或者非依本协议之约定，该支付是不可逆转的。
									<br />
									4、
									退返（或提现）：您可以要求本公司退返本公司代管的您的款项（即退返）或向您支付您的应收款（即提现）。当您向本公司做出退返（提现）指示时，必须提供一个与您委托本公司代管时的汇款人或您的名称相符的有效的国内银行账户，本公司将于收到指示后的一至五个工作日内，将相应的款项汇入您提供的有效银行账户（根据您提供的银行不同，会产生汇入时间上的差异）。除本条约定外，本公司不提供其他受领方式。
									<br />
									5、
									查询：本公司将对您在本系统中的所有操作进行记录，不论该操作之目的最终是否实现。您可以在本系统中实时查询您的钱门账户名下的交易记录，您认为记录有误的，本公司将向您提供本公司已按照您的指示收付款的记录。您理解并同意您最终收到款项的服务是由您提供的银行账户对应的银行提供的，您需向该银行请求查证。
									<br />
									6、
									款项专属：对您支付到本公司并归属至您钱门账户的款项及您通过钱门账户收到的货款，本公司将予以妥善保管，除本协议另行规定外，不作任何其他非您指示的用途。本公司通过您的用户名和密码识别您的指示，请您妥善保管您的用户名和密码，对于因密码泄露所致的损失，由您自行承担。
									本服务所涉及到的任何款项只以人民币计结，不提供任何形式的汇兑业务。
									<br />
									7、
									交易异常处理：您使用本服务时，可能由于银行本身系统问题、银行相关作业网络连线问题或其他不可抗拒因素，造成本服务无法提供。您确保您所填写的您的资料无误，如果因资料错误造成本公司于上述异常状况发生时，无法及时通知您相关交易后续处理方式的，本公司不承担任何损害赔偿责任。
									<br />
									8、
									您同意，基于运行和交易安全的需要,本公司可以暂时停止提供或者限制本服务部分功能,或提供新的功能,在任何功能减少、增加或者变化时,只要您仍然使用本服务,表示您仍然同意本协议或者变更后的协议。
									<br />
									（三）
									货到付款服务（COD服务）：是指买卖双方使用本系统，且约定买卖合同项下的交易货款由买方在收到交易货物时以现金方式支付到物流公司，物流公司在收到该款项后将交易货款支付给本公司，再由本公司支付至卖方钱门账户的一种支付方式。在您使用本项服务时，除适用钱门中介服务的相关约定外，还将优先适用以下条款：
									<br />
									1、
									本公司为您代收的交易货款系由您的交易对方通过物流公司以现金的方式提供给本公司，通过本公司支付到您的钱门账户内。您理解并同意，在您的交易对方通过物流公司将现金提供本公司的过程需要一定的时间，在物流公司向钱门或品尚告知其已收到您的交易对方支付的交易货款之日起的第三日，本公司将向您支付交易货款。
									<br />
									2、
									本公司为您代付的交易货款系由您收到交易货物后以现金方式提供给物流公司，物流公司转交给本公司后代您充值到您的钱门账户，然后代为支付到您的交易对方的钱门账户内；您向本公司提供的现金是由向您送达交易货物的物流公司代为转交的，物流公司可能因此向您单独收取费用，您理解并同意，该费用是物流公司基于其向您提供的转交服务所收取的，与本公司向您提供的本项服务无关。
									<br />
									3、
									您在选用本项服务作为交易支付方式后，该支付行为能否完成取决于您提供的收货地址是否是您的交易对方所选用的物流公司可以送达的地址，在物流公司确认不可送达时，本公司将提示您重新选择本公司的其他支付方式。
									<br />
									4、
									您在接受本项服务作为交易支付方式后，该支付行为能否完成取决于您选用的物流公司是否可将交易货物送达您的交易对方提供的收货地址，或您的交易对方提供的收货地址准确无误，或货物完全符合您与您的交易对方约定的状态。您理解并接受，您选用的物流公司不揽货、错误送达、货物被您的交易对方拒收等情形与本公司无关，且是本公司所不能预见、预防和控制的，因此所可能产生的所有损失将由您自行完全承担。
									<br />
									（四）
									即时到账服务：是指买卖双方使用本系统，且约定买卖合同项下的货款通过买方钱门账户即时向卖方钱门账户支付的一种支付方式。本公司提示您注意：该项服务一般适用于您与交易对方彼此都有充分信任的小额交易。在您与交易对方同意选择即时到账服务支付货款时，您所支付的款项将立刻进入交易对方的钱门账户。基于可能存在的风险，在使用即时到账服务支付交易货款之前，您理解并接受：
									<br />
									1、
									为控制可能存在的交易风险，本公司对所有用户使用即时到账服务支付交易货款时的每天交易最高限额以及每笔交易最高限额进行了限制。
									<br />
									2、
									使用即时到账服务支付货款是基于您对交易对方的充分信任，一旦您选用该方式，相对应的交易将不受本协议所有交易保护条款的保障，您将自行承担所有交易风险并自行处理所有相关的交易及货款纠纷。
									<br />
									<br />
									<strong>三、钱门账户</strong>
									<br />
									（一） 注册相关
									<br />
									在使用本服务前，您必须先行注册，取得本公司提供给您的“钱门账户”（以下简称该账户），您同意：
									<br />
									1、
									依本服务注册表之提示准确提供并在取得该账户后及时更新您的正确、最新及完整的资料。若有合理理由怀疑您提供的资料错误、不实、过时或不完整的，本公司有权暂停或终止向您提供部分或全部“钱门服务”。本公司对此不承担任何责任，您将承担因此产生的任何直接或间接支出。
									<br />
									2、
									因您未及时更新资料，导致本服务无法提供或提供时发生任何错误，您不得将此作为取消交易、拒绝付款的理由，您将承担因此产生的一切后果，本公司不承担任何责任。
									<br />
									3、
									您应对您的钱门账户负责，只有您本人可以使用您的钱门账户，该账户不可转让、不可赠与、不可继承。在您决定不再使用该账户时，您应将该账户下所对应的可用款项全部提现或者向本公司发出其它支付指令，并向本公司申请注销该账户。您同意，若您丧失全部或部分民事权利能力或民事行为能力，本公司有权根据有效法律文书（包括但不限于生效的法院判决、生效的遗嘱等）处置与您的钱门账户相关的款项。
									<br />
									（二） 账户安全
									<br />
									您将对使用该账户及密码进行的一切操作及言论负完全的责任，您同意：
									<br />
									1、 不向其他任何人泄露该账户及密码，亦不使用其他任何人的“钱门账户”及密码。
									<br />
									2、
									如您发现有他人冒用或盗用您的账户及密码或任何其他未经合法授权之情形时，应立即以有效方式通知本公司，要求本公司暂停相关服务。同时，您理解本公司对您的请求采取行动需要合理期限，在此之前，本公司对已执行的指令及(或)所导致的您的损失不承担任何责任。
									<br />
									<br />
									<strong>四、 “钱门服务”使用规则</strong>
									<br />
									为有效保障您使用本服务进行交易时的合法权益，您理解并同意接受以下规则：
									<br />
									（一）
									一旦您使用本服务，您即授权本公司代理您在您及（或）您指定人符合指定条件或状态时，支付款项给您指定人，或收取您指定人支付给您的款项。
									<br />
									（二）
									本公司通过以下两种方式中接受来自您的指令：其一，您在钱门网站或其他可使用本服务的网站上通过登录该账户密码及或数字证书并依照本服务预设流程所修改或确认的交易状态；其二您通过您注册时作为该账户名称或者与该账户绑定的手机或其他专属于您的通讯工具（以下合称该手机）号码向本系统发送的信息（短信或电话等）回复。无论您通过以上两种方式中的任一种向本公司发出指令，都不可撤回或撤销，且成为本公司代理您支付或收取款项的唯一指令。在您与第三方发生交易纠纷时，您授权本公司自行判断并决定将争议货款的全部或部分支付给交易一方或双方。
									<br />
									（三）
									您在使用本服务过程中，本协议内容、网页上出现的关于交易操作的提示或本公司发送到该手机的信息（短信或电话等）内容是您使用本服务的相关规则，您使用本服务即表示您同意接受本服务的相关规则。您了解并同意本公司单方修改服务的相关规则，而无须征得您的同意，服务规则应以您使用服务时的页面提示（或发送到该手机的短信或电话等）为准，您同意并遵照服务规则是您使用本服务的前提。
									<br />
									（四）
									本公司会以电子邮件（或发送到该手机的短信或电话等）方式通知您交易进展情况以及提示您进行下一步的操作，但本公司不保证您能够收到或者及时收到该邮件（或发送到该手机的短信或电话等），且不对此承担任何后果，因此，在交易过程中您应当及时登录到本公司网站查看和进行交易操作。因您没有及时查看和对交易状态进行修改或确认或未能提交相关申请而导致的任何纠纷或损失，本公司不负任何责任。
									<br />
									（五）
									您如果需要向交易对方交付货物，应根据交易状态页面（该手机接收到的信息）显示的买方地址，委托有合法经营资格的承运人将货物直接运送至对方或其指定收货人，并要求对方或其委托的第三方（该第三方应当提供对方的授权文件并出示对方及第三方的身份证原件）在收货凭证上签字确认，因货物延迟送达或在送达过程中的丢失、损坏，本公司不承担任何责任，应由您与交易对方自行处理。
									<br />
									（六）
									本公司对您所交易的标的物不提供任何形式的鉴定、证明的服务。如果您与交易对方发生交易纠纷，您授权由本公司根据本协议及钱门网站上载明的各项规则进行处理。您为解决纠纷而支出的通讯费、文件复印费、鉴定费等均由您自行承担。因市场因素致使商品涨价跌价而使任何一方得益或者受到损失而产生的纠纷（《争议处理规则》另有约定的除外），本公司不予处理。
									<br />
									（七）
									若您未完成注册过程成为钱门用户，他人通过本服务向您支付款项时，相应款项将无法划汇给您，您授权本公司暂时代为保管该款项，直到您完成该账户的注册和实名认证。
									<br />
									（八）
									本公司会将与您钱门账户相关的资金，独立于本公司营运资金之外，且不会将该资金用于非您指示的用途，但《钱门交易通用规则》约束的交易及本条第（十四）项约定的除外。
									<br />
									（九）
									本公司并非银行或其它金融机构，本服务也非金融业务，本协议项下的资金移转均通过银行来实现，你理解并同意您的资金于流转途中的合理时间。
									<br />
									（十） 您完全承担您使用本服务期间由本公司保管或代收或代付的款项的货币贬值风险及可能的孳息损失。
									<br />
									（十一）在您注册成为钱门用户时，您授权本公司通过银行或向第三者审核您的身份和资格，并通过品尚网或其它网站取得关于您使用本服务的相关资料。
									<br />
									（十二）您使用本服务进行交易或使用该账户登陆其他支持本服务的网站时，您即授权本公司将您的个人信息和交易信息披露给与您交易的另一方或您登陆的网站，该信息包括但不限于：您的真实姓名、联系方式、信用状况、钱门账户。
									<br />
									（十三）您不得将本服务用于非本公司许可的其他用途。
									<br />
									（十四）交易风险
									<br />
									1）当您通过本服务进行各项交易或接受交易款项时，若您或对方未遵从本服务条款或网站说明、交易页面中之操作步骤，则本公司有权拒绝为您与交易对方提供相关服务，且本公司不承担损害赔偿责任。若发生上述状况，而款项已先行划付至您或他人的钱门账户名下，您同意本公司有权直接自相关账户余额中扣回款项及禁止您要求支付此笔款项之权利。此款项若已汇入您的银行账户，您同意本公司有向您事后索回之权利，因您的原因导致本公司事后追索的，您应当承担本公司合理的追索费用。
									<br />
									2）因您的过错导致的任何损失由您自行承担，该过错包括但不限于：不按照交易提示操作，未及时进行交易操作，遗忘或泄漏密码，密码被他人破解，您使用的计算机被他人侵入。
									<br />
									（十五）服务费用
									<br />
									1）在您使用本服务时，本公司有权向您收取相关服务费用。本公司拥有制订及调整服务费之权利，具体服务费用以您使用本服务时钱门网站上所列之收费方式公告（在没有公告之前本服务免费）或您与本公司达成的其他书面协议为准。
									<br />
									2）除非另有说明或约定，您同意本公司有权自您委托本公司代管、代收或代付的款项中直接扣除上述服务费用。
									<br />
									（十六）积分
									<br />
									1）就您使用本服务，本公司将通过多种方式向您授予积分，积分不具有现金价值，无论您通过何种方式获得积分，您都不得使用积分换取任何现金或金钱。
									<br />
									2)积分不具备任何财产性质，并非您拥有所有权的财产，本公司有权单方面调整积分数值或调整本公司的积分规则，而无须征得您的同意。
									<br />
									3)您仅有权按本公司的积分规则，将所获积分交换本公司提供的指定的服务或产品。
									<br />
									4)如本公司怀疑您的积分的获得及(或)使用存有欺诈、滥用或其它本公司认为不当的行为，本公司可随时取消、限制或终止您的积分或积分使用。
									<br />
									<br />
									<strong>五、“钱门服务”使用限制</strong>
									<br />
									（一）
									您在使用本服务时应遵守中华人民共和国相关法律法规、您所在国家或地区之法令及相关国际惯例，不将本服务用于任何非法目的（包括用于禁止或限制交易物品的交易），也不以任何非法方式使用本服务。
									<br />
									（二）
									您不得利用本服务从事侵害他人合法权益之行为，否则应承担所有相关法律责任，因此导致本公司或本公司雇员受损的，您应承担赔偿责任。上述行为包括但不限于：
									<br />
									1、侵害他人名誉权、隐私权、商业秘密、商标权、著作权、专利权等合法权益。
									<br />
									2、违反依法定或约定之保密义务。
									<br />
									3、冒用他人名义使用本服务。
									<br />
									4、从事不法交易行为，如洗钱、贩卖枪支、毒品、禁药、盗版软件、黄色淫秽物品、其他本公司认为不得使用本服务进行交易的物品等。
									<br />
									5、提供赌博资讯或以任何方式引诱他人参与赌博。
									<br />
									6、非法使用他人银行账户（包括信用卡账户）或无效银行账号（包括信用卡账户）交易。
									<br />
									7、违反《银行卡业务管理办法》使用银行卡，或利用信用卡套取现金（以下简称套现）。
									<br />
									8、进行与您或交易对方宣称的交易内容不符的交易，或不真实的交易。
									<br />
									9、从事任何可能含有电脑病毒或是可能侵害本服务系统、资料之行为。
									<br />
									10、其他本公司有正当理由认为不适当之行为。
									<br />
									（三）
									您理解并同意，本公司不对因下述任一情况导致的任何损害赔偿承担责任，包括但不限于利润、商誉、使用、数据等方面的损失或其他无形损失的损害赔偿
									(无论本公司是否已被告知该等损害赔偿的可能性)：
									<br />
									1、
									本公司有权基于单方判断，包含但不限于本公司认为您已经违反本协议的明文规定及精神，暂停、中断或终止向您提供本服务或其任何部分，并移除您的资料。
									<br />
									2、
									本公司在发现异常交易或有疑义或有违反法律规定或本协议约定之虞时，有权不经通知先行暂停或终止该账户的使用（包括但不限于对该账户名下的款项和在途交易采取取消交易、调账等限制措施），并拒绝您使用本服务之部分或全部功能。
									<br />
									3、
									在必要时，本公司无需事先通知即可终止提供本服务，并暂停、关闭或删除该账户及您账号中所有相关资料及档案，并将您滞留在该账户的全部合法资金退回到您的银行账户。
									<br />
									（四）
									如您需要注销您的钱门账户，应先经本公司审核同意。本公司注销该账户，即表明本公司与您之间的协议已解除，但您仍应对您使用本服务期间的行为承担可能的违约或损害赔偿责任。
									<br />
									<br />
									<strong>六、隐私权保护</strong>
									<br />
									一旦您同意本协议或使用本服务，您即同意本公司按照以下条款来使用和披露您的个人信息。
									<br />
									（一） 未成年人的特别注意事项
									<br />
									在阅读本协议以注册钱门用户时，您尚未满18周岁的，您不能使用本服务，请您终止您的注册行为，不要向我们提供您的任何个人信息。
									<br />
									（二） 用户名和密码
									<br />
									在您注册为钱门用户时，我们会要求您设置用户名和密码来识别您的身份，并设置密码提示问题及其答案，以便在您丢失密码时用以确认您的身份。您仅可通过您设置的密码来使用该账户，如果您泄漏了密码，您可能会丢失您的个人识别信息，并可能导致对您不利的法律后果。该账户和密码因任何原因受到潜在或现实危险时，您应该立即和本公司取得联系，在本公司采取行动前，本公司对此不负任何责任。
									<br />
									（三） 注册信息
									<br />
									您注册该账户时应向本公司提供您的真实姓名、地址、国籍、电话号码和电子邮件地址，您还可以选择来填写相关附加信息（包括但不限于您公司所在的省份和城市、时区和邮政编码、传真号码、个人主页和您的职务）。为有针对性地向您提供新的服务和机会，您了解并同意本公司或您登录的其他网站将通过您的电子邮件地址或该手机通知您这些信息。
									<br />
									（四） 银行账户信息
									<br />
									本公司所提供的服务将需要您提供您的银行账户信息，在您提供相应信息后，本公司将严格履行相关保密约定。
									<br />
									（五） 交易行为
									<br />
									为了保障您使用本服务的安全以及不断改进服务质量，本公司将记录并保存您登录和使用本服务的相关信息，但本公司承诺不将此类信息提供给任何第三方（除双方另有约定或法律法规另有规定外）。
									<br />
									（六） 广告
									<br />
									本公司会对钱门用户的身份数据进行综合统计，并出于销售和奖励的需要使用或披露。
									<br />
									（七） Cookie的使用
									<br />
									您了解并同意，本公司使用cookie来使钱门网站对用户更友好，它可以帮您省去为使用我们的服务而重复填写注册信息和跟踪您的浏览器的状态。
									<br />
									（八） 第三方
									<br />
									钱门网站将公布钱门用户提供的商业机会，其他钱门用户可以获知这些商业机会。除了本公司和您使用该账户成功登录过的其他网站，本公司不会向任何第三方提供、出售、出租、分享您的个人信息。除了按法定或约定情况下披露您的信息，本公司将恪尽善意管理人的义务。
									<br />
									（九） 信息的存储和交换
									<br />
									您的信息和资料存储在位于中国的服务器上，为了备份的需要，本公司可能将您的信息和资料传送到位于别国的服务器上。
									<br />
									（十） 外部链接
									<br />
									钱门网站含有到其他网站的链接，但本公司对其他网站的隐私保护措施不负任何责任。本公司可能在任何需要的时候增加商业伙伴或共用品牌的网站。
									<br />
									（十一）安全
									<br />
									本公司仅按现有技术提供相应的安全措施来使本公司掌握的信息不丢失，不被滥用和变造。这些安全措施包括向其它服务器备份数据和对用户密码加密。尽管有这些安全措施，但本公司不保证这些信息的绝对安全。
									<br />
									<br />
									<strong>七、 系统中断或故障</strong>
									<br />
									系统因下列状况无法正常运作，使您无法使用各项服务时，本公司不承担损害赔偿责任，该状况包括但不限于：
									<br />
									（一） 本公司在钱门网站公告之系统停机维护期间。
									<br />
									（二） 电信设备出现故障不能进行数据传输的。
									<br />
									（三） 因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成本公司系统障碍不能执行业务的。
									<br />
									（四） 由于黑客攻击、电信部门技术调整或故障、网站升级、银行方面的问题等原因而造成的服务中断或者延迟。
									<br />
									<br />
									<strong>八、 责任范围及责任限制</strong>
									<br />
									（一） 本公司仅对本协议中列明的责任承担范围负责。
									<br />
									（二） 您明确因交易所产生的任何风险应由您与交易对方承担。
									<br />
									（三） 钱门用户信息是由用户本人自行提供的，本公司无法保证该信息之准确、及时和完整，您应对您的判断承担全部责任。
									<br />
									（四） 本公司不对交易标的及本服务提供任何形式的保证，包括但不限于以下事项：
									<br />
									1、本服务符合您的需求。
									<br />
									2、本服务不受干扰、及时提供或免于出错。
									<br />
									3、您经由本服务购买或取得之任何产品、服务、资讯或其他资料符合您的期望。
									<br />
									（五） 本服务之合作单位，所提供之服务品质及内容由该合作单位自行负责。
									<br />
									（六）
									您经由本服务之使用下载或取得任何资料，应由您自行考量且自负风险，因资料之下载而导致您电脑系统之任何损坏或资料流失，您应负完全责任。
									<br />
									（七） 您自本公司及本公司工作人员或经由本服务取得之建议和资讯，无论其为书面或口头形式，均不构成本公司对本服务之保证。
									<br />
									（八）
									在法律允许的情况下，本公司对于与本协议有关或由本协议引起的任何间接的、惩罚性的、特殊的、派生的损失（包括业务损失、收益损失、利润损失、使用数据或其他经济利益的损失），不论是如何产生的，也不论是由对本协议的违约（包括违反保证）还是由侵权造成的，均不负有任何责任，即使事先已被告知此等损失的可能性。另外即使本协议规定的排他性救济没有达到其基本目的，也应排除本公司对上述损失的责任。
									<br />
									（九） 除本协议另有规定外，在任何情况下，本公司对本协议所承担的违约赔偿责任总额不超过向您收取的当次服务费用总额。
									<br />
									<br />
									<strong>九、 完整协议</strong>
									<br />
									本协议由本协议条款与《争议处理规则》、《钱门交易通用规则》、《钱门认证服务协议》、《超时规则》、《安全措施》、《反欺诈措施》等钱门网站公示的各项规则组成，相关名词可互相引用参照，如有不同理解，以本协议条款为准。
									你对本协议理解和认同，您即对本协议所有组成部分的内容理解并认同，一旦您使用本服务，你和本公司即受本协议所有组成部分的约束。
									<br />
									本协议部分内容被有管辖权的法院认定为违法的，不因此影响其他内容的效力。
									<br />
									<br />
									<strong>十、商标、知识产权的保护</strong>
									<br />
									（一）
									钱门网站上所有内容，包括但不限于著作、图片、档案、资讯、资料、网站架构、网站画面的安排、网页设计，均由本公司或本公司关联企业依法拥有其知识产权，包括但不限于商标权、专利权、著作权、商业秘密等。
									<br />
									（二）
									非经本公司或本公司关联企业书面同意，任何人不得擅自使用、修改、复制、公开传播、改变、散布、发行或公开发表本网站程序或内容。
									<br />
									（三） 尊重知识产权是您应尽的义务，如有违反，您应承担损害赔偿责任。
									<br />
									<br />
									<strong>十一、法律适用与管辖</strong>
									<br />
									本协议之效力、解释、变更、执行与争议解决均适用中华人民共和国法律，没有相关法律规定的，参照通用国际商业惯例和（或）行业惯例。
									<br />
									因本协议产生之争议，均应依照中华人民共和国法律予以处理。
									<br />
									<!-- 添加收费规则20090112 -->
									<br />
									<strong>附件：</strong>
									<br />
									<br />
									<b>钱门服务收费规则</b>
									<br />
									本规则构成《钱门服务协议》的有效组成部分，具有同等法律效力；本规则有特别约定的，以本规则的约定为准；本规则未约定的内容，以《钱门服务协议》的约定为准。
									<br />
									本规则所指“本公司”，系指钱门网络科技有限公司。
									<br />
									<br />
									<strong>一、收费对象</strong>
									<br />
									本公司针对在钱门网站内，通过钱门软件系统的“我要收款”功能向其他钱门用户收取款项，和/或通过“我要付款”功能向其他钱门用户支付款项的钱门用户（上述服务在本协议中合称为“收费服务”），按照钱门账户类型和
									“交易流量”，向其收取一定比例的服务费用。
									<br />
									<br />
									<strong>二、收费细则</strong>
									<br />
									1、本规则所指“我要收款”功能，系钱门用户在钱门网站登录其钱门账户后，通过点击“我要收款”按钮，生成收款交易订单，且在交易对方（限钱门用户）确认付款后完成收款的功能。
									<br />
									2、本规则所指“我要付款”功能，系钱门用户在钱门网站登录其钱门账户后，通过点击“我要付款”按钮，
									且在“即时到账付款”“担保交易付款”中选择的任一付款方式，生成付款交易订单，且点击确认付款后完成支付的功能。
									<br />
									3、本规则所指“钱门账户类型”，系依照《钱门认证服务协议》已通过“钱门认证”的认证账户和未通过“钱门认证”的非认证账户。持有认证账户的钱门用户称为认证用户，持有非认证账户的钱门用户称为非认证用户。本公司将根据不同的账户类型按本条第5和第6项对钱门用户收取服务费用。
									<br />
									4、本协议所指完成的“交易流量”，系指在一定期间内使用“收费服务”而在不同钱门账户间完成流转的款项数额，如订单因任何原因未能完成款项流转的，则不计入
									“交易流量”。已完成流转的款项因任何原因退回的，本公司已收取的服务费用不退回。
									<br />
									5、对于非认证账户，该账户完成的“交易流量”在人民币500（伍佰）元以内（含本数，下同）的，无须向本公司支付服务费用；完成的“交易流量”累计超过人民币500（伍佰）元(不含本数）的，须按超出金额的1.5％向本公司支付费用，且涉及的每笔交易须支付的服务费用最低为人民币一元，最高为人民币一百元。自非认证账户完成认证当日始，适用本条第6项的规则。在未完成认证前已经支付的服务费用，在完成认证后不予退还。
									<br />
									6、对于认证账户，同一认证用户持有的全部认证账户于一个自然月内累计完成的“交易流量”在人民币5000（伍仟）元以内（含本数）的，无须向本公司支付服务费用；完成的“交易流量”累计超过人民币5000（伍仟）元（不含本数）的部分，须按超出部分金额的1.5％向本公司支付服务费用，且涉及的每笔交易须支付的服务费用最低为人民币一元，最高为人民币一百元。
									<br />
									7、非认证账户累计人民币500（伍佰）元以内的“交易流量”和同一认证用户持有的全部认证账户在一个自然月内累计人民币5000（伍仟）元以内的“交易流量”统称为“免费交易流量”，超出“免费交易流量”的“交易流量”统称为“收费交易流量”。
									<br />
									8、通过“我要收款”功能产生“收费交易流量”的，本公司将向收款方收取服务费，并直接自产生该流量的交易款项中扣减服务费，并将余款付至收款方的钱门账户；通过“我要付款”功能产生“收费交易流量”的，本公司将自付款方的钱门账户余额内扣取服务费用，付款方钱门账户余额不足支付服务费用的，则产生“收费交易流量”的付款交易订单视为无效订单。
									<br />
									<br />
									<strong>三、收费提示</strong>
									<br />
									1、钱门用户可通过本公司提供的功能查询其钱门账户的认证状态、“计费期间”、“免费交易流量”、单个“计费期间”的实际“交易流量”、已收取的服务费用等信息。相关信息的保存期限为一年。
									<br />
									2、在钱门用户使用“收费服务”创建订单时，本公司不对该订单收费应收取的服务费进行相应提示，钱门用户应自行据本条规则计算该订单应予扣减的服务费用。
									<br />
									3、钱门系统里订单对应的交易记录会显示服务费用的明细信息，该等明细信息是构成并核对本条第1项规则的唯一有效依据。如钱门用户对钱门所收取的服务费金额有任何异议的，应在收费发生后三日内向本公司提出异议，否则应视为无异议。
									<br />
									<br />
									<strong>四、本规则自2009年7月8日零时起施行，且本公司有权对本规则的内容进行单方面的变更，并以在钱门网站公告的方式予以公布，但本公司没有义务再行单独对每个钱门用户进行通知。本规则内容变更后，只要钱门用户继续使用“收费服务”的，即视为其同意接受变更后的规则内容的约束。</strong>
									<br />
								</p>
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

				<div class="agree">
					<span class="require">*</span> 出于安全考虑，请输入下面左侧显示的字符
					<img src="<%=request.getContextPath()%>/agent/createImage.jsp"
						border="0"
						onclick="this.src='<%=request.getContextPath()%>/agent/createImage.jsp'"
						style="cursor: pointer" />
					<input id="rand" name="rand" class="text_style" type="text"
						onclick="setText('checkcodetip','请输入左侧数字。')"
						onblur="setText('checkcodetip','')" rule="notnull" />
					<span id="checkcodetip"></span>
					<p class="agree yellowBox" style="width: 400px;">
						确认注册前请再次检查您的信息输入，确保输入框后都是“√”图标
					</p>
					<input type="button" class="submit" value="同意以上条款，并确认注册"
						onclick="toSubmit();" />
				</div>

				<c:import url="../_jsp/footer.jsp"></c:import>
			</div>
		</html:form>

	</body>
</html>

<script language="javascript" type="text/javascript">
var isEmail=false;
function toSubmit(){
	var formid = document.forms[0];
	var regm = /^([a-z0-9A-Z]+(-|_)*[\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-|_)*([a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/;
	var isCard=/^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/;
	var regmTelephone=/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/;
	  
	if(!document.getElementById("loginName").value.match(regm)){
		alert("账号输入错误！");
		formid.loginName.select();
		return false;
	}else if(document.getElementById("loginName").value!=document.getElementById("reLoginName").value){
		alert("确认账号与账号不相同！");
		formid.reLoginName.select();
		return false;
	}else if(document.getElementById("password").value.length<6){
		alert("登录密码位数必须是6位以上！");
		formid.password.select();
		return false;
	}else if(document.getElementById("password").value!=document.getElementById("repassword").value){
		alert("确认登录密码与登录密码不相同！");
		formid.repassword.select();
		return false;
	}else if(document.getElementById("payPassword").value.length<6){
		alert("支付密码位数必须是6位以上！");
		formid.payPassword.select();
		return false;
	}else if(document.getElementById("payPassword").value==document.getElementById("password").value){
		alert("支付密码不能和登录密码相同！");
		formid.payPassword.select();
		return false;
	}else if(document.getElementById("payPassword").value!=document.getElementById("repayPassword").value){
		alert("确认支付密码与支付密码不相同！");
		formid.repayPassword.select();
		return false;
	}else if(document.getElementById("name").value==""){
		alert("姓名不能为空！");
		formid.name.select();
		return false;
	}else	if(document.getElementById("TABLE2").style.display == "block"&&document.getElementById("companyName").value==""){		
			alert("企业名称不能为空！");
			formid.companyName.select();
			return false;		
	}else if (checkStatus(document.getElementById("certNum").value)==false){  
		alert("证件号码输入错误！");
		formid.certNum.select();
		return false;
	}else if(((/^13\d{9}$/g.test(document.getElementById("mobile").value))||(/^15\d{9}$/g.test(document.getElementById("mobile").value))||(/^18\d{9}$/g.test(document.getElementById("mobile").value)))==false){
		alert("手机号码输入错误！");
		formid.mobile.select();
		return false;
	}else if(regmTelephone.test(document.getElementById("telephone").value)==false){
		alert("电话号码输入错误！");
		formid.telephone.select();
		return false;
	}else if(document.getElementById("rand").value==""){
		alert("验证码不能为空！");
		formid.rand.select();
		return false;
	}else{
		formid.submit();
	}
		

}
//验证身份证
function checkStatus(_strIdNum)
{
	var isCard=/^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/;
	
	if (isCard.test(_strIdNum)||(/(\d)(?=(\d\d\d)+(?!\d))/g.test(_strIdNum)&&_strIdNum.length==15))
	{  
		return  true;
	}
	return false;
}  

//验证登陆名是否重复
function checkLogin()
{
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	var loginNameId=document.getElementById("loginName");
	var loginMessage=document.getElementById("loginMessage");
	var loginNameValue = loginNameId.value;
	if(chkemail('loginName',"loginMessage",""))
	{
	    //进行ajax请求,(使用的是property)
		var myAjax = new Ajax.Request("<%=request.getContextPath()%>/agent/agent.do?thisAction=checkExist&loginName="+loginNameValue,{thisAction:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			
			if(result==0){
					loginMessage.className="errors";
					if(IE_FIREFOX>-1)
						loginMessage.innerHTML="email地址已经被注册过了,请使用其它email地址注册";
					else
						loginMessage.textContent="email地址已经被注册过了,请使用其它email地址注册";
				}
			else if(result==1){
					if(IE_FIREFOX>-1)
						loginMessage.innerHTML="恭喜,您的账户名未被注册,请继续。";
					else
						loginMessage.textContent="恭喜,您的账户名未被注册,请继续。";
					loginMessage.className="sucess";
				}
		}, onException:showException});
	}
}
function showException(originalRequest, ex) {
	alert("Exception:" + ex.message);
	}
</script>
