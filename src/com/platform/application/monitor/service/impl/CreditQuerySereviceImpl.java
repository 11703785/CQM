package com.platform.application.monitor.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import antlr.StringUtils;


import org.springframework.jdbc.core.JdbcTemplate;
import com.platform.application.common.domain.DicType;
import com.platform.application.common.domain.Dictionary;
import com.platform.application.common.service.DicTypeService;
import com.platform.application.common.service.DictionaryService;

import com.platform.application.monitor.domain.Grzxcxmx;
import com.platform.application.monitor.service.CreditQueryService;
import com.platform.application.sysmanage.service.AreaService;
import com.platform.application.sysmanage.service.DepartmentService;
import com.platform.framework.common.util.StringUtil;
import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Department;
import com.platform.application.sysmanage.domain.Person;
import com.platform.application.sysmanage.vo.TreeNode;
import com.platform.framework.common.util.JsonUtil;
import com.platform.framework.core.dao.impl.GenericHibernateDao;
@Service
public class CreditQuerySereviceImpl extends GenericHibernateDao<Grzxcxmx,String> implements CreditQueryService {
	private DepartmentService departmentService;
	private DicTypeService dicTypeService;
	private DictionaryService dictionaryService;
	private AreaService areaService;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	@Autowired
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	@Autowired
	public void setDicTypeService(DicTypeService dicTypeService) {
		this.dicTypeService = dicTypeService;
	}
	@Autowired
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
    //机构查询统计图
	@Override
	public Map<String,String> departmentCount(String beginTime,String endTime,String departmentId,Person person,String level) throws Exception {
		 Map<String,String> maps = new TreeMap<String, String>();
		 StringBuffer sb = new StringBuffer();
		 Department dept=new Department();
		 Department dept1=new Department();
		 List<Department> deptChild=new ArrayList();
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 String deptcode_p="";
		 Date now=new Date();
         String month= sdf.format(now);
         //机构条件查询
         if(departmentId!=null&&!departmentId.equals("")){
         List septcode_ps = this.find("select personalOrgCode from Department where deptId in ("+departmentId+") or parent.deptId in("+departmentId+")");
           for(int i=0;i<septcode_ps.size();i++){
        	   if(i!=septcode_ps.size()-1){
        	   deptcode_p+="'"+septcode_ps.get(i)+"',";
        	   }else{
            	   deptcode_p+="'"+septcode_ps.get(i)+"'";   
        	   }
           }
         }
         //当前月份（方便直接查询GRZXCXMX表分区数据）
         if(month!=null&&!month.equals("")){
        	month=month.substring(5,7);
         }
         //查询机构
		 List<Department> departmentById = departmentService.getDepartmentById(departmentId);
		  if(departmentById.size()>0){
			 dept=departmentById.get(0);
		 }
		
		//默认只查当前月份数据
//		if(beginTime==null&&endTime==null){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}else if(beginTime.equals("")&&endTime.equals("")){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}
		  //当前登录用户所属机构的子机构
			 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 deptChild = departmentService.getAllChildren(person.getDepartment().getDeptId());
			 }
			 sb.append("select queryOrgNo,sum(total) from  grsummary as grs where 1=1 ");
         //判断登录用户，！root，只显示自己所属机构及子机构的数据
		 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 if(deptChild.size()>0){
				 String depts="";
				 for(int i=0;i<deptChild.size();i++){					
				 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";		 
				 }
				 sb.append(" and grs.upOrgCode in ("+depts+"'"+person.getDepartment().getPersonalOrgCode()+"')");
			 }else{
				 sb.append(" and grs.upOrgCode = '"+person.getDepartment().getPersonalOrgCode()+"'");
			 }
			}
		 //机构条件查询
