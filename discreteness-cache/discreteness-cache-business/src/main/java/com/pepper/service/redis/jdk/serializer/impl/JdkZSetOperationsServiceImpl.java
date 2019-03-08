package com.pepper.service.redis.jdk.serializer.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.pepper.service.redis.impl.ZSetOperationsImpl;
import com.pepper.service.redis.jdk.serializer.ZSetOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = ZSetOperationsService.class)
@DependsOn(value={"redisTemplate"})
public class JdkZSetOperationsServiceImpl extends ZSetOperationsImpl<String, Object> implements ZSetOperationsService {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	protected RedisOperations<String, Object> getTemplate() {
		return redisTemplate;
	}
}
