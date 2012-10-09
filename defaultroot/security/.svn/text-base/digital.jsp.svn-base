<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- security/digital.jsp -->
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/_css/shining.css" type="text/css" />

	</head>
	<body>
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,4&b=0,5,1,2" />
			<div id="main">
				<div id="left_box">
					<div class="leftbox_top">
						<div class="leftbox_bottom">
							<div class="leftbox_count">
								<p class="leftbox_count_p">
									<a href="#">数字证书介绍</a>
								</p>
								<!-- 
	              <p><a href="#">操作指南</a></p>
	              <p><a href="#">常见问题</a></p>
	               -->
							</div>
						</div>
					</div>
					<!-- 
	        <div class="leftbox_top1">
	          <div class="leftbox_bottom1">
	            <div class="leftbox_count1">
	              <p>安全公告</p>
	              <ul>
	                <li><a href="#">四步打造金牌安全钱门</a></li>
	                <li><a href="#">防骗十大绝招-招招致命</a></li>
	                <li><a href="#">防病毒木马，数字证书有绝招</a></li>
	              </ul>
	            </div>
	          </div>
	        </div>
	         -->
				</div>
				<form action="<%=request.getContextPath()%>/security/certificate.do">
					<div id="right_box">
						<div class="Digital_top">
							<div class="Digital_bottom">
								<div

									style="background: url(<%=request.getContextPath()%>/_img/security/img_zhengshu.gif) no-repeat 600px 10px">
									<label>
										什么是数字证书
									</label>
									<p>
										数字证书是由权威公正的第三方机构，即CA中心签发的证书。
									</p>
									<p>
										它以数字证书为核心的加密技术可以对网络上传输的信息进行加密和解密、数字签名和签名验证，确保网上传递信息的机密性、完整性。
									</p>
									<p class="p1">
										使用了数字证书，即使您发送的信息在网上被他人截获，甚至您丢失了个人的账户、密码等信息，仍可以保证您的账户、资金安全。
									</p>


									<!--  -->
									<input type="hidden" id="thisAction" name="thisAction"
										value="apply_DigitalCertificate" />
									<span class="simplyBtn_1"> 
									<c:if test="${URI.agent.status==3}">
											<c:if test="${URI.agent.certStatus!=1}">
													<input type="button" style="text-aligen: center" 
														onclick="applyCert();" class="buttom_middle"
														value="点此申请数字证书" />
											</c:if>
											<c:if test="${URI.agent.certStatus==1}">
													<input type="button" style="text-aligen: center"
														onclick="getCertificate();" class="buttom_middle"
														value="点此管理数字证书" />
											</c:if>
									</c:if> 
									<!-- why -->
									<c:if test="${URI.agent.status!=3}">
										<input type="button" style="text-aligen: center"
											class="buttom_middle" value="申请实名认证"
											onclick="checkCertification(<c:out value='${URI.agent.id}'/>)" />
									</c:if>
									 </span>
								</div>
								<script>
	                	function checkCertification(id){
	                		window.location.href="<%=request.getContextPath()%>/agent/agent.do?thisAction=checkCertification&id="+id;
	                	}
	                </script>
							</div>
						</div>
				</form>
				<div class="rightbox_top">
					<div class="rightbox_bottom">
						<div class="rightbox_count">
							<label>
								钱门数字证书的三大特点
							</label>
							<div class="Features_1">
								为了避免传统数字证书方案中，由于使用不当造成的证书丢失等安全隐患，钱门创造性的推出双证书解决方案：
								钱门会员在申请数字证书时，将同时获得两张证书，一张用于验证钱门账户，另一张用于验证会员当前所使用的计算机。
								<p>
									&nbsp;
								</p>
								<p>
									第二张证书不能备份，会员必须为每一台计算机重新申请一张。这样即使会员的数字证书被他人非法窃取，仍可保证其账户不会受到损失。
								</p>
							</div>
							<div class="Features_1 Features_2">
								钱门数字证书根据用户身份给予相应的网络资源访问权限。
								<p>
									&nbsp;
								</p>
								<p>
									申请使用数字证书后，如果在其他电脑登录钱门账户，没有导入数字证书备份的情况下，只能查询账户，不能进行任何操作，这样就相当于您拥有了类似“钥匙”一样的数字凭证，增强账户使用安全。
								</p>
							</div>
							<div class="Features_1 Features_3">
								1.即时申请、即时开通、即时使用。
								<p>
									2.量身定制多种途径维护数字证书，例如通过短信，安全问题等。
								</p>
								<p>
									3.不需要使用者掌握任何数字证书相关知识，也能轻松掌握。
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div id="footer">
			<a href="<%=request.getContextPath()%>/about/regarding.jsp">关于钱门</a>
			|
			<a href="<%=request.getContextPath()%>/about/talent.jsp">诚聘英才</a> |
			<a href="<%=request.getContextPath()%>/about/advertising.jsp">广告业务</a>
			|
			<a href="<%=request.getContextPath()%>/about/contact.jsp">联系我们</a> |
			<a href="<%=request.getContextPath()%>/about/disclaimer.jsp">服务说明</a>
			|
			<a href="<%=request.getContextPath()%>/about/help.jsp">安全保障</a> |
			<a href="<%=request.getContextPath()%>/about/join_help.jsp">加盟钱门</a>
			<div id="foot_info">
				<p>
					Copyright 2009-2012, www.qmpay.com .All Rights Reserved
				</p>
				<p>
					<a href="#"
						onclick="javascript:window.open('<%=request.getContextPath()%>/_jsp/ICP.jsp','','width=600,height=850,top=0')">
						增值电信业务经营许可证 粤B2-20090217 </a> &nbsp;
					<img src="<%=request.getContextPath()%>/_img/logo_footer.gif" />
				</p>
			</div>
		</div>
		</div>

	</body>
