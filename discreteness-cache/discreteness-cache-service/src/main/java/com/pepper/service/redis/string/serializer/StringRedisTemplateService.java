package com.pepper.service.redis.string.serializer;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface StringRedisTemplateService extends RedisOperations<String, String>, BeanClassLoaderAware {
	
}
