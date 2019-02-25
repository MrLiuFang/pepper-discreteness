package com.pepper.service.redis.jdk.serializer.impl;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import org.apache.dubbo.config.annotation.Service;
import com.pepper.service.redis.impl.ValueOperationsImpl;
import com.pepper.service.redis.jdk.serializer.ValueOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = ValueOperationsService.class)
@ConditionalOnBean(value={RedisTemplate.class})
public class JdkValueOperationsServiceImpl extends ValueOperationsImpl<String, Object> implements ValueOperationsService {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	protected RedisOperations<String, Object> getTemplate() {
		return redisTemplate;
	}
}
