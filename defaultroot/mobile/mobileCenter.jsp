<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- mobileCenter.jsp -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

		<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	</head>


	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />
			<div id="main">

				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid">
							<div class="mobilePhone_greenBox">
								<c:if test="${agent.mobileBindStatus==0}">
									<div class="greenBox_text">
										<html:link page="/agent/agent.do?thisAction=viewMobileBinding">申请绑定手机</html:link>
									</div>
								</c:if>
								<c:if test="${agent.mobileBindStatus!=0}">
									<div class="greenBox_title">
										您绑定的手机号码：
										<c:out value="${agent.mobilePhone}"></c:out>
									</div>
									<div class="greenBox_text">
										<span>您的手机状态：已绑定手机；</span>您可以
										<a href="../agent/agent.do?thisAction=unbindingMobile">申请解除手机绑定(所有手机服务将被取消)</a> 绑定手机后，您就可以享受以下方便使用的服务，
										<a href="#">并在充值时获得更多积分奖励！</a>
									</div>
								</c:if>
							</div>
							<div class="mobilePhone_tiele">
								<span>手机基础服务（必选）</span>
								<p style="color: #999; margin: 12px 0 4px 0; padding: 0">
									为了您的账户更加安全，钱门免费为您绑定以下的服务
								</p>
							</div>
							<table cellpadding="0" cellspacing="0" width="100%"
								class="deal_table" style="margin-top: 12px;">
								<tr>
									<th>
										<div>
											服务类型
										</div>
									</th>
									<th>
										<div>
											描述
										</div>
									</th>
									<th>
										<div>
											状态
										</div>
									</th>
								</tr>
								<tr>
									<td>
										手机找回密码
									</td>
									<td>
										利用手机来轻松找回遗忘的密码
									</td>
									<td>
										<c:out value="${agent.mobileBindStatusCaption}"></c:out>
									</td>
								</tr>
								<tr>
									<td>
										手机
										<a href="../agent/agent.do?thisAction=viewPayBalance"
											style="color: #005B9B">开启/关闭余额支付</a>
									</td>
									<td>
										您可以用手机开启或关闭账户余额支付功能
									</td>
									<td>
										<c:out value="${agent.canPayCaption}"></c:out>
									</td>
								</tr>
								<tr>
									<td>
										手机管理数字证书
									</td>
									<td>
										如果您的数字证书丢失了，您可以用手机注销数字证书
									</td>
									<td>
										<c:out value="${agent.mobileBindStatusCaption}"></c:out>
									</td>
								</tr>
							</table>
							<div class="mobilePhone_tiele">
								<span>手机自助服务（免费）</span>
								<p style="color: #999; margin: 12px 0 4px 0; padding: 0">
									您可以使用手机来完成一些钱门的产品功能的设置，方便又快捷
								</p>
							</div>
							<table cellpadding="0" cellspacing="0" width="100%"
								class="deal_table" style="margin-top: 12px;">
								<tr>
									<th>
										<div>
											服务类型
										</div>
									</th>
									<th>
										<div>
											描述
										</div>
									</th>
									<th>
										<div>
											状态
										</div>
									</th>
									<th>
										<div>
											操作
										</div>
									</th>
								</tr>
								<tr>
									<td>
										手机设置安全问题
									</td>
									<td>
										如果您设置了安全问题，您可以通过手机重新设置
									</td>
									<td>
										<c:out value="${agent.mobileQuestionStatusCaption}"></c:out>
									</td>
									<c:if test="${agent.mobileQuestionStatusCaption eq '开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobileQuestion"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">关闭</a>
										</td>
									</c:if>
									<c:if test="${agent.mobileQuestionStatusCaption eq '未开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobileQuestion"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">申请开通</a>
										</td>
									</c:if>
								</tr>
								<tr>
									<td>
										手机支付
									</td>
									<td>
										开启手机支付功能，您将可以通过短信或语音方式操作钱门账户余额进行付款
									</td>
									<td>
										<c:out value="${agent.mobilePayStatusCaption}"></c:out>
									</td>
									<c:if test="${agent.mobilePayStatusCaption eq '开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobilePay"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">关闭</a>
										</td>
									</c:if>
									<c:if test="${agent.mobilePayStatusCaption eq '未开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobilePay"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">申请开通</a>
										</td>
									</c:if>
								</tr>
								<tr>
									<td>
										手机动态口令
									</td>
									<td>
										有了密码和手机动态口令的双重保护，您的账户安全就得到了全面的提升
									</td>
									<td>
										<c:out value="${agent.mobilePasswordStatusCaption}"></c:out>
									</td>
									<c:if test="${agent.mobilePasswordStatusCaption eq '开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=closeMobilePassword"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">关闭</a>
										</td>
									</c:if>
									<c:if test="${agent.mobilePasswordStatusCaption eq '未开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobilePassword"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">申请开通</a>
										</td>
									</c:if>
								</tr>
								<tr>
									<td>
										手机号码登录
									</td>
									<td>
										如果您用手机号码做钱门账户名，您可以在此修改
									</td>
									<td>
										<c:out value="${agent.mobileLoginStatusCaption}"></c:out>
									</td>
									<c:if test="${agent.mobileLoginStatusCaption eq '开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobileLogin"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">关闭</a>
										</td>
									</c:if>
									<c:if test="${agent.mobileLoginStatusCaption eq '未开通'}">
										<td>
											<a href="../agent/agent.do?thisAction=startupMobileLogin"
												style="color: #005B9B; font-size: 14px; font-weight: bold;">申请开通</a>
										</td>
									</c:if>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<c:import url="../_jsp/footer.jsp"></c:import>
			</div>
	</body>
</html>
