package com.platform.application.rateindex.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.rateindex.domain.RateIndex;
import com.platform.application.rateindex.domain.RateTempIndex;
import com.platform.application.rateindex.domain.RateTempIndexScore;
import com.platform.application.rateindex.domain.RateTemplate;
import com.platform.application.rateindex.service.RateTempIndexScoreService;
import com.platform.application.rateindex.service.RateTempIndexService;
import com.platform.application.rateindex.service.RateTemplateService;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class RateTemplateServiceImpl extends GenericHibernateDao<RateTemplate,String> implements RateTemplateService {

	private RateTempIndexService rateTempIndexService;
	private RateTempIndexScoreService rateTempIndexScoreService;
	
	@Autowired
	public void setRateTempIndexService(RateTempIndexService rateTempIndexService) {
		this.rateTempIndexService = rateTempIndexService;
	}
	@Autowired
	public void setRateTempIndexScoreService(
			RateTempIndexScoreService rateTempIndexScoreService) {
		this.rateTempIndexScoreService = rateTempIndexScoreService;
	}

	public String getRateTemplateJson(RateTemplate rateTemplate, Person person, Page page) throws Exception {
		StringBuffer sb = new StringBuffer();
		List paras = new ArrayList();
		
		sb.append("select temp from RateTemplate temp where temp.orgId='"+person.getDepartment().getDeptId()+"'");
		
		List list = this.find(sb.toString(),paras,page);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("totalProperty", "recordsFiltered," + page.getTotalCount());
		map.put("root", "data");
		return JsonUtil.fromCollections(list, map);
	}

	public String loadRateIndex(String id, Person person) throws Exception {
		List rateIndexs = new ArrayList();
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode node = null;
		if(StringUtil.isBlank(id)){
			node = new TreeNode();
			node.setId("-1");
			node.setName("指标树");
			node.setIsParent("true");
			node.setHasChild("true");
			return "["+JsonUtil.fromObject(node)+"]";
		}
		rateIndexs = this.getAllRateIndex(id);
		for (int i = 0; i < rateIndexs.size(); i++) {
			RateIndex rateIndex = (RateIndex)rateIndexs.get(i);
			node = new TreeNode();
			node.setName(rateIndex.getName());
			node.setId(rateIndex.getId()+"_"+rateIndex.getLevels());
			
			if(this.isHasChild(rateIndex.getId())){
				node.setIsParent("true");
				node.setHasChild("true");
			}else{
				node.setIsParent("false");
				node.setHasChild("false");
			}
			
			list.add(node);
		}
		return JsonUtil.fromCollections(list);
	}

	/**
	 * 获得所有的指标
	 */
	private List getAllRateIndex(String id) throws Exception{
		String sql = "select i from RateIndex i where 1=1 ";
		if("-1".equals(id)){
			sql += " and i.parent = null order by i.orderNum asc";
		}else{
			String[] ids = id.split("_");
			sql += " and i.parent.id = '"+ids[0]+"' order by i.orderNum asc";
		}
		List lists = this.find(sql);
		return lists;
	}
	
	/**
	 * 判断是否有子指标
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private boolean isHasChild(String id)  throws Exception{
		boolean msg = false;
		String sql = "select i from RateIndex i where i.parent.id = '"+id+"' order by i.orderNum asc";
		List list = this.find(sql);
		if(list.size()>0){
			msg = true;
		}
		return msg;
	}

	public void txCreateRateTemplate(RateTemplate rateTemplate,String personId, String orgId, String areaids) throws Exception {
		if(this.isRepeateTemp(rateTemplate)){
			throw new Exception("模板名称重复，请重新输入！");
		}else{
			rateTemplate.setId(null);
			rateTemplate.setCreateTime(new Date());
			rateTemplate.setPersonId(personId);
			rateTemplate.setOrgId(orgId);
			rateTemplate.setStatus("0");
			
			List areaList = new ArrayList();
			if(StringUtil.isNotBlank(areaids)){
				String[] rArr = areaids.split(",");
				for (int i = 0; i < rArr.length; i++) {
					Area area = new Area();
					area.setId(rArr[i]);
					areaList.add(area);
				}
			}
			
			rateTemplate.setAreaList(areaList);
			this.save(rateTemplate);
		}
	}

	private boolean isRepeateTemp(RateTemplate rateTemplate) {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("from RateTemplate as temp where temp.name = ?");
		paras.add(rateTemplate.getName());
		if(StringUtils.isNotBlank(rateTemplate.getId())){
			sb.append(" and temp.id != ?");
			paras.add(rateTemplate.getId());
		}
		List list = this.find(sb.toString(), paras);
		if (list.size() > 0) return true;
		return false;
	}

	public void txUpdateRateTemplate(RateTemplate rateTemplate, String orgId, String personId, String areaids) throws Exception {
		if(this.isRepeateTemp(rateTemplate)){
			throw new Exception("模板名称重复，请重新输入！");
		}else{
			RateTemplate rateTemplates =(RateTemplate) this.load(rateTemplate.getId());
			rateTemplates.setCreateTime(new Date());
			rateTemplates.setPersonId(personId);
			rateTemplates.setOrgId(orgId);
			rateTemplates.setCode(rateTemplate.getCode());
			rateTemplates.setDescription(rateTemplate.getDescription());
			rateTemplates.setName(rateTemplate.getName());
			rateTemplates.setStatus(rateTemplate.getStatus());
			
			List areaList = new ArrayList();
			if(StringUtil.isNotBlank(areaids)){
				String[] rArr = areaids.split(",");
				for (int i = 0; i < rArr.length; i++) {
					Area area = new Area();
					area.setId(rArr[i]);
					areaList.add(area);
				}
			}
			
			rateTemplate.setAreaList(areaList);
			
			this.update(rateTemplates);
		}
	}

	@SuppressWarnings("unchecked")
	public void txDeleteRateTemplate(RateTemplate rateTemplate)throws Exception {
		RateTemplate rateTemplates =(RateTemplate) this.load(rateTemplate.getId());
		
		//删除关联指标
		String sql = "select ti from RateTempIndex ti where ti.temp.id='"+rateTemplate.getId()+"'";
		List<RateTempIndex> list = this.find(sql);
		rateTempIndexService.deleteAll(list);
		
		//删除关联指标的评分标准
		String sqls = "select tis from RateTempIndexScore tis where tis.temp.id='"+rateTemplate.getId()+"'";
		List<RateTempIndexScore> lists = this.find(sqls);
		rateTempIndexScoreService.deleteAll(lists);
		
		this.delete(rateTemplates);
		
	}

	public void txEnableRateTemplate(String ids)throws Exception {
	    if (StringUtil.isNotEmpty(ids)) {
	     String[] idArr = ids.split(",");
	     for (int i = 0; i < idArr.length; i++) {
	    	 RateTemplate rateTemplate=(RateTemplate)this.load(idArr[i]);
	    	 if (rateTemplate.getStatus().equals("0")) {
	    		 rateTemplate.setStatus("1");
			}else{
				rateTemplate.setStatus("0");
			}
	    	 this.update(rateTemplate);
		   }
	     }
	}

	
	public String loadRateTempIndexJson(String id, String tempid) throws Exception {
		List rateIndexs = new ArrayList();
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode node = null;
		if(StringUtil.isBlank(id)){
			RateTemplate temp = (RateTemplate)this.load(tempid);
			node = new TreeNode();
			node.setId(temp.getId()+";-1;-1");
			node.setName(temp.getName());
			node.setIsParent("true");
			node.setHasChild("true");
			return "["+JsonUtil.fromObject(node)+"]";
		}
		rateIndexs = this.getRateTempIndex(id);
		String[] ids = id.split(";");
		for (int i = 0; i < rateIndexs.size(); i++) {
			RateTempIndex tempIndex = (RateTempIndex)rateIndexs.get(i);
			node = new TreeNode();
			node.setName(tempIndex.getIndexName());
			node.setId(ids[0]+";"+tempIndex.getIndexId());
			
			if(this.isHasTempIndexChild(ids[0],tempIndex.getIndexId())){
				node.setIsParent("true");
				node.setHasChild("true");
			}else{
				node.setIsParent("false");
				node.setHasChild("false");
			}
			
			list.add(node);
		}
		return JsonUtil.fromCollections(list);
	}

	private List getRateTempIndex(String id) throws Exception{
		List list = new ArrayList();
		if(StringUtil.isNotBlank(id)){
			String[] ids = id.split(";");
			if("-1".equals(ids[1])){
				String sql = "select tempindex from RateTempIndex tempindex where tempindex.temp.id='"+ids[0]+"' and tempindex.parentIndexId=null order by tempindex.indexOrder asc";
				list = this.find(sql);
			}else{
				String sql = "select tempindex from RateTempIndex tempindex where tempindex.temp.id='"+ids[0]+"' and tempindex.parentIndexId='"+ids[1]+"' order by tempindex.indexOrder asc";
				list = this.find(sql);
			}
		}
		return list;
	}
	
	private boolean isHasTempIndexChild(String tempId, String indexId) throws Exception{
		boolean msg = false;
		String sql = "select i from RateTempIndex i where i.temp.id='"+tempId+"' and i.parentIndexId = '"+indexId+"'";
		List list = this.find(sql);
		if(list.size()>0){
			msg = true;
		}
		return msg;
	}

	public LinkedHashMap getReportRateInde(String id) throws Exception {
		LinkedHashMap<String,LinkedHashMap<String,List>> allMaps = new LinkedHashMap<String,LinkedHashMap<String,List>>();
		DecimalFormat format = new DecimalFormat("#0.0000");
		
		String sqlFirst = "select t from RateTempIndex t where t.temp.id='" + id + "' and t.parentIndexId is null order by t.indexOrder asc";
		List firstList = this.find(sqlFirst);//一级指标list
		for (int i = 0; i < firstList.size(); i++) {
			RateTempIndex rateTempIndex = (RateTempIndex) firstList.get(i);
			String pname = rateTempIndex.getIndexName() + "("+ String.valueOf(format.format(rateTempIndex.getWeight()) + ")"); 
			
			String sqls = "select t from RateTempIndex t where t.temp.id='"+ id + "' and t.parentIndexId ='" + rateTempIndex.getIndexId() + "' order by t.indexOrder asc";
			List elist = this.find(sqls);// 二级级指标
			LinkedHashMap<String,List> emap = new LinkedHashMap<String,List>();
			for (int j = 0; j < elist.size(); j++) {
				RateTempIndex e = (RateTempIndex) elist.get(j);

				String ename = e.getIndexName() + "("+ String.valueOf(format.format(e.getWeight()) + ")"); 
				
				String sqlScore = "select rateTempIndexScore from RateTempIndexScore rateTempIndexScore where rateTempIndexScore.temp.id='"+id+"' and rateTempIndexScore.indexId= '"+e.getIndexId()+"' order by rateTempIndexScore.standOrder asc";
				List listScore = this.find(sqlScore);//评分标准
				List slist = new ArrayList();
				for (int k = 0; k < listScore.size(); k++) {
					LinkedHashMap<String, String> smap = new LinkedHashMap<String, String>();
					RateTempIndexScore rateTempIndexScore = (RateTempIndexScore) listScore.get(k);
					if ("0".equals(e.getIndexvType())) {
						smap.put("pfbz", rateTempIndexScore.getStandName());// 评分标准
					} else {
						smap.put("pfbz", rateTempIndexScore.getStandFormule().replace("@", "指标值"));// 评分标准
					}
					smap.put("fz", String.valueOf(rateTempIndexScore.getScore()));// 分值
					
					slist.add(smap);
				}
				
				emap.put(ename, slist);
			}
			allMaps.put(pname, emap);
			
		}
		return allMaps;
		
	}

	public List getAllAreaList() throws Exception {
		String sql = "select area from Area area where levels='1' ";
		return this.find(sql);
	}

	public String getRateIndexDivStr(RateTemplate rateTemplate) throws Exception {
		DecimalFormat format = new DecimalFormat("#0.0000");
		String result = "";
		String sql = "select pri from RateTempIndex pri where pri.temp.id='"+rateTemplate.getId()+"' and pri.parentIndexId=null order by pri.indexOrder asc";
		List list = this.find(sql);
		for(int i=0; i<list.size(); i++){
			RateTempIndex pri = (RateTempIndex)list.get(i);
			
			String sqls = "select cri from RateTempIndex cri where cri.temp.id='"+rateTemplate.getId()+"' and cri.parentIndexId='"+pri.getIndexId()+"' order by cri.indexOrder asc";
			List lists = this.find(sqls);
			if(lists.size()>0){
				result += "<div class=\"box collapsed-box box-layout\">" +
						"<div class=\"box-header box-header-layout\">" +
						"<button type=\"button\" class=\"btn btn-box-tool\" data-widget=\"collapse\"><i class=\"fa fa-plus\"></i></button>" +
						"<label class=\"col1\">"+pri.getIndexLevels()+"级指标</label>" +
						"<label class=\"col2\">"+pri.getIndexName()+"</label>" +
						"<label class=\"col3\"><input name=\""+pri.getIndexId()+"\" class=\"-1\" type=\"text\" value=\""+format.format(pri.getWeight())+"\"></label>" +
						"<a href=\"#\" onclick=\"deleteThis(this)\" class=\"fa fa-remove text-red\" style=\"padding: 7px;\"></a>" +
						"</div><div class=\"box-body box-body-layout\">";
				
				for(int j=0; j<lists.size(); j++){
					RateTempIndex ri = (RateTempIndex)lists.get(j);
					result += "<div class=\"box box-content-layout\">" +
							"<label class=\"col1\">"+ri.getIndexLevels()+"级指标</label>" +
							"<label class=\"col2\">"+ri.getIndexName()+"</label>" +
							"<label class=\"col3\"><input name=\""+ri.getIndexId()+"\" class=\""+pri.getIndexId()+"\" type=\"text\" value=\""+format.format(ri.getWeight())+"\"></label>" +
							"<a href=\"#\" onclick=\"deleteThis(this)\" class=\"fa fa-remove text-red\" style=\"padding: 7px;\"></a></div>";
				}
				result += "</div></div>";
			}else{
				result += "<div class=\"box box-layout\" >" +
						"<div class=\"box box-header-layout\" style=\"padding-left: 40px;\">" +
						"<label class=\"col1\">"+pri.getIndexLevels()+"级指标</label>" +
						"<label class=\"col2\">"+pri.getIndexName()+"</label>" +
						"<label class=\"col3\"><input name=\""+pri.getIndexId()+"\" class=\"-1\" type=\"text\" value=\""+format.format(pri.getWeight())+"\"></label></div>" +
						"<a href=\"#\" onclick=\"deleteThis(this)\" class=\"fa fa-remove text-red\" style=\"padding: 7px;\"></a>" +
						"</div>"; 
			}
		}
		return result;
	}
}
