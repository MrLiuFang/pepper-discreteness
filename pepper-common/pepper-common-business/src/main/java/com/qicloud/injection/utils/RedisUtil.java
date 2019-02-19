package com.pepper.business.utils;

import org.springframework.stereotype.Component;

import org.apache.dubbo.config.annotation.Reference;
import com.pepper.api.redis.ListOperationsService;
import com.pepper.api.redis.RedisOperationsService;
import com.pepper.api.redis.SetOperationsService;
import com.pepper.api.redis.ValueOperationsService;

/**
 * 请尽量不要使用该工具类操作redis
 * 
 * @author mrliu
 *
 */
@Component
@Deprecated
public class RedisUtil {

	@Reference
	private ValueOperationsService<String, String> valueOperationsService;

	@Reference
	private ListOperationsService<String, String> listOperationsService;

	@Reference
	private RedisOperationsService<String, String> redisOperationsService;

	@Reference
	private SetOperationsService<String, String> setOperationsService;

	public ValueOperationsService<String, String> getValueOperationsService() {
		return valueOperationsService;
	}

	public ListOperationsService<String, String> getListOperationsService() {
		return listOperationsService;
	}

	public RedisOperationsService<String, String> getRedisOperationsService() {
		return redisOperationsService;
	}

	public SetOperationsService<String, String> getSetOperationsService() {
		return setOperationsService;
	}

}
