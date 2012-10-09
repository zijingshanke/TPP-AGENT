<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- passCertift.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />

  </head>
  
  <body style="text-align: center;">
  <div id="warp" align="left">
     <c:import url="/_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>成功提交</strong></p>
            </div>
          </div>
          <div class="success">
            <img src="../_img/OK_img.gif" class="ok_img" />
            <div class="success_right">
              <p><strong style="font-size:16px;">恭喜，您已通过钱门实名认证！</strong></p>
              <p style="border-bottom:1px solid #D2D2D2">如果日后您需要更换银行账户，可以到“我的账户-提现银行”中进行修改。点此<a href="#">查看详细操作方法</a></p>
              <ul class="success_ul">
                <li>日后您可以使用这个账户对您的其他账户进行关联，让其他账户也能享受与认证账户相同的功能。</li>
                <li>请点击返回 <a href="../agent/agent.do?thisAction=agentInfoById">我的钱门</a></li>
              </ul>
            </div>
            <div class="clear"></div>
          </div>
          
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
