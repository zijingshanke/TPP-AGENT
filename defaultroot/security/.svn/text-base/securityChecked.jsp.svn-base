<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- securityChecked.jsp -->
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
   <c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,3,2" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><span style="float:right;">上次登录时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${viewAgentInfo.currentLoginDate}"/></span><strong>您的钱门账户：<c:out value="${URI.agent.loginName}"></c:out></strong></p>
            </div>
          </div>
          <form action="" method="post">
              <table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
                  <tr>
                    <th><div>项目</div></th>
                    <th><div>说明</div></th>
                    <th><div>建议开通设置</div></th>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">登录密码</td>
                    <td>最好使用一个包含数字和字母，并且超过6位字符以上的密码。</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td style="padding-top:10px; padding-bottom:10px;">支付密码</td>
                    <td>最好使用一个包含数字和字母，并且超过6位字符以上的密码。支付密码必须与登录密码不一样。</td>
                    <td style="color:#005B9B"><a href="../agent/agent.do?thisAction=forgetPassword&type=paypassword">找回支付密码</a></td>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">安全保护问题</td>
                    <td>设了安全保护问题，就不用担心别人修改您的密码了。</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">短信提醒（免费）</td>
                    <td>账户邮箱、密码发生变更或有大额交易时，钱门将发送短信提醒您。（免费）</td>
                    <td style="color:#005B9B">
                    <c:if test="${URI.agent.mobileBindStatus==1}">
                    <a href="../agent/agent.do?thisAction=unbindingMobile">关闭</a></c:if>
                    <c:if test="${URI.agent.mobileBindStatus==0}">
                    <a href="../agent/agent.do?thisAction=viewMobileBinding">申请开通</a>
                    </c:if>
                    </td>
                  </tr>
                  <tr>
                    <td style="padding-top:10px; padding-bottom:10px;">钱门实名认证</td>
                    <td>通过钱门实名认证您就可以在网上开店，收款了。</td>
                    <td style="color:#005B9B">
                    <c:if test="${URI.agent.status!=3}">
                    <a href="../agent/agent.do?thisAction=checkCertification&id=<c:out value="${URI.agent.id}"></c:out>">申请实名认证</a>
                    </c:if></td>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">钱门数字证书</td>
                    <td>安装了数字证书，即使密码被盗，也不用担心账户安全。<br /><font color="#CD0B0B">申请钱门数字证书前，必须先开通钱门实名认证。</font></td>
                    <td style="color:#005B9B"><a href="">等待更新</a></td>
                  </tr>
                  <tr style="background:#F5F5F5;">
                    <td style="padding-top:10px; padding-bottom:10px;">支付盾</td>
                    <td>支付盾是数字证书的升级，随时随地更安全。</td>
                    <td style="color:#005B9B"><a href="">等待更新</a></td>
                  </tr>
                </table>
            </form>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
