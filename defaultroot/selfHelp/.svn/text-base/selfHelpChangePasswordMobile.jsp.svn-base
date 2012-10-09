<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- selfHelpChangePasswordMobile.jsp -->
<html>
  <head>
   <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/jquery.js"></script>
<script>
	function reSentValidCode(){
	var secs =20;
    document.forms[0].bt_sendvalidcode.disabled=true;
    for(i=1;i<=secs;i++) {
      window.setTimeout("update(" + i + ","+secs+")", i * 1000);
    }
	}
	function update(num) { 
	var secs =20;
	if(num == secs) { 
	document.forms[0].bt_sendvalidcode.value ="短信获取校验码"; 
	document.forms[0].bt_sendvalidcode.disabled=false; 
	} 
	else { 
	printnr = secs-num; 
	document.forms[0].bt_sendvalidcode.value = "( " + printnr +" 秒后短信获取校验码)"; 
	} 
	} 
	function sendValidateCode(){
	var mobile="<c:out value="${mobilePhone}"></c:out>";
	alert(mobile);
			$.ajax({
   		type: "POST",
   		data:"msgType=3&mobile="+mobile+"",
   		url: "../agent/agent.do?thisAction=ajxSendValidateCode",
  		 dataType:"text",
  		 success: function(data){
  		 	reSentValidCode();
  		} 
		}); 
	}
</script>
  </head> 
  <body onload="reSentValidCode();">
  <div id="warp">
     <c:if test="${URI==null}">
   <c:import url="../selfHelp/head.jsp"></c:import>
   </c:if>
   <c:if test="${URI!=null}">
   <c:import url="../_jsp/mainTop.jsp"></c:import>
   </c:if>
     <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
            <c:if test="${type eq 'password'}"> 
              <p><strong>用手机找回登录密码</strong></p>
            </c:if>
            <c:if test="${type eq 'payPassword'}">
              <p><strong>用手机找回支付密码</strong></p>
            </c:if>
            </div>
          </div>
          <div class="pay_way">
            <img src="../_img/pay_pic.gif" />
          </div>
	<html:form action="/agent/agent.do?thisAction=validateCodeGetBack">
	<input type="hidden" name="type" value="<c:out value="${type}"></c:out>"></input>
	<input type="hidden" name="loginName" value="<c:out value="${loginName}"></c:out>"></input>
	<input type="hidden" name="mobilePhone" value="<c:out value="${mobilePhone}"></c:out>"></input>
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <tr>
                  <td class="right_td">您绑定的手机号码：</td>
                  <td><span class="font_style2"><c:out value="${mobilePhone}"></c:out></span></td>
                </tr>
                <tr>
                  <td class="right_td">输入收到的校验码：</td>
                  <td><html:text property="mobileValidateCode"></html:text>
                                <c:if test="${codeError eq 'error'}"><span  class="yellowBox_mes" style="padding:auto;" >校验码有误!</span></c:if>
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td valign="top" class="Request">如果您没有收到校验码，您可以：<p style="margin:12px 0 0 0;"><span class="simplyBtn_1" style="margin:0"><input name="bt_sendvalidcode" type="button" class="buttom_middle" value="短信获取效验码" onclick="sendValidateCode();" /></span></p></td>
                </tr>
                <tr>
               	  <td>&nbsp;</td>
                  <td><span class="simplyBtn_1" style="margin:0"><input type="submit" class="buttom_middle" value="下一步" /><span></td>
                </tr>
              </table>
          </html:form>
          <div class="goBack">
          	<img src="../_img/goBack.gif" /><html:link page="/agent/agent.do?thisAction=jumpTermFashion&type=${type}&loginName=${loginName}">返回选择其他方式</html:link>
          </div>
        </div>
      </div>
    </div>
     <c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
