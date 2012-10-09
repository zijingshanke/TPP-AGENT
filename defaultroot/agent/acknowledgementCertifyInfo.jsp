<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!-- jsp:acknowledgementCrtifyInfo.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--  jsp:acknowledgementCertifyInfo.jsp -->
<html>
  <head>
   <title>钱门支付！--网上支付！安全! 放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  <script type="text/javascript">
  
  	function toUpdate(){
  		document.forms[0].action="agent.do?thisAction=updatePersonInfo";
  		document.forms[0].submit();
  	}
  
  </script>
  </head>
  
  <body style="text-align: center;">
   <html:form action="/agent/agent.do?thisAction=fillinPersonInfo">
  	  <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
  	  <html:hidden property="realName" value="${agent.name}"></html:hidden>
  	    <html:hidden property="name" value="${agent.name}"></html:hidden>
  	  <html:hidden property="certNum" value="${agent.certNum}"></html:hidden>
  	  <html:hidden property="loginName" value="${agent.loginName}"></html:hidden>  
  	  <html:hidden property="address" value="${agent.address}"></html:hidden>
  	   <html:hidden property="telephone" value="${agent.telephone}"></html:hidden>
  	   <html:hidden property="mobile" value="${agent.mobile}"></html:hidden>      
  	  <html:hidden property="account.cardNo" value="${agent.account.cardNo}"></html:hidden>     
  	  <html:hidden property="account.accountAddress" value="${agent.account.accountAddress}"></html:hidden>  
  	  <input type="hidden" name="provincesId" value="<c:out value="${agent.province.id}"></c:out>"/>
  	  <input type="hidden" name="citysId" value="<c:out value="${agent.city.id}"></c:out>"/>
  	  <input type="hidden" name="banksId" value="<c:out value="${agent.bankId}"></c:out>"/>
  	   <input type="hidden" name="bankId" value="<c:out value="${agent.bankId}"></c:out>"/>
  	   <html:hidden property="companyName" value="${agent.companyName}"></html:hidden>
  	    <html:hidden property="registerType" value="${agent.registerType}"></html:hidden>
  <div id="warp" align="left">
    <c:import url="/_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>您的个人信息</strong>　　日后您可以通过正确提供下列认证信息，来证明您的账户身份，请确保信息的正确并牢记这些信息！</p>
            </div>
          </div>
       
          <table cellpadding="0" cellspacing="0" width="80%" class="information_table">
            <tr>
              <td class="right_td">钱门账号:</td>
              <td style="color:#005C9C"><c:out value="${agent.loginName}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">真实姓名：</td>
              <td><c:out value="${agent.name}"></c:out></td>
            </tr>
             <c:if test="${agent.registerType==1}">
            	<tr>
              <td class="right_td">企业名称：</td>
              <td> <c:out value="${agent.companyName}"></c:out></td>
            </tr>
            </c:if>
            <tr>
              <td class="right_td">证件号码：</td>
              <td><c:out value="${agent.certNum}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">详细地址：</td>
              <td><c:out value="${agent.address}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">固定电话：</td>
              <td><c:out value="${agent.telephone}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">手机号码：</td>
              <td><c:out value="${agent.mobile}"></c:out></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>您的银行账户信息</strong></p>
            </div>
          </div>
          <table cellpadding="0" cellspacing="0" width="80%" class="information_table">
            <tr>
              <td class="right_td">银行开户姓名：</td>             
               <c:if test="${agent.registerType==0}">
              <td><c:out value="${agent.name}"></c:out></td>
              </c:if>
              <c:if test="${agent.registerType==1}">
              <td><c:out value="${agent.companyName}"></c:out></td>
              </c:if>
            </tr>
            <tr>
              <td class="right_td">开户银行名称：</td>
              <td><c:out value="${agent.account.bankName}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">开户银行所在省市：</td>
              <td><c:out value="${agent.province.cname}"></c:out>-<c:out value="${agent.city.cname}"></c:out></td>
            </tr>
             <tr>
              <td class="right_td">开户银行网点：</td>
              <td><c:out value="${agent.account.accountAddress}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td">个人银行账户：</td>
              <td><c:out value="${agent.account.cardNo}"></c:out></td>
            </tr>
            <tr>
              <td style="text-align:center;"> <input type="submit" class="btn1" value="提 交" /></td>
              <td><input type="button" class="btn1" value="返回修改" onclick="toUpdate();"/> </td>
            </tr>
            <tr><td>&nbsp;</td>
            </tr>
          </table>
        
        </div>
      </div>  </div>
  <c:import url="../_jsp/footer.jsp"></c:import>  
  </div>

    </html:form>
</body>
</html>
