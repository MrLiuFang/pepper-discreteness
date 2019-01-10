package com.pepper.service.redis.string.serializer;

import org.springframework.data.redis.core.HashOperations;

public interface HashOperationsService  extends HashOperations<String, String, String> {
	
}