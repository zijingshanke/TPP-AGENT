<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- jsp:addAccount.jsp  -->
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
  <head>
   <title>钱门支付！--网上支付！安全放心！</title>
    <link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  <script type="text/javascript" src="../_js/jquery-1.3.1.min.js"></script>
  <script type="text/javascript" src="../_js/json.js"></script></head>
  <script type="text/javascript">
  	 function check(){
  		document.forms[0].submit();
  	}
  	function editPerson(){
  		document.forms[0].action="agent.do?thisAction=toEditPersonInfo";
  		document.forms[0].submit();
  	}
  </script>
      
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6"/>
    	<div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>设置银行账户信息</strong></p>
            </div>
          </div>
          <html:form action="agent/agent.do">
           <html:hidden property="thisAction" value="addAccount"/>
            <html:hidden property="id" value="${URI.agent.id}"></html:hidden>
            	  <html:hidden property="bank.id" value="${agent.bank.id}"></html:hidden>
          <table cellpadding="0" cellspacing="0" width="80%" class="information_table">
            <tr>
              <td class="right_td">银行开户姓名：</td>
              <td><html:text property="realName"></html:text></td>
            </tr>
           
            <!-- 
             <tr>
              <td class="td_span">&nbsp;</td>
              <td class="td_span">如您没有合适的银行账户，<a href="javascript:editPerson();">修改身份信息</a></td>
            </tr>
            <tr>
              <td class="right_td">&nbsp;</td>
              <td><p class="td_p">提醒：必须使用以<c:out value="${agent.realName}"></c:out>未开户的银行账户进行认证。</p></td>
            </tr>
			  -->
            <tr>
              <td class="right_td">开户银行所在省份：</td>
              <td><select id="provinces"  name="provincesId" onchange="selectProvince(this.value)">
              	
              	</select></td>
            </tr>
            
            <tr>
              <td class="right_td"></td>
              <td class="td_span">在下列城市的工商很行开户的用户请在本栏中选择：宁波/大连/青岛/厦门/深圳/三峡。</td>
            </tr>
            <tr>
              <td class="right_td">开户银行所在城市：</td>
              <td>
              	<select id="citys" name="citysId" onchange="selectCity(this.value)">
              	</select>
              </td>
            </tr>
              <tr>
              <td class="right_td">开户银行名称：</td>
              <td>
              <select id="banks" name="banksId">
              </select>
              </td>
            </tr>
            <tr>
              <td class="right_td">个人银行账户：</td>
              <td><html:text property="account.cardNo" /></td>
            </tr>
            <tr>
              <td class="right_td"></td>
              <td class="td_span">您提交后钱门会给该账户注入一笔“确认资金”，你需要正确的输入这笔资金的数量才能通过认证。</td>
            </tr>
            <tr>
              <td class="right_td">请再输入一遍：</td>
              <td><html:text property="tempBankNum" value=""/>
              <font color="red"><c:if test="${error eq 'differentCardNo'}">
              二次输入的帐号卡不一致
              </c:if>
              <c:if test="${error eq 'existentCardNo'}">
              此帐号已被使用，
              </c:if></font></td>
            </tr>
            <tr>
              <td style="text-align:center;"><input type="button" value="下一步" class="btn1" onclick="check();" /></td>
              <td>&nbsp;</td>
            </tr>
          </table>
          </html:form>
        </div>
      </div>
    </div>
    <c:import url="/_jsp/footer.jsp" />
    </div>
  </body>
</html>
