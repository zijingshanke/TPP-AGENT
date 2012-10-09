<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/transactionPaymentConfirm.jsp -->

	<head>
<title>钱门支付！--网上支付！安全放心！</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		
	</head>

	<body>
		<div id="warp">
		<c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,0,3" />
		<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>付款成功 </strong></p>
            </div>
          </div>
          
           <div class="paymentSuccessful">
	          	<div class="paymentSuccessfulTitle">
	          		<c:if test="${transaction.type!=3}">
	          			<em><strong>你已经成功向卖家付款  <fmt:formatNumber pattern="0.00" value="${amount}"/>  元! </strong></em>
	          			<ul>	
						<li><html:link page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">查看我的账户余额</html:link></li>
						<li><html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">给我的账户充值</html:link></li>
					</ul>
	          		</c:if>
	          		<c:if test="${transaction.type==3}">
	          			<c:if test="${agreePayment!='yes'}">
			            	<em><strong>钱门已收到您的付款 <fmt:formatNumber pattern="0.00" value="${amount}"/>  元! </strong></em>
			                <p>我们已经通知卖家 <c:out value="${toAgentLoginName}"/> 发货。</p>
			                <ul>	
								<li><html:link page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">查看我的账户余额</html:link></li>
								<li><html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">给我的账户充值</html:link></li>
							</ul>							
							<ul>
								<li>您收到货物时务必确认货物完好和一致性，如发现表面不一致，应当直接退货或者要求在签收单上加注详细情况并让承运人签字确认。<br /></li>
								<li>如果卖家已经发货,请登录钱门，在“交易管理”中进行“确认收货”</li>
							</ul>
						</c:if>
						<c:if test="${agreePayment=='yes'}">
			            	<em><strong>钱门已经把你付的款  <fmt:formatNumber pattern="0.00" value="${amount}"/>  元 打到对方的钱门帐号上</strong></em>
			                <ul>	
								<li><html:link page="/agent/agent.do?thisAction=agentInfoById&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6">查看我的账户余额</html:link></li>
								<li><html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">给我的账户充值</html:link></li>
							</ul>							
						</c:if>
					</c:if>
	            </div>
	        </div>

        </div>
      </div>
    </div>
 <c:import url="/_jsp/footer.jsp"/>
</div>
  </body>
</html>

