<%@ page language="java"
	import="java.util.*,com.fordays.fdpay.right.UserRightInfo,com.fordays.fdpay.agent.Agent"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
			UserRightInfo uri = (UserRightInfo) request.getSession()
			.getAttribute("URI");
	Agent agent = new Agent();
	if (uri != null) {
		agent = uri.getAgent();
	}
%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/_css/qianmen.css" type="text/css" />

<div id="head">

	<span style="float: right; padding-top: 40px;"><img src="../_img/phone.gif"/>&nbsp;：<i
		style="font-size: 20px; color: red;">0756-8801800</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a
		href="../agent/agent.do?thisAction=viewMyAgent&showTabMenuIdList=1,6&showSubMenuIdList=0,7,5,6"
		class="head_a"> 您好， <c:if test="${URI.agent.registerType==0}">
				<c:out value="${URI.agent.name}"></c:out>
			</c:if> <c:if test="${URI.agent.registerType==1}">
				<c:if test="${URI.agent.companyName!=null}">
					<c:out value="${URI.agent.companyName}"></c:out>
				</c:if>
				<c:if test="${URI.agent.companyName==null}">
					<c:out value="${URI.agent.name}"></c:out>
				</c:if>
				<c:if test="${URI.agentUser!=null}">
            		- <c:out value="${URI.agentUser.name}"></c:out>
				</c:if>
			</c:if> <a href="<%=request.getContextPath()%>/help/index.jsp"
			style="border: none;">帮助中心</a> </a> [<a
		href="<%=basePath%>agent/agent.do?thisAction=removeSession"
		style="border: none;">退出</a>] <html:link
			page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">立即充值</html:link>
	</span>
</div>
<html:link page="/index.jsp">
	<img src="<%=request.getContextPath()%>/_img/logo.jpg" border="0"
		style="border: 0px; padding: 0 0 5px 15px;" />
