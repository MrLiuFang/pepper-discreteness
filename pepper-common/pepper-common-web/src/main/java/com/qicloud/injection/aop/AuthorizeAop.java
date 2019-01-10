package com.pepper.business.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pepper.common.emuns.model.Scope;

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
public @interface AuthorizeAop {

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

	/**
	 * 鉴权范围，指定端范围，默认为为后台。指定端后，登录用户只鉴权改端的登录用户，资源只鉴权该端的资源。
	 * 
	 * @see Scope.CONSOLE
	 * @return
	 */
	Scope authorizeScope() default Scope.CONSOLE;

}
