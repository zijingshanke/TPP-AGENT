<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: agentUser/viewAgentUsers.jsp -->

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	</head>

	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
			<div class="main_top">
				<div class="main_bottom">
					<div class="main_mid">
						<div class="main_mid_title">
							<strong>操作员管理</strong>
						</div>
						<div align="right" style="margin-top: 2px;">
							<input class="btn1" type="button" value="添加操作员" onclick="toAdd()" />
						</div>

						<table cellpadding="0" cellspacing="0" align="center" width="100%"
							class="deal_table"
							style="text-align: center; margin-top: 12px; border-top: 1px solid #d2d2d2;">
							<tr>
								<td>
									序号
								</td>
								<td>
									姓名
								</td>
								<td>
									账号
								</td>
								<td>
									操作
								</td>
							</tr>
							<c:forEach items="${agentUserlist}" var="list" varStatus="count">
								<tr>
									<td>
										<c:out value="${count.count}" />
									</td>
									<td>
										<c:out value="${list.name}" />
									</td>
									<td>
										<c:out value="${list.no}" />
									</td>
									<td>
										<html:link
											page="/agent/agentUser.do?thisAction=editAgentUserPage&agentUserId=${list.id}">[编辑]</html:link>
										|
										<a href="javascript:isDelete('<c:out value="${list.id}" />')">[删除]</a>
										<html:link
											page="/right/rightlist.do?thisAction=editAgentUserRoleRight&agentUserId=${list.id}">[设置权限]</html:link>

									</td>
								</tr>
							</c:forEach>
						</table>

					</div>
				</div>
			</div>

			<c:import url="/_jsp/footer.jsp" />
		</div>
		<script>
function toAdd(){
	window.location.href="../agent/agentUser.do?thisAction=addAgentUserPage"
} 
function isDelete(a){
	if(confirm("是否确定删除？"))
		window.location.href="../agent/agentUser.do?thisAction=deleteAgentUser&agentUserId="+a;

}
</script>
	</body>
</html>
