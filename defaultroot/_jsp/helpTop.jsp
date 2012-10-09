<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="head">
      <span style="float:right;padding-top:40px;">
      <img src="../_img/phone.gif"/>&nbsp;：<i style="font-size: 20px;color: red;">0756-8801800</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        您可以：<a href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister" style=" border:none;">注册</a> <a href="<%=basePath %>help/index.jsp" style=" border:none;">帮助中心</a><a href="<%=request.getContextPath()%>/login.jsp">返回登录</a>
      </span>
      <a href="../index.jsp" style="border:none; margin:0; padding:0;"><img src="../_img/logo.jpg" /></a>
    </div>
    <div  class="nav"> 
      <div class="nav_a" >
        <div class="nav_b">
          <span class="font_style3">帮助中心</span>
          <a href="../help/index.jsp" style="text-decoration:none;"><span class="more"><img src="../_img/out_ico.gif" />  返回帮助中心</span></a>
        </div>
      </div>
    </div>
    <script type="text/javascript">

</script>
    
