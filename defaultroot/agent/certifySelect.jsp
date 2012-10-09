<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- certifySelect.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/jquery-1.3.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$('.at_id_card2').click(function(){
		$('.at_id_card2').addClass('at_id_card');
		$('.at_id_card1,.at_id_card3,.at_id_card4').removeClass('at_id_card');
		$('.id_card_count2').show();
		$('.id_card_count1,.id_card_count3,.id_card_count4').hide();
	})
	$('.at_id_card1').click(function(){
		$('.at_id_card1').addClass('at_id_card');
		$('.at_id_card2,.at_id_card3,.at_id_card4').removeClass('at_id_card');
		$('.id_card_count1').show();
		$('.id_card_count2,.id_card_count3,.id_card_count4').hide();
	})
	$('.at_id_card3').click(function(){
		$('.at_id_card3').addClass('at_id_card');
		$('.at_id_card1,.at_id_card2,.at_id_card4').removeClass('at_id_card');
		$('.id_card_count3').show();
		$('.id_card_count1,.id_card_count2,.id_card_count4').hide();
	})
	$('.at_id_card4').click(function(){
		$('.at_id_card4').addClass('at_id_card');
		$('.at_id_card1,.at_id_card3,.at_id_card2').removeClass('at_id_card');
		$('.id_card_count4').show();
		$('.id_card_count1,.id_card_count3,.id_card_count2').hide();
	})
})
</script>
</head>
<body style="text-align: center;">
  <div id="warp" align="left">
 <c:import url="/_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <h1>选择您身份证件所在的地区</h1>
          <div class="id_card_address">
            <div class="id_card_menu">
              <a href="#" class="at_id_card at_id_card1">中国大陆用户</a>
              <a href="#" class="at_id_card2">港澳用户</a>
              <a href="#" class="at_id_card3">台湾用户</a>
              <a href="#" class="at_id_card4">外籍用户</a>
              <div class="clear"></div>
            </div>
            <div class="id_card_count id_card_count1">
              <p><strong>请确保您拥有以下其中一家银行的银行账户：</strong></p>
              <p>中国工商银行、招商银行、中国建设银行、中国农业银行、中国民生银行、兴业银行、上海浦东发展银行、交通银行</p>
              <p style="color:#666px;">钱门公司会向您提交的银行卡内打入一元以下的金额，请您在二天后查看银行账户所收到的准确金额，并登录钱门确认。</p>
              <p style="margin:20px 0 0 10px;"><a href="mainlandPersonBaseInput.jsp" class="a_btn2" style="margin-left:0;">立即申请</a></p>
            </div>
            <div class="id_card_count id_card_count2">
              <ul>
                <li>实名认证通过后将不能取消。</li>
                <li>只限于年满18周岁的港澳地区会员。</li>
                <li>港澳会员的实名认证服务由钱门提供，点击“立即申请”后，您即可进入钱门的申请页面。</li>
              </ul>
              <p style="margin:20px 0 0 10px;"><a href="#" class="a_btn2" style="margin-left:0;">立即申请</a></p>
            </div>
            <div class="id_card_count id_card_count3">
              <ul>
                <li>实名认证通过后将不能取消。</li>
                <li>只限于年满18周岁的台湾地区会员，需提供：您本人的台胞证和大陆银行账户、担保人有效证件、担保人亲笔签名的担保函。</li>
                <li><strong style="color:#C00;">请确保您拥有以下其中一家银行的银行账户：</strong></li>
                <li style="list-style:none;">中国工商银行、招商银行、中国建设银行、中国农业银行、中国民生银行、兴业银行、上海浦东发展银行、交通银行</li>
                <li>请确保所提交证件的有效期在3个月以上。</li>
              </ul>
              <p style="margin:20px 0 0 10px;"><a href="#" class="a_btn2" style="margin-left:0;">立即申请</a></p>
            </div>
            <div class="id_card_count id_card_count4">
              <ul>
                <li>实名认证通过后将不能取消。</li>
                <li>只限于年满18周岁的外籍会员，需提供：您本人的护照和大陆银行账户、担保人有效证件、担保人亲笔签名的担保函。</li>
                <li><strong style="color:#C00;">请确保您拥有以下其中一家银行的银行账户：</strong></li>
                <li style="list-style:none;">中国工商银行、招商银行、中国建设银行、中国农业银行、中国民生银行、兴业银行、上海浦东发展银行、交通银行</li>
                <li>请确保所提交证件的有效期在3个月以上。</li>
              </ul>
              <p style="margin:20px 0 0 10px;"><a href="#" class="a_btn2" style="margin-left:0;">立即申请</a></p>
            </div>
            <p class="p_style1">实名认证通过后将不能取消，通过认证的信息将作为您在钱门的真实身份信息。</p>
            <p class="p_style1">请认真填写认证信息：包括个人信息和银行账户信息，对于非大陆公民，还需提供担保人信息。</p>
          </div>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
