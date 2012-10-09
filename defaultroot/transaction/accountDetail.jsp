<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="antlr.collections.List"%>
<%@page import="com.fordays.fdpay.transaction.TransactionListForm"%>
<%@page import="com.fordays.fdpay.transaction.TransactionBalance"%>
<%@page import="com.fordays.fdpay.transaction.TempTransactionBalance"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%
	String menuList = "";
	if (request.getAttribute("menuList") != null
	    && !request.getAttribute("menuList").equals(""))
		menuList = request.getAttribute("menuList").toString(); 
		
		%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/accountDetail.jsp -->

	<head>
	<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache"></meta>
		<meta http-equiv="cache-control" content="no-cache"></meta>
		<meta http-equiv="expires" content="0"></meta>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"></meta>
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/weekDate.js" type="text/javascript"></script>
		<script type="text/javascript" src="../_js/jquery-1.3.1.min.js"></script>
	</head>
	<script language="javascript">

		function closeTransaction(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=closeTransaction&orderid="+orderid+"&tid="+tid;
		}
		
		function modifyTransactionPrice(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionById&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&type="+type;
		}
		
		function getTransactionDetailById(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionDetailById&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function getTransactionSuccessAndFailDetail(tid,statusid,orderid,flag,agentid,type){
		var loginId ="<c:out value="${talf.agent.id}" />";
		
		if(loginId==agentid){
			flag = 0; //登陆用户是买家则看卖家信息
		}else{
			flag = 1;  //反之看买家信息
		}
			window.open("../transaction/transaction.do?thisAction=getTransactionSuccessAndFailDetail&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3");
		}
		
		function transactionDetailByDunAndBatch(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=transactionDetailByDunAndBatch&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function getTransactionPaymentDetailById(tid,statusid,orderid,flag,agentid,type){
			if(flag==1){
				if(type==0)
					window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&shipment=1&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
				else
					window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
			}
			else
				window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&flag="+flag+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function getTransactionShippingDetailById(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionShippingDetailById&type="+type+"&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid;
		}
		
		function transactionPayment(tid,statusid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function chooseType(){
			var agentType = document.getElementById("agtype").value;
			window.location.href="../transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&agentType="+agentType;
		}
		
		function userMark(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=userMark&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&tid="+tid+"&orderid="+orderid;
		}
		
		function refundMent(tid,orderid,type){
			window.location.href="../transaction/transaction.do?thisAction=refundMent&tid="+tid+"&orderid="+orderid+"&type="+type;
		}
		
		function getRefundMentDetail(tid,orderid,edit){
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&tid="+tid+"&orderid="+orderid;
		}
		
		function getRefundMentBeforeShippingDetail(tid,orderid,flag){ 
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentBeforeShippingDetail&tid="+tid+"&orderid="+orderid+"&flag="+flag;
		}
		
		function sbm(){
			document.forms[0].action="../transaction/transactionlist.do?thisAction=outTransactionReport";
			document.forms[0].submit();
			document.forms[0].action="../transaction/transactionlist.do?thisAction=accountDetaillist";
		}
		 function custom(){
  		var cda=document.getElementById("selectDealDate");
  		cda.value="0";
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
	<body>
		<div id="warp">
		<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,1,6" />
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title2">
							<span class="right_span">我的账户：<c:out
									value="${talf.agent.loginName}" /> <html:link
									page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">查看账户余额</html:link>
							</span>
							<span class="font_style7">账户明细查询：</span>(按交易创建日期查询)
						</div>
						<html:form
							action="/transaction/transactionlist.do?thisAction=accountDetaillist">
							<html:hidden property="thisAction"/>
							<html:hidden property="lastAction" />
							<html:hidden property="intPage" />
							<html:hidden property="pageCount" />
							<html:hidden property="hql" />
							<input type="hidden" name="menuList" id="menuList" value="<c:out value='${menuList}'/>" />
							<html:hidden property="balanceType"/>
							<table cellpadding="0" cellspacing="0" width="100%"
								class="information_table">
								<tr>
									<td class="right_td">
										我要看：
									</td>
									<td>
											<html:text styleId="beginDate" property="beginDate" styleClass="text_style" onclick="custom();"
										 onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" ></html:text>
										-
											<html:text styleId="endDate" property="endDate" styleClass="text_style" onclick="custom();"
											onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"></html:text>
									
										<hidden id="selectDealDate"></hidden>
									<html:text property="dateType" styleId="dateType" size="3" styleClass="text_style" readonly="true" onclick="showDiv();" ></html:text>
						
								<div style="left:732px;top:230px;position:absolute;width:125px; display:none; border:1px solid #92B0DD;background: #FFFFFF" id="dateDiv">  
								  
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
								
								<e><c:out value="${errorDateMessage}"/></e>
								</tr>
								<tr>
								<td>
									&nbsp;
								</td>
								<td>
									<c:check code="sb10">
									<input type="submit" value="查 询" class="btn1" />
									</c:check>&nbsp;&nbsp;&nbsp;&nbsp;
									<font color="red"><c:out value="${magMaxDownloadAmount}"></c:out></font>
								</td>
								
								</tr>
							</table>
                     
							<div class="deal_list">
                   <div class="deal_list_head">
				<a id="Show_10" onclick="AllBalance();" style="cursor:hand" class="at_deal_list_head">所有</a>
				<a id="Show_11" onclick="Balance();" style="cursor:hand">可用余额</a>
				<a id="Show_12" onclick="OnBalance();" style="cursor:hand">冻结余额</a>
				<a id="Show_13" onclick="CreditBalance();"  style="cursor:hand">信用余额</a>
					
	                </div>
								<table cellpadding="0" cellspacing="0" width="100%" align="center" style="text-align: center"
									class="deal_table" style="word-break :break-all">
									<tr>
										<th><div align="center">创建时间</div></th>
										<th><div align="center">商户订单号</div></th>
										<th><div align="center">业务流水号</div></th>
										<th width="8%"><div align="center">类型</div></th>
										<th><div align="center">收入（元）</div></th>
										<th><div align="center">支出（元）</div></th>
										<c:if test="${balanceType==0 ||balanceType==1}">
										<th><div align="center">可用余额（元）</div></th>
										</c:if>
										<c:if test="${balanceType==0 ||balanceType==2}">
										<th><div align="center">冻结余额（元）</div></th>
										</c:if>
										<c:if test="${balanceType==0 ||balanceType==3}">
										<th><div align="center">信用余额（元）</div></th>
										</c:if>
										<c:if test="${balanceType==0}">
										<th><div align="center">账户总余额（元）</div></th>
										</c:if>
										<th width="4%"><div align="center">操作</div></th>
										<th width="9%"><div align="center">备注</div></th>
									</tr>
									<c:set var="ordeno" value="1"></c:set>
									<c:forEach var="tb" items="${talf.list}">				
										<tr>
											<td>
												<c:out value="${tb.transactionDate}"/>
												<br />
											</td>				
											<td style="color: #FF9900 ;">
												<c:out value="${tb.orderDetailsNo}"/>
											</td>
											<td >	
																
												<c:out value="${tb.orderNoSplit}"/>									
												<br />
											</td>
											
											<td>
												<c:out value="${tb.typeCaption}"></c:out>
												<br />
											</td>
											<td style="text-align: right;color:green;">
												<c:if test="${tb.toAgentId==talf.agent.id}">
													<fmt:formatNumber value="${tb.amount}" pattern="0.00"/>
												</c:if>
												<br />
											</td>
											<td style="text-align: right;color:red">
												<c:if test="${tb.fromAgentId==talf.agent.id}">
													<c:if test="${tb.amount>=0}">
													-<fmt:formatNumber value="${tb.amount}" pattern="0.00"/>
													</c:if>
												</c:if>
												<br />
											</td>
											<c:if test="${balanceType==0 ||balanceType==1}">
											<td style="text-align: right;color:olive;">
												<fmt:formatNumber value="${tb.balance}" pattern="0.00"/>
												<br />
											</td>
												</c:if>
											<c:if test="${balanceType==0 ||balanceType==2}">	
										   <td style="text-align: right;color:purple;">
												<fmt:formatNumber value="${tb.notallowBalance}" pattern="0.00"/>
												<br />
											</td>
											</c:if>
											<c:if test="${balanceType==0 ||balanceType==3}">
										   <td style="text-align: right;color:fuchsia;">
												<fmt:formatNumber value="${tb.creditBalance}" pattern="0.00"/>
												<br />
											</td>
											</c:if>
											<c:if test="${balanceType==0}">
											   <td style="text-align: right;">
												<fmt:formatNumber value="${tb.totalBalance}" pattern="0.00"/>
												<br />
											</td>
											</c:if>
											<td>
											<c:forEach var="lf" items="${tb.operate}" varStatus="st">
							                	<a href="<c:out value="${lf.url}"/>"><c:out value="${lf.urlName}"/></a>
						                	</c:forEach>
											</td>
											<td>
											</td>
										</tr>
									</c:forEach>
									<tr>
									<td class="page_td">
								    <c:check code="sb09">
									<input type="button" value="下载报表" onclick="sbm();" class="btn1" ></input>
									</c:check>
									</td>
									
										<td colspan="11" class="page_td">
											<div>
												共有记录&nbsp;
												<c:out value="${talf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${talf.intPage}" />
												/
												<c:out value="${talf.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
						</html:form>
					</div>
				</div>
			</div>
		</div>
		<c:import url="/_jsp/footer.jsp" />
	   <script language="javascript">
	   
      var menuList = "<%=menuList%>";
     if(menuList!=""){
        var tabArray = menuList.split(",");
		Show_Menu(tabArray[0],tabArray[1]);
	}
	
		function Show_Menu(tabid_num,tabnum){
			for(var i=0;i<4;i++){document.getElementById("Show_"+tabid_num+i).className="";}
			document.getElementById("Show_"+tabid_num+tabnum).className="at_deal_list_head";
		}
		
		function AllBalance(){
		    Show_Menu(1,0);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=accountDetaillist&menuList=1,0&status=1&flag=1&balanceType=0";
			document.forms[0].submit();
		}
		function Balance(){
		    Show_Menu(1,1);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=accountDetaillist&menuList=1,1&status=1&flag=1&balanceType=1";
			document.forms[0].submit();
		}
		
		function OnBalance(){
		    Show_Menu(1,2);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=accountDetaillist&menuList=1,2&status=1&flag=1&balanceType=2";
			document.forms[0].submit();
		}
		function CreditBalance(){
		    Show_Menu(1,3);
			document.forms[0].action="../transaction/transactionlist.do?thisAction=accountDetaillist&menuList=1,3&status=1&flag=1&balanceType=3";
			document.forms[0].submit();
		}
		</script>
	</body>
</html>
