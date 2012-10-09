package com.fordays.fdpay.agent.biz;
import java.util.List;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.AgentUser;
import com.neza.exception.AppException;
public interface AgentUserBiz {
	
	public void addAgentUser(AgentUser agentUser) throws AppException;//添加一个操作员
	public boolean updateAgentUser(AgentUser agentUser) throws AppException;//修改操作员信息
	public boolean deleteAgentUser(AgentUser agentUser) throws AppException;//删除一个操作员
	public boolean deleteAgentUser(Long agentUserId) throws AppException;
	public AgentUser checkAgentUserLogin(String no,String password) throws AppException;//检验操作员登录
	public boolean isExistAgentUser(String no,Long id) throws AppException;//检验操作员是否已经存在
	public AgentUser getAgentUserById(Long id) throws AppException;
	public AgentUser getAgentUserByNo(String no) throws AppException;
	public List listAgentUser(Long agentId) throws AppException;
}
