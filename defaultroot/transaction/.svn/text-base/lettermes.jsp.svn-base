<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- transaction/lettermes.jsp -->
  <head>
<title>钱门支付！--网上支付！安全放心！</title>   

	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />

  </head>
  
  <body>

  <div id="warp">
      <div id="warp">
     <c:if test="${URI==null}">
   <c:import url="../selfHelp/head.jsp"></c:import>
   </c:if>
   <c:if test="${URI!=null}">
   <c:import url="../_jsp/mainTop.jsp"></c:import>
   </c:if>
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>操作有误</strong></p>
            </div>
          </div>
          
          <div class="sendToEmail">
          	<div class="sendToEmailTitle">
            	<em>您还没有加入任何信用支付圈<!--  ， <a href="http://" >点此申请加入商务圈</a>--></em>
                <p>信用支付圈是钱门为用户之间提供的信用支付交易</p>
            </div>
            <ul class="sendToEmailList">
            	<li>授信是针对圈内用户的一种信用支付。 </li>
                <li>加入信用支付圈以后,如圈主有给您授信金额,您才可以授信还款。 </li>
            </ul>
          </div>
          <div class="goBack">
          	<img src="../_img/goBack.gif" /><html:link page="/transaction/transactionlist.do?thisAction=listTransactions&selectDealDate=1&flag=1">返回交易管理</html:link>
          </div>
        </div>
      </div>
    </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
</div>
</body>
</html>
