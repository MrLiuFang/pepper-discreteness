package com.pepper.service.authentication.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 统一鉴权AOP
 * 
 * @author mrliu
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Authorize {

	/**
	 * 鉴权用户是否登录
	 * 
	 * @return
	 */
	boolean authorizeLogin() default true;

	/**
	 * 鉴权url资源是否有权限可调用
	 * 
	 * @return
	 */
	boolean authorizeResources() default true;


}
