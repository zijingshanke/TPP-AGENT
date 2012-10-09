<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/getBatchPaymentDetail.jsp -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<script src="../_js/popcalendar.js" type="text/javascript"></script>
<script language="javascript">
		//function getTransactionDetailById(tid,statusid,orderid){
		//	window.location.href="../transaction/transaction.do?thisAction=getTransactionDetailById&flag=3&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&showTabMenuIdList=1,1&showSubMenuIdList=0,2,2,3";
		//}
		function transactionDetailByDunAndBatch(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=transactionDetailByDunAndBatch&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid;
		}
		
		function userMark(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=userMark&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&tid="+tid+"&orderid="+orderid;
		}
</script>

</head>
<body>
  <div id="warp">
<c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,2,3"/>
  <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid"> 
        <div class="main_title">
            <div class="main_title_right">
        	<p><strong>批量付款明细信息</strong> <html:link page="/transaction/transactionlist.do?thisAction=getBatchPaymentDetail&tabMenuList=${tabMenuList}&subMenuList=${subMenuList}&paytype=${paytype}">返回批量付款历史记录</html:link></p>
        </div>
        </div>
        <p>
        <div class="deal_list">
  <table cellpadding="0" cellspacing="0" width="100%" class="deal_table">
	<tr>
        <th><div>创建时间</div></th>
        <th><div>类型</div></th>
        <th><div>交易号</div></th>
        <th><div>行为</div></th>
        <th><div>交易对方</div></th>
        <th><div>商品名称</div></th>
        <th><div>金额(元)</div></th>
        <th><div>交易状态</div></th>
        <th><div>可执行操作</div></th>
        <th><div>备注</div></th>
    </tr>
    <c:forEach var="info" items="${tlf.list}" varStatus="statu">
	<tr>
    <td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm" value="${info.accountDate}"/></td>
	<td><span  class="FastpayIcon2">
		<c:out value="${info.typeCaption}"/>
    </span></td>  
    <td><c:out value="${info.orderDetails.orderDetailsNo}" /></td>
    <td><nobr><font color="#663399"><c:out value="${info.batchCaption}" /></font></nobr></td>
    <td><c:out value="${info.reverseTransactioner.name}" /></td>    
    <td><c:out value="${info.orderDetails.shopName}" /> </td>
    <td><c:out value="${info.amount}" />&nbsp;</td>
	<td><c:out value="${info.transactionStatus}" /></td>
	<td>
	<a href="javascript:transactionDetailByDunAndBatch('<c:out value="${info.id}" />','<c:out value="${info.status}" />','<c:out value="${info.orderDetails.id}" />','<c:out value="${info.flag}" />','<c:out value="${info.fromAgent.id}" />','<c:out value="${info.type}" />')">查看</a>
	</td>
	<td><a href="javascript:userMark('<c:out value="${info.id}" />','<c:out value="${info.orderDetails.id}" />')"  title="" >
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
    </table>
	</div>	
</div>
  
  </div>
  </div>
  <c:import url="/_jsp/footer.jsp"/>
  </div>
  
</body>
</html>


