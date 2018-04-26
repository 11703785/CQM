package com.platform.application.rateindex.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.platform.application.rateindex.domain.RateLevel;
import com.platform.application.rateindex.service.RateLevelService;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class RateLevelServiceImpl extends GenericHibernateDao<RateLevel,String> implements RateLevelService {

	public String getRateLevelJson(RateLevel level, Page page) throws Exception {
		List para = new  ArrayList();
		List leList = this.getRateLevel(para,page);
		 if(leList.size()>0){
			 for (int i = 0; i < leList.size(); i++) {
				 RateLevel rl=(RateLevel)leList.get(i);
				 HashMap<String, String> taskmap=new HashMap<String, String>();
				 taskmap.put("name", rl.getName());
				 taskmap.put("type", rl.getFormulas().replace("@","评级得分"));
				 taskmap.put("id",rl.getId());
				 para.add(taskmap);
			 }
		 }
	    Map<String,String> map = new HashMap<String,String>();
	    map.put("totalProperty", "recordsFiltered," + page.getTotalCount());
		map.put("root", "data");
		return JsonUtil.fromMapLists(para,map);
	}

	private List getRateLevel(List pare, Page page) {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		sb.append(" from RateLevel as rs order by rs.levelOrder asc");
		return find(sb.toString(), paras, page); 
		
	}
	private String getSymbols(String str) {
		String result = "";
		if("symbols1".equals(str)){
			result = "<";
		}else if("symbols2".equals(str)){
			result = "<=";
		}else if("symbols3".equals(str)){
			result = "==";
		}else if("symbols4".equals(str)){
			result = ">";
		}else if("symbols5".equals(str)){
			result = ">=";
		}
		return result;
	}
	public void txCreateRateLevel(RateLevel rl, String personId, String deptId,HttpServletRequest request)throws Exception {
		
		//String filedType = request.getParameter("filedType");	
		String numStr = request.getParameter("num");
		int num = StringUtil.isNotBlank(numStr)?Integer.valueOf(numStr):65;
		String temp = "A";
		for(int x=65;x<num;x++){
			temp = String.valueOf((char)x);
			String filedTypes = request.getParameter(temp);//范围类型
			boolean flag = true;
			if("0".equals(filedTypes)){//双值
				String name = request.getParameter(temp+"0"); //等级类别
				String min = request.getParameter(temp+"1");
				String f1 = this.getSymbols(request.getParameter(temp+"2"));//根据值，获取计算公式：<、<=、=、>、>=等
				String f2 = this.getSymbols(request.getParameter(temp+"3"));//根据值，获取计算公式：<、<=、=、>、>=等
				String max = request.getParameter(temp+"4");
				flag = isRepeat(name);
				if(flag){
					RateLevel rateLevel = new RateLevel();
					rateLevel.setCreateTime(new Date());
					rateLevel.setOrgId(deptId);
					rateLevel.setPersonId(personId);
					rateLevel.setFormulas(min+f1+"@"+f2+max);
					rateLevel.setName(name);
					this.save(rateLevel);
				}else{
					throw new Exception("存在同名的等级名称！");
				}
			}else{//单值
				String name = request.getParameter(temp+"0"); //等级类别
				String min = request.getParameter(temp+"1");
				String f1 = this.getSymbols(request.getParameter(temp+"2"));//根据值，获取计算公式：<、<=、=、>、>=等
				flag = isRepeat(name);
				if(flag){
					RateLevel rateLevel = new RateLevel();
					rateLevel.setCreateTime(new Date());
					rateLevel.setOrgId(deptId);
					rateLevel.setPersonId(personId);
					rateLevel.setFormulas(min+f1+"@");
					rateLevel.setName(name);
					this.save(rateLevel);
				}else{
					throw new Exception("存在同名的等级名称！");
				}
			}
		}
	}
	
	public boolean isRepeat(String name){
		boolean flag = true;
		String sql = "";
		List list = new ArrayList();
		sql = "from RateLevel r where r.name='"+name+"' " ;
		list = this.find(sql);
		if(list.size() > 0){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}
	public void verify(RateLevel rateLevel) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from RateLevel r where  r.name=?");
		List param = new ArrayList(2);
		param.add(rateLevel.getName());
		if(!StringUtil.isEmpty(rateLevel.getId())){
			sb.append(" and r.id != ?");
			param.add(rateLevel.getId());
		}
			List result = this.find(sb.toString(), param);
			if (result.size() > 0) {
				throw new Exception("存在同名的等级名称！");
			}
	}
	public void txUpdateRateLevel(RateLevel rl, String deptId, String personId)throws Exception {
		RateLevel rls=(RateLevel)this.load(rl.getId());
		rls.setCreateTime(rl.getCreateTime());
		rls.setDescription(rl.getDescription());
		rls.setFormulas(rl.getFormulas());
		rls.setName(rl.getName());
		verify(rl);
		this.update(rls);
		
	}

	public void txdeleteRatelevel(RateLevel rl)throws Exception  {
		RateLevel  rls=(RateLevel)this.load(rl.getId());
		this.delete(rls);
	}

	public List getAllLevelList() throws Exception {
		String sql = "select l from RateLevel l where 1=1 order by l.levelOrder";
		return this.find(sql);
	}

}
