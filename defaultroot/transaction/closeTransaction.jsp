<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/closeTransaction.jsp -->

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
			window.location.href="../transaction/transaction.do?thisAction=getTransactionById&tid="+tid+"&orderid="+orderid+"&type="+type;
		}
		
		function updateTransactionStatus(statusid,tid){
		    if (!confirm("确实要关闭交易吗？")) return;
			window.location.href="../transaction/transaction.do?thisAction=updateTransactionStatus&statusid="+statusid+"&tid="+tid;
		}
		
		function transactionPayment(tid,statusid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=transactionPayment&flag=1&statusid="+statusid+"&tid="+tid+"&orderid="+orderid;
		}
		
		function showMessageBox() {
			if(document.getElementsByName('closeReson')[0].value=='其他') {
				document.getElementsByName('otherMsg')[0].style.display="";
			} else {
				document.getElementsByName('otherMsg')[0].style.display="none";
				document.getElementsByName('otherMsg')[0].value="";
			}
		
		}
		
		function checkReasonAndClose() {
			if(document.getElementsByName('closeReson')[0].value=="请选择关闭理由") {
				alert("请选择关闭理由！");
				return false;
			}
			else if(document.getElementsByName('closeReson')[0].value=="其他"&&document.getElementsByName('otherMsg')[0].value==""){
				alert("请输入关闭理由！");
				document.getElementsByName('otherMsg')[0].focus();
				return false;
			}
			else {
				if (!confirm("您确认关闭本交易吗？")) return;
				
				document.forms[0].submit();
			}
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
              <p><strong>关闭交易</strong>&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&flag=1">返回交易管理</html:link></p>
          </div>
          </div>
       </div>
    </div>
   </div>
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
		  <table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">
             <tr>
                 <th style="text-align: center"><div>类型</div></th>
                 <th><div>商品或服务名称 （交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>）<img src="../_img/beizhu.gif" /></div></th>
                 <th><div>单价</div></th>
                 <th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">数量</div></th>
                 <th><div style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">邮费</div></th>
                 <th><div>折扣/涨价</div></th>
               <th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">应收金额 (元)</div></th>
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
		              <a href="<c:out value="${transaction.orderDetails.shopUrl}"/>"><c:out value="${transaction.orderDetails.shopName}"/></a>
		            </c:if>
                 </td>
                 <td><fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.shopPrice}"/></td>
                 <td style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;"><c:out value="${transaction.orderDetails.shopTotal}"/></td>
                 <td style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">
                   <c:choose>
		            <c:when test="${transaction.orderDetails.emailPrice==null}">
					0
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
                 <td  style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;"><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></td>
             </tr>
             <tr>
                 <td height="50" colspan="7" valign="top"><strong>商品描述：</strong><c:out value="${transaction.orderDetails.detailsContent}"/></td>
             </tr>
            
        </table>
        <div class="tableRight">
        	<span class="speak-top"><span>修改了交易价格或者想关闭交易，请及时通知买家。</span></span>
        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>)</em>
            <span class="headImg"><img src="../_img/user_pic.gif" /></span>
        </div>
        <div class="clear"></div>
        <div class="closeTransaction">
        <div class="closeTransactionTitle">
           <em>关闭交易</em>
           <p><strong>特别提醒。</strong></p>
           <p>请确认以下信息：</p>
           <ul>
           	 <li>请您确认本次关闭交易的操作已经通知买家，并于买家取得一致意见。</li>
             <li>如果您是单方面的关闭交易，将有可能导致买家对您的投诉。并进而影响您通过钱门进行交易权利。</li>
           </ul>
        </div>
        <html:form action="transaction/transaction.do?thisAction=closeTransactionConfirm">
        <input type="hidden" name="tid" value="<c:out value="${transaction.id}"/>"/>
        <input type="hidden" name="orderid" value="<c:out value="${transaction.orderDetails.id}"/>"/>
        	<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
        		<tr>
        		    <td class="right_td"><span class="font_style6">* </span>选择关闭理由：</td>
        			<td>
        				<select name="closeReson" onChange="showMessageBox()">
						<option value="请选择关闭理由">请选择关闭理由</option>
						<option value="长时间联系不到买家，交易不成功">&nbsp;&nbsp;&nbsp;长时间联系不到买家，交易不成功</option>
						<option value="买家购买意向不明确，取消交易">&nbsp;&nbsp;&nbsp;买家购买意向不明确，取消交易</option>
						<option value="买家已经通过网上银行直接汇款给我了">&nbsp;&nbsp;&nbsp;买家已经通过网上银行直接汇款给我了</option>
						<option value="买家没有开通网上银行，已经通过银行汇款给我了">&nbsp;&nbsp;&nbsp;买家没有开通网上银行，已经通过银行汇款给我了</option>
						<option value="买家不想购买了">&nbsp;&nbsp;&nbsp;买家不想购买了</option>
						<option value="同城交易，已经见面完成交易">&nbsp;&nbsp;&nbsp;同城交易，已经见面完成交易</option>
						<option value="没有货了，交易无法完成">&nbsp;&nbsp;&nbsp;没有货了，交易无法完成</option>
						<option value="其他">&nbsp;&nbsp;&nbsp;其他</option>
						</select>&nbsp;
						<input id="otherMsg" name="otherMsg" value="" type="text" class="text_style" style="display:none">
        			</td>
        		</tr>
        		<tr>
        		<td>
        			<input type="button" name="btn" class="btn1" value="确定关闭" onclick="checkReasonAndClose()"/>
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
</html>
