<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/payConfirm.jsp -->

  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<script src="../_js/prototype.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="../_js/prototype.js"></script>
	<script language="javascript">
		var toAgent = "<c:out value='${tranvo.toAgent}'/>";
	
		function checkPayPasswordBeforCommit(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
		    var payPassword = document.getElementById("payPassword").value;
		    var sellerAccount = document.getElementById("sellerAccount").value;
		    var allowBalance = document.getElementById("allowBalance").value;
		    var paymentPrice = document.getElementById("paymentPrice").value;
		    var shopPrice = document.getElementById("shopPrice").value;
		    var errorMessage = document.getElementById("errorMessage");
		    var autoPassword = document.getElementById("autoPassword");
		    var mobileValidateCode = document.getElementById("mobileValidateCode").value;
		    errorMessage.innerHTML = "";
		   // if(parseFloat(allowBalance)<parseFloat(paymentPrice)){
		   // 	errorMessage.innerHTML="<font color=red>帐号余额不足,请先充值!</font>";
		   // 	return false;
		   // }
		    
		    
		    var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword+"&paymentPrice="+paymentPrice+"&shopPrice="+shopPrice,{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;

			if(result==0){
				errorMessage.innerHTML="<font color=red>支付密码错误!</font>";
			}
			else if(result==2){
				errorMessage.innerHTML="<font color=red>帐号余额不足,请先充值!</font>";
				return false;
			}
			else{
			    if(autoPassword.style.display == "block" && mobileValidateCode==""){
		    		alert("请输入校验码");
		    		return false;
		    	}
				if(toAgent==""){
				    var checkAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkCanPay&amount="+paymentPrice+"&mobileValidateCode="+mobileValidateCode,{method:"post", onComplete:function (originalRequest) {
					var result2 = originalRequest.responseText;
					if(result2==0){
						errorMessage.innerHTML="<font color=red>余额支付功能已关闭!</font>";
						return false;
					}
					else if(result2==1){
						errorMessage.innerHTML="<font color=red>你支付的额度超过设置的单笔支付额度,请输入动态口令密码!</font>";
						
						autoPassword.style.display = "block";
						return false;
					}
					else if(result2==2){
						errorMessage.innerHTML="<font color=red>你支付的额度超过设置的当天支付累计资金数,请输入动态口令密码!</font>";
						
						autoPassword.style.display = "block";
						return false;
					}
					else if(result2==4){
						validateCodeMsg.innerHTML="<font color=red>校验码有误!</font>";
						autoPassword.style.display = "block";
						return false;
					}
					else if(result2==5){
						validateCodeMsg.innerHTML="<font color=red>点击获取动态口令并输入校验码!</font>";
						autoPassword.style.display = "block";
						return false;
					}
					else{
							var agentAjax=new Ajax.Request("../agent/agent.do?thisAction=register&newEmail="+encodeURI(sellerAccount),{
					 		method:"post",
								onComplete:function(originalRequest){
								var result2 = originalRequest.responseText;
								if(result2==1){		
									document.forms[0].submit();
								}			
							}, onException:showException
							});
					}
					}, onException:showException});
				}
				else{
					var checkAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkCanPay&amount="+paymentPrice+"&mobileValidateCode="+mobileValidateCode,{method:"post", onComplete:function (originalRequest) {
					var result2 = originalRequest.responseText;
					if(result2==0){
						errorMessage.innerHTML="<font color=red>余额支付功能已关闭!</font>";
						return false;
					}
					else if(result2==1){
						errorMessage.innerHTML="<font color=red>你支付的额度超过设置的单笔支付额度,请输入动态口令密码!</font>";
						
						autoPassword.style.display = "block";
						return false;
					}
					else if(result2==2){
						errorMessage.innerHTML="<font color=red>你支付的额度超过设置的当天支付累计资金数,请输入动态口令密码!</font>";
						
						autoPassword.style.display = "block";
						return false;
					}
					else if(result2==4){
						validateCodeMsg.innerHTML="<font color=red>校验码有误!</font>";
						autoPassword.style.display = "block";
						return false;
					}
					else if(result2==5){
						validateCodeMsg.innerHTML="<font color=red>点击获取动态口令并输入校验码!</font>";
						autoPassword.style.display = "block";
						return false;
					}
					else{
						document.forms[0].submit();
					}
					}, onException:showException});
				}
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
	</script>
   
  </head>
  
  <body>
  	<div id="warp">
  	<c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,0,3"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        <div class="main_title2">
           <c:if test="${paytype==0}"> 
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
          </c:if>
          
          <c:if test="${paytype==1}"> 
          <span class="font_style7">向陌生卖家付款</span> 
          <html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1">交易管理</html:link>
            <div class="Attention Attention1">
		        <div class="attentionTitle attentionTitle1">
		           <em>使用“即使到账”向陌生付款，请注意交易风险。</em>
		           <p>“即时到账付款”主要适用于家人或好友付款，如果对方只是一个陌生人的卖家，我们强烈推荐您使用更为安全的“担保交易付款”。</p>
		        </div>
	      </div>
          </c:if>
      </div>    
       </div>
     </div> 
     </div>  
     <html:form action="transaction/transaction.do?thisAction=paySave" method="post">
     <input type="hidden" name="allowBalance" id="allowBalance" value="<c:out value="${allowBalance}"/>"/>
     <input type="hidden" name="paymentPrice" id="paymentPrice" value="<c:out value="${tranvo.orderDetails.paymentPrice}"/>"/>
     <input type="hidden" name="shopPrice" id="shopPrice" value="<c:out value="${tranvo.orderDetails.shopPrice}"/>"/>
     
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">    
        <div class="main_title">
        
            <div class="main_title_right">
             <c:if test="${paytype==0}"> 
              <p><strong>确认亲友信息</strong></p>
             </c:if>
             <c:if test="${paytype==1}"> 
              <p><strong>确认购买信息</strong></p>
             </c:if>
            </div>
          </div>
  	
    <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
    <tr>
    	<td class="right_td">您的钱门账户:</td>
    	<td style="color:#005C9C"><c:out value="${loginAccount}"/></td> 
    </tr>
     <tr>
    	<td class="right_td">收款方账户名:</td>
    	<td><c:out value="${tranvo.sellerAccount}"/></td> 
    </tr>
    <c:if test="${tranvo.toAgent!=null}">
     <tr>
    	<td class="right_td">收款方真实姓名:</td>
    	<td><c:out value="${tranvo.toAgent.name}"/></td> 
    </tr>
    </c:if>
    <c:if test="${paytype==0}"> 
     <tr>
    	<td class="right_td">付款原因:</td>
    	<td style="word-break: break-all"><c:out value="${tranvo.orderDetails.paymentReason}"/></td> 
    </tr>
    <tr>
    	<td class="right_td">付款金额:</td>
    	<td><c:out value="${tranvo.orderDetails.paymentPrice}"/> 元</td> 
    </tr>
    <input type="hidden" name="orderDetails.paymentPrice" value="<c:out value="${tranvo.orderDetails.paymentPrice}"/>"/>
    </c:if>
    <c:if test="${paytype==1}"> 
    <tr>
    	<td class="right_td">商品或服务名称:</td>
    	<td><c:out value="${tranvo.orderDetails.shopName}"/></td> 
    </tr>
    <tr>
    	<td class="right_td">商品金额:</td>
    	<td><c:out value="${tranvo.orderDetails.shopPrice}"/> 元</td> 
    </tr>
    </c:if>
    <c:if test="${paytype==1 && tranvo.orderDetails.shopUrl!=null}"> 
    <tr>
    	<td class="right_td">商品展示网址:</td>
    	<td><c:out value="${tranvo.orderDetails.shopUrl}"/></td> 
    </tr>
    </c:if>
    <c:if test="${paytype==1}"> 
    	<tr>
    	<td class="right_td">付款说明:</td>
    	<td style="word-break: break-all"><c:out value="${tranvo.orderDetails.detailsContent}"/></td> 
    	<input type="hidden" name="orderDetails.shopPrice" value="<c:out value="${tranvo.orderDetails.shopPrice}"/>"/>
    </tr>
    </c:if>
    <c:if test="${tranvo.orderDetails.consigneeAddress!='0'}">
     <tr>
    	<td class="right_td">收货地址:</td>
    	<td><c:out value="${tranvo.orderDetails.consigneeAddress}"/></td> 
    </tr>
    </c:if>
    <c:if test="${paytype==0 && tranvo.orderDetails.detailsContent!=''}"> 
     <tr>
    	<td class="right_td">备注:</td>
    	<td style="word-break: break-all"><c:out value="${tranvo.orderDetails.detailsContent}"/></td> 
    </tr>
    </c:if>
    </table>
    
    </div>
    </div>
    </div>
    <c:if test="${tranvo.toAgent!=null}">
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>确认付款</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td"><span class="font_style6">* </span>支付密码：</td>
              <td><input type="password" id="payPassword" name="payPassword" value="" class="text_style" style="width:190px;" />
              	  <span id="errorMessage"></span>
              </td>
            </tr> 
             <tr style="display: none;" id="autoPassword">
	              <td class="right_td">&nbsp;</td>
	              <td><input type="text" class="text_style" name="mobileValidateCode" id="mobileValidateCode"/> <span class="simplyBtn_1"><input class="buttom_middle" type="button" value="点击获取动态口令密码" name="bb" onclick="getMobileValidateCode();"/></span> <span id="validateCodeMsg"></span></td>
	            </tr>
	         <tr style="display: none;" id="autoMsg">
	              <td class="right_td">&nbsp;</td>
	              <td>如果因网络延误导致超过10分钟未能收到验证码,请先点击主页的“退出”链接,然后重新获取</td>
	          </tr>
            <tr>
              <td>&nbsp;</td>
              <td valign="top"><html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" style="margin:0; line-height:0;">我要充值</html:link>&nbsp;&nbsp;<html:link page="/agent/agent.do?thisAction=forgetPassword&type=paypassword" style="margin:0; line-height:0;">找回支付密码</html:link></td>
            </tr>
            <tr>
              <td colspan="2" style="padding-left:148px;"><input type="button" value="确认付款" class="btn1" onclick="checkPayPasswordBeforCommit()"/></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    </c:if>
    
    <c:if test="${tranvo.toAgent==null}">
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
            <div class="Wronning">
                <div class="wronningTitle">
                    <em class="h1">您现在付款的账户名尚未注册钱门，</em>系统将自动创建该账户，钱门已通过Email或手机短信的方式通知收款方。请通知收款方尽快激活账户。若对方不及时激活账户，您付款后可以立即取消，也可以等待系统超时自动取消该笔付款，<em class="h1">但交易服务费将不返还。</em>
                </div>
              </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
          <tr>
              <td colspan="2" style="padding-left:142px;"><span class="font_style6">* </span>支付密码：
              <input type="password" id="payPassword" name="payPassword" value="" class="text_style" style="width:190px;" />
              	  <span id="errorMessage"></span>
              </td>
            </tr> 
            <tr style="display: none;" id="autoPassword">
	              <td colspan="2" style="padding-left:142px;">
	              <input type="text" class="text_style" name="mobileValidateCode" id="mobileValidateCode"/> <span class="simplyBtn_1"><input class="buttom_middle" type="button" value="点击获取动态口令密码" name="bb" onclick="getMobileValidateCode();"/></span> <span id="validateCodeMsg"></span></td>
	            </tr>
            <tr>
            <tr style="display: none;" id="autoMsg">
	              <td colspan="2" style="padding-left:142px;">如果因网络延误导致超过10分钟未能收到验证码,请先点击主页的“退出”链接,然后重新获取</td>
	          </tr>
            <tr>
              <td colspan="2" style="padding-left:142px;"><span class="simplyBtn_1"><input type="button" class="buttom_middle" onclick="checkPayPasswordBeforCommit()" value="确认付款" /></span><span class="simplyBtn_1" style="margin-left: 20px"><input type="button" class="buttom_middle" onclick="history.back(-1);" value="重新填写收款方账户" /></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
	</c:if>
 
	<input type="hidden" name="paytype" value="<c:out value="${paytype}"/>"/>
	<input type="hidden" id="sellerAccount" name="sellerAccount" value="<c:out value="${tranvo.sellerAccount}"/>"/>
    </html:form>

<c:import url="/_jsp/footer.jsp"/>
</div>
  </body>
</html>

