<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!--JSP页面: cooperate/directPayByAccount.jsp -->
<html>
	<head>
		<jsp:useBean id="GatewayForm" scope="request"
			type="com.fordays.fdpay.cooperate.action.GatewayForm" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
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
									<strong>确认付款信息，并通过钱门付款</strong>
								</p>
							</div>
						</div>
						<p style="margin-bottom: 0; color: #666;">
							--本次交易为
							<em style="font-style: normal; color: #F60;">“即时到帐交易”</em>，付款后您的钱将
							<strong>直接</strong>进入对方账户。
						</p>
						<table cellpadding="0" cellspacing="0" width="100%"
							class="deal_table" style="margin-top: 12px;">
							<tr>
								<th>
									<div>
										商品名称
									</div>
								</th>
								<th>
									<div>
										单价
									</div>
								</th>
								<th>
									<div>
										数量
									</div>
								</th>
								<th>
									<div>
										邮费
									</div>
								</th>
								<th>
									<div>
										原价
									</div>
								</th>
								<th>
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
											value="${GatewayForm.orderDetail.shopName}" /> </a>
								</td>
								<td>
									<fmt:formatNumber
										value="${GatewayForm.orderDetail.paymentPrice}"
										type="currency" pattern="0.00元" />
								</td>
								<td>
									<fmt:formatNumber value="${GatewayForm.orderDetail.shopTotal}"
										pattern="0件" />
								</td>
								<td>
									<fmt:formatNumber value="${GatewayForm.orderDetail.emailPrice}"
										type="currency" pattern="0.00元" />
								</td>
								<td>
									<fmt:formatNumber value="${GatewayForm.orderDetail.shopPrice}"
										type="currency" pattern="0.00元" />
								</td>
								<td>
									<fmt:formatNumber
										value="${GatewayForm.orderDetail.paymentPrice}"
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
										<a href="#" style="margin: 0"> <c:out
												value="${GatewayForm.subject}" /> </a>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										卖家：
									</td>
									<td>
										<c:out value="${GatewayForm.sellerInfo.name}" />
										(
										<c:out value="${GatewayForm.sellerInfo.email}" />
										)
									</td>
								</tr>
								<tr>
									<td class="right_td">
										买家：
									</td>
									<td>
										<c:out value="${GatewayForm.buyerInfo.name}" />
										(
										<c:out value="${GatewayForm.buyerInfo.email}" />
										)
									</td>
								</tr>
								<tr>
									<td class="right_td">
										交易号：
									</td>
									<td>
										<a href="#" style="margin: 0"> <c:out
												value="${GatewayForm.orderDetail.orderDetailsNo}" /> </a>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										交易类型：
									</td>
									<td>
										<img src="../_img/fdpay.gif" style="margin-right: 6px;" alt="" />
										即时到帐交易
									</td>
								</tr>
								<tr>
									<td class="right_td">
										购买时间：
									</td>
									<td>
										<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss"
											value="${GatewayForm.orderDetail.createDate}" />
									</td>
								</tr>
								<tr>
									<td class="right_td">
										商品描述：
									</td>
									<td>
										<c:out value="${GatewayForm.body}" />
									</td>
								</tr>
							</table>
						</div>
						<p>
						<div class="deal_list" style="position: relative;">
							<div class="deal_list_head">
								<a href="#" id="tab_1" onclick="choosePayment(1)"
									class="at_deal_list_head">钱门余额付款</a>
								<a href="#" id="tab_2" onclick="choosePayment(2)">网上银行付款</a>
							</div>
							<!-- 钱门余额付款 -->
							<div class="deal_list_count deal_list_count_1">
								<table cellpadding="0" cellspacing="0" width="100%"
									class="information_table">
									<tr>
										<td class="right_td">
											应付总价：
										</td>
										<td>
											<strong style="color: #F60; font-size: 16px;"><fmt:formatNumber
													value="${GatewayForm.totalFee}" type="currency"
													pattern="0.00元" /> </strong>
										</td>
									</tr>
									<tr>
										<td class="right_td">
											钱门账户余额：
										</td>
										<td>
											<fmt:formatNumber
												value="${GatewayForm.buyerInfo.allowBalance+GatewayForm.buyerInfo.checkAmount}"
												type="currency" pattern="0.00元" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td>
											<div class="blueBox_1">
												帐户余额不足,你可以先充值或者直接到网上银行付款。
											</div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<html:link
												page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">立即充值</html:link>
										</td>
									</tr>
								</table>
							</div>
							<!-- 钱门余额付款 -->
							<!-- 网上银行付款 START-->
							<div id="net_bank_payment"
								class="deal_list_count deal_list_count_1" style="display: none">
								<html:form action="/cooperate/gateway.do" method="post">
									<html:hidden property="url" />
									<html:hidden property="service" />
									<input type="hidden" id="version" name="version" value="B2C" />
									<table cellpadding="0" cellspacing="0" width="100%"
										class="information_table">
										<tr>
											<td class="right_td">
												网银应付总价：
												<br />
											</td>
											<td>
												<strong style="color: #F60; font-size: 16px;"> <fmt:formatNumber
														value="${GatewayForm.totalFee-GatewayForm.buyerInfo.allowBalance-GatewayForm.buyerInfo.checkAmount}"
														type="currency" pattern="0.00元" /> </strong>
												<input type="hidden" value="allowBalance"
													name="allowBalance" />
												<br />
											</td>
										</tr>
										<tr>
											<td class="right_td">
												<span class="font_style6">*</span> 选择网上银行：
												<br />
											</td>
											<td>
												<input class="radio" type="radio" name="banben"
													value="personal" checked="checked" onclick="sleVersion(3);" />
												个人版
												<input class="radio" type="radio" name="banben"
													value="enterprise" onclick="sleVersion(4);" />
												企业版
												<br />
											</td>
										</tr>
										<tr>
											<td>
												<br />
											</td>
											<td colspan="3">
												<div id="versionOfPersona3">
													<table>
														<tr>
															<td>
																&nbsp;
																<br />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="ICBC" checked="checked" />
																<img src="../_img/bank/bankLogo_1.gif"
																	onclick="check('ICBC');" />
																<br />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="CCB" />
																<img src="../_img/bank/bankLogo_3.gif"
																	onclick="check('CCB');" />
																<br />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="CMBC" />
																<img src="../_img/bank/bankLogo_9.gif"
																	onclick="check('CMBC');" />
																<br />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="ABC" />
																<img src="../_img/bank/bankLogo_4.gif"
																	onclick="check('ABC');" />
																<br />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;
																<br />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="BCM" onclick="check('BCM');" />
																<img src="../_img/bank/bankLogo_10.gif"
																	onclick="check('BCM');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="CITIC" onclick="check('CITIC');" />
																<img src="../_img/bank/bankLogo_11.gif"
																	onclick="check('CITIC');" />
															</td>
															<td colspan="2"></td>
														</tr>
														<tr>
															<td>
																&nbsp;
															</td>
															<td colspan="4">
																以下银行接口由中国银联提供
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="BOC_ChinaPay" onclick="check('BOC_ChinaPay');" />
																<img src="../_img/bank/bankLogo_13.gif"
																	onclick="check('BOC_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="PSBC_ChinaPay" onclick="check('PSBC_ChinaPay');" />
																<img src="../_img/bank/bankLogo_17.gif"
																	onclick="check('PSBC_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="CIB_ChinaPay" onclick="check('CIB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_6.gif"
																	onclick="check('CIB_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="HXB_ChinaPay" onclick="check('HXB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_18.gif"
																	onclick="check('HXB_ChinaPay');" />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="JSNX_ChinaPay" onclick="check('JSNX_ChinaPay');" />
																<img src="../_img/bank/bankLogo_22.gif"
																	onclick="check('JSNX_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="NBCB_ChinaPay" onclick="check('NBCB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_26.gif"
																	onclick="check('NBCB_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="ZHNX_ChinaPay" onclick="check('ZHNX_ChinaPay');" />
																<img src="../_img/bank/bankLogo_21.gif"
																	onclick="check('ZHNX_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="YDXH_ChinaPay" onclick="check('YDXH_ChinaPay');" />
																<img src="../_img/bank/bankLogo_23.gif"
																	onclick="check('YDXH_ChinaPay');" />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="CBHB_ChinaPay" onclick="check('CBHB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_19.gif"
																	onclick="check('CBHB_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="JCCB_ChinaPay" onclick="check('JCCB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_24.gif"
																	onclick="check('JCCB_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="WZCB_ChinaPay" onclick="check('WZCB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_25.gif"
																	onclick="check('WZCB_ChinaPay');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="HKB_ChinaPay" onclick="check('HKB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_20.gif"
																	onclick="check('HKB_ChinaPay');" />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;
															</td>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="FDB_ChinaPay" onclick="check('FDB_ChinaPay');" />
																<img src="../_img/bank/bankLogo_27.gif"
																	onclick="check('FDB_ChinaPay');" />
															</td>
															<td>
															</td>
														</tr>

													</table>
												</div>
												<div id="versionOfPersona4" style="display: none;">
													<table>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank"
																	value="ICBC" />
																<img src="../_img/bank/bankLogo_1.gif"
																	onclick="check('ICBC');" />
															</td>
															<td colspan="2">
																<input class="radio" type="radio" name="bank"
																	value="CCB" />
																<img src="../_img/bank/bankLogo_3.gif"
																	onclick="check('CCB');" />
															</td>
														</tr>
													</table>
												</div>
												<br />
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3">
												输入常用的email地址：
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3">
												<html:text property="buyerEmail" indexed="1"
													styleClass="text_style2"></html:text>
											</td>
										</tr>

										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3">
												请输入钱门的支付密码：
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td colspan="3">
												<html:password property="password" value="" indexed="1"
													styleClass="text_style2"></html:password>
												<em
													style="font-style: normal; font-size: 12px; margin-left: 12px;"><a
													href="<%=request.getContextPath()%>/agent/agent.do?thisAction=login">找回支付密码</a>
												</em>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
												<br />
											</td>
											<td>
												<span class="simplyBtn_1" style="margin: 0"> <input
														type="submit" style="cursor: pointer" id="b1"
														disabled="disabled" class="buttom_middle" value="确认无误，付款"
														onclick="goFastpayByFlatform();" /> </span>
												<br />
											</td>
											<td>
												<br />

												<br />
												<br />
											</td>
										</tr>

										<tr>
											<td></td>
											<td>
												<div class="ExclaimedMsg">
													<span> 钱门禁止<strong class="R">信用卡套现、银行卡转账、虚假交易</strong>等行为，一经发现将予以处罚，包括但不限于：限制收款、
														冻结账户、永久停止服务，并有可能影响相关信用记录。 </span>
													<p>
														<span> <input type="checkbox" name="ch" id="cc"
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
							<!-- 网上银行付款 END-->
						</div>
					</div>
				</div>
			</div>

			<div id="footer">
				CopyRight 2009-2012, www.qmpay.com .All Rights Reserved 增值电信业务经营许可证
				粤B2-20090217
			</div>
		</div>
	</body>
</html>
<script language="javascript">
<!--
function showTradeInfo(){
	//trade_info
	var obj=document.getElementById("moreimg");
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
var doc=document;

choosePayment(2);

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

//-------------网上银行支付
function sleVersion(no){	
	var div3=doc.getElementById("versionOfPersona3");
	var div4=doc.getElementById("versionOfPersona4");
	
	if(no==3){
		
		document.forms[0].version.value="B2C";
		
		div3.style.display="";
		div4.style.display="none";
		
		document.getElementById("cc").disabled=false;
	}else if(no==4){
		document.forms[0].version.value="B2B";
		div3.style.display="none";
		div4.style.display="";
		document.getElementById("cc").disabled=false;
	}
}
function check(myValue){
	var bank=doc.getElementsByName("bank");
	
	for(var i=0;i<bank.length;i++){  
		var bankValue = bank[i].value;	
		//alert(bankValue);	
		
        if(bankValue==myValue){
        	bank[i].checked=true; 
        } 
        
       var indexFlag = bankValue.lastIndexOf("ChinaPay");
	  // alert(indexFlag);		
		if (indexFlag > 0) {
			bank[i].value = "CMBC";
			document.forms[0].version.value="ChinaPay";
			//alert(document.forms[0].version.value);
		}
    } 
    
}
function choosePayment(index){
	var _tabName="tab_"+index;
	var _currentObj=doc.getElementById(_tabName);
		_currentObj.className="at_deal_list_head";
	if(index==1){
		showElement('dummy_payment');
		hiddenElement('net_bank_payment');
		var _tempObj=doc.getElementById("tab_2");
			_tempObj.className="";
		
	}else if(index==2){
		showElement('net_bank_payment');
		hiddenElement('dummy_payment');
		var _tempObj=doc.getElementById("tab_1");
			_tempObj.className="";
	}
}
function goFastpayByFlatform(){
	with(document.forms[0]){
		if(!required(buyerEmail)){
			alert('请输入常用的email地址');
			buyerEmail.focus();
			return false;
		}
		if(!required(password)){
			alert('请您输入钱门的支付密码');
			password.focus();
			return false;
		}
		service.value="fastpay_by_bank";
	}
	document.forms[1].submit();	
}

</script>