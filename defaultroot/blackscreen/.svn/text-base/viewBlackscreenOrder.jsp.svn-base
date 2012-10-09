<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: viewBlackscreenOrder.jsp -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link href="<%=request.getContextPath()%>/_css/qianmen1.css"
			rel="stylesheet" type="text/css" />
		<script src="<%=request.getContextPath()%>/_js/MyValidation.js"></script>
		<script type="text/javascript" src="../_js/validateform.js"></script>	
	<link rel="stylesheet" href="../_css2/reset.css" type="text/css"></link>
	<link rel="stylesheet" href="../_css2/phone.css" type="text/css"></link></head>

	<body>
		<div id="warp">
			  <c:import url="/_jsp/topMenu.jsp?a=1,7&b=0,8,1,1" />
		<div id="maincontent">
    <div class="mainimg">
       <p><a href="#"><img src="1_02.gif"></img></a></p>
    </div>
    <div class="Prepaidcont">
    	<img src="../_img/mobile/Process.jpg"/>
        <div class="content">
	        <h3>确认充值信息</h3>
<html:form action="/blackscreen/blackscreenpay.do?thisAction=pay">
      <html:hidden property="blackscreenAccount" value="${blackscreen.blackscreenAccount}"></html:hidden>
      <html:hidden property="resultAmount" value="${blackscreen.resultAmount}"></html:hidden>
		  <table width="90%" border="0" cellpadding="0" cellspacing="0" class="inputlist">
              <tr align="center">
                <td>黑屏账号:</td>
                <td align="left"><c:out value="${blackscreen.blackscreenAccount}"></c:out></td>
              </tr>
              <tr align="center">
                <td>充值金额:</td>
                <td align="left"><c:out value="${blackscreen.resultAmount}"></c:out>元</td>
              </tr>
              <tr>
              	<td colspan="2">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="center"><input type="submit" value="开始充值" class="topup_btn"/></td>
              </tr>
            </table>
            </html:form>
        </div>
                <div class="confoot" style="display: none;">
                	
                </div>
    </div>
    <div id="askedquestions" >
    <p><a href="#"><img src="1_01.gif"></img></a>
    </div>
</div>
		<c:import url="../_jsp/footer.jsp"></c:import>

		</div>

	</body>

</html>
