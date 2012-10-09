<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<jsp:useBean id="agentBind" scope="request"
	type="com.fordays.fdpay.agent.AgentBind" />
	
<!--JSP页面: cooperate/pengateway.jsp -->

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/qianmen.css" type="text/css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
		<script language="javascript"
			src="<%=request.getContextPath()%>/_js/common.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/_css/global_v2.css" />
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
		</script>
	</head>
	<body>
		<div id="warp">
			<div id="head">
				<span style="float: right;"> <em>您好，请 <html:link
							page="/agent/agent.do?thisAction=toRegister">立即注册</html:link>或 <html:link
							page="../index.jsp">登录</html:link> </em> <html:link
						page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">立即充值</html:link>
					<a href="#">快乐积分</a> <a href="../support/index.htm">帮助中心</a> <a
					href="#">社区</a> </span>
				<a href="../index.jsp" style="border: none; margin: 0; padding: 0;"><img
						src="../_img/logo.jpg" /> </a>
			</div>

			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>来自&nbsp;<c:out
											value="${agentBind.partnerInfo.name}" />&nbsp;的圈中圈信用支付交易。</strong>
								</p>
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" width="100%"
							class="deal_table" style="margin-top: 12px;">
							<tr>
								<th width="90%">
									<div>
										商品名称
									</div>
								</th>
								<th width="10%">
									<div>
										应付总价
									</div>
								</th>
							</tr>
							<tr>
								<td>
									<a href="#" id="TradeLink" onclick="showTradeInfo()"> <img
											src="../_img/zhankai.jpg" class="vcenter" id="moreimg"
											alt="查看此商品的详细信息" border="0" /> <c:out
											value="${agentBind.subject}" /> </a>
								</td>
								<td>
									<fmt:formatNumber value="${agentBind.totalFee}"
										type="currency" pattern="0.00元" />
								</td>
							</tr>
						</table>
						<div class="tradeInfo" id="trade_info"
							style="background: none; visibility: hidden; display: none;">
							<table cellpadding="0" cellspacing="0" width="100%"
								class="information_table">
								<tr>
									<td class="right_td">
										商品名称：
									</td>
									<td>
										<a href="#" style="margin: 0"><c:out
												value="${agentBind.subject}" /> </a>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										商品描述：
									</td>
									<td>
										<c:out value="${agentBind.body}" />
									</td>
								</tr>
								<tr>
									<td class="right_td">
										卖家：
									</td>
									<td>
										<c:out value="${agentBind.sellerInfo.name}" />
									</td>
								</tr>
								<tr>
									<td class="right_td">
										交易类型：
									</td>
									<td>
										<img src="../_img/fdpay.gif" style="margin-right: 6px;" alt="" />
										圈中圈信用支付交易
									</td>
								</tr>
							</table>
						</div>
						<div class="deal_list" style="position: relative;">
							<div class="deal_list_head">
								<c:if
									test="${agentBind.requestType!='HOUSE_PERPORTY_REQUEST'}">
									<a href="#" id="tab_1" onclick="choosePayment(1)"
										class="at_deal_list_head">钱门信用付款</a>
								</c:if>
							</div>
							<c:if test="${agentBind.requestType!='HOUSE_PERPORTY_REQUEST'}">
								<!-- 钱门信用余额付款 START -->
								<div class="deal_list_count deal_list_count_1"
									id="dummy_payment">
									<html:form action="/cooperate/agentBind.do" method="post"
										onsubmit="return goPayment()">
										<html:hidden property="url" />
										<html:hidden property="service" />
										<table cellpadding="0" cellspacing="0" width="100%"
											class="information_table">
											<tr>
												<td class="right_td">
													应付总价：
												</td>
												<td>
													<strong style="color: #F60; font-size: 16px;"> <fmt:formatNumber
															value="${agentBind.totalFee}" type="currency"
															pattern="0.00元" /> </strong>
												</td>
											</tr>
											<tr>
												<td class="right_td">
													
												</td>
												<td>
													<html:hidden property="buyerEmail" indexed="1"
														 value="${buyerEmail}" ></html:hidden>
													<em
														style="font-style: normal; color: #999; font-size: 12px; margin-left: 12px;"></em>
												</td>
											</tr>
											<tr>
												<td class="right_td">
													圈名：
												</td>
												<td>
													<c:out value="${cName}"></c:out>
												</td>
											</tr>
											<tr>
												<td class="right_td">
													请输入钱门的支付密码：
												</td>
												<td>
													<html:password property="password" indexed="1"
														styleClass="text_style2"></html:password>
													<em
														style="font-style: normal; font-size: 12px; margin-left: 12px;"><a
														href="<%=request.getContextPath()%>/agent/agent.do?thisAction=login">找回支付密码</a>
													</em>
												</td>
											</tr>
											<tr>
												<td></td>
												<td>
													<span class="simplyBtn_1" style="margin: 0"><input
															type="submit" style="cursor: pointer" id="b1"
															disabled="disabled" class="buttom_middle" value="确认无误，付款" />
													</span>
												</td>
											</tr>
											<tr>
												<td></td>
												<td>
													<div class="ExclaimedMsg">
														<span> 钱门禁止<strong class="R">信用卡套现、银行卡转账、虚假交易</strong>等行为，一经发现将予以处罚，包括但不限于：限制收款、
															冻结账户、永久停止服务，并有可能影响相关信用记录。 </span>
														<p>
															<span> <input type="checkbox" name="ch"
																	onclick="chooseOne(this);document.getElementById('b1').disabled='';" />
																我同意 <input type="checkbox" name="ch"
																	onclick="chooseOne(this);document.getElementById('b1').disabled='true';" />
																我不同意 </span>
													</div>
												</td>
											</tr>
										</table>
									</html:form>
								</div>
								<!-- 钱门余额付款 END -->
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<div id="footer">
				CopyRight 2009-2012, www.qmpay.com All Rights Reserved 增值电信业务经营许可证
				粤B2-20090217
			</div>
		</div>
	</body>
</html>
<script language="javascript">
var displayCount=0;
var doc=document;

//if(house!='HOUSE_PERPORTY_REQUEST')
	//choosePayment(1);

function goPayment(){
	with(document.forms[0]){
		if(!required(buyerEmail)){
			alert('请您输入钱门账户');
			buyerEmail.focus();
			return false;
		}
		if(!required(password)){
			alert('请您输入钱门的支付密码');
			password.focus();
			return false;
		}
		service.value="payment";
	}
	return true;
}
function showTradeInfo(){
	//trade_info
	var obj=doc.getElementById("moreimg");
	if(obj!=null&&typeof(obj)!="undefined"){
		if(obj.src.indexOf("_img/zhankai.jpg")>=0){
			obj.src="../_img/shouqi.jpg";
			showElement("trade_info");
		}else{
			obj.src="../_img/zhankai.jpg";
			hiddenElement("trade_info");
		}
	}

}

function choosePayment(index){
	var _tabName="tab_"+index;
	var _currentObj=doc.getElementById(_tabName);
		_currentObj.className="at_deal_list_head";
	if(index==1){
		showElement('dummy_payment');
		hiddenElement('derict_pay_by_bank');
		var _tempObj=doc.getElementById("tab_2");
			_tempObj.className="";
		
	}else if(index==2){
		showElement('derict_pay_by_bank');
		hiddenElement('dummy_payment');
		var _tempObj=doc.getElementById("tab_1");
			_tempObj.className="";
	}
}





</script>
