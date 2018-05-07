package com.platform.application.common.hibernate;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.BulkInsertionCapableIdentifierGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.PostInsertIdentifierGenerator;
import org.hibernate.id.PostInsertIdentityPersister;
import org.hibernate.id.SequenceIdentityGenerator;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
import org.hibernate.type.Type;

/**
 * Hibernate主键生成器.
 */
public class AutoIdentifierGenerator implements IdentifierGenerator, Configurable,
PostInsertIdentifierGenerator, BulkInsertionCapableIdentifierGenerator {

	/**
	 * 主键生成器.
	 */
	private PostInsertIdentifierGenerator generator;

	@Override
	public void configure(final Type type, final Properties params, final Dialect d) throws MappingException {
		if (d instanceof Oracle8iDialect) {
			SequenceIdentityGenerator sequenceGenerator = new SequenceIdentityGenerator();
			sequenceGenerator.configure(type, params, d);
			this.generator = sequenceGenerator;
		} else {
			this.generator = new IdentityGenerator();
		}
	}

	@Override
	public String determineBulkInsertionIdentifierGenerationSelectFragment(final Dialect dialect) {
		return null;
	}

	@Override
	public Serializable generate(final SessionImplementor session, final Object object) throws HibernateException {
		return this.generator.generate(session, object);
	}

	@Override
	public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(
			final PostInsertIdentityPersister persister, final Dialect dialect, final boolean isGetGeneratedKeysEnabled)
					throws HibernateException {
		return this.generator.getInsertGeneratedIdentifierDelegate(persister, dialect, isGetGeneratedKeysEnabled);
	}

	@Override
	public boolean supportsBulkInsertionIdentifierGeneration() {
		if (this.generator instanceof IdentityGenerator) {
			return true;
		}
		return false;
	}
}
