package com.pepper.service.redis.string.serializer.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.pepper.service.redis.impl.ZSetOperationsImpl;
import com.pepper.service.redis.string.serializer.ZSetOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = ZSetOperationsService.class)
@DependsOn(value={"redisTemplate"})
public class ZSetOperationsServiceImpl extends ZSetOperationsImpl<String, String> implements ZSetOperationsService {

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected RedisOperations<String, String> getTemplate() {
		return stringRedisTemplate;
	}

}
