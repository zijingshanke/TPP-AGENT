<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- jsp:fillinCertifyInfo.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  <script type="text/javascript" src="../_js/jquery-1.3.1.min.js"></script>
  <!-- <script type="text/javascript" src="../_js/json.js"></script> --></head>
  <script type="text/javascript">
 	 var selectPId="<c:out value="${selectPid}"></c:out>";
 	 var selectCId="<c:out value="${selectCid}"></c:out>";
 	 var selectBId="<c:out value="${selectBid}"></c:out>";
  	$(document).ready(function(){
  		getProvinces();
  		if(selectBId==""||selectBId==null){
  		document.getElementById("bankId").value=1;
  		}else{
  		document.getElementById("bankId").value=selectBId;
  		}
  		
	});
  	
  	function selectProvince(val){
		$("#citys").empty();
		getCitys(val);		
  	}
  	function getCitys(val){
  	var selectValue=0;
  		$.ajax({
   		type: "POST",
   		url: "../agent/agent.do?thisAction=getJosnCity",
  		 data: "pId="+val,
  		 dataType:"josn",
  		 success: function(data){
  		 	var json=eval('('+data+')'); 
		  		$(json.citys).each(function(i){ 
				if(selectCId==json.citys[i].id){
		  			$("#citys").append("<option value="+json.citys[i].id+" selected='selected'>"+json.citys[i].cname+"</option>");
		  			selectValue=selectCId;
		  			selectCId=0;
		  			}else{
					$("#citys").append("<option value="+json.citys[i].id+">"+json.citys[i].cname+"</option>");
					}
				});
				
  		} 
		}); 
  	}
  	
  	 
  		
  	function getProvinces(){
  	var selectValue=0;
  		$.ajax({
   		type: "POST",
   		url: "../agent/agent.do?thisAction=getJosnProvince",
  		 dataType:"josn",
  		 success: function(data){
  		 	var json=eval('('+data+')'); 
		  		$(json.provinces).each(function(i){ 
		  			if(selectPId==json.provinces[i].id){
		  			$("#provinces").append("<option value="+json.provinces[i].id+" selected='selected'>"+json.provinces[i].cname+"</option>");
		  				selectValue=selectPId;
		  				selectPId=0;
		  			}else{
					$("#provinces").append("<option value="+json.provinces[i].id+">"+json.provinces[i].cname+"</option>");
					}
				});
				if(selectValue>0){
					selectProvince(selectValue);
				}else{
				selectProvince(json.provinces[0].id);}
  		} 
		}); 
  		}
  	 function check(){
  		  var ck=/\D/g;
  	 	var address=document.getElementById("address").value;
  	 	var telephone=document.getElementById("telephone").value;
  	 	var mobile=document.getElementById("mobile");
  	 	var cardNo=document.getElementById("cardNo").value;
  	 	var tempCardNo=document.getElementById("tempCardNo").value;
  	 	 var accountAddress =document.getElementById("accountAddress").value;
  	 	  var idxBank = document.getElementById("bankId").selectedIndex;
   	    var bankName = document.getElementById("bankId").options[idxBank].text;
  	 	if(address!="" && telephone!="" && mobile!="" && cardNo!="" && tempCardNo!="" && accountAddress!=""){
  	 	
  	 		if(/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(telephone)==false){
  	 			alert("电话号码输入格式不正确！带区号的电话号码在区号后加‘—’符号！");
  	 			document.getElementById("telephone").select();
  	 			return false;
  	 		}
  	 		if(accountAddress.indexOf(bankName) != 0){
	  	 	   alert("开户行名称必须以您选择的银行名称开头！");
	  	 	   document.getElementById("accountAddress").select();
	  	 	   return false;
  	 		}
  	 		if(accountAddress.length <= bankName.length){
  	 			alert("开户行名称填写错误！（例：中国工商银行珠海拱北支行）");
	  	 	   document.getElementById("accountAddress").select();
	  	 	   return false;
  	 		}
  	 		if((/^13\d{8}$/g.test(mobile.value))||(/^15\d{8}$/g.test(mobile.value)) ||(/^18\d{8}$/g.test(mobile.value))){
  	 			
  	 			alert("手机号码输入格式不正确！");
  	 			document.getElementById("mobile").select();
  	 			return false;
  	 		}
  	 		if(/[^\u4E00-\u9FA5]/g.test(cardNo)==false){
  	 			alert("银行账户格式不对！");
  	 			document.getElementById("cardNo").select();
  	 			return false;
  	 		}
  	 		if(tempCardNo != cardNo){
  	 			alert("两次账户不相同！");
  	 			document.getElementById("cardNo").select();
  	 			return false;
  	 		}
  	 	}else{
			alert("您的资料没有填写完全！");
			return false;  	 	
  	 	}
  		document.forms[0].submit();
  	}
  	
  	function updatePerson(){
  		document.forms[0].action="../agent/agent.do?thisAction=jumpTremUpdate";
  		document.forms[0].submit();
  	}
  	
  </script>
  <body style="text-align: center;">
  <html:form action="/agent/agent.do?thisAction=fillinPersonInfonext">
  <html:hidden property="id" value="${agent.id}"></html:hidden>
  	  <html:hidden property="name" value="${agent.name}"></html:hidden>
  		<html:hidden property="companyName" value="${agent.companyName}"></html:hidden>
  	<html:hidden property="registerType" value="${agent.registerType}"></html:hidden>
  	  <html:hidden property="certNum" value="${agent.certNum}"></html:hidden>
  	  <html:hidden property="loginName" value="${URI.agent.loginName}"></html:hidden>  
  	   <html:hidden property="realName" value="${agent.realName}"></html:hidden> 
  <div id="warp" align="left">
  <c:import url="/_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>您的个人信息</strong>　　-日后您可以通过正确提供下列认证信息，来证明您的账户身份，请确保信息的正确并牢记这些信息！</p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="80%" class="information_table">
            <tr>
              <td class="right_td">钱门账号：</td>
              <td style="color:#005C9C"><c:out value="${agent.loginName}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">真实姓名：</td>
              <td> <c:out value="${agent.name}"></c:out></td>
            </tr>
            <c:if test="${agent.registerType==1}">
            	<tr>
              <td class="right_td">企业名称：</td>
              <td> <c:out value="${agent.companyName}"></c:out></td>
            </tr>
            </c:if>
             
            <tr>
              <td class="right_td">证件号码：</td>
              <td><c:out value="${agent.certNum}"></c:out><a href="javascript:updatePerson();">修改个人信息</a></td>
            </tr>
            <tr>
              <td class="right_td"></td>
              <td class="td_span">以下信息如果不属实，请重新填写！以免造成不必要的损失！<font color="red">请谨慎填写！</font> </td>
            </tr>
            <tr>
              <td class="right_td"><font color="red">*</font> 详细地址：</td>
              <td><html:text property="address" name="agent" styleId="address" value="${agent.address}"/></td>
            </tr>
            <tr>
              <td class="right_td"><font color="red">*</font> 固定电话：</td>
              <td><html:text property="telephone" name="agent" styleId="telephone" value="${agent.telephone}"/></td>
            </tr>
            <tr>
              <td class="right_td"><font color="red">*</font> 手机号码：</td>
              <td><html:text property="mobile" name="agent" styleId="mobile" value="${agent.mobile}"/></td>
            </tr>
            <!-- 
            <tr>
              <td class="right_td"></td>
              <td class="td_span">请至少填写固定电话和手机号码中的其中一项。</td>
            </tr>
             -->
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>您的银行账户信息</strong>   -该银行账户仅用于认证您的身份，您仍可以使用其他的银行账户进行充值和提现！</p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="98%" class="information_table">
            <tr>
              <td class="right_td">银行开户名称：</td>
               <c:if test="${agent.registerType==0}">
              <td><c:out value="${agent.name}"></c:out></td>
              </c:if>
              <c:if test="${agent.registerType==1}">
              <td><c:out value="${agent.companyName}"></c:out></td>
              </c:if>
            </tr>
            <!-- 
            <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span">如您没有合适的银行账户，<a href="javascript:jumpTremUpdate();">修改身份信息</a></td>
            </tr>
             -->
            <tr>
              <td class="right_td">&nbsp;</td>
              <td><p class="td_p">提醒：&nbsp;&nbsp;&nbsp;必须使用  <FONT color="#101903">
              <c:if test="${agent.registerType==0}">
              <c:out value="${agent.name}"></c:out>
              </c:if>
               <c:if test="${agent.registerType==1}">
              <c:out value="${agent.companyName}"></c:out>
              </c:if>
              </FONT>  的银行账户进行认证。以免影响您的提现业务！</p></td>
            </tr>
  <tr>
              <td class="right_td">开户银行名称：</td>
              <td>              
			<!-- 如需更改，见com.fordays.fdpay.base.Bank.java,按所需顺序填写	-->	
           <select name="bankId" id="bankId">
			<option value="1">中国建设银行</option>										
			<option value="2">中国农业银行</option>										
			<option value="3">中国工商银行</option>																			
			<option value="5">中国民生银行</option>
			<option value="9">交通银行</option>										
			<option value="11">中信银行</option>																											
			</select>	
             </td>
            </tr>
            <tr>
              <td class="right_td">开户银行所在省份：</td>
              <td><select id="provinces"  name="provincesId" onchange="selectProvince(this.value)"></select></td>
            </tr>
            <tr>
              <td class="right_td">开户银行所在城市：</td>
              <td>
              	<select id="citys" name="citysId">
              	</select>
              </td>
            </tr>
             <tr>
              <td class="right_td"></td>
              <td class="td_span">请您认真填写开户行网点名称，填写错误会影响您的提现。例如：中国工商银行珠海拱北支行。</td>
            </tr>
            <!-- 
            <tr>
              <td class="right_td"></td>
              <td class="td_span">请至少填写固定电话和手机号码中的其中一项。</td>
            </tr>
             -->
             <tr>
              <td class="right_td"><font color="red">*</font> 开户行网点名称:</td>
              <td><html:text property="account.accountAddress" styleId="accountAddress"/></td>
            </tr>
            <tr>
              <td class="right_td"><font color="red">*</font>
              <c:if test="${agent.registerType==1}">
              企业银行账户：
              </c:if>
              <c:if test="${agent.registerType==0}">
               个人银行账户：
               </c:if>
               </td>
              <td><html:text property="account.cardNo" styleId="cardNo" /></td>
            </tr>
            <tr>
              <td class="right_td"></td>
              <td class="td_span">您提交后钱门会给该账户注入一笔“确认资金”，您需要正确的输入这笔资金的数量才能通过认证。</td>
            </tr>
            <tr>
              <td class="right_td"><font color="red">*</font> 请再输入一遍：</td>
              <td>
             <input onpaste="return false;" type="text" name="tempBankNum" id="tempCardNo"  value=""/>
              <font color="red"><c:out value="${msg}"></c:out></font></td>
            </tr>
            <tr>
              <td style="text-align:center;"><input type="button" value="下一步" class="btn1" onclick="check();" /></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
            	<td colspan="2">
            	<div class="yellowBox">
            	如果您的银行卡不能绑定，您可以向钱门申诉：<br /> 
            	<a href="../_file/Real-nameAuthenticationComplaint.doc" onclick="download();">
            	<img src="../_img/calendar1.gif"/>&nbsp;请先下载申诉表</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<img src="../_img/rightArrow.gif" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正确填写后Email到 qmpay@qmpay.com ！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<img src="../_img/rightArrow.gif" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审核认证后您会得到钱门的通知邮件！
            	</div>
            	</td>
            </tr>
          </table>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
  </html:form>

</body>
</html>
