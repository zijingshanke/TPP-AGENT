<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: help/index.jsp -->

<head>
<title>钱门支付！--网上支付！安全放心！</title>
<link href="helpShining.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
  	</head>
  
  <body onload="initAd();"><c:import url="/_jsp/qqServices.jsp" />	
  <div id="warp">
  <div class="bodyTop"></div>
      <c:import url="/_jsp/helpTop.jsp" />
 
<div class="h10"></div>
<div class="h10"></div>
<!--header end-->
<!--mainBody-->
<div id="wrapper" class="clearfix">
  <!--leftNav-->
  <!--leftNav end-->

    <div id="content" class="clearfix">
      <div class="clear"></div>
      <div class="category">
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
  
   <div class="changehelp" id="change1" onclick="javascript:ShowFLT(1)"><span>企业用户帮助</span></div>
		  <div class="menu" id="LM1" style="display:none;">
		      <!-- 企业用户帮助还没做 -->
	      </div>
		  <div class="h5"></div>
		  <div class="changenow" id="change2" onclick="javascript:ShowFLT(2)" ><span>常规问题</span></div>
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
        <div class="contentRight">
<script language="javascript">
	function show_intro(pre,pree, n, select_n,css) {
		for (i = 1; i <= n; i++) {
			var intro = document.getElementById(pre + i);
			var cha = document.getElementById(pree + i);
			intro.style.display = "none";
			cha.className=" ";
			if (i == select_n) {
				intro.style.display = "block";
				cha.className=css;
			}
		}
	}
