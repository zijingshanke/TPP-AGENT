<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/batchCollectDetail.jsp -->

  <head> 
<title>钱门支付！--网上支付！安全放心！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>
	<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
	<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script language="javascript">
		
		function searchData(){
			document.forms[0].submit();
		}
	</script>
  </head>
  
  <body>
    <div id="warp">
    <c:import url="/_jsp/topMenu.jsp?a=1,0&b=0,1,2,3"/>
    <div class="main_top">
      <div class="main_bottom">
        <div class="main_mid">
       <div class="main_title">
            <div class="main_title_right">
        	<p><strong>批量收款记录</strong> <html:link page="/transaction/transaction.do?thisAction=batchCollect&paytype=5" title="">发起收款</html:link></p>
        </div>
       </div>
          <div class="com-search fn-clear">
				<html:form action="/transaction/transactionlist.do?thisAction=getBatchCollectDetail&tabMenuList=1,0&subMenuList=0,1,2,3" method="post">
					<table cellpadding="0" cellspacing="0" width="100%" class="information_table">
		            <tr>
		            <td class="right_td">筛选日期：</td> <td><input type="text" class="text_style" name="beginDate" id="beginDate" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /> - <input type="text" class="text_style" name="endDate" id="endDate"  onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" /> <input type="button" onclick="searchData();" class="btn1" value="确定" /></td>
		            </tr>
		            <tr>
		              <td class="right_td">收款理由或说明:</td><td><input class="text_style" type="text" name="searchKey" id="searchKey" class="i-text i-text-gray" /> &nbsp;&nbsp;<input type="button" onclick="searchData();" class="btn1" value="搜索" /></td>
		            </tr>
		            </table>
		         </html:form>
		        <div class="deal_list">
		        <table cellpadding="0" cellspacing="0" width="100%" class="deal_table">
                    <tr>
         				<th><div>日期</div></th> 
                        <th><div>名称</div></th> 
                        <th><div>总金额(元)</div></th> 
                        <th><div>状态</div></th> 
                    </tr>
                <c:forEach var="info" items="${tlf.list}" varStatus="statu">
					<tr>
	                    <td><span><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${info.createDate}"/></span></td>
	                    <td><span><c:out value="${info.shopName}" />
	                    <c:if test="${info.detailsContent!=null}">
	                       （<c:out value="${info.detailsContent}" />）
	                    </c:if>
	                    </span></td>
	                    <td><span><fmt:formatNumber pattern="0.00" value="${info.shopPrice}"/></span></td>
						<td><html:link page="/transaction/transactionlist.do?thisAction=getBatchCollectDetailById&paytype=5&orderid=${info.id}"><c:out value="${info.batchStatus}" /></html:link></td>
					</tr>
				</c:forEach>
            </table>
       		 </div>
    
    </div>
    </div>
    </div>
    </div>
    <c:import url="/_jsp/footer.jsp"/>
  </body>
</html>
