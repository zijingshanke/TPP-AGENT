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
										个人协议是什么？
									</h1>
										&nbsp;&nbsp;&nbsp;&nbsp;为了保证个人商家的权益，更好的为个人商家进行服务，钱门会与每一位使用钱门网关的个人商家签订标准协议。该协议会由钱门的人员发送给您。您签署完成后，传真或邮寄给钱门即可。 
									<div class="helpAbout">
										相关问题：
									</div>
									<div class="helpdetailedmore">
										<ul>
											 <li><a href="../help/newHelp_relevance_01.jsp" target="_blank">什么是钱门盾？</a></li>
						                    <li><a href="../help/newHelp_relevance_02.jsp" target="_blank">为什么要使用钱门盾？</a></li>
						                    <li><a href="../help/newHelp_relevance_03.jsp" target="_blank">个人协议是什么？</a></li>
						                    <li><a href="../help/newHelp_relevance_04.jsp" target="_blank">个人用户如何申请接入钱门网关？</a></li>
						                    <li><a href="../help/newHelp_relevance_05.jsp" target="_blank">忘记登录密码怎么办？</a></li>
						                    <li><a href="../help/newHelp_relevance_06.jsp" target="_blank">钱门盾的安全防护措施</a></li>
						                    <li><a href="../help/newHelp_relevance_07.jsp" target="_blank">没有通过实名认证能够进行什么操作？</a></li>
						                    <li><a href="../help/newHelp_relevance_08.jsp" target="_blank">怎样进行实名认证？</a></li>
						                    <li><a href="../help/newHelp_relevance_09.jsp" target="_blank">为什么无法输入密码？密码框不见了？</a></li>
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
