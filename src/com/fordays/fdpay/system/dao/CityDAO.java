package com.fordays.fdpay.system.dao;

import java.util.List;


import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.Province;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface CityDAO extends BaseDAO{
	public List getCityByProvince(Province province)throws AppException; 
	public City getCityById(Long cityId) throws AppException;
}
