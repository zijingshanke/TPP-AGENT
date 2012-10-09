<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/debit.jsp -->
	<head>
<title>钱门支付！--网上支付！安全放心！</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/prototype.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script language="javascript">
		function showAndHideContract(){
		    var btn = document.getElementById("b1").name;
		    if(btn=="b1"){
				document.getElementById("contract").style.display = "block";
				document.getElementById("b1").name="b2";
			}
			else if(btn=="b2"){
				document.getElementById("contract").style.display = "none";
				document.getElementById("b1").name="b1";
			}
		}
		
		function searchContact(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var searchContactInput = document.getElementById("searchContactInput").value;
			if(searchContactInput=="点此输入姓名或账户名"){
				alert("请输入搜索条件");
				return ;
			}
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=getListContact&searchContactInput="+encodeURIComponent(searchContactInput),{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			var contactListDiv = document.getElementById("contactListDiv");
			contactListDiv.innerHTML=result;
			}, onException:showException});
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
		
		function selectBuyerEmail(email){
			document.transaction.buyerAccount.value=email;
			document.getElementById("contract").style.display = "none";
			document.getElementById("b1").name="b1";
		}
		
		function checkPrice(obj){
		    var msg = document.getElementById("shopPriceDiv");
		    msg.innerHTML= "";
			var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/; 
			if(!re.test(obj.value)){
				msg.innerHTML= "金额只能由数字组成且最多带有2位小数";
				obj.focus();
				return false;
			}
			if(obj.value<=0.01||obj.value>1000000.00){
				msg.innerHTML= "金额必须是大于0.01及小于1000000.00的数字";
				obj.focus();
				return false;
			}
		}
		
		function checkForm(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var buyerAccount = document.getElementById("buyerAccount");
			var amount = document.getElementById("amount");
			var remark = document.getElementById("remark");
			var errorEmailMsg = document.getElementById("errorEmailMsg");
			var note=document.getElementById("note");
			var msg = document.getElementById("amountDiv");
		    msg.innerHTML= "";
			
			var currentAccount = "<c:out value='${loginAccount}'/>";
			var allowBalance = "<fmt:formatNumber value='${agent.allowBalance}' pattern='0.00' />";
		    
		    if(buyerAccount.value.trim()==""){
		    	alert("请输入借款方钱门账户!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
		    else{
		    	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!buyerAccount.value.match(reg)){
					buyerAccount.value = buyerAccount.value.trim();
		    		buyerAccount.focus();
					errorEmailMsg.innerHTML="<font color='red'>Email格式不对！</font>";
					return false;
				}
				else{
					errorEmailMsg.innerHTML = "账户名为E-mail地址";
				}
		    }

		    if(buyerAccount.value.toLowerCase()==currentAccount.toLowerCase()){
		    	alert("借款账户不能和自己相同!");
		    	buyerAccount.value = "";
		    	buyerAccount.focus();
		    	return false;
		    }
 
		    
		    if(amount.value==""){
		    	alert("请输入预支金额!");
		    	amount.focus();
		    	return false;
		    }
		    else{
		    	var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
				if(!re.test(amount.value)){
					msg.innerHTML= "金额只能由数字组成且最多带有2位小数";
					amount.focus();
					return false;
				}	
		    }
		    
		    if(remark.value.trim()==""){
		    	alert("请输入申请原因!");
		    	remark.value = "";
		    	remark.focus();
		    	return false;
		    }
		    else{
		    	remark.value = remark.value.trim();
		    }
		    var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=getRegisterTypeByEmail&agentName="+encodeURIComponent(buyerAccount.value),{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
				if(result==0){
					errorEmailMsg.innerHTML= "<font color='red'>预支钱门帐号应为企业帐号!</font>";
					return false;
				}
				else{
					document.forms[0].submit();
				}
			}, onException:showException});
			
		}
	</script>
		
	</head>

	<body>
		<div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,3,3" />
	 <div id="main">
      <div id="left_box">
        <div class="leftbox_top">
          <div class="leftbox_bottom">
            <div class="leftbox_count">
              <p class="leftbox_count_p"><a href="../transaction/transaction.do?thisAction=debit">申请预支</a></p>
              <p><a href="../transaction/transactionlist.do?thisAction=getDebitList">预支记录</a></p>
            </div>
          </div>
        </div>
      </div>
      <div id="right_box">
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
              <div class="rightbox_title">
                <div class="rightbox_title_right">
                  <div class="rightbox_title_count">
                  申请记录
                  </div>
                </div>
              </div>

      		<div class="chongzhi">
      		<html:form method="post" action="transaction/transaction.do?thisAction=addDebit">
      		 <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">你的钱门账户：</td>
              <td style="color:#005C9C"><c:out value="${agent.loginName}" /></td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">*</span> 预支钱门账户：</td>
              <td><input type="text" class="text_style" style="width:270px;" id="buyerAccount" name="buyerAccount"/> <input type="button" value="从联系人中选取" class="btn2" onclick="showAndHideContract()" name="b1" id="b1"/></td>
            </tr>
            <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span"><div id="errorEmailMsg">账户名为E-mail地址</div></td>
            </tr>
            
             <tr>
             <td class="td_span">&nbsp;</td>
		    <td>
		    	<div id="contract" class="Contact_box" style="margin-left:0">
			    <div class="Contact_title">
				<em>联系人</em> <html:link page="/agent/agentlist.do?thisAction=listContact&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6"><span>编辑</span></html:link>
			        <input type="text" class="text_style" value="点此输入姓名或账户名" onclick="this.value=''" id="searchContactInput" name="searchContactInput" maxlength="25"/>
			        <span class="simplyBtn_1"><input type="button" class="btn1" value="搜索" onclick="searchContact()"/></span>
			    </div>
			    <div id="contactListDiv">
			    	<table cellpadding="0" cellspacing="0" width="100%" class="Contact_table" style="margin-left:0"> 
			      		<tr>
			      		<c:forEach var="agentContact" items="${listContact}" varStatus="status">
			      			<td><a href="javascript:selectBuyerEmail('<c:out value="${agentContact.email}" />')"><c:out value="${agentContact.name}" /></a>(<c:out value="${agentContact.email}" />)</td>
			      			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      			
			      			<c:if test="${status.count mod 2==0}">
			      			<c:out value="<tr><td></td></tr>" escapeXml="false"/>
			      			</c:if>
			      		</c:forEach>
			      		</tr>
			      		<tr>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			      		<td align="right">共有<c:out value="${counts}"/>个联系人</td>
			      		</tr>
			      	</table>
			    </div>
			  </div>
			  </td>
		    </tr>
		     <tr>
              <td class="right_td"><span class="font_style6">* </span>预支金额：</td>
              <td><input type="text" class="text_style" style="width:70px;" name="amount" id="amount" maxlength="12"  onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')" /> 元  <div id="amountDiv" style="color: red"></div></td>
            </tr> 
		    <tr>
              <td class="right_td"><span class="font_style6">* </span>申请原因：</td>
              <td><textarea class="text_style" style="width:405px; height:65px;" id="remark" name="remark"></textarea></td>
            </tr> 
           
            <tr>
              <td class="right_td" valign="top">备注：</td>
              <td><textarea class="text_style" style="width:405px; height:65px;" id="note" name="note"></textarea></td>
            </tr>
            <tr>
              <td class="right_td" valign="top"></td>
              <td><c:out value="${emgError}"/></td>
            </tr>
                      
            <tr>
              <td colspan="2" style="padding-left:60px;"><input type="button" value="确 定" class="btn1" onclick="checkForm()"/></td>
            </tr>
          </table>
          <script language="javascript">
				document.getElementById("contract").style.display = "none";
			</script>
		     <input type="hidden" name="ptype" value="<c:out value="${paytype}"/>"/>
			 <input type="hidden" name="tabMenuList" value="<c:out value="${tabMenuList}"/>"/>
			 <input type="hidden" name="subMenuList" value="<c:out value="${subMenuList}"/>"/>
		  </html:form>
		   </div>
		  </div>
		</div>
	</div>
	</div>
	<c:import url="/_jsp/footer.jsp" />
	</div>
  </body>
</html>

