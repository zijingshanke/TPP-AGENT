<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: security/editSecurityQuestion.jsp -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		
		
		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/_js/jquery-1.3.1.min.js"></script>
	<style>
	.id_card {
		border:1px solid #FFD327;
		background:#FFFFEE;
		padding:15px 0 15px 15px;
		margin-top:10px;
	}	
	</style>
	</head>
	<body>
		<script>

function validateBrith(str){
	var patn = /^[0-9]{8}$/;
	if(patn.test(str)) return 0;
	return 1;
}	

function selectChange(n){
	var q2 = document.getElementById("secondquestionPart"+n).value;
	 var msg=document.getElementById("msg"+n);
	if(q2=="生日"){
 	 msg.innerHTML="日期的格式是:YYYYMMDD（如:19810101）";}else{
 	 msg.innerHTML="";
  }
  }
function check(){
	var q1=document.getElementById("secondquestionPart1").value;
	var q2=document.getElementById("secondquestionPart2").value;
	var q3=document.getElementById("secondquestionPart3").value;
	if(q1=="生日"){
	 	var brith=validateBrith(document.getElementById("answer1").value);
	 	 if(brith==1){
	 	 	document.getElementById("answer1").value="日期有误";
	 	 }
	}
	if(q2=="生日"){
		var  brith=validateBrith(document.getElementById("answer2").value);
	 	 if(brith==1){
	  	document.getElementById("answer2").value="日期有误";
		  }
	}
	if(q3=="生日"){
	var  brith=validateBrith(document.getElementById("answer3").value);
	  if(brith==1){
	  	document.getElementById("answer3").value="日期有误";
	  }
	}
	document.forms[0].submit();
	
}


</script>

		<html:form action="/security/security.do?thisAction=editSecurityQuestion">
<div id="warp" >
<c:import url="../_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
 <div class="id_card">
            <img src="../_img/alreday.gif" />
            <div class="id_card_already_right">
<c:if test="${error eq 'none'}">
			您需要正确设置3个安全保护问题
			<ul>
				<li>
					安全保护问题可用于找回登录密码、找回支付密码、申请证书、导入证书等。
				</li>
				<li>
					本次设置的安全保护问题将会覆盖原有的安全保护问题。
				</li>
				<li>
					您总共需要设置
					<strong style="color: red; margin: 0 0.5em;">3</strong>个安全保护问题。
				</li>
			</ul>
			</c:if>
			<c:if test="${error2 eq 'errortype2'}">
				<h5>请选择不同的问题，这样能提高您的账户安全性.</h5>
			</c:if>
			<c:if test="${error3 eq 'errortype3'}">
				<h5>请填写不同的答案，这样能提高您的账户安全性。</h5>
			</c:if>
			
					<c:if test="${error1 eq 'errortype1'}">
			
			<ul>
				<c:forEach var="error" items="${list}">
				<li><font color="red"><c:out value="${error[0]}"></c:out></font><c:out value="${error[1]}"></c:out></li>
				</c:forEach>
			</ul>
			</c:if>

            </div>
            <div class="clear"></div>
          </div>
   <div id="container" class="register">
	<html:form action="/agent/agent.do?thisAction=updateTelephone">
	<html:hidden property="id" value="${agent.id}"></html:hidden>
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="personInfo">
    <tr>
      <td class="tableLT"></td>
      <td class="tableTT"></td>
      <td class="tableRT"></td>
    </tr>
 
    <tr>
      <td class="tableLL"></td>
      <td class="container">
      	<div class="title"><span>修改联系电话</span></div>
        <table>


<tr><td>1</td><td>我</td><td><select id="firstquestionPart1" name="firstquestionPart1">
<option selected="selected" value="请选择">请选择</option>
<option value="爸爸" >爸爸</option>
                    <option value="妈妈" >妈妈</option>
                    <option value="奶奶" >奶奶</option>
                    <option value="爷爷" >爷爷</option>
                    <option value="外公" >外公</option>
                    <option value="外婆" >外婆</option>
                    <option value="丈夫" >丈夫</option>
                    <option value="妻子" >妻子</option>
                    <option value="姐姐" >姐姐</option>
                    <option value="妹妹" >妹妹</option>
                    <option value="哥哥" >哥哥</option>
                    <option value="弟弟" >弟弟</option>
                    <option value="儿子" >儿子</option>
                    <option value="女儿" >女儿</option>
</select></td><td>的</td><td>
<select id="secondquestionPart1" onchange="selectChange(1)" name="secondquestionPart1">
<option selected="selected" value="请选择">请选择</option>
					<option value="名字">名字</option>
					<option value="生日" >生日</option>
					<option value="出生地" >出生地</option>
