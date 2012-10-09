<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP交易管理: transaction/transactionPayment.jsp -->

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../_css/global_v2.css" />

		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/prototype.js"></script>
		<script src="../_js/certUtil.js"></script>
		<script language="javascript">
	function show_date_time(){
	
		var accountDate = "<fmt:formatDate pattern='yyyy/MM/dd HH:mm:ss' value='${transaction.accountDate}'/>";
		var timeDiv = document.getElementById("timeDiv");
		var timeID = null;
		timeID = window.setTimeout("show_date_time()", 1000);
		var target=new Date(accountDate);
		target.setDate(target.getDate()+7);
		today=new Date();

		timeold=(target.getTime()-today.getTime());
		
		sectimeold=timeold/1000;
		secondsold=Math.floor(sectimeold);
		msPerDay=24*60*60*1000;
		e_daysold=timeold/msPerDay;
		daysold=Math.floor(e_daysold);
		e_hrsold=(e_daysold-daysold)*24;
		hrsold=Math.floor(e_hrsold);
		e_minsold=(e_hrsold-hrsold)*60;
		minsold=Math.floor((e_hrsold-hrsold)*60);
		seconds=Math.floor((e_minsold-minsold)*60);
		
		if (daysold<0) {
			timeDiv.innerHTML="交易自动关闭!";
			clearTimeout(timeID);
		}
		else{
			if (daysold<10) {daysold="0"+daysold;}
			if (daysold<100) {daysold=daysold;}
			if (hrsold<10) {hrsold="0"+hrsold;}
			if (minsold<10) {minsold="0"+minsold;}
			if (seconds<10) {seconds="0"+seconds;}
			if (daysold<3) {
				timeDiv.innerHTML=daysold+"天"+hrsold+"小时"+minsold+"分"+seconds+"秒</font>";
			}
			else
				timeDiv.innerHTML=+daysold+"天"+hrsold+"小时"+minsold+"分"+seconds+"秒</font>";
		}
	}
		
		
		function showAndHideAgentDetail(){
		    var btn = document.getElementById("a1").name;
		    if(btn=="a1"){
				document.getElementById("agentDetail").style.display = "block";
				document.getElementById("a1").name="a2";
				document.getElementById("imgDiv").src="../_img/zhankai.jpg";
			}
			else if(btn=="a2"){
				document.getElementById("agentDetail").style.display = "none";
				document.getElementById("a1").name="a1";
				document.getElementById("imgDiv").src="../_img/shouqi.jpg";
			}
		}
		
		function checkPayPasswordBeforCommit(type,agentAddressInfo){
			var mobileValidateCode = document.getElementById("mobileValidateCode").value;
			var autoPassword = document.getElementById("autoPassword");
		 	var amount = document.getElementById("amount").value;
		 	var validateCodeMsg = document.getElementById("validateCodeMsg");
		 	var allowBalance = document.getElementById("allowBalance").value;
		 	validateCodeMsg.innerHTML = "";
		 	if(agentAddressInfo==""||typeof(agentAddressInfo)=="undefined"){
		 		if (!confirm("你还没有设置交易地址,是否确定不需要设置？")) return;
		 	}
		 	
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
		    var payPassword = document.getElementById("payPassword").value;
		    var errorMessage = document.getElementById("errorMessage");
		    errorMessage.innerHTML = "";
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword,{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			if(result==0){
				errorMessage.innerHTML="<font color=red>支付密码错误!</font>";
			}
			else{
			
				if(autoPassword.style.display == "block" && mobileValidateCode==""){
		    		alert("请输入校验码");
		    		return false;
		    	}
				var checkAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkCanPay&amount="+amount+"&mobileValidateCode="+mobileValidateCode,{method:"post", onComplete:function (originalRequest) {
					var result2 = originalRequest.responseText;
					if(result2==0){
						errorMessage.innerHTML="<font color=red>余额支付功能已关闭!</font>";
					}
					else 
					if(result2==1){
						errorMessage.innerHTML="<font color=red>你支付的额度超过设置的单笔支付额度,请输入动态口令密码!</font>";
						
						autoPassword.style.display = "block";
					}
					else if(result2==2){
						errorMessage.innerHTML="<font color=red>你支付的额度超过设置的当天支付累计资金数,请输入动态口令密码!</font>";
						
						autoPassword.style.display = "block";
					}
					else if(result2==4){
						validateCodeMsg.innerHTML="<font color=red>校验码有误!</font>";
						autoPassword.style.display = "block";
					}
					else if(result2==5){
						validateCodeMsg.innerHTML="<font color=red>点击获取动态口令并输入校验码!</font>";
						autoPassword.style.display = "block";
						return false;
					}
					else{
						if(type!=3){ //如果是担保交易收款,就不用提示这句
							if (!confirm("即时到账交易不受钱门保护,钱直接到对方账户,不能退款,你是否确定？")) return;
						}
							//判断是个人银行还是企业银行
							var mgamot=document.getElementById("msamount");
							var div2=document.getElementById("versionOfPersona2"); 
							var div1=document.getElementById("versionOfPersona1");
							if(div2!=null&&typeof(div2)!="undefined"){
								if(div2.style.display=="")
								{	
									var bk1=document.getElementsByName("bank4");
									for(var i=0;i<bk1.length;i++)
									{
										if(bk1[i].checked && i>=1)
										{
											mgamot.innerHTML="您选择的银行还没开通业务！";
											errorMessage.innerHTML="";
											return false;
										}	
										else
										{
											if(bk1[i].checked && bk1[i].value!="")
											{
												document.forms[0].bank.value=bk1[i].value;
												document.forms[0].version.value=document.forms[0].bank.value+"B2B";
											}
										}
									}		
								}else if(div1.style.display==""){	
									var bk=document.getElementsByName("bank3");
									for(var i=0;i<bk.length;i++)
									{
										if(bk[i].checked && i>20)
										{   
											mgamot.innerHTML="您选择的银行还没开通业务！";
											errorMessage.innerHTML="";
											return false;
										}else
										{
											if(bk[i].checked && bk[i].value!="")
											{
											
												document.forms[0].bank.value=bk[i].value;
												document.forms[0].version.value=document.forms[0].bank.value+"B2C";
												
												var indexFlag = bk[i].value.lastIndexOf("ChinaPay");
								  					
												if (indexFlag > 0) {
													document.forms[0].bank.value = "CMBC";
													document.forms[0].version.value="CMBCChinaPay";
												}
											}
										}
									}
								}
								
								//document.getElementById('b1').disabled='true';
								
								document.forms[0].submit();
							}
							else{
							    document.getElementById('b1').disabled='true';
								document.forms[0].submit();
							}
						}
					}, onException:showException});
				}
		}, onException:showException});
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
		
		function getMobileValidateCode(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var validateCodeMsg = document.getElementById("validateCodeMsg");
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=getMobileValidateCode",{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			document.getElementById("bb").disabled ="disabled";
			document.getElementById("autoMsg").style.display = "block";
			validateCodeMsg.innerHTML = "";
			alert("验证码已经发送到你手机上,请查收!");
			}, onException:showException});
		}

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
	<body onload="show_date_time()">
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3" />
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>请确认您购买的商品，并通过钱门付款</strong>
								</p>
							</div>
						</div>
						<c:if test="${transaction.type==3}">
							<p style="margin-bottom: 0; color: #666;">
								--本次交易为
								<em style="font-style: normal; color: #F60;">“担保交易”</em>，付款后您的钱将
								<strong>直接</strong>进入钱门账户。
							</p>
						</c:if>
						<c:if test="${transaction.type==4}">
							<p style="margin-bottom: 0; color: #666;">
								--本次交易为
								<em style="font-style: normal; color: #F60;">“即时到帐交易”</em>，付款后您的钱将
								<strong>直接</strong>进入对方账户。
							</p>
						</c:if>
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
									<div
										style="background: url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">
										数量
									</div>
								</th>
								<th>
									<div
										style="background: url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">
										邮费
									</div>
								</th>
								<th>
									<div>
										折扣/涨价
									</div>
								</th>
								<th>
									<div
										style="background: url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">
										原价
									</div>
								</th>
								<th>
									<div
										style="background: url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">
										应付总价
									</div>
								</th>
							</tr>
							<tr>
								<td>
									<img src="../_img/shouqi.jpg" / id="imgDiv" name="imgDiv">
									<a href="javascript:showAndHideAgentDetail()" name="a1" id="a1"
										title="点击展开详细"><c:out
											value="${transaction.orderDetails.shopName}" /> </a>
								</td>
								<td>
									<fmt:formatNumber pattern="0.00"
										value="${transaction.orderDetails.shopPrice}" />
									元
								</td>
								<td
									style="background: url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">
									<c:out value="${transaction.orderDetails.shopTotal}" />
									件
								</td>
								<td
									style="background: url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">
									<fmt:formatNumber pattern="0.00"
										value="${transaction.orderDetails.emailPrice}" />
									元
									<c:if test="${transaction.type==3}">
               [<b><c:out
												value="${transaction.orderDetails.logistics}" /> </b>]
               </c:if>
								</td>
								<td>
									<c:if test="${transaction.orderDetails.salePrice==null}">
            		0.00
            	</c:if>
									<c:if test="${transaction.orderDetails.salePrice!=null}">
										<fmt:formatNumber pattern="0.00"
											value="${transaction.orderDetails.salePrice}" />
									</c:if>
								</td>
								<td
									style="background: url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">
									<fmt:formatNumber pattern="0.00"
										value="${transaction.orderDetails.shopPrice*(transaction.orderDetails.shopTotal*1.0)}" />
									元
								</td>
								<td
									style="background: url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">
									<fmt:formatNumber pattern="0.00" value="${transaction.amount}" />
									元
								</td>
							</tr>
						</table>

						<div id="agentDetail">
							<table cellpadding="0" cellspacing="0" width="100%"
								class="information_table">
								<tr>
									<td class="right_td">
										商品名称：
									</td>
									<td>
										<c:out value="${transaction.orderDetails.shopName}" />
									</td>
								</tr>
								<tr>
									<td class="right_td">
										卖 家：
									</td>
									<td>
										<c:out value="${transaction.toAgent.name}" />
										<strong>(</strong>
										<c:out value="${transaction.toAgent.loginName}" />
										<strong>)</strong>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										买 家：
									</td>
									<td>
										<c:out value="${transaction.fromAgent.name}" />
										<strong>(</strong>
										<c:out value="${transaction.fromAgent.loginName}" />
										<strong>)</strong>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										交 易 号：
									</td>
									<td>
										<c:out value="${transaction.orderDetails.orderDetailsNo}" />
									</td>
								</tr>
								<tr>
									<td class="right_td">
										交易类型：
									</td>
									<td>
										<c:out value="${transaction.typeCaption}" />
									</td>
								</tr>
								<tr>
									<td class="right_td">
										购买时间：
									</td>
									<td>
										<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss"
											value="${transaction.accountDate}" />
									</td>
								</tr>

								<td class="right_td">
									超时时间：
								</td>
								<td>
									你的交易还有
									<img src="../_img/icon_timeout.gif"
										style="vertical-align: middle; margin: 0 8px;" />
									<strong style="margin-right: 8px;"><label id="timeDiv"></label>
									</strong> 结束
								</td>

								<c:if test="${agentAddressInfo==null}">
									<tr>
										<td class="right_td">
											收货地址：
										</td>
										<td>
											<html:link
												page="/agent/agentaddress.do?thisAction=getAgentAddressManage&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3">设置交易地址</html:link>
										</td>
									</tr>
								</c:if>
								<c:if test="${agentAddressInfo!=null}">
									<tr>
										<td class="right_td">
											收货地址：
										</td>
										<td>
											<c:out value="${agentAddressInfo.address}" />
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<strong><c:out value="${agentAddressInfo.name}" />
											</strong> (收)
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<c:out value="${agentAddressInfo.mtel}" />
											,
											<c:out value="${agentAddressInfo.tel}" />
										</td>
									</tr>
								</c:if>

								<c:if test="${transaction.orderDetails.detailsContent!=null}">
									<tr>
										<td class="right_td">
											商品描述：
										</td>
										<td>
											<c:out value="${transaction.orderDetails.detailsContent}" />
										</td>
									</tr>
								</c:if>
							</table>
						</div>

						<div class="deal_list" style="position: relative;">

							<div id="head1" class="deal_list_head">
								<c:choose>
									<c:when test="${transaction.fromAgent.allowBalance<=0.00}">
										<a id="Show_11" onclick="Show_Menu(1,0,1);"
											href="javascript:void(0)">网上银行付款</a>
									</c:when>
									<c:otherwise>
										<a id="Show_10" onclick="Show_Menu(1,0,1);"
											href="javascript:void(0)" class="at_deal_list_head">钱门余额付款</a>
										<a id="Show_11" onclick="Show_Menu(1,1,1);"
											href="javascript:void(0)">网上银行付款</a>
									</c:otherwise>

								</c:choose>
							</div>

							<div id="head2" class="deal_list_head">
								<a id="Show_20" onclick="Show_Menu(1,0,0);" href="#">数字证书</a>
							</div>


							<div id="DIV0">

								<c:if test="${URI.agent.validCertStatus==1}">
									<!-- 账号够钱付款 -->
									<c:if test="${enoughAmount==1}">
										<html:form
											action="transaction/transaction.do?thisAction=transactionPaymentConfirm"
											method="post">
											<table cellpadding="0" cellspacing="0" width="100%"
												class="information_table">
												<tr>
													<td class="right_td">
														应付总价：
													</td>
													<td>
														<strong style="color: #F60; font-size: 16px;"><fmt:formatNumber
																pattern="0.00" value="${transaction.amount}" /> </strong> 元
													</td>
												</tr>
												<tr>
													<td class="right_td">
														钱门账户余额：
													</td>
													<td>
														<fmt:formatNumber pattern="0.00"
															value="${transaction.fromAgent.allowBalance}" />
														元
													</td>
												</tr>
												<tr>
													<td class="right_td">
														请输入支付密码:
													</td>
													<td>
														<input type="password" class="text_style"
															name="payPassword" id="payPassword" />
														<span id="errorMessage"></span>
													</td>
												</tr>
												<tr style="display: none;" id="autoPassword">
													<td class="right_td">
														&nbsp;
													</td>
													<td>
														<input type="text" class="text_style"
															name="mobileValidateCode" id="mobileValidateCode" />
														<span class="simplyBtn_1"><input
																class="buttom_middle" type="button" value="点击获取动态口令密码"
																name="bb" onclick="getMobileValidateCode();" /> </span>
														<span id="validateCodeMsg"></span>
													</td>
												</tr>
												<tr style="display: none;" id="autoMsg">
													<td class="right_td">
														&nbsp;
													</td>
													<td>
														如果因网络延误导致超过10分钟未能收到验证码,请先点击主页的“退出”链接,然后重新获取
													</td>
												</tr>
												<tr>
													<td>
														&nbsp;
													</td>
													<td>
														<span class="simplyBtn_1"><input id="b1" disabled
																type="button" value="确认无误,付款" class="buttom_middle"
																onclick="checkPayPasswordBeforCommit('<c:out value="${transaction.type}"/>','<c:out value="${agentAddressInfo}"/>')" />
														</span>
													</td>
												</tr>
												<tr>
													<td class="right_td"></td>
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

											<input type="hidden" name="tid" id="tid"
												value="<c:out value="${transaction.id}"/>" />
											<input type="hidden" name="status" id="status"
												value="<c:out value="${transaction.status}"/>" />
											<input type="hidden" name="type" id="type"
												value="<c:out value="${transaction.type}"/>" />
											<input type="hidden" name="amount" id="amount"
												value="<c:out value="${transaction.amount}"/>" />
											<input type="hidden" name="allowBalance" id="allowBalance"
												value="<c:out value="${transaction.fromAgent.allowBalance}"/>" />
											<input type="hidden" name="toAgentId" id="toAgentId"
												value="<c:out value="${transaction.toAgent.id}"/>" />
											<input type="hidden" name="toAgentLoginName"
												id="toAgentLoginName"
												value="<c:out value="${transaction.toAgent.loginName}"/>" />
										</html:form>
									</c:if>
									<!-- 账号不够钱付款  此处开发环境不显示、不起作用，服务器上显示？为什么。。-->
									<c:if test="${enoughAmount==0}">
										<html:form
											action="/transaction/charge.do?thisAction=waitCharge"
											method="post">
											<input type="hidden" name="agentId" id="agentId"
												value="<c:out value="${transaction.fromAgent.id}"/>" />
											<input type="hidden" name="amount" id="amount"
												value="<c:out value="${price}"/>" />
											<!-- 这里是把除了余额款后,还要付多少钱 -->
											<input type="hidden" name="transactionNo" id="transactionNo"
												value="<c:out value="${transaction.no}"/>" />
											<input type="hidden" name="allowBalance" id="allowBalance"
												value="<c:out value="${transaction.fromAgent.allowBalance}"/>" />
											<html:hidden property="version"></html:hidden>
											<html:hidden property="bank"></html:hidden>
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
												<tr>
													<td class="right_td">
														钱门账户余额：
													</td>
													<td>
														<fmt:formatNumber pattern="0.00"
															value="${transaction.fromAgent.allowBalance}" />
														元 &nbsp;您的余额不够完成本次付款。钱门将引导您通过快速充值，完成本次付款。
													</td>

												</tr>
												<tr>
													<td class="right_td">
														&nbsp;
													</td>
													<td>
														<input type="radio" name="r1" value="0" checked="checked" />
														使用网上银行支付：
														<fmt:formatNumber pattern="0.00" value="${price}" />
														元
													</td>
												</tr>

												<tr>
													<td class="right_td">
														<span class="font_style6">*</span> 选择网上银行：
													</td>
													<td>
														<input class="radio" type="radio" name="banben"
															value="personal" checked="checked"
															onclick="sleVersion(1);" />
														个人版
														<input class="radio" type="radio" name="banben"
															value="enterprise" onclick="sleVersion(2);" />
														企业版
													</td>
												</tr>

												<tr>
													<td></td>
													<td colspan="3">
														<div id="versionOfPersona1">
															<table>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="ICBC" checked="checked" />
																		<img src="../_img/bank/bankLogo_1.gif"
																			onclick="check('bank3','ICBC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="CCB" />
																		<img src="../_img/bank/bankLogo_3.gif"
																			onclick="check('bank3','CCB');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="CMBC" />
																		<img src="../_img/bank/bankLogo_9.gif"
																			onclick="check('bank3','CMBC');" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="ABC" />
																		<img src="../_img/bank/bankLogo_4.gif"
																			onclick="check('bank3','ABC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BCM" />
																		<img src="../_img/bank/bankLogo_10.gif"
																			onclick="check('bank3','BCM');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="CITIC" />
																		<img src="../_img/bank/bankLogo_11.gif"
																			onclick="check('bank3','CITIC');" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_13.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_17.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_6.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_18.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_22.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_26.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																</tr>

																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_21.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_23.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_19.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_24.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_25.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_20.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="BOC_ChinaPay" />
																		<img src="../_img/bank/bankLogo_27.gif"
																			onclick="check('bank3','BOC');" />
																	</td>
																</tr>
																<tr>
																	<td colspan="3">
																		<font size="2" color="red">以下银行接口将于近日上线</font>
																	</td>
																</tr>
																<tr style="display: none">
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="" />
																		<img src="../_img/bank/bankLogo_7.gif" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="" />
																		<img src="../_img/bank/bankLogo_8.gif" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="" />
																		<img src="../_img/bank/bankLogo_5.gif" />
																	</td>
																</tr>
																<tr style="display: none">
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="" />
																		<img src="../_img/bank/bankLogo_2.gif" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank0"
																			value="" />
																		<img src="../_img/bank/bankLogo_12.gif" />
																	</td>
																	<td>
																		<input class="radio" type="radio" name="bank3"
																			value="" />
																		<img src="../_img/bank/bankLogo_6.gif" />
																	</td>
																</tr>
															</table>
														</div>
														<div id="versionOfPersona2" style="display: none;">
															<table>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank4"
																			value="ICBC" />
																		<img src="../_img/bank/bankLogo_1.gif"
																			onclick="check('bank4','ICBC');" />
																	</td>
																	<td colspan="2">
																		<input class="radio" type="radio" name="bank4"
																			value="CCB" checked="checked" />
																		<img src="../_img/bank/bankLogo_3.gif"
																			onclick="check('bank4','CCB');" />
																	</td>
																</tr>
																<tr>
																	<td colspan="3">
																		<font size="2" color="red">以下银行接口将于近日上线</font>
																	</td>
																</tr>
																<tr>
																	<td>
																		<input class="radio" type="radio" name="bank4"
																			value="BCM" />
																		<img src="../_img/bank/bankLogo_10.gif"
																			onclick="check('bank4','BCM')" />
																	</td>
																	<td colspan="3">
																		<input class="radio" type="radio" name="bank4"
																			value="CMBC" />
																		<img src="../_img/bank/bankLogo_9.gif"
																			onclick="check('bank4','CMBC');" />
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
												<tr>
													<td class="right_td">
														请输入支付密码:
													</td>
													<td>
														<input type="password" class="text_style"
															name="payPassword" id="payPassword" />
														<span id="errorMessage"></span>
													</td>
												</tr>

												<tr style="display: none;" id="autoPassword">
													<td class="right_td">
														&nbsp;
													</td>
													<td>
														<input type="text" class="text_style"
															name="mobileValidateCode" id="mobileValidateCode" />
														<span class="simplyBtn_1"><input
																class="buttom_middle" type="button" value="点击获取动态口令密码"
																name="bb" onclick="getMobileValidateCode();" /> </span>
														<span id="validateCodeMsg"></span>
													</td>
												</tr>

												<tr style="display: none;" id="autoMsg">
													<td class="right_td">
														&nbsp;
													</td>
													<td>
														如果因网络延误导致超过10分钟未能收到验证码,请先点击主页的“退出”链接,然后重新获取
													</td>
												</tr>

												<tr>
													<td>
														&nbsp;
													</td>
													<td colspan="2">
														<span class="simplyBtn_1"><input id="b2"
																type="button" disabled value="确认无误,付款"
																class="buttom_middle"
																onclick="checkPayPasswordBeforCommit('<c:out value="${transaction.type}"/>','<c:out value="${agentAddressInfo}"/>')" />
														</span>
														<br />
														<div id="msamount" style="color: red"></div>
													</td>

												</tr>

												<tr>
													<td class="right_td"></td>
													<td>
														<div class="ExclaimedMsg">
															<span> 钱门禁止<strong class="R">信用卡套现、银行卡转账、虚假交易</strong>等行为，一经发现将予以处罚，包括但不限于：限制收款、
																冻结账户、永久停止服务，并有可能影响相关信用记录。 </span>
															<p>
																<span> <input type="checkbox" name="ch"
																		onclick="chooseOne(this);document.getElementById('b2').disabled='';" />
																	我同意 <input type="checkbox" name="ch"
																		onclick="chooseOne(this);document.getElementById('b2').disabled='true';" />
																	我不同意 </span>
														</div>
													</td>
												</tr>
											</table>
										</html:form>
									</c:if>
								</c:if>
							</div>

							<!-- ************* -->
							<div id="DIV1">
								<html:form action="/transaction/charge.do?thisAction=waitCharge"
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
										<tr>
											<td class="right_td">
												钱门账户：
											</td>
											<td>
												<c:out value="${transaction.fromAgent.loginName}"></c:out>
											</td>
										</tr>

										<tr>
											<td class="right_td">
												<span class="font_style6">*</span> 选择网上银行：
											</td>
											<td>
												<input class="radio" type="radio" name="banben"
													value="personal" checked="checked" onclick="sleVersion(3);" />
												个人版
												<input class="radio" type="radio" name="banben"
													value="enterprise" onclick="sleVersion(4);" />
												企业版
											</td>
										</tr>

										<tr>
											<td></td>
											<td colspan="3">
												<div id="versionOfPersona3">
													<table>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="ICBC" checked="checked" />
																<img src="../_img/bank/bankLogo_1.gif"
																	onclick="check('bank0','ICBC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="CCB" />
																<img src="../_img/bank/bankLogo_3.gif"
																	onclick="check('bank0','CCB');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="CMBC" />
																<img src="../_img/bank/bankLogo_9.gif"
																	onclick="check('bank0','CMBC');" />
															</td>
														</tr>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="ABC" />
																<img src="../_img/bank/bankLogo_4.gif"
																	onclick="check('bank0','ABC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BCM" />
																<img src="../_img/bank/bankLogo_10.gif"
																	onclick="check('bank0','BCM');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="CITIC" />
																<img src="../_img/bank/bankLogo_11.gif" />
															</td>
														</tr>

														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_13.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_17.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_6.gif"
																	onclick="check('bank0','BOC');" />
															</td>
														</tr>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_18.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_22.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_26.gif"
																	onclick="check('bank0','BOC');" />
															</td>
														</tr>

														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_21.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_23.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_19.gif"
																	onclick="check('bank0','BOC');" />
															</td>
														</tr>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_24.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_25.gif"
																	onclick="check('bank0','BOC');" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_20.gif"
																	onclick="check('bank0','BOC');" />
															</td>
														</tr>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="BOC_ChinaPay" />
																<img src="../_img/bank/bankLogo_27.gif"
																	onclick="check('bank0','BOC');" />
															</td>
														</tr>

														<tr>
															<td colspan="3">
																<font size="2" color="red" colspan="3">以下银行接口将于近日上线</font>
															</td>
														</tr>
														<tr style="display: none">
															<td>
																<input class="radio" type="radio" name="bank0" value="" />
																<img src="../_img/bank/bankLogo_7.gif" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0" value="" />
																<img src="../_img/bank/bankLogo_8.gif" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0" value="" />
																<img src="../_img/bank/bankLogo_5.gif" />
															</td>
														</tr>
														<tr style="display: none">
															<td>
																<input class="radio" type="radio" name="bank0" value="" />
																<img src="../_img/bank/bankLogo_2.gif" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0" value="" />
																<img src="../_img/bank/bankLogo_12.gif" />
															</td>
															<td>
																<input class="radio" type="radio" name="bank0"
																	value="CITIC" />
																<img src="../_img/bank/bankLogo_6.gif"
																	onclick="check('bank0','BCM')" />
															</td>
														</tr>
													</table>
												</div>
												<div id="versionOfPersona4" style="display: none;">
													<table>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank1"
																	value="ICBC" checked="checked" />
																<img src="../_img/bank/bankLogo_1.gif"
																	onclick="check('bank1','ICBC');" />
															</td>
															<td colspan="2">
																<input class="radio" type="radio" name="bank1"
																	value="CCB" />
																<img src="../_img/bank/bankLogo_3.gif"
																	onclick="check('bank1','CCB');" />
															</td>
														</tr>
														<tr>
															<td colspan="3">
																<font size="2" color="red">以下银行接口将于近日上线</font>
															</td>
														</tr>
														<tr>
															<td>
																<input class="radio" type="radio" name="bank1"
																	value="BCM" />
																<img src="../_img/bank/bankLogo_10.gif"
																	onclick="check('bank1','BCM');" />
															</td>
															<td colspan="2">
																<input class="radio" type="radio" name="bank1"
																	value="CMBC" />
																<img src="../_img/bank/bankLogo_9.gif"
																	onclick="check('bank1','CMBC');" />
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
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
											</td>
										</tr>
									</table>

								</html:form>
							</div>

							<div id="DIV2">
								<div class="Worg">
									<div class="attentionTitle attentionTitle1">
										<em>请导入"数字证书"！</em>
									</div>
									<ul class="attentionList">
										<li>
											本台电脑还未导入证书，请用"网上银行付款"或
											<a href="javascript:importCert()">点此导入数字证书</a>。
										</li>
									</ul>
								</div>
							</div>
							<script language="javascript">
							
