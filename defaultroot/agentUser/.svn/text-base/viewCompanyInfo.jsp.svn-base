<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agentUser/viewCompanyInfo.jsp -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>
</head>

<body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,0,6" />
      <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_mid_title"><strong>企业信息</strong></div>
          <p>商户名称: <span class="font_style1"><c:out value="${agent.companyName}"></c:out></span></p>
          <p>银行类型:  <span class="font_style1">
          <c:if test="${account==null}">暂无</c:if>
          <c:if test="${account!=null}"><c:out value="${account.accountAddress}"></c:out></c:if>
          </span></p>
          <p>银行账号:<span class="font_style1"> <c:if test="${account==null}">暂无</c:if><c:if test="${account!=null}"><c:out value="${account.haskCardNo}"></c:out></c:if></span></p>
        </div>
      </div>
    </div>
  
 <c:import url="/_jsp/footer.jsp"/>
  </div>
</body>
</html>
