<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/creditRepayment.jsp -->
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
		
		function checkForm(){
			
			var repaymentPrice = document.getElementById("repaymentPrice");
			var repaymentContent = document.getElementById("repaymentContent");
			var paymentReason=document.getElementById("paymentReason");
			var msg = document.getElementById("shopPriceDiv");
			var payPassword=document.getElementById("payPassword");
		    msg.innerHTML= "";
		    var price="<fmt:formatNumber value='${repaymentAmaut}' pattern='0.00' />";
		    var re=/^([0-9]\d+|[0-9])(\.\d\d?)*$/;
		    if(repaymentPrice.value!=""){
		    	if(paymentReason.value.trim()==""){
		    	alert("请输入还款原因!");
		    	paymentReason.value = "";
		    	paymentReason.focus();
		    	return false;
			    }
			    else{
			    	paymentReason.value = paymentReason.value.trim();
			    }
		    	if(repaymentContent.value.trim()==""){
		    	alert("请输入还款备注!");
		    	repaymentContent.value = "";
		    	repaymentContent.focus();
		    	return false;
			    }
			    else{
			    	repaymentContent.value = repaymentContent.value.trim();
			    }
			    
		    	if(!re.test(repaymentPrice.value)){
					msg.innerHTML= "金额只能由数字组成且最多带有2位小数";
					repaymentPrice.focus();
					return false;
				}
				if(repaymentPrice.value<=0.01||parseFloat(repaymentPrice.value)>parseFloat(<c:out value='${transaction.creditPaymentAmount-transaction.creditRePaymentAmount}'/>)||parseFloat(repaymentPrice.value)>parseFloat(<c:out value='${transaction.fromAgent.allowBalance}'/>)){
					msg.innerHTML = "单价只能大于0.01,小于等于还款金额和可用余额";
					repaymentPrice.focus();
					return false;
				}
				 
		    if(payPassword.value==""){
		    	alert("请输入支付密码!");
		    	payPassword.value = "";
		    	payPassword.focus();
		    	return false;
		    }
				
				if(confirm("确定后，您将会还给<c:out value='${transaction.toAgent.loginName}'/>( "+repaymentPrice.value+" ) 元，您确认吗？")){
						document.forms[0].submit();
					}
		    }
		    else{
		    	alert("请输入还款金额!");
		    	repaymentPrice.focus();
		    	return false;
		    }
		    
	 }
		    
		   
	</script>
		
	</head>

	<body>
		<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,4,3" />
	<html:form method="post" action="transaction/transaction.do?thisAction=transactionCreditRepayment">
	<html:hidden property="fromAgentId" value="${transaction.fromAgent.id}"></html:hidden>
	<html:hidden property="toAgentId" value="${transaction.toAgent.id}"></html:hidden>
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>授信还款:<c:out value="${transaction.fromAgent.allowBalance}"></c:out></strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${transaction.fromAgent.loginName}" /></td>
            </tr>
             <tr>
              <td class="right_td">授信钱门账户：</td>
              <td><c:out value="${transaction.toAgent.loginName}"></c:out> </td>
            </tr>
            <tr>
              <td class="right_td">未结算授信欠款总额：</td>
              <td><c:out value="${transaction.creditPaymentAmount}"></c:out> </td>
            </tr>
           <tr>
              <td class="right_td">未结算授信还款总额：</td>
              <td style="color:#005C9C">
              <c:if test="${transaction.creditRePaymentAmount>0.00}">
             	<fmt:formatNumber value="${transaction.creditRePaymentAmount}" pattern="0.00" /> 元
              </c:if>
              <c:if test="${transaction.creditRePaymentAmount==0.00}">
              0.00 元
              </c:if>
              </td>
            </tr> 
            <tr>
            	<td class="right_td">欠款余额：</td>
            	<td style="color:red">
            	<fmt:formatNumber value="${transaction.creditPaymentAmount-transaction.creditRePaymentAmount}" pattern="0.00" /> 元</td>
            </tr>
             <tr>
              <td class="right_td"><span class="font_style6">* </span>还款原因：</td>
              <td><input type="text" style="width: 400px" class="text_style" name="orderDetails.paymentReason" id="paymentReason" value="还“<c:out value="${transaction.toAgent.loginName}"/>”所授信的欠款"/></td>
            </tr> 
            <tr>
              <td class="right_td"><span class="font_style6">* </span>还款金额：</td>
              <td><input type="text" class="text_style" style="width:70px;" name="repaymentPrice" id="repaymentPrice" maxlength="12"  onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')" value="<fmt:formatNumber value="${transaction.creditPaymentAmount-transaction.creditRePaymentAmount}" pattern="0.00" />"/> 元  <div id="shopPriceDiv" style="color: red"></div></td>
            </tr> 
            <tr>
              <td class="right_td" valign="top"><span class="font_style6">*</span> 还款说明：</td>
              <td><textarea class="text_style" style="width:405px; height:65px;" id="repaymentContent" name="repaymentContent"></textarea></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">* </span>支付密码：</td>
              <td><input type="password" class="text_style"  name="payPassword" id="payPassword"  /> <font color="red"><c:out value="${msgPass}"></c:out></font></td>
            </tr> 
            <tr>
              <td colspan="2" style="padding-left:60px;"><input type="button" value="确 定" class="btn1" onclick="checkForm();"/></td>
            </tr>
          </table>
        </div>
      </div>
    </div>

     <input type="hidden" name="tid" value="<c:out value="${transaction.id}"/>"/>
	 <input type="hidden" name="orderid" value="<c:out value="${transaction.orderDetails.id}"/>"/>
  </html:form>
 <c:import url="/_jsp/footer.jsp"/>
</div>
  </body>
</html>

