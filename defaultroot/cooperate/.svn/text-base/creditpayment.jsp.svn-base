<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<jsp:useBean id="GatewayForm" scope="request"
	type="com.fordays.fdpay.cooperate.action.GatewayForm" />
<!--JSP页面: cooperate/creditpayment.jsp -->

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
	
		<script src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/prototype.js"></script>
		<script src="../_js/certUtil.js"></script>
		
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
	function valiagents(email,i){
		$.get("../cooperate/gateway.do", { service: "validCert", email: email },function(data){
			if(data=="该钱门账户不存在！"){
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
			document.getElementById("bbbb").style.display = "none";
			document.getElementById("head2").style.display = "none";
			document.getElementById("dummy_payment").style.display = "block";
			document.getElementById("head1").style.display = "block";
		}else{
			document.getElementById("head1").style.display = "none";
			document.getElementById("dummy_payment").style.display = "none";
			document.getElementById("head2").style.display = "block";
			document.getElementById("bbbb").style.display = "block";	
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
											value="${GatewayForm.partnerInfo.name}" />&nbsp;的即时到账交易，直接付款给商家。</strong>
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
							<div class="deal_list_head">
									<a href="#" id="tab_1" onclick="choosePayment(1)"
										class="at_deal_list_head">钱门余额付款</a>
							</div>
								<!-- 钱门余额付款 START -->
								<div class="deal_list_count deal_list_count_1"
									id="dummy_payment">
									<br /><html:form action="/cooperate/gateway.do" method="post"
										onsubmit="return goPayment()">
										
										
										<table cellpadding="0" cellspacing="0" width="100%"
											class="information_table">
											<tr>
												<td class="right_td">
													应付总价：
												<br /></td>
												<td>
													<strong style="color: #F60; font-size: 16px;">  </strong>
												<br /></td>
											</tr>
											<tr>
												<td class="right_td">
													请输入钱门账户：
												<br /></td>
												<td>
													<html:text property="buyerEmail" styleId="payemail1" indexed="1" value="${GatewayForm.buyerEmail}">
													</html:text>
													<em
														style="font-style: normal; color: #999; font-size: 12px; margin-left: 12px;">(账户名为E-mail地址）</em>
												<br /></td>
											</tr>
											<tr>
												<td class="right_td">
													请输入钱门的支付密码：
												<br /></td>
												<td>
													<html:password property="password" indexed="1"  onmousedown="valiagent1()"
														styleClass="text_style2"></html:password>
													<em
														style="font-style: normal; font-size: 12px; margin-left: 12px;"><a
														href="<%=request.getContextPath()%>/agent/agent.do?thisAction=login">找回支付密码</a>
													</em>
												<br /></td>
											</tr>
											<tr>
												<td><br /></td>
												<td>
													<span class="simplyBtn_1" style="margin: 0"><input
															type="submit" style="cursor: pointer" id="b1"
															disabled="disabled" class="buttom_middle" value="确认无误，付款" />
													</span>
												<br /></td>
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
									<!-- 未安装数字证书 -->
								<div id="bbbb" style="display:none">
									<div id="dddd" class="Worg">
										<div class="attentionTitle attentionTitle1">
											<em>请导入"数字证书"！</em>
										</div>
										<ul class="attentionList">
											<li>
												本台电脑还未导入证书，请用"网上银行付款"或
												<a href="javascript:importCert()">点此登陆钱门导入数字证书</a>。
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
//var house = "<c:out value='${GatewayForm.requestType}'/>";
var doc=document;

//if(house!='HOUSE_PERPORTY_REQUEST')
//	choosePayment(2);

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
		document.getElementById('b1').disabled='true';
		service.value="direct_credit_payment";
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
</script>