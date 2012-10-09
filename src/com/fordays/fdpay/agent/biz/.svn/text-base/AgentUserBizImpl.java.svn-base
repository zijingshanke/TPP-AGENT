package com.fordays.fdpay.agent.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentUser;
import com.fordays.fdpay.agent.dao.AgentUserDAO;
import com.neza.exception.AppException;

public class AgentUserBizImpl implements AgentUserBiz {

	private AgentUserDAO agentUserDAO;
	
	public void setAgentUserDAO(AgentUserDAO agentUserDAO) {
		this.agentUserDAO = agentUserDAO;
	}

	public void addAgentUser(AgentUser agentUser) throws AppException {
		if(agentUser!=null){
			agentUserDAO.add(agentUser);
		}
		// TODO Auto-generated method stub
	}

	public boolean deleteAgentUser(Long agentUserId) throws AppException {
		// TODO Auto-generated method stub
		if(agentUserId>0 && agentUserId!=null){
			AgentUser agentUser=this.getAgentUserById(agentUserId);
			if(agentUser!=null){
				agentUserDAO.delete(agentUser);
				return true;
			}
		}
		return false;
	}
	
	
	public AgentUser checkAgentUserLogin(String no, String password) throws AppException {
		// TODO Auto-generated method stub
		return agentUserDAO.getByQueryString(no, password);
	
	}
	
	public boolean isExistAgentUser(String no,Long id) throws AppException {
		// TODO Auto-generated method stub
		if(!no.equals("") && id!=null){
			AgentUser agentUser=agentUserDAO.getByQueryString(no);
//			System.out.println("----"+agentUser.getNo());
			System.out.println("------"+id);
			if(agentUser!=null){
				System.out.println("----"+agentUser.getId());
				if( id!=agentUser.getId()){
					return true;
				}else{
					return false;
				}				
			}
			return false;
		}
		return true;	
	}
	
	public AgentUser getAgentUserById(Long id) throws AppException{
			return agentUserDAO.getById(id);
		}

	public boolean updateAgentUser(AgentUser agentUser) throws AppException {
		// TODO Auto-generated method stub
		if(agentUser!=null){
			agentUserDAO.updateOrSave(agentUser);
			return true;
		}
		return false;
	}
	
	public List listAgentUser(Long agentId) throws AppException{
		return agentUserDAO.list(agentId);
	}

	public boolean deleteAgentUser(AgentUser agentUser) throws AppException {
		// TODO Auto-generated method stub
		return false;
	}

	public AgentUser getAgentUserByNo(String no) throws AppException {
		// TODO Auto-generated method stub
		if(no!="" && no!=null){
			return agentUserDAO.getByQueryString(no);
		}
		return null;
	}


}
