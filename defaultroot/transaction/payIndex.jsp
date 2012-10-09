<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/payIndex.jsp -->

  <head>
  <title>钱门支付！--网上支付！安全放心！</title>
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	
	
	<link rel="stylesheet" type="text/css" href="../_css/global_v2.css" />
	<link rel="stylesheet" type="text/css" href="../_css/payment.css" />
	<link rel="stylesheet" type="text/css" href="../_css/tabs.css" />

	<script type='text/javascript' src='../_js/jquery.js'></script>
	
	<script type="text/javascript">
		function pay(tMenuList,sMenuList){
			var ptype = document.payForm.paytype.value;
			var num=0;

			for(var i=0;i<document.payForm.paytype.length;i++){
	            if(document.payForm.paytype[i].checked==true){
	              ptype = document.payForm.paytype[i].value;
	              num++;
	              break;
	           	}
       		} 
       		if(num==0){
       			alert("请选择付款方式!");
       			return false;
       		}

			window.location.href ="../transaction/transaction.do?thisAction=fastPay&ptype="+ptype;
		}
		
	</script>
</head>

<body>
<div id="warp">
<c:if test="${tab==1}">
<c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,0,3"/>
</c:if>
<c:if test="${tab==2}">
<c:import url="/_jsp/topMenu.jsp?a=1,1&b=0,2,1,3"/>
</c:if>
<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">

<script type="text/javascript" src="../_js/jquery.tabs.js"></script>

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		$('#container-1').tabs();
		if(!true){
    		$('.TabContent').find("input[@id='paytype2']").click(function() {
    		     var answer = $(this).parent("div").next();
    		     if (answer.is(':visible')) {
    		         answer.slideUp("fast");
    		     } else {
    		       $('.TabContent').find('.InformationMsg,.ExclaimedMsg').hide();
    		         answer.slideDown("fast");
    		     }
    		 });
			 $('.TabContent').find("input[@id='paytype1']").click(function() {
    		       $('.TabContent').find('.InformationMsg,.ExclaimedMsg').hide();
    		 });
		 }
		 else{
    		 $('.TabContent').find("input[@type='radio']").click(function() {
    		     var answer = $(this).parent("div").next();
    		     if (answer.is(':visible')) {
    		         answer.slideUp("fast");
    		     } else {
    		       $('.TabContent').find('.InformationMsg,.ExclaimedMsg').hide();
    		         answer.slideDown("fast");
    		     }
    		 });
		 }
	});
	</script>


<div id="Content" class="clearfix">
  <div id="container-1">

    <ul class="anchors clearfix">
    <c:if test="${tab==1}">
      <li><a href="#section-1" tabindex="1" title="即时到账付款"><img src="../_img/fdpay.gif" align="absmiddle">即时到账付款</a></li>
    </c:if>
    <c:if test="${tab==2}">
      <li><a href="#section-1" tabindex="1" title="担保交易付款"><img src="../_img/danpao.gif" align="absmiddle">担保交易付款</a></li>
    </c:if>
    </ul>
    <c:if test="${tab==1}">
    <div id="section-1" class="fragment">
      <div class="TabContent clearfix FastPay">
         <form name="payForm" method="post">
          <div class="left">

            <div class="trade_type"><font color="black">在钱门网站中，此图标表示您正在进行的是</font><a href="javascript:void(0)">即时到账交易</a></div>
          </div>
          <div class="right">
            <h2><img src="../_img/Fastpay_title.gif"></h2>
            <div class="FastPay_content" style="padding-top:1px;*padding-top:8px;margin-top:0">
              <p style="border-bottom:1px solid #FFF;"> 此功能 <strong class="R">不同于</strong> <a href="../transaction/transaction.do?thisAction=payIndex&tab=2" class="M">担保交易</a> ，<br />

                付款后您的钱将 <strong>直接</strong> 进入对方账户。 <br />
                我们推荐您在 <strong>亲朋好友间</strong> 使用此功能。</p>

               <c:if test="${URI.agent.validCertStatus==0}">
               		您是数字证书用户，本台电脑上还未导入证书，现在只能查询账户。
               		<div align="right"><img src="../_img/forward.gif"/>  <a href="javascript:void(0)">立即导入证书</a> </div>
               </c:if>
              

               <c:if test="${URI.agent.validCertStatus==1}">
                            您想：
              <div>
                <input type="radio" name="paytype" id="paytype1" value="0" style="*font-size:14px;*height:23px;*width:25px;">

                <label for="paytype1" style="font-size:16px;">直接给“亲朋好友”付钱</label>
              </div>
              <div class="InformationMsg hidden" style="width:85%;margin:5px 0px 5px 32px;" id="paytype1_info">
    				钱会直接付到收款人的钱门帐号上
    			</div>
              <div style="padding-top:10px;">
                <input type="radio" name="paytype" id="paytype2" value="1" style="*font-size:14px;*height:23px;*width:25px;">
                <label for="paytype2" style="font-size:16px;">向陌生卖家付款<strong> （不推荐）</strong></label>
              </div>
              <div class="ExclaimedMsg hidden" style="width:85%;margin:5px 0px 5px 32px;">此功能<strong class="R">钱立即给卖家</strong>，推荐您使用“<a href="../transaction/transaction.do?thisAction=payIndex&tab=2">担保交易</a>”。</div>
              <div style="padding-top:8px;padding-left:5px;text-align:left;">

                <button class="btn1" type="button" onclick="pay('<c:out value="${tabMenuList}"/>','<c:out value="${subMenuList}"/>')">下一步</button>
              </div>
              </c:if>
              <div id="Error" style="display:none;width:80%;margin:5px 0px 5px 32px;line-height:20px;font-size:12px;">请在上面选择使用的即时到账付款类型！</div>
              </div>
          </div>
        </form>
      </div>
    </div>
  </c:if>
  <c:if test="${tab==2}">
    <div id="section-2" class="fragment">
      <div class="TabContent TradePay">
        <div class="left">
          <div class="trade_type"><font color="black">在钱门网站中，此图标表示您正在进行的是</font><a href="javascript:void(0)">担保交易</a></div>
        </div>
        <div class="right">
          <h2><img src="../_img/Stanardpay_title.gif"></h2>
          <div class="FastPay_content" style="padding:10px 19px;margin-top:0">

            <p> 担保交易 <strong>保障货款安全</strong> ，购物真放心！
            <div style="padding:8px;border:1px solid #FC3;background:#FFF4C2;"> 1、买家确认 <strong>付款给钱门</strong><br />
              2、钱门通知 <strong>卖家发货</strong><br />
              3、买家收到货物满意后通知钱门 <strong>付款给卖家</strong><br />

            </div>
            </p>
            您可以直接上<a href="http://www.fdays.com/" target="_blank">品尚旅游物流平台</a>购物。<br />
            <br />
            如果您想与卖家建立担保交易，请联系卖家，让他登陆钱门网站，点击“我要收款”-“担保交易收款”创建交易，交易建立后您再登陆您的钱门账户，在“交易管理”中找到所对应的担保交易付款即可。</div>
        </div>
        <div class="HackBox"></div>

      </div>
    </div>
    </c:if>
  </div>
</div>

</div>
</div>
</div>
<div>
<c:import url="/_jsp/footer.jsp"/>
</body>
</html>