//		if( dept.getPersonalOrgCode()!=null){
//			 deptChild = departmentService.getAllChildren(dept.getDeptId());
//			 if(deptChild.size()>0){
//				 String depts="";
//				 for(int i=0;i<deptChild.size();i++){
//				 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";
//				 }
//			sb.append(" and upOrgCode in ("+depts+"'"+dept.getPersonalOrgCode()+"')");
//			 }else{
//			sb.append(" and upOrgCode='"+dept.getPersonalOrgCode()+"'");
//			 }
//		}	
		 if(StringUtil.isNotEmpty(level)){
			 List<Department> departmentByLevel = departmentService.getDepartmentByLevel(level);//查询符合级别要求的机构
			 String deptcode_level="";
			 for(int i=0;i<departmentByLevel.size()-1;i++){
				 deptcode_level+="'"+departmentByLevel.get(i).getPersonalOrgCode()+"',";
			 }
			 deptcode_level+="'"+departmentByLevel.get(departmentByLevel.size()-1)+"'";
			 sb.append(" and grs.uporgcode in ("+deptcode_level+") ");
		 }
		if(StringUtil.isNotEmpty(beginTime)){
			sb.append(" and grs.queryTime >= '"+beginTime+"' ");
		}
		if(StringUtil.isNotEmpty(endTime)){
			sb.append(" and grs.queryTime <= '"+endTime+"' ");
		}
		 if(deptcode_p!=null&&!deptcode_p.equals("")){
				sb.append(" and grs.uporgcode in ("+deptcode_p+") "); 
		 }
		sb.append(" group by grs.queryOrgNo ");
		System.out.println(sb);
		String regex = "[\u4E00-\u9FA5]+";
		List list = this.findSQL(sb.toString());
		if(list.size() > 0){
			for (Object obj : list) {
				Object[] objs = (Object[]) obj;
				if(objs[0]!=null&&objs[1]!=null){
					if(objs[0].toString().matches(regex)){
						maps.put(objs[0].toString(), objs[1].toString());
					}else{
							List<Department> departmentByCode = departmentService.getDepartmentByCode(objs[0].toString());
							dept1=null;
							if(departmentByCode.size()>0){
								 dept1=departmentByCode.get(0);
							 }
						if(dept1!=null){
							maps.put(dept1.getDeptName(), objs[1].toString());
						}
					}
					
					}
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
	//获得所有父节点机构 
	private List getAllrootNode() {
		String sql = "from Department dept where 1=1 and dept.deptStatus = '01' and dept.deptType = '01' order by dept.deptOrder asc ";
		List<Department> lists = this.find(sql);
		return lists;
	}

    //走势图
	@Override
	public Map<String, List> yearCount(String year1,String year2,Person person,String type,String time,List times,String dept,String area,String level) throws Exception {			
		   List<String> querytimelist=new ArrayList<String>();
	        List<String> sumlist=new ArrayList<String>();	
		Map<String,List> maps = new HashMap<String, List>();
				StringBuffer where = new StringBuffer();
				StringBuffer groupBy=new StringBuffer();
				 String deptcode_p="";//机构条件查询
				 String areaIds="";//条件查询辖区ID（包括子辖区）
				 List<Department> deptChild=new ArrayList();//子机构
				 List<Area> areaChild=new ArrayList();//子辖区
				 List keys=new ArrayList();
				 List valuestemp=new ArrayList();
				 List valuesList=new ArrayList();
                  int k=0;//循环为没有数据的日期填充0
				 Calendar now = Calendar.getInstance();  
			      int year=now.get(Calendar.YEAR);
			      int month=now.get(Calendar.MONTH)+1;
			      String  months=null;
			      if(month<10){
			    	 months="-0"+month;
			      }else{
				    	 months="-"+month;
			      }
			      int day=now.get(Calendar.DATE);
				 //获取当前登录用户所属机构的子机构
				 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
				 deptChild = departmentService.getAllChildren(person.getDepartment().getDeptId());
				 }
				 //登录用户所属辖区子辖区
				 if(person.getDepartment().getArea()!=null&&!person.getDepartment().getDeptId().equals("root")){
				 areaChild = areaService.getAreaChildList(person.getDepartment().getArea());
				 } 
				 //机构条件查询
				 if(dept!=null&&!dept.equals("")){
			         List septcode_ps = this.find("select personalOrgCode from Department where deptId in ("+dept+") or parent.deptId in("+dept+")");
			           for(int i=0;i<septcode_ps.size();i++){
			        	   if(i!=septcode_ps.size()-1){
			        	   deptcode_p+="'"+septcode_ps.get(i)+"',";
			        	   }else{
			            	   deptcode_p+="'"+septcode_ps.get(i)+"'";   
			        	   }
			           }
			         }
				   //条件查询辖区ID
		         if(area!=null&&!area.equals("")){
		        	 List areafind = this.find("select id from Area where id in("+area+") or parent.id in ("+area+")");
		        	 for(int i=0;i<areafind.size();i++){
		          	   if(i!=areafind.size()-1){
		          		 areaIds+="'"+areafind.get(i)+"',";
		          	   }else{
		          		 areaIds+="'"+areafind.get(i)+"'";   
		          	   }
		             }
		         }
				 try {
				 //类型默认为全省走势图
			     if(type==null||type.equals("")){
			    	  //判断登录用户，！root，只显示自己所属机构及子机构的数据
					 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
						 if(deptChild.size()>0){
							 String depts="";//所有的子机构和本机构
							 for(int i=0;i<deptChild.size();i++){					
							 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";
							 }
							 where.append(" and upOrgCode in ("+depts+"'"+person.getDepartment().getPersonalOrgCode()+"')");
					     }else{
					    	 where.append(" and upOrgCode = '"+person.getDepartment().getPersonalOrgCode()+"'");
						      }   
					}
					 if(StringUtil.isNotEmpty(time)){
							if(time.equals("0")){
								if(times.size()>0){
									where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");
								}else{
									where.append(" and queryTime>='"+year+months+"-01'");
								}
								groupBy.append(" group by queryTime order by queryTime");
							}else if(time.equals("1")){
								if(times.size()>0){
									where.append(" and querytime>='"+times.get(0)+"-01' and queryTime<='"+times.get(times.size()-1)+"-31'");
								}else{
									where.append(" and queryTime>='"+year+"-01-01'");
								}
								groupBy.append(" group by DATE_FORMAT(querytime,\'%Y-%m\') order by queryTime");
							}else if(time.equals("2")){
								where.append(" and querytime>='"+times.get(0)+"-01-01' and queryTime<='"+times.get(times.size()-1)+"-12-31'");
								groupBy.append(" group by DATE_FORMAT(querytime,\'%Y\') order by queryTime");

							}
								}else{
									if(times!=null){
										where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");	
									}else{
										where.append(" and queryTime>='"+year+months+"-01'");
									}
									groupBy.append(" group by queryTime order by queryTime");
								}
						if(time==null||time.equals("0")||time.equals("")){
						valuestemp=this.findSQL("select querytime,sum(total) from grsummary where 1=1"+where+groupBy);
						if(valuestemp.size() > 0){
							for (Object obj : valuestemp) {
								Object[] objs = (Object[]) obj;
								if(objs[0]!=null&&objs[1]!=null){
		                        querytimelist.add(objs[0].toString());
		                        sumlist.add(objs[1].toString());
								}
							}
						}
						 List values=new ArrayList();
						for(int j=0;j<times.size();j++){
							if(querytimelist.size()>0){
							if(times.get(j).toString().equals(querytimelist.get(k).toString())){
							    values.add(sumlist.get(k).toString());
							    if(k<querytimelist.size()-1){
								k++;
							    }
							}else{
								values.add("0");
							}
							}
						}
						
						valuesList.add(values);
					    keys.add("陕西省");
						}else
						if(time!=null&&time.equals("1")){
							valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y-%m\'),sum(total) from grsummary where 1=1"+where+groupBy);
							if(valuestemp.size() > 0){
								for (Object obj : valuestemp) {
									Object[] objs = (Object[]) obj;
									if(objs[0]!=null&&objs[1]!=null){
			                        querytimelist.add(objs[0].toString());
			                        sumlist.add(objs[1].toString());
									}
								}
							}
							 List values=new ArrayList();
							 for(int j=0;j<times.size();j++){
									if(querytimelist.size()>0){
									if(times.get(j).toString().equals(querytimelist.get(k).toString())){
									    values.add(sumlist.get(k).toString());
									    if(k<querytimelist.size()-1){
										k++;
									    }
									}else{
										values.add("0");
									}
									}
								}
							valuesList.add(values);
						    keys.add("陕西省");	
						}else if(time!=null&&time.equals("2")){
							valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y\'),sum(total) from grsummary where 1=1"+where+groupBy);
							if(valuestemp.size() > 0){
								for (Object obj : valuestemp) {
									Object[] objs = (Object[]) obj;
									if(objs[0]!=null&&objs[1]!=null){
			                        querytimelist.add(objs[0].toString());
			                        sumlist.add(objs[1].toString());
									}
								}
							}
							 List values=new ArrayList();
							 for(int j=0;j<times.size();j++){
									if(querytimelist.size()>0){
									if(times.get(j).toString().equals(querytimelist.get(k).toString())){
									    values.add(sumlist.get(k).toString());
									    if(k<querytimelist.size()-1){
										k++;
									    }
									}else{
										values.add("0");
									}
									}
								}
							valuesList.add(values);
						    keys.add("陕西省");
						}
			     }
				 else if(type.equals("0")){
			    //判断登录用户，！root，只显示自己所属机构及子机构的数据
				 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
					 if(deptChild.size()>0){
						 String depts="";//所有的子机构和本机构
						 for(int i=0;i<deptChild.size();i++){					
						 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";
						 }
						 where.append(" and upOrgCode in ("+depts+"'"+person.getDepartment().getPersonalOrgCode()+"')");
				     }else{
				    	 where.append(" and upOrgCode = '"+person.getDepartment().getPersonalOrgCode()+"'");
					      }   
				}
				 //时间类型为空时默认为按天显示，X轴为当月每天
				if(StringUtil.isNotEmpty(time)){
					if(time.equals("0")){
						if(times.size()>0){
							where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");
						}else{
							where.append(" and queryTime>='"+year+months+"-01'");
						}
						groupBy.append(" group by queryTime order by queryTime");
					}else if(time.equals("1")){
						if(times.size()>0){
							where.append(" and querytime>='"+times.get(0)+"-01' and queryTime<='"+times.get(times.size()-1)+"-31'");
						}else{
							where.append(" and queryTime>='"+year+"-01-01'");
						}
						groupBy.append(" group by DATE_FORMAT(querytime,\'%Y-%m\') order by queryTime");
					}else if(time.equals("2")){
						where.append(" and querytime>='"+times.get(0)+"-01-01' and queryTime<='"+times.get(times.size()-1)+"-12-31'");
						groupBy.append(" group by DATE_FORMAT(querytime,\'%Y\') order by queryTime");

					}
						}else{
							if(times!=null){
								where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");	
							}else{
								where.append(" and queryTime>='"+year+months+"-01'");
							}
							groupBy.append(" group by queryTime order by queryTime");
						}
				if(deptcode_p!=null&&!deptcode_p.equals("")){
                	where.append(" and uporgcode in ("+deptcode_p+")");
				}
				 if(StringUtil.isNotEmpty(level)){
					 List<Department> departmentByLevel = departmentService.getDepartmentByLevel(level);//查询符合级别要求的机构
					 String deptcode_level="";
					 for(int i=0;i<departmentByLevel.size()-1;i++){
						 deptcode_level+="'"+departmentByLevel.get(i).getPersonalOrgCode()+"',";
					 }
					 deptcode_level+="'"+departmentByLevel.get(departmentByLevel.size()-1)+"'";
					 where.append(" and uporgcode in ("+deptcode_level+") ");
				 }
				keys = this.findSQL("select queryorgno from grsummary where 1=1 "+where+" group by queryorgno order by queryTime");
				if(keys.size()>0){
					for(int i=0;i<keys.size();i++){
				String x="";
				x="where queryorgno='"+keys.get(i)+"'";
				String regex = "[\u4E00-\u9FA5]+";
				if(keys.get(i).toString().matches(regex)){
					keys.set(i, keys.get(i).toString());
				}else{
						List<Department> departmentByCode = departmentService.getDepartmentByCode(keys.get(i).toString());
						if(departmentByCode.size()>0){
							keys.set(i, departmentByCode.get(0).getDeptName());
						}
				}
				if(time==null||time.equals("0")||time.equals("")){
				valuestemp=this.findSQL("select querytime,sum(total) from grsummary "+x+where+groupBy);
				if(valuestemp.size() > 0){
					for (Object obj : valuestemp) {
						Object[] objs = (Object[]) obj;
						if(objs[0]!=null&&objs[1]!=null){
                        querytimelist.add(objs[0].toString());
                        sumlist.add(objs[1].toString());
						}
					}
				}
				 List values=new ArrayList();
				 for(int j=0;j<times.size();j++){
						if(querytimelist.size()>0){
						if(times.get(j).toString().equals(querytimelist.get(k).toString())){
						    values.add(sumlist.get(k).toString());
						    if(k<querytimelist.size()-1){
							k++;
						    }
						}else{
							values.add("0");
						}
						}
					}
				valuesList.add(values);
					}else if(time!=null&&time.equals("1")){
						valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y-%m\'),sum(total) from grsummary "+x+where+groupBy);
						if(valuestemp.size() > 0){
							for (Object obj : valuestemp) {
								Object[] objs = (Object[]) obj;
								if(objs[0]!=null&&objs[1]!=null){
		                        querytimelist.add(objs[0].toString());
		                        sumlist.add(objs[1].toString());
								}
							}
						}
						 List values=new ArrayList();
						 for(int j=0;j<times.size();j++){
								if(querytimelist.size()>0){
								if(times.get(j).toString().equals(querytimelist.get(k).toString())){
								    values.add(sumlist.get(k).toString());
								    if(k<querytimelist.size()-1){
									k++;
								    }
								}else{
									values.add("0");
								}
								}
							}
						valuesList.add(values);
					}else if(time!=null&&time.equals("2")){
						valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y\'),sum(total) from grsummary "+x+where+groupBy);
						if(valuestemp.size() > 0){
							for (Object obj : valuestemp) {
								Object[] objs = (Object[]) obj;
								if(objs[0]!=null&&objs[1]!=null){
		                        querytimelist.add(objs[0].toString());
		                        sumlist.add(objs[1].toString());
								}
							}
						}
						 List values=new ArrayList();
						 for(int j=0;j<times.size();j++){
								if(querytimelist.size()>0){
								if(times.get(j).toString().equals(querytimelist.get(k).toString())){
								    values.add(sumlist.get(k).toString());
								    if(k<querytimelist.size()-1){
									k++;
								    }
								}else{
									values.add("0");
								}
								}
							}
						valuesList.add(values);	
					}
					}
				}
				 }
			     //辖区走势图
				 else if(type.equals("1")){
				        //判断登录用户，！root，只显示自己所属机构及子机构的数据
						 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
							 if(areaChild.size()>0){
								 String areas="";
								 for(int i=0;i<areaChild.size();i++){					
									areas+="'"+areaChild.get(i).getId()+"',";
								 }
								 where.append(" and upOrgCode in (select deptCode_P from sys_department where areaid in ("+areas+"'"+person.getDepartment().getArea().getId()+"'))");
						     }else{
						    	 where.append(" and upOrgCode = (select deptCode_P from sys_department where areaid='"+person.getDepartment().getArea().getId()+"')");
							      }   
						}
						if(StringUtil.isNotEmpty(time)){
							if(time.equals("0")){
								if(times.size()>0){
									where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");
								}else{
									where.append(" and queryTime>='"+year+months+"-01'");
								}
								groupBy.append(" group by queryTime order by queryTime");
							}else if(time.equals("1")){
								if(times.size()>0){
									where.append(" and querytime>='"+times.get(0)+"-01' and queryTime<='"+times.get(times.size()-1)+"-31'");
								}else{
									where.append(" and queryTime>='"+year+"-01-01'");
								}
								groupBy.append(" group by DATE_FORMAT(querytime,\'%Y-%m\') order by queryTime");
							}else if(time.equals("2")){
								where.append(" and querytime>='"+times.get(0)+"-01-01' and queryTime<='"+times.get(times.size()-1)+"-12-31'");
								groupBy.append(" group by DATE_FORMAT(querytime,\'%Y\') order by queryTime");
							}
								}else{
									if(times!=null){
										where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");	
									}else{
										where.append(" and queryTime>='"+year+months+"-01'");
									}
									groupBy.append(" group by queryTime order by queryTime");
								}
						if(areaIds!=null&&!areaIds.equals("")){
							 where.append(" and upOrgCode in (select deptCode_P from sys_department where areaid in ("+areaIds+"))");	
						}
						//级别查询
						if(level!=null&&!level.equals("")){
							if(level.equals("0")){
							where.append(" and upOrgCode in (select deptCode_P from sys_department where length(areaid)=6)");
							}else{
								where.append(" and upOrgCode in (select deptCode_P from sys_department where length(areaid)=9)");	
							}
							}
						keys = this.findSQL("select uporgcode from grsummary where 1=1 "+where+" group by queryorgno order by queryTime");
							 if(keys.size()>0){
								 String areas="";
								 for(int i=0;i<keys.size();i++){					
									areas+="'"+keys.get(i)+"',";
								 }
								 keys = this.findSQL("select areaid from sys_department where deptcode_p in ("+areas+"'"+person.getDepartment().getPersonalOrgCode()+"') group by areaid");
						     }
							 if(keys.size()>0){
									for(int i=0;i<keys.size();i++){
										String x="";
										x="where uporgcode in ( select deptcode_p from sys_department where areaid='"+keys.get(i)+"')";
										List<Area> areaById = areaService.getAreaById(keys.get(i).toString());
										keys.set(i, areaById.get(0).getName());
										if(time==null||time.equals("0")||time.equals("")){
										valuestemp=this.findSQL("select querytime,sum(total) from grsummary "+x+where+groupBy);
										if(valuestemp.size() > 0){
											for (Object obj : valuestemp) {
												Object[] objs = (Object[]) obj;
												if(objs[0]!=null&&objs[1]!=null){
						                        querytimelist.add(objs[0].toString());
						                        sumlist.add(objs[1].toString());
												}
											}
										}
										 List values=new ArrayList();
										 for(int j=0;j<times.size();j++){
												if(querytimelist.size()>0){
												if(times.get(j).toString().equals(querytimelist.get(k).toString())){
												    values.add(sumlist.get(k).toString());
												    if(k<querytimelist.size()-1){
													k++;
												    }
												}else{
													values.add("0");
												}
												}
											}									
										valuesList.add(values);						
											}else if(time!=null&&time.equals("1")){
												valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y-%m\'),sum(total) from grsummary "+x+where+groupBy);
												if(valuestemp.size() > 0){
													for (Object obj : valuestemp) {
														Object[] objs = (Object[]) obj;
														if(objs[0]!=null&&objs[1]!=null){
								                        querytimelist.add(objs[0].toString());
								                        sumlist.add(objs[1].toString());
														}
													}
												}
												 List values=new ArrayList();
												 for(int j=0;j<times.size();j++){
														if(querytimelist.size()>0){
														if(times.get(j).toString().equals(querytimelist.get(k).toString())){
														    values.add(sumlist.get(k).toString());
														    if(k<querytimelist.size()-1){
															k++;
														    }
														}else{
															values.add("0");
														}
														}
													}								
												valuesList.add(values);	
									}else if(time!=null&&time.equals("2")){
										valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y\'),sum(total) from grsummary "+x+where+groupBy);
										if(valuestemp.size() > 0){
											for (Object obj : valuestemp) {
												Object[] objs = (Object[]) obj;
												if(objs[0]!=null&&objs[1]!=null){
						                        querytimelist.add(objs[0].toString());
						                        sumlist.add(objs[1].toString());
												}
											}
										}
										 List values=new ArrayList();
										 for(int j=0;j<times.size();j++){
												if(querytimelist.size()>0){
												if(times.get(j).toString().equals(querytimelist.get(k).toString())){
												    values.add(sumlist.get(k).toString());
												    if(k<querytimelist.size()-1){
													k++;
												    }
												}else{
													values.add("0");
												}
												}
											}							
										valuesList.add(values);		
									}
									}
							 }
						 }//地市级总走势图
				          else if(type.equals("2")){
						        //判断登录用户，！root，只显示自己所属机构及子机构的数据
							 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
								 if(areaChild.size()>0){
									 String areas="";
									 for(int i=0;i<areaChild.size();i++){					
										areas+="'"+areaChild.get(i).getId()+"',";
									 }
									 where.append(" and upOrgCode in (select deptCode_P from sys_department where areaid in ("+areas+"'"+person.getDepartment().getArea().getId()+"'))");
							     }else{
							    	 where.append(" and upOrgCode = (select deptCode_P from sys_department where areaid='"+person.getDepartment().getArea().getId()+"')");
								      }   
							}
							if(StringUtil.isNotEmpty(time)){
								if(time.equals("0")){
									if(times.size()>0){
										where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");
									}else{
										where.append(" and queryTime>='"+year+months+"-01'");
									}
									groupBy.append(" group by queryTime order by queryTime");
								}else if(time.equals("1")){
									if(times.size()>0){
										where.append(" and querytime>='"+times.get(0)+"-01' and queryTime<='"+times.get(times.size()-1)+"-31'");
									}else{
										where.append(" and queryTime>='"+year+"-01-01'");
									}
									groupBy.append(" group by DATE_FORMAT(querytime,\'%Y-%m\') order by queryTime");
								}else if(time.equals("2")){
									where.append(" and querytime>='"+times.get(0)+"-01-01' and queryTime<='"+times.get(times.size()-1)+"-12-31'");
									groupBy.append(" group by DATE_FORMAT(querytime,\'%Y\') order by queryTime");
								}
									}else{
										if(times!=null){
											where.append(" and queryTime>='"+times.get(0)+"' and queryTime<='"+times.get(times.size()-1)+"'");	
										}else{
											where.append(" and queryTime>='"+year+months+"-01'");
										}
										groupBy.append(" group by queryTime order by queryTime");
									}
							if(areaIds!=null&&!areaIds.equals("")){
								 where.append(" and upOrgCode in (select deptCode_P from sys_department where areaid in ("+areaIds+"))");	
							}
							List<Area> keystemp=new ArrayList<Area>();
							keystemp = areaService.getFirstArea();
								 if(keystemp.size()>0){
										for(int i=0;i<keystemp.size();i++){
											String x="";
											List areaChildList = areaService.getChildrenId(keystemp.get(i).getId());
											String areaChild1="";
											if(areaChildList.size()>0){
											for(int j=0;j<areaChildList.size();j++){
												if(j!=areaChildList.size()-1){
													areaChild1+="'"+areaChildList.get(j)+"',";
												}else 
												{
													areaChild1+="'"+areaChildList.get(j)+"','"+keystemp.get(i).getId()+"'";	
												}
											}}else{
												areaChild1+="'"+keystemp.get(i).getId()+"'";
											}
											
											x="where uporgcode in ( select sys_department.deptcode_p from sys_department,sys_area where sys_department.areaid in("+areaChild1+") and sys_area.id=sys_department.areaid)";
											keys.add( keystemp.get(i).getName());
											if(time==null||time.equals("0")||time.equals("")){
											valuestemp=this.findSQL("select querytime,sum(total) from grsummary "+x+where+groupBy);
											if(valuestemp.size() > 0){
												for (Object obj : valuestemp) {
													Object[] objs = (Object[]) obj;
													if(objs[0]!=null&&objs[1]!=null){
							                        querytimelist.add(objs[0].toString());
							                        sumlist.add(objs[1].toString());
													}
												}
											}
											 List values=new ArrayList();
											 for(int j=0;j<times.size();j++){
													if(querytimelist.size()>0){
													if(times.get(j).toString().equals(querytimelist.get(k).toString())){
													    values.add(sumlist.get(k).toString());
													    if(k<querytimelist.size()-1){
														k++;
													    }
													}else{
														values.add("0");
													}
													}
												}									
											valuesList.add(values);						
												}else if(time!=null&&time.equals("1")){
													valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y-%m\'),sum(total) from grsummary "+x+where+groupBy);
													if(valuestemp.size() > 0){
														for (Object obj : valuestemp) {
															Object[] objs = (Object[]) obj;
															if(objs[0]!=null&&objs[1]!=null){
									                        querytimelist.add(objs[0].toString());
									                        sumlist.add(objs[1].toString());
															}
														}
													}
													 List values=new ArrayList();
													 for(int j=0;j<times.size();j++){
															if(querytimelist.size()>0){
															if(times.get(j).toString().equals(querytimelist.get(k).toString())){
															    values.add(sumlist.get(k).toString());
															    if(k<querytimelist.size()-1){
																k++;
															    }
															}else{
																values.add("0");
															}
															}
														}								
													valuesList.add(values);	
										}else if(time!=null&&time.equals("2")){
											valuestemp=this.findSQL("select DATE_FORMAT(querytime,\'%Y\'),sum(total) from grsummary "+x+where+groupBy);
											if(valuestemp.size() > 0){
												for (Object obj : valuestemp) {
													Object[] objs = (Object[]) obj;
													if(objs[0]!=null&&objs[1]!=null){
							                        querytimelist.add(objs[0].toString());
							                        sumlist.add(objs[1].toString());
													}
												}
											}
											 List values=new ArrayList();
											 for(int j=0;j<times.size();j++){
													if(querytimelist.size()>0){
													if(times.get(j).toString().equals(querytimelist.get(k).toString())){
													    values.add(sumlist.get(k).toString());
													    if(k<querytimelist.size()-1){
														k++;
													    }
													}else{
														values.add("0");
													}
													}
												}							
											valuesList.add(values);		
										}
										}
								 }
							 }
				 maps.put("keys", keys);
				 maps.put("values", valuesList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return maps;
	}
	//查询原因统计图
	@Override
	public Map<String, String> resorceCount(String beginTime, String endTime,String grreason,Person person)throws Exception {
		Map<String,String> maps = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append("select queryReason,count(*) from  grzxcxmx where 1=1");
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
		if(StringUtil.isNotEmpty(grreason)&&grreason!=null) {
			List<Dictionary> dicById = dictionaryService.getDicById(grreason);
			Dictionary dic=dicById.get(0);
			sb.append(" and queryreason="+dic.getCode());
		}	
		sb.append(" group by queryReason");
		List list = this.findSQL(sb.toString());
		//查询数据字典中的个人查询原因
		List<Dictionary> dictList=dicTypeService.getIdType("grcxr");
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
	//查询原因
	@Override
	public List<Dictionary> getAllReason() throws Exception {
		StringBuffer sb = new StringBuffer();
		List param = new ArrayList(1);
		sb.append("select dic from DicType dict,Dictionary dic where dic.dicType=dict.id and dict.code='grcxr' ");	
		return this.find(sb.toString()); 
	}
	//辖区查询统计图
	@Override
	public Map<String, String> areaCount(String beginTime, String endTime,
			String areaId, Person person,String level) throws Exception {
		 Map<String,String> maps = new TreeMap<String, String>();//存储结果集
		 StringBuffer sb = new StringBuffer();//SQL语句
		 Area area=null;
		 StringBuffer where=new StringBuffer();//where条件
		 List<Area> areaChild=new ArrayList();//子辖区
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 Date now=new Date();
		 String areaIds="";//条件查询辖区ID（包括子辖区）
         String month= sdf.format(now);
         //获取当前月份（用于查询分区数据）
         if(month!=null&&!month.equals("")){
        	month=month.substring(5,7);
         }
         //条件查询辖区ID
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
		 //登录用户所属辖区子辖区
		 if(person.getDepartment().getArea()!=null&&!person.getDepartment().getDeptId().equals("root")){
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
//		 if( area!=null){
//			 areaChild = areaService.getAreaChildList(area);
//			 if(areaChild.size()>0){
//				 String areas="";
//				 for(int i=0;i<areaChild.size();i++){
//					 areas+="'"+areaChild.get(i).getId()+"',";
//					 System.out.println(areaChild.get(i).getId());
//				 }
//				 where.append(" and area.id in ("+areas+"'"+area.getId()+"')");
//			 }else{
//				 where.append(" and area.id='"+area.getId()+"'");
//			 }
//		 }	
		//时间条件查询
		if(StringUtil.isNotEmpty(beginTime)){
			where.append(" and grsummary.queryTime >= '"+beginTime+"' ");
		}
		if(StringUtil.isNotEmpty(endTime)){
			where.append(" and grsummary.queryTime <= '"+endTime+"' ");
		}
		//辖区条件查询
		if(areaIds!=null&&!areaIds.equals("")){
			where.append(" and area.id in ("+areaIds+")");
		}
		//级别查询
		if(level!=null&&!level.equals("")){
			where.append(" and area.levels= '"+level+"'");
		}
		sb.append("select name,sum(total) from (select area.name,grsummary.* from grsummary,sys_area as area,sys_department as dept where 1=1"+where+" and grsummary.uporgcode=dept.deptcode_p and area.id=dept.areaid) as aa group by name");
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
	//用户查询统计图
	@Override
	public Map<String, String> personCount(String beginTime, String endTime,
			String departmentId, Person person,String level) throws Exception {
		Map<String,String> maps = new TreeMap<String, String>();
		StringBuffer sb = new StringBuffer();
		 Department dept=new Department();
		 List<Department> deptChild=new ArrayList();
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 Date now=new Date();
		 String deptCode="";
       String month= sdf.format(now);
       //获取当前月份（方便GRZXCXMX表分区数据查询）
       if(month!=null&&!month.equals("")){
       	month=month.substring(5,7);
       }
       //条件查询辖区ID
       if(departmentId!=null&&!departmentId.equals("")){
      	 List deptfind = this.find("select personalOrgCode from Department where deptId in ("+departmentId+") or parent.deptId in("+departmentId+")");
      	 for(int i=0;i<deptfind.size();i++){
        	   if(i!=deptfind.size()-1){
        		   deptCode+="'"+deptfind.get(i)+"',";
        	   }else{
        		   deptCode+="'"+deptfind.get(i)+"'";   
        	   }
           }
       }
//		if(beginTime==null&&endTime==null){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}else if(beginTime.equals("")&&endTime.equals("")){
//		   sb.append(" and substring(querytime,1,10)<=( select curdate()) and substring(querytime,1,10)>= (select DATE_ADD(curdate(),interval -day(curdate())+1 day))");
//		}
       //获取当前登录用户所属机构的子机构
		 if(person.getDepartment().getDeptId()!=null&&!person.getDepartment().getDeptId().equals("root")){
		 deptChild = departmentService.getAllChildren(person.getDepartment().getDeptId());
		 }
		sb.append("select queryusername,sum(total) from grsummary where 1=1 ");
        //判断登录用户，！root，只显示自己所属机构及子机构的数据
		 if(person.getDepartment()!=null&&!person.getDepartment().getDeptId().equals("root")){
			 if(deptChild.size()>0){
				 String depts="";
				 for(int i=0;i<deptChild.size();i++){					
				 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";
				 }
				 sb.append(" and upOrgCode in ("+depts+"'"+person.getDepartment().getPersonalOrgCode()+"')");
		     }else{
				 sb.append(" and upOrgCode = '"+person.getDepartment().getPersonalOrgCode()+"'");
			      }   
		}
		 //机构条件查询（选择机构机构下所有用户查询统计）
//		if( dept.getPersonalOrgCode()!=null){
//			 deptChild = departmentService.getAllChildren(dept.getDeptId());
//			 if(deptChild.size()>0){
//				 String depts="";
//				 for(int i=0;i<deptChild.size();i++){
//				 depts+="'"+deptChild.get(i).getPersonalOrgCode()+"',";
//				 }
//			sb.append(" and upOrgCode in ("+depts+"'"+dept.getPersonalOrgCode()+"')");
//			 }else{
//			sb.append(" and upOrgCode='"+dept.getPersonalOrgCode()+"'");
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
