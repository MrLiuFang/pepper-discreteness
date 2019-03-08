package com.pepper.service.redis.jdk.serializer.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.pepper.service.redis.impl.SetOperationsImpl;
import com.pepper.service.redis.jdk.serializer.SetOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = SetOperationsService.class)
@DependsOn(value={"redisTemplate"})
public class JdkSetOperationsServiceImpl extends SetOperationsImpl<String, Object> implements SetOperationsService {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	protected RedisOperations<String, Object> getTemplate() {
		return redisTemplate;
	}
}
