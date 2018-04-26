package com.platform.application.sysmanage.service;

import java.util.List;
import java.util.Map;

import com.platform.application.sysmanage.domain.Area;
import com.platform.application.sysmanage.domain.Person;
import com.platform.framework.core.dao.GenericDao;

public interface AreaService extends GenericDao<Area,String> {
	/**
	 * 加载辖区树
	 * @param id
	 * @return
	 * @throws Exception
	 */
  public String getArea(String id,Person person,Area area) throws Exception;
  /**
	 * 加载辖区树
	 * @param id root用户
	 * @return
	 * @throws Exception
	 */
  public String getArea(String id) throws Exception;
  /**
   * 保存辖区
   * @param area
   * @throws Exception
   */
  public void txSaveArea(Area area)throws Exception;
  /**
   * 编辑辖区
   * @param area
   * @throws Exception
   */
  public void txEditArea(Area area)throws Exception;
  /**
   * 删除辖区
   * @param area
   * @throws Exception
   */
  public void txDeleteArea(Area area)throws Exception;
  /**
   * 添加校验，不让删除有下级辖区的数据
   * @param area
   * @return
   */
  public List getParentList(Area area);
  /**
   * 查询属于一级的辖区
   * @return
   * @throws Exception
   */
  public List getAreaFirst()throws Exception;
  public List getFirstArea()throws Exception;

  /**
   * 查询属于一级的辖区
   * @return
   * @throws Exception
   */
  public String getAreaJson(Area area)throws Exception;
  /**
   * 查询一级和二级的辖区
   * @return
   * @throws Exception
   */
  public List getAreaList()throws Exception;
  /**
	 * 根据父id获取子辖区
	 * @param area
	 * @return
	 */
  public List getAreaChildList(Area area) throws Exception;
  /**
	 * 查询出code name对应的map
	 * @param 
	 * @return
	 */
  public Map<String ,String> getAreaXX()throws Exception;
  
  /**
   * 临时方法：用于批量给counyId赋值
   * @throws Exception
   */
  public void txLinshiUpdateAllArea() throws Exception;
  //根据ID获得Area
  public List<Area> getAreaById(String aid);
  //根据辖区管理机构
  public  String getManagerByAreaJson(Person person,String id) throws Exception;
  //根据地区名称获得Area
  public List<Area> getAreaByName(String name);
  //获取所有子机构的id
  public List getChildrenId(String pid);
}
