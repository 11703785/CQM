package com.platform.application.common.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 拦截Controller
 * @author cyl
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationControllerLog {
	/**
	 *
	 * @return ""
	 */
	String description()  default "";
	/**
	 *
	 * @return 操作类型
	 */
	OperationType type();
}
