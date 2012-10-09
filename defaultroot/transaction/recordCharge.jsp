<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/recordCharge.jsp -->	

	<head>
	<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/popcalendar.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
	
	</head>
	<body>
	<div id="warp">
				<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,2,6" />
			<div id="main">
				<div id="container" class="register">
					<div id="left_box">
						<div class="leftbox_top">
							<div class="leftbox_bottom">
								<div class="leftbox_count">
									<p >
										<html:link page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" > 给本账户充值 </html:link>
									</p>
									<p>
										<html:link page="/transaction/charge.do?thisAction=rechargeOther&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" >给其他账户充值</html:link>
									</p>
									<p class="leftbox_count_p">
										<html:link page="/transaction/chargelist.do?thisAction=listCharge&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" >查看充值记录</html:link>
									</p>
									<p>
										<html:link page="/transaction/charge.do?thisAction=applicationCharge&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" >线下充值申请</html:link>
									</p>
								</div>
							</div>
						</div>
					</div>
					<div id="right_box">
						<div class="rightbox_top">
							<div class="rightbox_bottom">
								<div class="rightbox_count">
									<div class="rightbox_title">
										<div class="rightbox_title_right">
											<div class="rightbox_title_count">
												充值记录 
											</div>
										</div>
									</div>
									<!-- 开始 -->
									
		<html:form action="/transaction/chargelist.do?thisAction=listCharge" method="post">
			
			<html:hidden property="thisAction" />
			<html:hidden property="lastAction" />
			<html:hidden property="intPage" />
			<html:hidden property="pageCount" />
			<p>在此页面您可以查到您的所有充值记录。</p>
               
                   <table cellpadding="0" cellspacing="0" width="100%" class="information_table" style=" margin-top: 20px;">
                      <tr>
                        <td class="right_td">钱门账户：</td>
                        <td><c:out value="${agent.loginName}" /></td>
                      </tr>
                      <tr>
                        <td class="right_td">起止日期：</td>
                        <td><input type="text" class="text_style" name="beginDate" value="<c:out value="${clf.beginDate}"/>"  onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/> - <input type="text" class="text_style" name="endDate" value="<c:out value="${clf.endDate}"/>" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" />
						<font style="color: red;"><c:out value="${msDate}"></c:out>
						</font></td>
                      </tr>
                      <tr>
                        <td class="right_td">处理结果：</td>
                        <td><html:radio styleClass="radio" property="status" value="-1" >所有</html:radio>
							<html:radio styleClass="radio" property="status" value="1">成功</html:radio>
							<html:radio styleClass="radio" property="status" value="2">失败</html:radio>
							<html:radio styleClass="radio" property="status" value="0">处理中</html:radio>
						</td>
                      </tr>
                      <tr>
                        <td >&nbsp;</td>
                        <td><span class="simplyBtn_1" style="margin-left: 0;"><input type="submit" class="buttom_middle" value="查询" /></span></td>
                      </tr>
                   </table>
              
               <table cellpadding="0" cellspacing="0" width="100%" class="deal_table" style="margin-top: 12px;">
                      <tr>
                        <th><div>充值时间</div></th>
                        <th><div>充值流水号</div></th>
                        <th><div>充值银行</div></th>
                        <th><div>状态</div></th>
                        <th><div>充值金额（元）</div></th>
                      </tr>
                      <tr>
                        <c:forEach var="info" items="${clf.list}" varStatus="status">
							<tr>
								<td>
									<fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.chargeDate}"/>
								</td>
								<td>
									<c:out value="${info.orderNo}"></c:out>
								</td>
								<td>
								<c:if test="${info.bank eq 'CCB'}">建设银行</c:if>
								<c:if test="${info.bank eq 'ICBC'}">工商银行</c:if>
								<c:if test="${info.bank eq 'BCM'}">交通银行</c:if>
								<c:if test="${info.bank eq 'CMBC'}">民生银行</c:if>
								<c:if test="${info.bank eq 'ABC'}">农业银行</c:if>
								<c:if test="${info.bank eq 'BOC'}">中国银行</c:if>
								<c:if test="${info.bank eq 'CMB'}">招商银行</c:if>
								<c:if test="${info.bank eq 'CIB'}">兴业银行</c:if>
								<c:if test="${info.bank eq 'BOB'}">北京银行</c:if>
								<c:if test="${info.bank eq 'CEB'}">光大银行</c:if>
								<c:if test="${info.bank eq 'CITIC'}">中信银行</c:if>
								<c:if test="${info.bank eq 'GDB'}">广发银行</c:if>
								<c:if test="${info.bank eq 'SPDB'}">浦发银行</c:if>
								<c:if test="${info.bank eq 'SDB'}">深发银行</c:if>
								</td>
								<td>
									<c:out value="${info.state}"></c:out>
								</td>
								<td>
									<fmt:formatNumber value="${info.amount}" pattern="0.00"/>
								</td>
							</tr>
						</c:forEach>
                      </tr>
                      <tr>
                    	<td colspan="5" class="page_td">
						<div align="right">
							共有记录&nbsp;
							<c:out value="${clf.totalRowCount}"></c:out>
							&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
							<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
							|
							<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
							|
							<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
							|
							<a href="JavaScript:turnToPage(document.forms[0],3)">
								末页</a>] 页数:
							<c:out value="${clf.intPage}" />
							/
							<c:out value="${clf.pageCount}" />
							]
						</div>
					</div>
						</td>
                  	  </tr>
                 </table>
             
             </html:form>
            </div>
          </div>
        </div>
    </div>
    <c:import url="/_jsp/footer.jsp" />
</div>
	</body>
</html>


