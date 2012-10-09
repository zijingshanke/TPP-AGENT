package com.fordays.fdpay.base;

import javax.servlet.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fordays.fdpay.bank.BankLogUtil;
import com.fordays.fdpay.bank.LogUtil;
import com.fordays.fdpay.system.biz.SysInitBiz;

public class ContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		SysInitBiz sysInitBiz;
		System.out.println("init system,please waiting a few minutes.");
		try {
			ApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(event.getServletContext());

			sysInitBiz = (SysInitBiz) applicationContext.getBean("sysInitBiz");
			sysInitBiz.initPlatAgent();
			
			sysInitBiz.initRMIServer();

		} catch (Exception ex) {
			System.out.println("init system fails... " + ex.getMessage());
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		LogUtil myLog=new BankLogUtil(true,true,ContextListener.class);
		myLog.info("-------------context destroryed------------------");
		SysInitBiz sysInitBiz;		
		System.out.println("begin contextDestroyed...");
		try {
			ApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(event.getServletContext());

			sysInitBiz = (SysInitBiz) applicationContext.getBean("sysInitBiz");
			
			sysInitBiz.unbindRMIServer();
		} catch (Exception ex) {
			System.out.println("end contextDestroyed... " + ex.getMessage());
		}
		System.out.println("end  contextDestroyed... ");
	}

}