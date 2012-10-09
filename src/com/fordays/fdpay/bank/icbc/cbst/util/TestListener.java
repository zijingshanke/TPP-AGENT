package com.fordays.fdpay.bank.icbc.cbst.util;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestListener implements ServletContextListener {
	private Timer timer = null;

	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		timer.schedule(new TestTimer(), 0, 86400000);// milliseconds
	}

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}
}
