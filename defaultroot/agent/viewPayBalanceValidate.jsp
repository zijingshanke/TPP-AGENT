<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- viewPayBalanceValidate.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/jquery.js"></script>
<script>
	
	function sendValidateCode(){
		var mobile="<c:out value="${mobilePhone}"></c:out>";
				$.ajax({
	   		type: "POST",
	   		data:"msgType=4&mobile="+mobile+"",
	   		url: "../agent/agent.do?thisAction=ajxSendValidateCode",
	  		 dataType:"text",
	  		 success: function(data){
	  		 	reSentValidCode();
	  		} 
			}); 
	}
</script>
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>
   <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>余额支付功能管理</strong></p>
            </div>
          </div>
          <html:form action="/agent/agent.do?thisAction=startupPayBalanceValidate">
          <html:hidden property="mobilePhone" value="${mobilePhone}"></html:hidden>
          <html:hidden property="id" value="${agent.id}"></html:hidden>
          <div class="chenggongshizhi yuezhifu">
            <div class="yuezhifuTitle">
                <em>校验码已发送至您的手机上，请在下面输入框中输入校验码，并完成申请。</em>
                <p>您已经于<c:out value="${data}"/>申请开启“钱门余额支付功能”，校验码短信已经发送到您的手机上，请在下面输入框中输入校验码。</p>
				<p>校验码请勿透露他人。钱门工作人员不会索要您的校验码。</p>
                <div style="margin-top:12px;"><span style="font-size:14px; font-weight:bold;">验证码：</span>
                <html:text property="mobileValidateCode"></html:text><span class="simplyBtn_1" style="margin-left:12px;">
                <c:if test="${codeError eq 'error'}">
                验证码不正确!
                </c:if>
                <input type="submit" class="buttom_middle" value="确定" /></span></div>
            </div>
            <ul class="chenggongshizhiList">
                <li>如果长时间没有收到校验码，请<a href="javascript:sendValidateCode();">点此获得短信校验码</a>（稍候会收到确认信息，请耐心等待！）</li>
            </ul>
          </div>
          </html:form>
        </div>
      </div>
    </div>
    <c:import url="/_jsp/footer.jsp" />
    </div>
  </body>
</html>
