<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<div id="adLayer" style="position:absolute;left: 10px; top:10px;">
<table border="0" width="110" cellspacing="0" cellpadding="0"> 
<tr><td><img border=0 src="<%=request.getContextPath()%>/_img/kefu_up.gif"></td></tr> 
<tr><td valign="middle" background="<%=request.getContextPath()%>/_img/kefu_middle.gif"> &nbsp;
 <font color="#D4D0C8">点击交谈或留言</font>
</td></tr> 
<tr><td valign="middle" background="<%=request.getContextPath()%>/_img/kefu_middle.gif"> &nbsp;
 <font color="#D4D0C8">在线8:30-17:30</font>
</td></tr> 

<tr><td valign="middle" background="<%=request.getContextPath()%>/_img/kefu_middle.gif"> &nbsp;
<a href="http://sighttp.qq.com/cgi-bin/check?sigkey=659d55c361c883d169cc632bffd7822f70e6dfb2fb5a6dd46e16a376e85fd9cd"; target="_blank"; onclick="var tempSrc='http://sighttp.qq.com/wpa.js?rantime='+Math.random()+'&sigkey=659d55c361c883d169cc632bffd7822f70e6dfb2fb5a6dd46e16a376e85fd9cd';var oldscript=document.getElementById('testJs');var newscript=document.createElement('script');newscript.setAttribute('type','text/javascript'); newscript.setAttribute('id', 'testJs');newscript.setAttribute('src',tempSrc);if(oldscript == null){document.body.appendChild(newscript);}else{oldscript.parentNode.replaceChild(newscript, oldscript);}return false;">
<img src="http://is.qq.com/webpresence/images/status/03_online.gif"/>
</a >
</td></tr> 
<tr><td><img border=0 src="<%=request.getContextPath()%>/_img/kefu_down.gif"></td></tr> 
</table> 
</div>
<script language="JavaScript" type="text/javascript">
   function initAd() {
   var abc = document.getElementById("adLayer");
   abc.style.top = document.documentElement.scrollTop+10+"px";
   abc.style.left=10;
    setTimeout(function(){initAd();},50);
   }
   </script>