<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- changeSuccess.jsp -->
<html>
  <head>
    <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	
 	</head> 
  <body>
  <html:form action="/security/security.do?thisAction=changePassword">
  <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
 修改成功!
<c:import url="/_jsp/footer.jsp"/>
</div>

  </html:form>
  </body>
 
</html>
