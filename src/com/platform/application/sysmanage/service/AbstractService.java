package com.platform.application.sysmanage.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
	/**
	 * Hibernate Session 工厂类.
	 */
	@Autowired
	protected SessionFactory sessionFactory;
}
