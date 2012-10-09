package com.fordays.fdpay.agent.dao;

import java.util.List;

import com.fordays.fdpay.agent.Account;
import com.fordays.fdpay.agent.Agent;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AccountDAO extends BaseDAO {
	void saveAccount(Account account) throws AppException;

	void updateAccount(Account account) throws AppException;

	Account getAccountByAgentIdcertificationStatus(Long agentId, Long status)
			throws AppException;

	Account getAccountByBanknum(String banknum) throws AppException;

	List getAccountByAgentId(Long agentId) throws AppException;

	Account getAccountByBind(Long agentId, Long bindStatus) throws AppException;

	Account getAccountById(Long accountId) throws AppException;

	List getAccounts(Long agentId) throws AppException;
	 void deleteById(long id) throws AppException;

}
