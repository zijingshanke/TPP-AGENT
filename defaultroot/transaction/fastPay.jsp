<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/fastPay.jsp -->

  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	
	<script src="../_js/prototype.js"></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript">   
		function checkForm(ptype){
		    var sellerAccount = document.getElementById("sellerAccount");
		    var sellerAccountConfirm = document.getElementById("sellerAccountConfirm");
		    var currentAccount = "<c:out value='${loginAccount}'/>";
		    var totalPriceByDay = "<c:out value='${totalPriceByDay}'/>";
		    var errorEmailMsg = document.getElementById("errorEmailMsg"); 
		    
		    if(sellerAccount.value==currentAccount){
		    	alert("收款方账户不能和自己相同!");
		    	sellerAccount.value = "";
		    	sellerAccountConfirm.value = "";
		    	sellerAccount.focus();
		    	return false;
		    }
		    
		    if(sellerAccount.value.trim()==""){
		    	alert("请输入收款方钱门账户!");
		    	sellerAccount.value = "";
		    	sellerAccount.focus();
		    	return false;
		    }
		    else{
		    	//var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		    	var reg = /^([a-zA-Z0-9]+[-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!sellerAccount.value.match(reg)){
					sellerAccount.value = "";
		    		sellerAccount.focus();
					errorEmailMsg.innerHTML="<font color='red'>Email格式不对！</font>";
					return false;
				}
				else{
					errorEmailMsg.innerHTML = "账户名为E-mail地址";
				}
		    }
		    
		    if(sellerAccountConfirm.value.trim()==""){
		    	alert("请确认收款方钱门账户!");
		    	sellerAccountConfirm.value = "";
		    	sellerAccountConfirm.focus();
		    	return false;
		    }
		    else{
		    	if(sellerAccount.value.trim()!=sellerAccountConfirm.value.trim()){
		    		alert("确认收款方钱门账户不一致!");
		    		sellerAccountConfirm.focus();
		    		return false;
		    	}
		    }
		    
		    if(ptype==0){
		    	var paymentReason = document.getElementById("paymentReason");
		    	var paymentPrice = document.getElementById("paymentPrice");
		    	var detailsContent = document.getElementById("detailsContent");
		    	if(paymentReason.value.trim()==""){
			    	alert("请输入付款原因!");
			    	paymentReason.value = "";
			    	paymentReason.focus();
			    	return false;
		    	}
		    	else{
		    		paymentReason.value = paymentReason.value.trim();
		    	}
		    	
		    	if(paymentPrice.value==""){
			    	alert("请输入付款金额!");
			    	paymentPrice.focus();
			    	return false;
		    	}
		    	else{
		    		var priceMsg = document.getElementById("priceDiv");
				    priceMsg.innerHTML = "";
					var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
					if(!re.test(paymentPrice.value)){
						priceMsg.innerHTML = "金额只能由数字组成且最多带有2位小数";
						paymentPrice.focus();
						return false;
					}
					if(paymentPrice.value<=0.01||paymentPrice.value>10000000.00){
						priceMsg.innerHTML = "金额只能大于0.01及小于10000000.00";
						paymentPrice.focus();
						return false;
					}		
					//暂时保留
					//if(parseFloat(totalPriceByDay)+parseFloat(paymentPrice.value)>2000){
					//	priceMsg.innerHTML = "付款金额最多为每日 2000 元";
					//	return false;
					//}
		    	}
		    	
		    	if(detailsContent.value.trim()==""){
			    	alert("请输入备注!");
			    	detailsContent.value = "";
			    	detailsContent.focus();
			    	return false;
		    	}
		    	else{
		    		detailsContent.value = detailsContent.value.trim();
		    	}
		    }
		    else{
		    	var shopName = document.getElementById("shopName");
		    	var shopPrice = document.getElementById("shopPrice");
		    	var detailsContent = document.getElementById("detailsContent");
				if(shopName.value.trim()==""){
			    	alert("请输入商品或服务名称!");
			    	shopName.value = "";
			    	shopName.focus();
			    	return false;
		    	}
		    	else{
		    		shopName.value = shopName.value.trim();
		    	}	
		    	
		    	if(shopPrice.value==""){
			    	alert("请输入商品金额!");
			    	shopPrice.focus();
			    	return false;
		    	}
		    	else{
		    		var priceMsg = document.getElementById("priceDiv");
				    priceMsg.innerHTML = "";
					var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
					if(!re.test(shopPrice.value)){
						priceMsg.innerHTML = "金额只能由数字组成且最多带有2位小数";
						shopPrice.focus();
						return false;
					}
					if(shopPrice.value<=0.01||shopPrice.value>10000000.00){
						priceMsg.innerHTML = "金额只能大于0.01及小于10000000.00";
						shopPrice.focus();
						return false;
					}
					
					//暂时保留
					//if(parseFloat(totalPriceByDay)+parseFloat(shopPrice.value)>2000){
					//	priceMsg.innerHTML = "付款金额最多为每日 2000 元";
					//	return false;
					//}
		    	}

		    	if(detailsContent.value.trim()==""){
			    	alert("请输入付款说明!");
			    	detailsContent.value = "";
			    	detailsContent.focus();
			    	return false;
		    	}
		    	else{
		    		detailsContent.value = detailsContent.value.trim();
		    	}
		    }
		    
			
			document.forms[0].submit();
		}
		
		function checkPrice(obj){
		    var priceMsg = document.getElementById("priceDiv");
		    priceMsg.innerHTML = "";
			var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
			if(!re.test(obj.value)){
				priceMsg.innerHTML = "金额只能由数字组成且最多带有2位小数";
				//obj.focus();
				return false;
			}
			if(obj.value<=0.01||obj.value>10000000.00){
				priceMsg.innerHTML = "金额只能大于0.01及小于10000000.00";
				//obj.focus();
				return false;
			}
		}
		
		
		function showAndHideContract(){
		    var btn = document.getElementById("b1").name;
		    if(btn=="b1"){
				document.getElementById("contract").style.display = "block";
				document.getElementById("b1").name="b2";
			}
			else if(btn=="b2"){
				document.getElementById("contract").style.display = "none";
				document.getElementById("b1").name="b1";
			}
		}
		
		function selectBuyerEmail(email){
			document.transaction.sellerAccount.value=email;
			document.transaction.sellerAccountConfirm.value=email;
			document.getElementById("contract").style.display = "none";
			document.getElementById("b1").name="b1";
		}
		
		function searchContact(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var searchContactInput = document.getElementById("searchContactInput").value
			if(searchContactInput=="点此输入姓名或账户名"){
				alert("请输入搜索条件");
				return ;
			}
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=getListContact&searchContactInput="+encodeURIComponent(searchContactInput),{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			var contactListDiv = document.getElementById("contactListDiv");
			contactListDiv.innerHTML=result;
			}, onException:showException});
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
	</script>
   
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,0,3"/>
    <html:form method="post"  action="transaction/transaction.do?thisAction=payConfirm">
    <c:if test="${paytype==0}"> 
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title2">
          <span class="font_style7">直接给“亲朋好友”打钱</span> 
          <html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1">交易管理</html:link>
            
            <div class="Attention">
	          	<div class="attentionTitle attentionTitle1">
	            	<em>使用即时到账付款、方便、快捷。</em>
	            </div>
	            <ul class="attentionList">
	            	<li>无论是给异地求学的孩子、远方打工的家人，还是熟悉却难以谋面的朋友，您都可以使用“即时到账付款”将手中的钱立即付至他手中。</li>
	                <li>如果您要付款的对象只是一个陌生的卖家，请谨慎使用“即时到账付款”。 </li>
	                <li>若收款方钱门账户未通过实名认证，收款方将无法使用这笔资金。</li>
	            </ul>
          </div>
		</div>
     </div>
     </div> 
     </div>  
     
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">    
        <div class="main_title">
            <div class="main_title_right">
              <p><strong>填写亲友信息</strong></p>
            </div>
        </div> 
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${loginAccount}"/></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 收款方钱门账户：</td>
              <td class="td_span"><input type="text" class="text_style" style="width:270px;" id="sellerAccount" name="sellerAccount"/> <input type="button" value="从联系人中选取" class="btn2" onclick="showAndHideContract()" name="b1" id="b1"/></td>
            </tr>
            <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span"><div id="errorEmailMsg">账户名为E-mail地址</div></td>
            </tr>
   
    		<tr>
              <td class="right_td"><span class="font_style6">*</span> 确认收款方钱门账户：</td>
              <td class="td_span"><input class="text_style" id="sellerAccountConfirm" name="sellerAccountConfirm" type="text" value="" style="width:270px;" onpaste="return false;"></td>
            </tr>
            
		    <tr>
		    <td class="td_span">&nbsp;</td>
		    <td>
		    	<div id="contract" class="Contact_box" style="margin-left:0">
			    <div class="Contact_title">
				<em>联系人</em> <html:link page="/agent/agentlist.do?thisAction=listContact&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6"><span>编辑</span></html:link>
			        <input type="text" class="text_style" value="点此输入姓名或账户名" onclick="this.value=''" id="searchContactInput" name="searchContactInput" maxlength="25"/>
			        <span class="simplyBtn_1"><input type="button" class="btn1" value="搜索" onclick="searchContact()"/></span>
			    </div>
			    <div id="contactListDiv">
			    	<table cellpadding="0" cellspacing="0" width="100%" class="Contact_table" style="margin-left:0"> 
			      		<tr>
			      		<c:forEach var="agentContact" items="${listContact}" varStatus="status">
			      			<td><a href="javascript:selectBuyerEmail('<c:out value="${agentContact.email}" />')"><c:out value="${agentContact.name}" /></a>(<c:out value="${agentContact.email}" />)</td>
			      			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      			
			      			<c:if test="${status.count mod 2==0}">
			      			<c:out value="<tr><td></td></tr>" escapeXml="false"/>
			      			</c:if>
			      		</c:forEach>
			      		</tr>
			      		<tr>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td align="right">共有<c:out value="${counts}"/>个联系人</td>
			      		</tr>
			      	</table>
			    </div>
			  </td>
		    </tr>  
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>填写付款信息</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 付款原因：</td>
              <td><input type="text" class="text_style" style="width:270px;" id="paymentReason" name="orderDetails.paymentReason" maxlength="50"/></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 付款金额：</td>
              <td><input type="text" class="text_style" id="paymentPrice" name="orderDetails.paymentPrice" style="width:75px;" maxlength="12" onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"/> 元  <div id="priceDiv" style="color: red"></div></td>
            </tr>
            
            <tr>
              <td class="right_td" valign="top";><span class="font_style6">*</span> 备注：</td>
              <td><textarea class="text_style" style="width:405px; height:30px;" name="orderDetails.detailsContent" id="detailsContent"></textarea></td>
            </tr>
            
            <tr>
              <td class="right_td" valign="top";><span class="font_style6">*</span> 收货地址选择：</td>
              <td>
              <select name="orderDetails.consigneeAddress" style="width:205px">
		      	<option value="0">我不需要选择收货地址</option>
				<c:forEach var="agentAddress" items="${listAgentAddress}" varStatus="status">
				<option value="<c:out value="${agentAddress.name}" />,<c:out value="${agentAddress.address}" />,<c:out value="${agentAddress.postCode}" />"><c:out value="${agentAddress.name}" />,<c:out value="${agentAddress.address}" />,<c:out value="${agentAddress.postCode}" /></option>
				</c:forEach>		
			 </select>
      			<span class="td_span"><html:link page="/agent/agentaddress.do?thisAction=getAgentAddressManage" target="_blank">点此添加新的收货地址</html:link> <img alt=“！” src="../_img/zhuyi_ico.gif" class="vcenter" border="0"> 最多只能添加3个地址,添加后请刷新本页面
              </td>
            </tr>
            
            <tr>
              <td>&nbsp;</td>
              <td><input type="button" value="下一步" class="btn1" onclick="checkForm('<c:out value="${paytype}"/>')"/></td>
            </tr>
            
          </table>
        </div>
      </div>
    </div>
  </c:if> 
 
 <!-- 商家信息 -->
 
