<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
	String atype = "";
	String otype = "";
	if (request.getAttribute("atype") != null)
		atype = request.getAttribute("atype").toString();
	if (request.getAttribute("otype") != null)
		otype = request.getAttribute("otype").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/buyerTransactionRefundList.jsp -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
<script src="../_js/common.js" type="text/javascript"></script>
<script src="../_js/weekDate.js" type="text/javascript"></script>
<script language="javascript">
	
		function updateTransactionStatus(statusid,tid){
		    if (!confirm("确实要关闭交易吗？")) return;
			window.location.href="../transaction/transaction.do?thisAction=updateTransactionStatus&statusid="+statusid+"&tid="+tid;
		}
		
		function modifyTransactionPrice(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionById&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&type="+type;
		}
		
		function getTransactionDetailById(tid,statusid,orderid,flag){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionDetailById&flag="+flag+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function getTransactionPaymentDetailById(tid,statusid,orderid,flag,agentid){
			if(flag==1)
				window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
			else
				window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&flag="+flag+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function transactionPayment(tid,statusid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function chooseType(){
			var agentType = document.getElementById("agtype").value;
			window.location.href="../transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&agentType="+agentType;
		}
		
		function userMark(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=userMark&tid="+tid+"&orderid="+orderid+"&fg=buyer";
		}
		
		function refundMent(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=refundMent&tid="+tid+"&orderid="+orderid;
		}
		
		function getRefundMentDetail(tid,orderid,edit,flag){
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&flag="+flag+"&tid="+tid+"&orderid="+orderid+"&edit="+edit;
		}
		
		function getRefundMentBeforeShippingDetail(tid,orderid,flag){ 
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentBeforeShippingDetail&tid="+tid+"&orderid="+orderid+"&flag="+flag;
		}
		function viewTradeBuyerRefund(tid,orderid,flag){ 
			window.open("../transaction/transaction.do?thisAction=viewTradeBuyerRefund&tid="+tid+"&orderid="+orderid+"&flag="+flag);
		}
		
		function showAndHideOrderNo(){
		    var btn = document.getElementById("a1").name;
		    if(btn=="a1"){
				//document.getElementById("orderNoTR").style.display = "block";
				//document.getElementById("outOrderNoTR").style.display = "block";
				document.getElementById("searchShopNameTR").style.display = "block";
				document.getElementById("searchMoneyTR").style.display = "block";
				//document.getElementById("searchOrderNo").value = ""; 
				//document.getElementById("searchOutOrderNo").value = "";
				//document.getElementById("searchShopName").value = "";
				//document.getElementById("detail").innerHTML = "简单查询";
				//document.getElementById("a1").name="a2";
			}
			else if(btn=="a2"){
				//document.getElementById("orderNoTR").style.display = "none";
				//document.getElementById("outOrderNoTR").style.display = "none";
				document.getElementById("searchShopNameTR").style.display = "none";
				document.getElementById("searchMoneyTR").style.display = "none";
				//document.getElementById("searchOrderNo").value = "";
				//document.getElementById("searchOutOrderNo").value = "";
				//document.getElementById("searchShopName").value = "";
			
				//document.getElementById("detail").innerHTML = "高级查询";
				//document.getElementById("a1").name="a1";
			}
		}
		function smb(){
	
	var begin=document.getElementById("beginDate").value;
	var end=document.getElementById("endDate").value;
	var minq=document.forms[0].searchBeginMoney.value;
	var maxq=document.forms[0].searchEndMoney.value;

	if(begin!="" && end!="" && begin > end){
		alert("终止时间不能比起始时间小！");
		return false;
	}else if(minq!=""&&!(/^([0-9]\d+|[0-9])(\.\d\d?)*$/.test(minq))){
		     alert("最小金额输入错误");
		      document.forms[0].searchBeginMoney.value=0; 
		     document.forms[0].searchBeginMoney.focus();
		     return false;
	}else if(maxq!="" &&!(/^([0-9]\d+|[0-9])(\.\d\d?)*$/.test(maxq))){
			alert("最大金额输入错误");
			 document.forms[0].searchEndMoney.value=1000000;
			document.forms[0].searchEndMoney.focus();
			return false;
	}else if( minq!="" && maxq!="" && parseFloat(minq*100) > parseFloat(maxq*100)){
			alert("最小金额不能大于最大金额");
			return false;
	} 
	if(minq==""){
	 document.forms[0].searchBeginMoney.value=0; 
	 }
	if(maxq==""){
	document.forms[0].searchEndMoney.value=1000000;
	 }
	 
	document.forms[0].submit();
	
}

 function custom(){
  		
  		var txt=document.getElementById("dateType").value="自选";
  		document.getElementById("dateDiv").style.display ="none";
  }


  function showDiv(){
  
    var inputObj = document.getElementById("dateType");//文本框   	
    var divObj = document.getElementById("dateDiv");//div元素  
    var Left=0,Top=0;
    do{Left+=inputObj.offsetLeft,Top+=inputObj.offsetTop;}
    while(inputObj=inputObj.offsetParent);
    divObj.style.top = Top+22;   
    divObj.style.left = Left; 

       var daps  = document.getElementById("dateDiv").style;
       daps.display = ""; 
  }
	</script>

