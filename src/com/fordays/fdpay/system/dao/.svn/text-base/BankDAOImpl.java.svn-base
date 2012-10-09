package com.fordays.fdpay.system.dao;

import java.util.List;
import org.hibernate.Query;
import com.fordays.fdpay.system.Bank;
import com.fordays.fdpay.system.City;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class BankDAOImpl extends BaseDAOSupport implements BankDAO {
	public BankDAOImpl() {

	}

	public List getBankList() throws AppException {
		Hql hql = new Hql("from Bank");
		System.out.println(hql.toString());
		Query query = this.getQuery(hql);
		return query.list();
	}

	public List getBankByCity(City city) throws AppException {
		Hql hql = new Hql("from Bank where city.id=? ");
		hql.addParamter(city.getId());
		Query query = this.getQuery(hql);
		return query.list();
	}

	public Bank getBankById(Long bankId) throws AppException {
		Hql hql = new Hql("from Bank where id=? ");
		hql.addParamter(bankId);
		Query query = this.getQuery(hql);
		Bank bank = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			bank = (Bank) query.list().get(0);
		}
		return bank;
	}

}
