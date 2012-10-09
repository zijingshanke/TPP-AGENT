<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: agent/agentAddressManage.jsp -->

  <head> 
   <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript">
	
	   function jsTrim(str){
	    var re;
	    re = /^[ \t]*|[ \t]*$/g;
	    str = str.replace(re, '');
	    var p=/\s/g;
	    str=str.replace(p,"");
	    return(str);
	   }
	   
		function saveOrUpdate(aid){
		    var name = document.getElementById("name");
		    var address = document.getElementById("address");
		    var postCode = document.getElementById("postCode");
		    var tel = document.getElementById("tel");
		    var mtel = document.getElementById("mtel");
		    
		    if(jsTrim(name.value)==""){
		    	alert("请输入姓名!");
		    	name.value="";
		    	name.focus();
		    	return false;
		    }
		    
		    if(jsTrim(address.value)==""){
		    	alert("请输入所在地区!");
		    	address.value="";
		    	address.focus();
		    	return false;
		    }
		    
		    if(jsTrim(postCode.value)==""){
		    	alert("请输入邮政编码!");
		    	postCode.value="";
		    	postCode.focus();
		    	return false;
		    }
		    
		    if(jsTrim(tel.value)=="" && jsTrim(mtel.value)==""){
		    	alert("电话号码和手机号码请至少填一项!");
		    	tel.value="";
		    	mtel.value="";
		    	tel.focus();
		    	return false;
		    }
		    
			if(aid==0){ //添加
				document.agentAddress.action="../agent/agentaddress.do?thisAction=addAgentAddress&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3";
			}
			else{  //修改
				document.agentAddress.action="../agent/agentaddress.do?thisAction=updateAgentAddressById&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3&aid="+aid;
			}
			document.agentAddress.submit();
		}
		
		function deleteAgentAddress(aid){
			if (!confirm("确实要删除此数据吗？")) return;
			
			window.location.href="../agent/agentaddress.do?thisAction=deleteAgentAddress&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3&aid="+aid;
		}
		
		function setDefaultAddress(aid){
			window.location.href="../agent/agentaddress.do?thisAction=setDefaultAddress&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3&aid="+aid;
		}
	</script>
  
  </head>

  <body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,3&b=0,4,1,3"/>
  <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
        <div class="main_title2">
            <span class="font_style7">交易地址管理</span>
          </div>
    <html:form action="agent/agentaddress.do?thisAction=addAgentAddress&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3" method="post">
		<font color="red"><c:if test="${message!=''}"><c:out value="${message}"/></c:if></font>
		<table cellpadding="0" cellspacing="0" width="100%" class="deal_table">
				<tr> 
					<th><div align="center">设为默认</div></th>
					<th><div align="center">姓名</div></th>
					<th><div align="center">所在地址</div></th>
					<th><div align="center">邮政编码</div></th>
					<th><div align="center">电话号码</div></th>
					<th><div align="center">手机号码</div></th>
					<th><div align="center">操作</div></th>
				</tr>
			<c:forEach var="info" items="${addresslist}" varStatus="status">
		    <tr> 
				<td align="center">
				<c:if test="${info.status==1}">
				<input name="rbutton" type="radio" value="<c:out value="${info.id}" />" onclick="setDefaultAddress('<c:out value="${info.id}" />')" checked="checked">
				</c:if>
				<c:if test="${info.status!=1}">
				<input name="rbutton" type="radio" value="<c:out value="${info.id}" />" onclick="setDefaultAddress('<c:out value="${info.id}" />')">
				</c:if>
				</td>
				<td align="center"><c:out value="${info.name}" /></td>
				<td align="center"><c:out value="${info.address}" /></td>
				<td align="center"><c:out value="${info.postCode}" /></td>
				<td align="center"><c:out value="${info.tel}" /></td>
				<td align="center"><c:out value="${info.mtel}" /></td>
				<td align="center"><a href="../agent/agentaddress.do?thisAction=editAgentAddressById&showTabMenuIdList=1,3&showSubMenuIdList=0,4,1,3&id=<c:out value="${info.id}" />">修改</a> | <a href="javascript:deleteAgentAddress('<c:out value="${info.id}" />')">删除</a></td>
		   </tr>
		   </c:forEach>
		</table>
		<p>
		
            <span class="font_style7">编辑地址</span>
            
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:20px;">
		<tr> 
			<td class=h3><b>您可以最多添加3个地址。电话号码和手机号码请至少填一项。</b>
			</td>
			</tr>
			<tr id="provCityErrorInfo" style="display:none"> 
				<td class=h3><font color="red">如果您无法在下拉框内选择省市区信息，请准确填写“邮政编码”，以便系统解析您的地址。</font></td>
			</tr>
			</table>
			
			<table border="0" cellpadding="4" cellspacing="4" class="formM" width="100%">
			<tr> 
			<td class="form-left" align="right">姓名：<span><font color="red">*</font></span></td>
			<td class="form-right">
			<input type="text" class="text_style" id="name" name="name" value="<c:out value="${agAddress.name}" />">	
			</td>
			</tr>
			
			<tr> 
			<td class="form-left" align="right">所在地区：<span><font color="red">*</font></span></td>
			<td><input type="text" class="text_style" name="address" id="address" value="<c:out value="${agAddress.address}" />"></td>
			</tr>
			
			<tr> 
			<td class="form-left" align="right">邮政编码：<span><font color="red">*</font></td>
			<td class="form-right"><input type="text" class="text_style" name="postCode" id="postCode" value="<c:out value="${agAddress.postCode}" />"/></td>
			</tr>
			
			<tr>
				<td class="form-left" align="right">电话号码：</td>
				<td class="form-right">
				<table border="0" cellspacing="0" cellpadding="0">
				<tr><td><input name="tel" id="tel" class="text_style" type="text"  value="<c:out value="${agAddress.tel}" />"></td>
				<td id="Changecontentwrite1"><span> &nbsp;&nbsp;请按照此格式填写：0756-26888888&nbsp;&nbsp;</span></td><td>    
				</td></tr></table></td>
			</tr>
			<tr> 
				<td class="form-left" align="right">手机号码：</td>
				<td class="form-right"><input type="text" class="text_style" name="mtel" id="mtel" value="<c:out value="${agAddress.mtel}" />"></td>
			</tr>
			
			<tr> 
			<td>&nbsp;</td>
			<td>
			<c:choose>
			<c:when test="${flag=='edit'}">
				<input name="b1" type="button" value="保存修改" class="btn1" onclick="saveOrUpdate('<c:out value="${agAddress.id}" />')"/>
			</c:when>
			<c:otherwise>
				<c:if test="${listcount>=3}">
			   		<input name="b2" type="button" class="btn1" value="添加地址" disabled="disabled"/>
			   	</c:if>
			   <c:if test="${listcount<3}">
					<input name="b2" type="button" class="btn1" value="添加地址" onclick="saveOrUpdate(0)"/>
			   </c:if>
			</c:otherwise>
			</c:choose>
			</td>
	        </tr>
	        </table>
	       
	</html:form>

</div>
</div>
</div>
<c:import url="/_jsp/footer.jsp"/>
</div>

</body>
</html>
