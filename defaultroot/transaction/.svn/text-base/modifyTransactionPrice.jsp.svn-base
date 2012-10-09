<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/modifyTransactionPrice.jsp -->

  <head>  
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.1.min.js"></script>
	<script language="javascript">
		function countFee(){
			var salePrice = document.getElementById("salePrice").value;
			var emailPrice = document.getElementById("emailPrice").value;
			var shopPrice = document.getElementById("shopPrice").value;
			var shopTotal = document.getElementById("shopTotal").value;
			var type = document.getElementById("type").value;
			var amount = document.getElementById("amount").value;
			var totalFee;
			if(type!="5")
				totalFee = Math.round(shopPrice *100* shopTotal + Number(salePrice)*100 + Number(emailPrice)*100);
			else
				totalFee = Math.round(Number(salePrice)*100 + Number(emailPrice)*100 + Number(amount)*100);
    		totalFee = totalFee/100;
			document.getElementById("totalFeeDisplay").innerHTML=totalFee;
		}
		
		function submitForm(tid,orderid){
			var salePrice = document.getElementById("salePrice").value;
			var emailPrice = document.getElementById("emailPrice").value;
			var shopPrice = document.getElementById("shopPrice").value;
			var shopTotal = document.getElementById("shopTotal").value;
			
			var type = document.getElementById("type").value;
			var amount = document.getElementById("amount").value;
			var totalFee = 0;
			if(type!="5")
				totalFee = Math.round(shopPrice *100* shopTotal + Number(salePrice)*100 + Number(emailPrice)*100);
			else
				totalFee = Math.round(Number(salePrice)*100 + Number(emailPrice)*100 + Number(amount)*100);
    		totalFee = totalFee/100;
    		
    		if(totalFee<=0){
    			alert("应付总价不能小于0.01,请修改邮费或者折扣!");
    			return;
    		}
    		else if(totalFee>1000000){
    			alert("钱门交易的总金额限额为100万元，您的交易总金额超过了该限制,请修改邮费或者折扣!");
    			return;
    		}
			window.location.href="../transaction/transaction.do?thisAction=modifyTransactionPriceConfirm&tid="+tid+"&orderid="+orderid+"&emailPrice="+emailPrice+"&totalFee="+totalFee+"&salePrice="+salePrice;
		}
		
		function submitForm2(){
			document.transaction.action="../transaction/transaction.do?thisAction=updateTransactionPrice&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
			document.transaction.submit();
		}
	</script>
  
  </head>
    <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        
        <div class="main_title">
       		<div class="main_title_right">    
       		<p>
            	<strong>修改交易价格</strong> &nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1" >返回交易管理</html:link>
            </p>
         	</div>
        </div>
    	
    	<table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">
          <tr>
            <th><div>类型</div></th>
            <th><div>商品名称 (交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>)</div></th>
           	<th><div>单价</div></th>
			<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">数量</div></th>
           	<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">邮费(快递)</div></th>
           	<th><div>折扣/涨价</div></th>
			<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">应付金额 (元)</div></th>
          </tr
          <tr>
		  	<td>
		  	<c:out value="${transaction.typeCaption}"/>
			</td>
            <td>
            <c:if test="${transaction.orderDetails.shopUrl==null}">
            <c:out value="${transaction.orderDetails.shopName}"/>
            </c:if>
            <c:if test="${transaction.orderDetails.shopUrl!=null}">
              <a href="<c:out value="${transaction.orderDetails.shopUrl}"/>"><c:out value="${transaction.orderDetails.shopName}"/></a>
            </c:if>
            </td>
           	<td><fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.shopPrice}"/></td> 
			<td style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;"><c:out value="${transaction.orderDetails.shopTotal}"/></td>
            <td style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">
	            <c:choose>
	            <c:when test="${transaction.orderDetails.emailPrice==null}">
				0.00
	            </c:when>
	            <c:otherwise>
	            <fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.emailPrice}"/>
	            </c:otherwise>
	            </c:choose>
            </td>
            <td>
            	<c:if test="${transaction.orderDetails.salePrice==null}">
            		0.00
            	</c:if>
            	<c:if test="${transaction.orderDetails.salePrice!=null}">
            		<fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.salePrice}"/>
            	</c:if>
            </td>
			<td style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;"><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></td>
          </tr>
           <tr>
                 <td colspan="7"><strong>商品描述：</strong><c:out value="${transaction.orderDetails.detailsContent}"/></td>
           </tr>
           <tr bgcolor="#F8F8F8">
           		<td colspan="7">购买时间</td>
           </tr>
           <tr bgcolor="#F8F8F8">
           		<td colspan="7"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></td>
           </tr>
        </table>
        
        <div class="tableRight">
        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>)</em>
            <span class="headImg"><img src="../_img/user_pic.gif" style="margin:3px;"/></span>
            <span style="float: left; margin-top: 12px; margin-left: 12px">
                <a href="#" style="color:#005c9c;">查看信用</a>
                <a href="#" style="color:#005c9c;">留言</a>
            </span>
        </div>
        <div class="clear"></div>
        
		</div>
      </div>
    </div>
    
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="receiving receiving_3">
		<!-------------------修改交易信息---------------------->
		<c:if test="${trans==null}">
		<form name="transactionForm" style="padding-left:0;padding-right:0;" method="post" action="">
		<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
		   <tr>
               <td colspan="2" style="font-size:12px;"><strong style="color: #C60; font-size:14px; margin:0 0 0 12px;">修改交易价格：</strong></td>
           </tr>
			<tr >
				 <td width="159" class="right_td">原商品价格：</td>
				 <td width="785"><fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.shopPrice*(transaction.orderDetails.shopTotal*1.0)}"/></td>
			</tr>
			<tr>
				<td class="right_td">新邮费：</td>
				<td><input id="emailPrice" class="text_style" style="width:100px;" name="emailPrice" type="text" value="<fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.emailPrice}"/>"  onblur="countFee();" maxlength="7"/> 元</td>
			</tr>
			<tr>
				<td class="right_td">&nbsp;</td>
				<td style="font-size:12px; color:#999; padding-top:0">例如：如果您修改了物流承运商，请输入新的邮费。比如，从平邮修改为快递，新的邮费可能是20元，则输入20。</td>
			</tr>
			
			<tr>
				<td class="right_td">涨价或折扣：</td>
				<td>
					<c:if test="${transaction.orderDetails.salePrice==null}">
						<input id="salePrice"  class="text_style" name="salePrice" type="text"  value="0.00" style="width:100px;" onblur="countFee();" maxlength="7"/> 
						</c:if>
						<c:if test="${transaction.orderDetails.salePrice!=null}">
						<input id="salePrice"  class="text_style" name="salePrice" type="text" style="width:100px;" value="<fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.salePrice}"/>"  onblur="countFee();" maxlength="7"/> 
					</c:if>
					元
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
				<td style="font-size:12px; color:#999; padding-top:0">例如：要给买家便宜100元，则输入“-100”，要提高价格100元，则输入100。</td>
			</tr>
			
			<tr>
				<td class="right_td">买家购物应付总价：</td>
				<td><strong id="totalFeeDisplay"><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></strong> <strong>元</strong> </td>
			</tr>
           
			<tr>
				<td class="right_td">&nbsp;</td>
				<td><button name="doChange" class="btn1" type="button" onclick="submitForm('<c:out value="${transaction.id}"/>','<c:out value="${transaction.orderDetails.id}"/>')" >确定修改</button></td>
			</tr>

		</table>
			<input type="hidden" id="shopPrice" name="shopPrice" value="<c:out value="${transaction.orderDetails.shopPrice}"/>"/>
			<input type="hidden" id="shopTotal" name="shopTotal" value="<c:out value="${transaction.orderDetails.shopTotal}"/>"/>
			<input type="hidden" name="type" id="type" value="<c:out value="${type}" />"/>
			<input type="hidden" id="amount" name="amount" value="<c:out value="${transaction.amount}"/>"/>
		</form>
	</c:if>
	<c:if test="${trans!=null}">
	<form name="transaction" method="post" action="">
	<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
		<tr>
	         <td colspan="2" style="font-size:12px;"><strong style="color: #C60; font-size:14px; margin:0 0 0 12px;">修改后的交易信息内容：</strong></td>
	    </tr>
		<tr>
			<td class="right_td">邮费：</td>
			<td><fmt:formatNumber pattern="0.00" value="${trans.orderDetails.emailPrice}"/> 元</td>
		</tr>
		<tr>
			<td class="right_td">货价调整：</td>
			<td><fmt:formatNumber pattern="0.00" value="${trans.salePrice}"/> 元</td>
		</tr>
		<tr>
			<td class="right_td">买家实际应付：</td>
			<td><fmt:formatNumber pattern="0.00" value="${trans.amount}"/> 元</td>
		</tr>	
		<tr>
			<td class="right_td"><button name="confirm" class="btn1" type="button" onclick="submitForm2()" >确定修改</button></td>
			<td><button class="btn1" type="button" onclick="history.back(-1);">取消</button></td>
		</tr>		
				<input type="hidden" id="newEmailPrice" name="newEmailPrice" value="<c:out value="${trans.orderDetails.emailPrice}"/>"/>
				<input type="hidden" id="amount" name="amount" value="<c:out value="${trans.amount}"/>"/>
				<input type="hidden" id="tid" name="tid" value="<c:out value="${trans.id}"/>"/>
				<input type="hidden" id="orderid" name="orderid" value="<c:out value="${trans.orderDetails.id}"/>"/>
				<input type="hidden" id="salePrice" name="salePrice" value="<c:out value="${trans.salePrice}"/>"/>
				<input type="hidden" name="type" id="type" value="<c:out value="${type}" />"/>
		</table>
		</form>
    </c:if>
 
  </div>
   
  </div>
  </div>
  </div>
  <c:import url="/_jsp/footer.jsp"/>
  </body>
</html>
