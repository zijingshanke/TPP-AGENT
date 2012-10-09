<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%
	String menuList = "";
	if (request.getAttribute("menuList") != null
	    && !request.getAttribute("menuList").equals(""))
		menuList = request.getAttribute("menuList").toString();

	String atype = "";
	String otype = "";
	if (request.getAttribute("atype") != null)
		atype = request.getAttribute("atype").toString();
		
	if (request.getAttribute("otype") != null)
		otype = request.getAttribute("otype").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/listTransaction.jsp ---->

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
	
		function closeTransaction(tid,orderid){
			window.open("../transaction/transaction.do?thisAction=closeTransaction&orderid="+orderid+"&tid="+tid);
		}
		
		function expense(tid,debitNo){
			window.location.href="../transaction/transaction.do?thisAction=expense&debitNo="+debitNo+"&tid="+tid;
		}
		
		function modifyTransactionPrice(tid,orderid,type){
			window.open("../transaction/transaction.do?thisAction=getTransactionById&tid="+tid+"&orderid="+orderid+"&type="+type);
		}
		
		function getTransactionDetailById(tid,statusid,orderid,flag,agentid,type){
			window.open("../transaction/transaction.do?thisAction=getTransactionDetailById&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
		
		function getTransactionSuccessAndFailDetail(tid,statusid,orderid,flag,agentid,type){
			window.open("../transaction/transaction.do?thisAction=getTransactionSuccessAndFailDetail&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
		
		function transactionDetailByDunAndBatch(tid,statusid,orderid,flag,agentid,type){
			window.open("../transaction/transaction.do?thisAction=transactionDetailByDunAndBatch&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
		
		function transactionDetailByRedPacket(tid,statusid,orderid,flag,agentid,type){
			window.open("../transaction/transaction.do?thisAction=transactionDetailByRedPacket&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
		function getTransactionPaymentDetailById(tid,statusid,orderid,flag,agentid,type){
			if(flag==1){
				if(type==0)
					window.open("../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&shipment=1&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type);
				else
					window.open("../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type);
			}
			else
				window.open("../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&flag="+flag+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type);
		}
		
		function getTransactionShippingDetailById(tid,statusid,orderid,flag,agentid,type){
			window.open("../transaction/transaction.do?thisAction=getTransactionShippingDetailById&type="+type+"&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
		
		function transactionPayment(tid,statusid,orderid){
			window.open("../transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
		
		function transactionRepayment(tid){
			window.open("../transaction/transaction.do?thisAction=repayment&tid="+tid);
		}
		
		function creditRepayment(tid){
			window.open("../transaction/transaction.do?thisAction=creditRepayment&tid="+tid);
		}
		
		function chooseType(){
			var agentType = document.getElementById("agtype").value;
			
			var menuList = document.getElementById("menuList").value;
			var status = document.getElementById("status").value;
			window.location.href="../transaction/transactionlist.do?thisAction=listTransactions&agentType="+agentType+"&menuList="+menuList+"&status="+status;
		}
		
		function userMark(tid,orderid){
			window.open("../transaction/transaction.do?thisAction=userMark&tid="+tid+"&orderid="+orderid);
		}
		
		function refundMent(tid,orderid,type){
			window.open("../transaction/transaction.do?thisAction=refundMent&tid="+tid+"&orderid="+orderid+"&type="+type);
		}
		
		function offLineRefund(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=offLineRefundFirst&tid="+tid+"&orderid="+orderid+"&type="+type;
		}
		
		function offLineCreditRefund(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=offLineCreditRefundFirst&tid="+tid+"&orderid="+orderid+"&type="+type;
		}
		
		function getRefundMentDetail(tid,orderid,edit){
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&tid="+tid+"&orderid="+orderid;
		}
		
		//function getRefundMentBeforeShippingDetail(tid,orderid,flag){ 
		//	window.open("../transaction/transaction.do?thisAction=getRefundMentBeforeShippingDetail&tid="+tid+"&orderid="+orderid+"&flag="+flag);
		//}
		function getRefundMentBeforeShippingDetail(tid,orderid,flag,fg){ 
			window.open("../transaction/transaction.do?thisAction=getRefundMentBeforeShippingDetail&tid="+tid+"&orderid="+orderid+"&flag="+flag+"&fg="+fg);
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
		
		//去左空格;
		function ltrim(s){
		       return s.replace( /^\s*/, "");
		}
		//去右空格;
		function rtrim(s){
		       return s.replace( /\s*$/, "");
		}
		//左右空格;
		function trim(s){
		       return rtrim(ltrim(s));
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
<div id="warp"><c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3" />
<div class="main_top">
<div class="main_bottom">
<div class="main_mid">
<div class="main_title2"><span class="right_span">我的账户：<c:out
	value="${agent.loginName}" />&nbsp;<html:link
	page="/agent/agent.do?thisAction=agentInfoById">查看账户余额</html:link></span> <span
	class="font_style7">交易查询：</span>(按交易创建日期查询)</div>
<html:form action="transaction/transactionlist.do" method="post">
	<html:hidden property="thisAction"/>
	<html:hidden property="lastAction" />
	<html:hidden property="intPage" />
	<html:hidden property="pageCount" />
	<input type="hidden" name="ff" id="ff" value="" />
	<input type="hidden" name="menuList" id="menuList"
		value="<c:out value='${menuList}'/>" />
	<input type="hidden" name="status" id="status"
		value="<c:out value='${status}'/>" />
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
			<html:text styleId="beginDate" property="beginDate" styleClass="text_style" onclick="custom();"
				onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" readonly="true" />
			--
			<html:text styleId="endDate" property="endDate" styleClass="text_style" onclick="custom();"
				onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" readonly="true" />
						
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
			<td class="right_td">交易类型:</td>
			<td><span style="margin-right: 20px"><input type="radio"
				name="agentType" value="0" checked="checked" />买入/卖出交易</span><span
				style="margin-right: 20px"><input type="radio"
				name="agentType" value="1" />买入交易</span><input type="radio"
				name="agentType" value="2" />卖出交易</td>
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
			<td>
				
					<input type="button" value="查 询" class="btn1"
					onclick="smb();" /> &nbsp;
					<!--  <a href="javascript:showAndHideOrderNo()"
					name="a1" id="a1" title=""><span id="detail">高级查询</span></a> -->
				
			</td>
		</tr>
	</table>

	<div class="deal_list">
	<div class="deal_list_head">
	<a id="Show_10" onclick="AllTransaction();"  class="at_deal_list_head" style="cursor:hand">所有的交易</a>
	<a id="Show_11" onclick="Transactions();" style="cursor:hand">进行中的交易</a>
	<a id="Show_12" onclick="SuccessTransaction();" style="cursor:hand">成功的交易</a>
	<a id="Show_13" onclick="FailureTransaction();" style="cursor:hand">失败的交易</a>
	<a id="Show_14" onclick="RefundTransaction();"	style="cursor:hand">退款的交易</a>
	<c:check code=""></c:check>
	<a id="Show_15" onclick="LoanOrRepayment();" style="cursor:hand">借款/还款</a>
	<a id="Show_16" onclick="AdvanceOrReimburse();" style="cursor:hand">预支/报销</a>
	<a id="Show_17" onclick="LoaningOrRepayment();" style="cursor:hand">授信/还款</a>	
	<a id="Show_18" onclick="RedPacket();" style="cursor:hand">红包</a>
	</div>
	<div class="deal_list_count">当前显示：
	<select name="agtype"
		id="agtype" onchange="chooseType()">
		<option value="0">买入/卖出交易</option>
		<option value="1">买入交易</option>
		<option value="2">卖出交易</option>
	</select></div>
	<table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="word-break :break-all">
		<tr>
			<th width="10%">
			<div align="center">创建时间</div>
			</th>
			<th width="9%">
			<div align="center">类型</div>
			</th>
			<th width="11%">
			<div align="center">交易号</div>
			</th>
			<th width="5%">
			<div align="center">行为</div>
			</th>
			<th width="8%">
			<div align="center">交易对方</div>
			</th>
			<th width="13%">
			<div align="center">商品名称</div>
			</th>
			<th width="7%">
			<div align="center">收入</div>
			</th>
			<th width="7%">
			<div align="center">支出</div>
			</th>
			<th width="11%">
			<div align="center">交易状态</div>
			</th>
			<th width="14%">
			<div align="center">可执行操作</div>
			</th>
			<th width="5%">
			<div align="center">备注</div>
			</th>
		</tr>
		<c:forEach var="info" items="${tlf.list}" varStatus="statu">
			<tr>
				<td align="center"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss"
					value="${info.accountDate}" /></td>
				<td align="center"><c:out value="${info.typeCaption}"/>
				</td>
				<td align="center"><c:out
					value="${info.orderDetails.orderDetailsNo}" /></td>
				<c:if test="${info.flag==0}">
					<td align="center" style="color: #005B9B">
				</c:if>
				<c:if test="${info.flag==1}">
					<td align="center" style="color: #FF6600">
				</c:if>
				<c:out value="${info.actionCaption}" />
				</td>
				<td align="center"><c:out
					value="${info.reverseTransactioner.name}" /></td>
				<td style="word-break: break-all" align="center"><c:out
					value="${info.orderDetails.shopName}" /></td>
				<c:if test="${info.flag==1}">
				<td align="center"><fmt:formatNumber pattern="0.00"
					value="${info.amount}" /></td>
				<td align="center"></td>
				</c:if>
				<c:if test="${info.flag==0}">
				<td align="center"></td>
				<td align="center">-<fmt:formatNumber pattern="0.00"
					value="${info.amount}" /></td>
				</c:if>
				<td align="center"><c:choose>
					<c:when test="${info.status==10}">
                		 买家已付款，等待卖家发货
               			 <img src="../_img/icon_refund.gif" align="absmiddle"/>
					</c:when>
					<c:when test="${info.status==7}">
                		 卖家已发货，等待买家确认
               			 <img src="../_img/icon_refund.gif" align="absmiddle"/>
					</c:when>
					<c:otherwise>
						<c:out value="${info.transactionStatus}" />
					</c:otherwise>
				</c:choose></td>
				<td><!-- 可执行操作开始 --> 
					<c:forEach var="lf"
					   items="${info.operate}" varStatus="st">
					<a href="<c:out value="${lf.url}"/>"
						title="<c:out value="${lf.title}"/>"
						style='c: out value = "${lf.style}"/ &gt;'><c:out
						value="${lf.urlName}" /></a>
					<a href="<c:out value="${lf.url2}"/>"><c:out
						value="${lf.urlName2}" /></a>
					<a href="<c:out value="${lf.url3}"/>"><c:out
						value="${lf.urlName3}" /></a>
				</c:forEach> <!-- 可执行操作结束 --></td>
				<td align="center" style="border-right: 1px solid #ddd;"><a
					href="javascript:userMark('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />')">
				<c:if test="${info.flagStatus==1}">
					<img src="../_img/Remarks-1.gif"
						alt="<c:out value="${info.mark}"/>" />
				</c:if> <c:if test="${info.flagStatus==2}">
					<img src="../_img/Remarks-2.gif"
						alt="<c:out value="${info.mark}"/>" />
				</c:if> <c:if test="${info.flagStatus==3}">
					<img src="../_img/Remarks-3.gif"
						alt="<c:out value="${info.mark}"/>" />
				</c:if> <c:if test="${info.flagStatus==4}">
					<img src="../_img/Remarks-4.gif"
						alt="<c:out value="${info.mark}"/>" />
				</c:if> <c:if test="${info.flagStatus==5}">
					<img src="../_img/Remarks-5.gif"
						alt="<c:out value="${info.mark}"/>" />
				</c:if> <c:if test="${info.flagStatus==6}">
					<img src="../_img/Remarks-6.gif"
						alt="<c:out value="${info.mark}"/>" />
				</c:if> <c:if test="${info.flagStatus==null}">
					<img src="../_img/Remarks-6.gif" alt="编辑备注" />
				</c:if> </a></td>
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
<c:import url="/_jsp/footer.jsp" />
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
		//document.getElementById("otype").value=otype;
		document.forms[0].orderDetailsType[otype].checked=true;
	}
	
	if(a!="" || b!=""){
		document.getElementById("orderNoTR").style.display = "block";
		document.getElementById("outOrderNoTR").style.display = "block";
		//document.getElementById("searchShopNameTR").style.display = "block";
		//document.getElementById("searchMoneyTR").style.display = "block";
		//document.getElementById("detail").innerHTML = "简单查询";
		//document.getElementById("a1").name="a2";
	}
			
	var menuList = "<%=menuList%>";
    if(menuList!=""){
        var tabArray = menuList.split(",");
		Show_Menu(tabArray[0],tabArray[1]);
	}
	
	function Show_Menu(tabid_num,tabnum){
		for(var i=0;i<8;i++){document.getElementById("Show_"+tabid_num+i).className="";}
		document.getElementById("Show_"+tabid_num+tabnum).className="at_deal_list_head";
	}
       function AllTransaction(){
		    Show_Menu(1,0);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=listTransactions&menuList=1,0&flag=1&status=''";
			smb();
		}
	function Transactions(){
		    Show_Menu(1,1);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=listTransactions&menuList=1,1&status=1&flag=1";
			smb();
		}
	function SuccessTransaction(){
	    Show_Menu(1,2);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=listTransactions&menuList=1,2&status=3&flag=1";
		smb();;
	}
  function FailureTransaction(){
	    Show_Menu(1,3);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=listTransactions&menuList=1,3&status=4&flag=1";
		smb();
	}
  function RefundTransaction(){
	    Show_Menu(1,4);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=refundMentManage&flag=1";
		smb();
	} 
  	
    function LoanOrRepayment(){
	    Show_Menu(1,5);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getBorrowAndRepaymentList&menuList=1,5&flag=1";
		smb();
	}
	
	function AdvanceOrReimburse(){
	    Show_Menu(1,6);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getDebitAndExpenseList&menuList=1,6&flag=1";
		smb();
	}	
	
	function LoaningOrRepayment(){
	    Show_Menu(1,7);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getLetterAndRepaymentList&menuList=1,7&flag=1";
		smb();
	}
	function RedPacket(){
	    Show_Menu(1,8);
		document.forms[0].action="../transaction/transactionlist.do?thisAction=getRedPacketList&menuList=1,8&flag=1";
		smb();
	}
</script>
</html>
