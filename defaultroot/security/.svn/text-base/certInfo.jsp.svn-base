<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="SecurityActionForm" scope="request"
	type="com.fordays.fdpay.security.action.SecurityActionForm" />
	<!-- certInfo.jsp -->
<head>
<title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />
</head>
	<table class="popTable" style="padding: 4px 0;">
        	<tr>
            	<td style="padding: 8px 3px; text-align:right;" nowrap="nowrap" >姓名：</td>
                <td><c:out value="${SecurityActionForm.userName}" /></td>
          	</tr>
            <tr>
            	<td style="padding: 8px 3px; text-align:right;" nowrap="nowrap" >证书序列号：</td>
                <td><c:out value="${SecurityActionForm.serialNumber}" /></td>
          	</tr>
            <tr>
            	<td style="padding: 8px 3px; text-align:right;" nowrap="nowrap" >证书颁发者：</td>
                <td><c:out value="${SecurityActionForm.issuerDN}" /></td>
   	    	</tr>
            <tr>
            	<td style="padding: 8px 3px; text-align:right;" nowrap="nowrap" >证书持有者：</td>
                <td><c:out value="${SecurityActionForm.subjectDN}" /></td>
   	    	</tr>
            <tr>
            	<td style="padding: 8px 3px; text-align:right;" nowrap="nowrap" >证书状态：</td>
                <td><c:out value="${SecurityActionForm.status}" /></td>
       	 	</tr>
            <tr>
            	<td style="padding: 8px 3px; text-align:right;" nowrap="nowrap" >有效日期：</td>
                <td><c:out value="${SecurityActionForm.start}" />&nbsp;&nbsp;-&nbsp;&nbsp;<c:out value="${SecurityActionForm.end}" /></td>
       	 	</tr>
        </table>
</html>
