<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- viewStartupPayBalance.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />

  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>  
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>余额支付功能管理</strong></p>
            </div>
          </div>
          <html:form action="/agent/agent.do?thisAction=startupPayBalance">
         <html:hidden property="mobilePhone" value="${agent.mobilePhone}"></html:hidden>
         
              <table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
                  <tr>
                    <th width="73%"><div>功能描述</div></th>
                    <th width="8%"><div>当前状态</div></th>
                    <th width="19%"><div>操作</div></th>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">您开启“钱门余额支付功能”后，钱门账户中的余额就可以用于支付。请妥善保管好您的账户和密码，不要在网吧、图书馆等公用网络上使用余额支付，防止他人安装监测程序或木马程序窃取账号和密码。同时，我们建议您立即申请数字证书，以便更安全地管理您的账户和资金。 </td>
                    <td>已关闭</td>
                    <td>
                    <c:if test="${agent.mobileBindStatus==0}">
                    	开启钱门账户余额支付功能前，请先开启免费短信提醒功能。
                    <a href="../agent/agent.do?thisAction=viewMobileBinding">点击申请</a>
                    </c:if>
                    <c:if test="${agent.mobileBindStatus==1}">
                    	<span class="simplyBtn_1" style="margin:0;"><input type="submit" class="buttom_middle" value="开启余额支付功能" /></span>
                        <p>我们将把确认信息发送到您的手机上。</p>
                        <p>如果手机号码有误，请联系客服修改:0756-8801800。</p>
                    </c:if></td>
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
