<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- registerActivate.jsp -->
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<title>钱门支付！--网上支付！安全放心！</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	

<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script src="../_js/prototype.js"></script>
<script src="../_js/MyValidation.js"></script>
<script src="../_js/validateform.js"></script>

  <script>
 
  	function checkPayPassword(){
  		if(lengthBetween('payPassword','paypasswordTip',6,20,'您设置的登录密码有误，登录密码应该由6-20个英文字母、数字或符号组成。')){
  			checkNotEquals('password','payPassword','paypasswordTip','OK!','登录密码与支付密码一致，请重新填写。');			
  		}
  	}
  	
  	function toSubmit()
{
	var formid = document.forms[0];
	var ck=document.getElementsByName("registerType");
	var roid;
	for(var i=0;i<ck.length;i++){
		if(ck[i].checked)
		{
			roid=ck[i].value;
		}
	}
	if(roid==0)
		{
			if(formid.name.value!="" && formid.certNum.value!="" && formid.password.value!="" && formid.repassword.value!="" && formid.payPassword.value!="" && formid.repayPassword.value!="" && formid.answer.value!="" && formid.mobile.value!="" && formid.telephone.value!="")
			    formid.submit();
			else{
				alert("注册内容不完整！请认真填写！");
				formid.name.select();
			}
		}
	if(roid==1){
		if(formid.companyName.value!="" 
		&& formid.name.value!="" && formid.certNum.value!="" 
		&& formid.password.value!="" && formid.repassword.value!="" 
		&& formid.payPassword.value!="" && formid.repayPassword.value!="" 
		&& formid.answer.value!="" && formid.mobile.value!="" && formid.telephone.value!="")
			    formid.submit();
			else{
				alert("注册内容不完整！请认真填写！");
				formid.name.select();
			}
	}
}

function showRegisterType(no){
  		var table2=document.getElementById("company");
  		if(no=="0"){
  			table2.style.display = "none";
  		}else if(no=="1"){
  			table2.style.display = "block";
  		}
  		
  	}
  	
  </script>
  <style>
 div.information {
	font-size: 12px;
	height: 22px;
	line-height: 22px;
	padding-left: 35px;
	border: 1px solid #CCE;
	background: url('../_img/info_16.png') no-repeat scroll 6px -297px #eef5ff;
}
 div.errors {
	font-size: 12px;
	height: 22px;
	line-height: 22px;
	padding-left: 35px;
	border: 1px solid #FBC2C4;
	background: url('../_img/info_16.png') no-repeat scroll 6px -1197px #FBE3E4;
}
 div.sucess {
	font-size: 12px;
	height: 22px;
	line-height: 22px;
	padding-left: 35px;
	border: 1px solid #C6D880;
	background: url('../_img/info_16.png') no-repeat scroll 6px -597px #E6EFC2;
}
  </style>
  
  </head>
  
  <body style="text-align: center">
 <html:form action="/agent/agent.do?thisAction=activate" method="POST">
          <html:hidden property="loginName" value="${agent.loginName}"></html:hidden>
          <html:hidden property="id" value="${agent.id}"></html:hidden>
  <div id="warp" align="left">
