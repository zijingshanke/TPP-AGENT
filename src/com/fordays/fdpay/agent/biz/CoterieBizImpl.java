package com.fordays.fdpay.agent.biz;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.fordays.fdpay.agent.dao.CoterieDAO;
import com.neza.exception.AppException;

public class CoterieBizImpl implements CoterieBiz{
	CoterieDAO coterieDAO;
	public Coterie getCoterieById(long id) throws AppException {
		
		return coterieDAO.getCoterieById(id);
	}
	public CoterieDAO getCoterieDAO() {
		return coterieDAO;
	}
	public void setCoterieDAO(CoterieDAO coterieDAO) {
		this.coterieDAO = coterieDAO;
	}
	public Coterie getCoterieByPartner(String partner) throws AppException {
		
		return coterieDAO.getCoterieByPartner(partner);
	}
	public Coterie getCoterieByAgent(Agent agent) throws AppException {
		return coterieDAO.getCoterieByAgent(agent);
	}
	public void setKeyByAgent(Agent agent, String key) throws AppException {
		Coterie coterie=coterieDAO.getCoterieByAgent(agent);
		coterie.setSignKey(key);
		coterieDAO.updateOrSave(coterie);
	}
	public boolean checkAgentInCoterie(Agent agent) throws AppException {
		Coterie coterie=coterieDAO.getCoterieByAgent(agent);
		if(coterie!=null){
			return true;
		}
		return false;
	}

}
