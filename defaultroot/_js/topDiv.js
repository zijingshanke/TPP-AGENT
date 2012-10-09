function initContent(rootPath){
var defalut_divcontent ="";
  defalut_divcontent+="";
defalut_divcontent+="<div id='container'>";
defalut_divcontent+="<div id='main'>"
defalut_divcontent+="<div id='content'>";
defalut_divcontent+="<div id='content_top'>";
defalut_divcontent+="<div id='content_t_left'><img src='"+rootPath+"/_img/alreday.gif' /></div>";
defalut_divcontent+="<div id='content_t_title'>您的账户部分功能将不能正常使用。</div>";
defalut_divcontent+="<div id='content_t_right'><a href='javascript:this.cancle();'>关闭</a></div>";
defalut_divcontent+="</div>";           
defalut_divcontent+="<div id='contentMian'>";
defalut_divcontent+="<table width='480' border='0' cellspacing='0' cellpadding='0'>";
defalut_divcontent+="<tr>";
defalut_divcontent+="<td colspan='2' >请您进行以下操作：</td>";
defalut_divcontent+="</tr>"
defalut_divcontent+="<tr>";
defalut_divcontent+="<td width='35'>&nbsp;</td>";
defalut_divcontent+="<td width='445' style='font-weight:bold'>由于本台电脑还未导入数字证书，您只能进行账户查询。</td>";
defalut_divcontent+="</tr>";
defalut_divcontent+="<tr>";
defalut_divcontent+="<td style='background:url("+rootPath+"/_img/eorr.jpg) no-repeat center right'>&nbsp;</td>";
defalut_divcontent+="<td  >如果您还申请了证书，<a href='"+rootPath+"/security/certificate.do?thisAction=manageCertificate' target='_parent'>请点此管理证书</a></td>";
defalut_divcontent+="</tr>";
defalut_divcontent+="<tr>";
defalut_divcontent+="<td style=' background:url("+rootPath+"/_img/eorr.jpg) no-repeat center right'>&nbsp;</td>";
defalut_divcontent+="<td >如果您需要进行账户操作，<a href='"+rootPath+"/security/certificate.do?thisAction=importCertificatePage' target='_parent'>请点此导入证书</a></td>";
defalut_divcontent+="</tr>";
defalut_divcontent+="<tr>";
defalut_divcontent+="<td style=' background:url("+rootPath+"/_img/eorr.jpg) no-repeat center right'>&nbsp;</td>";
defalut_divcontent+="<td >如果您的证书备份文件丢失、损坏、不匹配或忘记了备份密码，<a href='"+rootPath+"/security/certificate.do?thisAction=revokeCertificatePage' target='_parent'>请申请注销证书</a></td>";
defalut_divcontent+="</tr>";
defalut_divcontent+="<tr>";
defalut_divcontent+="<td style=' background:url("+rootPath+"/_img/eorr.jpg) no-repeat center right'>&nbsp;</td>";
defalut_divcontent+="<td>如果您是工行U盾的证书用户，请在插入U盾后，重新登录。 </td>";
defalut_divcontent+="</tr>";
defalut_divcontent+="</table>";
defalut_divcontent+="</div>";
defalut_divcontent+="</div>";
defalut_divcontent+="</div>";
defalut_divcontent+="</div>";
	return defalut_divcontent;
}
function msg(divcontent,rootPath){
if(divcontent==null){
 divcontent=initContent(rootPath);
}
 var p=document.createElement("DIV");
 p.id="p";
 p.style.position="absolute";
 p.style.width=document.body.scrollWidth;
 p.style.height=document.body.scrollHeight+83;
 p.style.zIndex='1024';
 p.style.top='0px';
 p.style.left='0%';
 p.style.backgroundColor="#D0D0D0";
 p.style.opacity='0.5';
 p.style.filter="alpha(opacity=80)";
 document.body.appendChild(p);
 var p1=document.createElement("DIV");
 var top=parseInt(parseInt(document.body.scrollHeight)*0.25)+document.body.scrollTop;
 p1.style.position="absolute";
 p1.style.width="300px";
 p1.id="p1";
// var left=Math.ceil(((document.body.scrollWidth)-parseInt(p1.style.width.replace('px','')))/2)+document.body.scrollLeft;
 var left=(document.body.scrollWidth-510)/2;
 p1.style.height="0px";
 p1.style.zIndex='1024'; 
 p1.style.top=top+'px';
  p1.style.left=left+'px';
 p1.style.border="0px solid red";
 document.body.appendChild(p1);
 p1.innerHTML=divcontent;
 var arr=document.getElementsByTagName("select");
 var i=0;
 while(i<arr.length){
   arr[i].style.visibility='hidden';
   i++;
 }
 
 this.cancle=function(){
  document.body.removeChild(document.getElementById('p'));
  document.body.removeChild(document.getElementById('p1'));
  var arr=document.getElementsByTagName("select");
   var i=0;
   while(i<arr.length){
   arr[i].style.visibility='visible';
   i++;
   }
 }
 }