<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/batchRedPacket.jsp -->

<head>
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<script src="../_js/prototype.js"></script>
<script src="../_js/jquery-1.3.1.min.js"></script>
<script type="text/javascript">
	function select(val){
			addEmail(val);
	}
	function addEmail(val){
		 
		var email=$("#Email"+val).val();
		var objVal=$("#objtextarea").text();
		if(objVal.length>0){
			if(objVal.indexOf(email)==-1)
			objVal=objVal+";"+email;
		}else
			objVal=email;
		$("#objtextarea").text(objVal);
	}
	function addEmail2(val){
		 
		var email=val;
		var objVal=$("#objtextarea").text();
		if(objVal.length>0){
			if(objVal.indexOf(email)==-1)
			objVal=objVal+";"+email;
		}else
			objVal=email;
		$("#objtextarea").text(objVal);
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
    
		if(f_check_email(customPerson.value))
	   			  addEmail2(parEmail);
   		else{
	   			  $("#checkEmail").empty();
	   			  $("#checkEmail").append("<font color=red>您添加的邮箱格式有误,请认真填写!</font>");
   			  }
	}
	
	
	function checkForm(){
		var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
    	var allowBalance = document.getElementById("allowBalance").value;
		var checkEmail = document.getElementById("checkEmail");
		var mobileValidateCode = document.getElementById("mobileValidateCode").value;
		var autoPassword = document.getElementById("autoPassword");
		var errorMessage = document.getElementById("errorMessage");
		var validateCodeMsg = document.getElementById("validateCodeMsg");
		var errorPayPasswordMessage = document.getElementById("errorPayPasswordMessage");
		var payPassword = document.getElementById("payPassword").value;
		var niceword=document.getElementById("niceword").value;
		if(niceword==null||niceword==""){
		alert("请填写祝福语!");
		document.getElementById("niceword").focus();
		return false;
		}
		
		if(document.getElementById("objtextarea").value==""
		||document.getElementById("objtextarea").value==null){
			alert("请填写接收人员名单");
			return false;
		}else{
			if(!checkTextarea()){
				return false;
			}
		}
		if(!assign()){
			return false;		
		}
		var assignPrice = document.getElementById("assignPrice").value;
		var plays=document.getElementById("objtextarea").value.split(/[;,]/);
		var tempplayers="";
		for(var i=0;i<plays.length;i++){
			if(i==0){
				tempplayers=plays[0];
			}else{
				tempplayers=tempplayers+","+plays[i];
			}
			
		}
		
		document.getElementById("players").value=tempplayers;
		var tempCount=plays.length*assignPrice;
		errorPayPasswordMessage.innerHTML = "";
		errorMessage.innerHTML = "";
		checkEmail.innerHTML = "";
		validateCodeMsg.innerHTML = "";
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
		   var trlength=$("#tabEmail tr").length;
		   for(var i=0;i<trlength;i++){
		   		addEmail(i+1);
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
		return true;
	}
	
	function checkTextarea(){
	var objtextarea=document.getElementById("objtextarea");
	var val=objtextarea.value;
		if(val!=""&&val!=null){
		var players =val.split(/[;,]/);
		
		
			for(i=0;i<players.length;i++){
			
				if(!f_check_email(players[i])){
				var rng = objtextarea.createTextRange(); 
				rng.findText(players[i]); 
				rng.select() 
				alert("请填写正确的邮箱");
				return false;
				}
			}
			return true;
		}
		return false;
	}
	function f_check_email(val){  
	var email='<c:out value="${URI.agent.email}"></c:out>'
	if(val==""){
		return true;
	}
	if(email==val){
		alert("您不能给自己发红包!");
		return false;
	}
		var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 
		if(myReg.test(val)) return true; 
		else 	return false;
		 } 
		 
		 
	function selectCB(val){
		var cb1=document.getElementById("cb1");
		var cb2=document.getElementById("cb2");
		if(cb1.name==val){
			if(cb1.checked){
				cb2.checked=false;
				document.getElementById("shopUrl").value=cb1.value;
			}else{
				cb2.checked=true;
				document.getElementById("shopUrl").value=cb2.value;
			}
		}else{
			if(cb2.checked){
				cb1.checked=false;
				document.getElementById("shopUrl").value=cb2.value;
			}else{
				cb1.checked=true;
				document.getElementById("shopUrl").value=cb1.value;
			}
		}
	}
	
	
	
	
	function getAccountNames(){
	var emails=document.getElementById("objtextarea").value;
		$.ajax({
   		type: "POST",
   		url: "../transaction/transactionlist.do?thisAction=checkAgentEmails",
  		data: "emails="+emails,
  		success: function(msg){
  		
  		document.getElementById("objtextarea2").value=msg;
  		 }
		}); 
	}
	
	function selectShowDiv(num){
	if(num==1){
		document.getElementById("show_2").style.display="none";
		document.getElementById("show_1").style.display="block";
		document.getElementById("head2").className="";
		document.getElementById("head1").className="at_deal_list_head";
		
	}else{
		if(checkTextarea()){
		document.getElementById("show_1").style.display="none";
		document.getElementById("show_2").style.display="block";
		document.getElementById("head1").className="";
		document.getElementById("head2").className="at_deal_list_head";
		writeShowDiv();
		}

	}
	}
	function writeShowDiv(){
		getAccountNames();
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
              <p><strong>发红包</strong>&nbsp;<html:link page="/transaction/transactionlist.do?thisAction=getBatchPaymentDetail&paytype=${paytype}">查看历史记录</html:link>
            </div>
          </div>
	
            <p><img src="../_img/zhuyi_ico.jpg" style="vertical-align:middle; margin:0 10px;" />您可以向您的好友发送红包</p>
           <html:form action="/transaction/transaction.do?thisAction=addBatchRedPacket" method="post">
           <input type="hidden" name="shopUrl" value="_img/redpacket/gg4.gif"></input>
           <input type="hidden" name="players" />
            <table cellpadding="0" cellspacing="0" width="100%" class="aa_receivables">
              
              <tr class="no_tr" style="display: block;">
                <td class="right_td" valign="top">祝福语：</td>
                <td><textarea   style="width:415px; height:50px;" class="text_style" name="orderDetails.detailsContent" id="niceword" maxlength="100"></textarea><br />
				<span class="font_style8">送红包的同时也带上您最真诚的祝福</span></td>
              </tr>
              <tr class="no_tr" style="display: block;">
                <td class="right_td" valign="top">红包</td>
                <td>
                <input type="checkbox" name="cb1" onclick="selectCB(this.name)" checked="checked" value="_img/redpacket/gg4.gif" /><img src="<%=request.getContextPath()%>/_img/redpacket/gg2.gif" />
                <input type="checkbox" name="cb2" onclick="selectCB(this.name)" value="_img/redpacket/gg.gif"/><img src="<%=request.getContextPath()%>/_img/redpacket/gg3.gif" />
                
                </td>
              </tr>
              <tr>
                <td class="right_td"><span class="font_style6">*</span> 添加收红包人员：</td>
                <td>请从右侧列表选择成员添加，您也可以手动输入要添加的账号(邮箱)。</td>
              </tr>
              
              <tr>
                <td class="right_td" valign="top" style="text-align: left"><font color="red">注意:填写的接收人格式必须为(邮箱;邮箱)如 zwwlmzy@vip.qq.com;285576003@qq.com<br>方式一:您可以选择您的联系人的帐号<br>方式二:您可以手动在添加栏目添加邮箱<br>方式三:您可以直接在红包接受人员输入框中直接填写<br>小提示:如果您输入的邮箱还不是钱门帐号,钱门为您自动创建帐号,密码默认为:123456</font></td>
                <td>
                  <div class="table_box">
                    <div class="table_left_box">
                   <input type="hidden" id="count" name="count" value="0" />
                   <div style="overflow-y:auto;" id="autoDiv">
					<table id="Tab1">
					<tr>
						<td colspan="3">			
							<div class="deal_list_head2">
								<a id="head1" style="cursor:hand" onclick="selectShowDiv(1)">名单</a>
								<a id="head2" style="cursor:hand" onclick="selectShowDiv(2)">详细</a>
							</div>
							<div id="show_1" >
							<textarea rows="14" cols="5" style="width: 320px;" id="objtextarea" name="objtextarea"></textarea>
							</div>
							<div id="show_2" style="display: none;"><textarea readonly="readonly" rows="14" cols="5" style="width: 320px;" id="objtextarea2" name="objtextarea2" ></textarea></div>
						</td>
					</tr>
					<script>	document.getElementById("head1").className="at_deal_list_head";</script>
					</table>
					</div>
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
                        <table id="tabEmail">
                        	<c:forEach var="AgentContact" items="${alf.list}" varStatus="statu">
							<tr id="tt<c:out value='${statu.count}'/>" onmouseover="javascript:this.className='p_style2'" onmouseout="this.className=''">
							<td onclick="select(<c:out value="${statu.count}"></c:out>);"><label for="cb<c:out value='${statu.count}'></c:out>"><b><c:out value="${AgentContact.name}"/></b> <span class="font_style8">(<c:out value="${AgentContact.email}"/>)</span></label>
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
                <td class="right_td">设置红包大小：</td>
                <td><input type="text" id="assignPrice" name="assignPrice" value="" style="width:60px;" class="text_style" onfocus="this.className='text_style1'" onblur="this.className='text_style'"/> <input type="button" name="bb1" value="确定" class="btn1" onclick="assign(0);"/> 
                	
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
    
   <c:import url="/_jsp/footer.jsp"/>
  </div>
  
</body>
</html>
