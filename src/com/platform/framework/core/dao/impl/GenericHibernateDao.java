package com.platform.framework.core.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.platform.application.operation.domain.OptLog;
import com.platform.framework.common.util.StringUtil;
import com.platform.framework.core.dao.GenericDao;
import com.platform.framework.core.page.Page;

/**
 * 说明:通用DAO实现类（针对Hibernate）
 */
@Repository("dao")
public class GenericHibernateDao<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T,PK>{
	@Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
    
	//实体类类型(由构造方法自动赋值)
    private Class<T> entityClass;
	public GenericHibernateDao(){
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
	    }
	}
	
	/**
	 * -----------基本实体类操作增删改------------
	 */
	public T get(PK id){
		return (T)getHibernateTemplate().get(entityClass, id);
	}
	
	public T getWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().get(entityClass, id, lock);
		if (t != null) this.flush(); //立即刷新，否则锁不会生效
		return t;
	}

	public T load(PK id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	public T loadWithLock(PK id, LockMode lock) {
		T t = (T) getHibernateTemplate().load(entityClass, id, lock);
		if (t != null) this.flush(); // 立即刷新，否则锁不会生效
		return t;
	}
	
	public List<T> loadAll() {
		return (List<T>) getHibernateTemplate().loadAll(entityClass);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	public void updateWithLock(T entity, LockMode lock) {
		getHibernateTemplate().update(entity, lock);
		this.flush();  // 立即刷新，否则锁不会生效
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
    
	public void save(OptLog log){
		getHibernateTemplate().save(log);
	}
	
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<T> entities) {
		for(T t:entities){
			getHibernateTemplate().saveOrUpdate(t);
		}
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void deleteWithLock(T entity, LockMode lock) {
		getHibernateTemplate().delete(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效
	}

	public void deleteByKey(PK id) {
		this.delete(this.load(id));
	}

	public void deleteByKeyWithLock(PK id, LockMode lock) {
		this.deleteWithLock(this.load(id), lock);
	}

	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}
	
	/**
	 * -----------hql查询方法------------
	 */	
    public int bulkUpdate(String queryString) {
        return getHibernateTemplate().bulkUpdate(queryString);
    }

    public int bulkUpdate(String queryString, Object[] values) {
        return getHibernateTemplate().bulkUpdate(queryString, values);
    }

    public List find(String queryString) {
        return getHibernateTemplate().find(queryString);
    }

    public List find(String queryString, Object[] values) {
    	
        return getHibernateTemplate().find(queryString, values);
    }
    
    public List find(final String queryString,final List params) {
    	
    	List list =  getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createQuery(queryString);
				setParameters(query, params);
				return query.list();
			}
		});
    	
    	
        return list;
    }

    public List findByNamedParam(String queryString, String[] paramNames, Object[] values) {
        return getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
    }

    public List findByNamedQuery(String queryName) {
        return getHibernateTemplate().findByNamedQuery(queryName);
    }
    
    public List findByNamedQuery(String queryName, Object[] values) {
        return getHibernateTemplate().findByNamedQuery(queryName, values);
    }

    public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }
    
	public List findByNamedQueryAndNamedParam(final String queryNamed, final Map map){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.getNamedQuery(queryNamed);
				Set entiys = map.entrySet();
				Iterator it = entiys.iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry) it.next();
					String objKey = (String) entry.getKey();
					Object objValue = (Object) entry.getValue();
					query.setParameter(objKey, objValue);
				}
				return query.list();
			}
		});
	}
	
	public List findByNamedParam(final String queryString, final Map map){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createQuery(queryString);
				Set entiys = map.entrySet();
				Iterator it = entiys.iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry) it.next();
					String objKey = (String) entry.getKey();
					Object objValue = (Object) entry.getValue();
					query.setParameter(objKey, objValue);
				}
				return query.list();
			}
		});
	}
	
	public Long getResultCount(String countHql){
		List list = this.find(countHql);
		return (Long) list.get(0);
	}
	
	public Long getRowCount(final String query, final List parameters){
		Long i = (Long) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException{
				StringBuffer coutSql = new StringBuffer("select count(*) ");
				String trimSQL = query;
				String tempSQL = query.toLowerCase();
				if(tempSQL.trim().startsWith("select ", 0)){
					trimSQL = query.substring(tempSQL.indexOf(" from "), query
							.length());
					tempSQL = tempSQL.substring(tempSQL.indexOf(" from "),
							tempSQL.length());
				}
				if(StringUtil.contains(tempSQL, " order by ")){
					trimSQL = trimSQL.substring(0, tempSQL
							.indexOf(" order by "));
				}
				coutSql.append(trimSQL);
				Query qu = session.createQuery(coutSql.toString());
				setParameters(qu, parameters);

				List list=qu.list();
				Iterator irr = qu.iterate();
				//Long n=Long.valueOf(list.size());
				return irr.next() ;
			}
		});
		return i;
	}
	
	public List find(final String hql, final int pageIndex, final int pageSize){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createQuery(hql);
				int startIndex = (pageIndex - 1) * pageSize;
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}
	
	public List find(final String hql, final Object[] parameters, final int pageIndex, final int pageSize){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createQuery(hql);
				if(parameters != null && parameters.length > 0){
					for(int i = 0; i < parameters.length; i++){
						query.setParameter(i, parameters[i]);
					}
				}
				int startIndex = (pageIndex - 1) * pageSize;
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	public List find(final String hql, final List parameters,final Page pageInfo){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				if(null != pageInfo){
					pageInfo.setTotalCount(getRowCount(hql, parameters).longValue());
				}
				Query query = session.createQuery(hql);
				setParameters(query, parameters);
				if(null != pageInfo){
					int startIndex = (pageInfo.getPageIndex() - 1) * pageInfo.getPageSize();
					query.setFirstResult(startIndex);
					query.setMaxResults(pageInfo.getPageSize());
				}
				return query.list();
			}
		});
	}


	/**
	 * 设置查询的属性
	 * @param query
	 * @param parameters
	 */
	private void setParameters(Query query, List parameters){
		for(int i = 0; i < parameters.size(); i++){
			query.setParameter(i, parameters.get(i));
		}
	}
	private void setParameters(Query query, String[] nameForSetVar,	Object[] parameters){
		for(int i = 0; i < parameters.length; i++){
			if(parameters[i] instanceof List){
				query.setParameterList(nameForSetVar[i], (List) parameters[i]);
			}else{
				query.setParameter(nameForSetVar[i], parameters[i]);
			}
		}
	}

	
	
	/**
	 * -----------Criteria------------
	 */
    public DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(this.entityClass);
    }
    public Criteria createCriteria() {
        return this.createDetachedCriteria().getExecutableCriteria(
                this.getSessionFactory().getCurrentSession());
    }
    public List findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }
    public List findByCriteria(DetachedCriteria criteria, int firstResult,
            int maxResults) {
        return getHibernateTemplate().findByCriteria(criteria, firstResult,
                maxResults);
    }
    public List<T> findEqualByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        Example exam = Example.create(entity);
        exam.excludeZeroes();
        String[] defPropertys = getSessionFactory().getClassMetadata(
                entityClass).getPropertyNames();
        for (String defProperty : defPropertys) {
            int ii = 0;
            for (ii = 0; ii < propertyNames.length; ++ii) {
                if (defProperty.equals(propertyNames[ii])) {
                    criteria.addOrder(Order.asc(defProperty));
                    break;
                }
            }
            if (ii == propertyNames.length) {
                exam.excludeProperty(defProperty);
            }
        }
        criteria.add(exam);
        return (List<T>) criteria.list();
    }
    public List<T> findLikeByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        for (String property : propertyNames) {
            try {
                Object value = PropertyUtils.getProperty(entity, property);
                if (value instanceof String) {
                    criteria.add(Restrictions.like(property, (String) value,
                            MatchMode.ANYWHERE));
                    criteria.addOrder(Order.asc(property));
                } else {
                    criteria.add(Restrictions.eq(property, value));
                    criteria.addOrder(Order.asc(property));
                }
            } catch (Exception ex) {
                // 忽略无效的检索参考数据
            }
        }
        return (List<T>) criteria.list();
    }

    public Integer getRowCount(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());
        List list = this.findByCriteria(criteria, 0, 1);
        return (Integer) list.get(0);
    }

    public Object getStatValue(DetachedCriteria criteria, String propertyName,
            String StatName) {
        if (StatName.toLowerCase().equals("max"))
            criteria.setProjection(Projections.max(propertyName));
        else if (StatName.toLowerCase().equals("min"))
            criteria.setProjection(Projections.min(propertyName));
        else if (StatName.toLowerCase().equals("avg"))
            criteria.setProjection(Projections.avg(propertyName));
        else if (StatName.toLowerCase().equals("sum"))
            criteria.setProjection(Projections.sum(propertyName));
        else
            return null;
        List list = this.findByCriteria(criteria, 0, 1);
        return list.get(0);
    }
	
	
	/**
	 * -----------其它操作------------
	 */
	public void evict(T entity){
		this.getHibernateTemplate().evict(entity);
	}

	public void merge(T entity){
		this.getHibernateTemplate().merge(entity);
	}

	public void clear(){
		this.getHibernateTemplate().clear();
	}

	public void flush(){
		this.getHibernateTemplate().flush();
	}

    public void lock(T entity, LockMode lock) {
        getHibernateTemplate().lock(entity, lock);
    }

    public void initialize(Object proxy) {
        getHibernateTemplate().initialize(proxy);
    }
    
    
    
    public int sqlExecute(final String sql){
    	Object s=  getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query query = session.createSQLQuery(sql);
				return query.executeUpdate();
			}
		});
    	return Integer.valueOf(s.toString());
    }
	 
