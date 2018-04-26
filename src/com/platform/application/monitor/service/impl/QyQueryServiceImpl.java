package com.platform.application.monitor.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Service;

import com.platform.application.common.domain.Dictionary;
import com.platform.application.common.service.DicTypeService;
import com.platform.application.common.service.DictionaryService;
import com.platform.application.common.util.PropertyUtil;
import com.platform.application.monitor.domain.Grzxcxmx;
import com.platform.application.monitor.domain.Qyzxcxmx;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Person;

import com.platform.application.monitor.service.QyQueryService;
import com.platform.application.operation.domain.SourceOrg;
import com.platform.application.operation.service.OptLogService;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.FileUtilTool;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
@Service
public class QyQueryServiceImpl extends GenericHibernateDao<Qyzxcxmx,String> implements QyQueryService{
	private DicTypeService dicTypeService;
	private OptLogService optLogService;
	private DictionaryService dictionaryService;
    private DepartmentService deptService;
    private AreaService areaService;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Autowired
	public void setDicTypeService(DicTypeService dicTypeService) {
		this.dicTypeService = dicTypeService;
	}
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	@Autowired
	public void setOptLogService(OptLogService optLogService) {
		this.optLogService = optLogService;
	}
	@Autowired
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	@Autowired
	public void setDepartmentService(DepartmentService deptService) {
		this.deptService = deptService;
	}
	//企业机构征信查询统计图
	@Override
	public Map<String,String> departmentCount(String beginTime,String endTime,String departmentId,Person person,String level) throws Exception {
		Map<String,String> maps = new TreeMap<String, String>();
		StringBuffer sb = new StringBuffer();
		 Department dept=new Department();
		 Department dept1=new Department();
		 List<Department> deptChild=new ArrayList();
		 String deptcode="";
            //机构条件查询
		   if(departmentId!=null&&!departmentId.equals("")){
		         List deptcodes = this.find("select deptCode from Department where deptId in ("+departmentId+") or parent.deptId in("+departmentId+")");
		           for(int i=0;i<deptcodes.size();i++){
		        	   if(i!=deptcodes.size()-1){
		        		   deptcode+="'"+deptcodes.get(i)+"',";
		        	   }else{
		        		   deptcode+="'"+deptcodes.get(i)+"'";   
		        	   }
		           }
		         }
//		if(beginTime==null&&endTime==null){
//			   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//			}else if(beginTime.equals("")&&endTime.equals("")){
//			   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//			}
		   //获取当前登录用户所属机构的子机构
			 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
			    deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
				}
			sb.append("select queryOrgNo,sum(total) from  qysummary as qys where 1=1");
        //判断登录用户，！root，只显示自己所属机构及子机构的数据
		 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 if(deptChild.size()>0){
				 String depts="";
				 for(int i=0;i<deptChild.size();i++){					
				 depts+="'"+deptChild.get(i).getDeptCode()+"',"; 
				 }
				 sb.append(" and qys.upOrgCode in ("+depts+"'"+person.getDepartment().getDeptCode()+"')");
			 }else{
				 sb.append(" and qys.upOrgCode = '"+person.getDepartment().getDeptCode()+"'");
			 } 
			}
		 //机构条件查询
