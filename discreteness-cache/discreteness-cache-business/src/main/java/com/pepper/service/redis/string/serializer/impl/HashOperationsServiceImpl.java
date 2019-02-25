package com.pepper.service.redis.string.serializer.impl;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.apache.dubbo.config.annotation.Service;
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
@ConditionalOnBean(value={StringRedisTemplate.class})
public class HashOperationsServiceImpl extends HashOperationsImpl<String, String, String>  implements HashOperationsService{

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected RedisOperations<String, String> getTemplate() {
		return  stringRedisTemplate;
	}
	
	
}