</head>
<body>
  <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,8,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
<div class="main_title2"><span class="right_span">我的账户：<c:out
	value="${agent.loginName}" />&nbsp;<html:link
	page="/agent/agent.do?thisAction=agentInfoById">查看账户余额</html:link></span> <span
	class="font_style7">交易查询：</span>(按交易创建日期查询)</div>
		  <html:form action="transaction/transactionlist.do?thisAction=getBuyerTransactionRefundList&fg=buyer" method="post">
				<html:hidden property="thisAction" />
				<html:hidden property="lastAction" />
				<html:hidden property="intPage" />
				<html:hidden property="pageCount" />
	          <table cellpadding="0" cellspacing="0" width="100%"
		class="information_table">
		<tr>
			<td class="right_td">对方昵称或会员名：</td>
			<td><html:text property="searchAgentName" styleId="searchAgentName" styleClass="text_style"></html:text> </td>
		</tr>
		<tr id="orderNoTR">
			<td class="right_td">交易号：</td>
			<td><html:text property="searchOrderNo" styleId="searchOrderNo" styleClass="text_style"></html:text> </td>
		</tr>
		<tr id="outOrderNoTR">
			<td class="right_td">商品订单号：</td>
			<td><html:text property="searchOutOrderNo" styleId="searchOutOrderNo" styleClass="text_style"></html:text> </td>
		</tr>
		<tr id="searchShopNameTR">
			<td class="right_td">商品名称：</td>
			<td><html:text property="searchShopName" styleId="searchShopName" styleClass="text_style"></html:text> </td>
		</tr>
		<tr id="searchMoneyTR">
			<td class="right_td">交易金额：</td>
			<td>
			<html:text property="searchBeginMoney" styleId="searchBeginMoney" styleClass="text_style" onfocus="this.value=''" onkeyup="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"></html:text>
			--
			<html:text property="searchEndMoney" styleId="searchEndMoney" styleClass="text_style" onfocus="this.value=''" onkeyup="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"></html:text>
			</td>
		</tr>
		<tr>
			<td class="right_td">我要看：</td>
			<td>
			<html:text styleId="beginDate" property="beginDate" styleClass="text_style"
				onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" onclick="custom();" readonly="false"/>
			--
			<html:text styleId="endDate" property="endDate" styleClass="text_style"
				onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" onclick="custom();" readonly="false"/>
				
				<html:text property="dateType" styleId="dateType" size="3" styleClass="text_style" readonly="true" onclick="showDiv();" ></html:text>
				<div style="left:740px;top:420px;position:absolute;width:125px; display:none; border:1px solid #92B0DD;background: #FFFFFF" id="dateDiv">  
								  <table align="center">
								  <tr align="center">
								  <td ><a href="#" onclick="gaibian('1');">当日</a></td>
								  <td><a href="#" onclick="gaibian('6');">本周</a></td>
								  </tr>
								  <tr>
								  <td><a href="#" onclick="gaibian('5');">上周</a></td>
								  <td><a href="#" onclick="gaibian('2');">本月</a></td>
								  </tr>
								  <tr>
								  <td><a href="#" onclick="gaibian('3');">上月</a></td>
								  <td><a href="#" onclick="gaibian('4');">全部</a></td>
								  </tr>
								  </table>
								</div>
			</td>
			<td><c:if test="${errorDateMessage!=''}">
				<div><font color="red"><c:out
					value="${errorDateMessage}" /></font></div>
			</c:if></td>
		</tr>
		<tr>
			<td class="right_td">支付类型:</td>
			<td><span style="margin-right: 20px"><input type="radio"
				name="orderDetailsType" value="0" checked="checked" />全部</span><span
				style="margin-right: 20px"><input type="radio"
				name="orderDetailsType" value="1" />支付</span><input type="radio"
				name="orderDetailsType" value="2" />信用支付</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="button" value="查 询" class="btn1"
				onclick="smb();" /> &nbsp;
		<!-- 	
			<a href="javascript:showAndHideOrderNo()"
				name="a1" id="a1" title=""><span id="detail">高级查询</span></a>
		-->		
			</td>
		</tr>
	</table>

          <div class="deal_list">
            <div class="deal_list_head">
              <a id="Show_10" onclick="AllTransaction();" style="cursor:hand">所有买入交易</a>
              <a id="Show_11" onclick="Transactions();" style="cursor:hand" >进行中的交易</a>
              <a id="Show_12" onclick="SuccessTransaction();" style="cursor:hand" >成功的交易</a>
              <a id="Show_13" onclick="FailureTransaction();" style="cursor:hand" >失败的交易</a>
              <a id="Show_14" onclick="RefundTransaction();" style="cursor:hand" class="at_deal_list_head">退款的交易</a>
            </div>

            <table cellpadding="0" cellspacing="0" width="100%" class="deal_table">
              <tr>
                <th><div align="center">退款时间</div></th>
                <th><div align="center">类型</div></th>
                <th><div align="center">交易号</div></th>
                <th><div align="center">行为</div></th>
                <th><div align="center">商品名称</div></th>
                <th><div align="center">交易对方</div></th>
                <th><div align="center">退款状态</div></th>
                <th><div align="center">可执行操作</div></th>
                <th style="border-right:1px solid #bbb;" align="center"><div align="center">备注</div></th>
              </tr>
              <c:forEach var="info" items="${tlf.list}" varStatus="statu">
              <tr>
                <td align="center"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.refundsDate}"/> </td>
                <td align="center">
                <c:out value="${info.typeCaption}"/>
                
                </td>
                <td><c:out value="${info.orderDetails.orderDetailsNo}" /></td>
                <c:if test="${info.flag==0}">
                <td align="center" style="color:#005B9B">
                </c:if>
                <c:if test="${info.flag==1}">
                <td align="center" style="color:#FF6600">
                </c:if>
                <c:out value="${info.actionCaption}" /></td>
                <td align="center" class="contentwidth"><c:out value="${info.orderDetails.shopName}" /></td>
                <td align="center"><c:out value="${info.reverseTransactioner.name}" /></td>
                <td align="center">
                	<c:if test="${info.status==11}">
                		退款成功
                	</c:if>
                	<c:if test="${info.status==10 || info.status==7}">
                		退款协议等待卖家确认中
                	</c:if>
                </td>
                <td>
                    <c:if test="${info.flag==0 && info.status==10}">
						<a href="javascript:getRefundMentDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',1,1)">修改退款协议</a>	
						<a href="javascript:getRefundMentDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',0,0)">查看</a>
					</c:if>
					<c:if test="${info.flag==1 && info.status==10}">
						<a href="javascript:getRefundMentBeforeShippingDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',1)">处理退款协议</a>
						<a href="javascript:getRefundMentBeforeShippingDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',0)">查看</a>		
					</c:if>
					<c:if test="${info.flag==0 && info.status==7}">
						<a href="javascript:getRefundMentDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',1,1)">修改退款协议</a>	
						<a href="javascript:getRefundMentDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',0,0)">查看</a>
					</c:if>
					<c:if test="${info.flag==1 && info.status==7}">
						<a href="javascript:getRefundMentBeforeShippingDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',1)">处理退款协议</a>
						<a href="javascript:getRefundMentBeforeShippingDetail('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',0)">查看</a>		
					</c:if>
		 			<c:if test="${info.status==11}">
		 				<a href="javascript:viewTradeBuyerRefund('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />',3)">查看</a>
		 			</c:if>
		 		</td>
                <td align="center" style="border-right:1px solid #ddd;"><a href="javascript:userMark('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />')">
                <c:if test="${info.flagStatus==1}">
                	<img src="../_img/Remarks-1.gif" alt="<c:out value="${info.mark}"/>"/>
                </c:if>
                 <c:if test="${info.flagStatus==2}">
                	<img src="../_img/Remarks-2.gif" alt="<c:out value="${info.mark}"/>"/>
                </c:if>
                 <c:if test="${info.flagStatus==3}">
                	<img src="../_img/Remarks-3.gif" alt="<c:out value="${info.mark}"/>"/>
                </c:if>
                 <c:if test="${info.flagStatus==4}">
                	<img src="../_img/Remarks-4.gif" alt="<c:out value="${info.mark}"/>"/>
                </c:if>
                 <c:if test="${info.flagStatus==5}">
                	<img src="../_img/Remarks-5.gif" alt="<c:out value="${info.mark}"/>"/>
                </c:if>
                 <c:if test="${info.flagStatus==6 || info.flagStatus==null}">
                	<img src="../_img/Remarks-6.gif" alt="编辑备注"/>
                </c:if>
                </a></td>
              </tr>
              </c:forEach>
              <tr>
                <td colspan="11" class="page_td">
                	<div>共有记录&nbsp;<c:out value="${tlf.totalRowCount}"></c:out>&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp;
					[<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a> | <a
						href="JavaScript:turnToPage(document.forms[0],1)">上一页</a> | <a
						href="JavaScript:turnToPage(document.forms[0],2)">下一页</a> | <a
						href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>] 页数:<c:out
						value="${tlf.intPage}" />/<c:out value="${tlf.pageCount}" />]</div>
                </td>
              </tr>
            </table>
            
           </html:form>
            
          </div>
        </div>
      </div>
    </div>
  <c:import url="/_jsp/footer.jsp"/>
  </div>
