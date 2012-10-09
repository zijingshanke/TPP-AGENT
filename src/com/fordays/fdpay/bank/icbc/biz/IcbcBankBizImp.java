package com.fordays.fdpay.bank.icbc.biz;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import cn.com.infosec.icbc.ReturnValue;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.bank.BankUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.bank.ResultFromBank;
import com.fordays.fdpay.bank.icbc.IcbcB2BCmdToBank;
import com.fordays.fdpay.bank.icbc.IcbcB2CResultFromBank;
import com.fordays.fdpay.bank.icbc.IcbcB2CcmdToBank;
import com.fordays.fdpay.bank.icbc.IcbcOrder;
import com.fordays.fdpay.bank.icbc.IcbcOrderResult;
import com.fordays.fdpay.bank.icbc.IcbcTelB2CResultFromBank;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.transaction.Charge;
import com.neza.base.Constant;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.tool.DateUtil;

/**
 * 工商银行业务处理实现类
 */
public class IcbcBankBizImp implements IcbcBankBiz {
	private AgentDAO agentDAO;
	private LogUtil myLog;

	/**
	 * @网上银行
	 * @接口名：ICBC_PERBANK_B2C
	 * @版本号: 1.0.0.3
	 * @方法描述:创建B2C订单
	 */
	public IcbcB2CcmdToBank createIcbcB2CCmd(Charge charge) throws AppException {
		IcbcB2CcmdToBank bank = new IcbcB2CcmdToBank();
		bank.setAmount(BankUtil.getCentAmount(charge.getAmount()));
		String orderNo = charge.getOrderNo();
		bank.setOrderid(orderNo);
		String orderDate = DateUtil.getDateString("yyyyMMddHHmmss");
		bank.setOrderDate(orderDate);
		bank.setGoodsID("0001");
		bank.setGoodsName("QMPAY B2C CHARGE");
		bank.setGoodsNum("1");
		bank.setCarriageAmt("0");

		final String requestHost = charge.getRequestHost();
		bank.setMerHint(requestHost);// 显示在工行订单页面
		bank.setMerVAR(requestHost);// 订单处理结果消息原文返回

		bank.getICBC_B2C_CMD();
		return bank;
	}

	/**
	 * @网上银行
	 * @接口名：B2B
	 * @版本号：001.001.001.001
	 * @方法描述:创建B2B订单
	 */
	public IcbcB2BCmdToBank createIcbcB2BCmd(Charge charge) throws AppException {
		IcbcB2BCmdToBank bank = new IcbcB2BCmdToBank();
		bank.setContractAmt(BankUtil.getCentAmount(charge.getAmount()));
		bank.setContractNo(charge.getOrderNo());
		String orderDate = DateUtil.getDateString("yyyyMMddHHmmss");
		bank.setTranTime(orderDate);
		bank.setGoodsCode("0002");
		bank.setGoodsName("QMPAY B2B CHARGE");
		bank.setAmount("1");// 订单数量
		bank.setTransFee("0");// 已含运费
		bank.setShopRemark("");// 商城提示，MAX(120)

		final String requestHost = charge.getRequestHost();
		bank.setShopRem(requestHost);// 商城备注字段，MAX(100)

		bank.getICBC_B2B_CMD();// 签名、赋值
		return bank;
	}

