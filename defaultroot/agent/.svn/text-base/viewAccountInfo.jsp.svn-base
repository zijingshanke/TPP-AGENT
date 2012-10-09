<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- viewAccountInfo.jsp -->
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    <link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
      
  <script>
   function toUpdate(){
   		document.forms[0].action="agent.do?thisAction=updateAccountInfo";
   		document.forms[0].submit();
   }
   function acknowledgement(){
		document.forms[0].submit();
	}
  </script>
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>
    	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>确认银行账户信息</strong></p>
            </div>
          </div>
          <html:form action="agent/agent.do?thisAction=addAccount">
       <input type="hidden" name="citysId" value="<c:out value="${agent.city.id}"></c:out>"/>
  	  <input type="hidden" name="banksId" value="<c:out value="${agent.bankId}"></c:out>"/>
  	  	  <input type="hidden" name="provincesId" value="<c:out value="${agent.province.id}"></c:out>"/>
  	   <html:hidden property="name" value="${URI.agent.name}"></html:hidden>
  	    <html:hidden property="companyName" value="${URI.agent.companyName}"></html:hidden>
  	     <html:hidden property="account.cardNo" value="${agent.account.cardNo}"></html:hidden>
  	       	<html:hidden property="registerType" value="${URI.agent.registerType}"></html:hidden>
      <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
      <html:hidden property="status" value="${URI.agent.status}"></html:hidden>
       <html:hidden property="account.accountAddress" value="${agent.account.accountAddress}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="80%" class="information_table">
            <c:if test="${agent.registerType==1}">
            <tr>
              <td class="right_td">银行开户姓名：</td>
              <td><c:out value="${URI.agent.name}"></c:out></td>
            </tr>
           </c:if>
           <c:if test="${agent.registerType==1}">
            <tr>
              <td class="right_td">银行开户姓名：</td>
              <td><c:out value="${URI.agent.companyName}"></c:out></td>
            </tr>
           </c:if>
            <!-- 
             <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span">如您没有合适的银行账户，<a href="javascript:editPerson();">修改身份信息</a></td>
            </tr>
            <tr>
              <td class="right_td">&nbsp;</td>
              <td><p class="td_p">提醒：必须使用以<c:out value="${agent.realName}"></c:out>未开户的银行账户进行认证。</p></td>
            </tr>
			  -->
            <tr>
              <td class="right_td">开户银行所在省市：</td>
              <td><c:out value="${agent.province.cname}"></c:out>-<c:out value="${agent.city.cname}"></c:out></td>
            </tr>
              <tr>
              <td class="right_td">开户银行名称：</td>
              <td>
              <c:out value="${bankName}"></c:out>
              </td>
            </tr>
             <tr>
              <td class="right_td">开户银行网点：</td>
              <td>
              <c:out value="${agent.account.accountAddress}"></c:out>
              </td>
            </tr>
            <tr>
              <td class="right_td">个人银行账户：</td>
              <td><c:out value="${agent.account.cardNo}"></c:out></td>
            </tr>
            
             <tr>
              <td style="text-align:center;"><a href="javascript:acknowledgement()" class="a_btn1">提交</a></td>
              <td>
              <a href="javascript:toUpdate();" class="a_btn1">返回修改</a></td>
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
