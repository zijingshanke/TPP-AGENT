<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<!--JSP页面: transaction/rechargeable.jsp -->

	<head>
		<title>钱门支付！--网上支付！安全放心！</title>
		<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
		<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />
		
		<script type="text/javascript">
		//客户端校验数据
		function checkData(){
			var mgamot=document.getElementById("mgamount");		
			var amou=document.forms[0].amount.value;	

			mgamot.innerHTML="";
			var re=/^([1-9]|[0.])[0-9]{0,}(([.]*\d{1,2})|[0-9]{0,})$/;
			if(!re.test(amou) || amou<0.01 || amou>1000000){
				mgamot.innerHTML="金额为数字，最多为两位小数并且不能大于1000000！";
				return false;
			}else{
				return true;
			}	
		}
		
		function sendToBank(){
			if(checkData()){
				if(setBankVersionValue()){
					document.forms[0].submit();
				}				
			}
		}

		function cz(){
			var CZ=document.getElementById("czSelect");
			var mgcz=document.getElementById("mgChongzhi");
			mgcz.innerHTML="";
				if(CZ.options[CZ.selectedIndex].value==0){
					sle(2);
				}else if(CZ.options[CZ.selectedIndex].value==1){
					mgcz.innerHTML="此业务还未开通！";
				}
		 }
		 
		function sle(no){
		 var div1=document.getElementById("DIV1");
		 var div2=document.getElementById("DIV2");
		 var a1=document.getElementById("a1");
		 var a2=document.getElementById("a2");
			if(no==2){	
				div1.style.display="";
				div2.style.display="none";
				a1.className="";
				a2.className="at_id_card";
			}
			if(no==1){				
				div1.style.display="none";
				div2.style.display="";
				a1.className="at_id_card";
				a2.className="";
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
									<p class="leftbox_count_p">
										<html:link
											page="/transaction/charge.do?thisAction=rechargeable&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6"> 给本账户充值 </html:link>
									</p>
									<p>
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
												给本账户充值
											</div>
										</div>
									</div>
									<div class="chongzhi">
										即日起，每月给钱门账户充值，单笔不小于100元即可当月获得1次积分奖励（消费卡充值除外）：
										<table cellpadding="0" cellspacing="0" width="100%"
											class="deal_table" style="margin-top: 12px;">
											<tr>
												<th>
													<div>
														会员状态
													</div>
												</th>
												<th>
													<div>
														注册会员
													</div>
												</th>
												<th>
													<div>
														手机会员
													</div>
												</th>
												<th>
													<div>
														认证会员
													</div>
												</th>
												<th>
													<div>
														手机绑定且认证会员
													</div>
												</th>
											</tr>
											<tr>
												<td>
													充值奖励积分数
												</td>
												<td>
													<em>50</em>
												</td>
												<td>
													<em>100</em>
												</td>
												<td>
													<em>100</em>
												</td>
												<td>
													<em>150</em>
												</td>
											</tr>
										</table>
										<div class="chongzhi">
											<div class="id_card_menu">
												<a href="javascript:sle(1);" id="a1">充值向导</a>
												<a href="javascript:sle(2);" id="a2" class="at_id_card">网上银行</a>
												<div class="clear"></div>
											</div>
											<div id="DIV1">
												<div class="id_card_chongzhi id_card_chongzhi1"
													style="padding-left: 0; padding-right: 0; padding-bottom: 0;">
													<html:form
														action="/transaction/charge.do?thisAction=waitCharge">
														<html:hidden property="version"></html:hidden>
														<html:hidden property="bank"></html:hidden>
														<html:hidden property="type" value="0,0"></html:hidden>
														<table cellpadding="0" cellspacing="0" width="100%"
															class="information_table">
															<tr>
																<td class="right_td">
																	真实姓名：
																</td>
																<td colspan="3">
																	<c:out value="${agent.name}" />
																</td>
															</tr>
															<c:if test="${agent.registerType==1}">
																<tr>
																	<td class="right_td">
																		企业名称：
																	</td>
																	<td colspan="3">
																		<c:out value="${agent.companyName}" />
																	</td>
																</tr>

															</c:if>
															<tr>
																<td class="right_td">
																	钱门账户：
																</td>
																<td colspan="3" style="color: #005C9C">
																	<c:out value="${agent.loginName}" />
																</td>
															</tr>
															<tr>
																<td class="right_td">
																	<span class="font_style6">*</span>充值金额：
																</td>
																<td colspan="3">
																	<html:text property="amount" styleClass="text_style"></html:text>
																	元
																	<div id="mgamount" style="color: red"></div>
																</td>
															</tr>
															<!-- 选择银行 -->
															<c:import url="../bank/selectBank.jsp"></c:import>
															<tr>
																<td>
																	&nbsp;
																</td>
																<td colspan="3" class="yellowBox"
																	style="padding-left: 30px;">
																	钱门禁止
																	<em style="font-style: normal; color: #C00;">信用卡套现、银行卡转账、虚假交易</em>等行为，一经发现将予以处罚，包括但不限于:限制收款、冻结账户、永久停止服务，并有可能影响相关信用记录。
																</td>
																<td>
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td colspan="4">
																	<html:hidden property="agentId" value="${agent.id}"></html:hidden>
																	<span class="simplyBtn_1" style="margin-left: 167px;">
																		<input type="button" class="buttom_middle" value="下一步"
																			onclick="sendToBank();" /> </span>
																</td>
															</tr>
															<tr>
																<td>
																	&nbsp;
																</td>
															</tr>
														</table>
													</html:form>
												</div>
											</div>
											<div id="DIV2" style="display: none;">
												<div class="id_card_chongzhi id_card_chongzhi1"
													style="padding-left: 0; padding-right: 0; padding-bottom: 0;">
													<form class="changeChongzhi" style="padding: 0 10px;">
														<table>
															<tr>
																<td>
																	请选择充值方式：
																</td>
																<td>
																	<select id="czSelect" size="1" style="width: 150px;">
																		<option value="0">
																			银行充值
																		</option>
																		<option value="1">
																			网点充值
																		</option>
																	</select>
																</td>
																<td>
																	<input type="button" class="btn1" value="下一步"
																		onclick="cz();" />
																</td>
																<td>
																	<div id="mgChongzhi" style="color: red;"></div>
																</td>
															</tr>
														</table>
													</form>
													<a href="#" style="font-size: 13px">充值送积分，参加抽奖</a>
													<table align="center"
														style="line-height: 24px; background: #FDFDD0; border: 1px solid #B27FB2"
														width="97%">
														<tr>
															<td colspan="2"
																style="font-weight: bold; padding: 0 10px;">
																钱门给您提供了以下两种简单的充值方式，通过任意一种方式均可以实现“充值到钱门”：
															</td>
														</tr>
														<tr>
															<td colspan="2"
																style="font-weight: bold; padding: 0 10px;">
																<hr style="color: #6B7E92" noshade="noshade" />
															</td>
														</tr>
														<tr>
															<td width="15%" valign="top" style="padding: 0 15px;">
																银行充值：
															</td>
															<td width="85%" style="color: #666; padding: 0 10px;"
																align="left">
																您可以选择和钱门公司合作的十二家银行中的任意一家银行办卡，并开通该卡的网上支付功能，您就可以对钱门账户进行充值。
															</td>
														</tr>
														<tr>
															<td style="padding: 0 15px;">
																网点充值：
															</td>
															<td style="color: #666; padding: 0 10px;" align="left">
																您可以在钱门合作网点，购买钱门充值码，拥有钱门充值码就可以给钱门账户充值。
															</td>
														</tr>
													</table>
													<div>
														&nbsp;
													</div>
												</div>
											</div>
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
	</body>
</html>
