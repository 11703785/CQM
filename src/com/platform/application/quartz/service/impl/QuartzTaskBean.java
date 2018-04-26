package com.platform.application.quartz.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.platform.application.monitor.service.MonitorRecordService;
import com.platform.application.monitor.service.QyQueryService;

public class QuartzTaskBean extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(QuartzTaskBean.class);
	protected void executeInternal(JobExecutionContext jobexecutioncontext) {
		try{
			Trigger trigger = jobexecutioncontext.getTrigger();
			String triggerName = trigger.getKey().getName();
			System.out.println(triggerName);
			//调用grsummary(),qysummary()存储过程
			MonitorRecordService ms=(MonitorRecordService) getApplicationContext(jobexecutioncontext).getBean("monitorRecordServiceImpl");
			if(triggerName.equals("summary")){
				ms.summary();				
			}
			if(triggerName.equals("summarytemp")){
				ms.summarytemp();
			}
			//创建年度新表
			if(triggerName.equals("createTable")){
				ms.createTable();
			}
			//为createdate注入数据
			if(triggerName.equals("insert")){
				ms.insert();
			}
//			RateTaskService rateTaskService=(RateTaskService) getApplicationContext(jobexecutioncontext).getBean("rateTaskServiceImpl");
//			rateTaskService.txDsManualRateTask(triggerName);
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	private ApplicationContext getApplicationContext(final JobExecutionContext jobexecutioncontext) {
		try {
			return (ApplicationContext) jobexecutioncontext.getScheduler().getContext().get("applicationContextKey");
		} catch (SchedulerException e) {
			logger.error("jobexecutioncontext.getScheduler().getContext() error!", e);
			throw new RuntimeException(e);
		}
	}

}