</html>
<script>
<!--
	function shutwin(){
		//opener=null;
		//self.close();
	    var obj=document.getElementById("certificate");
	    obj.style.display="none";		
	}
	function applyCert(){
		with(document.forms[0]){
			action="<%=request.getContextPath()%>/security/certificate.do?thisAction=apply_DigitalCertificate";
			submit();
		}
	}
	function createXMLHttp(){
	    var xmlhttp;
	    try{
	        xmlhttp=new XMLHttpRequest();
	    }catch(e){
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    return xmlhttp;
	}
	function getCertificate()
	{
		var msg="<c:out value='${URI.agent.loginName}' />";
		
		window.location.href="<%=request.getContextPath()%>/security/certificate.do?thisAction=manageCertificate";
	}
	function showCertInfo(){
	    var xmlhttp=createXMLHttp();
	    xmlhttp.onreadystatechange=function(){
	        if(4==xmlhttp.readyState){
	            if(200==xmlhttp.status){
	            	var obj=document.getElementById("certificate");
	            	obj.style.display="";
	            	obj.style.width = 600 + 'px';
	            	obj.style.height = 300 + 'px';
	            	obj.style.top = (document.body.clientHeight - obj.offsetHeight)/2+" px";
	            	obj.style.left = (document.body.clientWidth - obj.offsetWidth)/2+" px";
	            	
	            	var certInfo=document.getElementById("cert_info");
	            	
	            	certInfo.innerHTML=xmlhttp.responseText;
	            }else{
	                alert("no");
	            }
	        }
	    }
	    var submitUrl="<%=request.getContextPath()%>/security/certificate.do?thisAction=getCertInfo";
	    xmlhttp.open("post",submitUrl,true);    
	    xmlhttp.send(null);
	    return false;    
	}
	function deleteCertInfo(){ 
	    /*var xmlhttp=createXMLHttp();
	    xmlhttp.onreadystatechange=function(){
	        if(4==xmlhttp.readyState){
	            if(200==xmlhttp.status){
	            	var obj=document.getElementById("certificate");
	            	obj.display="";
	            	obj.style.width = 600 + 'px';
	            	obj.style.height = 300 + 'px';
	            	obj.style.top = (document.body.clientHeight - obj.offsetHeight)/2+" px";
	            	obj.style.left = (document.body.clientWidth - obj.offsetWidth)/2+" px";
	            	
	            	var certInfo=document.getElementById("cert_info");
	            	
	            	certInfo.innerHTML=xmlhttp.responseText;
	            }else{
	                alert("no");
	            }
	        }
	    }
	    var submitUrl="<%=request.getContextPath()%>/security/certificate.do?thisAction=removeCert";
	    xmlhttp.open("post",submitUrl,true);    
	    xmlhttp.send(null);
	    return false; */
	    with(document.forms[0]){
	    	thisAction.value="removeCert";
	    	submit();
	    }
	}
//-->
</script>