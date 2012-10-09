<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: transaction/withdrawing.jsp -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>钱门支付！--网上支付！安全放心！</title>
		<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		<style type="text/css">
#loft_win {
	border: #FF9000 1px solid;
	background-color: #FFFFFF;
}

.loft_win_head {
	color: #FFFFFF;
	font-size: 13px;
	background-color: #FF9000;
	height: 25px;
	padding: 0, 5, 0, 5;
}
</style>
		<script type="text/javascript">
function initWindow(){
	var email="<c:out value="${URI.agent.email}"></c:out>"
	var validCertStatus="<c:out value="${URI.agent.validCertStatus}"></c:out>";
	if(validCertStatus==0){
		msg();
	}
}
  function apply()
  {	
  	  var registerType="<c:out value="${agent.registerType}"/>";
	  var msp=document.getElementById("mgpwd");
	  var msp1=document.getElementById("mgpwd1");
	  msp.innerHTML="";
	  var msa=document.getElementById("mgauot");
	  msa.innerHTML="";
	   var amount=document.forms[0].amounts.value;
	   
  	if(/^(([1-9]\d+|[1-9])|0)(\.\d\d?)*$/.test(amount))
  	{
  		if(document.forms[0].payPassword.value=="")
  		{
  			 msp1.style.display ="none";
  			 msp.innerHTML="支付密码不能为空";
  			return false;
  		}
  	}else{
  			msa.innerHTML="不能为空并且最多两位小数不能有空格";
  			document.forms[0].amounts.value="";
  			msp1.style.display ="none";
  			msp.innerHTML="";
  			return false;
  		}
  		
  		 if (confirm("您确定提现 "+amount+" 元吗？")) {
  		 		document.forms[0].submit();
  		 	}
  			
}
  </script>

	</head>

	<body onload="initWindow();">
		<div id="warp">
			<c:import url="/_jsp/topMenu.jsp?a=1,6&b=0,7,3,6" />
			<div id="main">
				<div id="left_box">
					<div class="leftbox_top">
						<div class="leftbox_bottom">
							<div class="leftbox_count">
								<p class="leftbox_count_p">
									<a
										href="../transaction/draw.do?thisAction=withdrawing&showTabMenuIdList=1,6&showSubMenuIdList=0,7,3,6">申请提现</a>
								</p>
								<p>
									<a
										href="../transaction/drawlist.do?thisAction=listDraw&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">提现记录</a>
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
											申请提现
										</div>
									</div>
								</div>
								<div class="chongzhi">
									<p>
										钱门账户不允许从事无真实交易背景的虚假交易、银行卡转账套现或洗钱等禁止的交易行为，否则充值款项将不能提现。
									</p>
									<div class="yellowBox yellowBox_mes">
										<c:if test="${agent.registerType==1}">
               您是企业账户，每笔的最高提现金额为<c:out
												value="${agent.maxItemDrawAmount}"></c:out>元！     <a
												href="../transaction/drawRules.jsp">查看提现规则</a>
										</c:if>
										<c:if test="${agent.registerType == 0}">
                您是个人账户，每笔的最高提现金额为<c:out
												value="${agent.maxItemDrawAmount}"></c:out>元！     <a
												href="../transaction/drawRules.jsp">查看提现规则</a>
										</c:if>
									</div>
									<div class="chongzhi">
										<div class="id_card_menu">

											<a class="at_id_card" id="tixian2">申请提现</a>
											<div class="clear"></div>
										</div>

										<div class="id_card_chongzhi id_card_tixian2">
											<html:form action="/transaction/draw.do?thisAction=addDraw"
												styleClass="tixianBox1">
												<html:hidden property="bank" value="${account.bankSname}"></html:hidden>
												<html:hidden property="cardNo" value="${account.cardNo}"></html:hidden>
												<table class="tixian_table" width="100%">
													<tr>
														<td colspan="2">
															<strong style="font-size: 14px; margin-left: 12px;">钱门账户信息</strong>
														</td>
													</tr>
													<tr>
														<td class="right_td">
															真实姓名：
														</td>
														<td>
															<c:out value="${agent.name}" />
														</td>
													</tr>
													<c:if test="${agent.registerType == 1}">
														<tr>
															<td class="right_td">
																企业名称：
															</td>
															<td>
																<c:out value="${agent.companyName}" />
															</td>
														</tr>
													</c:if>

													<tr>
														<td class="right_td">
															钱门账户：
														</td>
														<td>
															<c:out value="${agent.loginName}" />
														</td>
													</tr>
													<tr>
														<td class="right_td" valign="top">
															提现金额：
														</td>
														<td>
															<html:text property="amounts" value=""
																styleClass="text_style"></html:text>
															元
															<div id="mgauot" style="color: red;"></div>
															<font style="color: red;"> <c:if
																	test="${msgAmount eq 'amount3'}">
                                    	当天申请次数达到三次！<a
																		href='../transaction/drawRules.jsp'>查看提现规则</a>
																</c:if> </font>
															<p style="color: #999; margin: 8px 0 0 0; padding: 0">
																当前可提现金额：
																<em style="color: 005C9C; margin-right: 8px;"><fmt:formatNumber
																		value="${agent.allowBalance}" pattern="0.00" />
																</em>元
															</p>
														</td>
													</tr>
													<tr>
														<td class="right_td">
															支付密码：
														</td>
														<td>
															<html:password property="payPassword" value=""
																styleClass="text_style"></html:password>
															<div id="mgpwd1">
																<font style="color: red;"><c:out
																		value="${msgPayPassword}" />
																</font>
															</div>
															<div id="mgpwd" style="color: red;"></div>
														</td>

													</tr>
													<tr>
														<td>
															&nbsp;
														</td>
														<td class="td_span">
															<span class="simplyBtn_1"><input class="btn1"
																	type="button" name="sub" value="申 请" onclick="apply();" />
															</span><a
																href="../agent/agent.do?thisAction=forgetPassword&type=paypassword">找回支付密码</a>
														</td>
													</tr>
												</table>
											</html:form>
											<div class="tixianBox2">
												您正在将钱门账户中的资金转入到您指定的银行账号中
											</div>
											<form class="tixianBox1 tixianBox3">
												<table class="tixian_table " width="100%">
													<tr>
														<td colspan="2">
															<strong style="font-size: 14px; margin-left: 12px;">银行账号信息</strong><a
																href="../agent/agent.do?thisAction=tochangeBindBank">(更换银行账号)</a>
														</td>
													</tr>
													<tr>
														<td colspan="2" style="color: #C00; padding-left: 16px;">
															开户名称必须与“
															<c:if test="${agent.registerType == 1}">
																<c:out value="${agent.companyName}"></c:out>
															</c:if>
															<c:if test="${agent.registerType == 0}">
																<c:out value="${agent.name}"></c:out>
															</c:if>
															”一致
														</td>
													</tr>
													<c:if test="${agent.registerType == 1}">
														<tr>
															<td class="right_td">
																账户类型：
															</td>
															<td>
																企业用户
															</td>
														</tr>
														<tr>
															<td class="right_td">
																企业名称：
															</td>
															<td>
																<c:out value="${agent.companyName}" />
															</td>
														</tr>
													</c:if>
													<c:if test="${agent.registerType == 0}">
														<tr>
															<td class="right_td">
																账户类型：
															</td>
															<td>
																个人用户
															</td>
														</tr>
													</c:if>
													<tr>
														<td class="right_td">
															银行名称：
														</td>
														<td>
															<c:out value="${account.bankName}"></c:out>
														</td>
													</tr>
													<tr>
														<td class="right_td">
															银行账号：
														</td>
														<td>
															<c:out value="${account.haskCardNo}"></c:out>
														</td>
													</tr>
													<tr>
														<td colspan="2">
															<div class="yellowBox yellowBox_1">
																第一次提现请确保账户内有0.01元以上金额，并且请在存入金额后次日提现
															</div>
														</td>
													</tr>
												</table>
											</form>
											<div class="clear"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<c:import url="/_jsp/footer.jsp" />
		</div>

		<!-- 弹出层 -->
		<div
			style="position: absolute; border: 1px solid #FF9000; background-color: #FFFFFF; right: 0; top: 100px; width: 254px; height: 164px;"
			id="height_from">
			<table width="100%" border=0>
				<tr>

					<td width="100%" valign="top" align="center">
						<table cellSpacing="0" cellPadding="0" width="100%" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td width="70" class="loft_win_head">
									&nbsp; 温馨提示
								</td>
								<td width="26" class="loft_win_head">
								</td>
								<td align="right" class="loft_win_head">
									<span
										style="CURSOR: hand; font-size: 12px; font-weight: bold; margin-right: 4px;"
										onclick="closeDiv();">×</span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<c:if test="${views<3}">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<img src="../_img/reminder .gif" />
							&nbsp;
							<font color="red">您今天还能提现<c:out value="${3-views}" />次！</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${views==3}">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<img src="../_img/reminder .gif" />
							&nbsp;
							<font color="red">您今天提现次数已经达到3次，请明天再进行提现操作！</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${msgAmount eq 'amount3'}">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<img src="../_img/reminder .gif" />
							&nbsp;
							<font color="red">您今天提现次数已经达到3次，请明天再进行提现操作！</font>
						</td>
					</tr>
				</c:if>
				<c:if test="${msgPayPassword != null}">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<img src="../_img/reminder .gif" />
							&nbsp;
							<font color="red"><c:out value="${msgPayPassword}" /> </font>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
						<img src="../_img/_r1_c1.gif" />
						&nbsp;每笔提现金额不能大于核定的提现金额！
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
						<img src="../_img/_r1_c1.gif" />
						&nbsp;您每笔的最高提现金额为
						<c:out value="${agent.maxItemDrawAmount}"></c:out>
						元！
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
						<img src="../_img/_r1_c1.gif" />
						&nbsp;无论金额大小，每天提现次数为三次。
					</td>
				</tr>
			</table>

		</div>
		<!-- 弹出层 -->
		<script language="JavaScript" type="text/javascript">
	var i=0;
	function rightBottomAd(){
 		i+=1;
  		//if(i>100) closeDiv(); //想不用自动消失由用户来自己关闭所以屏蔽这句
		var abc = document.getElementById("height_from");
		abc.style.top = document.documentElement.scrollTop+document.documentElement.clientHeight-165+"px";
		setTimeout(function(){rightBottomAd();},50);
	}
	rightBottomAd();
	//关闭
	 function closeDiv()
	 {
	  document.getElementById('height_from').style.visibility='hidden';
	 }
</script>
</html>