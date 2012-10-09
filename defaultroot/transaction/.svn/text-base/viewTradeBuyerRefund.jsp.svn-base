<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/viewTradeBuyerRefund.jsp-->

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
			
			function getRefundMentDetail(tid,orderid,edit){
				window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&flag=update&tid="+tid+"&orderid="+orderid+"&edit=1";
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
		</script>
		
	</head>

	<body>
	<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,8,6" />
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
           <div class="main_title">
              <div class="main_title_right">
        		<p><strong>退款成功</strong></p>
        	</div>
     		</div>

	      	<div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle">
	            	<em>退款成功</em>
	            	<p>退款结束时间: <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.orderDetails.finishDate}"/></p>
	            </div>
	       </div>
     </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        <div class="main_title2">
        	<span class="font_style7">交易信息</span>
        </div>
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
        		<li><span>申请退款时间：</span>
        			<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.refundsDate}"/>
        		</li>
        		<!--  
        		<li><span>是否收到货：</span>
        			买家未收到货
        		</li>
        		-->
        		<c:if test="${transaction.orderDetails.refundsNote!=null}">
        			<li><span>退款理由：</span>
        			<c:out value="${transaction.orderDetails.refundsNote}"/>
        			</li>
        		</c:if>
        		
        		<li><span>退款状态：</span>
        			<c:out value="${transaction.transactionStatus}"/>
        		</li>
        		<li><span>退款金额：</span>
        			<b><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/> 元</b>
        		</li>
        		<li><span>支付说明：</span>
        			<c:out value="${transaction.orderDetails.detailsContent}"/>
        		</li>
        	</ul>
        </div>
		
    <div class="refundAgreement-right">
		<p>对方账户：<b class="txt-TIME-s"><c:out value="${transaction.toAgent.loginName}"/></b>
		</p>
		<p>操作提示</p>
		<p>本退款申请已经于 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.orderDetails.finishDate}"/> 成功。</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
    </div>
   <div class="clear"></div>
        
		</div>
      </div>
    </div>
<c:import url="/_jsp/footer.jsp"/>
</div>
  </body>
</html>

