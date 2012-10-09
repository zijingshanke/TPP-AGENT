<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!-- agent/myAgentInfo.jsp -->

  <head>  
<title>钱门支付！--网上支付！安全放心！</title>	
    <link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
<link rel="stylesheet" href="../_css2/my_account.css" type="text/css" />
  
  <script type="text/javascript">
  </script>
</head>
  
  <body>
<div id="warp">
<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,7,6"/>
    <div id="main">
      <div id="left_box">
        <div class="leftbox_top">
          <div class="leftbox_bottom">
            <div class="leftbox_count">
              <p class="leftbox_count_p"><a href="#">账户设置</a></p>
              <p><a href="../security/security.do?thisAction=securityChecked">账户安全检测</a></p>
              <!--  p><a href="../support/index.htm">帮助中心</a></p -->
            </div>
          </div>
        </div>
        
        <!-- div class="leftbox_top1">
          <div class="leftbox_bottom1">
            <div class="leftbox_count1">
              <p>常见问题</p>
              <ul>
                <li><a href="#">用钱门付款需要绑定银行卡吗？</a></li>
                <li><a href="#">如何更改钱门账户E-mail地址？</a></li>
                <li><a href="#">如何修改提现银行信息？</a></li>
                <li><a href="#">钱门手机服务有哪些？</a></li>
              </ul>
            </div>
          </div>
        </div -->
      </div>
      
      <div id="right_box">
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
              <div class="rightbox_title">
                <div class="rightbox_title_right">
                  <div class="rightbox_title_count">
                  个人信息
                  </div>
                </div>
              </div>
             
              
              <table cellpadding="0" cellspacing="0" width="400" class="right_count_tabel">
                <tr>
                  <td width="70" align="right">真实姓名：</td>
                  <td><span class="font_style2"><c:out value="${agent.name}"></c:out></span>&nbsp;&nbsp;
                   <c:if test="${agent.status==1}">
                  <a href="agent.do?thisAction=editAgentInfo&id=<c:out value="${URI.agent.id}"></c:out>">修改个人信息</a>
                  </c:if>                
                  </td>
                </tr>
                <tr>
                  <td width="70" align="right">E-mail：</td>
                  <td><span class="font_style2"><c:out value="${agent.loginName}"></c:out></span>&nbsp;&nbsp;<a href="agent.do?thisAction=editEmail">修改Email</a></td>
                </tr>
                <tr>
                  <td width="70" align="right">联系方式：</td>
                  <td><c:out value="${agent.telephone}"></c:out>&nbsp;&nbsp;<a href="agent.do?thisAction=editTelephone">修改电话号码</a> </td>
                </tr>
                <tr>
                  <td width="70" align="right">注册时间：</td>
                  <td><fmt:formatDate value="${agent.registerDate}" type="date" pattern="yyyy-MM-dd" /></td>
                </tr>
                <tr>
                  <td width="70" align="right">账户状态：</td>
                  <td>
                  <c:if test="${agent.status==3}">
                  <img src="../_img/pic_1.gif" /><a href="#">已认证</a>
                  </c:if>                  
                  <c:if test="${agent.status!=3}">
                  <img src="../_img/pic_2.gif" />
                  <html:link page="/agent/agent.do?thisAction=checkCertification&id=${URI.agent.id}" >申请认证</html:link>
                  </c:if>
                  <c:if test="${agent.mobileBindStatus==0}">
                  <img src="../_img/pic_4.gif" /><html:link page="/agent/agent.do?thisAction=viewMobileCenter">未绑定手机</html:link>
                  </c:if>
                  <c:if test="${agent.mobileBindStatus!=0}">
                  <img src="../_img/pic_3.gif" /><html:link page="/agent/agent.do?thisAction=viewMobileCenter">已绑定手机</html:link>
                  </c:if>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
              <div class="rightbox_title">
                <div class="rightbox_title_right">
                  <div class="rightbox_title_count">
                  账户信息
                  </div>
                </div>
              </div>
              <table cellpadding="0" cellspacing="0" width="100%" class="right_count_tabel">
                <tr>
                  <td width="70" align="right">可用余额：</td>
                  <td><span class="font_style3" style="width: 150px;text-align: left"><fmt:formatNumber pattern="0.00" value="${agent.allowBalanceAndCheckAmount}"/> 元</span><a href="../agent/agent.do?thisAction=viewPayBalance">余额支付（开启/关闭）</a><a 
        href="../transaction/transactionlist.do?thisAction=accountDetaillist&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,4"><img src="../_img/pic_5.gif" />账户明细查询</a></td>
                </tr>
                <tr>
                  <td width="70" align="right">冻结资金：</td>
                  <td><span class="font_style3" style="width: 150px;text-align: left"><fmt:formatNumber pattern="0.00" value="${agent.notallowBalance}"/> 元</span></td>
                </tr>
                <c:if test="${agent.registerType==0}">
                <tr>
                  <td width="70" align="right">账户类型：</td>
                  <td>个人</td>
                </tr>
                </c:if>
                <c:if test="${agent.registerType==1}">
                <tr>
                  <td width="70" align="right">账户类型：</td>
                  <td>企业</td>
                </tr>
                <tr>
                  <td width="70" align="right">企业名称：</td>
                  <td><c:out value="${agent.companyName}"/></td>
                </tr>
                </c:if>
                 <tr>
                  <td width="70" align="right" valign="top">提现银行：</td>
                  <td>
         <c:forEach var="account" items="${alf.list}" varStatus="status">        
     	 <c:choose>
      		<c:when test="${account.bindStatus==1}">
      			<p><c:out value="${account.bankName}"></c:out>:
      			<c:out value="${account.haskCardNo}"></c:out>[<font color="#074CA2">未绑定</font>]
      			<c:if test="${status.count==1}">
      			<a href="agent.do?thisAction=tochangeBindBank">修改绑定卡号</a> &nbsp; 
      			<html:link page="/agent/agent.do?thisAction=checkPayPassword">新增</html:link>
      			&nbsp;&nbsp;<html:link page="/agent/agent.do?thisAction=editBankByAgent">删除</html:link>
      			</c:if>
      			</p>
      	  </c:when>
      	  <c:when test="${account.bindStatus==2}">               
      			<p><c:out value="${account.bankName}"></c:out>:
      			<c:out value="${account.haskCardNo}"></c:out>[<font color="red">绑 定</font>]
      			<c:if test="${status.count==1}">
      			<a href="agent.do?thisAction=tochangeBindBank">修改绑定卡号</a> 
      			&nbsp;<html:link page="/agent/agent.do?thisAction=checkPayPassword">新增</html:link>
      			&nbsp;&nbsp;<html:link page="/agent/agent.do?thisAction=editBankByAgent">删除</html:link>
        		</c:if>
        		</p>
      		</c:when>
      	</c:choose>
      	</c:forEach>
      	<c:if test="${alf.list eq null}">
      	      			<p>未设置&nbsp;&nbsp;<html:link page="/agent/agent.do?thisAction=checkPayPassword">新增</html:link></p>
      	</c:if>
                  </td>
                </tr>  
              </table>
            </div>
          </div>
        </div>
        
         <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
              <div class="rightbox_title">
                <div class="rightbox_title_right">
                  <div class="rightbox_title_count">
                  商户圈
                  </div>
                </div>
              </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="right_count_tabel">
              <tr>
                <td width="70" align="right">商户圈：</td>
                <td> <a href="../agent/agent.do?thisAction=listConterie&showTabMenuIdList=1,6&showSubMenuIdList=0,7,0,6&name=<c:out value='${agent.loginName }'/>" >查看我的商户圈</a></td>
              </tr>
              <tr>
                <td width="70" align="right">推荐人：</td>
                <td>
                <c:if test="${agentRelationship.parentAgent != null}">
				<span class="font_style2"><c:out value="${agentRelationship.parentAgent.name}"></c:out></span>
				</c:if>
                <c:if test="${agentRelationship.parentAgent == null}">
				<span class="font_style2">无</span>
				</c:if>
				</td>
              </tr>
              <tr>
                <td width="70" align="right">业务员：</td>
                <td><html:link page="/agent/agent.do?thisAction=listChildAgent">查看我的业务员</html:link> </td>
              </tr>
            </table>
            </div>
          </div>
        </div>
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
              <div class="rightbox_title">
                <div class="rightbox_title_right">
                  <div class="rightbox_title_count">
                  安全信息
                  </div>
                </div>
              </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="right_count_tabel">
              <tr>
                <td width="70" align="right">登录密码：</td>
                <td>**********&nbsp;&nbsp;&nbsp;<html:link page="/security/changePassword.jsp">修改</html:link><a href="agent.do?thisAction=forgetPassword&type=password">找回登录密码</a></td>
              </tr>
              <tr>
                <td width="70" align="right">支付密码：</td>
                <td>**********&nbsp;&nbsp;&nbsp;<html:link page="/security/changePayPassword.jsp">修改</html:link><a href="agent.do?thisAction=forgetPassword&type=paypassword">找回支付密码</a></td>
              </tr>
              <tr>
                <td width="70" align="right">安全问题：</td>
                <td><html:link page="/security/security.do?thisAction=editQuestionAsk">设置安全保护问题</html:link> </td>
              </tr>
            </table>
            </div>
          </div>
        </div>
        <div class="rightbox_top">
          <div class="rightbox_bottom">
            <div class="rightbox_count">
              <div class="rightbox_title">
                <div class="rightbox_title_right">
                  <div class="rightbox_title_count">
                  交易信息
                  </div>
                </div>
              </div>
            <table cellpadding="0" cellspacing="0" width="100%" class="right_count_tabel">
              <tr>
                <td width="70" align="right">交易记录：</td>
                <td><html:link page="/transaction/transactionlist.do?thisAction=listTransactions&flag=1">查看最近交易记录</html:link></td>
              </tr>
              <tr>
                <td width="70" align="right">联系人：</td>
                <td><html:link page="/agent/agentlist.do?thisAction=listContact&flag=1">交易联系人管理</html:link></td>
              </tr>
            </table>
            </div>
          </div>
        </div>
      </div>
      <div class="clear"></div>
    </div>
    <c:import url="../_jsp/footer.jsp"></c:import>
</div>

</body>
</html>