</script>
		    <div class="navlist">
			    <span>热点问题</span>
			    <ul>
				    <li><a href="../help/bankcard_relevance.jsp"><span class="now" id="first1" onmouseover="show_intro('allmenu','first',6,1,'now')">银行卡相关问题</span></a></li>
				    <li><a href="../help/agent_relevance.jsp"><span id="first2"  onmouseover="show_intro('allmenu','first',6,2,'now')">注册/账户设置</span></a></li>
				    <li><a href="../help/transaction_relevance.jsp"><span id="first3"  onmouseover="show_intro('allmenu','first',6,3,'now')">收付款/提现</span></a></li>
				    <li><a href="../help/mobileResult_relevance.jsp"><span id="first4"  onmouseover="show_intro('allmenu','first',6,4,'now')">手机充值</span></a></li>
				    <li><a href="../help/finance_relevance.jsp"><span id="first5"  onmouseover="show_intro('allmenu','first',6,5,'now')">借/还款</span></a></li>
				    <li><a href="../help/newHelp_relevance.jsp"><span id="first6"  onmouseover="show_intro('allmenu','first',6,6,'now')">最新帮助</span></a></li>
				    
				</ul>
			</div>
			<div class="questionlist">

			    <ul id="allmenu1">
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
			    <ul id="allmenu2" style="display:none;">
					<li><a href="../help/agent_relevance_01.jsp" target="_blank">什么是交易密码？</a></li>
					<li><a href="../help/agent_relevance_02.jsp" target="_blank">登录密码和交易密码有何区别？</a></li>
					<li><a href="../help/agent_relevance_03.jsp" target="_blank">为什么密码明明正确但输入老是提示错误？</a></li>
					<li><a href="../help/agent_relevance_04.jsp" target="_blank">设置个人头像对图片有要求吗？</a></li>
					<li><a href="../help/agent_relevance_05.jsp" target="_blank">钱门账户的姓名可以修改吗？</a></li>
					<li><a href="../help/agent_relevance_06.jsp" target="_blank">人工账户冻结和解冻流程</a></li>
					<li><a href="../help/agent_relevance_07.jsp" target="_blank">人工找回密码流程</a></li>
					<li><a href="../help/agent_relevance_08.jsp" target="_blank">找回密码时，收不到邮件怎么办？</a></li>
					<li><a href="../help/agent_relevance_09.jsp" target="_blank">手机号码验证收费吗？</a></li>
				</ul>
			    <ul id="allmenu3" style="display:none;">
					 <li><a href="../help/transaction_relevance_01.jsp" target="_blank">我申请提现到银行卡了，多久能收到钱？</a></li>
				    <li><a href="../help/transaction_relevance_02.jsp" target="_blank">用来提现的银行卡必须是本人的吗？</a></li>
				    <li><a href="../help/transaction_relevance_03.jsp" target="_blank">提现是怎么收费的？我最多能提现多少钱？</a></li>
				    <li><a href="../help/transaction_relevance_04.jsp" target="_blank">付款到钱门账户需要收取手续费吗？</a></li>
				    <li><a href="../help/transaction_relevance_05.jsp" target="_blank">重复多次付款该怎么办？</a></li>
				    <li><a href="../help/transaction_relevance_06.jsp" target="_blank">提现失败原因解析</a></li>
				    <li><a href="../help/transaction_relevance_07.jsp" target="_blank">如何确认我的支付已经成功了？</a></li>
				    <li><a href="../help/transaction_relevance_08.jsp" target="_blank">用钱门支付服务收取货款、支付货款安全吗？</a></li>
				    <li><a href="../help/transaction_relevance_09.jsp" target="_blank">支付时出现提交URL参数为空是什么原因？</a></li>
				</ul>

			    <ul id="allmenu4" style="display:none;">
				    <li><a href="../help/mobileResult_relevance_01.jsp" target="_blank">使用钱门的在线手机充值服务，我每次充值可以充多少钱？</a></li>
                    <li><a href="../help/mobileResult_relevance_02.jsp" target="_blank">钱门的在线手机充值服务安全吗?</a></li>
                    <li><a href="../help/mobileResult_relevance_03.jsp" target="_blank">如果我的充值没有成功，收到钱门的退款需要多少时间？</a></li>
                    <li><a href="../help/mobileResult_relevance_04.jsp" target="_blank">如果我的充值没有成功，钱门会退款给我吗？</a></li>
                    <li><a href="../help/mobileResult_relevance_05.jsp" target="_blank">我的银行卡支持网上支付吗？</a></li>
                    <li><a href="../help/mobileResult_relevance_06.jsp" target="_blank">怎样使用银行卡进行手机充值？</a></li>
                    <li><a href="../help/mobileResult_relevance_07.jsp" target="_blank">我可以为别人的手机进行充值吗？</a></li>
                    <li><a href="../help/mobileResult_relevance_08.jsp" target="_blank">我不是钱门用户，可以使用钱门在线手机充值服务吗?</a></li>
                    <li><a href="../help/mobileResult_relevance_09.jsp" target="_blank">什么是钱门在线手机充值服务？</a></li>
				</ul>

			    <ul id="allmenu5" style="display:none;">
				    <li><a href="../help/finance_relevance_01.jsp" target="_blank">如何申请预支？</a></li>
                    <li><a href="../help/finance_relevance_02.jsp" target="_blank">报销怎么操作？</a></li>
                    <li><a href="../help/finance_relevance_03.jsp" target="_blank">怎样借款？</a></li>
                    <li><a href="../help/finance_relevance_04.jsp" target="_blank">如何还款？</a></li>
                    <li><a href="../help/finance_relevance_05.jsp" target="_blank">可以向任何人借款吗？</a></li>
                    <li><a href="../help/finance_relevance_06.jsp" target="_blank">可以向什么账户申请预支？</a></li>
                    <li><a href="../help/finance_relevance_07.jsp" target="_blank">没有通过实名认证可以申请预支吗？</a></li>
                    <li><a href="../help/finance_relevance_08.jsp" target="_blank">没有通过实名认证可以借款吗？</a></li>
                    <li><a href="../help/finance_relevance_09.jsp" target="_blank">预支/报销常见问题</a></li>
				</ul>
			    <ul id="allmenu6" style="display:none;">
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

				<div class="clear"></div>
		    </div>
			<div class="basic"></div>
			<div class="demo">
			    <div class="demo_title">产品功能演示</div>
			    <ul>
				    <li><a href="grzc1.jsp">个人注册</a></li>
				    <li><a href="ptcz1.jsp">如何充值</a></li>
				    <li><a href="smrz1.jsp">如何实名认证</a></li>
				    <li><a href="xzyh1.jsp">新增提现银行</a></li>
				    <li><a href="wjpwd1.jsp">忘记登录密码</a></li>
				    <li><a href="wjpapwd1.jsp">忘记交易密码</a></li>
				    <li><a href="xgpwd1.jsp">修改登录密码</a></li>
				    <li><a href="xgpapwd1.jsp">修改交易密码</a></li>

				</ul>
				<div class="clear"></div>
			</div>
			<div class="cs">
		      <div class="cs_online">
				    <div class="cs_title">客服在线答疑</div>
				<a href="http://sighttp.qq.com/cgi-bin/check?sigkey=659d55c361c883d169cc632bffd7822f70e6dfb2fb5a6dd46e16a376e85fd9cd"; target=_blank; onclick="var tempSrc='http://sighttp.qq.com/wpa.js?rantime='+Math.random()+'&sigkey=659d55c361c883d169cc632bffd7822f70e6dfb2fb5a6dd46e16a376e85fd9cd';var oldscript=document.getElementById('testJs');var newscript=document.createElement('script');newscript.setAttribute('type','text/javascript'); newscript.setAttribute('id', 'testJs');newscript.setAttribute('src',tempSrc);if(oldscript == null){document.body.appendChild(newscript);}else{oldscript.parentNode.replaceChild(newscript, oldscript);}return false;" class="online_link"><span>钱门客服</span> </a >
				  <span class="cs_intra">及时在线为您分忧！</span>				</div>

		      <div class="cs_hotline">
				    <div class="cs_title">7×24客服热线</div>
				  <span class="online_link">0756-8801800</span>
				  <span class="cs_intra">如果您遇到坐席忙，不必担心，请您稍后再拨。</span>				</div>
			    <div class="cs_email">
				    <div class="cs_title">客服电子邮件地址</div>

					<a name="#email" href="mailto:qmpay@qmpay.com" class="online_link"><span>qmpay@qmpay.com</span></a>
					<span class="cs_intra">请把疑问，发到客服邮箱，我们收到后在48小时内给您回复。</span>
				</div>
				<br clear="all" />
            </div>
	    </div>
        <div class="clear"></div>
      </div>

	  <div class="clear"></div>
  </div>
</div>
<c:import url="../_jsp/footer.jsp" />
</div>

  </body>
</html>
