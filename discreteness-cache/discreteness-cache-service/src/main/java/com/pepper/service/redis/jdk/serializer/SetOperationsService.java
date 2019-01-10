package com.pepper.service.redis.jdk.serializer;

import org.springframework.data.redis.core.SetOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface SetOperationsService extends SetOperations<String, Object> {

}
