<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
<head>
<TITLE>上传附件</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<link href="../_css/css.css" rel="stylesheet" type="text/css">
<script src="../_js/prototype.js" type="text/javascript"></script>
<script src="../_js/common.js" type="text/javascript"></script>


<script language="javascript">

 function submitForm()
 {
    if (valid())
    {
      document.forms[0].thisAction.value="add";
  	  document.forms[0].submit();	
  	}
 }
 
 function valid()
 {
   if(document.forms[0].uploadFile.value=="")
   {
     alert("附件名不能为空！");
     document.forms[0].uploadFile.focus();
     return false;
   }
   else
     return true; 
 }
 
 
 function del()
 {
   document.forms[0].thisAction.value="delete";
   document.forms[0].submit();	
 }
 
 function finish()
 {
  var tempAttach=$('div_attachment').innerHTML;
  var listAttachName=document.forms[0].listAttachName.value;
  var listAttachNum='<c:out value="${uf.listAttachNum}"/>';
  if($('filekey')) {
  	var vname = $('fileKey').value;
  }
  closeme(listAttachName,tempAttach,listAttachNum, vname);
}


  function closeme(listAttachName,_tempAttach,listAttachNum, vname)
  {
    if(window.opener!= null)
	{
	  if(!window.opener.getAttachs(listAttachName,_tempAttach,listAttachNum, vname))
	  {
	    window.focus();
	    return;
	  }	  
	}
	window.opener=null;
	window.close();     
  }

</script>
</head>

<body class="body_m">

<c:import url="/_jsp/mainTitle.jsp?title=上传附件" charEncoding="UTF-8"/>


<html:form  method="post" action="/upload/upload" enctype="multipart/form-data">
	<html:hidden property="thisAction" value="editattach" />
	<html:hidden property="path" />
	<html:hidden property="listAttachName" />

	<table width="100%" cellspacing="1" class="table_li">
		<tr>
			<td class="fbg">添加附件</td>
			<td colspan="3" class="fbg2">
				<html:file property="uploadFile" styleClass="colorblue2 p_5" size="40" />
				<input type="button" class="button1" value="上 传" onclick="submitForm();">
			</td>
		</tr>
		<tr>
			<td class="fbg">已经上传附件</td>
			<td colspan="3" class="fbg2"><c:if test="${uf.listAttach!=null}">
				<div><c:forEach var="info" items="${uf.listAttach}"
					varStatus="status">
					<html:radio property="fileKey" value="${info.vname}" />
					<c:out value="${status.count}. ${info.name}  [${info.sizeOnKB}KB]" /> 
					<br>
				</c:forEach></div>
				<span><a href="javascript:del()">删除</a>
			</c:if>
			</div>
			</td>
		</tr>
	</table>

	<div id="div_attachment"><c:if test="${uf.listAttach!=null}">
		<c:forEach var="info" items="${uf.listAttach}" varStatus="status">
			<c:out value="${status.count}. ${info.name}  [${info.sizeOnKB}KB]" />
			<br>
		</c:forEach>

	</c:if></div>

	<table width="100%" style="margin-top: 5px;">
		<tr>
			<td><input name="label" type="button" class="button1"
				value="完 成" onclick="finish();"> <input type="button" class="button1" value="返 回"
				onclick="window.history.back();">
		</tr>
	</table>
</html:form>

</body>
</html>
