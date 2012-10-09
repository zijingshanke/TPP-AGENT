<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<meta http-equiv=content-type content="text/html; charset=UTF-8">
<link href="../_css/css.css" rel="stylesheet" type="text/css" />
<c:if test="${URI==null}">
	<script language="JavaScript">
   	top.location="../login.jsp" 
	</script>
</c:if>

<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td height="30" style="border-bottom: #3366FF 1px solid;">
			<div id="divTitle"></div>
		</td>
	</tr>
</table>

<script type="text/javascript">
var title = "<c:out value="${param.title}" />";
var arrs = title.split('_');
var html = "<img src='../_img/145.gif'>&nbsp;";
var len = arrs.length;
for(var i=0; i<len; i++) {
	if(i == len - 1) {
		html += "<span class='title_font'>" + arrs[i] + "</span>";
	}else {
		html += "<span class='title_font'>" + arrs[i] + "</span>&nbsp;>>";
	}
}
document.getElementById("divTitle").innerHTML = html;
</script>

