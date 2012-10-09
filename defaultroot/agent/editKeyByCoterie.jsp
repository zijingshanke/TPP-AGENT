<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<element>
	<html xmlns="http://www.w3.org/1999/xhtml">
   <title>钱门支付！--网上支付！安全放心！</title>
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
<script type="text/javascript" src="../_js/common.js"></script></head>
  </head>
  <script type="text/javascript">
 	function sbm(){
 		var key=document.forms[0].signKey.value;
 		var p=document.getElementById("pwd").value;
 		var showmsg=document.getElementById("showmsg");
 		showmsg.innerHTML="";
 		if(key.length==0){
 			showmsg.innerHTML="密钥不能为空！";
 			return false;
 		}else
 		if(p.length<6){
 			showmsg.innerHTML="交易密码不能为空并且不能小于六位！";
 			return false;
 		}else{
 			document.forms[0].submit();
 		}
 	}
 	
 	function viewMyAgent(){
 		document.forms[0].action="../agent/agent.do?thisAction=viewMyAgent";
 		document.forms[0].submit();
 	}
  </script>
    <div id="warp">
   <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,0,6"/>  
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right" align="left">
              <p><strong>修改密钥：</strong></p>
            </div>
          </div>
          <html:form action="/agent/coterie.do?thisAction=setKeyByCoterie">
          <table class="information_table">
         <tr><td style="text-align: right">钱门帐户:</td><td><c:out value="${coterie.agent.loginName}"></c:out></td></tr>
         <tr><td style="text-align: right">企业名称:</td><td><c:out value="${coterie.agent.companyName}"/></td></tr>
         <tr>
         <td style="text-align: right">密钥:</td>
         <td>
         <html:text property="signKey" value="${coterie.signKey}" name="coterie" styleClass="text_style" style="width:250px;"></html:text>
         </td></tr>
         <tr>
         <td style="text-align: right">交易密码:</td>
         <td>
         <html:password property="agent.payPassword" name="coterie" styleId="pwd"  styleClass="text_style" value="" style="width:150px;"></html:password>
         </td>
         </tr>
         <html:hidden property="agent.loginName" name="coterie" value="${coterie.agent.loginName}"></html:hidden>
          </table>
         <div id="showmsg" style="color: red;">
         <c:out value="${msg}"></c:out>
         </div>
        <input type="button" value="确定" onclick="sbm();" class="btn1"/>
        <input type="button" value="返回我的账户" onclick="viewMyAgent();" class="btn1"/>
        </html:form>
            </div>
            <div class="clear">
            
            </div>
          </div>
        </div> <c:import url="/_jsp/footer.jsp" />
      </div>
     
  </body>
</html>