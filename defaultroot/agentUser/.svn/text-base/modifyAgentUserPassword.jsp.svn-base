<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agentUser/modifyAgentUserPassword.jsp -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>
    <link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css/banklist.css" type="text/css" />
</head>

<body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_mid_title"><strong>编辑操作员</strong> </div>
          
          <html:form action="/agent/agentUser.do?thisAction=modifyAgentUserPassword">         
          <table style="margin-left:300px;"> 
          	<tr>
	          	<td style="text-align:right;width:100px;">姓名：</td> <td style="text-align:center;width:170px;"><html:text property="name" value="${agentUser.name}" disabled="true" readonly="true"  style="width:150px;" styleClass="text_style"></html:text></td> <td></td> 
          	</tr>
           	<tr>
	           	<td style="text-align:right;width:100px;">登陆账号：</td><td style="text-align:center;width:170px;"><html:text property="no" value="${agentUser.no}" disabled="true" readonly="true" style="width:150px;" styleClass="text_style"></html:text></td> <td></td> 
           	</tr>
           	<tr>
 	          	<td style="text-align:right;width:100px;">登陆密码：</td><td style="text-align:center;width:170px;"><html:password property="password" value="" styleClass="text_style" style="width:150px;" styleId="password" onblur="checkPassword()"></html:password></td>	 <td style="width:200px;text-align:left;"><font color="red"> <div id="divPassword" style="display:none;"></div></font></td> 
           	</tr> 
           	<tr>
 	          	<td style="text-align:right;width:100px;">确认密码：</td> <td style="text-align:center;width:170px;"><html:password property="password2" value="" styleClass="text_style" style="width:150px;" styleId="password2" onblur="checkPassword2()"></html:password></td>  <td style="width:200px;text-align:left;"><font color="red"> <div id="divPassword2" style="display:none;"></div></font></td> 
           	</tr>
          	<tr>
          		<td></td>
          		<td colspan="2" style="width:300px;">
          		<input type="button" class="btn1" value="修改" onclick="checkForm()"/>
          		<input type="reset" class="btn1" value="重置" /></td>          		
          	</tr>
          	<html:hidden property="id" value="${agentUser.id}"></html:hidden>
          </table>
          </html:form>
          
        </div>
      </div>
    </div>
  
 <c:import url="/_jsp/footer.jsp"/>
  </div>
<script>

function checkForm(){
	 if(checkPassword()==false)
    {
		return;
    }
    else if(checkPassword2()==false)
    {
		return;
    }
    else{
		if(confirm("是否确认修改？"))
			document.forms[0].submit();	
	}
}
function checkPassword(){
	var password=document.getElementById('password').value;
	var dig="g";
	document.getElementById('password').value=Trim(password,dig);
	if(password==""){
		document.getElementById('divPassword').innerHTML="登陆密码不能为空！";
		document.getElementById('divPassword').style.display="block";
		return false;		
	}
	if(password.length<6 || password.length>18){
		document.getElementById('divPassword').innerHTML="登陆密码长度在6~18之间！";
		document.getElementById('divPassword').style.display="block";
		return false;					
	}	
	document.getElementById('divPassword').style.display="none";	
}
function checkPassword2(){
	var password2=document.getElementById('password2').value;
	var password=document.getElementById('password').value;
	document.getElementById('password2').value=password2;
	if(password2==""){
		document.getElementById('divPassword2').innerHTML="确认密码不能为空！";
		document.getElementById('divPassword2').style.display="block";
		return false;		
	}
	if(password2!=password){
		document.getElementById('divPassword2').innerHTML="确认密码必须和登录密码一致！！";
		document.getElementById('divPassword2').style.display="block";
		return false;					
	}
	document.getElementById('divPassword2').style.display="none";	
	
}

// 去全部空格   
function Trim(str,is_global) 
{ 
	var result; 
	result = str.replace(/(^\s+)|(\s+$)/g,""); 
	if(is_global.toLowerCase()=="g") 
	result = result.replace(/\s/g,""); 
	return result; 
}

function LTrim(str)
{
 return str.replace(/^\s*/g,"");
}

function RTrim(str)
{
 return str.replace(/\s*$/g,"");
}

function trim(str)
{
 return str.replace(/(^\s*)|(\s*$)/g, "");
}

</script>
</body>
</html>
