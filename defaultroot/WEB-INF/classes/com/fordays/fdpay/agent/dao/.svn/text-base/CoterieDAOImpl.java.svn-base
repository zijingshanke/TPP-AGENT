package com.fordays.fdpay.agent.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;


public class CoterieDAOImpl extends BaseDAOSupport implements CoterieDAO{

	public Coterie getCoterieById(long id) throws AppException {		
		Hql hql=new Hql("from Coterie where id=? ");
		hql.addParamter(id);
		Query query = this.getQuery(hql);
		Coterie coterie=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			coterie =(Coterie)query.list().get(0);
		}
		return coterie;
	}

	public void updateOrSave(Coterie coterie) throws AppException {
	    	this.getHibernateTemplate().saveOrUpdate(coterie);
	}
	
	public Coterie getCoterieByPartner(String partner) throws AppException {
		Hql hql=new Hql("from Coterie where partner=?");
		hql.addParamter(partner);
		Query query = this.getQuery(hql);
		Coterie coterie=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			coterie =(Coterie)query.list().get(0);
		}
		return coterie;
	}

	public Coterie getCoterieByAgent(Agent agent) throws AppException {
		Hql hql=new Hql("from Coterie c where c.agent.id=?");
		hql.addParamter(agent.getId());
		Query query = this.getQuery(hql);
		Coterie coterie=null;
		if(query!=null&&query.list()!=null&&query.list().size()>0){
			coterie =(Coterie)query.list().get(0);
		}
		return coterie;
	}
	public ArrayList<Coterie> getCoteries() throws AppException{
		
		Hql hql=new Hql("from Coterie c where status=1");		
		Query query = this.getQuery(hql);
		ArrayList<Coterie> coteries=new ArrayList<Coterie>();

		List list=query.list();
		if(query!=null&&list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++)
			{
				coteries.add((Coterie)list.get(i));
			}			
		}
		return coteries;
	}
	
	
}
