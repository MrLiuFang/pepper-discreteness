package com.pepper.service.redis.impl;

import org.springframework.data.redis.core.RedisOperations;

/**
 * 
 * @author mrliu
 *
 */
public abstract class Template<H,V> {

	protected abstract RedisOperations<H,V> getTemplate();
}
