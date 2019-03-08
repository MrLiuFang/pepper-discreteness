package com.pepper.service.redis.string.serializer.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.pepper.service.redis.impl.ValueOperationsImpl;
import com.pepper.service.redis.string.serializer.ValueOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = ValueOperationsService.class)
@DependsOn(value={"redisTemplate"})
public class ValueOperationsServiceImpl extends ValueOperationsImpl<String, String> implements ValueOperationsService {

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected RedisOperations<String, String> getTemplate() {
		return stringRedisTemplate;
	}
}
