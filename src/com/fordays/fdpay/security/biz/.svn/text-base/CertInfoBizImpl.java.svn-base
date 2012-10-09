package com.fordays.fdpay.security.biz;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.CertInfo;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.agent.dao.CertInfoDAO;
import com.fordays.fdpay.right.UserRightInfo;
import com.fordays.fdpay.security.Certification;
import com.fordays.fdpay.security.action.CertificateActionForm;
import com.fordays.fdpay.security.action.SecurityActionForm;
import com.fordays.fdpay.security.ssl.CertReq;
import com.fordays.fdpay.security.ssl.ClientCertInfo;
import com.fordays.fdpay.security.ssl.Constant;
import com.fordays.fdpay.security.ssl.GenRootCert;
import com.fordays.fdpay.security.ssl.KeyStoreConvert;
import com.fordays.fdpay.security.ssl.RemoveCert;
import com.fordays.fdpay.security.ssl.SignCert;
import com.ibm.security.x509.X509CertImpl;
import com.neza.base.DownLoadFile;
import com.neza.tool.DateUtil;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import com.neza.base.DownLoadFile;
import com.neza.encrypt.BASE64;
import org.apache.struts.upload.*;
public class CertInfoBizImpl implements CertInfoBiz {
	private TransactionTemplate transactionTemplate;
	private CertInfoDAO certInfoDAO;
	private AgentDAO agentDAO;

	public CertInfoDAO getCertInfoDAO() {
		return certInfoDAO;
	}

	public void setCertInfoDAO(CertInfoDAO certInfoDAO) {
		this.certInfoDAO = certInfoDAO;
	}

	public void setTransactionManager(HibernateTransactionManager thargeManager) {
		this.transactionTemplate = new TransactionTemplate(thargeManager);
	}

	public CertInfo getCertInfoByAgentId(long agentId) throws Exception {
		return certInfoDAO.getCertInfoByAgentId(agentId);
	}

