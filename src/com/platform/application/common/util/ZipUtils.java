package com.platform.application.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;

/**
 * <p>版权所有:(C)2003-2010 北京融嘉合创科技有限公司</p> 
 * @作者：chenwei
 * @日期：2010-9-30 下午02:15:41 
 * @描述：[ZipUtils]打包压缩解压缩工具类
 */
public class ZipUtils {

	private ZipUtils() {}
	/**
	 * <p>方法名称: zip|对目录中的文件进行打包</p>
	 * @param   dest    目前zip文件地址路径
	 * @param   src     被打包的目录路径
	 * @return
	 * @throws Exception
	 */
	public static void zip(String dest,String src){
		Zip zip = new Zip();
		zip.setBasedir(new File(src));
		//zip.setIncludes(...); 包括哪些文件或文件夹eg:zip.setIncludes("*.java");//zip.setExcludes(...); 排除哪些文件或文件夹
		zip.setDestFile(new File(dest));
		Project p = new Project();
		p.setBaseDir(new File(src));
		zip.setProject(p);
		zip.execute();
	}
	/**
	 * 压缩单个文件
	 * 输入XLS路径+文件名filepath，生成ZIP的路径及文件名zippath
	 * 输出String，找不到文件返回null，找的到就返回压缩文件信息
	 * */
	public static String ZipFile(String filepath ,String zippath) {
		File file;
		String msg=null;
		File zipFile;
		ZipOutputStream zipOut = null;
		InputStream input=null;
		try {
	        file = new File(filepath);
	        zipFile = new File(zippath);
	        input = new FileInputStream(file);
	        zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
	        zipOut.putNextEntry(new ZipEntry(file.getName()));
	        int temp = 0;
	        
	        while((temp = input.read()) != -1){
	            zipOut.write(temp);
	        }
	       
	        msg=zippath;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 try {
				zipOut.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
	/**
	 * 解压zip包
	 * */
	public static List<String> unzip(String zipFilePath, String destDir)
	  {
	    System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding")); //防止文件名中有中文时出错
	    System.setProperty("file.encoding", System.getProperty("sun.jnu.encoding")); //防止文件名中有中文时出错
//	    System.out.println(System.getProperty("sun.zip.encoding")); //ZIP编码方式
//	    System.out.println(System.getProperty("sun.jnu.encoding")); //当前文件编码方式
//	    System.out.println(System.getProperty("file.encoding")); //这个是当前文件内容编码方式
	    List<String> fileList=new ArrayList<String>();
	    File dir = new File(destDir);
	    if (!dir.exists()) dir.mkdirs();
	    FileInputStream fis;
	    byte[] buffer = new byte[1024];
	    try
	    {
	      fis = new FileInputStream(zipFilePath);
	      ZipInputStream zis = new ZipInputStream(fis,Charset.forName("gbk"));  
	      ZipEntry ze = zis.getNextEntry();
	      while (ze != null)
	      {
	        String fileName = ze.getName();
	        File newFile = new File(destDir + File.separator + fileName);
	        new File(newFile.getParent()).mkdirs();
	        FileOutputStream fos = new FileOutputStream(newFile);
	        int len;
	        while ((len = zis.read(buffer)) > 0)
	        {
	          fos.write(buffer, 0, len);
	        }
	        fileList.add(fileName);
	        fos.close();
	        zis.closeEntry();
	        ze = zis.getNextEntry();
	      }
	      zis.closeEntry();
	      zis.close();
	      fis.close();
	    }
	    catch (IOException e)
	    {
	      new Exception("解压出现问题："+e);
	      
	    }
	    return fileList;
	  }
	
	public static List<File> unzip(InputStream zipInputStream, File destDir)
	{
		List<File> fileList=new ArrayList<File>();
		ZipInputStream zis =null;
		FileOutputStream fos =null;
		byte[] buffer = new byte[1024];
		try
		{
			zis = new ZipInputStream(zipInputStream,Charset.forName("gbk"));  
			ZipEntry ze = zis.getNextEntry();
			while (ze != null)
			{
				String fileName = ze.getName();
				File newFile = new File(destDir,fileName);
				fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0)
				{
					fos.write(buffer, 0, len);
				}
				fileList.add(newFile);
				fos.close();
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
		}catch (IOException e){
			throw new RuntimeException("解压出现问题："+e.getMessage());
		}finally{
			try {
				if(zis!=null){
					zis.closeEntry();
					zis.close();
				}
				if(zis!=null)
					zipInputStream.close();
			} catch (IOException e) {
			}
		}
		
		return fileList;
	}
	public static void main(String[] args) {
		unzip("E:/crms/zipdatafile/xxx.zip","E:\\crms\\excel\\");
		//zip("E:\\crmserrorfile\\台账.zip","E:\\crmserrorfile");
	}
}
