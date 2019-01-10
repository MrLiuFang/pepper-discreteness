package com.pepper.service.redis.jdk.serializer;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface ZSetOperationsService extends ZSetOperations<String, Object> {

}
