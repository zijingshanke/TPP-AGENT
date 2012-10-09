<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: mobile19pay/viewMobilePay.jsp -->
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

	<body>
		<div id="warp">
		  <c:import url="/_jsp/topMenu.jsp?a=1,7&b=0,8,0,1" />
		  <div>&nbsp;</div>
			<div id="maincontent">
    <div class="mainimg">
        <p><a href="#"><img src="../_img/mobile/1.jpg"/></a></p>
        <p><a href="#"><img src="../_img/mobile/2.jpg" style="margin-top:10px"/></a></p>
    </div>
    <div class="Prepaidcont">
    	<img src="../_img/mobile/Process.jpg"/>
        <div class="content">
	        <h3>手机话费充值</h3>
            	  <html:form action="/mobile/agent19pay.do?thisAction=processOrder">
		  <table width="90%" border="0" cellpadding="0" cellspacing="0" class="inputlist">
		  <tr>
                <td>号码类型:</td>
                <td><html:select property="numType">
                <html:option value="1">移动电话</html:option>
              <!--   <html:option value="2">小灵通</html:option>
                <html:option value="3">固定电话</html:option>
                 -->
                </html:select>
                </td>
              </tr>
              <tr>
                <td>手机号码:</td>
                <td><html:text property="mobilenum"/></td>
              </tr>
              <tr>
                <td>再一次输入手机号码:</td>
                <td><html:text property="mobilenum2"/></td>
              </tr>
              <tr>
                <td>充值面额:</td>
                <td><html:select property="prodContent">
    	<html:option value="10">10元</html:option>
    	<html:option value="20">20元</html:option>
    	<html:option value="50">50元</html:option>
    	<html:option value="100">100元</html:option>
    	<html:option value="150">150元</html:option>
    	<html:option value="200">200元</html:option>
    	<html:option value="250">250元</html:option>
    	<html:option value="300">300元</html:option>
    	<html:option value="350">350元</html:option>
    	<html:option value="400">400元</html:option>
    	<html:option value="450">450元</html:option>
    	<html:option value="500">500元</html:option>
    </html:select>
                </select></td>
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
                <div class="confoot">
                	<strong>支持范围：</strong>
                	<p>支持全国各省市（澳门、香港、台湾地区除外）</p>
                	<p>支持移动、联通、电信所有手机号码</p>
                </div>
    </div>
    <div id="askedquestions">
    <h3>常见问题：</h3>
    	<ul>
        	<li>手机充值是否需要手续费？<p>不需要，不但不需要手续费。而且还可以随时充值。</p></li>
        	<li>什么是快充？<p>快充是指您进行手机充值付款成功后话费1-10分钟到帐的充值方式。</p></li>
        	<li>什么是慢充？<p>慢充是指您进行手机充值付款成功后话费48小时内到帐的充值方式。慢充只对钱门用户开放,并且选择慢充的手机必须是与你的钱门账号邦定的手机。</p></li>
        </ul>
    </div>
</div>
		<c:import url="../_jsp/footer.jsp"></c:import>

		</div>

	</body>
<script>
function LTrim(str)

{

 return str.replace(/^\s*/g,"");

}



function RTrim(str)

{

 return str.replace(/\s*$/g,"");

}



function trim(str)

{

 return str.replace(/(^\s*)|(\s*$)/g, "");

}

function IsMobel(str)

{

 var reg0 = /^13\d{9}$/;
    var reg1 = /^15\d{9}$/;
    var reg2 = /^18\d{9}$/;
    return (reg0.test(str)||reg1.test(str)||reg2.test(str))

}

	function check(){
		
		var mobilenum = document.agent19pay.mobilenum.value;
		var mobilenum2=document.agent19pay.mobilenum2.value;
		mobilenum=RTrim(mobilenum);
		mobilenum=trim(mobilenum);
		mobilenum=RTrim(mobilenum);
		mobilenum2=RTrim(mobilenum2);
		mobilenum2=trim(mobilenum2);
		mobilenum2=RTrim(mobilenum2);
		if(IsMobel(mobilenum)){
			document.agent19pay.mobilenum.value=mobilenum;
		}else{
			alert("手机号码格式有误，请认真填写!");
			document.agent19pay.mobilenum.select();
			document.agent19pay.mobilenum.focus();
			return;
		}
		if(mobilenum==""||mobilenum.length==0){
			alert("请输入充值手机号");
			document.agent19pay.mobilenum.focus();
			return;
		}else if(mobilenum2==""||mobilenum2.length==0){
			alert("请再次输入充值手机号");
			document.agent19pay.mobilenum2.focus();
			return;
		}else if(mobilenum!=mobilenum2){
			alert("二次输入不一致!请认真填写!");
			document.agent19pay.mobilenum2.focus();
			return;
		}else{
			document.agent19pay.submit();
		}
	}
</script>
</html>
