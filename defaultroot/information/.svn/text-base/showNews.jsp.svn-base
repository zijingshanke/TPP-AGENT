
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld " prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: transaction/showNews.jsp -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" /> 
	</head>
	<body>
		<div id="warp">
			<c:import url="../_jsp/newsTop.jsp" />
			<div class="main" style="margin-top: 10px;">
			<div id="news_left_box">
		        <div class="news_leftbox_top">
		          <div class="leftbox_bottom">
		            <div class="leftbox_count">
		             <p class="leftbox_count_p">更多新闻</p>
		             	<ul>
		             		<c:forEach items="${nlf.list}" var="news">
		             		<li><a href="../information/newslist.do?thisAction=showNews&id=<c:out value='${news.id}'/>">
							<c:out value="${news.tmpeTitle}"/> </a><br />（<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${news.createDate}"/>）</li>
		             		</c:forEach>
		             		<c:if test="${listSize>4}">
		             		<li><a href="../information/newslist.do?thisAction=listNews">更多>>></a></li>
		             		</c:if>
		             	</ul>
		              	
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
		               		 <strong><c:out value="${news.title}"/></strong>
		                  </div>
		                </div>
		              </div>
		              <div><br/>
							阅读次数:<c:out value="${news.readNum}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							发布时间：<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${news.createDate}"/>
							<br/><br/><br/><br/>
							
							<c:out value="${news.content}" escapeXml="false"></c:out>
						</div>
			 </div>
		  </div>
		</div>
	</div>
	</div>
	<c:import url="../_jsp/footer.jsp" />
	</div>
	</body>
</html>