<c:if test="${paytype==1}"> 
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title2">
          <span class="font_style7">向陌生卖家付款</span> 
          <html:link page="/transaction/transactionlist.do?thisAction=listTransactions&flag=1">交易管理</html:link>
            <div class="Attention Attention1">
		        <div class="attentionTitle attentionTitle1">
		           <em>使用“即时到账”向陌生人付款，请注意交易风险。</em>
		           <p>“即时到账付款”主要适用于家人或好友付款，如果对方只是一个陌生的卖家，我们强烈推荐您使用更为安全的“担保交易付款”。</p>
		        </div>
	      </div>
         </div>
        </div> 
        </div> 
        </div>  
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">    
        <div class="main_title">
            <div class="main_title_right">
              <p><strong>填写商家信息</strong></p>
            </div>
         </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${loginAccount}"/></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 收款方钱门账户：</td>
              <td class="td_span"><input type="text" class="text_style" style="width:270px;" id="sellerAccount" name="sellerAccount"/> <input type="button" value="从联系人中选取" class="btn2" onclick="showAndHideContract()" name="b1" id="b1"/></td>
            </tr>
            <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span"><div id="errorEmailMsg">账户名为E-mail地址</div></td>
            </tr>
   
   			<tr>
              <td class="right_td"><span class="font_style6">*</span> 确认收款方钱门账户：</td>
              <td class="td_span"><input class="text_style" id="sellerAccountConfirm" name="sellerAccountConfirm" type="text" value="" style="width:270px;" onpaste="return false;"></td>
            </tr>
            
		    <tr>
		    <td class="td_span">&nbsp;</td>
		    <td>
		    	<div id="contract" class="Contact_box" style="margin-left:0">
			    <div class="Contact_title">
				<em>联系人</em> <html:link page="/agent/agentlist.do?thisAction=listContact&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6"><span>编辑</span></html:link>
			        <input type="text" class="text_style" value="点此输入姓名或账户名" onclick="this.value=''" id="searchContactInput" name="searchContactInput"/>
			        <span class="simplyBtn_1"><input type="button" class="btn1" value="搜索" onclick="searchContact()"/></span>
			    </div>
			    <div id="contactListDiv">
			    	<table cellpadding="0" cellspacing="0" width="100%" class="Contact_table" style="margin-left:0"> 
			      		<tr>
			      		<c:forEach var="agentContact" items="${listContact}" varStatus="status">
			      			<td><a href="javascript:selectBuyerEmail('<c:out value="${agentContact.email}" />')"><c:out value="${agentContact.name}" /></a>(<c:out value="${agentContact.email}" />)</td>
			      			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      			
			      			<c:if test="${status.count mod 2==0}">
			      			<c:out value="<tr><td></td></tr>" escapeXml="false"/>
			      			</c:if>
			      		</c:forEach>
			      		</tr>
			      		<tr>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td align="right">共有<c:out value="${counts}"/>个联系人</td>
			      		</tr>
			      	</table>
			    </div>
			  </td>
		    </tr>
    
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>填写付款信息</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 商品或服务名称：</td>
              <td><input type="text" class="text_style" style="width:270px;" name="orderDetails.shopName" id="shopName"/></td>
            </tr>
            
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 商品金额：</td>
              <td><input type="text" class="text_style" name="orderDetails.shopPrice" id="shopPrice" style="width:75px;" maxlength="12" onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"/> 元  <div id="priceDiv" style="color: red"></div></td>
            </tr>
            
             <tr>
              <td class="right_td"> 商品展示网址：</td>
              <td><input type="text" class="text_style" name="orderDetails.shopUrl" id="shopUrl" value="http://"/></td>
            </tr>
            
            <tr>
              <td class="right_td" valign="top";><span class="font_style6">*</span> 付款说明：</td>
              <td><textarea class="text_style" style="width:405px; height:30px;" name="orderDetails.detailsContent" id="detailsContent"></textarea></td>
            </tr>
            
            <tr>
              <td class="right_td" valign="top";><span class="font_style6">*</span> 收货地址选择：</td>
              <td>
              <select name="orderDetails.consigneeAddress" style="width:205px">
		      	<option value="0">我不需要选择收货地址</option>
				<c:forEach var="agentAddress" items="${listAgentAddress}" varStatus="status">
				<option value="<c:out value="${agentAddress.name}" />,<c:out value="${agentAddress.address}" />,<c:out value="${agentAddress.postCode}" />"><c:out value="${agentAddress.name}" />,<c:out value="${agentAddress.address}" />,<c:out value="${agentAddress.postCode}" /></option>
				</c:forEach>		
			 </select>
      			<span class="td_span"><html:link page="/agent/agentaddress.do?thisAction=getAgentAddressManage" target="_blank">点此添加新的收货地址</html:link> <img alt=“！” src="../_img/zhuyi_ico.gif" class="vcenter" border="0"> 最多只能添加3个地址,添加后请刷新本页面</span> 
              </td>
            </tr>
            
            <tr>
              <td>&nbsp;</td>
              <td><input type="button" value="下一步" class="btn1" onclick="checkForm('<c:out value="${paytype}"/>')"/></td>
            </tr>
            
          </table>
        </div>
      </div>
    </div>
</c:if>  

 <input type="hidden" name="ptype" value="<c:out value="${paytype}"/>"/>
 <input type="hidden" name="tabMenuList" value="<c:out value="${tabMenuList}"/>"/>
 <input type="hidden" name="subMenuList" value="<c:out value="${subMenuList}"/>"/>
 
	 <script language="javascript">
		document.getElementById("contract").style.display = "none";
	</script>
</html:form>
<c:import url="/_jsp/footer.jsp"/>
</div>

  </body>
</html>
