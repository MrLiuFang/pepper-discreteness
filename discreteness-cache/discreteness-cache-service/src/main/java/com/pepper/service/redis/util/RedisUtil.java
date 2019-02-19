package com.pepper.service.redis.util;

import org.springframework.stereotype.Component;

import org.apache.dubbo.config.annotation.Reference;
import com.pepper.service.redis.string.serializer.HashOperationsService;
import com.pepper.service.redis.string.serializer.ListOperationsService;
import com.pepper.service.redis.string.serializer.SetOperationsService;
import com.pepper.service.redis.string.serializer.StringRedisTemplateService;
import com.pepper.service.redis.string.serializer.ValueOperationsService;
import com.pepper.service.redis.string.serializer.ZSetOperationsService;

/**
 * 请尽量不要使用该工具类操作redis
 * 
 * @author mrliu
 *
 */
@Component
@Deprecated
public class RedisUtil {

	@Reference(interfaceName="com.pepper.service.redis.string.serializer.ValueOperationsService")
	private ValueOperationsService valueOperationsService;

	@Reference(interfaceName="com.pepper.service.redis.string.serializer.ListOperationsService")
	private ListOperationsService listOperationsService;

	@Reference(interfaceName="com.pepper.service.redis.string.serializer.StringRedisTemplateService")
	private StringRedisTemplateService stringRedisTemplateService;
	
	@Reference(interfaceName="com.pepper.service.redis.string.serializer.SetOperationsService")
	private SetOperationsService setOperationsService;

	@Reference(interfaceName="com.pepper.service.redis.string.serializer.ZSetOperationsService")
	private ZSetOperationsService zSetOperationsService;
	
	@Reference(interfaceName="com.pepper.service.redis.string.serializer.HashOperationsService")
	private HashOperationsService hashOperationsService;

	public ValueOperationsService getValueOperationsService() {
		return valueOperationsService;
	}

	public ListOperationsService getListOperationsService() {
		return listOperationsService;
	}

	public StringRedisTemplateService getStringRedisTemplateService() {
		return stringRedisTemplateService;
	}

	public SetOperationsService getSetOperationsService() {
		return setOperationsService;
	}

	public ZSetOperationsService getzSetOperationsService() {
		return zSetOperationsService;
	}

	public HashOperationsService getHashOperationsService() {
		return hashOperationsService;
	}
	
	
	

}
