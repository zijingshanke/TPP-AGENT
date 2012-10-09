<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: transaction/applicationCharge.jsp -->
	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />

<script type="text/javascript">
function emaild()
{	
	var amou=document.forms[0].amount.value;
	var mgamot=document.getElementById("mgamount");
	mgamot.innerHTML="";
	var note=document.forms[0].note.value;
	var mgnote=document.getElementById("mgnote");
	mgnote.innerHTML="";
	var re=/^([1-9]|[0.])[0-9]{0,}(([.]*\d{1,2})|[0-9]{0,})$/;
	if(!re.test(amou) || amou<0.01 || amou>1000000){
		mgamot.innerHTML="金额为数字，最多为两位小数并且不能大于1000000！";
		mgnote.innerHTML="";
		return false;
	}
	
	if(note==""){
		mgnote.innerHTML="备注不能为空！";
		mgamot.innerHTML="";
		return false;
	}
	document.forms[0].submit();
}
  </script>
		</head>
		<body>
			<html:form  action="/transaction/charge.do?thisAction=applicationChargeGo">
			<input type="hidden" name="agentId" value="<c:out value="${agent.id}" />"/>
			<input type="hidden" name="type" value="<c:out value="${chargeType}"/>"/>
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
									<p >
										<html:link page="/transaction/chargelist.do?thisAction=listCharge&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" >查看充值记录</html:link>
									</p>
									<p  class="leftbox_count_p">
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
												线下充值申请
											</div>
										</div>
									</div>
									<!-- 开始 -->
	
				<div class="chongzhi">
                如果您与钱门公司有密切的业务往来，并且签署了相关协定，您就可以使用线下充值功能。
                <p style="margin:12px 0 0 0; padding:0;">申请成功后，请耐心的等待3-5个工作日！我们的工作人员会对您的申请进行处理！ <em style="font-style:normal; font-weight:bold; color:#005C9C;">请谨慎操作！</em></p>
				
					<table  cellpadding="0" cellspacing="0" width="100%" class="information_table" style="margin-top: 30px;">
					<tr>
							<td class="right_td">您的钱门账号：</td>
							<td style="color:#005C9C">
								<c:out value="${agent.loginName}" /><br />
							</td>
							<td></td>
						</tr>
						<tr>
							<td class="right_td"><span class="font_style6">*</span> 充值金额：</td>
							<td>
								<input type="text" name="amount" class="text_style"/><input type="hidden" name="bank" value="OTHER" />
							</td>
							<td>
								<div id="mgamount" style="color: red;"><font color="red"><c:out value="${msgamount}"/></font></div>
							</td>
						</tr>
					<tr>
                      <td class="td_span">&nbsp;</td>
                      <td class="td_span">金额为数字，最多为两位小数并且不能大于1000000！</td>
                    </tr>
                    <tr>
							<td class="right_td"><span class="font_style6">*</span> 备注：</td>
							<td>
								<input type="text" name="note" class="text_style"/>
							</td>
							<td>
								<div id="mgnote" style="color: red;"></div>
							</td>
					</tr>
					<tr>
                      <td class="td_span">&nbsp;</td>
                      <td class="td_span">请简要说明您的申请原因！</td>
                    </tr>
						<tr><td colspan="2">
						<span class="simplyBtn_1" style="margin-left:167px;">
						<input type="button" class="buttom_middle" value="申 请" onclick="emaild();"/>
						</span>
						</td></tr>
					</table>
						<!-- 结束 -->
       </div>
             </div>
            </div>
          </div>
        </div>
    </div>
</html:form>
	<!-- 结束 -->
			</div>
					<c:import url="/_jsp/footer.jsp" />
		</form>
		</body>

	</html>


