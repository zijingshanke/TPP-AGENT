package com.fordays.fdpay.agent.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentAddress;
import com.fordays.fdpay.agent.AgentContact;
import com.fordays.fdpay.agent.AgentListForm;
import com.fordays.fdpay.agent.AgentRelationship;
import com.fordays.fdpay.agent.AgentUser;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AgentUserDAO extends BaseDAO {
	
	public AgentUser add(AgentUser agentUser) throws AppException;//添加
	public void updateOrSave(AgentUser agentUser) throws AppException;//修改
	public void delete(AgentUser agentUser) throws AppException;//删除
	public AgentUser getByQueryString(String no,String password) throws AppException;//查询
	public AgentUser getByQueryString(String no) throws AppException;//查询
	public AgentUser isExist(String no,Long id) throws AppException;//查询
	public AgentUser getById(Long id) throws AppException; //要id查询
	public List list(Long agentId) throws AppException;
}
