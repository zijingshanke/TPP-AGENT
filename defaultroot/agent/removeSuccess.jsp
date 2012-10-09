<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>
       <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>撤销了实名认证</strong></p>
            </div>
          </div>
          <div class="yellowBox_Check Mobile_yellowBox">
          	<span>你已经成功的撤销了实名认证的申请，你可以<html:link page="/agent/agent.do?thisAction=checkCertification&id=${URI.agent.id}" >点击此处</html:link>从新申请实名认证....</span>
            <p style="color: #666">返回&nbsp; <a href="../agent/agent.do?thisAction=agentInfoById">我的钱门</a>&nbsp;</p>
          </div>
          

        </div>
      </div>
    </div>
    <c:import url="../_jsp/footer.jsp"></c:import>
</div>

</body>
</html>
