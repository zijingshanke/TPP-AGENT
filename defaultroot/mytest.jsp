<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<html>
<head>

<script src="<%=request.getContextPath()%>/_js/prototype.js"></script>
<script src="<%=request.getContextPath()%>/_js/certification.js"></script>
<script LANGUAGE="Javascript" src="" id="get">
</script>
</head>

<body>


<a href="http://www.qmpay.com" >到钱门去</a>

<script LANGUAGE="Javascript">
<!--
function get(url)
{
  var obj = document.getElementById("get");
  obj.src = url;
  if(obj.readStatus == 200)
  {
	  alert(param);
  }
}

function query()
{
 get(get.php);
}

<script language="JavaScript">
alert('开始...');
function testValid()
{
	var email='276628@qq.com';
	var vc=new ValidCerfication(email);
	
	vc.validSSL();
	if(flag)
	  alert("身份验证成功！");
	else
	  alert("请插入合法个人证书！");
}	
  testValid();
</script>
</body>
</html>