//		if( dept.getDeptCode()!=null){
//			 deptChild = deptService.getAllChildren(dept.getDeptId());
//			 if(deptChild.size()>0){
//				 String depts="";
//				 for(int i=0;i<deptChild.size();i++){
//				 depts+="'"+deptChild.get(i).getDeptCode()+"',";
//				 }
//			sb.append(" and upOrgCode in ("+depts+"'"+dept.getDeptCode()+"')");
//			 }else{
//			sb.append(" and upOrgCode='"+dept.getDeptCode()+"'");
//			 }
//		}	
		 if(StringUtil.isNotEmpty(level)){
			 List<Department> departmentByLevel = deptService.getDepartmentByLevel(level);//查询符合级别要求的机构
			 String deptcode_level="";
			 for(int i=0;i<departmentByLevel.size()-1;i++){
				 deptcode_level+="'"+departmentByLevel.get(i).getPersonalOrgCode()+"',";
			 }
			 deptcode_level+="'"+departmentByLevel.get(departmentByLevel.size()-1)+"'";
			 sb.append(" and qys.uporgcode in ("+deptcode_level+") ");
		 }
		if(deptcode!=null&&!deptcode.equals("")){
			sb.append(" and qys.upOrgCode in ("+deptcode+")");
		}
		if(StringUtil.isNotEmpty(beginTime)){
			sb.append(" and qys.queryTime >= '"+beginTime+"' ");
		}
		if(StringUtil.isNotEmpty(endTime)){
			sb.append(" and qys.queryTime <= '"+endTime+"' ");
		}
		sb.append(" group by qys.queryOrgNo ");
		String regex = "[\u4E00-\u9FA5]+";
		List list = this.findSQL(sb.toString());
		if(list.size() > 0){
			for (Object obj : list) {
				Object[] objs = (Object[]) obj;
				if(objs[0]!=null&&objs[1]!=null){
					if(objs[0].toString().matches(regex)){
						maps.put(objs[0].toString(), objs[1].toString());
					}else{
							List<Department> departmentByCode = deptService.getDepartmentByCode(objs[0].toString());
							dept1=null;
							if(departmentByCode.size()>0){
								 dept1=departmentByCode.get(0);
							 }
						if(dept1!=null){
							maps.put(dept1.getDeptName(), objs[1].toString());
						}
					}				}
			}
		}
		return maps;
	}
	//SQL查询
	 public List findSQL(final String sql){
	    	SQLQuery query=  getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session){
					Query q = session.createSQLQuery(sql);
					return q;
				}
			});
	    	return query.list();
	    }
	 //获取所有父节点机构
	private List getAllrootNode() {
		String sql = "from Department dept where 1=1 and dept.deptStatus = '01' and dept.deptType = '01' order by dept.deptOrder asc ";
		List<Department> lists = this.find(sql);
		return lists;
	}
    //企业征信查询年度对比统计图
	@Override
	public Map<String, String> yearCount(String year1,String year2,Person person) throws Exception {
			Map<String, String> maps = null;
			try {
				maps = new TreeMap<String, String>();
				StringBuffer sb = new StringBuffer();
				 List<Department> deptChild=new ArrayList();
				//获取当前登录用户所属机构的子机构
				 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
				    deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
					}
				 sb.append("select queryTime,sum(total) from qysummary where 1=1");
	        //判断登录用户，！root，只显示自己所属机构及子机构的数据
			 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
				 if(deptChild.size()>0){
					 String depts="";
					 for(int i=0;i<deptChild.size();i++){					
					 depts+="'"+deptChild.get(i).getDeptCode()+"',"; 
					 }
					 sb.append(" and upOrgCode in ("+depts+"'"+person.getDepartment().getDeptCode()+"')");
				 }else{
					 sb.append(" and upOrgCode = '"+person.getDepartment().getDeptCode()+"'");
				 } 
				}
				if(StringUtil.isNotEmpty(year1)){
					sb.append(" and queryTime>='"+year1+"-01-01'");
							}
				if(StringUtil.isNotEmpty(year2)){
					sb.append(" and queryTime<='"+year2+"-12-31'");
				}
				sb.append(" group by DATE_FORMAT(querytime,'%Y') order by queryTime");
				List list = this.findSQL(sb.toString());
				if(list.size() > 0){
					for (Object obj : list) {
						Object[] objs = (Object[]) obj;
						if(objs[0]!=null&&objs[1]!=null)
						maps.put(objs[0].toString().substring(0,4),objs[1].toString());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return maps;
	}
    //企业征信查询查询原因统计图
	@Override
	public Map<String, String> resorceCount(String beginTime, String endTime,String qyreason)throws Exception {
		Map<String,String> maps = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append("select queryReason,count(*) from  qyzxcxmx where 1=1");
		if(beginTime==null&&endTime==null){
			   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
			}else if(beginTime.equals("")&&endTime.equals("")){
			   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
			}
		if(StringUtil.isNotEmpty(beginTime)){
			sb.append(" and queryTime >= '"+beginTime+"' ");
		}
		if(StringUtil.isNotEmpty(endTime)){
			sb.append(" and queryTime <= '"+endTime+"' ");
		}
		if(StringUtil.isNotEmpty(qyreason)&&qyreason!=null) {
			List<Dictionary> dicById = dictionaryService.getDicById(qyreason);
			Dictionary dic=dicById.get(0);
			sb.append(" and queryreason="+dic.getCode());
		}
		sb.append(" group by queryReason");
		List list = this.findSQL(sb.toString());	
		List<Dictionary> dictList=dicTypeService.getIdType("qycxr");
		Collections.sort(dictList, new Comparator<Dictionary>(){  
            public int compare(Dictionary o1, Dictionary o2) {  
                if(Integer.parseInt(o1.getCode()) > Integer.parseInt(o2.getCode())){  
                    return 1;  
                }  
                if(Integer.parseInt(o1.getCode()) == Integer.parseInt(o2.getCode())){  
                    return 0;  
                }  
                return -1;  
            }  
        });
		Map<String,String> dicMap = new HashMap<String, String>();
		for(Dictionary dictionary:dictList){
			dicMap.put(dictionary.getCode(), dictionary.getName());
		}
		if(list.size() > 0){
			for (Object obj : list) {
				Object[] objs = (Object[]) obj;
				if(objs[0]!=null&&objs[1]!=null){
				maps.put(dicMap.get(String.valueOf(objs[0])), String.valueOf(objs[1]));
				}
			}
		}
		return maps;
	}
    //数据上报
	public void txUploadRecord(String type, String[] nameParts, File records,SourceOrg sourceOrg)
			throws Exception {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String csvPath=records.getPath();
		System.out.println(csvPath+"============3333333=============="+sd.format(new Date()));
		String x=nameParts[2].substring(0,4);
		String inptsql="load data local infile '"+csvPath.replace("\\", "/")+"' into table "+nameParts[1]+x+" character set 'UTF8' fields terminated by ',' optionally enclosed by '\"' lines terminated by '\r\n' ignore 0 lines;";
		//导入数据，返回导入成功了多少条数据
		System.out.println(inptsql);
		int total=sqlExecutew(inptsql);
		System.out.println(total);
		
		//===================保存操作日志==========================
		optLogService.txSaveLog(type,"数据"+total+"条",sourceOrg);
		
		//========================保持文件到相应目录=================================================
		String savePath = PropertyUtil.getPropertyByKey("SAVE_PATH"); // 文件保存目录
		
		savePath +="/"+ nameParts[0] + "/";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath +="/"+ nameParts[1] + "/";
		savePath +="/"+ ymd + "/";
		
		File savepath=new File(savePath);
		if (!savepath.exists()) {
			savepath.mkdirs();
		}
		FileUtils.copyFile(records, new File(savepath,records.getName()));//保持文件到相应目录
		System.out.println(records.getPath());
		System.out.println(csvPath+"-----------csvPath-------");
		FileUtilTool.deleteFile(csvPath);//删除临时文件
	}
	private int sqlExecutew(final String sql){
    	Object s=  getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createSQLQuery(sql);
				return query.executeUpdate();
			}
		});
    	return Integer.valueOf(s.toString());
    }
	@Override
	public List<Dictionary> getAllReason() throws Exception {
		StringBuffer sb = new StringBuffer();
		List param = new ArrayList(1);
		sb.append("select dic from DicType dict,Dictionary dic where dic.dicType=dict.id and dict.code='qycxr' ");
		return this.find(sb.toString());
	}
	//企业征信查询辖区统计图
	@Override
	public Map<String, String> areaCount(String beginTime, String endTime,
			String areaId, Person person,String level) throws Exception {
		Map<String,String> maps = new TreeMap<String, String>();//存储结果集
		StringBuffer sb = new StringBuffer();//SQL语句
		 String areaIds="";//条件查询辖区ID（包括子辖区）
		 StringBuffer where=new StringBuffer();//where条件
		 List<Area> areaChild=new ArrayList();//子辖区
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 Date now=new Date();
        String month= sdf.format(now);
        //获取当前月份（用于查询分区数据）
        if(month!=null&&!month.equals("")){
        	month=month.substring(5,7);
        }
        //辖区条件查询
        if(areaId!=null&&!areaId.equals("")){
       	 List areafind = this.find("select id from Area where id in("+areaId+") or parent.id in ("+areaId+")");
       	 for(int i=0;i<areafind.size();i++){
         	   if(i!=areafind.size()-1){
         		 areaIds+="'"+areafind.get(i)+"',";
         	   }else{
         		 areaIds+="'"+areafind.get(i)+"'";   
         	   }
            }
        }
		 //登录人所属辖区子辖区
		 if(person.getDepartment()!=null&&person.getDepartment().getArea()!=null&&!person.getDepartment().getDeptId().equals("root")){
		 areaChild = areaService.getAreaChildList(person.getDepartment().getArea());
		 }
		//查询当月数据（默认）
//		if(beginTime==null&&endTime==null){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}else if(beginTime.equals("")&&endTime.equals("")){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}
	        //判断登录用户，！root，只显示自己所属辖区及子辖区的数据
		 if(person.getDepartment().getArea()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 if(areaChild.size()>0){
				 String areas="";
				 for(int i=0;i<areaChild.size();i++){					
					 areas+="'"+areaChild.get(i).getId()+"',";
					 System.out.println(areaChild.get(i).getId());
				 }
				 where.append(" and area.id in ("+areas+"'"+person.getDepartment().getArea().getId()+"')");
			 }else{
				 where.append(" and area.id = '"+person.getDepartment().getArea().getId()+"'"); 
			 }
		 }
		//辖区条件查询
			if(areaIds!=null&&!areaIds.equals("")){
				where.append(" and area.id in ("+areaIds+")");
			}
		//时间条件查询
		if(StringUtil.isNotEmpty(beginTime)){
			where.append(" and qysummary.queryTime >= '"+beginTime+"' ");
		}
		if(StringUtil.isNotEmpty(endTime)){
			where.append(" and qysummary.queryTime <= '"+endTime+"' ");
		}
		//级别查询
		if(level!=null&&!level.equals("")){
			where.append(" and area.levels= '"+level+"'");
		}
		sb.append("select name,sum(total) from (select area.name,qysummary.* from qysummary,sys_area as area,sys_department as dept where 1=1"+where+" and qysummary.uporgcode=dept.deptcode and area.id=dept.areaid) as aa group by name");
		System.out.println(sb);
		List list = this.findSQL(sb.toString());
		if(list.size() > 0){
			for (Object obj : list) {
				Object[] objs = (Object[]) obj;
				if(objs[0]!=null&&objs[1]!=null){
				maps.put(objs[0].toString(), objs[1].toString());
				}
			}
		}
		return maps;
	}
	//企业用户查询统计图
	@Override
	public Map<String, String> personCount(String beginTime, String endTime,
			String departmentId, Person person) throws Exception {
		Map<String,String> maps = new TreeMap<String, String>();
		StringBuffer sb = new StringBuffer();
		 Department dept=new Department();
		 List<Department> deptChild=new ArrayList();
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 Date now=new Date();
		 String deptCode="";
       String month= sdf.format(now);
       //获取当前月份（方便QYZXCXMX表分区数据查询）
       if(month!=null&&!month.equals("")){
       	month=month.substring(5,7);
       }
       //机构条件查询
       if(departmentId!=null&&!departmentId.equals("")){
        	 List deptfind = this.find("select deptCode from Department where deptId in ("+departmentId+") or parent.deptId in("+departmentId+")");
        	 for(int i=0;i<deptfind.size();i++){
          	   if(i!=deptfind.size()-1){
          		   deptCode+="'"+deptfind.get(i)+"',";
          	   }else{
          		   deptCode+="'"+deptfind.get(i)+"'";   
          	   }
             }
         }
		 //获取当前登录用户所属机构的子机构
		 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
		 deptChild = deptService.getAllChildren(person.getDepartment().getDeptId());
		 }
		sb.append("select queryusername,sum(total) from  qysummary where 1=1 ");
