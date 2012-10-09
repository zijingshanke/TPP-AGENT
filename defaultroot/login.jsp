
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	
<title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/_css2/index.css" type="text/css" />
<script language="javascript" type="text/javascript">

function detectPlugin(CLSID,functionName)
{
	var pluginDiv=document.getElementById("MIMA");
	var objectActivexDiv=document.getElementById("objectActivex");
	var pswImgDiv=document.getElementById("pswImg");
	var myControl2=document.getElementById("myControl2");
	objectActivexDiv.style.display="none";
	pswImgDiv.style.display="none";
	
	try
	{
		if(eval("myControl2."+functionName)==undefined)
		{
			pswImgDiv.style.display="";
			
			return false;
		}
		else
		{			
		objectActivexDiv.style.display="";			
				return true;	
		}
	}
	catch(e)
	{
		alert(e.message);
		return false;
	}                                                                                                 
}
function checkActivex()
{
	detectPlugin("2E93307E-777D-49E4-886A-D5B04470796A","Init");
}
function winopen(a)
{
	window.open(a,"alank","width=600,height=400,scrollbars=yes,resizable=no,toolbar=no,directories=no,menubar=no,top=100,left=200");
}

   	function downloadActiveX(){
   		if(window.confirm("您确定要下载钱门安全控件吗？(Y/N)"))
   		winopen("<%=request.getContextPath()%>/qmCert_1.0.exe");
	}
 function toSubmit(){
/*
var formid = document.getElementById("f1")

if(validateForm(formid))
{
	formid.submit();
}

*/
var formid = document.getElementById("f1");
var myControl2=document.getElementById("myControl2");

if(myControl2.Init!=undefined){
	document.forms[0].password.value=myControl2.GetPassword();
	document.forms[0].submit();
}else{
	alert("请先下载安全控件然后在尝试登录");
	return false;
}
} 

</script>
<style>
.Attention {
	background: url(<%=request.getContextPath()%>/_img/worning_login_small.gif) no-repeat 15px 10px #FFFFEE;
	border: #FFD326 solid 1px;
	height: 20px;
	width:210px;
	margin: 5px 0 2px 0;
	padding-left: 50px;
	text-align:center;
	padding-top: 10px;
	padding-bottom: 24px;
	
}

</style>
</head>

