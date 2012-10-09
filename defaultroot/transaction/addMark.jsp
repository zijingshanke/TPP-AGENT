<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/addMark.jsp -->

  <head>  
    <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.1.min.js"></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<script language="javascript">
		function addMark(){
			var mark = document.getElementById("mark").value;
		}
		
		function checknum(){
		 var nn;
		 if(document.getElementById("mark").value.length>50){
		      document.getElementById("mark").value = document.getElementById("mark").value.substring(0,50); 
		 }
		 else{
		    nn=50-document.getElementById("mark").value.length;
		    document.getElementById("syzs").innerHTML="剩余字数："+nn;
		 }
		}
	</script>
  
  </head>
    <body>
    <div id="warp">
    <c:if test="${fg=='seller'}">
    	<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,9,6" />
    </c:if>
    <c:if test="${fg=='buyer'}">
    	<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,8,6" />
    </c:if>
    <c:if test="${fg==null}">
    	<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>
    </c:if>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
    	
    	<div class="main_title">
    		<div class="main_title_right">
              <p>
              <strong>添加交易备注</strong>&nbsp;
              <c:if test="${URI.agentUser==null}">
              <html:link page="/transaction/transactionlist.do?thisAction=listTransactions&flag=1">返回交易管理</html:link>
             </c:if>
              </p>
            </div>
        </div>
        <div class="main_title2"  style="margin-top:12px;">
            <span class="font_style7">交易信息</span>
         </div>
        <div>
        <div class="refundAgreement">
                <ul>
                    <li><span>交易号：</span><c:out value="${transaction.orderDetails.orderDetailsNo}"/></li> 
                    <li><span>交易类型：</span><c:out value="${transaction.typeCaption}"/></li>
                   
                    <li><span>交易状态：</span><c:out value="${transaction.transactionStatus}" /></li>
                    <li><span>商品名称：</span><c:out value="${transaction.orderDetails.shopName}"/></li>
                    <li><span>购物实付总价：</span><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/> <b>元</b></li>
                </ul>
        </div>
		<div class="refundAgreement-right">
                <span class="refundAgreement-right-title"><img src="../_img/Arrow.gif" style="vertical-align: middle; margin-right: 8px;"/>操作提示</span>
                <p>您可以选择不同的图标，来标记您的交易。</p><p>备注最多输入50个汉字。</p>
        </div>
       <div class="clear"></div>
        <div class="refundAgreementMore">
                <p style="font-size:14px;"><strong>编辑备注信息</strong></p>
       </div>
       
       <div class="refundAgreementModified">
              	<form name="transaction" method="post" action="../transaction/transaction.do?thisAction=addMark">
              	<input type="hidden" name="tid" value="<c:out value='${transaction.id}'/>" />
              	<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                    <tr>
                      <td class="right_td right_td_1">标准：</td>
                      <td>
                      	<input name="flagStatus" type="radio" value="1"/><img src="../_img/Remarks-1.gif" style=" margin: 4px 4px 0 4px;"/>
                        <input name="flagStatus" type="radio" value="2" /><img src="../_img/Remarks-2.gif" style=" margin: 4px 4px 0 4px;"/>
                        <input name="flagStatus" type="radio" value="3" /><img src="../_img/Remarks-3.gif" style=" margin: 4px 4px 0 4px;"/>
                        <input name="flagStatus" type="radio" value="4" /><img src="../_img/Remarks-4.gif" style=" margin: 4px 4px 0 4px;"/>
                        <input name="flagStatus" type="radio" value="5" /><img src="../_img/Remarks-5.gif" style=" margin: 4px 4px 0 4px;"/>
                        <input name="flagStatus" type="radio" value="6" /><img src="../_img/Remarks-6.gif" style=" margin: 4px 4px 0 4px;"/>
                      </td>
                    </tr>
                    <tr>
                      <td class="right_td right_td_1" valign="top">备注：</td>
                      <td>
                      <textarea id="mark" name="mark" cols="60" rows="3" onPropertyChange="checknum()" style="width: 400px; height: 100px;"><c:out value="${transaction.mark}"/></textarea>
                      <span name="syzs" id="syzs"></span>
                      </td>
                    </tr>
               		<tr>
              		  <td colspan="2" style="padding-left:200px;">
              		  <c:if test="${URI.agentUser!=null}">
	              		  <span class="simplyBtn_1">
	              		 	 <input type="submit" name="sendMessage" value="确 定" class="buttom_middle" disabled />
	              		 </span>
              		 </c:if>
              		 <c:if test="${URI.agentUser==null}">
	              		  <span class="simplyBtn_1">
	              		 	 <input type="submit" name="sendMessage" value="确 定" class="buttom_middle" />
	              		 </span>
              		 </c:if>
              		  </td>
            		</tr>
                 </table>
                 </form>
          </div>
          <div class="refundAgreement-bottom">
          <c:if test="${URI.agentUser==null}">
              	<img src="../_img/leftArrow.gif" style="vertical-align: middle; margin-right: 8px;"/>
              	<html:link page="/transaction/transactionlist.do?thisAction=refundMentManage">返回退款管理</html:link>
            </c:if>  	
              	<img src="../_img/Wroning_small.gif" style="vertical-align: middle; margin-right: 8px;"/><a href="#">查看留言</a>
         </div>
         </div>   	
  </div>
  </div>
  </div>
  </div>
  <c:import url="/_jsp/footer.jsp"/>
  </body>
</html>
<script language="javascript">
 var fstauts = "<c:out value='${transaction.flagStatus}'/>";
 for(var i=0;i<document.transaction.flagStatus.length;i++){
     if(document.transaction.flagStatus[i].value==fstauts){
       document.transaction.flagStatus[i].checked=true;
       break;
     }
     else{
     	document.transaction.flagStatus[5].checked=true;
     }
 } 
</script>
