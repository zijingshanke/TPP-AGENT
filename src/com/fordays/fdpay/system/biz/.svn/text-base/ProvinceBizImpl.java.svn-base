package com.fordays.fdpay.system.biz;

import java.util.List;
import com.fordays.fdpay.system.Province;
import com.fordays.fdpay.system.dao.ProvinceDAO;
import com.neza.exception.AppException;

public class ProvinceBizImpl implements ProvinceBiz {
	private ProvinceDAO provinceDAO;

	public List getProvinces() throws AppException {
		return provinceDAO.getProvinces();
	}

	public Province getProvinceById(Long provinceId) throws AppException {
		return provinceDAO.getProvinceById(provinceId);
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}
}
