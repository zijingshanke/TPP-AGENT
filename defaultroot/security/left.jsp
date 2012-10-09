<%@ page language="java" import="java.util.*,com.fordays.fdpay.right.UserRightInfo,com.fordays.fdpay.agent.Agent" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>一个左边的菜单</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	

	<style type="text/css">
	#FunctionMenu {
	CLEAR: both; BACKGROUND: url(https://img.alipay.com/assets/i/style/others/dividedLi_function.gif) no-repeat right bottom; PADDING-BOTTOM: 203px; MARGIN: 0px; WIDTH: 100%
}
#FunctionMenu UL {
	BORDER-RIGHT: #cbcbcb 1px solid; PADDING-RIGHT: 0px; PADDING-LEFT: 15px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px; LIST-STYLE-TYPE: none
}
#FunctionMenu UL LI {
	PADDING-RIGHT: 0px; DISPLAY: block; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px 0px 6px; PADDING-TOP: 0px
}
#FunctionMenu UL LI A {
	DISPLAY: block; BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) no-repeat 0px -461px; WIDTH: 137px; COLOR: #4d4d4d; TEXT-INDENT: 20px; LINE-HEIGHT: 28px; HEIGHT: 28px; TEXT-DECORATION: none
}
#FunctionMenu UL LI A:hover {
	COLOR: #ff7300
}
#FunctionMenu UL LI.Current A {
	FONT-WEIGHT: bold; FONT-SIZE: 12px; BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) no-repeat 0px -425px; WIDTH: 145px; COLOR: #000; LINE-HEIGHT: 33px; MARGIN-RIGHT: -1px; HEIGHT: 33px
}
DIV.Assignment #SideBar #FunctionMenu {
	BACKGROUND: none transparent scroll repeat 0% 0%; PADDING-BOTTOM: 0px; WIDTH: 225px
}
DIV.Assignment #SideBar #FunctionMenu UL {
	BORDER-TOP-WIDTH: 0px; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; PADDING-BOTTOM: 0px; WIDTH: 225px; PADDING-TOP: 33px; BORDER-RIGHT-WIDTH: 0px
}
DIV.Assignment #SideBar #FunctionMenu UL LI {
	DISPLAY: block; BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) no-repeat 0px -276px; MARGIN-BOTTOM: 7px; WIDTH: 200px; TEXT-INDENT: 0px; LINE-HEIGHT: 47px; HEIGHT: 47px; TEXT-DECORATION: none
}
DIV.Assignment #SideBar #FunctionMenu UL LI P {
	FONT-SIZE: 14px; COLOR: #9b9b9b; TEXT-INDENT: 105px
}
DIV.Assignment #SideBar #FunctionMenu UL LI P.StepOne {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) no-repeat 15px 11px
}
DIV.Assignment #SideBar #FunctionMenu UL LI P.StepTwo {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) 15px -81px
}
DIV.Assignment #SideBar #FunctionMenu UL LI P.StepThree {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) 15px -173px
}
DIV.Assignment #SideBar #FunctionMenu UL LI.Current {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) 0px -343px; WIDTH: 230px; TEXT-INDENT: 0px; LINE-HEIGHT: 61px; HEIGHT: 61px; TEXT-DECORATION: none
}
DIV.Assignment #SideBar #FunctionMenu UL LI.Current P {
	FONT-SIZE: 18px; COLOR: #192766; FONT-FAMILY: 黑体; TEXT-ALIGN: center
}
DIV.Assignment #SideBar #FunctionMenu UL LI.Current P.StepOne {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) 15px -28px
}
DIV.Assignment #SideBar #FunctionMenu UL LI.Current P.StepTwo {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) 15px -120px
}
DIV.Assignment #SideBar #FunctionMenu UL LI.Current P.StepThree {
	BACKGROUND: url(https://img.alipay.com/assets/i/style/head-tab/tab.png) 15px -212px
}
	
</style>
  <script type="text/javascript" src="../_js/jquery-1.3.1.min.js"></script></head>
  <body>
  <div id="SideBar" style="width:160px;">
    <div id="FunctionMenu">
      <ul>
        <li id="li1"><a href="/security/security.do?thisAction=securityUseKey"><span>支付盾介绍</span></a></li>
        <li id="li2"><a href="/security/security.do?thisAction=securityGuide"><span>操作指南</span></a></li>
        <li id="li3"><a href="#"><span>常见问题</span></a></li>
        <li id="li4"><a href="#"><span>支付盾介绍</span></a></li>
        <li id="li5"><a href="#"><span>操作指南</span></a></li>
        <li id="li6"><a href="#"><span>常见问题</span></a></li>      
      </ul>
  </div>
  </div>
  </body>
</html>
