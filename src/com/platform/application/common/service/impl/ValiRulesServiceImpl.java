package com.platform.application. common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.platform.application.common.domain.ValiRules;
import com.platform.application.common.service.ValiRulesService;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class ValiRulesServiceImpl extends GenericHibernateDao<ValiRules,String> implements ValiRulesService{

	public String getValiRulesListJson(Page pageObj) throws Exception {
		List para = new  ArrayList();
		List dicList = this.getValiRulesList(pageObj,para);
		if (dicList.size()>0) {
		   for (int i = 0; i < dicList.size(); i++) {
			   ValiRules valiRules = (ValiRules) dicList.get(i);
			   HashMap<String, String> taskmap=new HashMap<String, String>();
			   taskmap.put("name", valiRules.getName());
			   taskmap.put("rules", valiRules.getRules());
			   taskmap.put("descrption", valiRules.getDescrption());
			   taskmap.put("id", valiRules.getId());
			   para.add(taskmap);
		}
	 }	   
			Map<String,String> map = new HashMap<String,String>();
			map.put("totalProperty", "total," + pageObj.getTotalCount());
			map.put("root", "rows");
			return JsonUtil.fromMapLists(para, map);
	}
	public List getValiRulesList(Page page, List para) {
		StringBuffer sb = new StringBuffer();
		sb.append("from ValiRules rules");
		List list = find(sb.toString(), para, page); 
		return list;
	}

	public void txEditValiRules(ValiRules valiRules) throws Exception {
		ValiRules tmp = (ValiRules) this.load(valiRules.getId());
		this.verifyValiRules(valiRules);
		tmp.setName(valiRules.getName());
		tmp.setRules(valiRules.getRules());
		tmp.setDescrption(valiRules.getDescrption());
		this.update(tmp);
		
	}

	public void txSaveValiRules(ValiRules valiRules) throws Exception {
		valiRules.setId(null);
		verifyValiRules(valiRules);
		this.save(valiRules);
		
	}
	
	public void verifyValiRules(ValiRules valiRules) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from ValiRules a where a.name=?");
		List param = new ArrayList(1);
		param.add(valiRules.getName());
		if(!StringUtil.isEmpty(valiRules.getId())){
			sb.append(" and a.id != ?");
			param.add(valiRules.getId());
		}
			List result = this.find(sb.toString(), param);
			if (result.size() > 0) {
				throw new Exception("存在重名的规则名称！");
			}
	}
	public String txDeleteValiRules(ValiRules valiRules) throws Exception {
		String result = "true;校验规则删除成功！";
		
		ValiRules valiRule = (ValiRules)this.load(valiRules.getId());
		//String sql = "select tableinfoid,rulesid from data_tableinfo_rules  where rulesid ='"+valiRule.getId()+"'";
		//List list = this.findSQL(sql);
		//if(list.size() > 0){
			//result = "false;校验类型删除失败：该类型已与tableinfo表关联，不可删除！";
		//}else{
			this.delete(valiRules);
		//}
		return result;
	}
	public Map selValirulesMap() throws Exception{
		Map vmap=new HashMap<String, String>();
		List<ValiRules> vlist=this.loadAll();
		for(ValiRules vr :vlist){
			vmap.put(vr.getId(), vr.getName());
		}
		return vmap;
		
	}
}
