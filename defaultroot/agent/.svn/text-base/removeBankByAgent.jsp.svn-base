<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agent/removeBankByAgent.jsp -->	

 <head> 
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
 <script>
 function sbm(){
 	if (confirm("您确定要删除这个账户吗？"))
 		document.forms[0].submit();
 }
 
 </script>
 
  </head>
<body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>删除银行</strong><a href="agent.do?thisAction=viewMyAgent" class="a_style1">返回我的账户</a></p>
            </div>
          </div> 
          <html:form action="/agent/agent.do?thisAction=removeBankByAgent">
		<html:hidden property="id" value="${URI.agent.id}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td style="width:100px; text-align:right;">账户名：</td>
              <td><span class="font_style2"><c:out value="${URI.agent.loginName}"></c:out></span></td>
            </tr>
            <tr>
              <td align="right">已绑定银行：</td>
              <td class="banner_td">
              <c:out value="${bindAccount.haskCardNo}"></c:out>[<c:out value="${bindAccount.bankName}"></c:out>]
              </td>
            </tr>
            <tr>
              <td align="right" valign="top" style="padding-top:13px;">选择银行：</td>
              <td class="banner_td">
              <c:forEach var="account" items="${list}">
		<c:if test="${bindAccount.bankId==account.bankId}">
		<p><input type="radio" name="bindAccount" value="<c:out value="${account.id}"></c:out>" checked="checked" style="width: 25px"><c:out value="${account.haskCardNo}"></c:out>[<c:out value="${account.bankName}"></c:out>]</input></p>
		</c:if>
		<c:if test="${bindAccount.bankId!=account.bankId}">
	<p><input type="radio" style="width: 25px" name="bindAccount" value="<c:out value="${account.id}"></c:out>"><c:out value="${account.haskCardNo}"></c:out>[<c:out value="${account.bankName}"></c:out>]</input></p>
		</c:if>
	
	</c:forEach>
              	
              </td>
            </tr>
            <tr>
              <td align="right">支付密码：</td>
              <td>
              	<html:password property="payPassword" value="" styleClass="text_style"/><font color="red"><c:out value="${msgPayPassword}"></c:out><c:out value="${msg}"></c:out></font>
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>
              	<input type="button" value="确 认" class="btn1" onclick="sbm();"/>
              </td>
            </tr>
          </table>
          </html:form>
        </div>
      </div>
    </div>
   <c:import url="/_jsp/footer.jsp" />
  </div>
</body>
</html>
