package com.platform.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import com.platform.framework.core.page.Page;

/**
 * 说明:通用DAO接口
 */
public interface GenericDao<T extends Serializable, PK extends Serializable>{
	
	/**
	 * -----------基本实体类操作增删改------------
	 */
	//根据主键加载一个对象
	public Object get(PK id);    //如果没有相应的实体，返回 null
	public Object load(PK id);   //如果没有相应的实体，抛出异常
	public Object getWithLock(PK id, LockMode lockMode);    //根据主键获取实体并加锁
	public Object loadWithLock(PK id, LockMode lockMode);   //根据主键获取实体并加锁
	//获取全部实体
	public List<T> loadAll();
	//更新实体
    public void update(T entity);
    //更新实体并加锁
    public void updateWithLock(T entity, LockMode lock);
    //存储实体到数据库
    public void save(T entity);
    //增加或更新实体
    public void saveOrUpdate(T entity);
    //增加或更新集合中的全部实体
    public void saveOrUpdateAll(Collection<T> entities);
    //删除指定的实体
    public void delete(T entity);
    //加锁并删除指定的实体
    public void deleteWithLock(T entity, LockMode lock);
    // 根据主键删除指定实体
    public void deleteByKey(PK id);
    //根据主键加锁并删除指定的实体
    public void deleteByKeyWithLock(PK id, LockMode lock);
    //删除集合中的全部实体
    public void deleteAll(Collection<T> entities);


	/**
	 * -----------hql查询方法------------
	 */
    //使用HSQL语句直接增加、更新、删除实体
    public int bulkUpdate(String queryString);
    //使用带参数的HSQL语句增加、更新、删除实体
    public int bulkUpdate(String queryString, Object[] values);

    // 使用HSQL语句检索数据
    public List find(String queryString);
    // 使用带参数的HSQL语句检索数据
    public List find(String queryString, Object[] values);
    public List find(String query, List parameters);
    // 使用带命名的参数的HSQL语句检索数据
    public List findByNamedParam(String queryString, String[] paramNames, Object[] values);

    // 使用命名的HSQL语句检索数据
    public List findByNamedQuery(String queryName);
    // 使用带参数的命名HSQL语句检索数据
    public List findByNamedQuery(String queryName, Object[] values);
    // 使用带命名参数的命名HSQL语句检索数据
    public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values);
    /**
	 * 使用带命名参数的命名HSQL语句检索数据
	 * @param queryNamed   HSQL名称
	 * @param queryString  HSQL语句
	 * @param map          参数值与参数名称Map
	 */
	public List findByNamedQueryAndNamedParam(final String queryNamed, final Map map);
	public List findByNamedParam(final String queryString, final Map map);
 
	/**
	 * 根据HQL语句查询结果集数量
	 * @param countHql 负责查询结果集数量的HQL语句
	 * @return Long    查询结果
	 */
	public Long getResultCount(String countHql);
	public Long getRowCount(String query, List parameters);
	
	/**
	 * 根据HQL语句查询结果集
	 * @param pageIndex    当面页号
	 * @param pageSize     每页大小
	 * @return List 查询结果集合
	 */
	public List find(String hql, int pageIndex, int pageSize);

	/**
	 * 根据HQL语句查询结果集
	 * @param query        hql语句
	 * @param parameters   要传的参数集合
	 * @param pageIndex        当面页号
	 * @param pageSize         每页大小
	 * @return List 查询结果集合
	 */
	public List find(final String query, final Object[] parameters, final int pageIndex, final int pageSize);
	public List find(final String hql, final List parameters, final Page pageInfo);
	
	
	
	/**
	 * -----------Criteria------------
	 */
    // 创建与会话无关的检索标准对象
    public DetachedCriteria createDetachedCriteria();
    // 创建与会话绑定的检索标准对象
    public Criteria createCriteria();
    // 使用指定的检索标准检索数据
    public List findByCriteria(DetachedCriteria criteria);
    // 使用指定的检索标准检索数据，返回部分记录
    public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);
    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
    public List<T> findEqualByEntity(T entity, String[] propertyNames);
    // 使用指定的实体及属性(非主键)检索（满足属性 like 串实体值）数据
    public List<T> findLikeByEntity(T entity, String[] propertyNames);
    // 使用指定的检索标准检索数据，返回指定范围的记录
    public Integer getRowCount(DetachedCriteria criteria);
    // 使用指定的检索标准检索数据，返回指定统计值
    public Object getStatValue(DetachedCriteria criteria, String propertyName, String StatName);
	
	
	/**
	 * -----------其它操作------------
	 */
	//删除一缓存中的对象
	public void evict(T entity);
	//merge对象
	public void merge(T entity);
	//清空所有一级缓存对象
	public void clear();
	//强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	public void flush();
    //加锁指定的实体
    public void lock(T entity, LockMode lockMode);
    //强制初始化指定的实体
    public void initialize(Object proxy);

    //-----------------------------------
    public int sqlExecute(final String sql);
}