/**
 * 当涉及到分组时用这个方法
 */
	 
    public List findGroup(final String hql, final List parameters,final Page pageInfo){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				if(null != pageInfo){
					pageInfo.setTotalCount(getRowCountGroup(hql, parameters).longValue());	
				}
				Query query = session.createQuery(hql);
				setParameters(query, parameters);
				if(null != pageInfo){
					int startIndex = (pageInfo.getPageIndex() - 1) * pageInfo.getPageSize();
					query.setFirstResult(startIndex);
					query.setMaxResults(pageInfo.getPageSize());
				}
				return query.list();
			}
		});
	}

    public Long getRowCountGroup(final String query, final List parameters){
		Long i = (Long) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException{
				StringBuffer coutSql = new StringBuffer("select count(*) ");
				String trimSQL = query;
				String tempSQL = query.toLowerCase();
				if(tempSQL.trim().startsWith("select ", 0)){
					trimSQL = query.substring(tempSQL.indexOf(" from "), query
							.length());
					tempSQL = tempSQL.substring(tempSQL.indexOf(" from "),
							tempSQL.length());
				}
				if(StringUtil.contains(tempSQL, " order by ")){
					trimSQL = trimSQL.substring(0, tempSQL
							.indexOf(" order by "));
				}
				coutSql.append(trimSQL);
				Query qu = session.createQuery(coutSql.toString());
				setParameters(qu, parameters);
				List list=qu.list();
				//Iterator irr = qu.iterate();
				Long n=Long.valueOf(list.size());
				return n;
			}
		});
		return i;
	}
	 
    /********************************执行 sql（直接去数据库执行sql，避开hibernate自己的封装）******************************/
    /**
     * 分页查询
     * @param hql
     * @param pageInfo
     * @return
     */
    public List findSQL(final String hql, final Page pageInfo){
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				if(null != pageInfo){
					pageInfo.setTotalCount(getRowCountSQL(hql).longValue());
				}
				Query query = session.createSQLQuery(hql);
				if(null != pageInfo){
					int startIndex = (pageInfo.getPageIndex() - 1) * pageInfo.getPageSize();
					query.setFirstResult(startIndex);
					query.setMaxResults(pageInfo.getPageSize());
				}
				return query.list();
			}
		});
	}
    
    public Long getRowCountSQL(final String query){
		Long i = (Long) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException{
				StringBuffer coutSql = new StringBuffer("select count(*) ");
				String trimSQL = query;
				String tempSQL = query.toLowerCase();
				if(tempSQL.trim().startsWith("select ", 0)){
					trimSQL = query.substring(tempSQL.indexOf(" from "), query
							.length());
					tempSQL = tempSQL.substring(tempSQL.indexOf(" from "),
							tempSQL.length());
				}
				if(StringUtil.contains(tempSQL, " order by ")){
					trimSQL = trimSQL.substring(0, tempSQL
							.indexOf(" order by "));
				}
				coutSql.append(trimSQL);
				Query qu = session.createSQLQuery(coutSql.toString());
				
				List list=qu.list();
				if(list.size()>0){
					return Long.valueOf(String.valueOf(list.get(0)));
				}else{
					return 0;
				}
			}
		});
		return i;
	}
    
    /**
     * 不分页查询
     * @param sql
     * @return
     */
    public List findSQL(final String sql){
    	SQLQuery query=  getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session){
				Query q = session.createSQLQuery(sql);
				return q;
			}
		});
    	return query.list();
    }
}