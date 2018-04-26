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
import com.platform.application.common.service.DicTypeService;
import com.platform.application.common.service.DictionaryService;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class DicTypeServiceImpl extends GenericHibernateDao<DicType,String> implements DicTypeService {
	
	public DictionaryService dictionaryService;
	
	@Autowired
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	private static final Logger logger = Logger.getLogger(DicTypeServiceImpl.class);	

	/**字典类型维护------------------------------------------------- start**/
	//查询证件类型
	public List<Dictionary> getIdType(String code) throws Exception{
		List<Dictionary> list = new ArrayList<Dictionary>();
		String sql = "select dict from DicType dict where dict.code = '"+code+"'";
		List diclist = this.find(sql);
		if(diclist.size()>0){
			DicType dict = (DicType)diclist.get(0);
			list = dict.getDictionarys();
		}
		return list;
	}
	public String getDicTypeJson(DicType dicType, Page page)throws Exception {
		List para = new  ArrayList();
		List dicList = this.getDicTypeList(dicType,page,para);
		if (dicList.size()>0) {
		   for (int i = 0; i < dicList.size(); i++) {
			   DicType dic = (DicType) dicList.get(i);
			   HashMap<String, String> taskmap=new HashMap<String, String>();
			   taskmap.put("name", dic.getName());
			   taskmap.put("code", dic.getCode());
			   taskmap.put("id", dic.getId());
			   para.add(taskmap);
		}
	 }	   
		   Map<String,String> map = new HashMap<String,String>();
		   map.put("totalProperty", "total," + page.getTotalCount());
		   map.put("root", "rows");
		   return JsonUtil.fromMapLists(para, map);
	}	
	
	
	public List getDicTypeList(DicType dicType,Page page, List para) {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("from DicType dict");
		if(StringUtil.isNotBlank(dicType.getName())){
			sb.append(" where dict.name like'%"+dicType.getName()+"%'");
		}
		List list = find(sb.toString(), paras, page); 
		return list;
	}
	public void txCreateDicType(DicType dicType) throws Exception {
		dicType.setId(null);
		verifyArea(dicType);
		this.save(dicType);
		
	}
	
	public void verifyArea(DicType dicType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from DicType d where d.name=?");
		List param = new ArrayList(2);
		param.add(dicType.getName());
		if(!StringUtil.isEmpty(dicType.getId())){
			sb.append(" and d.id != ?");
			param.add(dicType.getId());
		}
			List result = this.find(sb.toString(), param);
			if (result.size() > 0) {
				throw new Exception("存在重名的字典类型！");
			}
	}
	public void txUpdateDicType(DicType dicType) throws Exception {
		DicType dicTypes = (DicType)this.load(dicType.getId());
		verifyArea(dicType);
		dicTypes.setName(dicType.getName());
		dicTypes.setCode(dicType.getCode());
		this.update(dicTypes);
		
	}


	public String txdeleteDicType(DicType dicType)throws Exception {
		String result = "true;字典类型删除成功！";
		DicType dicTypes = (DicType)this.load(dicType.getId());
		//String sql = "select info from DataTableInfo info where info.dicStr='"+dicType.getId()+"'";
		//List list = this.find(sql);
		//if(list.size()>0){
			//result = "false;字典类型删除失败：该类型已与采集表关联，不可删除！";
		//}else{
			String sqls = " select dic from Dictionary dic where dic.dicType.id='"+dicType.getId()+"'";
			List lists = this.find(sqls);
			for(int i=0; i<lists.size(); i++){
				Dictionary dic = (Dictionary)lists.get(i);
				dictionaryService.delete(dic);
			}
			this.delete(dicTypes);
		//}
		
		return result;
	}


	public List<DicType> getDicTypeNameList() throws Exception {
		List dicList = this.getDicTypeLists();
		return dicList;
	}
	public List getDicTypeLists() {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append("from DicType dict");
		List list = find(sb.toString(), paras); 
		return list;
	}

	public boolean isRepeat(Dictionary dictionart)throws Exception {
		String query = "select dic from Dictionary as dic where dic.name = ?";
		List paras = new ArrayList();
		paras.add(dictionart.getName());
		List list = this.find(query, paras);
		if(list.size() > 0)
			return true;
		return false;
	}
	public String getDicTypesJsonSelect() throws Exception {
			TreeNode node = null;
			DicType dicType = null;
			boolean flag = true;
			List children = new ArrayList();
			children = this.getAllRootNode();
			List list = new ArrayList();
			for (int i = 0; i < children.size(); i++) {
				dicType = (DicType) children.get(i);
				node = new TreeNode();
				node.setText(dicType.getName());
				node.setId(dicType.getId());
				node.setIsParent("true");
				node.setHasChild("true");
				list.add(node);
			}
			return JsonUtil.fromCollections(list);
	}
	public List getAllRootNode() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("from DicType dicType where 1=1  order by dicType.code ASC");
		return this.find(sb.toString());
	}
}