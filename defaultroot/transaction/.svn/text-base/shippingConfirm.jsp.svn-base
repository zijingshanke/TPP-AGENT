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

<!--JSP页面: transaction/shippingConfirm.jsp -->

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
		
		function getTransactionShippingDetailById(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionShippingDetailById&type="+type+"&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid;
		}
	</script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
  	
  </head>
    <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>发货</strong></p>
            </div>
          </div>
          <div class="Attention Attention3">
          	<div class="attentionTitle attentionTitle2">
            	<em>您已经完成发货</em>
                <p>一封包含有发货相关物流信息的邮件已经发送给买家（对方在钱门有E-mail地址）<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>）</p>
            </div>
            
            <ul class="attentionList">
                <li><html:link page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">转至“我的钱门”</html:link></li>
                <li><a href="javascript:getTransactionShippingDetailById('<c:out value="${transaction.id}"/>','<c:out value="${transaction.status}"/>','<c:out value="${transaction.orderDetails.id}"/>','1','<c:out value="${transaction.fromAgent.id}"/>',0)" >查看本次交易详情</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
   <c:import url="/_jsp/footer.jsp"/>
  </div>
 
  </body>
</html>
