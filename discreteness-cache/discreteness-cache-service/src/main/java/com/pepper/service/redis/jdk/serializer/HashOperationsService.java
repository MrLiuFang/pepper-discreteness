package com.pepper.service.redis.jdk.serializer;

import org.springframework.data.redis.core.HashOperations;

public interface HashOperationsService  extends HashOperations<String, String, Object> {
	
}