	public AgentDAO getAgentDAO() {
		return agentDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	// 添加数字证书
	public CertInfo addCertInfo(CertificateActionForm form, Agent agent, String ip)
			throws Exception {
		// HttpServletRequest request
		String msg = "";
		CertInfo cert = new CertInfo();
		cert.setCertName(agent.getName());
		cert.setIp(ip);
		cert.setUserName(form.getUserName());
		cert.setCreateDate(new java.sql.Timestamp(new Date().getTime()));
		cert.setCardNo(form.getIdentity());
		cert.setCardType(1l);
		cert.setMobil(form.getMobile());

		cert.setEmail(agent.getEmail());
		cert.setPassword(form.getPassword());// --设置密码

		Certification.deletePersonCert(cert, "arm");
		msg = Certification.reqPersonCert(cert); // 请求个人证书
		System.out.println("--1.请求生成个人证书--" + msg);

		Certification.deletePersonCert(cert, "cer");
		msg = Certification.signPersonCert(cert); // 签署个人证书
		System.out.println("--2.签署个人证书--" + msg);

		msg = Certification.receivePersonCert(cert); // 接收个人证书
		System.out.println("--3.接收个人证书--" + msg);

		Certification.deletePersonCert(cert, "p12");
		msg = Certification.getP12Cert(cert);// 导出证书
		System.out.println("--4.导出个人证书--" + msg);

		System.out.println("  password--" + form.getPassword());

		// msg=Certification.deletePersonCert(cert);//删除证书 .cer .arm
		// System.out.println("--删除证书"+msg);
		agent.setCertStatus(Constant.CERT_STATUS_1);// 设置用户证书状态 为 1
		cert.setStatus(Constant.CERT_STATUS_1); // 设置证书状态 为 1
		certInfoDAO.save(cert);// 添加证书
		agent.setCertInfo(cert);
		agentDAO.update(agent);// 更新用户证书信息
		// cert.setPassword(form.getPassword());

		return cert;
	}

	// 查看证书
	public CertInfo reviewCertificate(Agent agent) throws Exception {
		if (agent != null) {
			CertInfo certInfo = certInfoDAO.getCertInfoByAgentId(agent.getId());
			// 设置证书 有效终止日期
			if(certInfo!=null){
				try{
					System.out.println("证书创建日期："+certInfo.getCreateDate());
					Calendar ca = Calendar.getInstance();
					ca.setTime(certInfo.getCreateDate());
					int year = ca.get(ca.YEAR) + 1;
					ca.set(ca.YEAR, year);
					Timestamp ts = new Timestamp(ca.getTimeInMillis());
					System.out.println("明年日期：" + ts);
					certInfo.setEndTime(ts);
				}catch(Exception e){
					e.printStackTrace();
				}
				return certInfo;
			}
			System.out.println("没有 证书-->为空！");
		}
		return new CertInfo();
	}

	// 备份数字证书
	public CertInfo createUpCert(CertificateActionForm certificateActionForm,
			Agent agent, String ip) throws Exception {
		System.out.println("------执行生成证书Biz!--------");
		CertInfo certInfo = null;
		// String ip = request.getRemoteHost();

		if (agent.getCertStatus() != 1) {// 如果是申请用户则先添加证书
			certInfo = this.addCertInfo(certificateActionForm, agent, ip);// 添加数字证书
			System.out.println("证书信息添加成功！");
		}
//		if(agent.getCertStatus() == 1){
//			
//		}
		// 备份数字证书
//		certInfo = this.reviewCertificate(agent);
//		if (certInfo.getSerialNo() == null || certInfo.getSerialNo().equals("")) {
//			certInfo.setPassword(securityactionForm.getPassword());
//			String serialNum = Certification.getSerialNumber(certInfo);// 获得证书序列号
//			System.out.println("--序列号：" + serialNum);
//			if (!"".equals(serialNum)) {
//				certInfo.setSerialNo(serialNum);
//				certInfoDAO.save(certInfo);// 修改证书
//			}
//		}
//		System.out.println("备份证书————！");
		return certInfo;
	}

	public CertInfo backUpCert(CertificateActionForm certificateActionForm,
			Agent agent, String ip,HttpServletRequest request) throws Exception {
		CertInfo certInfo = null;
		// String ip = request.getRemoteHost();
		// 备份数字证书

		certInfo = this.reviewCertificate(agent);
		if (certInfo.getSerialNo() == null || certInfo.getSerialNo().equals("")) {
			certInfo.setPassword(certificateActionForm.getPassword());
			String serialNum = Certification.getSerialNumber(certInfo);// 获得证书序列号
			System.out.println("--序列号：" + serialNum);
			if (!"".equals(serialNum)) {
				certInfo.setSerialNo(serialNum);
				UserRightInfo uri=(UserRightInfo)request.getSession().getAttribute("URI");
				uri.getAgent().setSerialNo(serialNum);
				certInfoDAO.save(certInfo);// 修改证书
			}
		}
		System.out.println("备份证书————！");

		return certInfo;
	}

	// 注销数字证书
	public void revokeCert(Agent agent) throws Exception {
		CertInfo certInfo = null;
		certInfo = this.reviewCertificate(agent);
		String msg = "";
		if (certInfo != null) {
			certInfo.setStatus(Constant.CERT_STATUS_0); // 更改证书状态为0
			agent.setCertStatus(Constant.CERT_STATUS_0);// 更改用户证书状态 为 0
			agent.setCertInfo(null); // 设置证书ID为null
			msg = Certification.deletePersonCert(certInfo, "arm"); // 删除arm证书
			System.out.println("--删除arm证书" + msg);
			msg = Certification.deletePersonCert(certInfo, "cer"); // 删除cer证书
			System.out.println("--删除cer证书" + msg);
			msg = Certification.deletePersonCert(certInfo, "p12"); // 删除p12证书
			System.out.println("--删除p12证书" + msg);

			certInfoDAO.save(certInfo);// 修改证书
			agentDAO.update(agent);// 更新用户证书信息

			System.out.println("用户当前证书状态：" + agent.getCertStatus() + "--证书状态"
					+ certInfo.getStatus());
		}
	}

	

}
