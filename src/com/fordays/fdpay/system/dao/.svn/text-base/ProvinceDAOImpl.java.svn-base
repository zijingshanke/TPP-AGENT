package com.fordays.fdpay.system.dao;

import java.util.List;

import org.hibernate.Query;
import com.fordays.fdpay.system.Province;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.base.ListActionForm;
import com.neza.exception.AppException;

public class ProvinceDAOImpl extends BaseDAOSupport implements ProvinceDAO {
	public ProvinceDAOImpl(){
		
	}


	public List getProvinces() throws AppException {
		Hql hql=new Hql("from Province ");		
		Query query = this.getQuery(hql);
		return query.list();
	}

	public Province getProvinceById(Long provinceId) throws AppException {	
		Hql hql=new Hql("from Province where id=? ");
		hql.addParamter(provinceId);
		Query query = this.getQuery(hql);
		Province province=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			province =(Province)query.list().get(0);
		}
		return province;
	}



	

	

}
