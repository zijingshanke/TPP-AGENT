<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- passCertift.jsp -->
<html>
  <head>
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
  </head>
  
  <body style="text-align: center;">
  <div id="warp" align="left">
     <c:import url="/_jsp/certifyTop.jsp"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid" style="padding:5px 8px;">
          <div class="main_title">
            <div class="main_title_right">
              <p><strong>请等待平台处理认证请求</strong></p>
            </div>
          </div>
          <div class="success">
            <img src="../_img/OK_img.gif" class="ok_img" />
            <div class="success_right">
              <p><strong style="font-size:16px;">钱门实名认证正在处理中.....</strong></p>
              <p style="border-bottom:1px solid #D2D2D2">您申请了钱门实名认证。钱门公司将在1-3个工作日中向您提供的银行账号汇入了一定数目的资金(一元人民币以内）。  
请您通过网上银行、电话银行或到银行柜台查询收到的具体金额。  
然后登录您的钱门账户，将查到的金额填写在确认页面，完成您的银行账户核实。  
</p>
              <p style="border-bottom:1px solid #D2D2D2">如果您在3日之内没有收到打款而钱门又没有自动撤销您实名认证的申请，你可以手动的撤销，而后可以重新开始实名认证.........<a href="../agent/agent.do?thisAction=removeCertification">点此撤销</a>
</p>
              <ul class="success_ul">
                
                <li>请点击返回 <a href="../agent/agent.do?thisAction=agentInfoById">我的钱门</a></li>
              </ul>
            </div>
            <div class="clear"></div>
          </div>
          
        </div>
      </div>
    </div>
<c:import url="../_jsp/footer.jsp"></c:import>
  </div>
</body>
</html>
