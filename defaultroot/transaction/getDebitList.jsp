<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/getDebitList.jsp -->	

	<head>
	<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
	</head>
	<body>
	
<div id="warp">
		<c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,3,3" />
	<div id="main">
      <div id="left_box">
        <div class="leftbox_top">
          <div class="leftbox_bottom">
            <div class="leftbox_count">
              <p><a href="../transaction/transaction.do?thisAction=debit">申请预支</a></p>
              <p class="leftbox_count_p"><a href="../transaction/transactionlist.do?thisAction=getDebitList">预支记录</a></p>
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
                  预支记录
                  </div>
                </div>
              </div>
              <html:form action="/transaction/transactionlist.do?thisAction=getDebitList" method="post">
				<html:hidden property="thisAction" />
				<html:hidden property="lastAction" />
				<html:hidden property="intPage" />
				<html:hidden property="pageCount" />
                   <table cellpadding="0" cellspacing="0" width="100%" class="information_table" style=" margin-top: 20px;">
                      <tr>
                        <td class="right_td">起止日期：</td>
                        <td><input type="text" class="text_style" name="beginDate" value="<c:out value="${tlf.beginDate}"/>" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/> - <input type="text" class="text_style" value="<c:out value="${tlf.endDate}"/>" name="endDate" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" />
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
                        <th width="20%"><div align="center">申请时间</div></th>
                        <th width="20%"><div align="center">预支单号</div></th>
                        <th width="35%"><div align="center">申请原因</div></th>
                        <th width="15%"><div align="center">预支金额（元）</div></th>
                        <th width="20%"><div align="center">状态</div></th>
                      </tr>
                      <tr>
                        <c:forEach var="info" items="${tlf.list}" varStatus="status">
							<tr>
								<td align="center"><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.applyDate}"/></td>
								<td align="center"><c:out value="${info.no}"/></td>
								<td align="center"><c:out value="${info.remark}"/></td>
								<td align="center"><fmt:formatNumber value="${info.amount}" pattern="0.00"/></td>
								<td align="center"><c:out value="${info.debitStatus}" /></td>
							</tr>
						</c:forEach>
                      </tr>
                      <tr>
                    	<td colspan="8" class="page_td">
						<div align="right">
							共有记录&nbsp;
							<c:out value="${tlf.totalRowCount}"></c:out>
							&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
							<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a> |
							<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a> |
							<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a> |
							<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
							页数:
							<c:out value="${tlf.intPage}" />
							/
							<c:out value="${tlf.pageCount}" />
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