function importCert(){
		window.location.href="<%=request.getContextPath()%>/security.do?thisAction=importCertificatePage";
}		
					
function check(selbank,ba){
	var bank=document.getElementsByName(selbank);
	for(var i=0;i<bank.length;i++) {  
        if(bank[i].value==ba)
        bank[i].checked=true;  
    } 
 }

	function sleVersion(no){
		var div1=document.getElementById("versionOfPersona1");
		var div2=document.getElementById("versionOfPersona2");
		var div3=document.getElementById("versionOfPersona3");
		var div4=document.getElementById("versionOfPersona4");
		
		if(no==1){
			div1.style.display="";
			div2.style.display="none";
		}
		else if(no==2){
			div1.style.display="none";
			div2.style.display="";
		}
		else if(no==3){
			div3.style.display="";
			div4.style.display="none";
		}
		else if(no==4){
			div3.style.display="none";
			div4.style.display="";
		}
    }


function sendToBank(){
	var mgamot=document.getElementById("mgamount");
	var div2=document.getElementById("versionOfPersona4"); 
	if(div2.style.display=="")
	{
		var bk1=document.getElementsByName("bank1");
		for(var i=0;i<bk1.length;i++)
		{
			if(bk1[i].checked && i>=2)
			{
				mgamot.innerHTML="您选择的银行还没开通业务！";
				return false;
			}	
			else
			{
				if(bk1[i].checked && bk1[i].value!="")
				{
					document.forms[1].bank.value=bk1[i].value;
					document.forms[1].version.value=document.forms[1].bank.value+"B2B";
				}
			}
		}		
	}else{
		var bk=document.getElementsByName("bank0");
		
		for(var i=0;i<bk.length;i++){
			if(bk[i].checked && i>20){
				mgamot.innerHTML="您选择的银行还没开通业务！";
				return false;
			}else{
				if(bk[i].checked && bk[i].value!=""){
					document.forms[1].bank.value=bk[i].value;
					document.forms[1].version.value=document.forms[1].bank.value+"B2C";
					
					var indexFlag = bk[i].value.lastIndexOf("ChinaPay");
	  				// alert(indexFlag);		
					if (indexFlag > 0) {
						document.forms[1].bank.value = "CMBC";
						document.forms[1].version.value="CMBCChinaPay";
					}		
					
				}
			}
		}
	}
	document.forms[1].submit();
}

