package com.fordays.fdpay.system.dao;

import java.util.List;
import org.hibernate.Query;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.Province;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class CityDAOImpl extends BaseDAOSupport implements CityDAO {
	public CityDAOImpl() {

	}

	public List getCityByProvince(Province province) throws AppException {
		Hql hql = new Hql("from City where province.id=? ");
		hql.addParamter(province.getId());
		Query query = this.getQuery(hql);
		return query.list();
	}

	public City getCityById(Long cityId) throws AppException {
		Hql hql = new Hql("from City where id=? ");
		hql.addParamter(cityId);
		Query query = this.getQuery(hql);
		City city = null;
		if (query != null && query.list() != null && query.list().size() > 0) {
			city = (City) query.list().get(0);
		}
		return city;
	}

}
