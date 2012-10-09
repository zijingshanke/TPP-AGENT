package com.fordays.fdpay.agent.dao;

import java.util.ArrayList;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface CoterieDAO extends BaseDAO{
	public Coterie getCoterieById(long id) throws AppException;
	public Coterie getCoterieByPartner(String partner) throws AppException;
	public Coterie getCoterieByAgent(Agent agent) throws AppException;
	public ArrayList<Coterie> getCoteries() throws AppException;
	public void updateOrSave(Coterie coterie)throws AppException;
}
