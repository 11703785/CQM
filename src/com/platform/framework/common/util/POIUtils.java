package com.platform.framework.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellReference;
import org.pentaho.di.core.spreadsheet.KCell;
import org.pentaho.di.core.spreadsheet.KSheet;
import org.pentaho.di.core.spreadsheet.KWorkbook;
import org.pentaho.di.trans.steps.excelinput.ExcelInputField;
import org.pentaho.di.trans.steps.excelinput.SpreadSheetType;
import org.pentaho.di.trans.steps.excelinput.WorkbookFactory;

public class POIUtils {

	private static final Logger logger = Logger.getLogger(POIUtils.class);

	/**
	  * 根据EXCEL文件、sheet名称、行号获取某行不为空的值
	  * @param inputFile
	  * @param sheetName
	  * @param row
	  * @param encoding 编码
	  * @return
	  */
	public static List queryHeaderDatas(File inputFile,String sheetName,List<int[]> zbList,int row,String encoding) {
		
		InputStream ins=null;
		KWorkbook kw=null;
		ExcelInputField[] fields=new ExcelInputField[]{};
		ArrayList lists=new ArrayList();
		try {
			SpreadSheetType fileType=SpreadSheetType.JXL;//默认2003excel
			if(inputFile.getPath().endsWith("xlsx"))//2007excle
				fileType=SpreadSheetType.SAX_POI;
			
			ins= new FileInputStream(inputFile);
			kw=WorkbookFactory.getWorkbook(fileType, ins, encoding);
			
			KSheet s=kw.getSheet(sheetName);//设置sheet名称
			boolean zt=false;
			for(int j=row;j<s.getRows();j++){
				ArrayList<ExcelInputField> rowlists=new ArrayList<ExcelInputField>();
				 for(int k=0;k<zbList.size();k++){
					int[] zb=zbList.get(k);
					KCell c=s.getCell(zb[1], j);	//getCell(x,y)
					ExcelInputField field=new ExcelInputField();
					System.out.println(sheetName+"x:"+zb[1]+"y:"+j+" #"+c.getContents()+"#");
					if(k==0&&(c.getContents()==null||"".equals(c.getContents()))){
						zt=true;
						break;
					}else{
						field.setName(c.getContents());
						rowlists.add(field);
					}
				 }
				 
				 if(zt){
					 break;
				 }else{
					 lists.add(rowlists.toArray(fields));
					 
				 }
			}
			
		} catch (Exception e) {
			logger.info("获取EXCEL某行数据失败："+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(ins!=null)
					ins.close();
				if(kw!=null)
					kw.close();
			} catch (Exception e) {
				logger.info("关闭EXCEL文件流失败："+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return lists;
	}
	public static KWorkbook getWorkbook(String inputFile,String encoding) {
		
		InputStream ins=null;
		KWorkbook kw=null;
		try {
			SpreadSheetType fileType=SpreadSheetType.JXL;//默认2003excel
			if(inputFile.endsWith("xlsx"))//2007excle
				fileType=SpreadSheetType.SAX_POI;
			
			ins= new FileInputStream(inputFile);
			kw=WorkbookFactory.getWorkbook(fileType, ins, encoding);

			
		} catch (Exception e) {
			logger.info("加载EXCEL数据失败："+e.getMessage());
			throw new RuntimeException("加载EXCEL数据失败："+e.getMessage());
		}finally{
			try {
				if(ins!=null)
					ins.close();
			} catch (Exception e) {
				logger.info("关闭EXCEL文件流失败："+e.getMessage());
				throw new RuntimeException("关闭EXCEL文件流失败："+e.getMessage());
			}
		}
		
		return kw;
	}
	public static boolean TemplateMatching(File inputFile,String sheetName,List<String[]> cellList,String riziName){
		boolean status=false;
		InputStream ins=null;
		KWorkbook kw=null;
		StringBuffer newstr =new StringBuffer();
		try {
			SpreadSheetType fileType=SpreadSheetType.JXL;//默认2003excel
			if(inputFile.getPath().endsWith("xlsx"))//2007excle
				fileType=SpreadSheetType.SAX_POI;
			
			ins= new FileInputStream(inputFile);
			kw=WorkbookFactory.getWorkbook(fileType, ins, "GBK");
			
			KSheet s=kw.getSheet(sheetName);//设置sheet名称
			if(s!=null){
				for(int k=0;k<cellList.size();k++){
					String[] zb=cellList.get(k);
					KCell c=s.getCell(Integer.parseInt(zb[1]), Integer.parseInt(zb[0])-1);
					String exCellNam=c.getContents().trim();
					String dataCellNam=zb[2].trim();
					if(exCellNam.equals(dataCellNam)){
						status=true;
					}else{
						status=false;
						newstr.append("在Excel模板 "+sheetName+" 页中 ["+zb[3]+"] 坐标取值【"+exCellNam+"】与页面填入的【"+zb[2]+"】不匹配！\r\n");
					}
				 }
			}else{
				newstr.append("Sheet页:"+sheetName+"不存在！");
			}
			if(!"".equals(newstr.toString())){
				status=false;
				writeTxtFile(newstr,riziName);
			}
		} catch (Exception e) {
			logger.info("获取EXCEL某行数据失败："+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(ins!=null)
					ins.close();
				if(kw!=null)
					kw.close();
			} catch (Exception e) {
				logger.info("关闭EXCEL文件流失败："+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return status;
	}
	/** 
	* 创建文件 
	* 
	* @throws IOException 
	*/ 
	public static String creatTxtFile(String name) throws IOException { 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd-HHmmss");
		String filenameTemp =name+sdf.format(new Date())+".txt"; 
		File filename = new File(filenameTemp); 
		if (!filename.exists()) { 
			filename.createNewFile(); 
		} 
		return filenameTemp; 
	} 

	/** 
	* 写错误信息文件 
	* 
	* @param newStr 
	*            新内容 
	* @throws IOException 
	*/ 
	public static boolean writeTxtFile (StringBuffer newStr,String filenameTemp) throws IOException { 
	// 先读取原有文件内容，然后进行写入操作 
		boolean flag = false; 
		String filein = newStr + "\r\n"; 
		String temp = ""; 

		FileInputStream fis = null; 
		InputStreamReader isr = null; 
		BufferedReader br = null; 

		FileOutputStream fos = null; 
		PrintWriter pw = null; 
		try { 
				// 文件路径 
				File file = new File(filenameTemp); 
				// 将文件读入输入流 
				fis = new FileInputStream(file); 
				isr = new InputStreamReader(fis); 
				br = new BufferedReader(isr); 
				StringBuffer buf = new StringBuffer(); 

				// 保存该文件原有的内容 
				for (int j = 1; (temp = br.readLine()) != null; j++) { 
					buf = buf.append(temp); 
					// System.getProperty("line.separator") 
					// 行与行之间的分隔符 相当于“\n” 
					buf = buf.append(System.getProperty("line.separator")); 
				} 
				buf.append(filein); 

				fos = new FileOutputStream(file); 
				pw = new PrintWriter(fos); 
				pw.write(buf.toString().toCharArray()); 
				pw.flush(); 
				flag = true; 
		} catch (IOException e1) { 
	// TODO 自动生成 catch 块 
			throw e1; 
		} finally { 
			if (pw != null) { 
		pw.close(); 
			} 
			if (fos != null) { 
				fos.close(); 
			} 
			if (br != null) { 
				br.close(); 
			} 
			if (isr != null) { 
				isr.close(); 
			} 
			if (fis != null) { 
				fis.close(); 
			} 
		} 
		return flag; 
	} 
	public static void deleteTxt(String path){
		new File(path).delete();
	}

	public static ExcelInputField[] queryExcelDatas(File inputFile,String sheetName,int row,String encoding) {
		
			InputStream ins=null;
			KWorkbook kw=null;
			ExcelInputField[] fields=new ExcelInputField[]{};
			ArrayList<ExcelInputField> lists=new ArrayList<ExcelInputField>();
			try {
				SpreadSheetType fileType=SpreadSheetType.JXL;//默认2003excel
				if(inputFile.getPath().endsWith("xlsx"))//2007excle
					fileType=SpreadSheetType.SAX_POI;
				
				ins= new FileInputStream(inputFile);
				kw=WorkbookFactory.getWorkbook(fileType, ins, encoding);
				
				KSheet s=kw.getSheet(sheetName);//设置sheet名称
				KCell[] kc=s.getRow(row);//设置获取哪行数据
				
				for(KCell c:kc){
					if(c!=null && c.getContents().length()>0){
						//System.out.println("-----"+c.getContents());
						ExcelInputField field=new ExcelInputField();
						
						field.setName(c.getContents());//设置获取字段的名称
						field.setLength(-1);
						field.setPrecision(-1);
						field.setType(2);//字段类型为字符串
						//if(c.getContents().contains("金额")||c.getContents().contains("余额")||c.getContents().contains("利率")){
						//if(c.getContents().contains("金额")||c.getContents().contains("余额")||c.getContents().contains("利率")||c.getContents().contains("浮动值")){
							//field.setFormat("#.##");
						//}else{
						//	field.setFormat("#");
						//}
						//根据每个行的台账格式做相应的设置（建行、工行、农发不需要格式化）
						//if(c.getContents().contains("日期")||c.getContents().contains("起始日")||c.getContents().contains("到期日")||c.getContents().contains("起期")||c.getContents().contains("止期"))
						
							//field.setFormat("yyyy-MM-dd");
						
						lists.add(field);
					}
				}
			} catch (Exception e) {
				logger.info("获取EXCEL某行数据失败："+e.getMessage());
				e.printStackTrace();
			}finally{
				try {
					if(ins!=null)
						ins.close();
					if(kw!=null)
						kw.close();
				} catch (Exception e) {
					logger.info("关闭EXCEL文件流失败："+e.getMessage());
					e.printStackTrace();
				}
			}
			
			return lists.toArray(fields);
		}
	
	 /**
	  * 根据EXCEL文件、sheet名称、行号获取某行不为空的值
	  * @param inputFile
	  * @param sheetName
	  * @param row
	  * @param encoding 编码
	  * @return
	  */
	public static ExcelInputField[] queryHeaderDatas(File inputFile,String sheetName,int row,String encoding) {
		
		InputStream ins=null;
		KWorkbook kw=null;
		ExcelInputField[] fields=new ExcelInputField[]{};
		ArrayList<ExcelInputField> lists=new ArrayList<ExcelInputField>();
		try {
			SpreadSheetType fileType=SpreadSheetType.JXL;//默认2003excel
			if(inputFile.getPath().endsWith("xlsx"))//2007excle
				fileType=SpreadSheetType.SAX_POI;
			
			ins= new FileInputStream(inputFile);
			kw=WorkbookFactory.getWorkbook(fileType, ins, encoding);
			
			KSheet s=kw.getSheet(sheetName);//设置sheet名称
			KCell[] kc=s.getRow(row);//设置获取哪行数据
			
			for(KCell c:kc){
				if(c!=null && c.getContents().length()>0){
					//System.out.println("-----"+c.getContents());
					ExcelInputField field=new ExcelInputField();
					
					field.setName(c.getContents());//设置获取字段的名称
					field.setLength(-1);
					field.setPrecision(-1);
					field.setType(2);//字段类型为字符串
					//if(c.getContents().contains("金额")||c.getContents().contains("余额")||c.getContents().contains("利率")){
					if(c.getContents().contains("金额")||c.getContents().contains("余额")||c.getContents().contains("利率")||c.getContents().contains("浮动值")){
						//field.setFormat("#.##");
					}else{
						field.setFormat("#");
					}
					//根据每个行的台账格式做相应的设置（建行、工行、农发不需要格式化）
					if(c.getContents().contains("日期")||c.getContents().contains("起始日")||c.getContents().contains("到期日")||c.getContents().contains("起期")||c.getContents().contains("止期"))
					
						field.setFormat("yyyy-MM-dd");
					
					lists.add(field);
				}
			}
		} catch (Exception e) {
			logger.info("获取EXCEL某行数据失败："+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(ins!=null)
					ins.close();
				if(kw!=null)
					kw.close();
			} catch (Exception e) {
				logger.info("关闭EXCEL文件流失败："+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return lists.toArray(fields);
	}
	public static int getStartRow(String axis) {
		CellReference cell = new CellReference(axis);

		int r=cell.getRow();
		r++;
		return r;
	}
	public static short getStartCol(String axis) {
		CellReference cell = new CellReference(axis);
	
		short col=cell.getCol();
		return col;
	}
	public static String getAxis(int row, short cell) {
		CellReference cellRef = new CellReference(row, cell);
		
		String key = cellRef.formatAsString();
		return key;
	}
	public static String getCellAxis(int row, int cell) {
		CellReference cellRef = new CellReference(row, cell);
		short col=cellRef.getCol();
		String key = cellRef.convertNumToColString(col);
		return key;
	}
	public static String getCellAxi(String axis) {
		
		CellReference cellRef = new CellReference(axis);
		short col=cellRef.getCol();
		String key = cellRef.convertNumToColString(col);
		return key;
	}
	}
