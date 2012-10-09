package com.fordays.fdpay.transaction.action;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.biz.AgentBiz;
import com.fordays.fdpay.bank.BankOrderListener;
import com.fordays.fdpay.bank.LogUtil; //import com.fordays.fdpay.bank.abc.AbcB2BcmdToBank;
import com.fordays.fdpay.bank.abc.AbcB2CcmdToBank;
import com.fordays.fdpay.bank.abc.biz.AbcBankBiz;
import com.fordays.fdpay.bank.bcm.BcmB2CcmdToBank;
import com.fordays.fdpay.bank.bcm.biz.BcmBankBiz;
import com.fordays.fdpay.bank.boc.BocB2CcmdToBank;
import com.fordays.fdpay.bank.boc.biz.BocBankBiz;
import com.fordays.fdpay.bank.ccb.CcbB2BcmdToBank;
import com.fordays.fdpay.bank.ccb.CcbB2CcmdToBank;
import com.fordays.fdpay.bank.ccb.biz.CcbBankBiz;
import com.fordays.fdpay.bank.citic.CiticB2CcmdToBank;
import com.fordays.fdpay.bank.citic.biz.CiticBankBiz; //import com.fordays.fdpay.bank.cmbc.CmbcB2BcmdToBank;
import com.fordays.fdpay.bank.cmb.CmbB2CcmdToBank;
import com.fordays.fdpay.bank.cmb.biz.CmbBankBiz;
import com.fordays.fdpay.bank.cmbc.CmbcB2CcmdToBank;
import com.fordays.fdpay.bank.cmbc.biz.CmbcBankBiz;
import com.fordays.fdpay.bank.icbc.IcbcB2BCmdToBank;
import com.fordays.fdpay.bank.icbc.IcbcB2CcmdToBank;
import com.fordays.fdpay.bank.icbc.biz.IcbcBankBiz;
import com.fordays.fdpay.cooperate.MainTask;
import com.fordays.fdpay.transaction.Charge;
import com.fordays.fdpay.transaction.biz.ChargeBiz;
import com.fordays.fdpay.transaction.biz.ChargeLogBiz;
import com.hitrust.b2b.response.GenericResponse; //import com.hitrust.b2b.trustpay.client.b2b.FundTransferRequest;
import com.hitrust.trustpay.client.TrxResponse;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class ChargeAction extends BaseAction {
	private AgentBiz agentBiz;
	private ChargeBiz chargeBiz;
	private ChargeLogBiz chargeLogBiz;
	private AbcBankBiz abcBankBiz;
	private BcmBankBiz bcmBankBiz;
	private BocBankBiz bocBankBiz;
	private CcbBankBiz ccbBankBiz;
	private CiticBankBiz citicBankBiz;
	private CmbcBankBiz cmbcBankBiz;
	private CmbBankBiz cmbBankBiz;
	
	private IcbcBankBiz icbcBankBiz;

	// ------
	private String requestHead = "";
	private LogUtil myLog;

	// 给其他人充值
	public ActionForward rechargeOther(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = agentBiz.getAgentByRequest(request);

		String email = request.getParameter("tagentEmail");
		if (email == null) {
			request.setAttribute("agent", agent);
			return mapping.findForward("rechargeother");
		}
		Agent ag = agentBiz.checkAgentByEmail(email);
		if (email != null && ag == null) {
			request.setAttribute("agent", agent);
			request.setAttribute("msgEmail", "该账号不存在!");
			return mapping.findForward("rechargeother");
		} else if (email != null && ag != null) {
			request.setAttribute("agent", agent);
			Agent ssagent = agentBiz.getAgentByLoginName(email);
			request.setAttribute("sagent", ssagent);
			request.setAttribute("chargeType", Charge.CHARGE_TYPE_OTHER);
			return mapping.findForward("rechargeothergo");
		}
		request.setAttribute("agent", agent);
		return mapping.findForward("rechargeother");
	}

	// 充值
	public ActionForward rechargeable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Agent agent = agentBiz.getAgentByRequest(request);
		request.setAttribute("agent", agent);
		request.setAttribute("chargeType", Charge.CHARGE_TYPE_SELF);
		return mapping.findForward("rechargeable");
	}

	// 充值确认
	public ActionForward waitCharge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Charge charge = (Charge) form;
		Agent agent = agentBiz.getAgentByChargeForm(charge);
		charge.setAgent(agent);

		request.setAttribute("agent", agent);
		request.setAttribute("charge", charge);
		// 判断是否存在TransactionNo，是付账，还是只是充值
		System.out.println("充值确认，判断是否存在TransactionNo:"
				+ charge.getTransactionNo());
		if (!charge.getTransactionNo().equals("")) {
			System.out.println(">>>订单付款");
			return mapping.findForward("waittransaction");
		} else {
			System.out.println(">>>网银充值");
			return mapping.findForward("waitcharge");
		}
	}

	public ActionForward waitChargeI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Charge charge = (Charge) form;
		Agent agent = agentBiz.getAgentByChargeForm(charge);
		charge.setAgent(agent);

		request.setAttribute("agent", agent);
		request.setAttribute("charge", charge);
		// 判断是否存在TransactionNo，是付账，还是只是充值
		System.out.println(charge.getTransactionNo() + ">>>>>>>>>>>>>>>>>");
		if (!charge.getTransactionNo().equals(""))
			return mapping.findForward("waittransactionI");
		else
			return mapping.findForward("waitcharge");
	}

	// 线下充值
	public ActionForward applicationCharge(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("chargeType", Charge.CHARGE_TYPE_BACKGROUND);

		Agent agent = agentBiz.getAgentByRequest(request);
		request.setAttribute("agent", agent);
		return mapping.findForward("applicationCharge");
	}

	// 线下充值提交
	public ActionForward applicationChargeGo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String findForward = "";
		Charge charge = (Charge) form;

		Charge tempCharge = chargeBiz.createCharge(charge, request);
		chargeLogBiz.saveChargeLog(tempCharge, charge.getNote());

		findForward = "tipsCharge";
		return mapping.findForward(findForward);
	}

	// 他人充值确认
	public ActionForward waitChargeOther(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Charge charge = (Charge) form;

		Agent agentOther = agentBiz.getAgentById(Long.parseLong(charge
				.getAgentOtherId()));
		charge.setAgent(agentOther);
		Agent agentMy = agentBiz.getAgentById(Long.parseLong(charge
				.getAgentId()));

		request.setAttribute("agentMy", agentMy);
		request.setAttribute("agentOther", agentOther);
		request.setAttribute("charge", charge);
		return mapping.findForward("waitchargeother");
	}

	/**
	 * 网上银行接口
	 * 
	 * @author YanRui
	 */
	public ActionForward sendToBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		requestHead = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();

		Charge charge = (Charge) form;

		String version = charge.getVersion();
		
		System.out.println("version:" + version);
		
		if ("CCBB2C".equals(version)) {
			return sendToCCB_B2C(mapping, form, request, response); // 建设银行B2C订单(生产上线)

			// LogUtil myLog = new LogUtil(false, false, ChargeAction.class);
			// myLog.info("临时停止建行接口");
			// request.setAttribute("bankInfo", "建设银行盘点结算，B2C支付接口暂时停止使用，敬请谅解。");
			// return mapping.findForward("bankInfo");

		} else if ("CCBB2B".equals(version)) {
			return sendToCCB_B2B(mapping, form, request, response);// 建设银行B2B订单(生产上线)
		} else if ("ICBCB2C".equals(version)) {
			return sendToICBC_B2C(mapping, form, request, response);// 工商银行B2C订单(生产上线)
		} else if ("ICBCB2B".equals(version)) {
			return sendToICBC_B2B(mapping, form, request, response);// 工商银行B2B订单(生产上线)
		} else if ("BCMB2C".equals(version)) {
			return sendToBCM_B2C(mapping, form, request, response);// 交通银行B2C订单(生产上线)
		} else if ("CMBCB2C".equals(version)) {
			return sendToCMBC_B2C(mapping, form, request, response);// 民生银行B2C订单(生产上线)
		} else if ("CMBCB2B".equals(version)) {
			return sendToCMBC_B2B(mapping, form, request, response);// 民生银行B2B订单(开发中)
		} else if ("CMBCChinaPay".equals(version)) {
			return sendToCMBC_ChinaPay(mapping, form, request, response);// 民生银行银联接口订单(开发中)
		} else if ("ABCB2C".equals(version)) {
			return sendToABC_B2C(mapping, form, request, response);// 农业银行B2C订单(生产上线)
		} else if ("ABCB2B".equals(version)) {
			return sendToABC_B2B(mapping, form, request, response);// 农业银行B2B订单(测试中)
		} else if ("BOCB2C".equals(version)) {
			return sendToBOC_B2C(mapping, form, request, response);// 中国银行B2C订单(开发中)
		} else if ("CITICB2C".equals(version)) {
			return sendToCITIC_B2C(mapping, form, request, response);// 中信银行B2C订单(生产上线)
		} else if ("CMBB2C".equals(version)) {
			return sendToCMB_B2C(mapping, form, request, response);// 中信银行B2C订单(生产上线)
		} 
		else {
			String bankInfo = "银行接口建设中";
			myLog = new LogUtil(false, false, ChargeAction.class);
			myLog.error(bankInfo);
			request.setAttribute("bankInfo", bankInfo);
			return mapping.findForward("bankInfo");
		}
	}

	/**
	 * 向民生银行提交银联订单
	 */
	private ActionForward sendToCMBC_ChinaPay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);

		CmbcB2CcmdToBank bank = cmbcBankBiz
				.createCmbcChinaPayB2Ccmd(tempCharge);

		final String url = bank.getCMBC_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向民生银行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		request.setAttribute("CmbcB2CcmdToBank", bank);
		forward = "tempCMBCB2CChinaPay";

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	// 向中国银行提交B2C充值订单
	private ActionForward sendToBOC_B2C(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		BocB2CcmdToBank bank = bocBankBiz.createBOCB2CCmd(tempCharge);

		final String orderNo = bank.getOrderNo();
		final String url = bank.getBocB2Ccmd();
		try {
			response.sendRedirect(url);

			myLog.info("向中国银行发送B2C订单" + orderNo + ",url=" + url);
			chargeLogBiz.saveChargeLog(tempCharge, url);
		} catch (IOException e) {
			String bankInfo = "向中国银行发送B2C订单" + orderNo + "失败，原因是："
					+ e.getMessage();
			myLog.info(bankInfo);
			request.setAttribute("bankInfo", bankInfo);
			chargeBiz.removeSession(request, charge);
			forward = "bankInfo";
			return mapping.findForward(forward);
		}

		// ------开启线程,监听订单
		BankOrderListener bankListener = new BankOrderListener(bocBankBiz,
				chargeBiz, orderNo, "B2C", "", requestHead);
		MainTask.put(bankListener);
		// -------
		chargeBiz.removeSession(request, charge);
		return null;
	}

	// 向民生银行提交B2B充值订单
	private ActionForward sendToCMBC_B2B(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = "bankInfo";
		// Charge charge = (Charge) form;
		// Charge tempCharge = chargeBiz.createCharge(charge, request);
		// CmbcB2BcmdToBank bank = cmbcBankBiz.createCmbcB2Bcmd(tempCharge);
		// if (bank != null) {
		// GenericResponse tDirectPayResponse = bank.getDirectPayRequest()
		// .postRequest();
		// System.out.println("code=" + tDirectPayResponse.getCode());
		// System.out.println("Msg=" + tDirectPayResponse.getMessage());
		//
		// String url = "http://cmbc b2b";
		// chargeLogBiz.saveChargeLog(tempCharge, url);
		//
		// request.setAttribute("CmbcB2CcmdToBank", bank);
		// forward = "tempCMBCB2C";
		// }
		//
		// chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	/**
	 * 向农业银行提交B2C充值订单
	 */
	private ActionForward sendToABC_B2C(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		AbcB2CcmdToBank bank = abcBankBiz.createABCB2Ccmd(tempCharge);

		TrxResponse tTrxResponse = bank.getTTrxResponse();

		if (tTrxResponse.isSuccess()) {
			String url = tTrxResponse.getValue("PaymentURL");
			try {

				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			final String abcurl = url + bank.getUrl();
			final String orderNo = tempCharge.getOrderNo();

			myLog.info("向农业银行提交B2C订单" + orderNo + ",url:" + abcurl);
			chargeLogBiz.saveChargeLog(tempCharge, url);

			// ------开启线程,监听订单
			BankOrderListener bankListener = new BankOrderListener(abcBankBiz,
					chargeBiz, orderNo, "B2C", "", requestHead);
			MainTask.put(bankListener);
			// -------
		} else {
			request = abcBankBiz.printAbcB2CErrorMsg(tTrxResponse, request);
			chargeBiz.removeSession(request, charge);
			forward = "bankInfo";
			return mapping.findForward(forward);
		}
		chargeBiz.removeSession(request, charge);
		return null;
	}

	/**
	 * 向农业银行提交B2B充值订单
	 */
	private ActionForward sendToABC_B2B(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		// String forward = null;
		// Charge charge = (Charge) form;
		// Charge tempCharge = chargeBiz.createCharge(charge, request);
		// AbcB2BcmdToBank bank = abcBankBiz.createABCB2Bcmd(tempCharge);
		// FundTransferRequest fundTransferRequest =
		// bank.getFundTransferRequest();
		// com.hitrust.b2b.trustpay.client.TrxResponse tTrxResponse =
		// fundTransferRequest
		// .postRequest();
		// if (tTrxResponse.isSuccess()) {
		// String url = tTrxResponse.getValue("PaymentURL");
		// try {
		// response.sendRedirect(url);
		// } catch (IOException e) {
		// myLog.error("发送农业银行B2B订单失败：" + e.getMessage());
		// }
		// myLog.info("发送农业银行B2B订单" + tempCharge.getOrderNo() + ",url:" + url);
		// chargeLogBiz.saveChargeLog(tempCharge, url);
		// } else {
		// request = abcBankBiz.printAbcB2BErrorMsg(tTrxResponse, request);
		// chargeBiz.removeSession(request, charge);
		// forward = "bankInfo";
		// return mapping.findForward(forward);
		// }
		// chargeBiz.removeSession(request, charge);
		return null;
	}

	/**
	 * 向建行提交B2C充值订单
	 */
	private ActionForward sendToCCB_B2C(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		CcbB2CcmdToBank bank = ccbBankBiz.createCcbB2Ccmd(tempCharge);

		final String url = bank.getCCB_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			String bankInfo = "向建设银行发送B2C订单失败，原因是：" + e.getMessage();
			myLog.error(bankInfo);

			request.setAttribute("bankInfo", bankInfo);
			chargeBiz.removeSession(request, charge);
			return mapping.findForward("bankInfo");
		}

		myLog.info("向建设银行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		chargeBiz.removeSession(request, charge);
		return null;
	}

	/**
	 * 向建行提交B2B充值订单
	 */
	private ActionForward sendToCCB_B2B(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		CcbB2BcmdToBank bank = ccbBankBiz.createCcbB2Bcmd(tempCharge);

		final String url = bank.getCCB_B2B_URL();
		final String orderNo = tempCharge.getOrderNo();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			String bankInfo = "向建设银行发送B2B订单失败，原因是：" + e.getMessage();
			myLog.error(bankInfo);

			request.setAttribute("bankInfo", bankInfo);
			chargeBiz.removeSession(request, charge);
			return mapping.findForward("bankInfo");
		}

		myLog.info("向建设银行提交B2B订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		chargeBiz.removeSession(request, charge);
		return null;
	}

	/**
	 * 向工行提交B2C充值订单
	 */
	private ActionForward sendToICBC_B2C(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		IcbcB2CcmdToBank bank = icbcBankBiz.createIcbcB2CCmd(tempCharge);

		final String url = bank.getICBC_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向工商银行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		// ------开启线程,监听订单
		BankOrderListener bankListener = new BankOrderListener(icbcBankBiz,
				chargeBiz, orderNo, "B2C", "", requestHead);
		MainTask.put(bankListener);
		// ------

		request.setAttribute("IcbcB2CcmdToBank", bank);
		forward = "tempICBCB2C";

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	/**
	 * 向工行提交B2B充值订单
	 */
	private ActionForward sendToICBC_B2B(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		IcbcB2BCmdToBank bank = icbcBankBiz.createIcbcB2BCmd(tempCharge);

		final String url = bank.getICBC_B2B_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向工商银行提交B2B订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		// ------开启线程,监听订单
		BankOrderListener bankListener = new BankOrderListener(icbcBankBiz,
				chargeBiz, orderNo, "B2B", "", requestHead);
		MainTask.put(bankListener);
		// --------------------

		request.setAttribute("IcbcB2BCmdToBank", bank);
		forward = "tempICBCB2B";

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	/**
	 * 向交通银行提交B2C充值订单
	 */
	private ActionForward sendToBCM_B2C(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		BcmB2CcmdToBank bank = bcmBankBiz.createBCMB2CCmd(tempCharge);

		final String url = bank.getBCM_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向交通银行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		request.setAttribute("BcmB2CcmdToBank", bank);
		forward = "tempBCMB2C";

		// ------开启线程,监听订单
		BankOrderListener bankListener = new BankOrderListener(bcmBankBiz,
				chargeBiz, orderNo, "B2C", "", requestHead, 6);
		MainTask.put(bankListener);
		// -------------------

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	/**
	 * 向民生银行提交B2C充值订单
	 */
	private ActionForward sendToCMBC_B2C(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);
		CmbcB2CcmdToBank bank = cmbcBankBiz.createCmbcB2Ccmd(tempCharge);

		final String url = bank.getCMBC_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向民生银行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		request.setAttribute("CmbcB2CcmdToBank", bank);
		forward = "tempCMBCB2C";

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	/**
	 * 向中信银行提交B2C充值订单
	 */
	private ActionForward sendToCITIC_B2C(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);

		CiticB2CcmdToBank bank = citicBankBiz.createCiticB2Ccmd(tempCharge);

		final String url = bank.getCITIC_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向中信行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		request.setAttribute("CiticB2CcmdToBank", bank);
		forward = "tempCITICB2C";

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}

	/**
	 * 向招商银行提交B2C充值订单
	 */
	private ActionForward sendToCMB_B2C(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new LogUtil(false, false, ChargeAction.class);
		String forward = null;
		Charge charge = (Charge) form;
		Charge tempCharge = chargeBiz.createCharge(charge, request);

		CmbB2CcmdToBank bank = cmbBankBiz.createCmbB2Ccmd(tempCharge);

		final String url = bank.getCMB_B2C_URL();
		final String orderNo = tempCharge.getOrderNo();

		myLog.info("向招商行提交B2C订单" + orderNo + ",url:" + url);
		chargeLogBiz.saveChargeLog(tempCharge, url);

		request.setAttribute("CmbB2CcmdToBank", bank);
		forward = "tempCMBB2C";

		chargeBiz.removeSession(request, charge);
		return mapping.findForward(forward);
	}
	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public void setChargeBiz(ChargeBiz chargeBiz) {
		this.chargeBiz = chargeBiz;
	}

	public void setChargeLogBiz(ChargeLogBiz chargeLogBiz) {
		this.chargeLogBiz = chargeLogBiz;
	}

	public void setAbcBankBiz(AbcBankBiz abcBankBiz) {
		this.abcBankBiz = abcBankBiz;
	}

	public void setBcmBankBiz(BcmBankBiz bcmBankBiz) {
		this.bcmBankBiz = bcmBankBiz;
	}

	public void setBocBankBiz(BocBankBiz bocBankBiz) {
		this.bocBankBiz = bocBankBiz;
	}

	public void setCmbcBankBiz(CmbcBankBiz cmbcBankBiz) {
		this.cmbcBankBiz = cmbcBankBiz;
	}

	public void setCcbBankBiz(CcbBankBiz ccbBankBiz) {
		this.ccbBankBiz = ccbBankBiz;
	}

	public void setCiticBankBiz(CiticBankBiz citicBankBiz) {
		this.citicBankBiz = citicBankBiz;
	}
	
	public void setCmbBankBiz(CmbBankBiz cmbBankBiz) {
		this.cmbBankBiz = cmbBankBiz;
	}

	public void setIcbcBankBiz(IcbcBankBiz icbcBankBiz) {
		this.icbcBankBiz = icbcBankBiz;
	}

	public String getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(String requestHead) {
		this.requestHead = requestHead;
	}
}