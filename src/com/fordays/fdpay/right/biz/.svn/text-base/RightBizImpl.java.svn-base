package com.fordays.fdpay.right.biz;

import java.util.List;

import com.fordays.fdpay.agent.AgentRoleRight;
import com.fordays.fdpay.agent.AgentUserRoleRight;
import com.fordays.fdpay.agent.dao.AgentUserDAO;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.right.dao.RightDAO;
import com.fordays.fdpay.transaction.Charge;
import com.neza.exception.AppException;

public class RightBizImpl implements RightBiz {
	private RightDAO rightDAO;
	private AgentUserDAO agentUserDAO;

	public void setAgentUserRights(long agentUserId, long[] rightIds)
			throws AppException {
		rightDAO.deleteAllAgentUserRoleRight(agentUserId);
		for (int i = 0; i < rightIds.length; i++) {
			AgentUserRoleRight aurr = new AgentUserRoleRight();
			aurr.setAgentUser(agentUserDAO.getById(agentUserId));
			AgentRoleRight agentRoleRight = rightDAO
					.getAgentRoleRightById(rightIds[i]);
			aurr.setAgentRoleRight(agentRoleRight);
			rightDAO.update(aurr);
		}
	}

	public List getAgentUserRoleRightByAgentUserId(long agentUserId)
			throws AppException {
		return rightDAO.listRoleRightsByAgentUserId(agentUserId);
	}

	public List getAgentUsers(long agentId) throws AppException {
		return rightDAO.listAgentUsers(agentId);
	}

	/**
	 * @description:
	 * @param Charge charge
	 * @return UserRightInfo
	 * @remark:	BankAction调用
	 */
	public UserRightInfo getUserRightInfobyCharge(Charge charge)
			throws AppException {
		UserRightInfo uri = new UserRightInfo();
		uri.setAgent(charge.getAgent());

		List list = rightDAO.listRoleRights();
		for (int i = 0; i < list.size(); i++) {
			AgentRoleRight arr = (AgentRoleRight) list.get(i);
			uri.addRight(arr.getRightCode(), arr.getRightAction());
		}
		return uri;
	}

	public void setRightDAO(RightDAO rightDAO) {
		this.rightDAO = rightDAO;
	}

	public void setAgentUserDAO(AgentUserDAO agentUserDAO) {
		this.agentUserDAO = agentUserDAO;
	}
}
