<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionBySeller.jsp -->

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
		
		function totalPrice(){
			var emailPriceMsg = document.getElementById("emailDiv");
			emailPriceMsg.innerHTML = "";
			var shopPrice = document.getElementById("shopPrice").value;
			var shopTotal = document.getElementById("shopTotal").value;
			var emailPrice = document.getElementById("emailPrice").value;

			var total = parseFloat(shopPrice*shopTotal*100) + parseFloat(emailPrice*100);
			total = Math.round(total);
			total = total/100;
	
			document.getElementById("totalFee").innerHTML=total + " 元";
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
			var errorEmailMsg = document.getElementById("errorEmailMsg");
			errorEmailMsg.innerHTML = "";
			document.transaction.buyerAccount.value=email;
			document.getElementById("contract").style.display = "none";
			document.getElementById("b1").name="b1";
		}
		
		function checkForm(){
			var buyerAccount = document.getElementById("buyerAccount");
			var shopName = document.getElementById("shopName");
			var shopPrice = document.getElementById("shopPrice");
			var shopTotal = document.getElementById("shopTotal");
			var shopUrl = document.getElementById("shopUrl");
			var detailsContent = document.getElementById("detailsContent");
			var emailPrice = document.getElementById("emailPrice");
			var emailPriceUndertake = document.getElementById("emailPriceUndertake");
			var shopPriceMsg = document.getElementById("shopPriceDiv");
			var shopTotolMsg = document.getElementById("shopTotalDiv");
			var emailPriceMsg = document.getElementById("emailDiv");
			var errorEmailMsg = document.getElementById("errorEmailMsg");
			var TR1 = document.getElementById("TR1");
			var TR2 = document.getElementById("TR2");
			shopPriceMsg.innerHTML = "";
			shopTotolMsg.innerHTML = "";
			emailPriceMsg.innerHTML = "";
			var currentAccount = "<c:out value='${loginAccount}'/>";
		    
		    if(buyerAccount.value.trim()==""){
		    	alert("请输入买家钱门账户!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
		    
		    if(buyerAccount.value.toLowerCase()==currentAccount.toLowerCase()){
		    	alert("买家账户不能和自己相同!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
		   /* else{
		    	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!buyerAccount.value.match(reg)){
					buyerAccount.value = "";
		    		buyerAccount.focus();
					errorEmailMsg.innerHTML="<font color='red'>Email格式不对！</font>";
					return false;
				}
				else{
					errorEmailMsg.innerHTML = "账户名为E-mail地址";
				}
		    }*/
	
		    
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
		    	alert("请输入单价!");
		    	shopPrice.focus();
		    	return false;
		    }
		    else{
		    	var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				if(!re.test(shopPrice.value)){
					shopPriceMsg.innerHTML = "单价只能由数字组成且最多带有2位小数";
					shopPrice.focus();
					return false;
				}
				if(shopPrice.value<=0.01||shopPrice.value>1000000.00){
					shopPriceMsg.innerHTML = "单价只能大于0.01及小于1000000.00";
					shopPrice.focus();
					return false;
				}
		    }
  
		    if(shopTotal.value==""){
		    	alert("请输入数量!");
		    	shopTotal.focus();
		    	return false;
		    }
		    else{
		    	if(shopTotal.value<=0||shopTotal.value>10000){
		    		shopTotolMsg.innerHTML = "数量必须是大于0件，小于10000件";
		    		shopTotal.focus();
		    		return false;
		    	}
		    }
    
		    if(detailsContent.value.trim()==""){
		    	alert("请输入商品说明!");
		    	detailsContent.value = "";
		    	detailsContent.focus();
		    	return false;
		    }
		    else{
		    	detailsContent.value = detailsContent.value.trim();
		    }
		    
		    if(TR2.style.display != "none"){
			    if(emailPriceUndertake.value!="1"){
			        if(emailPrice.value=="" || emailPrice.value=="0.00"){
				    	alert("请输入邮费!");
				    	emailPrice.focus();
				    	return false;
			    	}
			    	else{
			    		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
						if(!re.test(emailPrice.value)){
							emailPriceMsg.innerHTML = "邮费只能由数字组成且最多带有2位小数";
							emailPrice.focus();
							return false;
						}
						if(emailPrice.value<=0.01||emailPrice.value>1000000.00){
							emailPriceMsg.innerHTML = "邮费只能大于0.01及小于1000000.00";
							emailPrice.focus();
							return false;
						}
			    	}
			    }
		    }
		    
		    var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var buyerAccount = document.getElementById("buyerAccount").value
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkAgent&agentName="+encodeURI(buyerAccount),{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			if(result==0){
				errorEmailMsg.innerHTML="<font color=red>钱门账户不存在!</font>";
			}
			else{	
				document.transaction.submit();
			}
			}, onException:showException});
		}
		
		function showAndHideEmailPrice(){
			var emailPriceUndertake = document.getElementById("emailPriceUndertake").value;
			var emailPrice = document.getElementById("emailPrice");
			if(emailPriceUndertake=="0"){
				document.getElementById("emailPriceDiv").style.display = "block";
				document.getElementById("emailPriceDiv2").style.display = "block";
				emailPrice.value = "0.00";
			}
			else if(emailPriceUndertake=="1"){
				document.getElementById("emailPriceDiv").style.display = "none";
				document.getElementById("emailPriceDiv2").style.display = "none";
				emailPrice.value = "0.00";
			}
		}
		
		function hidEmail(){
			var logType = document.getElementById("logType").value;
			var TR1 = document.getElementById("TR1");
			var TR2 = document.getElementById("TR2");
			var emailPrice = document.getElementById("emailPrice");
			if(logType=="2"){
				TR1.style.display = "none";
				TR2.style.display = "none";
				emailPrice.value = "0.00";
			}
			else{
				TR1.style.display = "block";
				TR2.style.display = "block";
				emailPrice.value = "0.00";
			}
		}
		
	</script>
  
  </head>
  
  <body>
     <div id="warp">
      <c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,0,3"/>
     <html:form method="post" action="transaction/transaction.do?thisAction=addTransactionSeller">
  <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title" id="mainDiv">
            <div class="main_title_right">
              <p><strong>您的个人信息</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${loginAccount}"/></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 买家钱门账户：</td>
              <td><input type="text" style="width:270px;" id="buyerAccount" name="buyerAccount" class="text_style"/> <input type="button" value="从联系人中选取" class="btn2" onclick="showAndHideContract()" name="b1" id="b1"/></td>
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
              <p><strong>填写商品信息</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 商品或服务名称：</td>
              <td><input type="text" style="width:270px;" id="shopName" name="orderDetails.shopName" class="text_style"/></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 单价：</td>
              <td><input type="text" id="shopPrice" name="orderDetails.shopPrice" style="width:75px;" class="text_style" onblur="totalPrice()" maxlength="12" onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')" /> 元  <div id="shopPriceDiv" style="color: red"></div></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 数量：</td>
              <td><input type="text" class="text_style" style="width:75px;" id="shopTotal" name="orderDetails.shopTotal" onblur="totalPrice()" maxlength="4" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false"/> 件 <div id="shopTotalDiv" style="color: red"></div>
              </td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6"></span> 商品展示网址：</td>
              <td><input type="text" class="text_style" style="width:270px;" id="shopUrl" name="orderDetails.shopUrl" value="http://"/></td>
            </tr>
            <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span">建议填写向买家展示商品的实物图片链接，以免买家收到商品后因描述不符申请退款</td>
            </tr>
            <tr>
              <td class="right_td" valign="top";><span class="font_style6">*</span> 商品描述：</td>
              <td><textarea class="text_style" style="width:405px; height:30px;" id="detailsContent" name="orderDetails.detailsContent"></textarea></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style=" height:160px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>填写物流信息</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 选择物流方式：</td>
              <td><select style="width:105px" name="orderDetails.logisticsType" id="logType" onchange="hidEmail();totalPrice();">
              <option value="0" > 快递</option>
		      <option value="1" > 平邮</option>
		      <option value="2" > 不需要运输</option>
              </select></td>
            </tr>
            <tr id="TR1">
              <td class="right_td"><span class="font_style6">*</span> 选择邮费承担方：</td>
              <td><select style="width:105px" name="orderDetails.emailPriceUndertake" id="emailPriceUndertake" onchange="showAndHideEmailPrice()">
              <option value="0"> 买家承担邮费</option>
	  		  <option value="1"> 卖家承担邮费</option>
              </select></td>
            </tr>
            <tr id="TR2">
              <td class="right_td"><div id="emailPriceDiv"><span class="font_style6" >*</span> 邮费：</div></td>
              <td><div id="emailPriceDiv2"><input type="text" class="text_style" style="width:60px;" value="0.00" id="emailPrice" name="orderDetails.emailPrice" onblur="totalPrice()" maxlength="12" onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"/> 元  <div id="emailDiv" style="color: red"></div></div></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style=" height:160px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>确定总价</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td style="width:95px;">&nbsp;</td>
              <td>总价： <span class="Result"  id="totalFee">0.00 元 </span></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><p class="td_p">创建“担保交易”收款后，系统会通过邮件通知买家，并通过钱门完成本次交易</p></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><input type="button" value="确 定" class="btn1" onclick="checkForm()"/></td>
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
