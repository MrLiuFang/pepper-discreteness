package com.pepper.service.authentication.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pepper.service.authentication.AuthorizeFactory;
import com.pepper.service.authentication.IAuthorize;

/**
 * 
 * @author mrliu
 *
 */
@Component
public class AuthorizeFactoryBean implements AuthorizeFactory {
	
	private static final Map<String, IAuthorize> map = new HashMap<String, IAuthorize>();

	@Override
	public IAuthorize getAuthorize(final String scope) {
		if(map.containsKey(scope)){
			return map.get(scope);
		}
		return null;
	}

	@Override
	public void setAuthorize(final String scope, final IAuthorize authorize) {
		map.put(scope, authorize);
	}
	
}
