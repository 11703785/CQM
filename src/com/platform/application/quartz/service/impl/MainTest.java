package com.platform.application.quartz.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.platform.application.quartz.service.SchedulerService;

public class MainTest {

	/**
	 * @param args
	 * @throws SchedulerException
	 */
	public static void main(String[] args) throws SchedulerException {
		//ApplicationContext act = SpringContextUtil.getApplicationContext();
		//00 00 15 13 7 ? * 
		//01 11 02 20 1,4,7,10 ? *  
		//59 58 23 8 * ? * 
		//00 00 23 ? * 5 
		//00 01 23 * * ? *
		//01 59 22 16 07 ? 2016 
		ApplicationContext springContext = new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/applicationContext.xml");
		System.out.println(springContext+"--------------------------------");
		SchedulerService schedulerService = (SchedulerService) springContext.getBean("schedulerService");
		System.out.println(schedulerService);
		SimpleDateFormat sdf= new SimpleDateFormat("ss mm HH dd MM ? yyyy");
		System.out.println(sdf.format(new Date()));
		// 执行业务逻辑...

		// 设置高度任务
		// 每10秒中执行调试一次
		
		//00 00 17 13 7 ? *
		//00 10 17 13 1,4,7,10 ? *
		//00 15 17 13 * ? *
		//00 20 17 ? * 3

		//schedulerService.schedule("DDDDDDDDDD3","DDDDDDDDDD3","0/5 * * ? * * *");

		//schedulerService.schedule("test","test","0/30 * * ? * * *");
		//schedulerService.pauseTrigger("DDDDDDDDDD3", "DDDDDDDDDD3");
		//schedulerService.removeTrigdger("DDDDDDDDDD", "DDDDDDDDDD");
		
		//schedulerService.pauseTrigger("测试","test");
		//schedulerService.resumeTrigger("测试","test");
	    //schedulerService.removeTrigdger("insert","insert");
		//每5分钟调一次存储过程
		//schedulerService.schedule("summarytemp","summarytemp","0/60 * * ? * * *");
		//schedulerService.pauseTrigger("summary","summary");
		schedulerService.pauseTrigger("summarytemp","summarytemp");
		schedulerService.resumeTrigger("summary","summary");
		//schedulerService.resumeTrigger("summarytemp","summarytemp");
	    //schedulerService.removeTrigdger("summary","summary");
        
		//每年新建表
		//schedulerService.schedule("createTable","createTable","0 0 0 1 1 ? *");
		//schedulerService.pauseTrigger("createTable","createTable");
		//schedulerService.resumeTrigger("createTable","createTable");
		
		//schedulerService.schedule("test","test","0/5 * * * * ? *");
		//schedulerService.pauseTrigger("test","test");
       
		//schedulerService.schedule("insert","insert","0/2 * * * * ? *");
		//schedulerService.pauseTrigger("insert","insert");
		schedulerService.resumeTrigger("insert","insert");

//		schedulerService.schedule("qysummary",startTime,endTime,999999999,50);
		//schedulerService.pauseTrigger("qysummary", "qysummary");
		//schedulerService.resumeTrigger("insert","insert");
		

		Date startTime = parse("2017-08-16 09:02:00");
		Date endTime = parse("2017-08-16 09:20:00");
		// 2014-08-19 16:33:00开始执行调度
		//schedulerService.schedule(startTime);

		// 2014-08-19 16:33:00开始执行调度，2014-08-22 21:10:00结束执行调试
		//schedulerService.schedule("测试任务6666",startTime, endTime,3,20000);

		// 2014-08-19 16:33:00开始执行调度，执行5次结束
		//schedulerService.schedule(startTime, 3);

		// 2014-08-19 16:33:00开始执行调度，每隔20秒执行一次，执行5次结束
		//schedulerService.schedule("666666",startTime,endTime,0,2000);
		//schedulerService.pauseTrigger("666666");
		//schedulerService.removeTrigdger("666666");
		// 等等，查看com.sundoctor.quartz.service.SchedulerService

	}

	private static Date parse(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}

