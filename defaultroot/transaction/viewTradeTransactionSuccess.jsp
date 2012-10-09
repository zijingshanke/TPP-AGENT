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

<!--JSP页面: transaction/viewTradeTransactionSuccess.jsp -->

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
<body>
    <div id="warp">   
	  	<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,9,6" />
	 
	<c:if test="${statusid==3}">
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
      	<div class="main_title">
            <div class="main_title_right">
              <p><strong>查看交易详情</strong>&nbsp;
              <c:if test="${URI.agentUser==null}">
              <html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1">返回交易管理</html:link>
              </c:if>
              </p>
            </div>
      </div>
		
		<div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle">
	            	<em>交易号 <c:out value="${transaction.orderDetails.orderDetailsNo}"/> 的交易已经成功</em>
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
	          <th><div>单价</div></th>
	          <th><div style="background: url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">数量</div></th>
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
            <td><fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.shopPrice}" />元</td>
			<td style="background: url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">
					<c:out value="${transaction.orderDetails.shopTotal}" /> 件
			</td>
            <td><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/>
            	   <a href="javascript:showDetail()">查看</a>
            </td>
          </tr>
          
          <tr bgcolor="#F8F8F8">
                 <td colspan="3">付款时间</td>
                 <td colspan="2">交易结束时间</td>
          </tr>
           <tr bgcolor="#F8F8F8">
                 <td colspan="3"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></td>
                 <td colspan="2"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.payDate}"/></td>
          </tr>
           <tr bgcolor="#F8F8F8">
                 <td colspan="3">支付说明</td>
                 <td colspan="2"></td>
          </tr>
           <tr bgcolor="#F8F8F8">
                 <td colspan="3"><c:out value="${transaction.orderDetails.detailsContent}"/></td>
                 <td colspan="2"></td>
          </tr>
        </table>
        
        <div class="tableRight">
            <span class="speak">
            	交易成功
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
		
    </c:if>
  <c:import url="/_jsp/footer.jsp"/>

  </div>
  </body>
</html>
