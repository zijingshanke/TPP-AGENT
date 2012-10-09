<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- jsp=checkPayPassword.jsp -->
<html>
  <head>    
   <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />

  </head>

   <body>
     
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>管理银行帐号</strong></p>
            </div>
          </div>
          <div class="retrieve_password">
            <img src="../_img/people_pi.gif" />
            <div class="retrieve_password_right">
              <p class="right_top_p">您正在进行的操作：管理银行帐号</p>
            </div>
            <div class="clear"></div>
          </div>
            <html:form action="agent/agent.do">
  <html:hidden property="thisAction" value="${param.thisAction}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table"  >
            <tr>
            <td style="width: 200px"></td>
              <td align="left" style="width: 200px"><span class="font_style6">*</span> 输入支付密码：</td>
              <td>
                <html:password style="width:180px;border:1px solid #999;" property="payPassword" value=""/>
			 </td>        
			 </tr>
            <tr>
             <td style="width: 200px"></td>
              <td colspan="2">
    <c:if test="${errorPayPassword eq 'true'}">
    	支付密码错误!请重新输入!    &nbsp;&nbsp;&nbsp;&nbsp;<a href="agent.do?thisAction=forgetPassword&type=paypassword">找回支付密码</a>
    </c:if>
    <c:if test="${errorPayPassword1 eq 'true'}">
    	您还没有通过实名认证！ &nbsp;&nbsp;&nbsp;&nbsp;<a href="agent/agent.do?thisAction=checkCertification&id=<c:out value='${agent.id }'/>">实名认证</a>
    </c:if>
    
    </td>
    </tr>
    <tr>
    <td colspan="3" style="text-align: center"><html:submit value="下一步" styleClass="btn1"/></td>
            </tr>
          </table>
          </html:form>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
  </div>
</body>
</html>
