<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld " prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
 
	<!--JSP页面: transaction/listNews.jsp --> 
 
	<head> 
		<title>钱门支付！--网上支付！安全放心！</title> 
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" /> 
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<style type="text/css">
			#news_left_box{width: 730px;}
			#news_left_box .news_leftbox_top{background: url(../_img/security/news_rightbox_bg_top.gif) no-repeat center top}
			#news_left_box .leftbox_bottom{background: url(../_img/security/news_rightbox_bg_bottom.gif) no-repeat center bottom #FFFFFF}
			#news_left_box .leftbox_count_p{background: url(../_img/security/news_list_btn4_bg.gif) no-repeat}
			#news_right_box{width: 240px;}
			#news_right_box .rightbox_top{background: url(../_img/security/news_leftbox_bg_top.gif) no-repeat center top;padding-top:8px;}
			#news_right_box .rightbox_bottom{background: url(../_img/security/news_leftbox_bg_bottom.gif) no-repeat center bottom;padding-bottom:8px;}
			.leftbox_count .post-date{float: right;}

		</style>
	</head>
	<body> 
		<div id="warp"> 
			
 
 
<div id="head"> 
      <span style="float:right;padding-top:40px;"> 
       <img src="../_img/phone.gif"/>&nbsp;：<font color="red" size="4px;">0756-8801800</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        您可以：<a href="../agent/agent.do?thisAction=toRegister" style=" border:none;">注册</a> <a href="/login.jsp">返回登录</a> 
      </span> 
      <a href="../index.jsp" style="border:none; margin:0; padding:0;"><img src="../_img/logo.jpg" /></a> 
    </div> 
    <div  class="nav"> 
      <div class="nav_a" > 
        <div class="nav_b"> 
          <span class="font_style3">最新公告</span> 
        </div> 
      </div> 
    </div> 
			<div class="main" style="margin-top: 10px;"> 
			<div id="news_left_box"> 
		        <div class="news_leftbox_top"> 
		          <div class="leftbox_bottom"> 
		            <div class="leftbox_count"> 
		             <p class="leftbox_count_p">更多新闻</p> 
		             <html:form action="/information/newslist.do?thisAction=listNews" method="post">
			             	<html:hidden property="thisAction"/>
							<html:hidden property="lastAction"/>
							<html:hidden property="intPage" />
							<html:hidden property="pageCount" />
		             	<ul> 
		             	
		             		<c:forEach items="${nlf.list}" var="info">
		             		<li>
								<span class="post-date"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.createDate}"/></span>
								<a href="../information/newslist.do?thisAction=showNews&id=<c:out value='${info.id}'/>"> <c:out value="${info.title}"></c:out> </a>
							</li>
		             		</c:forEach>
								<table width="100%">
								<tr>
								<td align="right"><br/>
								<font color="#D45151">
									共有记录&nbsp;
									<c:out value="${nlf.totalRowCount}"></c:out>
									&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
									<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
									|
									<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
									|
									<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
									|
									<a href="JavaScript:turnToPage(document.forms[0],3)">
										末页</a>] 页数:
									<c:out value="${nlf.intPage}" />
									/
									<c:out value="${nlf.pageCount}" />
									]
									</font>
									</td>
									</tr>
								</table>
		             	</ul> 
		              	</html:form>
		            </div> 
		          </div> 
		        </div> 
		      </div> 
		      <div id="news_right_box"> 
		        <div class="rightbox_top"> 
		          <div class="rightbox_bottom"> 
		            <div class="rightbox_count"> 
		              <div class="rightbox_title"> 
		                <div class="rightbox_title_right"> 
		                  <div class="rightbox_title_count"> 
		               		 <strong>相关链接</strong> 
		                  </div> 
		               
		                </div>   
		              </div> 
		              <div>
		             		&nbsp;
		              </div> 
			 </div> 
		  </div> 
		</div> 
	</div> 
	</div> 
 
<div class="clear"></div> 
<div id="footer"> 
		  <a href="../about/regarding.jsp">关于钱门</a> |
		  <a href="../about/talent.jsp">诚聘英才</a> |
		  <a href="../about/advertising.jsp">广告业务</a> |
		  <a href="../about/contact.jsp">联系我们</a> |
		  <a href="../about/help.jsp">安全保障</a> |
		  <a href="../about/disclaimer.jsp">服务说明</a> |
		  <a href="../about/join_help.jsp">加盟钱门</a> 
		 <div id="foot_info"> 
    <p>Copyright 2009-2012, www.qmpay.com .All Rights Reserved</p> 
    <p><a href="#" onclick="javascript:window.open('../_jsp/ICP.jsp','','width=600,height=850,top=0')"> 增值电信业务经营许可证 粤B2-20090217 </a>&nbsp; <img src="../_img/logo_footer.gif"/></p> 
   
  </div> 
	</div> 
	</body> 
</html> 