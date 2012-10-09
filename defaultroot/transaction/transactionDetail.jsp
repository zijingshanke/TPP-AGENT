<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionDetail.jsp -->

  <head>  
  <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.1.min.js"></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<script language="javascript">
		function modifyTransactionPrice(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionById&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&type="+type;
		}
		
		function updateTransactionStatus(statusid,tid){
		    if (!confirm("确实要关闭交易吗？")) return;
			window.location.href="../transaction/transaction.do?thisAction=updateTransactionStatus&statusid="+statusid+"&tid="+tid;
		}
		
		function transactionPayment(tid,statusid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function closeTransaction(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=closeTransaction&orderid="+orderid+"&tid="+tid;
		}
		
		function show_date_time(){
			var accountDate = "<fmt:formatDate pattern='yyyy/MM/dd HH:mm:ss' value='${transaction.accountDate}'/>";
			var timeDiv = document.getElementById("timeDiv");
			
			window.setTimeout("show_date_time()", 1000);
			var target=new Date(accountDate);
			target.setDate(target.getDate()+7);
			today=new Date();
	
			timeold=(target.getTime()-today.getTime());
			
			sectimeold=timeold/1000;
			secondsold=Math.floor(sectimeold);
			msPerDay=24*60*60*1000;
			e_daysold=timeold/msPerDay;
			daysold=Math.floor(e_daysold);
			e_hrsold=(e_daysold-daysold)*24;
			hrsold=Math.floor(e_hrsold);
			e_minsold=(e_hrsold-hrsold)*60;
			minsold=Math.floor((e_hrsold-hrsold)*60);
			seconds=Math.floor((e_minsold-minsold)*60);
			
			if (daysold<0) {
				timeDiv.innerHTML="交易自动关闭!";
			}
			else{
				if (daysold<10) {daysold="0"+daysold;}
				if (daysold<100) {daysold=daysold;}
				if (hrsold<10) {hrsold="0"+hrsold;}
				if (minsold<10) {minsold="0"+minsold;}
				if (seconds<10) {seconds="0"+seconds;}
				if (daysold<3) {
					timeDiv.innerHTML=daysold+"天"+hrsold+"小时"+minsold+"分"+seconds+"秒</font>";
				}
				else
					timeDiv.innerHTML=+daysold+"天"+hrsold+"小时"+minsold+"分"+seconds+"秒</font>";
			}
		}
	</script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
  
  </head>
  <c:if test="${flag==0}">
    <body onload="show_date_time()">
  </c:if>
  <c:if test="${flag==1}">
    <body>
  </c:if>
    <div id="warp"> 
    <c:if test="${flag==0 || flag==1}">
  	<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>
  </c:if>
  <c:if test="${flag==2}">
  <c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,2,3"/>
  </c:if>
  <c:if test="${flag==3}">
  <c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,2,3"/>
  </c:if>
  
    <c:if test="${statusid==3}">
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
	     	<div class="main_title">
	     	   <div class="main_title_right">
	        	<p><strong>查看交易详情</strong>
	        	</div>
	       </div>
        
    	<div class="paymentSuccessful">
	          <div class="paymentSuccessfulTitle">
	  			<em>交易号 <c:out value="${transaction.orderDetails.orderDetailsNo}"/> 的交易已经成功。</em>
	  			<p>交易结束时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm" value="${transaction.accountDate}"/></p>
			</div>
		</div>
		
		</div>
        </div>
      </div>

    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        
        <table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">

          <tr>
            <th style="text-align: center"><div>类型</div></th>
            <th><div>商品或服务名称 （交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>）<img src="../_img/beizhu.gif" /></div></th>
            <th><div>应付金额 (元)</div></th>
		  </tr>

          <tr>
		  	<td align="center">
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
            <td style="color: #005c9c; font-size:16px; font-weight: bold;"><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></td>
          </tr>
		  <tr bgcolor="#F8F8F8">
                 <td colspan="2">付款时间</td>
                 <td>交易结束时间</td>
          </tr>
          <tr bgcolor="#F8F8F8">
                 <td colspan="2"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></td>
                 <td>
                 <c:if test="${transaction.closeDate!=null}">
                 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.closeDate}"/>
                 </c:if>
                 <c:if test="${transaction.closeDate==null}">
                 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.payDate}"/>
                 </c:if>
                 
                 </td>
          </tr>
        </table>   
      </div>
    </div>
     </div>
    </c:if>
    
    <c:if test="${statusid!=3}">
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
    	<div class="main_title">
       		<div class="main_title_right">    
	           <p>
	            <c:if test="${flag==1}">
	        	<strong>买家已拍下商品，还未付款。</strong>
		       </c:if>
		       <c:if test="${flag==0}">
		        <strong>商品已拍下，等待您付款。</strong>
		       </c:if>
		       &nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1">返回交易管理</html:link>
		       </p>
         	</div>
        </div>
   </div>
    </div>
   </div>
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        <table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">
          <tr>
             <th><div>类型</div></th>
            <th><div>商品名称 (交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>)</div></th>
           	<th><div>单价</div></th>
			<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">数量</div></th>
           	<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">邮费(<c:out value="${transaction.orderDetails.logistics}"/>)</div></th>
           	<th><div>折扣/涨价</div></th>
			<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">应付金额 (元)</div></th>
          </tr>
		  
          <tr>
		  	<td><span>
		  	<c:out value="${transaction.typeCaption}"/>
		  	</span></td>
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
           <c:if test="${agentAddressInfo==null}">
           		<tr>
           		<td colspan="7">
           			<strong>收货地址：</strong>我不需要收货地址
           		</td>
           		</tr>
           </c:if>
           <c:if test="${agentAddressInfo!=null}">
           <tr>
           		<td colspan="7"><strong>收货人：</strong>
					<c:out value="${agentAddressInfo.name}"/>
           		</td>
           </tr>
           <tr>
           		<td colspan="7"><strong>收货地址：</strong> <c:out value="${agentAddressInfo.address}"/> ,手机 : <c:out value="${agentAddressInfo.mtel}"/> ,电话 : <c:out value="${agentAddressInfo.tel}"/> ,邮编 : <c:out value="${agentAddressInfo.postCode}"/></td>
           </tr>
           </c:if>
        </table>
		
		 <div class="tableRight">
        	<c:if test="${flag==0}">
        	<em>卖家：<c:out value="${transaction.toAgent.name}"/>（<c:out value="${transaction.toAgent.loginName}"/>)</em>
        	</c:if>
        	<c:if test="${flag==1}">
        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>)</em>
        	</c:if>
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
        <c:if test="${flag==0}">
          <div class="receiving_4">
        </c:if>
        <c:if test="${flag==1}">
          <div class="receiving_4">
        </c:if>
         <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
         <c:if test="${flag==0}">
         <tr align="center">
         	<td align="center">
         		这是一笔钱门担保交易，根据钱门交易规则，请在 <img src="../_img/icon_timeout.gif" style="vertical-align: middle; margin: 0 8px;"/><strong style="margin-right:8px;color: #FF7300;"><label id="timeDiv"></label></strong>  内完成付款，否则交易将自动关闭。
         	</td>
         </tr>
         </c:if>
		<tr align="center">
		<c:if test="${flag==0}">
		  <td align="center">
	 			<span class="simplyBtn_1"><input type="button" class="buttom_middle" name="actionPayment" value="立即付款到钱门" onclick="transactionPayment('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />')"/></span>
	 	  </td>
    	</c:if>
    		
    	<c:if test="${flag==1}">
	    	<td align="center">
		 		<input type="button" class="btn1" name="actionPayment1" value="修改价格" onclick="modifyTransactionPrice('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />','<c:out value="${transaction.type}" />')"/>
		 	</td>
		 	<td align="center">
		 		<input type="button" class="btn1" name="actionPayment2" value="关闭交易" onclick="closeTransaction('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />')"/>
		 	</td>
    	</c:if>
		  
		</tr>
		</table>
		</div>
		 </div>
		  </div>
	</c:if>
  </div>
  <c:import url="/_jsp/footer.jsp"/>
  </body>
</html>
