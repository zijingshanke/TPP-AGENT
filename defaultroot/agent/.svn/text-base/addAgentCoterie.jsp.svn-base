<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<!--JSP页面: agent/addAgentCoterie.jsp -->
  <head>
    <title>钱门支付！--网上支付！安全放心！</title>
    <link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
    
    
    <script type="text/javascript">
   function authentication()
{	
	var payPassword=document.getElementById("payPassword").value;
	var mspp=document.getElementById("mspp");
	if(payPassword==""||payPassword==null)
	{
		mspp.innerText="请输入您的支付密码！";
		return false;
	}else
	{
		document.forms[0].submit();
	}
}
    
    </script>
    
  </head>
  
  <body style="text-align: center;">
    <div id="warp" align="left">
    <c:import url="/_jsp/mainTop.jsp?a=1,6&b=0,7,4,6" />
    
    <div id="warp">
	<div id="container" class="register">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="personInfo">
	<tr>
		<td class="tableLT"></td>
		<td class="tableTT"></td>
		<td class="tableRT"></td>
	</tr>
	<tr>
		<td class="tableLL"></td>
		<td class="container">
		<div class="title"><span>加入商户圈：</span></div>	
	<!-- 开始 -->
	<html:form action="agent/agent.do?thisAction=saveAgentCoterie" method="post">
    <html:hidden property="coterieId" value="${coterie.id}"></html:hidden>
    <html:hidden property="loginName" value="${agent.loginName}"></html:hidden>
    <html:hidden property="fromDate" value="${acc.fromDate}"></html:hidden>
    <html:hidden property="expireDate" value="${acc.expireDate}"></html:hidden>
    <html:hidden property="repaymentType" value="${acc.repaymentType}"></html:hidden>
    <html:hidden property="leaveDays" value="${acc.leaveDays}"></html:hidden>
    <html:hidden property="minAmount" value="${acc.minAmount}"></html:hidden>
    <html:hidden property="transactionScope" value="${acc.transactionScope}"></html:hidden>
    		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="personInfo">
    			<tr>
    			<td align="right">商户圈名：</td>
    			<td><font color="#005C9C"> <c:out value="${coterie.name}"/></font></td>
    			<td></td>
    			</tr>
    			<tr>
    			<td align="right">钱门账户名：</td>
    			<td> <font color="#005C9C"> <c:out value="${agent.loginName}"/></font></td>
    			<td><div id="msln" style="color: red;" align="left"><c:out value="${msLongName}" /></div></td>
    			</tr>
    			
    			<td align="right">支付密码：</td>
    			<td><html:password property="payPassword" styleId="payPassword" value=""></html:password>
    			<td align="left"><div id="mspp" style="color: red;" align="left"><c:out value="${mspwd}" /></div></td>
    			</tr>
    			<tr>
    			<td></td>
    			<td><font color="#CDCDCA">钱门保障您交易安全的密码！</font></td>
    			<td></td>
    			</tr>
			</table>
    		
    		
    	<div id="Content"><strong>请阅读以下协议</strong>
		<div style="width:900px;margin:10px 15px; padding:10px;border:1px solid #999999;"><p><b>钱门服务协议</b>
			<b>委托支付协议</b>
      	  <p style="text-indent:20px;line-height: 18px;"> 您确认，在您授权钱门网络科技有限公司（以下称钱门），依据您在本签约页面指定的钱门用户向钱门发送的，向其指定的钱门账户支付特定款项之前，您已详细阅读本协议内容，一旦您点击确认授权钱门付款，表示您已阅读本协议并充分理解、同意接受本协议所有内容，本协议即对您和钱门产生法律约束力。</p>
          <p style="text-indent:20px;line-height: 18px;"> 您不可撤销的授权钱门按照你指定的钱门用户的指令，自您的钱门账户中支付该指令中要求的款项给该钱门用户指明的第三人，本授权仅得在您向钱门发出书面终止授权的指令或钱门终止向您提供钱门服务时终止。您承诺，钱门依照您的授权进行操作的一切风险由您和您指定的钱门用户承担。</p>
          <p style="text-indent:20px;line-height: 18px;"> 您充分理解并明确同意，本协议之签订是钱门为您的钱门账户提供的增值服务，除非钱门未按照您的授权及您指定的钱门用户的指令操作或执行指令错误，钱门将不对任何因本协议之履行承担任何法律责任，您也不得在诉讼、仲裁或其他任何形式的争议解决中向钱门主张权利，该等争议应由您与您指定的钱门用户自行协商或通过司法途径解决。</p>
          <p style="text-indent:20px;line-height: 18px;"> 双方同意，因钱门未按照您的授权及您指定的钱门用户的指令操作或执行指令错误而产生的争议，应协商解决，协商不成的，应提交钱门所在地有管辖权的人民法院裁决。<br />
  			</div></div>   
    	<table align="center">
    	<tr><td>同意以上协议并<span class="simplyBtn_1" style="margin:0"><input type="button" class="buttom_middle" value="确   定" onclick="authentication();"/></span>
</td><td></td><td>
    	<tr><td> <font color="#CDCDCA">您必须同意以上条款，输入支付密码后方能继续！</font> </td><td></td><td>
    	</table>
    	</html:form>
   <!-- 结束 -->
		</td>
        <td class="tableRR"></td>
    </tr>
    <tr>
      <td class="tabelLB"></td>
      <td class="tableBB"></td>
      <td class="tabelRB"></td>
    </tr>
    </table>
	</div><c:import url="/_jsp/footer.jsp" /></div>
</body>
</html>