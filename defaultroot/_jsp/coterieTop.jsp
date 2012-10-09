<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div id="head">
      <span style="float:right;padding-top:40px;">
      <img src="../_img/phone.gif"/>&nbsp;：<i style="font-size: 20px;color: red;">0756-8801800</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	<a href="../agent/agent.do?thisAction=viewMyAgent&showTabMenuIdList=1,6&showSubMenuIdList=0,7,5,6" class="head_a">
            您好， <c:if test="${URI.agent.registerType==0}">
            <c:out value="${URI.agent.name}"></c:out>
            </c:if>
            <c:if test="${URI.agent.registerType==1}">
            <c:out value="${URI.agent.companyName}"></c:out>
            </c:if>
        </a>
        <a href="<%=basePath %>help/index.jsp" style=" border:none;">帮助中心</a>
        [<a href="<%=basePath %>agent/agent.do?thisAction=removeSession" style=" border:none;">退出</a>]
       
      </span>
      <a href="../index.jsp" style="border:none; margin:0; padding:0;"><img src="../_img/logo.jpg" /></a>
    </div>
    <div  class="nav">
      <div class="nav_a" >
        <div class="nav_b">
          <span class="font_style3">退款协议</span>
        </div>
      </div>
    </div>
    <script type="text/javascript">

</script>
    
