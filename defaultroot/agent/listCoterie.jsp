<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agent/listCoterie.jsp -->

  <head>  
<title>钱门支付！--网上支付！安全放心！</title>
    <link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,4,6"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        <div class="main_title">
            <div class="main_title_right">
              <p><strong>我的商户圈</strong></p>
            </div>
        </div>
        <table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
    		<tr>
    			<th><div>商户圈名称</div></th>
    			<th><div>商户圈创建人</div></th>
    			<th><div>中间账号</div></th>
    			<th><div>临时账号</div></th>
    		</tr>
    		<c:if test="${coterie.id>0}">
    		<tr>
    		<td><c:out value="${coterie.name}"/></td>
    		<td><c:out value="${coterie.agent.name}"/></td>
    		<td><c:out value="${coterie.agent.loginName}"/></td>
    		<td><c:out value="${at.loginName}"/></td>
    		</tr>
    		</c:if>
    	</table>
    	<div>&nbsp;</div>
    	<div class="main_title">
            <div class="main_title_right">
              <p><strong>我加入的商户圈</strong></p>
            </div>
        </div>
        <table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
    		<tr>
    			<th><div>&nbsp;</div></th>
    			<th><div>商户圈名称</div></th>
    			<th><div>商户圈创建人</div></th>
    			<th><div>创建人账号</div></th>
    		</tr>
    		<%int i=1; %>
    	    <c:forEach items="${list}" var="ac">
    		<tr>
    		<td><%=i%> </td>
    		<td><c:out value="${ac.coterie.name}"></c:out> </td>
    		<td><c:out value="${ac.coterie.agent.name}"></c:out> </td>
    		<td><c:out value="${ac.coterie.agent.loginName}"></c:out> </td>
    		</tr>
    		<%i++; %>
    		</c:forEach>
        </table>
        
        </div>
    </div>
    </div><c:import url="/_jsp/footer.jsp"/>
	</div>
	
  </body>
</html>
