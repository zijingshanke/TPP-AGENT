package com.fordays.fdpay.system;

import java.util.Calendar;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fordays.fdpay.transaction.biz.TransactionBiz;

public class TransactionTaskJob extends QuartzJobBean
{

	private TransactionBiz transactionBiz;
	
	
	private boolean runing = false;

	public void setTransactionBiz(TransactionBiz transactionBiz)
	{
		this.transactionBiz = transactionBiz;
	}

	// @Override
	protected void executeInternal(JobExecutionContext context)
	    throws JobExecutionException
	{

		try
		{

			if (runing)
				return;
			else
				runing = true;
			Calendar calendar = Calendar.getInstance();
			int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
			int nowMin = calendar.get(Calendar.MINUTE);

			transactionBiz.executeClose();
			
	
		//	hql="from Transaction where sysdat-payDate>=24*7";
			
			
			runing = false;
		}
		catch (Exception ex)
		{
			System.out.print("." + ex.getMessage());
			runing = false;
		}
		// JobDetail job = new JobDetail("jobName", "groupName",
		// SimpleJob.class);
		// Scheduler.scheduleJob(job, cronTrigger);
	}



}