//		if(beginTime==null&&endTime==null){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}else if(beginTime.equals("")&&endTime.equals("")){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}
        //判断登录用户，！root，只显示自己所属机构及子机构的数据
		 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 if(deptChild.size()>0){
				 String depts="";
				 for(int i=0;i<deptChild.size();i++){					
				 depts+="'"+deptChild.get(i).getDeptCode()+"',";
				 }
				 sb.append(" and upOrgCode in ("+depts+"'"+person.getDepartment().getDeptCode()+"')");
		     }else{
				 sb.append(" and upOrgCode = '"+person.getDepartment().getDeptCode()+"'");
			      }   
		}
		 //机构条件查询（选择机构机构下所有用户查询统计）
//		if( dept.getDeptCode()!=null){
//			 deptChild = deptService.getAllChildren(dept.getDeptId());
//			 if(deptChild.size()>0){
//				 String depts="";
//				 for(int i=0;i<deptChild.size();i++){
//				 depts+="'"+deptChild.get(i).getDeptCode()+"',";
//				 }
//			sb.append(" and upOrgCode in ("+depts+"'"+dept.getDeptCode()+"')");
//			 }else{
//			sb.append(" and upOrgCode='"+dept.getDeptCode()+"'");
//			 }
//		}	
		 //机构条件查询（选择机构机构下所有用户查询统计）
	        if(deptCode!=null&&!deptCode.equals("")){
	        	sb.append(" and upOrgCode in ("+deptCode+")");
	        }
		if(StringUtil.isNotEmpty(beginTime)){
			sb.append(" and queryTime >= '"+beginTime+"' ");
		}
		if(StringUtil.isNotEmpty(endTime)){
			sb.append(" and queryTime <= '"+endTime+"' ");
		}
		sb.append(" group by queryusername ");
		System.out.println(sb);
		List list = this.findSQL(sb.toString());
		if(list.size() > 0){
			for (Object obj : list) {
				Object[] objs = (Object[]) obj;
				if(objs[0]!=null&&objs[1]!=null){
				maps.put(objs[0].toString(), objs[1].toString());
				}
			}
		}
		return maps;
	}
}
