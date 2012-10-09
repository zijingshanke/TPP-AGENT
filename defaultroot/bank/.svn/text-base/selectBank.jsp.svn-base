<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"
	language="java"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!-- JSP页面:bank/selectBank.jsp -->
<head>
	<title>钱门支付！--网上支付！安全放心！</title>
	<script language="javascript" src="../_js/selectBank.js"></script>
</head>
<tr>
	<td align="right">
		选择银行：
	</td>
	<td>
		<input class="radio" type="radio" name="bankVersion" value="personal"
			checked="checked" onclick="selectVersion(0);" />
		个人版
		<input class="radio" type="radio" name="bankVersion"
			value="enterprise" onclick="selectVersion(1);" />
		企业版
	</td>
	<td>
		<div id="bankNotice" style="color: red"></div>
	</td>
</tr>
<tr>
	<td></td>
	<td colspan="2">
		<div id="versionOfPersona1">
			<table>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0" value="ICBC"
							id="perICBC" checked="checked" onclick="clickPersonal('ICBC');" />
						<img src="../_img/bank/bankLogo_1.gif"
							onclick="clickPersonal('ICBC');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="CCB"
							onclick="clickPersonal('CCB');" />
						<img src="../_img/bank/bankLogo_3.gif"
							onclick="clickPersonal('CCB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="CMBC"
							onclick="clickPersonal('CMBC');" />
						<img src="../_img/bank/bankLogo_9.gif"
							onclick="clickPersonal('CMBC');" />
					</td>
				</tr>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0" value="ABC"
							onclick="clickPersonal('ABC');" />
						<img src="../_img/bank/bankLogo_4.gif"
							onclick="clickPersonal('ABC');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="BCM"
							onclick="clickPersonal('BCM');" />
						<img src="../_img/bank/bankLogo_10.gif"
							onclick="clickPersonal('BCM');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="CITIC"
							onclick="clickPersonal('CITIC');" />
						<img src="../_img/bank/bankLogo_11.gif"
							onclick="clickPersonal('CITIC');" />
					</td>
				</tr>
				<!---->
				<tr>
					<td>
						<font size="4" color="red">以下银行接口由中国银联提供</font>
					</td>
				</tr>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="BOC_ChinaPay" onclick="clickPersonal('BOC');" />
						<img src="../_img/bank/bankLogo_13.gif"
							onclick="clickPersonal('BOC');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="PSBC_ChinaPay" onclick="clickPersonal('PSBC');" />
						<img src="../_img/bank/bankLogo_17.gif"
							onclick="clickPersonal('PSBC');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="CIB_ChinaPay" onclick="clickPersonal('CIB');" />
						<img src="../_img/bank/bankLogo_6.gif"
							onclick="clickPersonal('CIB');" />
					</td>
				</tr>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="HXB_ChinaPay" onclick="clickPersonal('HXB');" />
						<img src="../_img/bank/bankLogo_18.gif"
							onclick="clickPersonal('HXB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="JSNX_ChinaPay" onclick="clickPersonal('JSNX');" />
						<img src="../_img/bank/bankLogo_22.gif"
							onclick="clickPersonl('JSNX');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="NBCB_ChinaPay" onclick="clickPersonal('NBCB');" />
						<img src="../_img/bank/bankLogo_26.gif"
							onclick="clickPersonal('NBCB');" />
					</td>
					<!-- 
					<td>
						<img src="../_img/bank/bank-all.jpg" onclick="displayAllBank();">
						<a onclick="displayAllBank();" title="更多银行请点击">选择更多支持银行</a>
					</td>
					 -->
				</tr>
				<!--<tr id="otherRow1" style="display: none">-->
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="ZHNX_ChinaPay" onclick="clickPersonal('ZHNX');" />
						<img src="../_img/bank/bankLogo_21.gif"
							onclick="clickPersonal('ZHNX');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="YDXH_ChinaPay" onclick="clickPersonal('YDXH');" />
						<img src="../_img/bank/bankLogo_23.gif"
							onclick="clickPersonal('YDXH');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="CBHB_ChinaPay" onclick="clickPersonal('CBHB');" />
						<img src="../_img/bank/bankLogo_19.gif"
							onclick="clickPersonl('CBHB');" />
					</td>
				</tr>
				<!-- <tr id="otherRow2" style="display: none"> -->
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="JCCB_ChinaPay" onclick="clickPersonal('JCCB');" />
						<img src="../_img/bank/bankLogo_24.gif"
							onclick="clickPersonal('JCCB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="WZCB_ChinaPay" onclick="clickPersonal('WZCB');" />
						<img src="../_img/bank/bankLogo_25.gif"
							onclick="clickPersonl('WZCB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="HKB_ChinaPay" onclick="clickPersonal('HKB');" />
						<img src="../_img/bank/bankLogo_20.gif"
							onclick="clickPersonal('HKB');" />
					</td>
				</tr>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank0"
							value="FDB_ChinaPay" onclick="clickPersonal('FDB');" />
						<img src="../_img/bank/bankLogo_27.gif"
							onclick="clickPersonal('FDB');" />
					</td>
				</tr>
				<tr>
					<td>
						<img src="../_img/bank/bank-all.jpg" onclick="displayNoFiring();">
						<a onclick="displayNoFiring();" title="更多银行请点击"><font size="4"
							color="red">查看近日即将上线银行接口</font> </a>
					</td>
				</tr>
				<tr id="otherRow11" style="display: none">
					<td>
						<input class="radio" type="radio" name="bank0" value="CMB"
							onclick="clickPersonal('CMB');" />
						<img src="../_img/bank/bankLogo_2.gif"
							onclick="clickPersonal('CMB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="GDB"
							onclick="clickPersonal('GDB');" />
						<img src="../_img/bank/bankLogo_7.gif"
							onclick="clickPersonal('GDB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="SDB"
							onclick="clickPersonal('SDB');" />
						<img src="../_img/bank/bankLogo_8.gif"
							onclick="clickPersonal('SDB');" />
					</td>
				</tr>
				<tr id="otherRow12" style="display: none">
					<td>
						<input class="radio" type="radio" name="bank0" value="SPDB"
							onclick="clickPersonal('SPDB');" />
						<img src="../_img/bank/bankLogo_5.gif"
							onclick="clickPersonal('SPDB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="BOB"
							onclick="clickPersonal('BOB');" />
						<img src="../_img/bank/bankLogo_15.gif"
							onclick="clickPersonal('BOB');" />
					</td>
					<td>
						<input class="radio" type="radio" name="bank0" value="CEB"
							onclick="clickPersonal('CEB');" />
						<img src="../_img/bank/bankLogo_12.gif"
							onclick="clickPersonal('CEB');" />
					</td>
				</tr>
			</table>
		</div>
		<div id="versionOfEnterprise" style="display: none;">
			<table>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank1" value="ICBC"
							checked="checked" onclick="clickEnterprise('ICBC');" />
						<img src="../_img/bank/bankLogo_1.gif"
							onclick="clickEnterprise('ICBC');" />
					</td>
					<td colspan="2">
						<input class="radio" type="radio" name="bank1" value="CCB"
							onclick="clickEnterprise('CCB');" />
						<img src="../_img/bank/bankLogo_3.gif"
							onclick="clickEnterprise('CCB');" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<font size="3" color="red">以下银行接口将于近日上线</font>
					</td>
				<tr>
					<td>
						<input class="radio" type="radio" name="bank1" value="ABC"
							onclick="clickEnterprise('ABC');" />
						<img src="../_img/bank/bankLogo_4.gif"
							onclick="clickEnterprise('ABC');" />
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3" class="blue-grayBox" style="padding-left: 30px;">
						小贴士：请确认您已是
						<em style="font-style: normal; color: #C00;" id="showBank">企业网银</em>的正常证书用户，否则无法完成充值。
					</td>
				</tr>
			</table>
		</div>
	</td>
</tr>

<c:import url="../bank/selectBankInfo.jsp"></c:import>