<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!-- editAgentInfo.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/common.js"></script></head>
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
				document.agent.submit();
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
  <body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>修改个人信息</strong><a href="agent.do?thisAction=viewMyAgent" class="a_style1">返回我的账户</a></p>
            </div>
          </div>
  <html:form action="/agent/agent.do">
  	<html:hidden property="id" value="${agent.id}"></html:hidden>
    <html:hidden property="registerType" value="${agent.registerType}"></html:hidden>
  	<html:hidden property="thisAction" value="updateAgentInfo"></html:hidden>
         <table cellpadding="0" cellspacing="0" width="100%" class="information_table" style="text-align: left">
         <tr><td class="right_td">钱门帐号:</td><td><span class="font_style2"><c:out value="${agent.loginName}"></c:out></span></td></tr>
         <c:if test="${agent.registerType==1}">
         <tr><td>企业名称:</td><td><html:text property="companyName" value="${agent.companyName}"></html:text></td></tr>
         </c:if>
         <tr><td class="right_td"><c:if test="${agent.registerType!=1}">用户姓名:</c:if><c:if test="${agent.registerType==1}">法人姓名:</c:if></td><td><html:text property="name" value="${agent.name}"></html:text></td></tr>
         <tr><td class="right_td"><c:if test="${agent.registerType!=1}">身份证号:</c:if><c:if test="${agent.registerType==1}">法人身份证号:</c:if></td><td><html:text property="certNum" value="${agent.certNum}"></html:text></td></tr>
         	<tr><td colspan="2">
          	<font id="showmsg" color="red">以上信息请仔细如实的填写!</font>
           </td></tr>
           <tr><td  class="right_td">
        <input type="button" value="确定" onclick="check()" class="btn1"/>
        </td><td>
        <input type="button" value="取消" onclick="back()" class="btn1"/>
         </td></tr>
         </table>
        <script type="text/javascript">
        	function back(){
        		document.forms[0].reset();
        		document.forms[0].thisAction.value="viewMyAgent";
        		document.forms[0].submit();
        	}
        </script>
         </html:form>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
 
</body>
</html>
