package com.fordays.fdpay.agent.biz;

import java.sql.Timestamp;
import java.util.List;
import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.dao.AccountDAO;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.TaskTimestamp;
import com.fordays.fdpay.system.dao.BankDAO;
import com.fordays.fdpay.system.dao.CityDAO;
import com.fordays.fdpay.system.dao.TaskTimestampDAO;
import com.neza.exception.AppException;

public class AccountBizImpl implements AccountBiz {
	private AccountDAO accountDAO;
	private AgentDAO agentDAO;
	private BankDAO bankDAO;
	private TaskTimestampDAO tasktimestampDAO;
	private CityDAO cityDAO;

	public void saveAccount(Account account) throws AppException {
		accountDAO.saveAccount(account);
	}

	public void updateAccount(Account account) throws AppException {
		accountDAO.updateAccount(account);
	}

	public Account getAccountByBanknum(String banknum) throws AppException {
		return this.accountDAO.getAccountByBanknum(banknum);
	}

	public Account getAccountByAgentIdcertificationStatus(Long agentId,
			Long status) throws AppException {
		return accountDAO.getAccountByAgentIdcertificationStatus(agentId,
				status);
	}

	public List getAccountByAgentId(Long agentId) throws AppException {
		return accountDAO.getAccountByAgentId(agentId);
	}

	public Account getAccountByBind(Long agentId, Long bindStatus)
			throws AppException {
		return accountDAO.getAccountByBind(agentId, bindStatus);
	}

	public Account getAccountById(Long accountId) throws AppException {
		return accountDAO.getAccountById(accountId);
	}

	public List getAccounts(Long agentId) throws AppException {
		return accountDAO.getAccounts(agentId);
	}

	public void addAccount(Agent agent) throws AppException {
		Account account = new Account();
		Agent tempagent = agentDAO.getAgentById(agent.getId());
		tempagent.setStatus(agent.getStatus());
		if(agent.getRegisterType()==0){
		account.setAccountName(agent.getName());
		}
		else {
		account.setAccountName(agent.getCompanyName());
		}
		account.setAccountAddress(agent.getAccount().getAccountAddress());
//		System.out.println("ddd:0-----:"+agent.getName());
//		System.out.println("ddd:0-----:"+agent.getBankId());
		account.setBankId(new Long(agent.getBankId()));
		account.setAgent(tempagent);
		account.setCardNo(agent.getAccount().getCardNo());
		City city = cityDAO.getCityById(new Long(agent.getCityId()));
		account.setCity(city);
		if (accountDAO.getAccountByBind(agent.getId(), Account.bindStatus_Bind) == null) {
			account.setBindStatus(Account.bindStatus_Bind);
		} else {
			account.setBindStatus(Account.bindStatus_NoBind);
		}

		account.setCreateDate(new Timestamp(System.currentTimeMillis()));

		if (agent.getAccount().getCertificationStatus() != null
				&& agent.getAccount().getCertificationStatus() == Account.certificationStatus_1) {
			// 创建人物实名认证任务戳
			account.setCertificationStatus(Account.certificationStatus_1);
			TaskTimestamp tasktimestamp = new TaskTimestamp();
			tasktimestamp.setAgent(tempagent);
			tasktimestamp.setStatus(TaskTimestamp.status_1);
			tasktimestamp
					.setTaskDate(new Timestamp(System.currentTimeMillis()));
			tasktimestamp.setCount(TaskTimestamp.count_3);
			tasktimestamp.setTaskType(TaskTimestamp.type_6);
			tasktimestamp.setTaskHour(new Long(24));
			tasktimestampDAO.save(tasktimestamp);
		}
		accountDAO.saveAccount(account);
	
		agentDAO.update(tempagent);
	}

	public String fillinAccountInfo(Agent agent) throws AppException {
		if (accountDAO.getAccountByBanknum(agent.getAccount().getCardNo()) != null) {
			return "existentCardNo";
		} else {
			return "next";
		}
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public void setTasktimestampDAO(TaskTimestampDAO tasktimestampDAO) {
		this.tasktimestampDAO = tasktimestampDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public void removeAccountById(long accountId) throws AppException {
		accountDAO.deleteById(accountId);
	}

}
