<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!--JSP页面: transaction/failCharge.jsp -->
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css2/my_account.css" type="text/css" />
  </head>
  
  <body>
   <div id="warp">
		<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,2,6" />
		<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>如何开通网上银行</strong></p>
            </div>
          </div>
          
           <div class="paymentSuccessful" style="height: auto;">
	          	<div class="paymentSuccessfulTitle"  style="height: auto;">
	          	
	          	<div class="Content FlowContent"><h3 align="left">操作没有成功？</h3>
<br>
请先确认您的银行卡是否已经开通网上银行功能，<a href="#" class="B" target="_blank">如何开通网上银行</a>

<dt><a href="#" target="_blank">银行已经扣款，但钱门账户余额没有增加？ </a></dt>
<dt><a href="#" target="_blank">银行页面打不开，怎么办？</a></dt>	
<div>
<p><span style="font-weight:bold;">问题没解决?<a href="#"><img src="../_img/select_button.gif" border="0" class="vcenter"></a></span></p>
<p>
<span style="font-weight:bold;">问题已经解决?</span><a href="javascript:history.go(-2);" >返回重新付款</a><img src="../_img/forward.gif" border="0" class="vcenter">
</p>
</div>
<div class="FullScreen">
<p><img src="../_img/back.gif"  class="vcenter" alt=">>" />  <a href="../agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">返回账户余额查询</a></p> 
</div>
</div>
	          	</div>
				</div>
				</div>
				</div>
				</div>		
				<c:import url="/_jsp/footer.jsp" />
				</div>
			</div>
  </body>
</html>
