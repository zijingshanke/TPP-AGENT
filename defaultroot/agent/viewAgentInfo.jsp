<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agent/viewAgentInfo.jsp -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/_js/topDiv.js"></script>
<script type="text/javascript">

function initWindow(){
	var email="<c:out value="${URI.agent.email}"></c:out>"
	var validCertStatus="<c:out value="${URI.agent.validCertStatus}"></c:out>";
	//alert(validCertStatus);
	var rootPath='<%=request.getContextPath()%>';
	if(validCertStatus==0){
		msg(null,rootPath);
	}
}
</script>
<style type="text/css">
 	#loft_win {border:#FF9000 1px solid; background-color: #FFFFFF;}
 	.loft_win_head {color: #FFFFFF; font-size:13px; background-color:#FF9000; height:25px; padding:0,5,0,5;}
</style>

<link rel="stylesheet" href="../_css/l_style.css" type="text/css"></link></head>

<body onload="initWindow();">
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,0,6"/>
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
         <div class="main_mid_title"><strong>账户余额查询</strong></div>
          <p>账户总额： <span class="font_style1"><fmt:formatNumber pattern="0.00" value="${viewAgentInfo.balanceAndCheckAmount}"/> 元</span></p>
          <p>可用余额： <span class="font_style1"><fmt:formatNumber pattern="0.00" value="${viewAgentInfo.allowBalanceAndCheckAmount}"/> 元</span>&nbsp;
          <c:check code="sc01">
	          <html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">充值</html:link>
          </c:check>
          </p>
          <p>冻结余额： <span class="font_style1"><fmt:formatNumber pattern="0.00" value="${viewAgentInfo.notallowBalance}"/> 元</span>&nbsp;</p>
          <p>信用余额： <span class="font_style1"><fmt:formatNumber pattern="0.00" value="${viewAgentInfo.creditBalance}"/> 元</span>&nbsp;</p>
          <p>上一次登录时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${viewAgentInfo.currentLoginDate}"/></p>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">

           <div class="main_mid_title">
          	<strong>账户明细查询</strong>
          </div>
		
         <p>&nbsp;账户明细： <html:link page="/transaction/transactionlist.do?thisAction=accountDetaillist&selectDealDate=1&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,4&balanceType=0">点此查询</html:link></p>
        </div>
      </div>
    </div>
 <c:import url="/_jsp/footer.jsp"/>
  </div>
  <!-- 弹出层 -->
   <div style="position:absolute;border:1px solid #FF9000; background-color: #FFFFFF; right:0; top: 100px; width: 254px; height: 164px;" id="height_from">
<table width="100%"  border=0>
   <tr>
    <td width="100%" valign="top" align="center">
     <table cellSpacing="0" cellPadding="0" width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
       <td width="75" class="loft_win_head">&nbsp; 温馨提示</td>
       <td width="21" class="loft_win_head"> </td>
       <td align="right" class="loft_win_head">
        <span style="CURSOR: hand;font-size:12px;font-weight:bold;margin-right:4px;" onclick="closeDiv();" >×</span>
       </td>
      </tr>
     </table>
    </td>
   </tr>
   <tr><td>&nbsp;</td></tr>
   <c:if test="${article>0}">
   <tr><td><img src="../_img/reminder .gif"/>&nbsp;您有未处理的业务：</td></tr>
   <tr><td>&nbsp;</td></tr>
   <tr><td><a href="../transaction/transactionlist.do?thisAction=listTransactions&menuList=1,1&status=1&flag=1" style="color:#005C9C;text-decoration:none;" target="_blank">未处理的交易：<font color="red"><c:out value="${article}"></c:out>笔</font></a></td></tr>
  	</c:if>
  	<c:if test="${articleRedPacket>0}">
  	<tr><td>
  	<c:forEach var="info" items="${trans.operate}">
  		<a href="<c:out value="${info.url}"></c:out>" style="color:#005C9C;text-decoration:none;" >未处理的红包：<font color="red"><c:out value="${articleRedPacket}"></c:out>笔</font></a>&nbsp;&nbsp;&nbsp; <html:image page="/_img/redPacket.gif"/>
  	</c:forEach>
  	</td></tr>
  	</c:if>
  	<c:if test="${article==0}">
  	<tr><td><img src="../_img/reminder .gif"/>&nbsp; 您没有未处理的业务! </td></tr>
  	</c:if>
  </table>
</div>


  <!-- 弹出层2 -->
   <div style="position:absolute; width: 500px; height: 458px; display: none;"  id="height_from2">
   <c:forEach var="info" items="${trans.operate}">
<a href="<c:out value="${info.url}"></c:out>" style="color:#005C9C;text-decoration:none;"><img src="<%=request.getContextPath()%>/<c:out value="${shopUrl}"></c:out>"/></a>
</c:forEach>
</div>
   <!-- 弹出层 -->
<script language="JavaScript" type="text/javascript">
	var i=0;
	function rightBottomAd(){
 		i+=1;
  		//if(i>100) closeDiv(); //想不用自动消失由用户来自己关闭所以屏蔽这句
		var abc = document.getElementById("height_from");
		
		abc.style.top = document.documentElement.scrollTop+document.documentElement.clientHeight-165+"px";
		
		setTimeout(function(){rightBottomAd();},50);
	}
	rightBottomAd();
	//关闭
	 function closeDiv()
	 {
	  document.getElementById('height_from').style.visibility='hidden';
	 }
	 
	 function closeDiv2(){
	   document.getElementById('height_from2').style.visibility='hidden';
	 }
	 
	 function showRedPacket(){
	 	if(i>100){
	 		closeDiv2();
	 	}
	 	 document.getElementById('height_from2').style.display='block';
	 	var abc2=document.getElementById("height_from2");
		var left=(document.body.scrollWidth-500)/2;
		var top=155;
		abc2.style.left=left;
		abc2.style.top=top;
		setTimeout(function(){showRedPacket();},50);
	 }
	 var articleRedPacket='<c:out value="${articleRedPacket}"></c:out>';
	 if(articleRedPacket>0){
	 	showRedPacket();
	 }
	 
	 	function transactionDetailByRedPacket(tid,statusid,orderid,flag,agentid,type){
			window.open("../transaction/transaction.do?thisAction=transactionDetailByRedPacket&flag="+flag+"&type="+type+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid);
		}
</script>
   
</body>
</html>
