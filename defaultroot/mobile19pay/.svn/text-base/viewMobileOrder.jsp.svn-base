<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: viewMobileOrder.jsp -->
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
			  <c:import url="/_jsp/topMenu.jsp?a=1,7&b=0,8,0,1" />
		<div id="maincontent">
    <div class="mainimg">
        <p><a href="#"><img src="../_img/mobile/1.jpg"/></a></p>
        <p><a href="#"><img src="../_img/mobile/2.jpg" style="margin-top:10px"/></a></p>
    </div>
    <div class="Prepaidcont">
    	<img src="../_img/mobile/Process.jpg"/>
        <div class="content">
	        <h3>确认充值信息</h3>
            	   <html:form action="/mobile/agent19pay.do?thisAction=pay">
 <html:hidden property="mobilenum" value="${agent19pay.mobilenum}"></html:hidden>
  <html:hidden property="prodid" value="${agent19pay.prodid}"></html:hidden>
   <html:hidden property="isptype" value="${agent19pay.isptype}"></html:hidden>
  	<html:hidden property="provincename" value="${agent19pay.provincename}"></html:hidden>
    <html:hidden property="price" value="${agent19pay.price}"></html:hidden>
      <html:hidden property="prodContent" value="${agent19pay.prodContent}"></html:hidden>
		  <table width="90%" border="0" cellpadding="0" cellspacing="0" class="inputlist">
              <tr>
                <td>手机号码:</td>
                <td><c:out value="${agent19pay.mobilenum}"></c:out></td>
              </tr>
               <tr>
                <td>运营商:</td>
                <td> <c:out value="${agent19pay.isptype}"></c:out></td>
              </tr>
             
              <tr>
                <td>号码所在地:</td>
                <td><c:out value="${agent19pay.provincename}"></c:out></td>
              </tr>
              <tr>
                <td>充值面额:</td>
                <td><c:out value="${agent19pay.prodContent}"></c:out></td>
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
                <div class="confoot">
                	<strong>支持范围：</strong>
                	<p>支持全国各省市（澳门、香港、台湾地区除外）</p>
                	<p>支持移动、联通、电信所有手机号码</p>
                </div>
    </div>
    <div id="askedquestions">
    <h3>常见问题：</h3>
    	<ul>
        	<li>手机充值是否需要手续费？<p>不需要，不但不需要手续费。而且还可以随时充值。</p></li>
        	<li>什么是快充？<p>快充是指您进行手机充值付款成功后话费1-10分钟到帐的充值方式。</p></li>
        	<li>什么是慢充？<p>慢充是指您进行手机充值付款成功后话费48小时内到帐的充值方式。慢充只对钱门用户开放,并且选择慢充的手机必须是与你的钱门账号邦定的手机。</p></li>
        </ul>
    </div>
</div>
		<c:import url="../_jsp/footer.jsp"></c:import>

		</div>

	</body>

</html>
