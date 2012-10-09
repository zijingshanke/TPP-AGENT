<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<html>
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css2/shining.css" type="text/css" />
		<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script type="text/javascript">
   var returnUrl="<c:out value='${returnUrl}'/>";
   var chargeType="<c:out value='${chargeType}'/>";
   function finish()
   {	
     returnUrl=returnUrl.replace("&","^");
     returnUrl=returnUrl.replace("&amp;","^");
     window.open("http://www.qmpay.com/transaction/finishCharge.jsp?myReturnUrl="+returnUrl,"myNewWin");
     return 1;
   }
</script>

	</head>
	<body>
		<div style="text-align: center; height: 100%;">
			<table align="center">
				<c:choose>
					<c:when test="${chargeStatus=='Success'}">
						<tr>
							<td style="font-size: 18px; color: red;">
								<c:out value="${chargeInfo}" />
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" value="确定" class="btn1" onclick="finish()" />
								&nbsp;
							</td>
						</tr>
					</c:when>
					<c:when test="${chargeStatus=='Failed'}">
						<tr>
							<td style="font-size: 18px; color: red;">
								<c:out value="${chargeInfo}" />
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" value="关闭窗口" class="btn1"
									onclick="Javascript:self.close();" />
								&nbsp;
							</td>
						</tr>
					</c:when>
				</c:choose>
			</table>
		</div>
	</body>
	<c:if test="${chargeStatus=='Success'}">
		<script type="text/javascript" language="javascript">	
		var val=finish();	
		var count=0;
		function closeCharge()
		{		 
		  if(val==1)
		  {
		    window.opener=null;
	        window.close(); //SELF WINDOWS
	      }
	      else
	      {
	        count++;
	        if(count++<5)
	          window.setTimeout("closeCharge()",3000);	
	      }
		}	
		 window.setTimeout("closeCharge()",3000);		
		</script>
	</c:if>
</html>
