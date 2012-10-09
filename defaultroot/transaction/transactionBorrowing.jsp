<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionBorrowing.jsp -->
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
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
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
			var shopPrice = document.getElementById("shopPrice");
			var detailsContent = document.getElementById("detailsContent");
			var errorEmailMsg = document.getElementById("errorEmailMsg");
			var borrowingDate=document.getElementById("borrowingDate");
			var paymentReason=document.getElementById("paymentReason");
			var msg = document.getElementById("shopPriceDiv");
			var payPassword=document.getElementById("payPassword");
		    msg.innerHTML= "";
			
			var currentAccount = "<c:out value='${agnet.loginName}'/>";
			var allowBalance = "<fmt:formatNumber value='${agent.allowBalance}' pattern='0.00' />";
		    
		    if(buyerAccount.value.trim()==""){
		    	alert("请输入借款方钱门账户!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
		    else{
		    	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
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
		    	alert("借款账户不能和自己相同!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
 
		    
		    if(shopPrice.value==""){
		    	alert("请输入借款金额!");
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
				if(shopPrice.value<=0.01 || parseFloat(shopPrice.value)>parseFloat(allowBalance)){
					msg.innerHTML = "单价只能大于0.01及小于账户可用金额！";
					shopPrice.focus();
					return false;
				}
		    }
		    
		    if(paymentReason.value.trim()==""){
		    	alert("请输入借款原因!");
		    	paymentReason.value = "";
		    	paymentReason.focus();
		    	return false;
		    }
		    else{
		    	paymentReason.value = paymentReason.value.trim();
		    }
		    if(detailsContent.value.trim()==""){
		    	alert("请输入借款备注!");
		    	detailsContent.value = "";
		    	detailsContent.focus();
		    	return false;
		    }
		    if(payPassword.value==""){
		    	alert("请输入支付密码!");
		    	payPassword.value = "";
		    	payPassword.focus();
		    	return false;
		    }
		    else{
		    	detailsContent.value = detailsContent.value.trim();
		    }
		   
		    
		    /*var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
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
								}); */
					if(confirm("确定后，您将会借给"+buyerAccount.value+"( "+shopPrice.value+" ) 元，您确认吗？")){
						document.forms[0].submit();
					}
				}
	</script>
		
	</head>

	<body>
		<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,3,3" />
	<html:form method="post" action="transaction/transaction.do?thisAction=addTransactionBorrowing">
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>借款给朋友</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${agent.loginName}" /></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 借款方钱门账户：</td>
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
              <td class="right_td"><span class="font_style6">* </span>借款原因：</td>
              <td><input type="text" class="text_style" name="orderDetails.paymentReason" id="paymentReason" /></td>
            </tr> 
            <tr>
              <td class="right_td"><span class="font_style6">* </span>借款金额：</td>
              <td><input type="text" class="text_style" style="width:70px;" name="orderDetails.shopPrice" id="shopPrice" maxlength="12"  onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')" /> 元  <div id="shopPriceDiv" style="color: red"></div></td>
            </tr> 
            <tr>
              <td class="right_td" valign="top"><span class="font_style6">*</span> 备注：</td>
              <td><textarea class="text_style" style="width:405px; height:65px;" id="detailsContent" name="orderDetails.detailsContent"></textarea></td>
            </tr>
            <tr>
            	<td class="right_td" valign="top"><span class="font_style6">*</span> 还款时间：</td>
            	<td>
            	<input type="text" id="borrowingDate" name="borrowingDate" class="text_style" onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" readonly="true"/>
            	<div id="shopDateDiv" style="color: red"></div>
            	</td>
            </tr>
            <tr>
            	<td class="right_td" valign="top"><span class="font_style6">*</span> 支付密码：</td>
            	<td>
            	<input type="password" id="payPassword" name="payPassword" class="text_style" />
            	&nbsp;<font color="red"><c:out value="${emsppw}"></c:out></font> 
            	</td>
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

