<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<jsp:useBean id="GatewayForm" scope="request"
	type="com.fordays.fdpay.cooperate.action.GatewayForm" />
	
<!--JSP页面: cooperate/gatewaynoqm.jsp -->

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>卡支付</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/qianmen.css" type="text/css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
		<script language="javascript"
			src="<%=request.getContextPath()%>/_js/common.js"></script>
		<script src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/prototype.js"></script>
		<script src="../_js/certUtil.js"></script>
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
		<object id='myControl1' name='myControl1'  classid='clsid:2E93307E-777D-49E4-886A-D5B04470796A' codebase='qmCert.cab'  width='0px;' height='0px;' style='display: none'></object>
	</head>
	
	<script language="javascript">
	function importCert(){
		window.location.href="<%=request.getContextPath()%>/security.do?thisAction=importCertificatePage";
	}
	function valiagent1(){
		var email = document.getElementById("payemail1").value;
		valiagents(email,1);
	}		
	function valiagent2(){
		var email = document.getElementById("payemail2").value;
		valiagents(email,2);
	}
	function valiagents(email,i){
		$.get("../cooperate/gateway.do", { service: "validCert", email: email },function(data){
			if(data=="该email账户不存在！"){
				return;
			}	
			if(data=="程序异常"){
				return;
			}
			var arragent = new Array();
			arragent = data.split(",");		
			validagent(arragent[0],arragent[1],email,arragent[2],i);
		} ); 
	}	
			
	function validagent(allowBalance,serialNumber,email,certStatus,i){
		serialNumber=serialNumber.toLowerCase();
		var result_validCert=validCerts(certStatus,serialNumber);
		if(result_validCert==1){
		  if(allowBalance==0){
		  		if(i==1){
					alert("账户余额不足，建议用网银支付！");
				}
		 		choosePayment(2);
				document.getElementById("bbbb").style.display = "none";
				document.getElementById("head2").style.display = "none";
				document.getElementById("head1").style.display = "block";
			}
			else{
				if(i==1){
					choosePayment(1);
				}else{
					choosePayment(2);
				}	
				document.getElementById("bbbb").style.display = "none";
				document.getElementById("head2").style.display = "none";
				document.getElementById("head1").style.display = "block";
			}
		}else{
				document.getElementById("derict_pay_by_bank").style.display = "none";
				document.getElementById("dummy_payment").style.display = "none";
				document.getElementById("bbbb").style.display = "block";
				document.getElementById("head1").style.display = "none";
				document.getElementById("head2").style.display = "block";
				document.getElementById("tab_3").className="at_deal_list_head";
		}
	}
  	
	function validCerts(certStatus,serialNumber){
		var return_value=0;
		var myControl = document.getElementById("myControl1");
		if(!isValidCerts(certStatus)){
			return 1;
		}
		if(validCert(isValidCerts(certStatus),serialNumber,myControl)){
			 return_value=1;
		}else{
			return_value=0;
		}
		return return_value;
	}
  </script>
	<body>
		<div id="warp">
		<!-- 
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
 -->
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
						<!-- 
							<div class="main_title_right">
							 
								<p>
									<strong>来自&nbsp;<c:out
											value="${GatewayForm.partnerInfo.name}" />&nbsp;的即时到账交易，直接付款给商家。</strong>
								</p>
								
							</div>
							-->
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
											value="${GatewayForm.subject}" /> </a>
								</td>
								<td>
									<fmt:formatNumber value="${GatewayForm.totalFee}"
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
												value="${GatewayForm.subject}" /> </a>
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
								<tr>
									<td class="right_td">
										卖家：
									</td>
									<td>
										<c:out value="${GatewayForm.sellerInfo.name}" />
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
							</table>
						</div>
						<div class="deal_list" style="position: relative;">
							<div id="head1" class="deal_list_head">
								<c:if
									test="${GatewayForm.requestType!='HOUSE_PERPORTY_REQUEST'}">
									<a href="#" id="tab_1" onclick="choosePayment(1)"
										class="at_deal_list_head">余额付款</a>
									<a href="#" id="tab_2" onclick="choosePayment(2)">网上银行付款</a>
								</c:if>
								<c:if
									test="${GatewayForm.requestType=='HOUSE_PERPORTY_REQUEST'}">
									<a href="#" id="tab_2" onclick="choosePayment(1)"
										class="at_deal_list_head">网上银行付款</a>
								</c:if>
							</div>
							<div id="head2" style="display:none;" class="deal_list_head">
								<a id="tab_3"  href="#">数字证书</a>
							</div>
							<c:if test="${GatewayForm.requestType!='HOUSE_PERPORTY_REQUEST'}">
								<!-- 余额付款 START -->
								<div class="deal_list_count deal_list_count_1"
									id="dummy_payment">
									<html:form action="/cooperate/gateway.do" method="post"
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
															value="${GatewayForm.totalFee}" type="currency"
															pattern="0.00元" /> </strong>
															<c:out value="${GatewayForm.totalFee}"></c:out>
												</td>
											</tr>
											<tr>
												<td>
												</td>
												<td>
													<html:text property="buyerEmail" styleId="payemail1" style="display:none;" indexed="1" value="${buyeremailnoqm}">
													</html:text>
												</td>
											</tr>
											<tr>
												<td class="right_td">
													请输入支付密码：
												</td>
												<td>
													<html:password property="password" onfocus="valiagent1()" indexed="1"
														styleClass="text_style2"></html:password>
												</td>
											</tr>
											<tr>
												<td></td>
												<td>
													<span class="simplyBtn_1" style="margin: 0"><input
															type="submit" style="cursor: pointer" id="b1"
															class="buttom_middle" value="确认无误，付款" />
													</span>
												</td>
											</tr>		
										</table>
									</html:form>
								</div>
								<!-- 余额付款 END -->
								<!-- 网上银行付款 START-->
								<div id="derict_pay_by_bank"
									class="deal_list_count deal_list_count_1" style="display: none">
									<html:form action="/cooperate/gateway.do" method="post">
										<html:hidden property="url" />
										<html:hidden property="service" />
										<input type="hidden" id="version" name="version" value="B2C" />
										<table cellpadding="0" cellspacing="0" width="100%"
											class="information_table">
											<tr>
												<td class="right_td">
													应付总价：
													<br />
												</td>
												<td>
													<strong style="color: #F60; font-size: 16px;"> <fmt:formatNumber
															value="${GatewayForm.totalFee}" type="currency"
															pattern="0.00元" /> </strong>
													<br />
												</td>
											</tr>
											<jsp:include page="../bank/selectBank.jsp"></jsp:include>
											<!-- 
											<tr>
												<td class="right_td">
													<span class="font_style6">*</span> 选择网上银行：
													<br />
												</td>
												<td>
													<input class="radio" type="radio" name="banben"
														value="personal" checked="checked"
														onclick="sleVersion(1);" />
													个人版
													<input class="radio" type="radio" name="banben"
														value="enterprise" onclick="sleVersion(2);" />
													企业版
													<br />
												</td>
											</tr>
											<tr>
												<td>
													<br />
												</td>
												<td colspan="3">
													<div id="versionOfPersona1">
														<table>
															<tr>
															<td>
																	<input class="radio" type="radio" name="bank"
																		id="perICBC" value="ICBC" checked="checked" />
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
																		value="ABC" onclick="check('ABC');" />
																	<img src="../_img/bank/bankLogo_4.gif"
																		onclick="check('ABC');" />
																	<br />
																</td>																
															</tr>															
															<tr>															
																<td>
																	<input class="radio" type="radio" name="bank" value="BCM" onclick="check('BCM');" />
																	<img src="../_img/bank/bankLogo_10.gif"  onclick="check('BCM');" />
																</td>																																																	
																<td>
																	<input class="radio" type="radio" name="bank" value="CITIC" onclick="check('CITIC');" />
																	<img src="../_img/bank/bankLogo_11.gif" onclick="check('CITIC');" />
																</td>
																<td>
																	&nbsp;
																</td>																														
															</tr>
															<tr>
																<td colspan="4">
																	<font  color="red">以下银行接口将于近日上线</font>
																</td>
															</tr>
															<tr>
																	<td>
																			<input class="radio" type="radio" name="bank0" value="BOC" onclick="check('BOC');"/>
																			<img src="../_img/bank/bankLogo_13.gif" onclick="check('BOC');"/>
																	</td>																
																				<td>
																					<input class="radio" type="radio" name="bank0" value="GDB" onclick="check('GDB');"/>
																					<img src="../_img/bank/bankLogo_7.gif" onclick="check('GDB');"/>
																				</td>
																				<td>
																					<input class="radio" type="radio" name="bank0" value="SDB" onclick="check('SDB');"/>
																					<img src="../_img/bank/bankLogo_8.gif" onclick="check('SDB');"/>
																				</td>
																				<td>
																					<input class="radio" type="radio" name="bank0" value="SPDB" onclick="check('SPDB');"/>
																					<img src="../_img/bank/bankLogo_5.gif" onclick="check('SPDB');"/>
																				</td>
																			</tr>																			
														</table>
													</div>
													<div id="versionOfPersona2" style="display: none;">
														<table>
															<tr>
																<td>
																	&nbsp;
																</td>
																<td colspan="3" class="blue-grayBox"
																	style="padding-left: 30px;">
																	小贴士：
																	<em style="font-style: normal; color: #C00;"
																		id="showBank">企业网银</em>暂未开通。
																</td>
															</tr>

														</table>
													</div>
													<br />
												</td>
											</tr>
											-->
											<tr>
												<td>
												</td>
												<td colspan="3">
													<html:text property="buyerEmail" styleId="payemail2" style="display:none;" indexed="1" value="${buyeremailnoqm}">
													</html:text>
												</td>
											</tr>

											<tr>
												<td>
													&nbsp;
												</td>
												<td colspan="3">
													请您输入支付密码：
												</td>
											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
												<td colspan="3">
													<html:password property="password" indexed="1" onfocus="valiagent2()"
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
															 type="button" style="cursor: pointer"
															id="b2" class="buttom_middle" onclick="fastpayByBank()" value="确认无误，付款" /> </span>
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
												<!--  
													<div class="ExclaimedMsg">
														<span> 钱门禁止<strong class="R">信用卡套现、银行卡转账、虚假交易</strong>等行为，一经发现将予以处罚，包括但不限于：限制收款、
															冻结账户、永久停止服务，并有可能影响相关信用记录。 </span>
														<p>
															<span> <input type="checkbox" id="cc" name="ch"
																	onclick="chooseOne(this);document.getElementById('b2').disabled='';" />
																我同意 <input type="checkbox" name="ch"
																	onclick="chooseOne(this);document.getElementById('b2').disabled='true';" />
																我不同意 </span>
													</div>
													-->
												</td>
											</tr>
										</table>
									</html:form>
								</div>
								<!-- 网上银行付款 END-->					
							</c:if>
							<c:if test="${GatewayForm.requestType=='HOUSE_PERPORTY_REQUEST'}">
								<!-- 房产网网上银行付款 START-->
								<div id="derict_pay_for_house_perporty"
									class="deal_list_count deal_list_count_1">
									<html:form action="/cooperate/gateway.do" method="post">
										<html:hidden property="url" />
										<html:hidden property="service" value="fastpay_for_no_account" />
										<input type="hidden" id="version" name="version" value="B2C" />
										<table cellpadding="0" cellspacing="0" width="100%"
											class="information_table">
											<tr>
												<td class="right_td">
													应付总价：
													<br />
												</td>
												<td>
													<strong style="color: #F60; font-size: 16px;"> <fmt:formatNumber
															value="${GatewayForm.totalFee}" type="currency"
															pattern="0.00元" /> </strong>
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
														value="personal" checked="checked"
														onclick="sleVersion(3);" />
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
																	<input class="radio" type="radio" name="bank"
																		value="ICBC" checked="checked"/>
																	<img src="../_img/bank/bankLogo_1.gif"
																		onclick="check('ICBC');" />																	
																</td>														
																<td>
																	<input class="radio" type="radio" name="bank"
																		value="CCB"  />
																	<img src="../_img/bank/bankLogo_3.gif"
																		onclick="check('CCB');" />																	
																</td>																
																<td>
																	<input class="radio" type="radio" name="bank"
																		value="CMBC" />
																	<img src="../_img/bank/bankLogo_9.gif"
																		onclick="check('CMBC');" />																	
																</td>
																<td>
																	<input class="radio" type="radio" name="bank"
																		value="ABC" onclick="check('ABC');" />
																	<img src="../_img/bank/bankLogo_4.gif"
																		onclick="check('ABC');" />																	
																</td>																
															</tr>
															<tr>															
																<td>
																	<input class="radio" type="radio" name="bank" value="BCM" onclick="check('BCM');" />
																	<img src="../_img/bank/bankLogo_10.gif"  onclick="check('BCM');" />
																</td>																 
																<td>
																	<input class="radio" type="radio" name="bank" value="CITIC" onclick="check('CITIC');" />
																	<img src="../_img/bank/bankLogo_11.gif" onclick="check('CITIC');" />
																</td>
															</tr>
															<tr>
																<td colspan="4">
																	<font  color="red">以下银行接口将于近日上线</font>
																</td>
															</tr>
															<tr>	
																<td>
																	&nbsp;
																</td>
																<td>
																	&nbsp;
																</td>
																<td>
																	&nbsp;
																</td>
																<td>
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td colspan="4" class="blue-grayBox"
																	style="padding-left: 30px;">
																	小贴士：
																	<em style="font-style: normal; color: #C00;"
																		id="showBank">企业网银</em>暂未开通。
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
													<br />
												</td>
												<td>
													<span class="simplyBtn_1" style="margin: 0"> <input
															type="button" style="cursor: pointer" disabled="disabled"
															id="b3" class="buttom_middle" value="确认无误，付款" onclick="fastpayForHousePerporty();"/> </span>
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
												<!-- 
													<div class="ExclaimedMsg">
														<span> 钱门禁止<strong class="R">信用卡套现、银行卡转账、虚假交易</strong>等行为，一经发现将予以处罚，包括但不限于：限制收款、
															冻结账户、永久停止服务，并有可能影响相关信用记录。 </span>
														<p>
															<span> <input type="checkbox" name="ch" id="dd"
																	onclick="chooseOne(this);document.getElementById('b3').disabled='';" />
																我同意 <input type="checkbox" name="ch"
																	onclick="chooseOne(this);document.getElementById('b3').disabled='true';" />
																我不同意 </span>
													</div>
													 -->
												</td>
											</tr>
										</table>
									</html:form>
								</div>
								<!-- 房产网网上银行付款 END-->
							</c:if>
							<!-- 未安装数字证书 -->
								<div id="bbbb" style="display:none">
									<div id="dddd" class="Worg">
										<div class="attentionTitle attentionTitle1">
											<em>请导入"数字证书"！</em>
										</div>
										<ul class="attentionList">
											<li>
												本台电脑还未导入证书，请用"网上银行付款"或
												<!-- <a href="javascript:importCert()">点此导入数字证书</a>。-->
											</li>
										</ul>
									</div>
							  </div>
							  <!-- 未安装数字证书end -->
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
var house = "<c:out value='${GatewayForm.requestType}'/>";
var doc=document;

