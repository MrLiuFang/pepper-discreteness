package com.pepper.business.api.core.auth;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.pepper.api.redis.ListOperationsService;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.SetOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.common.emuns.model.Scope;

/**
 * 登录鉴权
 * 
 * @author mrliu
 *
 */
public interface Authorize extends ApplicationListener<ContextRefreshedEvent> {

	/**
	 * 判断用户是否登录
	 * 
	 * @param token
	 * @return
	 */
	boolean isLogin(String token);

	/**
	 * 获取当前登录用户 由于core包不能引入具体的model包，所以只能返回map
	 * 
	 * @return
	 */
	Map<String, Object> getCurrentUser(String token);

	/**
	 * 删除鉴权信息
	 * 
	 * @param token
	 *            登录会话token
	 */
	void deleteAuthorizeInfo(String token);

	/**
	 * 设置用户鉴权信息
	 * 
	 * @param user_id
	 * @param token
	 */
	void setAuthorizeInfo(String user_id, String token);

	/**
	 * 根据token获取userid
	 * 
	 * @param token
	 * @return userid
	 */
	String getUserId(String token);

	/**
	 * 
	 * @return String
	 */
	String getTokenKey();

	/**
	 * 登录会话有效时长
	 * 
	 * @return
	 */
	Long getSessionTimeOut();

	

	/**
	 * 获取登录鉴权CookieName
	 * 
	 * @return
	 */
	String getAuthorizeCookieName();

	/**
	 * 删除用户资源权限
	 */
	void deleteUserResources(String userId);

	/**
	 * 存储用户资源到redis中
	 */
	void setUserResources(List<String> resourceList, String userId);

	/**
	 * 获取端类型
	 * 
	 * @return
	 */
	Scope getScope();

	/**
	 * 获取用户redis资源key
	 * 
	 * @return
	 */
	String getResourceKey(String userId);

	/**
	 * 后台用户登出，需要删掉jsp的auth标签的redis的code。
	 * 
	 * @param userId
	 * @return
	 */
	boolean deleteResourceCode(String userId);
}
