<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- fillinAccountInfo-->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script type="text/javascript" src="../_js/jquery-1.3.1.min.js"></script>
		<script type="text/javascript">
 	 var selectPId="<c:out value="${selectPid}"></c:out>";
 	 var selectCId="<c:out value="${selectCid}"></c:out>";
 	 var selectBId="<c:out value="${selectBid}"></c:out>";
  	$(document).ready(function(){
  		if(selectBId==""||selectBId==null){
  		document.getElementById("bankId").value=1;
  		}else{
  		document.getElementById("bankId").value=selectBId;
  		}
  		getProvinces();  		
	});
  	
  	function selectProvince(val){
		$("#citys").empty();
		getCitys(val);		
  	}	
  	function getCitys(val){
  	var selectValue=0;
  		$.ajax({
   		type: "POST",
   		url: "../agent/agent.do?thisAction=getJosnCity",
  		 data: "pId="+val,
  		 dataType:"josn",
  		 success: function(data){
  		 	var json=eval('('+data+')'); 
		  		$(json.citys).each(function(i){ 
				if(selectCId==json.citys[i].id){
		  			$("#citys").append("<option value="+json.citys[i].id+" selected='selected'>"+json.citys[i].cname+"</option>");
		  			selectValue=selectCId;
		  			selectCId=0;
		  			}else{
					$("#citys").append("<option value="+json.citys[i].id+">"+json.citys[i].cname+"</option>");
					}
				});		
  		} 
		}); 
  	}  	
  		
  	function getProvinces(){
  	var selectValue=0;
  		$.ajax({
   		type: "POST",
   		url: "../agent/agent.do?thisAction=getJosnProvince",
  		 dataType:"josn",
  		 success: function(data){
  		 	var json=eval('('+data+')'); 
		  		$(json.provinces).each(function(i){ 
		  			if(selectPId==json.provinces[i].id){
		  			$("#provinces").append("<option value="+json.provinces[i].id+" selected='selected'>"+json.provinces[i].cname+"</option>");
		  				selectValue=selectPId;
		  				selectPId=0;
		  			}else{
					$("#provinces").append("<option value="+json.provinces[i].id+">"+json.provinces[i].cname+"</option>");
					}
				});
				if(selectValue>0){
					selectProvince(selectValue);
				}else{
				selectProvince(json.provinces[0].id);}
  		} 
		}); 
  		}
  	 function check(){
  	 //开户行名称以银行名称开头  	 
        var idxBank = document.getElementById("bankId").selectedIndex;
   	    var bankName = document.getElementById("bankId").options[idxBank].text;
        var accountAddress =document.getElementById("accountAddress").value;
        
  	   var ck=/\D/g;
  	 	if(document.getElementById("cardNo").value==""){
  	 		document.getElementById("cardNoError").innerHTML="账号不能为空!";
  	 		return false;
  	 	}
  	 	if(accountAddress==""){
  	 		document.getElementById("accountAddressError").innerHTML="开户银行网点不能为空!";
  	 		return false;
  	 	}
  	 	if(accountAddress.indexOf(bankName) != 0){
  	 	   document.getElementById("accountAddressError").innerHTML="开户行名称必须以银行名称开头！";
  	 	   return false;
  	 	}
  	 	if(accountAddress.length <= bankName.length){
  	 		document.getElementById("accountAddressError").innerHTML="开户行名称填写错误！（例：中国工商银行珠海拱北支行）";
	  	 	return false;
  	 	}
  	 	if(document.getElementById("tempBankNum").value==""){
  	 		document.getElementById("cardNoError").innerHTML="重复账号不能为空!";
  	 		return false;
  	 	}
  	 	
  	 	if(document.getElementById("cardNo").value!==document.getElementById("tempBankNum").value){
  	 		document.getElementById("cardNoError").innerHTML="两次输入的账号不相同！";
  	 		return false;
  	 	}
  		document.forms[0].submit();
  	}
  </script>
	</head>
	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>成功提交</strong><a href="agent.do?thisAction=viewMyAgent"
										class="a_style1">返回我的账户</a>
								</p>
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" width="100%"
							class="information_table">
							<tr>
								<td class="right_td">
									账户名：
								</td>
								<td>
									<span class="font_style2"><c:out
											value="${URI.agent.loginName}"></c:out> </span>
								</td>
							</tr>
							<tr>
								<td class="right_td" valign="top">
									已添加银行：
								</td>
								<td class="banner_td">
									<c:if test="${accounts!=null}">
										<c:forEach var="account" items="${accounts}">
											<p>
												<c:out value="${account.bankName}"></c:out>
												:
												<c:out value="${account.cardNo}"></c:out>
											</p>
										</c:forEach>
									</c:if>
									<c:if test="${accounts eq null}">暂无</c:if>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid" style="padding: 5px 8px;">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>设置银行账户信息</strong>
								</p>
							</div>
						</div>
						<html:form action="agent/agent.do?thisAction=newAccount">
							<html:hidden property="id" value="${URI.agent.id}" />
							<table cellpadding="0" cellspacing="0" width="80%"
								class="information_table">
								<tr>
									<td class="right_td" width="18%">
										银行开户名：
									</td>
									<td width="82%">
									<c:if test="${URI.agent.registerType==0}">
										<c:out value="${URI.agent.name}"></c:out>
									</c:if>
									<c:if test="${URI.agent.registerType==1}">
										<c:out value="${URI.agent.companyName}"></c:out>
									</c:if>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										银行名称：
									</td>
									<!-- 见com.fordays.fdpay.base.Bank  -->
									<td>
										<select name="bankId" id="bankId">
										<option value="1">中国建设银行</option>
										<option value="2">中国农业银行</option>
										<option value="3">中国工商银行</option>
										<!-- 
										<option value="4">中国银行</option>
										-->
										<option value="5">中国民生银行</option>
										<option value="9">交通银行</option>
										<option value="11">中信银行</option>
											<!-- 
										<option value="6">招商银行</option> 
										<option value="7">兴业银行</option>
										<option value="8">北京银行</option>
										<option value="10">中国光大银行</option>
										<option value="13">广东发展银行</option>
										<option value="14">上海浦东发展银行</option>
										<option value="15">深圳发展银行</option>	
										-->
										</select>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										开户银行所在省份：
									</td>
									<td>
										<select id="provinces" name="provincesId"
											onchange="selectProvince(this.value)">
										</select>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										开户银行所在城市：
									</td>
									<td>
										<select id="citys" name="citysId">
										</select>
									</td>
								</tr>
								<tr>
									<td class="right_td"></td>
									<td class="td_span">
										例如：中国工商银行珠海拱北支行；如果您无法确定，建议您致电银行客服询问。
									</td>
								</tr>
								<tr>
									<td class="right_td">
										开户行网点名称:
									</td>
									<td>
										<html:text property="account.accountAddress"
											styleId="accountAddress" styleClass="text_style" />
										<font color="red" id="accountAddressError"></font>
									</td>
								</tr>
								<tr>
									<td class="right_td"></td>
									<td>
										<font color="red">此名称要和开户名、卡号相匹配，如果输入错误将提现失败！请仔细核对！！</font>
									</td>
								</tr>
								<tr>
								
									<td class="right_td">
										<c:if test="${URI.agent.registerType==0}">个人</c:if>
										<c:if test="${URI.agent.registerType==1}">企业</c:if>																				
										银行账号：
									</td>
									<td>
										<html:text property="account.cardNo" styleId="cardNo" styleClass="text_style" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></html:text>
										
										<font color="red" id="cardNoError"></font>
									</td>
								</tr>
								<tr>
									<td class="right_td">
										请再输入一遍：
									</td>
									<td>
										<input type="text" id="tempBankNum" class="text_style"
											name="tempBankNum" onpaste="return false;" value="" />
										<font color="red"> <c:if
												test="${error eq 'existentCardNo'}">
										    此银行帐号已被使用
										</c:if> </font>
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">
										<input type="button" value="下一步" class="btn1"
											onclick="return check();" />
									</td>
								</tr>
							</table>
						</html:form>
					</div>
				</div>
			</div>
			<c:import url="/_jsp/footer.jsp" />
		</div>
	</body>
</html>
