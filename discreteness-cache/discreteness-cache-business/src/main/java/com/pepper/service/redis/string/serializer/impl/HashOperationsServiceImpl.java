package com.pepper.service.redis.string.serializer.impl;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.pepper.service.redis.impl.HashOperationsImpl;
import com.pepper.service.redis.string.serializer.HashOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <H>
 * @param <HK>
 * @param <HV>
 */
@Service(interfaceClass = HashOperationsService.class)
@DependsOn(value={"redisTemplate"})
public class HashOperationsServiceImpl extends HashOperationsImpl<String, String, String>  implements HashOperationsService{

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected RedisOperations<String, String> getTemplate() {
		return  stringRedisTemplate;
	}
	
	
}
