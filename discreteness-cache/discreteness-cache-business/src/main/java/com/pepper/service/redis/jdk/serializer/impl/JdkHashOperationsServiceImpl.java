package com.pepper.service.redis.jdk.serializer.impl;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.service.redis.impl.HashOperationsImpl;
import com.pepper.service.redis.jdk.serializer.HashOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <H>
 * @param <HK>
 * @param <HV>
 */
@Service(interfaceClass = HashOperationsService.class)
public class JdkHashOperationsServiceImpl extends HashOperationsImpl<String, String, Object>  implements HashOperationsService{

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	protected RedisOperations<String, Object> getTemplate() {
		return redisTemplate;
	}
	
	
}
