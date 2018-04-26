package com.platform.application.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.common.domain.DicType;
import com.platform.application.common.domain.Dictionary;
import com.platform.application.common.domain.ValiRules;
import com.platform.application.common.service.DictionaryService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.service.LogService;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class DictionaryServiceImpl extends GenericHibernateDao<Dictionary,String> implements DictionaryService {

	private static final Logger logger = Logger.getLogger(DictionaryServiceImpl.class);
	private LogService logService = null;
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	/**字典维护 start**/
	public String getDictionaryJson(DicType dicType, Page page)throws Exception {
		
		List para = new  ArrayList();
		List dicList = this.getDictoryist(page,para,dicType);
		if (dicList.size()>0) {
		   for (int i = 0; i < dicList.size(); i++) {
			   Dictionary diction = (Dictionary) dicList.get(i);
			   HashMap<String, String> taskmap=new HashMap<String, String>();
			   taskmap.put("name", diction.getName());
			   taskmap.put("code", diction.getCode());
			   taskmap.put("dicTypeName", diction.getDicType().getName());
			   taskmap.put("memo", diction.getMemo());
			   taskmap.put("id", diction.getId());
			   para.add(taskmap);
		   }
		}	   
			Map<String,String> map = new HashMap<String,String>();
			map.put("totalProperty", "total," + page.getTotalCount());
			map.put("root", "rows");
			return JsonUtil.fromMapLists(para, map);
	}	
	public List getDictoryist(Page page, List para,DicType dicType) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Dictionary d where 1=1");
		if(StringUtil.isNotEmpty(dicType.getName())){
		 sb.append(" and d.dicType.name like '%"+dicType.getName()+"%'");
		}
		sb.append(" order by d.code asc");
		List list = find(sb.toString(), para, page); 
		return list;
	}
	public void txCreateDictionary(Dictionary dictionary,DicType dicType) throws Exception {
		if(isRepeatRole(dictionary,dicType)){
			throw new Exception("同一类型下字典信息名称重复，保存失败！");
		}else{
			dictionary.setDicType(dicType);
			dictionary.setId(null);
			this.save(dictionary);
		}
	}
	public void txUpdateDictionary(Dictionary dictionary,DicType dicType) throws Exception {
		Dictionary dictionarys=(Dictionary) this.load(dictionary.getId());
		dictionarys.setName(dictionary.getName());
		dictionarys.setCode(dictionary.getCode());
		dictionarys.setMemo(dictionary.getMemo());
		dictionarys.setDicType(dicType);
		dictionarys.setOrderNum(dictionary.getOrderNum());
		this.update(dictionarys);
		
	}
	
	public void txdeleteDictionary(Dictionary dictionary) throws Exception {
		Dictionary dictionarys=(Dictionary) this.load(dictionary.getId());
		this.delete(dictionarys);
	}
	public boolean isRepeatRole(Dictionary dictionary,DicType dicType) throws Exception {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("from Dictionary as dic where dic.name = ? and dic.dicType.id = ?");
		paras.add(dictionary.getName());
		paras.add(dicType.getId());
		List list = this.find(sb.toString(), paras);
		if (list.size() > 0) return true;
		return false;
	}
	
	public String getDicByLxAndCode(String string, String code) throws Exception {
		String sql = "select d from Dictionary d where d.dicType.code='"+string+"' and d.code='"+code+"'";
		List list = this.find(sql);
		String name = "";
		if(list.size()>0){
			Dictionary d = (Dictionary)list.get(0);
			name = d.getName();
		}
		return name;
	}

	public Map<String, String> getDictionaryMaps() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String sql = "select t.code,dic.code,dic.name from Dictionary dic,DicType t where dic.dicType.id=t.id";
		List list = this.find(sql);
		for(int i=0; i<list.size(); i++){
			Object[] obj = (Object[])list.get(i);
			map.put(String.valueOf(obj[0])+"_"+String.valueOf(obj[1]), String.valueOf(obj[2]));
		}
		return map;
	}
	
	public String getDicTypesJson(String id) throws Exception {
		List children = new ArrayList();
		TreeNode node = null;
		if (id==null|"-1".equals(id)) {// 取根节点
			DicType dictype = new DicType();
			dictype.setId("aaa");
			dictype.setName("字典类型树");
			children.add(dictype);
		} else {
			children = this.getAllDicType();
		}
		List list = new ArrayList();
		for (int i = 0; i < children.size(); i++) {
			DicType dictype = (DicType)children.get(i);
			node = new TreeNode();
			node.setId(dictype.getId());
			node.setName(dictype.getName());
			if(id==null|"-1".equals(id)){
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
	private List getAllDicType() throws Exception {
		String sql = "select t from DicType t where 1=1 order by t.code asc";
		return this.find(sql);
	}
	@Override
	public List<Dictionary> getDicById(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Dictionary d where 1=1");
		if(StringUtil.isNotEmpty(id)){
		 sb.append(" and d.id='"+id+"'");
		}
		sb.append(" order by d.code asc");
		
		return find(sb.toString()); 
	}
	
	
	
	/**字典维护 end**/
}