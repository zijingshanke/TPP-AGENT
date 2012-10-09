<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionDun.jsp -->
	<head>
<title>钱门支付！--网上支付！安全放心！</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/prototype.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script language="javascript">
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
		
		function searchContact(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var searchContactInput = document.getElementById("searchContactInput").value;
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
		
		function selectBuyerEmail(email){
			document.transaction.buyerAccount.value=email;
			document.getElementById("contract").style.display = "none";
			document.getElementById("b1").name="b1";
		}
		
		function checkPrice(obj){
		    var msg = document.getElementById("shopPriceDiv");
		    msg.innerHTML= "";
			var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
			if(!re.test(obj.value)){
				msg.innerHTML= "金额只能由数字组成且最多带有2位小数";
				obj.focus();
				return false;
			}
			if(obj.value<=0.01||obj.value>1000000.00){
				msg.innerHTML= "金额必须是大于0.01及小于1000000.00的数字";
				obj.focus();
				return false;
			}
		}
		
		function checkForm(){
			var buyerAccount = document.getElementById("buyerAccount");
			var shopName = document.getElementById("shopName");
			var shopPrice = document.getElementById("shopPrice");
			var detailsContent = document.getElementById("detailsContent");
			var errorEmailMsg = document.getElementById("errorEmailMsg");
			var msg = document.getElementById("shopPriceDiv");
		    msg.innerHTML= "";
			
			var currentAccount = "<c:out value='${loginAccount}'/>";
		    
		    if(buyerAccount.value.trim()==""){
		    	alert("请输入付款方钱门账户!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
		    else{
		    	var reg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 
				if(!buyerAccount.value.match(reg)){
					buyerAccount.value = buyerAccount.value.trim();
		    		buyerAccount.focus();
					errorEmailMsg.innerHTML="<font color='red'>Email格式不对！</font>";
					return false;
				}
				else{
					errorEmailMsg.innerHTML = "账户名为E-mail地址";
				}
		    }
		    
		    if(buyerAccount.value.toLowerCase()==currentAccount.toLowerCase()){
		    	alert("买家账户不能和自己相同!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
 
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
		    	alert("请输入收款金额!");
		    	shopPrice.focus();
		    	return false;
		    }
		    else{
		    	var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				if(!re.test(shopPrice.value)){
					msg.innerHTML= "金额只能由数字组成且最多带有2位小数";
					shopPrice.focus();
					return false;
				}
				if(shopPrice.value<=0.01||shopPrice.value>1000000.00){
					msg.innerHTML = "单价只能大于0.01及小于1000000.00";
					shopPrice.focus();
					return false;
				}
		    }
		    
		    if(detailsContent.value.trim()==""){
		    	alert("请输入收款说明!");
		    	detailsContent.value = "";
		    	detailsContent.focus();
		    	return false;
		    }
		    else{
		    	detailsContent.value = detailsContent.value.trim();
		    }
		    
		    var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var buyerAccount = document.getElementById("buyerAccount").value
				var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkAgent&agentName="+encodeURI(buyerAccount),
								{
									method:"post",
								    onComplete:function (originalRequest) {
									var result = originalRequest.responseText;
									if(result==0){						
										 var agentAjax=new Ajax.Request("../agent/agent.do?thisAction=register&newEmail="+encodeURI(buyerAccount),
										 	{
										 		method:"post",
		 										onComplete:function(originalRequest){
		 										var result2 = originalRequest.responseText;
		 										if(result2==1){	
		 												document.forms[0].submit();
		 											}			
												}
		 									});
									}
									else{
										document.forms[0].submit();
									}							
									}, onException:showException
								}); 
							}
	</script>
		
	</head>

	<body>
		<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,1,3" />
	<html:form method="post" action="transaction/transaction.do?thisAction=addTransactionSeller">
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>即时到账收款</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${loginAccount}" /></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 付款方钱门账户：</td>
              <td><input type="text" class="text_style" style="width:270px;" id="buyerAccount" name="buyerAccount"/> <input type="button" value="从联系人中选取" class="btn2" onclick="showAndHideContract()" name="b1" id="b1"/></td>
            </tr>
            <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span"><div id="errorEmailMsg">账户名为E-mail地址</div></td>
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
			  </div>
			  </td>
		    </tr>
		    
            <tr>
              <td class="right_td"><span class="font_style6">* </span>商品或服务名称：</td>
              <td><input type="text" class="text_style" style="width:270px;" name="orderDetails.shopName" id="shopName"/></td>
            </tr>   
            <tr>
              <td class="right_td"><span class="font_style6">* </span>收款金额：</td>
              <td><input type="text" class="text_style" style="width:70px;" name="orderDetails.shopPrice" id="shopPrice" maxlength="12"  onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"/> 元  <div id="shopPriceDiv" style="color: red"></div></td>
            </tr> 
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 类型：</td>
              <td><select style="width:105px" name="orderDetails.buyType">
              	<option value="1">
					商品购买
				</option>
				<option value="2">
					服务购买
				</option>
				<option value="3">
					网络拍卖
				</option>
              </select></td>
            </tr>
            <tr>
              <td class="right_td">商品展示网址：</td>
              <td><input type="text" class="text_style" style="width:270px;" name="orderDetails.shopUrl"/></td>
            </tr>
            <tr>
              <td class="right_td" valign="top";><span class="font_style6">*</span> 收款说明：</td>
              <td><textarea class="text_style" style="width:405px; height:65px;" id="detailsContent" name="orderDetails.detailsContent"></textarea></td>
            </tr>
            <tr>
              <td colspan="2" style="padding-left:60px;"><input type="button" value="确 定" class="btn1" onclick="checkForm()"/></td>
            </tr>
          </table>
        </div>
      </div>
    </div>

	<script language="javascript">
		document.getElementById("contract").style.display = "none";
	</script>
     <input type="hidden" name="ptype" value="<c:out value="${paytype}"/>"/>
	 <input type="hidden" name="tabMenuList" value="<c:out value="${tabMenuList}"/>"/>
	 <input type="hidden" name="subMenuList" value="<c:out value="${subMenuList}"/>"/>
  </html:form>
 <c:import url="/_jsp/footer.jsp"/>
</div>
  </body>
</html>

