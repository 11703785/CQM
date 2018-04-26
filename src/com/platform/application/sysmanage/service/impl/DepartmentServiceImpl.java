package com.platform.application.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Service;

import com.platform.application.common.util.Constants;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.service.LogService;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;

@Service
public class DepartmentServiceImpl extends GenericHibernateDao<Department,String> implements DepartmentService {
	
	private static final Logger logger = Logger.getLogger(DepartmentServiceImpl.class);
	private LogService logService = null;
	private AreaService areaService=null;
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	//新增机构
	public void txCreateDepartment(Department dept, Person person, String ip,String areaId) throws Exception {
		dept.setDeptId(null);
		
		Area area = new Area();
		area.setId(areaId);
		dept.setArea(area);
		
		if (dept.getParent() != null && !StringUtil.isEmpty(dept.getParent().getDeptId())){
			Department parent = this.load(dept.getParent().getDeptId());
			dept.setParent(parent);
			
			parent.setIsleaf("0");
		}else{
			throw new Exception("新建保存子机构未获取父机构信息！");
		}
		
		this.verifyDepartment(dept);
		this.save(dept);
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
			ip, "新建了一个名为："+dept.getDeptName()+"的部门！");
	}
	//更新机构
	public void txUpdateDepartment(Department dept, Person person, String ip,String aname) throws Exception {
		Department tDept = (Department) this.load(dept.getDeptId());
		String deptName = tDept.getDeptName();
		dept.setParent(tDept.getParent());
		this.verifyDepartment(dept);
		
		List<Area> areaByName = areaService.getAreaByName(aname);

		Area area = new Area();
		if(areaByName.size()>0){
			area=areaByName.get(0);
			}
		tDept.setArea(area);
		
		tDept.setDeptName(dept.getDeptName());
		tDept.setDeptType(dept.getDeptType());
		tDept.setDeptCode(dept.getDeptCode());
		tDept.setDeptOrder(dept.getDeptOrder());
		tDept.setDescription(dept.getDescription());
		tDept.setPersonalOrgCode(dept.getPersonalOrgCode());
		this.update(tDept);
		
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(),				ip, "修改了"+deptName+"的信息！");
	}
	/**
	 * 删除机构
	 */
	public void txDeleteDepartment(String id, Person person, String ip) throws Exception {
		Department dept = (Department) this.load(id);
		Department pDept = dept.getParent();
		String deptName = dept.getDeptName();
		String pName = pDept.getDeptName();
		if (dept.getParent() == null) {
			throw new Exception("根机构不允许被删除！");
		}
		if (this.hasChildren(id)|| this.hasUsers(id)){
			throw new Exception("该机构下已包含子机构或用户，不允许删除！");
		}
		this.delete(dept);
		//删除后如果父地区没有子地区了，则重置父地区的是否叶子节点属性
		if(!this.hasChildren(pDept.getDeptId())){
			logger.info("删除部门后重置父机构叶子节点属性！");
			pDept.setIsleaf(Constants.LEAF_1);
		}
		Date date = DateUtils.formatByDate(new Date(), DateUtils.ORA_DATE_TIMES3_FORMAT);
		logService.txSaveLog(person.getDepartment().getDeptId(), person.getDepartment().getDeptName(), date, person.getLoginName(), person.getPersonName(), 
				ip, "删除了"+pName+"下的"+deptName+"!");
	}
    //获得所有子机构
	public List getAllChildren(String pid) {
		StringBuffer sb = new StringBuffer();
		List<String> paras = new ArrayList<String>(1);
		if (null == pid) {
			sb.append("from Department dept WHERE dept.parent.deptId is null");
		} else {
			sb.append("from Department dept WHERE dept.parent.deptId = ? order by dept.deptOrder asc");
			paras.add(pid);
		}
		//sb.append(" order by dept.deptOrder asc");
		return this.find(sb.toString(), paras);
	}
    //机构树
	public String getDepartmentsJson(String pid,Person person) throws Exception{
		List children = null;
		if (pid==null|"-1".equals(pid)) {// 取根节点
			Department root = (Department)this.load(person.getDepartment().getOrg().getDeptId());
			String x=root.getDeptName();
			children = new ArrayList();
			children.add(root);
		} else {
			children = this.getAllChildren(pid);
		}
		List list = new ArrayList();
		Department dept = null;
		TreeNode node = null;
		for (int i = 0; i < children.size(); i++) {
			dept = (Department) children.get(i);
			node = new TreeNode();
			node.setText(dept.getDeptName());
			node.setId(dept.getDeptId());
			if ("0".equals(dept.getIsleaf())) {
				node.setState("closed");
			}else{
				node.setState("open");
			}
			list.add(node);
		}
		return JsonUtil.fromCollections(list);
	}

	
	
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
	//根据地区获得机构
	public List<Department> getDepartmentByArea(String aid){
		StringBuffer sb = new StringBuffer();
		List param = new ArrayList(1);
		sb.append("from Department d where d.area.id=?");
		param.add(aid);
		
		return this.find(sb.toString(), param);
	}
	

	//校验
	public void verifyDepartment(Department dept) throws Exception {
		if(dept.getParent()==null){
			if(!Department.TYPE_01.equals(dept.getDeptType())){
				throw new Exception("根机构类型必须是单位！");
			}
		}else{
			if(Department.TYPE_03.equals(dept.getParent().getDeptType()) && Department.TYPE_01.equals(dept.getDeptType())){
				throw new Exception("部门下不能建机构！");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("from Department d where (d.deptCode=? or (d.parent.deptId=? and d.deptName=?))");
			List param = new ArrayList(4);
			param.add(dept.getDeptCode());
			param.add(dept.getParent().getDeptId());
			param.add(dept.getDeptName());
			if(!StringUtil.isEmpty(dept.getDeptId())){
				sb.append(" and d.id != ?");
				param.add(dept.getDeptId());
			}
			List result = this.find(sb.toString(), param);
			if (result.size() > 0) {
				throw new Exception("机构编码重复或同级下存在重名的机构！");
			}
		}
	}
   //是否有子机构
	public boolean hasChildren(String deptId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from Department d where d.parent.deptId = ?");
		long size = 0;
		List paras = new ArrayList(1);
		paras.add(deptId);
		size = this.getRowCount(sb.toString(), paras).longValue();
		if (size > 0) return true;
		return false;
	}
	//根据ID获得机构
	public List<Department> getDepartmentById(String id){
		StringBuffer sb = new StringBuffer();
		sb.append("from Department p where p.deptId = ?");
		List paras = new ArrayList(1);
		paras.add(id);
		return this.find(sb.toString(), paras);
		
	}
	//机构下是否有用户
	public boolean hasUsers(String deptId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("from Person p where p.department.id = ?");
		long size = 0;
		List paras = new ArrayList(1);
		paras.add(deptId);
		size = this.getRowCount(sb.toString(), paras).longValue();
		if (size > 0) return true;
		return false;
	}
/*public Document BuildXMLDoc() throws IOException,JDOMException{
		
		StringBuffer sb = new StringBuffer();
		sb.append("from Department d where d.deptId<>'root' order by d.deptCode asc");
		List param = new ArrayList(4);
		List result = this.find(sb.toString(), param);
		
		
		// 创建根节点list;
		Element root = new Element("list");
		// 根节点增加到文档中
		Document doc = new Document(root);
		
		// 对数据库表的结果集操作
		for (int i = 0; i < result.size(); i++) {
			// 创建节点;
			Element elements = new Element("department");
			// 给节点增加属性id;
			//elements.setAttribute("id", "" + i);
			// 给节点增加子节点并赋值;
			Department dept = (Department) result.get(i);
			if(dept.getDeptName()!=null && dept.getDeptName().length()>0){//判断是否有地区名称
				elements.addContent(new Element("name").setText(dept.getDeptName()));
			}
			if(dept.getDeptCode()!=null && dept.getDeptCode().length()>0){//判断是否有地区编码
				elements.addContent(new Element("code").setText(dept.getDeptCode()));
			}
			if(String.valueOf(dept.getDeptOrder())!=null && String.valueOf(dept.getDeptOrder()).length()>0){//判断是否有地区编码
				elements.addContent(new Element("order").setText(String.valueOf(dept.getDeptOrder())));
			}
			if(dept.getParent().getDeptCode()!=null && dept.getParent().getDeptCode().length()>0){//判断是否有parentid
				elements.addContent(new Element("parentcode").setText(dept.getParent().getDeptCode()));
			}
			if(dept.getDeptType()!=null && dept.getDeptType().length()>0){//判断是否有地区级别
				elements.addContent(new Element("deptlevel").setText(dept.getDeptType()));
			}
			if(dept.getIsleaf()!=null && dept.getIsleaf().length()>0){//判断是否有是否末级
				elements.addContent(new Element("isleaf").setText(dept.getIsleaf()));
			}
			if(dept.getDescription()!=null && dept.getDescription().length()>0){//判断是否有描述
				elements.addContent(new Element("descriptions").setText(dept.getDescription()));
			}
			// 给父节点list增加子节点
			root.addContent(elements);
		}
		return doc;
	}*/

	public void txDeptUpdateRegist() throws Exception {
		/*Department dept = (Department) this.load("root");//取根节点
		String code = dept.getHomeaccessid();
		String priKey = Constants.str;
		String info = new String(SM2Utils.decrypt(Base64.decode(priKey.getBytes()),Base64.decode(code.getBytes())),"GBK");
		String temp[] = info.split(";");
		
		long a = (new Date()).getTime();
		long begin = Long.valueOf(temp[2]);
		long end = Long.valueOf(temp[3]);
		
		String de = info;
		if(a>=begin || a<=end){
			de = temp[0]+";"+temp[1]+";"+a+";"+temp[3]+";"+temp[4]+";"+temp[5]+";"+temp[6]+";"+temp[7];
		}
		String pubkS = SM2Utils.str;
		byte[] cipherText = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), de.getBytes());
		String recode = new String(Base64.encode(cipherText)); 
		
		dept.setHomeaccessid(recode);*/
		//this.update(dept);
	}
    //通过当前机构所属辖区编码查询同级别的兄弟辖区编码
	public Object[] findOtherAreaCode(String curCode) {
		Object[] obj = null;
		
		String lastStr = curCode.substring(curCode.length()-2);
		System.out.println(lastStr);
		String sql = "";
		if("00".equals(lastStr)){//地市
			String firstStr = curCode.substring(0,2);
			String centerStr = curCode.substring(2,4);
			sql = "SELECT TT.AREA_NO_ID FROM T_AREA_CODE TT WHERE TT.AREA_NO_ID REGEXP '^"+firstStr+"([0-9]{2})00$' and TT.AREA_NO_ID NOT REGEXP '^"+firstStr+"(00|"+centerStr+")00$';";
		}else{//县以下
			String firstStr = curCode.substring(0,4);
			sql = "SELECT TT.AREA_NO_ID FROM T_AREA_CODE TT WHERE TT.AREA_NO_ID REGEXP '^"+firstStr+"([0-9]{2})$' AND TT.AREA_NO_ID NOT REGEXP '^"+firstStr+"(00|"+lastStr+")$'";
		}
		
		final String sqls = sql;

		List list =  getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createSQLQuery(sqls);
				return query.list();
			}
		});
		
		if(list.size()>0){
			obj = list.toArray();
		}
		
		return obj;
	}
	
	//根据企业或个人编码查询机构
	@Override
	public List<Department> getDepartmentByCode(String code) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Department p where p.deptCode = ? or p.personalOrgCode=?");
		List paras = new ArrayList();
		paras.add(code);
		paras.add(code);
		return this.find(sb.toString(), paras);
	}
	@Override
	public List<Department> getDepartmentByLevel(String level) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Department p where p.isleaf = ? ");
		List paras = new ArrayList();
		paras.add(level);
		return this.find(sb.toString(), paras);
	}
	@Override
	public List<Area> getAreaByCode(String code) {
    List<Area> findSQL = this.findSQL("select area.* from sys_department as dept,sys_area as area where dept.deptcode_p='"+code+"' and dept.areaid=area.id");
    return findSQL;
	}
}