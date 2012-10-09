<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- securityCert.jsp -->
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
   
<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
    <div id="main">
      <div id="left_box">
        <div class="leftbox_top">
          <div class="leftbox_bottom">
            <div class="leftbox_count">
              <p class="leftbox_count_p"><a href="#">e数字证书介绍</a></p>
              <p><a href="#">操作指南</a></p>
              <p><a href="#">常见问题</a></p>
            </div>
          </div>
        </div>
        <div class="leftbox_top1">
          <div class="leftbox_bottom1">
            <div class="leftbox_count1">
              <p>安全公告</p>
              <ul>
                <li><a href="#">四步打造金牌安全钱门</a></li>
                <li><a href="#">防骗十大绝招-招招致命</a></li>
                <li><a href="#">防病毒木马，数字证书有绝招</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      
      <div id="right_box">
      	<div class="Digital_top">
            <div class="Digital_bottom">
            	<div style="background: url(../_img/img_zhengshu.gif) no-repeat 600px 10px">
                    <label>什么是数字证书</label>
                    <p>数字证书是由权威公正的第三方机构，即CA中心签发的证书。</p>
                    <p>它以数字证书为核心的加密技术可以对网络上传输的信息进行加密和解密、数字签名和签名验证，确保网上传递信息的机密性、完整性。</p>
                    <p class="p1">使用了数字证书，即使您发送的信息在网上被他人截获，甚至您丢失了个人的账户、密码等信息，仍可以保证您的账户、资金安全。</p>
                    <span class="simplyBtn_1"><input type="button" class="buttom_middle" value="请先申请实名认证" /></span>
                </div>
            </div>     	
        </div>
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
            	<label>钱门数字证书的三大特点</label>
                <div class="Features_1">
                	为了避免传统数字证书方案中，由于使用不当造成的证书丢失等安全隐患，钱门创造性的推出双证书解决方案： 钱门会员在申请数字证书时，将同时获得两张证书，一张用于验证钱门账户，另一张用于验证会员当前所使用的计算机。<p>&nbsp;</p><p>第二张证书不能备份，会员必须为每一台计算机重新申请一张。这样即使会员的数字证书被他人非法窃取，仍可保证其账户不会受到损失。</p>
                </div>
                <div class="Features_1 Features_2">
                	钱门数字证书根据用户身份给予相应的网络资源访问权限。<p>&nbsp;</p><p>申请使用数字证书后，如果在其他电脑登录钱门账户，没有导入数字证书备份的情况下，只能查询账户，不能进行任何操作，这样就相当于您拥有了类似“钥匙”一样的数字凭证，增强账户使用安全。</p>
                </div>
                <div class="Features_1 Features_3">
                	1.即时申请、即时开通、即时使用。
                    <p>2.量身定制多种途径维护数字证书，例如通过短信，安全问题等。</p>
                    <p>3.不需要使用者掌握任何数字证书相关知识，也能轻松掌握。</p>
                </div>
            </div>
          </div>
        </div>
        
        
      </div>
      <div class="clear"></div>
    </div>
    <c:import url="../_jsp/footer.jsp"></c:import>
</div>

</body>
</html>
