package com.platform.application.operation.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.operation.service.DataFromManagerService;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.LogService;
import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;

@Service
public class DataFromManagerServiceImpl extends GenericHibernateDao<SourceOrg,String> implements DataFromManagerService{
	private AreaService areaService=null;
	private LogService logService = null;
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	//获得接入点实体
	public List<SourceOrg> getSourceOrg(Person person, SourceOrg sourceOrg, Page page)
			throws Exception {
		List paras = new ArrayList(3);
		StringBuffer sb = new StringBuffer();
		if(person.getDepartment().getDeptId().equals("root")){
	    sb.append("from SourceOrg as sourceOrg where 1=1");

		}else{
		sb.append("from SourceOrg as sourceOrg where sourceOrg.code='"+person.getDepartment().getDeptCode()+"' or sourceOrg.personalCode='"+person.getDepartment().getPersonalOrgCode()+"'");}
		
		if(StringUtil.isNotBlank(sourceOrg.getName())){
			sb.append(" and sourceOrg.name like '%"+sourceOrg.getName()+"%'");
		}
		if(StringUtil.isNotBlank(sourceOrg.getCode())){
			sb.append(" and sourceOrg.code like '%"+sourceOrg.getCode()+"%'");
		}
		return find(sb.toString(), paras, page);
	}
    //接入点JSON
	public String getSourceOrgsJSON(Person person, SourceOrg sourceOrg, Page page)
			throws Exception {
		List<SourceOrg> sourceOrgList = this.getSourceOrg(person,sourceOrg,page);
		List para = new ArrayList();
		for(int i = 0;i<sourceOrgList.size();i++){
			SourceOrg sourceorg = sourceOrgList.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", sourceorg.getName());
			map.put("code",sourceorg.getCode() );
			map.put("id", sourceorg.getId());
			map.put("area", sourceorg.getArea().getName());
			map.put("description", sourceorg.getDescription());
			map.put("ip", sourceorg.getIp());
			map.put("personalCode", sourceorg.getPersonalCode());
			para.add(map);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");
		return JsonUtil.fromMapLists(para, map);
	}
    //新建接入点
	public void txCreateSourceOrg(SourceOrg sourceOrg, Person person, String ip,String areaId)
			throws Exception {
		Area area = new Area();
		area.setId(areaId);
		sourceOrg.setArea(area);
		if(isRepeatSourceOrg(sourceOrg)){
			throw new Exception("编码重复，创建失败！");
		}else{
			sourceOrg.setId(null);
			this.save(sourceOrg);
			//记录操作日志
			Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
			logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
					ip, "新建了一个接入点单位名为"+sourceOrg.getName()+"！");
		}
		
	}
	//校验辖区接入点是否已注册
	public boolean isRepeatSourceOrg(SourceOrg sourceOrg) throws Exception {
		List paras = new ArrayList(3);
		String sb="";
		sb="from SourceOrg as sourceOrg where  sourceOrg.code = '"+sourceOrg.getCode()+"' and sourceOrg.area = '"+sourceOrg.getArea()+"'";
		List list = this.find(sb, paras);
		if (list.size() > 0) return true;
		return false;
	}
    //删除接入点
	public void txDeleteSourceOrg(SourceOrg sourceOrg, Person person, String ip)
			throws Exception {
		SourceOrg sourceOrgnew = (SourceOrg)this.load(sourceOrg.getId());
		String name=sourceOrgnew.getName();
		this.delete(sourceOrgnew);
		
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
				ip, "删除了一个接入点单位名为"+name+"！");
	}
    //更新接入点
	public void txUpdateSourceOrg(SourceOrg sourceOrg, Person person, String ip,String aname)
			throws Exception {
		
		List<Area> areaByName = areaService.getAreaByName(aname);
		
		Area area = new Area();
		if(areaByName.size()>0){
		area=areaByName.get(0);
		}
		sourceOrg.setArea(area);
		if(isRepeatSourceOrg(sourceOrg)){
			throw new Exception("单位编码重复，更新失败！");
		}else{
			SourceOrg tmpsourceOrg = (SourceOrg)this.load(sourceOrg.getId());
			String name = tmpsourceOrg.getName();
			tmpsourceOrg.setName(sourceOrg.getName());
			tmpsourceOrg.setCode(sourceOrg.getCode());
			tmpsourceOrg.setArea(area);
			tmpsourceOrg.setDescription(sourceOrg.getDescription());
			tmpsourceOrg.setIp(sourceOrg.getIp());
			tmpsourceOrg.setPersonalCode(sourceOrg.getPersonalCode());
			this.update(tmpsourceOrg);
			//记录操作日志
			Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
			logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
					ip, "修改接入点单位名为"+name+"的单位信息！");
		}
	}
	
	//通过编码获取接入点
	@Override
	public List<SourceOrg> getSourceOrgByCode(String code) {
		List paras = new ArrayList(3);
		String sb="";
		sb="from SourceOrg as sourceOrg where  sourceOrg.code = '"+code+"' or sourceOrg.personalCode='"+code+"'";
		return this.find(sb, paras);
		
	}

}
