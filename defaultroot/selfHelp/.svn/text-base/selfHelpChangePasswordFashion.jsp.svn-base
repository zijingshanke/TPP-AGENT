<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- selfHelpChangePasswordFashion.jsp -->
<html>
  <head>
  <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />

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
              <p><strong>选择方式并操作</strong></p>
            </div>
          </div>
          <div class="pay_way">
            <img src="../_img/pay_pic.gif" />
                        <c:if test="${type eq 'password'}"><p class="pay_way_p">选择最适合您的方式来找回登录密码</p></c:if>
             <c:if test="${type eq 'paypassword'}"> <p class="pay_way_p">选择最适合您的方式来找回支付密码</p></c:if>
          </div>
          
                    <c:if test="${mobileBindStatus==1}">
          <div class="pay_kind">
          <c:if test="${type eq 'password'}">
          	<a href="../agent/agent.do?thisAction=getBackPassword&loginName=<c:out value="${loginName}"></c:out>" class="pay_kind_main">
             </c:if>
              <c:if test="${type eq 'paypassword'}">
          	<a href="../agent/agent.do?thisAction=getBackPayPassword&loginName=<c:out value="${loginName}"></c:out>" class="pay_kind_main">
             </c:if>
                <ul>
                    <li><span class="pay_kind_h1">用手机找回[推荐]</span></li>
                    <li>使用您的绑定手机接受校验码得方式快速找回登录,支付密码，本服务完全免费</li>
                    <li>
                        <ul class="pay_kind_1_bottom">
                            <li class="oneline">选用手机</li>
                            <li class="oneline">收校验码</li>
                            <li class="oneline">设置新密码</li>
                            <li class="done">成功！</li>
                        </ul>
                    </li>
                </ul>
            </a>
          </div>
          </c:if>
          <div class="pay_kind pay_kind_white">
          <c:if test="${type eq 'password'}">
	<a href="../agent/agent.do?thisAction=sendEmailChangePasswordCertNo&loginName=<c:out value="${loginName}"></c:out>" class="pay_kind_main">
  </c:if>
  <c:if test="${type eq 'paypassword'}">
  <a href="../agent/agent.do?thisAction=sendEmailChangePayPasswordCertNo&loginName=<c:out value="${loginName}"></c:out>" class="pay_kind_main">
   </c:if>
                <ul>
                    <li><span class="pay_kind_h1">用证件号码找回</span></li>
                    <li>
                        <ul class="pay_kind_1_bottom">
                            <li class="oneline">选用证件号码</li>
                            <li class="oneline">接收邮件</li>
                            <li class="oneline">输入证件号码</li>
                            <li class="oneline">设置新密码</li>
                            <li class="done">成功！</li>
                        </ul>
                    </li>
                </ul>
            </a>
          </div>
          <div class="pay_kind pay_kind_white">
          	<a href="#" class="pay_kind_main">
                <ul>
                    <li><span class="pay_kind_h1">提交人工审核申请单，客服审核需要2-3工作日</span></li>
                    <li>
                        <ul class="pay_kind_1_bottom">
                            <li class="oneline">选用人工审核</li>
                            <li class="oneline">接收邮件</li>
                            <li>填写账户信息核实身份</li>
                            <li>客服人工审核3个工作日</li>
                            <li class="oneline">审核成功</li>
                            <li class="done">成功！</li>
                        </ul>
                    </li>
                </ul>
            </a>
          </div>
          <div class="pay_kind pay_kind_white">
            <a href="#" id="pay_kind_white_a">查看我的安全保护问题
            	<div class="popbox">
                	<ul class="word_location">
                    	<li class="popbox_title">我的安全保护问题是:</li>
                        <li class="popbox_word">我的小学校名是什么</li>
                        <li class="popbox_word">我的小学校名是什</li>
                    </ul>
                </div>
            </a>
               <c:if test="${type eq 'password'}">
	<a href="../agent/agent.do?thisAction=sendEmailChangePasswordQuestion&loginName=<c:out value="${loginName}"></c:out>" class="pay_kind_main">
  </c:if>
  <c:if test="${type eq 'paypassword'}">
  <a href="../agent/agent.do?thisAction=sendEmailChangePayPasswordQuestion&loginName=<c:out value="${loginName}"></c:out>" class="pay_kind_main">
   </c:if>
                <ul>
                    <li><span class="pay_kind_h1">用安全保护问题找回</span></li>
                    <li>
                        <ul class="pay_kind_1_bottom">
                            <li class="oneline">选用安全问题</li>
                            <li class="oneline">接收邮件</li>
                            <li>回答安全保护问题</li>
                            <li class="oneline">设置新密码</li>
                            <li class="done">成功！</li>
                        </ul>
                    </li>
                </ul>
            </a>
          </div>
        </div>
      </div>
    </div>

    <div id="footer">
    CopyRight 2005-2008, fdays.com .All Rights Reserved 增值电信业务经营许可证 粤B2-20040561
  </div>
  </div>
</body>
</html>
