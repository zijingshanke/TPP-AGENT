<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: viewBlackscreenPay.jsp -->
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css2/reset.css" type="text/css"></link>
	<link rel="stylesheet" href="../_css2/phone.css" type="text/css"></link></head>

	<body onpaste="return false;">
		<div id="warp">
		  <c:import url="/_jsp/topMenu.jsp?a=1,7&b=0,8,1,1" />
		  <div>&nbsp;</div>
			<div id="maincontent">
    <div class="mainimg">
        <p><a href="#"><img src="1_02.gif"></img></a></p>
    </div>
    <div class="Prepaidcont">
    	<img src="../_img/mobile/Process.jpg"/>
        <div class="content">
	        <h3>黑屏充值</h3>
            	  <html:form action="/blackscreen/blackscreenpay.do?thisAction=processOrder">
		  <table width="90%" border="0" cellpadding="0" cellspacing="0" class="inputlist">
	
              <tr>
                <td>黑屏账号:</td>
                <td><html:text property="blackscreenAccount"/></td>
              </tr>
              <tr>
                <td>再一次输入黑屏账号:</td>
                <td><html:text property="blackscreenAccount2" /></td>
              </tr>
              <tr>
                <td>充值金额:</td>
                <td>
 
  <input type="text" class="text_style" id="resultAmount" name="resultAmount" style="width:75px;" maxlength="12" onKeyUp="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"/> 元  
                </td>
              </tr>
              <tr>
              	<td colspan="2"><font color="red"><c:out value="${error}"></c:out></font></td>
              </tr>
              <tr>
                <td colspan="2" align="center"><input type="button" name="button" class="topup_btn" value="确 认" onclick="check();" /></td>
              </tr>
            </table>
            </html:form>
        </div>
                <div class="confoot" style="display: none;">
            
                </div>
    </div>
    <div id="askedquestions" >
    	<p><a href="#"><img src="1_01.gif"></img></a>
        	</p>
        
    </div>
</div>
		<c:import url="../_jsp/footer.jsp"></c:import>

		</div>

	</body>
<script>


	function check(){
		
		var mobilenum = document.blackscreen.blackscreenAccount.value;
		var mobilenum2=document.blackscreen.blackscreenAccount2.value;
	    var prodContent = document.getElementById("resultAmount").value;
		

		
		if(mobilenum==""||mobilenum.length==0){
			alert("请输入充值黑屏账号");
			document.blackscreen.blackscreenAccount.focus();
			return;
		}else if(mobilenum2==""||mobilenum2.length==0){
			alert("请再次输入充值黑屏账号");
			document.blackscreen.blackscreenAccount2.focus();
			return;
		}else if(mobilenum!=mobilenum2){
			alert("二次输入不一致!请认真填写!");
			document.blackscreen.blackscreenAccount2.focus();
			return;
		}else if(prodContent==""){
		   alert("请输入充值金额!");
		   document.blackscreen.resultAmount.focus();
		   return;
		}
		else{
		           var re=/^([1-9][0-9]*|0)(\.[0-9]{0,2})?$/;
					if(!re.test(prodContent)){
						alert("金额只能由数字组成且最多带有2位小数");
						 document.blackscreen.resultAmount.focus();
						return false;
					}
					if(prodContent<=0.01||prodContent>10000000.00){
						alert("金额只能大于0.01及小于10000000.00");
						document.blackscreen.resultAmount.focus();
						return false;
					}
			document.blackscreen.submit();
		}
	}
</script>
</html>
