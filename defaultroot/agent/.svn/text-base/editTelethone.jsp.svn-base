<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- editTelethone.jsp -->
<html>
  <head>  
<title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
  </head>
  <script type="text/javascript">
  	 function f_check_phone(obj) {
  	 	var regu =/(^([0][1-9]{2,3}[-])?\d{3,8}(-\d{1,9})?$)|(^\([0][1-9]{2,3}\)\d{3,8}(\(\d{1,9}\))?$)|(^\d{3,8}$)/;
  	  	var re = new RegExp(regu);
  	  		if (re.test( obj.value )) {	  return true;	
  	  		}	
  	  		return false;} 
  	  		
  	  function check(){
  	  	var telephone=document.forms[0].telephone;
  	  	var checkmsg= document.getElementById("checkmsg");
  	  	if(f_check_phone(telephone)){
  	  		document.forms[0].submit();
  	  	}else
  	  	{
  	  		checkmsg.innerHTML="电话号码必须由数字和'-'构成 ";
  	  	}
  	  }
  </script>
  <body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>修改电话号码</strong><a href="agent.do?thisAction=viewMyAgent" class="a_style1">返回我的账户</a></p>
            </div>
          </div>
          <html:form action="/agent/agent.do?thisAction=updateTelephone">
	<html:hidden property="id" value="${agent.id}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">真实姓名：</td>
              <td><span><c:out value="${agent.name}"></c:out></span></td>
            </tr>
            <tr>
              <td class="right_td">账户名：</td>
              <td><span class="font_style2"><c:out value="${agent.loginName}"></c:out></span></td>
            </tr>
            <tr>
              <td class="right_td">联系号码：</td>
              <td><html:text property="telephone" value="" styleClass="text_style" style="width:200px"></html:text><div id="checkmsg"></div></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td valign="top" class="Request">例如：“0756-8800000”。</td>
            </tr>
            <tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="button" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认"  onclick="check();"/></span></td>
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