</html:link>
<div id="menu">
	<div id="menu_right">
		<div id="menu_mid">
			<div class="menu_top">
				<ul>
					<li id="Showtab_10" onclick="Show_TabMenu(1,0);"
						class="at_menu_top">

						<c:if test="${URI.agent.validCertStatus==0}">
							<html:link page="/page/accessDeniedCert.jsp">我要收款</html:link>
						</c:if>

						<c:if test="${URI.agent.validCertStatus==1}">
							<html:link
								page="/transaction/transaction.do?thisAction=transactionBySeller&paytype=3">我要收款</html:link>
						</c:if>
					</li>

					<li id="Showtab_11" onclick="Show_TabMenu(1,1);">
						<html:link
							page="/transaction/transaction.do?thisAction=payIndex&tab=1">我要付款</html:link>
					</li>

					<li id="Showtab_12" onclick="Show_TabMenu(1,2);">
						<html:link page="/shopping/building.jsp">我要缴费</html:link>
					</li>
					<c:if test="${URI.agentUser==null}">
						<li id="Showtab_13" onclick="Show_TabMenu(1,3);">
							<html:link
								page="/transaction/transactionlist.do?thisAction=listTransactions&selectDealDate=1&flag=1">交易管理</html:link>
						</li>
					</c:if>
					<c:if test="${URI.agentUser!=null}">
						<li style="display: none;" id="Showtab_13"
							onclick="Show_TabMenu(1,3);">
						</li>
					</c:if>

					<li id="Showtab_14" onclick="Show_TabMenu(1,4);">
						<html:link page="/security/security.do?thisAction=securityCenter">安全中心</html:link>
					</li>
					<li id="Showtab_15" onclick="Show_TabMenu(1,5);">
						<html:link page="/mobile/agent19pay.do?thisAction=viewMobilePay">商家服务</html:link>
					</li>
					<c:if test="${URI.agent.registerType==0}">
						<li id="Showtab_16" onclick="Show_TabMenu(1,6);">
							<html:link page="/agent/agent.do?thisAction=agentInfoById">我的钱门</html:link>
						</li>
					</c:if>
					<c:if test="${URI.agent.registerType==1}">
						<li id="Showtab_16" onclick="Show_TabMenu(1,6);">
							<html:link page="/agent/agent.do?thisAction=agentInfoById">商户后台</html:link>
						</li>
					</c:if>
					<li id="Showtab_17" onclick="Show_TabMenu(1,7);">
						<html:link page="/mobile/agent19pay.do?thisAction=viewMobilePay">充值业务</html:link>
					</li>
				</ul>
			</div>
			<div class="clear"></div>
			<div id="Showtabcon_10">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_10" onclick="Show_SubMenu(0,1,0,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">担保交易收款</html:link>
							</c:if>
							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=transactionBySeller&paytype=3">担保交易收款</html:link>
							</c:if>
						</li>
						<li id="Showsub_11" onclick="Show_SubMenu(0,1,1,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">即时到账收款</html:link>
							</c:if>

							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=transactionBySeller&paytype=4">即时到账收款</html:link>
							</c:if>
						</li>
						<li id="Showsub_12" onclick="Show_SubMenu(0,1,2,3)">

							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">批量收款</html:link>
							</c:if>

							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=batchCollect&paytype=5">批量收款</html:link>
							</c:if>
						</li>
						<li id="Showsub_13" onclick="Show_SubMenu(0,1,3,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">预支</html:link>
							</c:if>
							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link page="/transaction/transaction.do?thisAction=debit">预支</html:link>
							</c:if>
						</li>
						<li id="Showsub_14" onclick="Show_SubMenu(0,1,4,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">报销</html:link>
							</c:if>

							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transactionlist.do?thisAction=getExpenseList">报销</html:link>
							</c:if>
						</li>
					</ul>
				</div>
			</div>
			<div id="Showtabcon_11" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_20" onclick="Show_SubMenu(0,2,0,3)"
							class="at_menu_bottoma">
							<html:link
								page="/transaction/transaction.do?thisAction=payIndex&tab=1">即时到账付款</html:link>
						</li>
						<li id="Showsub_21" onclick="Show_SubMenu(0,2,1,3)">
							<html:link
								page="/transaction/transaction.do?thisAction=payIndex&tab=2">担保交易付款</html:link>
						</li>
						<li id="Showsub_22" onclick="Show_SubMenu(0,2,2,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">批量付款</html:link>
							</c:if>
							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=batchPayment&paytype=6">批量付款</html:link>
							</c:if>
						</li>

						<li id="Showsub_23" onclick="Show_SubMenu(0,2,3,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">借款\还款</html:link>
							</c:if>
							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=transactionByBorrowing">借款\还款</html:link>
							</c:if>
						</li>
						
						<li id="Showsub_24" onclick="Show_SubMenu(0,2,4,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">借款\还款</html:link>
							</c:if>
							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=creditRepayment">授信还款</html:link>
							</c:if>
						</li>
						<li id="Showsub_25" onclick="Show_SubMenu(0,2,5,3)">
							<c:if test="${URI.agent.validCertStatus==0}">
								<html:link page="/page/accessDeniedCert.jsp">红包</html:link>
							</c:if>
							<c:if test="${URI.agent.validCertStatus==1}">
								<html:link
									page="/transaction/transaction.do?thisAction=batchRedPacket&paytype=6">红包</html:link>
							</c:if>
						</li>
						

					</ul>
				</div>
			</div>
			<div id="Showtabcon_12" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_30" onclick="Show_SubMenu(0,3,0,2)"
							class="at_menu_bottoma">
							<html:link page="/shopping/building.jsp">公共事业缴费</html:link>
						</li>
						<li id="Showsub_31" onclick="Show_SubMenu(0,3,1,2)">
							<html:link page="/shopping/building.jsp">信用卡还贷</html:link>
						</li>
					</ul>
				</div>
			</div>
			<div id="Showtabcon_13" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_40" onclick="Show_SubMenu(0,4,0,3)"
							class="at_menu_bottoma">
							<html:link
								page="/transaction/transactionlist.do?thisAction=listTransactions&selectDealDate=1">交易管理首页</html:link>
						</li>
						<li id="Showsub_41" onclick="Show_SubMenu(0,4,1,3)">
							<html:link
								page="/agent/agentaddress.do?thisAction=getAgentAddressManage">交易地址管理</html:link>
						</li>
						<li id="Showsub_42" onclick="Show_SubMenu(0,4,2,3)">
							<html:link
								page="/transaction/transactionlist.do?thisAction=refundMentManage&selectDealDate=1">退款管理</html:link>
						</li>
					</ul>
				</div>
			</div>
			<div id="Showtabcon_14" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_50" onclick="Show_SubMenu(0,5,0,4)"
							class="at_menu_bottoma">
							<html:link page="/security/security.do?thisAction=securityCenter">安全中心首页</html:link>
						</li>
						<li id="Showsub_51" onclick="Show_SubMenu(0,5,1,4)">
							<html:link page="/security/certificate.do?thisAction=certificate">数字证书</html:link>
						</li>
						<li id="Showsub_52" onclick="Show_SubMenu(0,5,2,4)">
							<html:link page="/security/security.do?thisAction=securityUseKey">支付盾</html:link>
						</li>
						<li id="Showsub_53" onclick="Show_SubMenu(0,5,3,4)">
							<html:link
								page="/security/security.do?thisAction=securityChecked">安全检测</html:link>
						</li>
					</ul>
				</div>
			</div>
			<div id="Showtabcon_15" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_60" onclick="Show_SubMenu(0,6,0,2)"
							class="at_menu_bottoma">
							<html:link page="/mobile/agent19pay.do?thisAction=viewMobilePay">话费充值</html:link>
						</li>
					</ul>
				</div>
			</div>

			<div id="Showtabcon_16" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<c:if test="${URI.agent.registerType==0}">
							<li id="Showsub_70" onclick="Show_SubMenu(0,7,0,6)"
								class="at_menu_bottoma">
								<html:link page="/agent/agent.do?thisAction=agentInfoById">我的钱门首页</html:link>
							</li>
							<li id="Showsub_75" onclick="Show_SubMenu(0,7,5,6)"
								class="at_menu_bottoma">
								<html:link page="/agent/agent.do?thisAction=viewMyAgent">个人资料</html:link>
							</li>
						</c:if>

						<c:if test="${URI.agent.registerType==1}">
							<c:if test="${URI.agentUser!=null}">
								<li id="Showsub_70" onclick="Show_SubMenu(0,7,0,6)"
									class="at_menu_bottoma">
									<html:link
										page="/agent/agentUser.do?thisAction=viewCompanyInfo">企业资料</html:link>

								</li>
								<li id="Showsub_75" onclick="Show_SubMenu(0,7,5,6)">
									<html:link
										page="/agent/agentUser.do?thisAction=modifyAgentUserPasswordPage">修改密码</html:link>
								</li>
							</c:if>
							<c:if test="${URI.agentUser==null}">
								<li id="Showsub_70" onclick="Show_SubMenu(0,7,0,6)">
									<html:link page="/agent/agent.do?thisAction=viewMyAgent">我的账户</html:link>
								</li>
								<li id="Showsub_75" onclick="Show_SubMenu(0,7,5,6)">
									<html:link page="/agent/agentUser.do?thisAction=viewAgentUsers">操作员管理</html:link>
								</li>
							</c:if>
						</c:if>
						<c:check code="sb12">
							<li id="Showsub_79" onclick="Show_SubMenu(0,7,9,6)">
								<html:link
									page="/transaction/transactionlist.do?thisAction=getSellerAndBuyerTransactionList&fg=seller&flag=1&selectDealDate=1">卖出的交易</html:link>
							</li>
						</c:check>
						<li style="display: none;" id="Showsub_79"
							onclick="Show_SubMenu(0,7,9,6)"></li>
						<c:check code="sb11">
							<li id="Showsub_78" onclick="Show_SubMenu(0,7,8,6)">
								<html:link
									page="/transaction/transactionlist.do?thisAction=getSellerAndBuyerTransactionList&fg=buyer&flag=1&selectDealDate=1">买入的交易</html:link>
							</li>
						</c:check>
						<li style="display: none;" id="Showsub_78"
							onclick="Show_SubMenu(0,7,8,6)"></li>
						<li id="Showsub_71" onclick="Show_SubMenu(0,7,1,6)">
							<html:link
								page="/transaction/transactionlist.do?thisAction=accountDetaillist&selectDealDate=1&balanceType=0">账户查询</html:link>
						</li>

						<c:check code="sc01">
							<li id="Showsub_72" onclick="Show_SubMenu(0,7,2,6)">
								<html:link
									page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">充值</html:link>
							</li>
						</c:check>
						<li style="display: none;" id="Showsub_72"
							onclick="Show_SubMenu(0,7,2,6)">
						</li>

						<c:check code="sc02">
							<li id="Showsub_73" onclick="Show_SubMenu(0,7,3,6)">
								<c:if test="${URI.agent.validCertStatus==0}">
									<html:link page="/page/accessDeniedCert.jsp">提现</html:link>
								</c:if>
								<c:if test="${URI.agent.validCertStatus==1}">
									<html:link
										page="/transaction/draw.do?thisAction=withdrawing&showTabMenuIdList=1,6&showSubMenuIdList=0,7,3,6">提现</html:link>
								</c:if>
							</li>
						</c:check>
						<li style="display: none;" id="Showsub_73"
							onclick="Show_SubMenu(0,7,3,6)">
						</li>

						<c:check code="sb05,sb06,sb07,sb08">
							<li id="Showsub_74" onclick="Show_SubMenu(0,7,4,6)">
								<html:link
									page="/agent/agentlist.do?thisAction=listContact&flag=1">管理联系人</html:link>
							</li>
						</c:check>
						<li style="display: none;" id="Showsub_74"
							onclick="Show_SubMenu(0,7,4,6)">
						</li>


						<li id="Showsub_76" onclick="Show_SubMenu(0,7,6,6)">
							<html:link page="/agent/agent.do?thisAction=viewMobileCenter">手机服务</html:link>
						</li>
					</ul>
				</div>
			</div>
			<div id="Showtabcon_17" style="DISPLAY: none;">
				<div class="menu_bottom">
					<ul>
						<li id="Showsub_80" onclick="Show_SubMenu(0,8,0,1)"
							class="at_menu_bottoma">
							<html:link page="/mobile/agent19pay.do?thisAction=viewMobilePay">话费充值</html:link>
						</li>
		                 <li id="Showsub_81" onclick="Show_SubMenu(0,8,1,1)">
							<html:link page="/blackscreen/blackscreenpay.do?thisAction=viewBlackscreenPay">黑屏充值</html:link>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<script language="javascript">
	var tabMenuList = "<c:out value="${param.a}" />";
	var subMenuList = "<c:out value="${param.b}" />";
    if(tabMenuList!=""){ 
        var tabArray = tabMenuList.split(",");
		Show_TabMenu(tabArray[0],tabArray[1]);
	}
	
	if(subMenuList!=""){
		var subArray = subMenuList.split(",");
		Show_SubMenu(subArray[0],subArray[1],subArray[2],subArray[3]);
	}


function Show_TabMenu(tabid_num,tabnum){
	for(var i=0;i<7;i++){document.getElementById("Showtabcon_"+tabid_num+i).style.display="none";}
	for(var i=0;i<8;i++){document.getElementById("Showtab_"+tabid_num+i).className="";}
	document.getElementById("Showtab_"+tabid_num+tabnum).className="at_menu_top";
	document.getElementById("Showtabcon_"+tabid_num+tabnum).style.display="block";
}

/**
Show_SubMenu方法的参数说明
start:填写每一个分组第一个Showsub的第二个数字 ,如 第一个分组Showsub_10 ,就填0 ,第二个又Showsub_20开始,就填写2
subid_num:填写每一个分组第一个Showsub的第一个数字,如 分组Showsub_10,就填1,分组Showsub_20,就填写2
subnum:填写每一个分组第一个Showsub的第二个数字,如 Showsub_10,就填0,Showsub_11,就填1,如此类推,遇到Showsub_20的时候,又重新又0开始
length:根据要显示多少个链接来定,如 分组 Showtabcon_10中包含了4个链接,那么就取最后一个链接ID的第二位数字加1,就可以了,这里最后一个链接ID是
       id="Showsub_13",那么就填写3+1,也就是4
*/

	

function Show_SubMenu(start,subid_num,subnum,length){
    for(var i=start;i<length;i++){
    	document.getElementById("Showsub_"+subid_num+i).className="";
    }

	document.getElementById("Showsub_"+subid_num+subnum).className="at_menu_bottoma";
}
</script>