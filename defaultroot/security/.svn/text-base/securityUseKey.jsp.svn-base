<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- securityUseKey.jsp -->
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
<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,2,2" />
    <div id="main">
      <div id="left_box">
        <div class="leftbox_top">
          <div class="leftbox_bottom">
            <div class="leftbox_count">
              <p class="leftbox_count_p"><a href="#">支付盾介绍</a></p>
             <!-- 
              <p><a href="#">操作指南</a></p>
              <p><a href="#">常见问题</a></p>
               -->
            </div>
          </div>
        </div>
        <!-- 
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
         -->
      </div>
      
      <div id="right_box">
      	<div class="Digital_top">
            <div class="Digital_bottom">
            	<div style="background: url(../_img/img_Shield.gif) no-repeat 630px 10px">
                    <label>什么是支付盾</label>
                    <p>支付盾是钱门公司推出的安全解决方案。支付盾是联合第三方权威机构一起推出的安全产品。</p>
                    <p>它是具有电子签名和数字认证的工具，保证了您在网上信息传递时的保密性、唯一性、真实性和完整性。</p>
                    <p>支付盾酷似一面盾牌，时刻保护着您在钱门上操作的资金和账户安全。</p>
                    <p class="p1">支付盾费用 58 元，目前推广期，免国内快递费。</p>
                    <c:if test="${URI.agent.status!=3}">
                    <span class="simplyBtn_1"><input type="button"  onclick="checkCertification(<c:out value="${URI.agent.id}"></c:out>);" class="buttom_middle" value="申请实名认证" /></span>
                	</c:if>
                	 <c:if test="${URI.agent.status==3}">
                    <span class="simplyBtn_1"><input type="button"  class="buttom_middle" value="申请支付盾" /></span>
                	</c:if>
                </div>
                <script>
	                	function checkCertification(id){
	                		window.location.href="<%=request.getContextPath()%>/agent/agent.do?thisAction=checkCertification&id="+id;
	                	}
	                </script>
            </div>     	
        </div>
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
            	<label>支付盾的三大特点</label>
                <div class="Features_1">
                	您在钱门网站处理任何资金业务时，不需要担心木马、钓鱼网站、黑客等各种安全风险，支付盾将可以保障您在网上交易资金和您的钱门账户安全。<p>&nbsp;</p><p>支付盾是一个类似于U盘的实体安全工具，它内置的微型智能卡处理器能阻挡各种的风险，让您的账户始终处于安全的环境下。</p>
                </div>
                <div class="Features_1 Features_2">
                	支付盾是和您的钱门账户对应绑定的关系，从而保障了您的钱门资金安全。<p>&nbsp;</p><p>申请使用支付盾后，如果在没有插入支付盾的情况下登录钱门，只能进行查询账户操作，而不能进行其他任何对于资金变
动的操作。只有插入匹配账户的支付盾才能进行操作。这样就相当于支付盾就是您拥有的一把“钥匙”，增强您账户使用安全。</p>
                </div>
                <div class="Features_1 Features_3">
                	1.可以在任何的电脑上进行操作，免除了数字证书的备份烦恼。 
                    <p>2.拥有了支付盾，您将可以拥有更高的支付额度来进行资金交易了。</p>
                    <p>3.不需要使用者掌握任何数字证书相关知识，也能轻松掌握。增强您账户使用安全。</p>
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