</script>

						</div>
					</div>
				</div>

			</div>
			<c:import url="/_jsp/footer.jsp" />
			<script language="javascript">
			
  var allowBalance = "<c:out value='${transaction.fromAgent.allowBalance}'/>";

  var validCertStatus="<c:out value="${URI.agent.validCertStatus}"></c:out>";
  if(validCertStatus==1){
    if(allowBalance==0){
    	document.getElementById("DIV0").style.display = "none";
  		document.getElementById("DIV1").style.display = "block";
  		document.getElementById("DIV2").style.display = "none";
  		document.getElementById("head2").style.display = "none";
  		document.getElementById("head1").style.display = "block";
  		document.getElementById("Show_11").className="at_deal_list_head";
  	}
  	else{
  		document.getElementById("DIV1").style.display = "none";
  		document.getElementById("DIV0").style.display = "block";
  		document.getElementById("DIV2").style.display = "none";
  		document.getElementById("head1").style.display = "block";
  		document.getElementById("head2").style.display = "none";
  	}
  }else{
 		
  		document.getElementById("DIV1").style.display = "none";
  		document.getElementById("DIV0").style.display = "none";
  		document.getElementById("DIV2").style.display = "block";
  		document.getElementById("head1").style.display = "none";
  		document.getElementById("head2").style.display = "block";
  		document.getElementById("Show_20").className="at_deal_list_head";
  }
  	
  </script>
	</body>
	<script language="javascript">
	document.getElementById("agentDetail").style.display = "none";
	
	function Show_Menu(tabid_num,tabnum,flag){ //flag表示是否只有一个tab 0 表示只有1个
		if(flag!=0){
			for(var i=0;i<2;i++){
				document.getElementById("Show_"+tabid_num+i).className="";
				if(i==tabnum)
				{
					document.getElementById("DIV"+tabnum).style.display = "block";
				}
				else
				{
					document.getElementById("DIV"+i).style.display = "none";
				}
			}
			document.getElementById("Show_"+tabid_num+tabnum).className="at_deal_list_head";
		}
	}
	</script>
</html>
