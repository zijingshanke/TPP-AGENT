package com.fordays.fdpay.security.biz;

import javax.servlet.http.HttpServletRequest;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.CertInfo;
import com.fordays.fdpay.security.action.CertificateActionForm;

public interface CertInfoBiz {
	
	 	public CertInfo  addCertInfo(CertificateActionForm form,Agent agent,String ip) throws Exception;
	
	public CertInfo createUpCert(CertificateActionForm certificateActionForm,Agent agent,String ip) throws Exception;

	public CertInfo backUpCert(CertificateActionForm certificateActionForm,Agent agent,String ip,HttpServletRequest request) throws Exception;
	
	CertInfo getCertInfoByAgentId(long agentId) throws Exception;
	
	public CertInfo reviewCertificate(Agent agent)throws Exception;
	
	public void revokeCert(Agent agent) throws Exception;
	
}