<body onload="checkActivex()">
  <div id="warp">
    <div id="head">
     
      <span style="float:right; padding-top:40px;">
     
      <div class="quickLink"><img src="<%=request.getContextPath()%>/_img/phone.gif"/>&nbsp;：<i style="font-size: 20px;color: red;">0756-8801800</i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，请 <a href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister">注册</a> 或 <a href="<%=request.getContextPath()%>/login.jsp">登录</a></div>
      </span>
      <a href="<%=request.getContextPath()%>/index.jsp" style="border:none; margin:0; padding:0;"><img src="<%=request.getContextPath()%>/_img/logo.jpg" /></a>
      
      <img name="bannerADrotator" style="FILTER: revealTrans(duration=2,transition=20)" src="<%=request.getContextPath()%>/_img/head_img.gif" class="menu_img" />
    </div>
    <div id="main">
      <div id="left_box"><span name="pluginDiv" id="pluginDiv"></span>
      	<div class="yellowBox">本站采用绿色地址栏EV SSL加密，能保护您所输入的信息不会在网上传输的过程中被截取，请安心使用！</div>
      	<div>&nbsp;</div> 
        <img src="<%=request.getContextPath()%>/_img/little_img.gif" class="left_img" />
        <div class="leftbox_right_count">
          <p class="top_p">收款就是这么简单</p>
          <p>　　您只需要知道对方的钱门账户就可以向对方收款。我们会发邮件通知他尽快付款到您的钱门账户。为网上交易收款/为线下交易收款。向客户收货款/向同事、朋友或家人收款。</p>
        </div>
        <div class="clear"></div>
        <div class="leftbox_count">
          <p class="top_p">生活好帮手</p>
          <p>　　为您提供的不仅仅是安全、专业、保密的网上支付服务，更是为您打造一站式在线生活服务，做您的生活好帮手！</p>
        </div>       
        <div class="leftbox_count">
          <p class="top_p">轻松管理您的交易</p>
          <p>　　钱门提供安全的网上交易收付款服务，查询您账户中的收付款和交易记录，为进行中的交易进行下一步操作。<br />
          作为买家：为自己账户中的交易账单“付款”，为有异议的交易申请退款并“上传凭证”<br />
		  作为卖家：当交易有纠纷时“上传凭证”
          </p>
        </div>    
         <div class="leftbox_count">
         <iframe src="https://www.qmpay.com/information/newslist.do?thisAction=list" frameborder="0" scrolling="none" ALIGN="left" width="250px;" height="151px;"></iframe>
          <div class="leftbox_bm" align="left"  style="margin-top:10px;">
            <p class="left_title_p" >合作伙伴</p>
            <p><a href="http://www.icbc.com.cn/icbc/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_1.gif" title="中国工商银行"/></a><a href="http://www.95599.cn/cn/hq/abc/index/index.jsp/lang=cn/index.html" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_2.gif" title="中国农业银行"/></a><a href="http://www.boc.cn/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_3.gif" title="中国银行"/></a><a href="http://www.ccb.com/portal/cn/home/index.html" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_4.gif" title="中国建设银行"/></a><a href="http://www.cmbchina.com/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_5.gif" title="中国招商银行"/></a><a href="http://www.spdb.com.cn/chpage/c1/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_6.gif" title="中国浦发银行"/></a><a href="http://ebank.gdb.com.cn/comminfo/index.html" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_7.gif" title="广东发展银行"/></a><a href="http://www.cmbc.com.cn/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_8.gif" title="中国民生银行"/></a><a href="http://www.cib.com.cn/netbank/cn/index.html" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_9.gif" title="中国兴业银行"/></a><a href="http://bank.ecitic.com/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_10.gif" title="中信银行"/></a><a href="http://www.sdb.com.cn/website/page" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_11.gif" title="深圳发展银行"/></a><a href="http://www.psbc.com/" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_12.gif" title="邮政储蓄银行"/></a><a href="http://www.bankcomm.com/BankCommSite/cn/index.html" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_13.gif" title="中国交通银行"/></a><a href="http://www.hxb.com.cn/chinese/index.jsp" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_14.gif" title="华夏银行"/></a><a href="http://www.cebbank.com/ceb/html/index.html" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_15.gif" title="中国光大银行"/></a><a href="http://www.visa-asia.com/ap/cn/zh_CN/index.shtml" target="_blank"><img src="<%=request.getContextPath()%>/_img/bank_16.gif" title="Visa"/></a></p>
          </div>
          <div class="leftbox_br" align="left"  style="margin-top:10px;">
            <p class="left_title_p">由此开始</p>
            <p><span>准备！Let's Go!</span></p>
            <p><a href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister" ><img src="<%=request.getContextPath()%>/_img/inqmpay.jpg" /></a></p>
          </div>
        </div>       
      </div>
      <div id="right_box">
        <div class="right_box_count">
        <div class="right_box_count_1">			
		<c:if test="${error eq 'error'}">
			<div class="Attention">
			<font color="red">该用户没有激活,<br/><a href="<%=request.getContextPath()%>/agent/agent.do?thisAction=activationLogin&loginName=<c:out value="${loginName}"></c:out>">请点此重发激活信件</a></font></div>
		</c:if>
		<c:if test="${error2 eq 'error2'}">
			<div class="Attention">
			<font color="red">连续3次登录失败,系统将<c:out value="${loginName}"></c:out>锁定3个小时!<br/>忘了密码?<html:link page="/agent/agent.do?thisAction=forgetPassword&type=password">取回登录密码</html:link></font></div>
		</c:if>
		<c:if test="${errorRand eq 'error'}">
			<div class="Attention">
			发现错误：<br/><font color="red">验证码不正确！</font></div>
		</c:if>
		<c:if test="${error3 eq 'error3'}">
			<div class="Attention">
			发现错误：<br/><font color="red">用户名不存在！</font></div>
		</c:if>
		 <c:if test="${errorPassword eq 'error'}">
		<div class="Attention">
		 <font color="red">
		密码错误!</font><br/><html:link page="/agent/agent.do?thisAction=forgetPassword&type=password">请点此找回密码</html:link>
		</div>
		 </c:if>
        <form id="f1" action="<%=request.getContextPath()%>/agent/agent.do?thisAction=login" method="post">
         <html:hidden property="forwardpage" value="${param.forwardpage}"/>
         <input type="hidden" name="password"/>
          <table cellpadding="0" cellspacing="0" width="225">
            <tr>
              <th>欢迎登录钱门账户<br/><br/></th>
            </tr>
            <tr>
              <td>账户名：(Email地址或手机号)<br/><br/></td>
            </tr>
            <c:if test="${URI!=null}">
            <tr><td>尊敬的<font color="red" ><c:out value="${URI.agent.loginName}"></c:out></font>用户<br/><br/></td></tr>
            <tr><td>您目前已经登录!<html:link page="/agent/agent.do?thisAction=viewMyAgent"><strong>进入我的钱门</strong></html:link><br/><br/></td></tr>
            <tr><td style="text-align:center;"><html:link page="/agent/agent.do?thisAction=removeSession"><strong>注销</strong></html:link></td>
            </tr>
            </c:if>
            <c:if test="${URI==null}">
            <tr>
              <td><input name="loginName" id="loginName" value="" type="text"  rule="notnull" style="width:180px;border:1px solid #999;" /></td>
            </tr>
            <tr>
              <td>登录密码：</td>
            </tr>
            <tr>
              <td>
              <div id="MIMA">
              <div id="objectActivex" style="display: block;">
 <object id="myControl2" name="myControl2"  classid="clsid:2E93307E-777D-49E4-886A-D5B04470796A"  width="183px;" height="20px;" codebase="qmCert.cab" >
       </object>
              </div>
              <div id="pswImg" style="display: none;">
              <a href="javascript:downloadActiveX()"><img src="<%=request.getContextPath()%>/_img/psw.png"></img></a>
              </div>
             
              <div id="KONGJIAN" style="display: none;" class="kongJianBox">&nbsp;&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/_file/setup.exe">下载安全控件  </a></div>
              </td>
            </tr>
           
               <c:if test="${useRand eq 'yes'}">
            <tr>
				<td align="left"><input name="rand" type="text" style="border:1px solid #999;"
					title="请输入右侧验证码" size="6" class="colorblue" onkeydown="if(event.keyCode==13){toSubmit();}" /><html:img border="0"
					page="/servlet/com.neza.base.NumberImage" align="absmiddle"
					height="21" width="64" />请输入验证码</td>
			</tr>
        </c:if>
            <tr>
              <td style="text-align:center;"><input type="button" class="login_btn" value="登 录" onclick="toSubmit();" /></td>
            </tr>
             <tr>
              <td style="text-align:center;padding-top:30px;"><html:link page="/agent/agent.do?thisAction=forgetPassword&type=password">取回登录密码</html:link></td>
            </tr>
            <tr>
              <td style="text-align:center;">还没有<span class="font_style1">钱门</span>账户? <a href="<%=request.getContextPath()%>/agent/agent.do?thisAction=toRegister"><strong>立即注册</strong></a> 。</td>
            </tr>
            </c:if>
          </table>
            </form>
          </div>
        </div>
       <!-- <div class=right_box_count_next>
          <div class="right_box_count_2">
            <p><a href="<%=request.getContextPath()%>/_file/setup.exe">点击下载安全控件安装程序</a></p>
              <p><a href="#">无法输入登录密码？</a></p>
            <p><a href="#">控件安装有问题？点击解决</a></p>
            <p style="border-bottom:1px dashed #ccc;"></p>
            <p><a href="#">点击下载IE新手买卖家支付全过程演示</a></p>
            <p><a href="#">如何申请网上银行</a></p>
            <p><a href="#">安全支付秘诀</a></p>
          </div>
        </div>-->
      </div>
      <div class="clear"></div>
	<div id="footer">
		  <a href="<%=request.getContextPath()%>/about/regarding.jsp">关于钱门</a> |
		  <a href="<%=request.getContextPath()%>/about/talent.jsp">诚聘英才</a> |
		  <a href="<%=request.getContextPath()%>/about/advertising.jsp">广告业务</a> |
		  <a href="<%=request.getContextPath()%>/about/contact.jsp">联系我们</a> |
		  <a href="<%=request.getContextPath()%>/about/help.jsp">安全保障</a> |
		  <a href="<%=request.getContextPath()%>/about/disclaimer.jsp">服务说明</a> |
		  <a href="<%=request.getContextPath()%>/about/join_help.jsp">加盟钱门</a>
		 <div id="foot_info">
    <p>Copyright 2009-2012, www.qmpay.com .All Rights Reserved</p>
    <p><a href="#" onclick="javascript:window.open('<%=request.getContextPath()%>/_jsp/ICP.jsp','','width=600,height=850,top=0')"> 增值电信业务经营许可证 粤B2-20090217 </a> &nbsp;<a href="http://www.miibeian.gov.cn">粤ICP备09087470号</a>&nbsp;<a href="http://renzheng.360.cn/zhengshu.htm?id=2555">360安全认证</a> <img src="<%=request.getContextPath()%>/_img/logo_footer.gif" /></p>
   </div>
  </div>
  	<script language="javascript">
  		if(document.getElementById("loginName")!=null){
	 		document.getElementById("loginName").focus();
	 	}
	</script>
	
	
	<script language=JavaScript> 
