<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<html>
<head>
<!-- errorlink.jsp -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
</head>

<body>
  <div id="warp">
   <c:if test="${URI==null}">
   <c:import url="../selfHelp/head.jsp"></c:import>
   </c:if>
   <c:if test="${URI!=null}">
   <c:import url="../_jsp/mainTop.jsp"></c:import>
   </c:if>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>链接有误</strong></p>
            </div> 
          </div> 
          <div class="Attention Attention5">
          	<div class="attentionTitle attentionTitle5">
            	<em><strong style="font-size:14px;">链接有误!</strong></em>
            </div>
          </div>
        </div>
      </div>
    </div>
    
	

      <div id="footer">
        Copyright 2009-2012, www.qmpay.com .All Rights Reserved 增值电信业务经营许可证 粤B2-20090217
      </div>
  </div>
</body>
</html>