</body>
<script language="javascript">
	var atype = "<%=atype%>";
	var otype = "<%=otype%>";
	var a = "<c:out value='${tlf.searchOrderNo}'/>";
	var b = "<c:out value='${tlf.searchOutOrderNo}'/>";
	var c = "<c:out value='${tlf.searchShopName}'/>";
	if(atype!=""){
		document.getElementById("agtype").value=atype;
		document.forms[0].agentType[atype].checked=true;
	}
	
	if(otype!=""){
		document.forms[0].orderDetailsType[otype].checked=true;
	}
	
	if(a!="" || b!=""){
		//document.getElementById("orderNoTR").style.display = "block";
		//document.getElementById("outOrderNoTR").style.display = "block";
		document.getElementById("searchShopNameTR").style.display = "block";
		document.getElementById("searchMoneyTR").style.display = "block";
		//document.getElementById("detail").innerHTML = "简单查询";
		//document.getElementById("a1").name="a2";
	}
	
	function Show_Menu(tabid_num,tabnum){
		for(var i=0;i<5;i++){document.getElementById("Show_"+tabid_num+i).className="";}
		document.getElementById("Show_"+tabid_num+tabnum).className="at_deal_list_head";
	}
	
    function AllTransaction(){
		    Show_Menu(1,0);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=getSellerAndBuyerTransactionList&fg=buyer&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&menuList=1,0&flag=1&status=''";
			document.forms[0].submit();
		}
	function Transactions(){
		    Show_Menu(1,1);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=getSellerAndBuyerTransactionList&fg=buyer&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&menuList=1,1&status=1&flag=1";
			document.forms[0].submit();
		}
	function SuccessTransaction(){
	    Show_Menu(1,2);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getSellerAndBuyerTransactionList&fg=buyer&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&menuList=1,2&status=3&flag=1";
		document.forms[0].submit();
	}
  function FailureTransaction(){
	    Show_Menu(1,3);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getSellerAndBuyerTransactionList&fg=buyer&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&menuList=1,3&status=4&flag=1";
		document.forms[0].submit();
	}
  function RefundTransaction(){
	    Show_Menu(1,4);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getBuyerTransactionRefundList&flag=1";
		document.forms[0].submit();
	}
</script>
</html>
