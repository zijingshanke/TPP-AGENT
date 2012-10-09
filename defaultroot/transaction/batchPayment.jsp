<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/batchPayment.jsp -->

<head>
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<script src="../_js/prototype.js"></script>
<script src="../_js/jquery-1.3.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('.show_tr').click(function(){
			$('.no_tr,.no_tra').show();
			$('.show_tr').hide();
		})	  
		$('.no_tra').click(function(){
			$('.no_tr,.no_tra').hide();
			$('.show_tr').show();
		})	   
	})

	var MaxTr=Number("<c:out value="${MaxTr}" />");
	var MaxForCount=MaxTr;
	function select(val){
		var count= $("#count").val();
		if($("#cb"+val).attr("checked")){
			$("#tt"+val).removeClass("p_style2");
			$("#tt"+val).addClass("p_style3");
			addTr(val);
		}
		else{
			$("#tt"+val).removeClass("p_style3");
		    $("#tt"+val).addClass("p_style2");
			removeTr(val);	
		}
	}
	
	function addTr(val){
		 var email=$("#Email"+val).val();
		 if(isinTable(email)==0){
		 	$("#Tab1").append("<tr id='tr"+val+"'><td><b>"+$("#Name"+val).val()+"</b> (<span class='font_style8'>"+$("#Email"+val).val()+"</span>)</td><td><span class='float_right'><input type='text' class='text_style' style='width:60px;' onfocus='this.style.border=\"1px solid #FFD327\";' onblur='this.style.border=\"1px solid #999\";showCount("+val+")' id='text"+val+"' value='0.01'/>元 <img src='../_img/delete_ico.jpg' onclick='removeTr("+val+")'/></span></td></tr>");	
			$("#showMsg").empty();
			 showCount(val);
		 }
		 else{
		 		$("#checkEmail").empty();
   				$("#checkEmail").append("<font color=red>您已经添加了这个的帐户!</font>");
		}
	}
	
	function removeTr(val){
		if($("#cb"+val).attr("checked")){
			$("#cb"+val).attr("checked",false);	
		}
		$("#tr"+val).remove();
		$("#showMsg").empty();
		showCount();
	}
	
	function delTr(val){
		MaxTr=MaxTr-1;
		$("#tr"+val).remove();
		$("#Email"+val).remove();
		$("#showMsg").empty();
		showCount();
	}
	
	var emailsCount=0;
	var tempCount = 0;
	function reCount(){
		var count=0;
		
		if($("#Tab1 tr").length>0){
			for(var i=1;i<=MaxForCount;i++){		
				if($("#text"+i).val()!=undefined){				
					count+=Number($("#text"+i).val());
					$("#showMsg").append("<input type='hidden' id='emails' name='emails' value="+$("#Email"+i).val()+" />");
					$("#showMsg").append("<input type='hidden' id='prices' name='prices' value="+$("#text"+i).val()+" />");
				}
			}	
	    }
	    tempCount = count.toFixed(2);
		$("#showMsg").append("<p>共 <b>"+$("#Tab1 tr").length+"</b> 人,总额: "+count.toFixed(2)+" 元</p>");
	}
	
	var g_price = 0.00;
	function showCount(val){
		$("#showMsg").empty();
		$("#checkEmail").empty();
   		$("#checkEmail").append("<font>钱门账户名是Email地址!</font>");
   		var str=$("#text"+val).val();
   		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
   		
   		if(str!=null&&typeof(str)!="undefined"){
	   		if(isNaN(str)){
	        	alert("请输入正确的金额");
	        	$("#text"+val).val(0.01);
		    }
		    else {
		    	if(str<0){
		    		alert("请输入正确的金额");
	        		$("#text"+val).val("0.01");
		    	}else{
		    		if(!re.test(str)){
						alert('单价只能由数字组成且带有2位小数，格式为0.00');
						$("#text"+val).val("0.01");
					}
					if(str>1000000.00){
						alert('每人付款金额不能大于1000000');
						$("#text"+val).val("0.01");
					}
		    	}
		    }
		   
	    }
	    g_price = str;
		return reCount();
	}
	
	function addPayers(){
		var parEmail= $("#customPerson").val();
		var currentAccount = "<c:out value='${loginName}'/>";
    	var customPerson = document.getElementById("customPerson");
    	if(currentAccount.toLowerCase()==customPerson.value.toLowerCase()){
    		alert("不能添加自己的账户!");
	    	customPerson.value = "";
	    	customPerson.focus();
	    	return false;
    	}
		$.ajax({
   		type: "POST",
   		url: "../transaction/transactionlist.do?thisAction=ajaxCheckEmail",
  		data: "parEmail="+parEmail,
  		success: function(msg){
   			  if(msg!=""){
	   			  $("#checkEmail").empty();
	   			  if(isinTable(parEmail)==0){
		   			  	MaxTr=MaxTr+1;
						MaxForCount=MaxTr;
						$("#Tab1").append("<tr id='tr"+(MaxTr)+"'><td><b>"+msg+"</b> (<span class='font_style8'>"+parEmail+"</span>)</td><td><span class='float_right'><input type='text' class='text_style' style='width:60px;' onfocus='this.style.border=\"1px solid #FFD327\";' onblur='this.style.border=\"1px solid #999\";showCount("+MaxTr+")' id='text"+(MaxTr)+"' value='0.01'/>元 <img src='../_img/delete_ico.jpg' onclick='delTr("+MaxTr+")' /><span></td></tr>");						
						$("#Tab1").append("<input type='hidden' id='Email"+MaxTr+"' value='"+parEmail+"' />");
						$("#Tab1").append("<input type='hidden' id='Name"+MaxTr+"'  value='"+msg+"' />");	
						customPerson.value = "";			
						showCount();
	   			  	}else{
	   			  		$("#checkEmail").empty();
	   					$("#checkEmail").append("<font color=red>您已经添加了这个的帐户!</font>");
	   				}
   			  }
   			  else{
	   			  $("#checkEmail").empty();
	   			  $("#checkEmail").append("<font color=red>您添加的帐户不存在!</font>");
   			  }
  		 }
		}); 
	}
	
	function isinTable(email){
		var j=0;
		if($("#Tab1 tr").length>0){
			for(var i=0;i<MaxTr;i++){		
				if(i<document.getElementsByName("emails").length){
					var TrEmail = document.getElementsByName("emails")[i].value;
					if(TrEmail.toLowerCase()==email.toLowerCase()){
						j++;						
					}
				}
			}
		}
		return j;
	}
	
	function checkForm(){
		var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
    	var payReason = document.getElementById("payReason");
    	var allowBalance = document.getElementById("allowBalance").value;
		var checkEmail = document.getElementById("checkEmail");
		var mobileValidateCode = document.getElementById("mobileValidateCode").value;
		var autoPassword = document.getElementById("autoPassword");
		var errorMessage = document.getElementById("errorMessage");
		var validateCodeMsg = document.getElementById("validateCodeMsg");
		var errorPayPasswordMessage = document.getElementById("errorPayPasswordMessage");
		var payPassword = document.getElementById("payPassword").value;
		errorPayPasswordMessage.innerHTML = "";
		errorMessage.innerHTML = "";
		checkEmail.innerHTML = "";
		validateCodeMsg.innerHTML = "";

    	if(payReason.value==""){
    		alert("请输入付款理由!");
	    	payReason.focus();
	    	return false;
    	}
    	else{
	   		if((/\s+/.test(payReason.value))){
	   			alert("请输入收款理由!");
	   			payReason.value = "";
		    	payReason.focus();
		    	return false;
	   		}
	    }
    	
    	if($("#Tab1 tr").length<=0){
   			alert("请先添加收款人账号!");
   			return false;
    	}
    	if($("#Tab1 tr").length>30){
    		alert("只可以同时对30人发起付款!");
   			return false;
    	}
    	
    	if(g_price<0.01 || g_price>1000000){
    		alert("金额不能小于0.01或者大于1000000!");
    		return false;
    	}
    	
    	var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkPayPassword&payPassword="+payPassword,{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;

			if(result==0){
				errorPayPasswordMessage.innerHTML="<font color=red>支付密码错误!</font>";
			}
			else if(result==2){
				errorPayPasswordMessage.innerHTML="<font color=red>帐号余额不足,请先充值!</font>";
				return false;
			}
			else{
	    	if(autoPassword.style.display == "block" && mobileValidateCode==""){
	    		alert("请输入校验码");
	    		return false;
	    	}
	    	var checkAjax = new Ajax.Request("../transaction/transaction.do?thisAction=checkCanPayByBatchPayment&amount="+tempCount+"&mobileValidateCode="+mobileValidateCode,{method:"post", onComplete:function (originalRequest) {
			var result2 = originalRequest.responseText;
			if(result2==6){
				errorMessage.innerHTML="<font color=red>帐户余额不足,请先充值!</font>";
				return false;
			}
			else if(result2==0){
				errorMessage.innerHTML="<font color=red>余额支付功能已关闭!</font>";
				return false;
			}
			else if(result2==1){
				errorMessage.innerHTML="<font color=red>你支付的额度超过设置的单笔支付额度,请输入动态口令密码!</font>";	
				autoPassword.style.display = "block";
				return false;
			}
			else if(result2==2){
				errorMessage.innerHTML="<font color=red>你支付的额度超过设置的当天支付累计资金数,请输入动态口令密码!</font>";			
				autoPassword.style.display = "block";
				return false;
			}
			else if(result2==4){
				validateCodeMsg.innerHTML="<font color=red>校验码有误!</font>";
				autoPassword.style.display = "block";
				return false;
			}
			else if(result2==5){
				validateCodeMsg.innerHTML="<font color=red>点击获取动态口令并输入校验码!</font>";
				autoPassword.style.display = "block";
				return false;
			}
			else{
				document.forms[0].btntoken.disabled="disabled";
	    		document.forms[0].submit();
	    	}
	    	}, onException:showException});
	    }
		}, onException:showException});
	}
	
	function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
	}
	
	function getMobileValidateCode(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
			var validateCodeMsg = document.getElementById("validateCodeMsg");
			var myAjax = new Ajax.Request("../transaction/transaction.do?thisAction=getMobileValidateCode",{method:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			document.getElementById("bb").disabled ="disabled";
			document.getElementById("autoMsg").style.display = "block";
			validateCodeMsg.innerHTML = "";
			alert("验证码已经发送到你手机上,请查收!");
			}, onException:showException});
	}
	
	function Select_All(){
		var btn = document.getElementById("selectAll").name;
			
		   var i=0;
		   var m=0;
		   for(;i<document.forms[0].length;i++){
		   if(document.forms[0].elements[i].type!="checkbox"){
		   		continue;
		   	}
		   else
		   		m++;
		   if(btn=="selectAll"){
	       			document.forms[0].elements[i].checked = true;
	       			document.getElementById("selectAll").name="cancelAll";
	       			document.getElementById("selectAll").value="取消全选";
	       		}
	       	else if(btn=="cancelAll"){
	       			document.forms[0].elements[i].checked = false;
	       			document.getElementById("selectAll").name="selectAll";
	       			document.getElementById("selectAll").value="全选";
	       		}
		   }

		   if(btn=="selectAll"){
		   		for(var n=1;n<=m;n++){
		   			select(n);
		   		}
		   }
		   else{
		   		for(var n=1;n<=m;n++){
		   			select(n);
		   		}
		   }
		   
	}
	
	function assign(){
		var assignPrice = document.getElementById("assignPrice");
		if(assignPrice.value==""){
			alert("请输入金额!");
			assignPrice.focus();
			return false;
		}
		else{
		var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
   		
   		if(assignPrice.value!=null&&typeof(assignPrice.value)!="undefined"){
	   		if(isNaN(assignPrice.value)){
	        	alert("请输入正确的金额");
	        	return false;
		    }
		    else {
		    	if(assignPrice.value<0){
		    		alert("请输入正确的金额");
	        		return false;
		    	}else{
		    		if(!re.test(assignPrice.value)){
						alert('单价只能由数字组成且带有2位小数，格式为0.00');
						return false;
					}
					if(assignPrice.value>1000000.00){
						alert('每人付款金额不能大于1000000');
						return false;
					}
		    	}
		    }
		   
	    }
	}
		
		if($("#Tab1 tr").length<=0){
   			alert("请先添加收款人账号!");
   			return false;
    	}

    	for(var i=1;i<=MaxTr;i++){
    		if(document.getElementById("text"+i)!=null)
				document.getElementById("text"+i).value = assignPrice.value;
		}
		showCount(MaxTr);
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
              <p><strong>批量付款</strong>&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=getBatchPaymentDetail&paytype=${paytype}">查看历史记录</html:link>
            </div>
          </div>
	
            <p><img src="../_img/zhuyi_ico.jpg" style="vertical-align:middle; margin:0 10px;" />您可以向您的好友发起批量付款</p>
            <p><img src="../_img/dian.gif" style="vertical-align:middle; margin:0 10px 0 40px;" />可以同时对30人发起付款</p>
           <html:form action="/transaction/transaction.do?thisAction=addBatchPayment" method="post">
            <table cellpadding="0" cellspacing="0" width="100%" class="aa_receivables">
              <tr>
                <td class="right_td" valign="top"><span class="font_style6">*</span> 付款理由：</td>
                <td><input type="text" name="orderDetails.shopName" value="" id="payReason" class="text_style" onfocus="this.className='text_style1'" onblur="this.className='text_style'"/><a href="#" class="a_style1 show_tr">添加详细说明</a><a href="#" class="a_style1 no_tra">关闭详细说明</a><br />
				</td>
              </tr>
              <tr>
                <td class="right_td">&nbsp;</td>
                <td><span class="font_style8">最多6个字，如“旅游”、“同学会”等。</span></td>
              </tr>
              <tr class="no_tr">
                <td class="right_td" valign="top">详细说明：</td>
                <td><textarea  style="width:415px; height:50px;" class="text_style" name="orderDetails.detailsContent" maxlength="100"></textarea><br />
				<span class="font_style8">请填入真实内容，以便对方确认，例如“收上周五0756-唱K费用”。</span></td>
              </tr>
              <tr>
                <td class="right_td"><span class="font_style6">*</span> 添加批量付款人员：</td>
                <td>请从右侧列表选择成员添加，您也可以手动输入要添加的账号。</td>
              </tr>
              <tr>
                <td class="right_td">设置平均每人付款：</td>
                <td><input type="text" id="assignPrice" name="assignPrice" value="" style="width:60px;" class="text_style" onfocus="this.className='text_style1'" onblur="this.className='text_style'"/> <input type="button" name="bb1" value="确定" class="btn1" onclick="assign(0);"/> 
                	
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>
                  <div class="table_box">
                    <div class="table_left_box">
                   <input type="hidden" id="count" name="count" value="0" />
                   <div style="height:235px;overflow-y:auto;" id="autoDiv">
					<table id="Tab1">
					</table>
					</div>
                      <p class="table_left_box_p" id="showMsg" style="display: block">
                      
                      </p>
                      <p>
                        <input id="customPerson" type="text" name="customPerson" value="" class="text_style" onfocus="this.className='text_style1'" onblur="this.className='text_style'""/>
                      <input type="button" value="添 加" class="btn1" onclick="addPayers()"/></p>
                      <p class="font_style8" id="checkEmail">账户名为E-mail地址</p>
                    </div>
                    <div class="table_right_box">
                      <div class="table_right_box_count">
                        <p><b>我的联系人</b>  <input type="button" id="selectAll" class="btn1" name="selectAll" value="全选" onclick="Select_All();"/></p>
                        <hr>
                        <div style="height:235px;overflow-y:auto;">
                        <table>
                        	<c:forEach var="AgentContact" items="${alf.list}" varStatus="statu">
							<tr id="tt<c:out value='${statu.count}'/>" onmouseover="javascript:this.className='p_style2'" onmouseout="this.className=''">
							<td onclick="select(<c:out value="${statu.count}"></c:out>);"><input type="checkbox" id="cb<c:out value='${statu.count}'></c:out>"/><label for="cb<c:out value='${statu.count}'></c:out>"><b><c:out value="${AgentContact.name}"/></b> <span class="font_style8">(<c:out value="${AgentContact.email}"/>)</span></label>
							</td>
							</tr>
							
							<input type="hidden" id="Email<c:out value="${statu.count}"></c:out>" value="<c:out value="${AgentContact.email}"></c:out>" />
							<input type="hidden" id="Name<c:out value="${statu.count}"></c:out>" value="<c:out value="${AgentContact.name}"></c:out>" />
							</c:forEach>
                        </table>
                      </div>
                      </div>
                    </div>
                    <div class="clear"></div>
                  </div>
                </td>
              </tr>
              <tr>
              	<td class="right_td">支付密码:</td>
              	<td><input type="password" id="payPassword" name="payPassword" value="" class="text_style" style="width:190px;" />
              	  <span id="errorPayPasswordMessage"></span>
              	 </td>
              </tr>

              <tr>
                <td>&nbsp;</td>
                <td><input type="button" id="btntoken" value="发起付款"  class="btn1" onclick="checkForm()"/> &nbsp; <span id="errorMessage"></span></td>
              </tr>
              
              <tr style="display: none;" id="autoPassword">
	              <td>&nbsp;</td>
	              <td><input type="text" class="text_style" name="mobileValidateCode" id="mobileValidateCode"/> <span class="simplyBtn_1"><input class="buttom_middle" type="button" value="点击获取动态口令密码" id="bb" name="bb" onclick="getMobileValidateCode();"/></span> <span id="validateCodeMsg"></span></td>
	            </tr>  
	          <tr style="display: none;" id="autoMsg">
	              <td>&nbsp;</td>
	              <td>如果因网络延误导致超过10分钟未能收到验证码,请先点击主页的“退出”链接,然后重新获取</td>
	          </tr>
            </table>
            <input type="hidden" name="paytype" value="<c:out value="${paytype}"/>"/>
            <input type="hidden" name="allowBalance" id="allowBalance" value="<c:out value="${allowBalance}"/>"/>
            </html:form>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>使用遇到问题？</strong></p>
            </div>
          </div>
            <ul class="pay_ask">
              <li>
                <p><strong>批量付款是哪种交易呢?</strong></p>
                <p>答：批量付款是由付款方创建的即时到账交易。交易建立之后在对方账户将建立一笔“交易成功”的即时到账交易，对方登陆钱门账户，进入“交易管理”,收款方将会立即收到这笔交易资金。</p>
              </li>
              <li>
                <p><strong>批量付款创建之后，是否会发邮件通知对方</strong></p>
                <p>答：如果会员的账户是Email账户，会收到一封邮件提醒。</p>
              </li>
            </ul>
        </div>
      </div>
    </div>
   <c:import url="/_jsp/footer.jsp"/>
  </div>
  
</body>
</html>
