package com.pepper.service.redis.jdk.serializer;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface RedisTemplateService  extends RedisOperations<String, Object>, BeanClassLoaderAware{
	
}
