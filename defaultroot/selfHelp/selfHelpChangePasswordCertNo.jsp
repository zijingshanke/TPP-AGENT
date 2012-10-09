<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- selfHelpChangePasswordCertNo.jsp -->
<html>
  <head>
   <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  </head> 
  <body>
  <div id="warp">
      <c:import url="../selfHelp/head.jsp"></c:import>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>用安全保护问题找回</strong></p>
            </div>
          </div>
          <div class="pay_way">
            <img src="../_img/pay_pic.gif" />
          </div>
         
          <div class="Wronning Wronning1">
                <div class="wronningTitle">
                    <c:if test="${error eq 'errorCertNo'}">
                    <em class="tipsPayway" style="color: red;">您输入的证件号码有误,请认真填写!</em>
                    </c:if>
                    <c:if test="${error eq '' or error eq null}">
                    	  <em class="tipsPayway">请正确填写您的身份证号码</em>
                    </c:if>
                </div>
          </div>
          <html:form action="/agent/agent.do?thisAction=validateCertNo">
	 <input type="hidden" name="loginName" value="<c:out value="${loginName}"></c:out>"/>
	  <input type="hidden" name="type" value="<c:out value="${type}"></c:out>"/>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
            <tr>
              <td class="right_td">您的钱门账户：</td>
              <td><span class="font_style2"><c:out value="${loginName}"></c:out></span></td>
            </tr>
			<tr>
              <td class="right_td">您的证件号：</td>
              <td><input type="text" name="certNo" value="" /><div>
	 	
    </div></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td valign="top" class="Request">请输入您注册时所填写的证件号码，如果您已经通过“钱门实名认证”请输入认证时的证件号码。</td>
            </tr>
            <tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="submit" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" /></span></td>
            </tr>
          </table>
          </html:form>
          </form>
          <div class="goBack">
          	<img src="../_img/goBack.gif" /><html:link page="/agent/agent.do?thisAction=jumpTermFashion&type=${type}&loginName=${loginName}">返回选择其他方式</html:link>
          </div>
        </div>
      </div>
    </div>
    <div id="footer">
    CopyRight 2005-2008, fdays.com .All Rights Reserved 增值电信业务经营许可证 粤B2-20040561
  </div>
  </div>
</body>
</html>
