package com.fordays.fdpay.system.biz;

import java.util.List;
import com.fordays.fdpay.system.Bank;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.dao.BankDAO;
import com.neza.exception.AppException;

public class BankBizImpl implements BankBiz {
	private BankDAO bankDAO;

	public List getBankList() throws AppException {
		return bankDAO.getBankList();
	}

	public List getBankByCity(City city) throws AppException {
		return bankDAO.getBankByCity(city);
	}

	public Bank getBankById(Long bankId) throws AppException {
		return bankDAO.getBankById(bankId);
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}
}
