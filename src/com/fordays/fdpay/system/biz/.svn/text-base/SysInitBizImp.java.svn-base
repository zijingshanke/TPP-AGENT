package com.fordays.fdpay.system.biz;

import java.rmi.RemoteException;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.fordays.fdpay.bank.rmi.BankOrderRemoteBiz;
import com.fordays.fdpay.bank.rmi.BankOrderRemoteBizImp;
import com.fordays.fdpay.bank.rmi.RMINode;
import com.fordays.fdpay.bank.rmi.RMIServer;
import com.fordays.fdpay.cooperate.MainTask;
import com.neza.exception.AppException;

public class SysInitBizImp implements SysInitBiz {

	private AgentDAO agentDAO;
	private CoterieDAO coterieDAO;
	public String platAgentEmail = "";
	public String platChargeAgentEmail = "";
	public String platRateAgentEmail = "";

	public void setPlatAgentEmail(String platAgentEmail) {
		this.platAgentEmail = platAgentEmail;
	}

	public void setPlatChargeAgentEmail(String platChargeAgentEmail) {
		this.platChargeAgentEmail = platChargeAgentEmail;
	}

	public void setPlatRateAgentEmail(String platRateAgentEmail) {
		this.platRateAgentEmail = platRateAgentEmail;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setCoterieDAO(CoterieDAO coterieDAO) {
		this.coterieDAO = coterieDAO;
	}

	public void initPlatAgent() throws AppException {
		try {
			if (com.fordays.fdpay.base.Constant.platAgent == null)
				com.fordays.fdpay.base.Constant.platAgent = (Agent) agentDAO
						.getAgentByLoginName(platAgentEmail).clone();
			if (com.fordays.fdpay.base.Constant.platChargeAgent == null)
				com.fordays.fdpay.base.Constant.platChargeAgent = agentDAO
						.getAgentByLoginName(platChargeAgentEmail);
			if (com.fordays.fdpay.base.Constant.platRateAgent == null)
				com.fordays.fdpay.base.Constant.platRateAgent = agentDAO
						.getAgentByLoginName(platRateAgentEmail);

			if (com.fordays.fdpay.base.Constant.coteries == null)
				com.fordays.fdpay.base.Constant.coteries = coterieDAO
						.getCoteries();
		} catch (Exception ex) {
			System.out.println("init system plat agent fails... "
					+ ex.getMessage());
		}
		try {

			// 20090515-ref001-jason-s
			MainTask task = MainTask.getInstance();
			Thread thread = new Thread(task);
			thread.start();
			// 20090515-ref001-jason-e
			// System.out.println("--------client-00--"+com.fordays.fdpay.base.Constant.platAgent);
		} catch (Exception ex) {
			System.out.println("init MainTask fails... " + ex.getMessage());
		}
	}

	/**
	 * 初始化RMI Server
	 */
	public void initRMIServer() throws RemoteException {
		//此处目前只启动网上银行订单监听
		
		List<RMINode> nodeList = RMINode.getRMINodeList("bankOrder");
		BankOrderRemoteBiz bizObj = new BankOrderRemoteBizImp();

		RMIServer.init(nodeList, bizObj);
	}
	
	/**
	 * 解除RMI Server对象绑定
	 */
	public void unbindRMIServer() throws RemoteException {
		//此处目前只启动网上银行订单监听
		
		List<RMINode> nodeList = RMINode.getRMINodeList("bankOrder");
		BankOrderRemoteBiz bizObj = new BankOrderRemoteBizImp();

		RMIServer.unbind(nodeList, bizObj);
	}	
}
