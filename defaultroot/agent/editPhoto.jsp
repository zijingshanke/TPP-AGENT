<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- agent/editEmail.jsp -->

 <head> 
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<link href="<%=request.getContextPath()%>/_css/qianmen1.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="../_js/common.js"></script></head>
<script type="text/javascript">

function selectAttachment() {
	var _url = "../page/editAttach.jsp";
	openWindow(580,220,_url);
}

/*
function getAttachs(_tempAttach) {
	//$("div_attachment").innerHTML = _tempAttach;
	
	document.forms[0].photo.value=_tempAttach;
}
*/

function getAttachs(listAttachName,_tempAttach,listAttachNum, vname)
 {
 //alert('_taxpayerIds='+_taxpayerIds);
 //alert('_tempAttach='+_tempAttach);
 // alert('listAttachNum='+listAttachNum);
 
if(listAttachNum==0)
{
  alert("您还没有上传附件！");
  return false;
}

if(listAttachNum>1)
{
  alert("您一次只能上传一个附件！");
  return false;
}
var exName=vname.substr(vname.lastIndexOf(".")+1).toUpperCase();
if(!(exName=="JPG"||exName=="BMP"||exName=="GIF"||exName=="JPEG")){
	alert("文件类型只能是JPG/BMP/GIF/JPEG");
	return false;
}
document.forms[0].photo.value=vname;
document.forms[0].listAttachName.value=listAttachName;
document.getElementById("showImg").src="../upload/"+vname;
   return true;   
 }
	function LTrim(str)
	
	{
	
	 return str.replace(/^\s*/g,"");
	
	}



	function RTrim(str)
	
	{
	
	 return str.replace(/\s*$/g,"");
	
	}
 function check(){
// 	var photo =document.forms[0].photo.value;
//	photo=LTrim(photo);
// 	photo=RTrim(photo);
// 	if(photo==""||photo.length==0){
// 		alert("请上传您喜爱的头像!");
// 		return false;
// 	}
 	document.forms[0].submit();
 }
</script>
<body>
  <div id="warp">
  <c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,5,6" />
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>修改账户登录名</strong><a href="agent.do?thisAction=viewMyAgent" class="a_style1">返回我的账户</a></p>
            </div>
          </div>
          <html:form action="/agent/agent.do?thisAction=updatePhoto">
	<html:hidden property="id" value="${agent.id}"></html:hidden>
		<html:hidden property="listAttachName" ></html:hidden>
          <table cellpadding="0" cellspacing="0" width="100%" class="information_table" style="text-align: left">
            <tr>
              <td class="right_td">账户名：</td>
              <td><span class="font_style2"><c:out value="${agent.loginName}"></c:out></span></td>
            </tr>
            <tr>
              <td class="right_td">照片：</td>
             <html:hidden property="photo"></html:hidden> <td><html:img   width="100px" height="100px" page="/upload/${agent.photo}" styleId="showImg"/></td>

            </tr>
            <tr>
              <td class="right_td"></td>
              <td><input type="button" value="修改图片" onclick="selectAttachment();"/> </td>

            </tr>
            <tr>
              <td colspan="2" style="padding-left: 140px"><span class="simplyBtn_1 simplyBtn_11"><input type="button" class="buttom_middle" value="&nbsp;&nbsp;&nbsp;确认" onclick="check();" /></span></td>
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
