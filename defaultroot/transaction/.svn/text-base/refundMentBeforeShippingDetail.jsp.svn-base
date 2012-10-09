<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/refundMentBeforeShippingDetail.jsp 买家在卖家未发货之前退款的查看退款详情-->

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
		</script>
		
	</head>

	<body onload="show_date_time()">
	<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,2,3" />
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
           <div class="main_title">
              <div class="main_title_right">
           		<c:if test="${flag==0}">
        			<p><strong>退款协议等待卖家确认中</strong></p>
        		</c:if>
        		<c:if test="${flag==1}">
        		<p><strong>处理买家退款申请协议</strong></p>
        		</c:if>
        	</div>
     		</div>
     		<img src="../_img/buzhou_2.gif" style="margin-top:12px;"/>
     	</div>
      </div>
    </div>

	  <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title2">
            <span class="right_span">我的账户：<c:out value="${agent.loginName}"/> <a href="../agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">查看账户余额</a></span>
            <span class="font_style7">退款协议</span>
          </div>
          
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
                    <li><span>申请退款时间：</span><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.refundsDate}"/></li>
                    <li><span>是否收到货：</span>买家未收到货</li>
                    <li style="word-break: break-all"><span>退款理由：</span>
                    <c:forEach var="note" items="${transaction.orderDetails.formatRefundNote}" varStatus="st">
                    	<c:if test="${st.count mod 2==0}">
                    		<c:out value="<li><span>&nbsp;</span>" escapeXml="false"/>
                    	</c:if>
                    	<c:out value="${note}"/>
                    </c:forEach>
                    </li>
                    <li><span>退款状态：</span><c:out value="${transaction.transactionStatus}"/></li>
                    <li><span>退款金额：</span><strong><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/> 元</strong></li>
            </ul>
            
			 </div>
              <div class="refundAgreement-right">
              	<span class="refundAgreement-right-title"><img src="../_img/ring.gif" style="vertical-align: middle; margin-right: 8px;"/>时间提醒</span>
                <p>卖家（<c:out value="${transaction.toAgent.name}"/>）还有<img src="../_img/icon_timeout.gif" style="vertical-align: middle; margin: 0 8px;"/><strong style="margin-right:8px;"><label id="timeDiv"></label></strong>来处理本次退款申请。</p>
                <c:if test="${flag==0}">
                <p class="refundAgreement-right-down">如果这期间卖家（<c:out value="${transaction.toAgent.name}"/>）没有“同意该退款申请”或“对该申请提出任何异议”，本次退款申请将会自动生效。</p>
                </c:if>
                <c:if test="${flag==1}">
                <p class="refundAgreement-right-down">您如果未在该期限内与买家（<c:out value="${transaction.fromAgent.name}"/>）就退款<br>协议达成一致或未拒绝本次退款<br>申请，本次退款申请会自动生效。	</p>
                </c:if>
                <span class="refundAgreement-right-title"><img src="../_img/Arrow.gif" style="vertical-align: middle; margin-right: 8px;"/>操作提示</span>
                <c:if test="${flag==0}">
                <p>您已经于&nbsp;<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.refundsDate}"/>&nbsp;对本次交易提出退款申请。此退款单的最近修改时间为&nbsp;<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.refundsDate}"/> &nbsp;。</p>
                </c:if>
                <c:if test="${flag==1}">
				<p>本退款协议的最近修改时间为 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.refundsDate}"/> 。</p>
				</c:if>
                &nbsp;<a href="#">查看留言</a>&nbsp;|&nbsp;<a href="javascript:getTransactionPaymentDetailById('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />','<c:out value="${flag}" />','<c:out value="${transaction.fromAgent.id}"/>',1)">详细交易信息</a>&nbsp;|&nbsp;<a href="javascript:userMark('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />')">编辑备注</a></p>
              </div>
              <div class="clear"></div>

		</div>
      </div>
    </div>
     </div>
    
    <c:if test="${flag==1}"> <!-- 处理买家退款申请协议 -->
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="receiving receiving_2">

		   <html:form action="transaction/transaction.do?thisAction=agreeRefundMent" method="post">
		    <input type="hidden" name="amount" value="<c:out value="${transaction.amount}"/>"/>
		    <input type="hidden" name="tid" value="<c:out value="${transaction.id}"/>"/>
		    <input type="hidden" name="orderid" value="<c:out value="${transaction.orderDetails.id}"/>"/>
		    <input type="hidden" name="fromAgentId" value="<c:out value="${transaction.fromAgent.id}"/>"/>
		    <input type="hidden" name="flag" value="<c:out value="${flag}"/>"/>
		   <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
				<tr>
					<td class="right_td"><strong style="font-size:14px;">买家：</strong></td>
					<td ><c:out value="${transaction.fromAgent.name}"/></td>
				</tr>
				<tr>
					<td class="right_td"><span style="color:#F60;">* </span><strong style="font-size:14px;">请输入支付密码：</strong></td>
					<td ><input type="password" name="payPassword" id="payPassword" class="text_style"/> （如果您同意买家的退款申请，必须输入支付密码） <html:link page="/agent/agent.do?thisAction=forgetPassword&type=paypassword">找回支付密码</html:link>
					    <span id="errorMessage"></span>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><span class="simplyBtn_1"><input type="button" name="btn" class="buttom_middle" value="同意买家的退款申请协议" onclick="agreeApply()"/></span>
					    <span class="simplyBtn_1"><input type="button" name="btn" class="buttom_middle" value="我现在就去发货" onclick="getTransactionPaymentDetailById('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />','<c:out value="${flag}" />','<c:out value="${transaction.fromAgent.id}"/>',0)"/></span>
					</td>
				</tr>
			</table>
			</html:form>	
		
        </div>
        </div>
        
      </div>
    </div>
    </c:if>
    
    <c:if test="${flag==0}"> <!-- 处理买家退款申请协议 -->
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="receiving receiving_2">

		   <html:form action="transaction/transaction.do?thisAction=agreeRefundMent" method="post">
		    <input type="hidden" name="amount" value="<c:out value="${transaction.amount}"/>"/>
		    <input type="hidden" name="tid" value="<c:out value="${transaction.id}"/>"/>
		    <input type="hidden" name="orderid" value="<c:out value="${transaction.orderDetails.id}"/>"/>
		    <input type="hidden" name="fromAgentId" value="<c:out value="${transaction.fromAgent.id}"/>"/>
		    <input type="hidden" name="flag" value="<c:out value="${flag}"/>"/>
		   <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
				
				<tr align="center">
					<td></td>
					<td>
					    <span class="simplyBtn_1"><input type="button" name="btn" class="buttom_middle" value="立即处理退款协议" onclick="getRefundMentBeforeShippingDetail('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />',1)"/></span>
					</td>
				</tr>
			</table>
			</html:form>	
		
        </div>
        </div>
        
      </div>
    </div>
    </c:if>
    <c:import url="/_jsp/footer.jsp"/>
</div>

</div>

  </body>
</html>

