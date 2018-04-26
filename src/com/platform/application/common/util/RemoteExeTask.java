package com.platform.application.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.platform.framework.common.util.SpringContextUtil;

public class RemoteExeTask {
	
	 
	private static Logger logger = Logger.getLogger(RemoteExeTask.class);
	//private TaskExecute taskExecute;
	/*public String remoteExeTask(String names){
		String msg="";
		logger.info("=======远程调度执行任务开始===============");
		ApplicationContext ctx = SpringContextUtil.getApplicationContext();
		TaskManageService taskManageService=(TaskManageService) ctx.getBean("taskManageServiceImpl");
		TaskExecuteService taskExecuteService=(TaskExecuteService) ctx.getBean("taskExecuteServiceImpl");
		try {
			String temp[]=names.split(";");
			String overExe="";//已经执行过的；
			String overReport="";
			String unOver="";
			for(int x=0;x<temp.length-1;x++){
				int flag=taskExecuteService.isExecute(temp[x],temp[temp.length-1]);
				if(flag==0){//已经执行过
					overExe+=temp[x]+";";
					msg+="已经执行过下面的几个表："+overExe+"系统自动略过。";
				}else if(flag==1){//已经上报过。
					overReport+=temp[x]+";";
					msg+="已经上报过下面的几个表：系统自动略过。"+overReport;
				}else if(flag==2) {
					taskManageService.txRemoteExeTasks(taskExecute,names); 
					msg+="远程调度执行任务执行成功！";
				}
			}
			
			
	
		} catch (Exception e) {
			e.printStackTrace();
			msg="远程调度执行任务失败："+e.getMessage();
			logger.info(msg);
		}
		logger.info("======远程调度执行任务结束================");
		return msg;
	}*/
	
	public String remoteExeTask(FileInputStream in){
		String msg="失败";
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream("E:\\"+"SPLI_110000_100000_Q201609.zip");
			byte[] b = new byte[1024];
			while((in.read(b)) != -1){
			fos.write(b);
			}
			msg="成功";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	
	public byte[]  remoteGetLog(String names){
		try {
			String downPath=PropertyUtil.getPropertyByKey("FTP_SAVE_PATH");   //找到要下载文件的路径。
			SequenceInputStream sis = null;  
			InputStream is=null;
			byte bytes[]=null;
	        String name[]=names.split(";");
	        //构建流集合。  
            Vector<InputStream> vector = new Vector<InputStream>(); 
			for(int x=0;x<name.length;x++){
				File inputFile=new File(downPath+"\\"+name[x]+".log");
				if(inputFile.exists()) {
					if (inputFile.isFile()) {
						vector.addElement(new FileInputStream(inputFile));  
					} 
				}    
			}
			Enumeration<InputStream> e = vector.elements();  
            sis = new SequenceInputStream(e);  
            bytes = new byte[sis.available()]; 
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		 
	}
	 
//	public TaskExecute getTaskExecute() {
//		return taskExecute;
//	}
//	public void setTaskExecute(TaskExecute taskExecute) {
//		this.taskExecute = taskExecute;
//	}
	
	 
}
