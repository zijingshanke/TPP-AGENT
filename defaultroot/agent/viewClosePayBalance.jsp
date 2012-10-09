<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- viewClosePayBalance.jsp -->
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
         <html:form action="/agent/agent.do?thisAction=closePayBalance">
         <html:hidden property="mobilePhone" value="${URI.agent.mobilePhone}"></html:hidden>
         <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
              <table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
                  <tr>
                    <th width="73%"><div>功能描述</div></th>
                    <th width="11%"><div>当前状态</div></th>
                    <th width="16%"><div>操作</div></th>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">您关闭“钱门余额支付功能”后，您的钱门账户余额就不能用于支付，这样可以确保您账户余额的安全。</td>
                    <td>已开启</td>
                    <td><span class="simplyBtn_1" style="margin:0;"><input type="submit" class="buttom_middle" value="关闭余额支付功能" /></span></td>
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
