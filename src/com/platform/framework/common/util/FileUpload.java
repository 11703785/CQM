package com.platform.framework.common.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import com.platform.application.common.util.PropertyUtil;

public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
		//获取根目录
		String rootPath = PropertyUtil.getPropertyByKey("TEMP_PATH");

		//文件保存目录URLreUrl=request.getContextPath()+"/downHomeFile.action?fileName=/file/content/"+fileName;
		String saveUrl  = request.getContextPath() + "/downHomeFile.action?path=temp&fileName=";

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 2097152;
		if(!ServletFileUpload.isMultipartContent(request)){
			out.println(getError("请选择文件。"));
			return;
		}
		//检查目录
		File uploadDir = new File(rootPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
/*		if(!uploadDir.isDirectory()){
			out.println(getError("上传目录不存在。"));
			return;
		}*/
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			out.println(getError("目录名不正确。"));
			return;
		}
		//创建文件夹
//		savePath += dirName + "/";
//		saveUrl += dirName + "/";
//		File saveDirFile = new File(savePath);
//		if (!saveDirFile.exists()) {
//			saveDirFile.mkdirs();
//		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
//		savePath += ymd + "/";
		saveUrl += ymd + "/";
		
		File dirFile = new File(uploadDir,ymd);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		FileItem ft =(FileItem) items.get(0);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			String str=item.getFieldName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
				if(item.getSize() > maxSize){
					out.println(getError("请您上传小于2M的文件！"));
					return;
				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String realName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					out.println(getError("上传文件错误：只允许\n" + extMap.get(dirName) + "\n格式。"));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(dirFile, newFileName);
					item.write(uploadedFile);
				}catch(Exception e){
					out.println(getError("上传文件失败。"));
					return;
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				obj.put("realPath",ymd+"/"+newFileName);
				System.out.println(ymd+"/"+newFileName);
				obj.put("title",realName);
				out.println(obj.toJSONString());
			}
		}
					
					
					
		} catch (Exception e) {
			out.println(getError("上传文件失败。"));
			e.printStackTrace();
		}
		

	}
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
