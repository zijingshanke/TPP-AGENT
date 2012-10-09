package com.fordays.fdpay.system.biz;

import java.util.List;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.Province;
import com.fordays.fdpay.system.dao.CityDAO;
import com.neza.exception.AppException;

public class CityBizImpl implements CityBiz {
	private CityDAO cityDAO;

	public City getCityById(Long cityId) throws AppException {
		return cityDAO.getCityById(cityId);
	}

	public List getCityByProvince(Province province) throws AppException {
		return cityDAO.getCityByProvince(province);
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}
}
