<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<!--JSP页面: transaction/rechargeOtherGo.jsp -->
	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen1.css" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<script>
		function sendToBank(){
			if(checkData()){
				if(setBankVersionValue()){
					document.forms[0].submit();
				}				
			}
		}
		
		//客户端校验数据
		function checkData(){
			var mgamot=document.getElementById("mgamount");		
			var amou=document.forms[0].amounts.value;	

			mgamot.innerHTML="";
			var re=/^([1-9]|[0.])[0-9]{0,}(([.]*\d{1,2})|[0-9]{0,})$/;
			if(!re.test(amou) || amou<0.01 || amou>1000000){
				mgamot.innerHTML="金额为数字，最多为两位小数并且不能大于1000000！";
				return false;
			}else{
				document.forms[0].amount.value=amou;
				return true;
			}	
		}
  </script>
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
									<p>
										<html:link
											page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6"> 给本账户充值 </html:link>
									</p>
									<p class="leftbox_count_p">
										<html:link
											page="/transaction/charge.do?thisAction=rechargeOther&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">给其他账户充值</html:link>
									</p>
									<p>
										<html:link
											page="/transaction/chargelist.do?thisAction=listCharge&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">查看充值记录</html:link>
									</p>
									<p>
										<html:link
											page="/transaction/charge.do?thisAction=applicationCharge&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">线下充值申请</html:link>
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
												给其他账户充值
											</div>
										</div>
									</div>
									<!-- 开始 -->
									<html:form
										action="/transaction/charge.do?thisAction=waitChargeOther">
										<html:hidden property="bank"></html:hidden>
										<html:hidden property="version"></html:hidden>
										<html:hidden property="type" value="${chargeType}"></html:hidden>
										<html:hidden property="remark" value="${agent.loginName}"></html:hidden>
										<table cellpadding="0" cellspacing="0" width="100%"
											class="information_table">
											<tr>
												<td align="right" class="right_td">
													您要充值的账户名：
												</td>
												<td colspan="3">
													<c:out value="${sagent.name}" />
												</td>

											</tr>
											<tr>
												<td align="right" class="right_td">
													您要充值的钱门账户：
												</td>
												<td colspan="3" style="color: #005C9C">
													<c:out value="${sagent.loginName}" />
												</td>
											</tr>
											<tr>
												<td align="right">
													充值金额：
												</td>
												<td>
													<div>
														<input type="text" class="text_style" name="amounts"
															value="" />
														元&nbsp;&nbsp;&nbsp;
														<b id="mgamount" style="color: red;"></b>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<html:hidden property="agentId" value="${agent.id}"></html:hidden>
													<html:hidden property="amount" value="" />
													<html:hidden property="agentOtherId" value="${sagent.id}" />
												</td>
											</tr>
											<!-- 选择银行 -->
											<c:import url="../bank/selectBank.jsp"></c:import>
											<tr>
												<td>
													&nbsp;
												</td>
												<td>
													<input class="btn1" type="button" value="上一步"
														onclick="history.go(-1);" />
													<input class="btn1" type="button" name="sub" value="充值"
														onclick="sendToBank();" />
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
