<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<script src="../_js/common.js" type="text/javascript"></script>
<script src="../_js/stree/dtree.js"></script>
<script>
<!--
  function saveRight()
  {
     if (confirm("您真的要修改这些权限吗？"))
	 {
	  //  document.forms[0].selectedRightItem.value=sumCheckedBox(document.forms[0].selectedRightItem)	
	   
	//	alert(document.forms[0].selectedRightItem.value);
		
		
	    document.forms[0].thisAction.value="updateAgentUserRight";
		//alert(document.forms[0].selectedRightItem.value);
	    document.forms[0].submit();
	 }
  }
  //用户角色
//--> 
</script>
</head>

<body>
<div id="warp"><c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
<div class="main_top">
<div class="main_bottom">
<div class="main_mid">
<div class="main_mid_title"><strong>设置权限</strong> - <c:out value="${agentUserName}"/></div> 
<html:form action="/right/rightlist.do">
	<html:hidden name="aulf" property="thisAction" />
	<html:hidden name="aulf" property="agentUserId" />
	<xml id="xmlfile">
	<c:out value="${aulf.xml}" escapeXml="false" />
	</xml>
	<table class="table_in">
		<tr>
			<td valign="top"><script language="JavaScript">
<!--
function writeTree()
{
  var doc = new ActiveXObject("Microsoft.XMLDOM");
  var xmldoc;
  doc.async=false;
  doc=xmlfile.XMLDocument
  if (doc.parseError.errorCode!=0)
  {
	var myErr = doc.parseError;
	  alert("You have error " + myErr.reason);
	return;
  }
  xmldoc=doc.documentElement;
  var count=xmldoc.childNodes.length;

  var parentID=0;
  var treeId=0;
  var text="";
  var url="";
  var target="";
  var m=0;
  d = new dTree('d');
  d.add(0,-1,'功能项设定',"",' ?  ','','','');
  for (var j=0;j<count;j++)
  {   
    treeId=xmldoc.childNodes[j].attributes[4].value;
    text=xmldoc.childNodes[j].attributes[0].value;
    url=xmldoc.childNodes[j].attributes[3].value;
   // target=xmldoc.childNodes[j].attributes[2].value;
	d.add(treeId,0,text,url,text,target,'','');
	parentID=treeId
	mlen=xmldoc.childNodes[j].childNodes.length
	for (var m=0;m<mlen;m++)
	{	 
      treeId=xmldoc.childNodes[j].childNodes[m].attributes[4].value;
      text=xmldoc.childNodes[j].childNodes[m].attributes[0].value;
      url=xmldoc.childNodes[j].childNodes[m].attributes[3].value;
     // target=xmldoc.childNodes[j].childNodes[m].attributes[2].value;
	  d.add(treeId,parentID,text,url,text,target,'','');
	  mparentID=treeId
	  klen=xmldoc.childNodes[j].childNodes[m].childNodes.length
	  for (var k=0;k<klen;k++)
	  {	 
		treeId=xmldoc.childNodes[j].childNodes[m].childNodes[k].attributes[4].value;
		text=xmldoc.childNodes[j].childNodes[m].childNodes[k].attributes[0].value;
		url=xmldoc.childNodes[j].childNodes[m].childNodes[k].attributes[3].value;
		//target=xmldoc.childNodes[j].childNodes[m].childNodes[k].attributes[2].value;
		d.add(treeId,mparentID,text,url,text,target,'','');
	  }	
	}	
  }
  document.write(d);
} 
writeTree();
//-->
</script></td>
		</tr>
		<tr>
			<td valign="top">

			<div style="margin-top: 5px;">
				<input type="button" class="btn1" onclick="saveRight();" value="保 存">
			 	<input type="button" class="btn1" onclick="document.forms[0].reset();"value="重 置"> 
			 	<input type="button" class="btn1" onclick="window.history.back();" value="返 回">
			 
			</div>
			</td>
		</tr>
	</table>
</html:form>
</div>
</div>
</div>

<c:import url="/_jsp/footer.jsp" /></div>
<script language="JavaScript">
<!--
  var selectedRightItemsStr ='<c:out value="${aulf.selectedRightItemsStr}"/>';
  var items=selectedRightItemsStr.split(",");


  
  function fullCheckBox()
  {
    if(selectedRightItemsStr=='')
      return;
    var obj=document.forms[0].selectedRightItems;      
    for(var i=0;i<obj.length;i++)
    {
       if (selectValueX(obj[i].value))
         obj[i].checked=true;
      else  
         obj[i].checked=false;     
    }  
  }
  
  function selectValueX(value)
  {    
    for(var j=0;j<items.length;j++)
    {
   
      if(items[j]==value)
        return true;
    }  
    return false;
  }
  
  fullCheckBox();
//-->
</script>
</body>

</html>
