<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*,com.fordays.fdpay.right.UserRightInfo,com.fordays.fdpay.agent.Agent"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<%
	UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
	Agent agent = new Agent();
	if (uri != null) {
		agent = uri.getAgent();
	}
%>
<!--JSP交易管理: page/accessDeniedCertTop.jsp -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/_css/qianmen.css" type="text/css" />

		<div id="head">
		
			<span style="float: right;padding-top:40px;"> 
			<img src="../_img/phone.gif"/>&nbsp;：<i style="font-size: 20px;color: red;">0756-8801800</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="../agent/agent.do?thisAction=viewMyAgent&showTabMenuIdList=1,6&showSubMenuIdList=0,7,5,6" class="head_a"> 
				 <c:if test="${URI!=null}">您好， <c:if test="${URI.agent.registerType==0}">
            <c:out value="${URI.agent.name}"></c:out>
            </c:if>
            <c:if test="${URI.agent.registerType==1}">
            	<c:if test="${URI.agent.companyName!=null}">
		            <c:out value="${URI.agent.companyName}"></c:out>            	
            	</c:if>
	            <c:if test="${URI.agent.companyName==null}">
	            	<c:out value="${URI.agent.name}"></c:out>	            	
	            </c:if>
            	<c:if test="${URI.agentUser!=null}">
            		- <c:out value="${URI.agentUser.name}"></c:out>
            	</c:if>
            </c:if>
            </c:if>
            <a href="<%=request.getContextPath() %>/help/index.jsp" style=" border:none;">帮助中心</a>
             </a> <c:if test="${URI!=null}">[<a href="<%=basePath%>agent/agent.do?thisAction=removeSession" style="border: none;">退出</a>] </c:if>
				<html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">立即充值</html:link>
				</span>
		</div>
		<html:link page="/index.jsp">
			<img src="<%=request.getContextPath()%>/_img/logo.jpg" border="0" style="border: 0px;padding:0 0 5px 15px;" />
		</html:link>