<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>钱门支付！--网上支付！安全放心！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="../_css2/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
 	</head> 
  <body>
<div id="warp">
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,6,6" />	
	 <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>解除手机号码绑定</strong></p>
            </div>
          </div>
          <p style="margin-left:12px;">不想解除手机号码绑定？<a href="../agent/agent.do?thisAction=viewMobileCenter" style="color:#005c9c;">点此取消申请</a></p>
          <div style="font-size:14px; height:30px; line-height:30px; margin:0 0 12px 12px; border-bottom: #CCC solid 1px;"><img src="../_img/key.gif" alt="" style="margin-right: 8px;"/>请输入您原来的安全保护问题答案：<span style="color:#999;">（日期输入格式为19810101）</span></div>
          <html:form action="/agent/agent.do?thisAction=unBindingMobileValidateQuestion">
          <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
              <table cellpadding="0" cellspacing="0" width="100%" class="information_table">
                <c:forEach var="questionandanswer" items="${listquestionanswer}">
                <tr>
                  <td class="right_td">问题：</td>
                  <td><c:out value="${questionandanswer.question}"></c:out></td>
                </tr>
                <tr>
                  <td class="right_td">答案：</td>
                  <td><input type="text" class="text_style" name="answers"></input><div style="color: red"><c:out value="${questionandanswer.answerError}"></c:out></div></td>
                </tr>
             
                </c:forEach>
                <tr>
               	  <td>&nbsp;</td>
                  <td><span class="simplyBtn_1" style="margin:0"><input type="submit" class="buttom_middle" value="确定" /></span></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><div class="blueBox_1" style="width:500px;">如果您忘记了密码保护问题的答案，请<a href="#" style="margin-right:10px; font-size:14px;">提交申请单</a>联系客服处理</div></td>
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