if(house!='HOUSE_PERPORTY_REQUEST'){
	if(house=='master')  //买家钱不够
		choosePayment(2);
	else
		choosePayment(1);
}else{
	choosePayment(1);
}

function goPayment(){
	with(document.forms[0]){
		
		if(!required(password)){
			alert('请您输入支付密码');
			password.focus();
			return false;
		}
		document.getElementById('b1').disabled='true';
		service.value="paymentnoqm";
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

function selectVersion(no){
	//*********选择企业版还是个人版
	var div1=doc.getElementById("versionOfPersona1");
	var div2=doc.getElementById("versionOfEnterprise");
	if(no==0){
		document.forms[1].version.value="B2C"
		div1.style.display="";
		div2.style.display="none";
		document.getElementById("cc").disabled=false;
		selectBanksName='bank0';
	
	}else{
		document.forms[1].version.value="B2B"
		div1.style.display="none";
		div2.style.display="";
		document.getElementById("cc").disabled=false;
		selectBanksName='bank1';
	
	}
}

 //*******个人版网上银行
 function clickPersonal(bankName) {
	initBankNotice();
	var bank = document.getElementsByName("bank0");
	for (var i = 0; i < bank.length; i++) {
		if (bank[i].value == bankName) {
			bank[i].checked = true;
		}
	}
	var shuoming = "bank_" + bankName;
	document.getElementById(shuoming).style.display = "";
	
}

function sleVersion(no){
	var div1=doc.getElementById("versionOfPersona1");
	var div2=doc.getElementById("versionOfPersona2");
	var div3=doc.getElementById("versionOfPersona3");
	var div4=doc.getElementById("versionOfPersona4");
	
	if(no==1){
		div1.style.display="";
		div2.style.display="none";
		document.getElementById("cc").disabled=false;
	}
	else if(no==2){
		div1.style.display="none";
		div2.style.display="";
		document.getElementById("cc").disabled=true;
	}
	else if(no==3){
		document.forms[0].version.value="B2C"
		div3.style.display="";
		div4.style.display="none";
		document.getElementById("dd").disabled=false;
	}
	else if(no==4){
		document.forms[0].version.value="B2B"
		div3.style.display="none";
		div4.style.display="";
		document.getElementById("dd").disabled=true;		
	}
}



function check(ba)
{
	var bank=doc.getElementsByName("bank");
	for(var i=0;i<bank.length;i++) 
	{  
        if(bank[i].value==ba)
        bank[i].checked=true;  
    } 
}
function check0(ba)
{
	var bank=doc.getElementsByName("bank0");
	for(var i=0;i<bank.length;i++) 
	{  
        if(bank[i].value==ba)
        bank[i].checked=true;  
    } 
}
function checkOfPersona(ba)
{
	var bank=doc.getElementsByName("bank2");
	for(var i=0;i<bank.length;i++) 
	{  
        if(bank[i].value==ba)
        bank[i].checked=true;  
    } 
}
function checkOfPersona1(ba)
{
	var bank=doc.getElementsByName("bank1");
	for(var i=0;i<bank.length;i++) 
	{  
        if(bank[i].value==ba)
        bank[i].checked=true;  
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

function fastpayByBank(){
	with(document.forms[1]){
		
		if(!required(password)){
			alert('请您输入支付密码');
			password.focus();
			return false;
		}		
		document.getElementById('b2').disabled='true';
		service.value="fastpay_by_bank";
	}
	
	if(isICBC()){
  		var result=openModalDialog(392,188,"gatewayPopNotice.htm") 
  		//alert("--result--"+result)
  		if(result>0){  	
	  		document.forms[1].submit();
	  	}
	}else{
		document.forms[1].submit();
	}	
}

function isICBC(){
	var perICBC=document.getElementById("perICBC");
	if(perICBC.checked){
		return true;
	}else{
		return false;
	}    
}

function fastpayForHousePerporty(){
	if(isICBC()){
  		var result=openModalDialog(392,188,"gatewayPopNotice.htm") 
  		//alert("--result--"+result)
  		if(result>0){  	
	  		document.forms[1].submit();
	  	}
	}else{
		document.forms[1].submit();
	}	
}
</script>