<c:import url="../selfHelp/head.jsp"></c:import>
      <div class="sendToEmail modifySuccess" style="height: auto; width: auto;">
        <div class="sendToEmailTitle modifySuccessTitle" style="height:60px; line-height:30px;">
            <em>成功</em>
            <p>您已经免费得一个钱门账户，请填写以下信息完成最后一步注册。</p>
        </div>
        <ul style="padding:0 0 0 16px;">
          <li style="margin:8px 0;">拥有一个钱门账户，您就可以轻松完成网络收款，付款。尽情体验网上支付带给您的无穷乐趣...</li>
          <li style="margin:8px 0;">钱门尊重并保护所有使用钱门服务的个人隐私。未事先得到您的同意，我们不会有意将您的个人资料透露给任何第三方。</li>
        </ul>
      </div>
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>填写您的个人信息</strong><font color="red">（请如实填写，本栏信息在本次提交后无法修改，填写错误会影响您的付款或收款！）</font></p>
            </div>
          </div>
         
           
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
	            <tr>
	              <td class="right_td">用户类型：</td>
	              <td>
	             <input type="radio" name="registerType"  value="0" class="radio" onclick="showRegisterType(0)"/>
	             <span style="vertical-align:middle; margin-right:12px;">个人</span>
	             <input type="radio" name="registerType"  value="1" class="radio"  onclick="showRegisterType(1)"/>
	             <span style="vertical-align:middle;">公司</span></td>
	            </tr>
           </table>
           <div id="company" style="display: none;">
           <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
           			<tr>
          			<td></td>
          			<td colspan="2">
          			<div class="yellowBox">
          				钱门账户企业版办理流程：<br /> <a href="../_file/EnterpriseCertification.doc"><img src="../_img/calendar1.gif"/>下载申请表</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../_img/rightArrow.gif" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正确填写后Email到 qmpay@qmpay.com ！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../_img/rightArrow.gif" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审核认证后您会得到钱门的通知邮件！
					</div>
          			</td>
          		</tr>
	            <tr>
	              <td class="right_td">公司名称：</td>
	              <td><font color="red">*</font> <input class="text_style" type="text" value="<c:out value='${agent.companyName}'/>" name="companyName" id="companyName" onclick="setText('companyNameTip','请如实填写您公司的姓名。注册后不可更改。')" onblur="lengthBetween('companyName','companyNameTip',2,32,'公司名称长度必须在2到32位以内,请重新填写。')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/(^\s*)|(\s*$)/g,''))" onkeyup="value=value.replace(/(^\s*)|(\s*$)/g,'')" rule="length:2:32"/></td>
	              <td style="width: 60%"><div id="companyNameTip"></div></td>
	            </tr>
           </table>
           </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
	            <tr>
	              <td class="right_td">真实姓名：</td>
	              <td><font color="red">*</font> <input class="text_style" type="text" value="<c:out value='${agent.name}'/>" name="name" id="name" onclick="setText('nameTip','请如实填写您身份证上的姓名。注册后不可更改。')" onblur="lengthBetween('name','nameTip',2,32,'真实姓名长度必须在2到32位以内,请重新填写。')"  onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))"  onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')" rule="length:2:32"/></td>
	              <td style="width: 60%"><div id="nameTip"></div></td>
	            </tr>
	            <tr>
	              <td class="right_td">证件类型：</td>
	              <td colspan="2"><font color="red">*</font> <select name="">
	                <option value="1">身份证</option>
	                  </select></td>
	            </tr>
	            <tr>
	              <td class="right_td">证件号码：</td>
	              <td><font color="red">*</font> <input class="text_style" type="text" value="<c:out value='${agent.certNum}'/>" name="certNum" id="certNum" onclick="setText('certNumTip','请如实填写您的证件号码.')" onblur="checkCard('certNum','certNumTip','身份证号码必须是15位或者18位，请如实填写。')" rule="length:15:20"/></td>
	              <td><div id="certNumTip"></div></td>
	            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>设置您的钱门账户信息</strong></p>
            </div>
          </div>
          <div class="registerActivate">
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">钱门账户：</td>
              <td>  <font color="red"><c:out value="${agent.loginName}"></c:out></font></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td class="right_td">设置登录密码：</td>
              <td><font color="red">*</font> <input type="password" class="text_style" name="password" id="password" onclick="setText('passwordTip','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置登录密码。')" onblur="lengthBetween('password','passwordTip',6,20,'您设置的登录密码有误，登录密码应该由6-20个英文字母、数字或符号组成。')" rule="length:6:20"/></td>
              <td style="width: 60%"><div id="passwordTip"></div></td>
            </tr>
            <tr>
              <td class="right_td">确认登录密码：</td>
              <td><font color="red">*</font> <input type="password" class="text_style" name="repassword" id="repassword" onclick="setText('repasswordTip','请再填写一遍您上面填写的登录密码。')" onblur="checkEquals('password','repassword','repasswordTip','OK!','两次填写的密码不一致，请重新填写。')" rule="equal:password"/></td>
              <td><div id="repasswordTip"></div></td>
            </tr>
            <tr>
              <td class="right_td">设置支付密码：</td>
              <td><font color="red">*</font> <input type="password"  class="text_style" name="payPassword" id="payPassword" onclick="setText('paypasswordTip','由6-20个英文字母、数字或符号组成。建议使用大小写字母与数字混合设置登录密码。')" onblur="checkPayPassword();" rule="length:6:20"/></td>
              <td><div id="paypasswordTip"></div></td>
            </tr>
            <tr>
              <td class="right_td">确认支付密码：</td>
              <td><font color="red">*</font> <input type="password" class="text_style" name="repayPassword" id="repayPassword" onclick="setText('repaypasswordTip','请再填写一遍您上面填写的登录密码。')" onblur="checkEquals('payPassword','repayPassword','repaypasswordTip','OK!','两次填写的密码不一致，请重新填写。')" rule="equal:payPassword"/></td>
           	  <td><div id="repaypasswordTip"></div></td>
            </tr>
             </table>
            </div>
           <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">安全保护问题：</td>
              <td colspan="2"><font color="red">*</font> <select name="question" id="question">
			<option value="我爸爸妈妈的名字各是什么?">我爸爸妈妈的名字各是什么?</option>
			<option value="我婆婆或岳母的名字叫什么?">我婆婆或岳母的名字叫什么?</option>
			<option value="我爸爸的出生地在哪里?">我爸爸的出生地在哪里?</option>
			<option value="我妈妈的出生地在哪里?">我妈妈的出生地在哪里?</option>
			<option value="我的小学校名是什么?">我的小学校名是什么?</option>
			<option value="我的中学校名是什么?">我的中学校名是什么?</option>
			<option value="我的一个好朋友的手机号码是什么?">我的一个好朋友的手机号码是什么?</option>
			<option value="我的一个好朋友的爸爸名字是什么?">我的一个好朋友的爸爸名字是什么?</option>
            </select>
              </td>
            </tr>
            <tr>
              <td class="right_td">您的答案：</td>
              <td><font color="red">*</font> <input type="text" class="text_style" value="<c:out value='${agent.answer}'/>" name="answer" id="answer" onclick="setText('answerTip','这个问题和答案是在找回密码时使用，请您牢记。')" onblur="lengthBetween('answer','answerTip',4,32,'安全保护问题答案必须在4-32位字符之间,请重新填写。')" rule="length:4:32"/></td>
         	  <td style="width: 60%"><div id="answerTip"></div></td>
            </tr>
            <tr>
              <td class="right_td">手机号码：</td>
              <td><font color="red">*</font> <input type="text" class="text_style" value="<c:out value='${agent.mobile}'/>" name="mobile" id="mobile" onclick="chkmobile('mobile','mobileTip','请填写正确的手机号码。港澳台地区及海外用户请在联系电话栏填写。')" onblur="chkmobile('mobile','mobileTip','手机号码必须以13、15或18开头，共11位数字。')" /></td>
              <td><div id="mobileTip"></div></td>
            </tr>
            <tr>
              <td class="right_td">电话号码：</td>
              <td><font color="red">*</font> <input type="text" class="text_style" value="<c:out value='${agent.telephone}'/>" name="telephone" id="telephone" onclick="chktelephone('telephone','telephoneTip','请您填写联系电话作为联系方式。')" onblur="chktelephone('telephone','telephoneTip','联系电话必须填写座机电话号码或者加区号的座机电话号码。')" /></td>
              <td><div id="telephoneTip"></div></td>
            </tr>
            <tr>
              <td class="right_td">&nbsp;</td>
              <td><span class="simplyBtn_1"><input type="button" class="buttom_middle" value="保存并立即启用钱门账户" onclick="toSubmit()"  /></span></td>
              <td>&nbsp;</td>
            </tr>
          </table>
          </div>
        </div>
      </div><c:import url="../_jsp/footer.jsp"></c:import>
    </div>

</div>
</html:form>
</body>
<script>
  var password="<c:out value="${agent.password}"/>";
  var payPassword="<c:out value="${agent.payPassword}"/>";
  var reg="<c:out value="${agent.registerType}"/>";
  
  var regtype= document.getElementsByName("registerType");
  for(var i=0;i<regtype.length;i++){
		if(reg!=""){
			if(regtype[i].value==reg){
				regtype[i].checked=true;
				if(regtype[i].value==1){
				  document.getElementById("company").style.display = "";
				}
			}
		}else {
			if(regtype[i].value==0){
				regtype[i].checked=true;
				document.getElementById("company").style.display = "none";
			}
		}
	} 
		</script>
</html>
