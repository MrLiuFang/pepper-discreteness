package com.pepper.service.redis.string.serializer;

import org.springframework.data.redis.core.ListOperations;

/**
 * 
 * @author mrliu
 *
 * @param <K>
 * @param <V>
 */
public interface ListOperationsService extends ListOperations<String, String> {

}
