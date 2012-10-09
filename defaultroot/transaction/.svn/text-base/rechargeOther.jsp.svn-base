<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--JSP页面: transaction/rechargeOther.jsp -->
	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>钱门支付！--网上支付！安全放心！</title>
<link rel="stylesheet" href="../_css/shining.css" type="text/css" />
<link href="../_css/qianmen1.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../_css/qianmen.css" type="text/css" />

<script type="text/javascript">
function emaild()
{	
	
	var tagEmail=document.forms[0].tagentEmail.value;
	var mail=document.getElementById("mgemail");
	mail.innerHTML="";
	var regm = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!tagEmail.match(regm))
		{
			mail.innerHTML="对方账户为空、出现空格或者Email格式不正确！";
			return false;
		}else if(tagEmail=='<c:out value="${agent.loginName}" />'){
			mail.innerHTML="您不能给自己充值！";
			return false;
		}
	document.forms[0].submit();
}
  </script>
		</head>
		<body>
			<html:form  action="/transaction/charge.do?thisAction=rechargeOther">
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
									<p class="leftbox_count_p">
										<html:link page="/transaction/charge.do?thisAction=rechargeOther&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6" >给其他账户充值</html:link>
									</p>
									<p >
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
												给其他账户充值
											</div>
										</div>
									</div>
									<!-- 开始 -->
	
				<div class="chongzhi">
                如果您的亲友需要用钱门购买商品，但账户中没有资金，您可以使用此代充功能为其钱门账户充值。
                <p style="margin:12px 0 0 0; padding:0;">请输入对方的钱门账户名，充值后钱立即到对方的钱门账户中！ <em style="font-style:normal; font-weight:bold; color:#005C9C;">请谨慎操作！</em></p>
				
					<table  cellpadding="0" cellspacing="0" width="100%" class="information_table" style="margin-top: 30px;">
					<tr>
							<td class="right_td">您的钱门账号：</td>
							<td style="color:#005C9C">
								<c:out value="${agent.loginName}" /><br />
							</td>
							<td></td>
						</tr>
						<tr>
							<td class="right_td"><span class="font_style6">*</span> 对方钱门账户：</td>
							<td>
								<input type="text" name="tagentEmail" class="text_style"/>
							</td>
							<td>
								<div id="mgemail" style="color: red;"><font color="red"><c:out value="${msgEmail}"/></font></div>
							</td>
						</tr>
						 <tr>
                      <td class="td_span">&nbsp;</td>
                      <td class="td_span">钱门账户名为注册的E-mail</td>
                    </tr>
						<tr><td colspan="2">
						<span class="simplyBtn_1" style="margin-left:167px;">
						<input type="button" class="buttom_middle" value="下一步" onclick="emaild();"/>
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


