package com.platform.application.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.platform.application.monitor.service.QyQueryService;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.operation.service.DataFromManagerService;

public class SyncUploadCreditLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SyncUploadCreditLog.class);
	private String tempPath = PropertyUtil.getPropertyByKey("TEMP_PATH"); // 临时文件目录
	private String GRZXCXMX = PropertyUtil.getPropertyByKey("GRZXCXMX"); // 个人征信查询日志明细表名称
	private String QYZXCXMX = PropertyUtil.getPropertyByKey("QYZXCXMX"); //企业征信查询日志明细表名称
	File tempPathFile; //临时文件缓冲区
	private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void init() throws ServletException {
		tempPathFile = new File(tempPath);  
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs(); 
		} 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=UTF-8");  
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter w=response.getWriter();
		try {
	
			String msg=pushContent(request);
			w.write(msg);
		} catch (Exception e) {
			e.printStackTrace();
			w.write("error");
			logger.info(e.getMessage());
		}finally{
			if(w!=null)
				w.close();
		}

	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	/**
	 * 栏目内容接口
	 * @param code 栏目编码
	 * @throws Exception 
	 * 
	 */
	public String pushContent(HttpServletRequest request) throws Exception{
		
		StringBuffer err=new StringBuffer();//错误信息
		String msg="error";
		/**上传附件类型、大小校验
		
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		
		//最大文件大小20M
		long maxSize = 20971520;
		
		*/
		
		
		//检查目录写权限
		if(!tempPathFile.canWrite()){
			throw new RuntimeException("上传目录没有写权限！");
		}
		
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb   
		factory.setRepository(tempPathFile);// 设置缓冲区目录
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		HashMap<String, String> fields=new HashMap<String, String>();//上传的字段集合
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
		
			if (!item.isFormField()) {//附件
				
				String fileName = item.getName();
				System.out.println(fileName+"-size-"+item.getSize());
				fields.put("fileName",fileName);//上传文件的名称
				
				//检查文件大小
//					if(item.getSize() > maxSize){
//						throw new RuntimeException("您上传的文件太大,默认20M！");
//					}
				//检查扩展名
				//String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				
//					if(!Arrays.<String>asList(extMap.get("file").split(",")).contains(fileExt)){
//						throw new RuntimeException("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get("file") + "格式。");
//					}
				
				//生成时间戳附件名称
				//SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				//String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				File uploadedFile = new File(tempPathFile, fileName);
				//存储附近
				//item.write(uploadedFile);
				InputStream ops=item.getInputStream();
				
				BufferedReader br=new BufferedReader(new InputStreamReader(ops));
				OutputStreamWriter pw = new OutputStreamWriter(new FileOutputStream(uploadedFile),"UTF-8");//确认流的输
				String str=null;
				String endLine = System.getProperty("line.separator"); // 获取换行符
				while ((str = br.readLine())!= null) // 判断最后一行不存在，为空结束循环
				{
					pw.write(str+endLine);//将要写入文件的内容，写入到新文件
				};
				
				br.close();
				pw.close();
				
				System.out.println("============111111=============="+sd.format(new Date()));
				
				fields.put("filePath",uploadedFile.getPath());
				
			}else{//字段名称
				
				//相当于input的name属性   <input type="text" name="content">  
				String name = item.getFieldName();
				
				//input的value属性  
				String value = item.getString();
				
				fields.put(name, value);                        
				//System.out.println("属性:" + name + " 属性值:" + value);  
			}
			
		}
		
		String bankCode=fields.get("bankCode");
		String dataType=fields.get("dataType");
		String fileName=fields.get("fileName");
		String filePath=fields.get("filePath");
		System.out.println(filePath);
		File tempfile=new File(filePath);
		
		if(!tempfile.isFile() || fileName==null ||fileName.length()==0)
			err.append("您上报数据文件无效；");
		if(StringUtils.isBlank(bankCode))
			err.append("您上报数据参数机构码:["+bankCode+"]无效；");
		if(StringUtils.isBlank(dataType))
			err.append("您上报数据参数数据类型:["+dataType+"]无效；");
		
		//==========校验文件命名规范=======================================
		String[] nameParts=fileName.split("_");
		if(nameParts.length!=3)
			err.append("您上报数据文件命名无效；");
		if(!nameParts[0].equals(bankCode))
			err.append("您上报数据文件命名无效；");
		
		String tablename=nameParts[1];
		if(tablename.equals(GRZXCXMX) || tablename.equals(QYZXCXMX)){}else{
			err.append("您上报数据文件命名无效；");
		}
			
		ApplicationContext acx =SpringContextHolder.getApplicationContext();
		
		//校验上报机构是否在监测系统注册
		DataFromManagerService sourceOrgService=(DataFromManagerService) acx.getBean("dataFromManagerServiceImpl");
		List<SourceOrg> sourceOrgByCode = sourceOrgService.getSourceOrgByCode(bankCode);//查询接入点是否注册
		SourceOrg sourceOrg=null;
		if(sourceOrgByCode.size()>0){
			sourceOrg = sourceOrgByCode.get(0);
		}
		if(sourceOrg==null){
			err.append("接入点未注册，请注册后重新上报！");
		}
		if(err.length()==0){
			
			try {
				//========================保持文件数据到数据库=================================================
				QyQueryService qyQueryService=(QyQueryService) acx.getBean("qyQueryServiceImpl");
				qyQueryService.txUploadRecord(dataType, nameParts,tempfile,sourceOrg);
				msg="success";
			} catch (Exception e) {
				logger.info("机构["+bankCode+"]上报数据失败："+err.toString()+"---"+sd.format(new Date()));
				msg="error";
			}
		}else{
			//输出错误日志
			logger.info("机构["+bankCode+"]上报数据失败："+err.toString()+"---"+sd.format(new Date()));
		}
		return msg;
			
	}
	

}
