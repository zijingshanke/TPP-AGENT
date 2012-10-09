<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: mobile/mobileBinding.jsp -->

<html>
  <head>
 <title>钱门支付！--网上支付！安全放心！</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen.css" rel="stylesheet" type="text/css" />
	<script src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
	<script type="text/javascript" src="../_js/validateform.js"></script>
 	<script>
 		function toSubmit()
{
	var formid = document.forms[0];
	
	if(validateForm(formid))
	{
		formid.submit();
	}
}
 	</script>
 	</head> 

  
  <body>
<div id="warp">
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />	
<div class="main_top">
	<div class="main_bottom">
		<div class="main_mid">
			<div class="main_title">
				<div class="main_title_right">
					<p>
						<strong>填写资料</strong>
					</p>
				</div>
			</div>
		  <div style="border-bottom: #CCC solid 1px; height:40px; line-height:40px; font-weight:bold; font-size:14px;">
          	请输入您要绑定的手机号码，绑定手机免费：
          </div>
            <div id="container" class="register" style="width: auto;">
          <html:form action="/agent/agent.do?thisAction=sendValidateCode">
          <div class="container">
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <tr>
                  <td class="right_td">请输入手机号码：</td>
                  <td>
<input type="text" class="text_style" value="<c:out value='${ag.mobilePhone}'/>" name="mobilePhone" id="mobile" onclick="setText('mobileTip','手机号码必须以13、15或18开头，共11位数字。')" onblur="chkmobile('mobile','mobileTip','手机号码格式输入有误')" rule="mobile"/>
<font color="red"><c:out value="${mobileError}"/></font>
               </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><div id="mobileTip" class="information" >手机号码必须以13、15或18开头，共11位数字</div></td>                  
                </tr>
                <tr>
                  <td valign="top" class="right_td">请输入图片中的数字：</td>
                  <td colspan="1"><input name="rand" type="text" class="text_style"
					title="请输入右侧验证码" size="6" class="colorblue" /><html:img border="0"
					page="/servlet/com.neza.base.NumberImage" align="absmiddle"
					height="21" width="64" /><c:if test="${rand_Error eq 'error'}"><font color="red">验证码不正确</font></c:if>
					<c:if test="${rand_Error eq 'error2'}"><font color="red">手机已经被绑定过,请更换手机号码</font></c:if>
					</td>
                </tr>
                <tr>
               	  <td>&nbsp;</td>
                  <td><input type="button" name="bb1" class="btn1" value="下一步" onclick="toSubmit();"/>
                  </td>
                </tr>
              </table>
              </div>
          </html:form>
          </div>
        </div>
      </div>
    </div>
   <c:import url="../_jsp/footer.jsp"></c:import>
</div>

</body>
</html>
