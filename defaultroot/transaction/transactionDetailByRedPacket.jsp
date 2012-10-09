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

<!--JSP页面: transaction//transaction/transactionDetailByRedPacket.jsp -->

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
		function showDetail(){
			var orderId = "<c:out value='${transaction.orderDetails.id}'/>";
			var toAgentId = "<c:out value='${transaction.toAgent.id}'/>";
			openModalDialog(500,500,"../transaction/transactionlist.do?thisAction=getTransactionByToAgentId&orderid="+orderId+"&toAgentId="+toAgentId);
			
		}
		
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
		function transactionGatherRedPacket(tid,statusid,orderid){
			document.getElementById("actionPayment").disabled=true;
			window.location.href="../transaction/transaction.do?thisAction=transactionGatherRedPacket&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		function transactionRetreatRedPacket(tid,statusid,orderid){
			document.getElementById("actionPayment2").disabled=true;
			window.location.href="../transaction/transaction.do?thisAction=transactionRetreatRedPacket&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function userMark(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=userMark&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&tid="+tid+"&orderid="+orderid;
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
  <c:choose>
	  <c:when test="${flag==1 && statusid!=3}">
	  		<body onload="show_date_time()">
	  </c:when>
  	<c:otherwise>
  		<body>
  	</c:otherwise>
	  
  </c:choose>
    <div id="warp">   
     <c:if test="${flag==0 || flag==1}">
     	<c:if test="${seller==null}">
     		<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>
     	</c:if>
	  	<c:if test="${seller==1}">
	  	<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,9,6" />
	  	</c:if>
	  </c:if>
	  <c:if test="${flag==2}">
	  <c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,2,3"/>
	  </c:if>
	  <c:if test="${flag==3}">
	  <c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,2,3"/>
	  </c:if>
   
	<c:if test="${statusid==3||statusid==4}">
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
      	<div class="main_title">
            <div class="main_title_right">
              <p><strong>查看交易详情</strong>&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1">返回交易管理</html:link></p>
            </div>
      </div>
		
		<div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle">
	            	<em>交易号 <c:out value="${transaction.orderDetails.orderDetailsNo}"/> 的交易已经完成</em>
	                <p>交易结束时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></p>
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
	          <th><div>商品或服务名称 （交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>）<a href="javascript:userMark('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />')"><img src="../_img/beizhu.gif" /></a></div></th>
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
            <td><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/>
            	<c:if test="${seller==1}">
            	    <a href="javascript:showDetail()">查看</a>
            	</c:if>
            </td>
          </tr>
          
          <tr bgcolor="#F8F8F8">
                 <td colspan="2">付款时间</td>
                 <td>交易结束时间</td>
          </tr>
           <tr bgcolor="#F8F8F8">
                 <td colspan="2"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></td>
                 <td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.orderDetails.finishDate}"/></td>
          </tr>
          <tr>
          		<td colspan="3">支付说明：<c:out value="${transaction.orderDetails.detailsContent}"/></td>
         
           <tr>
          <td colspan="3"><strong>领取红包链接：</strong><c:out value="${transaction.mark}"/></td>
           </tr>
        </table>
        
        <div class="tableRight">
            <span class="speak">
            <c:choose>
            	<c:when test="${statusid==3}">
            		交易成功
            	</c:when>
            	<c:otherwise>
            		交易关闭
            	</c:otherwise>
            </c:choose>
            
            </span>
            <c:if test="${flag==1}">
        	<em>卖家：<c:out value="${transaction.toAgent.name}"/>（<c:out value="${transaction.toAgent.loginName}"/>)</em>
        	</c:if>
        	<c:if test="${flag==0}">
        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>)</em>
        	</c:if>
            <span class="headImg"><img src="../_img/user_pic.gif" /></span>
        </div>
        <div class="clear"></div>
		</div>
		</div>
		</div>
		
    </c:if>
    
     <c:if test="${statusid!=3&&statusid!=4}">
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
    	<div class="main_title">
    	<div class="main_title_right">
	    	<c:if test="${flag==0}">
	        	<p><strong>等待对方收取红包</strong>&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&flag=1">返回交易管理</html:link></p>
	       </c:if>
	       <c:if test="${flag==1}">
	        	<p><strong>等待您收取红包</strong>&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&flag=1">返回交易管理</html:link></p>
	       </c:if>
	       
       </div>
     </div>
   
        <table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">
          <tr>
            <th style="text-align: center"><div>类型</div></th>
	          <th><div>商品或服务名称 （交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>）<a href="javascript:userMark('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />')"><img src="../_img/beizhu.gif" /></a></div></th>
	          <th><div>金额 (元)</div></th>
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
	              <c:out value="${transaction.orderDetails.shopName}"/>
	            </c:if>
            </td>
            <td><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></td>
          </tr>
          <tr>
          		<td colspan="3"><strong>祝福语：</strong><c:out value="${transaction.orderDetails.detailsContent}"/></td>
          </tr>
           <tr>
          		<td colspan="3"><strong>领取红包链接：</strong><c:out value="${transaction.mark}"/></td>
          </tr>
        </table>
        <div class="tableRight">
            <span class="speak">
               <c:if test="${flag==1}">
            	等待您收取红包
            	</c:if>
            	<c:if test="${flag==0}">
            	等待对方收取红包
            	</c:if>
            </span>
            <c:if test="${flag==0}">
        	<em>卖家：<c:out value="${transaction.toAgent.name}"/>（<c:out value="${transaction.toAgent.loginName}"/>)</em>
        	</c:if>
        	<c:if test="${flag==1}">
        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>)</em>
        	</c:if>
            <span class="headImg"><img src="../_img/user_pic.gif" /></span>
        </div>
        <div class="clear"></div>
	  </div>
  </div>
  </div>
  
  <c:if test="${flag==1}">
   <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        	<div class="receiving_4">
   				<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
		         <tr align="center">
		         	<td align="center">
		         		这是一笔钱门即时到账交易，根据钱门交易规则，请在 <img src="../_img/icon_timeout.gif" style="vertical-align: middle; margin: 0 8px;"/><strong style="margin-right:8px;color: #FF7300;"><label id="timeDiv"></label></strong>  内完成付款，否则交易将自动关闭。
		         	</td>
		         </tr>
		         
		         
		          <tr align="center">
		          	<td align="center" colspan="3">
			 			<input type="button" class="actionPayment" i name="actionPayment" value="立即收取" onclick="transactionGatherRedPacket('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />')"/>
			 	    <input type="button" class="actionPayment2" name="actionPayment2" value="立即退还" onclick="transactionRetreatRedPacket('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />')"/>
			 	    </td>
			 	    
		          </tr>
		          
		         </table>
   			</div>
  		</div>
  	</div>
  </div>
  </c:if>
	</c:if>
	
  <c:import url="/_jsp/footer.jsp"/>

  </div>
  </body>
</html>
