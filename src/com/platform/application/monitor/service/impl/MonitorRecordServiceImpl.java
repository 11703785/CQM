package com.platform.application.monitor.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Service;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.common.util.Constants;
import com.platform.application.monitor.domain.Grsummary;
import com.platform.application.monitor.domain.Grzxcxmx;
import com.platform.application.monitor.domain.Qysummary;
import com.platform.application.monitor.domain.Qyzxcxmx;
import com.platform.application.monitor.service.CreditQueryService;
import com.platform.application.monitor.service.MonitorRecordService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Log;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.domain.Role;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.framework.common.util.CurValues;
import com.platform.framework.common.util.DateUtils;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
import com.platform.framework.core.page.Page;
import com.platform.framework.core.page.Pager;

@Service
public class MonitorRecordServiceImpl extends GenericHibernateDao<Grsummary,String> implements MonitorRecordService {
	private JdbcTemplate jdbcTemplate;
    private CreditQueryService creditQueryService;
    private DepartmentService deptService;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Autowired
	public void setCreditQueryService(CreditQueryService creditQueryService) {
		this.creditQueryService = creditQueryService;
	}
	@Autowired
	public void setDepartmentService(DepartmentService deptService) {
		this.deptService = deptService;
	}
	//个人征信数据查询JSON
	@Override
	public String getGrzxcxmxJSON(Grzxcxmx grzxcxmx,Page page,Person person,String beginTime,String endTime,String year)
			throws Exception {
		     //日期格式化
		     SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		     String y=null;
		     String x=null;
             Department dept=new Department();
             Department dept1=new Department();
    		 List<Department> deptChild=new ArrayList();
			 List<Map<String, Object>> grzxcxmxs=null ;
			 //机构条件查询
			 if(grzxcxmx!=null&&grzxcxmx.getQueryOrgNo()!=null&&!grzxcxmx.getQueryOrgNo().equals("")){
			 List<Department> departmentById = deptService.getDepartmentById(grzxcxmx.getQueryOrgNo());
			 if(departmentById.size()>0){
				 dept=departmentById.get(0);
			 }
			 }
			 String where=" where 1=1";
			 //当前登录用户所属机构的子机构
			 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
			 }
			 //判断登录人身份，！root，只显示自己机构数据
			 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
					 if(deptChild.size()>0){
						 String depts="";
						 for(int i=0;i<deptChild.size();i++){					
						 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";		 
						 }
						 where+=" and upOrgCode in ("+depts+"'"+person.getDepartment().getPersonalOrgCode()+"')";
					 }else{
						 where+=" and upOrgCode = '"+person.getDepartment().getPersonalOrgCode()+"'";
					 }
				}
			 if(year==null||year.equals("")){
				 Calendar a=Calendar.getInstance();
				  year=a.get(Calendar.YEAR)+"";//得到年
			 }
			 if(beginTime!=null&&!beginTime.equals("")){
				 where+=" and QueryTime>= "+"'"+year+"-"+beginTime+"'";
			 }
			 if(endTime!=null&&!endTime.equals("")){
				 where+=" and QueryTime<= "+"'"+year+"-"+endTime+"'";
			 }
			 if(grzxcxmx!=null&&grzxcxmx.getQueryOrgName()!=null&&!grzxcxmx.getQueryOrgName().equals("")){
				 where+=" and QueryOrgName like "+"'%"+grzxcxmx.getQueryOrgName()+"%'";
			}
			 if(grzxcxmx!=null&&grzxcxmx.getQueryUserName()!=null&&!grzxcxmx.getQueryUserName().equals("")){
				 where+=" and QueryUserName like "+"'%"+grzxcxmx.getQueryUserName()+"%'";
			}
			 if(grzxcxmx!=null&&grzxcxmx.getQueriedUserName()!=null&&!grzxcxmx.getQueriedUserName().equals("")){
				 where+=" and QueriedUserName like '%"+grzxcxmx.getQueriedUserName()+"%'";
			}
			 if(grzxcxmx!=null&&grzxcxmx.getIsQueried()!=null&&!grzxcxmx.getIsQueried().equals("")&&!grzxcxmx.getIsQueried().equals("请选择")){
				 where+=" and IsQueried ="+grzxcxmx.getIsQueried();
			} 
			 if(dept!=null&&dept.getPersonalOrgCode()!=null&&!dept.getPersonalOrgCode().equals("")){
				 where+=" and upOrgCode = '"+dept.getPersonalOrgCode()+"'";
			}
			 if(grzxcxmx!=null&&grzxcxmx.getQueryTime()!=null&&!grzxcxmx.getQueryTime().equals("")){
				 where+=" and queryTime like '"+grzxcxmx.getQueryTime().substring(0,10)+"%'";
			}
			 //分页
			Pager pager = this.getPageListAllCol("grzxcxmx"+year, where, page.getPageIndex(), page.getPageSize());
			 //获得分页结果
			 grzxcxmxs=pager.getResultList();
			 List  list=new ArrayList<Grzxcxmx>();
		   //查询数据字典集合
		   List<Map<String, Object>> dlist = jdbcTemplate.queryForList("select dic.name,dic.code,dict.code as dcode from  sys_Dictionary as dic,sys_DicType as dict where dic.typeId=dict.id and dict.code in ('"+Constants.GRZJT+"','"+Constants.GRCXV+"','"+Constants.GRCXR+"','"+Constants.GRSFS+"')");
		//为grzxcxmx实体注入
		for(int i=0;i<grzxcxmxs.size();i++){
			Grzxcxmx g=new Grzxcxmx();
			if(grzxcxmxs.get(i).get("QueryAuthTime")!=null){
			 x=sdf.format(grzxcxmxs.get(i).get("QueryAuthTime"));
			}
			if(grzxcxmxs.get(i).get("QueryTime")!=null){

			y=sdf.format(grzxcxmxs.get(i).get("QueryTime"));
			}
			g.setCertNo((String)grzxcxmxs.get(i).get("CertNo"));
 
			//查询数据字典对应的个人证件类型
			String result = this.result(dlist,(String)grzxcxmxs.get(i).get("CertType"), Constants.GRZJT);
			g.setCertType(result);
			//查询数据字典对应的个人是否查得
            result=this.result(dlist, (String)grzxcxmxs.get(i).get("IsQueried"), Constants.GRSFS);
			g.setIsQueried(result);
			//查询数据字典对应的个人查询版本
            result=this.result(dlist, (String)grzxcxmxs.get(i).get("QueryFormatName"), Constants.GRCXV);
			g.setQueryFormatName(result);
			//查询数据字典对应的个人查询原因
            result=this.result(dlist, (String)grzxcxmxs.get(i).get("QueryReason"), Constants.GRCXR);
			g.setQueryReason(result);
			g.setQueriedUserName((String)grzxcxmxs.get(i).get("QueriedUserName"));
			g.setQueryAuthTime(x);
			g.setQueryComputerIP((String)grzxcxmxs.get(i).get("QueryComputerIP"));
			g.setQueryOrgName((String)grzxcxmxs.get(i).get("QueryOrgName"));
			if(grzxcxmxs.get(i).get("QueryOrgNo")!=null){
			String v=(String) grzxcxmxs.get(i).get("QueryOrgNo");
			List<Department> departmentByCode = deptService.getDepartmentByCode(v);
			dept1=null;
			 if(departmentByCode.size()>0){
				 
				 dept1=departmentByCode.get(0);
			 }
			}
			if(dept1!=null){
			g.setQueryOrgNo(dept1.getDeptName());
			}else{
				g.setQueryOrgNo((String)grzxcxmxs.get(i).get("QueryOrgNo"));
			}
			g.setQueryTime(y);
			g.setQueryUserName((String)grzxcxmxs.get(i).get("QueryUserName"));
			g.setQueryUserSysName((String)grzxcxmxs.get(i).get("QueryUserSysName"));
			g.setUpOrgCode((String)grzxcxmxs.get(i).get("UpOrgCode"));	
			list.add(g);
		}
		Map map = new HashMap();
		map.put("totalProperty", "total," + pager.getTotalRows());
		map.put("root", "rows");		
		return JsonUtil.fromCollections(list, map);
	}
	//判断个人查询数据的证件类型，是否查得，查询原因，查询版本
	public String result(List<Map<String, Object>> dlist,String code,String type){
		String result="";
		for(int i=0;i<dlist.size();i++){
			if(dlist.get(i).get("dcode")!=null&&!dlist.get(i).get("dcode").toString().equals("")&&dlist.get(i).get("dcode").toString().equals(type)){
				if(dlist.get(i).get("code")!=null&&!dlist.get(i).get("code").toString().equals("")&&dlist.get(i).get("code").toString().equals(code)){
					result=dlist.get(i).get("name").toString();
				}
			}
		}
		return result;
	}
    //jdbc查询(公用方法)
	public Pager getPageListAllCol(String tableName,String where,int currentPage,int numPerPage) {
		Pager pager = null;
		try {
			pager = new Pager(tableName,where, currentPage, numPerPage, jdbcTemplate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		return pager;
	}
	//获得企业查询json
	@Override
	public String getQyzxcxmxJSON(Qyzxcxmx qyzxcxmx, Page page,Person person,String beginTime,String endTime,String year)
			throws Exception {
		     SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    		 List<Department> deptChild=new ArrayList();
		     String y=null;
		     String x=null;
	         Department dept=new Department();
	         Department dept1=new Department();
			 List<Map<String, Object>> qyzxcxmxs=null ;
			 //机构条件查询
			 if(qyzxcxmx!=null&&qyzxcxmx.getQueryOrgNo()!=null&&!qyzxcxmx.getQueryOrgNo().equals("")){
			 List<Department> departmentById = deptService.getDepartmentById(qyzxcxmx.getQueryOrgNo());
			 if(departmentById.size()>0){
				 dept=departmentById.get(0);
			 }
			 }
			 String where=" where 1=1";
			 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
				 deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
				}
				 //判断登录人身份，！root，只显示自己及子机构数据
				 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
						 if(deptChild.size()>0){
							 String depts="";
							 for(int i=0;i<deptChild.size();i++){					
							 depts+="'"+deptChild.get(i).getDeptCode()+"',";		 
							 }
							 where+=" and upOrgCode in ("+depts+"'"+person.getDepartment().getDeptCode()+"')";
						 }else{
							 where+=" and upOrgCode = '"+person.getDepartment().getDeptCode()+"'";
						 }
					}
			
			 if(year==null||year.equals("")){
				 Calendar a=Calendar.getInstance();
				  year=a.get(Calendar.YEAR)+"";//得到年
			 }
			 if(beginTime!=null&&!beginTime.equals("")){
				 where+=" and QueryTime>= "+"'"+year+"-"+beginTime+"'";
			 }
			 if(endTime!=null&&!endTime.equals("")){
				 where+=" and QueryTime<= "+"'"+year+"-"+endTime+"'";
			 }
			 if(qyzxcxmx!=null&&qyzxcxmx.getQueryOrgName()!=null&&!qyzxcxmx.getQueryOrgName().equals("")){
				 where+=" and QueryOrgName like "+"'%"+qyzxcxmx.getQueryOrgName()+"%'";
			}
			 if(qyzxcxmx!=null&&qyzxcxmx.getQueryUserName()!=null&&!qyzxcxmx.getQueryUserName().equals("")){
				 where+=" and QueryUserName like "+"'%"+qyzxcxmx.getQueryUserName()+"%'";
			}
			 if(qyzxcxmx!=null&&qyzxcxmx.getCompanyName()!=null&&!qyzxcxmx.getCompanyName().equals("")){
				 where+=" and CompanyName like "+"'%"+qyzxcxmx.getCompanyName()+"%'";
			}
			 if(qyzxcxmx!=null&&qyzxcxmx.getIsQueried()!=null&&!qyzxcxmx.getIsQueried().equals("")&&!qyzxcxmx.getIsQueried().equals("请选择")){
				 where+=" and IsQueried like "+"'%"+qyzxcxmx.getIsQueried()+"%'";
			} 
			 if(dept!=null&&dept.getDeptCode()!=null&&!dept.getDeptCode().equals("")){
				 where+=" and upOrgCode = '"+dept.getDeptCode()+"'";
			}
			 if(qyzxcxmx!=null&&qyzxcxmx.getQueryTime()!=null&&!qyzxcxmx.getQueryTime().equals("")){
				 where+=" and queryTime like '"+qyzxcxmx.getQueryTime().substring(0,10)+"%'";
			}
			 //分页
			Pager pager = this.getPageListAllCol("qyzxcxmx"+year, where, page.getPageIndex(), page.getPageSize());
			 //分页结果
			 qyzxcxmxs=pager.getResultList();
			 List  list=new ArrayList<Grzxcxmx>();
			 //数据字典查询集合 
	         List<Map<String, Object>> dlist = jdbcTemplate.queryForList("select dic.name,dic.code,dict.code as dcode from  sys_Dictionary as dic,sys_DicType as dict where dic.typeId=dict.id and dict.code in ('"+Constants.QYSFS+"')");
		      for(int i=0;i<qyzxcxmxs.size();i++){
		    	Qyzxcxmx g=new Qyzxcxmx();
			    if(qyzxcxmxs.get(i)!=null){
			    if(qyzxcxmxs.get(i).get("QueryAuthTime")!=null){
				 x=sdf.format(qyzxcxmxs.get(i).get("QueryAuthTime"));
				}
				if(qyzxcxmxs.get(i).get("QueryTime")!=null){
				y=sdf.format(qyzxcxmxs.get(i).get("QueryTime"));
				}		
				String q=(String) qyzxcxmxs.get(i).get("IsQueried");
			//企业是否查得
			String result=this.result(dlist, (String)qyzxcxmxs.get(i).get("IsQueried"), Constants.QYSFS);				
			g.setIsQueried(result);
			g.setQueryAuthTime(x);
			g.setQueryComputerIP((String)qyzxcxmxs.get(i).get("QueryComputerIP"));
			g.setQueryOrgName((String)qyzxcxmxs.get(i).get("QueryOrgName"));
				
			if(qyzxcxmxs.get(i).get("QueryOrgNo")!=null){
				List<Department> departmentByCode = deptService.getDepartmentByCode((String)qyzxcxmxs.get(i).get("QueryOrgNo"));
				dept1=null;
				if(departmentByCode.size()>0){
					 dept1=departmentByCode.get(0);
				 }
				}
			if(dept1!=null){
				g.setQueryOrgNo(dept1.getDeptName());
				}else{
					g.setQueryOrgNo((String)qyzxcxmxs.get(i).get("QueryOrgNo"));
				}
			g.setQueryTime(y);
			g.setQueryUserName((String)qyzxcxmxs.get(i).get("QueryUserName"));
			g.setQueryUserSysName((String)qyzxcxmxs.get(i).get("QueryUserSysName"));
			g.setUpOrgCode((String)qyzxcxmxs.get(i).get("UpOrgCode"));
			g.setCompanyName((String)qyzxcxmxs.get(i).get("CompanyName"));
			g.setDeptName((String)qyzxcxmxs.get(i).get("DeptName"));
			g.setZzCode((String)qyzxcxmxs.get(i).get("ZzCode"));
			list.add(g);
			}
		}
		Map map = new HashMap();
		map.put("totalProperty", "total," + pager.getTotalRows());
		map.put("root", "rows");		
		return JsonUtil.fromCollections(list, map);	
	}
	//个人查询详情
	@Override
	public Grzxcxmx getGrzxcxmx(Grzxcxmx grzxcxmx) throws Exception {
		 String where=" where 1=1";
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 String y=null;
		 String x=null;
		 Map<String,Object> gr=null;
		 Grzxcxmx g=new Grzxcxmx();
		if(grzxcxmx!=null){
		where+=" and querytime='"+grzxcxmx.getQueryTime()+"' and queryUserSysName='"+grzxcxmx.getQueryUserSysName()+"'";	
		//查询个人详情
        List<Map<String, Object>> grlist = jdbcTemplate.queryForList("select * from grzxcxmx"+grzxcxmx.getQueryTime().substring(0,4)+where);
		//查询个人字典列表
        List<Map<String, Object>> dlist = jdbcTemplate.queryForList("select dic.name,dic.code,dict.code as dcode from  sys_Dictionary as dic,sys_DicType as dict where dic.typeId=dict.id and dict.code in ('"+Constants.GRZJT+"','"+Constants.GRCXV+"','"+Constants.GRCXR+"','"+Constants.GRSFS+"')");
         if(grlist.size()>0){
         gr=grlist.get(0);
         }
         if(gr!=null){
        if(gr.get("QueryAuthTime")!=null){
			 x=sdf.format(gr.get("QueryAuthTime"));
			}
			if(gr.get("QueryTime")!=null){
			y=sdf.format(gr.get("QueryTime"));
			}
			g.setCertNo((String)gr.get("CertNo"));		 
			//查询数据字典对应的个人证件类型
			String result = this.result(dlist,(String)gr.get("CertType"), Constants.GRZJT);
			g.setCertType(result);
			//查询数据字典对应的个人是否查得
            result=this.result(dlist, (String)gr.get("IsQueried"), Constants.GRSFS);
			g.setIsQueried(result);
			//查询数据字典对应的个人查询版本
            result=this.result(dlist, (String)gr.get("QueryFormatName"), Constants.GRCXV);
			g.setQueryFormatName(result);
			//查询数据字典对应的个人查询原因
            result=this.result(dlist, (String)gr.get("QueryReason"), Constants.GRCXR);
			g.setQueryReason(result);
			g.setQueryOrgName((String)gr.get("QueryOrgName"));
			g.setQueryOrgNo((String)gr.get("QueryOrgNo"));
			g.setQueryTime(y);
			g.setQueryUserName((String)gr.get("QueryUserName"));
			g.setQueryUserSysName((String)gr.get("QueryUserSysName"));
			g.setUpOrgCode((String)gr.get("UpOrgCode").toString());			
			g.setQueriedUserName((String)gr.get("QueriedUserName"));
			g.setQueryAuthTime(x);
			g.setQueryComputerIP((String)gr.get("QueryComputerIP"));
         }
		}
		return g;
	}
	//企业查询详情
	@Override
	public Qyzxcxmx getQyzxcxmx(Qyzxcxmx qyzxcxmx) throws Exception {
		 String where=" where 1=1";
		 List<Map<String, Object>> grzxcxmxs=null ;
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 String y=null;
		 String x=null;
		 Map<String,Object> qy=null;
		 Qyzxcxmx g=new Qyzxcxmx();
		 List<Map<String, Object>> dlist = jdbcTemplate.queryForList("select dic.name,dic.code,dict.code as dcode from  sys_Dictionary as dic,sys_DicType as dict where dic.typeId=dict.id and dict.code in ('"+Constants.QYSFS+"')");
		if(qyzxcxmx!=null){
			where+=" and querytime='"+qyzxcxmx.getQueryTime()+"' and ZzCode='"+qyzxcxmx.getZzCode()+"'";
			 List<Map<String, Object>> qylist= jdbcTemplate.queryForList("select * from qyzxcxmx"+qyzxcxmx.getQueryTime().substring(0,4)+where);
			 if(qylist.size()>0){
			   qy=qylist.get(0);
			 }
			 if(qy!=null){
		 if(qy.get("QueryAuthTime")!=null){
			 x=sdf.format(qy.get("QueryAuthTime"));
			}
			if(qy.get("QueryTime")!=null){

			y=sdf.format(qy.get("QueryTime"));
			}
			//企业是否查得
        String result = this.result(dlist, (String)qy.get("IsQueried"), Constants.QYSFS);		
		g.setIsQueried(result);
		g.setQueryAuthTime(x);
		g.setQueryComputerIP((String)qy.get("QueryComputerIP"));
		g.setQueryOrgName((String)qy.get("QueryOrgName"));
		g.setQueryOrgNo((String)qy.get("QueryOrgNo"));
		g.setQueryTime(y);
		g.setQueryUserName((String)qy.get("QueryUserName"));
		g.setQueryUserSysName((String)qy.get("QueryUserSysName"));
		g.setUpOrgCode((String)qy.get("UpOrgCode"));
		g.setCompanyName((String)qy.get("CompanyName"));
		g.setDeptName((String)qy.get("DeptName"));
		g.setZzCode((String)qy.get("ZzCode"));
		}
		}
		return g;
	}
	//个人汇总JSON
	@Override
	public String getGrsummaryJSON(Grsummary grsummary,String departmentId, Page page,
			Person person, Date beginTime, Date endTime) throws Exception {
		List grs = this.getgrsummary(grsummary,departmentId,page,person,beginTime,endTime);
		Map map = new HashMap();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");		
		return JsonUtil.fromCollections(grs, map);
	}
	//个人汇总条件查询
	public List<Grsummary> getgrsummary(Grsummary grsummary,String departmentId, Page page,Person person,Date beginTime,Date endTime) throws Exception {
		List paras = new ArrayList();
		StringBuffer sb = new StringBuffer();
		Department dept=new Department();
		 List<Department> deptChild=new ArrayList();
		List<Department> departmentById = deptService.getDepartmentById(departmentId);
		if(departmentById.size()>0){
			dept=departmentById.get(0);
		}
		sb.append("from Grsummary as grs where 1=1");
		 //当前登录用户所属机构的子机构
		 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
		 deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
		 }
		 //判断登录人身份，！root，只显示自己机构数据
		 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
				 if(deptChild.size()>0){
					 String depts="";
					 for(int i=0;i<deptChild.size();i++){					
					 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";		 
					 }
					 sb.append(" and grs.upOrgCode in ("+depts+"'"+person.getDepartment().getPersonalOrgCode()+"')");
				 }else{
					 sb.append(" and grs.upOrgCode = ?");
					 paras.add(person.getDepartment().getPersonalOrgCode());
				 }
			}
		if(beginTime!=null){
			sb.append(" and grs.queryTime >= ?");
			paras.add(beginTime);
		}
		if(endTime!=null){
			sb.append(" and grs.queryTime <= ?");
			paras.add(DateUtils.modDay(endTime, 0));
		}
		if(grsummary!=null&grsummary.getQueryUserName()!=null&&!grsummary.getQueryUserName().equals("")){
			sb.append(" and grs.queryUserName = ?");
			paras.add(grsummary.getQueryUserName());
		}
		if(dept!=null&&dept.getPersonalOrgCode()!=null){
			sb.append(" and grs.upOrgCode = ?");
			paras.add(dept.getPersonalOrgCode());
		}
		if(grsummary!=null&grsummary.getQueryOrgName()!=null&&!grsummary.getQueryOrgName().equals("")){
			sb.append(" and grs.queryOrgName like ?");
			paras.add("%"+grsummary.getQueryOrgName()+"%");
		}
		sb.append(" order by grs.queryTime desc");
		return find(sb.toString(), paras, page);
	}
	//根据ID获得个人汇总详情
	@Override
	public List<Grsummary> getGrsummary(int id) throws Exception {
        StringBuffer sb=new StringBuffer();
        List paras = new ArrayList();
		sb.append("from Grsummary grs where grs.id=?");
		paras.add(id);
		return this.find(sb.toString(), paras);
	}
	//根据ID获得企业汇总详情
	@Override
	public List<Qysummary> getQysummary(int id) throws Exception {
		StringBuffer sb=new StringBuffer();
        List paras = new ArrayList();
		sb.append("from Qysummary qys where qys.id=?");
		paras.add(id);
		return this.find(sb.toString(), paras);
	}
	//企业汇总JSON
	@Override
	public String getQysummaryJSON(Qysummary qysummary, String departmentId,
			Page page, Person person, Date beginTime, Date endTime)
			throws Exception {
		List grs = this.getqysummary(qysummary,departmentId,page,person,beginTime,endTime);
		Map map = new HashMap();
		map.put("totalProperty", "total," + page.getTotalCount());
		map.put("root", "rows");		
		return JsonUtil.fromCollections(grs, map);
	}
	
	//企业汇总条件查询
	public List<Grsummary> getqysummary(Qysummary qysummary,String departmentId, Page page,Person person,Date beginTime,Date endTime) throws Exception {
		List paras = new ArrayList();
		StringBuffer sb = new StringBuffer();
		Department dept=new Department();
		 List<Department> deptChild=new ArrayList();
		List<Department> departmentById = deptService.getDepartmentById(departmentId);
		if(departmentById.size()>0){
			dept=departmentById.get(0);
		}
		 
		sb.append("from Qysummary as qys where 1=1");
		 //当前登录用户所属机构的子机构
		 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
		 deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
		 }
		 //判断登录人身份，！root，只显示自己机构数据
		 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
				 if(deptChild.size()>0){
					 String depts="";
					 for(int i=0;i<deptChild.size();i++){					
					 depts+="'"+deptChild.get(i).getDeptCode()+"',";		 
					 }
					 sb.append(" and qys.upOrgCode in ("+depts+"'"+person.getDepartment().getDeptCode()+"')");
				 }else{
					 sb.append(" and qys.upOrgCode = ?");
					 paras.add("'"+person.getDepartment().getDeptCode()+"'");
				 }
			}
		if(beginTime!=null){
			sb.append(" and qys.queryTime >= ?");
			paras.add(beginTime);
		}
		if(endTime!=null){
			sb.append(" and qys.queryTime <= ?");
			paras.add(DateUtils.modDay(endTime, 0));
		}
		if(qysummary!=null&qysummary.getQueryUserName()!=null&&!qysummary.getQueryUserName().equals("")){
			sb.append(" and qys.queryUserName like ?");
			paras.add("%"+qysummary.getQueryUserName()+"%");
		}
		if(dept!=null&&dept.getDeptCode()!=null){
			sb.append(" and qys.upOrgCode = ?");
			paras.add(dept.getDeptCode());
		}
		if(qysummary!=null&qysummary.getQueryOrgName()!=null&&!qysummary.getQueryOrgName().equals("")){
			sb.append(" and qys.queryOrgName like ?");
			paras.add("%"+qysummary.getQueryOrgName()+"%");
		}
		sb.append(" order by qys.queryTime desc");
		return find(sb.toString(), paras, page);
	}
	//调用注入数据的存储过程
	@Override
	public void summary() {
		 Calendar now = Calendar.getInstance();  
	      int year=now.get(Calendar.YEAR);
	      int month=now.get(Calendar.MONTH)+1;
	      int day=now.get(Calendar.DAY_OF_MONTH);
	      String days=year+"-"+month+"-"+day;
       this.jdbcTemplate.execute("call qysummary('qyzxcxmx"+year+"','"+days+"')");	
       this.jdbcTemplate.execute("call grsummary('grzxcxmx"+year+"','"+days+"')");		
	}
	//调用建表的存储过程
	@Override
	public void createTable() {
		 Calendar now = Calendar.getInstance();  
	      int year=now.get(Calendar.YEAR);  
       this.jdbcTemplate.execute("call grCreateTable('grzxcxmx"+year+"')");	
       this.jdbcTemplate.execute("call qyCreateTable('qyzxcxmx"+year+"')");	

	}
	//调用插入Createdate的存储过程
	@Override
	public void insert() {
		 Calendar now = Calendar.getInstance();  
	      int year=now.get(Calendar.YEAR);  
       this.jdbcTemplate.execute("call grinsertCreatedate('grzxcxmx"+year+"')");	
       this.jdbcTemplate.execute("call qyinsertCreatedate('qyzxcxmx"+year+"')");	
	}
	@Override
	public void summarytemp() {
		 Calendar now = Calendar.getInstance();  
	      int year=now.get(Calendar.YEAR);
      this.jdbcTemplate.execute("call qysummarytemp('qyzxcxmx"+year+"')");	
      this.jdbcTemplate.execute("call grsummarytemp('grzxcxmx"+year+"')");		
	}
}
