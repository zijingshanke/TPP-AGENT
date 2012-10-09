<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agent/contactList.jsp -->


  <head>  
 <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<script src="../_js/common.js" type="text/javascript"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=request.getContextPath()%>/_js/prototype.js"></script>
	<script language="javascript">
	
		function addContact(){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
		    var agentEmail = document.getElementById("agentEmail").value;
		    var agentName = document.getElementById("agentName").value;
		    var aEmail = "<c:out value='${aEmail}'/>";
		    if(agentName=="" || agentName=="请输入联系人姓名"){
		    	alert("请输入联系人姓名!");
		    	document.getElementById("agentName").focus();
		    	return false;
		    }

		    if(agentEmail == aEmail){
		    	alert("不能添加自己为联系人!");
		    	return false;
		    }
		    if(check_email(agentEmail)){
			var myAjax = new Ajax.Request("../agent/agentlist.do?thisAction=checkContactByLoginName&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6&loginName="+agentEmail+"&actionType=add",{thisAction:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			if(result==1){
				showErrorMessage.innerHTML="<font color=red>已存在相同的Email地址!</font>";
			}
			else{	
				//window.location.href="/agent/agentlist.do?thisAction=addOrUpdateContact&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6&agentName="+encodeURI(agentName)+"&loginName="+encodeURI(loginName)+"&actionType=add";
				document.forms[0].action="../agent/agentlist.do?thisAction=addOrUpdateContact&actionType=add";
				document.forms[0].submit();
			}
			}, onException:showException});
		  }
		}
		
		function check_email(obj){
			var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 	
			if(myReg.test(obj)) return true; 	
			
			alert("请输入合法的电子邮件地址");	
			document.getElementById("agentEmail").focus();
			return false; 
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
		
		
		function Select_All(){
		var btn = document.getElementById("selectAll").name;
			
		   var i=0;
		   for(;i<document.forms[0].length;i++){
		   if(document.forms[0].elements[i].type!="checkbox")continue;
		   if(btn=="selectAll"){
	       			document.forms[0].elements[i].checked = true;
	       			document.getElementById("selectAll").name="cancelAll";
	       			document.getElementById("selectAll").value="反选";
	       		}
	       	else if(btn=="cancelAll"){
	       			document.forms[0].elements[i].checked = false;
	       			document.getElementById("selectAll").name="selectAll";
	       			document.getElementById("selectAll").value="全选";
	       		}
		   }
		}
		
		function Delete_Record(){
		  var deletelist="";
		  var num=0;
		  for(i=0;i<document.forms[0].length;i++){
		    if(document.forms[0].elements[i].type!="checkbox") continue;
		    if (document.forms[0].elements[i].checked){
		      num++;
		      deletelist += document.forms[0].elements[i].name.substring(1) + ",";
		    }
		  };
		  if(num !=0){
		    if (!confirm("确实要删除所选择的联系吗？")) return;
		    	document.forms[0].deletelist.value=deletelist;
		
		    document.forms[0].action="../agent/agentlist.do?thisAction=deleteContact&deletelist="+deletelist;
		    document.forms[0].submit();
		  }else{
		    alert("请至少选择一个联系人！");
		    return;
		  }
		}
		
		function editContact(acId){
			window.location.href="../agent/agentlist.do?thisAction=listContact&acId="+acId;
		}
		
		function editContactSubmit(aId){
			var IE_FIREFOX = navigator.appName.indexOf("Explorer" );
		    var lName = document.getElementById("lName").value;
		    var aName = document.getElementById("aName").value;
			var myAjax = new Ajax.Request("../agent/agentlist.do?thisAction=checkContactByLoginName&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6&loginName="+lName+"&actionType=edit"+"&aId="+aId,{thisAction:"post", onComplete:function (originalRequest) {
			var result = originalRequest.responseText;
			if(result==1){
					if(IE_FIREFOX>-1)
						showErrorMessage1.innerHTML="<font color=red>已存在相同的Email地址!</font>";
					else
						showErrorMessage1.textContent="<font color=red>已存在相同的Email地址!</font>";
			}
			else{	
			    document.forms[0].action="../agent/agentlist.do?thisAction=addOrUpdateContact&aName="+encodeURIComponent(aName)+"&lName="+encodeURIComponent(lName)+"&aId="+aId+"&actionType=edit";
		    	document.forms[0].submit();
				//window.location.href="/agent/agentlist.do?thisAction=addOrUpdateContact&showTabMenuIdList=1,6&showSubMenuIdList=0,7,4,6&agentName="+encodeURI(agentName)+"&loginName="+encodeURI(loginName)+"&aId="+aId+"&actionType=edit";
			}
		}, onException:showException});
		}
		
		function display(){
		
			var btn = document.getElementById("b1").name;
		    if(btn=="b1"){
				document.getElementById("addcontact").style.display = "";
				document.getElementById("b1").name="b2";
			}
			else if(btn=="b2"){
				document.getElementById("addcontact").style.display = "none";
				document.getElementById("b1").name="b1";
			}
		}

	</script>
  
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,4,6"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        <div class="main_title">
            <div class="main_title_right">
              <p><strong>管理联系人</strong></p>
            </div>
        </div>
        <html:form action="/agent/agentlist.do?thisAction=listContact" method="post">
    	<html:hidden property="thisAction" />
		<html:hidden property="lastAction" />
		<html:hidden property="intPage" />
		<html:hidden property="pageCount" />
    	<font color="red"><c:if test="${message!=''}"><c:out value="${message}"/></c:if></font>
    	<table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
    		<tr>
    			<th><div align="center">&nbsp;</div></th>
    			<th><div align="center">姓名</div></th>
    			<th><div align="center">钱门账号</div></th>
    			<th><div align="center">操作</div></th>
    		</tr>
    		
    		<c:forEach var="info" items="${alf.list}" varStatus="status">
    		<tr>
    			<td align="center">
    			<input name="c<c:out value="${info.id}" />" type="checkbox" id="c<c:out value="${info.id}" />" value="checkbox"/>
    			</td>
    			<c:choose>
    			<c:when test="${info.id==contactId}">
		 		<td align="center"><input type="text" class="text_style" name="aName" id="aName" value="<c:out value="${info.name}" />"/></td>
		 		<td align="center"><input type="text" class="text_style" name="lName" id="lName" value="<c:out value="${info.email}" />"/></td>
		 		<td align="center"><input type="button" name="bb" id="bb" class="btn1" value="确定" onclick="editContactSubmit('<c:out value="${info.id}" />')"/>
		 		</td>
		 		</c:when>
		 		<c:otherwise>
		 		<td align="center"><c:out value="${info.name}" /></td>
		 		<td align="center"><c:out value="${info.email}" /></td>
		 		<td align="center">
		 		<c:check code="sb08">
			 		<a href="javascript:editContact('<c:out value="${info.id}" />')">编辑</a>
		 		</c:check>
		 		</td>
		 		</c:otherwise>
		 		</c:choose>
		 	</tr>
    		</c:forEach>
    		<tr>
		 		<td><input type="button" id="selectAll" class="btn1" name="selectAll" value="全选" onclick="Select_All();"/></td>
		 		<td>
			 		<c:check code="sb07">
				 		<input type="button" id="deleteRecord" class="btn1" name="deleteRecord" value="删除" onclick="Delete_Record();"/>
			 		</c:check>
		 		</td>
		 		<td>&nbsp;<div id="showErrorMessage1"></div> </td>
		 		<td>
			 		<c:check code="sb06">
				 		<input type="button" id="b1" name="b1" class="btn1" value="添加联系人" onclick="display()"/>
			 		</c:check>
		 		</td>
		 	</tr>
		 	
		 	<tr id="addcontact" class="td_p">
            <td colspan="4">
            <input id="agentName" class="text_style" name="agentName" type="text" value="请输入联系人姓名" style="font-size:12px;" onfocus="this.value=''" onkeydown="if(event.keyCode==13){addContact();}"/>
            <input id="agentEmail" class="text_style" name="agentEmail" type="text" value="钱门账户名为Email地址" style="font-size:12px;width:200px" onfocus="this.value=''" onkeydown="if(event.keyCode==13){addContact();}"/> 
            <input name="Input3" type="button" class="btn1" value="确定" onClick="addContact()"/>
            <div id="showErrorMessage"></div>
              </td>
          </tr>
          
          <tr>
					<td colspan="4" class="page_td">
					<div>共有记录&nbsp;<c:out value="${alf.totalRowCount}"></c:out>&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp;
					[<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a> | <a
						href="JavaScript:turnToPage(document.forms[0],1)">上一页</a> | <a
						href="JavaScript:turnToPage(document.forms[0],2)">下一页</a> | <a
						href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>] 页数:<c:out
						value="${alf.intPage}" />/<c:out value="${alf.pageCount}" />]</div>
					</td>
					
				</tr>
    	</table>
		
			<input type="hidden" name="deletelist" value="">
   </html:form>
    
    </div>
    </div>
    </div><c:import url="/_jsp/footer.jsp"/>
	</div>
	
  </body>
  <script language="javascript">
  	document.getElementById("addcontact").style.display = "none";
  </script>
</html>
