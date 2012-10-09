<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<!--JSP: transaction/transactionPaymentI.jsp -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/global_v2.css" type="text/css" />	

		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/prototype.js"></script>
		<script language="javascript">
		function chooseOne(cb){   
         var obj = document.getElementsByName("ch");
         for (i=0; i<obj.length; i++){   
             if (obj[i]!=cb){
             	obj[i].checked = false;
             }
             else{
             	obj[i].checked = true;
             }
         }
     }
     
     function sendToBank(){
			if(setBankVersionValue()){
				document.forms[0].submit();
			}				
	}
	</script>
	</head>
	<body>
		<div id="warp">
			<div id="head">
				<span style="float: right; padding-top: 40px;">

					<div class="quickLink">
						客服热线：
						<font color="red" size="4px;">0756-8801800</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，请
						<a
							href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister">注册</a>
						或
						<a href="<%=request.getContextPath()%>/login.jsp">登录</a>
					</div> </span>
				<a href="<%=request.getContextPath()%>/index.jsp"
					style="border: none; margin: 0; padding: 0;"><img
						src="<%=request.getContextPath()%>/_img/logo.jpg" /> </a>
			</div>
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="deal_list" style="position: relative;">
							<div class="deal_list_head">
								<a id="Show_11" onclick="Show_Menu(1,1,1);"
									href="javascript:void(0)">网上银行付款</a>
							</div>
							<div id="DIV0">
								<html:form
									action="/transaction/charge.do?thisAction=waitChargeI"
									method="post">
									<html:hidden property="agentId" name="agentId"
										value="${transaction.fromAgent.id}" />
									<html:hidden property="amount" name="amount"
										value="${transaction.amount}" />
									<html:hidden property="transactionNo" name="transactionNo"
										value="${transaction.no}" />
									<html:hidden property="bank"></html:hidden>
									<html:hidden property="version"></html:hidden>
									<html:hidden property="type" value="1,0"></html:hidden>
									<table cellpadding="0" cellspacing="0" width="100%"
										class="information_table">
										<tr>
											<td class="right_td">
												应付总价：
											</td>
											<td>
												<strong><font color="#FFD327"><fmt:formatNumber
															pattern="0.00" value="${transaction.amount}" /> </font> </strong> 元
											</td>
										</tr>
										<!-- 选择银行 -->
										<c:import url="../bank/selectBank.jsp"></c:import>
										<tr>
											<td>
												&nbsp;
											</td>
											<td>
												<input type="button" id="b3" disabled value="去银行付款"
													class="btn1" onclick="sendToBank();" />
												<div id="mgamount" style="color: red"></div>
											</td>
											<td></td>
										</tr>
										<tr>
											<td class="right_td"></td>
											<td>
												<div class="ExclaimedMsg">
													<span> 钱门禁止<strong class="R">信用卡套现、银行卡转账、虚假交易</strong>等行为，一经发现将予以处罚，包括但不限于：限制收款、
														冻结账户、永久停止服务，并有可能影响相关信用记录。 </span>
													<p>
														<span> <input type="checkbox" name="ch"
																onclick="chooseOne(this);document.getElementById('b3').disabled='';" />
															我同意 <input type="checkbox" name="ch"
																onclick="chooseOne(this);document.getElementById('b3').disabled='true';" />
															我不同意 </span>
												</div>
												</div>
											</td>
										</tr>
									</table>
								</html:form>
							</div>
							<script language="javascript">
							</script>
						</div>
					</div>
				</div>
			</div>
			<c:import url="/_jsp/footer.jsp" />
	</body>
</html>
