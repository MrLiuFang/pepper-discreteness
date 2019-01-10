package com.pepper.service.authentication.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pepper.core.constant.GlobalConstant;
import com.pepper.service.authentication.IAuthorize;
import com.pepper.service.redis.string.serializer.ListOperationsService;
import com.pepper.service.redis.string.serializer.SetOperationsService;
import com.pepper.service.redis.string.serializer.StringRedisTemplateService;
import com.pepper.service.redis.string.serializer.ValueOperationsService;
/**
 * 
 * @author mrliu
 *
 */

public abstract class AuthorizeImpl implements IAuthorize {
	
	@Autowired  
	protected Environment environment; 
	
	@Reference
	protected ValueOperationsService valueOperationsService;
	
	@Reference
	protected com.pepper.service.redis.jdk.serializer.ValueOperationsService jdkValueOperationsService;

	@Reference
	protected ListOperationsService listOperationsService;

	@Reference
	protected SetOperationsService setOperationsService;
	
	@Reference
	protected StringRedisTemplateService stringRedisTemplateService;
	

	@Override
	public boolean isLogin(@NonNull final String token) {
		if (StringUtils.hasText(token) && StringUtils.hasText(getUserId(token))) {
			return true;
		}
		return false;
	}

	/**
	 * 删除用户资源，直接删除USER_RESOURCES_KEY + scope + userId
	 */
	@Override
	public void deleteUserResources(@NonNull final String userId) {
		stringRedisTemplateService.delete(GlobalConstant.USER_RESOURCES_KEY + getScope().getKey() + "_" + userId);
	}

	@Override
	public boolean deleteResourceCode(@NonNull final String userId) {
		return stringRedisTemplateService.delete(GlobalConstant.USER_RESOURCE_CODE + userId);
	}

	@Override
	public void deleteAuthorizeInfo(@NonNull final String token) {
		if (StringUtils.hasText(token)) {
			stringRedisTemplateService.delete(token + getTokenKey());
		}
	}

	@Override
	public void setAuthorizeInfo(@NonNull final String userId,@NonNull final String token) {
		// 将用户登录token放到redis中，对应用户id，用于记录用户的登录状态。默认30分钟有效。
		valueOperationsService.set(token + getTokenKey(), userId, getSessionTimeOut(), TimeUnit.SECONDS);
		//设置登录会话token作用域
		valueOperationsService.set(token+GlobalConstant.AUTHORIZE_TOKEN_SCOPE,getScope().toString(), getSessionTimeOut(), TimeUnit.SECONDS);
	}

	@Override
	public String getUserId(@NonNull final String token) {
		if (token == null) {
			return null;
		}
		return valueOperationsService.get(token + getTokenKey());
	}

	/**
	 * 存储用户资源到redis中。将用户资源以USER_RESOURCES_KEY + scope + userId为key，放到redis set中
	 */
	public void setUserResourcesReal(@NonNull final String key,@NonNull final  List<String> resourceList,@NonNull final  String userId) {
		if (resourceList!=null && resourceList.size()>0) {
			List<String> resourceListNew = new ArrayList<String>();
			for (String urlJoinStr : resourceList) {
				if (StringUtils.hasText(urlJoinStr)) {
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
	public String getResourceKey(@NonNull final String userId) {
		return GlobalConstant.USER_RESOURCES_KEY + getScope().getKey() + "_" + userId;
	}

	/**
	 * 存储用户资源到redis中。将用户资源以USER_RESOURCES_KEY + scope + userId为key，放到redis set中
	 */
	@Override
	public void setUserResources(@NonNull final List<String> resourceList,@NonNull final  String userId) {
		setUserResourcesReal(GlobalConstant.USER_RESOURCES_KEY + getScope().getKey() + "_" + userId, resourceList, userId);
	}
	
	@Override
	public void setCurrentUser(String userId, Object currentUser) {
		jdkValueOperationsService.set(userId, currentUser);
	}
	
	@Override
	public Object getCurrentUser(String token) {
		String userId = getUserId(token);
		if(StringUtils.hasLength(userId)){
			return jdkValueOperationsService.get(userId);
		}
		return null;
	}
	
	@Override
	public Object getCurrentUser() {
		
		return null;
	}

}
