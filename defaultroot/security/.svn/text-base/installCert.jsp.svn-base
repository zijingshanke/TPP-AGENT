<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ page import="java.util.StringTokenizer"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="SecurityActionForm" scope="request"
	type="com.fordays.fdpay.security.action.SecurityActionForm" />
	
	<!-- installCert.jsp -->
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
</head>
<body onload="pageLoad();" >
  <div id="warp">
	<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>*/%&gt;申请数字证书</strong></p>
            </div>
          </div>
          <img src="<%=request.getContextPath()%>/_img/security/shuzizhengshu_3.gif" style="margin-top:12px;"/>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>安装证书</strong></p>
            </div>
          </div>
          <div class="blueBox_mes">
          	<span>
                证书安装中，请稍候...
            </span>
            <p>请选择所有弹出窗口的“是（Y）”完成安装。</p>
           <%/*<p>
            	<html:form action="/security/certificate.do" method="post">
					<html:hidden property="thisAction" value="downloadCert"/>
	            	<input type="button" onclick="pageLoad();" value="下载证书"/>
            	</html:form>
            </p>*/%>
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

<%
    // ------------------------------------------------------------- 返回CA证书内容 >>>
	String server_cert=SecurityActionForm.getCertificate();
	
    // 这里的这些操作，是为了生成vbscript中证书内容的变量定义
    String pkcs7ca = "sPKCS7ca=\"\" & vbcrlf\r\n";
    StringTokenizer st = new StringTokenizer(server_cert, "\r\n");     
    while (st.hasMoreTokens()) {
        String line = st.nextToken();
        if (line.equals("\r\n"))
            continue;
        pkcs7ca += "sPKCS7ca=sPKCS7ca & \"" + line + "\" & vbcrlf\r\n";      
    }
%>
<OBJECT id=XEnroll classid=clsid:127698e4-e730-4e5c-a2b1-21490a70c8a1
		codebase="xenroll.dll"></OBJECT>
<script type="text/vbscript">
	ON ERROR resume next
		<%=pkcs7ca%>      
	XEnroll.InstallPKCS7 sPKCS7ca
	If err.number = 438 then
		msgbox err.description & err.number
	Elseif err.number <> 0 then
		msgbox err.description & err.number      
	Else
		msgbox "根证书安装成功"     
	End If
</script>
<script type="text/javascript">
<!--
	function pageLoad(){
		var url="<%=request.getContextPath() %>/security/certificate.do?thisAction=downloadCert";
		window.location.href=url;	
	}
//-->
</script>
