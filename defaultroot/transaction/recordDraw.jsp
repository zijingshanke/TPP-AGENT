<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/recordDraw.jsp -->	

	<head>
	<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
	</head>
	<body>
	
<div id="warp">
		<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,3,6" />
	<div id="main">
      <div id="left_box">
        <div class="leftbox_top">
          <div class="leftbox_bottom">
            <div class="leftbox_count">
              <p><a href="../transaction/draw.do?thisAction=withdrawing&showTabMenuIdList=1,6&showSubMenuIdList=0,7,3,6">申请提现</a></p>
              <p class="leftbox_count_p"><a href="../transaction/drawlist.do?thisAction=listDraw&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">提现记录</a></p>
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
                  提现记录
                  </div>
                </div>
              </div>
              <html:form action="/transaction/drawlist.do?thisAction=listDraw" method="post">
				<html:hidden property="thisAction" />
				<html:hidden property="lastAction" />
				<html:hidden property="intPage" />
				<html:hidden property="pageCount" />
              
              <p>
                在此页面您可以查到您的所有提现记录。
               
                   <table cellpadding="0" cellspacing="0" width="100%" class="information_table" style=" margin-top: 20px;">
                      <tr>
                        <td class="right_td">钱门账户：</td>
                        <td><c:out value="${agent.loginName}" /></td>
                      </tr>
                      <tr>
                        <td class="right_td">起止日期：</td>
                        <td><input type="text" class="text_style" name="beginDate" value="<c:out value="${dlf.beginDate}"/>" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/> - <input type="text" class="text_style" value="<c:out value="${dlf.endDate}"/>" name="endDate" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" />
						<font style="color: red;"><c:out value="${msDate}"></c:out></font></td>
                      </tr>
                      <tr>
                        <td class="right_td">处理结果：</td>
                        <td><html:radio styleClass="radio" property="status" value="-1">所有</html:radio>
						<html:radio styleClass="radio" property="status" value="3">成功</html:radio>
						<html:radio styleClass="radio" property="status" value="4">失败</html:radio>
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
                        <th><div>申请时间</div></th>
                        <th><div>提现流水号</div></th>
                        <th><div>提现时间</div></th>
                        <th><div>提现银行</div></th>
                        <th><div>提现卡号</div></th>
                         <th><div>状态</div></th>
                        <th><div>提现金额（元）</div></th>
                      </tr>
                      <tr>
                        <c:forEach var="info" items="${dlf.list}" varStatus="status">
							<tr>
								<td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.requestDate}"/></td>
								<td> <c:out value="${info.orderNo}"></c:out> </td>
								<td><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.drawDate}"/></td>
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
								<td><c:out value="${info.haskCardNo}" /></td>
								<td><c:out value="${info.whereState}" /></td>
								<td><fmt:formatNumber value="${info.amount}" pattern="0.00"/></td>
							</tr>
						</c:forEach>
                      </tr>
                      <tr>
                    	<td colspan="8" class="page_td">
						<div align="right">
							共有记录&nbsp;
							<c:out value="${dlf.totalRowCount}"></c:out>
							&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
							<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a> |
							<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a> |
							<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a> |
							<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
							页数:
							<c:out value="${dlf.intPage}" />
							/
							<c:out value="${dlf.pageCount}" />
							]
						</div>
						</td>
                  	  </tr>
                 </table>

             </html:form>
            </div>
          </div>
        </div>
    </div>
</div>
<c:import url="/_jsp/footer.jsp" />
</div>
	</body>
</html>
