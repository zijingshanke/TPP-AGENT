<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!-- /security/checkedApply.jsp -->
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />

		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
		<script language="javascript"
			src="<%=request.getContextPath()%>/_js/common.js"></script>
	</head>
	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>申请数字证书</strong>
								</p>
							</div>
						</div>
						<img
							src="<%=request.getContextPath()%>/_img/security/zhengshu001.gif"
							style="margin-top: 12px;" />									
					</div>
				</div>
			</div>
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_title">
							<div class="main_title_right">
								<p>
									<strong>申请确认</strong>
								</p>
							</div>
						</div>
						<div class="yellowBox_mes">
							请查看您的手机，并将收到的手机短信“验证码” 输入到下面的输入框里以确认您的身份。
						</div>
						<html:form action="/security/certificate.do">
							<html:hidden property="thisAction" value="confirmMessage" />
							<table cellpadding="0" cellspacing="0" width="100%"
								class="information_table">
								<tr>
									<td style="width: 20%; text-align: right;">
										绑定的手机号码：
									</td>
									<td style="width: 80%;">
										<html:text property="mobile" styleId="mobile" indexed="1"  style="border:0px;" readonly="true"></html:text>
									</td>
								</tr>
								<tr>
									<td style="text-align: right;">
										安全验证码：
									</td>
									<td>
										<input type="text" id="verifyPwd" name="verifyPwd" />
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" name="button" id="scButtonId" onclick="sendMsg();"
											value="如果您没有收到短信，请点击重发" style="width: 250px;" />
									</td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: right;width:300px;">
										<em class="information_text"><div id="msg" style="color:red;"></div></em>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td>
										<span class="simplyBtn_1"><input id="btNext"
												name="btNext" type="button" class="buttom_middle"
												onclick="gotoNext()" ; value="下一步" /> </span><span
											class="simplyBtn_1" style="margin-left: 20px;"><input
												type="button" class="buttom_middle" value="返  回" onclick="history.go(-1)"/> </span>
									</td>
								</tr>
							</table>
						</html:form>
					</div>
				</div>
			</div>
			<div id="footer">
				CopyRight 2005-2008, fdays.com .All Rights Reserved 增值电信业务经营许可证
				粤B2-20040561
			</div>
		</div>
	</body>
</html>
<script>
<!--
 var XMLHttpReq;
 var _gSecond = 61;
 /**
 *
 */
 function gotoNext()
 {
 	
 	var obj=document.getElementById('verifyPwd');
 	if(!required(obj)){
 		showMsg("msg","安全验证码不能为空","information");
 	}
    var url="<%=request.getContextPath()%>/security/certificate.do?thisAction=verifyValidationcode&verifyPwd="+obj.value;
    ajaxRequest(url,callback);
 }
 function callback()
 {
    if(XMLHttpReq.readyState == 4)
    {
        if(XMLHttpReq.status == 200)
        {
            checkVerifyPassword();
        }
    }
 }
 function checkVerifyPassword()
 {
     var objects=XMLHttpReq.responseXML.getElementsByTagName("root");
     var isSuccess=objects[0].getElementsByTagName("is_success")[0].firstChild.data;
//     alert(isSuccess+"   验证码输入正确则为 T ");
     if(isSuccess=='T'){
     		document.forms[0].submit();
     		document.getElementById('btNext').disabled=true;
     }else{
     		//document.forms[0].btNext.disabled="true";
     		showMsg("msg","安全验证码不正确","information");
     }
 }
 /**
 *
 */
 function sendMsg()
 {
 	var obj=document.getElementById('mobile');
 	if(!required(obj)){
 		showMsg("msg","手机号码不能为空","information");
 	}else{
		if(!verifyMobile(obj.value)){
			showMsg("msg","手机号码输入有误","information");
			obj.focus();
			return false;				
		}
	}
	var msg1="钱门通知:您正在申请钱门的数字证书,验证码:";
	var msg2="，请正确填写验证码，完成数字证书的申请 ";
	
    var url="<%=request.getContextPath()%>/security/certificate.do?thisAction=sendMessage&mobile="+obj.value+"&msg1="+encodeURI(encodeURI(msg1))+"&msg2="+encodeURI(encodeURI(msg2));
  		_gSecond=61;
  		disabledButton('scButtonId');
    ajaxRequest(url,callbackSendMsg);
 }
 function callbackSendMsg()
 {
    if(XMLHttpReq.readyState == 4)
    {
        if(XMLHttpReq.status == 200)
        {
             var objects=XMLHttpReq.responseXML.getElementsByTagName("root");
    		 var isSend=objects[0].getElementsByTagName("is_send")[0].firstChild.data;
 //   		 alert(isSend);
        }
    }
 } 
/**
*
*/
function disabledButton(id){
    if(id == null || id.length == 0){
       id = "scButtonId";
    }
    var scButton = document.getElementById(id);
    if(_gSecond > 1){
       _gSecond --;
       scButton.value = "手机验证码已发送，请您查收！[" + (_gSecond < 10 ? '0' : '') + _gSecond + "]";
       scButton.disabled = true;
       window.setTimeout("disabledButton()",1000);
    }else{
       scButton.value = "如果您没有收到短信，请点击重发";
       
       scButton.disabled = false;
    }
}
window.onload = function(){disabledButton('scButtonId');}
//
 function ajaxRequest(_url,_callback)
 {
    if(window.XMLHttpRequest){
       XMLHttpReq=new XMLHttpRequest();
    }else if(window.ActiveXObject){
       XMLHttpReq=new ActiveXObject("Microsoft.XMLHTTP");
    }    
    if(XMLHttpReq){
        XMLHttpReq.open("GET",_url,true);
        XMLHttpReq.onreadystatechange=_callback;
        XMLHttpReq.send(null);
    }
 }
 //
 function showMsg(_id,_msg,_className){
 	var obj=document.getElementById(_id);
 	obj.innerHTML=_msg;
 	obj.className=_className;
 }
function verifyMobile(_mobile){
	if((/^13\d{9}$/g.test(_mobile))
	||(/^15\d{9}$/g.test(_mobile)) 
	||(/^18\d{9}$/g.test(_mobile))){
		return true;
	}
	return false;
}
//-->
</script>
