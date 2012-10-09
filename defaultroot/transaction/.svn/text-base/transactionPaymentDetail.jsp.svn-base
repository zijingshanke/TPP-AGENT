<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionPaymentDetail.jsp -->

  <head>  
<title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" language="javascript" src="../_js/jquery-1.3.1.min.js"></script>
	<script src="../_js/common.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<script language="javascript">
		function getRefundMentDetail(tid,orderid,flag){
			window.location.href="../transaction/transaction.do?thisAction=getRefundMentDetail&tid="+tid+"&orderid="+orderid+"&flag="+flag;
		}
		
		function getTransactionPaymentDetailById(tid,statusid,orderid,flag,agentid,type){
			window.location.href="../transaction/transaction.do?thisAction=getTransactionPaymentDetailById&shipment=1&flag="+flag+"&agentid="+agentid+"&statusid="+statusid+"&tid="+tid+"&orderid="+orderid+"&type="+type+"&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3";
		}
		
		function changeTransportType(item){
			var transportType = item.value;
			if(transportType==2){
				document.getElementById("shopCompanyNameTR").style.display="none";
				document.getElementById("shopOrderNumTR").style.display="none";
			}
			else{
				document.getElementById("shopCompanyNameTR").style.display="block";
				document.getElementById("shopOrderNumTR").style.display="block";
			}
		}
		
		function checkBeforeSubmit(){
			var type = document.getElementsByName("type");
			var companyName = document.getElementById("companyName");
			var no = document.getElementById("no");
			var note = document.getElementById("note");
			var num=0;
			
			for(var i=0;i<type.length;i++){
				if(document.forms[0].type[i].checked){
					num = document.forms[0].type[i].value;
				}
			}
			if(num!=2){
				if(companyName==null||typeof(companyName)=="undefined"||companyName.value==""){
					alert("请输入承运公司名称!");
					companyName.focus();
					return false;
				}
				if(no==null||typeof(no)=="undefined"||no.value==""){
					alert("请输入运单号!");
					no.focus();
					return false;
				}
			}
			if(note==null||typeof(note)=="undefined"||note.value==""){
					alert("请输入备注!");
					note.focus();
					return false;
			}
			document.forms[0].submit();
		}
	</script>
  	
  </head>
    <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
    
     <div class="main_title">
          <div class="main_title_right">
           <p>
     		<c:if test="${flag==1}">
        		<strong>买家已付款,等待你发货</strong> 
        	</c:if>
        	<c:if test="${flag==0}">
        		<strong>等待卖家发货中…… </strong> 
        	</c:if>
        	&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=listTransactions&showTabMenuIdList=1,3&showSubMenuIdList=0,4,0,3&flag=1" >交易管理</html:link>
        	</p>
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
            <th><div>商品名称 (交易号：<c:out value="${transaction.orderDetails.orderDetailsNo}"/>)<img src="../_img/beizhu.gif" /></div></th>
            <th><div>单价</div></th>
            <th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;">数量</div></th>
            <th><div style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">邮费</div></th>
            <th><div>折扣/涨价</div></th>
			<th><div style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;">应付金额 (元)</div></th>
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
	              <a style="color: #005c9c;" href="<c:out value="${transaction.orderDetails.shopUrl}"/>"><c:out value="${transaction.orderDetails.shopName}"/></a>
	            </c:if>
            </td>
            <td><fmt:formatNumber pattern="0.00" value="${transaction.orderDetails.shopPrice}"/></td>
			<td style="background:url(../_img/bg_sign.png) no-repeat 4px -38px; padding-left: 16px;"><c:out value="${transaction.orderDetails.shopTotal}"/></td>
            <td style="background:url(../_img/bg_sign.png) no-repeat 4px 10px; padding-left: 16px;">
				<c:choose>
	            <c:when test="${transaction.orderDetails.emailPrice==null}">
				0.00
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
            <td style="background:url(../_img/bg_sign.png) no-repeat 4px -63px; padding-left: 16px;"><fmt:formatNumber pattern="0.00" value="${transaction.amount}"/></td>
          </tr>
          
             <tr>
                 <td colspan="7"><strong>商品描述：</strong><c:out value="${transaction.orderDetails.detailsContent}"/><p><strong>收货人：</strong><c:out value="${transaction.fromAgent.name}"/></p>
                 <c:if test="${agentAddressInfo!=null}">
                 <p><strong>收货地址：</strong><c:out value="${agentAddressInfo.address}"/> ,手机 : <c:out value="${agentAddressInfo.mtel}"/> ,电话 : <c:out value="${agentAddressInfo.tel}"/> ,邮编 : <c:out value="${agentAddressInfo.postCode}"/></p>
                 </c:if>
                 <c:if test="${agentAddressInfo==null}">
                 <p><strong>收货地址：</strong>不需收货地址</p>
                 </c:if>
                 </td>
             </tr>
             <tr bgcolor="#F8F8F8">
                 <td colspan="7">
                 	<c:if test="${flag==1}">
	                 	<c:if test="${type==1}"> <!-- 点击查看 -->
	                 		付款时间
	                 	</c:if>
	                 	<c:if test="${type==0}"> <!-- 点击确认发货 -->
						 	购买时间
						</c:if>
					</c:if>
					
					<c:if test="${flag==0}">
						付款时间
					</c:if>
                 </td>
             </tr>
             
              <tr bgcolor="#F8F8F8">
                 <td colspan="7"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${transaction.payDate}"/></td>
             </tr>
        </table>
		<div class="tableRight">
        	<span class="speak-top"><span><p>
        	<c:if test="${flag==1}">
        	买家已付款，请及时发货。
        	</c:if>
        	<c:if test="${flag==0}">
        	等待卖家发货中…… 
        	</c:if>
        	</p></span></span>
        	<c:if test="${flag==0}">
        	<em>卖家：<c:out value="${transaction.toAgent.name}"/>(<c:out value="${transaction.toAgent.loginName}"/>)</em>
        	</c:if>
        	<c:if test="${flag==1}">
        	<em>买家：<c:out value="${transaction.fromAgent.name}"/>(<c:out value="${transaction.fromAgent.loginName}"/>)</em>
        	</c:if>
            <span class="headImg"><img src="../_img/user_pic.gif" style="margin:3px;"/></span>
            <span style="float: left; margin-top: 12px; margin-left: 12px">
                <a href="#" style="color:#005c9c;">查看信用</a>
                <a href="#" style="color:#005c9c;">留言</a>
            </span>
        </div>
        <!--  
		<div class="tableRight">
		    <span class="speak-top"><span><p>买家已付款，请及时发货。</p></span></span>
        	<em>卖家：<c:out value="${transaction.toAgent.name}"/>（<c:out value="${transaction.toAgent.loginName}"/>)</em>
            <span class="headImg"><img src="../_img/user_pic.gif" style="margin:3px;"/></span>
            <span style="float: left; margin-top: 12px; margin-left: 12px">
            </span>
        </div>
        -->
        <div class="clear"></div>
        </div>
      </div>
    </div>
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
	 
		<c:if test="${flag==0 && (transaction.status==10 || transaction.status==11)}">
		<div class="receiving_1">
		<table cellpadding="0" cellspacing="0" width="946">

			<tr>
			<td  align="center" colspan="2">	
				<strong style="color:#F60; font-size:16px; line-height:50px;">您已申请退款:</strong>
			</td>
			<tr>
				<td align="center" colspan="2">
				<span class="simplyBtn_1">
				<input type="button" name="btn" value="查看退款详情" class="buttom_middle" onclick="getRefundMentDetail('<c:out value="${transaction.id}" />','<c:out value="${transaction.orderDetails.id}" />','<c:out value="${flag}" />')"/>
				</span>
				</td>
			</tr>
		</table>
		</div>
		</c:if>
		
		<c:choose>
			<c:when test="${flag==1}">
				<c:if test="${shipment==null}">
					<div class="receiving_1">
					<table cellpadding="0" cellspacing="0" width="946">
						<tr>
						<td align="center" colspan="2">	
							<strong style="color:#F60; font-size:16px; line-height:50px;">买家已付款,请发货:</strong>
						</td>
						</tr>
						<td align="center" colspan="2"><span class="simplyBtn_1"><input type="button" name="btn" value="立即发货" class="buttom_middle" onclick="getTransactionPaymentDetailById('<c:out value="${transaction.id}" />','<c:out value="${transaction.status}" />','<c:out value="${transaction.orderDetails.id}" />','<c:out value="${flag}" />','<c:out value="${transaction.fromAgent.id}"/>',0)"/></span></td>
						</tr>
					</table>
					</div>
				</c:if>
				
				<c:if test="${shipment!=null}">
				<div class="receiving receiving_2">
				<html:form action="transaction/logistics.do?thisAction=shippingConfirm">
					
						<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
							<tr>
								<td colspan="2" style="font-size:12px;"><strong style="color: #C60; font-size:14px; margin:0 0 0 12px;">买家已付款，请填写发货信息：</strong> 请卖家在此核实交易收货人地址是否与您的发货地址相同，如有不同可能会导致买家无法正常接收货物，影响交易顺利完成。</td>
							</tr>
							<tr>
								<td width="159" valign="top" class="right_td"><span class="font_style6">*</span> 选择物流方式：</td>
								<td width="785">
								<input type="radio"  name="type" value="0" checked style="vertical-align: middle;" onclick="changeTransportType(this);"/>快递<p>
								<input type="radio"  name="type" value="1" onclick="changeTransportType(this);" style="vertical-align: middle;" />平邮</p>
								<p><input type="radio"  name="type" value="2" onclick="changeTransportType(this);" style="vertical-align: middle;" />不要运输</p>
								</td>
							</tr>

							<tr id="shopCompanyNameTR">
								<td class="right_td"><span class="font_style6">*</span> 承运公司名称：</td>
								<td><input type="text" name="companyName" id="companyName" value="" class="text_style"/></td>
							</tr>
							<tr id="shopOrderNumTR">
								<td class="right_td"><span class="font_style6">*</span> 运单号：</td>
								<td><input type="text" name="no" id="no" value="" class="text_style"/></td>
							</tr>
							<tr>
								<td class="right_td"><span class="font_style6">*</span> 备注：</td>
								<td><textarea name="note" id="note" style="width:400px; height:60px;" class="text_style"></textarea></td>
							</tr>
							<tr>
								<td class="right_td">&nbsp;</td>
								<td><input type="button" name="btn1" value="发货" class="btn1" onclick="checkBeforeSubmit()"/></td>
							</tr>
						</table>
					
					<input type="hidden" name="orderId" value="<c:out value="${transaction.orderDetails.id}"/>"/>
					<input type="hidden" name="tid" value="<c:out value="${transaction.id}"/>"/>
					</html:form>
					<div class="receiving-bottom">
		            	<strong style="margin-left:12px; line-height: 20px;">温馨提示：</strong>发生交易纠纷或资金损失，绝大多数是因为发货地址错误造成的，请通过页面中买家提供的联系方式联系买家确认发货地址的有效性
		            </div>
				</div>
				</c:if>
		    </c:when>
		</c:choose>

 
  </div>
  </div>
  </div>
   
  </div>
  <c:import url="/_jsp/footer.jsp"/>
  </body>
  <script language="javascript">
  	var tp = "<c:out value='${transaction.orderDetails.logisticsType}'/>";
  	var shipment = "<c:out value='${shipment}'/>";
  	if(shipment!=""){
	  	if(tp==2){
			document.getElementById("shopCompanyNameTR").style.display="none";
			document.getElementById("shopOrderNumTR").style.display="none";
		}
	  	document.forms[0].type[tp].checked=true;
  	}
  </script>
</html>
