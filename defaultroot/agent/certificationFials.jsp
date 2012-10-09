<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<!--certificationFials.jsp  -->
   <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />

  </head>
  
<body>
  <div id="warp">
   <c:import url="/_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>钱门实名认证</strong></p>
            </div>
            <div class="clear"></div>
          </div>
              <div class="Attention Attention1 Attention4">
                <div class="attentionTitle attentionTitle1">
                   <em>银行卡信息认证失败</em>
                   <p>您的银行卡信息认证失败，您可以<img src="../_img/renzhengshibai.gif" alt="" style="vertical-align:middle; margin:0 4px;"/><a href="agent.do?thisAction=newCertification" style="color:#005C9C;">重新发起认证</a>。</p>
                </div>
          	  </div>
              	<p><img src="../_img/daobangzhuzhongxin.gif" style="vertical-align:middle; margin:0 4px;"/>有问题？到帮助中心<a href="#" style="color:#005C9C;">搜索答案</a></p>
        </div>
      </div>
    </div>
   <c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
