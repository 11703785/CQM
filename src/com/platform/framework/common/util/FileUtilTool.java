package com.platform.framework.common.util;

import java.io.File;

import javax.servlet.ServletContext;

/**
 * 文件处理工具类
 */
public class FileUtilTool {

	/**
	 * 取得文件相对路径
	 * 
	 * @param basePath      基路径
	 * @param basePath      文件绝对路径
	 * @return String       文件相对路径
	 */
	public static String getRealPath(String basePath, String rePath) {
		return rePath.substring(basePath.length());
	}

	/**
	 * 取得文件后缀
	 * 
	 * @param fileName      文件名
	 * @return String       后缀名
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}

	/**
	 * 取得文件前缀
	 * 
	 * @param fileName      文件名
	 * @return String       文件前缀
	 */
	public static String getPrefix(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}

	/**
	 * 删除上下文环境中的一个文件
	 * 
	 * @param fileName         文件名
	 * @param application      系统上下文环境
	 */
	public static void deleteFile(ServletContext application, String filePath) {
		if (StringUtil.isNotBlank(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (StringUtil.isNotBlank(physicalFilePath)) {
				File file = new File(physicalFilePath);
				file.delete();
			}
		}
	}
	
	/**
	* 删除单个文件
	* @param   fileName    被删除文件的文件名
	* @return 单个文件删除成功返回true,否则返回false
	*/
	public static boolean deleteFile(String fileName){
		File file = new File(fileName);
		if(file.isFile() && file.exists()){
			return file.delete();
		}
//		else if(file.isDirectory() && file.exists()){
//			for(File f:file.listFiles()){
//				deleteFile(f.getPath());
//			}
//			file.delete();
//		}
		return false;
	}
	
	public static boolean mkdirDirectory(File directory){
		boolean falg=false;
		
		if(!directory.exists())
			falg=directory.mkdirs();
		return falg;
	}
	
	public static void main(String[] args) {
		
		String file="E:\\crms\\excel\\datatemp\\1489063466248";
		deleteFile(file);
	}

}