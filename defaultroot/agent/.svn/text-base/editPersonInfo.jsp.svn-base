<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- editPersonInfo.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/common.js"></script></head>
  </head>
  <script type="text/javascript">
 	function check(){
 		var name= document.agent.name.value;
 		var sid = document.agent.certNum.value;
 		var msg = document.getElementById("showmsg");
 		var companyName=document.agent.companyName;
		if(companyName!=undefined){
			if(companyName.value.length==0){
				msg.innerHTML="企业名称不能为空!请正确填写!";
				return;
			}
		}
		if(name.length>0){	
			if(!verifyIdentity(sid)){
				 msg.innerHTML="身份证的格式不正确，请认真填写!";
			}else{
				document.forms[0].submit();
			} 			
 		}else{
	 			msg.innerHTML="姓名不能为空!请正确填写!";
	 	}	
 	}
 	function verifyIdentity(_strIdNum)
{
	var isCard=/^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/;
	
	if (isCard.test(_strIdNum)||(/(\d)(?=(\d\d\d)+(?!\d))/g.test(_strIdNum)&&_strIdNum.length==15))
	{    
		return  true;
	}
	return false;
}  
  </script>
  <body style="text-align: center;">
  <html:form action="/agent/agent.do">
  <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
  <input type="hidden" name="isUpdate" value="yes"/>
  <html:hidden property="thisAction" value="editPersonInfo"></html:hidden>
   <html:hidden property="address" value="${agent.address}"></html:hidden>
      <html:hidden property="registerType" value="${agent.registerType}"></html:hidden>
   <html:hidden property="telephone" value="${agent.telephone}"></html:hidden>
    <html:hidden property="mobile" value="${agent.mobile}"></html:hidden>
    <html:hidden property="loginName" value="${agent.loginName}"></html:hidden>
       <html:hidden property="provinceId" value="${selectPid}"></html:hidden>
    <html:hidden property="cityId" value="${selectCid}"></html:hidden>
    <html:hidden property="bankId" value="${selectBid}"></html:hidden>
      	  <html:hidden property="account.cardNo" value="${agent.account.cardNo}"></html:hidden>     
  	  <html:hidden property="account.accountAddress" value="${agent.account.accountAddress}"></html:hidden>  
  <div id="warp" align="left">
    <c:import url="/_jsp/certifyTop.jsp"/>  
    <div style="padding:8px;"></div>
    <div class="main_top" style="margin-top:0">
      <div class="main_bottom">
        <div class="main_mid">
         <table>
         <tr><td>修改个人信息</td><td></td></tr>
         <tr><td>钱门帐号:</td><td><c:out value="${URI.agent.loginName}"></c:out></td></tr>
           <c:if test="${agent.registerType==1}">
         <tr><td>企业名称:</td><td><html:text property="companyName" value="${agent.companyName}"></html:text></td></tr>
         </c:if>
         <tr><td> <c:if test="${agent.registerType!=1}">用户姓名:</c:if><c:if test="${agent.registerType==1}">法人姓名:</c:if></td><td><html:text property="name" value="${agent.name}"></html:text></td></tr>
         <tr><td><c:if test="${agent.registerType!=1}">身份证号:</c:if><c:if test="${agent.registerType==1}">法人身份证号:</c:if></td><td><html:text property="certNum" value="${agent.certNum}"></html:text></td></tr>
         </table>
         <div id="showmsg"> <font color="red">*以上信息请仔细如实的填写!</font></div>
        <input type="button" value="确定" onclick="check()" class="btn1"/>
        <input type="button" value="取消" onclick="back()" class="btn1"/>
        <script type="text/javascript">
        	function back(){
        		document.forms[0].reset();
        		document.forms[0].isUpdate.value="no"
        		document.forms[0].submit();
        	}
        </script>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
  </html:form>
</body>
</html>