<!--

var bannerAD=new Array(); 
var bannerADlink=new Array(); 
var adNum=0; 
//修改下面的图片地址和相应的链接地址
bannerAD[0]="<%=request.getContextPath()%>/_img/head_img.gif"; 
bannerADlink[0]="#"; 
bannerAD[1]="<%=request.getContextPath()%>/_img/qmpay_happy2010.jpg"; 
bannerADlink[1]="#"; 
bannerAD[2]="<%=request.getContextPath()%>/_img/head_img.gif"; 
bannerADlink[2]="#"; 
bannerAD[3]="<%=request.getContextPath()%>/_img/qmpay_happy2010.jpg"; 
bannerADlink[3]="#"; 
bannerAD[4]="<%=request.getContextPath()%>/_img/head_img.gif"; 
bannerADlink[4]="#"; 
bannerAD[5]="<%=request.getContextPath()%>/_img/qmpay_happy2010.jpg"; 
bannerADlink[5]="#"; 

var preloadedimages=new Array(); 
for (i=1;i<bannerAD.length;i++){ 
preloadedimages[i]=new Image(); 
preloadedimages[i].src=bannerAD[i]; 
} 

function setTransition(){ 
if (document.all){ 
bannerADrotator.filters.revealTrans.Transition=Math.floor(Math.random()*23); 
bannerADrotator.filters.revealTrans.apply(); 
} 
} 

function playTransition(){ 
if (document.all) 
bannerADrotator.filters.revealTrans.play() 
} 

function nextAd(){ 
if(adNum<bannerAD.length-1)adNum++ ; 
else adNum=0; 
setTransition(); 
document.images.bannerADrotator.src=bannerAD[adNum]; 
playTransition(); 
theTimer=setTimeout("nextAd()", 5000); 
} 

function jump2url(){ 
jumpUrl=bannerADlink[adNum]; 
jumpTarget='_blank'; 
if (jumpUrl != ''){ 
if (jumpTarget != '')window.open(jumpUrl,jumpTarget); 
else location.href=jumpUrl; 
} 
} 
function displayStatusMsg() { 
status=bannerADlink[adNum]; 
document.returnValue = true; 
} 
-->
</script> 

</body>
</html>
