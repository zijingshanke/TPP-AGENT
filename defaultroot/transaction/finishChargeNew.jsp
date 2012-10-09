<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
System.out.println("finishCharge.jsp");
 %>

<script type="text/javascript">
   var myReturnUrl="<c:out value='${param.myReturnUrl}'/>";
    function finish()
    {	    
       myReturnUrl=myReturnUrl.replace("^amp;","&");
       myReturnUrl=myReturnUrl.replace("^","&");
       try
       {
     //  alert(opener.location);
	     if(opener)
	     {
	     //  alert("完成充值，准备在"+opener.location+"刷新主窗口！"+myReturnUrl)
		   opener.location=myReturnUrl;
		   self.close();
		 }
		 else
		   window.location=myReturnUrl;
       }
	   catch(e)
	   {
		 window.location=myReturnUrl;
	   }
    }

    finish();
    </script>
