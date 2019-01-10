package com.pepper.service.redis.jdk.serializer;

import org.springframework.data.redis.core.ListOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface ListOperationsService extends ListOperations<String, Object> {

}
