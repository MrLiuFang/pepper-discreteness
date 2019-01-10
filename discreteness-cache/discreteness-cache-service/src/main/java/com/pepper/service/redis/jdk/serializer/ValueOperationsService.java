package com.pepper.service.redis.jdk.serializer;

import org.springframework.data.redis.core.ValueOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface ValueOperationsService extends ValueOperations<String, Object> {

}
