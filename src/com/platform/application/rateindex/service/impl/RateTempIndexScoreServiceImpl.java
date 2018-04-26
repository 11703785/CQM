package com.platform.application.rateindex.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Service;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.rateindex.domain.RateTempIndex;
import com.platform.application.rateindex.domain.RateTempIndexScore;
import com.platform.application.rateindex.domain.RateTemplate;
import com.platform.application.rateindex.service.RateTempIndexScoreService;
import com.platform.application.rateindex.service.RateTempIndexService;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;


@Service
public class RateTempIndexScoreServiceImpl extends GenericHibernateDao<RateTempIndexScore, String> implements RateTempIndexScoreService {
	
	RateTempIndexService rateTempIndexService;
	
	@Autowired
	public void setRateTempIndexService(RateTempIndexService rateTempIndexService) {
		this.rateTempIndexService = rateTempIndexService;
	}
	
	public String loadRateIndexOptions(String ids, Person person, Page page)throws Exception {
		List paras = new ArrayList(2);
		String[] strs = ids.split(";");
		
		List data = new  ArrayList();
		List tempindexList = this.getTempIndex(strs[0],strs[1]);
		if(tempindexList.size()>0){
			RateTempIndex tempindex = (RateTempIndex)tempindexList.get(0);
			
			String sql = "select temps from RateTempIndexScore temps where temps.temp.id='"+strs[0]+"' and temps.indexId='"+strs[1]+"' order by temps.standOrder asc";
			List list = this.find(sql, paras, page);
			
			for(int i=0; i<list.size(); i++){
				RateTempIndexScore temp = (RateTempIndexScore)list.get(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", temp.getId());
				if("0".equals(tempindex.getScoreType())){
					map.put("standard", temp.getStandName());
				}else{
					String standFormule = temp.getStandFormule();
					map.put("standard", standFormule.replace("@", "指标值"));
				}
				map.put("score", String.valueOf(temp.getScore()));
				
				data.add(map);
			}
		}
		
		Map<String, String> map = new HashMap();
		map.put("totalProperty", "recordsFiltered," + page.getTotalCount());
		map.put("root", "data");
		return JsonUtil.fromMapLists(data,map);
	}
	public List<RateTempIndexScore> findRateIndexOptions(RateTempIndexScore rateTempIndexScore) throws Exception{
		List<RateTempIndexScore> indexOptions = new ArrayList<RateTempIndexScore>();
		String sql = "from RateIndexOptions where id='"+rateTempIndexScore.getId()+"'";
		indexOptions = this.find(sql);
		return indexOptions;
	}
	public void txDelete(RateTempIndexScore rateTempIndexScore) throws Exception {
		this.delete(rateTempIndexScore);
	}
	
	public void txSaveRateIndexOptions(Person person, HttpServletRequest request) throws Exception{
		String ids = request.getParameter("ids");
		String tempid = ids.split(";")[0];	//模板id
		
		RateTemplate rateTemp = new RateTemplate();
		rateTemp.setId(tempid);
		
		String indexid = ids.split(";")[1];	//指标id
		String scoretype = request.getParameter("scoretype");	//评分表准类型
		String dicType = request.getParameter("dicType");		//字典类型
		String filedType = request.getParameter("filedType");	//范围类型
		
		String numStr = request.getParameter("num");
		int num = StringUtil.isNotBlank(numStr)?Integer.valueOf(numStr):65;
		String temp = "A";
		
		if("0".equals(scoretype)){//直等评分
			for(int x=65;x<num;x++){
				temp = String.valueOf((char)x);
				String name = request.getParameter(temp+"0");
				String score = request.getParameter(temp+"1");
				String standOrder = request.getParameter(temp+"2");
				String standCode = request.getParameter(temp+"3");
				
				RateTempIndexScore tmp = new RateTempIndexScore();
				tmp.setTemp(rateTemp);
				tmp.setIndexId(indexid);
				tmp.setStandName(name);
				tmp.setStandCode(standCode);
				tmp.setStandOrder(Integer.parseInt(standOrder));
				tmp.setScore(Double.valueOf(score));
				
				this.save(tmp);//保存评分标准
			}
		}else{
			for(int x=65;x<num;x++){
				temp = String.valueOf((char)x);
				String filedTypes = request.getParameter(temp);
				
				if("0".equals(filedTypes)){//双值
					String min = request.getParameter(temp+"0");
					String f1 = this.getSymbols(request.getParameter(temp+"1"));//根据值，获取计算公式：<、<=、=、>、>=等
					String f2 = this.getSymbols(request.getParameter(temp+"2"));//根据值，获取计算公式：<、<=、=、>、>=等
					String max = request.getParameter(temp+"3");
					String score = request.getParameter(temp+"4");
					String standOrder = request.getParameter(temp+"5");
					
					RateTempIndexScore tmp = new RateTempIndexScore();
					tmp.setTemp(rateTemp);
					tmp.setStandOrder(Integer.parseInt(standOrder));
					tmp.setIndexId(indexid);
					tmp.setStandFormule(min+f1+"@"+f2+max);
					tmp.setScore(Double.valueOf(score));
					
					this.save(tmp);
				}else{//单值
					String min = request.getParameter(temp+"0");
					String f1 = this.getSymbols(request.getParameter(temp+"1"));//根据值，获取计算公式：<、<=、=、>、>=等
					String score = request.getParameter(temp+"4");
					String standOrder = request.getParameter(temp+"5");
					RateTempIndexScore tmp = new RateTempIndexScore();
					tmp.setTemp(rateTemp);
					tmp.setStandOrder(Integer.parseInt(standOrder));
					tmp.setIndexId(indexid);
					tmp.setStandFormule(min+f1+"@");
					tmp.setScore(Double.valueOf(score));
					
					this.save(tmp);
				}
			}
		}
		
		List tempindexList = this.getTempIndex(tempid,indexid);
		if(tempindexList.size()>0){
			RateTempIndex tempindex = (RateTempIndex)tempindexList.get(0);
			tempindex.setScoreType(scoretype);
			
			rateTempIndexService.save(tempindex);//保存评分类型
		}
	}

	private String getSymbols(String str) {
		String result = "";
		if("symbols1".equals(str)){
			result = "<";
		}else if("symbols2".equals(str)){
			result = "<=";
		}else if("symbols3".equals(str)){
			result = "=";
		}else if("symbols4".equals(str)){
			result = ">";
		}else if("symbols5".equals(str)){
			result = ">=";
		}
		return result;
	}

	private List getTempIndex(String tempid, String indexid) throws Exception{
		String sql = "select t from RateTempIndex t where t.temp.id='"+tempid+"' and t.indexId='"+indexid+"'";
		return this.find(sql);
	}

	public String getRateSearchJson(Page page) throws Exception {
		List para = new  ArrayList();
		List leList = this.getRateSearch(page);
	 if(leList.size()>0){
		 for (int i = 0; i < leList.size(); i++) {
			 Object[] strs = (Object[]) leList.get(i);
			 HashMap<String, String> taskmap=new HashMap<String, String>();
			 taskmap.put("mz", strs[1].toString());
			 taskmap.put("xb",strs[2].toString());
			 taskmap.put("hzxm",strs[3].toString());
			 taskmap.put("id",strs[0].toString());
			 para.add(taskmap);
		 }
	 }
	    Map<String,String> map = new HashMap<String,String>();
	    map.put("totalProperty", "recordsFiltered," + leList.size());
		map.put("root", "data");
		return JsonUtil.fromMapLists(para,map);
	}
	private List getRateSearch( Page page) {
		List paras = new ArrayList();
		String sql = "select id,mz,xb,hzxm from tb_hzxx";
		paras = findSQL(sql);
		return paras; 
		
	}

	public List getAllDicType() throws Exception {
		String sql = "select dicType from DicType dicType where 1=1";
		return this.find(sql);
	}
	
	public String getDictionaryByType(String typeid) throws Exception {
		
		String str = " ";
		String sql = "select dic from Dictionary dic where dic.dicType.id='"+typeid+"'";
		List list = this.find(sql);
		
		int num = 65;
		for(int i=0; i<list.size(); i++){
			Dictionary dic = (Dictionary)list.get(i);
			str += "<tr><td align=\"center\"><input name='"+(char)(num+i)+"2' class='form-control' style='width:50px;height:32px' readonly value='"+dic.getOrderNum()+"'/><input name='"+(char)(num+i)+"3' type='hidden' value='"+dic.getCode()+"'/></td>"+
					"<td align=\"center\"><input name='"+(char)(num+i)+"0' class='form-control' style='width:150px;height:32px' readonly value='"+dic.getName()+"'/></td>"+
					"<td align=\"center\"><input name='"+(char)(num+i)+"1' placeholder='请输入数字....' class='form-control' onblur='checkVal(this)' style='width:150px;height:32px'/><span class='regerror' style='color:red'></span></td></tr>";
					
		}
		
		return String.valueOf(num+list.size())+"@#_"+str;
	}
}
