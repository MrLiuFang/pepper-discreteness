package com.pepper.service.redis.string.serializer.impl;

import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.apache.dubbo.config.annotation.Service;
import com.pepper.service.redis.impl.ListOperationsImpl;
import com.pepper.service.redis.string.serializer.ListOperationsService;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
@Service(interfaceClass = ListOperationsService.class)
public class ListOperationsServiceImpl extends ListOperationsImpl<String, String> implements ListOperationsService {

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	protected RedisOperations<String, String> getTemplate() {
		return stringRedisTemplate;
	}

}
