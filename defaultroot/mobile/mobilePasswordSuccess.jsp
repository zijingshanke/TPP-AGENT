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
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />	
	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>手机动态口令</strong></p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="chenggongshizhi">
        <div class="chenggongshizhiTitle">
            <em>您已经成功开通了“手机动态口令”服务。</em>
        </div>
        <ul class="chenggongshizhiList">
            <li>在您付款、确认收货、修改账户信息时，需要您通过手机接收“动态口令”，并将“动态口令”输入到输入框中。</li>
            <li>多操作一步，却给您的钱门账户双倍保护。保障您的大额交易和敏感账户信息的安全。</li>
            <li><a href="../agent/agent.do?thisAction=viewMobileCenter">返回手机动态口令首页</a></li>
        </ul>
    </div>
   <c:import url="../_jsp/footer.jsp"></c:import>
</div>

</body>
</html>
