package com.pepper.service.redis.jdk.serializer.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.service.redis.impl.ListOperationsImpl;
import com.pepper.service.redis.jdk.serializer.ListOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = ListOperationsService.class)
public class JdkListOperationsServiceImpl extends ListOperationsImpl<String, Object> implements ListOperationsService{

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	protected RedisOperations<String, Object> getTemplate() {
		return redisTemplate;
	}
}
