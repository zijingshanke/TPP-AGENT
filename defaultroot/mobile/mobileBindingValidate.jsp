<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: mobile/mobileBindingValidate.jsp -->

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
		var secs =60;
	    document.forms[0].bt_sendvalidcode.disabled=true;
	    for(i=1;i<=secs;i++) {
	      window.setTimeout("update(" + i + ","+secs+")", i * 1000);
	    }
	}
	function ckXieYi(ckbox){
		 ckb=ckbox;
		if(ckb.checked){
			document.forms[0].btnSbm.disabled=false;
		}else{
			document.forms[0].btnSbm.disabled=true;
		}
	}
	function update(num) {  
		var secs =60;
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
				$.ajax({
	   		type: "POST",
	   		data:"msgType=1&mobile="+mobile+"",
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
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />
    <div id="main">
    
   
   
	  <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>开通手机服务</strong></p>
            </div>
          </div>
          <div class="yellowBox_Check">
          	<span>请查看您的手机，并将收到的六位数字的“校验码”输入到下面框里。</span>
            <p><a href="../agent/agent.do?thisAction=viewMobileCenter" style="color: #005C9C;">返回手机服务中心</a></p>
          </div>
          <div class="blueBox_1" style="width:500px;">
          	您已经于申请手机绑定。（手机号码为：<c:out value="${mobilePhone}"></c:out>）
          </div>
          <html:form action="/agent/agent.do?thisAction=bindingMobile">
           <html:hidden property="mobilePhone" value="${mobilePhone}"></html:hidden>
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <tr>
                  <td class="right_td">输入六位校验码：</td>
                  <td align="left"><html:text property="tempMobileValidateCode" styleClass="text_style"></html:text><c:if test="${codeError eq 'error'}"><span  class="yellowBox_mes" style="padding:auto;font-size: 12px;" >校验码有误!</span></c:if>
                 </td>         
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td colspan="2"><div class="yellowBox_mes" style="margin:0; font-size:12px;">每个手机24小时内最多只允许成功申请5次短信校验码。</div></td>
                </tr>
                <tr>
                  <td valign="top" class="right_td">输入支付密码：</td>
                  <td><html:password property="payPassword" styleClass="text_style"></html:password>
                    <c:if test="${payPasswordError eq 'error'}"> <span  class="yellowBox_mes" style="padding:auto;" >支付密码有误</span></c:if>
                  <a href="../agent/agent.do?thisAction=viewMobileCenter" style="color: #005C9C;">找回支付密码</a></td>
              	
                </tr>
                
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td valign="top" class="Request">如果您没有收到校验码，您可以：<p style="margin:12px 0 0 0;"><span class="simplyBtn_1" style="margin:0"><input name="bt_sendvalidcode" type="button" class="buttom_middle" value="短信获取校验码" onclick="sendValidateCode();" /></span></p></td>
                </tr> 
                <tr>
                  <td>&nbsp;</td>
                  <td style="color:#333;"><strong>手机服务条款</strong></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><textarea cols="50" style="width:300px; height:100px;" readonly="readonly">
     您确认本服务协议后，本服务协议即在您和钱门之间产生法律效力。请您务必在注册之前认真阅读全部服务协议内容，如有任何疑问，可向钱门咨询。

1、  无论您事实上是否在注册之前认真阅读了本服务协议，只要您点击协议正本下方的“确认”按钮，您的行为仍然表示您同意并签署了本服务协议。

2、  本协议不涉及您与钱门其它用户之间因网上交易而产生的法律关系及法律纠纷。

3、  如果您未保管好自己的帐号和密码而对您、钱门或第三方造成的损害，您将负全部责任。
                  
                  </textarea>
                  <input name="ckbox" type="checkbox" value="" style="margin-left:20px;" onclick="ckXieYi(this);"/>
                  <span style="vertical-align:middle; margin-left: 4px;">我接受手机协议</span></td>
                </tr> 
                <tr>
               	  <td>&nbsp;</td>
                  <td><span class="simplyBtn_1" style="margin:0"><input type="submit" name="btnSbm" class="buttom_middle" value="下一步" disabled="disabled" /></span></td>
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
