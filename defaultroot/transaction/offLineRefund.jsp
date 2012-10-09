<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/offLineRefund.jsp-->

	<head>
	<title>钱门支付！--网上支付！安全放心！</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/prototype.js"></script>
		<script language="javascript">
			function userMark(tid,orderid){
				window.location.href="../transaction/transaction.do?thisAction=userMark&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&tid="+tid+"&orderid="+orderid;
			}
			
			function refundMentNext(tid,orderid){
				var ptype = document.form1.paytype.value;
				var num=0;
	
				for(var i=0;i<document.form1.paytype.length;i++){
		            if(document.form1.paytype[i].checked==true){
		              ptype = document.form1.paytype[i].value;
		              num++;
		              break;
		           	}
	       		} 
	       		if(num==0){
	       			document.getElementById("Error").style.display = "block";
	       			return false;
	       		}
	       		window.location.href="../transaction/transaction.do?thisAction=refundMent&flag=next&tid="+tid+"&orderid="+orderid;
			}
			
			function checknum(){
				 var nn;
				 if(document.getElementById("refundsNote").value.length>200){
				      document.getElementById("refundsNote").value = document.getElementById("refundsNote").value.substring(0,200); 
				 }
				 else{
				    nn=200-document.getElementById("refundsNote").value.length;
				    document.getElementById("syzs").innerHTML="剩余字数："+nn;
				 }
			}
			
			function editRefund(){
				var refundsNote = document.getElementById("refundsNote");
				if(refundsNote.value==""){
					alert("必须填写退款理由!");
					refundsNote.focus();
					return false;
				}
				
					var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
				    var payPassword = document.getElementById("payPassword").value;
					var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword,{method:"post", onComplete:function (originalRequest) {
					var result = originalRequest.responseText;
					var errorMessage = document.getElementById("errorMessage");
					if(result==0){
						errorMessage.innerHTML="<font color=red>支付密码错误!</font>";
					}
					else{
						document.forms[0].submit();
					}
				}, onException:showException});
			}
			
			function showException(originalRequest, ex) {
				alert("Exception:" + ex.message);
			}
			
			function getRefundMentDetail(tid,orderid,flag){
				window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&tid="+tid+"&orderid="+orderid+"&edit=1&flag="+flag;
			}
			
			function getTransactionPaymentDetailById(tid,statusid,orderid,flag,agentid,type){
				window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&shipment=1&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
			}
			
			function agreeApply(){
				if (!confirm("确定退款后,相关货款将立即退还给买家!")) return;
				
					var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
				    var payPassword = document.getElementById("payPassword").value;
					var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword,{method:"post", onComplete:function (originalRequest) {
					var result = originalRequest.responseText;
					var errorMessage = document.getElementById("errorMessage");
					if(result==0){
						errorMessage.innerHTML="<font color=red>支付密码错误!</font>";
					}
					else{
						document.forms[0].submit();
					}
				}, onException:showException});
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
		function getRefundMentBeforeShippingDetail(tid,orderid,flag){ 
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentBeforeShippingDetail&tid="+tid+"&orderid="+orderid+"&flag="+flag;
		}
		
		function confirmRefund(){
			if (!confirm("确定退款后,相关货款将立即退还给买家!")) return;
				
					var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
				    var payPassword = document.getElementById("payPassword").value;
					var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword,{method:"post", onComplete:function (originalRequest) {
					var result = originalRequest.responseText;
					var errorMessage = document.getElementById("errorMessage");
					if(result==0){
						errorMessage.innerHTML="<font color=red>支付密码错误!</font>";
					}
					else{
						document.forms[0].submit();
					}
				}, onException:showException});
		}
		</script>
		
	</head>

	<body>
	<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3" />
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
           <div class="main_title">
              <div class="main_title_right">
        			<p><strong>线下退款</strong></p>
        	</div>
     		</div>
     	</div>
      </div>
    </div>

	  <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">    
		<div>
		<div class="refundAgreement">
			<ul>
                    <li><span>商品名称：</span>
                        <c:if test="${transaction.orderDetails.shopUrl==null}">
			            	<c:out value="${transaction.orderDetails.shopName}"/>
			            </c:if>
			            <c:if test="${transaction.orderDetails.shopUrl!=null}">
			              <a href="<c:out value="${transaction.orderDetails.shopUrl}"/>"><c:out value="${transaction.orderDetails.shopName}"/></a>
			            </c:if>
                    </li>
                    <li><span>购买时间：</span><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></li>
                    <li><span>退款金额：</span><strong><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/> 元</strong></li>
            </ul>
            
			 </div>
              <div class="clear"></div>

		</div>
      </div>
    </div>
     </div>
    
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="receiving receiving_2">

		   <html:form action="transaction/transaction.do?thisAction=offLineRefund" method="post">
		    <input type="hidden" name="tid" value="<c:out value="${transaction.id}"/>"/>
		    <input type="hidden" name="orderid" value="<c:out value="${transaction.orderDetails.id}"/>"/>
		   <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
				<tr>
					<td class="right_td"><strong style="font-size:14px;">买家：</strong></td>
					<td ><c:out value="${transaction.fromAgent.name}"/> (<c:out value="${transaction.fromAgent.loginName}"/>)</td>
				</tr>
				<tr>
					<td class="right_td"><span style="color:#F60;">* </span><strong style="font-size:14px;">请输入支付密码：</strong></td>
					<td ><input type="password" name="payPassword" id="payPassword" class="text_style"/>  <html:link page="/agent/agent.do?thisAction=forgetPassword&type=paypassword">找回支付密码</html:link>
					    <span id="errorMessage"></span>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><span class="simplyBtn_1"><input type="button" name="btn" class="buttom_middle" value="确认退款" onclick="confirmRefund()"/></span></td>
				</tr>
			</table>
			</html:form>	
		
        </div>
        </div>
        
      </div>
    </div>
  
    <c:import url="/_jsp/footer.jsp"/>
</div>

</div>

  </body>
</html>