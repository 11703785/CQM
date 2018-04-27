package com.platform.application.common.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 系统缓存注入注解类.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CacheComponent {
	/**
	 * 设置缓存组件名称.
	 *
	 * @return 缓存组件名称
	 */
	String value() default "";
}
