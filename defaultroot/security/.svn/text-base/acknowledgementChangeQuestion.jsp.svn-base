<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- acknoledgementChangeQuestion.jsp -->
<html>
  <head>
    <title>钱门支付！--网上支付！安全放心！</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
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
  	function updateQuestion(){
  		document.forms[0].action="/security/security.do?thisAction=editSecurityQuestion&isUpdate=yes";
  		document.forms[0].submit();
  	}
  </script>
  <html:form action="security/security.do?thisAction=changeSecurityQuestion">
 <input type="hidden" name="firstquestionPart1" value="<c:out value="${firstquestionPart1}"></c:out>"/>
 <input type="hidden" name="firstquestionPart2" value="<c:out value="${firstquestionPart2}"></c:out>"/>
 <input type="hidden" name="firstquestionPart3" value="<c:out value="${firstquestionPart3}"></c:out>"/>
 <input type="hidden" name="secondquestionPart1" value="<c:out value="${secondquestionPart1}"></c:out>"/>
 <input type="hidden" name="secondquestionPart2" value="<c:out value="${secondquestionPart2}"></c:out>"/>
 <input type="hidden" name="secondquestionPart3" value="<c:out value="${secondquestionPart3}"></c:out>"/>
 <input type="hidden" name="answer1" value="<c:out value="${answer1}"></c:out>"/>
 <input type="hidden" name="answer2" value="<c:out value="${answer2}"></c:out>"/>
 <input type="hidden" name="answer3" value="<c:out value="${answer3}"></c:out>"/>
  <input type="hidden" name="question" value="<c:out value="${question}"></c:out>"/>
 <input type="hidden" name="question1" value="<c:out value="${question1}"></c:out>"/>
 <input type="hidden" name="question2" value="<c:out value="${question2}"></c:out>"/>
         
  <div id="warp">
  <c:import url="../_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
  <div class="id_card">
            <img src="../_img/alreday.gif" />
            <div class="id_card_already_right">

 <h3>设置安全保护问题</h3>

	
		<strong>
					请牢记下列安全保护问题答案！这些答案可用于找回登录密码、支付密码，申请证书，导入证书等！
				</strong>
            </div>
            <div class="clear"></div>
          </div>
 


 <div id="container" class="register">

	  <table width="100%" border="0"  cellpadding="0" cellspacing="0" class="personInfo">
    <tr>
      <td class="tableLT"></td>
      <td class="tableTT"></td>
      <td class="tableRT"></td>
    </tr>
 
    <tr>
      <td class="tableLL"></td>
      <td class="container">
      	<div class="title" align="left"><span>修改密码</span></div>
	  <div>
 <table>
 <tr>
               <th style="width: 400px;" align="center">安全保护问题</th>
               <th   align="center">您的答案</th>
             </tr>
             <tr>
               <td   align="center"><c:out value="${question}"></c:out></td>
               <td   align="center"><c:out value="${answer1}"></c:out></td>
             </tr>
             <tr>
               <td  align="center"><c:out value="${question1}"></c:out></td>
               <td   align="center"><c:out value="${answer2}"></c:out></td>
             </tr>
             <tr>
               <td align="center"><c:out value="${question2}"></c:out></td>
               <td  align="center"><c:out value="${answer3}"></c:out></td>
             </tr>
     <tr align="center"><td colspan="2"  ><span class="simplyBtn_1"><input type="button" style="width: 120px" class="buttom_middle" value="返回修改" onclick="updateQuestion()"/></span>
     <span class="simplyBtn_1"><input style="width: 120px" class="buttom_middle" type="submit" value="确定"/></span>
      </td></tr>
 	           
 </table>
  </div>
      </td>
      <td class="tableRR"></td>
    </tr>

   	           
    <tr>
      <td class="tabelLB"></td>
      <td class="tableBB"></td>
      <td class="tabelRB"></td>
    </tr>
   
  </table>

	</div>
<c:import url="../_jsp/footer.jsp"/>
  </div>

</div>

  </html:form>
  </body>
 
</html>
