<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!--JSP页面: transaction/drawRules.jsp -->	

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
              <p class="leftbox_count_p"><a href="../transaction/draw.do?thisAction=withdrawing&showTabMenuIdList=1,6&showSubMenuIdList=0,7,3,6">申请提现</a></p>
              <p><a href="../transaction/drawlist.do?thisAction=listDraw&showTabMenuIdList=1,6&showSubMenuIdList=0,7,2,6">提现记录</a></p>
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
                  提现规则
                  </div>
                </div>
              </div>
            <table align="center" width="95%">
            <tr><td></td>
            <td><br/>
            <p align="center"> <b style="font-size: 20px;">关于钱门部分运营规则的解释</b></p>
			<font color="#DA6015" size="2px;">1、钱门用户申请提现的处理时间？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门公司将在用户发起提现申请当日的第二个工作日前处理提现，正常情况下款项将在开始处理后的1-2个工作日到达用户的银行账户。</font><br/>
			<br/><font color="#DA6015" size="2px;">2、提取资金是否需要开通网上银行？ </font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">提现的银行账户可以不开通网上银行，但开通网上银行的银行卡也是可以支持提现操作的。</font><br/>
			<br/><font color="#DA6015" size="2px;">3、钱门提示提交银行处理？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门提示“提交银行处理”是指钱门用户的提现申请钱门已经提交银行核实办理。如果钱门用户填写的银行卡信息正确有效，银行会反馈成功完成；钱门将在钱门用户申请提现后的第二个工作日处理提现，正常情况下款项将在钱门开始处理后的1-2个工作日到达钱门用户的银行账户。</font><br/> 
			<br/><font color="#DA6015" size="2px;">4、提现是否收费？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">提现暂不收费，如有修改另行通知。</font><br/>
			<br/><font color="#DA6015" size="2px;">5、提现次数限制，限额？ </font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门用户每日最多可以提现3次，超过3次请改日提现。</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">个人类型账户，提现最高限额：5万/笔，15万/天。</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">公司类型账户：非证书用户：20万/笔，60万/天。</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">证书用户：100万/笔，300万/天。</font><br/>
			<br/><font color="#DA6015" size="2px;">6、退款成功后，多长时间款可以进的账户？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">卖家确认同意退款后，钱即时退返到钱门用户的钱门账户，如果要提取到银行卡，可以操作申请提现，将钱移转到钱门用户绑定的银行账户中。</font><br/>
			<br/><font color="#DA6015" size="2px;">7、关于充值退回</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门用户充值到钱门账户后，未使用完的金额退回必须是退回到钱门用户充值的充值银行原账户中。（给其他账户充值的情况，充值退回的钱原路退回到充值人的银行卡中。）</font><br/>
			<br/><font color="#DA6015" size="2px;">8、关于充值的金额的提现</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门不允许对充值资金进行提现，可放置在钱门用户账户作消费使用。如需要取出充值的金额，请选择充值退回。申请成功后，会在3－5个工作日退回充值银行原账户中。</font><br/>
			<br/><font color="#DA6015" size="2px;">9、同一个身份证件在多个账户申请认证，是否可以同时申请通过？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">可以。 但必须绑定申请实名认证的身份证本人开户名的银行帐号。</font><br/>
			<br/><font color="#DA6015" size="2px;">10、未满18周岁，是否可以申请钱门实名认证？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">未满18周岁不可以成为钱门实名认证帐号。</font><br/>
			<br/><font color="#DA6015" size="2px;">11、通过钱门实名认证后，可以修改真实姓名和身份证号码吗？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">通过钱门实名认证后不允许修改真实姓名和身份证号码。如果钱门用户在公安局已更改了真实姓名或身份证号码，请您联系钱门客服进行处理。</font><br/>
			<br/><font color="#DA6015" size="2px;">12、银行开户名必需是本人的姓名？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门用户的银行卡开户名必须与申请认证的真实姓名相一致，钱门用户的银行卡认证才会审核通过，请使用认证人本人的银行卡进行银行认证审核。</font><br/>
			<br/><font color="#DA6015" size="2px;">13、在线充值时消费者银行卡上是否需要扣除手续费？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">不需要</font><br/>
			<br/><font color="#DA6015" size="2px;">14、充值是否有金额限制？</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">钱门账户充值没有金额限制，但要看钱门用户的网上银行卡有没有金额限制。</font><br/>
			<br/><font color="#DA6015" size="2px;">15、钱门用户为朋友的钱门账户充值，被充值的账户是否需要通过实名认证？是否有限额。</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">若有一方未通过实名认证，每次充值金额上限为500元，每日最多可操作三次。收款方与付款方均已通过实名认证则没有无限制。</font><br/>
			<br/><font color="#DA6015" size="2px;">16、关于钱门帐户企业版申请实名认证的流程？（注册时下载的认证表）</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">A、必须提供法人身份证和营业执照的彩色原件电子版（扫描件）；</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">B、公司所在详细地址、固定电话、手机号码；</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">C、认证所用提现银行的开户行网点名称及银行账户；</font><font color="red">（开户银行的账号必须是对公账户）</font><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<font color="#626262">D、资料齐全后由钱门相关业务部门审核认证后书面通知，商家认证的审核时间是3天。</font><br/><br/>
			</td></tr>
			<tr><td></td>
			<td>
            <a href="../transaction/draw.do?thisAction=withdrawing&showTabMenuIdList=1,6&showSubMenuIdList=0,7,3,6">返回提现</a>
            </td></tr>
            <tr><td>&nbsp;</td></tr>
           </table>
            </div>
          </div>
        </div>
    </div>
</div>
<c:import url="/_jsp/footer.jsp" />
</div>
	</body>
</html>
