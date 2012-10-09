<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<jsp:useBean id="GatewayForm" scope="request"
	type="com.fordays.fdpay.cooperate.action.GatewayForm" />
<!--JSP页面: cooperate/creditrepayment.jsp -->

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
											value="${GatewayForm.partnerInfo.name}" />&nbsp;的信用还款。</strong>
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
							</tr>
							<tr>
								<td>
									<a href="#" id="TradeLink" onclick="showTradeInfo()"> <img
											src="../_img/zhankai.jpg" class="vcenter" id="moreimg"
											alt="查看此商品的详细信息" border="0" /> <c:out
											value="${GatewayForm.subject}" /> </a>
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
										交易类型：
									</td>
									<td>
										<img src="../_img/fdpay.gif" style="margin-right: 6px;" alt="" />
										信用还款
									</td>
								</tr>
							</table>
						</div>
						<div class="deal_list" style="position: relative;">
							<div class="deal_list_head">
									<a href="#" id="tab_1" onclick="choosePayment(1)"
										class="at_deal_list_head">钱门信用支付</a>
							</div>
								<!-- 钱门余额付款 START -->
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
													还款金额：
												</td>
												<td>
													<strong style="color: #F60; font-size: 16px;"> <fmt:formatNumber
															value="${GatewayForm.repaymentAmount}" type="currency"
															pattern="0.00元" /> </strong>
												</td>
											</tr>
											<tr>
												<td class="right_td">
													圈名：
												</td>
												<td>
												<c:out value="${GatewayForm.coterie.name}"></c:out>
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
		
		if(!required(password)){
			alert('请您输入钱门的支付密码');
			password.focus();
			return false;
		}
		service.value="credit_repayment";
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