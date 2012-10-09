package com.fordays.fdpay.system.dao;

import java.util.List;

import com.fordays.fdpay.system.Bank;
import com.fordays.fdpay.system.City;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface BankDAO extends BaseDAO {
	public List getBankList() throws AppException;

	public List getBankByCity(City city) throws AppException;

	public Bank getBankById(Long bankId) throws AppException;
}
