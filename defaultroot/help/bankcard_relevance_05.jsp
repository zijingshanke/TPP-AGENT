<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: help/index.jsp -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<link href="helpShining.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/banklist.css" type="text/css" />
	</head>

	<body onload="initAd();"><c:import url="/_jsp/qqServices.jsp" />	
		<div id="warp">
			<div class="bodyTop"></div>
			<c:import url="/_jsp/helpTop.jsp" />
			<div class="h10"></div>
			<div class="h10"></div>
			<div class="clear"></div>
			<div class="contentLeft">
				<script language="JavaScript">
function ShowFLT(i) {
    lbmc = document.getElementById('LM' + i);
    if (lbmc.style.display == 'none') {
        lbmc.style.display = '';
var cha = document.getElementById('change' + i);
cha.className="changenow";
    }
    else {
        lbmc.style.display = 'none';
var cha = document.getElementById('change' + i);
cha.className="changehelp";
    }
}
      </script>

				<div class="changehelp" id="change1" onclick="javascript:ShowFLT(1)">
					<span>企业用户帮助</span>
				</div>
				<div class="menu" id="LM1" style="display: none;">
					<!-- 企业用户帮助还没做 -->
				</div>
				<div class="h5"></div>
				<div class="changenow" id="change2" onclick="javascript:ShowFLT(2)">
					<span>常规问题</span>
				</div>
				<div class="menu" id="LM2" style="display:block">
		      <ul>

		          <li><a href="#nogo">新手上路<!--[if !IE 6]><!--></a><!--<![endif]-->
		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="grzc1.jsp">钱门个人用户注册</a></li>
			              <li><a href="sjfw1.jsp">手机服务</a></li>
			          </ul>

		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
		          </li>
		          <li><a href="#nogo">提现、充值<!--[if !IE 6]><!--></a><!--<![endif]-->
		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="ptcz1.jsp">充值</a></li>
			              <li><a href="qtcz1.jsp">给其他账户充值</a></li>
			              <li><a href="tx1.jsp">提现</a></li>
			          </ul>
		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
		          </li>
		          <li><a href="#nogo">账户管理<!--[if !IE 6]><!--></a><!--<![endif]-->
		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="yhmsz1.jsp">用户名设置</a></li>
			              <li><a href="ghtx1.jsp">更换头像</a></li>
			              <li><a href="smrz1.jsp">实名认证</a></li>
			              <li><a href="xzyh1.jsp">新增提现银行</a></li>
			          </ul>
		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
	            </li>
		         

		          <li><a href="#nogo">安全相关<!--[if !IE 6]><!--></a><!--<![endif]-->
		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="xgpwd1.jsp">修改登录密码</a></li>
			              <li><a href="wjpwd1.jsp">忘记登录密码</a></li>
			              <li><a href="xgpapwd1.jsp">修改交易密码</a></li>
			              <li><a href="wjpapwd1.jsp">忘记交易密码</a></li>
			              <li><a href="szaqwt1.jsp">设置安全问题</a></li>
			              <li><a href="#">安全控件下载</a></li>
			          </ul>
		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
		          </li>
		          <li><a href="#nogo">钱门盾<!--[if !IE 6]><!--></a><!--<![endif]-->
		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="#"></a></li>
			          </ul>
		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
		          </li>
		          <li><a href="#nogo">手机充值
	              <!--[if !IE 6]><!--></a><!--<![endif]-->

		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="#"></a></li>
			          </ul>
		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
	            </li>
		          <li><a href="#nogo">钱门工具<!--[if !IE 6]><!--></a><!--<![endif]-->

		          <!--[if lte IE 6]><table><tr><td><![endif]-->
			          <ul>
			              <li><a href="#"></a></li>
			          </ul>
		          <!--[if lte IE 6]></td></tr></table></a><![endif]-->
		          </li>
		      </ul>
		    </div>

			</div>
			<div id="right_box">
				<div class="rightbox_top">
					<div class="rightbox_bottom">
						<div class="rightbox_count">
							<div class="rightbox_title">
								<div class="rightbox_title_right">
									<div class="rightbox_title_count">
										<a href="../help/index.jsp" style="text-decoration: none;">帮助中心</a>
										>>
										<span>常见问题</span>
									</div>
								</div>
							</div>
							<!-- 开始 -->
							<div class="contentRight">
								<div class="helpdetailed">
									<h1>
										招商银行银行卡使用限额
									</h1>
										<table width="100%" cellpadding="0" cellspacing="0" class="tablebanking">
										  <thead>
											<tr class="bankTitle"  bgcolor="#FDFDD0">
											  <td colspan="5"><strong>招商银行</strong>&nbsp;网银支付信息</td>
										    </tr>
											<tr>
											  <td>&nbsp;</td>
											  <td colspan="2">大众版</td>
											  <td colspan="2">专业版</td>
											</tr>
										  </thead>
											<tr>
											  <td width="8%">&nbsp;</td>
											  <td width="14%">单笔限额（元） </td>
											  <td>每日限额（元） </td>
											  <td width="14%">单笔限额（元） </td>
											  <td>每日限额（元） </td>
											</tr>
											<tr>
											  <td class="text12B">借记卡</td>
											  <td  colspan="2">          5000</td>
											  <td  colspan="2">无限额</td>
											</tr>
											<tr>
											  <td class="text12B">信用卡</td>
											  <td colspan="4">单笔限额497元（含），单日额度由信用卡额度决定。</td>
											</tr>
											<tr>
											  <td class="text12B">备注</td>
											  <td align="left"  colspan="4">
											    特别提醒：2007年8月17日起，为了最大限度地保证您的资金安全，招商银行对网上支付交易启用“验证码”进行身份验证实施动态管理，即客户进行网上支付消费时，适时启动“验证码”进行身份验证，需要您用在申请网上支付功能时预留的电话，通过手机短信或电话方式，获取四位数字“验证码”，验证通过后方可完成支付。如果您在开通网上支付功能时，没有预留电话或预留电话错误，可拔打招行客户服务电话95555进行咨询。<br/>
											    自2008年7月1日起，招行对借记卡大众版用户设置了网上支付季度限额。每季度网上支付限额为5万元，超过5万元限额后，该季度内暂停大众版网上支付功能，下个自然季度开始后恢复支付功能。本季度大众版支付总额达到5万元后，将只能通过专业版支付。<br/>
										        信用卡用户请先开通信用卡网上支付功能后再支付。<a href="http://www.cmbchina.com/personalbank/netpay/Faq.htm" target="_blank">查看开通帮助</a><br/>
											    </td>
										  </tr>
										</table>
									<div class="helpAbout">
										相关问题：
									</div>
									<div class="helpdetailedmore">
										<ul>
											<li><a href="../help/bankcard_relevance_01.jsp" target="_blank">钱门支持哪些银行卡？</a></li>
										    <li><a href="../help/bankcard_relevance_02.jsp" target="_blank">中国工商银行如何开通网上银行？</a></li>
										    <li><a href="../help/bankcard_relevance_03.jsp" target="_blank">中国工商银行银行卡使用限额</a></li>
										    <li><a href="../help/bankcard_relevance_04.jsp" target="_blank">招行如何开通网上银行</a></li>
										    <li><a href="../help/bankcard_relevance_05.jsp" target="_blank">招商银行银行卡使用限额</a></li>
										    <li><a href="../help/bankcard_relevance_06.jsp" target="_blank">农业银行银行卡使用限额</a></li>
										    <li><a href="../help/bankcard_relevance_07.jsp" target="_blank">交通银行银行卡使用限额</a></li>
										    <li><a href="../help/bankcard_relevance_08.jsp" target="_blank">中国建设银行银行卡使用限额</a></li>
										    <li><a href="../help/bankcard_relevance_09.jsp" target="_blank">中国银行银行卡使用限额</a> </li>
										</ul>
									</div>
								</div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
		</div>
		</div>
		<div class="clear"></div>
		<c:import url="../_jsp/footer.jsp" />
		</div>
	</body>
</html>
