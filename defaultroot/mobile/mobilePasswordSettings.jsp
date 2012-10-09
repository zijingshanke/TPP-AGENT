<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: mobile/mobilePasswordSettings.jsp -->


<html>
  <head>
    <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">	
<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
 <script type="text/javascript" src="../_js/common.js"></script>
 	<script>
 		function check(){
 			var maxItermAmount =document.forms[0].maxItermAmount;
 			var maxDayAmount =document.forms[0].maxDayAmount;
 			var payPassword =document.forms[0].payPassword;
 			if(maxItermAmount.value.length<=0){
 				alert("请输入金额!");
 				maxItermAmount.focus();
 			}else if(maxDayAmount.value.length<=0){
 				alert("请输入金额!");
 				maxDayAmount.focus();
 			}else if(payPassword.value.length<=0){
 				alert("请输入支付密码");
 				payPassword.focus();
 			}else{
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
 	</head> 

  
  <body>
<div id="warp">
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />	
	 <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>手机动态口令</strong></p>
            </div>
          </div>
          <img src="../_img/dongtai_buzhou_2.gif" alt="" style="margin-top:12px;"/>
        </div>
      </div>
    </div>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>设置额度</strong></p>
            </div>
          </div>
          
          <html:form action="/agent/agent.do?thisAction=settingMobilePassword">
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <tr>
                  <td class="right_td" valign="top">单笔支付额度：</td>
                  <td>
                  <html:text property="maxItermAmount" styleClass="text_style" style="width:150px; margin-right:8px;" onblur="checkPrice(this)" onkeyup="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')">元</html:text>
                    <p class="edu_text">当您的单笔支付资金超过该额度时，需要输入手机动态口令</p>
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                  <c:if test="${MaxItermAmountError eq 'error'}">
                  <div class="blueBox_1" style="width:500px;">您检查您设定的单笔支付额度是否大于等于200元</div>
                  </c:if>
                  <c:if test="${MaxItermAmountError eq null}">
                  <div class="blueBox_1" style="width:500px;">您设定的单笔支付额度必须大于等于200元</div>      
                  </c:if>           
                  </td>
                </tr>
                <tr>
                  <td valign="top" class="right_td">每天支付累计额度：</td>
                  <td>
                  	  <html:text property="maxDayAmount" styleClass="text_style" style="width:150px; margin-right:8px;" onblur="checkPrice(this)" onkeyup="value=value.replace(/[^.0-9]/g,'')" onchange="value=value.replace(/[^.0-9]/g,'')">元</html:text>
                    <p class="edu_text">您当天支付累计资金数超过该额度时，需要输入手机动态口令动态口令</p>
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                  <c:if test="${MaxDayAmountError eq 'error'}">
                  <div class="blueBox_1" style="width:500px;">您检查您设定的“每天支付累计额度”是否大于等于“单笔支付额度”</div>
                  </c:if>
                  <c:if test="${MaxDayAmountError eq null}">
                   <div class="blueBox_1" style="width:500px;">您设定的“每天支付累计额度”必须大于等于“单笔支付额度”</div>
                   </c:if>
                  </td>
                </tr>
                <tr>
                  <td valign="top" class="right_td">绑定手机号码：</td>
                  <td>
                  <c:if test="${URI.agent.mobileBindStatus==1}">
                	<c:out value="${URI.agent.mobilePhone}"/>
                  </c:if>                 	
                    <p class="edu_text"><a href="#" style="margin:0;">更换手机号码</a></p>
                  </td>
                </tr>
                <tr>
                  <td valign="top" class="right_td">支付密码：</td>
                  <td>
                  <html:password property="payPassword" value="" styleClass="text_style"></html:password>
                    <a href="agent.do?thisAction=forgetPassword&type=paypassword" style="margin: 0px" >找回支付密码</a>
                  </td>
                </tr>
                <tr>
                <td>&nbsp;</td>
                <td><c:if test="${PayPasswordError eq 'error'}">
                  <div class="blueBox_1" style="width:500px;color: red;">请输入正确的支付密码</div>
                  </c:if>
				</td>
                </tr>
                <tr>
               	  <td>&nbsp;</td>
                  <td><span class="simplyBtn_1" style="margin:0"><input type="button" class="buttom_middle" value="下一步" onclick="check();" /></span></td>
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
