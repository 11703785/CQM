package com.platform.application.sysmanage.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractService {
	/**
	 * Hibernate Session 工厂类.
	 */
	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * 查询分页数.
	 */
	@Value("${query.pagesize}")
	protected int pageSize;
}