</select>

</td>
<td>是:</td>
<td><input type="text" class="text_style" id="answer1" name="answer1"></input></td>
<td><div id="msg1" style="color: red;"></div></td>
</tr>


<tr><td>2</td><td>我</td><td><select id="firstquestionPart2" name="firstquestionPart2">
<option selected="selected" value="请选择">请选择</option>
<option value="爸爸" >爸爸</option>
                    <option value="妈妈" >妈妈</option>
                    <option value="奶奶" >奶奶</option>
                    <option value="爷爷" >爷爷</option>
                    <option value="外公" >外公</option>
                    <option value="外婆" >外婆</option>
                    <option value="丈夫" >丈夫</option>
                    <option value="妻子" >妻子</option>
                    <option value="姐姐" >姐姐</option>
                    <option value="妹妹" >妹妹</option>
                    <option value="哥哥" >哥哥</option>
                    <option value="弟弟" >弟弟</option>
                    <option value="儿子" >儿子</option>
                    <option value="女儿" >女儿</option>
</select></td><td>的</td><td>
<select id="secondquestionPart2" onchange="selectChange(2)" name="secondquestionPart2">
<option selected="selected" value="请选择">请选择</option>
					<option value="名字">名字</option>
					<option value="生日" >生日</option>
					<option value="出生地" >出生地</option>
</select>

</td>
<td>是:</td>
<td><input type="text" class="text_style" id="answer2" name="answer2"></input></td>
<td><div id="msg2" style="color: red;"></div></td>
</tr>

<tr><td>3</td><td>我</td><td><select id="firstquestionPart3" name="firstquestionPart3">
<option selected="selected" value="请选择">请选择</option>
<option value="爸爸" >爸爸</option>
                    <option value="妈妈" >妈妈</option>
                    <option value="奶奶" >奶奶</option>
                    <option value="爷爷" >爷爷</option>
                    <option value="外公" >外公</option>
                    <option value="外婆" >外婆</option>
                    <option value="丈夫" >丈夫</option>
                    <option value="妻子" >妻子</option>
                    <option value="姐姐" >姐姐</option>
                    <option value="妹妹" >妹妹</option>
                    <option value="哥哥" >哥哥</option>
                    <option value="弟弟" >弟弟</option>
                    <option value="儿子" >儿子</option>
                    <option value="女儿" >女儿</option>
</select></td><td>的</td><td>
<select id="secondquestionPart3" onchange="selectChange(3)" name="secondquestionPart3">
<option selected="selected" value="请选择">请选择</option>
					<option value="名字">名字</option>
					<option value="生日" >生日</option>
					<option value="出生地" >出生地</option>
</select>

</td>
<td>是:</td>
<td><input type="text" class="text_style" id="answer3" name="answer3"></input></td>
<td><div id="msg3" style="color: red;"></div></td>
</tr>
 <tr style="text-align: center;"><td colspan="3" ><span class="simplyBtn_1"><input type="button" style="width: 120px" class="buttom_middle" value="确定" onclick="check()" /></span></td></tr>
</table>				
      </td>
      <td class="tableRR"></td>
    </tr>
    
    <tr>
      <td class="tabelLB"></td>
      <td class="tableBB"></td>
      <td class="tabelRB"></td>
    </tr>
   
  </table>
	</html:form>
	</div>
	<c:import url="../_jsp/footer.jsp"/>
	</div>
	
<c:if test="${error1 eq 'errortype1' or error2 eq 'errortype2' or error3 eq 'errortype3' or isUpdate eq 'yes'}">
<script>
document.getElementById("firstquestionPart1").value="<c:out value="${firstquestionPart1}"></c:out>";
document.getElementById("firstquestionPart2").value="<c:out value="${firstquestionPart2}"></c:out>";
document.getElementById("firstquestionPart3").value="<c:out value="${firstquestionPart3}"></c:out>";
document.getElementById("secondquestionPart1").value="<c:out value="${secondquestionPart1}"></c:out>";
document.getElementById("secondquestionPart2").value="<c:out value="${secondquestionPart2}"></c:out>";
document.getElementById("secondquestionPart3").value="<c:out value="${secondquestionPart3}"></c:out>";
document.getElementById("answer1").value="<c:out value="${answer1}"></c:out>";
document.getElementById("answer2").value="<c:out value="${answer2}"></c:out>";
document.getElementById("answer3").value="<c:out value="${answer3}"></c:out>";
</script>
</c:if>
</html:form>

</div>
</body>


</html>
