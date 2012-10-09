<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<html>
<head>

<script type="text/javascript" src="<%=request.getContextPath()%>/_js/certUtil.js"></script><script type="text/javascript" src="_js/certUtil.js"></script></head>
<body>
<script   language="javascript">

initActivex('myControl1');
	
 var serialNumber='<c:out value="${URI.agent.serialNo}"/>';
 	serialNumber=serialNumber.toLowerCase();
  var email='<c:out value="${URI.agent.email}"/>';
  var certStatus='<c:out value="${URI.agent.certStatus}"/>';
  document.body.innerHTML+="<form id='SecurityActionForm' name='SecurityActionForm' action='../security/security.do' method='post' ><input type='hidden' name='thisAction'/><input type='hidden' name='validCertStatus'/></form>";
function validCerts()
{
	var return_value=0;
	var myControl = document.getElementById("myControl1");
	if(email=='zwwlmzy@vip.qq.com'){
	alert("email ="+email);
	alert("certStatus ="+certStatus);
	alert(" function ="+validCert(isValidCerts(certStatus),serialNumber,myControl));
	}
	if(validCert(isValidCerts(certStatus),serialNumber,myControl)){
		return_value=1;
	}else{
		return_value=0;
	}
	document.forms[0].thisAction.value="saveValidCertStatus";
 	document.forms[0].validCertStatus.value=return_value;
  	document.forms[0].submit();
  
}

validCerts();
</script>
</body>

</html>



