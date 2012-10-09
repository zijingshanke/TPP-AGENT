<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld " prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: transaction/waitTansaction -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>

		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css2/my_account.css" type="text/css" />

		<script src="../_js/common.js"></script>
		<script type="text/javascript">

function sendToBank(form)
{
 document.getElementById("submitbutton").disabled=true;
 var amount="<c:out value="${charge.amount}" />";
 var type="<c:out value="${charge.type}" />";
 var version="<c:out value="${charge.version}" />";
 var agentId="<c:out value="${agent.id}" />";
 var transactionNo="<c:out value="${charge.transactionNo}" />";
 var bank="<c:out value="${charge.bank}" />";
 var remark="<c:out value="${charge.remark}" />";
 var url="thisAction=sendToBank&amount="+amount+"&type="+type+"&version="+version+"&agentId="+agentId+"&bank="+bank+"&remark="+remark+"&transactionNo="+transactionNo;
 //var sign_url=MD5(url);
 
 if(bank=='ICBC'){
 	var result=openModalDialog(392,188,"../cooperate/gatewayPopNotice.htm") 
  	//alert("--result--"+result)
  	if(result>0){  	
	  	window.open("../transaction/charge.do?"+url,"myNewWin");
	}
  }else{
 	window.open("../transaction/charge.do?"+url,"myNewWin");
  }
}
</script>
	</head>
	<body>
		<html:form action="/transaction/charge.do">
			<html:hidden property="amount" value="${charge.amount}"></html:hidden>
			<html:hidden property="agentId" value="${agent.id}"></html:hidden>
			<html:hidden property="type" value="${charge.type}"></html:hidden>
			<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3" />
				<div class="main_top">
					<div class="main_bottom">
						<div class="main_mid" style="padding: 5px 8px;">
							<div class="main_title">
								<div class="main_title_right">
									<p>
										<strong>网银付款确认</strong>
									</p>
								</div>
							</div>

							<div class="paymentSuccessful" style="height: auto;">
								<div class="paymentSuccessfulTitle" style="height: auto;">
									<table>
										<tr>
											<td align="right">
												您的姓名：
											</td>
											<td>
												<font color="red"> <c:out value="${agent.name}"></c:out>
												</font>
											</td>
										</tr>
										<tr>
											<td align="right">
												您的账户：
											</td>
											<td>
												<font color="red"><c:out value="${agent.loginName}"></c:out>
												</font>
											</td>
										</tr>
										<tr>
											<td align="right">
												充值银行：
											</td>
											<td>
												<c:if test="${charge.version eq 'CCBB2C'}">
													<img src="../_img/bank/bankLogo_3.gif" />（个人版）
								</c:if>
												<c:if test="${charge.version eq 'CCBB2B'}">
													<img src="../_img/bank/bankLogo_3.gif" />（企业版）
								</c:if>
												<c:if test="${charge.version eq 'ICBCB2C'}">
													<img src="../_img/bank/bankLogo_1.gif" />（个人版）
								</c:if>
												<c:if test="${charge.version eq 'ICBCB2B'}">
													<img src="../_img/bank/bankLogo_1.gif" />（企业版）
								</c:if>
												<c:if test="${charge.version eq 'BCMB2C'}">
													<img src="../_img/bank/bankLogo_10.gif" />（个人版）
								</c:if>
												<c:if test="${charge.version eq 'BCMB2B'}">
													<img src="../_img/bank/bankLogo_10.gif" />（企业版）
								</c:if>
												<c:if test="${charge.version eq 'CMBCB2C'}">
													<img src="../_img/bank/bankLogo_9.gif" />（个人版）
								</c:if>
												<c:if test="${charge.version eq 'CMBCB2B'}">
													<img src="../_img/bank/bankLogo_9.gif" />（企业版）
								</c:if>
												<c:if test="${charge.version eq 'CMBCChinaPay'}">
													<img src="../_img/bank/chinapay.gif" />（中国银联支付通道）
															</c:if>
												<c:if test="${charge.version eq 'ABCB2C'}">
													<img src="../_img/bank/bankLogo_4.gif" />（个人版）
								</c:if>
												<c:if test="${charge.version eq 'ABCB2B'}">
													<img src="../_img/bank/bankLogo_4.gif" />（企业版）
								</c:if>
												<c:if test="${charge.version eq 'CITICB2C'}">
													<img src="../_img/bank/bankLogo_11.gif" />（个人版）
								</c:if>
												</font>
											</td>
										</tr>
										<tr>
											<td align="right">
												&nbsp;
											</td>
											<td class="yellowBox" style="padding-left: 30px;">
												为了确保您在支付完成后能够看到银行的支付信息请您在支付的时候务必要关掉诸如3721、google、IE、等拦截弹出窗口软件
											</td>
										</tr>
										<tr>
											<td align="right">
												充值金额：
											</td>
											<td>
												<font color="red"><fmt:formatNumber
														value="${charge.amount}" pattern="0.00" /> </font>元
											</td>

										</tr>

									</table>
									<input class="btn1" id="submitbutton" type="button" name="sub"
										value="确 认" onclick="sendToBank(this);" />
									<input class="btn1" type="button" value="上一步"
										onclick="history.go(-1);" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:import url="/_jsp/footer.jsp" />
			</div>
			</div>
		</html:form>
	</body>
</html>

