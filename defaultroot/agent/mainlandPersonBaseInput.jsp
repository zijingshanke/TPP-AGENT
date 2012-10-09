<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- mainlandPersonBaseInput.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/common.js"></script>
<script type="text/javascript">

	
	
	function check(){
 		var name= document.agent.realName.value;
 		var sid = document.agent.payPassword.value;
 		
 		if(name.length>0 && sid.length>0){
 			document.forms[0].submit();	
 		}else{
 			document.getElementById("error").innerHTML="信息填写不完整！";
 		}
 	}
	
</script>
<body style="text-align: center;">
  <div id="warp" align="left">
  <c:import url="../_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <h1>个人信息确认</h1>
          <html:form action="/agent/agent.do?thisAction=checkCertNum">
          <table cellpadding="0" cellspacing="0" width="80%" class="id_card_table">
          	<tr>
              <td style="width:125px; font-size:14px; text-align:right;"><font color="red">*</font>真实姓名：</td>
              <td><html:text property="realName" styleClass="text_style" /><strong id="error" style="color: red"><c:out value="${smgYanZheng}"/></strong> </td>          
            </tr>
            <tr>
              <td style="width:125px; font-size:14px; text-align:right;"><font color="red">*</font>支付密码：</td>
              <td><html:password property="payPassword" styleClass="text_style"/></td>
            </tr>
            
            <tr>
              <td style="text-align:center;"><input type="button" value="提 交" class="btn1" onclick="check()" /></td>
              <td>&nbsp;</td>
            </tr>
          </table>
          
          </html:form>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
