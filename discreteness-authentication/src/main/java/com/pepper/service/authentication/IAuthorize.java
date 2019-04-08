package com.pepper.service.authentication;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

import com.pepper.common.emuns.Scope;

/**
 * 登录鉴权
 * 
 * @author mrliu
 *
 */
public interface IAuthorize extends ApplicationListener<ContextRefreshedEvent>{

	/**
	 * 判断用户是否登录
	 * 
	 * @param token
	 * @return
	 */
	boolean isLogin(@NonNull final String token);

	/**
	 * 获取当前登录用户,由于各种业务可能导致用户模型不确定，所以直接返回object，使用时进行转化
	 * （数据来源redis。使用com.pepper.service.redis.jdk.serializer.ValueOperationsService进行复杂对象序列化
	 * 不能使用com.pepper.service.redis.string.serializer.ValueOperationsService进行操作，此接口无法序列化复杂对象）
	 * 
	 * @return
	 */
	Object getCurrentUser(@NonNull final String token);
	
	/**
	 * 获取当前登录用户,由于各种业务可能导致用户模型不确定，所以直接返回object，使用时进行类型转化
	 * （数据来源redis。使用com.pepper.service.redis.jdk.serializer.ValueOperationsService进行复杂对象序列化
	 * 不能使用com.pepper.service.redis.string.serializer.ValueOperationsService进行操作，此接口无法序列化复杂对象）
	 * 
	 * @return
	 */
	Object getCurrentUser();
	
	/**
	 * 
	 * @param userId
	 * @param currentUser
	 */
	void setCurrentUser(@NonNull final String userId,Object currentUser);

	/**
	 * 删除鉴权信息
	 * 
	 * @param token
	 *            登录会话token
	 */
	void deleteAuthorizeInfo(@NonNull final String token);

	/**
	 * 设置用户鉴权信息
	 * 
	 * @param user_id
	 * @param token
	 */
	void setAuthorizeInfo(@NonNull final String userId, @NonNull final String token);

	/**
	 * 根据token获取userid
	 * 
	 * @param token
	 * @return userid
	 */
	String getUserId(@NonNull final String token);

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
	 * 删除用户资源权限
	 */
	void deleteUserResources(@NonNull final String userId);

	/**
	 * 存储用户资源到redis中
	 */
	void setUserResources(@NonNull final List<String> resourceList,@NonNull final  String userId);

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
	String getResourceKey(@NonNull final String userId);

	/**
	 * 后台用户登出，需要删掉jsp的auth标签的redis的code。
	 * 
	 * @param userId
	 * @return
	 */
	boolean deleteResourceCode(@NonNull final String userId);
}
