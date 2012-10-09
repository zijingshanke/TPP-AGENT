<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- jsp:mobileUnBinding.jsp -->
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
	var secs =8;
    document.forms[0].bt_sendvalidcode.disabled=true;
    for(i=1;i<=secs;i++) {
      window.setTimeout("update(" + i + ","+secs+")", i * 1000);
    }
	}
	function update(num) { 
	var secs =8;
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
	var mobile="<c:out value="${URI.agent.mobilePhone}"></c:out>";
	alert(mobile);
			$.ajax({
   		type: "POST",
   		data:"msgType=2&mobile="+mobile+"",
   		url: "../agent/agent.do?thisAction=ajxSendValidateCode",
  		 dataType:"text",
  		 success: function(data){
  		 	reSentValidCode();
  		} 
		}); 
	}
	
	function unBindingMobile(){
		window.location.href="../agent/agent.do?thisAction=unBindingMobileQuestion";
	}
</script>
 	</head> 

  
  <body onload="reSentValidCode();">
<div id="warp">
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />	
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>取消手机服务</strong></p>
            </div>
          </div>
          <div class="yellowBox_Check">
          	<span>请查看您的手机，并将收到的六位数字的“校验码”输入到下面框里。</span>
            <p style="color:#666;">如果您换手机号码了，无法收到验证码，您可以通过<span class="simplyBtn_1 simplyBtn_11"><input type="button" class="buttom_middle" value="安全保护问题取消手机绑定" onclick="unBindingMobile();" /></span>。</p>
            <p style="color:#666;">如果您不想取消手机绑定，请<a href="../agent/agent.do?thisAction=viewMobileCenter" style="color: #005C9C;">点此取消申请</a>。</p>
          </div>
          <div class="blueBox_1" style="width:500px;">
          	您已经申请取消手机绑定。（手机号码为：<c:out value="${URI.agent.mobilePhone}"></c:out>）
          </div>
          <html:form action="/agent/agent.do?thisAction=unbindingMobileValidate">
          <html:hidden property="mobilePhone" value="${URI.agent.mobilePhone}"></html:hidden>
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <tr>
                  <td class="right_td">输入校验码：</td>
                  <td><html:text property="mobileValidateCode" styleClass="text_style" ></html:text>
                   <c:if test="${codeError eq 'error'}"><span  class="yellowBox_mes" style="padding:auto;" >校验码有误!</span></c:if>
                  </td>
                </tr>
                <tr>
                  <td valign="top" class="right_td">输入支付密码：</td>
                  <td><html:password property="payPassword" styleClass="text_style"></html:password>
                    <c:if test="${payPasswordError eq 'error'}"> <span  class="yellowBox_mes" style="padding:auto;" >支付密码有误</span></c:if>
                  <p style="margin:8px 0 0 0;"><a href="../agent/agent.do?thisAction=forgetPassword&type=paypassword" style="color: #005C9C;">找回支付密码</a></p></td>
                </tr>
                <tr>
               	  <td>&nbsp;</td>
                  <td><span class="simplyBtn_1" style="margin:0"><input type="submit" class="buttom_middle" value="取消绑定" /></span></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td valign="top" class="Request">如果您没有收到校验码，您可以：<p style="margin:12px 0 0 0;"><span class="simplyBtn_1" style="margin:0"><input type="button" class="buttom_middle" value="短信获取校验码" name="bt_sendvalidcode" onclick="sendValidateCode();" /></span></p></td>
                </tr> 
              </table>
          </html:form>
          
        </div>
      </div>
    </div>
   <c:import url="../_jsp/footer.jsp"></c:import>
</div>

</body>
</html>
