package com.pepper.service.redis.string.serializer.impl;

import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import com.pepper.service.redis.impl.SetOperationsImpl;
import com.pepper.service.redis.string.serializer.SetOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = SetOperationsService.class)
public class SetOperationsServiceImpl extends SetOperationsImpl<String, String> implements SetOperationsService {

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected RedisOperations<String, String> getTemplate() {
		return stringRedisTemplate;
	}
}
