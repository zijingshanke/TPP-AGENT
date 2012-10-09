<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钱门支付！--网上支付！安全放心！</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
<script src="../_js/common.js" type="text/javascript"></script>
</head>
<body>
<form action="">
	<br>
	<h4 align="center">分润/代缴信息</h4>
	<hr>
	<p>总笔数: <c:out value="${totalNum}"/> 笔</p>
	<p>总支出: <fmt:formatNumber pattern="0.00" value="${totalSum}" /> 元</p>
	<p>详细分润记录:</p>
	<table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="word-break :break-all">
		<tr>
			<th width="20%"><div align="center">外部订单号</div></th>
			<th width="20%"><div align="center">时间</div></th>
			<th width="20%"><div align="center">对方信息</div></th>
			<th width="20%"><div align="center">收入/支出</div></th>
			<th width="20%"><div align="center">备注</div></th>
		</tr>
		<c:forEach var="info" items="${tlf.list}" varStatus="statu">
		<tr>
			<td><c:out value="${info.orderDetails.orderNo}"/></td>
			<td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.accountDate}" /></td>
			<td><c:out value="${info.toAgent.name}"/> (<c:out value="${info.toAgent.loginName}"/>)</td>
			<td>-<fmt:formatNumber pattern="0.00" value="${info.amount}" /></td>
			<td><c:out value="${info.orderDetails.shopName}"/></td>
		</tr>
		</c:forEach>
	</table>
</form>
</div>
</html>