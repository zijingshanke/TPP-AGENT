<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- transactionRedPacketgeView.jsp -->
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
	<body onload="checkPaypasswor()">
		<div id="warp">
			<c:import url="../selfHelp/head.jsp"></c:import>
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>收到的红包</strong>
								</p>
							</div>
						</div>

						<html:form action="/transaction/transaction.do?thisAction=transactionGatherRedPacketByEmail2">
						<input type="hidden" name="tid" value="<c:out value="${tid}"></c:out>"/>
						<input type="hidden" name="sign" value="<c:out value="${sign}"></c:out>"/>
							<div>
								<table cellpadding="0" cellspacing="0" width="680"
									class="deal_table" style="margin-top: 12px; float: left">
									<tr>
										<th style="text-align: center">
											<div>
												类型
											</div>
										</th>
										<th>
											<div>
												商品或服务名称 （交易号：
												<c:out value="${transactionRedPacket.orderDetails.orderDetailsNo}" />
												）
												<a
													href="javascript:userMark('<c:out value="${transactionRedPacket.id}" />','<c:out value="${transactionRedPacket.orderDetails.id}" />')"><img
														src="../_img/beizhu.gif" /> </a>
											</div>
										</th>
										<th>
											<div>
												红包面额 (元)
											</div>
										</th>
									</tr>

									<tr>
										<td align="center">
											<c:out value="${transactionRedPacket.typeCaption}" />
										</td>
										<td>
											<c:if test="${transactionRedPacket.orderDetails.shopUrl==null}">
												<c:out value="${transactionRedPacket.orderDetails.shopName}" />
											</c:if>
											<c:if test="${transactionRedPacket.orderDetails.shopUrl!=null}">
												<a
													href="<c:out value="${transactionRedPacket.orderDetails.shopUrl}"/>"><c:out
														value="${transactionRedPacket.orderDetails.shopName}" /> </a>
											</c:if>
										</td>
										<td>
											<fmt:formatNumber pattern="0.00"
												value="${transactionRedPacket.amount}" />
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<strong>祝福语：</strong>
											<c:out value="${transactionRedPacket.orderDetails.detailsContent}" />
										</td>
									</tr>
								</table>
							</div>
							<div>
								<table cellpadding="0" cellspacing="0" width="100%"
									class="information_table">
									<tr>

									</tr>
									<tr>
										<td class="right_td">
											您的支付密码：
										</td>
										<td>
											<input type="password" name="pay_password" value="" id="pay_password"/>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td valign="top" class="Request">
											请输入正确的支付密码,以便领取红包。
										</td>
									</tr>
									<tr>
										<td colspan="2" style="padding-left: 140px">
											<span class="simplyBtn_1 simplyBtn_11">
											<input type="submit" class="buttom_middle"
													value="&nbsp;&nbsp;&nbsp;领取"/> </span>
										</td>
									</tr>
								</table>
							</div>
						</html:form>
						</form>
					</div>
				</div>
			</div>
			<div id="footer">
				CopyRight 2005-2008, fdays.com .All Rights Reserved 增值电信业务经营许可证
				粤B2-20040561
			</div>
		</div>
		<script>
		function checkPaypasswor(){
			var msgPayPassword="<c:out value="${msgPayPassword}"/>";
			if(msgPayPassword!=""){
				alert(msgPayPassword);
				return false;
			}
		}
	
//	 function checkPaypasswor(){
//		var currentAgentPayPwd="<c:out value="${transactionRedPacket.toAgent.payPassword}"/>";
//		alert(currentAgentPayPwd);
//		if(document.getElementById('pay_password').value!=currentAgentPayPwd){
//			alert("支付密码不正确,请查证!");
//			return false;
//		}
//		document.forms[0].submit();
//	}
	
		</script>
	</body>
	
</html>
