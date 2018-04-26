package com.platform.application.common.util;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

public class CallInterface {
	
	/**
	 * 调用精准扶贫上报接口方法
	 * FF
	 * 传入 文件路径，地区编码
	 * */
	public static String CallInt(String filepath,String areacode){
		String status="连接精准扶贫上报系统失败";
		String url = PropertyUtil.getPropertyByKey("JKSC_URL");		//上报数据接口URL
		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"----------ThIs_Is_tHe_bouNdaRY_$", Charset.defaultCharset());
		try {
			multipartEntity.addPart("deptcode",new StringBody(areacode, Charset.forName("UTF-8")));  
			multipartEntity.addPart("zipfile",new FileBody(new File(filepath),"application/zip"));
		  
			HttpPost request = new HttpPost(url);   
			request.setEntity(multipartEntity);
			request.addHeader("Content-Type","multipart/form-data; boundary=----------ThIs_Is_tHe_bouNdaRY_$");
		
			HttpResponse response;
			HttpClient httpClient =new com.platform.application.common.util.SSLClient();
			response = httpClient.execute(request);
			System.out.println("返回网络标识："+response.getStatusLine().getStatusCode());//返回200代表连接成功
			if(200==response.getStatusLine().getStatusCode()){
				status="SUCCESS";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * 调用接口查询入库结果
	 * input:机构编码，地区编码，期数，表代号，入库时间，版本号
	 * output:入库状态(result)：1 成功  0失败，地区编码(areaId)，金融机构编码(orgId)，期数(fiscalTerm)，表代号(template)，版本号(version)，时间(addTime)（成功可以算入库时间，失败的时候就是执行时间）
	 * {result:1,areaId:450400,orgId:C1010345000016,fiscalTerm:201609,template:PPLI,version:2,addTime:2016-10-14 00:02:07.0}
	 * */
	public static String SelDataStatus(String areaCode,String deptCode,String qitm,String tableCode,String rkdate,String version){
		String json="";
		String url = PropertyUtil.getPropertyByKey("JKCX_URL");		//查询结果接口URL
		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"----------ThIs_Is_tHe_bouNdaRY_$", Charset.defaultCharset());
		try {
			multipartEntity.addPart("areaCode",new StringBody(areaCode, Charset.forName("UTF-8")));  
			multipartEntity.addPart("deptCode",new StringBody(deptCode, Charset.forName("UTF-8")));
			multipartEntity.addPart("qitm",new StringBody(qitm, Charset.forName("UTF-8")));
			multipartEntity.addPart("tableCode",new StringBody(tableCode, Charset.forName("UTF-8")));
			//multipartEntity.addPart("areaCode",new StringBody(areaCode, Charset.forName("UTF-8")));
			HttpPost request = new HttpPost(url);   
			request.setEntity(multipartEntity);
		
			HttpResponse response;
			HttpClient httpClient =new com.platform.application.common.util.SSLClient();
			response = httpClient.execute(request);
			System.out.println("返回网络标识："+response.getStatusLine().getStatusCode());//返回200代表连接成功
			if(200==response.getStatusLine().getStatusCode()){
				json=EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	public static void main(String[] args) {
		SelDataStatus("450400","C1010345000016","201609","IPLI","","");
	}
}
