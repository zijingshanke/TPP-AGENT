<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <form action="doAction.jsp" name="frm">
   选择证书:<input type="file" name="file1"/><br>
   <input type="button" value="导入证书" onclick="sub();"/><br>
</object>
 <object id="myControl1" name="myControl1" classid="clsid:C82E6CC0-BF49-4D0D-BA35-279F52C8136B"  codebase="exe_SSL.cab#version=1,0,0,0">
       </object>  
</object>
</object>
</object>
   <input name="txt" type="text" value="testActiveAx" /><input type="button" value="显示所以证书" onclick="doScript();"/><br>
   <input type="button" onclick="TGetRemoteURL();" value="测试URL连接"/><br/>
  <input type="text" value="请下载安全空间"/>
  <object id="myControl2" name="myControl2"
   classid="clsid:9551B223-6188-4387-B293-C7D9D8173E3A"  codebase="exe_SSL.cab#version=1,0,0,0">
</object>
   <input type="button" onclick="getPasswordMD5();" value="测试MD5加密"/><br/>
   <input type="button" onclick="downloadActiveX();" value="下载安全控件"/><br/>
   [96e79218965eb72c92a549dd5a330112]
   
   </form>
   <script type="text/javascript">
   	var  myControl =document.forms[0].myControl1;
   	function sub(){
   
   	myControl.importCertificate(document.forms[0].file1.value);
   	}
   	
   	function doScript()
      {
     var str=myControl.getMySSLS;
    var strs= new Array(); //定义一数组
	strs=str.split(","); 
	var d1= document.getElementById("certinfo");
     for (i=0;i<strs.length ;i++ )    
    {    
        d1.innerHTML+=strs[i]+"<br/>";
    } 
      }
   	function TGetRemoteURL(){
   		alert(myControl.GetRemoteURL);
   	}
   	
   	function getPasswordMD5(){
   		var myControl2 = document.getElementById("myControl2");
   		var password=myControl2.encryptMD5();
   		alert("密码:"+password);
   		
   	}
   	
   	function winopen(a)
{
	window.open(a,"alank","width=600,height=400,scrollbars=yes,resizable=no,toolbar=no,directories=no,menubar=no,top=100,left=200");
}

   	function downloadActiveX(){
   		winopen("<%=request.getContextPath()%>/setup.exe");
   	}
   </script>
   <div id="certinfo">
   s<BR/>
   </div>
  </body>
</html>