	/**
	 * @网上银行
	 * @接口名称：ICBC_PERBANK_B2C
	 * @接口版本：1.0.0.3
	 * @方法描述：解析B2C支付结果
	 * @备注：
	 */
	public IcbcB2CResultFromBank getB2CResult(HttpServletRequest request)
			throws AppException {
		IcbcB2CResultFromBank result = null;

		String merVAR = request.getParameter("merVAR");
		String notifyDataEncode = request.getParameter("notifyData");
		String signMsg = request.getParameter("signMsg");

		myLog = new LogUtil(false, false, IcbcBankBizImp.class);
		myLog.info("接收到工商银行 B2C 反馈-------------");
		myLog.info("1-merVAR=" + merVAR);
		myLog.info("2-notifyData=" + notifyDataEncode);
		myLog.info("3-signMsg=" + signMsg);

		try {
			byte[] byteSrc = ReturnValue.base64dec(notifyDataEncode.getBytes());// 交易信息解码
			int byteSrcLength = byteSrc.length;

			XmlUtil xml = new XmlUtil();
			Document configDoc = BankUtil.loadXml(IcbcB2CcmdToBank
					.getConfigXmlUrl());
			// 验签公钥
			String verifySignCert = Constant.WEB_INFO_PATH
					+ xml.getTextByNode(configDoc,
							"//BANK/ICBC/B2C/verifySignCert");
			byte[] EncCert = BankUtil.getByteFromFile(verifySignCert);

			byte[] DecSign = ReturnValue.base64dec(signMsg.getBytes());// 签名解码
			if (DecSign != null) {
				if (EncCert != null) {
					int a = 1;
					a = ReturnValue.verifySign(byteSrc, byteSrcLength, EncCert,
							DecSign); // 验证签名

					myLog.info("验证签名标识[0:成功] " + a);

					if (a == 0) {
						String notifyData = new String(ReturnValue
								.base64dec(notifyDataEncode.getBytes("utf-8")))
								.toString();// 交易数据base64解码

						StringBuffer notifyDataBuf = new StringBuffer(
								notifyData);
						Document document = xml.readResult(notifyDataBuf);// 将银行返回的信息还原为XML
						String interfaceName = xml.getTextByNode(document,
								"//B2CRes/interfaceName");
						String interfaceVersion = xml.getTextByNode(document,
								"//B2CRes/interfaceVersion");
						String orderDate = xml.getTextByNode(document,
								"//B2CRes/orderInfo/orderDate");
						String orderid = xml.getTextByNode(document,
								"//B2CRes/orderInfo/orderid");
						String amount = xml.getTextByNode(document,
								"//B2CRes/orderInfo/amount");
						String curType = xml.getTextByNode(document,
								"//B2CRes/orderInfo/curType");
						String merID = xml.getTextByNode(document,
								"//B2CRes/orderInfo/merID");
						String merAcct = xml.getTextByNode(document,
								"//B2CRes/orderInfo/merAcct");
						String verifyJoinFlag = xml.getTextByNode(document,
								"//B2CRes/custom/verifyJoinFlag");
						String JoinFlag = xml.getTextByNode(document,
								"//B2CRes/custom/JoinFlag");
						String UserNum = xml.getTextByNode(document,
								"//B2CRes/custom/UserNum");
						String TranSerialNo = xml.getTextByNode(document,
								"//B2CRes/bank/TranSerialNo");
						String notifyDate = xml.getTextByNode(document,
								"//B2CRes/bank/notifyDate");
						String tranStat = xml.getTextByNode(document,
								"//B2CRes/bank/tranStat");
						String comment = xml.getTextByNode(document,
								"//B2CRes/bank/comment");
						// ----------------------------------
						result = new IcbcB2CResultFromBank();
						result.setMerVAR(merVAR);
						result.setNotifyData(notifyData);
						result.setSignMsg(signMsg);
						result.setInterfaceName(interfaceName);
						result.setInterfaceVersion(interfaceVersion);
						result.setOrderDate(orderDate);
						result.setOrderid(orderid);
						result.setAmount(amount);
						result.setCurType(curType);
						result.setMerID(merID);
						result.setMerAcct(merAcct);
						result.setVerifyJoinFlag(verifyJoinFlag);
						result.setJoinFlag(JoinFlag);
						result.setUserNum(UserNum);
						result.setTranSerialNo(TranSerialNo);
						result.setNotifyDate(notifyDate);
						result.setTranStat(tranStat);
						result.setComment(comment);
						// -----------------------
						result.setROrderNo(orderid);
						result.setRAmount(BankUtil.getDollarAmount(amount));// 分转元

						if ("1".equals(tranStat)) {
							result.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
						} else if ("2".equals(tranStat)) {
							result.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
						} else if ("3".equals(tranStat)) {
							result.setRChargeStatus(Charge.CHARGE_STATUS_7);// 可疑
						}

						result.setRRequestHost(merVAR);
						result.setRUrl(request.getRequestURL().toString() + "?"
								+ "merVAR=" + merVAR + "&notifyData="
								+ notifyData + "&signMsg=" + signMsg);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @网上银行
	 * @接口名称：B2B
	 * @接口版本：001.001.001.001
	 * @方法描述：解析B2B支付结果
	 */
	public ResultFromBank getB2BResult(HttpServletRequest request)
			throws AppException {
//		IcbcB2BResultFromBank b2bresult = null;
		String APIName = request.getParameter("APIName");
		String APIVersion = request.getParameter("APIVersion");
		String Shop_code = request.getParameter("Shop_code");
		String MerchantURL = request.getParameter("MerchantURL");
		String Serial_no = request.getParameter("Serial_no");// 指令序号
		String PayStatusZHCN = request.getParameter("PayStatusZHCN");// 订单处理状态
		String TranErrorCode = request.getParameter("TranErrorCode");// 错误代码
		String TranErrorMsg = request.getParameter("TranErrorMsg");
		String ContractNo = request.getParameter("ContractNo");// 订单号
		String ContractAmt = request.getParameter("ContractAmt");
		String Account_cur = request.getParameter("Account_cur");
		String JoinFlag = request.getParameter("JoinFlag");
		String ShopJoinFlag = request.getParameter("ShopJoinFlag");
		String CustJoinFlag = request.getParameter("CustJoinFlag");
		String CustJoinNumber = request.getParameter("CustJoinNumber");
		String NotifySign = request.getParameter("NotifySign");// 订单签名数据(Base64编码)
		String SendType = request.getParameter("SendType");
		String TranTime = request.getParameter("TranTime");// 接收交易时间
		String NotifyTime = request.getParameter("NotifyTime");// 返回通知日期时间
		String Shop_acc_num = request.getParameter("Shop_acc_num");
		String PayeeAcct = request.getParameter("PayeeAcct");
		String PayeeName = request.getParameter("PayeeName");
		String ShopRem = request.getParameter("ShopRem");

		try {
			TranErrorMsg = new String(TranErrorMsg.getBytes("ISO-8859-1"),
					"GBK");
			PayeeName = new String(PayeeName.getBytes("ISO-8859-1"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}

		myLog = new LogUtil(false, false, IcbcBankBizImp.class);
		myLog.info("接收到工商银行 B2B 反馈---");
//		myLog.info("1-APIName=" + APIName);
//		myLog.info("2-APIVersion=" + APIVersion);
//		myLog.info("3-Shop_code=" + Shop_code);
//		myLog.info("4-MerchantURL=" + MerchantURL);
//		myLog.info("5-Serial_no=" + Serial_no);
//		myLog.info("6-PayStatusZHCN=" + PayStatusZHCN);
//		myLog.info("7-TranErrorCode=" + TranErrorCode);
//		myLog.info("8-TranErrorMsg=" + TranErrorMsg);
//		myLog.info("9-ContractNo=" + ContractNo);
//		myLog.info("10-ContractAmt=" + ContractAmt);
//		myLog.info("11-Account_cur=" + Account_cur);
//		myLog.info("12-JoinFlag=" + JoinFlag);
//		myLog.info("13-ShopJoinFlag=" + ShopJoinFlag);
//		myLog.info("14-CustJoinFlag=" + CustJoinFlag);
//		myLog.info("15-CustJoinNumber=" + CustJoinNumber);
//		myLog.info("16-NotifySign=" + NotifySign);
//		myLog.info("17-SendType=" + SendType);
//		myLog.info("18-TranTime=" + TranTime);
//		myLog.info("19-NotifyTime=" + NotifyTime);
//		myLog.info("20-Shop_acc_num=" + Shop_acc_num);
//		myLog.info("21-PayeeAcct=" + PayeeAcct);
//		myLog.info("22-PayeeName=" + PayeeName);
//		myLog.info("23-ShopRem=" + ShopRem);

		StringBuffer plain = new StringBuffer();
		plain.append("APIName=").append(APIName).append("&APIVersion=").append(
				APIVersion).append("&Shop_code=").append(Shop_code).append(
				"&MerchantURL=").append(MerchantURL).append("&Serial_no=")
				.append(Serial_no).append("&PayStatusZHCN=").append(
						PayStatusZHCN).append("&TranErrorCode=").append(
						TranErrorCode).append("&TranErrorMsg=").append(
						TranErrorMsg).append("&ContractNo=").append(ContractNo)
				.append("&ContractAmt=").append(ContractAmt).append(
						"&Account_cur=").append(Account_cur).append(
						"&JoinFlag=").append(JoinFlag).append("&ShopJoinFlag=")
				.append(ShopJoinFlag).append("&CustJoinFlag=").append(
						CustJoinFlag).append("&CustJoinNumber=").append(
						CustJoinNumber).append("&SendType=").append(SendType)
				.append("&TranTime=").append(TranTime).append("&NotifyTime=")
				.append(NotifyTime).append("&Shop_acc_num=").append(
						Shop_acc_num).append("&PayeeAcct=").append(PayeeAcct)
				.append("&PayeeName=").append(PayeeName);
		// .append("&ShopRem=").append(ShopRem);

		// APIName=接口名称&APIVersion=接口版本号&Shop_code=商户代码&MerchantURL=支付结果信息通知程序地址&Serial_no=指令序号&PayStatusZHCN=订单处理状态
		// &TranErrorCode=错误代码&TranErrorMsg=错误描述&ContractNo=订单号&ContractAmt=订单金额&Account_cur=支付币种&JoinFlag=检验联名标志&ShopJoinFlag=商城联名标志
		// &CustJoinFlag=客户联名标志&CustJoinNumber=联名会员号&SendType=结果发送类型&TranTime=接收交易日期时间&NotifyTime=返回通知日期时间
		// &Shop_acc_num=商城账号&PayeeAcct=收款单位账号&PayeeName=收款单位名称

		String strSrc = plain.toString();
		myLog.info("验证签名,原文:" + strSrc);
		
		return initiativeQueryOrder(ContractNo, "B2B");
		
		// ----------------------验签公钥问题，使用查询接口确认订单状态
		// byte[] byteSrc = strSrc.getBytes();
		// int byteSrcLength = byteSrc.length;
		// byte[] EncSign = NotifySign.getBytes();
		// byte[] DecSign = ReturnValue.base64dec(EncSign);// 签名解码
		// XmlUtil xml = new XmlUtil();
		// Document configDoc =
		// BankUtil.loadXml(IcbcB2BCmdToBank.getConfigXmlUrl());
		// // String verifySignCertUrl = Constant.WEB_INFO_PATH+
		// // xml.getTextByNode(configDoc,"//BANK/ICBC/B2B/verifySignCert");
		//
		// String verifySignCertUrl = "E:\\icbcb2b\\ca.cer";
		// // ebb2cpublic.crt
		// // ebb2cpublic.key
		// // cabase64.cer
		// // ca.cer
		// // admin-b2c-test.crt
		// myLog.info("verifySignCertUrl:" + verifySignCertUrl);
		// byte[] EncCert = BankUtil.getByteFromFile(verifySignCertUrl);
		// if (DecSign != null) {
		// myLog.info("签名信息BASE64解码成功");
		// byte[] DecCert = ReturnValue.base64dec(EncCert);// 证书解码
		// if (DecCert != null) {
		// myLog.info("证书公钥BASE64解码成功");
		// int a = 1;
		// try {
		// a = ReturnValue.verifySign(byteSrc, byteSrcLength, DecCert,
		// DecSign); // 验证签名
		// System.out.println("--------a=" + a);
		// } catch (Exception e) {
		// e.printStackTrace();
		// myLog.info("验证工行签名异常");
		// myLog.error(e.getMessage());
		// }
		// if (a == 0) {
		// myLog.info("ICBC_B2B 验证签名成功");
		// b2bresult = new IcbcB2BResultFromBank();
		// b2bresult.setAPIName(APIName);
		// b2bresult.setAPIVersion(APIVersion);
		// b2bresult.setShop_code(Shop_code);
		// b2bresult.setMerchantURL(MerchantURL);
		// b2bresult.setSerial_no(Serial_no);
		// b2bresult.setPayStatusZHCN(PayStatusZHCN);
		// b2bresult.setTranErrorCode(TranErrorCode);
		// b2bresult.setTranErrorMsg(TranErrorMsg);
		// b2bresult.setContractNo(ContractNo);
		// b2bresult.setContractAmt(ContractAmt);
		// b2bresult.setAccount_cur(Account_cur);
		// b2bresult.setJoinFlag(JoinFlag);
		// b2bresult.setShopJoinFlag(ShopJoinFlag);
		// b2bresult.setCustJoinFlag(CustJoinFlag);
		// b2bresult.setCustJoinNumber(CustJoinNumber);
		// b2bresult.setNotifySign(NotifySign);// ---------
		// b2bresult.setSendType(SendType);
		// b2bresult.setTranTime(TranTime);
		// b2bresult.setNotifyTime(NotifyTime);
		// b2bresult.setShop_acc_num(Shop_acc_num);
		// b2bresult.setPayeeAcct(PayeeAcct);
		// b2bresult.setPayeeName(PayeeName);
		// b2bresult.setShopRem(ShopRem);
		//
		// // ------------------
		//
		// b2bresult.setROrderNo(ContractNo);
		// b2bresult.setRAmount(BankUtil.getDollarAmount(ContractAmt));// 分转元
		//
		// // PayStatusZHCN; // 订单处理状态 0-成功 1-失败 2-可疑交易 3-等待授权
		// if ("0".equals(PayStatusZHCN)) {
		// b2bresult.setRChargeStatus(Charge.CHARGE_STATUS_1);// 成功
		// } else if ("1".equals(PayStatusZHCN)) {
		// b2bresult.setRChargeStatus(Charge.CHARGE_STATUS_2);// 失败
		// } else if ("2".equals(PayStatusZHCN)) {
		// b2bresult.setRChargeStatus(Charge.CHARGE_STATUS_7);// 可疑
		// } else if ("3".equals(PayStatusZHCN)) {
		// b2bresult.setRChargeStatus(Charge.CHARGE_STATUS_8);// 等待授权
		// }
		//
		// b2bresult.setRRequestHost(ShopRem);
		// b2bresult.setRUrl(request.getRequestURL().toString() + "?"
		// + strSrc + "&ShopRem=" + ShopRem + "&NotifySign="
		// + NotifySign);
		// } else {
		// myLog.info("验签失败");
		// }
		// } else {
		// myLog.info("证书BASE64解码失败");
		// }
		// } else {
		// myLog.info("签名信息BASE64解码失败");
		// }
		// return b2bresult;
	}

	// =============================订单查询与监听============================
	/**
	 * @网上银行
	 * @接口名称:EAPI
	 * @方法描述:创建单笔订单查询对象
	 */
	public IcbcOrder createIcbcOrderQuery(Charge charge) throws AppException {
		String version = charge.getVersion();// 版本
		int versionLen = version.length();
		version = version.substring(versionLen - 3, versionLen);

		myLog = new LogUtil(false, false, IcbcBankBizImp.class);
		myLog.info("查询工商银行订单" + version);

		IcbcOrder order = new IcbcOrder(version);
		order.setOrderNum(charge.getOrderNo());
		String tranDate = DateUtil.getDateString(charge.getChargeDate(),
				"yyyyMMdd");
		order.setTranDate(tranDate);
		return order;
	}

	/**
	 * @implement interface:BankBiz
	 * @description:检查订单状态,BankOrderListener调用的方法
	 * @return {@link ResultFromBank}
	 */
	public ResultFromBank initiativeQueryOrder(String orderNo, String version)
			throws AppException {
		myLog = new LogUtil(false, false, IcbcBankBizImp.class);
		myLog.info("initiativeQueryOrder(" + orderNo + "," + version + ")");

		IcbcOrder order = new IcbcOrder(version);
		order.setOrderNum(orderNo);
		final String chargeDate = DateUtil.getDateString("yyyyMMdd");
		order.setTranDate(chargeDate);

		IcbcOrderResult orderResult = order.getIcbcOrderResult();

		return orderResult;
	}

	/**
	 * @implement interface:BankBiz
	 * @description:补单成功，发送通知邮件
	 * @param Charge
	 *            charge
	 * @return void
	 * @remark:订单监听器调用
	 */
	public void sendNoticeMail(Charge charge) throws AppException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("$orderNo$", charge.getOrderNo());
		params.put("$amount$", charge.getAmount().toString());
		params.put("$tranDate$", DateUtil.getDateString("yyyy-MM-dd hh:mm:ss"));

		Agent agent = agentDAO.getAgentById(charge.getAgent().getId());// 勿改
		params.put("$agentName$", agent.getLoginName());

		MailUtil.sslSend("钱门提示","0041", agent.getEmail(), params,Certification.getProtocol());
	}

	// =====================================未使用的方法==============================
	/**
	 * @电话银行
	 * @接口名称:ICBC_TEL_B2C_SUBMIT
	 * @接口版本：1.0.0.0 *
	 * @方法说明：解析Xml通知结果
	 */
	public IcbcTelB2CResultFromBank getTelB2CResult(HttpServletRequest request)
			throws AppException {
		String queryString = request.getQueryString();
		StringBuffer resultData = new StringBuffer(queryString);

		XmlUtil xmlctl = new XmlUtil();
		Document document = xmlctl.readResult(resultData);// 将银行返回的信息还原为xml

		String merID = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/merID");
		String orderDate = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/orderDate");
		String orderID = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/orderID");
		String merAcct = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/merAcct");
		String cutomerTel = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/cutomerTel");
		String amount = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/amount");
		String curType = xmlctl.getTextByNode(document,
				"//ICBC/B2C/SubmitOrder/curType");
		String code = xmlctl.getTextByNode(document, "//ICBC/B2C/Ret/code");
		String msg = xmlctl.getTextByNode(document, "//ICBC/B2C/Ret/msg");
		String bankTime = xmlctl.getTextByNode(document,
				"//ICBC/B2C/Ret/bankTime");
		String bankSignMsg = xmlctl.getTextByNode(document,
				"//ICBC/B2C/Ret/bankSignMsg");

		IcbcTelB2CResultFromBank bank = new IcbcTelB2CResultFromBank();
		bank.setMerID(merID);
		bank.setOrderDate(orderDate);
		bank.setOrderID(orderID);
		bank.setMerAcct(merAcct);
		bank.setCutomerTel(cutomerTel);
		bank.setAmount(amount);
		bank.setCurType(curType);
		bank.setCode(code);
		bank.setMsg(msg);
		bank.setBankTime(bankTime);
		bank.setBankSignMsg(bankSignMsg);
		return bank;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}
}