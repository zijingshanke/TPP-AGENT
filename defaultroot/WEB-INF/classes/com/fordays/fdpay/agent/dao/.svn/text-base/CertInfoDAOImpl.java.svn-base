package com.fordays.fdpay.agent.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.agent.CertInfo;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.base.ListActionForm;

public class CertInfoDAOImpl extends BaseDAOSupport implements CertInfoDAO{
	private TransactionTemplate actionLogTemplate;
	
	public void setActionLogManager(PlatformTransactionManager actionLogTemplate) {
		this.actionLogTemplate = new TransactionTemplate(actionLogTemplate);
	}
	public void delete(CertInfo cert) {
		this.getHibernateTemplate().delete(cert);
		this.getHibernateTemplate().flush();
	}
	
	public long save(CertInfo  cert) throws Exception{
		this.getHibernateTemplate().saveOrUpdate(cert);
		return cert.getId();
	}
	
	public List list(String arg0) {
		return null;
	}

	public List list(String arg0, ListActionForm arg1) {
		return null;
	}
//	select lb from Library lb join lb.books b where lb.disabled = false and b.id = :bId
	public CertInfo getCertInfoByAgentId(long agentId) throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append("select c from CertInfo c join c.agents a where a.id=? ");
		Hql hql = new Hql(sb.toString());
		hql.addParamter(agentId);
		Query query = this.getQuery(hql);

		CertInfo cert=null;
		if (query!=null&& query.list()!=null&&query.list().size() > 0)
		{
			cert=(CertInfo) query.list().get(0);
		}
//		System.out.println("---"+cert.getCertName()+"-CreateDate-"+cert.getCreateDate());

		return cert;
	}
}
