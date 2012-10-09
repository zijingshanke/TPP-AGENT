<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<!-- jsp:acknowledgementMoney.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script src="../_js/common.js" type="text/javascript"></script>
  </head>
  <script type="text/javascript">
  var isSubmit=true;
  	function checkMoney(money){
  		isSubmit=true;
  		str=money.value; 	
  		var msg = document.getElementById("showMsg");	
  		msg.innerHTML="";
  		if(!isMoney(str)){
  			msg.innerHTML="您输入的内容有错误!";
  			isSubmit=false;
  		}
  	}
  	
  	function checkSubmit(){
  		if(isSubmit){
  			document.forms[0].submit();
  		}
  	}
  		function checkPrice(obj){
		    
			var re=/^([1-9]|[0.])[0-9]{0,}(([.]*\d{1,2})|[0-9]{0,})$/;
			if(!re.test(obj.value)){
				return false;
			}
			if(obj.value<=0.01||obj.value>10000000.00){						
				return false;
			}
		}
  </script>
  <body style="text-align: center;">
 <html:form action="/agent/agent.do?thisAction=checkMoney">
 <html:hidden property="id" value="${agent.id}"></html:hidden>
 <html:hidden property="account.cardNo" value="${agent.account.cardNo}"></html:hidden>
  <div id="warp" align="left">
    <c:import url="/_jsp/certifyTop.jsp"/>
    
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>确认汇款金额</strong></p>
            </div>
            <div class="clear"></div>
            
          </div>
          
          <table cellpadding="0" cellspacing="0" width="800" class="information_table information_table1">
            <tr>
              <td class="right_td">钱门账号：</td>
              <td style="color:#005C9C"><c:out value="${agent.loginName}"></c:out></td>
            </tr>
            <tr><td colspan="2">          <div class="Wronning Wronning1">
                <div class="wronningTitle">
                    您有<em class="h1 h2">&nbsp;<c:out value="${URI.agent.checkMoneyCount}"></c:out>&nbsp;</em>次机会输入您的答案！
                </div>
          </div></td></tr>
            <tr>
              <td class="right_td">开户银行：</td>
              <td><c:out value="${agent.account.bankName}"></c:out>
              </td>
            </tr>
      <tr>
              <td class="right_td">开户银行帐号：</td>
              <td><c:out value="${agent.account.cardNo}"></c:out></td>
            </tr>
            <tr>
              <td class="right_td"></td>
              <td class="td_span">钱门已经向您的银行账户<c:out value="${agent.account.cardNo}"></c:out>注入了一定数目的资金，请将钱门给您注入的金额输入到下面的框内</td>
            </tr>
            <tr>
              <td class="right_td"><span class="font_style6">* </span>我看到有：</td>
              <td><html:text property="tempBalance" styleClass="text_style" style="width:150px; margin-right:8px;" onblur="checkPrice(this)" onkeyup="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')"></html:text> 元<div id="showMsg"></div></td>
            </tr>
            <tr>
              <td class="td_span"></td>
              <td class="td_span"><span style="color:#F00;">您有三次输入机会</span>，请输入正确有金额，三次失败以后需重新申请。金额精确到0.01元（如0.03）</td>
            </tr>
            <tr><td colspan="2">&nbsp;</td></tr>
             <tr>
              <td class="td_span"></td>
              <td class="td_span"><span style="color:#F00; font-size:16px;">
             <c:out value="${errormoney}"></c:out>
             <c:out value="${notmoney}"></c:out>
              </span>
              </td>
            </tr>
          </table>
          <p style="margin:10px 20px;"><input type="button" value="确 定" class="btn1" onclick="checkSubmit();"/></p>
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
  </html:form>
</body>
</html>
