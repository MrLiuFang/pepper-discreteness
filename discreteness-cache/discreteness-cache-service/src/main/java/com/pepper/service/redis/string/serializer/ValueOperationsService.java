package com.pepper.service.redis.string.serializer;

import org.springframework.data.redis.core.ValueOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface ValueOperationsService extends ValueOperations<String, String> {

}
