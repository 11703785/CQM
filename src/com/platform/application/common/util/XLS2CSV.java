/* ====================================================================
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements. See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==================================================================== */
package com.platform.application.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.common.util.POIUtils;
import com.platform.framework.common.util.UUID;

/**
 * XLS -> CSV 
 * @author 
 */
public class XLS2CSV implements HSSFListener {
    private int minColumns;
    private POIFSFileSystem fs;
    private PrintStream output;

    private int lastRowNumber;
    private int lastColumnNumber;

    /** 我们应该输出公式，还是它的值？ */
    private boolean outputFormulaValues = true;

    /** For parsing Formulas */
    private SheetRecordCollectingListener workbookBuildingListener;
    private HSSFWorkbook stubWorkbook;

    // Records we pick up as we process
    private SSTRecord sstRecord;
    private FormatTrackingHSSFListener formatListener;

    /** So we known which sheet we're on */
    private int sheetIndex = -1;
    private BoundSheetRecord[] orderedBSRs;
    private ArrayList boundSheetRecords = new ArrayList();

    // For handling formulas with string results
    private int nextRow;
    private int nextColumn;
    private boolean outputNextStringRecord;
    
    private final String OUTPUT_CHARSET = "UTF-8";
    private String sheetName="";//定义模板的sheet名称
    private String curSheetName="";//当前sheet名称
    private ArrayList<String> cellAxis=new ArrayList<String>();//定义模板的列
    private StringBuffer rowRecords=null;//某行的csv数据
    private String records="";//记录某行是否有数据
    private int startRow=0;//起始行
    private String annual="NUll";//采集数据所属频度
    private String isMain="";//是否户主表
    private String city="";//所属省/市
    private String userId="";//数据录入人
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param fs
     *            The POIFSFileSystem to process
     * @param output
     *            The PrintStream to output the CSV to
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     */
    public XLS2CSV(POIFSFileSystem fs, PrintStream output, int minColumns) {
        this.fs = fs;
        this.output = output;
        this.minColumns = minColumns;
    }
    
    public XLS2CSV(String inputFilePath, String outputFilePath) throws Exception {
        fs = new POIFSFileSystem(new FileInputStream(inputFilePath));
        output = new PrintStream(outputFilePath, OUTPUT_CHARSET);
        minColumns = -1;
    }
    public XLS2CSV(String inputFilePath, File outputFilePath,LinkedHashMap<String,Object> infos,String annual,String city, Person person) throws Exception {
    	fs = new POIFSFileSystem(new FileInputStream(inputFilePath));
    	output = new PrintStream(outputFilePath, OUTPUT_CHARSET);
    	minColumns = -1;
    	if(infos!=null){
    		this.sheetName=(String)infos.get("sheetName");//模板sheet名称
    		this.cellAxis=(ArrayList<String>) infos.get("cellAxis");//模板定义列
    		
    		this.isMain=(String) infos.get("isMain");//是否户主表
    		this.city=city;//所属省/市
    		
    		//起始行
    		Object sr= infos.get("startRow");

    		if(sr!=null)
    			this.startRow=Integer.valueOf(String.valueOf(sr));
    	}
    	this.annual=annual;
    	if(person!=null)
			this.userId=person.getPersonId();
    }

    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param filename
     *            The file to process
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     * @throws IOException
     * @throws FileNotFoundException
     */
    public XLS2CSV(String filename, int minColumns) throws IOException,
            FileNotFoundException {
        this(new POIFSFileSystem(new FileInputStream(filename)), System.out,
                minColumns);
    }

    /**
     * 启动的xls文件格式处理 为 CSV
     */
    public void process() throws IOException {
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
                this);
        formatListener = new FormatTrackingHSSFListener(listener);

        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();

        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new SheetRecordCollectingListener(
                    formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }

