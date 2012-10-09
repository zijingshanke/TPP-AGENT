<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionSuccessAndFailDetail.jsp -->

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
		
		function userMark(tid,orderid){
			window.location.href="../transaction/transaction.do?thisAction=userMark&tid="+tid+"&orderid="+orderid;
		}
	</script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
  	
  </head>
    <body>
    <div id="warp">
    <c:if test="${URI.agentUser==null}">
    	<c:if test="${fg=='seller'}">
    		<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,9,6" />
    	</c:if>
    	<c:if test="${fg==null}">
	    	<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>  	
	    </c:if>
    </c:if>
    <c:if test="${URI.agentUser!=null}">
	    	<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,1,6" />     
    </c:if>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>查看交易详情</strong> &nbsp;
              <c:if test="${URI.agentUser==null}">
              <html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1&selectDealDate=1">返回交易管理</html:link>
              </c:if>
              </p>
            </div>
          </div>
           <c:if test="${statusid==3}">
	          <div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle">
	            	<em>交易号 <c:out value="${transaction.orderDetails.orderDetailsNo}"/> 的交易已经成功</em>
	                <p>交易结束时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm" value="${transaction.accountDate}"/></p>
	            </div>
	          </div>
	          
          </c:if>
          <c:if test="${statusid==4 || statusid==11}"> <!-- 直接关闭交易和申请退款成功 -->
          	<div class="paymentFailure">
	          	<div class="paymentFailureTitle">
	            	<em>交易号 <c:out value="${transaction.orderDetails.orderDetailsNo}"/> 的交易已经关闭</em>
	                <p>关闭原因：<c:out value="${transaction.closeNote}"/></p>
	                <p>关闭时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.closeDate}"/></p>
	            </div>
	          </div>
          </c:if>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">

		  <table cellpadding="0" cellspacing="0" width="680" class="deal_table" style="margin-top: 12px; float:left">
             <tr>
                 <th style="text-align: center"><div>类型</div></th>
                 <th><div>商品或服务名称 （交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>）
                 <a href="javascript:userMark('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />')">
                 <c:if test="${transaction.flagStatus==1}">
                	<img src="../_img/Remarks-1.gif" alt="<c:out value="${transaction.mark}"/>"/>
                </c:if>
                 <c:if test="${transaction.flagStatus==2}">
                	<img src="../_img/Remarks-2.gif" alt="<c:out value="${transaction.mark}"/>"/>
                </c:if>
                 <c:if test="${transaction.flagStatus==3}">
                	<img src="../_img/Remarks-3.gif" alt="<c:out value="${transaction.mark}"/>"/>
                </c:if>
                 <c:if test="${transaction.flagStatus==4}">
                	<img src="../_img/Remarks-4.gif" alt="<c:out value="${transaction.mark}"/>"/>
                </c:if>
                 <c:if test="${transaction.flagStatus==5}">
                	<img src="../_img/Remarks-5.gif" alt="<c:out value="${transaction.mark}"/>"/>
                </c:if>
                 <c:if test="${transaction.flagStatus==6}">
                	<img src="../_img/Remarks-6.gif" alt="<c:out value="${transaction.mark}"/>"/>
                </c:if>
                <c:if test="${transaction.flagStatus==null}">
                	<img src="../_img/Remarks-6.gif" alt="编辑备注"/>
                </c:if>
                </a>
                 </div></th>
                 <th><div><c:if test="${flag==1}">应收</c:if><c:if test="${flag==0}">应付</c:if>金额 (元)</div></th>
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
                 <td style="color: #005c9c; font-size:16px; font-weight: bold;"><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></td>
             </tr>
             <c:if test="${statusid==11}">
             <tr>
             	<td colspan="3">成功退款金额：<fmt:formatNumber pattern="0.00" value="${transaction.amount}"/> 元</td>
             </tr>
             </c:if>
             <tr bgcolor="#F8F8F8">
                 <td colspan="2"><c:if test="${flag==1}">收款</c:if><c:if test="${flag==0}">付款</c:if>时间</td>
                 <td>交易结束时间</td>
             </tr>
             <tr>
                 <td colspan="2"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.accountDate}"/></td>
                 <td>
                 <c:if test="${transaction.closeDate!=null}">
                 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.closeDate}"/>
                 </c:if>
                 <c:if test="${transaction.closeDate==null}">
                 <fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.payDate}"/>
                 </c:if>
                 
                 </td>
             </tr>
              <tr bgcolor="#F8F8F8">
                 <td colspan="3" >支付说明</td>
             </tr>
              <tr>
                 <td colspan="3" ><c:out value="${transaction.orderDetails.detailsContent}"/></td>
             </tr>
             <logic:notEmpty name="letter" scope="request">
           	<tr bgcolor="#F8F8F8"><td colspan="3">授信/还款金额说明</td></tr>
           	<tr>
                 <td colspan="3" ><c:out value="${transaction.showAmountType}" /></td>
             </tr>
             </logic:notEmpty>
        </table>
        <div class="tableRight">
            <span class="speak">
            <c:choose>
            	<c:when test="${statusid==3}">
            		交易成功
            	</c:when>
            	<c:otherwise>
            		交易关闭
            	</c:otherwise>
            </c:choose>
            
            </span>

            	<c:if test="${flag==1}">
	        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>（<c:out value="${transaction.fromAgent.loginName}"/>)</em>
	        	</c:if>
	        	
	        	<c:if test="${flag==0}">
	        	<em>卖家：<c:out value="${transaction.toAgent.name}"/>（<c:out value="${transaction.toAgent.loginName}"/>)</em>
	        	</c:if>
            
            <span class="headImg"><img src="../_img/user_pic.gif" /></span>
        </div>
        <div class="clear"></div>
  </div>
  </div>
  </div>
  <c:import url="/_jsp/footer.jsp"/>
  </div>
  
  </body>
</html>
