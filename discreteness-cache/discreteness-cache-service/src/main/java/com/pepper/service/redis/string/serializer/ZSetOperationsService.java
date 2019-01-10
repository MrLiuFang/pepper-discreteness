package com.pepper.service.redis.string.serializer;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface ZSetOperationsService extends ZSetOperations<String, String> {

}