        factory.processWorkbookEvents(request, fs);
        close();
    }

    /**
     * 主要监听方法，处理事件，并输出格式为CSV
     * file is processed.
     */
    public void processRecord(Record record) {
    	int thisRow = -1;
    	int thisColumn = -1;
    	String thisStr = null;
    	
    	switch (record.getSid()) {
    	case BoundSheetRecord.sid:
    		
    		BoundSheetRecord sheet=(BoundSheetRecord) record;
    		boundSheetRecords.add(record);
    		break;
    	case BOFRecord.sid:
    		BOFRecord br = (BOFRecord) record;
    		if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
    			// Create sub workbook if required
    			if (workbookBuildingListener != null && stubWorkbook == null) {
    				stubWorkbook = workbookBuildingListener
    				.getStubHSSFWorkbook();
    			}
    			
    			// Output the worksheet name
    			// Works by ordering the BSRs by the location of
    			// their BOFRecords, and then knowing that we
    			// process BOFRecords in byte offset order
    			sheetIndex++;
    			if (orderedBSRs == null) {
    				orderedBSRs = BoundSheetRecord
    				.orderByBofPosition(boundSheetRecords);
    			}
    			
    			curSheetName=orderedBSRs[sheetIndex].getSheetname();
    			//output.println();
    			//output.println(orderedBSRs[sheetIndex].getSheetname() + " ["
    			//+ (sheetIndex + 1) + "]:");
    		}
    		break;
    		
    	case SSTRecord.sid:
    		sstRecord = (SSTRecord) record;
    		break;
    		
    	case BlankRecord.sid:
    		BlankRecord brec = (BlankRecord) record;
    		thisRow = brec.getRow();
    		thisColumn = brec.getColumn();
    		thisStr = "";
    		break;
    	case BoolErrRecord.sid:
    		BoolErrRecord berec = (BoolErrRecord) record;
    		
    		thisRow = berec.getRow();
    		thisColumn = berec.getColumn();
    		thisStr = "";
    		break;
    		
    	case FormulaRecord.sid:
    		FormulaRecord frec = (FormulaRecord) record;
    		
    		thisRow = frec.getRow();
    		thisColumn = frec.getColumn();
    		
    		if (outputFormulaValues) {
    			if (Double.isNaN(frec.getValue())) {
    				// Formula result is a string
    				// This is stored in the next record
    				outputNextStringRecord = true;
    				nextRow = frec.getRow();
    				nextColumn = frec.getColumn();
    			} else {
    				thisStr ='"' + formatListener.formatNumberDateCell(frec) + '"';
    			}
    		} else {
    			thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook,
    					frec.getParsedExpression()) + '"';
    		}
    		break;
    	case StringRecord.sid:
    		if (outputNextStringRecord) {
    			// String for formula
    			StringRecord srec = (StringRecord) record;
    			thisStr = '"' +srec.getString() + '"';
    			thisRow = nextRow;
    			thisColumn = nextColumn;
    			outputNextStringRecord = false;
    		}
    		break;
    		
    	case LabelRecord.sid:
    		LabelRecord lrec = (LabelRecord) record;
    		
    		thisRow = lrec.getRow();
    		thisColumn = lrec.getColumn();
    		thisStr = '"' + lrec.getValue() + '"';
    		break;
    	case LabelSSTRecord.sid:
    		LabelSSTRecord lsrec = (LabelSSTRecord) record;
    		
    		thisRow = lsrec.getRow();
    		thisColumn = lsrec.getColumn();
    		if (sstRecord == null) {
    			thisStr = '"' + "(No SST Record, can't identify string)" + '"';
    		} else {
    			thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex())
    			.toString() + '"';
    		}
    		break;
    	case NoteRecord.sid:
    		NoteRecord nrec = (NoteRecord) record;
    		
    		thisRow = nrec.getRow();
    		thisColumn = nrec.getColumn();
    		// TODO: Find object to match nrec.getShapeId()
    		thisStr = '"' + "(TODO)" + '"';
    		break;
    	case NumberRecord.sid:
    		NumberRecord numrec = (NumberRecord) record;
    		
    		thisRow = numrec.getRow();
    		thisColumn = numrec.getColumn();
    		
    		// 格式化
    		thisStr ='"' + formatListener.formatNumberDateCell(numrec)+ '"';
    		break;
    	case RKRecord.sid:
    		RKRecord rkrec = (RKRecord) record;
    		
    		thisRow = rkrec.getRow();
    		thisColumn = rkrec.getColumn();
    		thisStr = '"' + "(TODO)" + '"';
    		break;
    	default:
    		break;
    	}
    	
    	if(curSheetName.equals(sheetName)){
    		
    		// 如果是新行开始，初始化起始列的值
    		if (thisRow != -1 && thisRow != lastRowNumber) {
    			lastColumnNumber = -1;
    		}
    		
    		// 处理空值
    		if (record instanceof MissingCellDummyRecord) {
    			MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
    			thisRow = mc.getRow();
    			thisColumn = mc.getColumn();
    			thisStr = "";
    		}
    		
    		String cellaxis=POIUtils.getCellAxis(thisRow, thisColumn);
    		if(cellAxis.contains(cellaxis)){
    			//System.out.println(cellaxis+"："+thisStr);
    			
            	//存储某行的有效信息，判断本行是否有效
            	if(thisStr.length()>0)
            		records+=thisStr;
    			
            	//存储某行的csv内容
            	if(thisStr.length()==0)
            		thisStr="NULL";
            	if(rowRecords==null){
            		rowRecords=new StringBuffer();
            		if(this.isMain.equals("1")){
            			String[] cy=city.split("/");
            			thisStr="\""+this.annual+"\",\""+sdf.format(new Date())+"\",\""+cy[0]+"\",\""+cy[1]+"\","+thisStr;
            		}else if(this.isMain.equals("0")){
            			String id=UUID.randomUUID().toString();
            			thisStr="\""+id.replace("-", "")+"\",\""+this.annual+"\",\""+sdf.format(new Date())+"\","+thisStr;
            		}
            		rowRecords.append(thisStr);
            	}else{
            		rowRecords.append(","+thisStr);
            	}
    			
    			
    			
    			
    			
//    			if (thisStr != null) {
//    				if (thisColumn > 0) {
//    					output.print(',');
//    				}
//    				output.print(thisStr);
//    			}
    			
    			// 更新列和行计数
    			if (thisRow > -1)
    				lastRowNumber = thisRow;
    			if (thisColumn > -1)
    				lastColumnNumber = thisColumn;
    		}else{
    			
    			// 行结束列
    			if (record instanceof LastCellOfRowDummyRecord) {
    				LastCellOfRowDummyRecord r=(LastCellOfRowDummyRecord) record;
    				// 打印出任何空值丢失的逗号
    				if (minColumns > 0) {
    					// 列重新开始
    					if (lastColumnNumber == -1) {
    						lastColumnNumber = 0;
    					}
    					for (int i = lastColumnNumber; i < (minColumns); i++) {
    						output.print(',');
    					}
    				}
    				
    				// 新行开始
    				lastColumnNumber = -1;
    				
    				// 结束行
    	            if(r.getRow()>=startRow && records.length()>0){
    	            	//System.out.println(rowRecords.toString());
    	            	
    	            	//每行结束时加入默认录入人
    	            	rowRecords.append(",\"");
    	            	rowRecords.append(userId);
    	            	rowRecords.append("\",\"0\"");//默认校验状态：0通过；1不通过
    	            	
    	            	output.print(rowRecords.toString());
    	            	output.println();
    	            	
    	            }
    	            records="";
    	            rowRecords=null;
    			}
    			
    		}
    	}

    }

    public void close(){
    	this.output.close();
    }
    public static void main(String[] args) throws Exception {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	System.out.println(sdf.format(new Date()));
        XLS2CSV xls2csv = new XLS2CSV("D:\\基本信息表.xls","D:\\out.csv");
        xls2csv.process();
        System.out.println(sdf.format(new Date()));
    }
}