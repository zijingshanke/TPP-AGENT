<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionShippingDetail.jsp -->

  <head>  
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.1.min.js"></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<script src="../_js/prototype.js"></script>
	
	<script language="javascript">

		function getRefundMentDetail(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&tid="+tid+"&orderid="+orderid;
		}
		
		function refundMent(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=refundMent&tid="+tid+"&orderid="+orderid+"&type="+type;
		}
		
		function getTransactionPaymentDetailById(tid,statusid,orderid,flag,agentid){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&shipment=1&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function changeTransportType(item){
			var transportType = item.value;
			if(transportType==2){
				document.getElementById("shopCompanyNameTR").style.display="none";
				document.getElementById("shopOrderNumTR").style.display="none";
			}
			else{
				document.getElementById("shopCompanyNameTR").style.display="block";
				document.getElementById("shopOrderNumTR").style.display="block";
			}
		}
		
		function checkPayPasswordBeforCommit(){
		 	var amount = document.getElementById("amount").value;
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
		    var payPassword = document.getElementById("payPassword").value;
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword,{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			var errorMessage = document.getElementById("errorMessage");
			if(result==0){
				errorMessage.innerHTML="<font color=red>支付密码错误!</font>";
			}
			else{
				if (!confirm('请你收到货后再点击"确定",否则可能钱货两空!点击"确定"后会将 '+amount+' 元直接付款到卖家账户.')) return;
				document.forms[0].submit();
			}
		}, onException:showException});
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
		
		function checkBeforeSubmit(){
			var type = document.getElementsByName("type");
			var companyName = document.getElementById("companyName");
			var no = document.getElementById("no");
			var note = document.getElementById("note");
			var num=0;
			
			for(var i=0;i<type.length;i++){
				if(document.forms[0].type[i].checked){
					num = document.forms[0].type[i].value;
				}
			}

			if(num.value!=2){
				if(companyName.value==""){
					alert("请输入承运公司名称!");
					companyName.focus();
					return false;
				}
				if(no.value==""){
					alert("运单号!");
					no.focus();
					return false;
				}
			}
			document.forms[0].submit();
		}
		
		function getTransactionShippingDetailById(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionShippingDetailById&type="+type+"&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid;
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
     	<c:choose>
     		<c:when test="${transaction.status==3}"> <!-- 买家已经确认收货,交易成功 -->
     			<strong>查看交易详情</strong>
        	</c:when>
        	<c:otherwise>
        	<c:if test="${flag==1}">
        		<strong>您已发货，等待买家确认收货。</strong>
        	</c:if>
        	<c:if test="${flag==0}">
        		<c:choose>
        			<c:when test="${type==1}">
        				<strong>确认收货</strong>
        			</c:when>
        			<c:otherwise>
        				<strong>卖家已通过<c:out value="${logistics.companyName}"/> 给您发货，请您确认。</strong>
        			</c:otherwise>
        		</c:choose>		
        	</c:if>
        	</c:otherwise>
       </c:choose>
       &nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1">返回交易管理</html:link>
       </p>  
       </div>
     </div>
     <c:if test="${transaction.status==3}"> <!-- 买家已经确认收货,交易成功 -->
     	<div class="paymentSuccessful">
     	    <div class="paymentSuccessfulTitle">
	     		<em>交易号 <c:out value="${transaction.orderDetails.orderDetailsNo}"/> 的交易已经成功。</em>
	     		<p>交易结束时间： <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm" value="${transaction.orderDetails.finishDate}"/> </p>
     		</div>
     	</div>
     </c:if>
        <table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">
          <tr>
            <th width="10%"><div align="center">类型</div></th>
            <th width="25%"><div align="center">商品名称 (交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>)</div></th>
            <th width="10%"><div align="center">单价</div></th>
            <th width="10%"><div style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">数量</div></th>
            <th width="10%"><div style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">邮费</div></th>
            <th width="15%"><div align="center">折扣/涨价</div></th>
			<th width="15%"><div style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">应付金额</div></th>
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
	              <a style="color: #005c9c;" href="<c:out value="${transaction.orderDetails.shopUrl}"/>"><c:out value="${transaction.orderDetails.shopName}"/></a>
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
                 <td colspan="7"><strong>商品描述：</strong><c:out value="${transaction.orderDetails.detailsContent}"/><p><strong>收货人：</strong><c:out value="${transaction.fromAgent.name}"/></p>
                 <p><strong>收货地址：</strong>
                 <c:if test="${agentAddressInfo==null}">
                 	我不需要收货地址
                 </c:if>
                 <c:if test="${agentAddressInfo!=null}">
                 <c:out value="${agentAddressInfo.address}"/> ,手机 : <c:out value="${agentAddressInfo.mtel}"/> ,电话 : <c:out value="${agentAddressInfo.tel}"/> ,邮编 : <c:out value="${agentAddressInfo.postCode}"/>
                 </c:if>
                 </p></td>
             </tr>
             <tr bgcolor="#F8F8F8">
                 <!-- <td colspan="2">结束交易时间</td> -->
                 <td colspan="2">
                 <c:if test="${type==0 || type==2}"> <!-- 点击查看 -->
                 		付款时间
                 	</c:if>
                 	<c:if test="${type==1}"> <!-- 点击确认发货 -->
					 	购买时间
				</c:if>
				</td>
				<c:if test="${transaction.status==3}">
                 <td colspan="4">发货时间</td>
                 </c:if>
                 <c:if test="${transaction.status!=3}">
                 <td colspan="5">发货时间</td>
                 </c:if>
                 <c:if test="${transaction.status==3}">
				<td>交易结束时间</td>
				</c:if>
             </tr>
             <tr bgcolor="#F8F8F8">
                 <td colspan="2"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.payDate}"/></td>
                
                <c:if test="${transaction.status==3}">
                 <td colspan="3">
                 </c:if>
                 <c:if test="${transaction.status!=3}">
                 <td colspan="4">
                 </c:if>
                 <td>
                 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${logistics.consignmentDate}"/>
                 	<a href="javascript:void(0)" onmouseover="viewDetail(this,'transportType')">物流信息
                    </a> 
                 </td> 
                 <c:if test="${transaction.status==3}">
				<td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.orderDetails.finishDate}"/></td>
				</c:if>
             </tr>
        </table>
        
        <div id="viewDetailBox" class="HoverDetail" >
			<div class="top"></div>
			<div id="viewDetailBoxCenter" class="center">
		
			</div>
		</div>
		
		<div id="transportType" style="display: none;">
		<c:if test="${logistics!=null}">
			<p><strong><c:out value="${logistics.logType}"/>：</strong><c:out value="${logistics.companyName}"/></p>
			<p><strong>发货单号：</strong><c:out value="${logistics.no}"/></p>
			<p><strong>发货备注：</strong><c:out value="${logistics.note}"/></p>
		</c:if>
		</div>
		
	    <div class="tableRight">
	        <!--  <span class="speak-top"><span><p>发货时间：</p><p><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${logistics.consignmentDate}"/></p><p>物流公司：</p><p><c:out value="${logistics.logType}"/>，<c:out value="${logistics.companyName}"/>，发货单号：<c:out value="${logistics.no}"/></p></span></span>-->
	        <span class="speak">
	        	<c:if test="${transaction.status==3}">
	        	          交易完成
	        	</c:if>
	        	<c:if test="${transaction.status!=3}">
		        	<c:if test="${flag==0}">
		        	卖家已经发货,等待您确认收货
		        	</c:if>
		        	<c:if test="${flag==1}">
		        	您已发货，等待买家确认收货
		        	</c:if>
	        	</c:if>
	        </span>
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
     
          
		<c:if test="${transaction.status!=3}"> <!-- 买家还没确认收货的时候 -->
		<c:if test="${flag==0 && (transaction.status==10 || transaction.status==11)}">
		<div class="main_top">
      	<div class="main_bottom">
        <div class="main_mid">
		<div class="receiving">
		<table cellpadding="0" cellspacing="0" width="946">
			<tr align="center">
			<td>	
				<strong>您已申请退款:</strong>
			</td>
			</tr>
			<tr><td></td></tr>
			<tr align="center">
				<td><input type="button" name="btn" value="查看退款详情" class="btn1" onclick="getRefundMentDetail('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />')"/></td>
			</tr>
		</table>
		</div>
		</div>
		</div>
		</div>
		</c:if>
		
		<c:choose>
			<c:when test="${flag==0}">
				<c:choose>
					<c:when test="${type==2}">
					<div class="main_top">
			      <div class="main_bottom">
			        <div class="main_mid">
					<div class="receiving receiving_3">
						<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
						
								<tr align="center">
								<td>	
									<strong style="color:#F60; font-size:16px;">如果您对商品满意:</strong>
								</td>
								<!-- 卖家已经发货,暂时屏蔽退款, 留在以后业务需要做-->
									<!--  
								<td>	
									<strong style=" font-size:16px;">如您未收到商品或对商品不满意:</strong>
								</td>		
								-->		
								</tr>
								<tr align="center">
									<td><input type="button" name="btn" value="确认收货" class="btn1" onclick="getTransactionShippingDetailById('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />','<c:out value="${flag}" />','<c:out value="${transaction.fromAgent.id}"/>',1)"/></td>
									<!-- 卖家已经发货,暂时屏蔽退款, 留在以后业务需要做-->
									<!--  
									<td><input type="button" name="btn" value="申请退款" class="btn1" onclick="refundMent('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />',1)"/></td>
									-->
								</tr>
						</table>
						</div>
						</div>
					</div>
					</div>
					</c:when>
					<c:otherwise>
					<div class="main_top">
			      <div class="main_bottom">
			        <div class="main_mid">
					 <html:form action="transaction/transaction.do?thisAction=transactionPaymentConfirm" method="post">
					 <div class="receiving receiving_3">
						<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
						     <tr>
				               <td colspan="2" style="font-size:12px;"><strong style="color: #C60; font-size:14px; margin:0 0 0 12px;">我已收到货，同意钱门打款</strong></td>
				             </tr>	
							 <tr >
							    <td>&nbsp;</td>
								 <td>
								 	<ul style="line-height:1.5em;list-style-position:inside;margin:10px 0;padding:0;">
										<li><strong style="color:#f00;font-size:12px;">如果您还没有收到卖家的物品，请不要点击同意付款。否则您可能钱货两空。</strong></li>
										<li>为了确保交易安全，请在收货确认以后，才输入“钱门账户支付密码”并同意付款。</li>
									</ul>
								 </td>
							</tr>
		
					    	<tr>
					    	    <td>&nbsp;</td>
					    		<td>输入钱门账户支付密码：<input type="password" name="payPassword" id="payPassword" class="text_style"/> <span id="errorMessage"></span></td>
					    	</tr>
					    	<tr>
					    	    <td>&nbsp;</td>
					    		<td><input type="button" name="bun" value="同意付款" class="btn1" onclick="checkPayPasswordBeforCommit()"/></td>
					    	</tr>
								<!-- 卖家已经发货,暂时屏蔽退款, 留在以后业务需要做-->
								<!--  
								<td>	
								    <table>
								    	<tr>
								    		<td>
								    			<strong style="font-size:16px;">如您未收到商品或对商品不满意:</strong>
								    		</td>
								    	</tr>
								    	<tr>
								    		<td>&nbsp;</td>
								    	</tr>
								    	<tr>
											<td align="center"><input type="button" name="btn" value="申请退款" class="btn1" onclick="refundMent('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />',1)"/></td>
										</tr>
								    </table>
									
								</td>
								-->	
						</table>
						<input type="hidden" name="tid" id="tid" value="<c:out value="${transaction.id}"/>"/>
	       	 			<input type="hidden" name="type" id="type" value="<c:out value="${transaction.type}"/>"/>
						<input type="hidden" name="amount" id="amount" value="<c:out value="${transaction.amount}"/>"/>
						<input type="hidden" name="status" id="status" value="<c:out value="${transaction.status}"/>"/>
						<input type="hidden" name="toAgentId" id="toAgentId" value="<c:out value="${transaction.toAgent.id}"/>"/>
						<input type="hidden" name="toAgentLoginName" id="toAgentLoginName" value="<c:out value="${transaction.toAgent.loginName}"/>"/>
						<input type="hidden" name="agreePayment" id="agreePayment" value="yes"/>
					</div>
					</html:form>
					</div>
					</div>
					</div>
					</c:otherwise>
				</c:choose>

		    </c:when>
		</c:choose>
		</c:if>
 
  
  <c:import url="/_jsp/footer.jsp"/>
  </div>
   
  </body>
  <script language="javascript">

	function viewDetail(C,E){
	var B=document.getElementById(E);
	var A=document.getElementById("viewDetailBox");
	var F=document.getElementById("viewDetailBoxCenter");
	if(!A){
		A=document.createElement("div");
		A.id="viewDetailBox";
		A.className="HoverDetail";
		var D=document.createElement("div");
		D.className="top";
		F=document.createElement("div");
		F.id="viewDetailBoxCenter";
		F.className="center";
		A.appendChild(D);
		A.appendChild(F);
		document.body.appendChild(A)
	}
	if(B){
		F.innerHTML=B.innerHTML;
		A.style.display="block";
		A.style.left=(getXY(C).left-183)+"px";
		A.style.top=getXY(C).top+C.offsetHeight+"px";
		C.onmouseout=A.onmouseout=function(H){
			var G=H||event;
			if(window.navigator.userAgent.indexOf("MSIE")>0){
				if(!A.contains(G.toElement)){
					A.style.display="none"
				}
			}
			else{
				if(!A.contains(G.relatedTarget)){
					A.style.display="none"
				}
			}
		}
	}
}

window.onload=function(){
	if(typeof (HTMLElement)!="undefined"){
		HTMLElement.prototype.contains=function(A){
			while(A!=null&&typeof (A.tagName)!="undefined"){
				if(A==this){
					return true
				}A=A.parentNode
			}
			return false
		}
	}
}

function getXY(C){
	var B=C.offsetTop;
	var A=C.offsetLeft;
	while(C=C.offsetParent){
		B+=C.offsetTop;A+=C.offsetLeft
	}
	return{
		left:A,top:B
	}
}
  </script>
</html>
