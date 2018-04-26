package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class AreaServiceImpl extends GenericHibernateDao<Area,String> implements AreaService {
	private DepartmentService departmentService=null;
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	//当前登录用户辖区树
	public String getArea(String pid,Person person,Area area1) throws Exception {
		TreeNode node = null;
		Area area = null;
			boolean flag = true;
			List children = new ArrayList();
			if(pid == null || pid.equals("")){
				if(area1.getLevels().equals("0")){
					children = getAllRootNode(area1.getId());
				}else{
					children = getAllRootNodeC(area1.getId());
				}
			}else{
				children = getChildren(pid);
			}
			List list = new ArrayList();
			for (int i = 0; i < children.size(); i++) {
				area = (Area) children.get(i);
				node = new TreeNode();
				node.setText(area.getName());
				node.setId(area.getId());
				if ("0".equals(area.getLevels())) {
					node.setState("closed");
				}else{
					node.setState("open");
				}
				flag = getParents(area.getId());//校验是否存在下级地区
				if (flag) {
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
	
	//更新辖区
	public void txEditArea(Area area) throws Exception {
		Area tmp = (Area) this.load(area.getId());
		this.verifyArea(area);
		tmp.setName(area.getName());
		tmp.setCode(area.getCode());
		tmp.setLevels(area.getLevels());
		tmp.setDescription(area.getDescription());
		
		if(StringUtil.equals(tmp.getLevels(), "0")){//地市
			tmp.setCountyid(null);
		}else if(StringUtil.equals(area.getLevels(), "1")){//区县
			tmp.setCountyid(tmp.getId());
		}else if(StringUtil.equals(area.getLevels(), "2")){//乡镇
			tmp.setCountyid(tmp.getParent().getId());
		}else if(StringUtil.equals(area.getLevels(), "3")){//村
			Area tempa = this.load(area.getParent().getId());
			tmp.setCountyid(tmp.getParent().getId());
		}
		
		this.update(tmp);
	}
	
	//保存辖区
	public void txSaveArea(Area area) throws Exception {
		if(area.getParent()!=null && StringUtil.isEmpty(area.getParent().getId())){
			area.setParent(null);
		}
		area.setId(area.getCode());
		verifyArea(area);
		this.save(area);
		
		if(StringUtil.equals(area.getLevels(), "0")){//地市
			area.setCountyid(null);
		}else if(StringUtil.equals(area.getLevels(), "1")){//区县
			area.setCountyid(area.getId());
		}else if(StringUtil.equals(area.getLevels(), "2")){//乡镇
			area.setCountyid(area.getParent().getId());
		}else if(StringUtil.equals(area.getLevels(), "3")){//村
			Area tempa = this.load(area.getParent().getId());
			area.setCountyid(tempa.getParent().getId());
		}
		this.update(area);
		
	}
	
	//校验辖区名称
	public void verifyArea(Area area) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from Area a where a.parent.id=? and a.name=?");
		List param = new ArrayList(3);
		param.add(area.getParent().getId());
		param.add(area.getName());
		if(!StringUtil.isEmpty(area.getId())){
			sb.append(" and a.id != ?");
			param.add(area.getId());
		}
			List result = this.find(sb.toString(), param);
			if (result.size() > 0) {
				throw new Exception("同级下存在重名的地区！");
			}
	}
	
	//删除辖区
	public void txDeleteArea(Area area) throws Exception {
		this.delete(area);
	}
	/**
	 * 获文件夹
	 */
	public List getAllRootNode(String id) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("from Area area where area.id ='"+id+"' and area.levels = '0' order by area.code ASC");
		return this.find(sb.toString());
	}
	//获取所有地级市
	public List getAllRootNode() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("from Area area where area.parent is null or area.parent ='' order by area.code ASC");
		return this.find(sb.toString());
	}
	public List getAllRootNodeC(String id){
		StringBuffer sb = new StringBuffer();
		sb.append("from Area area where area.id=? order by area.code ASC");
		List paras = new ArrayList(1);
		paras.add(id);
		return this.find(sb.toString(), paras);
	}
	
	/**
	 * 校验删除辖区
	 */
	public List getParentList(Area area) {
		String sql = "from Area area  where  area.parent='"+area.getId()+"'";
		List list = new ArrayList();
		list = this.find(sql);
		return list;
	}
	/**
	 * 是否有父节点
	 */
	private boolean getParents(String pid){
		boolean flag = true;
		List list = new ArrayList();
		String sql = "from Area area where area.parent.id = '"+pid+"'";
		list = this.find(sql);
		if(list.size()>0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
//获得level为1的辖区
	public List getAreaFirst() throws Exception {
		String sql = "select area from Area area where area.levels = '1' ";
		List list = this.find(sql);
		return list;
	}
	//获得level为0的辖区
	public List getFirstArea() throws Exception {
		String sql = "select area from Area area where area.levels = '0' ";
		List list = this.find(sql);
		return list;
	}
	
	public String getAreaJson(Area area) throws Exception {
		String sql = "select area from Area area where area.parent.id='"+area.getId()+"'";
		List list  = this.find(sql);
		return JsonUtil.fromCollections(list);
	}
	////获得level为1或0的辖区
	public List getAreaList() throws Exception {
		String sql = "select area from Area area where 1=1 and area.levels in('0','1') order by area.levels asc";
		List list = this.find(sql);
		return list;
	}
	//root用户加载树
	public String getArea(String pid) throws Exception {
		TreeNode node = null;
		Area area = null;
			boolean flag = true;
			List children = new ArrayList();
			if(pid == null || pid.equals("")){ 
				children = getAllRootNode();
			}else{
				children = getChildren(pid);
			}
			List list = new ArrayList();
			for (int i = 0; i < children.size(); i++) {
				area = (Area) children.get(i);
				node = new TreeNode();
				node.setText(area.getName());
				node.setId(area.getId());
				if ("3".equals(area.getLevels())) {
					node.setState("open");
				}else{
					node.setState("closed");
				}
				flag = getParents(area.getId());//校验是否存在下级地区
				if (flag) {
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
	 * 获得下级
	 */
	public List getChildren(String pid) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("from Area area where area.parent.id=? order by area.code ASC");
		List paras = new ArrayList(1);
		paras.add(pid);
		return this.find(sb.toString(), paras);
	}
	//获取子辖区
	public List getAreaChildList(Area area) throws Exception {
		String sql = "select a from Area a where a.parent.id='"+area.getId()+"'";
		return this.find(sql);
	}
	 /**
	 * 查询出code name对应的map
	 * @param 
	 * @return
	 */
	public Map<String ,String> getAreaXX()throws Exception{
		Map map=new HashMap<String, String>();
		List<Area> list=this.loadAll();
		for(Area area:list){
			map.put(area.getCode(), area.getName());
		}
		return map;
	}
	/**
	   * 临时方法：用于批量给counyId赋值
	   * @throws Exception
	   */
	public void txLinshiUpdateAllArea() throws Exception {
		List<Area> list=this.loadAll();
		for(Area area:list){
			if(StringUtil.equals(area.getLevels(), "0")){//地市
				area.setCountyid(null);
			}else if(StringUtil.equals(area.getLevels(), "1")){//区县
				area.setCountyid(area.getId());
			}else if(StringUtil.equals(area.getLevels(), "2")){//乡镇
				area.setCountyid(area.getParent().getId());
			}else if(StringUtil.equals(area.getLevels(), "3")){//村
				area.setCountyid(area.getParent().getParent().getId());
			}
			this.update(area);
		}
		
	}
	  //根据ID获得Area
	@Override
	public List<Area> getAreaById(String aid) {
		StringBuffer sb = new StringBuffer();
		List paras = new ArrayList(1);
		sb.append("from Area area where area.id=?");
		paras.add(aid);
		return this.find(sb.toString(), paras);
	}
	  //根据辖区管理机构
	@Override
	public String getManagerByAreaJson(Person person, String id) throws Exception {
     	TreeNode node = null;
		Area area = null;
		Department dept=null;
			boolean flag = true;
			List children = new ArrayList();
			List children2=new ArrayList();
			if(id == null || id.equals("")){ 
				if(person.getDepartment().getDeptId().equals("root")){
			    children=this.getFirstArea();

				}else{
				Area root = this.load(person.getDepartment().getArea().getId());
				children.add(root);
				}
			}else{
				children = getChildren(id);
				children2=departmentService.getDepartmentByArea(id);
			}
			List list = new ArrayList();
			for (int i = 0; i < children.size(); i++) {
				area = (Area) children.get(i);
				node = new TreeNode();
				node.setText(area.getName());
				node.setId(area.getId()+"_"+"area");
				if ("3".equals(area.getLevels())) {
					node.setState("open");
				}else{
					node.setState("closed");
				}
				flag = getParents(area.getId());//校验是否存在下级地区
				if (flag) {
					node.setIsParent("true");
					node.setHasChild("true");
				}else{
					node.setIsParent("false");
					node.setHasChild("false");
				}				
				list.add(node);
			}
			
			for(int j=0;j<children2.size();j++){
				
				dept=(Department) children2.get(j);
				node = new TreeNode();
				node.setText(dept.getDeptName());
				node.setId(dept.getDeptId()+"_"+"dept");
				if ("0".equals(dept.getIsleaf())) {
					node.setState("closed");
				}else{
					node.setState("open");
				}
				list.add(node);
				}
	
			return JsonUtil.fromCollections(list);
	}
	  //根据地区名称获得Area
	@Override
	public List<Area> getAreaByName(String name) {
		StringBuffer sb = new StringBuffer();
		List paras = new ArrayList(1);
		sb.append("from Area area where area.name=?");
		paras.add(name);
		return this.find(sb.toString(), paras);
	}

	@Override
	public List getChildrenId(String pid) {
		return this.findSQL("select id from sys_area where parentid='"+pid+"'");
	}

	
}
