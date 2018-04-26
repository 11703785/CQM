package com.platform.application.quartz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.CronExpression;

public class FrequencyAsTime {
	
	private static String year="59 59 23 1 1 ? *";//默认每年最后一天的23:59:59
	private static String quarter="59 59 23 1 1,4,7,10 ? *";//默认3、6、9、12月的最后一天23:59:59
	private static String month="59 59 23 1 * ? *";//默认每月的最后一天23:59:59
	private static String week="59 59 23 * * 1 *";//默认每周的最后一天23:59:59
	private static String day="59 59 23 * * ? *";//默认每天的23:59:59
	
	/**
	 * 根据频度、延迟天数、启动时间计算定时任务的执行时间
	 * @param frequency
	 * @param delayDays
	 * @param exeTime
	 * @throws Exception 
	 */
	public static CronExpression quartzTimeByFrequency(String frequency,Integer delayDays,String exeTime) throws Exception{
		
		SimpleDateFormat sd=new SimpleDateFormat("ss mm HH");
		SimpleDateFormat s=new SimpleDateFormat("HH:mm:ss");
		String quartzTime="";
		StringBuffer sbf=new StringBuffer();
		
		if(frequency!=null && frequency.equals("y")){//年频
			quartzTime=year;
			int defaultDay=1;//默认月第一天执行，如果有延迟，累加
			if(delayDays!=null && delayDays>0)
				defaultDay=delayDays;
			
			if(exeTime!=null && exeTime.length()>0){
				sbf.append(timeTotime(exeTime));
				sbf.append(" "+defaultDay);
				sbf.append(" 1 ? * ");
				quartzTime=sbf.toString();
			}
			
		}else if(frequency!=null && frequency.equals("q")){//季频
			quartzTime=quarter;
			int defaultDay=1;//默认月第一天执行，如果有延迟，累加
			if(delayDays!=null && delayDays>0)
				defaultDay=delayDays;
			
			if(exeTime!=null && exeTime.length()>0){
				sbf.append(timeTotime(exeTime));
				sbf.append(" "+defaultDay);
				sbf.append(" 1,4,7,10 ? * ");
				quartzTime=sbf.toString();
			}
			
		}else if(frequency!=null && frequency.equals("m")){//月频
			quartzTime=month;
			int defaultDay=1;//默认月第一天执行，如果有延迟，累加
			if(delayDays!=null && delayDays>0)
				defaultDay=delayDays;
			
			if(exeTime!=null && exeTime.length()>0){
				sbf.append(timeTotime(exeTime));
				sbf.append(" "+defaultDay);
				sbf.append(" * ? * ");
				quartzTime=sbf.toString();
			}
			
		}else if(frequency!=null && frequency.equals("w")){//  周频
			quartzTime=week;
			int defaultWeek=2;//默认周一执行，如果有延迟，累加
			if(delayDays!=null && delayDays>0){
				if(delayDays==7){//
					delayDays=1;
				}else{
					delayDays=delayDays+1;
				}
				defaultWeek=delayDays;
			}
			
			if(exeTime!=null && exeTime.length()>0){
				sbf.append(timeTotime(exeTime));
				sbf.append(" ? * ");
				sbf.append(defaultWeek);
				quartzTime=sbf.toString();
			}
			
			
			
		}else if(frequency!=null && frequency.equals("d")){//  日频
			quartzTime=day;
			if(exeTime!=null && exeTime.length()>0){
				sbf.append(timeTotime(exeTime));
				sbf.append(" * * ? *");
				quartzTime=sbf.toString();
			}
		}else if(frequency!=null && frequency.equals("s")){//  一次
			
			SimpleDateFormat sdf= new SimpleDateFormat(" dd MM ? yyyy");  
			Calendar calendar = Calendar.getInstance();
			
			int defaultDay=0;//默认当天执行，如果有延迟，累加
			if(delayDays!=null && delayDays>0)
				defaultDay=delayDays;
			calendar.add(Calendar.DATE, delayDays);
			
			
			if(exeTime!=null && exeTime.length()>0){
				sbf.append(timeTotime(exeTime));
				sbf.append(sdf.format(calendar.getTime()));
				quartzTime=sbf.toString();
			}
		}
		
		
		return new CronExpression(quartzTime);
	}
	
	/**
	 * 启动时间格式转换
	 * @param exeTime
	 * @return
	 * @throws Exception
	 */
	public static String timeTotime(String exeTime) throws Exception{
		if(exeTime!=null && exeTime.length()>0){
			SimpleDateFormat sd=new SimpleDateFormat("ss mm HH");
			SimpleDateFormat s=new SimpleDateFormat("HH:mm:ss");
			exeTime=sd.format(s.parse(exeTime));
		}
		
		return exeTime;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SimpleDateFormat sd=new SimpleDateFormat("ss mm HH");
			SimpleDateFormat s=new SimpleDateFormat("HH:mm:ss");
			s.parse("17:12:11");
			System.out.println(sd.format(s.parse("17:12:11")));
			Calendar c=Calendar.getInstance();
			
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		

	}

}
