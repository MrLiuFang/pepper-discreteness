package com.pepper.business.core.auth.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.api.redis.ListOperationsService;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.SetOperationsService;
import com.pepper.api.redis.ValueOperationsService;
import com.pepper.core.constant.RedisConstant;
import com.pepper.business.api.core.auth.Authorize;
import com.pepper.utils.Util;

/**
 * 
 * @author mrliu
 *
 */

public abstract class AuthorizeImpl implements Authorize {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Reference
	protected ValueOperationsService<String, String> valueOperationsService;

	@Reference
	protected RedisOperationsService<String, String> redisOperationsService;

	@Reference
	protected ListOperationsService<String, String> listOperationsService;

	@Reference
	protected SetOperationsService<String, String> setOperationsService;
	
	/**
	 * 用户可用资源URL list key
	 */
	public final String USER_RESOURCES_KEY = "user_resources_";

	@Override
	public boolean isLogin(String token) {
		if (Util.isNotEmpty(token) && Util.isNotEmpty(getUserId(token))) {
			return true;
		}
		return false;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

	}
	/**
	 * 删除用户资源，直接删除USER_RESOURCES_KEY + scope + userId
	 */
	@Override
	public void deleteUserResources(String userId) {
		redisOperationsService.delete(USER_RESOURCES_KEY + getScope().getKey() + "_" + userId);
	}

	@Override
	public boolean deleteResourceCode(String userId) {
		return redisOperationsService.delete(RedisConstant.USER_RESOURCE_CODE + userId);
	}

	@Override
	public synchronized void deleteAuthorizeInfo(String token) {
		if (StringUtils.hasText(token)) {
			redisOperationsService.delete(token + getTokenKey());
		}
	}

	@Override
	public synchronized void setAuthorizeInfo(String user_id, String token) {
		// 将用户登录token放到redis中，对应用户id，用于记录用户的登录状态。默认30分钟有效。
		valueOperationsService.set(token + getTokenKey(), user_id, getSessionTimeOut(), TimeUnit.SECONDS);
	}

	@Override
	public synchronized String getUserId(String token) {
		if (token == null) {
			return null;
		}
		return valueOperationsService.get(token + getTokenKey());
	}

	/**
	 * 存储用户资源到redis中。将用户资源以USER_RESOURCES_KEY + scope + userId为key，放到redis set中
	 */
	public void setUserResourcesReal(String key, List<String> resourceList, String userId) {
		if (Util.isNotEmpty(resourceList)) {
			List<String> resourceListNew = new ArrayList<String>();
			for (String urlJoinStr : resourceList) {
				if (Util.isNotEmpty(urlJoinStr)) {
					for (String url : urlJoinStr.split(";")) {
						// 因为鉴权拦截器拦截的url都是/开头的，所以这里的url也必须以/开头保存。
						if (url.startsWith("/")) {
							resourceListNew.add(url);
						} else {
							resourceListNew.add("/" + url);
						}
					}
				}
			}
			String[] resourceListArr = new String[resourceListNew.size()];
			resourceListArr = resourceListNew.toArray(resourceListArr);
			setOperationsService.add(key, resourceListArr);
		}
	}
	
	@Override
	public String getResourceKey(String userId) {
		return USER_RESOURCES_KEY + getScope().getKey() + "_" + userId;
	}

	/**
	 * 存储用户资源到redis中。将用户资源以USER_RESOURCES_KEY + scope + userId为key，放到redis set中
	 */
	@Override
	public void setUserResources(List<String> resourceList, String userId) {
		setUserResourcesReal(USER_RESOURCES_KEY + getScope().getKey() + "_" + userId, resourceList, userId);
	}
	
	public Map<String, Object> getCurrentUser(String token) {
		String userId = getUserId(token);
		Map<String, Object> user = jdbcTemplate.queryForMap(" select * from t_member where id =? ", userId);
		return user;
	}

}
